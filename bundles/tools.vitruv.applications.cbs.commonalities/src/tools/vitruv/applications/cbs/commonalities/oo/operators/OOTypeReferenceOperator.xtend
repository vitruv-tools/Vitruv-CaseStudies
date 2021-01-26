package tools.vitruv.applications.cbs.commonalities.oo.operators

import tools.vitruv.applications.cbs.commonalities.domaincommon.CommonPrimitiveType
import tools.vitruv.applications.cbs.commonalities.domaincommon.operators.AbstractTypeReferenceOperator
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.Intermediate
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static tools.vitruv.framework.util.XtendAssertHelper.*

/**
 * Maps between type references of the ObjectOrientedDesign concept domain and
 * another concept domain.
 */
@AttributeMappingOperator(
	name='ooTypeReference',
	commonalityAttributeType=@AttributeType(multiValued=false, type=String),
	participationAttributeType=@AttributeType(multiValued=false, type=String)
)
class OOTypeReferenceOperator extends AbstractTypeReferenceOperator<String, Object> {

	/**
	 * @param executionState the reactions execution state
	 * @param targetConceptDomainName the name of the target concept domain
	 */
	new(ReactionExecutionState executionState, String targetConceptDomainName) {
		// Note: The participation domain name also matches the concept domain name
		super(executionState, 'ObjectOrientedDesign', targetConceptDomainName, Intermediate)
	}

	override protected getReferencedDomainType(String domainTypeReference) {
		assertTrue(domainTypeReference !== null)
		if (domainTypeReference.isIntermediateReference) {
			// Intermediate reference:
			val intermediateModelResource = getIntermediateResource(participationDomainName)
			val referencedIntermediateType = intermediateModelResource.resolveIntermediateReference(domainTypeReference)
			return referencedIntermediateType
		} else {
			// Primitive:
			return CommonPrimitiveType.byName(domainTypeReference) // returns null for empty String
		}
	}

	override protected getDomainTypeReference(Object domainType) {
		assertTrue(domainType !== null)
		if (domainType instanceof Intermediate) {
			return domainType.toIntermediateReference
		} else if (domainType instanceof CommonPrimitiveType) {
			return domainType.name
		} else {
			throw new IllegalStateException("Unexpected intermediate type: " + domainType)
		}
	}

	override protected asString(Object domainType) {
		if (domainType instanceof Intermediate) {
			// Check if we find a 'name' feature:
			val nameFeature = domainType.eClass.EStructuralFeatures.findFirst[name == 'name']
			if (nameFeature !== null && nameFeature.EType.instanceClass == String) {
				val name = domainType.eGet(nameFeature) as String
				if (!name.nullOrEmpty) {
					return name
				}
			}
		} else if (domainType instanceof CommonPrimitiveType) {
			return domainType.name
		} else {
			return domainType.toString
		}
	}

	override protected toCommonPrimitiveType(Object domainType) {
		assertTrue(domainType !== null)
		if (domainType instanceof CommonPrimitiveType) {
			return domainType
		}
		return null
	}

	override protected toPrimitiveDomainTypeReference(CommonPrimitiveType commonPrimitiveType) {
		// OO and CBS use the same common primitive types:
		return commonPrimitiveType.name
	}
}

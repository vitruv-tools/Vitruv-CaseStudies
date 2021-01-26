package tools.vitruv.applications.cbs.commonalities.uml.operators

import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Type
import tools.vitruv.applications.cbs.commonalities.domaincommon.CommonPrimitiveType
import tools.vitruv.applications.cbs.commonalities.domaincommon.operators.AbstractTypeReferenceOperator
import tools.vitruv.applications.cbs.commonalities.uml.UmlPrimitiveType
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static tools.vitruv.framework.util.XtendAssertHelper.*

/**
 * Maps between references to UML {@link Type}s and an intermediate
 * representation of these type references.
 */
@AttributeMappingOperator(
	name='umlTypeReference',
	commonalityAttributeType=@AttributeType(multiValued=false, type=String),
	participationAttributeType=@AttributeType(multiValued=false, type=Type)
)
class UmlTypeReferenceOperator extends AbstractTypeReferenceOperator<Type, Type> {

	/**
	 * @param executionState the reactions execution state
	 * @param targetConceptDomainName the name of the target concept domain
	 */
	new(ReactionExecutionState executionState, String targetConceptDomainName) {
		super(executionState, 'UML', targetConceptDomainName, Type)
	}

	override protected getReferencedDomainType(Type domainTypeReference) {
		// UML does not use a separate type to represent type references:
		return domainTypeReference
	}

	override protected getDomainTypeReference(Type domainType) {
		// UML does not use a separate type to represent type references:
		return domainType
	}

	override protected asString(Type domainType) {
		return domainType.qualifiedName
	}

	private def toCommonPrimitiveType(PrimitiveType primitiveType) {
		assertTrue(primitiveType !== null)
		switch (primitiveType.name) {
			case UmlPrimitiveType.BOOLEAN.umlTypeName:
				return CommonPrimitiveType.BOOLEAN
			case UmlPrimitiveType.INTEGER.umlTypeName:
				return CommonPrimitiveType.INTEGER
			case UmlPrimitiveType.REAL.umlTypeName:
				return CommonPrimitiveType.DOUBLE
			case UmlPrimitiveType.STRING.umlTypeName:
				return CommonPrimitiveType.STRING
			default: {
				throw new IllegalArgumentException("Unsupported UML primitive type: " + primitiveType.name)
			}
		}
	}

	override protected toCommonPrimitiveType(Type domainType) {
		assertTrue(domainType !== null)
		if (domainType instanceof PrimitiveType) {
			return domainType.toCommonPrimitiveType
		}
		return null
	}

	override protected toPrimitiveDomainTypeReference(CommonPrimitiveType commonPrimitiveType) {
		assertTrue(commonPrimitiveType !== null)
		switch (commonPrimitiveType) {
			case BOOLEAN:
				return UmlPrimitiveType.BOOLEAN.umlType
			case INTEGER:
				return UmlPrimitiveType.INTEGER.umlType
			case DOUBLE:
				return UmlPrimitiveType.REAL.umlType
			case STRING:
				return UmlPrimitiveType.STRING.umlType
			default: {
				throw new IllegalArgumentException("Unsupported common primitive type: " + commonPrimitiveType.name)
			}
		}
	}
}

package tools.vitruv.applications.cbs.commonalities.pcm.operators

import org.eclipse.emf.ecore.EObject
import org.palladiosimulator.pcm.core.entity.NamedElement
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import tools.vitruv.applications.cbs.commonalities.domaincommon.CommonPrimitiveType
import tools.vitruv.applications.cbs.commonalities.domaincommon.operators.AbstractTypeReferenceOperator
import tools.vitruv.applications.cbs.commonalities.pcm.PcmPrimitiveDataType
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static tools.vitruv.framework.util.XtendAssertHelper.*

/**
 * Maps between references to PCM types ({@link DataType}s, but also types such
 * as {@link OperationInterface}) and an intermediate representation of these
 * type references.
 * <p>
 * Since there is no common PCM type for the types of objects this operator
 * supports, it operates on plain {@link EObject}s.
 */
@AttributeMappingOperator(
	name='pcmTypeReference',
	commonalityAttributeType=@AttributeType(multiValued=false, type=String),
	participationAttributeType=@AttributeType(multiValued=false, type=EObject)
)
class PcmTypeReferenceOperator extends AbstractTypeReferenceOperator<EObject, EObject> {

	/**
	 * @param executionState the reactions execution state
	 * @param targetConceptDomainName the name of the target concept domain
	 */
	new(ReactionExecutionState executionState, String targetConceptDomainName) {
		super(executionState, 'PCM', targetConceptDomainName, DataType)
	}

	override protected getReferencedDomainType(EObject domainTypeReference) {
		// PCM does not use a separate type to represent type references:
		return domainTypeReference
	}

	override protected getDomainTypeReference(EObject domainType) {
		// PCM does not use a separate type to represent type references:
		return domainType
	}

	override protected asString(EObject domainType) {
		if (domainType instanceof PrimitiveDataType) {
			return domainType.type.name()
		} else if (domainType instanceof NamedElement) {
			return domainType.entityName
		} else {
			return domainType.toString
		}
	}

	private def toCommonPrimitiveType(PrimitiveDataType primitiveDataType) {
		assertTrue(primitiveDataType !== null)
		switch (primitiveDataType.type) {
			case PcmPrimitiveDataType.BOOL.pcmTypeEnum:
				return CommonPrimitiveType.BOOLEAN
			case PcmPrimitiveDataType.INT.pcmTypeEnum:
				return CommonPrimitiveType.INTEGER
			case PcmPrimitiveDataType.DOUBLE.pcmTypeEnum:
				return CommonPrimitiveType.DOUBLE
			case PcmPrimitiveDataType.STRING.pcmTypeEnum:
				return CommonPrimitiveType.STRING
			default: {
				throw new IllegalArgumentException("Unsupported PCM primitive datatype: " + primitiveDataType.type)
			}
		}
	}

	override protected toCommonPrimitiveType(EObject domainType) {
		assertTrue(domainType !== null)
		if (domainType instanceof PrimitiveDataType) {
			return domainType.toCommonPrimitiveType
		}
		return null
	}

	override protected toPrimitiveDomainTypeReference(CommonPrimitiveType commonPrimitiveType) {
		assertTrue(commonPrimitiveType !== null)
		switch (commonPrimitiveType) {
			case BOOLEAN:
				return PcmPrimitiveDataType.BOOL.pcmType
			case INTEGER:
				return PcmPrimitiveDataType.INT.pcmType
			case DOUBLE:
				return PcmPrimitiveDataType.DOUBLE.pcmType
			case STRING:
				return PcmPrimitiveDataType.STRING.pcmType
			default: {
				throw new IllegalArgumentException("Unsupported common primitive type: " + commonPrimitiveType.name)
			}
		}
	}
}

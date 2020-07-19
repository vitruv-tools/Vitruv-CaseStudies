package tools.vitruv.applications.cbs.commonalities.util.pcm.operators.attribute

import java.util.Optional
import org.palladiosimulator.pcm.core.entity.NamedElement
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import tools.vitruv.applications.cbs.commonalities.util.common.CommonPrimitiveType
import tools.vitruv.applications.cbs.commonalities.util.common.operators.attribute.AbstractTypeReferenceOperator
import tools.vitruv.applications.cbs.commonalities.util.pcm.PcmPrimitiveDataType
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static tools.vitruv.framework.util.XtendAssertHelper.*

/**
 * Maps between references to PCM {@link DataType}s and an intermediate
 * representation of these type references.
 */
@AttributeMappingOperator(
	name='pcmTypeReference',
	commonalityAttributeType=@AttributeType(multiValued=false, type=String),
	participationAttributeType=@AttributeType(multiValued=false, type=DataType)
)
class PcmTypeReferenceOperator extends AbstractTypeReferenceOperator<DataType, DataType> {

	/**
	 * @param executionState the reactions execution state
	 * @param targetConceptDomainName the name of the target concept domain
	 */
	new(ReactionExecutionState executionState, String targetConceptDomainName) {
		super(executionState, 'PCM', targetConceptDomainName, DataType)
	}

	override protected getReferencedDomainType(DataType domainTypeReference) {
		// PCM does not use a separate type to represent type references:
		return domainTypeReference
	}

	override protected getDomainTypeReference(DataType domainType) {
		// PCM does not use a separate type to represent type references:
		return domainType
	}

	override protected asString(DataType domainType) {
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

	override protected toCommonPrimitiveType(DataType domainType) {
		assertTrue(domainType !== null)
		if (domainType instanceof PrimitiveDataType) {
			return Optional.of(domainType.toCommonPrimitiveType)
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

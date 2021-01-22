package tools.vitruv.applications.cbs.commonalities.pcm.operators

import org.palladiosimulator.pcm.repository.DataType
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

/**
 * Maps between references to PCM {@link DataType}s and an intermediate
 * representation of these type references.
 * <p>
 * The implementation delegates to {@link PcmTypeReferenceOperator}. Any
 * intermediate type references without corresponding PCM DataType get mapped
 * to <code>null</code>.
 */
@AttributeMappingOperator(
	name='pcmDataTypeReference',
	commonalityAttributeType=@AttributeType(multiValued=false, type=String),
	participationAttributeType=@AttributeType(multiValued=false, type=DataType)
)
class PcmDataTypeReferenceOperator extends AbstractAttributeMappingOperator<String, DataType> {

	// Maps between PCM and intermediate type references:
	val PcmTypeReferenceOperator pcmTypeReferenceOperator

	/**
	 * @param executionState the reactions execution state
	 * @param targetConceptDomainName the name of the target concept domain
	 */
	new(ReactionExecutionState executionState, String targetConceptDomainName) {
		super(executionState)
		this.pcmTypeReferenceOperator = new PcmTypeReferenceOperator(executionState, targetConceptDomainName)
	}

	override applyTowardsCommonality(DataType pcmDataType) {
		val intermediateTypeReference = pcmTypeReferenceOperator.applyTowardsCommonality(pcmDataType)
		return intermediateTypeReference
	}

	override applyTowardsParticipation(String intermediateTypeReference) {
		val pcmType = pcmTypeReferenceOperator.applyTowardsParticipation(intermediateTypeReference)
		if (pcmType instanceof DataType) {
			pcmType
		} else {
			return null
		}
	}
}

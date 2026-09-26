package tools.vitruv.applications.cbs.operators.pcm.operators;

import org.palladiosimulator.pcm.repository.DataType;

import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Maps between references to PCM {@link DataType}s and an intermediate
 * representation of these type references.
 * <p>
 * The implementation delegates to {@link PcmTypeReferenceOperator}. Any
 * intermediate type references without corresponding PCM DataType get mapped
 * to <code>null</code>.
 */
@AttributeMappingOperator(name = "pcmDataTypeReference", commonalityAttributeType = @AttributeType(multiValued = false, type = String.class), participationAttributeType = @AttributeType(multiValued = false, type = DataType.class))
public class PcmDataTypeReferenceOperator extends AbstractAttributeMappingOperator<String, DataType> {

    // Maps between PCM and intermediate type references:
    private final PcmTypeReferenceOperator pcmTypeReferenceOperator;

    /**
     * Creates a new PCM data type reference operator.
     * 
     * @param executionState          the reactions execution state
     * @param targetConceptDomainName the name of the target concept domain
     */
    public PcmDataTypeReferenceOperator(ReactionExecutionState executionState,
            String targetConceptDomainName) {
        super(executionState);
        this.pcmTypeReferenceOperator = new PcmTypeReferenceOperator(executionState,
                targetConceptDomainName);
    }

    @Override
    public String applyTowardsCommonality(DataType pcmDataType) {
        return pcmTypeReferenceOperator.applyTowardsCommonality(pcmDataType);
    }

    @Override
    public DataType applyTowardsParticipation(String intermediateTypeReference) {
        Object pcmType = pcmTypeReferenceOperator.applyTowardsParticipation(intermediateTypeReference);
        return (pcmType instanceof DataType) ? (DataType) pcmType : null;
    }
}
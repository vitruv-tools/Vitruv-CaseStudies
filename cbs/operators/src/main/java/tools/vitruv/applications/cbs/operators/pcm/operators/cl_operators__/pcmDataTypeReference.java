package tools.vitruv.applications.cbs.operators.pcm.operators.cl_operators__;

import org.palladiosimulator.pcm.repository.DataType;
import tools.vitruv.applications.cbs.operators.pcm.operators.PcmDataTypeReferenceOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link PcmDataTypeReferenceOperator} available as the {@code pcmDataTypeReference} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "pcmDataTypeReference",
        commonalityAttributeType = @AttributeType(multiValued = false, type = String.class),
        participationAttributeType = @AttributeType(multiValued = false, type = DataType.class))
public class pcmDataTypeReference extends PcmDataTypeReferenceOperator {
    public pcmDataTypeReference(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState, targetConceptDomainName);
    }
}

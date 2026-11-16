package tools.vitruv.applications.cbs.operators.domaincommon.operators.cl_operators__;

import tools.vitruv.applications.cbs.operators.domaincommon.operators.InverseOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link InverseOperator} available as the {@code inverse} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "inverse",
        commonalityAttributeType = @AttributeType(multiValued = false, type = boolean.class),
        participationAttributeType = @AttributeType(multiValued = false, type = boolean.class))
public class inverse extends InverseOperator {
    public inverse(ReactionExecutionState executionState) {
        super(executionState);
    }
}

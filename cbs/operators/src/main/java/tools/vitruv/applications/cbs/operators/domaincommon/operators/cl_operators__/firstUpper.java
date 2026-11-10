package tools.vitruv.applications.cbs.operators.domaincommon.operators.cl_operators__;

import tools.vitruv.applications.cbs.operators.domaincommon.operators.FirstUpperOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link FirstUpperOperator} available as the {@code firstUpper} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "firstUpper",
        commonalityAttributeType = @AttributeType(multiValued = false, type = String.class),
        participationAttributeType = @AttributeType(multiValued = false, type = String.class))
public class firstUpper extends FirstUpperOperator {
    public firstUpper(ReactionExecutionState executionState) {
        super(executionState);
    }
}

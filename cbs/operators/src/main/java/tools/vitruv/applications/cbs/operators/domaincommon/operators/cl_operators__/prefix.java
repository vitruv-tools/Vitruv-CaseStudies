package tools.vitruv.applications.cbs.operators.domaincommon.operators.cl_operators__;

import tools.vitruv.applications.cbs.operators.domaincommon.operators.PrefixOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link PrefixOperator} available as the {@code prefix} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "prefix",
        commonalityAttributeType = @AttributeType(multiValued = false, type = String.class),
        participationAttributeType = @AttributeType(multiValued = false, type = String.class))
public class prefix extends PrefixOperator {
    public prefix(ReactionExecutionState executionState, String suffix) {
        super(executionState, suffix);
    }
}

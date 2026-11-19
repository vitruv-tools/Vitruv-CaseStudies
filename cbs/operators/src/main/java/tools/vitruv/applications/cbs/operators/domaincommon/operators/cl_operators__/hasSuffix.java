package tools.vitruv.applications.cbs.operators.domaincommon.operators.cl_operators__;

import java.util.List;
import tools.vitruv.applications.cbs.operators.domaincommon.operators.SuffixConditionOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.ParticipationConditionOperator;

/**
 * Makes {@link SuffixConditionOperator} available as the {@code hasSuffix} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@ParticipationConditionOperator(name = "hasSuffix")
public class hasSuffix extends SuffixConditionOperator {
    public hasSuffix(Object leftOperand, List<?> rightOperands) {
        super(leftOperand, rightOperands);
    }
}

package tools.vitruv.applications.cbs.operators.java.operators.cl_operators__;

import java.util.List;
import tools.vitruv.applications.cbs.operators.java.operators.JavaVisibilityConditionOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.ParticipationConditionOperator;

/**
 * Makes {@link JavaVisibilityConditionOperator} available as the {@code hasJavaVisibility} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@ParticipationConditionOperator(name = "hasJavaVisibility")
public class hasJavaVisibility extends JavaVisibilityConditionOperator {
    public hasJavaVisibility(Object leftOperand, List<?> rightOperands) {
        super(leftOperand, rightOperands);
    }
}

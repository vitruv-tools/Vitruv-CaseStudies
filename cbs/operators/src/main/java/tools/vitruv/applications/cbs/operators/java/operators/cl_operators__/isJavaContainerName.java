package tools.vitruv.applications.cbs.operators.java.operators.cl_operators__;

import java.util.List;
import tools.vitruv.applications.cbs.operators.java.operators.JavaContainerNameConditionOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.ParticipationConditionOperator;

/**
 * Makes {@link JavaContainerNameConditionOperator} available as the {@code isJavaContainerName} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@ParticipationConditionOperator(name = "isJavaContainerName")
public class isJavaContainerName extends JavaContainerNameConditionOperator {
    public isJavaContainerName(Object leftOperand, List<?> rightOperands) {
        super(leftOperand, rightOperands);
    }
}

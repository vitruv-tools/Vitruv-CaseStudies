package tools.vitruv.applications.cbs.operators.uml.operators.cl_operators__;

import java.util.List;
import tools.vitruv.applications.cbs.operators.uml.operators.UmlContainerNameConditionOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.ParticipationConditionOperator;

/**
 * Makes {@link UmlContainerNameConditionOperator} available as the {@code isUmlContainerName} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@ParticipationConditionOperator(name = "isUmlContainerName")
public class isUmlContainerName extends UmlContainerNameConditionOperator {
    public isUmlContainerName(Object leftOperand, List<?> rightOperands) {
        super(leftOperand, rightOperands);
    }
}

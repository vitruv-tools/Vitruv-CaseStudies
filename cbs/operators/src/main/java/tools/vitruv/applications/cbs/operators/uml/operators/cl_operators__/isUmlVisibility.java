package tools.vitruv.applications.cbs.operators.uml.operators.cl_operators__;

import java.util.List;
import tools.vitruv.applications.cbs.operators.uml.operators.UmlVisibilityConditionOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.ParticipationConditionOperator;

/**
 * Makes {@link UmlVisibilityConditionOperator} available as the {@code isUmlVisibility} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@ParticipationConditionOperator(name = "isUmlVisibility")
public class isUmlVisibility extends UmlVisibilityConditionOperator {
    public isUmlVisibility(Object leftOperand, List<?> rightOperands) {
        super(leftOperand, rightOperands);
    }
}

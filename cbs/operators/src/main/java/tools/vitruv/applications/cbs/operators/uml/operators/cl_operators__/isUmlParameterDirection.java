package tools.vitruv.applications.cbs.operators.uml.operators.cl_operators__;

import java.util.List;
import tools.vitruv.applications.cbs.operators.uml.operators.UmlParameterDirectionConditionOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.ParticipationConditionOperator;

/**
 * Makes {@link UmlParameterDirectionConditionOperator} available as the {@code isUmlParameterDirection} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@ParticipationConditionOperator(name = "isUmlParameterDirection")
public class isUmlParameterDirection extends UmlParameterDirectionConditionOperator {
    public isUmlParameterDirection(Object leftOperand, List<?> rightOperands) {
        super(leftOperand, rightOperands);
    }
}

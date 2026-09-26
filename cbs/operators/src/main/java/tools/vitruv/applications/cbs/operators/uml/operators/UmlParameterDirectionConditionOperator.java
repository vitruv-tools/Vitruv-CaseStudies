package tools.vitruv.applications.cbs.operators.uml.operators;

import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.ParameterDirectionKind;

import static com.google.common.base.Preconditions.checkArgument;

import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.AbstractSingleArgumentConditionOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.ParticipationConditionOperator;

@ParticipationConditionOperator(name = "isUmlParameterDirection")
public class UmlParameterDirectionConditionOperator extends AbstractSingleArgumentConditionOperator {

    private static final Logger logger = Logger.getLogger(UmlParameterDirectionConditionOperator.class);

    public UmlParameterDirectionConditionOperator(Object leftOperand, List<?> rightOperands) {
        super(leftOperand, rightOperands);
        checkArgument(getRightOperand() instanceof String, "Expecting String as right operand!");
        checkArgument(getRightParameterDirection() != null, "Invalid ParameterDirectionKind: " + getRightOperand());
    }

    // Returns null if no matching ParameterDirectionKind is found.
    private ParameterDirectionKind getRightParameterDirection() {
        return ParameterDirectionKind.getByName((String) getRightOperand());
    }

    @Override
    public void enforce() {
        ((EObject) leftOperandObject).eSet(leftOperandFeature, getRightParameterDirection());
    }

    @Override
    public boolean check() {
        Object leftParameterDirection = ((EObject) leftOperandObject).eGet(leftOperandFeature);
        boolean result = Objects.equals(leftParameterDirection, getRightParameterDirection());
        if (!result) {
            logger.debug(String.format(
                    "Condition check failed. leftObject='%s', leftFeature='%s', leftParameterDirection='%s', rightParameterDirection='%s'.",
                    leftOperandObject, leftOperandFeature, leftParameterDirection, getRightParameterDirection()));
        }
        return result;
    }
}
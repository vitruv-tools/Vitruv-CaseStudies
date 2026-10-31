package tools.vitruv.applications.cbs.operators.uml.operators;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;

import static com.google.common.base.Preconditions.checkArgument;

import tools.vitruv.applications.cbs.operators.oo.Visibility;
import tools.vitruv.applications.cbs.operators.uml.UmlVisibilityHelper;
import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.AbstractSingleArgumentConditionOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.ParticipationConditionOperator;

@ParticipationConditionOperator(name = "isUmlVisibility")
public class UmlVisibilityConditionOperator extends AbstractSingleArgumentConditionOperator {

    private static final Logger logger = Logger.getLogger(UmlVisibilityConditionOperator.class);

    public UmlVisibilityConditionOperator(Object leftOperand, List<?> rightOperands) {
        super(leftOperand, rightOperands);
        checkArgument(isVisibilityAttribute(leftOperandFeature), String.format(
                "Invalid visibility attribute '%s'.", leftOperandFeature.getName()));
        checkArgument(getRightOperand() instanceof String, "Expecting String as right operand!");
        checkArgument(getRightVisibility() != null, "Invalid visibility: " + getRightOperand());
    }

    private static boolean isVisibilityAttribute(EStructuralFeature feature) {
        if (feature instanceof EAttribute attr) {
            return UMLPackage.Literals.VISIBILITY_KIND == attr.getEAttributeType();
        }
        return false;
    }

    private Visibility getRightVisibility() {
        return Visibility.byName((String) getRightOperand());
    }

    @Override
    public void enforce() {
        Visibility vis = getRightVisibility();
        ((EObject) leftOperandObject).eSet(leftOperandFeature, UmlVisibilityHelper.toUmlVisibility(vis));
    }

    @Override
    public boolean check() {
        Object raw = ((EObject) leftOperandObject).eGet(leftOperandFeature);
        VisibilityKind umlVisibility = (raw instanceof VisibilityKind) ? (VisibilityKind) raw : null;
        Visibility leftVisibility = UmlVisibilityHelper.toVisibility(umlVisibility);
        boolean result = (leftVisibility == getRightVisibility());
        if (!result) {
            logger.debug(String.format(
                    "Condition check failed. leftObject='%s', leftFeature='%s', leftVisibility='%s', rightVisibility='%s'.",
                    leftOperandObject, leftOperandFeature, leftVisibility, getRightVisibility()));
        }
        return result;
    }
}
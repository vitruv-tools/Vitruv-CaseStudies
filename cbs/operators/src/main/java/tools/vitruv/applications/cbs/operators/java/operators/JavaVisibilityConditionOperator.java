package tools.vitruv.applications.cbs.operators.java.operators;

import java.util.List;

import org.apache.log4j.Logger;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import org.emftext.language.java.modifiers.ModifiersPackage;

import static com.google.common.base.Preconditions.checkArgument;

import static tools.vitruv.applications.cbs.operators.java.JavaVisibilityHelper.getVisibility;
import static tools.vitruv.applications.cbs.operators.java.JavaVisibilityHelper.setVisibility;
import tools.vitruv.applications.cbs.operators.oo.Visibility;
import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.AbstractSingleArgumentConditionOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.ParticipationConditionOperator;

/**
 * Converted from Xtend.
 */
@ParticipationConditionOperator(name = "hasJavaVisibility")
public class JavaVisibilityConditionOperator extends AbstractSingleArgumentConditionOperator {

    private static final Logger logger = Logger.getLogger(JavaVisibilityConditionOperator.class);

    public JavaVisibilityConditionOperator(Object leftOperand, List<?> rightOperands) {
        super(leftOperand, rightOperands);
        checkArgument(leftOperandObject instanceof AnnotableAndModifiable,
                "Expecting an AnnotableAndModifiable as left operand object!");
        checkArgument(
                leftOperandFeature == ModifiersPackage.Literals.ANNOTABLE_AND_MODIFIABLE__ANNOTATIONS_AND_MODIFIERS,
                "Expecting 'annotationsAndModifiers' as left operand feature!");
        checkArgument(getRightOperand() instanceof String, "Expecting String as right operand!");
        checkArgument(getRightVisibility() != null, "Invalid visibility: " + getRightOperand());
    }

    private AnnotableAndModifiable getLeftAnnotableAndModifiable() {
        return (AnnotableAndModifiable) leftOperandObject;
    }

    private Visibility getRightVisibility() {
        return Visibility.byName((String) getRightOperand());
    }

    @Override
    public void enforce() {
        List<AnnotationInstanceOrModifier> modifiers = getLeftAnnotableAndModifiable().getAnnotationsAndModifiers();
        List<AnnotationInstanceOrModifier> newModifiers = setVisibility(modifiers, getRightVisibility());
        if (modifiers != newModifiers) {
            modifiers.clear();
            modifiers.addAll(newModifiers);
        }
    }

    @Override
    public boolean check() {
        List<AnnotationInstanceOrModifier> modifiers = getLeftAnnotableAndModifiable().getAnnotationsAndModifiers();
        Visibility leftVisibility = getVisibility(modifiers);
        boolean result = (leftVisibility == getRightVisibility());
        if (!result) {
            logger.debug(String.format(
                    "Condition check failed. leftObject='%s', leftFeature='%s', leftVisibility='%s', rightVisibility='%s'.",
                    leftOperandObject, leftOperandFeature, leftVisibility, getRightVisibility()));
        }
        return result;
    }
}
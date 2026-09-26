package tools.vitruv.applications.cbs.operators.java.operators;

import java.util.List;

import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;

import static tools.vitruv.dsls.commonalities.runtime.helper.XtendAssertHelper.assertTrue;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Similar to {@link JavaAbstractModifierOperator}, but inverts the value of
 * the boolean modifier flag.
 */
@AttributeMappingOperator(name = "javaInverseAbstractModifier", commonalityAttributeType = @AttributeType(multiValued = false, type = Boolean.class), participationAttributeType = @AttributeType(multiValued = true, type = AnnotationInstanceOrModifier.class))
public class JavaInverseAbstractModifierOperator extends JavaAbstractModifierOperator {

    /**
     * Creates a new JavaInverseAbstractModifierOperator.
     * 
     * @param executionState the reactions execution state
     * @param javaModifiable the modifiable Java element
     */
    public JavaInverseAbstractModifierOperator(ReactionExecutionState executionState,
            AnnotableAndModifiable javaModifiable) {
        super(executionState, javaModifiable);
    }

    @Override
    public Boolean applyTowardsCommonality(List<AnnotationInstanceOrModifier> modifiers) {
        Boolean modifierPresent = super.applyTowardsCommonality(modifiers);
        assertTrue(modifierPresent != null);
        return !modifierPresent;
    }

    @Override
    public List<AnnotationInstanceOrModifier> applyTowardsParticipation(Boolean modifierAbsent) {
        assertTrue(modifierAbsent != null);
        return super.applyTowardsParticipation(!modifierAbsent);
    }
}
package tools.vitruv.applications.cbs.operators.java.operators;

import java.util.List;

import org.apache.log4j.Logger;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;

import static com.google.common.base.Preconditions.checkNotNull;

import static tools.vitruv.applications.cbs.operators.java.JavaVisibilityHelper.getVisibility;
import static tools.vitruv.applications.cbs.operators.java.JavaVisibilityHelper.setVisibility;
import tools.vitruv.applications.cbs.operators.oo.Visibility;
import static tools.vitruv.applications.util.temporary.java.JavaModifierUtil.getModifierNames;
import static tools.vitruv.dsls.commonalities.runtime.helper.XtendAssertHelper.assertTrue;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

@AttributeMappingOperator(name = "javaVisibility", commonalityAttributeType = @AttributeType(multiValued = false, type = String.class), participationAttributeType = @AttributeType(multiValued = true, type = AnnotationInstanceOrModifier.class))
public class JavaVisibilityOperator
        extends AbstractAttributeMappingOperator<String, List<AnnotationInstanceOrModifier>> {

    private static final Logger logger = Logger.getLogger(JavaVisibilityOperator.class);

    private final AnnotableAndModifiable javaModifiable;

    public JavaVisibilityOperator(ReactionExecutionState executionState, AnnotableAndModifiable javaModifiable) {
        super(executionState);
        checkNotNull(javaModifiable, "javaModifiable is null");
        this.javaModifiable = javaModifiable;
    }

    @Override
    public String applyTowardsCommonality(List<AnnotationInstanceOrModifier> modifiers) {
        Visibility visibility = getVisibility(modifiers);
        assertTrue(visibility != null);
        String visibilityName = visibility.name();
        logger.debug(String.format("Mapping Java modifiers %s to visibility %s.", getModifierNames(modifiers),
                visibilityName));
        return visibilityName;
    }

    @Override
    public List<AnnotationInstanceOrModifier> applyTowardsParticipation(String visibilityName) {
        Visibility visibility = Visibility.byName(visibilityName); // can be null
        List<AnnotationInstanceOrModifier> currentModifiers = javaModifiable.getAnnotationsAndModifiers();
        List<AnnotationInstanceOrModifier> newModifiers = setVisibility(currentModifiers, visibility);
        logger.debug(String.format("Mapping visibility %s and current Java modifiers %s to new modifiers %s.",
                visibility, getModifierNames(currentModifiers), getModifierNames(newModifiers)));
        return newModifiers;
    }
}
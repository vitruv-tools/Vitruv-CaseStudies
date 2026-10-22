package tools.vitruv.applications.cbs.operators.java.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.log4j.Logger;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import org.emftext.language.java.modifiers.Modifier;

import static com.google.common.base.Preconditions.checkNotNull;

import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Base class for Java modifier operators.
 */
public abstract class AbstractJavaModifierOperator
        extends AbstractAttributeMappingOperator<Boolean, List<AnnotationInstanceOrModifier>> {

    private static final Logger logger = Logger.getLogger(AbstractJavaModifierOperator.class);
    private final AnnotableAndModifiable javaModifiable;

    /**
     * Creates a new modifier operator.
     * 
     * @param executionState the reactions execution state
     * @param javaModifiable the modifiable Java element
     * @throws IllegalArgumentException if javaModifiable is null
     */
    protected AbstractJavaModifierOperator(ReactionExecutionState executionState,
            AnnotableAndModifiable javaModifiable) {
        super(executionState);
        checkNotNull(javaModifiable, "javaModifiable is null");
        this.javaModifiable = javaModifiable;
    }

    /**
     * Gets the name of this modifier.
     */
    protected abstract String getModifierName();

    /**
     * Checks if the given modifier is relevant for this operator.
     */
    protected abstract boolean isRelevantModifier(Modifier modifier);

    /**
     * Creates a new instance of this modifier.
     */
    protected abstract Modifier createModifier();

    private boolean isRelevantModifier(AnnotationInstanceOrModifier modifier) {
        return modifier instanceof Modifier &&
                isRelevantModifier((Modifier) modifier);
    }

    private boolean containsRelevantModifier(Iterable<? extends AnnotationInstanceOrModifier> modifiers) {
        return StreamSupport.stream(modifiers.spliterator(), false)
                .anyMatch(this::isRelevantModifier);
    }

    private static List<String> getModifierNames(Iterable<? extends AnnotationInstanceOrModifier> modifiers) {
        return StreamSupport.stream(modifiers.spliterator(), false)
                .map(m -> m.getClass().getName())
                .collect(Collectors.toList());
    }

    @Override
    public Boolean applyTowardsCommonality(List<AnnotationInstanceOrModifier> modifiers) {
        boolean modifierPresent = containsRelevantModifier(modifiers);

        logger.debug(String.format(
                "Mapping Java modifiers %s to boolean flag %s for modifier '%s'",
                getModifierNames(modifiers),
                modifierPresent,
                getModifierName()));

        return modifierPresent;
    }

    @Override
    public List<AnnotationInstanceOrModifier> applyTowardsParticipation(Boolean modifierPresent) {
        List<AnnotationInstanceOrModifier> currentModifiers = javaModifiable.getAnnotationsAndModifiers();
        List<AnnotationInstanceOrModifier> newModifiers = new ArrayList<>(currentModifiers);

        if (modifierPresent) {
            if (!containsRelevantModifier(currentModifiers)) {
                newModifiers.add(createModifier());
            } // Else: Already contains the relevant modifier -> No changes required.
        } else {
            newModifiers.removeIf(this::isRelevantModifier);
        }

        logger.debug(String.format(
                "Mapping boolean flag %s for modifier '%s' and current Java modifiers %s to new modifiers %s",
                modifierPresent,
                getModifierName(),
                getModifierNames(currentModifiers),
                getModifierNames(newModifiers)));

        return newModifiers;
    }
}
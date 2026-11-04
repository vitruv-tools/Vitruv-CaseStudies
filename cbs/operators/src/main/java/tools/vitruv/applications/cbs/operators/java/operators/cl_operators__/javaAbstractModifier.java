package tools.vitruv.applications.cbs.operators.java.operators.cl_operators__;

import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import tools.vitruv.applications.cbs.operators.java.operators.JavaAbstractModifierOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link JavaAbstractModifierOperator} available as the {@code javaAbstractModifier} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "javaAbstractModifier",
        commonalityAttributeType = @AttributeType(multiValued = false, type = boolean.class),
        participationAttributeType = @AttributeType(multiValued = true, type = AnnotationInstanceOrModifier.class))
public class javaAbstractModifier extends JavaAbstractModifierOperator {
    public javaAbstractModifier(ReactionExecutionState executionState, AnnotableAndModifiable javaModifiable) {
        super(executionState, javaModifiable);
    }
}

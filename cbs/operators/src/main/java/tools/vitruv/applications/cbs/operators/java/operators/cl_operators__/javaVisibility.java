package tools.vitruv.applications.cbs.operators.java.operators.cl_operators__;

import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import tools.vitruv.applications.cbs.operators.java.operators.JavaVisibilityOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link JavaVisibilityOperator} available as the {@code javaVisibility} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "javaVisibility",
        commonalityAttributeType = @AttributeType(multiValued = false, type = String.class),
        participationAttributeType = @AttributeType(multiValued = true, type = AnnotationInstanceOrModifier.class))
public class javaVisibility extends JavaVisibilityOperator {
    public javaVisibility(ReactionExecutionState executionState, AnnotableAndModifiable javaModifiable) {
        super(executionState, javaModifiable);
    }
}

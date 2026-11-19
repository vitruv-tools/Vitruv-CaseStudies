package tools.vitruv.applications.cbs.operators.java.operators.cl_operators__;

import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import tools.vitruv.applications.cbs.operators.java.operators.JavaStaticModifierOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link JavaStaticModifierOperator} available as the {@code javaStaticModifier} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "javaStaticModifier",
        commonalityAttributeType = @AttributeType(multiValued = false, type = boolean.class),
        participationAttributeType = @AttributeType(multiValued = true, type = AnnotationInstanceOrModifier.class))
public class javaStaticModifier extends JavaStaticModifierOperator {
    public javaStaticModifier(ReactionExecutionState executionState, AnnotableAndModifiable javaModifiable) {
        super(executionState, javaModifiable);
    }
}

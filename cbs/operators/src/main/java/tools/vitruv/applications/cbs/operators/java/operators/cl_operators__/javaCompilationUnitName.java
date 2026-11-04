package tools.vitruv.applications.cbs.operators.java.operators.cl_operators__;

import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.applications.cbs.operators.java.operators.JavaCompilationUnitNameOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link JavaCompilationUnitNameOperator} available as the {@code javaCompilationUnitName} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "javaCompilationUnitName",
        commonalityAttributeType = @AttributeType(multiValued = false, type = String.class),
        participationAttributeType = @AttributeType(multiValued = false, type = String.class))
public class javaCompilationUnitName extends JavaCompilationUnitNameOperator {
    public javaCompilationUnitName(ReactionExecutionState executionState, CompilationUnit javaCompilationUnit) {
        super(executionState, javaCompilationUnit);
    }
}

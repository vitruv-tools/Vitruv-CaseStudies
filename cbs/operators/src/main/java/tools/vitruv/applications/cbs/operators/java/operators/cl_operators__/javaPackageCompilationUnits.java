package tools.vitruv.applications.cbs.operators.java.operators.cl_operators__;

import tools.vitruv.applications.cbs.operators.java.operators.JavaPackageCompilationUnitsOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.reference.ReferenceMappingOperator;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link JavaPackageCompilationUnitsOperator} available as the {@code javaPackageCompilationUnits} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@ReferenceMappingOperator(
        name = "javaPackageCompilationUnits",
        isMultiValued = true,
        isAttributeReference = true)
public class javaPackageCompilationUnits extends JavaPackageCompilationUnitsOperator {
    public javaPackageCompilationUnits(ReactionExecutionState executionState) {
        super(executionState);
    }
}

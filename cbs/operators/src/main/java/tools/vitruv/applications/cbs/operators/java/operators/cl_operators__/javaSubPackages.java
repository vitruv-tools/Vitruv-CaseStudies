package tools.vitruv.applications.cbs.operators.java.operators.cl_operators__;

import tools.vitruv.applications.cbs.operators.java.operators.JavaSubPackagesOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.reference.ReferenceMappingOperator;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link JavaSubPackagesOperator} available as the {@code javaSubPackages} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@ReferenceMappingOperator(
        name = "javaSubPackages",
        isMultiValued = true,
        isAttributeReference = true)
public class javaSubPackages extends JavaSubPackagesOperator {
    public javaSubPackages(ReactionExecutionState executionState) {
        super(executionState);
    }
}

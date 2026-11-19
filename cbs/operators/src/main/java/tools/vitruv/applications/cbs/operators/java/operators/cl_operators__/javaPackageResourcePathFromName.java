package tools.vitruv.applications.cbs.operators.java.operators.cl_operators__;

import org.emftext.language.java.containers.Package;
import tools.vitruv.applications.cbs.operators.java.operators.JavaPackageResourcePathFromNameOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link JavaPackageResourcePathFromNameOperator} available as the {@code javaPackageResourcePathFromName} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "javaPackageResourcePathFromName",
        commonalityAttributeType = @AttributeType(multiValued = false, type = String.class),
        participationAttributeType = @AttributeType(multiValued = false, type = String.class))
public class javaPackageResourcePathFromName extends JavaPackageResourcePathFromNameOperator {
    public javaPackageResourcePathFromName(ReactionExecutionState executionState, String sourcePath, Package javaPackage) {
        super(executionState, sourcePath, javaPackage);
    }
}

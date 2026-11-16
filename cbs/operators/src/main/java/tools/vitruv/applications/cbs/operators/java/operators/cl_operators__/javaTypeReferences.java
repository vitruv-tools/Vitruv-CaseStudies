package tools.vitruv.applications.cbs.operators.java.operators.cl_operators__;

import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.cbs.operators.java.operators.JavaTypeReferencesOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link JavaTypeReferencesOperator} available as the {@code javaTypeReferences} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "javaTypeReferences",
        commonalityAttributeType = @AttributeType(multiValued = true, type = String.class),
        participationAttributeType = @AttributeType(multiValued = true, type = TypeReference.class))
public class javaTypeReferences extends JavaTypeReferencesOperator {
    public javaTypeReferences(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState, targetConceptDomainName);
    }
}

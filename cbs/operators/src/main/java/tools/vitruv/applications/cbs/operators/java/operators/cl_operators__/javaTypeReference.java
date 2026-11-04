package tools.vitruv.applications.cbs.operators.java.operators.cl_operators__;

import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.cbs.operators.java.operators.JavaTypeReferenceOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link JavaTypeReferenceOperator} available as the {@code javaTypeReference} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "javaTypeReference",
        commonalityAttributeType = @AttributeType(multiValued = false, type = String.class),
        participationAttributeType = @AttributeType(multiValued = false, type = TypeReference.class))
public class javaTypeReference extends JavaTypeReferenceOperator {
    public javaTypeReference(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState, targetConceptDomainName);
    }
}

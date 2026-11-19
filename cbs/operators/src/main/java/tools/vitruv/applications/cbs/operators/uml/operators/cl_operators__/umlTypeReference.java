package tools.vitruv.applications.cbs.operators.uml.operators.cl_operators__;

import org.eclipse.uml2.uml.Type;
import tools.vitruv.applications.cbs.operators.uml.operators.UmlTypeReferenceOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link UmlTypeReferenceOperator} available as the {@code umlTypeReference} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "umlTypeReference",
        commonalityAttributeType = @AttributeType(multiValued = false, type = String.class),
        participationAttributeType = @AttributeType(multiValued = false, type = Type.class))
public class umlTypeReference extends UmlTypeReferenceOperator {
    public umlTypeReference(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState, targetConceptDomainName);
    }
}

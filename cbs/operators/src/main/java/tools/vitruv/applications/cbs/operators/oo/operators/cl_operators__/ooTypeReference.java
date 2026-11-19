package tools.vitruv.applications.cbs.operators.oo.operators.cl_operators__;

import tools.vitruv.applications.cbs.operators.oo.operators.OOTypeReferenceOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link OOTypeReferenceOperator} available as the {@code ooTypeReference} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "ooTypeReference",
        commonalityAttributeType = @AttributeType(multiValued = false, type = String.class),
        participationAttributeType = @AttributeType(multiValued = false, type = String.class))
public class ooTypeReference extends OOTypeReferenceOperator {
    public ooTypeReference(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState, targetConceptDomainName);
    }
}

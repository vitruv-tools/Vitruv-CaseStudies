package tools.vitruv.applications.cbs.operators.oo.operators.cl_operators__;

import tools.vitruv.applications.cbs.operators.oo.operators.OOTypeReferencesOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link OOTypeReferencesOperator} available as the {@code ooTypeReferences} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "ooTypeReferences",
        commonalityAttributeType = @AttributeType(multiValued = true, type = String.class),
        participationAttributeType = @AttributeType(multiValued = true, type = String.class))
public class ooTypeReferences extends OOTypeReferencesOperator {
    public ooTypeReferences(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState, targetConceptDomainName);
    }
}

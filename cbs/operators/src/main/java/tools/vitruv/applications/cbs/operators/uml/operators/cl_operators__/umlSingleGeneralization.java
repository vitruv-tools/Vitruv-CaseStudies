package tools.vitruv.applications.cbs.operators.uml.operators.cl_operators__;

import org.eclipse.uml2.uml.Generalization;
import tools.vitruv.applications.cbs.operators.uml.operators.UmlSingleGeneralizationOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link UmlSingleGeneralizationOperator} available as the {@code umlSingleGeneralization} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "umlSingleGeneralization",
        commonalityAttributeType = @AttributeType(multiValued = false, type = String.class),
        participationAttributeType = @AttributeType(multiValued = true, type = Generalization.class))
public class umlSingleGeneralization extends UmlSingleGeneralizationOperator {
    public umlSingleGeneralization(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState, targetConceptDomainName);
    }
}

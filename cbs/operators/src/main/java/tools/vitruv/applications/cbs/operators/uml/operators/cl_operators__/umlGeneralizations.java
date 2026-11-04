package tools.vitruv.applications.cbs.operators.uml.operators.cl_operators__;

import org.eclipse.uml2.uml.Generalization;
import tools.vitruv.applications.cbs.operators.uml.operators.UmlGeneralizationsOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link UmlGeneralizationsOperator} available as the {@code umlGeneralizations} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "umlGeneralizations",
        commonalityAttributeType = @AttributeType(multiValued = true, type = String.class),
        participationAttributeType = @AttributeType(multiValued = true, type = Generalization.class))
public class umlGeneralizations extends UmlGeneralizationsOperator {
    public umlGeneralizations(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState, targetConceptDomainName);
    }
}

package tools.vitruv.applications.cbs.operators.uml.operators.cl_operators__;

import org.eclipse.uml2.uml.VisibilityKind;
import tools.vitruv.applications.cbs.operators.uml.operators.UmlVisibilityOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link UmlVisibilityOperator} available as the {@code umlVisibility} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "umlVisibility",
        commonalityAttributeType = @AttributeType(multiValued = false, type = String.class),
        participationAttributeType = @AttributeType(multiValued = false, type = VisibilityKind.class))
public class umlVisibility extends UmlVisibilityOperator {
    public umlVisibility(ReactionExecutionState executionState) {
        super(executionState);
    }
}

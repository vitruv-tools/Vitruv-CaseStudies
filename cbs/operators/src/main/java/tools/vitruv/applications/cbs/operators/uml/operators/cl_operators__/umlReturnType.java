package tools.vitruv.applications.cbs.operators.uml.operators.cl_operators__;

import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import tools.vitruv.applications.cbs.operators.uml.operators.UmlReturnTypeOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link UmlReturnTypeOperator} available as the {@code umlReturnType} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "umlReturnType",
        commonalityAttributeType = @AttributeType(multiValued = false, type = String.class),
        participationAttributeType = @AttributeType(multiValued = true, type = Parameter.class))
public class umlReturnType extends UmlReturnTypeOperator {
    public umlReturnType(ReactionExecutionState executionState, String targetConceptDomainName, Operation operation) {
        super(executionState, targetConceptDomainName, operation);
    }
}

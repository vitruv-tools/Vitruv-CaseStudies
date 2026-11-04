package tools.vitruv.applications.cbs.operators.uml.operators.cl_operators__;

import org.eclipse.uml2.uml.InterfaceRealization;
import tools.vitruv.applications.cbs.operators.uml.operators.UmlInterfaceRealizationsOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link UmlInterfaceRealizationsOperator} available as the {@code umlInterfaceRealizations} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "umlInterfaceRealizations",
        commonalityAttributeType = @AttributeType(multiValued = true, type = String.class),
        participationAttributeType = @AttributeType(multiValued = true, type = InterfaceRealization.class))
public class umlInterfaceRealizations extends UmlInterfaceRealizationsOperator {
    public umlInterfaceRealizations(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState, targetConceptDomainName);
    }
}

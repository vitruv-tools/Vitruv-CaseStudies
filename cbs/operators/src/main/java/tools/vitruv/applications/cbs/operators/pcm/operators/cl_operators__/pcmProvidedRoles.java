package tools.vitruv.applications.cbs.operators.pcm.operators.cl_operators__;

import org.palladiosimulator.pcm.repository.ProvidedRole;
import tools.vitruv.applications.cbs.operators.pcm.operators.PcmProvidedRolesOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link PcmProvidedRolesOperator} available as the {@code pcmProvidedRoles} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "pcmProvidedRoles",
        commonalityAttributeType = @AttributeType(multiValued = true, type = String.class),
        participationAttributeType = @AttributeType(multiValued = true, type = ProvidedRole.class))
public class pcmProvidedRoles extends PcmProvidedRolesOperator {
    public pcmProvidedRoles(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState, targetConceptDomainName);
    }
}

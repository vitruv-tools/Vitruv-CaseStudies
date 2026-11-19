package tools.vitruv.applications.cbs.operators.pcm.operators.cl_operators__;

import org.eclipse.emf.ecore.EObject;
import tools.vitruv.applications.cbs.operators.pcm.operators.PcmTypeReferenceOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Makes {@link PcmTypeReferenceOperator} available as the {@code pcmTypeReference} operator in commonalities.
 *
 * @see tools.vitruv.applications.cbs.operators
 */
@AttributeMappingOperator(
        name = "pcmTypeReference",
        commonalityAttributeType = @AttributeType(multiValued = false, type = String.class),
        participationAttributeType = @AttributeType(multiValued = false, type = EObject.class))
public class pcmTypeReference extends PcmTypeReferenceOperator {
    public pcmTypeReference(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState, targetConceptDomainName);
    }
}

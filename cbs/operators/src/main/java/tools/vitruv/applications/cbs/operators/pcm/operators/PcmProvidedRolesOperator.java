package tools.vitruv.applications.cbs.operators.pcm.operators;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Maps between PCM {@link OperationProvidedRole}s and their interfaces and
 * corresponding intermediate type references.
 * <p>
 * This reuses the implementation of {@link PcmTypeReferenceOperator} to map
 * between PCM and intermediate type references.
 */
@AttributeMappingOperator(name = "pcmProvidedRoles", commonalityAttributeType = @AttributeType(multiValued = true, type = String.class), participationAttributeType = @AttributeType(multiValued = true, type = ProvidedRole.class))
public class PcmProvidedRolesOperator
        extends AbstractAttributeMappingOperator<List<String>, List<ProvidedRole>> {

    private static final Logger logger = Logger.getLogger(PcmProvidedRolesOperator.class);

    // Maps between PCM and intermediate type references:
    private final PcmTypeReferenceOperator pcmTypeReferenceOperator;

    /**
     * Creates a new PCM provided roles operator.
     * 
     * @param executionState          the reactions execution state
     * @param targetConceptDomainName the name of the target concept domain
     */
    public PcmProvidedRolesOperator(ReactionExecutionState executionState,
            String targetConceptDomainName) {
        super(executionState);
        this.pcmTypeReferenceOperator = new PcmTypeReferenceOperator(executionState,
                targetConceptDomainName);
    }

    @Override
    public List<String> applyTowardsCommonality(List<ProvidedRole> pcmProvidedRoles) {
        List<String> intermediateTypeReferences = pcmProvidedRoles.stream()
                .filter(OperationProvidedRole.class::isInstance)
                .map(OperationProvidedRole.class::cast)
                .map(providedRole -> {
                    OperationInterface pcmInterface = providedRole.getProvidedInterface__OperationProvidedRole();
                    return pcmTypeReferenceOperator.applyTowardsCommonality(pcmInterface);
                })
                .filter(ref -> ref != null)
                .collect(Collectors.toList());

        logger.debug(String.format("Mapping PCM provided roles %s to intermediate type references %s",
                pcmProvidedRoles, intermediateTypeReferences));

        return intermediateTypeReferences;
    }

    @Override
    public List<ProvidedRole> applyTowardsParticipation(List<String> intermediateTypeReferences) {
        List<ProvidedRole> pcmProvidedRoles = intermediateTypeReferences.stream()
                .map(ref -> {
                    Object pcmInterface = pcmTypeReferenceOperator.applyTowardsParticipation(ref);
                    if (!(pcmInterface instanceof OperationInterface)) {
                        return null;
                    }

                    OperationProvidedRole role = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
                    role.setProvidedInterface__OperationProvidedRole((OperationInterface) pcmInterface);
                    return role;
                })
                .filter(role -> role != null)
                .collect(Collectors.toList());

        logger.debug(String.format(
                "Mapping intermediate type references %s to PCM provided roles %s",
                intermediateTypeReferences, pcmProvidedRoles));

        return pcmProvidedRoles;
    }
}
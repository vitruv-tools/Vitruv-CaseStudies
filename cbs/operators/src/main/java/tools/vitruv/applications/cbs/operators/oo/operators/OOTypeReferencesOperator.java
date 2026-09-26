package tools.vitruv.applications.cbs.operators.oo.operators;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Maps between multiple type references of the ObjectOrientedDesign concept
 * domain and another concept domain.
 * <p>
 * The actual mapping is delegated to {@link OOTypeReferenceOperator}.
 */
@AttributeMappingOperator(name = "ooTypeReferences", commonalityAttributeType = @AttributeType(multiValued = true, type = String.class), participationAttributeType = @AttributeType(multiValued = true, type = String.class))
public class OOTypeReferencesOperator extends AbstractAttributeMappingOperator<List<String>, List<String>> {

    private static final Logger logger = Logger.getLogger(OOTypeReferencesOperator.class);
    // Handles the mapping between OO and other intermediate type references:
    private final OOTypeReferenceOperator ooTypeReferenceOperator;

    public OOTypeReferencesOperator(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState);
        this.ooTypeReferenceOperator = new OOTypeReferenceOperator(executionState, targetConceptDomainName);
    }

    @Override
    public List<String> applyTowardsCommonality(List<String> ooTypeReferences) {
        List<String> intermediateTypeReferences = ooTypeReferences.stream()
                .map(ooTypeReferenceOperator::applyTowardsCommonality)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        logger.debug(String.format("Mapping OO type references %s to intermediate type references %s",
                ooTypeReferences, intermediateTypeReferences));
        return intermediateTypeReferences;
    }

    @Override
    public List<String> applyTowardsParticipation(List<String> intermediateTypeReferences) {
        List<String> ooTypeReferences = intermediateTypeReferences.stream()
                .map(ooTypeReferenceOperator::applyTowardsParticipation)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        logger.debug(String.format("Mapping intermediate type references %s to OO type references %s",
                intermediateTypeReferences, ooTypeReferences));
        return ooTypeReferences;
    }
}
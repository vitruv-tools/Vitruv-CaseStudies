package tools.vitruv.applications.cbs.operators.java.operators;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.emftext.language.java.types.TypeReference;

import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

@AttributeMappingOperator(name = "javaTypeReferences", commonalityAttributeType = @AttributeType(multiValued = true, type = String.class), participationAttributeType = @AttributeType(multiValued = true, type = TypeReference.class))
public class JavaTypeReferencesOperator extends AbstractAttributeMappingOperator<List<String>, List<TypeReference>> {

    private static final Logger logger = Logger.getLogger(JavaTypeReferencesOperator.class);

    private final JavaTypeReferenceOperator javaTypeReferenceOperator;

    public JavaTypeReferencesOperator(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState);
        this.javaTypeReferenceOperator = new JavaTypeReferenceOperator(executionState, targetConceptDomainName);
    }

    @Override
    public List<String> applyTowardsCommonality(List<TypeReference> javaTypeReferences) {
        List<String> intermediateTypeReferences = javaTypeReferences.stream()
                .map(javaTypeReferenceOperator::applyTowardsCommonality)
                .collect(Collectors.toList());
        logger.debug(String.format("Mapping Java type references %s to intermediate type references %s",
                javaTypeReferences, intermediateTypeReferences));
        return intermediateTypeReferences;
    }

    @Override
    public List<TypeReference> applyTowardsParticipation(List<String> intermediateTypeReferences) {
        List<TypeReference> javaTypeReferences = intermediateTypeReferences.stream()
                .map(javaTypeReferenceOperator::applyTowardsParticipation)
                .collect(Collectors.toList());
        logger.debug(String.format("Mapping intermediate type references %s to Java type references %s",
                intermediateTypeReferences, javaTypeReferences));
        return javaTypeReferences;
    }
}
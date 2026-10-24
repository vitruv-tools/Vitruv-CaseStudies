package tools.vitruv.applications.cbs.operators.java.operators;

import org.emftext.language.java.types.TypeReference;

import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

@AttributeMappingOperator(name = "javaTypeReferenceOrNull", commonalityAttributeType = @AttributeType(multiValued = false, type = String.class), participationAttributeType = @AttributeType(multiValued = false, type = TypeReference.class))
public class JavaTypeReferenceOrNullOperator extends AbstractAttributeMappingOperator<String, TypeReference> {

    private final JavaTypeReferenceOperator javaTypeReferenceOperator;

    public JavaTypeReferenceOrNullOperator(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState);
        this.javaTypeReferenceOperator = new JavaTypeReferenceOperator(executionState, targetConceptDomainName);
    }

    @Override
    public String applyTowardsCommonality(TypeReference domainTypeReference) {
        return javaTypeReferenceOperator.applyTowardsCommonality(domainTypeReference);
    }

    @Override
    public TypeReference applyTowardsParticipation(String intermediateTypeReference) {
        TypeReference javaTypeReference = null;
        if (intermediateTypeReference != null) {
            javaTypeReference = javaTypeReferenceOperator.applyTowardsParticipation(intermediateTypeReference);
            if (javaTypeReferenceOperator.isDomainVoidReference(javaTypeReference)) {
                javaTypeReference = null;
            }
        }
        return javaTypeReference;
    }
}
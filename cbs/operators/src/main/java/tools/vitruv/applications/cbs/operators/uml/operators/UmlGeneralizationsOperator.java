package tools.vitruv.applications.cbs.operators.uml.operators;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.UMLFactory;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

@AttributeMappingOperator(name = "umlGeneralizations", commonalityAttributeType = @AttributeType(multiValued = true, type = String.class), participationAttributeType = @AttributeType(multiValued = true, type = Generalization.class))
public class UmlGeneralizationsOperator extends AbstractAttributeMappingOperator<List<String>, List<Generalization>> {

    private static final Logger logger = Logger.getLogger(UmlGeneralizationsOperator.class);

    private final UmlTypeReferenceOperator umlTypeReferenceOperator;

    public UmlGeneralizationsOperator(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState);
        this.umlTypeReferenceOperator = new UmlTypeReferenceOperator(executionState, targetConceptDomainName);
    }

    @Override
    public List<String> applyTowardsCommonality(List<Generalization> umlGeneralizations) {
        List<String> intermediateTypeReferences = umlGeneralizations.stream()
                .map(g -> umlTypeReferenceOperator.applyTowardsCommonality(g.getGeneral()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        logger.debug(String.format("Mapping UML generalizations %s to intermediate type references %s",
                umlGeneralizations, intermediateTypeReferences));
        return intermediateTypeReferences;
    }

    @Override
    public List<Generalization> applyTowardsParticipation(List<String> intermediateTypeReferences) {
        List<Generalization> umlGeneralizations = intermediateTypeReferences.stream()
                .map(ref -> {
                    Object classifierObj = umlTypeReferenceOperator.applyTowardsParticipation(ref);
                    if (!(classifierObj instanceof Classifier)) {
                        return null;
                    }
                    Classifier umlClassifier = (Classifier) classifierObj;
                    Generalization gen = UMLFactory.eINSTANCE.createGeneralization();
                    gen.setGeneral(umlClassifier);
                    return gen;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        logger.debug(String.format("Mapping intermediate type references %s to UML generalizations %s",
                intermediateTypeReferences, umlGeneralizations));
        return umlGeneralizations;
    }
}
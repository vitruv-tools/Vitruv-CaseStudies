package tools.vitruv.applications.cbs.operators.uml.operators;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.UMLFactory;

import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

@AttributeMappingOperator(name = "umlSingleGeneralization", commonalityAttributeType = @AttributeType(multiValued = false, type = String.class), participationAttributeType = @AttributeType(multiValued = true, type = Generalization.class))
public class UmlSingleGeneralizationOperator extends AbstractAttributeMappingOperator<String, List<Generalization>> {

    private static final Logger logger = Logger.getLogger(UmlSingleGeneralizationOperator.class);

    private final UmlTypeReferenceOperator umlTypeReferenceOperator;

    public UmlSingleGeneralizationOperator(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState);
        this.umlTypeReferenceOperator = new UmlTypeReferenceOperator(executionState, targetConceptDomainName);
    }

    @Override
    public String applyTowardsCommonality(List<Generalization> umlGeneralizations) {
        if (umlGeneralizations == null) {
            logger.debug("Mapping UML generalizations null to single intermediate type reference null.");
            return null;
        }
        for (Generalization g : umlGeneralizations) {
            String intermediateTypeReference = umlTypeReferenceOperator.applyTowardsCommonality(g.getGeneral());
            logger.debug(String.format("Mapping UML generalizations %s to single intermediate type reference %s",
                    umlGeneralizations, intermediateTypeReference));
            return intermediateTypeReference;
        }
        logger.debug(String.format("Mapping UML generalizations %s to single intermediate type reference null",
                umlGeneralizations));
        return null;
    }

    @Override
    public List<Generalization> applyTowardsParticipation(String intermediateTypeReference) {
        Object classifierObj = umlTypeReferenceOperator.applyTowardsParticipation(intermediateTypeReference);
        Classifier umlClassifier = (classifierObj instanceof Classifier) ? (Classifier) classifierObj : null;
        final List<Generalization> umlGeneralizations;
        if (umlClassifier != null) {
            Generalization umlGeneralization = UMLFactory.eINSTANCE.createGeneralization();
            umlGeneralization.setGeneral(umlClassifier);
            umlGeneralizations = Collections.singletonList(umlGeneralization);
        } else {
            umlGeneralizations = Collections.emptyList();
        }
        logger.debug(String.format("Mapping intermediate type reference %s to UML generalizations %s",
                intermediateTypeReference, umlGeneralizations));
        return umlGeneralizations;
    }
}
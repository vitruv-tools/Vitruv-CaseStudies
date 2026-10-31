package tools.vitruv.applications.cbs.operators.uml.operators;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.UMLFactory;

import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

@AttributeMappingOperator(name = "umlInterfaceRealizations", commonalityAttributeType = @AttributeType(multiValued = true, type = String.class), participationAttributeType = @AttributeType(multiValued = true, type = InterfaceRealization.class))
public class UmlInterfaceRealizationsOperator
        extends AbstractAttributeMappingOperator<List<String>, List<InterfaceRealization>> {

    private static final Logger logger = Logger.getLogger(UmlInterfaceRealizationsOperator.class);

    private final UmlTypeReferenceOperator umlTypeReferenceOperator;

    public UmlInterfaceRealizationsOperator(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState);
        this.umlTypeReferenceOperator = new UmlTypeReferenceOperator(executionState, targetConceptDomainName);
    }

    @Override
    public List<String> applyTowardsCommonality(List<InterfaceRealization> umlInterfaceRealizations) {
        List<String> intermediateTypeReferences = umlInterfaceRealizations.stream()
                .map(ir -> umlTypeReferenceOperator.applyTowardsCommonality(ir.getContract()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        logger.debug(String.format("Mapping UML interface realizations %s to intermediate type references %s",
                umlInterfaceRealizations, intermediateTypeReferences));
        return intermediateTypeReferences;
    }

    @Override
    public List<InterfaceRealization> applyTowardsParticipation(List<String> intermediateTypeReferences) {
        List<InterfaceRealization> umlInterfaceRealizations = intermediateTypeReferences.stream()
                .map(ref -> {
                    Object ifaceObj = umlTypeReferenceOperator.applyTowardsParticipation(ref);
                    if (!(ifaceObj instanceof Interface)) {
                        return null;
                    }
                    Interface umlInterface = (Interface) ifaceObj;
                    InterfaceRealization ir = UMLFactory.eINSTANCE.createInterfaceRealization();
                    ir.setContract(umlInterface);
                    return ir;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        logger.debug(String.format("Mapping intermediate type references %s to UML interface realizations %s",
                intermediateTypeReferences, umlInterfaceRealizations));
        return umlInterfaceRealizations;
    }
}
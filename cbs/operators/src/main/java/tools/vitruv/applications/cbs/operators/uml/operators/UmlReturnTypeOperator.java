package tools.vitruv.applications.cbs.operators.uml.operators;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;

import static com.google.common.base.Preconditions.checkNotNull;

import static tools.vitruv.dsls.commonalities.runtime.helper.XtendAssertHelper.assertTrue;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

@AttributeMappingOperator(name = "umlReturnType", commonalityAttributeType = @AttributeType(multiValued = false, type = String.class), participationAttributeType = @AttributeType(multiValued = true, type = Parameter.class))
public class UmlReturnTypeOperator extends AbstractAttributeMappingOperator<String, List<Parameter>> {

    private static final Logger logger = Logger.getLogger(UmlReturnTypeOperator.class);

    private final UmlTypeReferenceOperator umlTypeReferenceOperator;
    private final Operation operation;

    public UmlReturnTypeOperator(ReactionExecutionState executionState, String targetConceptDomainName,
            Operation operation) {
        super(executionState);
        checkNotNull(operation, "operation is null");
        this.umlTypeReferenceOperator = new UmlTypeReferenceOperator(executionState, targetConceptDomainName);
        this.operation = operation;
    }

    private static boolean isReturnParameter(Parameter parameter) {
        assertTrue(parameter != null);
        return parameter.getDirection() == ParameterDirectionKind.RETURN_LITERAL;
    }

    private static Parameter getReturnParameter(List<Parameter> parameters) {
        if (parameters == null)
            return null;
        for (Parameter p : parameters) {
            if (isReturnParameter(p))
                return p;
        }
        return null;
    }

    @Override
    public String applyTowardsCommonality(List<Parameter> umlParameters) {
        Parameter returnParameter = getReturnParameter(umlParameters);
        Type umlReturnType = (returnParameter != null) ? returnParameter.getType() : null;
        String intermediateTypeRef = umlTypeReferenceOperator.applyTowardsCommonality(umlReturnType);
        logger.debug(String.format("Mapping parameters '%s' to intermediate return type reference '%s'.", umlParameters,
                intermediateTypeRef));
        return intermediateTypeRef;
    }

    @Override
    public List<Parameter> applyTowardsParticipation(String intermediateTypeReference) {
        Type umlReturnType = (Type) umlTypeReferenceOperator.applyTowardsParticipation(intermediateTypeReference);
        List<Parameter> newParameters = new ArrayList<>(operation.getOwnedParameters());

        if (umlReturnType != null) {
            Parameter returnParameter = getReturnParameter(newParameters);
            if (returnParameter == null) {
                returnParameter = UMLFactory.eINSTANCE.createParameter();
                returnParameter.setDirection(ParameterDirectionKind.RETURN_LITERAL);
                returnParameter.setLower(1);
                returnParameter.setUpper(1);
                newParameters.add(0, returnParameter);
            }
            returnParameter.setType(umlReturnType);
        } else {
            newParameters.removeIf(UmlReturnTypeOperator::isReturnParameter);
        }

        logger.debug(String.format("Mapping return type '%s' and previous parameters '%s' to new parameters '%s'.",
                umlReturnType, operation.getOwnedParameters(), newParameters));
        return newParameters;
    }
}
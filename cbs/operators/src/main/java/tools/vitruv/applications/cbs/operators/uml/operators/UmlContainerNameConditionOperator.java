package tools.vitruv.applications.cbs.operators.uml.operators;

import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;

import static com.google.common.base.Preconditions.checkArgument;

import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.AbstractNoArgumentConditionOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.ParticipationConditionOperator;

/**
 * Sets and/or compares the name of a {@link NamedElement} with the name of its
 * container.
 */
@ParticipationConditionOperator(name = "isUmlContainerName")
public class UmlContainerNameConditionOperator extends AbstractNoArgumentConditionOperator {

    private static final Logger logger = Logger.getLogger(UmlContainerNameConditionOperator.class);

    public UmlContainerNameConditionOperator(Object leftOperand, List<?> rightOperands) {
        super(leftOperand, rightOperands);
        checkArgument(leftOperandObject instanceof NamedElement,
                "Expecting a NamedElement as left operand object!");
        checkArgument(leftOperandFeature == UMLPackage.Literals.NAMED_ELEMENT__NAME,
                "Expecting 'NamedElement.name' as left operand feature!");
    }

    /**
     * Gets the left operand object as {@link NamedElement}.
     */
    private NamedElement getLeftNamedElement() {
        return (NamedElement) leftOperandObject;
    }

    /**
     * Gets the name of the left operand object.
     */
    private String getLeftObjectName() {
        return getLeftNamedElement().getName();
    }

    /**
     * Gets the name of the container.
     *
     * @return the container name, or {@code null} if no name is found
     */
    private String getContainerName() {
        EObject container = ((EObject) leftOperandObject).eContainer();
        if (container instanceof NamedElement namedElement) {
            return namedElement.getName();
        }
        return null;
    }

    @Override
    public void enforce() {
        String containerName = getContainerName();
        getLeftNamedElement().setName(containerName);
    }

    @Override
    public boolean check() {
        String containerName = getContainerName();
        String leftObjectName = getLeftObjectName();
        boolean result = Objects.equals(leftObjectName, containerName);
        if (!result) {
            logger.debug(String.format(
                    "Condition check failed. leftObject='%s', leftFeature='%s', leftObjectName='%s', container='%s', containerName='%s'.",
                    leftOperandObject, leftOperandFeature, leftObjectName, ((EObject) leftOperandObject).eContainer(),
                    containerName));
        }
        return result;
    }
}
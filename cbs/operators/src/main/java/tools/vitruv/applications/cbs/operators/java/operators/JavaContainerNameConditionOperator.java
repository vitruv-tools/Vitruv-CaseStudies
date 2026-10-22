package tools.vitruv.applications.cbs.operators.java.operators;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.commons.CommonsPackage;
import org.emftext.language.java.commons.NamedElement;

import static com.google.common.base.Preconditions.checkArgument;

import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.AbstractNoArgumentConditionOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.ParticipationConditionOperator;

/**
 * Sets and/or compares the name of a {@link NamedElement} with the name of its
 * container.
 */
@ParticipationConditionOperator(name = "isJavaContainerName")
public class JavaContainerNameConditionOperator extends AbstractNoArgumentConditionOperator {

    private static final Logger logger = Logger.getLogger(JavaContainerNameConditionOperator.class);

    public JavaContainerNameConditionOperator(Object leftOperand, List<?> rightOperands) {
        super(leftOperand, rightOperands);
        checkArgument(leftOperandObject instanceof NamedElement,
                "Expecting a NamedElement as left operand object!");
        checkArgument(leftOperandFeature == CommonsPackage.Literals.NAMED_ELEMENT__NAME,
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
     * @return the container name, or <code>null</code> if no name is found
     */
    private String getContainerName() {
        EObject container = leftOperandObject.eContainer();
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
        boolean result = ((leftObjectName == null ? containerName == null : leftObjectName.equals(containerName)));
        if (!result) {
            logger.debug(String.format(
                    "Condition check failed. leftObject='%s', leftFeature='%s', " +
                            "leftObjectName='%s', container='%s', containerName='%s'",
                    leftOperandObject,
                    leftOperandFeature,
                    leftObjectName,
                    leftOperandObject.eContainer(),
                    containerName));
        }

        return result;
    }
}
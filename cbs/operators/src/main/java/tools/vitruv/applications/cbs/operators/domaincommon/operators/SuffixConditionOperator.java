package tools.vitruv.applications.cbs.operators.domaincommon.operators;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EcorePackage;

import static com.google.common.base.Preconditions.checkArgument;

import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.AbstractSingleArgumentConditionOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.ParticipationConditionOperator;

/**
 * Checks if the value of a specified String-valued attribute ends with the
 * given suffix.
 * <p>
 * Note: This condition operator can only be checked, not enforced!
 */
@ParticipationConditionOperator(name = "hasSuffix")
public class SuffixConditionOperator extends AbstractSingleArgumentConditionOperator {

    private static final Logger logger = Logger.getLogger(SuffixConditionOperator.class);

    /**
     * Creates a new SuffixConditionOperator.
     * 
     * @param leftOperand   the left operand object
     * @param rightOperands list containing the right operand (suffix)
     * @throws IllegalArgumentException if left operand is not a String attribute
     *                                  or right operand is not a String
     */
    public SuffixConditionOperator(Object leftOperand, List<?> rightOperands) {
        super(leftOperand, rightOperands);
        checkArgument(leftOperandFeature.getEType() == EcorePackage.Literals.ESTRING,
                "Expecting a String-valued attribute as left operand!");
        checkArgument(getRightOperand() instanceof String,
                "Expecting a String as right operand!");
    }

    private String getSuffix() {
        return (String) getRightOperand();
    }

    private String getLeftAttributeValue() {
        return (String) leftOperandObject.eGet(leftOperandFeature);
    }

    @Override
    public void enforce() {
        // Participation conditions are enforced during participation instantiation.
        // At this point the left attribute value is not guaranteed to have been setup
        // yet.
        // Attribute mappings are better suited to assign values which depend on the
        // values of commonality attributes.
        throw new UnsupportedOperationException(
                "This ParticipationConditionOperator does not support enforcement!");
    }

    @Override
    public boolean check() {
        String leftAttributeValue = getLeftAttributeValue();
        String suffix = getSuffix();
        boolean result = leftAttributeValue.endsWith(suffix);

        if (!result) {
            logger.debug(String.format(
                    "Condition check failed. leftObject=getLeftOperandObject(), leftFeature=getLeftOperandFeature(), " +
                            "leftAttributeValue='%s', suffix='%s'.",
                    leftAttributeValue,
                    suffix));
        }
        return result;
    }
}
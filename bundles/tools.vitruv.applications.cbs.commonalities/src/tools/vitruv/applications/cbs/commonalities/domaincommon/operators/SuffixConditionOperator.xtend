package tools.vitruv.applications.cbs.commonalities.domaincommon.operators

import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EcorePackage
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.AbstractSingleArgumentConditionOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.ParticipationConditionOperator

import static com.google.common.base.Preconditions.*

/**
 * Checks if the value of a specified String-valued attribute ends with the
 * given suffix.
 * <p>
 * Note: This condition operator can only be checked, not enforced!
 */
@ParticipationConditionOperator(name='hasSuffix')
class SuffixConditionOperator extends AbstractSingleArgumentConditionOperator {

	static val Logger logger = Logger.getLogger(SuffixConditionOperator)

	new(Object leftOperand, List<?> rightOperands) {
		super(leftOperand, rightOperands)
		checkArgument(leftOperandFeature.EType == EcorePackage.Literals.ESTRING,
			"Expecting a String-valued attribute as left operand!")
		checkArgument(rightOperand instanceof String, "Expecting a String as right operand!")
	}

	private def String getSuffix() {
		return rightOperand as String
	}

	private def String getLeftAttributeValue() {
		return leftOperandObject.eGet(leftOperandFeature) as String
	}

	override enforce() {
		// Participation conditions are enforced during participation instantiation. At this point the left attribute
		// value is not guaranteed to have been setup yet. Attribute mappings are better suited to assign values which
		// depend on the values of commonality attributes.
		throw new UnsupportedOperationException("This ParticipationConditionOperator does not support enforcement!")
	}

	override check() {
		val leftAttributeValue = leftAttributeValue
		val suffix = suffix
		val result = leftAttributeValue.endsWith(suffix)
		if (!result) {
			logger.debug('''Condition check failed. leftObject='«leftOperandObject»', leftFeature='«leftOperandFeature
				»', leftAttributeValue='«leftAttributeValue»', suffix='«suffix»'.''')
		}
		return result
	}
}

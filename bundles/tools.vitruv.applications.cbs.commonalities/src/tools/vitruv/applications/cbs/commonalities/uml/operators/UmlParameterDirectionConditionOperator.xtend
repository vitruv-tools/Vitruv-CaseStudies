package tools.vitruv.applications.cbs.commonalities.uml.operators

import java.util.List
import org.apache.log4j.Logger
import org.eclipse.uml2.uml.ParameterDirectionKind
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.AbstractSingleArgumentConditionOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.ParticipationConditionOperator

import static com.google.common.base.Preconditions.*

/**
 * Checks and/or sets the parameter direction kind for a given UML Parameter.
 */
@ParticipationConditionOperator(name = 'isUmlParameterDirection')
class UmlParameterDirectionConditionOperator extends AbstractSingleArgumentConditionOperator {

	static val Logger logger = Logger.getLogger(UmlParameterDirectionConditionOperator)

	new(Object leftOperand, List<?> rightOperands) {
		super(leftOperand, rightOperands)
		checkArgument(rightOperand instanceof String, "Expecting String as right operand!")
		checkArgument(rightParameterDirection !== null, "Invalid ParameterDirectionKind: " + rightOperand)
	}

	// Returns null if no matching ParameterDirectionKind is found.
	private def ParameterDirectionKind getRightParameterDirection() {
		return ParameterDirectionKind.getByName(rightOperand as String)
	}

	override enforce() {
		leftOperandObject.eSet(leftOperandFeature, rightParameterDirection)
	}

	override check() {
		val leftParameterDirection = leftOperandObject.eGet(leftOperandFeature)
		val result = (leftParameterDirection === rightParameterDirection)
		if (!result) {
			logger.debug('''Condition check failed. leftObject='«leftOperandObject»', leftFeature='«leftOperandFeature
				»', leftParameterDirection='«leftParameterDirection»', rightParameterDirection='«
				rightParameterDirection»'.''')
		}
		return result
	}
}

package tools.vitruv.applications.cbs.commonalities.util.uml.operators.condition

import java.util.List
import org.eclipse.uml2.uml.Classifier
import tools.vitruv.applications.cbs.commonalities.util.oo.Visibility
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.AbstractSingleArgumentOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.IParticipationClassConditionOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.ParticipationConditionOperator

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.applications.cbs.commonalities.util.uml.UmlVisibilityHelper.*

@ParticipationConditionOperator(name = 'hasUmlVisibility')
class UmlVisibilityConditionOperator extends AbstractSingleArgumentOperator implements IParticipationClassConditionOperator {

	new(Object leftOperand, List<?> rightOperands) {
		super(leftOperand, rightOperands)
		checkArgument(leftOperand instanceof Classifier,
			"Expecting a Classifier as left operand!")
		checkArgument(rightOperand instanceof String, "Expecting String as right operand!")
		checkArgument(rightVisibility !== null, "Invalid visibility: " + rightOperand)
	}

	private def Classifier getLeftClassifier() {
		return leftOperandObject as Classifier
	}

	private def Visibility getRightVisibility() {
		return Visibility.byName(rightOperand as String)
	}

	override enforce() {
		leftClassifier.visibility = rightVisibility.toUmlVisibility
	}

	override check() {
		return (leftClassifier.visibility.toVisibility === rightVisibility)
	}
}

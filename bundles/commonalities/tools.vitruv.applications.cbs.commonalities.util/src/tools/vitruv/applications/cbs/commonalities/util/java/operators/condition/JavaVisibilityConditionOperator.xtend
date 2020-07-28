package tools.vitruv.applications.cbs.commonalities.util.java.operators.condition

import java.util.List
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import tools.vitruv.applications.cbs.commonalities.util.oo.Visibility
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.AbstractSingleArgumentOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.IParticipationClassConditionOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.ParticipationConditionOperator

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.applications.cbs.commonalities.util.java.JavaVisibilityHelper.*

@ParticipationConditionOperator(name = 'hasJavaVisibility')
class JavaVisibilityConditionOperator extends AbstractSingleArgumentOperator implements IParticipationClassConditionOperator {

	new(Object leftOperand, List<?> rightOperands) {
		super(leftOperand, rightOperands)
		checkArgument(leftOperand instanceof AnnotableAndModifiable,
			"Expecting an AnnotableAndModifiable as left operand!")
		checkArgument(rightOperand instanceof String, "Expecting String as right operand!")
		checkArgument(rightVisibility !== null, "Invalid visibility: " + rightOperand)
	}

	private def AnnotableAndModifiable getLeftAnnotableAndModifiable() {
		return leftOperandObject as AnnotableAndModifiable
	}

	private def Visibility getRightVisibility() {
		return Visibility.byName(rightOperand as String)
	}

	override enforce() {
		val modifiers = leftAnnotableAndModifiable.annotationsAndModifiers
		val newModifiers = modifiers.setVisibility(rightVisibility)
		modifiers.clear
		modifiers += newModifiers
	}

	override check() {
		val modifiers = leftAnnotableAndModifiable.annotationsAndModifiers
		return (modifiers.visibility === rightVisibility)
	}
}

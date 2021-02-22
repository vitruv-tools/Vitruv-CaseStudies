package tools.vitruv.applications.cbs.commonalities.java.operators

import java.util.List
import org.apache.log4j.Logger
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.ModifiersPackage
import tools.vitruv.applications.cbs.commonalities.oo.Visibility
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.AbstractSingleArgumentConditionOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.ParticipationConditionOperator

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.applications.cbs.commonalities.java.JavaVisibilityHelper.*

@ParticipationConditionOperator(name = 'hasJavaVisibility')
class JavaVisibilityConditionOperator extends AbstractSingleArgumentConditionOperator {

	static val Logger logger = Logger.getLogger(JavaVisibilityConditionOperator)

	new(Object leftOperand, List<?> rightOperands) {
		super(leftOperand, rightOperands)
		checkArgument(leftOperandObject instanceof AnnotableAndModifiable,
			"Expecting an AnnotableAndModifiable as left operand object!")
		checkArgument(leftOperandFeature == ModifiersPackage.Literals.ANNOTABLE_AND_MODIFIABLE__ANNOTATIONS_AND_MODIFIERS,
			"Expecting 'annotationsAndModifiers' as left operand feature!")
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
		if (modifiers != newModifiers) {
			modifiers.clear
			modifiers += newModifiers
		}
	}

	override check() {
		val modifiers = leftAnnotableAndModifiable.annotationsAndModifiers
		val leftVisibility = modifiers.visibility
		val result = (leftVisibility === rightVisibility)
		if (!result) {
			logger.debug('''Condition check failed. leftObject='«leftOperandObject»', leftFeature='«leftOperandFeature
				»', leftVisibility='«leftVisibility»', rightVisibility='«rightVisibility»'.''')
		}
		return result
	}
}

package tools.vitruv.applications.cbs.commonalities.uml.operators

import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.uml2.uml.UMLPackage
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.cbs.commonalities.oo.Visibility
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.AbstractSingleArgumentConditionOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.ParticipationConditionOperator

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.applications.cbs.commonalities.uml.UmlVisibilityHelper.*

/**
 * Checks and/or sets the visibility kind of an UML object which has a
 * visibility attribute.
 */
@ParticipationConditionOperator(name = 'isUmlVisibility')
class UmlVisibilityConditionOperator extends AbstractSingleArgumentConditionOperator {

	static val Logger logger = Logger.getLogger(UmlVisibilityConditionOperator)

	new(Object leftOperand, List<?> rightOperands) {
		super(leftOperand, rightOperands)
		checkArgument(leftOperandFeature.isVisibilityAttribute, '''Invalid visibility attribute '«
			leftOperandFeature.name»'.''')
		checkArgument(rightOperand instanceof String, "Expecting String as right operand!")
		checkArgument(rightVisibility !== null, "Invalid visibility: " + rightOperand)
	}

	/**
	 * Checks if the given feature is a visibility attribute.
	 */
	private static def isVisibilityAttribute(EStructuralFeature feature) {
		if (feature instanceof EAttribute) {
			if (feature.EAttributeType === UMLPackage.Literals.VISIBILITY_KIND) {
				return true
			}
		}
		return false
	}

	private def Visibility getRightVisibility() {
		return Visibility.byName(rightOperand as String)
	}

	override enforce() {
		leftOperandObject.eSet(leftOperandFeature, rightVisibility.toUmlVisibility)
	}

	override check() {
		val umlVisibility = leftOperandObject.eGet(leftOperandFeature) as VisibilityKind
		val leftVisibility = umlVisibility.toVisibility
		val result = (leftVisibility === rightVisibility)
		if (!result) {
			logger.debug('''Condition check failed. leftObject='«leftOperandObject»', leftFeature='«leftOperandFeature
				»', leftVisibility='«leftVisibility»', rightVisibility='«rightVisibility»'.''')
		}
		return result
	}
}

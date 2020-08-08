package tools.vitruv.applications.cbs.commonalities.util.uml.operators.condition

import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.uml2.uml.UMLPackage
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.cbs.commonalities.util.oo.Visibility
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.AbstractSingleArgumentOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.IParticipationClassConditionOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.ParticipationConditionOperator

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.applications.cbs.commonalities.util.uml.UmlVisibilityHelper.*

/**
 * Checks and/or sets the visibility kind for any type of UML object which has
 * a visibility attribute.
 */
@ParticipationConditionOperator(name = 'hasUmlVisibility')
class UmlVisibilityConditionOperator extends AbstractSingleArgumentOperator implements IParticipationClassConditionOperator {

	static val Logger logger = Logger.getLogger(UmlVisibilityConditionOperator)

	private val EAttribute visibilityAttribute

	new(Object leftOperand, List<?> rightOperands) {
		super(leftOperand, rightOperands)
		this.visibilityAttribute = leftOperandObject.eClass.findVisibilityFeature
		checkNotNull(visibilityAttribute, "Could not find visibility attribute in " + leftOperandObject.eClass.name)
		checkArgument(rightOperand instanceof String, "Expecting String as right operand!")
		checkArgument(rightVisibility !== null, "Invalid visibility: " + rightOperand)
	}

	/**
	 * Searches for a supported kind of 'visibility' attribute.
	 * <p>
	 * The UML metamodel has no common super type for all objects which have a
	 * visibility attribute. For this operator to support all types of objects
	 * which have a visibility attribute, we need to find the respective
	 * visibility attribute and then reflectively get or set it.
	 * <p>
	 * Returns <code>null</code> if we could not find a valid visibility
	 * attribute.
	 */
	private static def findVisibilityFeature(EClass eClass) {
		val feature = eClass.getEStructuralFeature('visibility')
		if (feature instanceof EAttribute) {
			if (feature.EAttributeType === UMLPackage.Literals.VISIBILITY_KIND) {
				return feature
			}
		}
		return null // Could not find a valid visibility attribute
	}

	private def Visibility getRightVisibility() {
		return Visibility.byName(rightOperand as String)
	}

	override enforce() {
		leftOperandObject.eSet(visibilityAttribute, rightVisibility.toUmlVisibility)
	}

	override check() {
		val umlVisibility = leftOperandObject.eGet(visibilityAttribute) as VisibilityKind
		val leftVisibility = umlVisibility.toVisibility
		val result = (leftVisibility === rightVisibility)
		if (!result) {
			logger.debug('''Condition check failed. leftObject='«leftOperandObject»', leftFeature='«leftOperandFeature
				»', leftVisibility='«leftVisibility»', rightVisibility='«rightVisibility»'.''')
		}
		return result
	}
}

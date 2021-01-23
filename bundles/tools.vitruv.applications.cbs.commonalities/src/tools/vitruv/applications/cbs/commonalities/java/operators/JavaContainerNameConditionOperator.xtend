package tools.vitruv.applications.cbs.commonalities.java.operators

import java.util.List
import org.apache.log4j.Logger
import org.emftext.language.java.commons.CommonsPackage
import org.emftext.language.java.commons.NamedElement
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.AbstractNoArgumentConditionOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.ParticipationConditionOperator

import static com.google.common.base.Preconditions.*

/**
 * Sets and/or compares the name of a {@link NamedElement} with the name of its
 * container.
 */
@ParticipationConditionOperator(name = 'isJavaContainerName')
class JavaContainerNameConditionOperator extends AbstractNoArgumentConditionOperator {

	static val Logger logger = Logger.getLogger(JavaContainerNameConditionOperator)

	new(Object leftOperand, List<?> rightOperands) {
		super(leftOperand, rightOperands)
		checkArgument(leftOperandObject instanceof NamedElement,
			"Expecting a NamedElement as left operand object!")
		checkArgument(leftOperandFeature === CommonsPackage.Literals.NAMED_ELEMENT__NAME,
			"Expecting 'NamedElement.name' as left operand feature!")
	}

	/**
	 * Gets the left operand object as {@link NamedElement}.
	 */
	private def NamedElement getLeftNamedElement() {
		return (leftOperandObject as NamedElement)
	}

	/**
	 * Gets the name of the left operand object.
	 */
	private def String getLeftObjectName() {
		return leftNamedElement.name
	}

	/**
	 * Gets the name of the container.
	 * 
	 * @return the container name, or <code>null</code> if no name is found
	 */
	private def String getContainerName() {
		val container = leftOperandObject.eContainer
		if (container instanceof NamedElement) {
			return container.name
		}
		return null
	}

	override enforce() {
		val containerName = containerName
		leftNamedElement.name = containerName
	}

	override check() {
		val containerName = containerName
		val leftObjectName = leftObjectName
		val result = (leftObjectName === containerName)
		if (!result) {
			logger.debug('''Condition check failed. leftObject='«leftOperandObject»', leftFeature='«leftOperandFeature
				»', leftObjectName='«leftObjectName»', container='«leftOperandObject.eContainer»', containerName='«
				containerName»'.''')
		}
		return result
	}
}

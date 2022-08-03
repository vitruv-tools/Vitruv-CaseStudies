package tools.vitruv.applications.cbs.commonalities.domaincommon.operators

import org.apache.log4j.Logger
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState

import static tools.vitruv.dsls.commonalities.runtime.helper.XtendAssertHelper.*

/**
 * Converts between two boolean attributes, with one being the inverse of the
 * other.
 */
@AttributeMappingOperator(
	name='inverse',
	commonalityAttributeType = @AttributeType(multiValued=false, type=boolean),
	participationAttributeType = @AttributeType(multiValued=false, type=boolean)
)
class InverseOperator extends AbstractAttributeMappingOperator<Boolean, Boolean> {

	static val Logger logger = Logger.getLogger(InverseOperator)

	/**
	 * @param executionState the reactions execution state
	 */
	new(ReactionExecutionState executionState) {
		super(executionState)
	}

	// boolean -> !boolean
	override applyTowardsCommonality(Boolean value) {
		assertTrue(value !== null)
		val result = !value
		logger.debug('''Mapping Boolean '«value»' to Boolean '«result»'.''')
		return result
	}

	// boolean -> !boolean
	override applyTowardsParticipation(Boolean value) {
		assertTrue(value !== null)
		val result = !value
		logger.debug('''Mapping Boolean '«value»' to Boolean '«result»'.''')
		return result
	}
}

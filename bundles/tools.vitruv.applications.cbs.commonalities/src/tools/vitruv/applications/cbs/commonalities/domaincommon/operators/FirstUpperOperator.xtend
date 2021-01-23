package tools.vitruv.applications.cbs.commonalities.domaincommon.operators

import org.apache.log4j.Logger
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

/**
 * Converts between a String with a lower case character at the front on the
 * participation side and a String with an upper case character at the front on
 * the commonality side.
 */
@AttributeMappingOperator(
	name='firstUpper',
	commonalityAttributeType = @AttributeType(multiValued=false, type=String),
	participationAttributeType = @AttributeType(multiValued=false, type=String)
)
class FirstUpperOperator extends AbstractAttributeMappingOperator<String, String> {

	static val Logger logger = Logger.getLogger(FirstUpperOperator)

	/**
	 * @param executionState the reactions execution state
	 */
	new(ReactionExecutionState executionState) {
		super(executionState)
	}

	// first lower -> first upper
	override applyTowardsCommonality(String string) {
		val firstUpper = string.toFirstUpper
		logger.debug('''Mapping string '«string»' to firstUpper '«firstUpper»'.''')
		return firstUpper
	}

	// first upper -> first lower
	override applyTowardsParticipation(String string) {
		val firstLower = string.toFirstLower
		logger.debug('''Mapping string '«string»' to firstLower '«firstLower»'.''')
		return firstLower
	}
}

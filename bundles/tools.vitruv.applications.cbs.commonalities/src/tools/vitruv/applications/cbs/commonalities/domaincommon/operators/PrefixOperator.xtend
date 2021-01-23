package tools.vitruv.applications.cbs.commonalities.domaincommon.operators

import org.apache.log4j.Logger
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static com.google.common.base.Preconditions.*

/**
 * Converts between {@code "<prefix><suffix>"} on the participation side and
 * {@code "<prefix>"} on the commonality side.
 */
@AttributeMappingOperator(
	name='prefix',
	commonalityAttributeType = @AttributeType(multiValued=false, type=String),
	participationAttributeType = @AttributeType(multiValued=false, type=String)
)
class PrefixOperator extends AbstractAttributeMappingOperator<String, String> {

	static val Logger logger = Logger.getLogger(PrefixOperator)

	val String suffix

	/**
	 * @param executionState the reactions execution state
	 * @param suffix the suffix, can be empty
	 */
	new(ReactionExecutionState executionState, String suffix) {
		super(executionState)
		checkNotNull(suffix, "suffix is null")
		this.suffix = suffix
	}

	// "<prefix><suffix>" -> "<prefix>"
	override applyTowardsCommonality(String fullText) {
		var String prefix = fullText
		if (fullText.endsWith(suffix)) {
			prefix = fullText.substring(0, fullText.length - suffix.length)
		}
		logger.debug('''Mapping full text '«fullText»' to prefix '«prefix»'.''')
		return prefix
	}

	// "<prefix>" -> "<prefix><suffix>"
	override applyTowardsParticipation(String prefix) {
		val fullText = prefix + suffix
		logger.debug('''Mapping prefix '«prefix»' to full text '«fullText»'.''')
		return fullText
	}
}

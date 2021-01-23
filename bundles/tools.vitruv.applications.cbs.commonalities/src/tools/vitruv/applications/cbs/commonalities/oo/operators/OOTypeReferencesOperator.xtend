package tools.vitruv.applications.cbs.commonalities.oo.operators

import java.util.List
import org.apache.log4j.Logger
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

/**
 * Maps between multiple type references of the ObjectOrientedDesign concept
 * domain and another concept domain.
 * <p>
 * The actual mapping is delegated to {@link OOTypeReferenceOperator}.
 */
@AttributeMappingOperator(
	name='ooTypeReferences',
	commonalityAttributeType=@AttributeType(multiValued=true, type=String),
	participationAttributeType=@AttributeType(multiValued=true, type=String)
)
class OOTypeReferencesOperator extends AbstractAttributeMappingOperator<List<String>, List<String>> {

	static val Logger logger = Logger.getLogger(OOTypeReferencesOperator)

	// Handles the mapping between OO and other intermediate type references:
	val OOTypeReferenceOperator ooTypeReferenceOperator

	/**
	 * @param executionState the reactions execution state
	 * @param targetConceptDomainName the name of the target concept domain
	 */
	new(ReactionExecutionState executionState, String targetConceptDomainName) {
		super(executionState)
		this.ooTypeReferenceOperator = new OOTypeReferenceOperator(executionState, targetConceptDomainName)
	}

	// OO intermediate type references -> Other intermediate type references
	override applyTowardsCommonality(List<String> ooTypeReferences) {
		val intermediateTypeReferences = ooTypeReferences.map [
			ooTypeReferenceOperator.applyTowardsCommonality(it)
		]
		logger.debug('''Mapping OO type references «ooTypeReferences» to intermediate type references «
			intermediateTypeReferences».''')
		return intermediateTypeReferences
	}

	// Other intermediate type references -> OO intermediate type references
	override applyTowardsParticipation(List<String> intermediateTypeReferences) {
		val ooTypeReferences = intermediateTypeReferences.map [
			ooTypeReferenceOperator.applyTowardsParticipation(it)
		]
		logger.debug('''Mapping intermediate type references «intermediateTypeReferences» to OO type references «
			ooTypeReferences».''')
		return ooTypeReferences
	}
}

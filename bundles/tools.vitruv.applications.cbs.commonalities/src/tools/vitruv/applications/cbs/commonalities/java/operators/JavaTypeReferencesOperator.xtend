package tools.vitruv.applications.cbs.commonalities.java.operators

import java.util.List
import org.apache.log4j.Logger
import org.emftext.language.java.types.TypeReference
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

/**
 * Maps between multiple Java type references and multiple corresponding
 * intermediate representations of these type references.
 * <p>
 * The actual mapping is delegated to {@link JavaTypeReferenceOperator}.
 */
@AttributeMappingOperator(
	name='javaTypeReferences',
	commonalityAttributeType=@AttributeType(multiValued=true, type=String),
	participationAttributeType=@AttributeType(multiValued=true, type=TypeReference)
)
class JavaTypeReferencesOperator extends AbstractAttributeMappingOperator<List<String>, List<TypeReference>> {

	static val Logger logger = Logger.getLogger(JavaTypeReferencesOperator)

	// Handles the mapping between Java and intermediate type references:
	val JavaTypeReferenceOperator javaTypeReferenceOperator

	/**
	 * @param executionState the reactions execution state
	 * @param targetConceptDomainName the name of the target concept domain
	 */
	new(ReactionExecutionState executionState, String targetConceptDomainName) {
		super(executionState)
		this.javaTypeReferenceOperator = new JavaTypeReferenceOperator(executionState, targetConceptDomainName)
	}

	// Java type references -> Intermediate type references
	override applyTowardsCommonality(List<TypeReference> javaTypeReferences) {
		val intermediateTypeReferences = javaTypeReferences.map [
			javaTypeReferenceOperator.applyTowardsCommonality(it)
		]
		logger.debug('''Mapping Java type references «javaTypeReferences» to intermediate type references «
			intermediateTypeReferences».''')
		return intermediateTypeReferences
	}

	// Intermediate type references -> Java type references
	override applyTowardsParticipation(List<String> intermediateTypeReferences) {
		val javaTypeReferences = intermediateTypeReferences.map [
			javaTypeReferenceOperator.applyTowardsParticipation(it)
		]
		logger.debug('''Mapping intermediate type references «intermediateTypeReferences» to Java type references «
			javaTypeReferences».''')
		return javaTypeReferences
	}
}

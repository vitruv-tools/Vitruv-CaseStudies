package tools.vitruv.applications.cbs.commonalities.java.operators

import org.emftext.language.java.types.TypeReference
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

/**
 * Similar to {@link JavaTypeReferenceOperator}, but maps to <code>null</code>
 * instead of Void.
 * <p>
 * This operator delegates to {@link JavaTypeReferenceOperator} for the actual
 * mapping.
 */
@AttributeMappingOperator(
	name='javaTypeReferenceOrNull',
	commonalityAttributeType=@AttributeType(multiValued=false, type=String),
	participationAttributeType=@AttributeType(multiValued=false, type=TypeReference)
)
class JavaTypeReferenceOrNullOperator extends AbstractAttributeMappingOperator<String, TypeReference> {

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

	// Java type reference -> Intermediate type reference
	override String applyTowardsCommonality(TypeReference domainTypeReference) {
		val intermediateTypeReference = javaTypeReferenceOperator.applyTowardsCommonality(domainTypeReference)
		return intermediateTypeReference
	}

	// Intermediate type reference -> Java type reference
	override TypeReference applyTowardsParticipation(String intermediateTypeReference) {
		var TypeReference javaTypeReference = null
		if (intermediateTypeReference !== null) {
			javaTypeReference = javaTypeReferenceOperator.applyTowardsParticipation(intermediateTypeReference)
			if (javaTypeReferenceOperator.isDomainVoidReference(javaTypeReference)) {
				javaTypeReference = null
			}
		}
		return javaTypeReference
	}
}

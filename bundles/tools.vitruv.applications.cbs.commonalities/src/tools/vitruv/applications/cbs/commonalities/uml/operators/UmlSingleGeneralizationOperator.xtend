package tools.vitruv.applications.cbs.commonalities.uml.operators

import java.util.List
import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Generalization
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

/**
 * Similar to {@link UmlGeneralizationsOperator}, but only maps a single
 * generalization.
 */
@AttributeMappingOperator(
	name='umlSingleGeneralization',
	commonalityAttributeType=@AttributeType(multiValued=false, type=String),
	participationAttributeType=@AttributeType(multiValued=true, type=Generalization)
)
class UmlSingleGeneralizationOperator extends AbstractAttributeMappingOperator<String, List<Generalization>> {

	static val Logger logger = Logger.getLogger(UmlSingleGeneralizationOperator)

	// Maps between UML and intermediate type references:
	val UmlTypeReferenceOperator umlTypeReferenceOperator

	/**
	 * @param executionState the reactions execution state
	 * @param targetConceptDomainName the name of the target concept domain
	 */
	new(ReactionExecutionState executionState, String targetConceptDomainName) {
		super(executionState)
		this.umlTypeReferenceOperator = new UmlTypeReferenceOperator(executionState, targetConceptDomainName)
	}

	// UML generalizations -> Intermediate type reference
	override applyTowardsCommonality(List<Generalization> umlGeneralizations) {
		val intermediateTypeReference = umlGeneralizations.map [
			umlTypeReferenceOperator.applyTowardsCommonality(it.general)
		].head
		logger.debug('''Mapping UML generalizations «umlGeneralizations» to single intermediate type reference «
			intermediateTypeReference».''')
		return intermediateTypeReference
	}

	// Intermediate type reference -> UML generalizations
	override applyTowardsParticipation(String intermediateTypeReference) {
		val umlClassifier = umlTypeReferenceOperator.applyTowardsParticipation(intermediateTypeReference) as Classifier
		var List<Generalization> umlGeneralizations
		if (umlClassifier !== null) {
			val umlGeneralization = UMLFactory.eINSTANCE.createGeneralization => [
				general = umlClassifier
			]
			umlGeneralizations = #[umlGeneralization]
		} else {
			umlGeneralizations = #[]
		}
		logger.debug('''Mapping intermediate type reference «intermediateTypeReference» to UML generalizations «
			umlGeneralizations».''')
		return umlGeneralizations
	}
}

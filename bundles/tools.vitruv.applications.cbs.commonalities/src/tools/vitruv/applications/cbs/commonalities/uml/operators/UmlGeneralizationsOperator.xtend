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
 * Maps between UML {@link Generalization}s and intermediate type references.
 * <p>
 * This reuses the implementation of {@link UmlTypeReferenceOperator} to map
 * between UML and intermediate type references.
 */
@AttributeMappingOperator(
	name='umlGeneralizations',
	commonalityAttributeType=@AttributeType(multiValued=true, type=String),
	participationAttributeType=@AttributeType(multiValued=true, type=Generalization)
)
class UmlGeneralizationsOperator extends AbstractAttributeMappingOperator<List<String>, List<Generalization>> {

	static val Logger logger = Logger.getLogger(UmlGeneralizationsOperator)

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

	// UML generalizations -> Intermediate type references
	override applyTowardsCommonality(List<Generalization> umlGeneralizations) {
		val intermediateTypeReferences = umlGeneralizations.map [
			umlTypeReferenceOperator.applyTowardsCommonality(it.general)
		].filterNull.toList
		logger.debug('''Mapping UML generalizations «umlGeneralizations» to intermediate type references «
			intermediateTypeReferences».''')
		return intermediateTypeReferences
	}

	// Intermediate type references -> UML generalizations
	override applyTowardsParticipation(List<String> intermediateTypeReferences) {
		val umlGeneralizations = intermediateTypeReferences.map [
			val umlClassifier = umlTypeReferenceOperator.applyTowardsParticipation(it) as Classifier
			if (umlClassifier === null) return null
			return UMLFactory.eINSTANCE.createGeneralization => [
				general = umlClassifier
			]
		].filterNull.toList
		logger.debug('''Mapping intermediate type references «intermediateTypeReferences» to UML generalizations «
			umlGeneralizations».''')
		return umlGeneralizations
	}
}

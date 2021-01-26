package tools.vitruv.applications.cbs.commonalities.uml.operators

import java.util.List
import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.InterfaceRealization
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

/**
 * Maps between UML {@link InterfaceRealization}s and intermediate type
 * references.
 * <p>
 * This reuses the implementation of {@link UmlTypeReferenceOperator} to map
 * between UML and intermediate type references.
 */
@AttributeMappingOperator(
	name='umlInterfaceRealizations',
	commonalityAttributeType=@AttributeType(multiValued=true, type=String),
	participationAttributeType=@AttributeType(multiValued=true, type=InterfaceRealization)
)
class UmlInterfaceRealizationsOperator extends AbstractAttributeMappingOperator<List<String>, List<InterfaceRealization>> {

	static val Logger logger = Logger.getLogger(UmlInterfaceRealizationsOperator)

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

	// UML interface realizations -> Intermediate type references
	override applyTowardsCommonality(List<InterfaceRealization> umlInterfaceRealizations) {
		val intermediateTypeReferences = umlInterfaceRealizations.map [
			umlTypeReferenceOperator.applyTowardsCommonality(it.contract)
		].filterNull.toList
		logger.debug('''Mapping UML interface realizations «umlInterfaceRealizations» to intermediate type references «
			intermediateTypeReferences».''')
		return intermediateTypeReferences
	}

	// Intermediate type references -> UML interface realizations
	override applyTowardsParticipation(List<String> intermediateTypeReferences) {
		val umlInterfaceRealizations = intermediateTypeReferences.map [
			val umlInterface = umlTypeReferenceOperator.applyTowardsParticipation(it) as Interface
			if (umlInterface === null) return null
			return UMLFactory.eINSTANCE.createInterfaceRealization => [
				contract = umlInterface
			]
		].filterNull.toList
		logger.debug('''Mapping intermediate type references «intermediateTypeReferences
			» to UML interface realizations «umlInterfaceRealizations».''')
		return umlInterfaceRealizations
	}
}

package tools.vitruv.applications.cbs.commonalities.uml.operators

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.cbs.commonalities.oo.Visibility
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static extension tools.vitruv.applications.cbs.commonalities.uml.UmlVisibilityHelper.*

@AttributeMappingOperator(
	name='umlVisibility',
	commonalityAttributeType=@AttributeType(multiValued=false, type=String),
	participationAttributeType=@AttributeType(multiValued=false, type=VisibilityKind)
)
class UmlVisibilityOperator extends AbstractAttributeMappingOperator<String, VisibilityKind> {

	static val Logger logger = Logger.getLogger(UmlVisibilityOperator)

	/**
	 * @param executionState the reactions execution state
	 */
	new(ReactionExecutionState executionState) {
		super(executionState)
	}

	// UML VisibilityKind -> Visibility name
	override applyTowardsCommonality(VisibilityKind umlVisibility) {
		val visibility = umlVisibility.toVisibility // can be null
		val visibilityName = visibility?.name // can be null
		logger.debug('''Mapping UML VisibilityKind «umlVisibility» to visibility «visibilityName».''')
		return visibilityName
	}

	// Visibility name -> UML VisibilityKind
	override applyTowardsParticipation(String visibilityName) {
		val visibility = Visibility.byName(visibilityName) // can be null
		val umlVisibility = visibility.toUmlVisibility // can be null
		logger.debug('''Mapping visibility «visibilityName» to UML VisibilityKind «umlVisibility».''')
		return umlVisibility
	}
}

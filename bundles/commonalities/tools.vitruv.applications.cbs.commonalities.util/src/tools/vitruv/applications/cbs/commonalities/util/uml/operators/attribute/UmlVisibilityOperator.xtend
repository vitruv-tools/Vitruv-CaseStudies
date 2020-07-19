package tools.vitruv.applications.cbs.commonalities.util.uml.operators.attribute

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.cbs.commonalities.util.oo.Visibility
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

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

	// Only returns null if the given VisibilityKind is null.
	private static def Visibility toVisibility(VisibilityKind umlVisibility) {
		if (umlVisibility === null) return null
		switch (umlVisibility) {
			case PUBLIC_LITERAL: return Visibility.PUBLIC
			case PROTECTED_LITERAL: return Visibility.PROTECTED
			case PACKAGE_LITERAL: return Visibility.PACKAGE
			case PRIVATE_LITERAL: return Visibility.PRIVATE
			default: {
				throw new IllegalArgumentException("Unhandled UML visibility kind: " + umlVisibility.getName())
			}
		}
	}

	// Only returns null if the given visibility is null.
	private static def toUMLVisibility(Visibility visibility) {
		if (visibility === null) return null
		switch (visibility) {
			case PUBLIC: return VisibilityKind.PUBLIC_LITERAL
			case PROTECTED: return VisibilityKind.PROTECTED_LITERAL
			case PACKAGE: return VisibilityKind.PACKAGE_LITERAL
			case PRIVATE: return VisibilityKind.PRIVATE_LITERAL
			default: {
				throw new IllegalStateException("Unhandled Visibility value: " + visibility)
			}
		}
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
		val umlVisibility = visibility.toUMLVisibility // can be null
		logger.debug('''Mapping visibility «visibilityName» to UML VisibilityKind «umlVisibility».''')
		return umlVisibility
	}
}

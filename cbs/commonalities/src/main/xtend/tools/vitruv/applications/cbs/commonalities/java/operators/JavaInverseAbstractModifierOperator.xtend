package tools.vitruv.applications.cbs.commonalities.java.operators

import java.util.List
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState

import static tools.vitruv.dsls.commonalities.runtime.helper.XtendAssertHelper.*

/**
 * Similar to {@link JavaAbstractModifierOperator}, but inverts the value of
 * the boolean modifier flag.
 */
@AttributeMappingOperator(
	name='javaInverseAbstractModifier',
	commonalityAttributeType=@AttributeType(multiValued=false, type=boolean),
	participationAttributeType=@AttributeType(multiValued=true, type=AnnotationInstanceOrModifier)
)
class JavaInverseAbstractModifierOperator extends JavaAbstractModifierOperator {

	/**
	 * @param executionState the reactions execution state
	 */
	new(ReactionExecutionState executionState, AnnotableAndModifiable javaModifiable) {
		super(executionState, javaModifiable)
	}

	override applyTowardsCommonality(List<AnnotationInstanceOrModifier> modifiers) {
		val modifierPresent = applyTowardsCommonality(modifiers)
		assertTrue(modifierPresent !== null)
		return !modifierPresent
	}

	override applyTowardsParticipation(Boolean modifierAbsent) {
		assertTrue(modifierAbsent !== null)
		return super.applyTowardsParticipation(!modifierAbsent)
	}
}

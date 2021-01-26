package tools.vitruv.applications.cbs.commonalities.java.operators

import java.util.List
import org.apache.log4j.Logger
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier
import tools.vitruv.applications.cbs.commonalities.oo.Visibility
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static com.google.common.base.Preconditions.*
import static tools.vitruv.framework.util.XtendAssertHelper.*

import static extension tools.vitruv.applications.cbs.commonalities.java.JavaVisibilityHelper.*
import static extension tools.vitruv.domains.java.util.JavaModifierUtil.*

@AttributeMappingOperator(
	name='javaVisibility',
	commonalityAttributeType=@AttributeType(multiValued=false, type=String),
	participationAttributeType=@AttributeType(multiValued=true, type=AnnotationInstanceOrModifier)
)
class JavaVisibilityOperator extends AbstractAttributeMappingOperator<String, List<AnnotationInstanceOrModifier>> {

	static val Logger logger = Logger.getLogger(JavaVisibilityOperator)

	val AnnotableAndModifiable javaModifiable

	/**
	 * @param executionState the reactions execution state
	 */
	new(ReactionExecutionState executionState, AnnotableAndModifiable javaModifiable) {
		super(executionState)
		checkNotNull(javaModifiable, "javaModifiable is null")
		this.javaModifiable = javaModifiable
	}

	// modifiers -> Visibility name
	override applyTowardsCommonality(List<AnnotationInstanceOrModifier> modifiers) {
		val visibility = modifiers.visibility
		assertTrue(visibility !== null)
		val visibilityName = visibility.name
		logger.debug('''Mapping Java modifiers «modifiers.modifierNames» to visibility «visibilityName».''')
		return visibilityName
	}

	// Visibility name -> modifiers
	override applyTowardsParticipation(String visibilityName) {
		val visibility = Visibility.byName(visibilityName) // can be null
		val currentModifiers = javaModifiable.annotationsAndModifiers
		val newModifiers = currentModifiers.visibility = visibility
		logger.debug('''Mapping visibility «visibility» and current Java modifiers «currentModifiers.modifierNames
			» to new modifiers «newModifiers.modifierNames».''')
		return newModifiers
	}
}

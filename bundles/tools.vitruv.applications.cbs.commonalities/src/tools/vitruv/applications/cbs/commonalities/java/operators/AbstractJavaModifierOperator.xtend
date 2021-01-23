package tools.vitruv.applications.cbs.commonalities.java.operators

import java.util.ArrayList
import java.util.List
import org.apache.log4j.Logger
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier
import org.emftext.language.java.modifiers.Modifier
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static com.google.common.base.Preconditions.*

/**
 * Base class for Java modifier operators.
 */
package abstract class AbstractJavaModifierOperator extends AbstractAttributeMappingOperator<Boolean, List<AnnotationInstanceOrModifier>> {

	static val Logger logger = Logger.getLogger(AbstractJavaModifierOperator)

	val AnnotableAndModifiable javaModifiable

	/**
	 * @param executionState the reactions execution state
	 */
	new(ReactionExecutionState executionState, AnnotableAndModifiable javaModifiable) {
		super(executionState)
		checkNotNull(javaModifiable, "javaModifiable is null")
		this.javaModifiable = javaModifiable
	}

	protected abstract def String getModifierName()

	protected abstract def boolean isRelevantModifier(Modifier modifier)

	private def boolean isRelevantModifier(AnnotationInstanceOrModifier modifier) {
		return modifier instanceof Modifier && (modifier as Modifier).isRelevantModifier
	}

	private def boolean containsRelevantModifier(Iterable<? extends AnnotationInstanceOrModifier> modifiers) {
		return !modifiers.filter[isRelevantModifier].isEmpty
	}

	protected abstract def Modifier createModifier()

	private static def List<String> getModifierNames(Iterable<? extends AnnotationInstanceOrModifier> modifiers) {
		return modifiers.map[it.class.name].toList
	}

	// modifiers -> boolean modifier flag
	override applyTowardsCommonality(List<AnnotationInstanceOrModifier> modifiers) {
		val modifierPresent = modifiers.containsRelevantModifier
		logger.debug('''Mapping Java modifiers «modifiers.modifierNames» to boolean flag «
			modifierPresent» for modifier '«modifierName»'.''')
		return modifierPresent
	}

	// boolean modifier flag -> modifiers
	override applyTowardsParticipation(Boolean modifierPresent) {
		val currentModifiers = javaModifiable.annotationsAndModifiers
		var List<AnnotationInstanceOrModifier> newModifiers = new ArrayList(currentModifiers)
		if (modifierPresent) {
			if (!currentModifiers.containsRelevantModifier) {
				newModifiers += createModifier()
			} // Else: Already contains the relevant modifier -> No changes required.
		} else {
			newModifiers.removeIf[isRelevantModifier]
		}
		logger.debug('''Mapping boolean flag «modifierPresent» for modifier '«modifierName»' and current Java modifiers «
			currentModifiers.modifierNames» to new modifiers «newModifiers.modifierNames».''')
		return newModifiers
	}
}

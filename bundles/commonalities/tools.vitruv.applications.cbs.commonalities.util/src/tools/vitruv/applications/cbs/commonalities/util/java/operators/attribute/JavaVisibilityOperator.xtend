package tools.vitruv.applications.cbs.commonalities.util.java.operators.attribute

import java.util.ArrayList
import java.util.List
import java.util.Objects
import org.apache.log4j.Logger
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.modifiers.Private
import org.emftext.language.java.modifiers.Protected
import org.emftext.language.java.modifiers.Public
import tools.vitruv.applications.cbs.commonalities.util.oo.Visibility
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static com.google.common.base.Preconditions.*
import static tools.vitruv.framework.util.XtendAssertHelper.*

@AttributeMappingOperator(
	name='javaVisibility',
	commonalityAttributeType=@AttributeType(multiValued=false, type=String),
	participationAttributeType=@AttributeType(multiValued=true, type=AnnotationInstanceOrModifier)
)
class JavaVisibilityOperator extends AbstractAttributeMappingOperator<String, List<AnnotationInstanceOrModifier>> {

	static val Logger logger = Logger.getLogger(JavaVisibilityOperator)
	static val List<Class<? extends Modifier>> JAVA_VISIBILITY_MODIFIER_TYPES = #[Public, Protected, Private]

	val AnnotableAndModifiable javaModifiable

	/**
	 * @param executionState the reactions execution state
	 */
	new(ReactionExecutionState executionState, AnnotableAndModifiable javaModifiable) {
		super(executionState)
		checkNotNull(javaModifiable, "javaModifiable is null")
		this.javaModifiable = javaModifiable
	}

	private static def boolean isJavaVisibilityModifier(Modifier modifier) {
		return JAVA_VISIBILITY_MODIFIER_TYPES.exists[isInstance(modifier)]
	}

	// The modifier is expected to be one of the known Java visibility modifiers, or null.
	// Null is mapped to Visibility.PACKAGE.
	// Does not return null.
	private static def Visibility toVisibility(Modifier javaVisibilityModifier) {
		if (javaVisibilityModifier === null) {
			return Visibility.PACKAGE
		}
		switch (javaVisibilityModifier) {
			Public: return Visibility.PUBLIC
			Protected: return Visibility.PROTECTED
			Private: return Visibility.PRIVATE
			default: {
				throw new IllegalArgumentException("Unknown Java visibility modifier: "
					+ javaVisibilityModifier.class.name)
			}
		}
	}

	// Returns null for Visibility.PACKAGE. Returns PUBLIC for visibility 'null'.
	private static def toJavaModifier(Visibility visibility) {
		if (visibility === null) {
			// Elements in Java always have some visibility, we default to public:
			return ModifiersFactory.eINSTANCE.createPublic
		}
		switch (visibility) {
			case PUBLIC: return ModifiersFactory.eINSTANCE.createPublic
			case PROTECTED: return ModifiersFactory.eINSTANCE.createProtected
			case PACKAGE: return null
			case PRIVATE: return ModifiersFactory.eINSTANCE.createPrivate
			default: {
				throw new IllegalStateException("Unhandled Visibility value: " + visibility)
			}
		}
	}

	private static def List<String> getModifierNames(Iterable<? extends AnnotationInstanceOrModifier> modifiers) {
		return modifiers.map[it.class.name].toList
	}

	private static def Iterable<Modifier> getJavaVisibilityModifiers(
		Iterable<? extends AnnotationInstanceOrModifier> modifiers) {
		return modifiers.filter(Modifier).filter[isJavaVisibilityModifier]
	}

	// Note: The modifiers can also be null.
	private static def boolean isEqualModifier(Modifier modifier1, Modifier modifier2) {
		// Comparing their EClasses should be sufficient.
		return Objects.equals(modifier1?.eClass, modifier2?.eClass)
	}

	// modifiers -> Visibility name
	override applyTowardsCommonality(List<AnnotationInstanceOrModifier> modifiers) {
		val visibilityModifiers = modifiers.javaVisibilityModifiers
		if (visibilityModifiers.size > 1) {
			throw new IllegalStateException("Java object has more than one visibility modifier: "
				+ visibilityModifiers.modifierNames)
		}
		var visibilityModifier = visibilityModifiers.head // can be null for package-private
		val visibility = visibilityModifier.toVisibility
		assertTrue(visibility !== null)
		val visibilityName = visibility.name
		logger.debug('''Mapping Java modifiers «modifiers.modifierNames» to visibility «visibilityName».''')
		return visibilityName
	}

	// Visibility name -> modifiers
	override applyTowardsParticipation(String visibilityName) {
		val visibility = Visibility.byName(visibilityName) // can be null
		val newJavaVisibilityModifier = visibility.toJavaModifier // can be null

		val currentModifiers = javaModifiable.annotationsAndModifiers
		var List<AnnotationInstanceOrModifier> newModifiers = new ArrayList(currentModifiers)

		// Remove any no longer required visibility modifiers:
		// Note: If there already exists a modifier which matches our new target visibility modifier, we don't remove
		// and later re-add it. This ensures that the order of the modifiers remains unchanged.
		newModifiers.removeIf [
			it instanceof Modifier && isJavaVisibilityModifier(it as Modifier)
			&& !isEqualModifier(it as Modifier, newJavaVisibilityModifier)
		]

		// Add new visibility modifier (if necessary and not yet present):
		if (newJavaVisibilityModifier !== null // null -> Visibility.PACKAGE -> no modifier required
			&& !newModifiers.javaVisibilityModifiers.exists[isEqualModifier(newJavaVisibilityModifier)]) {
			newModifiers.add(newJavaVisibilityModifier)
		}

		logger.debug('''Mapping visibility «visibility» and current Java modifiers «currentModifiers.modifierNames
			» to new modifiers «newModifiers.modifierNames».''')
		return newModifiers
	}
}

package tools.vitruv.applications.cbs.commonalities.java

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.ArrayList
import java.util.List
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.modifiers.Private
import org.emftext.language.java.modifiers.Protected
import org.emftext.language.java.modifiers.Public
import tools.vitruv.applications.cbs.commonalities.oo.Visibility

import static com.google.common.base.Preconditions.*
import static tools.vitruv.framework.util.XtendAssertHelper.*

import static extension tools.vitruv.domains.java.util.JavaModifierUtil.*

/**
 * Helper class for the mapping between Java's visibility modifiers and
 * {@link Visibility}.
 */
@Utility
class JavaVisibilityHelper {

	/**
	 * Gets the {@link Visibility} from the given Java modifiers.
	 */
	static def getVisibility(List<AnnotationInstanceOrModifier> modifiers) {
		checkNotNull(modifiers, "modifiers is null")
		val visibilityModifiers = modifiers.visibilityModifiers
		if (visibilityModifiers.size > 1) {
			throw new IllegalStateException("Java object has more than one visibility modifier: "
				+ visibilityModifiers.modifierNames)
		}
		var visibilityModifier = visibilityModifiers.head // can be null for package-private
		val visibility = visibilityModifier.toVisibility
		assertTrue(visibility !== null)
		return visibility
	}

	/**
	 * Gets a new List of Java modifiers with the given {@link Visibility}
	 * applied.
	 * <p>
	 * A <code>null</code> visibility is mapped to Public. Package-private
	 * visibility results in the removal of any previous visibility modifier.
	 */
	static def setVisibility(List<AnnotationInstanceOrModifier> previousModifiers, Visibility visibility) {
		checkNotNull(previousModifiers, "previousModifiers is null")
		val newJavaVisibilityModifier = visibility.toJavaModifier // can be null
		val List<AnnotationInstanceOrModifier> newModifiers = new ArrayList(previousModifiers)

		// Remove any no longer required visibility modifiers:
		// Note: If there already exists a modifier which matches our new target visibility modifier, we don't remove
		// and later re-add it. This ensures that the order of the modifiers remains unchanged.
		newModifiers.removeIf [
			isVisibilityModifier(it) && !isEqualModifier(it as Modifier, newJavaVisibilityModifier)
		]

		// Add new visibility modifier (if necessary and not yet present):
		if (newJavaVisibilityModifier !== null // null -> Visibility.PACKAGE -> no modifier required
			&& !newModifiers.visibilityModifiers.exists[isEqualModifier(newJavaVisibilityModifier)]) {
			newModifiers.add(newJavaVisibilityModifier)
		}
		return newModifiers
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
}

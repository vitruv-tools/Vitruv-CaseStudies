package tools.vitruv.applications.cbs.commonalities.uml

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.cbs.commonalities.oo.Visibility

/**
 * Helper class for the mapping between UML's {@link VisibilityKind} and
 * {@link Visibility}.
 */
@Utility
class UmlVisibilityHelper {

	/**
	 * Maps the given UML {@link VisibilityKind} to the corresponding
	 * {@link Visibility}.
	 * <p>
	 * Returns <code>null</code> if the given VisibilityKind is
	 * <code>null</code>.
	 */
	static def Visibility toVisibility(VisibilityKind umlVisibility) {
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

	/**
	 * Maps the given {@link Visibility} to the corresponding UML
	 * {@link VisibilityKind}.
	 * <p>
	 * Returns <code>null</code> if the given Visibility is <code>null</code>.
	 */
	static def toUmlVisibility(Visibility visibility) {
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
}

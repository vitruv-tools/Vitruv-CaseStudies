package tools.vitruv.applications.cbs.operators.uml;

import org.eclipse.uml2.uml.VisibilityKind;

import edu.kit.ipd.sdq.activextendannotations.Utility;
import tools.vitruv.applications.cbs.operators.oo.Visibility;

@Utility
public final class UmlVisibilityHelper {

    private UmlVisibilityHelper() {
        // utility
    }

    public static Visibility toVisibility(VisibilityKind umlVisibility) {
        if (umlVisibility == null) {
            return null;
        }
        switch (umlVisibility) {
            case PUBLIC_LITERAL -> {
                return Visibility.PUBLIC;
            }
            case PROTECTED_LITERAL -> {
                return Visibility.PROTECTED;
            }
            case PACKAGE_LITERAL -> {
                return Visibility.PACKAGE;
            }
            case PRIVATE_LITERAL -> {
                return Visibility.PRIVATE;
            }
            default -> throw new IllegalArgumentException("Unhandled UML visibility kind: " + umlVisibility.getName());
        }
    }

    public static VisibilityKind toUmlVisibility(Visibility visibility) {
        if (visibility == null) {
            return null;
        }
        switch (visibility) {
            case PUBLIC -> {
                return VisibilityKind.PUBLIC_LITERAL;
            }
            case PROTECTED -> {
                return VisibilityKind.PROTECTED_LITERAL;
            }
            case PACKAGE -> {
                return VisibilityKind.PACKAGE_LITERAL;
            }
            case PRIVATE -> {
                return VisibilityKind.PRIVATE_LITERAL;
            }
            default -> throw new IllegalStateException("Unhandled Visibility value: " + visibility);
        }
    }
}
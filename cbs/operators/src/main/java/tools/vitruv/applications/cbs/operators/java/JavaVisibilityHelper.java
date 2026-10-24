package tools.vitruv.applications.cbs.operators.java;

import java.util.ArrayList;
import java.util.List;

import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.modifiers.Private;
import org.emftext.language.java.modifiers.Protected;
import org.emftext.language.java.modifiers.Public;

import static com.google.common.base.Preconditions.checkNotNull;

import edu.kit.ipd.sdq.activextendannotations.Utility;
import tools.vitruv.applications.cbs.operators.oo.Visibility;
import tools.vitruv.applications.util.temporary.java.JavaModifierUtil;
import static tools.vitruv.dsls.commonalities.runtime.helper.XtendAssertHelper.assertTrue;

/**
 * AbstractJavaModifierOperator
 * Helper class for the mapping between Java's visibility modifiers and
 * {@link Visibility}.
 */
@Utility
public final class JavaVisibilityHelper {

    private JavaVisibilityHelper() {
        // Prevent instantiation
    }

    /**
     * Gets the {@link Visibility} from the given Java modifiers.
     * 
     * @param modifiers The list of modifiers to analyze
     * @return The visibility represented by the modifiers
     * @throws IllegalStateException if multiple visibility modifiers are found
     * @throws NullPointerException  if modifiers is null
     */
    public static Visibility getVisibility(List<AnnotationInstanceOrModifier> modifiers) {
        checkNotNull(modifiers, "modifiers is null");
        List<Modifier> visibilityModifiers = (List<Modifier>) JavaModifierUtil.getVisibilityModifiers(modifiers);

        if (visibilityModifiers.size() > 1) {
            throw new IllegalStateException("Java object has more than one visibility modifier: "
                    + JavaModifierUtil.getModifierNames(visibilityModifiers));
        }

        Modifier visibilityModifier = visibilityModifiers.isEmpty() ? null : visibilityModifiers.get(0);
        Visibility visibility = toVisibility(visibilityModifier);
        assertTrue(visibility != null);
        return visibility;
    }

    /**
     * Gets a new List of Java modifiers with the given {@link Visibility} applied.
     * <p>
     * A <code>null</code> visibility is mapped to Public. Package-private
     * visibility results in the removal of any previous visibility modifier.
     * 
     * @param previousModifiers The existing modifiers
     * @param visibility        The visibility to apply
     * @return New list with updated visibility modifiers
     * @throws NullPointerException if previousModifiers is null
     */
    public static List<AnnotationInstanceOrModifier> setVisibility(
            List<AnnotationInstanceOrModifier> previousModifiers,
            Visibility visibility) {

        checkNotNull(previousModifiers, "previousModifiers is null");
        final Modifier newJavaVisibilityModifier = toJavaModifier(visibility);
        final List<AnnotationInstanceOrModifier> newModifiers = new ArrayList<>(previousModifiers);

        // Remove any no longer required visibility modifiers
        newModifiers.removeIf(modifier -> JavaModifierUtil.isVisibilityModifier(modifier) &&
                !JavaModifierUtil.isEqualModifier((Modifier) modifier, newJavaVisibilityModifier));

        // Add new visibility modifier if needed
        if (newJavaVisibilityModifier != null) {
            boolean alreadyHas = false;
            for (Modifier m : JavaModifierUtil.getVisibilityModifiers(newModifiers)) {
                if (JavaModifierUtil.isEqualModifier(m, newJavaVisibilityModifier)) {
                    alreadyHas = true;
                    break;
                }
            }
            if (!alreadyHas) {
                newModifiers.add(newJavaVisibilityModifier);
            }
        }
        return newModifiers;
    }

    /**
     * Converts a Java modifier to a Visibility enum value.
     * The modifier is expected to be one of the known Java visibility modifiers, or
     * null.
     * Null is mapped to Visibility.PACKAGE.
     */
    private static Visibility toVisibility(Modifier javaVisibilityModifier) {
        if (javaVisibilityModifier == null) {
            return Visibility.PACKAGE;
        }

        if (javaVisibilityModifier instanceof Public) {
            return Visibility.PUBLIC;
        } else if (javaVisibilityModifier instanceof Protected) {
            return Visibility.PROTECTED;
        } else if (javaVisibilityModifier instanceof Private) {
            return Visibility.PRIVATE;
        } else {
            throw new IllegalArgumentException("Unknown Java visibility modifier: "
                    + javaVisibilityModifier.getClass().getName());
        }
    }

    /**
     * Converts a Visibility enum value to a Java modifier.
     * Returns null for Visibility.PACKAGE. Returns PUBLIC for visibility 'null'.
     */
    private static Modifier toJavaModifier(Visibility visibility) {
        if (visibility == null) {
            // Elements in Java always have some visibility, we default to public
            return ModifiersFactory.eINSTANCE.createPublic();
        }

        return switch (visibility) {
            case PUBLIC -> ModifiersFactory.eINSTANCE.createPublic();
            case PROTECTED -> ModifiersFactory.eINSTANCE.createProtected();
            case PACKAGE -> null;
            case PRIVATE -> ModifiersFactory.eINSTANCE.createPrivate();
            default -> throw new IllegalStateException("Unhandled Visibility value: " + visibility);
        };
    }
}
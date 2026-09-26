package tools.vitruv.applications.util.temporary.java;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.activextendannotations.Utility;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.modifiers.Private;
import org.emftext.language.java.modifiers.Protected;
import org.emftext.language.java.modifiers.Public;

@Utility
@SuppressWarnings("all")
public final class JavaModifierUtil {
    private static final Logger logger = Logger.getLogger(JavaModifierUtil.class.getSimpleName());

    private static final List<Class<? extends Modifier>> JAVA_VISIBILITY_MODIFIER_TYPES =
            Collections.unmodifiableList(CollectionLiterals.newArrayList(Public.class, Protected.class, Private.class));

    public static boolean isVisibilityModifier(final Modifier modifier) {
        return IterableExtensions.exists(JAVA_VISIBILITY_MODIFIER_TYPES, it -> it.isInstance(modifier));
    }

    public static boolean isVisibilityModifier(final AnnotationInstanceOrModifier modifier) {
        return (modifier instanceof Modifier) && isVisibilityModifier((Modifier) modifier);
    }

    public static Iterable<Modifier> getVisibilityModifiers(final Iterable<? extends AnnotationInstanceOrModifier> modifiers) {
        return IterableExtensions.filter(Iterables.filter(modifiers, Modifier.class), JavaModifierUtil::isVisibilityModifier);
    }

    public static List<String> getModifierNames(final Iterable<? extends AnnotationInstanceOrModifier> modifiers) {
        return IterableExtensions.toList(IterableExtensions.map(modifiers, it -> it.getClass().getName()));
    }

    public static boolean isEqualModifier(final Modifier modifier1, final Modifier modifier2) {
        EClass eClass1 = modifier1 != null ? modifier1.eClass() : null;
        EClass eClass2 = modifier2 != null ? modifier2.eClass() : null;
        return Objects.equals(eClass1, eClass2);
    }

    public static void setJavaVisibilityModifier(final AnnotableAndModifiable modifiable, final JavaVisibility visibility) {
        if (Objects.equals(visibility, JavaVisibility.PACKAGE)) {
            removeJavaVisibilityModifiers(modifiable);
        } else {
            final Modifier visibilityModifier = getJavaVisibilityModifierFromEnum(visibility);
            if (visibilityModifier != null) {
                if (!modifiable.hasModifier(visibilityModifier.getClass())) {
                    removeJavaVisibilityModifiers(modifiable);
                    setJavaModifier(modifiable, visibilityModifier, true);
                }
            } else {
                logger.warn("No corresponding Java-Visibility-Modifier found for " + visibility);
            }
        }
    }

    public static Modifier getJavaVisibilityModifierFromEnum(final JavaVisibility visibility) {
        if (visibility == null) {
            throw new IllegalArgumentException("Invalid Visibility: null");
        }
        switch (visibility) {
            case PUBLIC:
                return ModifiersFactory.eINSTANCE.createPublic();
            case PRIVATE:
                return ModifiersFactory.eINSTANCE.createPrivate();
            case PROTECTED:
                return ModifiersFactory.eINSTANCE.createProtected();
            case PACKAGE:
                return null;
            default:
                throw new IllegalArgumentException("Invalid Visibility: " + visibility);
        }
    }

    public static JavaVisibility getEnumConstantFromJavaVisibility(final Modifier modifier) {
        if (modifier == null) {
            return JavaVisibility.PACKAGE;
        }
        String name = modifier.eClass().getName();
        if (Objects.equals(name, Private.class.getSimpleName())) {
            return JavaVisibility.PRIVATE;
        } else if (Objects.equals(name, Protected.class.getSimpleName())) {
            return JavaVisibility.PROTECTED;
        } else if (Objects.equals(name, Public.class.getSimpleName())) {
            return JavaVisibility.PUBLIC;
        } else {
            throw new IllegalArgumentException("Invalid VisibilityModifier: " + modifier);
        }
    }

    public static void setJavaModifier(final AnnotableAndModifiable jModifiable, final Modifier modifier, final boolean add) {
        if (add) {
            if (!jModifiable.hasModifier(modifier.getClass())) {
                jModifiable.addModifier(modifier);
            } else {
                logger.warn("The Java AnnotableAndModifiable " + jModifiable.getClass() + " already has the modifier " + modifier.getClass());
            }
        } else {
            jModifiable.removeModifier(modifier.getClass());
        }
    }

    public static void addModifierIfNotNull(final AnnotableAndModifiable jModifiable, final Modifier mod) {
        if (mod != null) {
            setJavaModifier(jModifiable, mod, true);
        }
    }

    public static void setName(final NamedElement namedElement, final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Cannot set name of " + namedElement + " to null");
        }
        namedElement.setName(name);
    }

    public static void setFinal(final AnnotableAndModifiable modifiable, final boolean toAdd) {
        setJavaModifier(modifiable, ModifiersFactory.eINSTANCE.createFinal(), toAdd);
    }

    public static void setAbstract(final AnnotableAndModifiable modifiable, final boolean toAdd) {
        setJavaModifier(modifiable, ModifiersFactory.eINSTANCE.createAbstract(), toAdd);
    }

    public static void setStatic(final AnnotableAndModifiable modifiable, final boolean toAdd) {
        setJavaModifier(modifiable, ModifiersFactory.eINSTANCE.createStatic(), toAdd);
    }

    public static void removeJavaVisibilityModifiers(final AnnotableAndModifiable modifiable) {
        if (modifiable.hasModifier(Private.class)) {
            modifiable.removeModifier(Private.class);
        }
        if (modifiable.hasModifier(Protected.class)) {
            modifiable.removeModifier(Protected.class);
        }
        if (modifiable.hasModifier(Public.class)) {
            modifiable.removeModifier(Public.class);
        }
    }

    public static JavaVisibility getJavaVisibilityConstantFromUmlVisibilityKind(final VisibilityKind uVisibility) {
        if (uVisibility == null) {
            throw new IllegalArgumentException("Unknown VisibilityKind: null");
        }
        switch (uVisibility) {
            case PUBLIC_LITERAL:
                return JavaVisibility.PUBLIC;
            case PROTECTED_LITERAL:
                return JavaVisibility.PROTECTED;
            case PACKAGE_LITERAL:
                return JavaVisibility.PACKAGE;
            case PRIVATE_LITERAL:
                return JavaVisibility.PRIVATE;
            default:
                throw new IllegalArgumentException("Unknown VisibilityKind: " + uVisibility);
        }
    }

    public static VisibilityKind getUmlVisibilityKindFromJavaVisibilityConstant(final JavaVisibility jVisibility) {
        if (jVisibility == null) {
            throw new IllegalArgumentException("Unknown Java-Visibility: null");
        }
        switch (jVisibility) {
            case PUBLIC:
                return VisibilityKind.PUBLIC_LITERAL;
            case PROTECTED:
                return VisibilityKind.PROTECTED_LITERAL;
            case PACKAGE:
                return VisibilityKind.PACKAGE_LITERAL;
            case PRIVATE:
                return VisibilityKind.PRIVATE_LITERAL;
            default:
                throw new IllegalArgumentException("Unknown Java-Visibility: " + jVisibility);
        }
    }

    public static VisibilityKind getUmlVisibilityKindFromJavaElement(final AnnotableAndModifiable javaElement) {
        EList<AnnotationInstanceOrModifier> modifiers = javaElement.getAnnotationsAndModifiers();
        for (AnnotationInstanceOrModifier modifier : modifiers) {
            if (modifier instanceof Public) {
                return VisibilityKind.PUBLIC_LITERAL;
            } else if (modifier instanceof Protected) {
                return VisibilityKind.PROTECTED_LITERAL;
            } else if (modifier instanceof Private) {
                return VisibilityKind.PRIVATE_LITERAL;
            }
        }
        return VisibilityKind.PACKAGE_LITERAL;
    }

    public static void setJavaVisibility(final AnnotableAndModifiable jModifiable, final VisibilityKind uVisibility) {
        setJavaVisibilityModifier(jModifiable, getJavaVisibilityConstantFromUmlVisibilityKind(uVisibility));
    }

    private JavaModifierUtil() {}
}

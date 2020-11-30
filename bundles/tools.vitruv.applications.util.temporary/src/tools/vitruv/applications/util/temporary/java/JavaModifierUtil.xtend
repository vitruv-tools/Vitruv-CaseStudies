package tools.vitruv.applications.util.temporary.java

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.apache.log4j.Logger
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.commons.NamedElement
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.modifiers.Private
import org.emftext.language.java.modifiers.Protected
import org.emftext.language.java.modifiers.Public

/**
 * Util class for java modifiers and visibilities.
 * 
 * @author Fei
 */
@Utility
class JavaModifierUtil {

    static val logger = Logger.getLogger(JavaModifierUtil.simpleName)

    /**
     * Sets for the modifiable the java visibility modifier corresponding to the given JavaVisibility enum constant.
     * If visibility is JavaVisibility.PACKAGE, all visibility modifiers will be removed from the
     * modifiable.
     * 
     * @param modifiable the AnnotableAndModifiable for which the visibility should be set
     * @param visibility the java visibility to set
     * 
     */
    def static setJavaVisibilityModifier(AnnotableAndModifiable modifiable, JavaVisibility visibility) {
        if (visibility == JavaVisibility.PACKAGE) {
            removeJavaVisibilityModifiers(modifiable)
        } else {
            val visibilityModifier = getJavaVisibilityModifierFromEnum(visibility)
            if (visibilityModifier !== null) {
                if (!modifiable.hasModifier(visibilityModifier.class)) {
                    removeJavaVisibilityModifiers(modifiable)
                    setJavaModifier(modifiable, visibilityModifier, true)
                }
            } else {
                logger.warn("No corresponding Java-Visibility-Modifier found for " + visibility)
            }
        }
    }

    /**
     * Returns null if visibility is JavaVisibility.PACKAGE.
     * 
     * @return the Java Modifier corresponding to the JavaVisibility enum constant.
     * @throws IllegalArgumentException if visibility is invalid
     */
    def static getJavaVisibilityModifierFromEnum(JavaVisibility visibility) {
        switch visibility {
            case JavaVisibility.PUBLIC: return ModifiersFactory.eINSTANCE.createPublic
            case JavaVisibility.PRIVATE: return ModifiersFactory.eINSTANCE.createPrivate
            case JavaVisibility.PROTECTED: return ModifiersFactory.eINSTANCE.createProtected
            case JavaVisibility.PACKAGE: return null
            default: throw new IllegalArgumentException("Invalid Visibility: " + visibility)
        }
    }

    /**
     * Returns the corresponding JavaVisibility enum constant of the given
     * modifier. If modifier is null, it will return JavaVisibility.PACKAGE.
     * 
     * @return the corresponding JavaVisibility enum constant
     * @throws IllegalArgumentException if modifier is not a visibility modifier
     */
    def static JavaVisibility getEnumConstantFromJavaVisibility(Modifier modifier) {
        if (modifier === null) {
            return JavaVisibility.PROTECTED
        }
        switch (modifier.eClass.name) {
            case Private.simpleName: return JavaVisibility.PRIVATE
            case Protected.simpleName: return JavaVisibility.PROTECTED
            case Public.simpleName: return JavaVisibility.PUBLIC
            default: throw new IllegalArgumentException("Invalid VisibilityModifier: " + modifier)
        }
    }

    /**
     * Adds modifier to jModifiable if add is true.
     * Otherwise it will remove all instances of the modifier's class from jModifiable.
     * 
     * @param jModifiable the AnnotableAndModifiable for which the modifier should be added or removed
     * @param the modifier to add or remove
     * @param add true to add the modifier, else remove
     */
    def static setJavaModifier(AnnotableAndModifiable jModifiable, Modifier modifier, boolean add) {
        if (add) {
            if (!jModifiable.hasModifier(modifier.class)) {
                jModifiable.addModifier(modifier)
            } else {
                logger.warn("The Java AnnotableAndModifiable " + jModifiable.class + " already has the modifier " + modifier.class)
            }
        } else {
            jModifiable.removeModifier(modifier.class)
        }
    }

    /**
     * Adds mod to jModifiable if mod is not null.
     * 
     * @param jModifiable the AnnotableAndMidifiable
     * @param mod the modifier to add
     */
    def static addModifierIfNotNull(AnnotableAndModifiable jModifiable, Modifier mod) {
        if (mod !== null) {
            setJavaModifier(jModifiable, mod, true)
        }
    }

    /**
     * Sets the name of namedElement to name if name is not null.
     * 
     * @param namedElment the NamedElement
     * @param the name to set
     * @throws IllegalArgumentException if name is null
     */
    def static setName(NamedElement namedElement, String name) {
        if (name === null) {
            throw new IllegalArgumentException("Cannot set name of " + namedElement + " to null")
        }
        namedElement.name = name
    }

    /**
     * Adds a final modifier to modifiable, if toAdd is true.
     * Otherwise it removes all final modifiers from modifiable.
     * 
     * @param modifiable the AnnotableAndModifiable
     * @param toAdd true to add the modifier, otherwise remove
     */
    def static setFinal(AnnotableAndModifiable modifiable, boolean toAdd) {
        setJavaModifier(modifiable, ModifiersFactory.eINSTANCE.createFinal, toAdd)
    }

    /**
     * Adds a abstract modifier to modifiable, if toAdd is true.
     * Otherwise it removes all abstract modifiers from modifiable.
     * 
     * @param modifiable the AnnotableAndModifiable
     * @param toAdd true to add the modifier, otherwise remove
     */
    def static setAbstract(AnnotableAndModifiable modifiable, boolean toAdd) {
        setJavaModifier(modifiable, ModifiersFactory.eINSTANCE.createAbstract, toAdd)
    }

    /**
     * Adds a static modifier to modifiable, if toAdd is true.
     * Otherwise it removes all static modifiers from modifiable.
     * 
     * @param modifiable the AnnotableAndModifiable
     * @param toAdd true to add the modifier, otherwise remove
     */
    def static setStatic(AnnotableAndModifiable modifiable, boolean toAdd) {
        setJavaModifier(modifiable, ModifiersFactory.eINSTANCE.createStatic, toAdd)
    }

    /**
     * Removes all Private, Public and Protected modifiers from a modifiable.
     * 
     * @param modifiable the AnnotableAndModifiable from which all visibility modifiers should be removed
     */
    def static removeJavaVisibilityModifiers(AnnotableAndModifiable modifiable) {
        if (modifiable.hasModifier(typeof(Private))) modifiable.removeModifier(typeof(Private))
        if (modifiable.hasModifier(typeof(Protected))) modifiable.removeModifier(typeof(Protected))
        if (modifiable.hasModifier(typeof(Public))) modifiable.removeModifier(typeof(Public))
    }

    /**
     * Returns the corresponding JavaVisibility enum constant corresponding to
     * the VisibilityKind enum constant.
     */
    def static getJavaVisibilityConstantFromUmlVisibilityKind(VisibilityKind uVisibility) {
        switch (uVisibility) {
            case VisibilityKind.PUBLIC_LITERAL: return JavaVisibility.PUBLIC
            case VisibilityKind.PROTECTED_LITERAL: return JavaVisibility.PROTECTED
            case VisibilityKind.PACKAGE_LITERAL: return JavaVisibility.PACKAGE
            case VisibilityKind.PRIVATE_LITERAL: return JavaVisibility.PRIVATE
            default: throw new IllegalArgumentException("Unknown VisibilityKind: " + uVisibility)
        }
    }

    /**
     * Returns the corresponding VisibilityKind enum constant corresponding to
     * the JavaVisibility enum constant. This is the reverse function of
     * {@link #getJavaVisibilityConstantFromUmlVisibilityKind(VisibilityKind)}
     */
    def static getUmlVisibilityKindFromJavaVisibilityConstant(JavaVisibility jVisibility) {
        switch (jVisibility) {
            case JavaVisibility.PUBLIC: return VisibilityKind.PUBLIC_LITERAL
            case JavaVisibility.PROTECTED: return VisibilityKind.PROTECTED_LITERAL
            case JavaVisibility.PACKAGE: return VisibilityKind.PACKAGE_LITERAL
            case JavaVisibility.PRIVATE: return VisibilityKind.PRIVATE_LITERAL
            default: throw new IllegalArgumentException("Unknown Java-Visibility: " + jVisibility)
        }
    }

    /**
     * Returns the corresponding UMLVisibility enum constant corresponding to
     * the given java visibility modifier
     */
    def static getUMLVisibilityKindFromJavaModifier(Modifier visibilityModifier) {
        return getUmlVisibilityKindFromJavaVisibilityConstant(getEnumConstantFromJavaVisibility(visibilityModifier))
    }

    /**
     * Sets the java visibility modifier corresponding to uVisibility to jModifiable
     * 
     * @param jModifiable the AnnotableAndModifiable for which a java visibility modifier should be set
     * @param uVisibility the VisibilityKind
     */
    def static setJavaVisibility(AnnotableAndModifiable jModifiable, VisibilityKind uVisibility) {
        setJavaVisibilityModifier(jModifiable, getJavaVisibilityConstantFromUmlVisibilityKind(uVisibility))
    }
}

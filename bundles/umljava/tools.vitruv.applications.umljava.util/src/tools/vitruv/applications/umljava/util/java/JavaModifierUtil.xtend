package tools.vitruv.applications.umljava.util.java

import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.commons.NamedElement
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.modifiers.Private
import org.emftext.language.java.modifiers.Protected
import org.emftext.language.java.modifiers.Public
import org.apache.log4j.Logger

class JavaModifierUtil {
    private static val logger = Logger.getLogger(JavaModifierUtil.simpleName)
    private new() {}
    
    def static void setJavaVisibilityModifier(AnnotableAndModifiable modifiable, JavaVisibility visibility) {
        if (visibility == JavaVisibility.PACKAGE) {
            removeJavaVisibilityModifiers(modifiable)
        } else {
            val visibilityModifier = getJavaVisibilityModifierFromEnum(visibility)
            if (visibilityModifier !== null) {
                removeJavaVisibilityModifiers(modifiable)
                setJavaModifier(modifiable, visibilityModifier, true)
            } else {
                logger.warn("No corresponding Java-Visibility-Modifier found for " + visibility)
            }
        }
    }
    
    /**
     * Returns the Java Modifier corresponding to the JavaVisibility-Enum Constant.
     * Returns null if visibility is JavaVisibility.PACKAGE.
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
    
    def static JavaVisibility getEnumConstantFromJavaVisibility(Class<? extends Modifier> modifier) {
        
    }
    
    /**
     * @param add true -> add, else remove
     */
    def static setJavaModifier(AnnotableAndModifiable jModifiable, Modifier mod, boolean add) {
        if (add) {
            if (!jModifiable.hasModifier(mod.class)) {
                jModifiable.addModifier(mod)  
            } else {
                logger.warn("The Java AnnotableAndModifiable " + jModifiable.class + " already has the modifier " + mod.class)
            }
        } else {
            jModifiable.removeModifier(mod.class)
        }
    }
    
    /**
     * Adds mod to jModifiable if mod is not null.
     */
    def static addModifierIfNotNull(AnnotableAndModifiable jModifiable, Modifier mod) {
        if (mod !== null) {
            setJavaModifier(jModifiable, mod, true)
        }
    }
    
    def static void setName(NamedElement namedElement, String name) {
        if (name === null) {
            throw new IllegalArgumentException("Cannot set name of " + namedElement + " to null")
        }
        namedElement.name = name
    }
    
    def static void setFinal(AnnotableAndModifiable modifiable, boolean toAdd) {
        setJavaModifier(modifiable, ModifiersFactory.eINSTANCE.createFinal, toAdd)
    }
    
    def static void setAbstract(AnnotableAndModifiable modifiable, boolean toAdd) {
        setJavaModifier(modifiable, ModifiersFactory.eINSTANCE.createAbstract, toAdd)
    }
    
    def static void setStatic(AnnotableAndModifiable modifiable, boolean toAdd) {
        setJavaModifier(modifiable, ModifiersFactory.eINSTANCE.createStatic, toAdd)
    }
    
    /**
     * Removes all Private, Public and Protected modifiers from a modifiable.
     */
    def static removeJavaVisibilityModifiers(AnnotableAndModifiable modifiable) {
        modifiable.removeModifier(typeof(Private))
        modifiable.removeModifier(typeof(Protected))
        modifiable.removeModifier(typeof(Public))
    }
    
    /**
     * Removes a modifier from a modifiable
     */
    def static <T extends Modifier> removeJavaModifier(AnnotableAndModifiable modifiable, Class<T> modifier ) {
        modifiable.removeModifier(modifier)
    }
   
    def static getJavaVisibilityConstantFromUmlVisibilityKind(VisibilityKind uVisibility) {
        switch (uVisibility) {
            case VisibilityKind.PUBLIC_LITERAL: return JavaVisibility.PUBLIC
            case VisibilityKind.PROTECTED_LITERAL: return JavaVisibility.PROTECTED
            case VisibilityKind.PACKAGE_LITERAL: return JavaVisibility.PACKAGE
            case VisibilityKind.PRIVATE_LITERAL: return JavaVisibility.PRIVATE
            default: throw new IllegalArgumentException("Unknown VisibilityKind: " + uVisibility)
        }
    }
}
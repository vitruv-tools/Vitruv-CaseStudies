package tools.vitruv.applications.umljava.java2uml

import org.emftext.language.java.modifiers.Modifier
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.modifiers.Private
import org.emftext.language.java.modifiers.Protected
import org.emftext.language.java.modifiers.Public

class JavaToUmlHelper {
    
    def static VisibilityKind getUmlVisibilityKind(Modifier mod) {
        if (mod == null) {
            throw new IllegalArgumentException("Invalid VisibilityModifier null")
        }
        switch mod.class {
            case Private: return VisibilityKind.PRIVATE_LITERAL
            case Protected: return VisibilityKind.PROTECTED_LITERAL
            case Public: return VisibilityKind.PUBLIC_LITERAL
            default: throw new IllegalArgumentException("Invalid VisibilityModifier: " + mod.class)
        }
    }
}
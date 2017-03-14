package tools.vitruv.applications.umljava.java2uml

import org.emftext.language.java.modifiers.Modifier
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.modifiers.Private
import org.emftext.language.java.modifiers.Protected
import org.emftext.language.java.modifiers.Public
import org.eclipse.uml2.uml.Type
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.Void
import org.eclipse.uml2.uml.Model
import tools.vitruv.framework.correspondence.CorrespondenceModel
import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import java.util.Set
import org.eclipse.uml2.uml.UMLFactory

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
    
    def static Type getUmlType(TypeReference jType) {
        if (jType.target instanceof Void) {
            return null;
        }
    }
    
    def static Model getUmlModel(CorrespondenceModel correspondenceModel) {
        val Set<Model> models = correspondenceModel.getAllEObjectsOfTypeInCorrespondences(Model)
        if (models.nullOrEmpty) {
           return UMLFactory.eINSTANCE.createModel();
        }
        if (1 != models.size) {
            //log.warn("found more than one repository. Returning the first")
        }
        return models.get(0)
    }
}
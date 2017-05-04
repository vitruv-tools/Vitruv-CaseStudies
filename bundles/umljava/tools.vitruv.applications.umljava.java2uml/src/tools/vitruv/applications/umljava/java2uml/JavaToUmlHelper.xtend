package tools.vitruv.applications.umljava.java2uml

import java.util.Set
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.Private
import org.emftext.language.java.modifiers.Protected
import org.emftext.language.java.modifiers.Public
import org.emftext.language.java.types.Boolean
import org.emftext.language.java.types.Byte
import org.emftext.language.java.types.Char
import org.emftext.language.java.types.Double
import org.emftext.language.java.types.Float
import org.emftext.language.java.types.Int
import org.emftext.language.java.types.Long
import org.emftext.language.java.types.Short
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.Void
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.applications.umljava.util.java.JavaTypeUtil;


import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import tools.vitruv.framework.userinteraction.UserInteracting
import org.apache.log4j.Logger

class JavaToUmlHelper {
    private static val logger = Logger.getLogger(JavaToUmlHelper.simpleName)
    private static val ROOTMODELDIRECTORY = "model"
    private static val DEFAULTMODELNAME = "model"
    private static var MODELNAME = DEFAULTMODELNAME
    private static val MODELNAME_INPUTMESSAGE = "Please enter a name for the uml root model"
    public static val DEFAULT_INTERFACEREALIZATION_NAME = "DefaultInterfaceRealizationName"
    
    /**
     * @param vis Wenn null, return package-private
     */
    def static VisibilityKind getUmlVisibilityKind(Modifier vis) {
        if (vis == null) {
            return VisibilityKind.PACKAGE_LITERAL 
        }
        switch vis.eClass.name {
            case Private.simpleName: return VisibilityKind.PRIVATE_LITERAL
            case Protected.simpleName: return VisibilityKind.PROTECTED_LITERAL
            case Public.simpleName: return VisibilityKind.PUBLIC_LITERAL
            default: throw new IllegalArgumentException("Invalid VisibilityModifier: " + vis)
        }
    }
    def static getRootModelFile() {
    	return ROOTMODELDIRECTORY + "/" + MODELNAME + ".uml"
    }
    def static Type getUmlType(TypeReference jType, org.eclipse.uml2.uml.Class customType, Model model) {
        if (jType == null || jType instanceof Void) {
            return null
        }
        if (customType != null) {
            return customType
        }
        val primType = UMLFactory.eINSTANCE.createPrimitiveType()
        switch jType {
            Void : return null
            Int : primType.name = JavaTypeUtil.INT
            Boolean: primType.name = JavaTypeUtil.BOOLEAN
            Byte : primType.name = JavaTypeUtil.BYTE
            Long : primType.name = JavaTypeUtil.LONG
            Double : primType.name = JavaTypeUtil.DOUBLE
            Short : primType.name = JavaTypeUtil.SHORT
            Float : primType.name = JavaTypeUtil.FLOAT
            Char : primType.name = JavaTypeUtil.CHAR
            default: throw new IllegalArgumentException("no corresponding uml-primitiveType: for java TypeReference: " + jType.target)
        }
        model.packagedElements += primType;
        println("primType: "+primType.name);
        return primType;
    }
    
    def static Model getUmlModel(CorrespondenceModel correspondenceModel, UserInteracting userInteracting) {
        val Set<Model> models = correspondenceModel.getAllEObjectsOfTypeInCorrespondences(Model)
        if (models.nullOrEmpty) {
			val model = UMLFactory.eINSTANCE.createModel();
			model.name = DEFAULTMODELNAME;
			 
			val userModelName = userInteracting.getTextInput(MODELNAME_INPUTMESSAGE)
			if (userModelName.nullOrEmpty) {
				model.name = DEFAULTMODELNAME;
			} else {
				MODELNAME = userModelName
				model.name = userModelName
			}
            //We add a correspondence of the model with itself to save it in the correspondence model
			correspondenceModel.createAndAddCorrespondence(model, model)
			return model;
        }
        if (1 != models.size) {
            logger.warn("found more than one repository. Returning the first")
        }
        return models.get(0)
    }
}
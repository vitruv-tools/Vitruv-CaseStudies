package tools.vitruv.applications.umljava.java2uml


import static tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*
import static tools.vitruv.applications.umljava.util.uml.UmlClassifierAndPackageUtil.*
import static tools.vitruv.applications.umljava.util.java.JavaStandardType.*
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
import org.eclipse.emf.ecore.EObject
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization.UserExecution
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.classifiers.Classifier
import org.apache.log4j.PropertyConfigurator

class JavaToUmlHelper {
    private static val logger = Logger.getLogger(JavaToUmlHelper.simpleName)
    private static var ROOTMODELDIRECTORY = "model"
    private static val DEFAULTMODELNAME = "model"
    private static var MODELNAME = DEFAULTMODELNAME
    private static val MODELNAME_INPUTMESSAGE = "Please enter a name for the uml root model"
    private static val MODELPATH_INPUTMESSAGE = "Please enter a path for the uml root model"
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
    
    def static dispatch Type getUmlType(org.emftext.language.java.types.Void jVoid, Model model, CorrespondenceModel correspondenceModel) {
        return null
    }
    def static dispatch Type getUmlType(java.lang.Void nullReference, Model model, CorrespondenceModel correspondenceModel) {
        return null
    }
    def static dispatch Type getUmlType(org.emftext.language.java.types.PrimitiveType jPrimtype, Model model, CorrespondenceModel correspondenceModel) {
        return findUmlPrimitiveType(getTypeKeyword(jPrimtype), model)
    }
    
    def static dispatch Type getUmlType(NamespaceClassifierReference jNamespaceRef, Model model, CorrespondenceModel correspondenceModel) {
        return getUmlType(getJavaTypeFromTypeReference(jNamespaceRef), model, correspondenceModel)
    }
    
    def static dispatch Type getUmlType(org.emftext.language.java.classifiers.Class jClass, Model model, CorrespondenceModel correspondenceModel) {
        if ("String".equals(jClass.name)) {
            return findUmlPrimitiveType("String", model)
        } else {
            val correspUmltype = findFirstCorrespondeningUmlElementByNameAndType(correspondenceModel, jClass.name, org.eclipse.uml2.uml.Class)
            if (correspUmltype === null) {
                val umlTypeClass = createUmlClass(jClass.name, VisibilityKind.PUBLIC_LITERAL, false, false)
                model.packagedElements += umlTypeClass
                return umlTypeClass
            }
            return correspUmltype
        }
    }
    
    def static dispatch Type getUmlType(org.emftext.language.java.classifiers.Interface jInterface, Model model, CorrespondenceModel correspondenceModel) {
        return findFirstCorrespondeningUmlElementByNameAndType(correspondenceModel, jInterface.name, org.eclipse.uml2.uml.Interface)
    }
    
    def static dispatch Type getUmlType(org.emftext.language.java.classifiers.Enumeration jEnum, Model model, CorrespondenceModel correspondenceModel) {
        return findFirstCorrespondeningUmlElementByNameAndType(correspondenceModel, jEnum.name, org.eclipse.uml2.uml.Enumeration)
    }
    
    def static Model getUmlModel(CorrespondenceModel correspondenceModel, UserInteracting userInteracting) {
        val Set<Model> models = correspondenceModel.getAllEObjectsOfTypeInCorrespondences(Model)
        if (models.nullOrEmpty) {
			val model = UMLFactory.eINSTANCE.createModel();
			model.name = DEFAULTMODELNAME;
			
			val userModelName = userInteracting.getTextInput(MODELNAME_INPUTMESSAGE)
			val userModelPath = userInteracting.getTextInput(MODELPATH_INPUTMESSAGE)
			if (userModelName.nullOrEmpty) {
				model.name = DEFAULTMODELNAME;
			} else {
				MODELNAME = userModelName
				model.name = userModelName
			}
			if (!userModelPath.nullOrEmpty) {
                ROOTMODELDIRECTORY = userModelPath;
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
    
    def static org.eclipse.uml2.uml.Package findUmlPackage(CorrespondenceModel correspondenceModel, String packageName) {
        val Set<org.eclipse.uml2.uml.Package> allPackages = correspondenceModel.getAllEObjectsOfTypeInCorrespondences(org.eclipse.uml2.uml.Package)
        
        val packages = allPackages.filter[name.equals(packageName)]
        if (packages.nullOrEmpty) {
            logger.warn("The UML-Package with the name " + packageName + " does not exist in the correspondence model")
        }
        return packages.head
    }
    
    def static org.eclipse.uml2.uml.PrimitiveType findUmlPrimitiveType(String name, Model umlModel) {
        val primType = umlModel.packagedElements.filter(org.eclipse.uml2.uml.PrimitiveType).filter[it.name.equals(name)]
        if (primType.nullOrEmpty) {
            val newPrimType = createUmlPrimitiveType(name)
            umlModel.packagedElements += newPrimType
            logger.debug("Creating a UML-PrimitiveType with the name " + name)
            return newPrimType
        }
        return primType.head
    }
    
    def static <T extends org.eclipse.uml2.uml.NamedElement> findFirstCorrespondeningUmlElementByNameAndType(CorrespondenceModel correspondenceModel, String name, Class<T> typeOfJavaElement) {
        return correspondenceModel.getAllEObjectsOfTypeInCorrespondences(typeOfJavaElement).filter[it.name.equals(name)].head
    }
    
    def static void showMessage(UserInteracting userinteraction, String message) {
        userinteraction.showMessage(message)
    }
}
package tools.vitruv.applications.umljava.java2uml

import static tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*
import static tools.vitruv.applications.umljava.util.uml.UmlClassifierAndPackageUtil.*
import static tools.vitruv.applications.umljava.util.java.JavaStandardType.*
import java.util.Set
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.framework.correspondence.CorrespondenceModel


import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import tools.vitruv.framework.userinteraction.UserInteracting
import org.apache.log4j.Logger
import org.emftext.language.java.types.NamespaceClassifierReference

/**
 * Helper class for the Java2Uml reactions. Contains functions who depends on
 * the correspondence model or userinteracting and the name and path for the
 * uml rootmodel.
 * 
 * @author Fei
 */
class JavaToUmlHelper {
    private static val logger = Logger.getLogger(JavaToUmlHelper.simpleName)
    private static var ROOTMODELDIRECTORY = "model"
    private static var MODELNAME = "model"
    private static val MODELNAME_INPUTMESSAGE = "Please enter a name for the uml root model"
    private static val MODELPATH_INPUTMESSAGE = "Please enter a path for the uml root model"
    
    /**
     * Returns the path to the uml root model relative to the project root.
     * 
     * @return the path to the uml root model
     */
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
    /**
     * Retrieves a corresponding UML-Classifier that corresponds to the given Java-Class by
     * searching the correspondence model manually by type and name.
     * 
     * @param jClass The Java-Class whose corresponding UML-Type should be retrieved
     * @param model The underlying root model for the
     * @return the corresponding UMLl-Classifier or null if none can be found
     * 
     */
    def static dispatch Type getUmlType(org.emftext.language.java.classifiers.Class jClass, Model model, CorrespondenceModel correspondenceModel) {
        if ("String".equals(jClass.name)) {
            return findUmlPrimitiveType("String", model)
        } else {
            //UML-Classifier, because the jClass can correspond to a UML-DataType
            var correspUmltype = findFirstCorrespondeningUmlElementByNameAndType(correspondenceModel, jClass.name, org.eclipse.uml2.uml.Classifier)
            if (correspUmltype === null) {
                //Check manually added Types
                correspUmltype = model.packagedElements.filter(org.eclipse.uml2.uml.Class).filter[it.name.equals(jClass.name)].head
                if (correspUmltype === null) {
                    val umlTypeClass = createUmlClass(jClass.name, VisibilityKind.PUBLIC_LITERAL, false, false)
                    model.packagedElements += umlTypeClass
                    return umlTypeClass
                }
            }
            return correspUmltype
        }
    }
    
    /**
     * Returns the corresponding UML-Interface or null of none could be found.
     * 
     * @return the corresponding UML-Interface of the given Java-Interface.
     */
    def static dispatch Type getUmlType(org.emftext.language.java.classifiers.Interface jInterface, Model model, CorrespondenceModel correspondenceModel) {
        return findFirstCorrespondeningUmlElementByNameAndType(correspondenceModel, jInterface.name, org.eclipse.uml2.uml.Interface)
    }
    
    def static dispatch Type getUmlType(org.emftext.language.java.classifiers.Enumeration jEnum, Model model, CorrespondenceModel correspondenceModel) {
        return findFirstCorrespondeningUmlElementByNameAndType(correspondenceModel, jEnum.name, org.eclipse.uml2.uml.Enumeration)
    }
    
    /**
     * Returns the (first) uml root model in the correspondence model.
     * Creates a new uml root model and adds it to the correspondence model if
     * none could be found.
     * 
     * @param correspondenceModel the correspondence model that contains /should contain the uml root model
     * @param userInteracting the userinteracting to promt the user if a new uml model must be created
     * @return the uml root model
     */
    def static Model getUmlModel(CorrespondenceModel correspondenceModel, UserInteracting userInteracting) {
        val Set<Model> models = correspondenceModel.getAllEObjectsOfTypeInCorrespondences(Model)
        if (models.nullOrEmpty) {
			val model = UMLFactory.eINSTANCE.createModel();
			val userModelName = userInteracting.getTextInput(MODELNAME_INPUTMESSAGE)
			val userModelPath = userInteracting.getTextInput(MODELPATH_INPUTMESSAGE)
			if (userModelName.nullOrEmpty) {
				model.name = MODELNAME;
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
    
    /**
     * Searches and retrieves the first UML-Package in the correspondence model that has an
     * equal name as the given package name.
     * 
     * @param correspondenceModel the correspondenceModel in which the UML-Package should be searched
     * @param packageName the package name for which a fitting UML-Package should be retrieved
     * @return the corresponding UML-Package or null if none could be found 
     */
    def static org.eclipse.uml2.uml.Package findUmlPackage(CorrespondenceModel correspondenceModel, String packageName) {
        val Set<org.eclipse.uml2.uml.Package> allPackages = correspondenceModel.getAllEObjectsOfTypeInCorrespondences(org.eclipse.uml2.uml.Package)
        
        val packages = allPackages.filter[name.equals(packageName)]
        if (packages.nullOrEmpty) {
            logger.warn("The UML-Package with the name " + packageName + " does not exist in the correspondence model")
            return null
        }
        return packages.head
    }
    
    /**
     * Searches in the packaged elements of the given uml model for primitive uml types.
     * If one matches the given name, the UML-PrimitiveType will be returned.
     * Otherwise it creates a new UML-PrimitiveType with the given name and adds it
     * to the given uml model
     * 
     * @param name the name of the UML-PrimitiveType
     * @param umlModel the uml root model
     */
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
    
    def private static <T extends org.eclipse.uml2.uml.NamedElement> findFirstCorrespondeningUmlElementByNameAndType(CorrespondenceModel correspondenceModel, String name, Class<T> typeOfJavaElement) {
        return correspondenceModel.getAllEObjectsOfTypeInCorrespondences(typeOfJavaElement).filter[it.name.equals(name)].head
    }
    
    /**
     * Displays a text message for the user.
     * 
     * @param userinteracting The userinteracting needed for the display
     * @param message the message to show
     * 
     */
    def static void showMessage(UserInteracting userinteraction, String message) {
        userinteraction.showMessage(message)
    }
}
package tools.vitruv.applications.umljava.java2uml

import java.util.ArrayList
import java.util.HashSet
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Enumeration
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.generics.QualifiedTypeArgument
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.Void
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.userinteraction.UserInteractor

import static tools.vitruv.applications.umljava.util.java.JavaStandardType.*
import static tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*
import static tools.vitruv.applications.umljava.util.uml.UmlClassifierAndPackageUtil.*

import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import org.emftext.language.java.types.TypesFactory
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.EObject

/**
 * Helper class for the Java2Uml reactions. Contains functions who depends on
 * the correspondence model or userinteracting and the name and path for the
 * uml rootmodel.
 * 
 * @author Fei
 */
class JavaToUmlHelper {
    private static val logger = Logger.getLogger(JavaToUmlHelper.simpleName)
//    public static def getRootModelFile(){
//    	return "model/model.uml" //TODO deprecated and nonfunctional, but here for now, so that the tests don't complain
//    }
    

//    def static dispatch Type getUmlType(Void jVoid, Model model, CorrespondenceModel correspondenceModel) {
//        return null
//    }
//    def static dispatch Type getUmlType(java.lang.Void nullReference, Model model, CorrespondenceModel correspondenceModel) {
//        return null
//    }
//    def static dispatch Type getUmlType(org.emftext.language.java.types.PrimitiveType jPrimtype, Model model, CorrespondenceModel correspondenceModel) {
//        return findUmlPrimitiveType(getTypeKeyword(jPrimtype), model)
//    }
//    
//    def static dispatch Type getUmlType(NamespaceClassifierReference jNamespaceRef, Model model, CorrespondenceModel correspondenceModel) {
//        return getUmlType(getJavaTypeFromTypeReference(jNamespaceRef), model, correspondenceModel)
//    }
//    /**
//     * Retrieves a corresponding UML-Classifier that corresponds to the given Java-Class by
//     * searching the correspondence model manually by type and name.
//     * 
//     * @param jClass The Java-Class whose corresponding UML-Type should be retrieved
//     * @param model The underlying root model for the
//     * @return the corresponding UMLl-Classifier or null if none can be found
//     * 
//     */
//    def static dispatch Type getUmlType(org.emftext.language.java.classifiers.Class jClass, Model model, CorrespondenceModel correspondenceModel) {
//        if ("String".equals(jClass.name)) {
//            return findUmlPrimitiveType("String", model)
//        } else {
//            //UML-Classifier, because the jClass can correspond to a UML-DataType
//            var correspUmltype = findFirstCorrespondeningUmlElementByNameAndType(correspondenceModel, jClass.name, Classifier)
//            if (correspUmltype === null) {
//                //Check manually added Types
//                correspUmltype = model.packagedElements.filter(Class).filter[it.name.equals(jClass.name)].head
//                if (correspUmltype === null) {
//                    val umlTypeClass = createUmlClass(jClass.name, VisibilityKind.PUBLIC_LITERAL, false, false)
//                    model.packagedElements += umlTypeClass
//                    return umlTypeClass
//                }
//            }
//            return correspUmltype
//        }
//    }
//    
//    /**
//     * Returns the corresponding UML-Interface or null of none could be found.
//     * 
//     * @return the corresponding UML-Interface of the given Java-Interface.
//     */
//    def static dispatch Type getUmlType(org.emftext.language.java.classifiers.Interface jInterface, Model model, CorrespondenceModel correspondenceModel) {
//    	// TODO check if and why it is necessary to search by name instead of direct retrieve
//        return findFirstCorrespondeningUmlElementByNameAndType(correspondenceModel, jInterface.name, Interface)
//    }
//    
//    def static dispatch Type getUmlType(org.emftext.language.java.classifiers.Enumeration jEnum, Model model, CorrespondenceModel correspondenceModel) {
//        return findFirstCorrespondeningUmlElementByNameAndType(correspondenceModel, jEnum.name, Enumeration)
//    }
    
    /**
     * Searches and retrieves the first UML-Package in the correspondence model that has an
     * equal name as the given package name.
     * 
     * @param correspondenceModel the correspondenceModel in which the UML-Package should be searched
     * @param packageName the package name for which a fitting UML-Package should be retrieved
     * @return the corresponding UML-Package or null if none could be found 
     */
    def static Package findUmlPackage(CorrespondenceModel correspondenceModel, String packageName) {
        val Set<Package> allPackages = correspondenceModel.getAllEObjectsOfTypeInCorrespondences(Package)
        
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
    def static PrimitiveType findUmlPrimitiveType(String name, Model umlModel) {
        val primType = umlModel.packagedElements.filter(PrimitiveType).filter[it.name.equals(name)]
        if (primType.nullOrEmpty) {
            val newPrimType = createUmlPrimitiveType(name)
            umlModel.packagedElements += newPrimType
            logger.debug("Creating a UML-PrimitiveType with the name " + name)
            return newPrimType
        }
        return primType.head
    }
    
    def private static <T extends NamedElement> findFirstCorrespondeningUmlElementByNameAndType(CorrespondenceModel correspondenceModel, String name, java.lang.Class<T> typeOfJavaElement) {
        return correspondenceModel.getAllEObjectsOfTypeInCorrespondences(typeOfJavaElement).filter[it.name.equals(name)].head
    }
    
    /**
     * Displays a text message for the user.
     * 
     * @param userinteracting The userinteracting needed for the display
     * @param message the message to show
     * 
     */
    def static void showMessage(UserInteractor userinteraction, String message) {
        userinteraction.showMessage(message)
    }
}
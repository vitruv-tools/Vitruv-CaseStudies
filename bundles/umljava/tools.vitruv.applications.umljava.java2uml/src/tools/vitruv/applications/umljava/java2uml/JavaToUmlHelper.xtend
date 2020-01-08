package tools.vitruv.applications.umljava.java2uml

import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Package
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import tools.vitruv.framework.userinteraction.UserInteractor
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.Class

/**
 * Helper class for the Java2Uml reactions. Contains functions who depends on
 * the correspondence model or userinteracting and the name and path for the
 * uml rootmodel.
 * 
 * @author Fei
 */
class JavaToUmlHelper {
    private static val logger = Logger.getLogger(JavaToUmlHelper.simpleName)
    
    /**
     * Searches and retrieves the UML package in the UML model that has an equal name as the given package name.
     * If there is more than one package with the given name, an {@link IllegalStateException} is thrown.
     * 
     * @param umlModel the UML model Model in which the UML packages should be searched
     * @param packageName the package name for which a fitting UML package should be retrieved
     * @return the UML package or null if none could be found
     */
    def static Package findUmlPackage(Model umlModel, String packageName) {
        val Set<Package> allPackages = umlModel.eAllContents.filter(Package).toSet
        val packages = allPackages.filter[name == packageName]
        if (packages.nullOrEmpty) {
            logger.warn("The UML-Package with the name " + packageName + " does not exist in the correspondence model")
            return null
        }
        if (packages.size > 1) {
            throw new IllegalStateException("There is more than one package with name " + packageName + " in the UML model.")
        }
        return packages.head
    }

    /**
     * Searches and retrieves the UML interface located in a specific package of a UML model that has an equal name as the given package name.
     * If there is more than one package with the given name an {@link IllegalStateException} is thrown.
     * 
     * @param umlModel the UML model Model in which the UML packages should be searched
     * @param interfaceName the interface name for which a fitting UML interface should be retrieved
     * @param packageName the package name in which the UML interface should be located.
     * @return the UML interface or null if none could be found
     */
    def static Interface findUmlInterface(Model umlModel, String interfaceName, String packageName) {
        return umlModel.findUmlType(interfaceName, packageName, Interface)
    }

    /**
     * Searches and retrieves the UML class located in a specific package of a UML model that has an equal name as the given package name.
     * If there is more than one package with the given name an {@link IllegalStateException} is thrown.
     * 
     * @param umlModel the UML model Model in which the UML packages should be searched
     * @param className the class name for which a fitting UML class should be retrieved
     * @param packageName the package name in which the UML class should be located.
     * @return the UML class or null if none could be found
     */
    def static Class findUmlClass(Model umlModel, String className, String packageName) {
        return umlModel.findUmlType(className, packageName, Class)
    }

    def private static <T extends Type> findUmlType(Model umlModel, String typeName, String packageName, java.lang.Class<T> type) {
        val uPackage = umlModel.findUmlPackage(packageName)
        if (uPackage === null) {
            return null
        }
        val types = uPackage.ownedTypes.filter(type).filter[name == typeName].toSet
        if (types.size > 1) {
            throw new IllegalStateException("There is more than one type with name " + typeName + " in the package " + uPackage)
        }
        return types.head
    }

    /**
     * Displays a text message for the user.
     * 
     * @param userinteractor The userinteractor needed for the display
     * @param message the message to show
     * 
     */
    def static void showMessage(UserInteractor userinteractor, String message) {
        userinteractor.notificationDialogBuilder.message(message).windowModality(WindowModality.MODAL).startInteraction
    }
}
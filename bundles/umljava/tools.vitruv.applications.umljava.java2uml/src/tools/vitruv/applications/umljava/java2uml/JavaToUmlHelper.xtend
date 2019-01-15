package tools.vitruv.applications.umljava.java2uml

import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Package
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.userinteraction.UserInteractor

import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality

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
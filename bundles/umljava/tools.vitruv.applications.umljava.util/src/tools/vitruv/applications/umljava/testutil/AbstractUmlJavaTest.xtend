package tools.vitruv.applications.umljava.testutil

import org.eclipse.emf.ecore.EObject
import tools.vitruv.domains.java.JavaDomainProvider
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.testutils.VitruviusApplicationTest
import org.apache.log4j.Logger

/**
 * Abstract class for umljava tests in both directions.
 * Initializes java and uml domain.
 * 
 * @author Fei
 */
abstract class AbstractUmlJavaTest extends VitruviusApplicationTest {
    private static val logger = Logger.getLogger(typeof(VitruviusApplicationTest).simpleName)
    
    override protected getVitruvDomains() {
        return #[new UmlDomainProvider().domain, new JavaDomainProvider().domain];
    }

    /**
     * Retrieves all corresponding objects of obj.
     * 
     * {@link tools.vitruv.framework.tests.VitruviusUnmonitoredApplicationTest#getCorrespondenceModel}
     * @param obj the object for which the corresponding objects should be retrieved
     * @return the corresponding objects of obj or null if none could be found
     * @throws IllegalArgumentException if obj is null
     */
    def protected getCorrespondingObjectList(EObject obj) {
        if (obj === null) {
            throw new IllegalArgumentException("Cannot retrieve correspondence for null")
        }
        val corrList = getCorrespondenceModel.getCorrespondingEObjects(#[obj]).flatten;
        if (corrList.nullOrEmpty) {
            logger.warn("No Correspondences found for " + obj)
            return null
        }
        return corrList
    }
    
    /**
     * Retrieves all corresponding objects of obj and filters the result list by the class c
     * 
     * {@link #getCorrespondingObjectList(EObject)}
     * @param obj the object for which the corresponding objects should be retrieved
     * @return the corresponding objects of obj filtered by c or null if none could be found
     */
    def protected <T extends EObject> getCorrespondingObjectListWithClass(EObject obj, Class<T> c) {
        return getCorrespondingObjectList(obj)?.filter(c)
    }
    
    /**
     * Retrieves all corresponding objects of obj, filters the result list by the class c
     * and returns the first element of the remaining list
     * 
     * {@link #getCorrespondingObjectList(EObject)}
     * @param obj the object for which the first corresponding object should be retrieved
     * @return the first corresponding object of obj or null if none could be found
     */
    def protected <T extends EObject> getFirstCorrespondingObjectWithClass(EObject obj, Class<T> c) {
        val correspondingObjectList = getCorrespondingObjectListWithClass(obj, c)
        if (correspondingObjectList.nullOrEmpty) {
            logger.warn("There are no corresponding objects for " + obj + " of the type " + c.class + ". Returning null.")
            return null
        } else if (correspondingObjectList.size > 1) {
            logger.warn("There are more than one corresponding object for " + obj + " of the type " + c.class + ". Returning the first.")
        }
        return correspondingObjectList.head
    }
}
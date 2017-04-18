package tools.vitruv.applications.umljava.testutil

import org.eclipse.emf.ecore.EObject
import tools.vitruv.domains.java.JavaDomain
import tools.vitruv.domains.uml.UmlDomain
import tools.vitruv.framework.tests.VitruviusApplicationTest
import org.apache.log4j.Logger

abstract class AbstractUmlJavaTest extends VitruviusApplicationTest {
    private static val logger = Logger.getLogger(typeof(VitruviusApplicationTest).simpleName)
    
    override protected createMetamodels() {
        return #[new UmlDomain().metamodel, new JavaDomain().metamodel];
    }

    def protected getCorrespondingObjectList(EObject obj) {
        if (obj === null) {
            throw new IllegalArgumentException("Cannot retrieve correspondence for null")
        }
        val corrList = getCorrespondenceModel.getCorrespondingEObjects(#[obj]).flatten;
        if (corrList.nullOrEmpty) {
            return null
        }
        return corrList
    }
    
    def protected <T extends EObject> getCorrespondingObjectListWithClass(EObject obj, Class<T> c) {
        return getCorrespondingObjectList(obj).filter(c)
    }
    
    def protected <T extends EObject> getFirstCorrespondingObjectWithClass(EObject obj, Class<T> c) {
        val correspondingObjectList = getCorrespondingObjectListWithClass(obj, c)
        if (correspondingObjectList.nullOrEmpty) {
            logger.warn("There are no corresponding objects for " + obj + " of the type " + c.class + ". Returning null.")
            return null
        } else if (correspondingObjectList.size > 1) {
            logger.warn("There are more than one corresponding object for " + obj + " of the type " + c.class + ". Returning the first.")
        }
        return correspondingObjectList.head as T
    }
}
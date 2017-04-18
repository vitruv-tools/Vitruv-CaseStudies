package tools.vitruv.applications.umljava.testutil

import org.eclipse.emf.ecore.EObject
import tools.vitruv.domains.java.JavaDomain
import tools.vitruv.domains.uml.UmlDomain
import tools.vitruv.framework.tests.VitruviusApplicationTest

abstract class AbstractUmlJavaTest extends VitruviusApplicationTest {
    
    override protected createMetamodels() {
        return #[new UmlDomain().metamodel, new JavaDomain().metamodel];
    }

    def protected getCorrespondingObjects(EObject obj) {
        if (obj === null) {
            throw new IllegalArgumentException("Cannot retrieve correspondence for null")
        }
        val corrList = getCorrespondenceModel.getCorrespondingEObjects(#[obj]).flatten;
        if (corrList.nullOrEmpty) {
            return null
        }
        return corrList
    }
    
    def protected <T> getCorrespondingObjectWithClass(EObject obj, Class<T> c) {
        return getCorrespondingObjects(obj).filter(c).head as T
    }
}
package tools.vitruv.applications.umljava.util

import org.eclipse.emf.common.util.EList
import org.eclipse.uml2.uml.Classifier
import java.util.Iterator

class UmlUtil {
    
    /**
     * 
     */
    def static EList<org.eclipse.uml2.uml.Classifier> extractSuperInterfaces(org.eclipse.uml2.uml.Interface umlInterface) {
        val supers = umlInterface?.generals
        if (supers.nullOrEmpty) {
            return null
        }
        return supers
    }
    
    def static removeClassifierFromIterator(Iterator<? extends Classifier> iter, Classifier classif) {
        while (iter.hasNext) {
                if (classif.name.equals(iter.next.name)) {
                    iter.remove;
                }
        }
    }
}
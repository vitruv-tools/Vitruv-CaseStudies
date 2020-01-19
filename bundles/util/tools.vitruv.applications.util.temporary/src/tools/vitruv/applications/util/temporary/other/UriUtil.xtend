package tools.vitruv.applications.util.temporary.other

import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil
import org.eclipse.emf.ecore.EObject

/**
 * TODO TS Temporary URI utility class, could maybe be merged with {@link URIUtil}
 * The methods contained in this class came from different application utils with some duplication.
 */
@Utility
class UriUtil {
    def static normalizeURI(EObject eObject) {
        if (null === eObject.eResource || null === eObject.eResource.resourceSet) {
            return false
        }
        val uriConverter = eObject.eResource.resourceSet.getURIConverter
        eObject.eResource.URI = uriConverter.normalize(eObject.eResource.getURI)
        return true
    }
}

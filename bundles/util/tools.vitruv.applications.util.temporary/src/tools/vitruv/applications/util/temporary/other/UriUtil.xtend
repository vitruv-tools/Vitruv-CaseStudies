package tools.vitruv.applications.util.temporary.other

import org.apache.log4j.Logger
import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil
import org.eclipse.emf.ecore.EObject

/**
 * TODO TS Temporary URI utility class, could maybe be merged with {@link URIUtil}
 * The methods contained in this class came from different application utils with some duplication.
 */
@Utility
class UriUtil {

    static val logger = Logger.getLogger(UriUtil.simpleName)

    def static normalizeURI(EObject eObject) {
        if (null === eObject.eResource || null === eObject.eResource.resourceSet) {
            // While this happens sometimes it is unclear if that is a problem or intended:
            logger.warn('''URI of EObject «eObject» could not be normalized, because either the Resource or the ResourceSet are null!''')
            return false
        }
        val uriConverter = eObject.eResource.resourceSet.getURIConverter
        eObject.eResource.URI = uriConverter.normalize(eObject.eResource.getURI)
        return true
    }
}

package tools.vitruv.applications.util.temporary.other;

import edu.kit.ipd.sdq.activextendannotations.Utility;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * TODO TS: Temporary URI utility class. Could maybe be merged with {@link URIUtil}.
 * The methods in this class originated from different application utilities with some duplication.
 */
@Utility
@SuppressWarnings("all")
public final class UriUtil {

    private static final Logger logger = Logger.getLogger(UriUtil.class.getSimpleName());

    private UriUtil() {
        // Private constructor to prevent instantiation
    }

    public static boolean normalizeURI(final EObject eObject) {
        if (eObject.eResource() == null || eObject.eResource().getResourceSet() == null) {
            StringConcatenation message = new StringConcatenation();
            message.append("URI of EObject ");
            message.append(eObject);
            message.append(" could not be normalized, because either the Resource or the ResourceSet are null!");
            logger.warn(message);
            return false;
        }

        URIConverter uriConverter = eObject.eResource().getResourceSet().getURIConverter();
        Resource resource = eObject.eResource();
        resource.setURI(uriConverter.normalize(resource.getURI()));
        return true;
    }
}

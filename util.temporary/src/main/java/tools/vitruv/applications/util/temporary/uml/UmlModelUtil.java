package tools.vitruv.applications.util.temporary.uml;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.activextendannotations.Utility;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;

import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.uml2.uml.Model;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

import tools.vitruv.dsls.reactions.runtime.helper.PersistenceHelper;

/**
 * Utility class for loading UML models.
 */
public final class UmlModelUtil {

    private UmlModelUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Tries to load a persisted UML model based on a relative path and a source EObject.
     * <p>
     * The resource can only be found if it was previously persisted (e.g., after change propagation).
     *
     * @param relativeModelPath The path to the model file relative to the project.
     * @param source            An EObject from the source project (used to resolve the URI).
     * @return An {@link Optional} containing the {@link Model} if found, or empty otherwise.
     */
    public static Optional<Model> loadPersistedModelFromSource(final String relativeModelPath, final EObject source) {
        URI modelUri = PersistenceHelper.getURIFromSourceProjectFolder(source, relativeModelPath);

        if (URIUtil.existsResourceAtUri(modelUri)) {
            Resource modelResource = source.eResource().getResourceSet().getResource(modelUri, true);
            Model model = IterableExtensions.head(Iterables.filter(modelResource.getContents(), Model.class));
            return Optional.of(model);
        }

        return Optional.empty();
    }
}

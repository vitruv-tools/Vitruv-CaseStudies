package tools.vitruv.applications.util.temporary.uml

import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil
import java.util.Optional
import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.Model
import tools.vitruv.extensions.dslsruntime.reactions.helper.PersistenceHelper

@Utility
class UmlModelUtil {

    /**
     * Tries to load a persisted model from a source object.
     * The resource can only be found if it was previously persisted, which happens after the change propagation terminates.
     * @param relativeModelPath is the path to the model file.
     * @param source is the source object, e.g. a model element contained in that model.
     * @returns the optional model or nothing.
     */
    def static Optional<Model> loadPersistedModelFromSource(String relativeModelPath, EObject source) {
        val uri = PersistenceHelper.getURIFromSourceProjectFolder(source, relativeModelPath) 
        if (URIUtil.existsResourceAtUri(uri)) { 
            val resource = source.eResource.resourceSet.getResource(uri, true)
            return Optional.of(resource.contents.filter(Model).head)
        }
        return Optional.empty
    }
}
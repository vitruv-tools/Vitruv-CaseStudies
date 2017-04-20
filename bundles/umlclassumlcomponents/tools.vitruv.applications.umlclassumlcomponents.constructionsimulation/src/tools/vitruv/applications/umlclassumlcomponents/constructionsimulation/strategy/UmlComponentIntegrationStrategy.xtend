package tools.vitruv.applications.umlclassumlcomponents.constructionsimulation.strategy

import java.util.List
import org.eclipse.core.resources.IResource
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.uml2.uml.Model
import static tools.vitruv.extensions.constructionsimulation.util.ResourceHelper.*
import tools.vitruv.extensions.constructionsimulation.strategies.IntegrationStategy
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.applications.umlclassumlcomponents.constructionsimulation.traversal.UmlComponentTraversalStrategy

public class UmlComponentIntegrationStrategy extends IntegrationStategy {

    override Resource loadModel(String path) {
        val resourceSet = new ResourceSetImpl()
        val res = resourceSet.getResource(URI.createFileURI(path), true)
        return res
    }

    override protected Resource checkAndEnforceInvariants(Resource model) {
        return model
    }
    
    override List<VitruviusChange> createChangeModels(IResource resource, Resource validModel) {
    	//create correct URI for valid model:
        val uri = createPlatformUriForResource(validModel)
        
        val compModel = validModel.allContents.head as Model
        
        var EList<VitruviusChange> changes = null
        
        val umlTraversal = new UmlComponentTraversalStrategy()
        
        try {
            changes = umlTraversal.traverse(compModel, uri, null)
        } catch (UnsupportedOperationException e) {
            this.logger.error(e.getMessage())
        }
        return changes
    }
}

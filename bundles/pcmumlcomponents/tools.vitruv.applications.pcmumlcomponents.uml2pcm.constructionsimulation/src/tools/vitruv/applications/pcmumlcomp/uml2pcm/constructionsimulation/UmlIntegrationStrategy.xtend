package tools.vitruv.applications.pcmumlcomp.uml2pcm.constructionsimulation

import java.io.File
import org.eclipse.core.resources.IResource
import org.eclipse.core.resources.IWorkspace
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.IPath
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.uml2.uml.Model
import tools.vitruv.extensions.constructionsimulation.strategies.IntegrationStategy
import tools.vitruv.framework.change.description.VitruviusChange
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl

class UmlIntegrationStrategy extends IntegrationStategy {
	
	override protected checkAndEnforceInvariants(Resource model) {
		return model;
	}
	
	override createChangeModels(IResource resource, Resource validModel) {
		val uri = createPlatformUriForResource(validModel)
		val umlModel = validModel.allContents.head as Model
		var EList<VitruviusChange> changes = null
		val umlTraversal = new ModelTraversalStrategy()
		try {
			changes = umlTraversal.traverse(umlModel, uri, null)
		} catch (UnsupportedOperationException e) {
			logger.error(e.message)
		}
		return changes
	}
	
	override loadModel(String path) {
		val resourceSet = new ResourceSetImpl()
		return resourceSet.getResource(URI.createFileURI(path), true)
	}
	
	protected def URI createPlatformUriForResource(Resource resource) {
		val IWorkspace workspace = ResourcesPlugin.workspace
		val IPath workspaceDir = workspace.root.location
		var workspaceString = workspaceDir.toString()
		workspaceString = workspaceString.replace("/", File.separator)
		
		val platRelativeModelLoc = resource.URI.toFileString().replace(workspaceString, "")
		return URI.createPlatformResourceURI(platRelativeModelLoc, true)
	}	
}
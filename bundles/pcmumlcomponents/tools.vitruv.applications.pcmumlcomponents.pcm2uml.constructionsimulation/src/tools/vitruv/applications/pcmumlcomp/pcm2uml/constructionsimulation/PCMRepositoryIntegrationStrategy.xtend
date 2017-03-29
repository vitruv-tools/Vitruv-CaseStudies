package tools.vitruv.applications.pcmumlcomp.pcm2uml.constructionsimulation

import org.eclipse.core.resources.IResource
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.resource.Resource
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.domains.pcm.util.RepositoryModelLoader
import tools.vitruv.extensions.constructionsimulation.strategies.IntegrationStategy
import tools.vitruv.extensions.constructionsimulation.traversal.ITraversalStrategy
import tools.vitruv.framework.change.description.VitruviusChange
import org.eclipse.emf.common.util.URI
import org.eclipse.core.resources.IWorkspace
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.IPath
import java.io.File
import java.util.List

class PCMRepositoryIntegrationStrategy extends IntegrationStategy {
	
	override protected checkAndEnforceInvariants(Resource model) {
		return model
	}
	
	override List<VitruviusChange> createChangeModels(IResource resource, Resource validModel) {
		val ITraversalStrategy<Repository> strategy = new RepositoryTraversalStrategy
		var EList<VitruviusChange> changes = null
		val Repository repository = validModel.contents.get(0) as Repository
		try {
			changes = strategy.traverse(repository, createPlatformUriForResource(validModel), null)
		} catch (UnsupportedOperationException e) {
			logger.error(e.message)
		}
		return changes
	}
	
	override Resource loadModel(String path) {
		return RepositoryModelLoader.loadPCMResource(path)
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
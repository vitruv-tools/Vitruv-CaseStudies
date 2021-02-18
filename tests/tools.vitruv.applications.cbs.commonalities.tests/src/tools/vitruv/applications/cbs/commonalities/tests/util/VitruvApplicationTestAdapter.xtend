package tools.vitruv.applications.cbs.commonalities.tests.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.common.util.URI

/**
 * Interface to internals of {@link VitruvApplicationTest} for testing
 * related helpers.
 */
interface VitruvApplicationTestAdapter {

	def Resource getResourceAt(String modelPathInProject)

	def Resource getTestResource(String resourcePath)
	
	def <T extends EObject> T at(Class<T> type, URI uri)

	def void createAndSynchronizeModel(String modelPathInProject, EObject rootElement)

}

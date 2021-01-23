package tools.vitruv.applications.cbs.commonalities.tests.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource

/**
 * Interface to internals of {@link VitruvApplicationTest} for testing
 * related helpers.
 */
interface VitruvApplicationTestAdapter {

	def Resource getResourceAt(String modelPathInProject)

	def Resource getTestResource(String resourcePath)

	def void createAndSynchronizeModel(String modelPathInProject, EObject rootElement)

}

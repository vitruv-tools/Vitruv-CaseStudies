package tools.vitruv.applications.cbs.commonalities.tests.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.testutils.VitruviusApplicationTest

/**
 * Interface to internals of {@link VitruviusApplicationTest} for testing
 * related helpers.
 */
interface VitruvApplicationTestAdapter {

	def Resource getResourceAt(String modelPathInProject)

	def Resource getTestResource(String resourcePath)

	def void createAndSynchronizeModel(String modelPathInProject, EObject rootElement)

	def void saveAndSynchronizeChanges(Resource resource)

	def void deleteAndSynchronizeModel(String modelPathInProject)
}

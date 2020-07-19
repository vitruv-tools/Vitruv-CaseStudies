package tools.vitruv.applications.cbs.commonalities.tests

import com.google.inject.Inject
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.dsls.commonalities.testutils.CommonalitiesExecutionTest

import static org.junit.Assert.*

abstract class CBSCommonalitiesExecutionTest extends CommonalitiesExecutionTest {

	@Inject CBSExecutionTestCompiler compiler

	@Accessors(PROTECTED_GETTER)
	val VitruvApplicationTestAdapter vitruvApplicationTestAdapter = createVitruvApplicationTestAdapter()

	private def VitruvApplicationTestAdapter createVitruvApplicationTestAdapter() {
		val resourceAtFunc = [ String modelPathInProject |
			resourceAt(modelPathInProject)
		]
		val testResourceFunc = [ String resourcePath |
			getTestResource(resourcePath)
		]
		val createAndSynchronizeModelFunc = [ String modelPathInProject, EObject rootElement |
			createAndSynchronizeModel(modelPathInProject, rootElement)
		]
		val saveAndSynchronizeChangesFunc = [ Resource resource |
			saveAndSynchronizeChanges(resource)
		]
		val deleteAndSynchronizeModelFunc = [ String modelPathInProject |
			deleteAndSynchronizeModel(modelPathInProject)
		]
		return new VitruvApplicationTestAdapter() {

			override getResourceAt(String modelPathInProject) {
				resourceAtFunc.apply(modelPathInProject)
			}

			override getTestResource(String resourcePath) {
				testResourceFunc.apply(resourcePath)
			}

			override createAndSynchronizeModel(String modelPathInProject, EObject rootElement) {
				createAndSynchronizeModelFunc.apply(modelPathInProject, rootElement)
			}

			override saveAndSynchronizeChanges(Resource resource) {
				saveAndSynchronizeChangesFunc.apply(resource)
			}

			override deleteAndSynchronizeModel(String modelPathInProject) {
				deleteAndSynchronizeModelFunc.apply(modelPathInProject)
			}
		}
	}

	@Accessors(PROTECTED_GETTER)
	private var ResourceSet testResourcesResourceSet

	override protected createChangePropagationSpecifications() {
		compiler.changePropagationDefinitions
	}

	override protected setup() {
		testResourcesResourceSet = new ResourceSetImpl()
	}

	override protected cleanup() {
		testResourcesResourceSet = null
	}

	protected final def getTestResource(String resourcePath) {
		val resourceUri = URI.createURI(resourcePath)
		val resource = testResourcesResourceSet.getResource(resourceUri, true)
		assertNotNull("Resource not found: " + resourcePath, resource)
		return resource
	}
}
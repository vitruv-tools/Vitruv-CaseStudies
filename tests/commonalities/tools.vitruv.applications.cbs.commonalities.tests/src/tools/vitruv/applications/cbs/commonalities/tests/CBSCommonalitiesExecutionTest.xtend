package tools.vitruv.applications.cbs.commonalities.tests

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.dsls.commonalities.testutils.CommonalitiesExecutionTest

abstract class CBSCommonalitiesExecutionTest extends CommonalitiesExecutionTest {

	@Inject CBSExecutionTestCompiler compiler

	@Accessors(PROTECTED_GETTER)
	val VitruvApplicationTestAdapter vitruvApplicationTestAdapter = createVitruvApplicationTestAdapter()

	private def VitruvApplicationTestAdapter createVitruvApplicationTestAdapter() {
		val resourceAtFunc = [ String modelPathInProject |
			resourceAt(modelPathInProject)
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

	override protected createChangePropagationSpecifications() {
		compiler.changePropagationDefinitions
	}
}

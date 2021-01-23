package tools.vitruv.applications.cbs.commonalities.tests.util

import org.eclipse.emf.ecore.EObject
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

import static com.google.common.base.Preconditions.*

/**
 * Handles the domain specific but test case independent aspects of creating
 * and checking models for test cases.
 */
abstract class DomainModelTester {

	protected val extension VitruvApplicationTestAdapter vitruvApplicationTestAdapter

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		checkNotNull(vitruvApplicationTestAdapter, "vitruvApplicationTestAdapter is null")
		this.vitruvApplicationTestAdapter = vitruvApplicationTestAdapter
	}

	def void createAndSynchronizeModel(EObject rootObject)

	def void createAndSynchronizeModels(Iterable<? extends EObject> rootObjects) {
		rootObjects.forEach[createAndSynchronizeModel]
	}

	def void assertModelExists(EObject model)

	def void assertModelsExist(Iterable<? extends EObject> rootObjects) {
		rootObjects.forEach[assertModelExists]
	}
}

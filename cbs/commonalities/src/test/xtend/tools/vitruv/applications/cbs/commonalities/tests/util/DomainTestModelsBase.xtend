package tools.vitruv.applications.cbs.commonalities.tests.util

import java.util.List
import java.util.function.Supplier
import org.eclipse.emf.ecore.EObject
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

import static com.google.common.base.Preconditions.*

abstract class DomainTestModelsBase {
	protected val extension VitruvApplicationTestAdapter vitruvApplicationTestAdapter
	protected val DomainModelTester modelTester

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		checkNotNull(vitruvApplicationTestAdapter, "vitruvApplicationTestAdapter is null")
		this.vitruvApplicationTestAdapter = vitruvApplicationTestAdapter
		this.modelTester = createModelTester()
	}

	protected abstract def DomainModelTester createModelTester()

	protected def DomainModel newModel(Supplier<List<? extends EObject>> modelCreator) {
		return new SimpleDomainModel(modelTester, modelCreator)
	}
}

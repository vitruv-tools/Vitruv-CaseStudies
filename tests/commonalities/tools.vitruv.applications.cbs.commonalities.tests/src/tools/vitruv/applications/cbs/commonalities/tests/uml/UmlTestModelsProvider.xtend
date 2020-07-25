package tools.vitruv.applications.cbs.commonalities.tests.uml

import java.util.function.Function
import tools.vitruv.applications.cbs.commonalities.tests.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

class UmlTestModelsProvider<D> extends DomainModelsProvider<D> {

	new(Function<VitruvApplicationTestAdapter, D> provider) {
		super('UML', provider)
	}
}

package tools.vitruv.applications.cbs.commonalities.tests.pcm

import java.util.function.Function
import tools.vitruv.applications.cbs.commonalities.tests.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

class PcmTestModelsProvider<D> extends DomainModelsProvider<D> {

	new(Function<VitruvApplicationTestAdapter, D> provider) {
		super('PCM', provider)
	}
}

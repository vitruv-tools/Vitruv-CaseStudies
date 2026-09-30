package tools.vitruv.applications.cbs.commonalities.tests.util.pcm;

import java.util.function.Function;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class PcmTestModelsProvider<D> extends DomainModelsProvider<D> {

	public PcmTestModelsProvider(Function<VitruvApplicationTestAdapter, D> provider) {
		super("PCM", provider);
	}
}

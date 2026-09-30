package tools.vitruv.applications.cbs.commonalities.tests.util.pcm;

import tools.vitruv.applications.cbs.commonalities.tests.util.DomainTestModelsBase;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class PcmTestModelsBase extends DomainTestModelsBase {

	public PcmTestModelsBase(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	@Override
	protected PcmModelTester createModelTester() {
		return new PcmModelTester(vitruvApplicationTestAdapter);
	}
}

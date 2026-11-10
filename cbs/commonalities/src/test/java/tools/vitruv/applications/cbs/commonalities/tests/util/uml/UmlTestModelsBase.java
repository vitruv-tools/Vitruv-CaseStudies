package tools.vitruv.applications.cbs.commonalities.tests.util.uml;

import tools.vitruv.applications.cbs.commonalities.tests.util.DomainTestModelsBase;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class UmlTestModelsBase extends DomainTestModelsBase {

    protected final UmlTestHelper umlTestHelper;

    public UmlTestModelsBase(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
        this.umlTestHelper = new UmlTestHelper(vitruvApplicationTestAdapter);
    }

    @Override
    protected UmlModelTester createModelTester() {
        return new UmlModelTester(vitruvApplicationTestAdapter);
    }
}

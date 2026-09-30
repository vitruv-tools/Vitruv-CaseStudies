package tools.vitruv.applications.cbs.commonalities.tests.util.uml;

import java.util.function.Function;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class UmlTestModelsProvider<D> extends DomainModelsProvider<D> {

	public UmlTestModelsProvider(Function<VitruvApplicationTestAdapter, D> provider) {
		super("UML", provider);
	}
}

package tools.vitruv.applications.cbs.commonalities.tests.util.java;

import java.util.function.Function;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class JavaTestModelsProvider<D> extends DomainModelsProvider<D> {

	public JavaTestModelsProvider(Function<VitruvApplicationTestAdapter, D> provider) {
		super("Java", provider);
	}
}

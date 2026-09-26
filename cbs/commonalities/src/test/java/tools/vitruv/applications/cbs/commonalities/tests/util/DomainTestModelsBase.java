package tools.vitruv.applications.cbs.commonalities.tests.util;

import java.util.List;
import java.util.function.Supplier;
import org.eclipse.emf.ecore.EObject;
import static com.google.common.base.Preconditions.checkNotNull;

public abstract class DomainTestModelsBase {
    protected final VitruvApplicationTestAdapter vitruvApplicationTestAdapter;
    protected final DomainModelTester modelTester;

    protected DomainTestModelsBase(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        checkNotNull(vitruvApplicationTestAdapter, "vitruvApplicationTestAdapter is null");
        this.vitruvApplicationTestAdapter = vitruvApplicationTestAdapter;
        this.modelTester = createModelTester();
    }

    protected abstract DomainModelTester createModelTester();

    protected DomainModel newModel(Supplier<List<? extends EObject>> modelCreator) {
        return new SimpleDomainModel(modelTester, modelCreator);
    }
}
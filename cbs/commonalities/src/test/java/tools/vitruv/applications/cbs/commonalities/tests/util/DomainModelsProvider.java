package tools.vitruv.applications.cbs.commonalities.tests.util;

import java.util.function.Function;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Provides the domain models for a specific domain.
 * <p>
 * Note: The domain models are actually provided by an external function which
 * is passed to instances of this class during construction. The benefit of
 * this wrapper class is that it can additionally provide the name of the
 * domain and is suitable to be used as parameter in parameterized tests.
 * 
 * @param <D> the type of the provided domain models
 */
public class DomainModelsProvider<D> {

    private final String domainName;
    private final Function<VitruvApplicationTestAdapter, D> provider;

    public DomainModelsProvider(String domainName, Function<VitruvApplicationTestAdapter, D> provider) {
        checkNotNull(domainName, "domainName is null");
        checkNotNull(provider, "provider is null");
        this.provider = provider;
        this.domainName = domainName;
    }

    public final String getDomainName() {
        return domainName;
    }

    public D getModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        return provider.apply(vitruvApplicationTestAdapter);
    }

    // Note: This is used in the test name of parameterized tests.
    @Override
    public final String toString() {
        return domainName;
    }
}
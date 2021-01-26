package tools.vitruv.applications.cbs.commonalities.tests.util

import java.util.function.Function
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

import static com.google.common.base.Preconditions.*

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
class DomainModelsProvider<D> {

	val String domainName
	val Function<VitruvApplicationTestAdapter, D> provider

	new(String domainName, Function<VitruvApplicationTestAdapter, D> provider) {
		checkNotNull(domainName, "domainName is null")
		checkNotNull(provider, "provider is null")
		this.provider = provider
		this.domainName = domainName
	}

	final def String getDomainName() {
		return domainName
	}

	def getModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		return provider.apply(vitruvApplicationTestAdapter)
	}

	// Note: This is used in the test name of parameterized tests.
	final override String toString() {
		return domainName
	}
}

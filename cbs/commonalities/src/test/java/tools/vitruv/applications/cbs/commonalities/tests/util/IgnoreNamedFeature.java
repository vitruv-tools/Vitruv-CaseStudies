package tools.vitruv.applications.cbs.commonalities.tests.util;

import java.util.Objects;
import java.util.function.Consumer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.hamcrest.Description;

public class IgnoreNamedFeature implements FeatureMatcher {

	private final String featureName;

	IgnoreNamedFeature(String featureName) {
		this.featureName = featureName;
	}

	@Override
	public boolean isForFeature(EObject expectedObject, EStructuralFeature feature) {
		return Objects.equals(feature.getName(), featureName);
	}

	@Override
	public Consumer<Description> getMismatch(Object expectedValue, Object itemValue) {
		return null;
	}
}

package tools.vitruv.applications.cbs.commonalities.tests.util;

import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.hamcrest.Description;

public class IgnoreAllExceptNamedFeatures implements FeatureMatcher {

	private final List<String> featureNames;

	IgnoreAllExceptNamedFeatures(String... featureNames) {
		this.featureNames = List.of(featureNames);
	}

	@Override
	public boolean isForFeature(EObject expectedObject, EStructuralFeature feature) {
		return !featureNames.contains(feature.getName());
	}

	@Override
	public Consumer<Description> getMismatch(Object expectedValue, Object itemValue) {
		return null;
	}
}

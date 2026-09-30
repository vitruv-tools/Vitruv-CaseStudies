package tools.vitruv.applications.cbs.commonalities.tests.util;

import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.hamcrest.Description;

public class IgnoreAllExceptTypedFeatures implements FeatureMatcher {

	private final List<EClassifier> featureTypes;

	IgnoreAllExceptTypedFeatures(EClassifier... featureTypes) {
		this.featureTypes = List.of(featureTypes);
	}

	@Override
	public boolean isForFeature(EObject expectedObject, EStructuralFeature feature) {
		return !featureTypes.contains(feature.getEType());
	}

	@Override
	public Consumer<Description> getMismatch(Object expectedValue, Object itemValue) {
		return null;
	}
}

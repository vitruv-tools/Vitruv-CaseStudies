package tools.vitruv.applications.cbs.commonalities.tests.util;

import java.util.function.Consumer;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.hamcrest.Description;

public class IgnoreTypedFeatures implements FeatureMatcher {

	private final EClassifier featureType;

	IgnoreTypedFeatures(EClassifier featureType) {
		this.featureType = featureType;
	}

	@Override
	public boolean isForFeature(EObject expectedObject, EStructuralFeature feature) {
		return feature.getEType() == featureType;
	}

	@Override
	public Consumer<Description> getMismatch(Object expectedValue, Object itemValue) {
		return null;
	}
}

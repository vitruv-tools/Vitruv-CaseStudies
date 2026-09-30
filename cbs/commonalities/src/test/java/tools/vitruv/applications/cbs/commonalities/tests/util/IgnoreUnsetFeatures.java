package tools.vitruv.applications.cbs.commonalities.tests.util;

import java.util.function.Consumer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.hamcrest.Description;

public class IgnoreUnsetFeatures implements FeatureMatcher {

	IgnoreUnsetFeatures() {
	}

	@Override
	public boolean isForFeature(EObject expectedObject, EStructuralFeature feature) {
		return !expectedObject.eIsSet(feature);
	}

	@Override
	public Consumer<Description> getMismatch(Object expectedValue, Object itemValue) {
		return null;
	}
}

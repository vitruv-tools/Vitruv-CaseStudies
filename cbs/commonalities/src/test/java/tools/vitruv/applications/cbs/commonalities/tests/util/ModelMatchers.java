package tools.vitruv.applications.cbs.commonalities.tests.util;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.hamcrest.Matcher;

public final class ModelMatchers {

	private ModelMatchers() {
	}

	public static Matcher<Resource> contains(EObject root, FeatureMatcher... featureMatchers) {
		return new ResourceContainmentMatcher(root, featureMatchers);
	}

	public static Matcher<Resource> doesNotExist() {
		return new ResourceInexistenceMatcher();
	}

	public static Matcher<EObject> equalsDeeply(EObject object, FeatureMatcher... featureMatchers) {
		return new ModelTreeEqualityMatcher(object, featureMatchers);
	}

	public static IgnoreNamedFeature ignoring(String featureName) {
		return new IgnoreNamedFeature(featureName);
	}

	public static IgnoreAllExceptNamedFeatures ignoringAllExcept(String... featureNames) {
		return new IgnoreAllExceptNamedFeatures(featureNames);
	}

	// ignores features that are unset in the expected (!) object
	public static IgnoreUnsetFeatures ignoringUnsetFeatures() {
		return new IgnoreUnsetFeatures();
	}

	public static IgnoreTypedFeatures ignoringFeaturesOfType(EClassifier featureType) {
		return new IgnoreTypedFeatures(featureType);
	}

	public static IgnoreAllExceptTypedFeatures ignoringAllExceptFeaturesOfType(EClassifier... featureTypes) {
		return new IgnoreAllExceptTypedFeatures(featureTypes);
	}
}

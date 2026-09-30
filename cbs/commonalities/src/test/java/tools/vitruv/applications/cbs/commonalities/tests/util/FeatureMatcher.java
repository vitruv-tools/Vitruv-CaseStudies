package tools.vitruv.applications.cbs.commonalities.tests.util;

import java.util.function.Consumer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.hamcrest.Description;

public interface FeatureMatcher {
	boolean isForFeature(EObject expectedObject, EStructuralFeature feature);

	Consumer<Description> getMismatch(Object expectedValue, Object itemValue);
}

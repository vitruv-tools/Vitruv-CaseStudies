package tools.vitruv.applications.cbs.commonalities.tests.util;

import java.util.Collections;
import org.eclipse.emf.ecore.resource.Resource;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

class ResourceInexistenceMatcher extends TypeSafeMatcher<Resource> {

	private boolean exists;

	@Override
	protected void describeMismatchSafely(Resource item, Description mismatchDescription) {
		mismatchDescription.appendText("there was a resource at ").appendValue(item.getURI());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("the resource not to exist");
	}

	@Override
	protected boolean matchesSafely(Resource item) {
		exists = item.getResourceSet().getURIConverter().exists(item.getURI(), Collections.emptyMap());
		return !exists;
	}
}

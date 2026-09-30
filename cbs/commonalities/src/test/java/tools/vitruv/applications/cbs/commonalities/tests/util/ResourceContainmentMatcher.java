package tools.vitruv.applications.cbs.commonalities.tests.util;

import java.util.Collections;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

class ResourceContainmentMatcher extends TypeSafeMatcher<Resource> {

	private final Matcher<EObject> delegateMatcher;
	private final EObject expectedObject;
	private int contentsSize;
	private boolean exists;

	ResourceContainmentMatcher(EObject expectedObject, FeatureMatcher... featureMatchers) {
		this.expectedObject = expectedObject;
		this.delegateMatcher = new ModelTreeEqualityMatcher(expectedObject, featureMatchers);
	}

	@Override
	protected void describeMismatchSafely(Resource item, Description mismatchDescription) {
		if (!exists) {
			mismatchDescription.appendText("there is no resource at ").appendValue(item.getURI());
		} else if (contentsSize == 0) {
			mismatchDescription.appendText("the resource was empty.");
		} else if (contentsSize > 1) {
			mismatchDescription.appendText("the resource contained ").appendValue(contentsSize)
					.appendText(" instead of just one content element.");
		} else {
			delegateMatcher.describeMismatch(expectedObject, mismatchDescription);
		}
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("A resource containing the object tree rooted at ").appendValue(expectedObject);
	}

	@Override
	protected boolean matchesSafely(Resource item) {
		exists = item.getResourceSet().getURIConverter().exists(item.getURI(), Collections.emptyMap());
		if (!exists) {
			return false;
		}
		contentsSize = item.getContents().size();
		if (contentsSize != 1) {
			return false;
		}
		return delegateMatcher.matches(item.getContents().get(0));
	}
}

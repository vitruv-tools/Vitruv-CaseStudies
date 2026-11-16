package tools.vitruv.applications.cbs.commonalities.tests.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.TypeSafeMatcher;

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

    public static FeatureMatcher ignoring(String featureName) {
        return new IgnoringFeatureMatcher((expectedObject, feature) -> feature.getName().equals(featureName));
    }

    public static FeatureMatcher ignoringAllExcept(String... featureNames) {
        List<String> featureNameList = List.of(featureNames);
        return new IgnoringFeatureMatcher(
                (expectedObject, feature) -> !featureNameList.contains(feature.getName()));
    }

    // ignores features that are unset in the expected (!) object
    public static FeatureMatcher ignoringUnsetFeatures() {
        return new IgnoringFeatureMatcher((expectedObject, feature) -> !expectedObject.eIsSet(feature));
    }

    public static FeatureMatcher ignoringFeaturesOfType(EClassifier featureType) {
        return new IgnoringFeatureMatcher((expectedObject, feature) -> feature.getEType() == featureType);
    }

    public static FeatureMatcher ignoringAllExceptFeaturesOfType(EClassifier... featureTypes) {
        List<EClassifier> featureTypeList = List.of(featureTypes);
        return new IgnoringFeatureMatcher(
                (expectedObject, feature) -> !featureTypeList.contains(feature.getEType()));
    }

    /**
     * A {@link FeatureMatcher} that accepts any value of the features it is
     * responsible for.
     */
    private static final class IgnoringFeatureMatcher implements FeatureMatcher {
        private final FeatureFilter filter;

        IgnoringFeatureMatcher(FeatureFilter filter) {
            this.filter = filter;
        }

        @Override
        public boolean isForFeature(EObject expectedObject, EStructuralFeature feature) {
            return filter.appliesTo(expectedObject, feature);
        }

        @Override
        public Consumer<Description> getMismatch(Object expectedValue, Object itemValue) {
            return null;
        }
    }

    @FunctionalInterface
    private interface FeatureFilter {
        boolean appliesTo(EObject expectedObject, EStructuralFeature feature);
    }

    private static final class ResourceContainmentMatcher extends TypeSafeMatcher<Resource> {
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

    private static final class ResourceInexistenceMatcher extends TypeSafeMatcher<Resource> {

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
            return !item.getResourceSet().getURIConverter().exists(item.getURI(), Collections.emptyMap());
        }
    }

    private static final class ModelTreeEqualityMatcher extends TypeSafeMatcher<EObject> {

        private final EObject expectedObject;
        private final FeatureMatcher[] featureMatchers;
        private Deque<String> navigationStack = new ArrayDeque<>();
        private Consumer<Description> mismatch;
        // Bidirectional mapping between objects which have been compared and considered equal.
        // Note that each object can be considered equal to at most one other object. This ensures that two objects
        // are considered structurally equal only if the graphs formed by all their referenced objects have the same
        // topology. This is similar to EMF's implementation of EcoreUtil.equals(EObject, EObject).
        private final Map<Object, Object> objectMapping = new HashMap<>();

        ModelTreeEqualityMatcher(EObject expectedObject, FeatureMatcher[] featureMatchers) {
            this.expectedObject = expectedObject;
            this.featureMatchers = featureMatchers;
        }

        @Override
        protected boolean matchesSafely(EObject item) {
            return equalsDeeply(expectedObject, item, false);
        }

        private FeatureMatcher findFeatureChecker(EObject expectedObject, EStructuralFeature feature) {
            for (FeatureMatcher featureChecker : featureMatchers) {
                if (featureChecker.isForFeature(expectedObject, feature)) {
                    return featureChecker;
                }
            }
            return new FeatureMatcher() {
                @Override
                public boolean isForFeature(EObject expectedObject, EStructuralFeature feature) {
                    return true;
                }

                @Override
                public Consumer<Description> getMismatch(Object expectedValue, Object itemValue) {
                    if (equalsDeeply(expectedValue, itemValue, feature.isOrdered())) {
                        return null;
                    } else {
                        return mismatch;
                    }
                }
            };
        }

        private void mapObjects(EObject expected, EObject item) {
            objectMapping.put(expected, item);
            objectMapping.put(item, expected);
        }

        private void unmapObjects(EObject expected, EObject item) {
            objectMapping.remove(expected);
            objectMapping.remove(item);
        }

        private boolean equalsDeeply(Object expected, Object item, boolean ordered) {
            if (expected instanceof EObject expectedEObject && item instanceof EObject itemEObject) {
                return eObjectsEqualDeeply(expectedEObject, itemEObject);
            } else if (expected instanceof Collection<?> expectedCollection
                    && item instanceof Collection<?> itemCollection) {
                return collectionsEqualDeeply(expectedCollection, itemCollection, ordered);
            } else if (!Objects.equals(expected, item)) {
                equalityMismatch(expected, item);
                return false;
            }
            return true;
        }

        // The comparison is done similarly to EMF's implementation of EcoreUtil.equals(EObject, EObject).
        // The difference is that we issue additional diagnostic messages if the object comparison fails at any point.
        private boolean eObjectsEqualDeeply(EObject expected, EObject item) {
            // Check if the expected object has already been compared:
            Object mappedItem = objectMapping.get(expected);
            if (mappedItem == item) {
                return true;
            } else if (mappedItem != null) {
                // The object has already been mapped to some other object.
                mismatch = description -> description
                        .appendText("did not match the topologically expected object. Expected ")
                        .appendValue(mappedItem).appendText(" (mapped and equal to ").appendValue(expected)
                        .appendText(") but found ").appendValue(item).appendText(".");
                return false;
            }

            // Check if the given item object has already been compared:
            Object mappedExpected = objectMapping.get(item);
            if (mappedExpected == expected) {
                return true;
            } else if (mappedExpected != null) {
                // The object has already been mapped to some other object.
                mismatch = description -> description.appendText(
                        "has already been compared and mapped to some other object in the expected model tree. Expected ")
                        .appendValue(expected).appendText(" but the object ").appendValue(item)
                        .appendText(" has already been mapped to ").appendValue(mappedExpected).appendText(".");
                return false;
            }

            // Check if objects are the same instance:
            if (expected == item) {
                mapObjects(expected, item);
                return true;
            }

            // Compare proxies:
            if (expected.eIsProxy()) {
                // item has to be a proxy as well and their URIs need to match:
                if (((InternalEObject) expected).eProxyURI().equals(((InternalEObject) item).eProxyURI())) {
                    mapObjects(expected, item);
                    return true;
                } else {
                    mismatch = description -> description.appendText("did not match the expected proxy. Expected ")
                            .appendValue(expected).appendText(" but found ").appendValue(item).appendText(".");
                    return false;
                }
            } else if (item.eIsProxy()) {
                mismatch = description -> description.appendText("is an unexpected proxy. Expected ")
                        .appendValue(expected).appendText(" but found ").appendValue(item).appendText(".");
                return false;
            }

            // Compare classes:
            if (expected.eClass() != item.eClass()) {
                mismatch = description -> description.appendText("had the wrong EClass. Expected ")
                        .appendValue(expected.eClass().getName()).appendText(" but found ")
                        .appendValue(item.eClass().getName()).appendText(".");
                return false;
            }

            // Consider the objects to be equal for now. This helps with situations in which the below feature
            // comparisons recursively try to compare the same objects again.
            mapObjects(expected, item);

            // Compare feature values:
            for (EStructuralFeature feature : expected.eClass().getEAllStructuralFeatures()) {
                if (feature.isDerived()) {
                    continue;
                }
                navigationStack.push("." + feature.getName());
                Consumer<Description> matcherMismatch = findFeatureChecker(expected, feature)
                        .getMismatch(expected.eGet(feature), item.eGet(feature));
                if (matcherMismatch != null) {
                    mismatch = matcherMismatch;
                    unmapObjects(expected, item);
                    return false;
                }
                navigationStack.pop();
            }
            return true;
        }

        private boolean collectionsEqualDeeply(Collection<?> expected, Collection<?> item, boolean ordered) {
            if (expected.size() > item.size()) {
                mismatch = description -> description.appendText("did not contain enough elements. Expected ")
                        .appendValue(expected.size()).appendText(" elements but found only ")
                        .appendValue(item.size()).appendText(".");
                return false;
            } else if (expected.size() < item.size()) {
                mismatch = description -> description.appendText("contained too many elements. Expected ")
                        .appendValue(expected.size()).appendText(" elements but found ").appendValue(item.size())
                        .appendText(".");
                return false;
            }
            int count = 0;
            Iterator<?> expectedIter = expected.iterator();
            Collection<?> itemOrdered = ordered ? item : new ArrayList<>(item);
            Iterator<?> itemIter = itemOrdered.iterator();
            boolean[] usedItemIndices = ordered ? null : new boolean[itemOrdered.size()];

            while (expectedIter.hasNext()) {
                navigationStack.push("[" + count + "]");
                Object expectedElement = expectedIter.next();
                // Capture the current navigation stack:
                Deque<String> originalNavigationStack = new ArrayDeque<>(navigationStack);
                if (!equalsDeeply(expectedElement, itemIter.next(), false)) {
                    if (ordered) {
                        return false;
                    }
                    // Capture the original mismatch:
                    StringDescription originalMismatchDescription = new StringDescription();
                    describeMismatch(originalMismatchDescription, navigationStack, mismatch);

                    // If not ordered, retry with all elements not matched yet.
                    if (!containsDeepEqual(itemOrdered, expectedElement, usedItemIndices)) {
                        int notFoundCount = count;
                        // Restore the original navigation stack:
                        navigationStack = originalNavigationStack;
                        navigationStack.pop();
                        mismatch = description -> description
                                .appendText("did not contain an element equal to the " + notFoundCount
                                        + ". element (")
                                .appendValue(expectedElement).appendText("). Original mismatch: ")
                                .appendText(originalMismatchDescription.toString());
                        return false;
                    } else {
                        // Restore the original navigation stack and continue:
                        navigationStack = originalNavigationStack;
                    }
                } else if (!ordered) {
                    usedItemIndices[count] = true;
                }
                navigationStack.pop();
                count++;
            }
            return true;
        }

        private boolean containsDeepEqual(Collection<?> unordered, Object expected, boolean[] usedItemIndices) {
            int itemCount = 0;
            for (Object unorderedItem : unordered) {
                if (equalsDeeply(unorderedItem, expected, false) && !usedItemIndices[itemCount]) {
                    usedItemIndices[itemCount] = true;
                    return true;
                }
                itemCount++;
            }
            return false;
        }

        private void equalityMismatch(Object expected, Object item) {
            mismatch = description -> description.appendText("had the wrong value. Expected ")
                    .appendValue(expected).appendText(" but found ").appendValue(item);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("An EObject deeply equal to ").appendValue(expectedObject);
        }

        @Override
        protected void describeMismatchSafely(EObject item, Description mismatchDescription) {
            describeMismatch(mismatchDescription, navigationStack, mismatch);
        }

        private static void describeMismatch(Description description, Deque<String> navigationStack,
                Consumer<Description> mismatch) {
            if (navigationStack.isEmpty()) {
                description.appendText("The EObject ");
            } else {
                StringBuilder path = new StringBuilder();
                navigationStack.descendingIterator().forEachRemaining(path::append);
                description.appendText("The element at object" + path + " ");
            }
            mismatch.accept(description);
        }
    }
}

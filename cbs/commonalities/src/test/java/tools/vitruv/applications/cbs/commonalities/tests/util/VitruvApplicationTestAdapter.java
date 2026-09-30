package tools.vitruv.applications.cbs.commonalities.tests.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Interface to internals of {@link VitruvApplicationTest} for testing
 * related helpers.
 */
public interface VitruvApplicationTestAdapter {

	Resource getResourceAt(String modelPathInProject);

	Resource getTestResource(String resourcePath);

	<T extends EObject> T at(Class<T> type, URI uri);

	void createAndSynchronizeModel(String modelPathInProject, EObject rootElement);
}

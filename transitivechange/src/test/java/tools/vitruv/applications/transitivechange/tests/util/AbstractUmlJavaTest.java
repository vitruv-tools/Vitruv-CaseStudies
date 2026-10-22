package tools.vitruv.applications.transitivechange.tests.util;

import com.google.common.collect.Iterables;
import java.util.function.Function;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.applications.testutility.integration.NonTransactionalVitruvApplicationTest;
import tools.vitruv.applications.util.temporary.java.JavaSetup;
import tools.vitruv.change.testutils.RegisterMetamodelsInStandalone;

/**
 * Abstract class for umljava tests in both directions.
 * Initializes java and uml domain.
 *
 * @author Fei
 */
@ExtendWith(RegisterMetamodelsInStandalone.class)
public abstract class AbstractUmlJavaTest extends NonTransactionalVitruvApplicationTest {
	private static final Logger logger = Logger.getLogger(NonTransactionalVitruvApplicationTest.class.getSimpleName());

	protected final Function<URI, Resource> resourceRetriever = uri -> resourceAt(uri);

	@BeforeAll
	public static void setupJavaFactories() {
		JavaSetup.prepareFactories();
	}

	@BeforeEach
	public final void setupJavaClasspath() {
		JavaSetup.resetClasspathAndRegisterStandardLibrary();
	}

	/**
	 * Retrieves all corresponding objects of obj.
	 *
	 * @param obj the object for which the corresponding objects should be retrieved
	 * @return the corresponding objects of obj or null if none could be found
	 * @throws IllegalArgumentException if obj is null
	 */
	protected Iterable<EObject> getCorrespondingObjectList(EObject obj) {
		if (obj == null) {
			throw new IllegalArgumentException("Cannot retrieve correspondence for null");
		}
		Iterable<EObject> corrList = getCorrespondingEObjects(obj, EObject.class);
		if (isNullOrEmpty(corrList)) {
			logger.warn("No Correspondences found for " + obj);
			return null;
		}
		return corrList;
	}

	/**
	 * Retrieves all corresponding objects of obj and filters the result list by the class c
	 *
	 * {@link #getCorrespondingObjectList(EObject)}
	 * @param obj the object for which the corresponding objects should be retrieved
	 * @return the corresponding objects of obj filtered by c or null if none could be found
	 */
	protected <T extends EObject> Iterable<T> getCorrespondingObjectListWithClass(EObject obj, Class<T> c) {
		Iterable<EObject> correspondingObjectList = getCorrespondingObjectList(obj);
		return correspondingObjectList == null ? null : Iterables.filter(correspondingObjectList, c);
	}

	/**
	 * Retrieves all corresponding objects of obj, filters the result list by the class c
	 * and returns the first element of the remaining list
	 *
	 * {@link #getCorrespondingObjectList(EObject)}
	 * @param obj the object for which the first corresponding object should be retrieved
	 * @return the first corresponding object of obj or null if none could be found
	 */
	protected <T extends EObject> T getFirstCorrespondingObjectWithClass(EObject obj, Class<T> c) {
		Iterable<T> correspondingObjectList = getCorrespondingObjectListWithClass(obj, c);
		if (isNullOrEmpty(correspondingObjectList)) {
			logger.warn("There are no corresponding objects for " + obj + " of the type " + c.getClass() +
				". Returning null.");
			return null;
		} else if (Iterables.size(correspondingObjectList) > 1) {
			logger.warn("There are more than one corresponding object for " + obj + " of the type " + c.getClass() +
				". Returning the first.");
		}
		return Iterables.getFirst(correspondingObjectList, null);
	}

	protected static boolean isNullOrEmpty(Iterable<?> iterable) {
		return iterable == null || Iterables.isEmpty(iterable);
	}
}

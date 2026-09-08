package tools.vitruv.applications.testutility.integration

import org.eclipse.emf.ecore.EObject

/** Provides read access to correspondences produced by a Vitruv test. */
interface CorrespondenceRetriever {
	def <T extends EObject> Iterable<T> getCorrespondingEObjects(EObject eObject, Class<T> type)

	def <T extends EObject> Iterable<T> getCorrespondingEObjects(EObject eObject, Class<T> type, String tag)
}

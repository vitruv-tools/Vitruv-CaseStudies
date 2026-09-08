package tools.vitruv.applications.testutility.integration

import edu.kit.ipd.sdq.activextendannotations.DelegateExcept
import java.nio.file.Path
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import tools.vitruv.change.testutils.views.NonTransactionalTestView
import tools.vitruv.change.testutils.views.TestView
import tools.vitruv.framework.testutils.integration.DefaultVirtualModelBasedTestView
import tools.vitruv.framework.testutils.integration.VitruvApplicationTest
import tools.vitruv.framework.vsum.internal.InternalVirtualModel

import static com.google.common.base.Preconditions.checkArgument
import static extension tools.vitruv.change.atomic.hid.ObjectResolutionUtil.getHierarchicUriFragment

/**
 * Adapts Vitruv 4's supported application test API for tests that need to
 * explicitly record and propagate changes.
 */
abstract class NonTransactionalVitruvApplicationTest extends VitruvApplicationTest implements NonTransactionalTestView, CorrespondenceRetriever {
	@DelegateExcept(TestView)
	NonTransactionalTestView testView

	override generateTestView(Path testProjectPath, Path vsumPath) {
		val testView = new DefaultVirtualModelBasedTestView(testProjectPath, vsumPath, changePropagationSpecifications,
			uriMode)
		testView.disposeViewResourcesAfterPropagation = false
		this.testView = testView
		return testView
	}

	override <T extends EObject> Iterable<T> getCorrespondingEObjects(EObject object, Class<T> type, String tag) {
		checkArgument(object !== null, "object must not be null")
		val resolvedObject = object.resolveInVirtualModel
		if (resolvedObject === null) {
			emptyList
		} else {
			internalVirtualModel.correspondenceModel.getCorrespondingEObjects(resolvedObject, tag).filter(type)
		}
	}

	override <T extends EObject> Iterable<T> getCorrespondingEObjects(EObject object, Class<T> type) {
		getCorrespondingEObjects(object, type, null)
	}

	private def getInternalVirtualModel() {
		virtualModel as InternalVirtualModel
	}

	private def dispatch EObject resolveInVirtualModel(EObject object) {
		if (object.eResource !== null) {
			internalVirtualModel.getModelInstance(object.eResource.URI).resource.getEObject(object.hierarchicUriFragment)
		}
	}

	private def dispatch EObject resolveInVirtualModel(EClass eClass) {
		eClass
	}
}

package tools.vitruv.applications.testutility.integration;

import static com.google.common.base.Preconditions.checkArgument;
import static tools.vitruv.change.atomic.hid.ObjectResolutionUtil.getHierarchicUriFragment;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.testutils.views.NonTransactionalTestView;
import tools.vitruv.framework.testutils.integration.DefaultVirtualModelBasedTestView;
import tools.vitruv.framework.testutils.integration.VirtualModelBasedTestView;
import tools.vitruv.framework.testutils.integration.VitruvApplicationTest;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

/**
 * Adapts Vitruv 4's supported application test API for tests that need to
 * explicitly record and propagate changes.
 */
public abstract class NonTransactionalVitruvApplicationTest extends VitruvApplicationTest
		implements NonTransactionalTestView, CorrespondenceRetriever {
	private NonTransactionalTestView testView;

	@Override
	public VirtualModelBasedTestView generateTestView(Path testProjectPath, Path vsumPath) {
		var testView = new DefaultVirtualModelBasedTestView(testProjectPath, vsumPath,
			getChangePropagationSpecifications(), getUriMode());
		testView.setDisposeViewResourcesAfterPropagation(false);
		this.testView = testView;
		return testView;
	}

	@Override
	public <T extends EObject> Iterable<T> getCorrespondingEObjects(EObject object, Class<T> type, String tag) {
		checkArgument(object != null, "object must not be null");
		EObject resolvedObject = resolveInVirtualModel(object);
		if (resolvedObject == null) {
			return Collections.emptyList();
		} else {
			return Iterables.filter(
				getInternalVirtualModel().getCorrespondenceModel().getCorrespondingEObjects(resolvedObject, tag), type);
		}
	}

	@Override
	public <T extends EObject> Iterable<T> getCorrespondingEObjects(EObject object, Class<T> type) {
		return getCorrespondingEObjects(object, type, null);
	}

	private InternalVirtualModel getInternalVirtualModel() {
		return (InternalVirtualModel) getVirtualModel();
	}

	private EObject resolveInVirtualModel(EObject object) {
		if (object instanceof EClass eClass) {
			return eClass;
		}
		if (object.eResource() != null) {
			return getInternalVirtualModel().getModelInstance(object.eResource().getURI()).getResource()
				.getEObject(getHierarchicUriFragment(object));
		}
		return null;
	}

	// Delegation of NonTransactionalTestView methods (except those of TestView) to the test view

	@Override
	public void close() throws Exception {
		testView.close();
	}

	@Override
	public void disposeViewResources() {
		testView.disposeViewResources();
	}

	@Override
	public List<PropagatedChange> propagate() {
		return testView.propagate();
	}

	@Override
	public void setDisposeViewResourcesAfterPropagation(boolean disposeViewResourcesAfterPropagation) {
		testView.setDisposeViewResourcesAfterPropagation(disposeViewResourcesAfterPropagation);
	}

	@Override
	public <T extends Notifier> T startRecordingChanges(T notifier) {
		return testView.startRecordingChanges(notifier);
	}

	@Override
	public <T extends Notifier> T stopRecordingChanges(T notifier) {
		return testView.stopRecordingChanges(notifier);
	}
}

package tools.vitruv.applications.demo.insurancefamilies.tests.util;

import java.util.Collection;
import java.util.LinkedHashSet;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.dsls.testutils.TestModel;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.changederivation.StateBasedChangeResolutionStrategy;

public final class TestModelViewAdapter<T extends EObject> implements TestModel<T>, View {
	private final View actualView;
	private final Class<T> rootElementType;

	private TestModelViewAdapter(View view, Class<T> rootElementType) {
		actualView = view;
		this.rootElementType = rootElementType;
	}

	@Override
	public Class<T> getRootElementType() {
		return rootElementType;
	}

	@Override
	public Collection<T> getTypedRootObjects() {
		LinkedHashSet<T> typedRootObjects = new LinkedHashSet<>();
		for (EObject rootObject : getRootObjects()) {
			if (rootElementType.isInstance(rootObject)) {
				typedRootObjects.add(rootElementType.cast(rootObject));
			}
		}
		return typedRootObjects;
	}

	public static <E extends EObject> TestModel<E> createTestModelAdapter(View view, Class<E> rootElementType) {
		return new TestModelViewAdapter<>(view, rootElementType);
	}

	// Delegation of the View methods to the actual view

	@Override
	public void close() throws Exception {
		actualView.close();
	}

	@Override
	public Collection<EObject> getRootObjects() {
		return actualView.getRootObjects();
	}

	@Override
	public <S> Collection<S> getRootObjects(Class<S> clazz) {
		return actualView.getRootObjects(clazz);
	}

	@Override
	public ViewSelection getSelection() {
		return actualView.getSelection();
	}

	@Override
	public ViewType<? extends ViewSelector> getViewType() {
		return actualView.getViewType();
	}

	@Override
	public boolean isClosed() {
		return actualView.isClosed();
	}

	@Override
	public boolean isModified() {
		return actualView.isModified();
	}

	@Override
	public boolean isOutdated() {
		return actualView.isOutdated();
	}

	@Override
	public void moveRoot(EObject object, URI newLocation) {
		actualView.moveRoot(object, newLocation);
	}

	@Override
	public void registerRoot(EObject object, URI persistAt) {
		actualView.registerRoot(object, persistAt);
	}

	@Override
	public void update() {
		actualView.update();
	}

	@Override
	public CommittableView withChangeDerivingTrait() {
		return actualView.withChangeDerivingTrait();
	}

	@Override
	public CommittableView withChangeDerivingTrait(StateBasedChangeResolutionStrategy changeResolutionStrategy) {
		return actualView.withChangeDerivingTrait(changeResolutionStrategy);
	}

	@Override
	public CommittableView withChangeRecordingTrait() {
		return actualView.withChangeRecordingTrait();
	}
}

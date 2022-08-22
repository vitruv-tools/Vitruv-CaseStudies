package tools.vitruv.applications.demo.insurancefamilies.tests.util

import tools.vitruv.framework.views.View
import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.emf.ecore.EObject
import tools.vitruv.dsls.testutils.TestModel

final class TestModelViewAdapter<T extends EObject> implements TestModel<T>, View {
	@Delegate val View actualView
	val Class<T> rootElementType

	private new(View view, Class<T> rootElementType) {
		actualView = view
		this.rootElementType = rootElementType
	}

	override getRootElementType() {
		return rootElementType
	}

	override getTypedRootObjects() {
		rootObjects.filter(rootElementType).toSet
	}

	static def <E extends EObject> TestModel<E> createTestModelAdapter(View view, Class<E> rootElementType) {
		return new TestModelViewAdapter(view, rootElementType)
	}

}

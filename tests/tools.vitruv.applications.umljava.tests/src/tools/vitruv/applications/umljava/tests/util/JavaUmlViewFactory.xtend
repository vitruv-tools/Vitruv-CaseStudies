package tools.vitruv.applications.umljava.tests.util

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.framework.vsum.views.ViewProvider
import org.eclipse.uml2.uml.Model
import org.emftext.language.java.containers.CompilationUnit
import java.util.Collection
import tools.vitruv.framework.vsum.views.ViewTypeFactory
import tools.vitruv.framework.vsum.views.View
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.not
import static org.hamcrest.CoreMatchers.equalTo
import org.emftext.language.java.containers.Package

@FinalFieldsConstructor
class JavaUmlViewFactory {
	val ViewProvider viewProvider

	private def View createUmlView() {
		createViewOfElements("UML", #{Model})
	}

	private def View createJavaView() {
		createViewOfElements("Java packages and classes", #{Package, CompilationUnit})
	}

	private def View createUmlAndJavaClassesView() {
		createViewOfElements("UML and Java classes", #{CompilationUnit, Model})
	}

	private def View createUmlAndJavaPackagesView() {
		createViewOfElements("UML and Java packages", #{Package, Model})
	}

	private def View createViewOfElements(String viewName, Collection<Class<?>> rootTypes) {
		val selector = viewProvider.createSelector(ViewTypeFactory.createIdentityMappingViewType(viewName))

		for (rootElement : selector.selectableElements.filter[element|rootTypes.exists[it.isInstance(element)]]) {
			selector.setSelected(rootElement, true)
		}
		val view = selector.createView()
		assertThat("view must not be null", view, not(equalTo(null)))
		return view
	}

	/**
	 * Changes the given view according to the given modification function, commits the performed changes
	 * and closes the view afterwards.
	 */
	private def void changeView(View view, (View)=>void modelModification) {
		modelModification.apply(view)
		view.commitChanges()
		view.close()
	}

	/**
	 * Changes the UML view containing all UML models as root elements according to the given modification 
	 * function, commits the performed changes and closes the view afterwards.
	 */
	def void changeUmlView((View)=>void modelModification) {
		changeView(createUmlView, modelModification)
	}

	/**
	 * Changes the Java view containing all Java packages and classes as root elements according to the 
	 * given modification function, commits the performed changes and closes the view afterwards.
	 */
	def void changeJavaView((View)=>void modelModification) {
		changeView(createJavaView, modelModification)
	}

	/**
	 * Validates the given view by applying the validation function and closes the view afterwards.
	 */
	private def void validateView(View view, (View)=>void viewValidation) {
		viewValidation.apply(view)
		view.close()
	}

	/**
	 * Validates the UML view containing all UML models by applying the validation function
	 * and closes the view afterwards.
	 */
	def void validateUmlView((View)=>void viewValidation) {
		validateView(createUmlView, viewValidation)
	}

	/**
	 * Validates the Java view containing all packages and classes by applying the validation function
	 * and closes the view afterwards.
	 */
	def void validateJavaView((View)=>void viewValidation) {
		validateView(createJavaView, viewValidation)
	}

	/**
	 * Validates the Java and UML view containing all UML models and Java classes by applying the 
	 * validation function and closes the view afterwards.
	 */
	def void validateUmlAndJavaClassesView((View)=>void viewValidation) {
		validateView(createUmlAndJavaClassesView, viewValidation)
	}

	/**
	 * Validates the Java and UML view containing all UML models and Java packages by applying the 
	 * validation function and closes the view afterwards.
	 */
	def void validateUmlAndJavaPackagesView((View)=>void viewValidation) {
		validateView(createUmlAndJavaPackagesView, viewValidation)
	}

}

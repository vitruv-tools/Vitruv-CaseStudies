package tools.vitruv.applications.umljava.tests.util

import org.eclipse.uml2.uml.Model
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.Package
import tools.vitruv.framework.views.View
import tools.vitruv.testutils.TestViewFactory

@FinalFieldsConstructor
class JavaUmlViewFactory extends TestViewFactory {
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

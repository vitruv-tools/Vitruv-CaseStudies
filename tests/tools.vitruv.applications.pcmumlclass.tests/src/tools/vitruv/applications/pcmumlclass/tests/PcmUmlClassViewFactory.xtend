package tools.vitruv.applications.pcmumlclass.tests

import tools.vitruv.testutils.TestViewFactory
import tools.vitruv.framework.views.View
import org.eclipse.uml2.uml.Model
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.system.System

@FinalFieldsConstructor
class PcmUmlClassViewFactory extends TestViewFactory {
	
	private def View createUmlView() {
		createViewOfElements("UML", #{Model})
	}

	private def View createPcmView() {
		createViewOfElements("Pcm packages and components", #{Repository, RepositoryComponent})
	}

	private def View createUmlAndPcmClassesView() {
		createViewOfElements("UML and Pcm components", #{RepositoryComponent, Model})
	}

	private def View createUmlAndPcmPackagesView() {
		createViewOfElements("UML and Pcm packages", #{Repository, Model})
	}
	
	private def View createUmlAndPcmSystemView() {
		createViewOfElements("UML and Pcm packages", #{System, Model})
	}

	/**
	 * Changes the UML view containing all UML models as root elements 
	 * according to the given modification function. 
	 * Records the performed changes, commits the recorded changes, and closes the view afterwards.
	 */
	def void changeUmlView((View)=>void modelModification) {
		changeViewRecordingChanges(createUmlView, modelModification)
	}

	/**
	 * Changes the Java view containing all Java packages and classes as root elements 
	 * according to the given modification function. 
	 * Records the performed changes, commits the recorded changes, and closes the view afterwards.
	 */
	def void changePcmView((View)=>void modelModification) {
		changeViewRecordingChanges(createPcmView, modelModification)
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
	def void validatePcmView((View)=>void viewValidation) {
		validateView(createPcmView, viewValidation)
	}

	/**
	 * Validates the Java and UML view containing all UML models and Java classes by applying the 
	 * validation function and closes the view afterwards.
	 */
	def void validateUmlAndPcmClassesView((View)=>void viewValidation) {
		validateView(createUmlAndPcmClassesView, viewValidation)
	}

	/**
	 * Validates the Java and UML view containing all UML models and Java packages by applying the 
	 * validation function and closes the view afterwards.
	 */
	def void validateUmlAndPcmPackagesView((View)=>void viewValidation) {
		validateView(createUmlAndPcmPackagesView, viewValidation)
	}
	
	def void validateUmlAndPcmSystemView((View)=>void viewValidation) {
		validateView(createUmlAndPcmSystemView, viewValidation)
	}
	
}
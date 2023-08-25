package tools.vitruv.applications.SimuLinkAutoSAR.tests.util

import autoSAR.AutoSARModel
import simulink.SimulinkModel
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.framework.views.View
import tools.vitruv.testutils.TestViewFactory


@FinalFieldsConstructor
class SimuLinkAutoSARViewFactory extends TestViewFactory {
	private def View createAutoSARView() {
		createViewOfElements("AutoSAR", #{AutoSARModel})
	}

	private def View createSimuLinkView() {
		createViewOfElements("SimuLink", #{SimulinkModel})
	}

	private def View createAutoSARAndSimuLinkModelView() {
		createViewOfElements("AutoSAR and SimuLink classes", #{AutoSARModel, SimulinkModel})
	}


	/**
	 * Changes the AutoSAR view containing all AutoSAR models as root elements 
	 * according to the given modification function. 
	 * Records the performed changes, commits the recorded changes, and closes the view afterwards.
	 */
	def void changeAutoSARView((View)=>void modelModification) {
		changeViewRecordingChanges(createAutoSARView, modelModification)
	}

	/**
	 * Changes the SimuLink view containing all SimuLink packages and classes as root elements 
	 * according to the given modification function. 
	 * Records the performed changes, commits the recorded changes, and closes the view afterwards.
	 */
	def void changeSimuLinkView((View)=>void modelModification) {
		changeViewRecordingChanges(createSimuLinkView, modelModification)
	}

	/**
	 * Validates the AutoSAR view containing all AutoSAR models by applying the validation function
	 * and closes the view afterwards.
	 */
	def void validateAutoSARView((View)=>void viewValidation) {
		validateView(createAutoSARView, viewValidation)
	}

	/**
	 * Validates the SimuLink view containing all packages and classes by applying the validation function
	 * and closes the view afterwards.
	 */
	def void validateSimuLinkView((View)=>void viewValidation) {
		validateView(createSimuLinkView, viewValidation)
	}

	/**
	 * Validates the SimuLink and AutoSAR view containing all AutoSAR models and SimuLink models by applying the 
	 * validation function and closes the view afterwards.
	 */
	def void validateAutoSARAndSimuLinkClassesView((View)=>void viewValidation) {
		validateView(createAutoSARAndSimuLinkModelView, viewValidation)
	}

	
}

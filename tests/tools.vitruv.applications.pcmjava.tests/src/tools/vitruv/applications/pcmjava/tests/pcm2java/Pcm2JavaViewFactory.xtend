package tools.vitruv.applications.pcmjava.tests.pcm2java

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.mdsd.jamopp.model.java.containers.CompilationUnit
import tools.mdsd.jamopp.model.java.containers.Package
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.system.System
import tools.vitruv.framework.views.View
import tools.vitruv.testutils.TestViewFactory

@FinalFieldsConstructor
class Pcm2JavaViewFactory extends TestViewFactory {

	private def View createJavaView() {
		createViewOfElements("Java", #{Package, CompilationUnit})
	}

	private def View createPcmView() {
		createViewOfElements("PCM", #{Repository, System})
	}

	def void changeJavaView((View)=>void modelModification) {
		changeViewRecordingChanges(createJavaView, modelModification)
	}

	def void changePcmView((View)=>void modelModification) {
		changeViewRecordingChanges(createPcmView, modelModification)
	}

	def void validateJavaView((View)=>void viewValidation) {
		validateView(createJavaView, viewValidation)
	}

	def void validatePcmView((View)=>void viewValidation) {
		validateView(createPcmView, viewValidation)
	}
}

package tools.vitruv.applications.pcmjava.tests.pcm2java

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.testutils.TestViewFactory
import tools.vitruv.framework.views.View
import org.palladiosimulator.pcm.repository.Repository

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimSinglePcmRepository
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.EmptyModel

@FinalFieldsConstructor
class Pcm2JavaViewFactory extends TestViewFactory {
	
	private def View createJavaView(){
		createViewOfElements("Java", #{Package, CompilationUnit, EmptyModel})
	}
	
	private def View createPcmView(){
		createViewOfElements("PCM", #{Repository})
	}
	
	def void changeJavaView((View)=> void modelModification){
		changeViewRecordingChanges(createJavaView, modelModification)
	}
	
	def void changePcmView((View)=> void modelModification){
		changeViewRecordingChanges(createPcmView, modelModification)
	}
	
	def void validateJavaView((View)=> void viewValidation){
		validateView(createJavaView, viewValidation)
	}
	
	def void validatePcmView((View)=> void viewValidation){
		validateView(createPcmView, viewValidation)
	}
	
	// === modification helper ===
	
	def void modifySingleRepository(View view, (Repository)=> void modification){
		var repository = claimSinglePcmRepository(view)
		modification.apply(repository)
	}
}

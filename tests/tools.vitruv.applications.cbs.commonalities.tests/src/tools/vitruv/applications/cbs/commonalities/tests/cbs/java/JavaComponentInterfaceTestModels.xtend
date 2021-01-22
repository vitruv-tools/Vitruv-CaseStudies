package tools.vitruv.applications.cbs.commonalities.tests.cbs.java

import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.modifiers.ModifiersFactory
import tools.vitruv.applications.cbs.commonalities.tests.cbs.ComponentInterfaceTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.*

class JavaComponentInterfaceTestModels extends JavaTestModelsBase implements ComponentInterfaceTest.DomainModels {

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override emptyComponentInterfaceCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val contractsPackage = javaRepositoryModel.contractsPackage

			val javaInterface = ClassifiersFactory.eINSTANCE.createInterface => [
				name = INTERFACE_NAME
				annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
			]
			val compilationUnit = contractsPackage.newCompilationUnit(javaInterface)

			return (javaRepositoryModel.rootObjects + #[
				compilationUnit
			]).toList
		]
	}
}

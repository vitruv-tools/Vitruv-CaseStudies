package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml

import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.applications.cbs.commonalities.tests.cbs.ComponentInterfaceTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase

class UmlComponentInterfaceTestModels extends UmlTestModelsBase implements ComponentInterfaceTest.DomainModels {

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override emptyComponentInterfaceCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.contractsPackage => [
				packagedElements += UMLFactory.eINSTANCE.createInterface => [
					name = INTERFACE_NAME
				]
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}
}

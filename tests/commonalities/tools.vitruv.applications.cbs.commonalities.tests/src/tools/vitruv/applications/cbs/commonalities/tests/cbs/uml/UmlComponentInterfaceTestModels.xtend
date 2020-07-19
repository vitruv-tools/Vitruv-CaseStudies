package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml

import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractComponentInterfaceTest
import tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

class UmlComponentInterfaceTestModels extends UmlTestModelsBase implements AbstractComponentInterfaceTest.DomainModels {

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

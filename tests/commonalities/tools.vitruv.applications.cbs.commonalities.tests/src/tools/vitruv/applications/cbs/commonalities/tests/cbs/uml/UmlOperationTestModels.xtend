package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml

import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.applications.cbs.commonalities.tests.cbs.OperationTest
import tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

class UmlOperationTestModels extends UmlTestModelsBase implements OperationTest.DomainModels {

	private static def newUmlInterface() {
		return UMLFactory.eINSTANCE.createInterface => [
			name = INTERFACE_NAME
		]
	}

	private static def newUmlOperation() {
		return UMLFactory.eINSTANCE.createOperation => [
			name = OPERATION_NAME
		]
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override emptyOperationCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.contractsPackage => [
				packagedElements += newUmlInterface => [
					ownedOperations += newUmlOperation
				]
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}
}

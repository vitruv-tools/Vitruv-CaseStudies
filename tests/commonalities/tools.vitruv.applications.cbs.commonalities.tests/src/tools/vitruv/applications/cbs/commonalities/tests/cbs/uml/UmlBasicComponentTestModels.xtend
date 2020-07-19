package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml

import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractBasicComponentTest
import tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

class UmlBasicComponentTestModels extends UmlTestModelsBase implements AbstractBasicComponentTest.DomainModels {

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override emptyBasicComponentCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.repositoryPackage => [
				packagedElements += UMLFactory.eINSTANCE.createPackage => [
					name = COMPONENT_NAME.toFirstLower
					packagedElements += UMLFactory.eINSTANCE.createClass => [
						name = COMPONENT_NAME + 'Impl'
					]
				]
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}
}

package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml

import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.cbs.commonalities.tests.cbs.BasicComponentTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase

class UmlBasicComponentTestModels extends UmlTestModelsBase implements BasicComponentTest.DomainModels {

	private static def newUmlComponentPackage() {
		return UMLFactory.eINSTANCE.createPackage => [
			name = COMPONENT_NAME.toFirstLower
		]
	}

	private static def newUmlComponentClass() {
		return UMLFactory.eINSTANCE.createClass => [
			name = COMPONENT_NAME + 'Impl'
			visibility = VisibilityKind.PUBLIC_LITERAL
		]
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override emptyBasicComponentCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()
			umlRepositoryModel.repositoryPackage => [
				packagedElements += newUmlComponentPackage => [
					packagedElements += newUmlComponentClass
				]
			]
			return #[
				umlRepositoryModel.model
			]
		]
	}
}

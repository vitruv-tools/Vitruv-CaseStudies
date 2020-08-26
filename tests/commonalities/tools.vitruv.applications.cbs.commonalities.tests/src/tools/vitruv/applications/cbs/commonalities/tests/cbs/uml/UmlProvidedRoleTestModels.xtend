package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml

import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.cbs.commonalities.tests.cbs.ProvidedRoleTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase

class UmlProvidedRoleTestModels extends UmlTestModelsBase implements ProvidedRoleTest.DomainModels {

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

	private static def newUmlInterfaceRealization() {
		return UMLFactory.eINSTANCE.createInterfaceRealization
	}

	private static def newUmlInterface1() {
		return UMLFactory.eINSTANCE.createInterface => [
			name = INTERFACE_1_NAME
			visibility = VisibilityKind.PUBLIC_LITERAL
		]
	}

	private static def newUmlInterface2() {
		return newUmlInterface1 => [
			name = INTERFACE_2_NAME
		]
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override componentWithProvidedRoleCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()
			val interface1 = newUmlInterface1
			umlRepositoryModel.contractsPackage => [
				packagedElements += interface1
			]
			umlRepositoryModel.repositoryPackage => [
				packagedElements += newUmlComponentPackage => [
					packagedElements += newUmlComponentClass => [
						interfaceRealizations += newUmlInterfaceRealization => [
							contract = interface1
						]
					]
				]
			]
			return #[
				umlRepositoryModel.model
			]
		]
	}

	override componentWithMultipleProvidedRolesCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()
			val interface1 = newUmlInterface1
			val interface2 = newUmlInterface2
			umlRepositoryModel.contractsPackage => [
				packagedElements += interface1
				packagedElements += interface2
			]
			umlRepositoryModel.repositoryPackage => [
				packagedElements += newUmlComponentPackage => [
					packagedElements += newUmlComponentClass => [
						interfaceRealizations += newUmlInterfaceRealization => [
							contract = interface1
						]
						interfaceRealizations += newUmlInterfaceRealization => [
							contract = interface2
						]
					]
				]
			]
			return #[
				umlRepositoryModel.model
			]
		]
	}
}

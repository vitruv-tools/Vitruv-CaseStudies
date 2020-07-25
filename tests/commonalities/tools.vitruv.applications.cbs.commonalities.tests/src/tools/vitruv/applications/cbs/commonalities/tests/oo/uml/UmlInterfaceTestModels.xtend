package tools.vitruv.applications.cbs.commonalities.tests.oo.uml

import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.applications.cbs.commonalities.tests.oo.InterfaceTest
import tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.*

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlModelHelper.*

class UmlInterfaceTestModels extends UmlTestModelsBase implements InterfaceTest.DomainModels {

	private static def newUmlPackage1() {
		return UMLFactory.eINSTANCE.createPackage => [
			name = PACKAGE_1_NAME
		]
	}

	private static def newUmlPackage2() {
		return UMLFactory.eINSTANCE.createPackage => [
			name = PACKAGE_2_NAME
		]
	}

	private static def newUmlInterface1() {
		return UMLFactory.eINSTANCE.createInterface => [
			name = INTERFACE_1_NAME
		]
	}

	private static def newUmlInterface2() {
		return UMLFactory.eINSTANCE.createInterface => [
			name = INTERFACE_2_NAME
		]
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	// Empty interface

	override emptyInterfaceCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage1.withElements(newUmlInterface1))
			return #[
				umlModel
			]
		]
	}

	// Multiple interfaces

	override multipleInterfacesInSamePackageCreation() {
		return newModel [
			val umlModel = newUmlModel => [
				packagedElements += newUmlPackage1 => [
					packagedElements += newUmlInterface1
					packagedElements += newUmlInterface2
				]
			]
			return #[
				umlModel
			]
		]
	}

	override multipleInterfacesInDifferentPackagesCreation() {
		return newModel [
			val umlModel = newUmlModel => [
				packagedElements += newUmlPackage1 => [
					packagedElements += newUmlInterface1
				]
				packagedElements += newUmlPackage2 => [
					packagedElements += newUmlInterface2
				]
			]
			return #[
				umlModel
			]
		]
	}
}

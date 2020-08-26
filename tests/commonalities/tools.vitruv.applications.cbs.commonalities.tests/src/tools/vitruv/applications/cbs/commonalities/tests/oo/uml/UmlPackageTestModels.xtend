package tools.vitruv.applications.cbs.commonalities.tests.oo.uml

import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.applications.cbs.commonalities.tests.oo.PackageTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.*

class UmlPackageTestModels extends UmlTestModelsBase implements PackageTest.DomainModels {

	private static def newRoot1UmlPackage() {
		return UMLFactory.eINSTANCE.createPackage => [
			name = ROOT1_PACKAGE_NAME
		]
	}

	private static def newRoot2UmlPackage() {
		return UMLFactory.eINSTANCE.createPackage => [
			name = ROOT2_PACKAGE_NAME
		]
	}

	private static def newSub1UmlPackage() {
		return UMLFactory.eINSTANCE.createPackage => [
			name = SUB1_PACKAGE_NAME
		]
	}

	private static def newSub2UmlPackage() {
		return UMLFactory.eINSTANCE.createPackage => [
			name = SUB2_PACKAGE_NAME
		]
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override singleRootPackageCreation() {
		return newModel [
			val umlModel = newUmlModel => [
				packagedElements += newRoot1UmlPackage
			]
			return #[
				umlModel
			]
		]
	}

	override multiRootPackageCreation() {
		return newModel [
			val umlModel = newUmlModel => [
				packagedElements += newRoot1UmlPackage
				packagedElements += newRoot2UmlPackage
			]
			return #[
				umlModel
			]
		]
	}

	override subPackagesCreation() {
		return newModel [
			val umlModel = newUmlModel => [
				packagedElements += newRoot1UmlPackage => [
					packagedElements += newSub1UmlPackage
					packagedElements += newSub2UmlPackage
				]
				packagedElements += newRoot2UmlPackage => [
					packagedElements += newSub1UmlPackage
					packagedElements += newSub2UmlPackage
				]
			]
			return #[
				umlModel
			]
		]
	}
}

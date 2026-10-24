package tools.vitruv.applications.cbs.commonalities.tests.oo.uml

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.cbs.commonalities.tests.oo.ClassTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.*

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlModelHelper.*

class UmlClassTestModels extends UmlTestModelsBase implements ClassTest.DomainModels {

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

	private static def newBasicUmlClass1() {
		return UMLFactory.eINSTANCE.createClass => [
			name = CLASS_1_NAME
		]
	}

	private static def newUmlClass1() {
		return newBasicUmlClass1 => [
			visibility = VisibilityKind.PUBLIC_LITERAL
		]
	}

	private static def newUmlClass2() {
		return newUmlClass1 => [
			name = CLASS_2_NAME
		]
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

	/**
	 * Returning <code>null</code> results in no visibility being set.
	 */
	protected def VisibilityKind defaultClassVisibility() {
		return null
	}

	private def withDefaultVisibility(Class umlClass) {
		return umlClass => [
			val defaultVisibility = defaultClassVisibility
			if (defaultClassVisibility !== null) {
				visibility = defaultVisibility
			}
		]
	}

	// Empty class

	override emptyClassCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage1.withElements(newBasicUmlClass1 => [
				withDefaultVisibility
			]))
			return #[
				umlModel
			]
		]
	}

	// Visibility

	override privateClassCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage1.withElements(newUmlClass1 => [
				visibility = VisibilityKind.PRIVATE_LITERAL
			]))
			return #[
				umlModel
			]
		]
	}

	override publicClassCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage1.withElements(newUmlClass1 => [
				visibility = VisibilityKind.PUBLIC_LITERAL
			]))
			return #[
				umlModel
			]
		]
	}

	override protectedClassCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage1.withElements(newUmlClass1 => [
				visibility = VisibilityKind.PROTECTED_LITERAL
			]))
			return #[
				umlModel
			]
		]
	}

	override packagePrivateClassCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage1.withElements(newUmlClass1 => [
				visibility = VisibilityKind.PACKAGE_LITERAL
			]))
			return #[
				umlModel
			]
		]
	}

	// Modifiers

	override finalClassCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage1.withElements(newUmlClass1 => [
				isFinalSpecialization = true
			]))
			return #[
				umlModel
			]
		]
	}

	override abstractClassCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage1.withElements(newUmlClass1 => [
				isAbstract = true
			]))
			return #[
				umlModel
			]
		]
	}

	override classWithMultipleModifiersCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage1.withElements(newUmlClass1 => [
				isAbstract = true
				isFinalSpecialization = true
			]))
			return #[
				umlModel
			]
		]
	}

	// Multiple classes

	override multipleClassesInSamePackageCreation() {
		return newModel [
			val umlModel = newUmlModel => [
				packagedElements += newUmlPackage1 => [
					packagedElements += newUmlClass1
					packagedElements += newUmlClass2
				]
			]
			return #[
				umlModel
			]
		]
	}

	override multipleClassesInDifferentPackagesCreation() {
		return newModel [
			val umlModel = newUmlModel => [
				packagedElements += newUmlPackage1 => [
					packagedElements += newUmlClass1
				]
				packagedElements += newUmlPackage2 => [
					packagedElements += newUmlClass2
				]
			]
			return #[
				umlModel
			]
		]
	}

	// Super class

	override classWithSuperClassCreation() {
		return newModel [
			val umlModel = newUmlModel => [
				packagedElements += newUmlPackage1 => [
					val umlClass2 = newUmlClass2
					packagedElements += umlClass2
					packagedElements += newUmlClass1 => [
						generalizations += UMLFactory.eINSTANCE.createGeneralization => [
							general = umlClass2
						]
					]
				]
			]
			return #[
				umlModel
			]
		]
	}

	// Implemented interfaces

	override classImplementingInterfaceCreation() {
		return newModel [
			val umlModel = newUmlModel => [
				packagedElements += newUmlPackage1 => [
					val umlInterface1 = newUmlInterface1
					packagedElements += umlInterface1
					packagedElements += newUmlClass1 => [
						interfaceRealizations += UMLFactory.eINSTANCE.createInterfaceRealization => [
							contract = umlInterface1
						]
					]
				]
			]
			return #[
				umlModel
			]
		]
	}

	override classImplementingMultipleInterfacesCreation() {
		return newModel [
			val umlModel = newUmlModel => [
				packagedElements += newUmlPackage1 => [
					val umlInterface1 = newUmlInterface1
					val umlInterface2 = newUmlInterface2
					packagedElements += umlInterface1
					packagedElements += umlInterface2
					packagedElements += newUmlClass1 => [
						interfaceRealizations += UMLFactory.eINSTANCE.createInterfaceRealization => [
							contract = umlInterface1
						]
						interfaceRealizations += UMLFactory.eINSTANCE.createInterfaceRealization => [
							contract = umlInterface2
						]
					]
				]
			]
			return #[
				umlModel
			]
		]
	}
}

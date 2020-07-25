package tools.vitruv.applications.cbs.commonalities.tests.oo.uml

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.cbs.commonalities.tests.oo.ClassTest
import tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

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

	private static def newUmlClass1() {
		return UMLFactory.eINSTANCE.createClass => [
			name = CLASS_1_NAME
		]
	}

	private static def newUmlClass2() {
		return UMLFactory.eINSTANCE.createClass => [
			name = CLASS_2_NAME
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
			val umlModel = newUmlModel.withElements(newUmlPackage1.withElements(newUmlClass1 => [
				withDefaultVisibility
			]))
			return #[
				umlModel
			]
		]
	}

	// Modifiers

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

	override finalClassCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage1.withElements(newUmlClass1 => [
				withDefaultVisibility
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
				withDefaultVisibility
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
				visibility = VisibilityKind.PUBLIC_LITERAL
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
					packagedElements += newUmlClass1.withDefaultVisibility
					packagedElements += newUmlClass2.withDefaultVisibility
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
					packagedElements += newUmlClass1.withDefaultVisibility
				]
				packagedElements += newUmlPackage2 => [
					packagedElements += newUmlClass2.withDefaultVisibility
				]
			]
			return #[
				umlModel
			]
		]
	}
}

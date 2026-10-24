package tools.vitruv.applications.cbs.commonalities.tests.oo.uml

import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.cbs.commonalities.tests.oo.ClassPropertyTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase
import tools.vitruv.applications.cbs.commonalities.uml.UmlPrimitiveType

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.*

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlModelHelper.*

class UmlClassPropertyTestModels extends UmlTestModelsBase implements ClassPropertyTest.DomainModels {

	private static def newUmlPackage() {
		return UMLFactory.eINSTANCE.createPackage => [
			name = PACKAGE_NAME
		]
	}

	private static def newUmlClass() {
		return UMLFactory.eINSTANCE.createClass => [
			name = CLASS_NAME
			visibility = VisibilityKind.PUBLIC_LITERAL
		]
	}

	private static def newBasicUmlProperty() {
		return UMLFactory.eINSTANCE.createProperty => [
			name = PROPERTY_NAME
			type = UmlPrimitiveType.INTEGER.umlType // Default type
		]
	}

	private static def newUmlProperty() {
		return newBasicUmlProperty => [
			visibility = VisibilityKind.PRIVATE_LITERAL // Default visibility
		]
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	/**
	 * Returning <code>null</code> results in no visibility being set.
	 */
	protected def VisibilityKind defaultPropertyVisibility() {
		return null
	}

	private def withDefaultVisibility(Property umlProperty) {
		return umlProperty => [
			val defaultVisibility = defaultPropertyVisibility
			if (defaultVisibility !== null) {
				visibility = defaultVisibility
			}
		]
	}

	// Basic

	override basicPrimitiveClassPropertyCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedAttributes += newBasicUmlProperty => [
					withDefaultVisibility
				]
			]))
			return #[
				umlModel
			]
		]
	}

	// Visibility

	override privateClassPropertyCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedAttributes += newBasicUmlProperty => [
					visibility = VisibilityKind.PRIVATE_LITERAL
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override publicClassPropertyCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedAttributes += newBasicUmlProperty => [
					visibility = VisibilityKind.PUBLIC_LITERAL
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override protectedClassPropertyCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedAttributes += newBasicUmlProperty => [
					visibility = VisibilityKind.PROTECTED_LITERAL
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override packagePrivateClassPropertyCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedAttributes += newUmlProperty => [
					visibility = VisibilityKind.PACKAGE_LITERAL
				]
			]))
			return #[
				umlModel
			]
		]
	}

	// Modifiers

	override finalClassPropertyCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedAttributes += newUmlProperty => [
					isReadOnly = true
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override staticClassPropertyCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedAttributes += newUmlProperty => [
					isStatic = true
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override classPropertyWithMultipleModifiersCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedAttributes += newUmlProperty => [
					isStatic = true
					isReadOnly = true
				]
			]))
			return #[
				umlModel
			]
		]
	}

	// Type references

	override stringClassPropertyCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedAttributes += newUmlProperty => [
					name = STRING_PROPERTY_NAME
					type = UmlPrimitiveType.STRING.umlType
				]
			]))
			return #[
				umlModel
			]
		]
	}

	// Multiple properties

	override multiplePrimitiveClassPropertiesCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedAttributes += newUmlProperty() => [
					name = BOOLEAN_PROPERTY_NAME
					type = UmlPrimitiveType.BOOLEAN.umlType
				]
				ownedAttributes += newUmlProperty() => [
					name = INT_PROPERTY_NAME
					type = UmlPrimitiveType.INTEGER.umlType
				]
				ownedAttributes += newUmlProperty() => [
					name = DOUBLE_PROPERTY_NAME
					type = UmlPrimitiveType.REAL.umlType
				]
			]))
			return #[
				umlModel
			]
		]
	}
}

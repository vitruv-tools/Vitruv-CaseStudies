package tools.vitruv.applications.umlclassumlcomponents.tests.comp2class

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory

import static extension tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Disabled

class DataTypeTest extends AbstractComp2ClassTest {

	@Test
	def void testCreateClassForDataType() {
		userInteraction.onNextMultipleChoiceSingleSelection.respondWith("No")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createDataType() => [
				name = DATATYPE_NAME
			]
		]

		rootElement.claimPackagedElementWithName(DataType, DATATYPE_NAME)
		rootElement.claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME).
			claimPackagedElementWithName(Class, DATATYPE_NAME)
	}

	@Test
	def void testAddPropertyToDataType() {
		userInteraction.onNextMultipleChoiceSingleSelection.respondWith("No")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createDataType() => [
				name = DATATYPE_NAME
			]
		]

		rootElement.claimPackagedElementWithName(DataType, DATATYPE_NAME).propagate [
			ownedAttributes += UMLFactory.eINSTANCE.createProperty() => [
				name = PROPERTY_NAME
			]
		]

		rootElement.claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME).
			claimPackagedElementWithName(Class, DATATYPE_NAME). //
			claimOwnedAttributeWithName(PROPERTY_NAME)
	}

	@Test
	def void testRenameDataTypeProperty() {
		userInteraction.onNextMultipleChoiceSingleSelection.respondWith("No")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createDataType() => [
				name = DATATYPE_NAME
				ownedAttributes += UMLFactory.eINSTANCE.createProperty() => [
					name = PROPERTY_NAME
				]
			]
		]

		val newPropertyName = PROPERTY_NAME + "New"
		rootElement.claimPackagedElementWithName(DataType, DATATYPE_NAME).propagate [
			claimOwnedAttributeWithName(PROPERTY_NAME) => [
				name = newPropertyName
			]
		]

		rootElement.claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME).
			claimPackagedElementWithName(Class, DATATYPE_NAME). //
			claimOwnedAttributeWithName(newPropertyName)
	}

	@Test
	def void testAddOperationToDataType() {
		userInteraction.onNextMultipleChoiceSingleSelection.respondWith("No")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createDataType() => [
				name = DATATYPE_NAME
			]
		]

		rootElement.claimPackagedElementWithName(DataType, DATATYPE_NAME).propagate [
			ownedOperations += UMLFactory.eINSTANCE.createOperation() => [
				name = OPERATION_NAME
			]
		]

		rootElement.claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME).
			claimPackagedElementWithName(Class, DATATYPE_NAME). //
			claimOwnedOperationWithName(OPERATION_NAME)
	}

	@Disabled
	@Test
	def void testRenameDataTypeOperation() {
		userInteraction.onNextMultipleChoiceSingleSelection.respondWith("No")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createDataType() => [
				name = DATATYPE_NAME
				ownedOperations += UMLFactory.eINSTANCE.createOperation() => [
					name = OPERATION_NAME
				]
			]
		]

		val newOperationName = OPERATION_NAME + "New"
		rootElement.claimPackagedElementWithName(DataType, DATATYPE_NAME).propagate [
			claimOwnedOperationWithName(PROPERTY_NAME) => [
				name = newOperationName
			]
		]

		rootElement.claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME).
			claimPackagedElementWithName(Class, DATATYPE_NAME). //
			claimOwnedOperationWithName(newOperationName)
	}

}

package tools.vitruv.applications.umlclassumlcomponents.tests.class2comp

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory

import static extension tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.is

class DataTypeTest extends AbstractClass2CompTest {

	@Test
	def void testCreateDataTypeForClass() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_DATATYPES_PACKAGE_NAME»' to an existing Component''')
		].respondWith("No")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_DATATYPES_PACKAGE_NAME
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME
				]
			]
		]

		rootElement.claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME)
		rootElement.claimPackagedElementWithName(DataType, CLASS_NAME)
		assertThat(rootElement.packagedElements.size, is(2))
	}

	@Test
	def void testRenameDataType() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_DATATYPES_PACKAGE_NAME»' to an existing Component''')
		].respondWith("No")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_DATATYPES_PACKAGE_NAME
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME
				]
			]
		]

		rootElement.claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME)
		rootElement.claimPackagedElementWithName(DataType, CLASS_NAME)
		assertThat(rootElement.packagedElements.size, is(2))

		rootElement.propagate [
			claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME) => [
				claimPackagedElementWithName(Class, CLASS_NAME) => [
					name = CLASS_NAME2
				]
			]
		]

		rootElement.claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME)
		rootElement.claimPackagedElementWithName(DataType, CLASS_NAME2)
		assertThat(rootElement.packagedElements.size, is(2))
	}

	@Test
	def void testAddPropertyToDataType() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_DATATYPES_PACKAGE_NAME»' to an existing Component''')
		].respondWith("No")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_DATATYPES_PACKAGE_NAME
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME
					ownedAttributes += UMLFactory.eINSTANCE.createProperty() => [
						name = PROPERTY_NAME
					]
				]
			]
		]

		rootElement.claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME)
		val dataType = rootElement.claimPackagedElementWithName(DataType, CLASS_NAME)
		dataType.claimOwnedAttributeWithName(PROPERTY_NAME)
		assertThat(dataType.ownedAttributes.size, is(1))
		assertThat(rootElement.packagedElements.size, is(2))
	}

	@Test
	def void testRenameDataTypeProperty() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_DATATYPES_PACKAGE_NAME»' to an existing Component''')
		].respondWith("No")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_DATATYPES_PACKAGE_NAME
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME
					ownedAttributes += UMLFactory.eINSTANCE.createProperty() => [
						name = PROPERTY_NAME
					]
				]
			]
		]

		val newPropertyName = PROPERTY_NAME + "New"
		rootElement.propagate [
			claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME) => [
				claimPackagedElementWithName(Class, CLASS_NAME) => [
					claimOwnedAttributeWithName(PROPERTY_NAME) => [
						name = newPropertyName
					]
				]
			]
		]

		rootElement.claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME)
		val dataType = rootElement.claimPackagedElementWithName(DataType, CLASS_NAME)
		dataType.claimOwnedAttributeWithName(newPropertyName)
		assertThat(dataType.ownedAttributes.size, is(1))
		assertThat(rootElement.packagedElements.size, is(2))
	}

	@Test
	def void testAddOperationToDataType() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_DATATYPES_PACKAGE_NAME»' to an existing Component''')
		].respondWith("No")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_DATATYPES_PACKAGE_NAME
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME
					ownedOperations += UMLFactory.eINSTANCE.createOperation() => [
						name = OPERATION_NAME
					]
				]
			]
		]

		rootElement.claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME)
		val dataType = rootElement.claimPackagedElementWithName(DataType, CLASS_NAME)
		dataType.claimOwnedOperationWithName(OPERATION_NAME)
		assertThat(dataType.ownedOperations.size, is(1))
		assertThat(rootElement.packagedElements.size, is(2))
	}

	@Test
	def void testRenameDataTypeOperation() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_DATATYPES_PACKAGE_NAME»' to an existing Component''')
		].respondWith("No")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_DATATYPES_PACKAGE_NAME
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME
					ownedOperations += UMLFactory.eINSTANCE.createOperation() => [
						name = OPERATION_NAME
					]
				]
			]
		]

		val newOperationName = OPERATION_NAME + "New"
		rootElement.propagate [
			claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME) => [
				claimPackagedElementWithName(Class, CLASS_NAME) => [
					claimOwnedOperationWithName(OPERATION_NAME) => [
						name = newOperationName
					]
				]
			]
		]

		rootElement.claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME)
		val dataType = rootElement.claimPackagedElementWithName(DataType, CLASS_NAME)
		dataType.claimOwnedOperationWithName(newOperationName)
		assertThat(dataType.ownedOperations.size, is(1))
		assertThat(rootElement.packagedElements.size, is(2))
	}

}

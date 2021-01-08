package tools.vitruv.applications.pcmumlcomponents.tests.uml2pcm

import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.UMLFactory
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import tools.vitruv.applications.pcmumlcomponents.uml2pcm.UmlToPcmUtil

class DataTypesTest extends AbstractUmlPcmTest {

	protected val DATATYPE_NAME = "fooType"

	@Test
	def void primitiveTypeCreate() {
		val primitiveType = UMLFactory.eINSTANCE.createPrimitiveType()
		primitiveType.name = UML_TYPE_BOOL
		rootElement.packagedElements += primitiveType
		propagate
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[primitiveType]).flatten
		assertEquals(1, correspondingElements.length)
		val pcmType = correspondingElements.get(0)
		assertTrue(pcmType instanceof PrimitiveDataType)
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(primitiveType.name), (pcmType as PrimitiveDataType).type)
	}

	@Test
	def void compositeTypeCreate() {
		val typeName = DATATYPE_NAME
		val dataType = UMLFactory.eINSTANCE.createDataType()
		dataType.name = typeName
		val nrOwnedTypesBefore = rootElement.ownedTypes.length
		rootElement.packagedElements += dataType
		userInteraction.addNextSingleSelection(1)
		propagate
		assertEquals(nrOwnedTypesBefore + 1, rootElement.ownedTypes.length)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[dataType]).flatten
		assertEquals(1, correspondingElements.length)
		val pcmType = correspondingElements.get(0)
		assertTrue(pcmType instanceof CompositeDataType)
	}

	@Test
	def void compositeTypeAddProperty() {
		val dataType = UMLFactory.eINSTANCE.createDataType()
		dataType.name = DATATYPE_NAME
		rootElement.packagedElements += dataType
		userInteraction.addNextSingleSelection(1)
		val propertyName = PARAMETER_NAME
		val propertyType = UMLFactory.eINSTANCE.createPrimitiveType()
		propertyType.name = UML_TYPE_INT
		rootElement.packagedElements += propertyType
		val property = UMLFactory.eINSTANCE.createProperty()
		property.name = propertyName
		property.type = propertyType
		dataType.ownedAttributes += property
		propagate
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[dataType]).flatten
		val pcmType = (correspondingElements.get(0) as CompositeDataType)
		assertEquals(1, pcmType.innerDeclaration_CompositeDataType.length)
		assertEquals(propertyName, pcmType.innerDeclaration_CompositeDataType.get(0).entityName)
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(propertyType.name),
			(pcmType.innerDeclaration_CompositeDataType.get(0).datatype_InnerDeclaration as PrimitiveDataType).type)
	}

	protected def DataType createCompositeDataType(String name) {
		val dataType = UMLFactory.eINSTANCE.createDataType()
		dataType.name = DATATYPE_NAME
		rootElement.packagedElements += dataType
		userInteraction.addNextSingleSelection(1)
		propagate
		return dataType
	}

	protected def Property createProperty(DataType umlType, String name, String datatype) {
		val propertyType = UMLFactory.eINSTANCE.createPrimitiveType()
		propertyType.name = datatype
		rootElement.packagedElements += propertyType
		val property = UMLFactory.eINSTANCE.createProperty()
		property.name = name
		property.type = propertyType
		umlType.ownedAttributes += property
		propagate
		return property
	}

	protected def org.palladiosimulator.pcm.repository.DataType getCorrespondingDataType(DataType umlType) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlType]).flatten
		return correspondingElements.get(0) as org.palladiosimulator.pcm.repository.DataType
	}

	@Test
	def void compositeTypeChangeProperty() {
		val umlType = createCompositeDataType(DATATYPE_NAME)
		val umlProperty = createProperty(umlType, PARAMETER_NAME, UML_TYPE_INT)

		val newType = UMLFactory.eINSTANCE.createPrimitiveType()
		newType.name = UML_TYPE_BOOL
		rootElement.packagedElements += newType
		umlProperty.type = newType

		val newPropertyName = PARAMETER_NAME_2
		umlProperty.name = newPropertyName

		propagate

		var pcmType = (getCorrespondingDataType(umlType) as CompositeDataType)
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(newType.name),
			(pcmType.innerDeclaration_CompositeDataType.get(0).datatype_InnerDeclaration as PrimitiveDataType).type)
		assertEquals(newPropertyName, pcmType.innerDeclaration_CompositeDataType.get(0).entityName)
	}

	@Test
	def void compositeTypeDeleteProperty() {
		val umlType = createCompositeDataType(DATATYPE_NAME)
		val property1 = createProperty(umlType, PARAMETER_NAME, UML_TYPE_INT)
		val remainingPropertyName = PARAMETER_NAME_2
		createProperty(umlType, remainingPropertyName, UML_TYPE_REAL)

		umlType.ownedAttributes -= property1

		propagate

		var pcmType = (getCorrespondingDataType(umlType) as CompositeDataType)
		assertEquals(1, pcmType.innerDeclaration_CompositeDataType.length())
		assertEquals(remainingPropertyName, pcmType.innerDeclaration_CompositeDataType.get(0).entityName)
	}

}

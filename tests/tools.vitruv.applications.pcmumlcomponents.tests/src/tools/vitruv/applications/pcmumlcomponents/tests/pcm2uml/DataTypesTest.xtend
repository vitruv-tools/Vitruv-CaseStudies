package tools.vitruv.applications.pcmumlcomponents.tests.pcm2uml

import org.eclipse.uml2.uml.DataType
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.RepositoryFactory

import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.Operation
import static extension edu.kit.ipd.sdq.commons.util.org.palladiosimulator.pcm.repository.ParameterUtil.*
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import tools.vitruv.applications.pcmumlcomponents.pcm2uml.PcmToUmlUtil

class DataTypesTest extends AbstractPcmUmlTest {

	protected val ATTRIBUTE_NAME = "attr"
	protected val DATATYPE_NAME = "TestType"

	protected def PrimitiveDataType createPrimitiveDataType() {
		val pcmDataType = RepositoryFactory.eINSTANCE.createPrimitiveDataType()
		pcmDataType.type = PrimitiveTypeEnum.BOOL
		rootElement.dataTypes__Repository += pcmDataType
		propagate
		return pcmDataType
	}

	@Test
	def void testPrimitiveDataTypeCreate() {
		val pcmDataType = createPrimitiveDataType()
		val correspondingElements = pcmDataType.correspondingElements
		assertEquals(1, correspondingElements.length)
		val umlModel = getUmlModel()
		val umlType = umlModel.getOwnedType(PcmToUmlUtil.getUmlPrimitiveTypeName(pcmDataType.type))
		assertNotNull(umlType)
		assertEquals(PcmToUmlUtil.getUmlPrimitiveTypeName(PrimitiveTypeEnum.BOOL), umlType.name)
	}

	@Test
	def void testPrimitiveDataTypeDelete() {
		val dataTypesBefore = rootElement.dataTypes__Repository.length
		val pcmDataType = createPrimitiveDataType()
		rootElement.dataTypes__Repository.remove(pcmDataType)
		assertEquals(dataTypesBefore, rootElement.dataTypes__Repository.length)
	}

	protected def CompositeDataType createCompositeDataType(String name) {
		val pcmDataType = RepositoryFactory.eINSTANCE.createCompositeDataType()
		pcmDataType.entityName = name
		rootElement.dataTypes__Repository += pcmDataType
		propagate
		return pcmDataType
	}

	protected def CompositeDataType initCompositeDataTypeDeclaration(String name, String attribute) {
		val dataType = createCompositeDataType(name)
		val declaration = RepositoryFactory.eINSTANCE.createInnerDeclaration()
		declaration.entityName = attribute
		declaration.datatype_InnerDeclaration = createPrimitiveDataType()
		dataType.innerDeclaration_CompositeDataType += declaration
		propagate
		return dataType
	}

	@Test
	def void testCompositeDataTypeCreate() {
		val pcmDataType = createCompositeDataType(DATATYPE_NAME)
		val correspondingElements = pcmDataType.correspondingElements
		assertEquals(1, correspondingElements.length)
		val umlModel = getUmlModel()
		val umlType = umlModel.getOwnedType(pcmDataType.entityName)
		assertNotNull(umlType)
	}

	@Test
	def void testCompositeDataTypeDeclarationAdd() {
		val attributeName = ATTRIBUTE_NAME
		val pcmDataType = initCompositeDataTypeDeclaration(DATATYPE_NAME, attributeName)
		val correspondingElements = pcmDataType.correspondingElements
		val umlType = (correspondingElements.get(0) as DataType)
		assertEquals(1, umlType.ownedAttributes.length)
		assertEquals(attributeName, umlType.ownedAttributes.get(0).name)
		assertEquals(PcmToUmlUtil.getUmlPrimitiveTypeName(PrimitiveTypeEnum.BOOL),
			umlType.ownedAttributes.get(0).type.name)
	}

	@Test
	def void testCompositeDataTypeDeclarationEdit() {
		val attributeName = ATTRIBUTE_NAME
		val pcmDataType = initCompositeDataTypeDeclaration(DATATYPE_NAME, ATTRIBUTE_NAME + "2")
		val correspondingElements = pcmDataType.correspondingElements
		val declaration = pcmDataType.innerDeclaration_CompositeDataType.get(0)
		declaration.entityName = attributeName
		pcmDataType.innerDeclaration_CompositeDataType.set(0, declaration)
		propagate
		val umlType = (correspondingElements.get(0) as DataType)
		assertEquals(1, umlType.ownedAttributes.length)
		assertEquals(attributeName, umlType.ownedAttributes.get(0).name)
	}

	@Test
	def void testCompositeDataTypeDeclarationDelete() {
		val attributeName = ATTRIBUTE_NAME
		val innerType = RepositoryFactory.eINSTANCE.createPrimitiveDataType()
		innerType.type = PrimitiveTypeEnum.INT
		rootElement.dataTypes__Repository += innerType
		val pcmDataType = initCompositeDataTypeDeclaration(DATATYPE_NAME, attributeName)
		val declaration = RepositoryFactory.eINSTANCE.createInnerDeclaration()
		declaration.entityName = ATTRIBUTE_NAME + "2"
		declaration.datatype_InnerDeclaration = innerType
		pcmDataType.innerDeclaration_CompositeDataType += declaration
		propagate
		val correspondingElements = pcmDataType.correspondingElements
		val umlType = (correspondingElements.get(0) as DataType)
		assertEquals(2, umlType.ownedAttributes.length, "Type should be initialized with two properties")
		val removedDeclaration = pcmDataType.innerDeclaration_CompositeDataType.remove(0)
		assertEquals(removedDeclaration.entityName, attributeName, "removed type should be the expected one")
		propagate
		assertEquals(1, umlType.ownedAttributes.length, "synchronized type should only have one owned attribute left")
	}

	@Test
	def void testCompositeDataTypeDelete() {
		val dataTypesBefore = rootElement.dataTypes__Repository.length
		val pcmDataType = createCompositeDataType(DATATYPE_NAME)
		rootElement.dataTypes__Repository.remove(pcmDataType)
		assertEquals(dataTypesBefore, rootElement.dataTypes__Repository.length)
	}

	protected def CollectionDataType initCollectionDataType(String name, PrimitiveTypeEnum innerTypeValue) {
		val innerType = RepositoryFactory.eINSTANCE.createPrimitiveDataType()
		innerType.type = innerTypeValue
		rootElement.dataTypes__Repository += innerType
		val pcmDataType = RepositoryFactory.eINSTANCE.createCollectionDataType()
		pcmDataType.entityName = name
		rootElement.dataTypes__Repository += pcmDataType
		pcmDataType.innerType_CollectionDataType = innerType
		propagate
		return pcmDataType
	}

	@Test
	def void importedDataTypesTest() {
		val pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface()
		pcmInterface.entityName = INTERFACE_NAME
		val pcmOperation = RepositoryFactory.eINSTANCE.createOperationSignature()
		pcmOperation.entityName = OPERATION_NAME
		pcmInterface.signatures__OperationInterface += pcmOperation
		pcmOperation.returnType__OperationSignature = getPrimitiveType(PrimitiveTypeEnum.BOOL)
		val pcmParameter = RepositoryFactory.eINSTANCE.createParameter()
		pcmParameter.name = PARAMETER_NAME
		pcmParameter.dataType__Parameter = getPrimitiveType(PrimitiveTypeEnum.STRING)
		pcmOperation.parameters__OperationSignature += pcmParameter
		rootElement.interfaces__Repository += pcmInterface
		propagate

		val umlParameter = pcmParameter.correspondingElements.head as Parameter
		assertEquals(PcmToUmlUtil.getUmlPrimitiveTypeName(PrimitiveTypeEnum.STRING), umlParameter.type.name)

		val umlOperation = pcmOperation.correspondingElements.head as Operation
		assertEquals(PcmToUmlUtil.getUmlPrimitiveTypeName(PrimitiveTypeEnum.BOOL), umlOperation.type.name)
	}

	@Test
	def void unmappedDataTypeTest() {
		val pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface()
		pcmInterface.entityName = INTERFACE_NAME
		val pcmOperation = RepositoryFactory.eINSTANCE.createOperationSignature()
		pcmOperation.entityName = OPERATION_NAME
		pcmInterface.signatures__OperationInterface += pcmOperation
		val pcmParameter = RepositoryFactory.eINSTANCE.createParameter()
		pcmParameter.name = PARAMETER_NAME
		pcmParameter.dataType__Parameter = getPrimitiveType(PrimitiveTypeEnum.BYTE)
		pcmOperation.parameters__OperationSignature += pcmParameter
		rootElement.interfaces__Repository += pcmInterface
		propagate

		val umlParameter = pcmParameter.correspondingElements.head as Parameter
		assertEquals(PcmToUmlUtil.getUmlPrimitiveTypeName(PrimitiveTypeEnum.BYTE), umlParameter.type.name)
	}
}

package tools.vitruv.applications.pcmumlcomponents.tests.pcm2uml

import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.DataType
import org.eclipse.uml2.uml.Model
import org.palladiosimulator.pcm.repository.InnerDeclaration
import org.palladiosimulator.pcm.repository.Parameter
import static extension edu.kit.ipd.sdq.commons.util.org.palladiosimulator.pcm.repository.ParameterUtil.*;
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Disabled

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertEquals
import tools.vitruv.applications.pcmumlcomponents.pcm2uml.PcmToUmlUtil

class CollectionTypesTest extends AbstractPcmUmlTest {

	protected val COLLECTION_TYPE_SUFFIX = "Collection"
	protected val CONTAINERTYPE_NAME = "fooType"

	protected def CollectionDataType createCollectionType(String name, DataType innerType) {
		val dataType = RepositoryFactory.eINSTANCE.createCollectionDataType()
		dataType.entityName = name
		dataType.innerType_CollectionDataType = innerType
		rootElement.dataTypes__Repository += dataType
		return dataType
	}

	protected def CollectionDataType createCollectionType(String name, PrimitiveTypeEnum innerType) {
		val innerDataType = getPrimitiveType(innerType)
		return createCollectionType(name, innerDataType)
	}

	protected def CollectionDataType createCollectionType(PrimitiveTypeEnum innerType) {
		return createCollectionType(innerType.toString().collectionTypeName, innerType)
	}

	protected def String collectionTypeName(String typeName) {
		return typeName + COLLECTION_TYPE_SUFFIX
	}

	protected def InnerDeclaration createDataTypeWithInnerDeclaration(DataType innerDeclarationType) {
		val innerDeclaration = RepositoryFactory.eINSTANCE.createInnerDeclaration()
		innerDeclaration.entityName = ATTRIBUTE_NAME
		innerDeclaration.datatype_InnerDeclaration = innerDeclarationType
		val compositeType = RepositoryFactory.eINSTANCE.createCompositeDataType()
		compositeType.entityName = CONTAINERTYPE_NAME
		compositeType.innerDeclaration_CompositeDataType += innerDeclaration
		rootElement.dataTypes__Repository += compositeType
		return innerDeclaration
	}

	protected def Parameter createInterfaceWithParameterizedOperation(DataType parameterType) {
		val pcmParameter = RepositoryFactory.eINSTANCE.createParameter()
		pcmParameter.name = PARAMETER_NAME
		pcmParameter.dataType__Parameter = parameterType
		val pcmOperation = RepositoryFactory.eINSTANCE.createOperationSignature()
		pcmOperation.entityName = OPERATION_NAME
		pcmOperation.parameters__OperationSignature += pcmParameter
		val pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface()
		pcmInterface.entityName = INTERFACE_NAME
		pcmInterface.signatures__OperationInterface += pcmOperation
		rootElement.interfaces__Repository += pcmInterface
		return pcmParameter
	}

	@Test
	def void createCollectionTypeTest() {
		val innerType = PrimitiveTypeEnum.BOOL
		val collectionType = createCollectionType(innerType)
		propagate

		var umlModel = rootElement.correspondingElements.head as Model
		assertTrue(umlModel.packagedElements.filter[t|t instanceof org.eclipse.uml2.uml.DataType].empty)
		assertFalse(collectionType.correspondingElements.empty)

		val innerDeclaration = createDataTypeWithInnerDeclaration(collectionType)
		propagate

		umlModel = rootElement.correspondingElements.head as Model
		val umlTypes = umlModel.packagedElements.filter[t|t instanceof org.eclipse.uml2.uml.DataType]
		assertFalse(umlTypes.empty)
		// as the inner collection type is a primitive type no further data type should be created
		assertEquals(1, umlTypes.length)

		val umlAttribute = innerDeclaration.correspondingElements.head as org.eclipse.uml2.uml.Property
		assertEquals(0, umlAttribute.lowerBound)
		assertEquals(-1, umlAttribute.upperBound)
		assertEquals(PcmToUmlUtil.getUmlPrimitiveTypeName(innerType), umlAttribute.type.name)
	}

	@Test
	def void createNonPrimitiveCollectionTypeTest() {
		val innerTypeName = "A"
		val innerType = RepositoryFactory.eINSTANCE.createCompositeDataType()
		innerType.entityName = innerTypeName
		rootElement.dataTypes__Repository += innerType
		val collectionType = createCollectionType(innerTypeName.collectionTypeName, innerType)
		propagate

		var umlModel = rootElement.correspondingElements.head as Model
		assertFalse(collectionType.correspondingElements.empty)
		assertFalse(innerType.correspondingElements.empty)

		val innerDeclaration = createDataTypeWithInnerDeclaration(collectionType)
		propagate

		umlModel = rootElement.correspondingElements.head as Model
		val umlTypes = umlModel.packagedElements.filter[t|t instanceof org.eclipse.uml2.uml.DataType]
		assertFalse(umlTypes.empty)
		assertEquals(2, umlTypes.length)

		val umlInnerType = innerType.correspondingElements.head as org.eclipse.uml2.uml.DataType
		assertNotNull(umlInnerType)

		val umlAttribute = innerDeclaration.correspondingElements.head as org.eclipse.uml2.uml.Property
		assertEquals(0, umlAttribute.lowerBound)
		assertEquals(-1, umlAttribute.upperBound)
		assertEquals(umlInnerType, umlAttribute.type)

	}

	/**
	 * The most inner type of a nested collection type should be applied
	 */
	@Test
	def void nestedCollectionTest() {
		val innerType = PrimitiveTypeEnum.INT
		val nestedType = createCollectionType(innerType)
		val collectionType = createCollectionType(nestedType.entityName.collectionTypeName, nestedType)

		val innerDeclaration = createDataTypeWithInnerDeclaration(collectionType)
		propagate

		val umlAttribute = innerDeclaration.correspondingElements.head as org.eclipse.uml2.uml.Property
		assertEquals(PcmToUmlUtil.getUmlPrimitiveTypeName(innerType), umlAttribute.type.name)
	}

	/**
	 * Change the inner type of a collection type - all occurrences should be adapted
	 */
	// TODO Reeable, currently disabled because of indeterministic failure
	@Disabled
	@Test
	def void changeInnerCollectionTypeTest() {
		val innerType = RepositoryFactory.eINSTANCE.createCompositeDataType()
		innerType.entityName = "innerType"
		rootElement.dataTypes__Repository += innerType
		val collectionType = createCollectionType(innerType.entityName.collectionTypeName, innerType)
		val pcmAttribute = createDataTypeWithInnerDeclaration(collectionType)
		val pcmParameter = createInterfaceWithParameterizedOperation(collectionType)
		propagate

		val newInnerType = getPrimitiveType(PrimitiveTypeEnum.STRING)
		collectionType.innerType_CollectionDataType = newInnerType
		propagate

		val umlInnerType = newInnerType.correspondingElements.head as org.eclipse.uml2.uml.DataType
		var umlAttribute = pcmAttribute.correspondingElements.head as org.eclipse.uml2.uml.Property
		assertEquals(0, umlAttribute.lowerBound)
		assertEquals(-1, umlAttribute.upperBound)
		assertEquals(umlInnerType, umlAttribute.type)

		var umlParameter = pcmParameter.correspondingElements.head as org.eclipse.uml2.uml.Parameter
		assertEquals(0, umlParameter.lowerBound)
		assertEquals(-1, umlParameter.upperBound)
		assertEquals(umlInnerType, umlParameter.type)
	}

	/**
	 * Unset the inner type of a collection - all occurrences should be unset 
	 */
	@Test
	def void unsetInnerCollectionTypeTest() {
		val innerType = RepositoryFactory.eINSTANCE.createCompositeDataType()
		innerType.entityName = "innerType"
		rootElement.dataTypes__Repository += innerType
		val collectionType = createCollectionType(innerType.entityName.collectionTypeName, innerType)
		val pcmAttribute = createDataTypeWithInnerDeclaration(collectionType)
		val pcmParameter = createInterfaceWithParameterizedOperation(collectionType)
		propagate

		collectionType.innerType_CollectionDataType = null
		propagate

		var umlAttribute = pcmAttribute.correspondingElements.head as org.eclipse.uml2.uml.Property
		assertEquals(1, umlAttribute.lowerBound)
		assertEquals(1, umlAttribute.upperBound)
		assertNull(umlAttribute.type)

		var umlParameter = pcmParameter.correspondingElements.head as org.eclipse.uml2.uml.Parameter
		assertEquals(1, umlParameter.lowerBound)
		assertEquals(1, umlParameter.upperBound)
		assertNull(umlParameter.type)
	}

	/**
	 * Replace a collection type with a different data type - the multiplicity should be reset
	 */
	@Test
	def void replaceCollectionTypeTest() {
		val innerType = RepositoryFactory.eINSTANCE.createCompositeDataType()
		innerType.entityName = "innerType"
		rootElement.dataTypes__Repository += innerType
		val collectionType = createCollectionType(innerType.entityName.collectionTypeName, innerType)
		val pcmAttribute = createDataTypeWithInnerDeclaration(collectionType)
		val pcmParameter = createInterfaceWithParameterizedOperation(collectionType)
		propagate

		val newInnerType = getPrimitiveType(PrimitiveTypeEnum.BOOL)

		pcmAttribute.datatype_InnerDeclaration = newInnerType
		pcmParameter.dataType__Parameter = newInnerType
		propagate

		val umlInnerType = newInnerType.correspondingElements.head as org.eclipse.uml2.uml.DataType
		var umlAttribute = pcmAttribute.correspondingElements.head as org.eclipse.uml2.uml.Property
		assertEquals(1, umlAttribute.lowerBound)
		assertEquals(1, umlAttribute.upperBound)
		assertEquals(umlInnerType.name, umlAttribute.type.name)

		var umlParameter = pcmParameter.correspondingElements.head as org.eclipse.uml2.uml.Parameter
		assertEquals(1, umlParameter.lowerBound)
		assertEquals(1, umlParameter.upperBound)
		assertEquals(umlInnerType.name, umlParameter.type.name)
	}

}

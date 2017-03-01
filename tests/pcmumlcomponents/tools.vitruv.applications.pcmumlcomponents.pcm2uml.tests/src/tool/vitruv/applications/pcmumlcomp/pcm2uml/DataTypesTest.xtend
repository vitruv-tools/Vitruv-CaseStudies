package tool.vitruv.applications.pcmumlcomp.pcm2uml

import org.eclipse.uml2.uml.DataType
import org.junit.Test
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.aplications.pcmumlcomp.pcm2uml.PcmToUmlUtil

import static org.junit.Assert.*
import org.palladiosimulator.pcm.repository.CollectionDataType

class DataTypesTest extends AbstractPcmUmlTest {
	
	protected def PrimitiveDataType createPrimitiveDataType() {
		val pcmDataType = RepositoryFactory.eINSTANCE.createPrimitiveDataType()
		pcmDataType.type = PrimitiveTypeEnum.BOOL
		rootElement.dataTypes__Repository += pcmDataType
		saveAndSynchronizeChanges(pcmDataType);
		return pcmDataType
	}
	
	@Test
	public def void testPrimitiveDataTypeCreate() {
		val pcmDataType = createPrimitiveDataType()
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmDataType]).flatten
		assertEquals(1, correspondingElements.length)
		val umlModel = getUmlModel()
		val umlType = umlModel.getOwnedType(PcmToUmlUtil.getUmlPrimitiveType(pcmDataType.type))
		assertNotNull(umlType)
		assertEquals(PcmToUmlUtil.getUmlPrimitiveType(PrimitiveTypeEnum.BOOL), umlType.name)
	}
	
	@Test
	public def void testPrimitiveDataTypeDelete() {
		val dataTypesBefore = rootElement.dataTypes__Repository.length
		val pcmDataType = createPrimitiveDataType()
		rootElement.dataTypes__Repository.remove(pcmDataType)
		assertEquals(dataTypesBefore, rootElement.dataTypes__Repository.length)
	}
	
	protected def CompositeDataType createCompositeDataType(String name) {
		val pcmDataType = RepositoryFactory.eINSTANCE.createCompositeDataType();
		pcmDataType.entityName = name
		rootElement.dataTypes__Repository += pcmDataType
		saveAndSynchronizeChanges(pcmDataType);
		return pcmDataType
	}
	
	protected def CompositeDataType initCompositeDataTypeDeclaration(String name, String attribute) {
		val dataType = createCompositeDataType(name)
		val declaration = RepositoryFactory.eINSTANCE.createInnerDeclaration()
		declaration.entityName = attribute
		declaration.datatype_InnerDeclaration = createPrimitiveDataType()
		dataType.innerDeclaration_CompositeDataType += declaration
		saveAndSynchronizeChanges(dataType)
		return dataType
	}
	
	@Test
	public def void testCompositeDataTypeCreate() {
		val pcmDataType = createCompositeDataType("FooType")
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmDataType]).flatten
		assertEquals(1, correspondingElements.length)
		val umlModel = getUmlModel()
		val umlType = umlModel.getOwnedType(pcmDataType.entityName)
		assertNotNull(umlType)
	}
	
	@Test
	public def void testCompositeDataTypeDeclarationAdd() {
		val attributeName = "v1"
		val pcmDataType = initCompositeDataTypeDeclaration("BarType", attributeName)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmDataType]).flatten
		val umlType = (correspondingElements.get(0) as DataType)
		assertEquals(1, umlType.ownedAttributes.length)
		assertEquals(attributeName, umlType.ownedAttributes.get(0).name)
		// TODO: what happens so that this name gets changed to "BarType"?
		assertEquals(PcmToUmlUtil.getUmlPrimitiveType(PrimitiveTypeEnum.BOOL), umlType.ownedAttributes.get(0).datatype.name)
	}
	
	@Test
	public def void testCompositeDataTypeDeclarationEdit() {
		val attributeName = "v3"
		val pcmDataType = initCompositeDataTypeDeclaration("BarType2", "v2")
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmDataType]).flatten
		val declaration = pcmDataType.innerDeclaration_CompositeDataType.get(0)
		declaration.entityName = attributeName
		pcmDataType.innerDeclaration_CompositeDataType.set(0, declaration)
		saveAndSynchronizeChanges(pcmDataType)
		val umlType = (correspondingElements.get(0) as DataType)
		assertEquals(1, umlType.ownedAttributes.length)
		assertEquals(attributeName, umlType.ownedAttributes.get(0).name)
	}
	
	@Test
	public def void testCompositeDataTypeDeclarationDelete() {
		val attributeName = "v4"
		val pcmDataType = initCompositeDataTypeDeclaration("BarType3", attributeName)
		val declaration = RepositoryFactory.eINSTANCE.createInnerDeclaration()
		declaration.entityName = "v5"
		val innerType = RepositoryFactory.eINSTANCE.createPrimitiveDataType()
		innerType.type = PrimitiveTypeEnum .INT
		declaration.datatype_InnerDeclaration = innerType
		pcmDataType.innerDeclaration_CompositeDataType += declaration
		saveAndSynchronizeChanges(pcmDataType)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmDataType]).flatten
		val umlType = (correspondingElements.get(0) as DataType)
		assertEquals("Type should be initialized with two properties", 2, umlType.ownedAttributes.length)
		val removedDeclaration = pcmDataType.innerDeclaration_CompositeDataType.remove(0)
		assertEquals("removed type should be the expected one", removedDeclaration.entityName, attributeName)
		saveAndSynchronizeChanges(pcmDataType)
		assertEquals("synchronized type should only have one owned attribute left", 1, umlType.ownedAttributes.length)
	}
	
	@Test
	public def void testCompositeDataTypeDelete() {
		val dataTypesBefore = rootElement.dataTypes__Repository.length
		val pcmDataType = createCompositeDataType("BazType")
		rootElement.dataTypes__Repository.remove(pcmDataType)
		assertEquals(dataTypesBefore, rootElement.dataTypes__Repository.length)
	}
	
	protected def CollectionDataType initCollectionDataType(String name, PrimitiveTypeEnum innerTypeValue) {
		val pcmDataType = RepositoryFactory.eINSTANCE.createCollectionDataType()
		pcmDataType.entityName = name
		val innerType = RepositoryFactory.eINSTANCE.createPrimitiveDataType()
		innerType.type = innerTypeValue
		saveAndSynchronizeChanges(innerType)
		pcmDataType.innerType_CollectionDataType = innerType
		saveAndSynchronizeChanges(pcmDataType)
		return pcmDataType
	}
	
	@Test
	public def void testCollectionDataTypeCreate() {
		val pcmDataType = initCollectionDataType("c1", PrimitiveTypeEnum.INT)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmDataType]).flatten
		val umlType = (correspondingElements.get(0) as DataType)
		println("[testCollectionDataTypeCreate], " + (umlType == null));
		assertEquals(1, umlType.ownedAttributes.length)
		assertEquals("innerType", umlType.ownedAttributes.get(0))
	}
	
	@Test
	public def void testCollectionDataTypeChangeType() {
		val pcmDataType = initCollectionDataType("c2", PrimitiveTypeEnum.INT)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmDataType]).flatten
		val umlType = (correspondingElements.get(0) as DataType)
		assertEquals(PcmToUmlUtil.getUmlPrimitiveType(PrimitiveTypeEnum.INT), umlType.ownedAttributes.get(0).type.name)
		
		val innerType = RepositoryFactory.eINSTANCE.createPrimitiveDataType()
		innerType.type = PrimitiveTypeEnum.DOUBLE
		pcmDataType.innerType_CollectionDataType = innerType
		saveAndSynchronizeChanges(pcmDataType)
		assertEquals(PcmToUmlUtil.getUmlPrimitiveType(PrimitiveTypeEnum.DOUBLE), umlType.ownedAttributes.get(0).type.name)
	}
}
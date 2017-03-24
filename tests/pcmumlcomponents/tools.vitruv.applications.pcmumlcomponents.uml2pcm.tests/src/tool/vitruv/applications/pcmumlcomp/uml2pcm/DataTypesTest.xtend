package tool.vitruv.applications.pcmumlcomp.uml2pcm

import org.eclipse.uml2.uml.UMLFactory
import org.junit.Test
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import tools.vitruv.aplications.pcmumlcomp.uml2pcm.UmlToPcmUtil

import static org.junit.Assert.*
import org.apache.log4j.Logger
import org.apache.log4j.Level
import tools.vitruv.framework.userinteraction.impl.UserInteractorDialog
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.eclipse.uml2.uml.DataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum

class DataTypesTest extends AbstractUmlPcmTest {
	
	@Test
	public def void primitiveTypeCreate() {
		val primitiveType = UMLFactory.eINSTANCE.createPrimitiveType()
		primitiveType.name = "Boolean"
		rootElement.packagedElements += primitiveType
		saveAndSynchronizeChanges(rootElement)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[primitiveType]).flatten
		assertEquals(1, correspondingElements.length)
		val pcmType = correspondingElements.get(0)
		assertTrue(pcmType instanceof PrimitiveDataType)
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(primitiveType.name), (pcmType as PrimitiveDataType).type)
	}
	
	@Test
	public def void collectionTypeCreate() {
		val innerType = UMLFactory.eINSTANCE.createPrimitiveType()
		innerType.name = "Integer"
		rootElement.packagedElements += innerType
		
		testUserInteractor.addNextSelections(0)
		val umlType = UMLFactory.eINSTANCE.createDataType()
		umlType.name = "IntList"
		rootElement.packagedElements += umlType
		
		val umlProperty = UMLFactory.eINSTANCE.createProperty()
		umlProperty.name = UmlToPcmUtil.CollectionTypeAttributeName
		umlProperty.type = innerType
		umlType.ownedAttributes += umlProperty
		
		saveAndSynchronizeChanges(rootElement)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlType]).flatten
		assertEquals(1, correspondingElements.length)
		assertTrue(correspondingElements.get(0) instanceof CollectionDataType)
		val pcmType = (correspondingElements.get(0) as CollectionDataType)
		assertEquals(umlType.name, pcmType.entityName)
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(innerType.name), (pcmType.innerType_CollectionDataType as PrimitiveDataType).type)
		
	}
	
	@Test
	public def void compositeTypeCreate() {
		val typeName = "t1"
		val dataType = UMLFactory.eINSTANCE.createDataType()
		dataType.name = typeName
		val nrOwnedTypesBefore = rootElement.ownedTypes.length
		rootElement.packagedElements += dataType
		testUserInteractor.addNextSelections(1)
		saveAndSynchronizeChanges(rootElement)
		assertEquals(nrOwnedTypesBefore + 1, rootElement.ownedTypes.length)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[dataType]).flatten
		assertEquals(1, correspondingElements.length)
		val pcmType = correspondingElements.get(0)
		assertTrue(pcmType instanceof CompositeDataType)
	}
	
	@Test
	public def void compositeTypeAddProperty() {
		//Logger.rootLogger.level = Level.ALL
		val dataType = UMLFactory.eINSTANCE.createDataType()
		dataType.name = "t2"
		rootElement.packagedElements += dataType
		testUserInteractor.addNextSelections(1)
		val propertyName = "p1"
		val propertyType = UMLFactory.eINSTANCE.createPrimitiveType()
		propertyType.name = "Integer"
		rootElement.packagedElements += propertyType
		//val property = dataType.createOwnedAttribute(propertyName, propertyType)
		val property = UMLFactory.eINSTANCE.createProperty()
		property.name = propertyName
		property.type = propertyType
		dataType.ownedAttributes += property
		saveAndSynchronizeChanges(rootElement)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[dataType]).flatten
		val pcmType = (correspondingElements.get(0) as CompositeDataType)
		assertEquals(1, pcmType.innerDeclaration_CompositeDataType.length)
		assertEquals(propertyName, pcmType.innerDeclaration_CompositeDataType.get(0).entityName)
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(propertyType.name),
			(pcmType.innerDeclaration_CompositeDataType.get(0).datatype_InnerDeclaration as PrimitiveDataType).type)
	}
	
	protected def DataType createCollectionDataType(String name) {
		val dataType = UMLFactory.eINSTANCE.createDataType()
		dataType.name = "t2"
		rootElement.packagedElements += dataType
		testUserInteractor.addNextSelections(0)
		saveAndSynchronizeChanges(rootElement)
		return dataType
	}
	
	protected def DataType createCompositeDataType(String name) {
		val dataType = UMLFactory.eINSTANCE.createDataType()
		dataType.name = "t2"
		rootElement.packagedElements += dataType
		testUserInteractor.addNextSelections(1)
		saveAndSynchronizeChanges(rootElement)
		return dataType
	}
	
	protected def org.eclipse.uml2.uml.Property createProperty(DataType umlType, String name, String datatype) {
		val propertyType = UMLFactory.eINSTANCE.createPrimitiveType()
		propertyType.name = datatype
		rootElement.packagedElements += propertyType
		val property = UMLFactory.eINSTANCE.createProperty()
		property.name = name
		property.type = propertyType
		umlType.ownedAttributes += property
		saveAndSynchronizeChanges(rootElement)
		return property
	}
	
	protected def org.palladiosimulator.pcm.repository.DataType getCorrespondingDataType(DataType umlType) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlType]).flatten
		return correspondingElements.get(0) as org.palladiosimulator.pcm.repository.DataType
	}
	
	@Test
	public def void compositeTypeChangeProperty() {
		val umlType = createCompositeDataType("t1")
		val umlProperty = createProperty(umlType, "p1", "Integer")
		
		val newType = UMLFactory.eINSTANCE.createPrimitiveType()
		newType.name = "Boolean"
		rootElement.packagedElements += newType
		umlProperty.type = newType
		
		val newPropertyName = "p11"
		umlProperty.name = newPropertyName
		
		saveAndSynchronizeChanges(rootElement)
		
		var pcmType = (getCorrespondingDataType(umlType) as CompositeDataType)
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(newType.name),
			(pcmType.innerDeclaration_CompositeDataType.get(0).datatype_InnerDeclaration as PrimitiveDataType).type)
		assertEquals(newPropertyName, pcmType.innerDeclaration_CompositeDataType.get(0).entityName)
	}
	
	@Test
	public def void compositeTypeDeleteProperty() {
		val umlType = createCompositeDataType("t1")
		val property1 = createProperty(umlType, "p1", "Integer")
		val remainingPropertyName = "p2"
		createProperty(umlType, remainingPropertyName, "Real")
		
		umlType.ownedAttributes -= property1
		
		saveAndSynchronizeChanges(rootElement)
		
		var pcmType = (getCorrespondingDataType(umlType) as CompositeDataType)
		assertEquals(1, pcmType.innerDeclaration_CompositeDataType.length())
		assertEquals(remainingPropertyName, pcmType.innerDeclaration_CompositeDataType.get(0).entityName)
	}
	
	@Test
	public def void collectionTypeDeleteProperty() {
		val umlType = createCollectionDataType("t4")
		val property1 = createProperty(umlType, "p1", "Integer")
		val property2 = createProperty(umlType, "p2", "Boolean")
		
		var pcmType = (getCorrespondingDataType(umlType) as CollectionDataType)
		assertEquals(PrimitiveTypeEnum.BOOL, (pcmType.innerType_CollectionDataType as PrimitiveDataType).type)
		
		umlType.ownedAttributes -= property2
		saveAndSynchronizeChanges(umlType)
		pcmType = (getCorrespondingDataType(umlType) as CollectionDataType)
		assertEquals(PrimitiveTypeEnum.INT, (pcmType.innerType_CollectionDataType as PrimitiveDataType).type)
		
		umlType.ownedAttributes -= property1
		saveAndSynchronizeChanges(umlType)
		pcmType = (getCorrespondingDataType(umlType) as CollectionDataType)
		assertNull(pcmType.innerType_CollectionDataType)
	}
	
}
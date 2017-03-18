package tool.vitruv.applications.pcmumlcomp.uml2pcm

import org.eclipse.uml2.uml.UMLFactory
import org.junit.Test
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import tools.vitruv.aplications.pcmumlcomp.uml2pcm.UmlToPcmUtil

import static org.junit.Assert.*
import org.apache.log4j.Logger
import org.apache.log4j.Level

class DataTypesTest extends AbstractUmlPcmTest {
	
	@Test
	public def void testPrimitiveTypeCreate() {
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
	public def void testCompositeTypeCreate() {
		val typeName = "t1"
		val dataType = UMLFactory.eINSTANCE.createDataType()
		dataType.name = typeName
		val nrOwnedTypesBefore = rootElement.ownedTypes.length
		rootElement.packagedElements += dataType
		saveAndSynchronizeChanges(rootElement)
		assertEquals(nrOwnedTypesBefore + 1, rootElement.ownedTypes.length)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[dataType]).flatten
		assertEquals(1, correspondingElements.length)
		val pcmType = correspondingElements.get(0)
		assertTrue(pcmType instanceof CompositeDataType)
	}
	
	@Test
	public def void testDataTypeAddProperty() {
		Logger.rootLogger.level = Level.ALL
		val dataType = UMLFactory.eINSTANCE.createDataType()
		dataType.name = "t2"
		rootElement.packagedElements += dataType
		val propertyName = "p1"
		val propertyType = UMLFactory.eINSTANCE.createPrimitiveType()
		propertyType.name = "Integer"
		rootElement.packagedElements += propertyType
		val property = dataType.createOwnedAttribute(propertyName, propertyType)
		saveAndSynchronizeChanges(rootElement)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[dataType]).flatten
		val pcmType = (correspondingElements.get(0) as CompositeDataType)
		assertEquals(1, pcmType.innerDeclaration_CompositeDataType.length)
		println("[testDataTypeAddProperty]")
		println(pcmType.innerDeclaration_CompositeDataType.get(0))
		println(pcmType.innerDeclaration_CompositeDataType.get(0).entityName)
		println(property.name)
		assertEquals(propertyName, pcmType.innerDeclaration_CompositeDataType.get(0).entityName)
	}
	
}
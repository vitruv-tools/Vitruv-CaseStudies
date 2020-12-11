package tools.vitruv.applications.pcmumlcomponents.tests.uml2pcm

import org.eclipse.emf.common.util.BasicEList
import org.eclipse.ocl.ecore.UnlimitedNaturalLiteralExp
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.UMLFactory
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.InnerDeclaration
import org.palladiosimulator.pcm.repository.Parameter

import org.palladiosimulator.pcm.repository.OperationSignature
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import tools.vitruv.applications.pcmumlcomponents.uml2pcm.UmlToPcmTypesUtil

class MultiplicityTest extends AbstractUmlPcmTest {

	protected static val INNER_DATATYPE_NAME = "innerType"

	protected def DataType createInnerDataType() {
		val type = UMLFactory.eINSTANCE.createDataType()
		type.name = INNER_DATATYPE_NAME
		rootElement.packagedElements += type
		return type
	}

	@Test
	def void attributeMultiplicityTest() {
		val innerDataType = createInnerDataType()
		val umlDataType = UMLFactory.eINSTANCE.createDataType()
		umlDataType.name = "fooType"
		rootElement.packagedElements += umlDataType
		umlDataType.createOwnedAttribute(PARAMETER_NAME, innerDataType, 0, UnlimitedNaturalLiteralExp.UNLIMITED)
		saveAndSynchronizeChanges(rootElement)

		val pcmRepository = rootElement.claimCorrespondingRepository
		assertEquals(3, pcmRepository.dataTypes__Repository.length)

		val collectionType = pcmRepository.dataTypes__Repository.findFirst[t|t instanceof CollectionDataType]
		assertNotNull(collectionType)
		assertEquals(INNER_DATATYPE_NAME + UmlToPcmTypesUtil.COLLECTION_TYPE_SUFFIX,
			(collectionType as CollectionDataType).entityName)

		val compositeType = umlDataType.correspondingElements.head as CompositeDataType
		assertEquals(collectionType, compositeType.innerDeclaration_CompositeDataType.head.datatype_InnerDeclaration)

		val pcmInnerType = pcmRepository.dataTypes__Repository.findFirst [t |
			t instanceof CompositeDataType && (t as CompositeDataType).entityName == INNER_DATATYPE_NAME
		]
		assertEquals(pcmInnerType, (collectionType as CollectionDataType).innerType_CollectionDataType)
	}

	@Test
	def void parameterMultiplicityTest() {
		val parameterType = createInnerDataType()
		val umlInterface = UMLFactory.eINSTANCE.createInterface()
		umlInterface.name = INTERFACE_NAME
		rootElement.packagedElements += umlInterface
		val umlOperation = umlInterface.createOwnedOperation(OPERATION_NAME, #[PARAMETER_NAME].toEList(),
			#[parameterType as Type].toEList())
		val umlParameter = umlOperation.ownedParameters.head
		umlParameter.upper = 2
		saveAndSynchronizeChanges(rootElement)

		val pcmRepository = rootElement.claimCorrespondingRepository
		assertEquals(2, pcmRepository.dataTypes__Repository.length)

		val pcmParameter = umlParameter.correspondingElements.head as Parameter

		val collectionType = pcmRepository.dataTypes__Repository.findFirst[t|t instanceof CollectionDataType]

		assertEquals(collectionType, pcmParameter.dataType__Parameter)
	}
	
	@Test
	def void returnTypeMultiplicityTest() {
		val returnType = createInnerDataType()
		val umlInterface = UMLFactory.eINSTANCE.createInterface()
		umlInterface.name = INTERFACE_NAME
		rootElement.packagedElements += umlInterface
		val umlOperation = umlInterface.createOwnedOperation(OPERATION_NAME, new BasicEList<String>(), new BasicEList<Type>(), returnType)
		val umlParameter = umlOperation.ownedParameters.head
		umlParameter.lower = 0
		saveAndSynchronizeChanges(rootElement)

		val pcmRepository = rootElement.claimCorrespondingRepository
		val collectionType = pcmRepository.dataTypes__Repository.findFirst[t|t instanceof CompositeDataType]
		val pcmOperation = umlOperation.correspondingElements.head as OperationSignature
		assertEquals(collectionType, pcmOperation.returnType__OperationSignature)
	}

	/**
	 * Whilst setting <i>umlAttribute.upper = 1</i> resulted in a correct serialization in the output (and correct assertions),
	 * the corresponding transformation-code recognized the lowerBound as <i>0</i> at this point, thus not setting the data 
	 * type back to the non-list representation. 
	 */
	@Test
	def void unsetMultiplicityTest() {
		val innerDataType = createInnerDataType()
		val umlDataType = UMLFactory.eINSTANCE.createDataType()
		umlDataType.name = "fooType"
		rootElement.packagedElements += umlDataType
		val umlAttribute = umlDataType.createOwnedAttribute(PARAMETER_NAME, innerDataType, 1, UnlimitedNaturalLiteralExp.UNLIMITED)
		saveAndSynchronizeChanges(rootElement)

		umlAttribute.upperValue = null
		saveAndSynchronizeChanges(umlAttribute)
		assertEquals(1, umlAttribute.upperBound)
		assertEquals(1, umlAttribute.lowerBound)
		assertEquals(umlAttribute.upper, umlAttribute.upperBound)
		assertEquals(umlAttribute.lowerBound, umlAttribute.upperBound)
		
		val pcmInnerDeclaration = umlAttribute.correspondingElements.head as InnerDeclaration
		assertTrue(pcmInnerDeclaration.datatype_InnerDeclaration instanceof CompositeDataType)
		assertEquals(INNER_DATATYPE_NAME, (pcmInnerDeclaration.datatype_InnerDeclaration as CompositeDataType).entityName)
	}
	
	@Test
	def void deleteMultiplicityType() {
		val innerDataType = createInnerDataType()
		saveAndSynchronizeChanges(rootElement)
		var pcmRepository = rootElement.claimCorrespondingRepository
		assertEquals(1, pcmRepository.dataTypes__Repository.length)
		
		val umlDataType = UMLFactory.eINSTANCE.createDataType()
		umlDataType.name = "fooType"
		rootElement.packagedElements += umlDataType
		umlDataType.createOwnedAttribute(PARAMETER_NAME, innerDataType, 1, UnlimitedNaturalLiteralExp.UNLIMITED)
		saveAndSynchronizeChanges(rootElement)
		pcmRepository = rootElement.claimCorrespondingRepository
		assertEquals(3, pcmRepository.dataTypes__Repository.length)
		
		rootElement.packagedElements -= umlDataType
		saveAndSynchronizeChanges(rootElement)
		pcmRepository = rootElement.claimCorrespondingRepository
		assertEquals(2, pcmRepository.dataTypes__Repository.length)
		
		rootElement.packagedElements -= innerDataType
		saveAndSynchronizeChanges(rootElement)
		pcmRepository = rootElement.claimCorrespondingRepository
		assertEquals(0, pcmRepository.dataTypes__Repository.length)
	}

}

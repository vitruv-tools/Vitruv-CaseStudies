package tools.vitruv.applications.pcmumlcomponents.tests.uml2pcm

import org.eclipse.emf.common.util.BasicEList
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.UMLFactory
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import tools.vitruv.applications.pcmumlcomponents.uml2pcm.UmlToPcmTypesUtil
import tools.vitruv.applications.pcmumlcomponents.uml2pcm.UmlToPcmUtil

class ImportedDataTypesTest extends AbstractUmlPcmTest {
	
	protected val UMLTYPE_BOOL = "Boolean"
	
	/**
	 * Cannot modify resource set without a write transaction
	 */
	//@Test
	def void importedTypesExistTest() {
		val umlTypes = importPrimitiveTypes()
		assertNotNull(umlTypes.getOwnedMember(UMLTYPE_BOOL))
		val umlType = umlTypes.getOwnedMember(UMLTYPE_BOOL) as DataType
		val pcmRepository = rootElement.claimCorrespondingRepository
		propagate
		val pcmType = UmlToPcmTypesUtil.retrieveCorrespondingPcmType(umlType, pcmRepository, false, null, correspondenceModel)
		assertNotNull(pcmType)
		assertTrue(pcmType instanceof PrimitiveDataType)
		assertEquals(PrimitiveTypeEnum.BOOL, (pcmType as PrimitiveDataType).type)
	}
	
	@Test
	def void unmappedPrimitiveTypeTest() {
		importPrimitiveTypes()
		val umlTypeName = "MyPrimitive"
		val umlType = UMLFactory.eINSTANCE.createPrimitiveType()
		umlType.name = umlTypeName
		userInteraction.addNextSingleSelection(PrimitiveTypeEnum.BYTE_VALUE)
		rootElement.packagedElements += umlType
		propagate
		val pcmRepository = rootElement.claimCorrespondingRepository
		val pcmType = UmlToPcmTypesUtil.retrieveCorrespondingPcmType(umlType, pcmRepository, false, null, correspondenceModel) as PrimitiveDataType
		assertEquals(PrimitiveTypeEnum.BYTE, pcmType.type)
	}
	
	@Test
	def void mapToCompositeTypeTest() {
		importPrimitiveTypes()
		val umlTypeName = "MyPrimitive"
		val umlType = UMLFactory.eINSTANCE.createPrimitiveType()
		umlType.name = umlTypeName
		userInteraction.addNextSingleSelection(PrimitiveTypeEnum.values.length)
		rootElement.packagedElements += umlType
		propagate
		val pcmType = umlType.correspondingElements.head
		assertTrue(pcmType instanceof CompositeDataType)
		assertEquals(umlTypeName, (pcmType as CompositeDataType).entityName)
	}
	
	@Test
	def void useImportedTypeTest() {
		val umlTypes = importPrimitiveTypes()
		val umlType = umlTypes.getOwnedMember(UMLTYPE_BOOL) as PrimitiveType
		val umlInterface = UMLFactory.eINSTANCE.createInterface()
		umlInterface.createOwnedOperation(OPERATION_NAME, new BasicEList<String>(), new BasicEList<Type>(), umlType)
		rootElement.packagedElements += umlInterface
		propagate
		val pcmInterface = correspondenceModel.getCorrespondingEObjects(#[umlInterface]).flatten.head as OperationInterface
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(UMLTYPE_BOOL),
					 (pcmInterface.signatures__OperationInterface.head.returnType__OperationSignature as PrimitiveDataType).type)
	}
	
}
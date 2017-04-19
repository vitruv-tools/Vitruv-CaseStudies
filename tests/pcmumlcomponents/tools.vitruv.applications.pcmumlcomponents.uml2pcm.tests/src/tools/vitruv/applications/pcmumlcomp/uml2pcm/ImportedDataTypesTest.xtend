package tools.vitruv.applications.pcmumlcomp.uml2pcm

import org.junit.Test
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum

import static org.junit.Assert.*
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.uml2.uml.Type
import org.palladiosimulator.pcm.repository.OperationInterface

class ImportedDataTypesTest extends AbstractUmlPcmTest {
	
	protected val UMLTYPE_BOOL = "Boolean"
	
	@Test
	public def void importedTypesExist() {
		val umlTypes = importPrimitiveTypes()
		assertNotNull(umlTypes.getOwnedMember(UMLTYPE_BOOL))
		val umlType = umlTypes.getOwnedMember(UMLTYPE_BOOL)
		val pcmType = UmlToPcmUtil.getPcmPrimitiveType(umlType.name, userInteractor)
		assertNotNull(pcmType)
		assertTrue(pcmType instanceof PrimitiveDataType)
		assertEquals(PrimitiveTypeEnum.BOOL, pcmType.type)
	}
	
	@Test
	public def void unmappedPrimitiveType() {
		importPrimitiveTypes()
		val umlTypeName = "MyPrimitive"
		userInteractor.addNextSelections(PrimitiveTypeEnum.BYTE_VALUE)
		val pcmType = UmlToPcmUtil.getPcmPrimitiveType(umlTypeName, userInteractor)
		assertEquals(PrimitiveTypeEnum.BYTE, pcmType.type)
		
		// user interaction shouldn't be requested a second time
		val secondRequest = UmlToPcmUtil.getPcmPrimitiveType(umlTypeName, userInteractor)
		assertEquals(PrimitiveTypeEnum.BYTE, secondRequest.type)
	}
	
	@Test
	public def void useImportedType() {
		val umlTypes = importPrimitiveTypes()
		val umlType = umlTypes.getOwnedMember(UMLTYPE_BOOL) as PrimitiveType
		val umlInterface = UMLFactory.eINSTANCE.createInterface()
		umlInterface.createOwnedOperation("disposableOp", new BasicEList<String>(), new BasicEList<Type>(), umlType)
		rootElement.packagedElements += umlInterface
		saveAndSynchronizeChanges(rootElement)
		val pcmInterface = correspondenceModel.getCorrespondingEObjects(#[umlInterface]).flatten.head as OperationInterface
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(UMLTYPE_BOOL),
					 (pcmInterface.signatures__OperationInterface.head.returnType__OperationSignature as PrimitiveDataType).type)
	}
	
}
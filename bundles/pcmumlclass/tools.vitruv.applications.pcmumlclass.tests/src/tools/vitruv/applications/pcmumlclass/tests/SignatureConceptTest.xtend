package tools.vitruv.applications.pcmumlclass.tests

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.junit.Test
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.Assert.*
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.junit.Ignore
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.LiteralUnlimitedNatural

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::OperationSignature with its
 * corresponding uml::Operation and the return type of the signature with an uml::Parameter (return parameter) in the uml::Operation.
 * <br><br>
 * Related files: PcmSignature.reactions, UmlSignatureOperation.reactions, UmlReturnAndRegularParameterType.reactions
 */
class SignatureConceptTest extends PcmUmlClassApplicationTest {

    protected static val final Logger logger = Logger.getLogger(typeof(SignatureConceptTest).simpleName);

	private static val PCM_MODEL_FILE = "model/Repository.repository"
	private static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
			DefaultLiterals.UML_EXTENSION
	
	static val TEST_INTERFACE = "TestInterface"
	static val TEST_SIGNATURE = "testSignature"	 
	private static val TEST_COMPOSITE_DATATYPE = "TestCompositeType"
	private static val TEST_COMPOSITE_DATATYPE_2 = "TestCompositeType_2"
	private static val TEST_COLLECTION_DATATYPE = "TestCollectionType"
	
	def public static void checkSignatureConcept(
			CorrespondenceModel cm, 
			OperationSignature pcmSignature, 
			Operation umlOperation
	){
		val returnParam = umlOperation.ownedParameters.findFirst[param | param.direction === ParameterDirectionKind.RETURN_LITERAL]
		assertTrue(corresponds(cm, pcmSignature, umlOperation, TagLiterals.SIGNATURE__OPERATION))
		assertTrue(corresponds(cm, pcmSignature, returnParam, TagLiterals.SIGNATURE__RETURN_PARAMETER))
		assertTrue(pcmSignature.entityName == umlOperation.name)
		// the name needs to be set, so that its TUID is distinct and the object is not confused with new instances
		assertTrue(returnParam.name == DefaultLiterals.RETURN_PARAM_NAME) 
		// return types of both model elements should correspond to each other if they are set
		assertTrue(isCorrect_DataType_Parameter_Correspondence(cm, pcmSignature.returnType__OperationSignature, returnParam))
		// should both be contained in corresponding interfaces
		assertTrue(corresponds(cm, pcmSignature.interface__OperationSignature, umlOperation.interface, TagLiterals.INTERFACE_TO_INTERFACE))
	}
	
	def protected checkSignatureConcept(OperationSignature pcmSignature){
		val umlOperation = getModifiableCorr(pcmSignature, Operation, TagLiterals.SIGNATURE__OPERATION)
		checkSignatureConcept(correspondenceModel, pcmSignature, umlOperation)
	}
	
	def protected checkSignatureConcept(Operation umlOperation){
		val pcmSignature = getModifiableCorr(umlOperation, OperationSignature, TagLiterals.SIGNATURE__OPERATION)
		checkSignatureConcept(correspondenceModel, pcmSignature, umlOperation)
	}

	def private Repository createRepositoryWithInterface(){
		userInteractor.addNextSelections(UML_MODEL_FILE)
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		pcmRepository.entityName = "testCbsRepository"
		
		var pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface
		pcmInterface.entityName = TEST_INTERFACE
		pcmRepository.interfaces__Repository += pcmInterface
		
		var pcmCompositeType = RepositoryFactory.eINSTANCE.createCompositeDataType
		pcmCompositeType.entityName = TEST_COMPOSITE_DATATYPE
		pcmRepository.dataTypes__Repository += pcmCompositeType
		
		var pcmCompositeType_2 = RepositoryFactory.eINSTANCE.createCompositeDataType
		pcmCompositeType_2.entityName = TEST_COMPOSITE_DATATYPE_2
		pcmRepository.dataTypes__Repository += pcmCompositeType_2
		
		var pcmCollectionType = RepositoryFactory.eINSTANCE.createCollectionDataType
		pcmCollectionType.entityName = TEST_COLLECTION_DATATYPE
		pcmCollectionType.innerType_CollectionDataType = pcmCompositeType_2
		pcmRepository.dataTypes__Repository += pcmCollectionType
		
		createAndSynchronizeModel(PCM_MODEL_FILE, pcmRepository)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

		return reloadResourceAndReturnRoot(pcmRepository) as Repository 
	}
	
	def private OperationInterface getPcmTestInterface(Repository pcmRepository){
		return pcmRepository.interfaces__Repository.findFirst[it.entityName == TEST_INTERFACE] as OperationInterface
	}
	
	def private Interface getUmlTestInterface(Repository pcmRepository){
		return getModifiableCorr(getPcmTestInterface(pcmRepository), Interface, TagLiterals.INTERFACE_TO_INTERFACE)
	}
	
	def private CompositeDataType getPcmCompositeDatatype(Repository pcmRepository){
		return pcmRepository.dataTypes__Repository.filter(CompositeDataType)
			.findFirst[it.entityName == TEST_COMPOSITE_DATATYPE] as CompositeDataType
	}
	
	def private getUmlCompositeDatatypeClass(Repository pcmRepository){
		return getModifiableCorr(getPcmCompositeDatatype(pcmRepository), org.eclipse.uml2.uml.Class, TagLiterals.COMPOSITE_DATATYPE__CLASS)
	}
	
	def private CompositeDataType getPcmCompositeDatatype_2(Repository pcmRepository){
		return pcmRepository.dataTypes__Repository.filter(CompositeDataType)
			.findFirst[it.entityName == TEST_COMPOSITE_DATATYPE_2] as CompositeDataType
	}
	
	def private getUmlCompositeDatatypeClass_2(Repository pcmRepository){
		return getModifiableCorr(getPcmCompositeDatatype_2(pcmRepository), org.eclipse.uml2.uml.Class, TagLiterals.COMPOSITE_DATATYPE__CLASS)
	}
	
	def private getPcmCollectionDatatype(Repository pcmRepository){
		return pcmRepository.dataTypes__Repository.filter(CollectionDataType)
			.findFirst[it.entityName == TEST_COLLECTION_DATATYPE] 
	}
	
	def private getUmlCollectionDatatype_Property(Repository pcmRepository){
		return getModifiableCorr(getPcmCollectionDatatype(pcmRepository), org.eclipse.uml2.uml.Class, TagLiterals.COLLECTION_DATATYPE__PROPERTY)
	}

	@Test
	@Ignore
	def void testCreateSignatureConcept_UML_withPrimitiveType() {
		var pcmRepository = createRepositoryWithInterface()
		var umlInterface = getUmlTestInterface(pcmRepository)
		startRecordingChanges(umlInterface)
		
		var umlOperation = umlInterface.createOwnedOperation(TEST_SIGNATURE, null, null)
		var umlParam = umlOperation.createOwnedParameter("someParameter", UML_INT)
		umlParam.direction = ParameterDirectionKind.INOUT_LITERAL
		saveAndSynchronizeChanges(umlInterface)
		
		reloadResourceAndReturnRoot(umlInterface)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		umlInterface = getUmlTestInterface(pcmRepository)
		
		umlOperation = umlInterface.ownedOperations.head
		assertNotNull(umlOperation)
		assertTrue(umlOperation.name == TEST_SIGNATURE)
		checkSignatureConcept(umlOperation)
		
		//Type propagation
		val pcmParameter = getPcmTestInterface(pcmRepository).signatures__OperationInterface.findFirst[it.entityName == TEST_SIGNATURE]
				.parameters__OperationSignature.findFirst[it.parameterName == "someParameter"]
		assertNotNull(pcmParameter)
		assertTrue(pcmParameter.dataType__Parameter == PCM_INT)
	}
	
	@Test
	@Ignore
	def void testCreateSignatureConcept_PCM_withPrimitiveType() {
		var pcmRepository = createRepositoryWithInterface()
		var pcmInterface = getPcmTestInterface(pcmRepository)
		
		var pcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature
		pcmSignature.entityName = TEST_SIGNATURE
		pcmSignature.returnType__OperationSignature = PCM_INT
		pcmInterface.signatures__OperationInterface += pcmSignature
		saveAndSynchronizeChanges(pcmSignature)
		
//		reloadResourceAndReturnRoot(mUmlInterface)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		pcmInterface = getPcmTestInterface(pcmRepository)
		
		pcmSignature = pcmInterface.signatures__OperationInterface.head
		assertNotNull(pcmSignature)
		assertTrue(pcmSignature.entityName == TEST_SIGNATURE)
		checkSignatureConcept(pcmSignature)

		//Type propagation
		val umlTestOperation = getUmlTestInterface(pcmRepository).operations.findFirst[it.name == TEST_SIGNATURE]
		assertNotNull(umlTestOperation)
		assertTrue(umlTestOperation.type == UML_INT)
	}
	
	@Test
	def void testCreateSignatureConcept_UML_withCompositeType() {
		var pcmRepository = createRepositoryWithInterface()
		var umlInterface = getUmlTestInterface(pcmRepository)
		startRecordingChanges(umlInterface)
		
		var umlOperation = umlInterface.createOwnedOperation(TEST_SIGNATURE, null, null)
		var umlParam = umlOperation.createOwnedParameter("someParameter", getUmlCompositeDatatypeClass(pcmRepository))
		umlParam.direction = ParameterDirectionKind.INOUT_LITERAL
		saveAndSynchronizeChanges(umlInterface)
		
		reloadResourceAndReturnRoot(umlInterface)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		umlInterface = getUmlTestInterface(pcmRepository)
		
		umlOperation = umlInterface.ownedOperations.head
		assertNotNull(umlOperation)
		assertTrue(umlOperation.name == TEST_SIGNATURE)
		checkSignatureConcept(umlOperation)
		
		val pcmParameter = getPcmTestInterface(pcmRepository).signatures__OperationInterface.findFirst[it.entityName == TEST_SIGNATURE]
				.parameters__OperationSignature.findFirst[it.parameterName == "someParameter"]
		assertNotNull(pcmParameter)
		assertTrue(pcmParameter.dataType__Parameter == getPcmCompositeDatatype(pcmRepository))
	}
	
	@Test
	def void testCreateSignatureConcept_PCM_withCompositeType() {
		var pcmRepository = createRepositoryWithInterface()
		var pcmInterface = getPcmTestInterface(pcmRepository)
		
		var pcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature
		pcmSignature.entityName = TEST_SIGNATURE
		pcmSignature.returnType__OperationSignature = getPcmCompositeDatatype(pcmRepository)
		pcmInterface.signatures__OperationInterface += pcmSignature
		saveAndSynchronizeChanges(pcmSignature)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		pcmInterface = getPcmTestInterface(pcmRepository)
		
		pcmSignature = pcmInterface.signatures__OperationInterface.head
		assertNotNull(pcmSignature)
		assertTrue(pcmSignature.entityName == TEST_SIGNATURE)
		checkSignatureConcept(pcmSignature)

		val umlTestOperation = getUmlTestInterface(pcmRepository).operations.findFirst[it.name == TEST_SIGNATURE]
		assertNotNull(umlTestOperation)
		assertTrue(umlTestOperation.type == getUmlCompositeDatatypeClass(pcmRepository))
	}
	
	@Test
	def void testCreateSignatureConcept_UML_withCollectionType() {
		var pcmRepository = createRepositoryWithInterface()
		var umlInterface = getUmlTestInterface(pcmRepository)
		startRecordingChanges(umlInterface) 
		// fails here with "IllegalState: InterfaceImpl has no UUID" if the Test-class runs alone,
		// but works in the CompositeTypeTest, or when run together with other test classes
		
		var umlOperation = umlInterface.createOwnedOperation(TEST_SIGNATURE, null, null)
		var umlParam = umlOperation.createOwnedParameter("someParameter", null)
		umlParam.direction = ParameterDirectionKind.INOUT_LITERAL
		//set collection type
		umlParam.lower = 0
		umlParam.upper = LiteralUnlimitedNatural.UNLIMITED
		umlParam.type = getUmlCompositeDatatypeClass_2(pcmRepository)
		saveAndSynchronizeChanges(umlInterface)
		
		reloadResourceAndReturnRoot(umlInterface)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		umlInterface = getUmlTestInterface(pcmRepository)
		
		umlOperation = umlInterface.ownedOperations.head
		assertNotNull(umlOperation)
		assertTrue(umlOperation.name == TEST_SIGNATURE)
		checkSignatureConcept(umlOperation)
		
		val pcmParameter = getPcmTestInterface(pcmRepository).signatures__OperationInterface.findFirst[it.entityName == TEST_SIGNATURE]
				.parameters__OperationSignature.findFirst[it.parameterName == "someParameter"]
		assertNotNull(pcmParameter)
		assertTrue(pcmParameter.dataType__Parameter == getPcmCollectionDatatype(pcmRepository))
	}
	
	@Test
	def void testCreateSignatureConcept_PCM_withCollectionType() {
		var pcmRepository = createRepositoryWithInterface()
		var pcmInterface = getPcmTestInterface(pcmRepository)
		
		var pcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature
		pcmSignature.entityName = TEST_SIGNATURE
		pcmSignature.returnType__OperationSignature = getPcmCollectionDatatype(pcmRepository)
		pcmInterface.signatures__OperationInterface += pcmSignature
		saveAndSynchronizeChanges(pcmSignature)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		pcmInterface = getPcmTestInterface(pcmRepository)
		
		pcmSignature = pcmInterface.signatures__OperationInterface.head
		assertNotNull(pcmSignature)
		assertTrue(pcmSignature.entityName == TEST_SIGNATURE)
		checkSignatureConcept(pcmSignature)

		val umlTestOperation = getUmlTestInterface(pcmRepository).operations.findFirst[it.name == TEST_SIGNATURE]
		assertNotNull(umlTestOperation)
		assertTrue(EcoreUtil.equals(umlTestOperation.type, getUmlCompositeDatatypeClass_2(pcmRepository)))
	}
	
	
	
	
	
	
}

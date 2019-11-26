package tools.vitruv.applications.pcmumlclass.mapping.tests.cases

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.Property
import org.junit.Test
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals
import tools.vitruv.applications.pcmumlclass.mapping.tests.PcmUmlClassApplicationTestHelper
import tools.vitruv.applications.pcmumlclass.mapping.tests.PcmUmlClassTest
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.Assert.*
import org.eclipse.uml2.uml.Operation
import org.junit.Ignore
import org.apache.log4j.Logger

class RequiredRoleTest extends PcmUmlClassTest {
	private static val logger = Logger.getLogger(RequiredRoleTest)

	private val REQUIRED_ROLE_NAME = "testRequiredRole"

	def public static void checkRequiredRoleConcept(
		CorrespondenceModel cm,
		OperationRequiredRole pcmRequired,
		Property umlRequiredInstance,
		Parameter umlRequiredParameter
	) {
		assertNotNull(umlRequiredInstance)
		assertNotNull(umlRequiredParameter)
		assertTrue(corresponds(cm, pcmRequired, umlRequiredInstance, TagLiterals.REQUIRED_ROLE__PROPERTY))
		assertTrue(corresponds(cm, pcmRequired, umlRequiredParameter, TagLiterals.REQUIRED_ROLE__PARAMETER))
		// the respective type references have to correspond
		assertTrue(corresponds(cm, pcmRequired.requiredInterface__OperationRequiredRole, umlRequiredInstance.type))
		assertTrue(corresponds(cm, pcmRequired.requiredInterface__OperationRequiredRole, umlRequiredParameter.type))
		// the owning component and component implementation have to correspond
		assertTrue(
			corresponds(cm, pcmRequired.requiringEntity_RequiredRole, umlRequiredInstance.class_,
				TagLiterals.IPRE__IMPLEMENTATION))
		assertTrue(
			corresponds(cm, pcmRequired.requiringEntity_RequiredRole, umlRequiredParameter.operation?.class_,
				TagLiterals.IPRE__IMPLEMENTATION))
		assertEquals(pcmRequired.entityName , umlRequiredInstance.name)
		assertEquals(pcmRequired.entityName , umlRequiredParameter.name)
	}

	def protected checkRequiredRoleConcept(OperationRequiredRole pcmRequired) {
		assertNotNull(pcmRequired)
		val umlRequiredInstance = helper.getModifiableCorr(pcmRequired, Property, TagLiterals.REQUIRED_ROLE__PROPERTY)
		val umlRequiredParameter = helper.getModifiableCorr(pcmRequired, Parameter,
			TagLiterals.REQUIRED_ROLE__PARAMETER)
		checkRequiredRoleConcept(correspondenceModel, pcmRequired, umlRequiredInstance, umlRequiredParameter)
	}

	def protected checkRequiredRoleConcept(Property umlRequiredInstance, Parameter umlRequiredParameter) {
		assertNotNull(umlRequiredInstance)
		assertNotNull(umlRequiredParameter)
		val pcmRequired = helper.getModifiableCorr(umlRequiredParameter, OperationRequiredRole,
			TagLiterals.REQUIRED_ROLE__PARAMETER)
		assertNotNull(pcmRequired)
		checkRequiredRoleConcept(pcmRequired)
	}

	def private Repository createRepository_Component_Interface() {
		var pcmRepository = helper.createRepository
		createAndSynchronizeModel(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE, pcmRepository)
		val pcmComponent =helper.createComponent(pcmRepository)
		var pcmInterface = helper.createOperationInterface(pcmRepository)
		saveAndSynchronizeChanges(pcmComponent)
		saveAndSynchronizeChanges(pcmInterface)		
		val umlComponentImpl = helper.getModifiableCorr(pcmComponent, Class, TagLiterals.IPRE__IMPLEMENTATION)
		val umlInterface = helper.getModifiableCorr(pcmInterface, Interface, TagLiterals.INTERFACE_TO_INTERFACE)
		val umlOperation= helper.getModifiableCorr(pcmComponent, Operation, TagLiterals.IPRE__CONSTRUCTOR)
		assertNotNull(umlComponentImpl)
		assertNotNull(umlInterface)
		assertNotNull(umlOperation)
		return reloadResourceAndReturnRoot(pcmRepository) as Repository
	}

	@Ignore("Not working properly yet")
	@Test
	def void testRequiredRoleConcept_PCM() {
		var pcmRepository = createRepository_Component_Interface

		var pcmRequired = RepositoryFactory.eINSTANCE.createOperationRequiredRole
		pcmRequired.requiredInterface__OperationRequiredRole = helper.getPcmOperationInterface(pcmRepository)
		var pcmComponent = helper.getPcmComponent(pcmRepository)
		pcmComponent.requiredRoles_InterfaceRequiringEntity += pcmRequired

		saveAndSynchronizeChanges(pcmRequired)
		val umlRequiredInstance = helper.getCorr(pcmRequired, Property, TagLiterals.REQUIRED_ROLE__PROPERTY)
		assertNotNull(umlRequiredInstance)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		pcmComponent = helper.getPcmComponent(pcmRepository)
		assertEquals("There should be exactly one RequiredRole since only one was created by the test case.", 1,
			pcmComponent.requiredRoles_InterfaceRequiringEntity.size)
		pcmRequired = pcmComponent.requiredRoles_InterfaceRequiringEntity.head as OperationRequiredRole
		assertNotNull(pcmRequired)
		checkRequiredRoleConcept(pcmRequired)
	}

	@Ignore("Not working properly yet")
	@Test
	def void testRequiredRoleConcept_UML() {
		var pcmRepository = createRepository_Component_Interface
		var umlConstructor = helper.getUmlComponentConstructor(pcmRepository)
		var umlComponentImpl = helper.getUmlComponentImpl(pcmRepository)
		var umlInterface = helper.getUmlInterface(pcmRepository)
		assertNotNull(umlInterface)
		assertNotNull(umlComponentImpl)
		assertNotNull(umlConstructor)		
		//startRecordingChanges(umlComponentImpl)
		
		//property in implementation[ownedAttribute]  	,	interface in property[type]		
		var umlRequiredInstanceField = umlComponentImpl.createOwnedAttribute(REQUIRED_ROLE_NAME, umlInterface)
		//umlRequiredInstanceField.type = umlInterface
		saveAndSynchronizeChanges(umlComponentImpl)
		
		//startRecordingChanges(umlConstructor)		
		//	parameter in operation[ownedParameter]  , interface in parameter[type]
		var umlConstructorParameter = umlConstructor.createOwnedParameter(REQUIRED_ROLE_NAME,
			umlInterface)
		//umlConstructorParameter.type =  umlInterface
		logger.debug('should create mapping now')	
		saveAndSynchronizeChanges(umlConstructorParameter)
		
		reloadResourceAndReturnRoot(umlConstructorParameter)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository

		umlConstructor = helper.getUmlComponentConstructor(pcmRepository)
		assertEquals("There should be exactly one Parameter for one RequiredRole created by the test case.", 1,
			umlConstructor.ownedParameters.size)
		umlConstructorParameter = umlConstructor.ownedParameters.findFirst[it.name == REQUIRED_ROLE_NAME]
		assertNotNull(umlConstructorParameter)
				umlComponentImpl = helper.getUmlComponentImpl(pcmRepository)
		assertEquals("There should be exactly one Property for one RequiredRole created by the test case.", 1,
			umlComponentImpl.ownedAttributes.size)
		umlRequiredInstanceField = helper.getUmlComponentImpl(pcmRepository).ownedAttributes.findFirst [
			it.name == REQUIRED_ROLE_NAME
		]
		assertNotNull(umlRequiredInstanceField)
		checkRequiredRoleConcept(umlRequiredInstanceField, umlConstructorParameter)
	}


}

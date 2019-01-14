package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.Property
import org.junit.Test
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.Assert.*

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::OperationRequiredRole 
 * of a pcm::InterfaceProvidingRequiringEntity (IPRE) with its corresponding uml::Parameter (constructor parameter 
 * of the IPRE implementation class) and uml::Property (field in the IPRE implementation class used to store the 
 * Component passed to the constructor).
 * <br><br>
 * Related files: PcmRequiredRole.reactions, UmlRequiredRoleParameter.reactions, UmlRequiredRoleProperty.reactions
 */
class RequiredRoleConceptTest extends PcmUmlClassApplicationTest {

	private val REQUIRED_ROLE_NAME = "testRequiredRole"	 
	
	def public static void checkRequiredRoleConcept(
			CorrespondenceModel cm,
			OperationRequiredRole pcmRequired,
			Property umlRequiredInstance,
			Parameter umlRequiredParameter
	) {
		assertNotNull(pcmRequired)
		assertNotNull(umlRequiredInstance)
		assertNotNull(umlRequiredParameter)
		assertTrue(corresponds(cm, pcmRequired, umlRequiredInstance, TagLiterals.REQUIRED_ROLE__PROPERTY))
		assertTrue(corresponds(cm, pcmRequired, umlRequiredParameter, TagLiterals.REQUIRED_ROLE__PARAMETER))
		//the respective type references have to correspond
		assertTrue(corresponds(cm, pcmRequired.requiredInterface__OperationRequiredRole, umlRequiredInstance.type))
		assertTrue(corresponds(cm, pcmRequired.requiredInterface__OperationRequiredRole, umlRequiredParameter.type))
		// the owning component and component implementation have to correspond
		assertTrue(corresponds(cm, pcmRequired.requiringEntity_RequiredRole, umlRequiredInstance.class_, TagLiterals.IPRE__IMPLEMENTATION))
		assertTrue(corresponds(cm, pcmRequired.requiringEntity_RequiredRole, umlRequiredParameter.operation?.class_, TagLiterals.IPRE__IMPLEMENTATION))
		assertTrue(pcmRequired.entityName == umlRequiredInstance.name)
		assertTrue(pcmRequired.entityName == umlRequiredParameter.name)
	}
	
	def protected checkRequiredRoleConcept(OperationRequiredRole pcmRequired) {
		val umlRequiredInstance = helper.getModifiableCorr(pcmRequired, Property, TagLiterals.REQUIRED_ROLE__PROPERTY)
		val umlRequiredParameter = helper.getModifiableCorr(pcmRequired, Parameter, TagLiterals.REQUIRED_ROLE__PARAMETER)
		checkRequiredRoleConcept(correspondenceModel, pcmRequired, umlRequiredInstance, umlRequiredParameter)
	}
	
	def protected checkRequiredRoleConcept(Property umlRequiredInstance) {
		val pcmRequired = helper.getModifiableCorr(umlRequiredInstance, OperationRequiredRole, TagLiterals.REQUIRED_ROLE__PROPERTY)
		assertNotNull(pcmRequired)
		checkRequiredRoleConcept(pcmRequired)
	}
	
	def protected checkRequiredRoleConcept(Parameter umlRequiredParameter) {
		val pcmRequired = helper.getModifiableCorr(umlRequiredParameter, OperationRequiredRole, TagLiterals.REQUIRED_ROLE__PARAMETER)
		assertNotNull(pcmRequired)
		checkRequiredRoleConcept(pcmRequired)
	}

	def private Repository createRepository_Component_Interface() {
		var pcmRepository = helper.createRepository
		helper.createComponent(pcmRepository)
		helper.createOperationInterface(pcmRepository)
		
		userInteractor.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		createAndSynchronizeModel(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE, pcmRepository)

		return reloadResourceAndReturnRoot(pcmRepository) as Repository 
	}

	@Test
	def void testRequiredRoleConcept_PCM() {
		var pcmRepository = createRepository_Component_Interface
		
		var pcmRequired = RepositoryFactory.eINSTANCE.createOperationRequiredRole
		pcmRequired.requiredInterface__OperationRequiredRole = helper.getPcmOperationInterface(pcmRepository)
		helper.getPcmComponent(pcmRepository).requiredRoles_InterfaceRequiringEntity += pcmRequired
		
		saveAndSynchronizeChanges(pcmRequired)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		val pcmComponent = helper.getPcmComponent(pcmRepository)
		assertEquals("There should be exactly one RequiredRole since only one was created by the test case.", 
			1, pcmComponent.requiredRoles_InterfaceRequiringEntity.size)
		pcmRequired = pcmComponent.requiredRoles_InterfaceRequiringEntity.head as OperationRequiredRole
		assertNotNull(pcmRequired)
		checkRequiredRoleConcept(pcmRequired)
	}
	
	@Test
	def void testRequiredRoleConcept_UML_RequiredConstructorParameter() {
		var pcmRepository = createRepository_Component_Interface
		var umlConstructor = helper.getUmlComponentConstructor(pcmRepository)
		startRecordingChanges(umlConstructor)
		
		var umlConstructorParameter = umlConstructor.createOwnedParameter(REQUIRED_ROLE_NAME, helper.getUmlInterface(pcmRepository))
		
		saveAndSynchronizeChanges(umlConstructorParameter)
		reloadResourceAndReturnRoot(umlConstructorParameter)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		umlConstructor = helper.getUmlComponentConstructor(pcmRepository)
		assertEquals("There should be exactly one Parameter for one RequiredRole created by the test case.", 
			1, umlConstructor.ownedParameters.size)
		umlConstructorParameter = umlConstructor.ownedParameters.findFirst[it.name == REQUIRED_ROLE_NAME]
		assertNotNull(umlConstructorParameter)
		checkRequiredRoleConcept(umlConstructorParameter)
	}
	
	@Test
	def void testRequiredRoleConcept_UML_RequiredInstanceField() {
		var pcmRepository = createRepository_Component_Interface
		var umlComponentImpl = helper.getUmlComponentImpl(pcmRepository)
		var umlInterface = helper.getUmlInterface(pcmRepository)
		startRecordingChanges(umlComponentImpl)
		
		var umlRequiredInstanceField = umlComponentImpl.createOwnedAttribute(REQUIRED_ROLE_NAME, umlInterface)
		
		saveAndSynchronizeChanges(umlRequiredInstanceField)
		reloadResourceAndReturnRoot(umlRequiredInstanceField)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		umlComponentImpl = helper.getUmlComponentImpl(pcmRepository)
		assertEquals("There should be exactly one Property for one RequiredRole created by the test case.", 
			1, umlComponentImpl.ownedAttributes.size)
		umlRequiredInstanceField = helper.getUmlComponentImpl(pcmRepository).ownedAttributes.findFirst[it.name == REQUIRED_ROLE_NAME]
		assertNotNull(umlRequiredInstanceField)
		checkRequiredRoleConcept(umlRequiredInstanceField)
	}
}

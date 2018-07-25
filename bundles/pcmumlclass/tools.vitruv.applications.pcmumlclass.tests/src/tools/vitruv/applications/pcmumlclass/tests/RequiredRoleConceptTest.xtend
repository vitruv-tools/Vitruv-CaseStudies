package tools.vitruv.applications.pcmumlclass.tests

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.Property
import org.junit.Test
import org.palladiosimulator.pcm.repository.CompositeComponent
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
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

    protected static val final Logger logger = Logger.getLogger(typeof(RequiredRoleConceptTest).simpleName);

	private static val PCM_MODEL_FILE = "model/Repository.repository"
	private static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
			DefaultLiterals.UML_EXTENSION
	
	private val REPOSITORY_NAME = "testCbsRepository"
	private val COMPONENT_NAME = "TestComponent"
	private val INTERFACE_NAME = "TestInterface"
	
	private val PARAMETER_NAME = "testConstructorParameter"
	private val PROPERTY_NAME = "testRequiredInstanceField"
	 
	
	def public static void checkRequiredRoleConcept(
			CorrespondenceModel cm,
			OperationRequiredRole pcmRequired,
			Property umlRequiredInstance,
			Parameter umlRequiredParameter
	){
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
	}
	
	def protected checkRequiredRoleConcept(OperationRequiredRole pcmRequired){
		val umlRequiredInstance = getModifiableCorr(pcmRequired, Property, TagLiterals.REQUIRED_ROLE__PROPERTY)
		val umlRequiredParameter = getModifiableCorr(pcmRequired, Parameter, TagLiterals.REQUIRED_ROLE__PARAMETER)
		checkRequiredRoleConcept(correspondenceModel, pcmRequired, umlRequiredInstance, umlRequiredParameter)
	}
	
	def protected checkRequiredRoleConcept(Property umlRequiredInstance){
		val pcmRequired = getModifiableCorr(umlRequiredInstance, OperationRequiredRole, TagLiterals.REQUIRED_ROLE__PROPERTY)
		assertNotNull(pcmRequired)
		val umlRequiredParameter = getModifiableCorr(pcmRequired, Parameter, TagLiterals.REQUIRED_ROLE__PARAMETER)
		checkRequiredRoleConcept(correspondenceModel, pcmRequired, umlRequiredInstance, umlRequiredParameter)
	}
	
	def protected checkRequiredRoleConcept(Parameter umlRequiredParameter){
		val pcmRequired = getModifiableCorr(umlRequiredParameter, OperationRequiredRole, TagLiterals.REQUIRED_ROLE__PARAMETER)
		assertNotNull(pcmRequired)
		val umlRequiredInstance = getModifiableCorr(pcmRequired, Property, TagLiterals.REQUIRED_ROLE__PROPERTY)
		checkRequiredRoleConcept(correspondenceModel, pcmRequired, umlRequiredInstance, umlRequiredParameter)
	}

	def private Repository createRepository_Component_Interface(){
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		pcmRepository.entityName = REPOSITORY_NAME
		
		var pcmComponent = RepositoryFactory.eINSTANCE.createCompositeComponent
		pcmComponent.entityName = COMPONENT_NAME
		pcmRepository.components__Repository += pcmComponent
		
		var pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface
		pcmInterface.entityName = INTERFACE_NAME
		pcmRepository.interfaces__Repository += pcmInterface
		
		createAndSynchronizeModel(PCM_MODEL_FILE, pcmRepository)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

		return reloadResourceAndReturnRoot(pcmRepository) as Repository 
	}
	
	def private getPcmComponent(Repository pcmRepository){
		return pcmRepository.components__Repository.findFirst[it.entityName == COMPONENT_NAME] as CompositeComponent
	}
	
	def private getUmlComponentImpl(Repository pcmRepository){
		return getModifiableCorr(getPcmComponent(pcmRepository), Class, TagLiterals.IPRE__IMPLEMENTATION)
	}

	def private getPcmInterface(Repository pcmRepository){
		return pcmRepository.interfaces__Repository.findFirst[it.entityName == INTERFACE_NAME] as OperationInterface
	}
	
	def private getUmlInterface(Repository pcmRepository){
		return getModifiableCorr(getPcmInterface(pcmRepository), Interface, TagLiterals.INTERFACE_TO_INTERFACE)
	}

	@Test
	def void testRequiredRoleConcept_PCM() {
		var pcmRepository = createRepository_Component_Interface
		
		var pcmRequired = RepositoryFactory.eINSTANCE.createOperationRequiredRole
		pcmRequired.requiredInterface__OperationRequiredRole = getPcmInterface(pcmRepository)
		getPcmComponent(pcmRepository).requiredRoles_InterfaceRequiringEntity += pcmRequired
		
		saveAndSynchronizeChanges(pcmRequired)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		pcmRequired = getPcmComponent(pcmRepository).requiredRoles_InterfaceRequiringEntity.head as OperationRequiredRole
		assertNotNull(pcmRequired)
		checkRequiredRoleConcept(pcmRequired)
	}
	
	@Test
	def void testRequiredRoleConcept_UML_RequiredConstructorParameter() {
		var pcmRepository = createRepository_Component_Interface
		var umlConstructor = getUmlComponentImpl(pcmRepository).ownedOperations
			.findFirst[it.name == COMPONENT_NAME + DefaultLiterals.IMPLEMENTATION_SUFFIX]
		startRecordingChanges(umlConstructor)
		
		var umlConstructorParameter = umlConstructor.createOwnedParameter(PARAMETER_NAME, getUmlInterface(pcmRepository))
		
		saveAndSynchronizeChanges(umlConstructorParameter)
		reloadResourceAndReturnRoot(umlConstructorParameter)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		umlConstructorParameter = getUmlComponentImpl(pcmRepository).ownedOperations
			.findFirst[it.name == COMPONENT_NAME + DefaultLiterals.IMPLEMENTATION_SUFFIX]
			?.ownedParameters.findFirst[it.name == PARAMETER_NAME]
		assertNotNull(umlConstructorParameter)
		checkRequiredRoleConcept(umlConstructorParameter)
	}
	
	@Test
	def void testRequiredRoleConcept_UML_RequiredInstanceField() {
		var pcmRepository = createRepository_Component_Interface
		var umlComponentImpl = getUmlComponentImpl(pcmRepository)
		var umlInterface = getUmlInterface(pcmRepository)
		startRecordingChanges(umlComponentImpl)
		
		// write-access fails but, read/iterate seems to be fine 
		var umlRequiredInstanceField = umlComponentImpl.createOwnedAttribute(PROPERTY_NAME, umlInterface)
//		var umlRequiredInstanceField = UMLFactory.eINSTANCE.createProperty
//		umlRequiredInstanceField.name = PROPERTY_NAME
//		umlRequiredInstanceField.type = umlInterface
//		umlComponentImpl.ownedAttributes += umlRequiredInstanceField
		
		saveAndSynchronizeChanges(umlRequiredInstanceField)
		reloadResourceAndReturnRoot(umlRequiredInstanceField)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		umlRequiredInstanceField = getUmlComponentImpl(pcmRepository).ownedAttributes.findFirst[it.name == PROPERTY_NAME]
		assertNotNull(umlRequiredInstanceField)
		checkRequiredRoleConcept(umlRequiredInstanceField)
	}
	
	
	
	
	
	
	
	
}

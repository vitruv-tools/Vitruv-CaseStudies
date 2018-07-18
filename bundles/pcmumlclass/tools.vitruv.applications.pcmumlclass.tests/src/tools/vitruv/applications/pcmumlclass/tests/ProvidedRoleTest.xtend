package tools.vitruv.applications.pcmumlclass.tests

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Package
import org.junit.Test
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil

import static org.junit.Assert.*
import org.junit.Ignore
import org.palladiosimulator.pcm.repository.CompositeComponent
import org.palladiosimulator.pcm.repository.OperationProvidedRole

// A small 'm' prefix will signal that the eObject is loaded from the resourceSet an therefore modifiable.
// Working only on modifiable instances would theoretically allow for the comparison via identity.
// This would be cleaner for checking composition constraints, as equality [equals(target.container, source)] does not ensure the correct containment relation.
// For now stick with equality.

class ProvidedRoleTest extends PcmUmlClassApplicationTest {

    protected static val final Logger logger = Logger.getLogger(typeof(ProvidedRoleTest).simpleName);

	private static val PCM_MODEL_FILE = "model/Repository.repository"
	private static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
			DefaultLiterals.UML_EXTENSION
	
	private val REPOSITORY_NAME = "testCbsRepository"
	private val COMPONENT_NAME = "TestComponent"
	private val INTERFACE_NAME = "TestInterface"
	 
	
	def public static void checkProvidedRoleConcept(
			CorrespondenceModel cm,
			OperationProvidedRole pcmProvided,
			org.eclipse.uml2.uml.Generalization umlGeneralization
	){
		assertNotNull(pcmProvided)
		assertNotNull(umlGeneralization)
		assertTrue(corresponds(cm, pcmProvided, umlGeneralization, TagLiterals.PROVIDED_ROLE__GENERALIZATION))
		//the respective type references have to correspond
		assertTrue(corresponds(cm, pcmProvided.providedInterface__OperationProvidedRole, umlGeneralization.general,TagLiterals.INTERFACE_TO_INTERFACE))
		// the owning component and component implementation have to correspond
		assertTrue(corresponds(cm, pcmProvided.providingEntity_ProvidedRole, umlGeneralization.specific, TagLiterals.IPRE__IMPLEMENTATION))
	}
	
	def protected checkProvidedRoleConcept(OperationProvidedRole pcmProvided){
		val umlGeneralization = getModifiableCorr(pcmProvided, org.eclipse.uml2.uml.Generalization, TagLiterals.PROVIDED_ROLE__GENERALIZATION)
		checkProvidedRoleConcept(correspondenceModel, pcmProvided, umlGeneralization)
	}
	
	def protected checkProvidedRoleConcept(org.eclipse.uml2.uml.Generalization umlGeneralization){
		val pcmProvided = getModifiableCorr(umlGeneralization, OperationProvidedRole, TagLiterals.PROVIDED_ROLE__GENERALIZATION)
		checkProvidedRoleConcept(correspondenceModel, pcmProvided, umlGeneralization)
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
		return getModifiableCorr(getPcmComponent(pcmRepository), org.eclipse.uml2.uml.Class, TagLiterals.IPRE__IMPLEMENTATION)
	}

	def private getPcmInterface(Repository pcmRepository){
		return pcmRepository.interfaces__Repository.findFirst[it.entityName == INTERFACE_NAME] as OperationInterface
	}
	
	def private getUmlInterface(Repository pcmRepository){
		return getModifiableCorr(getPcmInterface(pcmRepository), org.eclipse.uml2.uml.Interface, TagLiterals.INTERFACE_TO_INTERFACE)
	}

	@Test
	def void testProvidedRoleConcept_PCM() {
		var pcmRepository = createRepository_Component_Interface
		
		var pcmProvided = RepositoryFactory.eINSTANCE.createOperationProvidedRole
		pcmProvided.providedInterface__OperationProvidedRole = getPcmInterface(pcmRepository)
		getPcmComponent(pcmRepository).providedRoles_InterfaceProvidingEntity += pcmProvided
		
		saveAndSynchronizeChanges(pcmProvided)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		pcmProvided = getPcmComponent(pcmRepository).providedRoles_InterfaceProvidingEntity.head as OperationProvidedRole
		checkProvidedRoleConcept(pcmProvided)
	}
	
	
	@Test
	def void testProvidedRoleConcept_UML() {
		var pcmRepository = createRepository_Component_Interface
		startRecordingChanges(getUmlComponentImpl(pcmRepository))
		
		var umlGeneralization = getUmlComponentImpl(pcmRepository).createGeneralization(getUmlInterface(pcmRepository))
		
		saveAndSynchronizeChanges(umlGeneralization)
		reloadResourceAndReturnRoot(umlGeneralization)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		val umlInterface = getUmlInterface(pcmRepository) //necessary that it is final for Lambda
		umlGeneralization = getUmlComponentImpl(pcmRepository).generalizations.findFirst[it.general == umlInterface]
		checkProvidedRoleConcept(umlGeneralization)
	}
	
	
	
	
	
	
	
	
	
}

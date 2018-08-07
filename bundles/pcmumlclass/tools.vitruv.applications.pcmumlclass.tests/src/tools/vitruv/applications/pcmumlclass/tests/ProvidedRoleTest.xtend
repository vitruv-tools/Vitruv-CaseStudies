package tools.vitruv.applications.pcmumlclass.tests

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Generalization
import org.junit.Test
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.Assert.*

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::OperationProvidedRole 
 * in an pcm::InterfaceProvidingRequiringEntity (IPRE) with an uml::Generalization in the uml::Class (implementation) corresponding to the IPRE.
 * <br><br>
 * Related files: PcmProvidedRole.reactions, UmlProvidedRoleGeneralization.reactions
 */
class ProvidedRoleTest extends PcmUmlClassApplicationTest {

    protected static val final Logger logger = Logger.getLogger(typeof(ProvidedRoleTest).simpleName);	 
	
	def public static void checkProvidedRoleConcept(
			CorrespondenceModel cm,
			OperationProvidedRole pcmProvided,
			Generalization umlGeneralization
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
		val umlGeneralization = helper.getModifiableCorr(pcmProvided, Generalization, TagLiterals.PROVIDED_ROLE__GENERALIZATION)
		checkProvidedRoleConcept(correspondenceModel, pcmProvided, umlGeneralization)
	}
	
	def protected checkProvidedRoleConcept(Generalization umlGeneralization){
		val pcmProvided = helper.getModifiableCorr(umlGeneralization, OperationProvidedRole, TagLiterals.PROVIDED_ROLE__GENERALIZATION)
		checkProvidedRoleConcept(correspondenceModel, pcmProvided, umlGeneralization)
	}

	def private Repository createRepository_Component_Interface(){
		val pcmRepository = helper.createRepository()
		helper.createComponent(pcmRepository)
		helper.createOperationInterface(pcmRepository)
		
		userInteractor.addNextSelections(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		createAndSynchronizeModel(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE, pcmRepository)
		assertModelExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		return reloadResourceAndReturnRoot(pcmRepository) as Repository 
	}

	@Test
	def void testProvidedRoleConcept_PCM() {
		var pcmRepository = createRepository_Component_Interface
		
		var pcmProvided = RepositoryFactory.eINSTANCE.createOperationProvidedRole
		pcmProvided.providedInterface__OperationProvidedRole = helper.getPcmOperationInterface(pcmRepository)
		helper.getPcmComponent(pcmRepository).providedRoles_InterfaceProvidingEntity += pcmProvided
		
		saveAndSynchronizeChanges(pcmProvided)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		pcmProvided = helper.getPcmComponent(pcmRepository).providedRoles_InterfaceProvidingEntity.head as OperationProvidedRole
		checkProvidedRoleConcept(pcmProvided)
	}
	
	
	@Test
	def void testProvidedRoleConcept_UML() {
		var pcmRepository = createRepository_Component_Interface
		startRecordingChanges(helper.getUmlComponentImpl(pcmRepository))
		
		var umlGeneralization = helper.getUmlComponentImpl(pcmRepository).createGeneralization(helper.getUmlInterface(pcmRepository))
		
		saveAndSynchronizeChanges(umlGeneralization)
		reloadResourceAndReturnRoot(umlGeneralization)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		val umlInterface = helper.getUmlInterface(pcmRepository) //necessary that it is final for Lambda
		umlGeneralization = helper.getUmlComponentImpl(pcmRepository).generalizations.findFirst[it.general == umlInterface]
		checkProvidedRoleConcept(umlGeneralization)
	}
	
	
	
	
	
	
	
	
	
}

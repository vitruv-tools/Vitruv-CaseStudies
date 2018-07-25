package tools.vitruv.applications.pcmumlclass.tests

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Property
import org.junit.Test
import org.palladiosimulator.pcm.core.composition.AssemblyContext
import org.palladiosimulator.pcm.core.composition.CompositionFactory
import org.palladiosimulator.pcm.repository.CompositeComponent
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.Assert.*

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::AssemblyContext 
 * in a pcm::ComposedProvidingRequiringEntity (CPRE) with a uml::Property in an uml::Class (the implementation class to the CPRE). 
 * <br><br>
 * Related files: PcmAssemblyContext.reactions, UmlAssemblyContextProperty.reactions
 */
class AssemblyContextConceptTest extends PcmUmlClassApplicationTest {

    protected static val final Logger logger = Logger.getLogger(typeof(AssemblyContextConceptTest).simpleName);

	private static val PCM_MODEL_FILE = "model/Repository.repository"
	private static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
			DefaultLiterals.UML_EXTENSION
	
	private val REPOSITORY_NAME = "testCbsRepository"
	private val COMPONENT_NAME = "TestComponent"
	private val COMPONENT_NAME_2 = "TestComponent_2"
	
	private val PROPERTY_NAME = "testAssemblyContextField"
	 
	
	def public static void checkAssemblyContextConcept(
			CorrespondenceModel cm,
			AssemblyContext pcmAssemblyContext,
			Property umlAssemblyContextProperty
	){
		assertNotNull(pcmAssemblyContext)
		assertNotNull(umlAssemblyContextProperty)
		assertTrue(corresponds(cm, pcmAssemblyContext, umlAssemblyContextProperty, TagLiterals.ASSEMBLY_CONTEXT__PROPERTY))
		assertTrue(corresponds(cm, pcmAssemblyContext.parentStructure__AssemblyContext, umlAssemblyContextProperty.owner,TagLiterals.IPRE__IMPLEMENTATION))
		assertTrue(corresponds(cm, pcmAssemblyContext.encapsulatedComponent__AssemblyContext, umlAssemblyContextProperty.type, TagLiterals.IPRE__IMPLEMENTATION))
	}
	
	def protected checkAssemblyContextConcept(AssemblyContext pcmAssemblyContext){
		val umlAssemblyContextProperty = getModifiableCorr(pcmAssemblyContext, Property, TagLiterals.ASSEMBLY_CONTEXT__PROPERTY)
		checkAssemblyContextConcept(correspondenceModel, pcmAssemblyContext, umlAssemblyContextProperty)
	}
	
	def protected checkAssemblyContextConcept(Property umlAssemblyContextProperty){
		val pcmAssemblyContext = getModifiableCorr(umlAssemblyContextProperty, AssemblyContext, TagLiterals.ASSEMBLY_CONTEXT__PROPERTY)
		checkAssemblyContextConcept(correspondenceModel, pcmAssemblyContext, umlAssemblyContextProperty)
	}

	/**
	 * Initialize a pcm::Repository with two CompositeComponents and synchronize them with their uml-counterparts.
	 */
	def private Repository createRepository_2Components(){
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		pcmRepository.entityName = REPOSITORY_NAME
		
		var pcmComponent = RepositoryFactory.eINSTANCE.createCompositeComponent
		pcmComponent.entityName = COMPONENT_NAME
		pcmRepository.components__Repository += pcmComponent
		
		var pcmComponent_2 = RepositoryFactory.eINSTANCE.createCompositeComponent
		pcmComponent_2.entityName = COMPONENT_NAME_2
		pcmRepository.components__Repository += pcmComponent_2
		
		createAndSynchronizeModel(PCM_MODEL_FILE, pcmRepository)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

		return reloadResourceAndReturnRoot(pcmRepository) as Repository 
	}
	
	def private getPcmComponent(Repository pcmRepository){
		return pcmRepository.components__Repository.findFirst[it.entityName == COMPONENT_NAME] as CompositeComponent
	}
	def private getPcmComponent_2(Repository pcmRepository){
		return pcmRepository.components__Repository.findFirst[it.entityName == COMPONENT_NAME_2] as CompositeComponent
	}
	
	def private getUmlComponentImpl(Repository pcmRepository){
		return getModifiableCorr(getPcmComponent(pcmRepository), Class, TagLiterals.IPRE__IMPLEMENTATION)
	}
	def private getUmlComponentImpl_2(Repository pcmRepository){
		return getModifiableCorr(getPcmComponent_2(pcmRepository), Class, TagLiterals.IPRE__IMPLEMENTATION)
	}


	@Test
	def void testCreateAssemblyContextConcept_PCM() {
		var pcmRepository = createRepository_2Components()
		var pcmComponent = getPcmComponent(pcmRepository)
		
		var pcmAssemblyContext = CompositionFactory.eINSTANCE.createAssemblyContext
		pcmAssemblyContext.encapsulatedComponent__AssemblyContext = getPcmComponent_2(pcmRepository) // TODO same component doesn't trigger change event
		pcmComponent.assemblyContexts__ComposedStructure += pcmAssemblyContext
		
		saveAndSynchronizeChanges(pcmAssemblyContext)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		pcmAssemblyContext = getPcmComponent(pcmRepository).assemblyContexts__ComposedStructure.head
		assertNotNull(pcmAssemblyContext)
		checkAssemblyContextConcept(pcmAssemblyContext)
	}
	
	@Test
	def void testCreateAssemblyContextConcept_UML() {
		var pcmRepository = createRepository_2Components()
		var umlComponent = getUmlComponentImpl(pcmRepository)
		startRecordingChanges(umlComponent)
		
		var umlAssemblyContextProperty = umlComponent.createOwnedAttribute(PROPERTY_NAME, getUmlComponentImpl_2(pcmRepository))
		
		saveAndSynchronizeChanges(umlAssemblyContextProperty)
		reloadResourceAndReturnRoot(umlAssemblyContextProperty)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		umlAssemblyContextProperty = getUmlComponentImpl(pcmRepository).ownedAttributes.findFirst[it.name == PROPERTY_NAME]
		assertNotNull(umlAssemblyContextProperty)
		checkAssemblyContextConcept(umlAssemblyContextProperty)
	}
	
	
	
	
	
	
	
	
	
}

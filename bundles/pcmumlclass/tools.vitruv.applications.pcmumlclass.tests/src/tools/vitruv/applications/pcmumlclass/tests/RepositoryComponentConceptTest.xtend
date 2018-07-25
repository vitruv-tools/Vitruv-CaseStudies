package tools.vitruv.applications.pcmumlclass.tests

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.VisibilityKind
import org.junit.Test
import org.palladiosimulator.pcm.repository.CompositeComponent
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.Assert.*

/**
 * This test class tests the reactions and routines that are supposed to synchronize synchronize a pcm::RepositoryComponent with 
 * its corresponding uml::Package, uml::Class (implementation), and uml::Operation (constructor).
 * <br><br>
 * Related files: 
 * 		PcmRepositoryComponent.reactions,
 * 		UmlRepositoryComponentPackage.reactions,
 * 		UmlIPREClassReactions.reactions,
 * 		UmlIPREConstructorOperation.reactions
 */
class RepositoryComponentConceptTest extends PcmUmlClassApplicationTest {

    protected static val final Logger logger = Logger.getLogger(typeof(RepositoryComponentConceptTest).simpleName);

	private static val PCM_MODEL_FILE = "model/Repository.repository"
	private static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
			DefaultLiterals.UML_EXTENSION
	
	private val REPOSITORY_NAME = "testCbsRepository"
	private val COMPONENT_NAME = "testComponent"
	 
	
	def public static void checkRepositoryComponentConcept(
			CorrespondenceModel cm, 
			RepositoryComponent pcmComponent, 
			Package umlComponentPkg,
			Class umlComponentImpl,
			Operation umlComponentConstructor
	){
		assertNotNull(pcmComponent)
		assertNotNull(umlComponentPkg)
		assertNotNull(umlComponentImpl)
		assertNotNull(umlComponentConstructor)
		assertTrue(corresponds(cm, pcmComponent, umlComponentPkg, TagLiterals.REPOSITORY_COMPONENT__PACKAGE))
		assertTrue(corresponds(cm, pcmComponent, umlComponentImpl, TagLiterals.IPRE__IMPLEMENTATION))
		assertTrue(corresponds(cm, pcmComponent, umlComponentConstructor, TagLiterals.IPRE__CONSTRUCTOR))
		assertTrue(pcmComponent.entityName.toFirstLower == umlComponentPkg.name) 
		assertTrue(pcmComponent.entityName == umlComponentPkg.name.toFirstUpper)
		assertTrue(pcmComponent.entityName + DefaultLiterals.IMPLEMENTATION_SUFFIX == umlComponentImpl.name)
		assertTrue(pcmComponent.entityName + DefaultLiterals.IMPLEMENTATION_SUFFIX == umlComponentConstructor.name)
		// decided against explicit constructor return type, because it's a common convention
		assertTrue(umlComponentImpl.isFinalSpecialization)
		assertTrue(umlComponentImpl.visibility === VisibilityKind.PUBLIC_LITERAL)
		assertTrue(umlComponentImpl.package === umlComponentPkg)
		//component repository should correspond to the parent package of the component package
		assertTrue(corresponds(cm, pcmComponent.repository__RepositoryComponent, umlComponentPkg.nestingPackage, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE))
	}
	
	def protected checkRepositoryComponentConcept(RepositoryComponent pcmComponent){
		val  umlComponentPkg = getModifiableCorr(pcmComponent, Package, TagLiterals.REPOSITORY_COMPONENT__PACKAGE)
		val  umlComponentImpl = getModifiableCorr(pcmComponent, Class, TagLiterals.IPRE__IMPLEMENTATION)
		val  umlComponentConstructor = getModifiableCorr(pcmComponent, Operation, TagLiterals.IPRE__CONSTRUCTOR)
		checkRepositoryComponentConcept(correspondenceModel, pcmComponent, umlComponentPkg, umlComponentImpl, umlComponentConstructor)
	}
	
	def protected checkRepositoryComponentConcept(Package umlComponentPkg){
		val  pcmComponent = getModifiableCorr(umlComponentPkg, RepositoryComponent, TagLiterals.REPOSITORY_COMPONENT__PACKAGE)
		assertNotNull(pcmComponent)
		val  umlComponentImpl = getModifiableCorr(pcmComponent, Class, TagLiterals.IPRE__IMPLEMENTATION)
		val  umlComponentConstructor = getModifiableCorr(pcmComponent, Operation, TagLiterals.IPRE__CONSTRUCTOR)
		checkRepositoryComponentConcept(correspondenceModel, pcmComponent, umlComponentPkg, umlComponentImpl, umlComponentConstructor)
	}

	/**
	 * Initialize a pcm::Repository and its corresponding uml-counterparts.
	 */
	def private Repository createRepository(){
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		pcmRepository.entityName = REPOSITORY_NAME
		createAndSynchronizeModel(PCM_MODEL_FILE, pcmRepository)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

		return reloadResourceAndReturnRoot(pcmRepository) as Repository 
	}
	
	def private Package getRepositoryPackage(Repository pcmRepository){
		return getModifiableCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
	}

	@Test
	def void testRepositoryComponentConcept_PCM() {
		var pcmRepository = createRepository
		
		var pcmComponent = RepositoryFactory.eINSTANCE.createCompositeComponent
		pcmComponent.entityName = COMPONENT_NAME
		pcmRepository.components__Repository += pcmComponent
		
		saveAndSynchronizeChanges(pcmComponent)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		pcmComponent = pcmRepository.components__Repository.head as CompositeComponent
		
		checkRepositoryComponentConcept(pcmComponent)
	}
	
	
	@Test
	def void testRepositoryComponentConcept_UML() {
		var pcmRepository = createRepository
		var umlRepositoryPkg = getRepositoryPackage(pcmRepository)
		startRecordingChanges(umlRepositoryPkg)
		
		userInteractor.addNextSelections(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__COMPOSITE_COMPONENT)
		var umlComponentPkg = umlRepositoryPkg.createNestedPackage(COMPONENT_NAME)
		
		saveAndSynchronizeChanges(umlComponentPkg)
		reloadResourceAndReturnRoot(umlComponentPkg)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		umlRepositoryPkg = getRepositoryPackage(pcmRepository)
		umlComponentPkg = umlRepositoryPkg.nestedPackages.findFirst[it.name == COMPONENT_NAME]
		
		checkRepositoryComponentConcept(umlComponentPkg)
	}
	
	
	
	
	
	
	
	
	
}

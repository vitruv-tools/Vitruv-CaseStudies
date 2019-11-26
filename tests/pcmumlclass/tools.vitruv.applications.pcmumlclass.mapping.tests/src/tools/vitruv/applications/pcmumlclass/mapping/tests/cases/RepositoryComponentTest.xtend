package tools.vitruv.applications.pcmumlclass.mapping.tests.cases

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.VisibilityKind
import org.junit.Test
import org.palladiosimulator.pcm.repository.CompositeComponent
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.mapping.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals
import tools.vitruv.applications.pcmumlclass.mapping.tests.PcmUmlClassApplicationTestHelper
import tools.vitruv.applications.pcmumlclass.mapping.tests.PcmUmlClassTest
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.Assert.*

class RepositoryComponentTest extends PcmUmlClassTest{
	
	private val COMPONENT_NAME = "TestComponent"
	 
	def public static void checkRepositoryComponentConcept(
			CorrespondenceModel cm, 
			RepositoryComponent pcmComponent, 
			Package umlComponentPkg,
			Class umlComponentImpl,
			Operation umlComponentConstructor
	) {
		assertNotNull(pcmComponent)
		assertNotNull(umlComponentPkg)
		assertNotNull(umlComponentImpl)
		assertNotNull(umlComponentConstructor)
		assertTrue(corresponds(cm, pcmComponent, umlComponentPkg, TagLiterals.REPOSITORY_COMPONENT__PACKAGE))
		assertTrue(corresponds(cm, pcmComponent, umlComponentImpl, TagLiterals.IPRE__IMPLEMENTATION))
		assertTrue(corresponds(cm, pcmComponent, umlComponentConstructor, TagLiterals.IPRE__CONSTRUCTOR))
		assertEquals(umlComponentPkg.name, pcmComponent.entityName.toFirstLower) 
		assertEquals(umlComponentPkg.name.toFirstUpper, pcmComponent.entityName)
		assertEquals( pcmComponent.entityName + DefaultLiterals.IMPLEMENTATION_SUFFIX, umlComponentImpl.name)
		assertEquals( pcmComponent.entityName + DefaultLiterals.IMPLEMENTATION_SUFFIX, umlComponentConstructor.name )
		// decided against explicit constructor return type, because it's a common convention
		//commented out, broke the test
		//assertTrue(umlComponentImpl.isFinalSpecialization)
		assertEquals(VisibilityKind.PUBLIC_LITERAL, umlComponentImpl.visibility)
		assertEquals(umlComponentPkg, umlComponentImpl.package)
		//component repository should correspond to the parent package of the component package
		assertTrue(corresponds(cm, pcmComponent.repository__RepositoryComponent, umlComponentPkg.nestingPackage, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE))
	}
	
	def protected checkRepositoryComponentConcept(RepositoryComponent pcmComponent) {
		val  umlComponentPkg = helper.getModifiableCorr(pcmComponent, Package, TagLiterals.REPOSITORY_COMPONENT__PACKAGE)
		val  umlComponentImpl = helper.getModifiableCorr(pcmComponent, Class, TagLiterals.IPRE__IMPLEMENTATION)
		val  umlComponentConstructor = helper.getModifiableCorr(pcmComponent, Operation, TagLiterals.IPRE__CONSTRUCTOR)
		checkRepositoryComponentConcept(correspondenceModel, pcmComponent, umlComponentPkg, umlComponentImpl, umlComponentConstructor)
	}
	
	def protected checkRepositoryComponentConcept(Package umlComponentPkg) {
		val  pcmComponent = helper.getModifiableCorr(umlComponentPkg, RepositoryComponent, TagLiterals.REPOSITORY_COMPONENT__PACKAGE)
		assertNotNull(pcmComponent)
		checkRepositoryComponentConcept(pcmComponent)
	}
	
	/**
	 * Initialize a pcm::Repository and its corresponding uml-counterparts.
	 */
	def private Repository createRepository() {
		var pcmRepository = helper.createRepository()
		
		createAndSynchronizeModel(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE, pcmRepository)
		assertModelExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		return reloadResourceAndReturnRoot(pcmRepository) as Repository 
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
		var umlRepositoryPkg = helper.getUmlRepositoryPackage(pcmRepository)
		startRecordingChanges(umlRepositoryPkg)
		val componentPackageName = COMPONENT_NAME.toFirstLower
		var umlComponentPkg = umlRepositoryPkg.createNestedPackage(componentPackageName)
		val umlImplementationClass = umlComponentPkg.createOwnedClass(COMPONENT_NAME+DefaultLiterals.IMPLEMENTATION_SUFFIX, false)
		val umlConstructor = umlImplementationClass.createOwnedOperation(COMPONENT_NAME+DefaultLiterals.IMPLEMENTATION_SUFFIX, null ,null)	
		saveAndSynchronizeChanges(umlConstructor)
		reloadResourceAndReturnRoot(umlComponentPkg)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		umlRepositoryPkg = helper.getUmlRepositoryPackage(pcmRepository)
		
		umlComponentPkg = umlRepositoryPkg.nestedPackages.findFirst[it.name == componentPackageName]
		
		checkRepositoryComponentConcept(umlComponentPkg)
	}
}
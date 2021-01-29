package tools.vitruv.applications.transitivechange.tests.circular.pcmumlclassjava

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.VisibilityKind
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.CompositeComponent
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.applications.pcmumlclass.tests.PcmUmlClassApplicationTestHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.jupiter.api.Assertions.*
import java.nio.file.Path

/**
 * This class is based on the correlating PCM/UML test class. It is extended to include Java in the network.
 * This test class tests the reactions and routines that are supposed to synchronize synchronize a pcm::RepositoryComponent with 
 * its corresponding uml::Package, uml::Class (implementation), and uml::Operation (constructor).
 * <br><br>
 * Related files: 
 * 		PcmRepositoryComponent.reactions,
 * 		UmlRepositoryComponentPackage.reactions,
 * 		UmlIPREClassReactions.reactions,
 * 		UmlIPREConstructorOperation.reactions
 */
class RepositoryComponentConceptTest extends PcmUmlJavaTransitiveChangeTest {

	val COMPONENT_NAME = "testComponent"

	def static void checkRepositoryComponentConcept(
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
		assertTrue(pcmComponent.entityName.toFirstLower == umlComponentPkg.name)
		assertTrue(pcmComponent.entityName == umlComponentPkg.name.toFirstUpper)
		assertTrue(pcmComponent.entityName + DefaultLiterals.IMPLEMENTATION_SUFFIX == umlComponentImpl.name)
		assertTrue(pcmComponent.entityName + DefaultLiterals.IMPLEMENTATION_SUFFIX == umlComponentConstructor.name)
		// decided against explicit constructor return type, because it's a common convention
		assertTrue(umlComponentImpl.isFinalSpecialization)
		assertTrue(umlComponentImpl.visibility === VisibilityKind.PUBLIC_LITERAL)
		assertTrue(umlComponentImpl.package === umlComponentPkg)
		// component repository should correspond to the parent package of the component package
		assertTrue(
			corresponds(cm, pcmComponent.repository__RepositoryComponent, umlComponentPkg.nestingPackage,
				TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE))
	}

	def protected checkRepositoryComponentConcept(RepositoryComponent pcmComponent) {
		val umlComponentPkg = helper.getModifiableCorr(pcmComponent, Package, TagLiterals.REPOSITORY_COMPONENT__PACKAGE)
		val umlComponentImpl = helper.getModifiableCorr(pcmComponent, Class, TagLiterals.IPRE__IMPLEMENTATION)
		val umlComponentConstructor = helper.getModifiableCorr(pcmComponent, Operation, TagLiterals.IPRE__CONSTRUCTOR)
		checkRepositoryComponentConcept(correspondenceModel, pcmComponent, umlComponentPkg, umlComponentImpl,
			umlComponentConstructor)
		// Check Java model:
		umlComponentConstructor.checkJavaConstructor
		umlComponentImpl.checkJavaType
		// Created before test cases, should be still there:
		val umlPackage = helper.getUmlRepositoryPackage(pcmComponent.repository__RepositoryComponent)
		umlPackage.checkJavaPackage
		umlPackage.nestedPackages.forEach[checkJavaPackage] // does also contain umlComponentPkg
	}

	def protected checkRepositoryComponentConcept(Package umlComponentPkg) {
		val pcmComponent = helper.getModifiableCorr(umlComponentPkg, RepositoryComponent,
			TagLiterals.REPOSITORY_COMPONENT__PACKAGE)
		assertNotNull(pcmComponent)
		checkRepositoryComponentConcept(pcmComponent)
	}

	/**
	 * Initialize a pcm::Repository and its corresponding uml-counterparts.
	 */
	def private Repository createRepository() {
		val pcmRepository = helper.createRepository()

		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		resourceAt(Path.of(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)).startRecordingChanges => [
			contents += pcmRepository
		]
		propagate
		assertModelExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		return pcmRepository.clearResourcesAndReloadRoot
	}

	@Test
	def void testRepositoryComponentConcept_PCM() {
		var pcmRepository = createRepository

		var pcmComponent = RepositoryFactory.eINSTANCE.createCompositeComponent
		pcmComponent.entityName = COMPONENT_NAME
		pcmRepository.components__Repository += pcmComponent

		propagate
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot
		pcmComponent = pcmRepository.components__Repository.head as CompositeComponent

		checkRepositoryComponentConcept(pcmComponent)
	}

	@Test
	def void testRepositoryComponentConcept_UML() {
		var pcmRepository = createRepository
		var umlRepositoryPkg = helper.getUmlRepositoryPackage(pcmRepository)
		startRecordingChanges(umlRepositoryPkg)

		userInteraction.addNextSingleSelection(
			DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__COMPOSITE_COMPONENT)
		var umlComponentPkg = umlRepositoryPkg.createNestedPackage(COMPONENT_NAME)

		userInteraction.addNextSingleSelection(0)
		propagate
		umlComponentPkg.clearResourcesAndReloadRoot
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot
		umlRepositoryPkg = helper.getUmlRepositoryPackage(pcmRepository)

		umlComponentPkg = umlRepositoryPkg.nestedPackages.findFirst[it.name == COMPONENT_NAME]

		checkRepositoryComponentConcept(umlComponentPkg)
	}
}

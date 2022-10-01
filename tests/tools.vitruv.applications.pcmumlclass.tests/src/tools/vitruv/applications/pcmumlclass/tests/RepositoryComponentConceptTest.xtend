package tools.vitruv.applications.pcmumlclass.tests

import org.palladiosimulator.pcm.repository.CompositeComponent
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.applications.pcmumlclass.tests.PcmUmlElementEqualityValidation.*
import static extension tools.vitruv.applications.pcmumlclass.tests.PcmQueryUtil.*
import static extension tools.vitruv.applications.testutility.uml.UmlQueryUtil.*
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne

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

	val COMPONENT_NAME = "testComponent"
	val NEW_COMPONENT_NAME = "newTestComponent"

	@Test
	def void testCreateRepositoryComponentConcept_PCM() {
		initPCMRepository

		changePcmView[
			var pcmComponent = RepositoryFactory.eINSTANCE.createCompositeComponent
			pcmComponent.entityName = COMPONENT_NAME
			defaultPcmRepository.components__Repository += pcmComponent
		]

		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = claimPcmRepository(PACKAGE_NAME_FIRST_UPPER)

			assertNotNull(umlPackage.claimPackage(COMPONENT_NAME))
			assertNotNull(pcmPackage.eContents)

			assertElementsEqual(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testCreateRepositoryComponentConcept_UML() {
		initUMLModel

		changeUmlView[
			userInteraction.addNextSingleSelection(
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__COMPOSITE_COMPONENT)
			umlRootPackage.createNestedPackage(COMPONENT_NAME)
		]

		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = claimPcmRepository(PACKAGE_NAME_FIRST_UPPER)

			assertNotNull(umlPackage.claimPackage(COMPONENT_NAME))
			assertNotNull(pcmPackage.eContents)

			assertElementsEqual(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testRenameRepositoryComponentConcept_PCM() {
		initPCMRepository

		changePcmView[
			var pcmComponent = RepositoryFactory.eINSTANCE.createCompositeComponent
			pcmComponent.entityName = COMPONENT_NAME
			defaultPcmRepository.components__Repository += pcmComponent
		]

		// Ensure preconditions for rename are fulfilled:
		validateUmlAndPcmPackagesView[
			assertNotNull(umlRootPackage.claimPackage(COMPONENT_NAME))
			assertEquals(COMPONENT_NAME.toFirstUpper,
				(defaultPcmRepository.components__Repository.claimOne as CompositeComponent).entityName)
		]

		changePcmView[
			(defaultPcmRepository.components__Repository.
				claimOne as CompositeComponent).entityName = NEW_COMPONENT_NAME.toFirstUpper
		]

		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = claimPcmRepository(PACKAGE_NAME_FIRST_UPPER)

			assertNotNull(umlPackage.claimPackage(NEW_COMPONENT_NAME))
			assertNotNull(pcmPackage.eContents)

			assertElementsEqual(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testRenameRepositoryComponentConcept_UML() {
		initUMLModel

		changeUmlView[
			userInteraction.addNextSingleSelection(
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__COMPOSITE_COMPONENT)
			umlRootPackage.createNestedPackage(COMPONENT_NAME)
		]

		// Ensure preconditions for rename are fulfilled:
		validateUmlAndPcmPackagesView[
			assertNotNull(umlRootPackage.claimPackage(COMPONENT_NAME))
			assertEquals(COMPONENT_NAME.toFirstUpper,
				(defaultPcmRepository.components__Repository.claimOne as CompositeComponent).entityName)
		]

		changeUmlView[
			umlRootPackage.claimPackage(COMPONENT_NAME).name = NEW_COMPONENT_NAME
		]

		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = claimPcmRepository(PACKAGE_NAME_FIRST_UPPER)

			assertNotNull(umlPackage.claimPackage(NEW_COMPONENT_NAME))
			assertNotNull(pcmPackage.eContents)

			assertElementsEqual(umlPackage, pcmPackage)
		]
	}
}

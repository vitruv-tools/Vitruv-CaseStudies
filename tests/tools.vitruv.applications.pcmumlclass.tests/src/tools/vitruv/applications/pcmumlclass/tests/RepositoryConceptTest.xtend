package tools.vitruv.applications.pcmumlclass.tests

import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.junit.jupiter.api.Test
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.Model
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.URI
import org.eclipse.xtend.lib.annotations.Accessors
import org.junit.jupiter.api.BeforeEach
import java.nio.file.Path
import tools.vitruv.applications.pcmumlclass.tests.PcmUmlclassViewFactory
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.framework.views.View
import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertNull
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.isResource
import static tools.vitruv.testutils.matchers.ModelMatchers.isNoResource
import static tools.vitruv.applications.pcmumlclass.tests.PcmUmlElementEqualityValidation.*
import static extension tools.vitruv.applications.pcmumlclass.tests.UmlQueryUtil.*
import static extension tools.vitruv.applications.pcmumlclass.tests.PcmQueryUtil.*

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::Repository
 * with its corresponding uml::Packages.
 * <br><br>
 * Related files: PcmRepository.reactions, UmlRepositoryAndSystemPackage.reactions
 */
class RepositoryConceptTest extends ViewBasedVitruvApplicationTest {
	protected var extension PcmUmlclassViewFactory viewFactory

	@Accessors(PROTECTED_GETTER)
	static val MODEL_FILE_EXTENSION = "uml"
	@Accessors(PROTECTED_GETTER)
	static val MODEL_NAME = "model"
	@Accessors(PROTECTED_GETTER)
	static val MODEL_FOLDER_NAME = "model"

	static val PACKAGE_NAME = "rootpackage"
	static val PACKAGE_NAME_FIRST_UPPER = "Rootpackage"
	static val PACKAGE_RENAMED = "rootpackagerenamed"

	def protected void assertModelExists(String modelPath) {
		val modelUri = getUri(Path.of(modelPath))
		assertThat(modelUri, isResource)
	}

	def protected void assertModelNotExists(String modelPath) {
		val modelUri = getUri(Path.of(modelPath))
		assertThat(modelUri, isNoResource)
	}

	protected def getDefaultUmlModel(View view) {
		view.claimUmlModel(MODEL_NAME)
	}

	protected def getDefaultPcmRepository(View view) {
		view.claimPcmRepository(PACKAGE_NAME_FIRST_UPPER)
	}

	protected def void createUmlModel((Model)=>void modelInitialization) {
		changeUmlView [
			val umlModel = UMLFactory.eINSTANCE.createModel
			createAndRegisterRoot(umlModel, MODEL_NAME.projectModelPath.uri)
			modelInitialization.apply(umlModel)
		]
	}

	protected def void createPcmRepository(String packageName) {
		changePcmView [
			val repository = RepositoryFactory.eINSTANCE.createRepository => [
				it.entityName = packageName
			]
			createAndRegisterRoot(repository, MODEL_NAME.projectModelPath.uri)
		]
	}

	protected def void createAndRegisterRoot(View view, EObject rootObject, URI persistenceUri) {
		view.registerRoot(rootObject, persistenceUri)
	}

	protected def Path getProjectModelPath(String modelName) {
		Path.of(MODEL_FOLDER_NAME).resolve(modelName + "." + MODEL_FILE_EXTENSION)
	}

	protected def void changeUmlModel((Model)=>void modelModification) {
		changeUmlView [
			modelModification.apply(defaultUmlModel)
		]
	}

	protected def void changePcmModel((Repository)=>void modelModification) {
		changePcmView [
			modelModification.apply(defaultPcmRepository)
		]
	}

	def void createUmlRootPackage(String packageName) {
		changeUmlModel [
			packagedElements += UMLFactory.eINSTANCE.createPackage => [
				it.name = packageName
			]
		]
	}

	@BeforeEach
	def void setup() {
		viewFactory = new PcmUmlclassViewFactory(virtualModel)
		createUmlModel[name = MODEL_NAME]
	}

	@Test
	def void testCreateRepositoryConcept_UML_System() {
		userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__SYSTEM)
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.PCM_MODEL_SYSTEM_FILE)
		createUmlRootPackage(PACKAGE_NAME)

		validateUmlAndPcmSystemView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = claimPcmSystem()
			assertElementsEqual(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testCreateRepositoryConcept_UML_Repository() {
		userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY)
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		createUmlRootPackage(PACKAGE_NAME)

		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = defaultPcmRepository
			assertElementsEqual(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testCreateRepositoryConcept_PCM() {
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		createPcmRepository(PACKAGE_NAME_FIRST_UPPER)

		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = defaultPcmRepository
			assertElementsEqual_PCM(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testRenameRepositoryConcept_UML() {
		userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY)
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		createUmlRootPackage(PACKAGE_NAME)

		changeUmlView[
			defaultUmlModel.getPackagedElement(PACKAGE_NAME).setName(PACKAGE_RENAMED)
		]

		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_RENAMED)
			val pcmPackage = claimPcmRepository(PACKAGE_RENAMED.toFirstUpper)
			assertElementsEqual(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testRenameRepositoryConcept_PCM() {
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		createPcmRepository(PACKAGE_NAME_FIRST_UPPER)

		changePcmView[
			defaultPcmRepository.setEntityName(PACKAGE_RENAMED.toFirstUpper)
		]

		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_RENAMED)
			val pcmPackage = claimPcmRepository(PACKAGE_RENAMED.toFirstUpper)
			assertElementsEqual_PCM(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testDeleteRepositoryConcept_PCM() {
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		createPcmRepository(PACKAGE_NAME_FIRST_UPPER)

		validateUmlAndPcmPackagesView [
			assertNotNull(defaultPcmRepository)
			assertNotNull(defaultUmlModel)
		]
		assertModelExists(MODEL_NAME)

		userInteraction.addNextConfirmationInput(true)
		changePcmView[
			EcoreUtil.delete(defaultPcmRepository)
		]

		validateUmlAndPcmPackagesView [
			val umlModel = defaultUmlModel

			assertNull(defaultPcmRepository)
			assertTrue(defaultUmlModel.eContents.empty)
			assertModelNotExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
			assertModelNotExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
			assertTrue(umlModel?.packagedElements.empty)
		]
	}

		
	}
	
	override protected getChangePropagationSpecifications() {
		return #[
			new CombinedPcmToUmlClassReactionsChangePropagationSpecification,
			new CombinedUmlClassToPcmReactionsChangePropagationSpecification
		]
		
	}
}

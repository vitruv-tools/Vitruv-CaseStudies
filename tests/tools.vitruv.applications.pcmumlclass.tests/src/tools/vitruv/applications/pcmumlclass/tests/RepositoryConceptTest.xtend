package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import java.nio.file.Path
import static extension tools.vitruv.applications.pcmumlclass.PcmUmlClassHelper.isPackageFor
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.applications.pcmumlclass.tests.PcmUmlclassViewFactory
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest
import org.eclipse.uml2.uml.Model
import tools.vitruv.framework.views.View
import static extension tools.vitruv.applications.pcmumlclass.tests.UmlQueryUtil.*
import static extension tools.vitruv.applications.pcmumlclass.tests.PcmQueryUtil.*
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertEquals
import org.eclipse.uml2.uml.UMLFactory
import static tools.vitruv.applications.pcmumlclass.tests.PcmUmlElementEqualityValidation.*
import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.URI
import org.junit.jupiter.api.BeforeEach
import org.palladiosimulator.pcm.PcmFactory

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
	static val NESTED_PACKAGE_NAME = "nestedpackage"
	static val PACKAGE_RENAMED = "rootpackagerenamed"

	def protected checkRepositoryConcept(
		Repository pcmRepo,
		Package umlRepositoryPkg,
		Package umlContractsPkg,
		Package umlDatatypesPkg
	) {
		// containment constraints
		assertTrue(EcoreUtil.equals(umlContractsPkg.nestingPackage, umlRepositoryPkg))
		assertTrue(EcoreUtil.equals(umlDatatypesPkg.nestingPackage, umlRepositoryPkg))
		// attribute constraints
		assertTrue(umlRepositoryPkg.isPackageFor(pcmRepo))
		assertTrue(umlRepositoryPkg.name.toFirstUpper == pcmRepo.entityName)
		assertTrue(umlContractsPkg.name == DefaultLiterals.CONTRACTS_PACKAGE_NAME)
		assertTrue(umlDatatypesPkg.name == DefaultLiterals.DATATYPES_PACKAGE_NAME)
	}

	protected def getDefaultUmlModel(View view) {
		view.claimUmlModel(MODEL_NAME)
	}
	
	protected def getDefaultPcmModel(View view) {
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
		changeUmlView [
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
	
	/**
	 * Changes the UML model in the UML view and commits the performed changes.
	 */
	protected def void changeUmlModel((Model)=>void modelModification) {
		changeUmlView [
			modelModification.apply(defaultUmlModel)
		]
	}
	
	protected def void changePcmModel((Repository)=>void modelModification) {
		changePcmView [
			modelModification.apply(defaultPcmModel)
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
		println("UML Sys create repository test")
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
		println("UML Repo create repository test")
		userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY)
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		createUmlRootPackage(PACKAGE_NAME)
		
		validateUmlAndPcmPackagesView [ 
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = defaultPcmModel
			assertElementsEqual(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testCreateRepositoryConcept_PCM() {
		println("PCM create repository test")
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		
		createPcmRepository(PACKAGE_NAME_FIRST_UPPER)

		
		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = defaultPcmModel
			assertElementsEqual(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testRenameRepositoryConcept_PCM() {
		
	}

	@Test
	def void testDeleteRepositoryConcept_PCM() {
		
	}
	
	override protected getChangePropagationSpecifications() {
		return #[
			new CombinedPcmToUmlClassReactionsChangePropagationSpecification,
			new CombinedUmlClassToPcmReactionsChangePropagationSpecification
		]
		
	}
}

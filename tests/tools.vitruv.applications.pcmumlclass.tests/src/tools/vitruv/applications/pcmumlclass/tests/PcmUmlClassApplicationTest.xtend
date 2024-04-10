package tools.vitruv.applications.pcmumlclass.tests

import java.nio.file.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.xtend.lib.annotations.Accessors
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.^extension.ExtendWith
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.system.System
import org.palladiosimulator.pcm.system.SystemFactory
import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.tests.helper.PcmModelEqualityHelper
import tools.vitruv.applications.testutility.uml.UmlQueryUtil
import tools.vitruv.framework.views.View
import tools.vitruv.testutils.RegisterMetamodelsInStandalone
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest
import tools.vitruv.testutils.printing.DefaultModelPrinter
import tools.vitruv.testutils.printing.DefaultPrintIdProvider
import tools.vitruv.testutils.printing.DefaultPrintTarget

import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.testutils.matchers.ModelMatchers.isNoResource
import static tools.vitruv.testutils.matchers.ModelMatchers.isResource

import static extension tools.vitruv.applications.pcmumlclass.tests.PcmQueryUtil.*
import static extension tools.vitruv.applications.testutility.uml.UmlQueryUtil.*

@ExtendWith(RegisterMetamodelsInStandalone)
abstract class PcmUmlClassApplicationTest extends ViewBasedVitruvApplicationTest {

	@Accessors(PROTECTED_GETTER)
	static val UML_MODEL_FILE_EXTENSION = "uml"
	@Accessors(PROTECTED_GETTER)
	static val UML_MODEL_NAME = "model"
	@Accessors(PROTECTED_GETTER)
	static val UML_MODEL_FOLDER_NAME = "model"

	@Accessors(PROTECTED_GETTER)
	static val PCM_REPOSITORY_FILE_EXTENSION = "repository"
	@Accessors(PROTECTED_GETTER)
	static val PCM_SYSTEM_FILE_EXTENSION = "system"
	@Accessors(PROTECTED_GETTER)
	static val PCM_MODEL_FOLDER_NAME = "pcm"

	static protected val PACKAGE_NAME = "rootpackage"
	static protected val PACKAGE_NAME_FIRST_UPPER = "Rootpackage"
	static protected val CONTRACTS_PACKAGE = "contracts"
	static protected val DATATYPES_PACKAGE = "datatypes"

	public static val PCM_MODEL_FILE = "model/Repository.repository"
	public static val PCM_MODEL_SYSTEM_FILE = "model/System.system"
	public static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
		DefaultLiterals.UML_EXTENSION

	protected var extension PcmUmlClassViewFactory viewFactory

	override protected getChangePropagationSpecifications() {
		return #[
			new CombinedPcmToUmlClassReactionsChangePropagationSpecification,
			new CombinedUmlClassToPcmReactionsChangePropagationSpecification
		]
	}

	@BeforeEach
	def void setup() {
		viewFactory = new PcmUmlClassViewFactory(virtualModel)
		createUmlModel[name = UML_MODEL_NAME]
	}

	protected def void createAndRegisterRoot(View view, EObject rootObject, URI persistenceUri) {
		view.registerRoot(rootObject, persistenceUri)
	}

	protected def Path getPcmProjectModelPath(String modelName, String modelFileExtension) {
		Path.of(PCM_MODEL_FOLDER_NAME).resolve(modelName + "." + modelFileExtension)
	}

	protected def Path getUmlProjectModelPath(String modelName) {
		Path.of(UML_MODEL_FOLDER_NAME).resolve(modelName + "." + UML_MODEL_FILE_EXTENSION)
	}

	def void initUMLModel() {
		userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY)
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		createUmlRootPackage(PACKAGE_NAME)
	}

	def void initPCMRepository() {
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		createRepository(PACKAGE_NAME_FIRST_UPPER)
	}

	// --- UML creators ---
	protected def void createUmlModel((Model)=>void modelInitialization) {
		changeUmlView [
			val umlModel = UMLFactory.eINSTANCE.createModel
			modelInitialization.apply(umlModel)
			createAndRegisterRoot(umlModel, UML_MODEL_NAME.umlProjectModelPath.uri)
		]
	}

	protected def void createUmlRootPackage(String packageName) {
		changeUmlView [
			defaultUmlModel.packagedElements += UMLFactory.eINSTANCE.createPackage => [
				it.name = packageName
			]
		]
	}

	// --- PCM creators ---
	protected def void createRepository(String repositoryName) {
		createRepository[
			entityName = repositoryName;
		]
	}

	private def void createRepository((Repository)=>void repositoryInitalization) {
		changePcmView[
			var repository = RepositoryFactory.eINSTANCE.createRepository();
			repositoryInitalization.apply(repository)
			registerRoot(repository, getPcmProjectModelPath(repository.entityName, PCM_REPOSITORY_FILE_EXTENSION).uri)
		]
	}

	protected def void createSystem(String systemyName) {
		createSystem [
			entityName = systemyName;
		]
	}

	private def void createSystem((System)=>void systemInitialization) {
		changePcmView[
			val system = SystemFactory.eINSTANCE.createSystem();
			systemInitialization.apply(system)
			registerRoot(system, getPcmProjectModelPath(system.entityName, PCM_SYSTEM_FILE_EXTENSION).uri)
		]
	}

	// --- UML model queries ---
	protected def getDefaultUmlModel(View view) {
		view.claimUmlModel(UML_MODEL_NAME)
	}

	protected def getUmlRootPackage(View view) {
		view.defaultUmlModel.claimPackage(PACKAGE_NAME)
	}

	protected def getUmlContractsPackage(View view) {
		view.defaultUmlModel.getNestedPackages.findFirst[it.name.equals(PACKAGE_NAME)].nestedPackages.findFirst [
			it.name.equals(CONTRACTS_PACKAGE)
		]
	}

	protected def getUmlDatatypesPackage(View view) {
		view.defaultUmlModel.getNestedPackages.findFirst[it.name.equals(PACKAGE_NAME)].nestedPackages.findFirst [
			it.name.equals(DATATYPES_PACKAGE)
		]
	}

	// --- PCM model queries
	protected def getDefaultPcmRepository(View view) {
		view.claimPcmRepository(PACKAGE_NAME_FIRST_UPPER)
	}

	// --- assertions ---
	protected def void assertEqualityAndContainmentOfUmlModel(Model actualModel, Model expectedModel) {
		assertTrue(new EcoreUtil.EqualityHelper().equals(actualModel, expectedModel),
			modelComparisonErrorMessage(expectedModel, actualModel))
	}

	protected def void assertEqualityAndContainmentOfUmlPackage(Model model, String packageName,
		Package expectedPackage) {
		val splittedPackageName = packageName.split("\\.")
		var currentPackage = model as Package

		for (fragment : splittedPackageName) {
			currentPackage = UmlQueryUtil.claimPackageableElement(currentPackage, Package, fragment)
		}
		val matchedPackage = currentPackage

		assertTrue(new EcoreUtil.EqualityHelper().equals(matchedPackage, expectedPackage),
			modelComparisonErrorMessage(expectedPackage, matchedPackage))
	}

	protected def assertEqualityOfPcmRepository(Repository repository, Repository expectedRepository) {
		val equalityHelper = new PcmModelEqualityHelper();
		assertTrue(equalityHelper.equals(repository, expectedRepository),
			modelComparisonErrorMessage(expectedRepository, repository))
	}

	protected def assertEqualityOfPcmSystem(System system, System expectedSystem) {
		val equalityHelper = new PcmModelEqualityHelper();
		assertTrue(equalityHelper.equals(system, expectedSystem), modelComparisonErrorMessage(expectedSystem, system))
	}

	protected def String modelComparisonErrorMessage(EObject expected, EObject actual) {
		var printTarget = new DefaultPrintTarget()
		var printIdProvider = new DefaultPrintIdProvider()
		var modelPrinter = new DefaultModelPrinter()

		printTarget.print("Expected actual model to equal expected model.")
		printTarget.newLine
		printTarget.print("Actual:")
		printTarget.newLine
		modelPrinter.printObject(printTarget, printIdProvider, actual)

		printTarget.newLine
		printTarget.print("Expected:")
		printTarget.newLine
		modelPrinter.printObject(printTarget, printIdProvider, expected)

		return printTarget.toString
	}

	def protected void assertModelExists(String modelPath) {
		val modelUri = getUri(Path.of(modelPath))
		assertThat(modelUri, isResource)
	}

	def protected void assertModelNotExists(String modelPath) {
		val modelUri = getUri(Path.of(modelPath))
		assertThat(modelUri, isNoResource)
	}
}

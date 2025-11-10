package tools.vitruv.applications.demo.insurancefamilies.tests.insurance2families;

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory
import edu.kit.ipd.sdq.metamodels.families.Family
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import edu.kit.ipd.sdq.metamodels.insurance.Gender
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceFactory
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtend.lib.annotations.Accessors
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.change.interaction.UserInteractionOptions.NotificationType
import tools.vitruv.change.propagation.ChangePropagationSpecification
import tools.vitruv.change.propagation.ChangePropagationSpecificationRepository
import tools.vitruv.change.propagation.impl.DefaultChangeRecordingModelRepository
import tools.vitruv.applications.demo.insurancefamilies.insurance2families.InsuranceToFamiliesChangePropagationSpecification
import tools.vitruv.applications.demo.insurancefamilies.insurance2families.PositionPreference
import tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceFamiliesDefaultTestModelFactory
import tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceFamiliesTestModelFactory
import tools.vitruv.dsls.testutils.TestModel
import tools.vitruv.change.testutils.TestLogging
import tools.vitruv.change.testutils.TestProject
import tools.vitruv.change.testutils.TestProjectManager
import tools.vitruv.change.testutils.TestUserInteraction
import tools.vitruv.change.testutils.TestUserInteraction.MultipleChoiceInteractionDescription
import tools.vitruv.change.testutils.views.ChangePublishingTestView
import tools.vitruv.change.testutils.views.NonTransactionalTestView
import tools.vitruv.change.testutils.views.UriMode

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createInsuranceClient
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.FamiliesQueryUtil.claimFamilyRegister
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceQueryUtil.claimInsuranceDatabase
import static tools.vitruv.change.testutils.TestModelRepositoryFactory.createTestChangeableModelRepository
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply

enum FamilyPreference {
	New,
	Existing
}

@ExtendWith(#[TestLogging, TestProjectManager])
abstract class AbstractInsuranceToFamiliesTest {
	protected var extension InsuranceFamiliesTestModelFactory _testModelFactory
	protected var Path testProjectPath
	TestUserInteraction userInteraction

	/**
	 * Can be used to set a different kind of test model factory and test user interaction to be used in subclasses.
	 */
	protected def setTestExecutionContext(InsuranceFamiliesTestModelFactory testModelFactory,
		TestUserInteraction testUserInteraction) {
		this._testModelFactory = testModelFactory
		this.userInteraction = testUserInteraction
	}

	@BeforeEach
	def final void setupViewFactory(@TestProject Path testProjectPath) {
		this.testProjectPath = testProjectPath
		val userInteraction = new TestUserInteraction()
		setTestExecutionContext(
			new InsuranceFamiliesDefaultTestModelFactory(prepareTestView(testProjectPath, userInteraction)),
			userInteraction)
	}

	private def NonTransactionalTestView prepareTestView(Path testProjectPath,
		TestUserInteraction userInteraction) throws IOException {
		val changePropagationSpecificationProvider = new ChangePropagationSpecificationRepository(
			changePropagationSpecifications)
		val modelRepository = new DefaultChangeRecordingModelRepository(null, Files.createTempDirectory(null));
		val changeableModelRepository = createTestChangeableModelRepository(modelRepository,
			changePropagationSpecificationProvider, userInteraction)
		return new ChangePublishingTestView(testProjectPath, userInteraction, UriMode.FILE_URIS,
			changeableModelRepository, modelRepository.uuidResolver) [ modelRepository.getModelResource(it) ]
	}
	
	// === setup ===
	
	@Accessors(PROTECTED_GETTER)
	static val FAMILY_MODEL_FILE_EXTENSION = "families"
	@Accessors(PROTECTED_GETTER)
	static val INSURANCE_MODEL_FILE_EXTENSION = "insurance"
	@Accessors(PROTECTED_GETTER)
	static val MODEL_FOLDER_NAME = "model"
	
	protected def getDefaultFamilyRegister(TestModel<FamilyRegister> model) {
		claimFamilyRegister(model)
	}

	protected def Path getProjectModelPath(String modelName, String modelFileExtension) {
		Path.of(MODEL_FOLDER_NAME).resolve(modelName + "." + modelFileExtension)
	}
	
	protected def URI getUri(Path viewRelativePath) {
		createFileURI(testProjectPath.resolve(viewRelativePath).normalize().toFile())
	}

	def protected Iterable<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return #[new InsuranceToFamiliesChangePropagationSpecification()]
	}

	protected def void createAndRegisterRoot(TestModel<InsuranceDatabase> model, InsuranceDatabase rootObject, URI persistenceUri) {
		model.registerRoot(rootObject, persistenceUri)
	}
	
	protected def void deleteRoot(EObject rootObject) {
		EcoreUtil.delete(rootObject)
	}
	
	// === creators ===
	
	private def void createInsuranceDatabase((InsuranceDatabase)=> void insuranceDatabaseInitialization) {
		changeInsuranceModel [
			var insuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase
			insuranceDatabaseInitialization.apply(insuranceDatabase)
			createAndRegisterRoot(insuranceDatabase,  getProjectModelPath("insurance", INSURANCE_MODEL_FILE_EXTENSION).uri)
		]
	}
	
	protected def String fullName(String firstName, String lastName) {
	 	return firstName + " " + lastName
	}
	
	// === initializers ===
	
	protected def void createEmptyInsuranceDatabase(){
		createInsuranceDatabase[]
	}
	
	protected def void createInsuranceDatabaseWithCompleteFamily() {
		createInsuranceDataBaseWithOptionalCompleteFamily(true, true, true, true)
	}
	
	protected def createInsuranceDataBaseWithOptionalCompleteFamily(boolean insertFather, boolean insertMother, boolean insertSon, boolean insertDaugther) {
		if(!(insertFather || insertMother || insertSon || insertDaugther)){
			throw new IllegalArgumentException("can't create empty family")
		}
		
		var insertCount = 0
		
		createInsuranceDatabase[]
		
		if(insertFather){
			decideParentOrChild(PositionPreference.Parent)
			changeInsuranceModel [
				claimInsuranceDatabase(it) => [
					insuranceclient += createInsuranceClient[ name = fullName(FIRST_DAD_1, LAST_NAME_1) gender = Gender.MALE]
				]
			]
			insertCount++;
		}
		
		if(insertMother){
			decideParentOrChild(PositionPreference.Parent)
			if(insertCount > 0) decideNewOrExistingFamily(FamilyPreference.Existing, 1)
			changeInsuranceModel [
				claimInsuranceDatabase(it) => [
					insuranceclient += createInsuranceClient[ name = fullName(FIRST_MOM_1, LAST_NAME_1) gender = Gender.FEMALE]
				]
			]
			insertCount++;
		}
	
		if(insertSon){
			decideParentOrChild(PositionPreference.Child)
			if(insertCount > 0) decideNewOrExistingFamily(FamilyPreference.Existing, 1)
			changeInsuranceModel [
				claimInsuranceDatabase(it) => [
					insuranceclient += createInsuranceClient[ name = fullName(FIRST_SON_1, LAST_NAME_1) gender = Gender.MALE]
				]
			]
		}
		
		if(insertDaugther){
			decideParentOrChild(PositionPreference.Child)
			if(insertCount > 0) decideNewOrExistingFamily(FamilyPreference.Existing, 1)
			changeInsuranceModel [
				claimInsuranceDatabase(it) => [
					insuranceclient += createInsuranceClient[ name = fullName(FIRST_DAU_1, LAST_NAME_1) gender = Gender.FEMALE]
				]
			]
		}
	}
	
	// === interaction ===
	
	protected def void awaitReplacementInformation(String insuranceClientName, String oldFamilyName){
		userInteraction.acknowledgeNotification[
			it.message == "Insurance Client " + insuranceClientName + 
				" has been replaced by another insurance client in his family (" + oldFamilyName +
				"). Please decide in which family and role " + insuranceClientName + " should be." 
				&&
			it.title == "Insurance Client has been replaced in his original family" &&
			it.notificationType == NotificationType.INFORMATION
		]
	}
	
	protected def void decideParentOrChild(PositionPreference preference) {
		val String parentChildTitle = "Parent or Child?"
		userInteraction.onMultipleChoiceSingleSelection[title.equals(parentChildTitle)].respondWithChoiceAt(if (preference === PositionPreference.Parent) 0 else 1)
	}
	
	protected def void decideNewOrExistingFamily(FamilyPreference preference, int familyIndex) {
		userInteraction
			.onMultipleChoiceSingleSelection[assertFamilyOptions(it)]
			.respondWithChoiceAt(if (preference === FamilyPreference.New) 0 else familyIndex)
	}
	
	// === assertions ===
	
	protected def void assertFamily(Family expected, Family actual) {
		assertThat(actual, equalsDeeply(expected));
	}
	
	protected def void assertNumberOfFamilies(TestModel<FamilyRegister> model, int expectedNumberOfFamilies){
		assertEquals(expectedNumberOfFamilies, claimFamilyRegister(model).families.size)
	}
	
	val String newOrExistingFamilyTitle = "New or Existing Family?"
	
	protected def boolean assertFamilyOptions(MultipleChoiceInteractionDescription interactionDescription) {
		//First option is always a new family
		assertEquals(interactionDescription.choices.get(0), "insert in a new family")
		val tail = interactionDescription.choices.drop(1)
		//There must be a second option otherwise there would not be an interaction
		assertTrue(tail.size > 0)
		val familyName = tail.get(0).split(":").get(0)
		//All other options have to offer families with the same name
		tail.forEach[familyOption|familyOption.split(":").get(0).equals(familyName)]
		
		return interactionDescription.title.equals(newOrExistingFamilyTitle)
	}
	
	// === data ===
	
	// First Set of reused static strings for the first names of the persons
	protected final static String FIRST_DAD_1 = "Anton"
	protected final static String FIRST_MOM_1 = "Berta"
	protected final static String FIRST_SON_1 = "Chris"
	protected final static String FIRST_DAU_1 = "Daria"

	// Second Set of reused static strings for the first names of the persons
	protected final static String FIRST_DAD_2 = "Adam"
	protected final static String FIRST_MOM_2 = "Birgit"
	protected final static String FIRST_SON_2 = "Charles"
	protected final static String FIRST_DAU_2 = "Daniela"

	// Set of reused static strings for the last names of the persons
	protected final static String LAST_NAME_1 = "Meier"
	protected final static String LAST_NAME_2 = "Schulze"
	protected final static String LAST_NAME_3 = "Müller"
	
	protected final static Family COMPLETE_FAMILY_1 = FamiliesFactory.eINSTANCE.createFamily => [
		lastName = LAST_NAME_1
		father = FamiliesFactory.eINSTANCE.createMember => [ firstName = FIRST_DAD_1]
		mother = FamiliesFactory.eINSTANCE.createMember => [ firstName = FIRST_MOM_1]
		sons += FamiliesFactory.eINSTANCE.createMember => [ firstName = FIRST_SON_1]
		daughters += FamiliesFactory.eINSTANCE.createMember => [ firstName = FIRST_DAU_1]
	]
}

package tools.vitruv.applications.demo.insurancefamilies.tests.insurance2families;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createInsuranceClient;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.FamiliesQueryUtil.claimFamilyRegister;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceQueryUtil.claimInsuranceDatabase;
import static tools.vitruv.change.testutils.TestModelRepositoryFactory.createTestChangeableModelRepository;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory;
import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.families.Member;
import edu.kit.ipd.sdq.metamodels.insurance.Gender;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.applications.demo.insurancefamilies.insurance2families.InsuranceToFamiliesChangePropagationSpecification;
import tools.vitruv.applications.demo.insurancefamilies.insurance2families.PositionPreference;
import tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceFamiliesDefaultTestModelFactory;
import tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceFamiliesTestModelFactory;
import tools.vitruv.change.composite.propagation.ChangeableModelRepository;
import tools.vitruv.change.interaction.UserInteractionOptions.NotificationType;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.propagation.ChangePropagationSpecificationRepository;
import tools.vitruv.change.propagation.impl.DefaultChangeRecordingModelRepository;
import tools.vitruv.change.testutils.TestLogging;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.change.testutils.TestUserInteraction;
import tools.vitruv.change.testutils.TestUserInteraction.MultipleChoiceInteractionDescription;
import tools.vitruv.change.testutils.views.ChangePublishingTestView;
import tools.vitruv.change.testutils.views.NonTransactionalTestView;
import tools.vitruv.change.testutils.views.UriMode;
import tools.vitruv.dsls.testutils.TestModel;

@ExtendWith({TestLogging.class, TestProjectManager.class})
public abstract class AbstractInsuranceToFamiliesTest {
	protected InsuranceFamiliesTestModelFactory _testModelFactory;
	protected Path testProjectPath;
	private TestUserInteraction userInteraction;

	/**
	 * Can be used to set a different kind of test model factory and test user interaction to be used in subclasses.
	 */
	protected void setTestExecutionContext(InsuranceFamiliesTestModelFactory testModelFactory,
		TestUserInteraction testUserInteraction) {
		this._testModelFactory = testModelFactory;
		this.userInteraction = testUserInteraction;
	}

	@BeforeEach
	public final void setupViewFactory(@TestProject Path testProjectPath) throws IOException {
		this.testProjectPath = testProjectPath;
		TestUserInteraction userInteraction = new TestUserInteraction();
		setTestExecutionContext(
			new InsuranceFamiliesDefaultTestModelFactory(prepareTestView(testProjectPath, userInteraction)),
			userInteraction);
	}

	private NonTransactionalTestView prepareTestView(Path testProjectPath,
		TestUserInteraction userInteraction) throws IOException {
		ChangePropagationSpecificationRepository changePropagationSpecificationProvider = new ChangePropagationSpecificationRepository(
			getChangePropagationSpecifications());
		DefaultChangeRecordingModelRepository modelRepository = new DefaultChangeRecordingModelRepository(null, Files.createTempDirectory(null));
		ChangeableModelRepository changeableModelRepository = createTestChangeableModelRepository(modelRepository,
			changePropagationSpecificationProvider, userInteraction);
		return new ChangePublishingTestView(testProjectPath, userInteraction, UriMode.FILE_URIS,
			changeableModelRepository, modelRepository.getUuidResolver(), uri -> modelRepository.getModelResource(uri));
	}

	// === setup ===

	private static final String FAMILY_MODEL_FILE_EXTENSION = "families";
	private static final String INSURANCE_MODEL_FILE_EXTENSION = "insurance";
	private static final String MODEL_FOLDER_NAME = "model";

	protected static String getFAMILY_MODEL_FILE_EXTENSION() {
		return FAMILY_MODEL_FILE_EXTENSION;
	}

	protected static String getINSURANCE_MODEL_FILE_EXTENSION() {
		return INSURANCE_MODEL_FILE_EXTENSION;
	}

	protected static String getMODEL_FOLDER_NAME() {
		return MODEL_FOLDER_NAME;
	}

	protected FamilyRegister getDefaultFamilyRegister(TestModel<FamilyRegister> model) {
		return claimFamilyRegister(model);
	}

	protected Path getProjectModelPath(String modelName, String modelFileExtension) {
		return Path.of(MODEL_FOLDER_NAME).resolve(modelName + "." + modelFileExtension);
	}

	protected URI getUri(Path viewRelativePath) {
		return createFileURI(testProjectPath.resolve(viewRelativePath).normalize().toFile());
	}

	protected Iterable<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return List.of(new InsuranceToFamiliesChangePropagationSpecification());
	}

	protected void createAndRegisterRoot(TestModel<InsuranceDatabase> model, InsuranceDatabase rootObject, URI persistenceUri) {
		model.registerRoot(rootObject, persistenceUri);
	}

	protected void deleteRoot(EObject rootObject) {
		EcoreUtil.delete(rootObject);
	}

	// === creators ===

	private void createInsuranceDatabase(Consumer<InsuranceDatabase> insuranceDatabaseInitialization) {
		changeInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase();
			insuranceDatabaseInitialization.accept(insuranceDatabase);
			createAndRegisterRoot(model, insuranceDatabase, getUri(getProjectModelPath("insurance", INSURANCE_MODEL_FILE_EXTENSION)));
		});
	}

	protected String fullName(String firstName, String lastName) {
		return firstName + " " + lastName;
	}

	// === initializers ===

	protected void createEmptyInsuranceDatabase() {
		createInsuranceDatabase(insuranceDatabase -> {
		});
	}

	protected void createInsuranceDatabaseWithCompleteFamily() {
		createInsuranceDataBaseWithOptionalCompleteFamily(true, true, true, true);
	}

	protected void createInsuranceDataBaseWithOptionalCompleteFamily(boolean insertFather, boolean insertMother, boolean insertSon, boolean insertDaugther) {
		if (!(insertFather || insertMother || insertSon || insertDaugther)) {
			throw new IllegalArgumentException("can't create empty family");
		}

		int insertCount = 0;

		createInsuranceDatabase(insuranceDatabase -> {
		});

		if (insertFather) {
			decideParentOrChild(PositionPreference.Parent);
			changeInsuranceModel(model -> {
				claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
					client.setName(fullName(FIRST_DAD_1, LAST_NAME_1));
					client.setGender(Gender.MALE);
				}));
			});
			insertCount++;
		}

		if (insertMother) {
			decideParentOrChild(PositionPreference.Parent);
			if (insertCount > 0) {
				decideNewOrExistingFamily(FamilyPreference.Existing, 1);
			}
			changeInsuranceModel(model -> {
				claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
					client.setName(fullName(FIRST_MOM_1, LAST_NAME_1));
					client.setGender(Gender.FEMALE);
				}));
			});
			insertCount++;
		}

		if (insertSon) {
			decideParentOrChild(PositionPreference.Child);
			if (insertCount > 0) {
				decideNewOrExistingFamily(FamilyPreference.Existing, 1);
			}
			changeInsuranceModel(model -> {
				claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
					client.setName(fullName(FIRST_SON_1, LAST_NAME_1));
					client.setGender(Gender.MALE);
				}));
			});
		}

		if (insertDaugther) {
			decideParentOrChild(PositionPreference.Child);
			if (insertCount > 0) {
				decideNewOrExistingFamily(FamilyPreference.Existing, 1);
			}
			changeInsuranceModel(model -> {
				claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
					client.setName(fullName(FIRST_DAU_1, LAST_NAME_1));
					client.setGender(Gender.FEMALE);
				}));
			});
		}
	}

	// === interaction ===

	protected void awaitReplacementInformation(String insuranceClientName, String oldFamilyName) {
		userInteraction.acknowledgeNotification(notification ->
			Objects.equals(notification.getMessage(), "Insurance Client " + insuranceClientName
				+ " has been replaced by another insurance client in his family (" + oldFamilyName
				+ "). Please decide in which family and role " + insuranceClientName + " should be.")
			&& Objects.equals(notification.getTitle(), "Insurance Client has been replaced in his original family")
			&& notification.getNotificationType() == NotificationType.INFORMATION);
	}

	protected void decideParentOrChild(PositionPreference preference) {
		String parentChildTitle = "Parent or Child?";
		userInteraction.onMultipleChoiceSingleSelection(interaction -> interaction.getTitle().equals(parentChildTitle))
			.respondWithChoiceAt(preference == PositionPreference.Parent ? 0 : 1);
	}

	protected void decideNewOrExistingFamily(FamilyPreference preference, int familyIndex) {
		userInteraction
			.onMultipleChoiceSingleSelection(interaction -> assertFamilyOptions(interaction))
			.respondWithChoiceAt(preference == FamilyPreference.New ? 0 : familyIndex);
	}

	// === assertions ===

	protected void assertFamily(Family expected, Family actual) {
		assertThat(actual, equalsDeeply(expected));
	}

	protected void assertNumberOfFamilies(TestModel<FamilyRegister> model, int expectedNumberOfFamilies) {
		assertEquals(expectedNumberOfFamilies, claimFamilyRegister(model).getFamilies().size());
	}

	private final String newOrExistingFamilyTitle = "New or Existing Family?";

	protected boolean assertFamilyOptions(MultipleChoiceInteractionDescription interactionDescription) {
		//First option is always a new family
		assertEquals(Iterables.get(interactionDescription.getChoices(), 0), "insert in a new family");
		Iterable<String> tail = Iterables.skip(interactionDescription.getChoices(), 1);
		//There must be a second option otherwise there would not be an interaction
		assertTrue(Iterables.size(tail) > 0);
		String familyName = Iterables.get(tail, 0).split(":")[0];
		//All other options have to offer families with the same name
		tail.forEach(familyOption -> familyOption.split(":")[0].equals(familyName));

		return interactionDescription.getTitle().equals(newOrExistingFamilyTitle);
	}

	// === data ===

	// First Set of reused static strings for the first names of the persons
	protected static final String FIRST_DAD_1 = "Anton";
	protected static final String FIRST_MOM_1 = "Berta";
	protected static final String FIRST_SON_1 = "Chris";
	protected static final String FIRST_DAU_1 = "Daria";

	// Second Set of reused static strings for the first names of the persons
	protected static final String FIRST_DAD_2 = "Adam";
	protected static final String FIRST_MOM_2 = "Birgit";
	protected static final String FIRST_SON_2 = "Charles";
	protected static final String FIRST_DAU_2 = "Daniela";

	// Set of reused static strings for the last names of the persons
	protected static final String LAST_NAME_1 = "Meier";
	protected static final String LAST_NAME_2 = "Schulze";
	protected static final String LAST_NAME_3 = "Müller";

	protected static final Family COMPLETE_FAMILY_1 = createCompleteFamily1();

	private static Family createCompleteFamily1() {
		Family family = FamiliesFactory.eINSTANCE.createFamily();
		family.setLastName(LAST_NAME_1);
		family.setFather(createMember(FIRST_DAD_1));
		family.setMother(createMember(FIRST_MOM_1));
		family.getSons().add(createMember(FIRST_SON_1));
		family.getDaughters().add(createMember(FIRST_DAU_1));
		return family;
	}

	private static Member createMember(String firstName) {
		Member member = FamiliesFactory.eINSTANCE.createMember();
		member.setFirstName(firstName);
		return member;
	}

	// Replaces the Xtend extension field _testModelFactory
	protected void changeInsuranceModel(Consumer<TestModel<InsuranceDatabase>> modelModification) {
		_testModelFactory.changeInsuranceModel(modelModification);
	}

	protected void changeFamilyModel(Consumer<TestModel<FamilyRegister>> modelModification) {
		_testModelFactory.changeFamilyModel(modelModification);
	}

	protected void validateInsuranceModel(Consumer<TestModel<InsuranceDatabase>> viewValidation) {
		_testModelFactory.validateInsuranceModel(viewValidation);
	}

	protected void validateFamilyModel(Consumer<TestModel<FamilyRegister>> viewValidation) {
		_testModelFactory.validateFamilyModel(viewValidation);
	}
}

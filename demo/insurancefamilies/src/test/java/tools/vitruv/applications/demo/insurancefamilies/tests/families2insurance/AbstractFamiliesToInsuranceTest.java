package tools.vitruv.applications.demo.insurancefamilies.tests.families2insurance;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createFamily;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createFamilyMember;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createInsuranceClient;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.FamiliesQueryUtil.claimFamilyRegister;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceQueryUtil.claimInsuranceDatabase;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.containsAllOf;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply;
import static tools.vitruv.change.testutils.views.ChangePublishingTestView.createDefaultChangePublishingTestView;

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.insurance.Gender;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceClient;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.applications.demo.insurancefamilies.families2insurance.FamiliesToInsuranceChangePropagationSpecification;
import tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceFamiliesDefaultTestModelFactory;
import tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceFamiliesTestModelFactory;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.testutils.TestLogging;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.change.testutils.views.NonTransactionalTestView;
import tools.vitruv.dsls.testutils.TestModel;

@ExtendWith({TestLogging.class, TestProjectManager.class})
public abstract class AbstractFamiliesToInsuranceTest {
	protected InsuranceFamiliesTestModelFactory _testModelFactory;
	protected Path testProjectPath;

	/**
	 * Can be used to set a different kind of test model factory to be used in subclasses.
	 */
	protected void setTestModelFactory(InsuranceFamiliesTestModelFactory testModelFactory) {
		this._testModelFactory = testModelFactory;
	}

	@BeforeEach
	public final void setupViewFactory(@TestProject Path testProjectPath) throws IOException {
		this.testProjectPath = testProjectPath;
		setTestModelFactory(new InsuranceFamiliesDefaultTestModelFactory(prepareTestView(testProjectPath)));
	}

	private NonTransactionalTestView prepareTestView(Path testProjectPath) throws IOException {
		return createDefaultChangePublishingTestView(testProjectPath, getChangePropagationSpecifications());
	}

	protected Iterable<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return List.of(new FamiliesToInsuranceChangePropagationSpecification());
	}

	protected enum MemberRole {
		Father,
		Mother,
		Son,
		Daughter
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

	protected void createAndRegisterRoot(TestModel<FamilyRegister> model, FamilyRegister rootObject, URI persistenceUri) {
		model.registerRoot(rootObject, persistenceUri);
	}

	protected void deleteRoot(EObject rootObject) {
		EcoreUtil.delete(rootObject);
	}

	// === creators ===

	private void createFamilyRegister(Consumer<FamilyRegister> familyRegisterInitalization) {
		changeFamilyModel(model -> {
			FamilyRegister familyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
			familyRegisterInitalization.accept(familyRegister);
			createAndRegisterRoot(model, familyRegister, getUri(getProjectModelPath("families", FAMILY_MODEL_FILE_EXTENSION)));
		});
	}

	// === initializers ===

	protected void createEmptyFamilyRegister() {
		createFamilyRegister(familyRegister -> {
		});
	}

	protected void createOneCompleteFamily() {
		createOneOptionalFamily(true, true, true, true);
	}

	protected void createOneOptionalFamily(boolean insertFather, boolean insertMother, boolean insertSon, boolean insertDaugther) {
		if (!(insertFather || insertMother || insertSon || insertDaugther)) {
			throw new IllegalArgumentException("can't create empty family");
		}
		createEmptyFamilyRegister();

		changeFamilyModel(model -> {
			FamilyRegister familyRegister = claimFamilyRegister(model);
			familyRegister.getFamilies().add(createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				if (insertFather) {
					family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_1)));
				}
				if (insertMother) {
					family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
				}
				if (insertSon) {
					family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
				}
				if (insertDaugther) {
					family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
				}
			}));
		});

		List<InsuranceClient> expectedInsuranceClients = new ArrayList<>();
		if (insertSon) {
			expectedInsuranceClients.add(SON11);
		}
		if (insertDaugther) {
			expectedInsuranceClients.add(DAU11);
		}
		if (insertFather) {
			expectedInsuranceClients.add(DAD11);
		}
		if (insertMother) {
			expectedInsuranceClients.add(MOM11);
		}

		InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase();
		expectedInsuranceDatabase.getInsuranceclient().addAll(expectedInsuranceClients);

		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	protected void createTwoCompleteFamilies() {
		createOneCompleteFamily();

		changeFamilyModel(model -> {
			FamilyRegister familyRegister = claimFamilyRegister(model);
			familyRegister.getFamilies().add(createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_2)));
				family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_2)));
				family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_2)));
				family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_2)));
			}));
		});

		InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase();
		expectedInsuranceDatabase.getInsuranceclient().addAll(List.of(SON11, DAU11, DAD11, MOM11, SON22, DAU22, DAD22, MOM22));
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	// === assertions ===

	protected void assertCorrectInsuranceDatabase(InsuranceDatabase expectedInsuranceDatabase, InsuranceDatabase insuranceDatabase) {
		assertEquals(expectedInsuranceDatabase.getInsuranceclient().size(), insuranceDatabase.getInsuranceclient().size());
		assertThat(insuranceDatabase.getInsuranceclient(), containsAllOf(expectedInsuranceDatabase.getInsuranceclient()));
		assertThat(expectedInsuranceDatabase.getInsuranceclient(), containsAllOf(insuranceDatabase.getInsuranceclient()));
	}

	public void assertCorrectFamilyRegister(FamilyRegister expectedFamilyRegister, FamilyRegister familyRegister) {
		assertThat(familyRegister, equalsDeeply(expectedFamilyRegister));
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

	/* Static reusable predefined InsuranceClients.
	 * The first number indicates from which string set (above) the forename is.
	 * the second number indicates from which string set (above) the lastname is.
	 */
	protected static final InsuranceClient DAD11 = createInsuranceClient(client -> { client.setName(FIRST_DAD_1 + " " + LAST_NAME_1); client.setGender(Gender.MALE); });
	protected static final InsuranceClient MOM11 = createInsuranceClient(client -> { client.setName(FIRST_MOM_1 + " " + LAST_NAME_1); client.setGender(Gender.FEMALE); });
	protected static final InsuranceClient SON11 = createInsuranceClient(client -> { client.setName(FIRST_SON_1 + " " + LAST_NAME_1); client.setGender(Gender.MALE); });
	protected static final InsuranceClient DAU11 = createInsuranceClient(client -> { client.setName(FIRST_DAU_1 + " " + LAST_NAME_1); client.setGender(Gender.FEMALE); });

	protected static final InsuranceClient DAD12 = createInsuranceClient(client -> { client.setName(FIRST_DAD_1 + " " + LAST_NAME_2); client.setGender(Gender.MALE); });
	protected static final InsuranceClient MOM12 = createInsuranceClient(client -> { client.setName(FIRST_MOM_1 + " " + LAST_NAME_2); client.setGender(Gender.FEMALE); });
	protected static final InsuranceClient SON12 = createInsuranceClient(client -> { client.setName(FIRST_SON_1 + " " + LAST_NAME_2); client.setGender(Gender.MALE); });
	protected static final InsuranceClient DAU12 = createInsuranceClient(client -> { client.setName(FIRST_DAU_1 + " " + LAST_NAME_2); client.setGender(Gender.FEMALE); });

	protected static final InsuranceClient DAD21 = createInsuranceClient(client -> { client.setName(FIRST_DAD_2 + " " + LAST_NAME_1); client.setGender(Gender.MALE); });
	protected static final InsuranceClient MOM21 = createInsuranceClient(client -> { client.setName(FIRST_MOM_2 + " " + LAST_NAME_1); client.setGender(Gender.FEMALE); });
	protected static final InsuranceClient SON21 = createInsuranceClient(client -> { client.setName(FIRST_SON_2 + " " + LAST_NAME_1); client.setGender(Gender.MALE); });
	protected static final InsuranceClient DAU21 = createInsuranceClient(client -> { client.setName(FIRST_DAU_2 + " " + LAST_NAME_1); client.setGender(Gender.FEMALE); });

	protected static final InsuranceClient DAD22 = createInsuranceClient(client -> { client.setName(FIRST_DAD_2 + " " + LAST_NAME_2); client.setGender(Gender.MALE); });
	protected static final InsuranceClient MOM22 = createInsuranceClient(client -> { client.setName(FIRST_MOM_2 + " " + LAST_NAME_2); client.setGender(Gender.FEMALE); });
	protected static final InsuranceClient SON22 = createInsuranceClient(client -> { client.setName(FIRST_SON_2 + " " + LAST_NAME_2); client.setGender(Gender.MALE); });
	protected static final InsuranceClient DAU22 = createInsuranceClient(client -> { client.setName(FIRST_DAU_2 + " " + LAST_NAME_2); client.setGender(Gender.FEMALE); });

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

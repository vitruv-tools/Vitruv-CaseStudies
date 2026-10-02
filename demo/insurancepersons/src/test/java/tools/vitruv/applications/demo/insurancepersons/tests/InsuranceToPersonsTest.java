package tools.vitruv.applications.demo.insurancepersons.tests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.exists;
import static tools.vitruv.change.testutils.views.ChangePublishingTestView.createDefaultChangePublishingTestView;

import edu.kit.ipd.sdq.metamodels.insurance.Gender;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceClient;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceFactory;
import edu.kit.ipd.sdq.metamodels.persons.Female;
import edu.kit.ipd.sdq.metamodels.persons.Male;
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister;
import edu.kit.ipd.sdq.metamodels.persons.PersonsFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.applications.demo.insurancepersons.insurance2persons.InsuranceToPersonsChangePropagationSpecification;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.testutils.TestLogging;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.change.testutils.TestUserInteraction;
import tools.vitruv.change.testutils.views.TestView;

@ExtendWith({TestLogging.class, TestProjectManager.class})
public class InsuranceToPersonsTest implements TestView {
	private TestView testView;

	/**
	 * Can be used to set a different kind of test view to be used in subclasses.
	 */
	protected void setTestView(TestView testView) {
		this.testView = testView;
	}

	protected Iterable<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return List.of(new InsuranceToPersonsChangePropagationSpecification());
	}

	@BeforeEach
	public void prepare(@TestProject Path testProjectPath) throws IOException {
		testView = prepareTestView(testProjectPath);
	}

	private TestView prepareTestView(Path testProjectPath) throws IOException {
		return createDefaultChangePublishingTestView(testProjectPath, getChangePropagationSpecifications());
	}

	@AfterEach
	public void cleanup() throws Exception {
		testView.close();
	}

	private static final String MALE_NAME = "Max Mustermann";
	private static final String MALE_NAME_2 = "Bernd Mustermann";
	private static final String FEMALE_NAME = "Erika Mustermann";
	private static final String FEMALE_NAME_2 = "Berta Mustermann";
	private static final String FEMALE_NAME_3 = "Berta Musterfrau";
	private static final String SPECIAL_CHAR_NAME = "Berta? Müster-frau";
	// Model Paths
	private static final Path PERSONS_MODEL = Path.of("model/persons.persons");
	private static final Path INSURANCE_MODEL = Path.of("model/insurance.insurance");

	/**Before each test a new {@link InsuranceDatabase} has to be created as starting point.
	 * This is checked by several assertions to ensure correct preconditions for the tests.
	 */
	public void insertRegister() {
		propagate(resourceAt(INSURANCE_MODEL), resource -> {
			resource.getContents().add(InsuranceFactory.eINSTANCE.createInsuranceDatabase());
		});
		assertThat(resourceAt(PERSONS_MODEL), exists());
		assertEquals(1, resourceAt(PERSONS_MODEL).getContents().size());
		assertEquals(1, countElements(resourceAt(PERSONS_MODEL).getAllContents()));
		assertThat(resourceAt(PERSONS_MODEL).getContents().get(0), instanceOf(PersonRegister.class));
		assertEquals(0, countElements(resourceAt(PERSONS_MODEL).getContents().get(0).eAllContents()));
	}

	/**Check if the actual {@link PersonRegister} looks like the expected one.
	 */
	public void assertCorrectPersonRegister(PersonRegister expectedPersonRegister) {
		Resource personModel = resourceAt(PERSONS_MODEL);
		assertThat(personModel, exists());
		assertEquals(1, personModel.getContents().size());
		EObject personRegister = personModel.getContents().get(0);
		assertThat(personRegister, instanceOf(PersonRegister.class));
		PersonRegister castedPersonRegister = (PersonRegister) personRegister;
		assertThat(castedPersonRegister, equalsDeeply(expectedPersonRegister));
	}

	/**Check if the actual {@link InsuranceDatabase} looks like the expected one.
	 */
	public void assertCorrectInsuranceDatabase(InsuranceDatabase expectedInsuranceDatabase) {
		Resource insuranceModel = resourceAt(INSURANCE_MODEL);
		assertThat(insuranceModel, exists());
		assertEquals(1, insuranceModel.getContents().size());
		EObject insuranceDatabase = insuranceModel.getContents().get(0);
		assertThat(insuranceDatabase, instanceOf(InsuranceDatabase.class));
		InsuranceDatabase castedInsuranceDatabase = (InsuranceDatabase) insuranceDatabase;
		assertThat(castedInsuranceDatabase, equalsDeeply(expectedInsuranceDatabase));
	}

	@Test
	public void testCreateInsuranceDatabase() {
		insertRegister();
		assertThat(resourceAt(INSURANCE_MODEL), exists());
		assertThat(resourceAt(PERSONS_MODEL), exists());

		InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase();
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();

		assertCorrectInsuranceDatabase(expectedInsuranceDatabase);
		assertCorrectPersonRegister(expectedPersonRegister);
	}

	@Test
	public void testDeleteInsuranceDatabase() {
		insertRegister();
		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
		});

		propagate(resourceAt(INSURANCE_MODEL), resource -> {
			resource.getContents().clear();
		});

		assertEquals(0, resourceAt(INSURANCE_MODEL).getContents().size());
		assertEquals(0, resourceAt(PERSONS_MODEL).getContents().size());
		assertThat(resourceAt(INSURANCE_MODEL), not(exists()));
		assertThat(resourceAt(PERSONS_MODEL), not(exists()));
	}

	@Test
	public void testCreatedClient() {
		insertRegister();
		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			InsuranceClient insuranceClient = InsuranceFactory.eINSTANCE.createInsuranceClient();
			insuranceClient.setName(MALE_NAME);
			insuranceDatabase.getInsuranceclient().add(insuranceClient);
		});

		InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase();
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(MALE_NAME));

		assertCorrectInsuranceDatabase(expectedInsuranceDatabase);
		assertCorrectPersonRegister(expectedPersonRegister);
	}

	@Test
	public void testCreatedClient_multiple() {
		insertRegister();
		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME, Gender.FEMALE));
		});

		InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase();
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME, Gender.FEMALE));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(MALE_NAME));
		expectedPersonRegister.getPersons().add(createFemale(FEMALE_NAME));

		assertCorrectInsuranceDatabase(expectedInsuranceDatabase);
		assertCorrectPersonRegister(expectedPersonRegister);
	}

	@Test
	public void testChangedName() {
		insertRegister();
		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME, Gender.FEMALE));
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME_2, Gender.FEMALE));
		});

		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			InsuranceClient searchedClient = insuranceDatabase.getInsuranceclient().stream()
				.filter(client -> client.getName().equals(FEMALE_NAME_2))
				.findFirst()
				.orElse(null);
			searchedClient.setName(FEMALE_NAME_3);
		});

		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(MALE_NAME));
		expectedPersonRegister.getPersons().add(createFemale(FEMALE_NAME));
		expectedPersonRegister.getPersons().add(createFemale(FEMALE_NAME_3));
		InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase();
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME, Gender.FEMALE));
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME_3, Gender.FEMALE));
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase);
		assertCorrectPersonRegister(expectedPersonRegister);
	}

	@Test
	public void testChangedName_empty() {
		insertRegister();
		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME, Gender.FEMALE));
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME_2, Gender.MALE));
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME_2, Gender.FEMALE));
		});

		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			InsuranceClient searchedClient = insuranceDatabase.getInsuranceclient().stream()
				.filter(client -> client.getName().equals(FEMALE_NAME))
				.findFirst()
				.orElse(null);
			searchedClient.setName("");
		});

		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(MALE_NAME));
		expectedPersonRegister.getPersons().add(createFemale(""));
		expectedPersonRegister.getPersons().add(createMale(MALE_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(FEMALE_NAME_2));
		InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase();
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient("", Gender.FEMALE));
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME_2, Gender.MALE));
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME_2, Gender.FEMALE));
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase);
		assertCorrectPersonRegister(expectedPersonRegister);
	}

	@Test
	public void testChangedName_specialChars() {
		insertRegister();
		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME, Gender.FEMALE));
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME_2, Gender.MALE));
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME_2, Gender.FEMALE));
		});

		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			InsuranceClient searchedClient = insuranceDatabase.getInsuranceclient().stream()
				.filter(client -> client.getName().equals(FEMALE_NAME_2))
				.findFirst()
				.orElse(null);
			searchedClient.setName(SPECIAL_CHAR_NAME);
		});

		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(MALE_NAME));
		expectedPersonRegister.getPersons().add(createFemale(FEMALE_NAME));
		expectedPersonRegister.getPersons().add(createMale(MALE_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(SPECIAL_CHAR_NAME));
		InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase();
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME, Gender.FEMALE));
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME_2, Gender.MALE));
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(SPECIAL_CHAR_NAME, Gender.FEMALE));
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase);
		assertCorrectPersonRegister(expectedPersonRegister);
	}

	@Test
	public void testChangedGender_toFemale() {
		insertRegister();
		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME, Gender.FEMALE));
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME_2, Gender.MALE));
		});

		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			InsuranceClient searchedClient = insuranceDatabase.getInsuranceclient().stream()
				.filter(client -> client.getGender().equals(Gender.MALE))
				.findFirst()
				.orElse(null);
			searchedClient.setGender(Gender.FEMALE);
		});

		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createFemale(MALE_NAME));
		expectedPersonRegister.getPersons().add(createFemale(FEMALE_NAME));
		expectedPersonRegister.getPersons().add(createMale(MALE_NAME_2));
		InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase();
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.FEMALE));
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME, Gender.FEMALE));
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME_2, Gender.MALE));
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase);
		assertCorrectPersonRegister(expectedPersonRegister);
	}

	@Test
	public void testChangedGender_toMale() {
		insertRegister();
		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME, Gender.FEMALE));
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME_2, Gender.MALE));
		});

		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			InsuranceClient searchedClient = insuranceDatabase.getInsuranceclient().stream()
				.filter(client -> client.getGender().equals(Gender.FEMALE))
				.findFirst()
				.orElse(null);
			searchedClient.setGender(Gender.MALE);
		});

		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(MALE_NAME));
		expectedPersonRegister.getPersons().add(createMale(FEMALE_NAME));
		expectedPersonRegister.getPersons().add(createMale(MALE_NAME_2));
		InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase();
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME, Gender.MALE));
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME_2, Gender.MALE));
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase);
		assertCorrectPersonRegister(expectedPersonRegister);
	}

	@Test
	public void testDeletedClient_first_notOnly() {
		insertRegister();
		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME, Gender.FEMALE));
		});

		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			InsuranceClient searchedClient = insuranceDatabase.getInsuranceclient().stream()
				.filter(client -> client.getName().equals(MALE_NAME))
				.findFirst()
				.orElse(null);
			insuranceDatabase.getInsuranceclient().remove(searchedClient);
		});

		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createFemale(FEMALE_NAME));
		InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase();
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME, Gender.FEMALE));
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase);
		assertCorrectPersonRegister(expectedPersonRegister);
	}

	@Test
	public void testDeletedClient_middle_notOnly() {
		insertRegister();
		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME, Gender.FEMALE));
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME_2, Gender.FEMALE));
		});

		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			InsuranceClient searchedClient = insuranceDatabase.getInsuranceclient().stream()
				.filter(client -> client.getName().equals(FEMALE_NAME))
				.findFirst()
				.orElse(null);
			insuranceDatabase.getInsuranceclient().remove(searchedClient);
		});

		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(MALE_NAME));
		expectedPersonRegister.getPersons().add(createFemale(FEMALE_NAME_2));
		InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase();
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME_2, Gender.FEMALE));
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase);
		assertCorrectPersonRegister(expectedPersonRegister);
	}

	@Test
	public void testDeletedClient_last_notOnly() {
		insertRegister();
		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME, Gender.FEMALE));
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME_2, Gender.FEMALE));
		});

		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			InsuranceClient searchedClient = insuranceDatabase.getInsuranceclient().stream()
				.filter(client -> client.getName().equals(FEMALE_NAME_2))
				.findFirst()
				.orElse(null);
			insuranceDatabase.getInsuranceclient().remove(searchedClient);
		});

		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(MALE_NAME));
		expectedPersonRegister.getPersons().add(createFemale(FEMALE_NAME));
		InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase();
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
		expectedInsuranceDatabase.getInsuranceclient().add(createInsuranceClient(FEMALE_NAME, Gender.FEMALE));
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase);
		assertCorrectPersonRegister(expectedPersonRegister);
	}

	@Test
	public void testDeletedClient_only() {
		insertRegister();
		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			insuranceDatabase.getInsuranceclient().add(createInsuranceClient(MALE_NAME, Gender.MALE));
		});

		propagate(from(InsuranceDatabase.class, INSURANCE_MODEL), insuranceDatabase -> {
			InsuranceClient searchedClient = insuranceDatabase.getInsuranceclient().stream()
				.filter(client -> client.getName().equals(MALE_NAME))
				.findFirst()
				.orElse(null);
			insuranceDatabase.getInsuranceclient().remove(searchedClient);
		});

		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase();

		assertCorrectInsuranceDatabase(expectedInsuranceDatabase);
		assertCorrectPersonRegister(expectedPersonRegister);
	}

	// Helpers replacing the Xtend builder expressions

	private static InsuranceClient createInsuranceClient(String name, Gender gender) {
		InsuranceClient insuranceClient = InsuranceFactory.eINSTANCE.createInsuranceClient();
		insuranceClient.setName(name);
		insuranceClient.setGender(gender);
		return insuranceClient;
	}

	private static Male createMale(String fullName) {
		Male male = PersonsFactory.eINSTANCE.createMale();
		male.setFullName(fullName);
		return male;
	}

	private static Female createFemale(String fullName) {
		Female female = PersonsFactory.eINSTANCE.createFemale();
		female.setFullName(fullName);
		return female;
	}

	private static int countElements(Iterator<?> iterator) {
		int count = 0;
		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}
		return count;
	}

	// Delegation of the TestView methods to the test view

	@Override
	public void close() throws Exception {
		testView.close();
	}

	@Override
	public <T extends EObject> T from(Class<T> clazz, Path viewRelativePath) {
		return testView.from(clazz, viewRelativePath);
	}

	@Override
	public <T extends EObject> T from(Class<T> clazz, Resource resource) {
		return testView.from(clazz, resource);
	}

	@Override
	public <T extends EObject> T from(Class<T> clazz, URI modelUri) {
		return testView.from(clazz, modelUri);
	}

	@Override
	public URI getUri(Path viewRelativePath) {
		return testView.getUri(viewRelativePath);
	}

	@Override
	public TestUserInteraction getUserInteraction() {
		return testView.getUserInteraction();
	}

	@Override
	public void moveTo(Resource resource, Path newViewRelativePath) {
		testView.moveTo(resource, newViewRelativePath);
	}

	@Override
	public void moveTo(Resource resource, URI newUri) {
		testView.moveTo(resource, newUri);
	}

	@Override
	public <T extends Notifier> List<PropagatedChange> propagate(T notifier, Consumer<T> consumer) {
		return testView.propagate(notifier, consumer);
	}

	@Override
	public <T extends Notifier> T record(T notifier, Consumer<T> consumer) {
		return testView.record(notifier, consumer);
	}

	@Override
	public Resource resourceAt(Path viewRelativePath) {
		return testView.resourceAt(viewRelativePath);
	}

	@Override
	public Resource resourceAt(URI modelUri) {
		return testView.resourceAt(modelUri);
	}
}

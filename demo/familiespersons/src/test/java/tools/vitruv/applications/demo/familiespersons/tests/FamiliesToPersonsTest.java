package tools.vitruv.applications.demo.familiespersons.tests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tools.vitruv.applications.demo.familiespersons.tests.PropagationExceptionAssertions.assertPropagationException;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.exists;
import static tools.vitruv.change.testutils.views.ChangePublishingTestView.createDefaultChangePublishingTestView;

import com.google.common.collect.Iterators;
import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory;
import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.families.Member;
import edu.kit.ipd.sdq.metamodels.persons.Female;
import edu.kit.ipd.sdq.metamodels.persons.Male;
import edu.kit.ipd.sdq.metamodels.persons.Person;
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister;
import edu.kit.ipd.sdq.metamodels.persons.PersonsFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tools.vitruv.applications.demo.familiespersons.families2persons.FamiliesToPersonsChangePropagationSpecification;
import tools.vitruv.applications.demo.familiespersons.families2persons.FamiliesToPersonsHelper;
import tools.vitruv.applications.demo.familiespersons.persons2families.PersonsToFamiliesChangePropagationSpecification;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.testutils.TestLogging;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.change.testutils.TestUserInteraction;
import tools.vitruv.change.testutils.views.TestView;

/**Test to validate the transfer of changes from the FamilyModel to the PersonModel.
 * @author Dirk Neumann
 */
@ExtendWith({TestLogging.class, TestProjectManager.class})
public class FamiliesToPersonsTest implements TestView {
	private static final Logger logger = LogManager.getLogger(FamiliesToPersonsTest.class);
	private String nameOfTestMethod = null;

	private TestView testView;

	/**
	 * Can be used to set a different kind of test view to be used in subclasses.
	 */
	protected void setTestView(TestView testView) {
		this.testView = testView;
	}

	protected Iterable<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return List.of(new FamiliesToPersonsChangePropagationSpecification(), new PersonsToFamiliesChangePropagationSpecification());
	}

	@BeforeEach
	public void prepare(TestInfo testInfo, @TestProject Path testProjectPath) throws IOException {
		this.nameOfTestMethod = testInfo.getDisplayName();
		testView = prepareTestView(testProjectPath);
	}

	private TestView prepareTestView(Path testProjectPath) throws IOException {
		return createDefaultChangePublishingTestView(testProjectPath, getChangePropagationSpecifications());
	}

	@AfterEach
	public void cleanup() throws Exception {
		testView.close();
	}

	// First Set of reused static strings for the first names of the persons
	private static final String FIRST_DAD_1 = "Anton";
	private static final String FIRST_MOM_1 = "Berta";
	private static final String FIRST_SON_1 = "Chris";
	private static final String FIRST_DAU_1 = "Daria";

	// Second Set of reused static strings for the first names of the persons
	private static final String FIRST_DAD_2 = "Adam";
	private static final String FIRST_MOM_2 = "Birgit";
	private static final String FIRST_SON_2 = "Charles";
	private static final String FIRST_DAU_2 = "Daniela";

	// Set of reused static strings for the last names of the persons
	private static final String LAST_NAME_1 = "Meier";
	private static final String LAST_NAME_2 = "Schulze";
	private static final String LAST_NAME_3 = "Müller";


	// Model Paths
	private static final Path PERSONS_MODEL = Path.of("model/persons.persons");
	private static final Path FAMILIES_MODEL = Path.of("model/families.families");

	/* Static reusable predefined Persons.
	 * The first number indicates from which string set (above) the forename is.
	 * the second number indicates from which string set (above) the lastname is.
	 */
	private static final Male DAD11 = createMale(FIRST_DAD_1 + " " + LAST_NAME_1);
	private static final Female MOM11 = createFemale(FIRST_MOM_1 + " " + LAST_NAME_1);
	private static final Male SON11 = createMale(FIRST_SON_1 + " " + LAST_NAME_1);
	private static final Female DAU11 = createFemale(FIRST_DAU_1 + " " + LAST_NAME_1);

	private static final Male DAD12 = createMale(FIRST_DAD_1 + " " + LAST_NAME_2);
	private static final Female MOM12 = createFemale(FIRST_MOM_1 + " " + LAST_NAME_2);
	private static final Male SON12 = createMale(FIRST_SON_1 + " " + LAST_NAME_2);
	private static final Female DAU12 = createFemale(FIRST_DAU_1 + " " + LAST_NAME_2);

	private static final Male DAD21 = createMale(FIRST_DAD_2 + " " + LAST_NAME_1);
	private static final Female MOM21 = createFemale(FIRST_MOM_2 + " " + LAST_NAME_1);
	private static final Male SON21 = createMale(FIRST_SON_2 + " " + LAST_NAME_1);
	private static final Female DAU21 = createFemale(FIRST_DAU_2 + " " + LAST_NAME_1);

	private static final Male DAD22 = createMale(FIRST_DAD_2 + " " + LAST_NAME_2);
	private static final Female MOM22 = createFemale(FIRST_MOM_2 + " " + LAST_NAME_2);
	private static final Male SON22 = createMale(FIRST_SON_2 + " " + LAST_NAME_2);
	private static final Female DAU22 = createFemale(FIRST_DAU_2 + " " + LAST_NAME_2);


	/**
	 * Before each test a new {@link FamilyRegister} has to be created as starting point.
	 * This is checked by several assertions to ensure correct preconditions for the tests.
	 */
	public void insertRegister() {
		Resource x = resourceAt(FAMILIES_MODEL);
		propagate(x, resource -> resource.getContents().add(FamiliesFactory.eINSTANCE.createFamilyRegister()));
		assertThat(resourceAt(PERSONS_MODEL), exists());
		assertEquals(1, resourceAt(PERSONS_MODEL).getContents().size());
		assertEquals(1, Iterators.size(resourceAt(PERSONS_MODEL).getAllContents()));
		assertThat(resourceAt(PERSONS_MODEL).getContents().get(0), instanceOf(PersonRegister.class));
		assertEquals(0, Iterators.size(resourceAt(PERSONS_MODEL).getContents().get(0).eAllContents()));
	}

	/**Creates a {@link Family} with the given familieName. Used by the "insertFamilyWith..."-tests
	 */
	public Family createFamily(String familieName) {
		Family family = FamiliesFactory.eINSTANCE.createFamily();
		family.setLastName(familieName);
		return family;
	}

	/**Inserts a new {@link Family}. This should not have any effect on the Persons-Model.
	 */
	@Test
	public void testInsertNewFamily() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		Family family = createFamily(LAST_NAME_1);
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> familyRegister.getFamilies().add(family));
		logger.trace(nameOfTestMethod + " - propagation done");
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Checks if the actual {@link FamilyRegister looks like the expected one.
	 */
	public void assertCorrectFamilyRegister(FamilyRegister expectedFamilyRegister) {
		Resource familyModel = resourceAt(FAMILIES_MODEL);
		assertThat(familyModel, exists());
		assertEquals(1, familyModel.getContents().size());
		EObject familyRegister = familyModel.getContents().get(0);
		assertThat(familyRegister, instanceOf(FamilyRegister.class));
		FamilyRegister castedFamilyRegister = (FamilyRegister) familyRegister;
		assertThat(castedFamilyRegister, equalsDeeply(expectedFamilyRegister));
	}

	/**Checks if the actual {@link PersonRegister looks like the expected one.
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

	/**Inserts a new {@link Family} and insert a father into it afterwards.
	 */
	@Test
	public void testInsertFamilyWithFather() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		Family family = createFamily(LAST_NAME_1);
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(family);
			family.setFather(createMember(FIRST_DAD_1));
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_1));
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Inserts a new {@link Family} and insert a mother into it afterwards.
	 */
	@Test
	public void testInsertFamilyWithMother() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		Family family = createFamily(LAST_NAME_1);
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(family);
			family.setMother(createMember(FIRST_MOM_1));
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(MOM11);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Inserts a new {@link Family} and insert a son into it afterwards.
	 */
	@Test
	public void testInsertFamilyWithSon() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		Family family = createFamily(LAST_NAME_1);
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(family);
			family.getSons().add(createMember(FIRST_SON_1));
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(SON11);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Inserts a new {@link Family} and insert a daughter into it afterwards.
	 */
	@Test
	public void testInsertFamilyWithDaughter() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		Family family = createFamily(LAST_NAME_1);
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(family);
			family.getDaughters().add(createMember(FIRST_DAU_1));
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(DAU11);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Used to build the starting point for many other tests like deleting and renaming operations.
	 * Creates a {@link Family} including a father, a mother, a son and a daughter and maps this
	 * changes to the {@link PersonRegister} which then includes two {@link Male} and two {@link Female}.
	 */
	public void createOneFamilyBeforeTesting() {
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}));
		});
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD11, MOM11, SON11, DAU11));
		assertCorrectPersonRegister(expectedPersonRegister);
	}

	/**Used to build an extended starting point for many other tests like replacing and moving members.
	 * Creates a {@link Family} including a father, a mother, a son and a daughter and maps this
	 * changes to the {@link PersonRegister} which then includes two {@link Male} and two {@link Female}.
	 */
	public void createTwoFamiliesBeforeTesting() {
		this.createOneFamilyBeforeTesting();
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setFather(createMember(FIRST_DAD_2));
				family.setMother(createMember(FIRST_MOM_2));
				family.getSons().add(createMember(FIRST_SON_2));
				family.getDaughters().add(createMember(FIRST_DAU_2));
			}));
		});
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD11, MOM11, SON11, DAU11, DAD22, MOM22, SON22, DAU22));
		assertCorrectPersonRegister(expectedPersonRegister);
	}

	/**Deletes a father from a {@link Family} and the corresponding {@link Male} from the {@link PersonRegister}.
	 */
	@Test
	public void testDeleteFatherFromFamily() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family selectedFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1) && family.getFather().getFirstName().equals(FIRST_DAD_1))
				.findFirst().orElse(null);
			selectedFamily.setFather(null);
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(MOM11, SON11, DAU11));
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Deletes a son from a {@link Family} and the corresponding {@link Male} from the {@link PersonRegister}.
	 */
	@Test
	public void testDeleteSonFromFamily() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family selectedFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1)
					&& family.getSons().stream().anyMatch(son -> son.getFirstName().equals(FIRST_SON_1)))
				.findFirst().orElse(null);
			Member sonToDelete = selectedFamily.getSons().stream()
				.filter(son -> son.getFirstName().equals(FIRST_SON_1))
				.findFirst().orElse(null);
			selectedFamily.getSons().remove(sonToDelete);
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD11, MOM11, DAU11));
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Deletes a mother from a {@link Family} and the corresponding {@link Female} from the {@link PersonRegister}.
	 */
	@Test
	public void testDeleteMotherFromFamily() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family selectedFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1) && family.getMother().getFirstName().equals(FIRST_MOM_1))
				.findFirst().orElse(null);
			selectedFamily.setMother(null);
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD11, SON11, DAU11));
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Deletes a daughter from a {@link Family} and the corresponding {@link Female} from the {@link PersonRegister}.
	 */
	@Test
	public void testDeleteDaughterFromFamily() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family selectedFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1)
					&& family.getDaughters().stream().anyMatch(daughter -> daughter.getFirstName().equals(FIRST_DAU_1)))
				.findFirst().orElse(null);
			Member daughterToDelete = selectedFamily.getDaughters().stream()
				.filter(daughter -> daughter.getFirstName().equals(FIRST_DAU_1))
				.findFirst().orElse(null);
			selectedFamily.getDaughters().remove(daughterToDelete);
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD11, MOM11, SON11));
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Changes the lastname of a {@link Family} and should edit the fullnames of
	 * all corresponding {@link Person}s from the {@link PersonRegister}.
	 */
	@Test
	public void testChangeLastName() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family selectedFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			selectedFamily.setLastName(LAST_NAME_2);
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD12, MOM12, SON12, DAU12));
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Changes the firstname of a father of a {@link Family} and should edit the
	 * fullname of the corresponding {@link Male} in the {@link PersonRegister}.
	 */
	@Test
	public void testChangeFirstNameFather() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family selectedFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1) && family.getFather().getFirstName().equals(FIRST_DAD_1))
				.findFirst().orElse(null);
			selectedFamily.getFather().setFirstName(FIRST_DAD_2);
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD21, MOM11, SON11, DAU11));
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Changes the firstname of a son of a {@link Family} and should edit the
	 * fullname of the corresponding {@link Male} in the {@link PersonRegister}.
	 */
	@Test
	public void testChangeFirstNameSon() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family selectedFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1)
					&& family.getSons().stream().anyMatch(son -> son.getFirstName().equals(FIRST_SON_1)))
				.findFirst().orElse(null);
			Member sonToChange = selectedFamily.getSons().stream()
				.filter(son -> son.getFirstName().equals(FIRST_SON_1))
				.findFirst().orElse(null);
			sonToChange.setFirstName(FIRST_SON_2);
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD11, MOM11, SON21, DAU11));
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Changes the firstname of a mother of a {@link Family} and should edit the
	 * fullname of the corresponding {@link Female} in the {@link PersonRegister}.
	 */
	@Test
	public void testChangeFirstNameMother() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family selectedFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1) && family.getMother().getFirstName().equals(FIRST_MOM_1))
				.findFirst().orElse(null);
			selectedFamily.getMother().setFirstName(FIRST_MOM_2);
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD11, MOM21, SON11, DAU11));
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Changes the firstname of a daughter of a {@link Family} and should edit the
	 * fullname of the corresponding {@link Female} in the {@link PersonRegister}.
	 */
	@Test
	public void testChangeFirstNameDaughter() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family selectedFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1)
					&& family.getDaughters().stream().anyMatch(daughter -> daughter.getFirstName().equals(FIRST_DAU_1)))
				.findFirst().orElse(null);
			Member daughterToChange = selectedFamily.getDaughters().stream()
				.filter(daughter -> daughter.getFirstName().equals(FIRST_DAU_1))
				.findFirst().orElse(null);
			daughterToChange.setFirstName(FIRST_DAU_2);
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD11, MOM11, SON11, DAU21));
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	// ========== REPLACING PARENTS ==========
	/**Replace the father with a new member which causes the original father to be moved
	 * to a new family with the same lastname in which he is the only member.
	 */
	@Test
	public void testReplaceFatherWithNewMember() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family family = familyRegister.getFamilies().stream()
				.filter(candidate -> candidate.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			family.setFather(createMember(FIRST_DAD_2));
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createMember(FIRST_DAD_2));
			family.setMother(createMember(FIRST_MOM_1));
			family.getSons().add(createMember(FIRST_SON_1));
			family.getDaughters().add(createMember(FIRST_DAU_1));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD21, MOM11, SON11, DAU11));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Replace the father with a father from a different family which causes the original father
	 * to be moved to a new family with the same lastname in which he is the only member.
	 * The replacing father will be removed from his original family in return.
	 */
	@Test
	public void testReplaceFatherWithExistingFather() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createTwoFamiliesBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family family1 = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			Family family2 = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_2))
				.findFirst().orElse(null);
			family1.setFather(family2.getFather());
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createMember(FIRST_DAD_2));
			family.setMother(createMember(FIRST_MOM_1));
			family.getSons().add(createMember(FIRST_SON_1));
			family.getDaughters().add(createMember(FIRST_DAU_1));
		}));
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_2);
			family.setMother(createMember(FIRST_MOM_2));
			family.getSons().add(createMember(FIRST_SON_2));
			family.getDaughters().add(createMember(FIRST_DAU_2));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD21, MOM11, SON11, DAU11, MOM22, SON22, DAU22));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Replace the father with a father from a different family which causes the original father
	 * to be moved to a new family with the same lastname in which he is the only member.
	 * The replacing father will be removed from his original family in return.
	 *
	 * In this version, the replacing father was the last member of his family before
	 * the replacing happens. Therefore, his old family will be deleted afterwards.
	 */
	@Test
	public void testReplaceFatherWithExistingPreviouslyLonlyFather() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setFather(createMember(FIRST_DAD_2));
			}));
		});
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family family1 = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			Family family2 = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_2))
				.findFirst().orElse(null);
			family1.setFather(family2.getFather());
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createMember(FIRST_DAD_2));
			family.setMother(createMember(FIRST_MOM_1));
			family.getSons().add(createMember(FIRST_SON_1));
			family.getDaughters().add(createMember(FIRST_DAU_1));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD21, MOM11, SON11, DAU11));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Replace the father with a son from a different family which causes the original father
	 * to be moved to a new family with the same lastname in which he is the only member.
	 * The replacing son will be removed from his original family in return.
	 */
	@Test
	public void testReplaceFatherWithExistingSon() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createTwoFamiliesBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family family1 = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			Family family2 = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_2))
				.findFirst().orElse(null);
			family1.setFather(family2.getSons().stream()
				.filter(son -> son.getFirstName().equals(FIRST_SON_2))
				.findFirst().orElse(null));
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createMember(FIRST_SON_2));
			family.setMother(createMember(FIRST_MOM_1));
			family.getSons().add(createMember(FIRST_SON_1));
			family.getDaughters().add(createMember(FIRST_DAU_1));
		}));
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_2);
			family.setFather(createMember(FIRST_DAD_2));
			family.setMother(createMember(FIRST_MOM_2));
			family.getDaughters().add(createMember(FIRST_DAU_2));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(SON21, MOM11, SON11, DAU11, DAD22, MOM22, DAU22));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Replace the mother with a new member which causes the original mother to be moved
	 * to a new family with the same lastname in which she is the only member.
	 */
	@Test
	public void testReplaceMotherWithNewMember() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family family = familyRegister.getFamilies().stream()
				.filter(candidate -> candidate.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			family.setMother(createMember(FIRST_MOM_2));
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createMember(FIRST_DAD_1));
			family.setMother(createMember(FIRST_MOM_2));
			family.getSons().add(createMember(FIRST_SON_1));
			family.getDaughters().add(createMember(FIRST_DAU_1));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD11, MOM21, SON11, DAU11));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Replace the mother with a mother from a different family which causes the original mother
	 * to be moved to a new family with the same lastname in which she is the only member.
	 * The replacing mother will be removed from its original family in return.
	 */
	@Test
	public void testReplaceMotherWithExistingMother() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createTwoFamiliesBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family family1 = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			Family family2 = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_2))
				.findFirst().orElse(null);
			family1.setMother(family2.getMother());
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createMember(FIRST_DAD_1));
			family.setMother(createMember(FIRST_MOM_2));
			family.getSons().add(createMember(FIRST_SON_1));
			family.getDaughters().add(createMember(FIRST_DAU_1));
		}));
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_2);
			family.setFather(createMember(FIRST_DAD_2));
			family.getSons().add(createMember(FIRST_SON_2));
			family.getDaughters().add(createMember(FIRST_DAU_2));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD11, MOM21, SON11, DAU11, DAD22, SON22, DAU22));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Replace the mother with a daughter from a different family which causes the original mother
	 * to be moved to a new family with the same lastname in which she is the only member.
	 * The replacing daughter will be removed from her original family in return.
	 */
	@Test
	public void testReplaceMotherWithExistingDaughter() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createTwoFamiliesBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family family1 = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			Family family2 = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_2))
				.findFirst().orElse(null);
			family1.setMother(family2.getDaughters().stream()
				.filter(daughter -> daughter.getFirstName().equals(FIRST_DAU_2))
				.findFirst().orElse(null));
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createMember(FIRST_DAD_1));
			family.setMother(createMember(FIRST_DAU_2));
			family.getSons().add(createMember(FIRST_SON_1));
			family.getDaughters().add(createMember(FIRST_DAU_1));
		}));
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_2);
			family.setFather(createMember(FIRST_DAD_2));
			family.setMother(createMember(FIRST_MOM_2));
			family.getSons().add(createMember(FIRST_SON_2));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD11, DAU21, SON11, DAU11, DAD22, MOM22, SON22));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Replace the mother with a daughter from a different family which causes the original mother
	 * to be moved to a new family with the same lastname in which she is the only member.
	 * The replacing daughter will be removed from her original family in return.
	 *
	 * In this version, the replacing daughter was the last member of her family before
	 * the replacing happens. Therefore, her old family will be deleted afterwards.
	 */
	@Test
	public void testReplaceMotherWithExistingPreviouslyLonlyDaughter() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.getDaughters().add(createMember(FIRST_DAU_2));
			}));
		});
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family family1 = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			Family family2 = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_2))
				.findFirst().orElse(null);
			family1.setMother(family2.getDaughters().stream()
				.filter(daughter -> daughter.getFirstName().equals(FIRST_DAU_2))
				.findFirst().orElse(null));
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createMember(FIRST_DAD_1));
			family.setMother(createMember(FIRST_DAU_2));
			family.getSons().add(createMember(FIRST_SON_1));
			family.getDaughters().add(createMember(FIRST_DAU_1));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD11, DAU21, SON11, DAU11));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	// ========== MOVING MEMBERS - SAME POSITION ==========
	/**A father switches his family and stays a father.
	 */
	@Test
	public void testSwitchFamilySamePositionFather() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(this.createFamily(LAST_NAME_2));
		});
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family oldFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			Family newFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_2))
				.findFirst().orElse(null);
			newFamily.setFather(oldFamily.getFather());
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setMother(createMember(FIRST_MOM_1));
			family.getSons().add(createMember(FIRST_SON_1));
			family.getDaughters().add(createMember(FIRST_DAU_1));
		}));
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_2);
			family.setFather(createMember(FIRST_DAD_1));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD12, MOM11, SON11, DAU11));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**A mother switches her family and stays a mother.
	 */
	@Test
	public void testSwitchFamilySamePositionMother() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(this.createFamily(LAST_NAME_2));
		});
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family oldFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			Family newFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_2))
				.findFirst().orElse(null);
			newFamily.setMother(oldFamily.getMother());
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createMember(FIRST_DAD_1));
			family.getSons().add(createMember(FIRST_SON_1));
			family.getDaughters().add(createMember(FIRST_DAU_1));
		}));
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_2);
			family.setMother(createMember(FIRST_MOM_1));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD11, MOM12, SON11, DAU11));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**A son switches his family and stays a son.
	 */
	@Test
	public void testSwitchFamilySamePositionSon() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(this.createFamily(LAST_NAME_2));
		});
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family oldFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			Family newFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_2))
				.findFirst().orElse(null);
			newFamily.getSons().add(oldFamily.getSons().stream()
				.filter(son -> son.getFirstName().equals(FIRST_SON_1))
				.findFirst().orElse(null));
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createMember(FIRST_DAD_1));
			family.setMother(createMember(FIRST_MOM_1));
			family.getDaughters().add(createMember(FIRST_DAU_1));
		}));
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_2);
			family.getSons().add(createMember(FIRST_SON_1));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD11, MOM11, SON12, DAU11));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**A daughter switches her family and stays a daughter.
	 */
	@Test
	public void testSwitchFamilySamePositionDaughter() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(this.createFamily(LAST_NAME_2));
		});
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family oldFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			Family newFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_2))
				.findFirst().orElse(null);
			newFamily.getDaughters().add(oldFamily.getDaughters().stream()
				.filter(daughter -> daughter.getFirstName().equals(FIRST_DAU_1))
				.findFirst().orElse(null));
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createMember(FIRST_DAD_1));
			family.setMother(createMember(FIRST_MOM_1));
			family.getSons().add(createMember(FIRST_SON_1));
		}));
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_2);
			family.getDaughters().add(createMember(FIRST_DAU_1));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD11, MOM11, SON11, DAU12));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Test to move around members of the families repeatedly to check if correspondences are maintained correctly.
	 */
	@Test
	public void testRepeatedlyMovingFathersBetweenFamilies() {
		insertRegister();
		//Defining some additional values for this test
		String first_mom_3 = "Beate";
		Male dad13 = createMale(FIRST_DAD_1 + " " + LAST_NAME_3);
		Female mom33 = createFemale(first_mom_3 + " " + LAST_NAME_3);

		logger.trace(nameOfTestMethod + " - begin");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family family1 = createFamily(LAST_NAME_1);
			Family family2 = createFamily(LAST_NAME_2);
			Family family3 = createFamily(LAST_NAME_3);
			familyRegister.getFamilies().addAll(List.of(family1, family2, family3));
			family1.setFather(createMember(FIRST_DAD_1));
			family2.setFather(createMember(FIRST_DAD_2));

			family1.setMother(createMember(FIRST_MOM_1));
			family2.setMother(createMember(FIRST_MOM_2));
			family3.setMother(createMember(first_mom_3));
		});
		PersonRegister expectedPersonRegisterAfterPreparation = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegisterAfterPreparation.getPersons().addAll(List.of(DAD11, DAD22, MOM11, MOM22, mom33));
		assertCorrectPersonRegister(expectedPersonRegisterAfterPreparation);
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family family1 = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			Family family2 = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_2))
				.findFirst().orElse(null);
			Family family3 = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_3))
				.findFirst().orElse(null);

			family3.setFather(family2.getFather());
			family2.setFather(family1.getFather());
			family1.setFather(family3.getFather());
			family3.setFather(family2.getFather());
			family2.setFather(family1.getFather());
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setMother(createMember(FIRST_MOM_1));
		}));
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_2);
			family.setMother(createMember(FIRST_MOM_2));
			family.setFather(createMember(FIRST_DAD_2));
		}));
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_3);
			family.setMother(createMember(first_mom_3));
			family.setFather(createMember(FIRST_DAD_1));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(dad13, DAD22, MOM11, MOM22, mom33));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	// ========== MOVING MEMBERS - DIFFERENT POSITION ==========
	/**A son switches his family and becomes a father.
	 */
	@Test
	public void testSwitchFamilyDifferentPositionSonToFather() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(this.createFamily(LAST_NAME_2));
		});
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family oldFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			Family newFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_2))
				.findFirst().orElse(null);
			newFamily.setFather(oldFamily.getSons().stream()
				.filter(son -> son.getFirstName().equals(FIRST_SON_1))
				.findFirst().orElse(null));
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createMember(FIRST_DAD_1));
			family.setMother(createMember(FIRST_MOM_1));
			family.getDaughters().add(createMember(FIRST_DAU_1));
		}));
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_2);
			family.setFather(createMember(FIRST_SON_1));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD11, MOM11, SON12, DAU11));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**A son switches his family and becomes a father.
	 * Version in which the son was the last member in his family before. Therefore, the old family
	 * of the son will be deleted as he becomes the father in the new family.
	 */
	@Test
	public void testSwitchFamilyDifferentPositionLonlySonToFather() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.getSons().add(createMember(FIRST_SON_1));
			}));
			familyRegister.getFamilies().add(createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_2));
			}));
		});
		PersonRegister expectedPersonRegisterAfterPreparation = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegisterAfterPreparation.getPersons().addAll(List.of(SON11, MOM22));
		assertCorrectPersonRegister(expectedPersonRegisterAfterPreparation);
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family oldFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			Family newFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_2))
				.findFirst().orElse(null);
			newFamily.setFather(oldFamily.getSons().stream()
				.filter(son -> son.getFirstName().equals(FIRST_SON_1))
				.findFirst().orElse(null));
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_2);
			family.setFather(createMember(FIRST_SON_1));
			family.setMother(createMember(FIRST_MOM_2));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(SON12, MOM22));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**A daughter switches her family and becomes a mother.
	 */
	@Test
	public void testSwitchFamilyDifferentPositionDaughterToMother() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(this.createFamily(LAST_NAME_2));
		});
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family oldFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			Family newFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_2))
				.findFirst().orElse(null);
			newFamily.setMother(oldFamily.getDaughters().stream()
				.filter(daughter -> daughter.getFirstName().equals(FIRST_DAU_1))
				.findFirst().orElse(null));
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createMember(FIRST_DAD_1));
			family.setMother(createMember(FIRST_MOM_1));
			family.getSons().add(createMember(FIRST_SON_1));
		}));
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_2);
			family.setMother(createMember(FIRST_DAU_1));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD11, MOM11, SON11, DAU12));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**A daughter switches her family and becomes a mother.
	 * Version in which the daughter was the last member in her family before. Therefore, the old family
	 * of the daughter will be deleted as she becomes the mother in the new family.
	 */
	@Test
	public void testSwitchFamilyDifferentPositionLonlyDaughterToMother() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}));
			familyRegister.getFamilies().add(createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setFather(createMember(FIRST_DAD_2));
			}));
		});
		PersonRegister expectedPersonRegisterAfterPreparation = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegisterAfterPreparation.getPersons().addAll(List.of(DAU11, DAD22));
		assertCorrectPersonRegister(expectedPersonRegisterAfterPreparation);
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family oldFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			Family newFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_2))
				.findFirst().orElse(null);
			newFamily.setMother(oldFamily.getDaughters().stream()
				.filter(daughter -> daughter.getFirstName().equals(FIRST_DAU_1))
				.findFirst().orElse(null));
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_2);
			family.setFather(createMember(FIRST_DAD_2));
			family.setMother(createMember(FIRST_DAU_1));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD22, DAU12));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**A father switches his family and becomes a son.
	 */
	@Test
	public void testSwitchFamilyDifferentPositionFatherToSon() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(this.createFamily(LAST_NAME_2));
		});
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family oldFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			Family newFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_2))
				.findFirst().orElse(null);
			newFamily.getSons().add(oldFamily.getFather());
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setMother(createMember(FIRST_MOM_1));
			family.getSons().add(createMember(FIRST_SON_1));
			family.getDaughters().add(createMember(FIRST_DAU_1));
		}));
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.getSons().add(createMember(FIRST_DAD_1));
			family.setLastName(LAST_NAME_2);
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD12, MOM11, SON11, DAU11));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**A mother switches her family and becomes a daughter.
	 */
	@Test
	public void testSwitchFamilyDifferentPositionMotherToDaughter() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(this.createFamily(LAST_NAME_2));
		});
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			Family oldFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1))
				.findFirst().orElse(null);
			Family newFamily = familyRegister.getFamilies().stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_2))
				.findFirst().orElse(null);
			newFamily.getDaughters().add(oldFamily.getMother());
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createMember(FIRST_DAD_1));
			family.getSons().add(createMember(FIRST_SON_1));
			family.getDaughters().add(createMember(FIRST_DAU_1));
		}));
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.getDaughters().add(createMember(FIRST_MOM_1));
			family.setLastName(LAST_NAME_2);
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(List.of(DAD11, MOM12, SON11, DAU11));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	// ========== CONFLICTING SEX ==========
	/**Test if exception is thrown when a former mother is assigned to be a father.
	 */
	@Test
	public void testExceptionSexChanges_AssignMotherToFather() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createTwoFamiliesBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		RuntimeException thrownExceptionAssignMotherToFather = assertThrows(RuntimeException.class, () -> {
			propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
				Family family1 = familyRegister.getFamilies().stream()
					.filter(family -> family.getLastName().equals(LAST_NAME_1))
					.findFirst().orElse(null);
				Family family2 = familyRegister.getFamilies().stream()
					.filter(family -> family.getLastName().equals(LAST_NAME_2))
					.findFirst().orElse(null);
				family1.setFather(family2.getMother());
			});
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		String expectedMessage = "The position of a male family member can only be assigned to members with no or a male corresponding person.";
		assertPropagationException(thrownExceptionAssignMotherToFather, UnsupportedOperationException.class, expectedMessage);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Test if exception is thrown when a former daughter is assigned to be a son.
	 */
	@Test
	public void testExceptionSexChanges_AssignDaughterToSon() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createTwoFamiliesBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		RuntimeException thrownExceptionAssignDaughterToSon = assertThrows(RuntimeException.class, () -> {
			propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
				Family family1 = familyRegister.getFamilies().stream()
					.filter(family -> family.getLastName().equals(LAST_NAME_1))
					.findFirst().orElse(null);
				Family family2 = familyRegister.getFamilies().stream()
					.filter(family -> family.getLastName().equals(LAST_NAME_2))
					.findFirst().orElse(null);
				family1.getSons().add(family2.getDaughters().stream()
					.filter(daughter -> daughter.getFirstName().equals(FIRST_DAU_2))
					.findFirst().orElse(null));
			});
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		String expectedMessage = "The position of a male family member can only be assigned to members with no or a male corresponding person.";
		assertPropagationException(thrownExceptionAssignDaughterToSon, UnsupportedOperationException.class, expectedMessage);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Test if exception is thrown when a former father is assigned to be a mother.
	 */
	@Test
	public void testExceptionSexChanges_AssignFatherToMother() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createTwoFamiliesBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		RuntimeException thrownExceptionAssignFatherToMother = assertThrows(RuntimeException.class, () -> {
			propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
				Family family1 = familyRegister.getFamilies().stream()
					.filter(family -> family.getLastName().equals(LAST_NAME_1))
					.findFirst().orElse(null);
				Family family2 = familyRegister.getFamilies().stream()
					.filter(family -> family.getLastName().equals(LAST_NAME_2))
					.findFirst().orElse(null);
				family1.setMother(family2.getFather());
			});
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		String expectedMessage = "The position of a female family member can only be assigned to members with no or a female corresponding person.";
		assertPropagationException(thrownExceptionAssignFatherToMother, UnsupportedOperationException.class, expectedMessage);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Test if exception is thrown when a former son is assigned to be a daughter.
	 */
	@Test
	public void testExceptionSexChanges_AssignSonToDaughter() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createTwoFamiliesBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		RuntimeException thrownExceptionAssignSonToDaughter = assertThrows(RuntimeException.class, () -> {
			propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
				Family family1 = familyRegister.getFamilies().stream()
					.filter(family -> family.getLastName().equals(LAST_NAME_1))
					.findFirst().orElse(null);
				Family family2 = familyRegister.getFamilies().stream()
					.filter(family -> family.getLastName().equals(LAST_NAME_2))
					.findFirst().orElse(null);
				family1.getDaughters().add(family2.getSons().stream()
					.filter(son -> son.getFirstName().equals(FIRST_SON_2))
					.findFirst().orElse(null));
			});
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		String expectedMessage = "The position of a female family member can only be assigned to members with no or a female corresponding person.";
		assertPropagationException(thrownExceptionAssignSonToDaughter, UnsupportedOperationException.class, expectedMessage);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	// ========== NAMING ==========
	/**Unescapes escaped escape sequences for linefeed, carriage return and tabulator escape sequences.
	 * Unfortunately, <code>org.junit.jupiter.params.provider.Arguments.of(...)</code> is not able
	 * to deal with escape sequences like </code>\n</code>. Therefore, these sequences have to be escaped for
	 * the ParameterizedTest and then unescaped for the intended use.
	 */
	public String unescapeString(String string) {
		return string.replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t");
	}

	@ParameterizedTest(name = "{index} => role={0}, escapedNewName={1}, expectedExceptionMessage={2}")
	@MethodSource("nameAndExceptionProvider")
	public void testExceptionRenamingMemberWithInvalidFirstName(MemberRole role, String escapedNewName, String expectedExceptionMessage) {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		String unescapedNewName = escapedNewName != null ? unescapeString(escapedNewName) : null;
		this.createOneFamilyBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		RuntimeException thrownExceptionSetNullAsFirstName = assertThrows(RuntimeException.class, () -> {
			propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
				Family family1 = familyRegister.getFamilies().stream()
					.filter(family -> family.getLastName().equals(LAST_NAME_1))
					.findFirst().orElse(null);
				if (role != null) {
					switch (role) {
						case Father -> family1.getFather().setFirstName(unescapedNewName);
						case Mother -> family1.getMother().setFirstName(unescapedNewName);
						case Son -> {
							Member sonToRename = family1.getSons().stream()
								.filter(son -> son.getFirstName().equals(FIRST_SON_1))
								.findFirst().orElse(null);
							sonToRename.setFirstName(unescapedNewName);
						}
						case Daughter -> {
							Member daughterToRename = family1.getDaughters().stream()
								.filter(daughter -> daughter.getFirstName().equals(FIRST_DAU_1))
								.findFirst().orElse(null);
							daughterToRename.setFirstName(unescapedNewName);
						}
					}
				}
			});
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		String expectedMessage = expectedExceptionMessage;
		assertPropagationException(thrownExceptionSetNullAsFirstName, IllegalStateException.class, expectedMessage);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	@ParameterizedTest(name = "{index} => role={0}, escapedNewName={1}, expectedExceptionMessage={2}")
	@MethodSource("nameAndExceptionProvider")
	public void testExceptionCreationOfMemberWithInvalidFirstName(MemberRole role, String escapedNewName, String expectedExceptionMessage) {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		String unescapedNewName = escapedNewName != null ? unescapeString(escapedNewName) : null;
		this.createOneFamilyBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		RuntimeException thrownExceptionSetNullAsFirstName = assertThrows(RuntimeException.class, () -> {
			propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
				Family family1 = familyRegister.getFamilies().stream()
					.filter(family -> family.getLastName().equals(LAST_NAME_1))
					.findFirst().orElse(null);
				Member newMember = createMember(unescapedNewName);
				if (role != null) {
					switch (role) {
						case Father -> family1.setFather(newMember);
						case Mother -> family1.setMother(newMember);
						case Son -> family1.getSons().add(newMember);
						case Daughter -> family1.getDaughters().add(newMember);
					}
				}
			});
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		String expectedMessage = expectedExceptionMessage;
		assertPropagationException(thrownExceptionSetNullAsFirstName, IllegalStateException.class, expectedMessage);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	public static Stream<Arguments> nameAndExceptionProvider() {
		return Stream.of(
			Arguments.of(MemberRole.Father, null, FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_NULL),
			Arguments.of(MemberRole.Father, "", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Father, "\\n\\t\\r", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Father, FIRST_DAD_1 + "\\n", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Father, FIRST_DAD_1 + "\\t", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Father, FIRST_DAD_1 + "\\r", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Mother, null, FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_NULL),
			Arguments.of(MemberRole.Mother, "", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Mother, "\\t\\n\\r", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Mother, FIRST_MOM_1 + "\\n", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Mother, FIRST_MOM_1 + "\\t", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Mother, FIRST_MOM_1 + "\\r", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Son, null, FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_NULL),
			Arguments.of(MemberRole.Son, "", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Son, "\\n\\t\\r", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Son, FIRST_SON_1 + "\\n", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Son, FIRST_SON_1 + "\\t", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Son, FIRST_SON_1 + "\\r", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Daughter, null, FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_NULL),
			Arguments.of(MemberRole.Daughter, "", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Daughter, "\\t\\n\\r", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Daughter, FIRST_DAU_1 + "\\n", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Daughter, FIRST_DAU_1 + "\\t", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Daughter, FIRST_DAU_1 + "\\r", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES)
		);
	}

	/**Test the creation of a family without a lastname and the correct creation of corresponding
	 * persons without a white space as seperaotr attached to the firstname of the member.
	 */
	public void testCreatingFamilyWithEmptyLastName() {
		logger.trace(nameOfTestMethod + " - begin");
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().add(createFamily(family -> {
				family.setLastName("");
				family.setFather(createMember(FIRST_DAD_1));
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}));
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_1));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_1));
		expectedPersonRegister.getPersons().add(createMale(FIRST_SON_1));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_1));
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	// ========== DELETION ==========
	/**Deletes all {@link Family}s with matching lastname from the {@link FamilyRegister}.
	 * All {@link Member}s which were contained in these families will be deleted together
	 * with there corresponding {@link Person}s in the {@link PersonRegister} as well.
	 * If only families without members are deleted, the {@link PersonRegister}
	 * will not be affected.
	 */
	@Test
	public void testDeleteAllFamiliesWithMatchingName() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(from(FamilyRegister.class, FAMILIES_MODEL), familyRegister -> {
			familyRegister.getFamilies().removeIf(family -> family.getLastName().equals(LAST_NAME_1));
		});
		logger.trace(nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	/**Deletes the {@link FamilyRegister} with all its contents which leads to
	 * the deletion of the corresponding {@link PersonRegister} with all its contents.
	 */
	@Test
	public void testDeleteFamilyRegister() {
		insertRegister();
		logger.trace(nameOfTestMethod + " - begin");
		this.createOneFamilyBeforeTesting();
		logger.trace(nameOfTestMethod + " - preparation done");
		propagate(resourceAt(FAMILIES_MODEL), resource -> resource.getContents().clear());
		logger.trace(nameOfTestMethod + " - propagation done");
		assertEquals(0, resourceAt(FAMILIES_MODEL).getContents().size());
		assertEquals(0, resourceAt(PERSONS_MODEL).getContents().size());
		assertThat(resourceAt(FAMILIES_MODEL), not(exists()));
		assertThat(resourceAt(PERSONS_MODEL), not(exists()));
		logger.trace(nameOfTestMethod + " - finished without errors");
	}

	// Helpers replacing the Xtend builder expressions (X => [...])

	private static Family createFamily(Consumer<Family> familyInitialization) {
		Family family = FamiliesFactory.eINSTANCE.createFamily();
		familyInitialization.accept(family);
		return family;
	}

	private static Member createMember(String firstName) {
		Member member = FamiliesFactory.eINSTANCE.createMember();
		member.setFirstName(firstName);
		return member;
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

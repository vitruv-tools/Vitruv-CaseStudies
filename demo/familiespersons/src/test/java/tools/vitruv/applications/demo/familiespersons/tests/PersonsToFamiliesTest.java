package tools.vitruv.applications.demo.familiespersons.tests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.applications.demo.familiespersons.tests.PropagationExceptionAssertions.assertPropagationException;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.exists;
import static tools.vitruv.change.testutils.views.ChangePublishingTestView.createDefaultChangePublishingTestView;

import com.google.common.collect.Iterables;
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
import tools.vitruv.applications.demo.familiespersons.persons2families.PersonsToFamiliesChangePropagationSpecification;
import tools.vitruv.applications.demo.familiespersons.persons2families.PersonsToFamiliesHelper;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.testutils.TestLogging;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.change.testutils.TestUserInteraction;
import tools.vitruv.change.testutils.TestUserInteraction.MultipleChoiceInteractionDescription;
import tools.vitruv.change.testutils.views.TestView;

/**Test to validate the transfer of changes from the PersonModel to the FamilyModel.
 * @author Dirk Neumann
 */
@ExtendWith({TestLogging.class, TestProjectManager.class})
public class PersonsToFamiliesTest implements TestView {
	private static final Logger logger = LogManager.getLogger(PersonsToFamiliesTest.class);
	private String nameOfTestMethod = null;

	private TestView testView;

	/**
	 * Can be used to set a different kind of test view to be used in subclasses.
	 */
	protected void setTestView(TestView testView) {
		this.testView = testView;
	}

	protected Iterable<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return List.of(new PersonsToFamiliesChangePropagationSpecification(), new FamiliesToPersonsChangePropagationSpecification());
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

	private boolean preferParent = false;

	public void decideParentOrChild(PositionPreference preference) {
		String parentChildTitle = "Parent or Child?";
		this.preferParent = preference == PositionPreference.Parent;
		getUserInteraction()
			.onMultipleChoiceSingleSelection(interactionDescription -> interactionDescription.getTitle().equals(parentChildTitle))
			.respondWithChoiceAt(preference == PositionPreference.Parent ? 0 : 1);
	}

	public void decideNewOrExistingFamily(FamilyPreference preference) {
		//If we want to insert in a new family, we choose 0 anyway
		//If we choose an existing family, but do not specify in which,
		//we probably want to choose the first one we find which is at index 1,
		//since index 0 is to choose a new family
		decideNewOrExistingFamily(preference, preference == FamilyPreference.New ? 0 : 1);
	}


	private final String newOrExistingFamilyTitle = "New or Existing Family?";

	public void decideNewOrExistingFamily(FamilyPreference preference, int familyIndex) {
		getUserInteraction()
			.onMultipleChoiceSingleSelection(this::assertFamilyOptions)
			.respondWithChoiceAt(preference == FamilyPreference.New ? 0 : familyIndex);
	}

	public boolean assertFamilyOptions(MultipleChoiceInteractionDescription interactionDescription) {
		//First option is always a new family
		assertEquals(Iterables.get(interactionDescription.getChoices(), 0), "insert in a new family");
		Iterable<String> tail = Iterables.skip(interactionDescription.getChoices(), 1);
		//There must be a second option otherwise there would not be an interaction
		assertTrue(Iterables.size(tail) > 0);
		String familyName = Iterables.get(tail, 0).split(":")[0];
		//All other options have to offer families with the same name
		tail.forEach(familyOption -> familyOption.split(":")[0].equals(familyName));

		if (preferParent) {
			//If we want to insert a parent, each offered family has to not have this kind of parent
			//Therefore all families either must not have a father or must not have a mother
			boolean noFathers = Iterables.all(tail, familyOption -> !familyOption.matches(".*F:.*;.*"));
			boolean noMothers = Iterables.all(tail, familyOption -> !familyOption.matches(".*M:.*;.*"));
			assertTrue(noFathers || noMothers);
		}

		return interactionDescription.getTitle().equals(newOrExistingFamilyTitle);
	}


	/**Before each test a new {@link PersonRegister} has to be created as starting point.
	 * This is checked by several assertions to ensure correct preconditions for the tests.
	 */
	public void insertRegister() {
		propagate(resourceAt(PERSONS_MODEL), resource -> resource.getContents().add(PersonsFactory.eINSTANCE.createPersonRegister()));
		assertThat(resourceAt(FAMILIES_MODEL), exists());
		assertEquals(1, resourceAt(FAMILIES_MODEL).getContents().size());
		assertEquals(1, Iterators.size(resourceAt(FAMILIES_MODEL).getAllContents()));
		assertThat(resourceAt(FAMILIES_MODEL).getContents().get(0), instanceOf(FamilyRegister.class));
		assertEquals(0, Iterators.size(resourceAt(FAMILIES_MODEL).getContents().get(0).eAllContents()));
	}

	/**Check if the actual {@link FamilyRegister looks like the expected one.
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

	/**Check if the actual {@link PersonRegister looks like the expected one.
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

	/**Create two families which then build the starting point for other tests
	 * in which families in the {@link FamilyRegister} are needed.
	 */
	@Test
	public void createFamiliesForTesting() {
		insertRegister();
		decideParentOrChild(PositionPreference.Parent);
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_1));
		});
		decideParentOrChild(PositionPreference.Parent);
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_2));
		});
		decideParentOrChild(PositionPreference.Child);
		decideNewOrExistingFamily(FamilyPreference.Existing, 1);
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createMale(FIRST_SON_1 + " " + LAST_NAME_2));
		});
		decideParentOrChild(PositionPreference.Child);
		decideNewOrExistingFamily(FamilyPreference.Existing, 1);
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_1));
		});
		getUserInteraction().assertAllInteractionsOccurred();

		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_1));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createMale(FIRST_SON_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_1));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
	}

	// =====================================
	// CREATE MALE
	// =====================================
	// ========== FATHER ==========
	@Test
	public void testCreateMale_Father_EmptyRegister() {
		insertRegister();
		logger.trace(this.nameOfTestMethod + " - begin");
		// Father
		decideParentOrChild(PositionPreference.Parent);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_1));
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createMember(FIRST_DAD_1));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_1));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testCreateMale_Father_AutomaticNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Father
		decideParentOrChild(PositionPreference.Parent);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createMale(FIRST_DAD_2 + " " + LAST_NAME_1));
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_2));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_1));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createMale(FIRST_SON_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_1));
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_2 + " " + LAST_NAME_1));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testCreateMale_Father_ChoosingNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Father
		decideParentOrChild(PositionPreference.Parent);
		// New Family
		decideNewOrExistingFamily(FamilyPreference.New);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createMale(FIRST_DAD_2 + " " + LAST_NAME_2));
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setFather(createMember(FIRST_DAD_2));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(
			List.of(
				createMale(FIRST_DAD_1 + " " + LAST_NAME_1),
				createFemale(FIRST_MOM_1 + " " + LAST_NAME_2),
				createMale(FIRST_SON_1 + " " + LAST_NAME_2),
				createFemale(FIRST_DAU_1 + " " + LAST_NAME_1),
				createMale(FIRST_DAD_2 + " " + LAST_NAME_2)
			)
		);
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testCreateMale_Father_ChoosingExistingFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Father
		decideParentOrChild(PositionPreference.Parent);
		// First existing Family
		decideNewOrExistingFamily(FamilyPreference.Existing);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createMale(FIRST_DAD_2 + " " + LAST_NAME_2));
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setFather(createMember(FIRST_DAD_2));
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(
			List.of(
				createMale(FIRST_DAD_1 + " " + LAST_NAME_1),
				createFemale(FIRST_MOM_1 + " " + LAST_NAME_2),
				createMale(FIRST_SON_1 + " " + LAST_NAME_2),
				createFemale(FIRST_DAU_1 + " " + LAST_NAME_1),
				createMale(FIRST_DAD_2 + " " + LAST_NAME_2)
			)
		);
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testRename_Father_AutomaticNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Father
		decideParentOrChild(PositionPreference.Parent);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedDad = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_DAD_1 + " " + LAST_NAME_1)).findFirst().orElse(null);
			searchedDad.setFullName(FIRST_DAD_1 + " " + LAST_NAME_3);
		});
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_3);
				family.setFather(createMember(FIRST_DAD_1));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_3));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createMale(FIRST_SON_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_1));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testRename_Father_ChoosingNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Father
		decideParentOrChild(PositionPreference.Parent);
		// New Family
		decideNewOrExistingFamily(FamilyPreference.New);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedDad = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_DAD_1 + " " + LAST_NAME_1)).findFirst().orElse(null);
			searchedDad.setFullName(FIRST_DAD_1 + " " + LAST_NAME_2);
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setFather(createMember(FIRST_DAD_1));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createMale(FIRST_SON_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_1));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testRename_Father_ChoosingExistingFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Father
		decideParentOrChild(PositionPreference.Parent);
		// First existing Family
		decideNewOrExistingFamily(FamilyPreference.Existing);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedDad = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_DAD_1 + " " + LAST_NAME_1)).findFirst().orElse(null);
			searchedDad.setFullName(FIRST_DAD_1 + " " + LAST_NAME_2);
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setFather(createMember(FIRST_DAD_1));
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createMale(FIRST_SON_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_1));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	// ========== SON ==========
	@Test
	public void testCreateMale_Son_EmptyRegister() {
		insertRegister();
		logger.trace(this.nameOfTestMethod + " - begin");
		// Son
		decideParentOrChild(PositionPreference.Child);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createMale(FIRST_SON_1 + " " + LAST_NAME_1));
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.getSons().add(createMember(FIRST_SON_1));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_SON_1 + " " + LAST_NAME_1));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testCreateMale_Son_AutomaticNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Son
		decideParentOrChild(PositionPreference.Child);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createMale(FIRST_SON_2 + " " + LAST_NAME_3));
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_3);
				family.getSons().add(createMember(FIRST_SON_2));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(
			List.of(
				createMale(FIRST_DAD_1 + " " + LAST_NAME_1),
				createFemale(FIRST_MOM_1 + " " + LAST_NAME_2),
				createMale(FIRST_SON_1 + " " + LAST_NAME_2),
				createFemale(FIRST_DAU_1 + " " + LAST_NAME_1),
				createMale(FIRST_SON_2 + " " + LAST_NAME_3)
			)
		);
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testCreateMale_Son_ChoosingNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Son
		decideParentOrChild(PositionPreference.Child);
		// New Family
		decideNewOrExistingFamily(FamilyPreference.New);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createMale(FIRST_SON_2 + " " + LAST_NAME_1));
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.getSons().add(createMember(FIRST_SON_2));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(
			List.of(
				createMale(FIRST_DAD_1 + " " + LAST_NAME_1),
				createFemale(FIRST_MOM_1 + " " + LAST_NAME_2),
				createMale(FIRST_SON_1 + " " + LAST_NAME_2),
				createFemale(FIRST_DAU_1 + " " + LAST_NAME_1),
				createMale(FIRST_SON_2 + " " + LAST_NAME_1)
			)
		);
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testCreateMale_Son_ChoosingExistingFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Son
		decideParentOrChild(PositionPreference.Child);
		// First existing Family
		decideNewOrExistingFamily(FamilyPreference.Existing);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createMale(FIRST_SON_2 + " " + LAST_NAME_2));
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
				family.getSons().add(createMember(FIRST_SON_2));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(
			List.of(
				createMale(FIRST_DAD_1 + " " + LAST_NAME_1),
				createFemale(FIRST_MOM_1 + " " + LAST_NAME_2),
				createMale(FIRST_SON_1 + " " + LAST_NAME_2),
				createFemale(FIRST_DAU_1 + " " + LAST_NAME_1),
				createMale(FIRST_SON_2 + " " + LAST_NAME_2)
			)
		);
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testRename_Son_AutomaticNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Son
		decideParentOrChild(PositionPreference.Child);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedSon = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_SON_1 + " " + LAST_NAME_2)).findFirst().orElse(null);
			searchedSon.setFullName(FIRST_SON_1 + " " + LAST_NAME_3);
		});
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_3);
				family.getSons().add(createMember(FIRST_SON_1));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_1));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createMale(FIRST_SON_1 + " " + LAST_NAME_3));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_1));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testRename_Son_ChoosingNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Son
		decideParentOrChild(PositionPreference.Child);
		// New Family
		decideNewOrExistingFamily(FamilyPreference.New);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedSon = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_SON_1 + " " + LAST_NAME_2)).findFirst().orElse(null);
			searchedSon.setFullName(FIRST_SON_1 + " " + LAST_NAME_1);
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.getSons().add(createMember(FIRST_SON_1));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_1));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createMale(FIRST_SON_1 + " " + LAST_NAME_1));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_1));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testRename_Son_ChoosingExistingFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Son
		decideParentOrChild(PositionPreference.Child);
		// First existing Family
		decideNewOrExistingFamily(FamilyPreference.Existing);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedSon = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_SON_1 + " " + LAST_NAME_2)).findFirst().orElse(null);
			searchedSon.setFullName(FIRST_SON_1 + " " + LAST_NAME_1);
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.getSons().add(createMember(FIRST_SON_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_1));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createMale(FIRST_SON_1 + " " + LAST_NAME_1));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_1));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	// =====================================
	// CREATE FEMALE
	// =====================================
	// ========== MOTHER ==========
	@Test
	public void testCreateMale_Mother_EmptyRegister() {
		insertRegister();
		logger.trace(this.nameOfTestMethod + " - begin");
		// Mother
		decideParentOrChild(PositionPreference.Parent);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_1));
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setMother(createMember(FIRST_MOM_1));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_1));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testCreateFemale_Mother_AutomaticNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Mother
		decideParentOrChild(PositionPreference.Parent);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createFemale(FIRST_MOM_2 + " " + LAST_NAME_2));
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_2));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(
			List.of(
				createMale(FIRST_DAD_1 + " " + LAST_NAME_1),
				createFemale(FIRST_MOM_1 + " " + LAST_NAME_2),
				createMale(FIRST_SON_1 + " " + LAST_NAME_2),
				createFemale(FIRST_DAU_1 + " " + LAST_NAME_1),
				createFemale(FIRST_MOM_2 + " " + LAST_NAME_2)
			)
		);
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testCreateFemale_Mother_ChoosingNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Mother
		decideParentOrChild(PositionPreference.Parent);
		// New Family
		decideNewOrExistingFamily(FamilyPreference.New);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createFemale(FIRST_MOM_2 + " " + LAST_NAME_1));
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setMother(createMember(FIRST_MOM_2));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(
			List.of(
				createMale(FIRST_DAD_1 + " " + LAST_NAME_1),
				createFemale(FIRST_MOM_1 + " " + LAST_NAME_2),
				createMale(FIRST_SON_1 + " " + LAST_NAME_2),
				createFemale(FIRST_DAU_1 + " " + LAST_NAME_1),
				createFemale(FIRST_MOM_2 + " " + LAST_NAME_1)
			)
		);
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testCreateFemale_Mother_ChoosingExistingFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Mother
		decideParentOrChild(PositionPreference.Parent);
		// First existing Family
		decideNewOrExistingFamily(FamilyPreference.Existing);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createFemale(FIRST_MOM_2 + " " + LAST_NAME_1));
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.setMother(createMember(FIRST_MOM_2));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(
			List.of(
				createMale(FIRST_DAD_1 + " " + LAST_NAME_1),
				createFemale(FIRST_MOM_1 + " " + LAST_NAME_2),
				createMale(FIRST_SON_1 + " " + LAST_NAME_2),
				createFemale(FIRST_DAU_1 + " " + LAST_NAME_1),
				createFemale(FIRST_MOM_2 + " " + LAST_NAME_1)
			)
		);
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testRename_Mother_AutomaticNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Mother
		decideParentOrChild(PositionPreference.Parent);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedMom = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_MOM_1 + " " + LAST_NAME_2)).findFirst().orElse(null);
			searchedMom.setFullName(FIRST_MOM_1 + " " + LAST_NAME_3);
		});
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.getSons().add(createMember(FIRST_SON_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_3);
				family.setMother(createMember(FIRST_MOM_1));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_1));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_3));
		expectedPersonRegister.getPersons().add(createMale(FIRST_SON_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_1));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testRename_Mother_ChoosingNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Mother
		decideParentOrChild(PositionPreference.Parent);
		// New Family
		decideNewOrExistingFamily(FamilyPreference.New);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedMom = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_MOM_1 + " " + LAST_NAME_2)).findFirst().orElse(null);
			searchedMom.setFullName(FIRST_MOM_1 + " " + LAST_NAME_1);
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.getSons().add(createMember(FIRST_SON_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setMother(createMember(FIRST_MOM_1));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_1));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_1));
		expectedPersonRegister.getPersons().add(createMale(FIRST_SON_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_1));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testRename_Mother_ChoosingExistingFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Mother
		decideParentOrChild(PositionPreference.Parent);
		// First existing Family
		decideNewOrExistingFamily(FamilyPreference.Existing);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedMom = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_MOM_1 + " " + LAST_NAME_2)).findFirst().orElse(null);
			searchedMom.setFullName(FIRST_MOM_1 + " " + LAST_NAME_1);
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.setMother(createMember(FIRST_MOM_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.getSons().add(createMember(FIRST_SON_1));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_1));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_1));
		expectedPersonRegister.getPersons().add(createMale(FIRST_SON_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_1));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	// ========== DAUGHTHER ==========
	@Test
	public void testCreateMale_Daughter_EmptyRegister() {
		insertRegister();
		logger.trace(this.nameOfTestMethod + " - begin");
		// Daugther
		decideParentOrChild(PositionPreference.Child);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_1));
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().add(createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.getDaughters().add(createMember(FIRST_DAU_1));
		}));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_1));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testCreateFemale_Daughter_AutomaticNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Daugther
		decideParentOrChild(PositionPreference.Child);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createFemale(FIRST_DAU_2 + " " + LAST_NAME_3));
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_3);
				family.getDaughters().add(createMember(FIRST_DAU_2));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(
			List.of(
				createMale(FIRST_DAD_1 + " " + LAST_NAME_1),
				createFemale(FIRST_MOM_1 + " " + LAST_NAME_2),
				createMale(FIRST_SON_1 + " " + LAST_NAME_2),
				createFemale(FIRST_DAU_1 + " " + LAST_NAME_1),
				createFemale(FIRST_DAU_2 + " " + LAST_NAME_3)
			)
		);
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testCreateFemale_Daughter_ChoosingNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Daugther
		decideParentOrChild(PositionPreference.Child);
		// New Family
		decideNewOrExistingFamily(FamilyPreference.New);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createFemale(FIRST_DAU_2 + " " + LAST_NAME_1));
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.getDaughters().add(createMember(FIRST_DAU_2));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(
			List.of(
				createMale(FIRST_DAD_1 + " " + LAST_NAME_1),
				createFemale(FIRST_MOM_1 + " " + LAST_NAME_2),
				createMale(FIRST_SON_1 + " " + LAST_NAME_2),
				createFemale(FIRST_DAU_1 + " " + LAST_NAME_1),
				createFemale(FIRST_DAU_2 + " " + LAST_NAME_1)
			)
		);
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testCreateFemale_Daughter_ChoosingExistingFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Daugther
		decideParentOrChild(PositionPreference.Child);
		// First existing Family
		decideNewOrExistingFamily(FamilyPreference.Existing);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			personRegister.getPersons().add(createFemale(FIRST_DAU_2 + " " + LAST_NAME_2));
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
				family.getDaughters().add(createMember(FIRST_DAU_2));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().addAll(
			List.of(
				createMale(FIRST_DAD_1 + " " + LAST_NAME_1),
				createFemale(FIRST_MOM_1 + " " + LAST_NAME_2),
				createMale(FIRST_SON_1 + " " + LAST_NAME_2),
				createFemale(FIRST_DAU_1 + " " + LAST_NAME_1),
				createFemale(FIRST_DAU_2 + " " + LAST_NAME_2)
			)
		);
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testRename_Daughter_AutomaticNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Daugther
		decideParentOrChild(PositionPreference.Child);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedDaughter = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_DAU_1 + " " + LAST_NAME_1)).findFirst().orElse(null);
			searchedDaughter.setFullName(FIRST_DAU_1 + " " + LAST_NAME_3);
		});
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_3);
				family.getDaughters().add(createMember(FIRST_DAU_1));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_1));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createMale(FIRST_SON_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_3));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testRename_Daughter_ChoosingNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Daugther
		decideParentOrChild(PositionPreference.Child);
		// New Family
		decideNewOrExistingFamily(FamilyPreference.New);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedDaughter = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_DAU_1 + " " + LAST_NAME_1)).findFirst().orElse(null);
			searchedDaughter.setFullName(FIRST_DAU_1 + " " + LAST_NAME_2);
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.getDaughters().add(createMember(FIRST_DAU_1));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_1));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createMale(FIRST_SON_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_2));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testRename_Daughter_ChoosingExistingFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		// Daugther
		decideParentOrChild(PositionPreference.Child);
		// First existing Family
		decideNewOrExistingFamily(FamilyPreference.Existing);
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedDaughter = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_DAU_1 + " " + LAST_NAME_1)).findFirst().orElse(null);
			searchedDaughter.setFullName(FIRST_DAU_1 + " " + LAST_NAME_2);
		});
		getUserInteraction().assertAllInteractionsOccurred();
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
				family.getSons().add(createMember(FIRST_SON_1));
				family.getDaughters().add(createMember(FIRST_DAU_1));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_1));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createMale(FIRST_SON_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_2));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	// ========== EXCEPTIONS ==========
	/**Unescapes escaped escape-sequences for linefeed, carriage return and tabulator escape-sequences.
	 * Unfortunately, <code>org.junit.jupiter.params.provider.Arguments.of(...)</code> is not able
	 * to deal with escape-sequences like </code>\n</code>. Therefore, these sequences have to be escaped for
	 * the ParameterizedTest and then unescaped for the intended use.
	 */
	public String unescapeString(String string) {
		return string.replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t");
	}

	/**Test that error is thrown when trying to rename a {@link Person} with an empty name.
	 */
	@ParameterizedTest(name = " {index} => escapedNewName= {0}, expectedExceptionMessage= {1}")
	@MethodSource("nameAndExceptionProvider")
	public void testException_CreateWithInvalidFullname(String escapedNewName, String expectedExceptionMessage) {
		insertRegister();
		logger.trace(this.nameOfTestMethod + " - begin");
		String unescapedNewName = escapedNewName != null ? unescapeString(escapedNewName) : null;
		logger.trace(this.nameOfTestMethod + " - preparation done");
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
			propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
				personRegister.getPersons().add(createMale(unescapedNewName));
			});
		});
		logger.trace(this.nameOfTestMethod + " - propagation done");
		assertPropagationException(thrownException, IllegalStateException.class, expectedExceptionMessage);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	/**Test that error is thrown when trying to rename a {@link Person} with an empty name.
	 */
	@ParameterizedTest(name = " {index} => escapedNewName= {0}, expectedExceptionMessage= {1}")
	@MethodSource("nameAndExceptionProvider")
	public void testException_RenameWithInvalidFullname(String escapedNewName, String expectedExceptionMessage) {
		logger.trace(this.nameOfTestMethod + " - begin");
		String unescapedNewName = escapedNewName != null ? unescapeString(escapedNewName) : null;
		this.createFamiliesForTesting();
		logger.trace(this.nameOfTestMethod + " - preparation done");
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
			propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
				Person searchedDad = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_DAD_1 + " " + LAST_NAME_1)).findFirst().orElse(null);
				searchedDad.setFullName(unescapedNewName);
			});
		});
		logger.trace(this.nameOfTestMethod + " - propagation done");
		assertPropagationException(thrownException, IllegalStateException.class, expectedExceptionMessage);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	public static Stream<Arguments> nameAndExceptionProvider() {
		return Stream.of(
			Arguments.of(null, PersonsToFamiliesHelper.EXCEPTION_MESSAGE_FIRSTNAME_NULL),
			Arguments.of("", PersonsToFamiliesHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of("\\n\\t\\r", PersonsToFamiliesHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(FIRST_DAD_1 + "\\n", PersonsToFamiliesHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(FIRST_DAD_1 + "\\t", PersonsToFamiliesHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(FIRST_DAD_1 + "\\r", PersonsToFamiliesHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES)
		);
	}

	// ========== EDITING ==========
	/**Test the renaming of the firstname of a single person which should
	 * only effect this person and the corresponding {@link Member}.
	 */
	@Test
	public void testRenamingOfFirstname() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		logger.trace(this.nameOfTestMethod + " - preparation done");


		// Father
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedDad = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_DAD_1 + " " + LAST_NAME_1)).findFirst().orElse(null);
			searchedDad.setFullName(FIRST_DAD_2 + " " + LAST_NAME_1);
		});
		// Mother
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedMom = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_MOM_1 + " " + LAST_NAME_2)).findFirst().orElse(null);
			searchedMom.setFullName(FIRST_MOM_2 + " " + LAST_NAME_2);
		});
		// Son
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedSon = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_SON_1 + " " + LAST_NAME_2)).findFirst().orElse(null);
			searchedSon.setFullName(FIRST_SON_2 + " " + LAST_NAME_2);
		});
		// Daugther
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedDaughter = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_DAU_1 + " " + LAST_NAME_1)).findFirst().orElse(null);
			searchedDaughter.setFullName(FIRST_DAU_2 + " " + LAST_NAME_1);
		});
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createMember(FIRST_DAD_2));
				family.getDaughters().add(createMember(FIRST_DAU_2));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_2));
				family.getSons().add(createMember(FIRST_SON_2));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_2 + " " + LAST_NAME_1));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_2 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createMale(FIRST_SON_2 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_2 + " " + LAST_NAME_1));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	@Test
	public void testChangeFamilyRoleAfterRenaming() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		logger.trace(this.nameOfTestMethod + " - preparation done");


		// Father
		decideParentOrChild(PositionPreference.Child);
		decideNewOrExistingFamily(FamilyPreference.Existing);
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedDad = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_DAD_1 + " " + LAST_NAME_1)).findFirst().orElse(null);
			searchedDad.setFullName(FIRST_DAD_1 + " " + LAST_NAME_2);
		});
		// Mother
		decideParentOrChild(PositionPreference.Parent);
		decideNewOrExistingFamily(FamilyPreference.Existing);
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedMom = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_MOM_1 + " " + LAST_NAME_2)).findFirst().orElse(null);
			searchedMom.setFullName(FIRST_MOM_1 + " " + LAST_NAME_1);
		});
		// Son
		//TODO: JW for some reason moving a son / daughter from a family with more than one sons / daughters results in an invalid change sequence
//		decideParentOrChild(PositionPreference.Parent)
//		decideNewOrExistingFamily(FamilyPreference.Existing)
//		PersonRegister.from(PERSONS_MODEL).propagate [
//			val searchedSon = persons.findFirst[person|person.fullName.equals(FIRST_SON_1 + " " + LAST_NAME_2)]
//			searchedSon.fullName = FIRST_SON_1 + " " + LAST_NAME_1
//		]
		// Daugther
		decideParentOrChild(PositionPreference.Child);
		decideNewOrExistingFamily(FamilyPreference.Existing);
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedDaughter = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_DAU_1 + " " + LAST_NAME_1)).findFirst().orElse(null);
			searchedDaughter.setFullName(FIRST_DAU_1 + " " + LAST_NAME_2);
		});
		logger.trace(this.nameOfTestMethod + " - propagation done");
		getUserInteraction().assertAllInteractionsOccurred();
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setMother(createMember(FIRST_MOM_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.getDaughters().add(createMember(FIRST_DAU_1));
				family.getSons().add(createMember(FIRST_SON_1));
				family.getSons().add(createMember(FIRST_DAD_1));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale(FIRST_DAD_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_1));
		expectedPersonRegister.getPersons().add(createMale(FIRST_SON_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_2));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	/**Test different special names which do not match the scheme firstname + " " + lastname.
	 * In the cases of more than two parts in the name separated by spaces, the last part is
	 * the lastname and everything else is the firstname.
	 * In the case of no spaces the name will be used as firstname and as lastname.
	 */
	@Test
	public void testSpecialNames() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		logger.trace(this.nameOfTestMethod + " - preparation done");
		// Father
		decideParentOrChild(PositionPreference.Parent);
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedDad = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_DAD_1 + " " + LAST_NAME_1)).findFirst().orElse(null);
			searchedDad.setFullName("The Earl of Dorincourt");
		});
		// Mother
		decideParentOrChild(PositionPreference.Parent);
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedMom = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_MOM_1 + " " + LAST_NAME_2)).findFirst().orElse(null);
			searchedMom.setFullName("Cindy aus Marzahn");
		});
		// Son
		decideParentOrChild(PositionPreference.Child);
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedSon = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_SON_1 + " " + LAST_NAME_2)).findFirst().orElse(null);
			searchedSon.setFullName("Saruman");
		});
		// Daugther
		decideParentOrChild(PositionPreference.Child);
		decideNewOrExistingFamily(FamilyPreference.New);
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedDaughter = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_DAU_1 + " " + LAST_NAME_1)).findFirst().orElse(null);
			searchedDaughter.setFullName("Daenerys_Targaryen");
		});
		logger.trace(this.nameOfTestMethod + " - propagation done");
		getUserInteraction().assertAllInteractionsOccurred();
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName("");
				family.getSons().add(createMember("Saruman"));
			}),
			createFamily(family -> {
				family.setLastName("Dorincourt");
				family.setFather(createMember("The Earl of"));
			}),
			createFamily(family -> {
				family.setLastName("Marzahn");
				family.setMother(createMember("Cindy aus"));
			}),
			createFamily(family -> {
				family.setLastName("");
				family.getDaughters().add(createMember("Daenerys_Targaryen"));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createMale("The Earl of Dorincourt"));
		expectedPersonRegister.getPersons().add(createFemale("Cindy aus Marzahn"));
		expectedPersonRegister.getPersons().add(createMale("Saruman"));
		expectedPersonRegister.getPersons().add(createFemale("Daenerys_Targaryen"));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	// ========== DELETION ==========
	/**Test the deletion of a person when the corresponding {@link Family} still contains other
	 * {@link Member}s. In this case, this family and the remaining members should be untouched.
	 */
	@Test
	public void testDeletePerson_NotLastInFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		createFamiliesForTesting();
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedDad = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_DAD_1 + " " + LAST_NAME_1)).findFirst().orElse(null);
			personRegister.getPersons().remove(searchedDad);

			Person searchedSon = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_SON_1 + " " + LAST_NAME_2)).findFirst().orElse(null);
			personRegister.getPersons().remove(searchedSon);
		});
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		expectedFamilyRegister.getFamilies().addAll(List.of(
			createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.getDaughters().add(createMember(FIRST_DAU_1));
			}),
			createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createMember(FIRST_MOM_1));
			})
		));
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		expectedPersonRegister.getPersons().add(createFemale(FIRST_MOM_1 + " " + LAST_NAME_2));
		expectedPersonRegister.getPersons().add(createFemale(FIRST_DAU_1 + " " + LAST_NAME_1));
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	/**Test the deletion of a person when the corresponding {@link Family} does not contain
	 * any other {@link Member}s anymore. Without members a family should not exist. (Design-Decision!)
	 * Therefore, the empty family gets deleted as well.
	 */
	@Test
	public void testDeletePerson_LastInFamily() {
		logger.trace(this.nameOfTestMethod + " - begin");
		testDeletePerson_NotLastInFamily();
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(from(PersonRegister.class, PERSONS_MODEL), personRegister -> {
			Person searchedMom = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_MOM_1 + " " + LAST_NAME_2)).findFirst().orElse(null);
			personRegister.getPersons().remove(searchedMom);

			Person searchedDau = personRegister.getPersons().stream().filter(person -> person.getFullName().equals(FIRST_DAU_1 + " " + LAST_NAME_1)).findFirst().orElse(null);
			personRegister.getPersons().remove(searchedDau);
		});
		logger.trace(this.nameOfTestMethod + " - propagation done");
		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister();
		assertCorrectFamilyRegister(expectedFamilyRegister);
		assertCorrectPersonRegister(expectedPersonRegister);
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	/**Test the deletion of the {@link PersonRegister}. The corresponding {@link FamilyRegister}
	 * will be then deleted as well and all contained elements in both of the registers.
	 */
	@Test
	public void testDeletePersonsRegister() {
		logger.trace(this.nameOfTestMethod + " - begin");
		this.createFamiliesForTesting();
		logger.trace(this.nameOfTestMethod + " - preparation done");
		propagate(resourceAt(PERSONS_MODEL), resource -> resource.getContents().clear());
		logger.trace(this.nameOfTestMethod + " - propagation done");
		assertEquals(0, resourceAt(FAMILIES_MODEL).getContents().size());
		assertEquals(0, resourceAt(PERSONS_MODEL).getContents().size());
		assertThat(resourceAt(FAMILIES_MODEL), not(exists()));
		assertThat(resourceAt(PERSONS_MODEL), not(exists()));
		logger.trace(this.nameOfTestMethod + " - finished without errors");
	}

	// Helpers replacing the Xtend builder expressions (e.g. createMale => [fullName = ...])

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

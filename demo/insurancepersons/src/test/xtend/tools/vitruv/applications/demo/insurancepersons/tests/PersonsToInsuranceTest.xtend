package tools.vitruv.applications.demo.insurancepersons.tests

import edu.kit.ipd.sdq.metamodels.insurance.Gender
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceFactory
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister
import edu.kit.ipd.sdq.metamodels.persons.PersonsFactory
import java.io.IOException
import java.nio.file.Path
import org.eclipse.xtend.lib.annotations.Delegate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.change.propagation.ChangePropagationSpecification
import tools.vitruv.applications.demo.insurancepersons.persons2insurance.PersonsToInsuranceChangePropagationSpecification
import tools.vitruv.change.testutils.TestLogging
import tools.vitruv.change.testutils.TestProject
import tools.vitruv.change.testutils.TestProjectManager
import tools.vitruv.change.testutils.views.TestView

import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.change.testutils.matchers.ModelMatchers.*
import static tools.vitruv.change.testutils.views.ChangePublishingTestView.createDefaultChangePublishingTestView

@ExtendWith(TestLogging, TestProjectManager)
class PersonsToInsuranceTest implements TestView {
	@Delegate var TestView testView

	/**
	 * Can be used to set a different kind of test view to be used in subclasses.
	 */
	protected def setTestView(TestView testView) {
		this.testView = testView
	}

	protected def Iterable<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return #[new PersonsToInsuranceChangePropagationSpecification() as ChangePropagationSpecification]
	}

	@BeforeEach
	def void prepare(@TestProject Path testProjectPath) {
		testView = prepareTestView(testProjectPath)
	}

	private def TestView prepareTestView(Path testProjectPath) throws IOException {
		return createDefaultChangePublishingTestView(testProjectPath, changePropagationSpecifications)
	}

	@AfterEach
	def void cleanup() {
		testView.close()
	}

	static val MALE_NAME = "Max Mustermann"
	static val MALE_NAME_2 = "Bernd Mustermann"
	static val FEMALE_NAME = "Erika Mustermann"
	static val FEMALE_NAME_2 = "Berta Mustermann"
	static val FEMALE_NAME_3 = "Berta Musterfrau"
	// Model Paths
	final static Path PERSONS_MODEL = Path.of('model/persons.persons')
	final static Path INSURANCE_MODEL = Path.of('model/insurance.insurance')

	/**Before each test a new {@link PersonRegister} has to be created as starting point.
	 * This is checked by several assertions to ensure correct preconditions for the tests. 
	 */
	def void insertRegister() {
		resourceAt(PERSONS_MODEL).propagate[contents += PersonsFactory.eINSTANCE.createPersonRegister]
		assertThat(resourceAt(INSURANCE_MODEL), exists);
		assertEquals(1, resourceAt(INSURANCE_MODEL).contents.size);
		assertEquals(1, resourceAt(INSURANCE_MODEL).allContents.size);
		assertThat(resourceAt(INSURANCE_MODEL).contents.head, instanceOf(InsuranceDatabase));
		assertEquals(0, resourceAt(INSURANCE_MODEL).contents.head.eAllContents().size);
	}

	/**Check if the actual {@link PersonRegister} looks like the expected one.
	 */
	def void assertCorrectPersonRegister(PersonRegister expectedPersonRegister) {
		val personModel = resourceAt(PERSONS_MODEL)
		assertThat(personModel, exists)
		assertEquals(1, personModel.contents.size)
		val personRegister = personModel.contents.head
		assertThat(personRegister, instanceOf(PersonRegister))
		val PersonRegister castedPersonRegister = personRegister as PersonRegister
		assertThat(castedPersonRegister, equalsDeeply(expectedPersonRegister))
	}

	/**Check if the actual {@link InsuranceDatabase} looks like the expected one.
	 */
	def void assertCorrectInsuranceDatabase(InsuranceDatabase expectedInsuranceDatabase) {
		val insuranceModel = resourceAt(INSURANCE_MODEL)
		assertThat(insuranceModel, exists)
		assertEquals(1, insuranceModel.contents.size)
		val insuranceDatabase = insuranceModel.contents.head
		assertThat(insuranceDatabase, instanceOf(InsuranceDatabase))
		val InsuranceDatabase castedInsuranceDatabase = insuranceDatabase as InsuranceDatabase
		assertThat(castedInsuranceDatabase, equalsDeeply(expectedInsuranceDatabase))
	}

	@Test
	def testCreatePersonsModel() {
		insertRegister()
		assertThat(resourceAt(INSURANCE_MODEL), exists)
		assertThat(resourceAt(PERSONS_MODEL), exists)

		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => []
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => []

		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
	}

	@Test
	def void testDeletePersonsRegister() {
		insertRegister()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
		]
		resourceAt(PERSONS_MODEL).propagate[contents.clear()]
		assertEquals(0, resourceAt(INSURANCE_MODEL).contents.size())
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.size())
		assertThat(resourceAt(INSURANCE_MODEL), not(exists))
		assertThat(resourceAt(PERSONS_MODEL), not(exists))
	}

	@Test
	def void testCreatedPerson_male_emptyDatabase() {
		insertRegister()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
		]

		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				gender = Gender.MALE
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
		]
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
	}

	@Test
	def void testCreatedPerson_female_emptyDatabase() {
		insertRegister()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
		]

		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME
				gender = Gender.FEMALE
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
		]
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
	}

	@Test
	def void testCreatedPerson_multiple() {
		insertRegister()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME_2]
		]

		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME_2]
		]
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME
				gender = Gender.FEMALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME_2
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME_2
				gender = Gender.FEMALE
			]
		]
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
	}

	@Test
	def void testChangedFullName() {
		insertRegister()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
		]

		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedPerson = persons.findFirst[x|x.fullName.equals(FEMALE_NAME)]
			searchedPerson.fullName = FEMALE_NAME_3
		]

		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME_3]
		]
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME_3
				gender = Gender.FEMALE
			]
		]
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
	}

	@Test
	def void testDeletedPerson_first_notOnly() {
		insertRegister()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
		]

		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedPerson = persons.findFirst[x|x.fullName.equals(MALE_NAME)]
			persons.remove(searchedPerson)
		]

		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
		]
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME
				gender = Gender.FEMALE
			]
		]
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
	}

	@Test
	def void testDeletedPerson_middle_notOnly() {
		insertRegister()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME_2]
		]

		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedPerson = persons.findFirst[x|x.fullName.equals(FEMALE_NAME)]
			persons.remove(searchedPerson)
		]

		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME_2]
		]
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME_2
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME_2
				gender = Gender.FEMALE
			]
		]
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
	}

	@Test
	def void testDeletedPerson_last_notOnly() {
		insertRegister()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME_2]
		]

		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedPerson = persons.findFirst[x|x.fullName.equals(FEMALE_NAME_2)]
			persons.remove(searchedPerson)
		]

		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME_2]
		]
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME
				gender = Gender.FEMALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME_2
				gender = Gender.MALE
			]
		]
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
	}

	@Test
	def void testDeletedPerson_only() {
		insertRegister()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
		]

		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedPerson = persons.findFirst[x|x.fullName.equals(MALE_NAME)]
			persons.remove(searchedPerson)
		]

		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => []
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => []
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
	}
}

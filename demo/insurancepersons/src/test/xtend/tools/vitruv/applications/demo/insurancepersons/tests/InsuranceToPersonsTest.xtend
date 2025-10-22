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
import tools.vitruv.applications.demo.insurancepersons.insurance2persons.InsuranceToPersonsChangePropagationSpecification
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
class InsuranceToPersonsTest implements TestView {
	@Delegate var TestView testView

	/**
	 * Can be used to set a different kind of test view to be used in subclasses.
	 */
	protected def setTestView(TestView testView) {
		this.testView = testView
	}

	protected def Iterable<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return #[new InsuranceToPersonsChangePropagationSpecification()]
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
	static val SPECIAL_CHAR_NAME = "Berta? Müster-frau"
	// Model Paths
	final static Path PERSONS_MODEL = Path.of('model/persons.persons')
	final static Path INSURANCE_MODEL = Path.of('model/insurance.insurance')

	/**Before each test a new {@link InsuranceDatabase} has to be created as starting point.
	 * This is checked by several assertions to ensure correct preconditions for the tests. 
	 */
	def void insertRegister() {
		resourceAt(INSURANCE_MODEL).propagate[contents += InsuranceFactory.eINSTANCE.createInsuranceDatabase()]
		assertThat(resourceAt(PERSONS_MODEL), exists);
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(1, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.head, instanceOf(PersonRegister));
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.head.eAllContents().size);
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
	def void testCreateInsuranceDatabase() {
		insertRegister()
		assertThat(resourceAt(INSURANCE_MODEL), exists)
		assertThat(resourceAt(PERSONS_MODEL), exists)

		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => []
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => []

		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
	}

	@Test
	def void testDeleteInsuranceDatabase() {
		insertRegister()
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				gender = Gender.MALE
			]
		]

		resourceAt(INSURANCE_MODEL).propagate[contents.clear()]

		assertEquals(0, resourceAt(INSURANCE_MODEL).contents.size())
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.size())
		assertThat(resourceAt(INSURANCE_MODEL), not(exists))
		assertThat(resourceAt(PERSONS_MODEL), not(exists))
	}

	@Test
	def void testCreatedClient() {
		insertRegister()
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [name = MALE_NAME]
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
	def void testCreatedClient_multiple() {
		insertRegister()
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME
				gender = Gender.FEMALE
			]
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
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
		]

		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
	}

	@Test
	def void testChangedName() {
		insertRegister()
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME
				gender = Gender.FEMALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME_2
				gender = Gender.FEMALE
			]
		]

		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			val searchedClient = insuranceclient.findFirst[x|x.name.equals(FEMALE_NAME_2)]
			searchedClient.name = FEMALE_NAME_3
		]

		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME_3]
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
				name = FEMALE_NAME_3
				gender = Gender.FEMALE
			]
		]
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
	}

	@Test
	def void testChangedName_empty() {
		insertRegister()
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
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

		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			val searchedClient = insuranceclient.findFirst[x|x.name.equals(FEMALE_NAME)]
			searchedClient.name = ""
		]

		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = ""]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME_2]
		]
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = ""
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
	def void testChangedName_specialChars() {
		insertRegister()
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
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

		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			val searchedClient = insuranceclient.findFirst[x|x.name.equals(FEMALE_NAME_2)]
			searchedClient.name = SPECIAL_CHAR_NAME
		]

		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = SPECIAL_CHAR_NAME]
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
				name = SPECIAL_CHAR_NAME
				gender = Gender.FEMALE
			]
		]
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
	}

	@Test
	def void testChangedGender_toFemale() {
		insertRegister()
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
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

		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			val searchedClient = insuranceclient.findFirst[x|x.gender.equals(Gender.MALE)]
			searchedClient.gender = Gender.FEMALE
		]

		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME_2]
		]
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				gender = Gender.FEMALE
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
	def void testChangedGender_toMale() {
		insertRegister()
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
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

		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			val searchedClient = insuranceclient.findFirst[x|x.gender.equals(Gender.FEMALE)]
			searchedClient.gender = Gender.MALE
		]

		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FEMALE_NAME]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME_2]
		]
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME
				gender = Gender.MALE
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
	def void testDeletedClient_first_notOnly() {
		insertRegister()
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME
				gender = Gender.FEMALE
			]
		]

		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			val searchedClient = insuranceclient.findFirst[x|x.name.equals(MALE_NAME)]
			insuranceclient.remove(searchedClient)
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
	def void testDeletedClient_middle_notOnly() {
		insertRegister()
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME
				gender = Gender.FEMALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME_2
				gender = Gender.FEMALE
			]
		]

		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			val searchedClient = insuranceclient.findFirst[x|x.name.equals(FEMALE_NAME)]
			insuranceclient.remove(searchedClient)
		]

		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME_2]
		]
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
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
	def void testDeletedClient_last_notOnly() {
		insertRegister()
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME
				gender = Gender.FEMALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME_2
				gender = Gender.FEMALE
			]
		]

		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			val searchedClient = insuranceclient.findFirst[x|x.name.equals(FEMALE_NAME_2)]
			insuranceclient.remove(searchedClient)
		]

		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
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
		]
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
	}

	@Test
	def void testDeletedClient_only() {
		insertRegister()
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				gender = Gender.MALE
			]
		]

		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			val searchedClient = insuranceclient.findFirst[x|x.name.equals(MALE_NAME)]
			insuranceclient.remove(searchedClient)
		]

		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => []
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => []

		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
	}
}

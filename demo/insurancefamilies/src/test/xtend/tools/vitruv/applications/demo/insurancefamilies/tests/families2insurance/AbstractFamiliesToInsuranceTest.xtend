package tools.vitruv.applications.demo.insurancefamilies.tests.families2insurance;

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import edu.kit.ipd.sdq.metamodels.insurance.Gender
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceClient
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceFactory
import java.io.IOException
import java.nio.file.Path
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtend.lib.annotations.Accessors
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.change.propagation.ChangePropagationSpecification
import tools.vitruv.applications.demo.insurancefamilies.families2insurance.FamiliesToInsuranceChangePropagationSpecification
import tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceFamiliesDefaultTestModelFactory
import tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceFamiliesTestModelFactory
import tools.vitruv.dsls.testutils.TestModel
import tools.vitruv.change.testutils.TestLogging
import tools.vitruv.change.testutils.TestProject
import tools.vitruv.change.testutils.TestProjectManager
import tools.vitruv.change.testutils.views.NonTransactionalTestView

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createFamily
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createFamilyMember
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createInsuranceClient
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.FamiliesQueryUtil.claimFamilyRegister
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceQueryUtil.claimInsuranceDatabase
import static tools.vitruv.change.testutils.matchers.ModelMatchers.containsAllOf
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply
import static tools.vitruv.change.testutils.views.ChangePublishingTestView.createDefaultChangePublishingTestView

@ExtendWith(#[TestLogging, TestProjectManager])
abstract class AbstractFamiliesToInsuranceTest {
	protected var extension InsuranceFamiliesTestModelFactory _testModelFactory
	protected var Path testProjectPath

	/**
	 * Can be used to set a different kind of test model factory to be used in subclasses.
	 */
	protected def setTestModelFactory(InsuranceFamiliesTestModelFactory testModelFactory) {
		this._testModelFactory = testModelFactory
	}

	@BeforeEach
	def final void setupViewFactory(@TestProject Path testProjectPath) {
		this.testProjectPath = testProjectPath
		testModelFactory = new InsuranceFamiliesDefaultTestModelFactory(prepareTestView(testProjectPath))
	}

	private def NonTransactionalTestView prepareTestView(Path testProjectPath) throws IOException {
		return createDefaultChangePublishingTestView(testProjectPath, changePropagationSpecifications)
	}

	protected def Iterable<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return #[new FamiliesToInsuranceChangePropagationSpecification()]
	}
	
	protected enum MemberRole {
 		Father,
 		Mother,
 		Son,
 		Daughter
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

	protected def void createAndRegisterRoot(TestModel<FamilyRegister> model, FamilyRegister rootObject, URI persistenceUri) {
		model.registerRoot(rootObject, persistenceUri)
	}
	
	protected def void deleteRoot(EObject rootObject) {
		EcoreUtil.delete(rootObject)
	}
	
	// === creators ===
	
	private def void createFamilyRegister((FamilyRegister)=> void familyRegisterInitalization){
		changeFamilyModel[
			var familyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister
			familyRegisterInitalization.apply(familyRegister)
			createAndRegisterRoot(familyRegister, getProjectModelPath("families", FAMILY_MODEL_FILE_EXTENSION).uri)
		]
	}
	
	// === initializers ===
	
	protected def void createEmptyFamilyRegister() {
		createFamilyRegister[]
	}
	
	protected def void createOneCompleteFamily(){
		createOneOptionalFamily(true, true, true, true)
	}
	
	protected def void createOneOptionalFamily(boolean insertFather, boolean insertMother, boolean insertSon, boolean insertDaugther){
		if(!(insertFather || insertMother || insertSon || insertDaugther)){
			throw new IllegalArgumentException("can't create empty family")
		}
		createEmptyFamilyRegister()
		
		changeFamilyModel[
			claimFamilyRegister(it) => [
				families += createFamily[
					lastName = LAST_NAME_1
					if (insertFather)
						father = createFamilyMember [firstName = FIRST_DAD_1]
					if (insertMother)
						mother = createFamilyMember [firstName = FIRST_MOM_1]
					if (insertSon)
						sons += createFamilyMember [firstName = FIRST_SON_1]
					if (insertDaugther)
						daughters += createFamilyMember [firstName = FIRST_DAU_1]
				]
			]
		]
		
		val List<InsuranceClient> expectedInsuranceClients = new ArrayList()
		if(insertSon)
			expectedInsuranceClients += SON11
		if(insertDaugther)
			expectedInsuranceClients += DAU11
		if(insertFather)
			expectedInsuranceClients += DAD11
		if(insertMother)
			expectedInsuranceClients += MOM11
		
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += expectedInsuranceClients
		]
		
		validateInsuranceModel[
			claimInsuranceDatabase(it) => [
				assertCorrectInsuranceDatabase(expectedInsuranceDatabase, it)
			]
		]
	}
	
	protected def void createTwoCompleteFamilies(){
		createOneCompleteFamily()
		
		changeFamilyModel[
			claimFamilyRegister(it) => [
				families += createFamily [
					lastName = LAST_NAME_2
					father = createFamilyMember [firstName = FIRST_DAD_2]
					mother = createFamilyMember [firstName = FIRST_MOM_2]
					sons += createFamilyMember [firstName = FIRST_SON_2]
					daughters += createFamilyMember [firstName = FIRST_DAU_2]
				]
			]
		]
		
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += #[SON11, DAU11, DAD11, MOM11, SON22, DAU22, DAD22, MOM22]
		]
		validateInsuranceModel[
			claimInsuranceDatabase(it) => [
				assertCorrectInsuranceDatabase(expectedInsuranceDatabase, it)
			]
		]
	}
	
	// === assertions ===
	
	protected def assertCorrectInsuranceDatabase(InsuranceDatabase expectedInsuranceDatabase, InsuranceDatabase insuranceDatabase) {
		assertEquals(expectedInsuranceDatabase.insuranceclient.size(), insuranceDatabase.insuranceclient.size());
		assertThat(insuranceDatabase.insuranceclient, containsAllOf(expectedInsuranceDatabase.insuranceclient));
		assertThat(expectedInsuranceDatabase.insuranceclient, containsAllOf(insuranceDatabase.insuranceclient));
	}
	
	def void assertCorrectFamilyRegister(FamilyRegister expectedFamilyRegister, FamilyRegister familyRegister){
		assertThat(familyRegister, equalsDeeply(expectedFamilyRegister))
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
	
	/* Static reusable predefined InsuranceClients.
	 * The first number indicates from which string set (above) the forename is.
	 * the second number indicates from which string set (above) the lastname is.
	 */
	protected final static InsuranceClient DAD11 = createInsuranceClient [name = FIRST_DAD_1 + " " + LAST_NAME_1 gender = Gender.MALE]
	protected final static InsuranceClient MOM11 = createInsuranceClient [name = FIRST_MOM_1 + " " + LAST_NAME_1 gender = Gender.FEMALE]
	protected final static InsuranceClient SON11 = createInsuranceClient [name = FIRST_SON_1 + " " + LAST_NAME_1 gender = Gender.MALE]
	protected final static InsuranceClient DAU11 = createInsuranceClient [name = FIRST_DAU_1 + " " + LAST_NAME_1 gender = Gender.FEMALE]

	protected final static InsuranceClient DAD12 = createInsuranceClient [name = FIRST_DAD_1 + " " + LAST_NAME_2 gender = Gender.MALE]
	protected final static InsuranceClient MOM12 = createInsuranceClient [name = FIRST_MOM_1 + " " + LAST_NAME_2 gender = Gender.FEMALE]
	protected final static InsuranceClient SON12 = createInsuranceClient [name = FIRST_SON_1 + " " + LAST_NAME_2 gender = Gender.MALE]
	protected final static InsuranceClient DAU12 = createInsuranceClient [name = FIRST_DAU_1 + " " + LAST_NAME_2 gender = Gender.FEMALE]

	protected final static InsuranceClient DAD21 = createInsuranceClient [name = FIRST_DAD_2 + " " + LAST_NAME_1 gender = Gender.MALE]
	protected final static InsuranceClient MOM21 = createInsuranceClient [name = FIRST_MOM_2 + " " + LAST_NAME_1 gender = Gender.FEMALE]
	protected final static InsuranceClient SON21 = createInsuranceClient [name = FIRST_SON_2 + " " + LAST_NAME_1 gender = Gender.MALE]
	protected final static InsuranceClient DAU21 = createInsuranceClient [name = FIRST_DAU_2 + " " + LAST_NAME_1 gender = Gender.FEMALE]

	protected final static InsuranceClient DAD22 = createInsuranceClient [name = FIRST_DAD_2 + " " + LAST_NAME_2 gender = Gender.MALE]
	protected final static InsuranceClient MOM22 = createInsuranceClient [name = FIRST_MOM_2 + " " + LAST_NAME_2 gender = Gender.FEMALE]
	protected final static InsuranceClient SON22 = createInsuranceClient [name = FIRST_SON_2 + " " + LAST_NAME_2 gender = Gender.MALE]
	protected final static InsuranceClient DAU22 = createInsuranceClient [name = FIRST_DAU_2 + " " + LAST_NAME_2 gender = Gender.FEMALE]
	
}
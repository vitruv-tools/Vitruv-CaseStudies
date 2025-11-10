package tools.vitruv.applications.demo.insurancefamilies.tests.families2insurance

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import edu.kit.ipd.sdq.metamodels.families.Member
import edu.kit.ipd.sdq.metamodels.insurance.Gender
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceClient
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase
import java.util.stream.Stream
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import tools.vitruv.applications.demo.insurancefamilies.families2insurance.FamiliesToInsuranceHelper

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertThrows
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createFamily
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createFamilyMember
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createInsuranceClient
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createInsuranceDatabase
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.FamiliesQueryUtil.claimFamilies
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.FamiliesQueryUtil.claimFamilyRegister
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceQueryUtil.claimInsuranceDatabase

class FamiliesToInsuranceTest extends AbstractFamiliesToInsuranceTest {

	// === TEST: FAMILY-REGISTER ===
	
	@Test
	def void testDeleteFamilyRegister() {
		createOneCompleteFamily()

		changeFamilyModel[
			var familyRegister = claimFamilyRegister(it)
			deleteRoot(familyRegister)
		]

		validateFamilyModel[
			assertEquals(0, it.getRootObjects().size)
		]
		validateInsuranceModel[
			assertEquals(0, it.getRootObjects().size)
		]
	}

	@Test
	def void deleteFamilyWithMatchingName() {
		createTwoCompleteFamilies()

		changeFamilyModel[
			claimFamilies(it) => [
				it.removeIf([it.lastName.equals(LAST_NAME_2)])
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[SON11, DAU11, DAD11, MOM11]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	// === TESTS: FAMILY (Basic) ===
	@Test
	def void testInsertNewFamily() {
		createEmptyFamilyRegister()
		val family = createFamily[lastName = LAST_NAME_1]

		changeFamilyModel[
			claimFamilies(it) => [
				it += family
			]
		]

		val expectedInsuraceDatabase = createInsuranceDatabase[]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuraceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void insertFamilyWithFather() {
		createEmptyFamilyRegister()
		val family = createFamily[lastName = LAST_NAME_1]

		changeFamilyModel[
			claimFamilies(it) => [
				it += family
				family.father = createFamilyMember [firstName = FIRST_DAD_1]
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += createInsuranceClient [
				name = FIRST_DAD_1 + " " + LAST_NAME_1
				gender = Gender.MALE
			]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void insertFamilyWithMother() {
		createEmptyFamilyRegister()
		val family = createFamily[lastName = LAST_NAME_1]

		changeFamilyModel[
			claimFamilies(it) => [
				it += family
				family.mother = createFamilyMember [firstName = FIRST_MOM_1]
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += createInsuranceClient [
				name = FIRST_MOM_1 + " " + LAST_NAME_1
				gender = Gender.FEMALE
			]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void insertFamilyWithSon() {
		createEmptyFamilyRegister()
		val family = createFamily[lastName = LAST_NAME_1]

		changeFamilyModel[
			claimFamilies(it) => [
				it += family
				family.sons += createFamilyMember [firstName = FIRST_SON_1]
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += createInsuranceClient [
				name = FIRST_SON_1 + " " + LAST_NAME_1
				gender = Gender.MALE
			]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void insertFamilyWithDaughter() {
		createEmptyFamilyRegister()
		val family = createFamily[lastName = LAST_NAME_1]

		changeFamilyModel[
			claimFamilies(it) => [
				it += family
				family.daughters += createFamilyMember [firstName = FIRST_DAU_1]
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += createInsuranceClient [
				name = FIRST_DAU_1 + " " + LAST_NAME_1
				gender = Gender.FEMALE
			]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void deleteFatherFromFamily() {
		createOneCompleteFamily()

		changeFamilyModel[
			claimFamilies(it) => [
				val selectedFamily = it.findFirst [
					it.lastName.equals(LAST_NAME_1) && it.father.firstName.equals(FIRST_DAD_1)
				]
				selectedFamily.father = null
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[SON11, DAU11, MOM11]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void deleteMotherFromFamily() {
		createOneCompleteFamily()

		changeFamilyModel[
			claimFamilies(it) => [
				val selectedFamily = it.findFirst [
					it.lastName.equals(LAST_NAME_1) && it.mother.firstName.equals(FIRST_MOM_1)
				]
				selectedFamily.mother = null
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[SON11, DAU11, DAD11]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void deleteSonFromFamily() {
		createOneCompleteFamily()

		changeFamilyModel[
			claimFamilies(it) => [
				val selectedFamily = it.findFirst [
					it.lastName.equals(LAST_NAME_1) && it.sons.stream().anyMatch[s|s.firstName.equals(FIRST_SON_1)]
				]
				selectedFamily.sons.removeIf[s|s.firstName.equals(FIRST_SON_1)]
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[DAU11, DAD11, MOM11]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void deleteDautherFromFamily() {
		createOneCompleteFamily()

		changeFamilyModel[
			claimFamilies(it) => [
				val selectedFamily = it.findFirst [
					it.lastName.equals(LAST_NAME_1) && it.daughters.stream().anyMatch[s|s.firstName.equals(FIRST_DAU_1)]
				]
				selectedFamily.daughters.removeIf[s|s.firstName.equals(FIRST_DAU_1)]
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[SON11, DAD11, MOM11]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void testChangelastName() {
		createOneCompleteFamily();

		changeFamilyModel[
			claimFamilies(it) => [
				val selectedFamily = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				selectedFamily.lastName = LAST_NAME_2
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[SON12, DAU12, DAD12, MOM12]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	// === TESTS: MEMBER ===
	@Test
	def void testChangeFirstNameFather() {
		createOneCompleteFamily()

		changeFamilyModel[
			claimFamilies(it) => [
				val selectedFamily = it.findFirst [
					it.lastName.equals(LAST_NAME_1) && it.father.firstName.equals(FIRST_DAD_1)
				]
				selectedFamily.father.firstName = FIRST_DAD_2
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[SON11, DAU11, DAD21, MOM11]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void testChangeFirstNameMother() {
		createOneCompleteFamily()

		changeFamilyModel[
			claimFamilies(it) => [
				val selectedFamily = it.findFirst [
					it.lastName.equals(LAST_NAME_1) && it.mother.firstName.equals(FIRST_MOM_1)
				]
				selectedFamily.mother.firstName = FIRST_MOM_2
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[SON11, DAU11, DAD11, MOM21]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void testChangeFirstNameSon() {
		createOneCompleteFamily()

		changeFamilyModel[
			claimFamilies(it) => [
				val selectedFamily = it.findFirst [
					it.lastName.equals(LAST_NAME_1) && it.sons.exists[son|son.firstName.equals(FIRST_SON_1)]
				]
				val sonToChange = selectedFamily.sons.findFirst[it.firstName.equals(FIRST_SON_1)]
				sonToChange.firstName = FIRST_SON_2
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[SON21, DAU11, DAD11, MOM11]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void testChangeFirstNameDaugther() {
		createOneCompleteFamily()

		changeFamilyModel[
			claimFamilies(it) => [
				val selectedFamily = it.findFirst [
					it.lastName.equals(LAST_NAME_1) && it.daughters.exists[it.firstName.equals(FIRST_DAU_1)]
				]
				val daughterToChange = selectedFamily.daughters.findFirst[it.firstName.equals(FIRST_DAU_1)]
				daughterToChange.firstName = FIRST_DAU_2
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[SON11, DAU21, DAD11, MOM11]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	// === TESTS: FAMILY (Switch/Replace) ===
	// replacement of father causes deletion of old father and creates new father  
	@Test
	def void testReplaceFatherWithNewMember() {
		createOneCompleteFamily()

		changeFamilyModel[
			claimFamilies(it) => [
				val family = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				family.father = createFamilyMember [firstName = FIRST_DAD_2]
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[SON11, DAU11, MOM11, DAD21]
		]

		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	// replacement of father (1) with existing father (2) causes 
	// name change of existing father (2) 
	// deletion of existing father (1)
	@Test
	def void testReplaceFatherWithExistingFather() {
		createTwoCompleteFamilies()

		changeFamilyModel[
			claimFamilies(it) => [
				val family1 = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				val family2 = it.findFirst[it.lastName.equals(LAST_NAME_2)]
				family1.father = family2.father;
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[DAD21, SON11, DAU11, MOM11, SON22, DAU22, MOM22]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	// replacement of the father (1) with a father (2) from a family with only one member causes
	// deletion of the original father (1)
	// new name of the new father (2)
	@Test
	def void testReplaceFatherWithExistingPreviouslyLonlyFather() {
		createOneCompleteFamily()
		changeFamilyModel[
			claimFamilies(it) => [
				it += createFamily [
					lastName = LAST_NAME_2
					father = createFamilyMember [firstName = FIRST_DAD_2]
				]
			]
		]

		changeFamilyModel[
			claimFamilies(it) => [
				val family1 = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				val family2 = it.findFirst[it.lastName.equals(LAST_NAME_2)]
				family1.father = family2.father
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[DAD21, MOM11, SON11, DAU11]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	// replacement of the father (1) with a son (2) from another family causes
	// deletion of father (1)
	// name change of son/new father (2)
	@Test
	def void testReplaceFatherWithExistingSon() {
		createTwoCompleteFamilies()

		changeFamilyModel[
			claimFamilies(it) => [
				val family1 = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				val family2 = it.findFirst[it.lastName.equals(LAST_NAME_2)]
				family1.father = family2.sons.findFirst[son|son.firstName.equals(FIRST_SON_2)]
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[SON21, MOM11, SON11, DAU11, DAD22, MOM22, DAU22]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void testReplaceMotherWithNewMember() {
		createOneCompleteFamily()

		changeFamilyModel[
			claimFamilies(it) => [
				val family = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				family.mother = createFamilyMember [firstName = FIRST_MOM_2]
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[SON11, DAU11, DAD11, MOM21]
		]

		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void testReplaceMotherWithExistingMother() {
		createTwoCompleteFamilies()

		changeFamilyModel[
			claimFamilies(it) => [
				val family1 = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				val family2 = it.findFirst[it.lastName.equals(LAST_NAME_2)]
				family1.mother = family2.mother;
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[DAD11, SON11, DAU11, MOM21, SON22, DAU22, DAD22]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void testReplaceMotherWithExistingPreviouslyLonlyMother() {
		createOneCompleteFamily()
		changeFamilyModel[
			claimFamilies(it) => [
				it += createFamily [
					lastName = LAST_NAME_2
					mother = createFamilyMember [firstName = FIRST_MOM_2]
				]
			]
		]

		changeFamilyModel[
			claimFamilies(it) => [
				val family1 = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				val family2 = it.findFirst[it.lastName.equals(LAST_NAME_2)]
				family1.mother = family2.mother
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[DAD11, MOM21, SON11, DAU11]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void testReplaceMotherWithExistingDaughter() {
		createTwoCompleteFamilies()

		changeFamilyModel[
			claimFamilies(it) => [
				val family1 = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				val family2 = it.findFirst[it.lastName.equals(LAST_NAME_2)]
				family1.mother = family2.daughters.findFirst[daugther|daugther.firstName.equals(FIRST_DAU_2)]
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[SON11, DAU11, DAD11, DAU21, SON22, DAD22, MOM22]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void testSwitchFamilySamePositionFather() {
		createOneCompleteFamily()
		changeFamilyModel[
			claimFamilies(it) => [
				it += createFamily[lastName = LAST_NAME_2]
				it += createFamily[lastName = LAST_NAME_2]
			]
		]

		changeFamilyModel[
			claimFamilies(it) => [
				val oldFamily = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				val newFamily = it.findFirst[it.lastName.equals(LAST_NAME_2)]
				newFamily.father = oldFamily.father
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[DAD12, MOM11, SON11, DAU11]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void testSwitchFamilySamePositionMother() {
		createOneCompleteFamily()
		changeFamilyModel[
			claimFamilies(it) => [
				it += createFamily[lastName = LAST_NAME_2]
			]
		]

		changeFamilyModel[
			claimFamilies(it) => [
				val oldFamily = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				val newFamily = it.findFirst[it.lastName.equals(LAST_NAME_2)]
				newFamily.mother = oldFamily.mother
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[DAD11, MOM12, SON11, DAU11]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void testSwitchFamilySamePositionSon() {
		createOneCompleteFamily()
		changeFamilyModel[
			claimFamilies(it) => [
				it += createFamily[lastName = LAST_NAME_2]
			]
		]

		changeFamilyModel[
			claimFamilies(it) => [
				val oldFamily = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				val newFamily = it.findFirst[it.lastName.equals(LAST_NAME_2)]
				newFamily.sons += oldFamily.sons.findFirst[son|son.firstName.equals(FIRST_SON_1)]
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[DAD11, MOM11, SON12, DAU11]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void testSwitchFamilySamePositionDaugther() {
		createOneCompleteFamily()
		changeFamilyModel[
			claimFamilies(it) => [
				it += createFamily[lastName = LAST_NAME_2]
			]
		]
		changeFamilyModel[
			claimFamilies(it) => [
				val oldFamily = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				val newFamily = it.findFirst[it.lastName.equals(LAST_NAME_2)]
				newFamily.daughters += oldFamily.daughters.findFirst[daughter|daughter.firstName.equals(FIRST_DAU_1)]
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[DAD11, MOM11, SON11, DAU12]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void testRepetedlyMovingFatherBetweenFamilies() {
		createEmptyFamilyRegister()
		val String first_mom_3 = "Beate"
		val InsuranceClient dad13 = createInsuranceClient [name = FIRST_DAD_1 + " " + LAST_NAME_3 gender = Gender.MALE]
		val InsuranceClient mom33 = createInsuranceClient [name = first_mom_3 + " " + LAST_NAME_3 gender = Gender.FEMALE]

		changeFamilyModel[
			claimFamilies(it) => [
				val family1 = createFamily [lastName = LAST_NAME_1]
				val family2 = createFamily [lastName = LAST_NAME_2]
				val family3 = createFamily [lastName = LAST_NAME_3]
				it += #[family1, family2, family3]
				family1.father = createFamilyMember [firstName = FIRST_DAD_1]
				family2.father = createFamilyMember [firstName = FIRST_DAD_2]

				family1.mother = createFamilyMember [firstName = FIRST_MOM_1]
				family2.mother = createFamilyMember [firstName = FIRST_MOM_2]
				family3.mother = createFamilyMember [firstName = first_mom_3]
			]
		]

		changeFamilyModel[
			claimFamilies(it) => [
				val family1 = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				val family2 = it.findFirst[it.lastName.equals(LAST_NAME_2)]
				val family3 = it.findFirst[it.lastName.equals(LAST_NAME_3)]

				family3.father = family2.father
				family2.father = family1.father
				family1.father = family3.father
				family3.father = family2.father
				family2.father = family1.father
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[dad13, DAD22, MOM11, MOM22, mom33]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void testSwitchSonToFather() {
		createOneCompleteFamily()
		changeFamilyModel[
			claimFamilies(it) => [
				it += createFamily[lastName = LAST_NAME_2]
			]
		]

		changeFamilyModel[
			claimFamilies(it) => [
				val oldFamily = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				val newFamily = it.findFirst[it.lastName.equals(LAST_NAME_2)]
				newFamily.father = oldFamily.sons.findFirst[son|son.firstName.equals(FIRST_SON_1)]
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[DAD11, MOM11, SON12, DAU11]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void testSwitchFatherToSon() {
		createOneCompleteFamily()
		changeFamilyModel[
			claimFamilies(it) => [
				it += createFamily[lastName = LAST_NAME_2]
			]
		]

		changeFamilyModel[
			claimFamilies(it) => [
				val oldFamily = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				val newFamily = it.findFirst[it.lastName.equals(LAST_NAME_2)]
				newFamily.sons += oldFamily.father
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[DAD12, MOM11, SON11, DAU11]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void testSwitchDautherToMother() {
		createOneCompleteFamily()
		changeFamilyModel[
			claimFamilies(it) => [
				it += createFamily[lastName = LAST_NAME_2]
			]
		]

		changeFamilyModel[
			claimFamilies(it) => [
				val oldFamily = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				val newFamily = it.findFirst[it.lastName.equals(LAST_NAME_2)]
				newFamily.mother = oldFamily.daughters.findFirst[daughter|daughter.firstName.equals(FIRST_DAU_1)]
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[DAD11, MOM11, SON11, DAU12]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void testSwitchMotherToDaughter() {
		createOneCompleteFamily()
		changeFamilyModel[
			claimFamilies(it) => [
				it += createFamily[lastName = LAST_NAME_2]
			]
		]

		changeFamilyModel[
			claimFamilies(it) => [
				val oldFamily = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				val newFamily = it.findFirst[it.lastName.equals(LAST_NAME_2)]
				newFamily.daughters += oldFamily.mother
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += #[DAD11, MOM12, SON11, DAU11]
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}

	@Test
	def void familyGetsDeletedIfEmpty_father() {
		createOneCompleteFamily()

		changeFamilyModel[
			claimFamilies(it) => [
				val family = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				family.mother = null
				family.sons -= family.sons
				family.daughters -= family.daughters

				family.father = null
			]
		]

		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister
		validateFamilyModel[
			val familiyRegister = claimFamilyRegister(it)
			assertCorrectFamilyRegister(expectedFamilyRegister, familiyRegister)
		]
	}
	
	@Test
	def void familyGetsDeletedIfEmpty_mother() {
		createOneCompleteFamily()

		changeFamilyModel[
			claimFamilies(it) => [
				val family = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				family.father = null
				family.sons -= family.sons
				family.daughters -= family.daughters

				family.mother = null
			]
		]

		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister
		validateFamilyModel[
			val familiyRegister = claimFamilyRegister(it)
			assertCorrectFamilyRegister(expectedFamilyRegister, familiyRegister)
		]
	}
	
	@Test
	def void familyGetsDeletedIfEmpty_son() {
		createOneCompleteFamily()

		changeFamilyModel[
			claimFamilies(it) => [
				val family = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				family.mother = null
				family.daughters -= family.daughters
				family.father = null
				
				family.sons -= family.sons
			]
		]

		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister
		validateFamilyModel[
			val familiyRegister = claimFamilyRegister(it)
			assertCorrectFamilyRegister(expectedFamilyRegister, familiyRegister)
		]
	}
	
	@Test
	def void familyGetsDeletedIfEmpty_daugther() {
		createOneCompleteFamily()

		changeFamilyModel[
			claimFamilies(it) => [
				val family = it.findFirst[it.lastName.equals(LAST_NAME_1)]
				family.mother = null
				family.sons -= family.sons
				family.father = null
				
				family.daughters -= family.daughters
			]
		]

		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister
		validateFamilyModel[
			val familiyRegister = claimFamilyRegister(it)
			assertCorrectFamilyRegister(expectedFamilyRegister, familiyRegister)
		]
	}

	@Test
	def void testExceptionSexChanges_AssignMotherToFather() {
		createTwoCompleteFamilies()
		val thrownExceptionAssignMotherToFather = assertThrows(
			UnsupportedOperationException,
			[
				changeFamilyModel[
					claimFamilies(it) => [
						val family1 = it.findFirst[family|family.lastName.equals(LAST_NAME_1)]
						val family2 = it.findFirst[family|family.lastName.equals(LAST_NAME_2)]
						family1.father = family2.mother
					]
				]
			]
		)

		val String expectedMessage = "The position of a male family member can only be assigned to members with no or a male corresponding insurance client."
		assertEquals(expectedMessage, thrownExceptionAssignMotherToFather.message)
	}

	@Test
	def void testExceptionSexChanges_AssignDaughterToSon() {
		createTwoCompleteFamilies()

		val thrownExceptionAssignDaughterToSon = assertThrows(
			UnsupportedOperationException,
			[
				changeFamilyModel[
					claimFamilies(it) => [
						val family1 = it.findFirst[family|family.lastName.equals(LAST_NAME_1)]
						val family2 = it.findFirst[family|family.lastName.equals(LAST_NAME_2)]
						family1.sons += family2.daughters.findFirst[daughter|daughter.firstName.equals(FIRST_DAU_2)]
					]
				]
			]
		)

		val String expectedMessage = "The position of a male family member can only be assigned to members with no or a male corresponding insurance client."
		assertEquals(expectedMessage, thrownExceptionAssignDaughterToSon.message)
	}

	@Test
	def void testExceptionSexChanges_AssignFatherToMother() {
		createTwoCompleteFamilies()

		val thrownExceptionAssignFatherToMother = assertThrows(
			UnsupportedOperationException,
			[
				changeFamilyModel[
					claimFamilies(it) => [
						val family1 = it.findFirst[family|family.lastName.equals(LAST_NAME_1)]
						val family2 = it.findFirst[family|family.lastName.equals(LAST_NAME_2)]
						family1.mother = family2.father
					]
				]
			]
		)

		val String expectedMessage = "The position of a female family member can only be assigned to members with no or a female corresponding insurance client."
		assertEquals(expectedMessage, thrownExceptionAssignFatherToMother.message)
	}

	@Test
	def void testExceptionSexChanges_AssignSonToDaughter() {
		createTwoCompleteFamilies()

		val thrownExceptionAssignSonToDaughter = assertThrows(
			UnsupportedOperationException,
			[
				changeFamilyModel[
					claimFamilies(it) => [
						val family1 = it.findFirst[family|family.lastName.equals(LAST_NAME_1)]
						val family2 = it.findFirst[family|family.lastName.equals(LAST_NAME_2)]
						family1.daughters += family2.sons.findFirst[son|son.firstName.equals(FIRST_SON_2)]
					]
				]
			]
		)

		val String expectedMessage = "The position of a female family member can only be assigned to members with no or a female corresponding insurance client."
		assertEquals(expectedMessage, thrownExceptionAssignSonToDaughter.message)
	}

	def String unescapeString(String string) {
		return string.replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t")
	}

	@ParameterizedTest(name="{index} => role={0}, escapedNewName={1}, expectedExceptionMessage={2}")
	@MethodSource("nameAndExceptionProvider")
	def void testExceptionRenamingMemberWithInvalidFirstName(MemberRole role, String escapedNewName,
		String expectedExceptionMessage) {

		val unescapedNewName = if(escapedNewName !== null) unescapeString(escapedNewName) else null
		createOneCompleteFamily()

		val thrownExceptionSetNullAsFirstName = assertThrows(
			IllegalStateException,
			[
				changeFamilyModel[
					claimFamilies(it) => [
						val family1 = it.findFirst[family|family.lastName.equals(LAST_NAME_1)]
						switch role {
							case MemberRole.Father:
								family1.father.firstName = unescapedNewName
							case MemberRole.Mother:
								family1.mother.firstName = unescapedNewName
							case MemberRole.Son:
								family1.sons.
									findFirst[son|son.firstName.equals(FIRST_SON_1)].firstName = unescapedNewName
							case MemberRole.Daughter:
								family1.daughters.findFirst [ daughter |
									daughter.firstName.equals(FIRST_DAU_1)
								].firstName = unescapedNewName
						}
					]
				]
			]
		)

		val String expectedMessage = expectedExceptionMessage
		assertEquals(expectedMessage, thrownExceptionSetNullAsFirstName.message)
	}

	@ParameterizedTest(name="{index} => role={0}, escapedNewName={1}, expectedExceptionMessage={2}")
	@MethodSource("nameAndExceptionProvider")
	def void testExceptionCreationOfMemberWithInvalidFirstName(MemberRole role, String escapedNewName,
		String expectedExceptionMessage) {
		val unescapedNewName = if(escapedNewName !== null) unescapeString(escapedNewName) else null
		createOneCompleteFamily()

		val thrownExceptionSetNullAsFirstName = assertThrows(
			IllegalStateException,
			[
				changeFamilyModel[
					claimFamilies(it) => [
						val family1 = it.findFirst[family|family.lastName.equals(LAST_NAME_1)]
						val Member newMember = createFamilyMember [firstName = unescapedNewName]
						switch role {
							case MemberRole.Father: family1.father = newMember
							case MemberRole.Mother: family1.mother = newMember
							case MemberRole.Son: family1.sons += newMember
							case MemberRole.Daughter: family1.daughters += newMember
						}
					]
				]
			]
		)

		val String expectedMessage = expectedExceptionMessage
		assertEquals(expectedMessage, thrownExceptionSetNullAsFirstName.message)
	}

	def static Stream<Arguments> nameAndExceptionProvider() {
		Stream.of(
			Arguments.of(MemberRole.Father, null, FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_NULL),
			Arguments.of(MemberRole.Father, "", FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Father, "\\n\\t\\r",
				FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Father, FIRST_DAD_1 + "\\n",
				FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Father, FIRST_DAD_1 + "\\t",
				FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Father, FIRST_DAD_1 + "\\r",
				FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Mother, null, FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_NULL),
			Arguments.of(MemberRole.Mother, "", FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Mother, "\\t\\n\\r",
				FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Mother, FIRST_MOM_1 + "\\n",
				FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Mother, FIRST_MOM_1 + "\\t",
				FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Mother, FIRST_MOM_1 + "\\r",
				FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Son, null, FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_NULL),
			Arguments.of(MemberRole.Son, "", FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Son, "\\n\\t\\r", FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Son, FIRST_SON_1 + "\\n",
				FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Son, FIRST_SON_1 + "\\t",
				FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Son, FIRST_SON_1 + "\\r",
				FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Daughter, null, FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_NULL),
			Arguments.of(MemberRole.Daughter, "", FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Daughter, "\\t\\n\\r",
				FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Daughter, FIRST_DAU_1 + "\\n",
				FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Daughter, FIRST_DAU_1 + "\\t",
				FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Daughter, FIRST_DAU_1 + "\\r",
				FamiliesToInsuranceHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES)
		)
	}

	@Test
	def void testCreatingFamilyWithEmptylastName() {
		createEmptyFamilyRegister()
		changeFamilyModel[
			claimFamilies(it) => [
				it += createFamily [
					lastName = ""
					father = createFamilyMember [firstName = FIRST_DAD_1]
					mother = createFamilyMember [firstName = FIRST_MOM_1]
					sons += createFamilyMember [firstName = FIRST_SON_1]
					daughters += createFamilyMember [firstName = FIRST_DAU_1]
				]
			]
		]

		val InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase [
			insuranceclient += createInsuranceClient [name = FIRST_SON_1 gender = Gender.MALE]
			insuranceclient += createInsuranceClient [name = FIRST_DAU_1 gender = Gender.FEMALE]
			insuranceclient += createInsuranceClient [name = FIRST_DAD_1 gender = Gender.MALE]
			insuranceclient += createInsuranceClient [name = FIRST_MOM_1 gender = Gender.FEMALE]
			
		]
		validateInsuranceModel[
			val insuranceDatabase = claimInsuranceDatabase(it)
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase)
		]
	}
}

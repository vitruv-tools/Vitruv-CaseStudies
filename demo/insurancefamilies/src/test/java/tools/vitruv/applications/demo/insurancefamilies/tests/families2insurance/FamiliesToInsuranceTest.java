package tools.vitruv.applications.demo.insurancefamilies.tests.families2insurance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createFamily;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createFamilyMember;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createInsuranceClient;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createInsuranceDatabase;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.FamiliesQueryUtil.claimFamilies;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.FamiliesQueryUtil.claimFamilyRegister;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceQueryUtil.claimInsuranceDatabase;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.PropagationExceptionAssertions.assertPropagationException;

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory;
import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.families.Member;
import edu.kit.ipd.sdq.metamodels.insurance.Gender;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceClient;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase;
import java.util.List;
import java.util.stream.Stream;
import org.eclipse.emf.common.util.EList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tools.vitruv.applications.demo.insurancefamilies.families2insurance.FamiliesToInsuranceHelper;

public class FamiliesToInsuranceTest extends AbstractFamiliesToInsuranceTest {

	// === TEST: FAMILY-REGISTER ===

	@Test
	public void testDeleteFamilyRegister() {
		createOneCompleteFamily();

		changeFamilyModel(model -> {
			FamilyRegister familyRegister = claimFamilyRegister(model);
			deleteRoot(familyRegister);
		});

		validateFamilyModel(model -> {
			assertEquals(0, model.getRootObjects().size());
		});
		validateInsuranceModel(model -> {
			assertEquals(0, model.getRootObjects().size());
		});
	}

	@Test
	public void deleteFamilyWithMatchingName() {
		createTwoCompleteFamilies();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			families.removeIf(family -> family.getLastName().equals(LAST_NAME_2));
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(SON11, DAU11, DAD11, MOM11));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	// === TESTS: FAMILY (Basic) ===
	@Test
	public void testInsertNewFamily() {
		createEmptyFamilyRegister();
		Family family = createFamily(newFamily -> newFamily.setLastName(LAST_NAME_1));

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			families.add(family);
		});

		InsuranceDatabase expectedInsuraceDatabase = createInsuranceDatabase(database -> {
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuraceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void insertFamilyWithFather() {
		createEmptyFamilyRegister();
		Family family = createFamily(newFamily -> newFamily.setLastName(LAST_NAME_1));

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			families.add(family);
			family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_1)));
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(FIRST_DAD_1 + " " + LAST_NAME_1);
				client.setGender(Gender.MALE);
			}));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void insertFamilyWithMother() {
		createEmptyFamilyRegister();
		Family family = createFamily(newFamily -> newFamily.setLastName(LAST_NAME_1));

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			families.add(family);
			family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(FIRST_MOM_1 + " " + LAST_NAME_1);
				client.setGender(Gender.FEMALE);
			}));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void insertFamilyWithSon() {
		createEmptyFamilyRegister();
		Family family = createFamily(newFamily -> newFamily.setLastName(LAST_NAME_1));

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			families.add(family);
			family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(FIRST_SON_1 + " " + LAST_NAME_1);
				client.setGender(Gender.MALE);
			}));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void insertFamilyWithDaughter() {
		createEmptyFamilyRegister();
		Family family = createFamily(newFamily -> newFamily.setLastName(LAST_NAME_1));

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			families.add(family);
			family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(FIRST_DAU_1 + " " + LAST_NAME_1);
				client.setGender(Gender.FEMALE);
			}));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void deleteFatherFromFamily() {
		createOneCompleteFamily();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family selectedFamily = families.stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1) && family.getFather().getFirstName().equals(FIRST_DAD_1))
				.findFirst().orElse(null);
			selectedFamily.setFather(null);
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(SON11, DAU11, MOM11));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void deleteMotherFromFamily() {
		createOneCompleteFamily();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family selectedFamily = families.stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1) && family.getMother().getFirstName().equals(FIRST_MOM_1))
				.findFirst().orElse(null);
			selectedFamily.setMother(null);
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(SON11, DAU11, DAD11));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void deleteSonFromFamily() {
		createOneCompleteFamily();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family selectedFamily = families.stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1) && family.getSons().stream().anyMatch(s -> s.getFirstName().equals(FIRST_SON_1)))
				.findFirst().orElse(null);
			selectedFamily.getSons().removeIf(s -> s.getFirstName().equals(FIRST_SON_1));
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(DAU11, DAD11, MOM11));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void deleteDautherFromFamily() {
		createOneCompleteFamily();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family selectedFamily = families.stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1) && family.getDaughters().stream().anyMatch(s -> s.getFirstName().equals(FIRST_DAU_1)))
				.findFirst().orElse(null);
			selectedFamily.getDaughters().removeIf(s -> s.getFirstName().equals(FIRST_DAU_1));
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(SON11, DAD11, MOM11));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void testChangelastName() {
		createOneCompleteFamily();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family selectedFamily = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			selectedFamily.setLastName(LAST_NAME_2);
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(SON12, DAU12, DAD12, MOM12));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	// === TESTS: MEMBER ===
	@Test
	public void testChangeFirstNameFather() {
		createOneCompleteFamily();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family selectedFamily = families.stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1) && family.getFather().getFirstName().equals(FIRST_DAD_1))
				.findFirst().orElse(null);
			selectedFamily.getFather().setFirstName(FIRST_DAD_2);
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(SON11, DAU11, DAD21, MOM11));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void testChangeFirstNameMother() {
		createOneCompleteFamily();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family selectedFamily = families.stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1) && family.getMother().getFirstName().equals(FIRST_MOM_1))
				.findFirst().orElse(null);
			selectedFamily.getMother().setFirstName(FIRST_MOM_2);
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(SON11, DAU11, DAD11, MOM21));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void testChangeFirstNameSon() {
		createOneCompleteFamily();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family selectedFamily = families.stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1) && family.getSons().stream().anyMatch(son -> son.getFirstName().equals(FIRST_SON_1)))
				.findFirst().orElse(null);
			Member sonToChange = selectedFamily.getSons().stream().filter(son -> son.getFirstName().equals(FIRST_SON_1)).findFirst().orElse(null);
			sonToChange.setFirstName(FIRST_SON_2);
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(SON21, DAU11, DAD11, MOM11));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void testChangeFirstNameDaugther() {
		createOneCompleteFamily();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family selectedFamily = families.stream()
				.filter(family -> family.getLastName().equals(LAST_NAME_1) && family.getDaughters().stream().anyMatch(daughter -> daughter.getFirstName().equals(FIRST_DAU_1)))
				.findFirst().orElse(null);
			Member daughterToChange = selectedFamily.getDaughters().stream().filter(daughter -> daughter.getFirstName().equals(FIRST_DAU_1)).findFirst().orElse(null);
			daughterToChange.setFirstName(FIRST_DAU_2);
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(SON11, DAU21, DAD11, MOM11));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	// === TESTS: FAMILY (Switch/Replace) ===
	// replacement of father causes deletion of old father and creates new father
	@Test
	public void testReplaceFatherWithNewMember() {
		createOneCompleteFamily();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family family = families.stream().filter(candidate -> candidate.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_2)));
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(SON11, DAU11, MOM11, DAD21));
		});

		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	// replacement of father (1) with existing father (2) causes
	// name change of existing father (2)
	// deletion of existing father (1)
	@Test
	public void testReplaceFatherWithExistingFather() {
		createTwoCompleteFamilies();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family family1 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			Family family2 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
			family1.setFather(family2.getFather());
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(DAD21, SON11, DAU11, MOM11, SON22, DAU22, MOM22));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	// replacement of the father (1) with a father (2) from a family with only one member causes
	// deletion of the original father (1)
	// new name of the new father (2)
	@Test
	public void testReplaceFatherWithExistingPreviouslyLonlyFather() {
		createOneCompleteFamily();
		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			families.add(createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_2)));
			}));
		});

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family family1 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			Family family2 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
			family1.setFather(family2.getFather());
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(DAD21, MOM11, SON11, DAU11));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	// replacement of the father (1) with a son (2) from another family causes
	// deletion of father (1)
	// name change of son/new father (2)
	@Test
	public void testReplaceFatherWithExistingSon() {
		createTwoCompleteFamilies();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family family1 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			Family family2 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
			family1.setFather(family2.getSons().stream().filter(son -> son.getFirstName().equals(FIRST_SON_2)).findFirst().orElse(null));
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(SON21, MOM11, SON11, DAU11, DAD22, MOM22, DAU22));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void testReplaceMotherWithNewMember() {
		createOneCompleteFamily();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family family = families.stream().filter(candidate -> candidate.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_2)));
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(SON11, DAU11, DAD11, MOM21));
		});

		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void testReplaceMotherWithExistingMother() {
		createTwoCompleteFamilies();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family family1 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			Family family2 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
			family1.setMother(family2.getMother());
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(DAD11, SON11, DAU11, MOM21, SON22, DAU22, DAD22));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void testReplaceMotherWithExistingPreviouslyLonlyMother() {
		createOneCompleteFamily();
		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			families.add(createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_2)));
			}));
		});

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family family1 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			Family family2 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
			family1.setMother(family2.getMother());
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(DAD11, MOM21, SON11, DAU11));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void testReplaceMotherWithExistingDaughter() {
		createTwoCompleteFamilies();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family family1 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			Family family2 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
			family1.setMother(family2.getDaughters().stream().filter(daugther -> daugther.getFirstName().equals(FIRST_DAU_2)).findFirst().orElse(null));
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(SON11, DAU11, DAD11, DAU21, SON22, DAD22, MOM22));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void testSwitchFamilySamePositionFather() {
		createOneCompleteFamily();
		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			families.add(createFamily(family -> family.setLastName(LAST_NAME_2)));
			families.add(createFamily(family -> family.setLastName(LAST_NAME_2)));
		});

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family oldFamily = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			Family newFamily = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
			newFamily.setFather(oldFamily.getFather());
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(DAD12, MOM11, SON11, DAU11));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void testSwitchFamilySamePositionMother() {
		createOneCompleteFamily();
		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			families.add(createFamily(family -> family.setLastName(LAST_NAME_2)));
		});

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family oldFamily = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			Family newFamily = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
			newFamily.setMother(oldFamily.getMother());
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(DAD11, MOM12, SON11, DAU11));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void testSwitchFamilySamePositionSon() {
		createOneCompleteFamily();
		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			families.add(createFamily(family -> family.setLastName(LAST_NAME_2)));
		});

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family oldFamily = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			Family newFamily = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
			newFamily.getSons().add(oldFamily.getSons().stream().filter(son -> son.getFirstName().equals(FIRST_SON_1)).findFirst().orElse(null));
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(DAD11, MOM11, SON12, DAU11));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void testSwitchFamilySamePositionDaugther() {
		createOneCompleteFamily();
		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			families.add(createFamily(family -> family.setLastName(LAST_NAME_2)));
		});
		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family oldFamily = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			Family newFamily = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
			newFamily.getDaughters().add(oldFamily.getDaughters().stream().filter(daughter -> daughter.getFirstName().equals(FIRST_DAU_1)).findFirst().orElse(null));
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(DAD11, MOM11, SON11, DAU12));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void testRepetedlyMovingFatherBetweenFamilies() {
		createEmptyFamilyRegister();
		String first_mom_3 = "Beate";
		InsuranceClient dad13 = createInsuranceClient(client -> {
			client.setName(FIRST_DAD_1 + " " + LAST_NAME_3);
			client.setGender(Gender.MALE);
		});
		InsuranceClient mom33 = createInsuranceClient(client -> {
			client.setName(first_mom_3 + " " + LAST_NAME_3);
			client.setGender(Gender.FEMALE);
		});

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family family1 = createFamily(family -> family.setLastName(LAST_NAME_1));
			Family family2 = createFamily(family -> family.setLastName(LAST_NAME_2));
			Family family3 = createFamily(family -> family.setLastName(LAST_NAME_3));
			families.addAll(List.of(family1, family2, family3));
			family1.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_1)));
			family2.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_2)));

			family1.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
			family2.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_2)));
			family3.setMother(createFamilyMember(member -> member.setFirstName(first_mom_3)));
		});

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family family1 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			Family family2 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
			Family family3 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_3)).findFirst().orElse(null);

			family3.setFather(family2.getFather());
			family2.setFather(family1.getFather());
			family1.setFather(family3.getFather());
			family3.setFather(family2.getFather());
			family2.setFather(family1.getFather());
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(dad13, DAD22, MOM11, MOM22, mom33));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void testSwitchSonToFather() {
		createOneCompleteFamily();
		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			families.add(createFamily(family -> family.setLastName(LAST_NAME_2)));
		});

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family oldFamily = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			Family newFamily = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
			newFamily.setFather(oldFamily.getSons().stream().filter(son -> son.getFirstName().equals(FIRST_SON_1)).findFirst().orElse(null));
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(DAD11, MOM11, SON12, DAU11));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void testSwitchFatherToSon() {
		createOneCompleteFamily();
		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			families.add(createFamily(family -> family.setLastName(LAST_NAME_2)));
		});

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family oldFamily = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			Family newFamily = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
			newFamily.getSons().add(oldFamily.getFather());
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(DAD12, MOM11, SON11, DAU11));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void testSwitchDautherToMother() {
		createOneCompleteFamily();
		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			families.add(createFamily(family -> family.setLastName(LAST_NAME_2)));
		});

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family oldFamily = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			Family newFamily = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
			newFamily.setMother(oldFamily.getDaughters().stream().filter(daughter -> daughter.getFirstName().equals(FIRST_DAU_1)).findFirst().orElse(null));
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(DAD11, MOM11, SON11, DAU12));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void testSwitchMotherToDaughter() {
		createOneCompleteFamily();
		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			families.add(createFamily(family -> family.setLastName(LAST_NAME_2)));
		});

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family oldFamily = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			Family newFamily = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
			newFamily.getDaughters().add(oldFamily.getMother());
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().addAll(List.of(DAD11, MOM12, SON11, DAU11));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}

	@Test
	public void familyGetsDeletedIfEmpty_father() {
		createOneCompleteFamily();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family family = families.stream().filter(candidate -> candidate.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			family.setMother(null);
			family.getSons().removeAll(family.getSons());
			family.getDaughters().removeAll(family.getDaughters());

			family.setFather(null);
		});

		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		validateFamilyModel(model -> {
			FamilyRegister familiyRegister = claimFamilyRegister(model);
			assertCorrectFamilyRegister(expectedFamilyRegister, familiyRegister);
		});
	}

	@Test
	public void familyGetsDeletedIfEmpty_mother() {
		createOneCompleteFamily();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family family = families.stream().filter(candidate -> candidate.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			family.setFather(null);
			family.getSons().removeAll(family.getSons());
			family.getDaughters().removeAll(family.getDaughters());

			family.setMother(null);
		});

		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		validateFamilyModel(model -> {
			FamilyRegister familiyRegister = claimFamilyRegister(model);
			assertCorrectFamilyRegister(expectedFamilyRegister, familiyRegister);
		});
	}

	@Test
	public void familyGetsDeletedIfEmpty_son() {
		createOneCompleteFamily();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family family = families.stream().filter(candidate -> candidate.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			family.setMother(null);
			family.getDaughters().removeAll(family.getDaughters());
			family.setFather(null);

			family.getSons().removeAll(family.getSons());
		});

		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		validateFamilyModel(model -> {
			FamilyRegister familiyRegister = claimFamilyRegister(model);
			assertCorrectFamilyRegister(expectedFamilyRegister, familiyRegister);
		});
	}

	@Test
	public void familyGetsDeletedIfEmpty_daugther() {
		createOneCompleteFamily();

		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			Family family = families.stream().filter(candidate -> candidate.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
			family.setMother(null);
			family.getSons().removeAll(family.getSons());
			family.setFather(null);

			family.getDaughters().removeAll(family.getDaughters());
		});

		FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		validateFamilyModel(model -> {
			FamilyRegister familiyRegister = claimFamilyRegister(model);
			assertCorrectFamilyRegister(expectedFamilyRegister, familiyRegister);
		});
	}

	@Test
	public void testExceptionSexChanges_AssignMotherToFather() {
		createTwoCompleteFamilies();
		RuntimeException thrownExceptionAssignMotherToFather = assertThrows(
			RuntimeException.class,
			() -> changeFamilyModel(model -> {
				EList<Family> families = claimFamilies(model);
				Family family1 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
				Family family2 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
				family1.setFather(family2.getMother());
			})
		);

		String expectedMessage = "The position of a male family member can only be assigned to members with no or a male corresponding insurance client.";
		assertPropagationException(thrownExceptionAssignMotherToFather, UnsupportedOperationException.class, expectedMessage);
	}

	@Test
	public void testExceptionSexChanges_AssignDaughterToSon() {
		createTwoCompleteFamilies();

		RuntimeException thrownExceptionAssignDaughterToSon = assertThrows(
			RuntimeException.class,
			() -> changeFamilyModel(model -> {
				EList<Family> families = claimFamilies(model);
				Family family1 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
				Family family2 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
				family1.getSons().add(family2.getDaughters().stream().filter(daughter -> daughter.getFirstName().equals(FIRST_DAU_2)).findFirst().orElse(null));
			})
		);

		String expectedMessage = "The position of a male family member can only be assigned to members with no or a male corresponding insurance client.";
		assertPropagationException(thrownExceptionAssignDaughterToSon, UnsupportedOperationException.class, expectedMessage);
	}

	@Test
	public void testExceptionSexChanges_AssignFatherToMother() {
		createTwoCompleteFamilies();

		RuntimeException thrownExceptionAssignFatherToMother = assertThrows(
			RuntimeException.class,
			() -> changeFamilyModel(model -> {
				EList<Family> families = claimFamilies(model);
				Family family1 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
				Family family2 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
				family1.setMother(family2.getFather());
			})
		);

		String expectedMessage = "The position of a female family member can only be assigned to members with no or a female corresponding insurance client.";
		assertPropagationException(thrownExceptionAssignFatherToMother, UnsupportedOperationException.class, expectedMessage);
	}

	@Test
	public void testExceptionSexChanges_AssignSonToDaughter() {
		createTwoCompleteFamilies();

		RuntimeException thrownExceptionAssignSonToDaughter = assertThrows(
			RuntimeException.class,
			() -> changeFamilyModel(model -> {
				EList<Family> families = claimFamilies(model);
				Family family1 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
				Family family2 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_2)).findFirst().orElse(null);
				family1.getDaughters().add(family2.getSons().stream().filter(son -> son.getFirstName().equals(FIRST_SON_2)).findFirst().orElse(null));
			})
		);

		String expectedMessage = "The position of a female family member can only be assigned to members with no or a female corresponding insurance client.";
		assertPropagationException(thrownExceptionAssignSonToDaughter, UnsupportedOperationException.class, expectedMessage);
	}

	public String unescapeString(String string) {
		return string.replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t");
	}

	@ParameterizedTest(name = "{index} => role={0}, escapedNewName={1}, expectedExceptionMessage={2}")
	@MethodSource("nameAndExceptionProvider")
	public void testExceptionRenamingMemberWithInvalidFirstName(MemberRole role, String escapedNewName,
		String expectedExceptionMessage) {

		String unescapedNewName = escapedNewName != null ? unescapeString(escapedNewName) : null;
		createOneCompleteFamily();

		RuntimeException thrownExceptionSetNullAsFirstName = assertThrows(
			RuntimeException.class,
			() -> changeFamilyModel(model -> {
				EList<Family> families = claimFamilies(model);
				Family family1 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
				if (role != null) {
					switch (role) {
						case Father ->
							family1.getFather().setFirstName(unescapedNewName);
						case Mother ->
							family1.getMother().setFirstName(unescapedNewName);
						case Son ->
							family1.getSons().stream()
								.filter(son -> son.getFirstName().equals(FIRST_SON_1))
								.findFirst().orElse(null).setFirstName(unescapedNewName);
						case Daughter ->
							family1.getDaughters().stream()
								.filter(daughter -> daughter.getFirstName().equals(FIRST_DAU_1))
								.findFirst().orElse(null).setFirstName(unescapedNewName);
					}
				}
			})
		);

		String expectedMessage = expectedExceptionMessage;
		assertPropagationException(thrownExceptionSetNullAsFirstName, IllegalStateException.class, expectedMessage);
	}

	@ParameterizedTest(name = "{index} => role={0}, escapedNewName={1}, expectedExceptionMessage={2}")
	@MethodSource("nameAndExceptionProvider")
	public void testExceptionCreationOfMemberWithInvalidFirstName(MemberRole role, String escapedNewName,
		String expectedExceptionMessage) {
		String unescapedNewName = escapedNewName != null ? unescapeString(escapedNewName) : null;
		createOneCompleteFamily();

		RuntimeException thrownExceptionSetNullAsFirstName = assertThrows(
			RuntimeException.class,
			() -> changeFamilyModel(model -> {
				EList<Family> families = claimFamilies(model);
				Family family1 = families.stream().filter(family -> family.getLastName().equals(LAST_NAME_1)).findFirst().orElse(null);
				Member newMember = createFamilyMember(member -> member.setFirstName(unescapedNewName));
				if (role != null) {
					switch (role) {
						case Father -> family1.setFather(newMember);
						case Mother -> family1.setMother(newMember);
						case Son -> family1.getSons().add(newMember);
						case Daughter -> family1.getDaughters().add(newMember);
					}
				}
			})
		);

		String expectedMessage = expectedExceptionMessage;
		assertPropagationException(thrownExceptionSetNullAsFirstName, IllegalStateException.class, expectedMessage);
	}

	public static Stream<Arguments> nameAndExceptionProvider() {
		return Stream.of(
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
		);
	}

	@Test
	public void testCreatingFamilyWithEmptylastName() {
		createEmptyFamilyRegister();
		changeFamilyModel(model -> {
			EList<Family> families = claimFamilies(model);
			families.add(createFamily(family -> {
				family.setLastName("");
				family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_1)));
				family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
				family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
				family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
			}));
		});

		InsuranceDatabase expectedInsuranceDatabase = createInsuranceDatabase(database -> {
			database.getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(FIRST_SON_1);
				client.setGender(Gender.MALE);
			}));
			database.getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(FIRST_DAU_1);
				client.setGender(Gender.FEMALE);
			}));
			database.getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(FIRST_DAD_1);
				client.setGender(Gender.MALE);
			}));
			database.getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(FIRST_MOM_1);
				client.setGender(Gender.FEMALE);
			}));
		});
		validateInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			assertCorrectInsuranceDatabase(expectedInsuranceDatabase, insuranceDatabase);
		});
	}
}

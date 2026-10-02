package tools.vitruv.applications.demo.insurancefamilies.tests.insurance2families;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createFamily;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createFamilyMember;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.CreatorsUtil.createInsuranceClient;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.FamiliesQueryUtil.claimFamily;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.FamiliesQueryUtil.claimFamilyRegister;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceQueryUtil.claimInsuranceClient;
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceQueryUtil.claimInsuranceDatabase;

import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.insurance.Gender;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceClient;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import tools.vitruv.applications.demo.insurancefamilies.insurance2families.PositionPreference;

public class InsuranceToFamiliesTest extends AbstractInsuranceToFamiliesTest {

	// === TESTS: InsuranceDatabase ===

	@Test
	public void testCreateInsuranceDatabase() {
		changeInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase();
			createAndRegisterRoot(model, insuranceDatabase, getUri(getProjectModelPath("insurance", getINSURANCE_MODEL_FILE_EXTENSION())));
		});

		validateFamilyModel(model -> {
			assertEquals(1, model.getRootObjects().size());
			assertNumberOfFamilies(model, 0);
		});
	}

	@Test
	public void testDeleteInsuranceDatabase() {
		createEmptyInsuranceDatabase();

		changeInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			deleteRoot(insuranceDatabase);
		});

		validateFamilyModel(model -> {
			assertEquals(0, model.getRootObjects().size());
		});
	}

	// === TESTS: Client ===

	@Test
	public void testCreatedClient_father() {
		createEmptyInsuranceDatabase();

		decideParentOrChild(PositionPreference.Parent);
		changeInsuranceModel(model -> {
			claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(fullName(FIRST_DAD_1, LAST_NAME_1));
				client.setGender(Gender.MALE);
			}));
		});

		Family expectedFamily = createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_1)));
		});
		validateFamilyModel(model -> {
			Family family = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_1);
			assertFamily(expectedFamily, family);
		});
	}

	@Test
	public void testCreatedClient_mother() {
		createEmptyInsuranceDatabase();

		decideParentOrChild(PositionPreference.Parent);
		changeInsuranceModel(model -> {
			claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(fullName(FIRST_MOM_1, LAST_NAME_1));
				client.setGender(Gender.FEMALE);
			}));
		});

		Family expectedFamily = createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
		});
		validateFamilyModel(model -> {
			Family family = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_1);
			assertFamily(expectedFamily, family);
		});
	}

	@Test
	public void testCreatedClient_son() {
		createEmptyInsuranceDatabase();

		decideParentOrChild(PositionPreference.Child);
		changeInsuranceModel(model -> {
			claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(fullName(FIRST_SON_1, LAST_NAME_1));
				client.setGender(Gender.MALE);
			}));
		});

		Family expectedFamily = createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
		});
		validateFamilyModel(model -> {
			Family family = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_1);
			assertFamily(expectedFamily, family);
		});
	}

	@Test
	public void testCreatedClient_dauther() {
		createEmptyInsuranceDatabase();

		decideParentOrChild(PositionPreference.Child);
		changeInsuranceModel(model -> {
			claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(fullName(FIRST_DAU_1, LAST_NAME_1));
				client.setGender(Gender.FEMALE);
			}));
		});

		Family expectedFamily = createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
		});
		validateFamilyModel(model -> {
			Family family = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_1);
			assertFamily(expectedFamily, family);
		});
	}

	@Test
	public void testCreatedClient_addMultipleToExistingFamily() {
		createEmptyInsuranceDatabase();

		decideParentOrChild(PositionPreference.Parent);
		changeInsuranceModel(model -> {
			claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(fullName(FIRST_DAD_1, LAST_NAME_1));
				client.setGender(Gender.MALE);
			}));
		});
		decideParentOrChild(PositionPreference.Parent);
		decideNewOrExistingFamily(FamilyPreference.Existing, 1);
		changeInsuranceModel(model -> {
			claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(fullName(FIRST_MOM_1, LAST_NAME_1));
				client.setGender(Gender.FEMALE);
			}));
		});
		decideParentOrChild(PositionPreference.Child);
		decideNewOrExistingFamily(FamilyPreference.Existing, 1);
		changeInsuranceModel(model -> {
			claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(fullName(FIRST_SON_1, LAST_NAME_1));
				client.setGender(Gender.MALE);
			}));
		});
		decideParentOrChild(PositionPreference.Child);
		decideNewOrExistingFamily(FamilyPreference.Existing, 1);
		changeInsuranceModel(model -> {
			claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(fullName(FIRST_SON_2, LAST_NAME_1));
				client.setGender(Gender.MALE);
			}));
		});
		decideParentOrChild(PositionPreference.Child);
		decideNewOrExistingFamily(FamilyPreference.Existing, 1);
		changeInsuranceModel(model -> {
			claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(fullName(FIRST_DAU_1, LAST_NAME_1));
				client.setGender(Gender.FEMALE);
			}));
		});
		decideParentOrChild(PositionPreference.Child);
		decideNewOrExistingFamily(FamilyPreference.Existing, 1);
		changeInsuranceModel(model -> {
			claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(fullName(FIRST_DAU_2, LAST_NAME_1));
				client.setGender(Gender.FEMALE);
			}));
		});

		Family expectedFamily = createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_1)));
			family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
			family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
			family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_2)));
			family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
			family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_2)));
		});
		validateFamilyModel(model -> {
			Family family = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_1);
			assertFamily(expectedFamily, family);
		});
	}

	@Test
	public void testCreatedClient_fatherNewFamily() {
		createInsuranceDatabaseWithCompleteFamily();

		decideParentOrChild(PositionPreference.Parent);
		decideNewOrExistingFamily(FamilyPreference.New, 0);
		changeInsuranceModel(model -> {
			claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(fullName(FIRST_DAD_2, LAST_NAME_1));
				client.setGender(Gender.MALE);
			}));
		});

		Family expectedFamily1 = COMPLETE_FAMILY_1;
		Family expectedFamily2 = createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_2)));
		});
		validateFamilyModel(model -> {
			List<Family> families = claimFamilyRegister(model).getFamilies();
			assertNumberOfFamilies(model, 2);

			assertFamily(expectedFamily1, families.get(0));
			assertFamily(expectedFamily2, families.get(1));
		});
	}

	@Test
	public void testCreatedClient_motherNewFamily() {
		createInsuranceDatabaseWithCompleteFamily();

		decideParentOrChild(PositionPreference.Parent);
		decideNewOrExistingFamily(FamilyPreference.New, 0);
		changeInsuranceModel(model -> {
			claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(fullName(FIRST_MOM_2, LAST_NAME_1));
				client.setGender(Gender.FEMALE);
			}));
		});

		Family expectedFamily1 = COMPLETE_FAMILY_1;
		Family expectedFamily2 = createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_2)));
		});
		validateFamilyModel(model -> {
			List<Family> families = claimFamilyRegister(model).getFamilies();
			assertNumberOfFamilies(model, 2);
			assertFamily(expectedFamily1, families.get(0));
			assertFamily(expectedFamily2, families.get(1));
		});
	}

	@Test
	public void testCreatedClient_sonNewFamily() {
		createInsuranceDatabaseWithCompleteFamily();

		decideParentOrChild(PositionPreference.Child);
		decideNewOrExistingFamily(FamilyPreference.New, 0);
		changeInsuranceModel(model -> {
			claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(fullName(FIRST_SON_2, LAST_NAME_1));
				client.setGender(Gender.MALE);
			}));
		});

		Family expectedFamily1 = COMPLETE_FAMILY_1;
		Family expectedFamily2 = createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_2)));
		});
		validateFamilyModel(model -> {
			List<Family> families = claimFamilyRegister(model).getFamilies();
			assertNumberOfFamilies(model, 2);
			assertFamily(expectedFamily1, families.get(0));
			assertFamily(expectedFamily2, families.get(1));
		});
	}

	@Test
	public void testCreatedClient_daugtherNewFamily() {
		createInsuranceDatabaseWithCompleteFamily();

		decideParentOrChild(PositionPreference.Child);
		decideNewOrExistingFamily(FamilyPreference.New, 0);
		changeInsuranceModel(model -> {
			claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(fullName(FIRST_DAU_2, LAST_NAME_1));
				client.setGender(Gender.FEMALE);
			}));
		});

		Family expectedFamily1 = COMPLETE_FAMILY_1;
		Family expectedFamily2 = createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_2)));
		});
		validateFamilyModel(model -> {
			List<Family> families = claimFamilyRegister(model).getFamilies();
			assertNumberOfFamilies(model, 2);

			assertFamily(expectedFamily1, families.get(0));
			assertFamily(expectedFamily2, families.get(1));
		});
	}

	@Test
	public void testCreatedClient_fatherExistingFamilyBlocked() {
		createInsuranceDatabaseWithCompleteFamily();

		decideParentOrChild(PositionPreference.Parent);
		decideNewOrExistingFamily(FamilyPreference.Existing, 1);
		awaitReplacementInformation(fullName(FIRST_DAD_1, LAST_NAME_1), LAST_NAME_1);
		decideParentOrChild(PositionPreference.Parent);
		decideNewOrExistingFamily(FamilyPreference.New, 0);
		changeInsuranceModel(model -> {
			claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(fullName(FIRST_DAD_2, LAST_NAME_1));
				client.setGender(Gender.MALE);
			}));
		});

		Family expectedFamily1 = createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_2)));
			family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
			family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
			family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
		});
		Family expectedFamily2 = createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_1)));
		});
		validateFamilyModel(model -> {
			List<Family> families = claimFamilyRegister(model).getFamilies();
			assertNumberOfFamilies(model, 2);
			assertFamily(expectedFamily1, families.get(0));
			assertFamily(expectedFamily2, families.get(1));
		});
	}

	@Test
	public void testCreatedClient_motherExistingFamilyBlocked() {
		createInsuranceDatabaseWithCompleteFamily();

		decideParentOrChild(PositionPreference.Parent);
		decideNewOrExistingFamily(FamilyPreference.Existing, 1);
		awaitReplacementInformation(fullName(FIRST_MOM_1, LAST_NAME_1), LAST_NAME_1);
		decideParentOrChild(PositionPreference.Parent);
		decideNewOrExistingFamily(FamilyPreference.New, 0);
		changeInsuranceModel(model -> {
			claimInsuranceDatabase(model).getInsuranceclient().add(createInsuranceClient(client -> {
				client.setName(fullName(FIRST_MOM_2, LAST_NAME_1));
				client.setGender(Gender.FEMALE);
			}));
		});

		Family expectedFamily1 = createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_1)));
			family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_2)));
			family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
			family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
		});
		Family expectedFamily2 = createFamily(family -> {
			family.setLastName(LAST_NAME_1);
			family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
		});
		validateFamilyModel(model -> {
			List<Family> families = claimFamilyRegister(model).getFamilies();
			assertNumberOfFamilies(model, 2);

			assertFamily(expectedFamily1, families.get(0));
			assertFamily(expectedFamily2, families.get(1));
		});
	}

	@Test
	public void testDeleteClient_father() {
		createInsuranceDatabaseWithCompleteFamily();

		changeInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			insuranceDatabase.getInsuranceclient().remove(insuranceDatabase.getInsuranceclient().stream()
				.filter(client -> Objects.equals(client.getName(), fullName(FIRST_DAD_1, LAST_NAME_1)))
				.findFirst().orElse(null));
		});

		validateFamilyModel(model -> {
			Family expectedFamily = createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
				family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
				family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
			});
			Family family = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_1);
			assertFamily(expectedFamily, family);
		});
	}

	@Test
	public void testDeleteClient_mother() {
		createInsuranceDatabaseWithCompleteFamily();

		changeInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			insuranceDatabase.getInsuranceclient().remove(insuranceDatabase.getInsuranceclient().stream()
				.filter(client -> Objects.equals(client.getName(), fullName(FIRST_MOM_1, LAST_NAME_1)))
				.findFirst().orElse(null));
		});

		validateFamilyModel(model -> {
			Family expectedFamily = createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_1)));
				family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
				family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
			});
			Family family = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_1);
			assertFamily(expectedFamily, family);
		});
	}

	@Test
	public void testDeleteClient_son() {
		createInsuranceDatabaseWithCompleteFamily();

		changeInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			insuranceDatabase.getInsuranceclient().remove(insuranceDatabase.getInsuranceclient().stream()
				.filter(client -> Objects.equals(client.getName(), fullName(FIRST_SON_1, LAST_NAME_1)))
				.findFirst().orElse(null));
		});

		validateFamilyModel(model -> {
			Family expectedFamily = createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_1)));
				family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
				family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
			});
			Family family = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_1);
			assertFamily(expectedFamily, family);
		});
	}

	@Test
	public void testDeleteClient_daughter() {
		createInsuranceDatabaseWithCompleteFamily();

		changeInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			insuranceDatabase.getInsuranceclient().remove(insuranceDatabase.getInsuranceclient().stream()
				.filter(client -> Objects.equals(client.getName(), fullName(FIRST_DAU_1, LAST_NAME_1)))
				.findFirst().orElse(null));
		});

		validateFamilyModel(model -> {
			Family expectedFamily = createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_1)));
				family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
				family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
			});
			Family family = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_1);
			assertFamily(expectedFamily, family);
		});
	}

	@Test
	public void testDeleteClient_deleteFamilyIfEmpty() {
		createInsuranceDatabaseWithCompleteFamily();

		changeInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			List<InsuranceClient> existingClients = new ArrayList<>(insuranceDatabase.getInsuranceclient());
			for (InsuranceClient existingClient : existingClients) {
				insuranceDatabase.getInsuranceclient().remove(existingClient);
			}
		});

		validateFamilyModel(model -> {
			assertNumberOfFamilies(model, 0);
		});
	}

	@Test
	public void testChangeGender_MotherToFather() {
		createInsuranceDataBaseWithOptionalCompleteFamily(false, true, true, true);

		changeInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			claimInsuranceClient(insuranceDatabase, FIRST_MOM_1, LAST_NAME_1).setGender(Gender.MALE);
		});

		validateFamilyModel(model -> {
			Family expectedFamily = createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
				family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
				family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
			});
			Family family = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_1);
			assertFamily(expectedFamily, family);
		});
	}

	@Test
	public void testChangeGender_FatherToMother() {
		createInsuranceDataBaseWithOptionalCompleteFamily(true, false, true, true);

		changeInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			claimInsuranceClient(insuranceDatabase, FIRST_DAD_1, LAST_NAME_1).setGender(Gender.FEMALE);
		});

		validateFamilyModel(model -> {
			Family expectedFamily = createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_DAD_1)));
				family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
				family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
			});
			Family family = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_1);
			assertFamily(expectedFamily, family);
		});
	}

	@Test
	public void testChangeGender_DaugtherToSon() {
		createInsuranceDataBaseWithOptionalCompleteFamily(true, true, true, true);

		changeInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			claimInsuranceClient(insuranceDatabase, FIRST_DAU_1, LAST_NAME_1).setGender(Gender.MALE);
		});

		validateFamilyModel(model -> {
			Family expectedFamily = createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_1)));
				family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
				family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
				family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
			});
			Family family = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_1);
			assertFamily(expectedFamily, family);
		});
	}

	@Test
	public void testChangeGender_SonToDaughter() {
		createInsuranceDataBaseWithOptionalCompleteFamily(true, true, true, true);

		changeInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			claimInsuranceClient(insuranceDatabase, FIRST_SON_1, LAST_NAME_1).setGender(Gender.FEMALE);
		});

		validateFamilyModel(model -> {
			Family expectedFamily = createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_1)));
				family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
				family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
				family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
			});
			Family family = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_1);
			assertFamily(expectedFamily, family);
		});
	}

	@Test
	public void testChangeGender_MotherToFatherBlocked() {
		createInsuranceDatabaseWithCompleteFamily();

		awaitReplacementInformation(fullName(FIRST_DAD_1, LAST_NAME_1), LAST_NAME_1);
		decideParentOrChild(PositionPreference.Parent);
		decideNewOrExistingFamily(FamilyPreference.New, 0);
		changeInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			claimInsuranceClient(insuranceDatabase, FIRST_MOM_1, LAST_NAME_1).setGender(Gender.MALE);
		});

		validateFamilyModel(model -> {
			Family expectedFamily1 = createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
				family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
				family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
			});
			Family expectedFamily2 = createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_1)));
			});

			List<Family> families = claimFamilyRegister(model).getFamilies();
			assertNumberOfFamilies(model, 2);
			Family family1 = families.get(0);
			Family family2 = families.get(1);
			assertFamily(expectedFamily1, family1);
			assertFamily(expectedFamily2, family2);
		});
	}

	@Test
	public void testChangeGender_FatherToMotherBlocked() {
		createInsuranceDatabaseWithCompleteFamily();

		awaitReplacementInformation(fullName(FIRST_MOM_1, LAST_NAME_1), LAST_NAME_1);
		decideParentOrChild(PositionPreference.Parent);
		decideNewOrExistingFamily(FamilyPreference.New, 0);
		changeInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			claimInsuranceClient(insuranceDatabase, FIRST_DAD_1, LAST_NAME_1).setGender(Gender.FEMALE);
		});

		validateFamilyModel(model -> {
			Family expectedFamily1 = createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_DAD_1)));
				family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
				family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
			});
			Family expectedFamily2 = createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
			});

			List<Family> families = claimFamilyRegister(model).getFamilies();
			assertNumberOfFamilies(model, 2);
			Family family1 = families.get(0);
			Family family2 = families.get(1);
			assertFamily(expectedFamily1, family1);
			assertFamily(expectedFamily2, family2);
		});
	}

	@Test
	public void testChangeName_firstName() {
		createInsuranceDatabaseWithCompleteFamily();

		changeInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			claimInsuranceClient(insuranceDatabase, FIRST_DAD_1, LAST_NAME_1).setName(fullName(FIRST_DAD_2, LAST_NAME_1));
		});

		validateFamilyModel(model -> {
			Family expectedFamily = createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_2)));
				family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
				family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
				family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
			});
			Family family = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_1);
			assertFamily(expectedFamily, family);
		});
	}

	@Test
	public void testChangeName_onlyMemberInFamily() {
		createInsuranceDataBaseWithOptionalCompleteFamily(false, false, true, false);

		changeInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			claimInsuranceClient(insuranceDatabase, FIRST_SON_1, LAST_NAME_1).setName(fullName(FIRST_SON_1, LAST_NAME_2));
		});

		validateFamilyModel(model -> {
			Family expectedFamily = createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
			});
			assertNumberOfFamilies(model, 1);
			Family family = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_2);
			assertFamily(expectedFamily, family);
		});
	}

	@Test
	public void testChangeName_newFamilyAsParent() {
		createInsuranceDatabaseWithCompleteFamily();

		decideParentOrChild(PositionPreference.Parent);
		changeInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			claimInsuranceClient(insuranceDatabase, FIRST_SON_1, LAST_NAME_1).setName(fullName(FIRST_SON_1, LAST_NAME_2));
		});

		validateFamilyModel(model -> {
			Family expectedFamily1 = createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_1)));
				family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
				family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
			});
			Family expectedFamily2 = createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
			});
			assertNumberOfFamilies(model, 2);
			Family family1 = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_1);
			assertFamily(expectedFamily1, family1);
			Family family2 = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_2);
			assertFamily(expectedFamily2, family2);
		});
	}

	@Test
	public void testChangeName_newFamilyAsChild() {
		createInsuranceDatabaseWithCompleteFamily();

		decideParentOrChild(PositionPreference.Child);
		changeInsuranceModel(model -> {
			InsuranceDatabase insuranceDatabase = claimInsuranceDatabase(model);
			claimInsuranceClient(insuranceDatabase, FIRST_SON_1, LAST_NAME_1).setName(fullName(FIRST_SON_1, LAST_NAME_2));
		});

		validateFamilyModel(model -> {
			Family expectedFamily1 = createFamily(family -> {
				family.setLastName(LAST_NAME_1);
				family.setFather(createFamilyMember(member -> member.setFirstName(FIRST_DAD_1)));
				family.setMother(createFamilyMember(member -> member.setFirstName(FIRST_MOM_1)));
				family.getDaughters().add(createFamilyMember(member -> member.setFirstName(FIRST_DAU_1)));
			});
			Family expectedFamily2 = createFamily(family -> {
				family.setLastName(LAST_NAME_2);
				family.getSons().add(createFamilyMember(member -> member.setFirstName(FIRST_SON_1)));
			});
			assertNumberOfFamilies(model, 2);
			Family family1 = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_1);
			assertFamily(expectedFamily1, family1);
			Family family2 = claimFamily(getDefaultFamilyRegister(model), LAST_NAME_2);
			assertFamily(expectedFamily2, family2);
		});
	}
}

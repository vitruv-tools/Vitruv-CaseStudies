package tools.vitruv.applications.demo.insurancefamilies.tests.util;

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory;
import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.Member;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceClient;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceFactory;
import java.util.function.Consumer;

public final class CreatorsUtil {
	private CreatorsUtil() {
	}

	public static Family createFamily(Consumer<Family> familyInitalization) {
		Family family = FamiliesFactory.eINSTANCE.createFamily();
		familyInitalization.accept(family);
		return family;
	}

	public static Member createFamilyMember(Consumer<Member> familyMemberInitalization) {
		Member member = FamiliesFactory.eINSTANCE.createMember();
		familyMemberInitalization.accept(member);
		return member;
	}

	public static InsuranceDatabase createInsuranceDatabase(Consumer<InsuranceDatabase> insuranceDatabaseInitialization) {
		InsuranceDatabase insuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase();
		insuranceDatabaseInitialization.accept(insuranceDatabase);
		return insuranceDatabase;
	}


	public static InsuranceClient createInsuranceClient(Consumer<InsuranceClient> insuranceClientInitialization) {
		InsuranceClient insuranceClient = InsuranceFactory.eINSTANCE.createInsuranceClient();
		insuranceClientInitialization.accept(insuranceClient);
		return insuranceClient;
	}
}

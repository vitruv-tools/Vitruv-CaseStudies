package tools.vitruv.applications.demo.performance.common;

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.families.Member;

public class FixedFamiliesModelGenerator {
	private FixedFamiliesModelGenerator() {}
	
	public static record FamilyModelGenerationParameters(int noFamilies, int noMembersPerFamily) {}
	
	public static void createFamilies(FamilyRegister register, FamilyModelGenerationParameters params) {
		for (long familyNumber = 0; familyNumber < params.noFamilies(); familyNumber++) {
			var family = FamiliesFactory.eINSTANCE.createFamily();
			family.setLastName(generateName());
			family.setFather(createMember());
			family.setMother(createMember());
			
			for (long childCount = 0; childCount < params.noMembersPerFamily(); childCount++) {
				if (childCount % 2 == 0) {
					family.getDaughters().add(createMember());
				} else {
					family.getSons().add(createMember());
				}
			}
			
			register.getFamilies().add(family);
		}
	}
	
	private static Member createMember() {
		var member = FamiliesFactory.eINSTANCE.createMember();
		member.setFirstName(generateName());
		return member;
	}
	
	private static String generateName() {
		return "aaaaaaaaaaaaaaaaaaaaaaaaaa";
	}
}

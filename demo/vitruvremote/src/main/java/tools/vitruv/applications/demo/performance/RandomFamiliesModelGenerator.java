package tools.vitruv.applications.demo.performance;

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.families.Member;
import tools.vitruv.applications.demo.performance.common.FamilyModelGenerationParameters;

public class RandomFamiliesModelGenerator {
	private RandomFamiliesModelGenerator() {}
	
	public static void createFamilies(FamilyRegister register, FamilyModelGenerationParameters params) {
		for (long familyNumber = 0; familyNumber < params.noFamilies(); familyNumber++) {
			var family = FamiliesFactory.eINSTANCE.createFamily();
			family.setLastName(generateName());
			family.setFather(createMember());
			family.setMother(createMember());
			
			for (long childCount = 0; childCount < params.noMembersPerFamily(); childCount++) {
				var rnd = Math.random();
				if (rnd < 0.5) {
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
		int a = 'a';
		long nameLength = Math.round(Math.random() * 20) + 4;
		String generatedName = "";
		for (long idx = 0; idx < nameLength; idx++) {
			generatedName += (char) (a + Math.round(Math.random() * 26));
		}
		return generatedName;
	}
	
	
}

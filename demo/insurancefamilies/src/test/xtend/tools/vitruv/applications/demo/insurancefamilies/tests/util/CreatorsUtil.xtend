package tools.vitruv.applications.demo.insurancefamilies.tests.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.metamodels.families.Family
import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory
import edu.kit.ipd.sdq.metamodels.families.Member
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceClient
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceFactory
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase

@Utility
class CreatorsUtil {
	static def Family createFamily((Family)=> void familyInitalization){
		var family = FamiliesFactory.eINSTANCE.createFamily
		familyInitalization.apply(family)
		return family
	}
	
	static def Member createFamilyMember((Member)=> void familyMemberInitalization){
		var member = FamiliesFactory.eINSTANCE.createMember
		familyMemberInitalization.apply(member)
		return member
	}
	
	static def InsuranceDatabase createInsuranceDatabase((InsuranceDatabase)=> void insuranceDatabaseInitialization) {
		var insuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase
		insuranceDatabaseInitialization.apply(insuranceDatabase)
		return insuranceDatabase
	}
	
	
	static def InsuranceClient createInsuranceClient((InsuranceClient)=> void insuranceClientInitialization) {
		var insuranceClient = InsuranceFactory.eINSTANCE.createInsuranceClient
		insuranceClientInitialization.apply(insuranceClient)
		return insuranceClient
	}
}

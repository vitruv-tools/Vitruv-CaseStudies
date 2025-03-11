package tools.vitruv.applications.demo.insurancefamilies.tests.util

import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase
import tools.vitruv.dsls.testutils.TestModel

interface InsuranceFamiliesTestModelFactory {
	def void changeInsuranceModel((TestModel<InsuranceDatabase>)=>void modelModification)

	def void changeFamilyModel((TestModel<FamilyRegister>)=>void modelModification)

	def void validateInsuranceModel((TestModel<InsuranceDatabase>)=>void viewValidation)

	def void validateFamilyModel((TestModel<FamilyRegister>)=>void viewValidation)
}

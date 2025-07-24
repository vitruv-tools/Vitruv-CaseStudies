package tools.vitruv.applications.demo.insurancefamilies.tests.util

import tools.vitruv.change.testutils.views.NonTransactionalTestView
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase
import tools.vitruv.dsls.testutils.TestViewBasedTestModel
import tools.vitruv.dsls.testutils.ChangePropagatingTestViewBasedTestModel
import tools.vitruv.dsls.testutils.TestModel

class InsuranceFamiliesDefaultTestModelFactory implements InsuranceFamiliesTestModelFactory {
	val TestViewBasedTestModel<InsuranceDatabase> insuranceModel
	val TestViewBasedTestModel<FamilyRegister> familiesModel

	new(NonTransactionalTestView testView) {
		insuranceModel = new ChangePropagatingTestViewBasedTestModel(testView, InsuranceDatabase)
		familiesModel = new ChangePropagatingTestViewBasedTestModel(testView, FamilyRegister)
	}

	override void changeInsuranceModel((TestModel<InsuranceDatabase>)=>void modelModification) {
		insuranceModel.applyChanges(modelModification, familiesModel)
	}

	override void changeFamilyModel((TestModel<FamilyRegister>)=>void modelModification) {
		familiesModel.applyChanges(modelModification, insuranceModel)
	}

	override void validateInsuranceModel((TestModel<InsuranceDatabase>)=>void viewValidation) {
		viewValidation.apply(insuranceModel)
	}

	override void validateFamilyModel((TestModel<FamilyRegister>)=>void viewValidation) {
		viewValidation.apply(familiesModel)
	}
}

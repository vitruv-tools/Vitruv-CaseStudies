package tools.vitruv.applications.demo.insurancefamilies.tests.util

import tools.vitruv.framework.testutils.integration.TestViewFactory
import static tools.vitruv.applications.demo.insurancefamilies.tests.util.TestModelViewAdapter.createTestModelAdapter
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import tools.vitruv.framework.views.View
import tools.vitruv.framework.views.ViewProvider
import tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceFamiliesTestModelFactory
import tools.vitruv.dsls.testutils.TestModel

class InsuranceFamiliesViewBasedTestModelFactory extends TestViewFactory implements InsuranceFamiliesTestModelFactory {

	new(ViewProvider viewProvider) {
		super(viewProvider)
	}

	private def View createInsuranceView() {
		createViewOfElements("insurance", #{InsuranceDatabase})
	}

	private def View createFamilyView() {
		createViewOfElements("families", #{FamilyRegister})
	}

	override void changeInsuranceModel((TestModel<InsuranceDatabase>)=>void modelModification) {
		val insuranceView = createInsuranceView
		changeViewRecordingChanges(insuranceView, [
			modelModification.apply(createTestModelAdapter(insuranceView, InsuranceDatabase))
		])
	}

	override void changeFamilyModel((TestModel<FamilyRegister>)=>void modelModification) {
		val familyView = createFamilyView
		changeViewRecordingChanges(familyView, [
			modelModification.apply(createTestModelAdapter(familyView, FamilyRegister))
		])
	}

	override void validateInsuranceModel((TestModel<InsuranceDatabase>)=>void viewValidation) {
		val insuranceView = createInsuranceView
		validateView(insuranceView, [viewValidation.apply(createTestModelAdapter(insuranceView, InsuranceDatabase))])
	}

	override void validateFamilyModel((TestModel<FamilyRegister>)=>void viewValidation) {
		val familyView = createFamilyView
		validateView(familyView, [viewValidation.apply(createTestModelAdapter(familyView, FamilyRegister))])
	}
}

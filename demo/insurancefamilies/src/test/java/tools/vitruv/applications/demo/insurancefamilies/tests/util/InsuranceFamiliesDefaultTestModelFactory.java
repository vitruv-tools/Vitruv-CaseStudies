package tools.vitruv.applications.demo.insurancefamilies.tests.util;

import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase;
import java.util.function.Consumer;
import tools.vitruv.change.testutils.views.NonTransactionalTestView;
import tools.vitruv.dsls.testutils.ChangePropagatingTestViewBasedTestModel;
import tools.vitruv.dsls.testutils.TestModel;
import tools.vitruv.dsls.testutils.TestViewBasedTestModel;

public class InsuranceFamiliesDefaultTestModelFactory implements InsuranceFamiliesTestModelFactory {
	private final TestViewBasedTestModel<InsuranceDatabase> insuranceModel;
	private final TestViewBasedTestModel<FamilyRegister> familiesModel;

	public InsuranceFamiliesDefaultTestModelFactory(NonTransactionalTestView testView) {
		insuranceModel = new ChangePropagatingTestViewBasedTestModel<>(testView, InsuranceDatabase.class);
		familiesModel = new ChangePropagatingTestViewBasedTestModel<>(testView, FamilyRegister.class);
	}

	@Override
	public void changeInsuranceModel(Consumer<TestModel<InsuranceDatabase>> modelModification) {
		insuranceModel.applyChanges(modelModification, familiesModel);
	}

	@Override
	public void changeFamilyModel(Consumer<TestModel<FamilyRegister>> modelModification) {
		familiesModel.applyChanges(modelModification, insuranceModel);
	}

	@Override
	public void validateInsuranceModel(Consumer<TestModel<InsuranceDatabase>> viewValidation) {
		viewValidation.accept(insuranceModel);
	}

	@Override
	public void validateFamilyModel(Consumer<TestModel<FamilyRegister>> viewValidation) {
		viewValidation.accept(familiesModel);
	}
}

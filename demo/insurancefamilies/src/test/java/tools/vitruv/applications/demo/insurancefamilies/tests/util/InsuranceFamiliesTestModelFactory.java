package tools.vitruv.applications.demo.insurancefamilies.tests.util;

import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase;
import java.util.function.Consumer;
import tools.vitruv.dsls.testutils.TestModel;

public interface InsuranceFamiliesTestModelFactory {
	void changeInsuranceModel(Consumer<TestModel<InsuranceDatabase>> modelModification);

	void changeFamilyModel(Consumer<TestModel<FamilyRegister>> modelModification);

	void validateInsuranceModel(Consumer<TestModel<InsuranceDatabase>> viewValidation);

	void validateFamilyModel(Consumer<TestModel<FamilyRegister>> viewValidation);
}

package tools.vitruv.applications.demo.insurancefamilies.tests.util;

import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;

import edu.kit.ipd.sdq.metamodels.insurance.InsuranceClient;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase;
import java.util.Objects;
import tools.vitruv.dsls.testutils.TestModel;

public final class InsuranceQueryUtil {
	private InsuranceQueryUtil() {
	}

	public static InsuranceDatabase claimInsuranceDatabase(TestModel<InsuranceDatabase> model) {
		return claimOne(model.getTypedRootObjects());
	}

	public static InsuranceClient claimInsuranceClient(InsuranceDatabase insuranceDatabase, String firstName, String lastName) {
		return claimOne(insuranceDatabase.getInsuranceclient().stream()
			.filter(it -> Objects.equals(it.getName(), firstName + " " + lastName)).toList());
	}
}

package tools.vitruv.applications.demo.insurancefamilies.tests.util;

import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;

import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import java.util.Objects;
import org.eclipse.emf.common.util.EList;
import tools.vitruv.dsls.testutils.TestModel;

public final class FamiliesQueryUtil {
	private FamiliesQueryUtil() {
	}

	public static FamilyRegister claimFamilyRegister(TestModel<FamilyRegister> testModel) {
		return claimOne(testModel.getTypedRootObjects());
	}

	public static EList<Family> claimFamilies(TestModel<FamilyRegister> testModel) {
		return claimOne(testModel.getTypedRootObjects()).getFamilies();
	}

	public static Family claimFamily(FamilyRegister register, String lastName) {
		return claimOne(register.getFamilies().stream().filter(it -> Objects.equals(it.getLastName(), lastName)).toList());
	}
}

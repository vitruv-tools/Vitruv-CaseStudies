package tools.vitruv.applications.demo.insurancefamilies.tests.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import edu.kit.ipd.sdq.metamodels.families.Family
import org.eclipse.emf.common.util.EList
import tools.vitruv.dsls.testutils.TestModel

@Utility
class FamiliesQueryUtil {
	
	static def FamilyRegister claimFamilyRegister(TestModel<FamilyRegister> testModel) {
		testModel.getTypedRootObjects().claimOne
	}
	
	static def EList<Family> claimFamilies(TestModel<FamilyRegister> testModel) {
		testModel.getTypedRootObjects().claimOne.families
	}
	
	static def Family claimFamily(FamilyRegister register, String lastName) {
		register.families.filter[it.lastName == lastName].claimOne
	}
}

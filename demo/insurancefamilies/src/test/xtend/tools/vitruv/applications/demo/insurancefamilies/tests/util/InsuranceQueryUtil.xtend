package tools.vitruv.applications.demo.insurancefamilies.tests.util

import edu.kit.ipd.sdq.activextendannotations.Utility

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase
import tools.vitruv.dsls.testutils.TestModel

@Utility
class InsuranceQueryUtil {
	
	static def claimInsuranceDatabase(TestModel<InsuranceDatabase> model){
		model.getTypedRootObjects().claimOne
	}
	
	static def claimInsuranceClient(InsuranceDatabase insuranceDatabase, String firstName, String lastName){
		insuranceDatabase.insuranceclient.filter[name == firstName + " " + lastName].claimOne
	}
}
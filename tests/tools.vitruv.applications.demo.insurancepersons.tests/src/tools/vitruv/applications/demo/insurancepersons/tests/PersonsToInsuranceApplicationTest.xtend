package tools.vitruv.applications.demo.insurancepersons.tests

import org.eclipse.xtend.lib.annotations.Delegate
import org.junit.jupiter.api.BeforeEach
import tools.vitruv.testutils.TestProject
import java.nio.file.Path
import tools.vitruv.change.propagation.ChangePropagationMode
import tools.vitruv.testutils.VirtualModelBasedTestView
import tools.vitruv.testutils.views.UriMode
import tools.vitruv.testutils.DefaultVirtualModelBasedTestView
import tools.vitruv.dsls.demo.insurancepersons.tests.PersonsToInsuranceTest

class PersonsToInsuranceApplicationTest extends PersonsToInsuranceTest implements VirtualModelBasedTestView {
	@Delegate var VirtualModelBasedTestView virtualModelBasedTestView

	@BeforeEach
	def void prepareVirtualModel(@TestProject Path testProjectPath, @TestProject(variant="vsum") Path vsumPath) {
		virtualModelBasedTestView = new DefaultVirtualModelBasedTestView(
			testProjectPath,
			vsumPath,
			changePropagationSpecifications,
			UriMode.FILE_URIS
		)
		virtualModelBasedTestView.virtualModel.changePropagationMode = ChangePropagationMode.SINGLE_STEP
		testView = virtualModelBasedTestView
	}

}

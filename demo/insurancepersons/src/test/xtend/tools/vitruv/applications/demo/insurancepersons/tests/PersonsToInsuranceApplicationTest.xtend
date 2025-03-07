package tools.vitruv.applications.demo.insurancepersons.tests

import org.eclipse.xtend.lib.annotations.Delegate
import org.junit.jupiter.api.BeforeEach
import tools.vitruv.change.testutils.TestProject
import java.nio.file.Path
import tools.vitruv.change.propagation.ChangePropagationMode
import tools.vitruv.framework.testutils.integration.VirtualModelBasedTestView
import tools.vitruv.change.testutils.views.UriMode
import tools.vitruv.framework.testutils.integration.DefaultVirtualModelBasedTestView

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

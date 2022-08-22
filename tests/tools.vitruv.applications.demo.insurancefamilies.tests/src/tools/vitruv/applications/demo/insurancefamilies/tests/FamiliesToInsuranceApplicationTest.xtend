package tools.vitruv.applications.demo.insurancefamilies.tests

import org.junit.jupiter.api.BeforeEach
import tools.vitruv.testutils.TestProject
import java.nio.file.Path
import tools.vitruv.testutils.TestUserInteraction
import tools.vitruv.framework.vsum.VirtualModelBuilder
import tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceFamiliesViewBasedTestModelFactory
import tools.vitruv.dsls.demo.insurancefamilies.tests.families2insurance.FamiliesToInsuranceTest

class FamiliesToInsuranceApplicationTest extends FamiliesToInsuranceTest {

	@BeforeEach
	def final package void prepareTestModelFactory(@TestProject Path testProjectPath,
		@TestProject(variant="vsum") Path vsumPath) {
		val userInteraction = new TestUserInteraction()
		val virtualModel = new VirtualModelBuilder() //
		.withStorageFolder(vsumPath) //
		.withUserInteractorForResultProvider(new TestUserInteraction.ResultProvider(userInteraction)) //
		.withChangePropagationSpecifications(changePropagationSpecifications).buildAndInitialize()
		testModelFactory = new InsuranceFamiliesViewBasedTestModelFactory(virtualModel)
	}

}

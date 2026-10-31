package tools.vitruv.applications.demo.insurancefamilies.tests;

import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import tools.vitruv.applications.demo.insurancefamilies.tests.families2insurance.FamiliesToInsuranceTest;
import tools.vitruv.applications.demo.insurancefamilies.tests.util.InsuranceFamiliesViewBasedTestModelFactory;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestUserInteraction;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

public class FamiliesToInsuranceApplicationTest extends FamiliesToInsuranceTest {

	@BeforeEach
	final void prepareTestModelFactory(@TestProject Path testProjectPath,
		@TestProject(variant = "vsum") Path vsumPath) throws Exception {
		TestUserInteraction userInteraction = new TestUserInteraction();
		InternalVirtualModel virtualModel = new VirtualModelBuilder() //
		.withStorageFolder(vsumPath) //
		.withUserInteractorForResultProvider(new TestUserInteraction.ResultProvider(userInteraction)) //
		.withChangePropagationSpecifications(getChangePropagationSpecifications()).buildAndInitialize();
		setTestModelFactory(new InsuranceFamiliesViewBasedTestModelFactory(virtualModel));
	}

}

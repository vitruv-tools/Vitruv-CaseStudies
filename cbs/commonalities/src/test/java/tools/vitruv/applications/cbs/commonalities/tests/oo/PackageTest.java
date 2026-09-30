package tools.vitruv.applications.cbs.commonalities.tests.oo;

import static tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.orderedPairs;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest;
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaPackageTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlPackageTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider;

public class PackageTest extends CBSCommonalitiesExecutionTest {

	public static List<Object[]> testParameters() {
		List<DomainModelsProvider<DomainModels>> domainModelsProviders = List.of(
				new UmlTestModelsProvider<>(UmlPackageTestModels::new),
				new JavaTestModelsProvider<>(JavaPackageTestModels::new));
		return orderedPairs(domainModelsProviders);
	}

	public interface DomainModels {

		String ROOT1_PACKAGE_NAME = "root1";
		String ROOT2_PACKAGE_NAME = "root2";
		String SUB1_PACKAGE_NAME = "sub1";
		String SUB2_PACKAGE_NAME = "sub2";

		DomainModel singleRootPackageCreation();

		DomainModel multiRootPackageCreation();

		DomainModel subPackagesCreation();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void singleRootPackageCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).singleRootPackageCreation().createAndSynchronize();
		getModels(targetModelsProvider).singleRootPackageCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void multiRootPackageCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).multiRootPackageCreation().createAndSynchronize();
		getModels(targetModelsProvider).multiRootPackageCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void subPackagesCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).subPackagesCreation().createAndSynchronize();
		getModels(targetModelsProvider).subPackagesCreation().check();
		// TODO check that no PCM repository is created for this?
	}

	// TODO package renaming
	// TODO package moving
}

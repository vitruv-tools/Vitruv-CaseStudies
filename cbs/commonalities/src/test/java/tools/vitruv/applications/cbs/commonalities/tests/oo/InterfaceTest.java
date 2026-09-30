package tools.vitruv.applications.cbs.commonalities.tests.oo;

import static tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.orderedPairs;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest;
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaInterfaceTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlInterfaceTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider;

public class InterfaceTest extends CBSCommonalitiesExecutionTest {

	public static List<Object[]> testParameters() {
		List<DomainModelsProvider<DomainModels>> domainModelsProviders = List.of(
				new UmlTestModelsProvider<>(UmlInterfaceTestModels::new),
				new JavaTestModelsProvider<>(JavaInterfaceTestModels::new));
		return orderedPairs(domainModelsProviders);
	}

	/**
	 * All created interfaces are of public visibility currently.
	 */
	public interface DomainModels {

		String PACKAGE_1_NAME = "root";
		String PACKAGE_2_NAME = "root2";
		String INTERFACE_1_NAME = "Foo";
		String INTERFACE_2_NAME = "Bar";
		String INTERFACE_3_NAME = "Baz";

		// Empty interface

		/**
		 * An interface with only the minimally required attributes.
		 */
		DomainModel emptyInterfaceCreation();

		// Multiple interfaces

		DomainModel multipleInterfacesInSamePackageCreation();
		DomainModel multipleInterfacesInDifferentPackagesCreation();

		// Super interfaces

		/**
		 * Interface 1 extending interface 2 from the same package.
		 */
		DomainModel interfaceWithSuperInterfaceCreation();

		/**
		 * Interface 1 extending interface 2 and 3 from the same package.
		 */
		DomainModel interfaceWithMultipleSuperInterfacesCreation();
	}

	// Empty interface

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void emptyInterfaceCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).emptyInterfaceCreation().createAndSynchronize();
		getModels(targetModelsProvider).emptyInterfaceCreation().check();
	}

	// Multiple interfaces

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void multipleInterfacesInSamePackageCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).multipleInterfacesInSamePackageCreation().createAndSynchronize();
		getModels(targetModelsProvider).multipleInterfacesInSamePackageCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void multipleInterfacesInDifferentPackagesCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).multipleInterfacesInDifferentPackagesCreation().createAndSynchronize();
		getModels(targetModelsProvider).multipleInterfacesInDifferentPackagesCreation().check();
	}

	// Super interfaces

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void interfaceWithSuperInterfaceCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).interfaceWithSuperInterfaceCreation().createAndSynchronize();
		getModels(targetModelsProvider).interfaceWithSuperInterfaceCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void interfaceWithMultipleSuperInterfacesCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).interfaceWithMultipleSuperInterfacesCreation().createAndSynchronize();
		getModels(targetModelsProvider).interfaceWithMultipleSuperInterfacesCreation().check();
	}

	// TODO renaming
	// TODO support for non-public interfaces? (eg. package-private, or private inner interfaces)
}

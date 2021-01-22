package tools.vitruv.applications.cbs.commonalities.tests.oo

import java.util.List
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaInterfaceTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlInterfaceTestModels
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.*
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.ParameterizedTest

class InterfaceTest extends CBSCommonalitiesExecutionTest {

	static def List<Object[]> testParameters() {
		val domainModelsProviders = #[
			new UmlTestModelsProvider [new UmlInterfaceTestModels(it)],
			new JavaTestModelsProvider [new JavaInterfaceTestModels(it)]
		]
		return domainModelsProviders.orderedPairs
	}

	/**
	 * All created interfaces are of public visibility currently.
	 */
	interface DomainModels {

		static val PACKAGE_1_NAME = 'root'
		static val PACKAGE_2_NAME = 'root2'
		static val INTERFACE_1_NAME = 'Foo'
		static val INTERFACE_2_NAME = 'Bar'
		static val INTERFACE_3_NAME = 'Baz'

		// Empty interface

		/**
		 * An interface with only the minimally required attributes.
		 */
		def DomainModel emptyInterfaceCreation()

		// Multiple interfaces

		def DomainModel multipleInterfacesInSamePackageCreation()
		def DomainModel multipleInterfacesInDifferentPackagesCreation()

		// Super interfaces

		/**
		 * Interface 1 extending interface 2 from the same package.
		 */
		def DomainModel interfaceWithSuperInterfaceCreation()

		/**
		 * Interface 1 extending interface 2 and 3 from the same package.
		 */
		def DomainModel interfaceWithMultipleSuperInterfacesCreation()
	}

	// Empty interface

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void emptyInterfaceCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.emptyInterfaceCreation.createAndSynchronize()
		targetModelsProvider.getModels.emptyInterfaceCreation.check()
	}

	// Multiple interfaces

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void multipleInterfacesInSamePackageCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.multipleInterfacesInSamePackageCreation.createAndSynchronize()
		targetModelsProvider.getModels.multipleInterfacesInSamePackageCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void multipleInterfacesInDifferentPackagesCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.multipleInterfacesInDifferentPackagesCreation.createAndSynchronize()
		targetModelsProvider.getModels.multipleInterfacesInDifferentPackagesCreation.check()
	}

	// Super interfaces

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void interfaceWithSuperInterfaceCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.interfaceWithSuperInterfaceCreation.createAndSynchronize()
		targetModelsProvider.getModels.interfaceWithSuperInterfaceCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void interfaceWithMultipleSuperInterfacesCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.interfaceWithMultipleSuperInterfacesCreation.createAndSynchronize()
		targetModelsProvider.getModels.interfaceWithMultipleSuperInterfacesCreation.check()
	}

	// TODO renaming
	// TODO support for non-public interfaces? (eg. package-private, or private inner interfaces)
}

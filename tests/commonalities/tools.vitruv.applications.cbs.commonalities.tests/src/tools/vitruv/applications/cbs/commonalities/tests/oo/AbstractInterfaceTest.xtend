package tools.vitruv.applications.cbs.commonalities.tests.oo

import org.junit.Test
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.DomainModel

abstract class AbstractInterfaceTest extends CBSCommonalitiesExecutionTest {

	interface DomainModels {

		static val PACKAGE_1_NAME = 'root'
		static val PACKAGE_2_NAME = 'root2'
		static val INTERFACE_1_NAME = 'Foo'
		static val INTERFACE_2_NAME = 'Bar'

		// Empty interface

		/**
		 * An interface with only the minimally required attributes.
		 */
		def DomainModel emptyInterfaceCreation()

		// Multiple interfaces

		def DomainModel multipleInterfacesInSamePackageCreation()
		def DomainModel multipleInterfacesInDifferentPackagesCreation()
	}

	protected abstract def DomainModels getSourceModels()
	protected abstract def DomainModels getTargetModels()

	// Empty interface

	@Test
	def void emptyInterfaceCreation() {
		sourceModels.emptyInterfaceCreation.createAndSynchronize()
		targetModels.emptyInterfaceCreation.check()
	}

	// Multiple interfaces

	@Test
	def void multipleInterfacesInSamePackageCreation() {
		sourceModels.multipleInterfacesInSamePackageCreation.createAndSynchronize()
		targetModels.multipleInterfacesInSamePackageCreation.check()
	}

	@Test
	def void multipleInterfacesInDifferentPackagesCreation() {
		sourceModels.multipleInterfacesInDifferentPackagesCreation.createAndSynchronize()
		targetModels.multipleInterfacesInDifferentPackagesCreation.check()
	}

	// TODO renaming
}

package tools.vitruv.applications.cbs.commonalities.tests.oo

import org.junit.Test
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.DomainModel

abstract class AbstractPackageTest extends CBSCommonalitiesExecutionTest {

	interface DomainModels {

		static val ROOT1_PACKAGE_NAME = 'root1'
		static val ROOT2_PACKAGE_NAME = 'root2'
		static val SUB1_PACKAGE_NAME = 'sub1'
		static val SUB2_PACKAGE_NAME = 'sub2'

		def DomainModel singleRootPackageCreation()
		def DomainModel multiRootPackageCreation()
		def DomainModel subPackagesCreation()
	}

	protected abstract def DomainModels getSourceModels()
	protected abstract def DomainModels getTargetModels()

	@Test
	def void singleRootPackageCreation() {
		sourceModels.singleRootPackageCreation.createAndSynchronize()
		targetModels.singleRootPackageCreation.check()
	}

	@Test
	def void multiRootPackageCreation() {
		sourceModels.multiRootPackageCreation.createAndSynchronize()
		targetModels.multiRootPackageCreation.check()
	}

	@Test
	def void subPackagesCreation() {
		sourceModels.subPackagesCreation.createAndSynchronize()
		targetModels.subPackagesCreation.check()
		// TODO check that no PCM repository is created for this?
	}

	// TODO package renaming
	// TODO package moving
}

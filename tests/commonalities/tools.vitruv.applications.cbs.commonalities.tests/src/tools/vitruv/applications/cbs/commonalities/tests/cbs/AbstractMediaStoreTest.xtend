package tools.vitruv.applications.cbs.commonalities.tests.cbs

import org.junit.Test
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.DomainModel

abstract class AbstractMediaStoreTest extends CBSCommonalitiesExecutionTest {

	interface DomainModels {

		def DomainModel mediaStoreCreation()
	}

	protected abstract def DomainModels getSourceModels()
	protected abstract def DomainModels getTargetModels()

	@Test
	def void mediaStoreCreation() {
		sourceModels.mediaStoreCreation.createAndSynchronize()
		targetModels.mediaStoreCreation.check()
	}
}

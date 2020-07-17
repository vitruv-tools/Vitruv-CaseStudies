package tools.vitruv.applications.cbs.commonalities.tests.cbs

import org.junit.Test
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.DomainModel

abstract class AbstractRepositoryTest extends CBSCommonalitiesExecutionTest {

	interface DomainModels {

		def DomainModel emptyRepositoryCreation()
	}

	protected abstract def DomainModels getSourceModels()
	protected abstract def DomainModels getTargetModels()

	@Test
	def void emptyRepositoryCreation() {
		sourceModels.emptyRepositoryCreation.createAndSynchronize()
		targetModels.emptyRepositoryCreation.check()
	}

	// TODO repository renaming
}

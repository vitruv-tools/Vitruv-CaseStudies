package tools.vitruv.applications.cbs.commonalities.tests.cbs

import org.junit.Test
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.DomainModel

abstract class AbstractBasicComponentTest extends CBSCommonalitiesExecutionTest {

	interface DomainModels {

		static val COMPONENT_NAME = 'SomeBasicComponent'

		def DomainModel emptyBasicComponentCreation()
	}

	protected abstract def DomainModels getSourceModels()
	protected abstract def DomainModels getTargetModels()

	@Test
	def void emptyBasicComponentCreation() {
		sourceModels.emptyBasicComponentCreation.createAndSynchronize()
		targetModels.emptyBasicComponentCreation.check()
	}

	// TODO component renaming
}

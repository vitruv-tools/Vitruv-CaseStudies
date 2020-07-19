package tools.vitruv.applications.cbs.commonalities.tests.cbs

import org.junit.Test
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.DomainModel

abstract class AbstractComponentInterfaceTest extends CBSCommonalitiesExecutionTest {

	interface DomainModels {

		static val INTERFACE_NAME = 'SomeInterface'

		def DomainModel emptyComponentInterfaceCreation()
	}

	protected abstract def DomainModels getSourceModels()
	protected abstract def DomainModels getTargetModels()

	@Test
	def void emptyComponentInterfaceCreation() {
		sourceModels.emptyComponentInterfaceCreation.createAndSynchronize()
		targetModels.emptyComponentInterfaceCreation.check()
	}

	// TODO interface renaming
}

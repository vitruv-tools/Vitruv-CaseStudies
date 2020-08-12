package tools.vitruv.applications.cbs.commonalities.tests

import com.google.inject.Inject
import tools.vitruv.applications.cbs.commonalities.tests.util.AbstractCBSCommonalitiesExecutionTest

abstract class CBSCommonalitiesExecutionTest extends AbstractCBSCommonalitiesExecutionTest {

	@Inject CBSExecutionTestCompiler compiler

	override protected createChangePropagationSpecifications() {
		compiler.changePropagationDefinitions
	}
}

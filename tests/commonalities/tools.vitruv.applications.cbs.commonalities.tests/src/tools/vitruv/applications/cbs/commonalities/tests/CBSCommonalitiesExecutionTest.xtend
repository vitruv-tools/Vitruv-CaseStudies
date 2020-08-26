package tools.vitruv.applications.cbs.commonalities.tests

import com.google.inject.Inject
import org.emftext.language.java.JavaClasspath
import tools.vitruv.applications.cbs.commonalities.tests.util.AbstractCBSCommonalitiesExecutionTest
import tools.vitruv.domains.java.JamoppLibraryHelper

abstract class CBSCommonalitiesExecutionTest extends AbstractCBSCommonalitiesExecutionTest {

	@Inject CBSExecutionTestCompiler compiler

	override protected createChangePropagationSpecifications() {
		compiler.changePropagationDefinitions
	}

	override protected setup() {
		super.setup()
		// This is necessary because otherwise Maven tests will fail as resources from previous
		// tests are still in the classpath and accidentally resolved:
		JavaClasspath.reset()
		// We also need to freshly register the standard library again then:
		JamoppLibraryHelper.registerStdLib
	}
}

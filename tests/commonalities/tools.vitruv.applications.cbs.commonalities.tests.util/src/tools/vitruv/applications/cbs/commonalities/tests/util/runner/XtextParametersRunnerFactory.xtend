package tools.vitruv.applications.cbs.commonalities.tests.util.runner

import org.junit.runners.parameterized.ParametersRunnerFactory
import org.junit.runners.parameterized.TestWithParameters
import org.junit.runners.model.InitializationError

class XtextParametersRunnerFactory implements ParametersRunnerFactory {

	override createRunnerForTestWithParameters(TestWithParameters test) throws InitializationError {
		return new XtextRunnerWithParameters(test)
	}
}

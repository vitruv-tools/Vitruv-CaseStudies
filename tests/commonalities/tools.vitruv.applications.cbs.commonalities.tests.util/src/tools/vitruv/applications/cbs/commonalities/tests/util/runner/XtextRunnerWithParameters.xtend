package tools.vitruv.applications.cbs.commonalities.tests.util.runner

import org.eclipse.xtext.testing.IInjectorProvider
import org.eclipse.xtext.testing.IRegistryConfigurator
import org.eclipse.xtext.testing.XtextRunner
import org.junit.runners.model.FrameworkMethod
import org.junit.runners.model.InitializationError
import org.junit.runners.model.Statement
import org.junit.runners.parameterized.BlockJUnit4ClassRunnerWithParameters
import org.junit.runners.parameterized.TestWithParameters
import java.util.List

/**
 * This Junit runner combines Xtext's runner and Junit's parameterized runner.
 * <p>
 * Adapted from https://www.eclipse.org/forums/index.php?t=msg&th=1075706 and
 * https://stackoverflow.com/questions/42420267/xtext-parameterized-xtext-runner
 * <p>
 * Implementation copied from {@link XtextRunner}.
 */
class XtextRunnerWithParameters extends BlockJUnit4ClassRunnerWithParameters {

	// Workaround to access the Xtext internal InjectorProviders.
	private static class InternalXtextRunner extends XtextRunner {

		new(Class<?> testClass) throws InitializationError {
			super(testClass)
		}

		override protected void validateConstructor(List<Throwable> errors) {
			// validation is done by the actual runner
		}

		// Redeclared to be visible in the outer class.
		protected override IInjectorProvider getOrCreateInjectorProvider() {
			return super.getOrCreateInjectorProvider()
		}
	}

	private InternalXtextRunner xtextRunner

	new(TestWithParameters test) throws InitializationError {
		super(test)
		xtextRunner = new InternalXtextRunner(test.getTestClass().getJavaClass())
	}

	override Object createTest() throws Exception {
		val object = super.createTest()
		val injectorProvider = xtextRunner.getOrCreateInjectorProvider()
		if (injectorProvider !== null) {
			val injector = injectorProvider.getInjector()
			if (injector !== null)
				injector.injectMembers(object)
		}
		return object
	}

	override protected Statement methodBlock(FrameworkMethod method) {
		val injectorProvider = xtextRunner.getOrCreateInjectorProvider()
		if (injectorProvider instanceof IRegistryConfigurator) {
			val registryConfigurator = injectorProvider
			registryConfigurator.setupRegistry()
			val methodBlock = superMethodBlock(method)
			return new Statement() {
				override void evaluate() throws Throwable {
					try {
						methodBlock.evaluate()
					} finally {
						registryConfigurator.restoreRegistry()
					}
				}
			}
		} else {
			return superMethodBlock(method)
		}
	}

	protected def Statement superMethodBlock(FrameworkMethod method) {
		return super.methodBlock(method)
	}
}

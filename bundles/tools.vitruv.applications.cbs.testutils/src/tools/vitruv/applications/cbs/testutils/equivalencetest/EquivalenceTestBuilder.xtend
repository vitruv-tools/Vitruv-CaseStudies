package tools.vitruv.applications.cbs.testutils.equivalencetest

import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.testutils.TestView
import java.util.function.Consumer
import org.junit.jupiter.api.DynamicNode
import tools.vitruv.testutils.TestUserInteraction

/**
 * Constructs an equivalence test, i.e. a test that executes steps in different domains and checks that if propagated,
 * the steps lead to the same results.
 */
interface EquivalenceTestBuilder {
	/**
	 * Registers a step in the provided {@code domain}, executing the provided {@code action}. This step will both
	 * be used as a test step and as a reference step to compare the results of steps in other domains.
	 */
	def void stepFor(VitruvDomain domain, Consumer<TestView> action)

	/**
	 * Registers an alternate input step in the provided {@code domain}, executing the provided {@code action}. This 
	 * step will only be used as a test step. A proper step needs to be registered for {@code domain} before calling 
	 * this method. The provided {@code name} should describe what is different in this alternative.
	 */
	def VariantOptions inputVariantFor(VitruvDomain domain, String name, Consumer<TestView> action)

	/**
	 * Declares that the steps built by this builder depend on the steps that are created by {@code otherTest}. The
	 * tests created by this builder will only run if the other steps can be executed successfully before.
	 */
	def void dependsOn((EquivalenceTestBuilder)=>void otherTest)
	
	/**
	 * Calls the provided {@code interactionsProvider} before starting the test run, allowing the lambda to
	 * configure the user interations
	 */
	def void userInteractions((TestUserInteraction)=>void interactionsProvider)

	def Iterable<? extends DynamicNode> testsThatStepsAreEquivalent()

	interface VariantOptions {
		/**
		 * When running this variant, also make sure that the effective result of it is equal to the effective result
		 * of the main step for this domain.
		 */
		def VariantOptions alsoCompareToMainStepOfSameDomain()
	}
}

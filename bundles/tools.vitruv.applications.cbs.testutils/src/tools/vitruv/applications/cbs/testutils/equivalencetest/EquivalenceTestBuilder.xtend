package tools.vitruv.applications.cbs.testutils.equivalencetest

import java.util.function.Consumer
import org.junit.jupiter.api.DynamicNode
import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor
import tools.vitruv.testutils.TestUserInteraction
import tools.vitruv.testutils.views.TestView

/**
 * Constructs an equivalence test, i.e. a test that executes steps in different metamodels and checks that if propagated,
 * the steps lead to the same results.
 */
interface EquivalenceTestBuilder {
	/**
	 * Registers a step in the provided {@code metamodel}, executing the provided {@code action}. This step will both
	 * be used as a test step and as a reference step to compare the results of steps in other metamodels.
	 */
	def void stepFor(MetamodelDescriptor metamodel, Consumer<TestView> action)

	/**
	 * Registers an alternate input step in the provided {@code metamodel}, executing the provided {@code action}. This 
	 * step will only be used as a test step. A proper step needs to be registered for {@code metamodel} before calling 
	 * this method. The provided {@code name} should describe what is different in this alternative. The variant
	 * is validated against the results of other metamodels using their steps with a name containing the one of this
	 * variant or, if such a variant does not exist, the default step.
	 */
	def VariantOptions inputVariantFor(MetamodelDescriptor metamodel, String name, Consumer<TestView> action)

	/**
	 * Declares that the steps built by this builder depend on the steps that are created by {@code otherTest}. The
	 * tests created by this builder will only run if the other steps can be executed successfully before.
	 */
	def void dependsOn((EquivalenceTestBuilder)=>void otherTest)

	/**
	 * Calls the provided {@code interactionsProvider} before starting the test run, allowing the lambda to
	 * configure the user interactions.
	 */
	def void userInteractions((TestUserInteraction)=>void interactionsProvider)

	def Iterable<? extends DynamicNode> testsThatStepsAreEquivalent()

	interface VariantOptions {
		/**
		 * When running this variant, also make sure that the effective result of it is equal to the effective result
		 * of the main step for this metamodel.
		 */
		def VariantOptions alsoCompareToMainStepOfSameMetamodel()
	}
}

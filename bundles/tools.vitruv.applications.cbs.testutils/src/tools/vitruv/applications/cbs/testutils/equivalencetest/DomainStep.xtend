package tools.vitruv.applications.cbs.testutils.equivalencetest

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.framework.domains.VitruvDomain
import java.util.function.Consumer
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.testutils.TestView
import java.util.Map
import java.util.Collection
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.testutils.TestUserInteraction

package interface DomainStep {
	def VitruvDomain getTargetDomain()

	def void executeIn(TestView testView)

	def String getName()

	def Map<VitruvDomain, DomainStep> determineReferenceSteps(
		Map<VitruvDomain, ? extends Collection<? extends DomainStep>> availableSteps)
}

@FinalFieldsConstructor
package class MainStep implements DomainStep {
	@Accessors
	val VitruvDomain targetDomain
	val Consumer<TestView> action

	override executeIn(TestView testView) {
		action.accept(testView)
	}

	override getName() { null }

	override Map<VitruvDomain, DomainStep> determineReferenceSteps(
		Map<VitruvDomain, ? extends Collection<? extends DomainStep>> availableSteps) {
		availableSteps.filter[_, steps|!steps.isEmpty].mapValues[get(0)]
	}
}

@FinalFieldsConstructor
package class VariantStep implements DomainStep, EquivalenceTestBuilder.VariantOptions {
	@Accessors
	val VitruvDomain targetDomain
	val Consumer<TestView> action
	@Accessors
	val String name
	var includeSameDomain = false

	override executeIn(TestView testView) {
		action.accept(testView)
	}

	override alsoCompareToMainStepOfSameDomain() {
		includeSameDomain = true
		this
	}

	override Map<VitruvDomain, DomainStep> determineReferenceSteps(
		Map<VitruvDomain, ? extends Collection<? extends DomainStep>> availableSteps) {
		availableSteps.filter [ domain, steps |
			!steps.isEmpty && (includeSameDomain || domain != targetDomain)
		].mapValues [
			// For other domains, look if there is a variant step with a matching name, otherwise use the main one
			it.filter(VariantStep).findFirst[targetDomain != this.targetDomain && this.name.contains(name)] ?: get(0)
		]
	}
}

@FinalFieldsConstructor
package class StepWithUserInteractionSetup implements DomainStep {
	@Delegate val DomainStep delegate
	val (TestUserInteraction)=>void userInteractionSetup

	override executeIn(TestView testView) {
		userInteractionSetup.apply(testView.userInteraction)
		delegate.executeIn(testView)
		testView.userInteraction.clearResponses()
	}
}

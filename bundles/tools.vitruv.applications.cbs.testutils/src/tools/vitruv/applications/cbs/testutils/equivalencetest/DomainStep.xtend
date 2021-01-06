package tools.vitruv.applications.cbs.testutils.equivalencetest

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.framework.domains.VitruvDomain
import java.util.function.Consumer
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.testutils.TestView
import java.util.Map
import java.util.Collection

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
		].mapValues[get(0)]
	}
}

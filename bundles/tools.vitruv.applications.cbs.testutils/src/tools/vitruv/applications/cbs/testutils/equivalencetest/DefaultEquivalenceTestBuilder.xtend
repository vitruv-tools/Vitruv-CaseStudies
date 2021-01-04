package tools.vitruv.applications.cbs.testutils.equivalencetest

import static org.junit.jupiter.api.DynamicTest.dynamicTest
import tools.vitruv.framework.domains.VitruvDomain
import java.util.LinkedList
import java.util.List
import java.util.function.Consumer
import static com.google.common.base.Preconditions.checkState
import org.junit.jupiter.api.^extension.ExtensionContext
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import static com.google.common.base.Preconditions.checkArgument
import tools.vitruv.testutils.TestView
import tools.vitruv.testutils.UriMode
import java.util.Map
import java.util.HashMap
import tools.vitruv.applications.cbs.testutils.ModelComparisonSettings
import java.util.Set
import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTestBuilder.VariantOptions

@FinalFieldsConstructor
package class DefaultEquivalenceTestBuilder implements EquivalenceTestBuilder {
	val ExtensionContext parentContext
	val List<ChangePropagationSpecification> changePropagationSpecifications
	val UriMode uriMode
	val ModelComparisonSettings modelComparisonSettings
	/* both maps always contain lists for all domains */
	val Map<VitruvDomain, List<DomainStep>> dependencySteps
	val Map<VitruvDomain, List<DomainStep>> steps
	val Checks checks

	package new(
		ExtensionContext parentContext,
		List<ChangePropagationSpecification> changePropagationSpecifications,
		UriMode uriMode,
		ModelComparisonSettings modelComparisonSettings
	) {
		this.parentContext = parentContext
		this.changePropagationSpecifications = changePropagationSpecifications
		this.uriMode = uriMode
		this.modelComparisonSettings = modelComparisonSettings
		val targetDomains = changePropagationSpecifications.map[sourceDomain].toSet
		this.dependencySteps = mapForDomains(targetDomains)
		this.steps = mapForDomains(targetDomains)
		this.checks = new Checks(steps)
	}

	override dependsOn((EquivalenceTestBuilder)=>void otherTest) {
		val dependencyBuilder = new DependencyBuilder(steps.keySet)
		otherTest.apply(dependencyBuilder)
		dependencySteps += dependencyBuilder.allDependencySteps
	}

	override stepFor(VitruvDomain domain, Consumer<TestView> action) {
		checks.forStep(domain)
		steps += new MainStep(domain, action)
	}

	override inputVariantFor(VitruvDomain domain, String name, Consumer<TestView> action) {
		checks.forVariant(domain, name)
		steps += new VariantStep(domain, action, name)
	}

	override testsThatStepsAreEquivalent() {
		checks.forFinalizing()

		steps.entrySet.flatMap [ entry |
			val testDomain = entry.key
			val testSteps = entry.value
			testSteps.indexed.map [ args |
				val testIndex = args.key
				val testStep = args.value
				val referenceSteps = testStep.determineReferenceSteps(steps)
				var testName = '''«testDomain.name» «'\u2192' /* right arrow */ » {«FOR rd : referenceSteps.keySet SEPARATOR ', '»«rd.name»«ENDFOR»}'''
				if (testStep.name !== null) {
					testName += ''' — «testStep.name»'''
				}
				val testContext = new EquivalenceTestExtensionContext(testName, testIndex, parentContext,
					testStep.targetDomain)
				dynamicTest(
					testName,
					new EquivalenceTestExecutable(testStep, dependencySteps, referenceSteps,
						changePropagationSpecifications, uriMode, modelComparisonSettings, testContext)
				)
			]
		]
	}

	def private static <T extends DomainStep> operator_add(Map<VitruvDomain, List<DomainStep>> steps, T domainStep) {
		steps.get(domainStep.targetDomain) += domainStep
		return domainStep
	}

	def private static mapForDomains(Set<VitruvDomain> domains) {
		val result = new HashMap<VitruvDomain, List<DomainStep>>(domains.size)
		for (domain : domains) {
			result.put(domain, new LinkedList)
		}
		return result
	}

	@FinalFieldsConstructor
	private static class Checks {
		val Map<VitruvDomain, List<DomainStep>> steps
		var active = true

		def private forStep(VitruvDomain domain) {
			checkArgument(
				steps.containsKey(domain),
				'''No registered «ChangePropagationSpecification.simpleName» has «domain» as source domain!'''
			)
			checkActive()
		}

		def private forVariant(VitruvDomain domain, String name) {
			forStep(domain)
			checkState(
				!steps.get(domain).isEmpty,
				'''You must first register a proper step in «domain.name» before registering a variant!'''
			)
			checkArgument(name !== null && !name.isBlank(), '''You must provide a name for a variant!''')
		}

		def private forDependency() {
			checkActive()
		}

		def private checkActive() {
			checkState(
				active,
				'''You have already requested the tests from this builder. No more steps can be added.'''
			)
		}

		def private forFinalizing() {
			checkState(
				steps.filter[_, stepList|!stepList.isEmpty].size >= 2,
				'Please register steps for at least two domains!'
			)
			active = false
		}
	}

	private static class DependencyBuilder implements EquivalenceTestBuilder {
		// we cannot merge dependency steps and steps directly, because the checks would then be incorrect.
		val Map<VitruvDomain, List<DomainStep>> dependencySteps
		val Map<VitruvDomain, List<DomainStep>> steps
		val Checks checks
		static val mockVariantOptions = new VariantOptions() {
			override alsoCompareToMainStepOfSameDomain() { this }
		}

		new(Set<VitruvDomain> targetDomains) {
			this.dependencySteps = mapForDomains(targetDomains)
			this.steps = mapForDomains(targetDomains)
			this.checks = new Checks(steps)
		}

		override dependsOn((EquivalenceTestBuilder)=>void otherTest) {
			checks.forDependency()
			val dependencyBuilder = new DependencyBuilder(steps.keySet)
			otherTest.apply(dependencyBuilder)
			dependencySteps += dependencyBuilder.allDependencySteps
		}

		override stepFor(VitruvDomain domain, Consumer<TestView> action) {
			checks.forStep(domain)
			steps += new MainStep(domain, action)
		}

		override inputVariantFor(VitruvDomain domain, String name, Consumer<TestView> action) {
			checks.forVariant(domain, name)
			// variants are irrelevant for dependencies, so discard them
			mockVariantOptions
		}

		override testsThatStepsAreEquivalent() {
			checks.forFinalizing()
			emptyList()
		}

		def getAllDependencySteps() {
			checks.forFinalizing()
			steps.forEach[domain, steps|dependencySteps.get(domain) += steps]
			return dependencySteps
		}
	}
}

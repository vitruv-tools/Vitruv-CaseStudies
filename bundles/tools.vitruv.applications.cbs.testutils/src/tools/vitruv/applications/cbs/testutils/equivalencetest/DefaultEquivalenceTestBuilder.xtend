package tools.vitruv.applications.cbs.testutils.equivalencetest

import java.util.Collection
import java.util.LinkedList
import java.util.List
import java.util.Map
import java.util.Set
import java.util.TreeMap
import java.util.function.Consumer
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.junit.jupiter.api.^extension.ExtensionContext
import tools.vitruv.applications.cbs.testutils.ModelComparisonSettings
import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTestBuilder.VariantOptions
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.testutils.TestUserInteraction
import tools.vitruv.testutils.TestView
import tools.vitruv.testutils.UriMode

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import static java.util.Comparator.comparing
import static org.junit.jupiter.api.DynamicTest.dynamicTest
import tools.vitruv.framework.propagation.ChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomainProviderRegistry

abstract class DefaultBuilderCommon implements EquivalenceTestBuilder {
	/* both maps always contain lists for all domains */
	protected val Map<VitruvDomain, List<DomainStep>> dependencySteps
	protected val Map<VitruvDomain, List<DomainStep>> steps
	protected var (TestUserInteraction)=>void userInteractionConfiguration = null
	protected val checks = new Checks(this)
	
	protected new(Set<VitruvDomain> targetDomains) {
		this.dependencySteps = mapForDomains(targetDomains)
		this.steps = mapForDomains(targetDomains)
	}
	
	def private static mapForDomains(Set<VitruvDomain> domains) {
		val result = new TreeMap<VitruvDomain, List<DomainStep>>(comparing [name])
		for (domain : domains) {
			result.put(domain, new LinkedList)
		}
		return result
	}
	
	def protected modifyIfNecessary(DomainStep domainStep) {
		if (userInteractionConfiguration !== null) {
			new StepWithUserInteractionSetup(domainStep, userInteractionConfiguration)
		} else {
			domainStep
		}
	}
	
	override userInteractions((TestUserInteraction)=>void interactionsProvider) {
		checks.forUserInteractionConfiguration()
		this.userInteractionConfiguration = interactionsProvider
	}

	@FinalFieldsConstructor
	protected static class Checks {
		val DefaultBuilderCommon target
		var active = true

		def forStep(VitruvDomain domain) {
			checkArgument(
				target.steps.containsKey(domain),
				'''No registered «ChangePropagationSpecification.simpleName» has «domain» as source domain!'''
			)
			checkActive()
		}

		def forVariant(VitruvDomain domain, String name) {
			forStep(domain)
			checkState(
				!target.steps.get(domain).isEmpty,
				'''You must first register a proper step in «domain.name» before registering a variant!'''
			)
			checkArgument(name !== null && !name.isBlank(), '''You must provide a name for a variant!''')
		}

		def forDependency() {
			checkActive()
		}

		def private checkActive() {
			checkState(
				active,
				'''You have already requested the tests from this builder. No more steps can be added.'''
			)
		}
		
		def forUserInteractionConfiguration() {
			checkState(
				target.userInteractionConfiguration === null,
				"User interactions have already been configured!"
			)
		}

		def forFinalizing() {
			checkState(
				target.steps.filter[_, stepList|!stepList.isEmpty].size >= 2,
				'Please register steps for at least two domains!'
			)
			active = false
		}
	}
}

@FinalFieldsConstructor
package class DefaultEquivalenceTestBuilder extends DefaultBuilderCommon implements EquivalenceTestBuilder {
	val ExtensionContext parentContext
	val Collection<ChangePropagationSpecification> changePropagationSpecifications
	val UriMode uriMode
	val ModelComparisonSettings modelComparisonSettings

	package new(
		ExtensionContext parentContext,
		Collection<ChangePropagationSpecification> changePropagationSpecifications,
		UriMode uriMode,
		ModelComparisonSettings modelComparisonSettings
	) {
		super(changePropagationSpecifications.flatMap[sourceMetamodel.nsURIs].flatMap [
			VitruvDomainProviderRegistry.findDomainsForMetamodelRootNsUri(it)
		].toSet)
		this.parentContext = parentContext
		this.changePropagationSpecifications = changePropagationSpecifications
		this.uriMode = uriMode
		this.modelComparisonSettings = modelComparisonSettings
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
				val testStep = modifyIfNecessary(args.value)
					
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
	
	override userInteractions((TestUserInteraction)=>void interactionsProvider) {
		checks.forUserInteractionConfiguration()
		this.userInteractionConfiguration = interactionsProvider
	}

	def private static <T extends DomainStep> operator_add(Map<VitruvDomain, List<DomainStep>> steps, T domainStep) {
		steps.get(domainStep.targetDomain) += domainStep
		return domainStep
	}

	private static class DependencyBuilder extends DefaultBuilderCommon implements EquivalenceTestBuilder  {
		static val mockVariantOptions = new VariantOptions() {
			override alsoCompareToMainStepOfSameDomain() { this }
		}

		new(Set<VitruvDomain> targetDomains) {
			super(targetDomains)
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
			steps.forEach [domain, steps |
				dependencySteps.get(domain) += steps.map [modifyIfNecessary()]
			]
			return dependencySteps
		}
	}
}

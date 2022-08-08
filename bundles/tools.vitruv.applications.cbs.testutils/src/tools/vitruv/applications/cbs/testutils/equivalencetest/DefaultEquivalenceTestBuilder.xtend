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
import tools.vitruv.testutils.TestUserInteraction
import tools.vitruv.testutils.TestView
import tools.vitruv.testutils.UriMode

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import static java.util.Comparator.comparing
import static org.junit.jupiter.api.DynamicTest.dynamicTest
import tools.vitruv.change.propagation.ChangePropagationSpecification
import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor

abstract class DefaultBuilderCommon implements EquivalenceTestBuilder {
	/* both maps always contain lists for all metamodels */
	protected val Map<MetamodelDescriptor, List<MetamodelStep>> dependencySteps
	protected val Map<MetamodelDescriptor, List<MetamodelStep>> steps
	protected var (TestUserInteraction)=>void userInteractionConfiguration = null
	protected val checks = new Checks(this)
	
	protected new(Set<MetamodelDescriptor> targetMetamodels) {
		this.dependencySteps = tools.vitruv.applications.cbs.testutils.equivalencetest.DefaultBuilderCommon.mapForMetamodels(targetMetamodels)
		this.steps = tools.vitruv.applications.cbs.testutils.equivalencetest.DefaultBuilderCommon.mapForMetamodels(targetMetamodels)
	}
	
	def private static mapForMetamodels(Set<MetamodelDescriptor> metamodels) {
		val result = new TreeMap<MetamodelDescriptor, List<MetamodelStep>>(comparing [name])
		for (metamodel : metamodels) {
			result.put(metamodel, new LinkedList)
		}
		return result
	}
	
	def protected modifyIfNecessary(MetamodelStep metamodelStep) {
		if (userInteractionConfiguration !== null) {
			new StepWithUserInteractionSetup(metamodelStep, userInteractionConfiguration)
		} else {
			metamodelStep
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

		def forStep(MetamodelDescriptor metamodel) {
			checkActive()
		}

		def forVariant(MetamodelDescriptor metamodel, String name) {
			forStep(metamodel)
			checkState(
				!target.steps.get(metamodel).isEmpty,
				'''You must first register a proper step in «metamodel.name» before registering a variant!'''
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
				'Please register steps for at least two metamodels!'
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
		super(emptySet)
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

	override stepFor(MetamodelDescriptor metamodel, Consumer<TestView> action) {
		checks.forStep(metamodel)
		steps += new MainStep(metamodel, action)
	}

	override inputVariantFor(MetamodelDescriptor metamodel, String name, Consumer<TestView> action) {
		checks.forVariant(metamodel, name)
		steps += new VariantStep(metamodel, action, name)
	}

	override testsThatStepsAreEquivalent() {
		checks.forFinalizing()
		
		steps.entrySet.flatMap [ entry |
			val testMetamodel = entry.key
			val testSteps = entry.value
			testSteps.indexed.map [ args |
				val testIndex = args.key
				val testStep = modifyIfNecessary(args.value)
					
				val referenceSteps = testStep.determineReferenceSteps(steps)
				var testName = '''«testMetamodel.name» «'-x' » {«FOR rd : referenceSteps.keySet SEPARATOR ', '»«rd.name»«ENDFOR»}'''
				if (testStep.name !== null) {
					testName += ''' — «testStep.name»'''
				}
				val testContext = new EquivalenceTestExtensionContext(testName, testIndex, parentContext,
					testStep.targetMetamodel)
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

	def private static <T extends MetamodelStep> operator_add(Map<MetamodelDescriptor, List<MetamodelStep>> steps, T metamodelStep) {
		steps.putIfAbsent(metamodelStep.targetMetamodel, new LinkedList());
		steps.get(metamodelStep.targetMetamodel) += metamodelStep
		return metamodelStep
	}

	private static class DependencyBuilder extends DefaultBuilderCommon implements EquivalenceTestBuilder  {
		static val mockVariantOptions = new VariantOptions() {
			override alsoCompareToMainStepOfSameMetamodel() { this }
		}

		new(Set<MetamodelDescriptor> targetMetamodels) {
			super(targetMetamodels)
		}

		override dependsOn((EquivalenceTestBuilder)=>void otherTest) {
			checks.forDependency()
			val dependencyBuilder = new DependencyBuilder(steps.keySet)
			otherTest.apply(dependencyBuilder)
			dependencySteps += dependencyBuilder.allDependencySteps
		}

		override stepFor(MetamodelDescriptor metamodel, Consumer<TestView> action) {
			checks.forStep(metamodel)
			steps += new MainStep(metamodel, action)
		}

		override inputVariantFor(MetamodelDescriptor metamodel, String name, Consumer<TestView> action) {
			checks.forVariant(metamodel, name)
			// variants are irrelevant for dependencies, so discard them
			mockVariantOptions
		}

		override testsThatStepsAreEquivalent() {
			checks.forFinalizing()
			emptyList()
		}
		
		def getAllDependencySteps() {
			checks.forFinalizing()
			steps.forEach [metamodel, steps |
				steps.map [modifyIfNecessary()].forEach[dependencySteps += it] 
			]
			return dependencySteps
		}
	}
}

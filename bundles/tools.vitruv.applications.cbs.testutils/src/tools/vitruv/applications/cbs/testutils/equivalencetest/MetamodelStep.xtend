package tools.vitruv.applications.cbs.testutils.equivalencetest

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import java.util.function.Consumer
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.testutils.TestView
import java.util.Map
import java.util.Collection
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.testutils.TestUserInteraction
import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor

package interface MetamodelStep {
	def MetamodelDescriptor getTargetMetamodel()

	def void executeIn(TestView testView)

	def String getName()

	def Map<MetamodelDescriptor, MetamodelStep> determineReferenceSteps(
		Map<MetamodelDescriptor, ? extends Collection<? extends MetamodelStep>> availableSteps)
}

@FinalFieldsConstructor
package class MainStep implements MetamodelStep {
	@Accessors
	val MetamodelDescriptor targetMetamodel
	val Consumer<TestView> action

	override executeIn(TestView testView) {
		action.accept(testView)
	}

	override getName() { null }

	override Map<MetamodelDescriptor, MetamodelStep> determineReferenceSteps(
		Map<MetamodelDescriptor, ? extends Collection<? extends MetamodelStep>> availableSteps) {
		availableSteps.filter[_, steps|!steps.isEmpty].mapValues[get(0)]
	}
}

@FinalFieldsConstructor
package class VariantStep implements MetamodelStep, EquivalenceTestBuilder.VariantOptions {
	@Accessors
	val MetamodelDescriptor targetMetamodel
	val Consumer<TestView> action
	@Accessors
	val String name
	var includeSameMetamodel = false

	override executeIn(TestView testView) {
		action.accept(testView)
	}

	override alsoCompareToMainStepOfSameMetamodel() {
		includeSameMetamodel = true
		this
	}

	override Map<MetamodelDescriptor, MetamodelStep> determineReferenceSteps(
		Map<MetamodelDescriptor, ? extends Collection<? extends MetamodelStep>> availableSteps) {
		availableSteps.filter [ metamodel, steps |
			!steps.isEmpty && (includeSameMetamodel || metamodel != targetMetamodel)
		].mapValues [
			// For other metamodels, look if there is a variant step with a matching name, otherwise use the main one
			it.filter(VariantStep).findFirst[targetMetamodel != this.targetMetamodel && this.name.contains(name)] ?: get(0)
		]
	}
}

@FinalFieldsConstructor
package class StepWithUserInteractionSetup implements MetamodelStep {
	@Delegate val MetamodelStep delegate
	val (TestUserInteraction)=>void userInteractionSetup

	override executeIn(TestView testView) {
		userInteractionSetup.apply(testView.userInteraction)
		delegate.executeIn(testView)
		testView.userInteraction.clearResponses()
	}
}

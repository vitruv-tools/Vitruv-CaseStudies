package tools.vitruv.applications.cbs.testutils.equivalencetest

import java.util.Collection
import java.util.Map
import java.util.function.Consumer
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor
import tools.vitruv.testutils.TestUserInteraction
import tools.vitruv.testutils.views.NonTransactionalTestView

package interface MetamodelStep {
	def MetamodelDescriptor getTargetMetamodel()

	def void executeIn(NonTransactionalTestView testView)

	def String getName()

	def Map<MetamodelDescriptor, MetamodelStep> determineReferenceSteps(
		Map<MetamodelDescriptor, ? extends Collection<? extends MetamodelStep>> availableSteps)
}

@FinalFieldsConstructor
package class MainStep implements MetamodelStep {
	@Accessors
	val MetamodelDescriptor targetMetamodel
	val Consumer<NonTransactionalTestView> action

	override executeIn(NonTransactionalTestView testView) {
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
	val Consumer<NonTransactionalTestView> action
	@Accessors
	val String name
	var includeSameMetamodel = false

	override executeIn(NonTransactionalTestView testView) {
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

	override executeIn(NonTransactionalTestView testView) {
		userInteractionSetup.apply(testView.userInteraction)
		delegate.executeIn(testView)
		testView.userInteraction.clearResponses()
	}
}

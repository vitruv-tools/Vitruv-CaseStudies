package tools.vitruv.applications.cbs.testutils.equivalencetest;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor;
import tools.vitruv.change.testutils.views.TestView;

class VariantStep implements MetamodelStep, EquivalenceTestBuilder.VariantOptions {
    private final MetamodelDescriptor targetMetamodel;
    private final Consumer<TestView> action;
    private final String name;
    private boolean includeSameMetamodel = false;

    VariantStep(MetamodelDescriptor targetMetamodel, Consumer<TestView> action, String name) {
        this.targetMetamodel = targetMetamodel;
        this.action = action;
        this.name = name;
    }

    @Override
    public MetamodelDescriptor getTargetMetamodel() {
        return targetMetamodel;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void executeIn(TestView testView) {
        action.accept(testView);
    }

    @Override
    public EquivalenceTestBuilder.VariantOptions alsoCompareToMainStepOfSameMetamodel() {
        includeSameMetamodel = true;
        return this;
    }

    @Override
    public Map<MetamodelDescriptor, MetamodelStep> determineReferenceSteps(
            Map<MetamodelDescriptor, ? extends Collection<? extends MetamodelStep>> availableSteps) {
        Map<MetamodelDescriptor, MetamodelStep> referenceSteps = new LinkedHashMap<>();
        availableSteps.forEach((metamodel, steps) -> {
            if (!steps.isEmpty() && (includeSameMetamodel || !metamodel.equals(targetMetamodel))) {
                referenceSteps.put(metamodel, matchingVariantOrMainStep(steps));
            }
        });
        return referenceSteps;
    }

    /**
     * For other metamodels, looks for a variant step with a matching name, otherwise uses the main one.
     */
    private MetamodelStep matchingVariantOrMainStep(Collection<? extends MetamodelStep> steps) {
        return steps.stream()
                .filter(VariantStep.class::isInstance)
                .map(VariantStep.class::cast)
                .filter(variant -> !variant.targetMetamodel.equals(targetMetamodel) && name.contains(variant.name))
                .<MetamodelStep>map(variant -> variant)
                .findFirst()
                .orElseGet(() -> steps.iterator().next());
    }
}

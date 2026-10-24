package tools.vitruv.applications.cbs.testutils.equivalencetest;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor;
import tools.vitruv.change.testutils.TestUserInteraction;
import tools.vitruv.change.testutils.views.TestView;

interface MetamodelStep {
    MetamodelDescriptor getTargetMetamodel();

    void executeIn(TestView testView);

    String getName();

    Map<MetamodelDescriptor, MetamodelStep> determineReferenceSteps(
            Map<MetamodelDescriptor, ? extends Collection<? extends MetamodelStep>> availableSteps);
}

final class MainStep implements MetamodelStep {
    private final MetamodelDescriptor targetMetamodel;
    private final Consumer<TestView> action;

    MainStep(MetamodelDescriptor targetMetamodel, Consumer<TestView> action) {
        this.targetMetamodel = targetMetamodel;
        this.action = action;
    }

    public MetamodelDescriptor getTargetMetamodel() {
        return targetMetamodel;
    }

    @Override
    public void executeIn(TestView testView) {
        action.accept(testView);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<MetamodelDescriptor, MetamodelStep> determineReferenceSteps(
            Map<MetamodelDescriptor, ? extends Collection<? extends MetamodelStep>> availableSteps) {

        // Xtend: availableSteps.filter[_, steps|!steps.isEmpty].mapValues[get(0)]
        Map<MetamodelDescriptor, MetamodelStep> result = new LinkedHashMap<>();
        for (Map.Entry<MetamodelDescriptor, ? extends Collection<? extends MetamodelStep>> e : availableSteps
                .entrySet()) {
            Collection<? extends MetamodelStep> steps = e.getValue();
            if (steps != null && !steps.isEmpty()) {
                result.put(e.getKey(), firstOf(steps));
            }
        }
        return result;
    }

    private static MetamodelStep firstOf(Collection<? extends MetamodelStep> steps) {
        Iterator<? extends MetamodelStep> it = steps.iterator();
        return it.hasNext() ? it.next() : null;
    }
}

final class VariantStep implements MetamodelStep, EquivalenceTestBuilder.VariantOptions {
    private final MetamodelDescriptor targetMetamodel;
    private final Consumer<TestView> action;
    private final String name;
    private boolean includeSameMetamodel = false;

    VariantStep(MetamodelDescriptor targetMetamodel, Consumer<TestView> action, String name) {
        this.targetMetamodel = targetMetamodel;
        this.action = action;
        this.name = name;
    }

    public MetamodelDescriptor getTargetMetamodel() {
        return targetMetamodel;
    }

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

        // Xtend:
        // availableSteps.filter [ metamodel, steps |
        // !steps.isEmpty && (includeSameMetamodel || metamodel != targetMetamodel)
        // ].mapValues [
        // it.filter(VariantStep).findFirst[
        // targetMetamodel != this.targetMetamodel && this.name.contains(name)
        // ] ?: get(0)
        // ]
        Map<MetamodelDescriptor, MetamodelStep> result = new LinkedHashMap<>();
        for (Map.Entry<MetamodelDescriptor, ? extends Collection<? extends MetamodelStep>> e : availableSteps
                .entrySet()) {
            MetamodelDescriptor meta = e.getKey();
            Collection<? extends MetamodelStep> steps = e.getValue();
            if (steps == null || steps.isEmpty())
                continue;
            if (!includeSameMetamodel && Objects.equals(meta, this.targetMetamodel))
                continue;

            // Prefer a VariantStep in that metamodel whose name is contained in this.name,
            // else the "main" (first) step.
            MetamodelStep chosen = steps.stream()
                    .filter(VariantStep.class::isInstance)
                    .map(VariantStep.class::cast)
                    .filter(v -> !Objects.equals(v.getTargetMetamodel(), this.targetMetamodel)
                            && this.name != null
                            && v.getName() != null
                            && this.name.contains(v.getName()))
                    .findFirst()
                    .map(MetamodelStep.class::cast)
                    .orElseGet(() -> firstOf(steps));

            result.put(meta, chosen);
        }
        return result;
    }

    private static MetamodelStep firstOf(Collection<? extends MetamodelStep> steps) {
        return steps.stream().findFirst().orElse(null);
    }
}

final class StepWithUserInteractionSetup implements MetamodelStep {
    private final MetamodelStep delegate;
    private final Consumer<TestUserInteraction> userInteractionSetup;

    StepWithUserInteractionSetup(MetamodelStep delegate, Consumer<TestUserInteraction> userInteractionSetup) {
        this.delegate = delegate;
        this.userInteractionSetup = userInteractionSetup;
    }

    @Override
    public MetamodelDescriptor getTargetMetamodel() {
        return delegate.getTargetMetamodel();
    }

    @Override
    public void executeIn(TestView testView) {
        // Xtend:
        // userInteractionSetup.apply(testView.userInteraction)
        // delegate.executeIn(testView)
        // testView.userInteraction.clearResponses()
        TestUserInteraction ui = testView.getUserInteraction();
        userInteractionSetup.accept(ui);
        delegate.executeIn(testView);
        ui.clearResponses();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public Map<MetamodelDescriptor, MetamodelStep> determineReferenceSteps(
            Map<MetamodelDescriptor, ? extends Collection<? extends MetamodelStep>> availableSteps) {
        return delegate.determineReferenceSteps(availableSteps);
    }
}

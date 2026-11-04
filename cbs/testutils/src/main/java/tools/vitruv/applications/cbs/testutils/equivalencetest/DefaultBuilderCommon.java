package tools.vitruv.applications.cbs.testutils.equivalencetest;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static java.util.Comparator.comparing;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;

import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor;
import tools.vitruv.change.testutils.TestUserInteraction;

abstract class DefaultBuilderCommon implements EquivalenceTestBuilder {
    /* both maps always contain lists for all metamodels */
    protected final Map<MetamodelDescriptor, List<MetamodelStep>> dependencySteps;
    protected final Map<MetamodelDescriptor, List<MetamodelStep>> steps;
    protected Consumer<TestUserInteraction> userInteractionConfiguration = null;
    protected final Checks checks = new Checks(this);

    protected DefaultBuilderCommon(Set<MetamodelDescriptor> targetMetamodels) {
        this.dependencySteps = mapForMetamodels(targetMetamodels);
        this.steps = mapForMetamodels(targetMetamodels);
    }

    private static Map<MetamodelDescriptor, List<MetamodelStep>> mapForMetamodels(
            Set<MetamodelDescriptor> metamodels) {
        Map<MetamodelDescriptor, List<MetamodelStep>> result = new TreeMap<>(comparing(MetamodelDescriptor::getName));
        for (MetamodelDescriptor metamodel : metamodels) {
            result.put(metamodel, new LinkedList<>());
        }
        return result;
    }

    protected MetamodelStep modifyIfNecessary(MetamodelStep metamodelStep) {
        if (userInteractionConfiguration != null) {
            return new StepWithUserInteractionSetup(metamodelStep, userInteractionConfiguration);
        } else {
            return metamodelStep;
        }
    }

    @Override
    public void userInteractions(Consumer<TestUserInteraction> interactionsProvider) {
        checks.forUserInteractionConfiguration();
        this.userInteractionConfiguration = interactionsProvider;
    }

    /**
     * Adds the step to the list of its target metamodel in {@code steps}.
     */
    protected static <T extends MetamodelStep> T addStep(Map<MetamodelDescriptor, List<MetamodelStep>> steps,
            T metamodelStep) {
        steps.computeIfAbsent(metamodelStep.getTargetMetamodel(), metamodel -> new LinkedList<>()).add(metamodelStep);
        return metamodelStep;
    }

    protected static class Checks {
        private final DefaultBuilderCommon target;
        private boolean active = true;

        Checks(DefaultBuilderCommon target) {
            this.target = target;
        }

        void forStep(MetamodelDescriptor metamodel) {
            checkActive();
        }

        void forVariant(MetamodelDescriptor metamodel, String name) {
            forStep(metamodel);
            checkState(!target.steps.get(metamodel).isEmpty(),
                    "You must first register a proper step in %s before registering a variant!", metamodel.getName());
            checkArgument(name != null && !name.isBlank(), "You must provide a name for a variant!");
        }

        void forDependency() {
            checkActive();
        }

        private void checkActive() {
            checkState(active, "You have already requested the tests from this builder. No more steps can be added.");
        }

        void forUserInteractionConfiguration() {
            checkState(target.userInteractionConfiguration == null, "User interactions have already been configured!");
        }

        void forFinalizing() {
            long metamodelsWithSteps = target.steps.values().stream().filter(stepList -> !stepList.isEmpty()).count();
            checkState(metamodelsWithSteps >= 2, "Please register steps for at least two metamodels!");
            active = false;
        }
    }
}

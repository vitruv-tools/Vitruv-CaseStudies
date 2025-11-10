package tools.vitruv.applications.cbs.testutils.equivalencetest;

import java.util.*;
import java.util.function.Consumer;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static java.util.Comparator.comparing;

import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor;
import tools.vitruv.change.testutils.TestUserInteraction;
import tools.vitruv.change.testutils.views.TestView;

public abstract class DefaultBuilderCommon implements EquivalenceTestBuilder {
    protected final Map<MetamodelDescriptor, List<MetamodelStep>> dependencySteps;
    protected final Map<MetamodelDescriptor, List<MetamodelStep>> steps;
    protected Consumer<TestUserInteraction> userInteractionConfiguration = null;
    protected final Checks checks;

    protected DefaultBuilderCommon(Set<MetamodelDescriptor> targetMetamodels) {
        this.dependencySteps = mapForMetamodels(targetMetamodels);
        this.steps = mapForMetamodels(targetMetamodels);
        this.checks = new Checks(this);
    }

    private static Map<MetamodelDescriptor, List<MetamodelStep>> mapForMetamodels(Set<MetamodelDescriptor> metamodels) {
        TreeMap<MetamodelDescriptor, List<MetamodelStep>> result = new TreeMap<>(
                comparing(MetamodelDescriptor::getName));
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

    protected static class Checks {
        private final DefaultBuilderCommon target;
        private boolean active = true;

        public Checks(DefaultBuilderCommon target) {
            this.target = target;
        }

        public void forStep(MetamodelDescriptor metamodel) {
            checkActive();
        }

        public void forVariant(MetamodelDescriptor metamodel, String name) {
            forStep(metamodel);
            checkState(
                    !target.steps.get(metamodel).isEmpty(),
                    String.format("You must first register a proper step in %s before registering a variant!",
                            metamodel.getName()));
            checkArgument(name != null && !name.isBlank(), "You must provide a name for a variant!");
        }

        public void forDependency() {
            checkActive();
        }

        private void checkActive() {
            checkState(
                    active,
                    "You have already requested the tests from this builder. No more steps can be added.");
        }

        public void forUserInteractionConfiguration() {
            checkState(
                    target.userInteractionConfiguration == null,
                    "User interactions have already been configured!");
        }

        public void forFinalizing() {
            checkState(
                    target.steps.entrySet().stream()
                            .filter(entry -> !entry.getValue().isEmpty())
                            .count() >= 2,
                    "Please register steps for at least two metamodels!");
            active = false;
        }
    }
}
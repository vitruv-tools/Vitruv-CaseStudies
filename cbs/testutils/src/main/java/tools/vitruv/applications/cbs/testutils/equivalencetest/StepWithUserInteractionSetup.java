package tools.vitruv.applications.cbs.testutils.equivalencetest;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor;
import tools.vitruv.change.testutils.TestUserInteraction;
import tools.vitruv.change.testutils.views.TestView;

/**
 * Configures the user interactions before executing the wrapped step and clears them afterwards.
 */
class StepWithUserInteractionSetup implements MetamodelStep {
    private final MetamodelStep delegate;
    private final Consumer<TestUserInteraction> userInteractionSetup;

    StepWithUserInteractionSetup(MetamodelStep delegate, Consumer<TestUserInteraction> userInteractionSetup) {
        this.delegate = delegate;
        this.userInteractionSetup = userInteractionSetup;
    }

    @Override
    public void executeIn(TestView testView) {
        userInteractionSetup.accept(testView.getUserInteraction());
        delegate.executeIn(testView);
        testView.getUserInteraction().clearResponses();
    }

    @Override
    public MetamodelDescriptor getTargetMetamodel() {
        return delegate.getTargetMetamodel();
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

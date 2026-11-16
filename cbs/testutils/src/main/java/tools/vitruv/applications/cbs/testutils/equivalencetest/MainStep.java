package tools.vitruv.applications.cbs.testutils.equivalencetest;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor;
import tools.vitruv.change.testutils.views.TestView;

class MainStep implements MetamodelStep {
    private final MetamodelDescriptor targetMetamodel;
    private final Consumer<TestView> action;

    MainStep(MetamodelDescriptor targetMetamodel, Consumer<TestView> action) {
        this.targetMetamodel = targetMetamodel;
        this.action = action;
    }

    @Override
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
        Map<MetamodelDescriptor, MetamodelStep> referenceSteps = new LinkedHashMap<>();
        availableSteps.forEach((metamodel, steps) -> {
            if (!steps.isEmpty()) {
                referenceSteps.put(metamodel, steps.iterator().next());
            }
        });
        return referenceSteps;
    }
}

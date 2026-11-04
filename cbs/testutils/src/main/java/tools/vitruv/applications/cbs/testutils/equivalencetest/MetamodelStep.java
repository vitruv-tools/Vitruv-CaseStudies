package tools.vitruv.applications.cbs.testutils.equivalencetest;

import java.util.Collection;
import java.util.Map;

import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor;
import tools.vitruv.change.testutils.views.TestView;

interface MetamodelStep {
    MetamodelDescriptor getTargetMetamodel();

    void executeIn(TestView testView);

    String getName();

    Map<MetamodelDescriptor, MetamodelStep> determineReferenceSteps(
            Map<MetamodelDescriptor, ? extends Collection<? extends MetamodelStep>> availableSteps);
}

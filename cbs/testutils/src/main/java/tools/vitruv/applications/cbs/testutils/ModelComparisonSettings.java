package tools.vitruv.applications.cbs.testutils;

import java.util.Collections;
import java.util.List;

import tools.vitruv.change.testutils.matchers.ModelDeepEqualityOption;

public interface ModelComparisonSettings {
    ModelComparisonSettings NONE = metamodel -> Collections.emptyList();

    List<? extends ModelDeepEqualityOption> getEqualityOptionsForMetamodel(MetamodelDescriptor metamodel);
}
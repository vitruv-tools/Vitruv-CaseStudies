package tools.vitruv.applications.cbs.testutils;

import java.util.List;

import tools.vitruv.change.testutils.matchers.ModelDeepEqualityOption;

public interface ModelComparisonSettings {
    ModelComparisonSettings NONE = metamodel -> List.of();

    List<? extends ModelDeepEqualityOption> getEqualityOptionsForMetamodel(MetamodelDescriptor metamodel);
}

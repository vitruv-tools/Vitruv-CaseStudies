package tools.vitruv.applications.cbs.testutils

import java.util.List
import tools.vitruv.change.testutils.matchers.ModelDeepEqualityOption

interface ModelComparisonSettings {
	public static val ModelComparisonSettings NONE = [emptyList()]

	def List<? extends ModelDeepEqualityOption> getEqualityOptionsForMetamodel(MetamodelDescriptor metamodel)
}

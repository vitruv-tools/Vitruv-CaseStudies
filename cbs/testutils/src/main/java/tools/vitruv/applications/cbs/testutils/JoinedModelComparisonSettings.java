package tools.vitruv.applications.cbs.testutils;

import java.util.List;
import tools.vitruv.change.testutils.matchers.ModelDeepEqualityOption;

public class JoinedModelComparisonSettings implements ModelComparisonSettings {
	private final List<ModelComparisonSettings> settings;

	public JoinedModelComparisonSettings(List<ModelComparisonSettings> settings) {
		this.settings = settings;
	}

	@Override
	public List<? extends ModelDeepEqualityOption> getEqualityOptionsForMetamodel(MetamodelDescriptor metamodel) {
		return settings.stream()
				.<ModelDeepEqualityOption>flatMap(setting -> setting.getEqualityOptionsForMetamodel(metamodel).stream())
				.toList();
	}
}

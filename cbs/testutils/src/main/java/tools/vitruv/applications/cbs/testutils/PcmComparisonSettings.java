package tools.vitruv.applications.cbs.testutils;

import static de.uka.ipd.sdq.identifier.IdentifierPackage.Literals.IDENTIFIER__ID;
import static tools.vitruv.applications.cbs.testutils.PcmCreators.pcm;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.ignoringFeatures;

import java.util.List;
import java.util.Objects;
import tools.vitruv.change.testutils.matchers.ModelDeepEqualityOption;

public class PcmComparisonSettings implements ModelComparisonSettings {
	@Override
	public List<? extends ModelDeepEqualityOption> getEqualityOptionsForMetamodel(MetamodelDescriptor metamodel) {
		if (Objects.equals(metamodel, pcm.getMetamodel())) {
			return List.of(ignoringFeatures(IDENTIFIER__ID));
		}
		return List.of();
	}
}

package tools.vitruv.applications.cbs.testutils;

import static de.uka.ipd.sdq.identifier.IdentifierPackage.Literals.IDENTIFIER__ID;
import static tools.vitruv.applications.cbs.testutils.PcmCreators.pcm;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.ignoringFeatures;

import java.util.List;

import tools.vitruv.change.testutils.matchers.ModelDeepEqualityOption;

/**
 * Ignores the generated IDs of PCM elements when comparing them.
 */
public class PcmComparisonSettings implements ModelComparisonSettings {
    @Override
    public List<? extends ModelDeepEqualityOption> getEqualityOptionsForMetamodel(MetamodelDescriptor metamodel) {
        if (metamodel.equals(pcm.getMetamodel())) {
            return List.of(ignoringFeatures(IDENTIFIER__ID));
        }
        return List.of();
    }
}

package tools.vitruv.applications.cbs.testutils;

import static org.emftext.commons.layout.LayoutPackage.Literals.LAYOUT_INFORMATION;
import static org.emftext.commons.layout.LayoutPackage.Literals.LAYOUT_INFORMATION__VISIBLE_TOKEN_TEXT;
import static tools.vitruv.applications.cbs.testutils.JavaCreators.java;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.ignoringFeatures;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.ignoringFeaturesOfType;

import java.util.List;

import tools.vitruv.change.testutils.matchers.ModelDeepEqualityOption;

/**
 * Ignores the layout information of JaMoPP models when comparing them.
 */
public class JamoppComparisonSettings implements ModelComparisonSettings {
    @Override
    public List<? extends ModelDeepEqualityOption> getEqualityOptionsForMetamodel(MetamodelDescriptor metamodel) {
        if (metamodel.equals(java.getMetamodel())) {
            return List.of(ignoringFeaturesOfType(LAYOUT_INFORMATION),
                    ignoringFeatures(LAYOUT_INFORMATION__VISIBLE_TOKEN_TEXT));
        }
        return List.of();
    }
}

package tools.vitruv.applications.cbs.testutils

import static tools.vitruv.applications.cbs.testutils.JavaCreators.java
import static tools.vitruv.change.testutils.matchers.ModelMatchers.ignoringFeaturesOfType
import static org.emftext.commons.layout.LayoutPackage.Literals.*
import java.util.List
import org.emftext.commons.layout.LayoutPackage
import static tools.vitruv.change.testutils.matchers.ModelMatchers.ignoringFeatures

class JamoppComparisonSettings implements ModelComparisonSettings {
	override getEqualityOptionsForMetamodel(MetamodelDescriptor metamodel) {
		if(metamodel == java.metamodel) List.of(ignoringFeaturesOfType(LAYOUT_INFORMATION),
			ignoringFeatures(#[LayoutPackage.Literals.LAYOUT_INFORMATION__VISIBLE_TOKEN_TEXT])) else emptyList
	}
}

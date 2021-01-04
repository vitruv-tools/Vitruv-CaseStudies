package tools.vitruv.applications.cbs.testutils

import static tools.vitruv.applications.cbs.testutils.JavaCreators.java
import static tools.vitruv.testutils.matchers.ModelMatchers.ignoringFeaturesOfType
import static org.emftext.commons.layout.LayoutPackage.Literals.*
import tools.vitruv.framework.domains.VitruvDomain
import java.util.List

class JamoppComparisonSettings implements ModelComparisonSettings {
	override getEqualityFeatureFiltersForDomain(VitruvDomain domain) {
		if (domain == java.domain) List.of(ignoringFeaturesOfType(LAYOUT_INFORMATION)) else emptyList
	}
}

package tools.vitruv.applications.cbs.testutils

import java.util.List
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.testutils.matchers.EqualityFeatureFilter

interface ModelComparisonSettings {
	public static val NONE = new ModelComparisonSettings {
		override getEqualityFeatureFiltersForDomain(VitruvDomain domain) { emptyList }
	}

	def List<EqualityFeatureFilter> getEqualityFeatureFiltersForDomain(VitruvDomain domain)
}

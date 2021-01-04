package tools.vitruv.applications.cbs.testutils

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import java.util.List
import tools.vitruv.framework.domains.VitruvDomain

@FinalFieldsConstructor
class JoinedModelComparisonSettings implements ModelComparisonSettings {
	val List<ModelComparisonSettings> settings

	override getEqualityFeatureFiltersForDomain(VitruvDomain domain) {
		settings.flatMap[getEqualityFeatureFiltersForDomain(domain)].toList
	}
}

package tools.vitruv.applications.cbs.testutils

import tools.vitruv.framework.domains.VitruvDomain
import static tools.vitruv.applications.cbs.testutils.PcmCreators.pcm
import static tools.vitruv.testutils.matchers.ModelMatchers.ignoringFeatures
import static de.uka.ipd.sdq.identifier.IdentifierPackage.Literals.*
import java.util.List

class PcmComparisonSettings implements ModelComparisonSettings {

	override getEqualityOptionsForDomain(VitruvDomain domain) {
		if (domain == pcm.domain) List.of(ignoringFeatures(IDENTIFIER__ID)) else emptyList
	}
}

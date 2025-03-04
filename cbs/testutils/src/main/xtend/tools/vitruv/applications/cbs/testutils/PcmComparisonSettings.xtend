package tools.vitruv.applications.cbs.testutils

import static tools.vitruv.applications.cbs.testutils.PcmCreators.pcm
import static tools.vitruv.change.testutils.matchers.ModelMatchers.ignoringFeatures
import static de.uka.ipd.sdq.identifier.IdentifierPackage.Literals.*
import java.util.List

class PcmComparisonSettings implements ModelComparisonSettings {

	override getEqualityOptionsForMetamodel(MetamodelDescriptor metamodel) {
		if (metamodel == pcm.metamodel) List.of(ignoringFeatures(IDENTIFIER__ID)) else emptyList
	}
}

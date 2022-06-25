package tools.vitruv.applications.cbs.testutils

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import java.util.List
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

@FinalFieldsConstructor
class JoinedModelComparisonSettings implements ModelComparisonSettings {
	val List<ModelComparisonSettings> settings

	override getEqualityOptionsForMetamodel(MetamodelDescriptor metamodel) {
		settings.flatMapFixed [getEqualityOptionsForMetamodel(metamodel)]
	}
}

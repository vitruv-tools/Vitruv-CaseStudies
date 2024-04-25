package tools.vitruv.applications.viewfilter.utils

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.framework.views.View
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.system.System
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne

@Utility
class PcmQueryUtil {

	static def getPcmSystem(View view) {
		view.getRootObjects(System)
	}

	static def getPcmRepository(View view) {
		view.getRootObjects(Repository)
	}

	static def claimPcmRepository(View view, String packageName) {
		var list = getPcmRepository(view).iterator.filter[it.entityName.equals(packageName)].toList
		getPcmRepository(view).iterator.filter[it.entityName.equals(packageName)].toIterable.claimOne
	}

	static def claimPcmSystem(View view) {
		getPcmSystem(view).claimOne
	}
}
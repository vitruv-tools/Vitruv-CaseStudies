package tools.vitruv.applications.pcmumlclass.tests

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.framework.views.View
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.system.System

@Utility
class PcmQueryUtil {
	
	static def getPcmSystem(View view) {
		view.getRootObjects(System)
	}
	
	static def getPcmRepository(View view) {
		view.getRootObjects(Repository)
	}
	
	//nötig?
	static def claimPcmRepository(View view, String packageName) {
		getPcmRepository(view).iterator.next
	}
	
	static def claimPcmSystem(View view) {
		getPcmSystem(view).iterator.next
	}
}
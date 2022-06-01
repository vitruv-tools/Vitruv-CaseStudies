package tools.vitruv.applications.pcmumlclass.tests

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.framework.views.View
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.system.System
import org.eclipse.emf.ecore.EObject
import org.palladiosimulator.pcm.repository.Repository

import static org.junit.jupiter.api.Assertions.assertEquals

@Utility
class PcmQueryUtil {
	
	static def getPcmSystem(View view) {
		view.getRootObjects(System)
	}
	
	static def getPcmRepository(View view) {
		view.getRootObjects(Repository)
	}
	
	static def claimPcmRepository(View view, String packageName) {
		// primitiveDataTypes repository is not desired as output
		assertEquals(getPcmRepository(view).iterator.findFirst[it.entityName.equals(packageName)], 
			getPcmRepository(view).iterator.findLast[it.entityName.equals(packageName)], "More than one repository was created."
		)
		getPcmRepository(view).iterator.findFirst[it.entityName.equals(packageName)]
	}
	
	static def claimPcmSystem(View view) {
		getPcmSystem(view).iterator.next
	}
}
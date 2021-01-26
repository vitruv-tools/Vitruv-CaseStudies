package tools.vitruv.applications.cbs.commonalities.tests.pcm

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.cbs.commonalities.tests.TestConstants.PCM

@Utility
class PcmTestModelHelper {

	static def newPcmRepository() {
		return RepositoryFactory.eINSTANCE.createRepository => [
			entityName = PCM.REPOSITORY_NAME
		]
	}
}

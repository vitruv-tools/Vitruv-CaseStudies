package tools.vitruv.applications.cbs.commonalities.tests.util.pcm

import org.eclipse.emf.ecore.EObject
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelTester
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestHelper

class PcmModelTester extends DomainModelTester {

	val extension PcmTestHelper pcmTestHelper = new PcmTestHelper(vitruvApplicationTestAdapter)

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override createAndSynchronizeModel(EObject rootObject) {
		switch (rootObject) {
			Repository: {
				rootObject.createAndSynchronizePcmRepository
			}
			default: {
				throw new IllegalStateException("Unhandled PCM root object: " + rootObject)
			}
		}
	}

	override assertModelExists(EObject rootObject) {
		switch (rootObject) {
			Repository: {
				rootObject.assertPcmRepositoryExists
			}
			default: {
				throw new IllegalStateException("Unhandled PCM root object: " + rootObject)
			}
		}
	}
}

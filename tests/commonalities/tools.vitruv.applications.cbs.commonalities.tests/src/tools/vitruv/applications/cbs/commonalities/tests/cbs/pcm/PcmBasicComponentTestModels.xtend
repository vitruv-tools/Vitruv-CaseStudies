package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm

import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractBasicComponentTest
import tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.*

class PcmBasicComponentTestModels extends PcmTestModelsBase implements AbstractBasicComponentTest.DomainModels {

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override emptyBasicComponentCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				components__Repository += RepositoryFactory.eINSTANCE.createBasicComponent => [
					entityName = COMPONENT_NAME
				]
			]
			return #[
				pcmRepository
			]
		]
	}
}

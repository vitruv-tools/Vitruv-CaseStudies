package tools.vitruv.applications.cbs.commonalities.tests.cbs

import java.util.List
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmMediaStoreTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlMediaStoreTestModels_1_Packages
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlMediaStoreTestModels_2_ClassAndInterfaceStubs
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlMediaStoreTestModels_3_CompositeDataTypes
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlMediaStoreTestModels_4_OperationSignatures
import tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.runner.XtextParametersRunnerFactory

@RunWith(Parameterized)
@Parameterized.UseParametersRunnerFactory(XtextParametersRunnerFactory)
class MediaStoreTest extends CBSCommonalitiesExecutionTest {

	@Parameterized.Parameters(name='{0} to {1}')
	static def List<Object[]> testParameters() {
		val pcmModels = new PcmTestModelsProvider [new PcmMediaStoreTestModels(it)]
		val umlModels1 = new DomainModelsProvider('UML (1_Packages)') [
			new UmlMediaStoreTestModels_1_Packages(it)
		]
		val umlModels2 = new DomainModelsProvider('UML (2_ClassAndInterfaceStubs)') [
			new UmlMediaStoreTestModels_2_ClassAndInterfaceStubs(it)
		]
		val umlModels3 = new DomainModelsProvider('UML (3_CompositeDataTypes)') [
			new UmlMediaStoreTestModels_3_CompositeDataTypes(it)
		]
		val umlModels4 = new DomainModelsProvider('UML (4_OperationSignatures)') [
			new UmlMediaStoreTestModels_4_OperationSignatures(it)
		]
		return #[
			#[pcmModels, umlModels1],
			#[pcmModels, umlModels2],
			#[pcmModels, umlModels3],
			#[pcmModels, umlModels4]
		]
	}

	interface DomainModels {

		def DomainModel mediaStoreCreation()
	}

	val DomainModels sourceModels
	val DomainModels targetModels

	new(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		this.sourceModels = sourceModelsProvider.getModels(vitruvApplicationTestAdapter)
		this.targetModels = targetModelsProvider.getModels(vitruvApplicationTestAdapter)
	}

	@Test
	def void mediaStoreCreation() {
		sourceModels.mediaStoreCreation.createAndSynchronize()
		targetModels.mediaStoreCreation.check()
	}
}

package tools.vitruv.applications.cbs.commonalities.tests.cbs

import java.util.List
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmMediaStoreTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlMediaStoreTestModels_1_Packages
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlMediaStoreTestModels_2_ClassAndInterfaceStubs
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlMediaStoreTestModels_3_CompositeDataTypes
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlMediaStoreTestModels_4_OperationSignatures
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsProvider
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlMediaStoreTestModels_5_ProvidedRoles

class MediaStoreTest extends CBSCommonalitiesExecutionTest {

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
		val umlModels5 = new DomainModelsProvider('UML (5_ProvidedRoles)') [
			new UmlMediaStoreTestModels_5_ProvidedRoles(it)
		]
		return #[
			#[pcmModels, umlModels1],
			#[pcmModels, umlModels2],
			#[pcmModels, umlModels3],
			#[pcmModels, umlModels4],
			#[pcmModels, umlModels5]
		]
	}

	interface DomainModels {

		def DomainModel mediaStoreCreation()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void mediaStoreCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.mediaStoreCreation.createAndSynchronize()
		targetModelsProvider.getModels.mediaStoreCreation.check()
	}
}

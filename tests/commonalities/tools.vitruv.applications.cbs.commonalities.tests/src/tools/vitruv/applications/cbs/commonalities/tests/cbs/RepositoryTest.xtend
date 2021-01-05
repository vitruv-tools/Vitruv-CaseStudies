package tools.vitruv.applications.cbs.commonalities.tests.cbs

import java.util.List
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaRepositoryTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmRepositoryTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlRepositoryTestModels
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider

class RepositoryTest extends CBSCommonalitiesExecutionTest {

	static def List<Object[]> testParameters() {
		val domainModelsProviders = #[
			new PcmTestModelsProvider [new PcmRepositoryTestModels(it)],
			new UmlTestModelsProvider [new UmlRepositoryTestModels(it)],
			new JavaTestModelsProvider [new JavaRepositoryTestModels(it)]
		]
		return domainModelsProviders.orderedPairs
	}

	interface DomainModels {

		def DomainModel emptyRepositoryCreation()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void emptyRepositoryCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.emptyRepositoryCreation.createAndSynchronize()
		targetModelsProvider.getModels.emptyRepositoryCreation.check()
	}

	// TODO repository renaming
}

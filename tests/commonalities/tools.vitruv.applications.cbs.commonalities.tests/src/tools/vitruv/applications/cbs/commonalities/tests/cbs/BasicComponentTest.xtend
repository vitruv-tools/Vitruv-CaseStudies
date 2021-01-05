package tools.vitruv.applications.cbs.commonalities.tests.cbs

import java.util.List
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaBasicComponentTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmBasicComponentTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlBasicComponentTestModels
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider

class BasicComponentTest extends CBSCommonalitiesExecutionTest {

	static def List<Object[]> testParameters() {
		val domainModelsProviders = #[
			new PcmTestModelsProvider [new PcmBasicComponentTestModels(it)],
			new UmlTestModelsProvider [new UmlBasicComponentTestModels(it)],
			new JavaTestModelsProvider [new JavaBasicComponentTestModels(it)]
		]
		return domainModelsProviders.orderedPairs
	}

	interface DomainModels {

		static val COMPONENT_NAME = 'SomeBasicComponent'

		def DomainModel emptyBasicComponentCreation()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void emptyBasicComponentCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.emptyBasicComponentCreation.createAndSynchronize()
		targetModelsProvider.getModels.emptyBasicComponentCreation.check()
	}

	// TODO component renaming
}

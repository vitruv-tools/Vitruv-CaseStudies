package tools.vitruv.applications.cbs.commonalities.tests.cbs

import java.util.List
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaComponentInterfaceTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmComponentInterfaceTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlComponentInterfaceTestModels
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider

class ComponentInterfaceTest extends CBSCommonalitiesExecutionTest {

	static def List<Object[]> testParameters() {
		val domainModelsProviders = #[
			new PcmTestModelsProvider [new PcmComponentInterfaceTestModels(it)],
			new UmlTestModelsProvider [new UmlComponentInterfaceTestModels(it)],
			new JavaTestModelsProvider [new JavaComponentInterfaceTestModels(it)]
		]
		return domainModelsProviders.orderedPairs
	}

	interface DomainModels {

		static val INTERFACE_NAME = 'SomeInterface'

		def DomainModel emptyComponentInterfaceCreation()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void emptyComponentInterfaceCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.emptyComponentInterfaceCreation.createAndSynchronize()
		targetModelsProvider.getModels.emptyComponentInterfaceCreation.check()
	}

	// TODO interface renaming
}

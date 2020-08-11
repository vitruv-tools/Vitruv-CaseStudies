package tools.vitruv.applications.cbs.commonalities.tests.cbs

import java.util.List
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaComponentInterfaceTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmComponentInterfaceTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlComponentInterfaceTestModels
import tools.vitruv.applications.cbs.commonalities.tests.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.runner.XtextParametersRunnerFactory

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.*

@RunWith(Parameterized)
@Parameterized.UseParametersRunnerFactory(XtextParametersRunnerFactory)
class ComponentInterfaceTest extends CBSCommonalitiesExecutionTest {

	@Parameterized.Parameters(name='{0} to {1}')
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

	val DomainModels sourceModels
	val DomainModels targetModels

	new(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		this.sourceModels = sourceModelsProvider.getModels(vitruvApplicationTestAdapter)
		this.targetModels = targetModelsProvider.getModels(vitruvApplicationTestAdapter)
	}

	@Test
	def void emptyComponentInterfaceCreation() {
		sourceModels.emptyComponentInterfaceCreation.createAndSynchronize()
		targetModels.emptyComponentInterfaceCreation.check()
	}

	// TODO interface renaming
}

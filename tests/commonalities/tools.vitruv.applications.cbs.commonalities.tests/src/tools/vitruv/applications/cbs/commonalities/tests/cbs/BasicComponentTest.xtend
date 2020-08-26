package tools.vitruv.applications.cbs.commonalities.tests.cbs

import java.util.List
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaBasicComponentTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmBasicComponentTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlBasicComponentTestModels
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.runner.XtextParametersRunnerFactory
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.*

@RunWith(Parameterized)
@Parameterized.UseParametersRunnerFactory(XtextParametersRunnerFactory)
class BasicComponentTest extends CBSCommonalitiesExecutionTest {

	@Parameterized.Parameters(name='{0} to {1}')
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

	val DomainModels sourceModels
	val DomainModels targetModels

	new(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		this.sourceModels = sourceModelsProvider.getModels(vitruvApplicationTestAdapter)
		this.targetModels = targetModelsProvider.getModels(vitruvApplicationTestAdapter)
	}

	@Test
	def void emptyBasicComponentCreation() {
		sourceModels.emptyBasicComponentCreation.createAndSynchronize()
		targetModels.emptyBasicComponentCreation.check()
	}

	// TODO component renaming
}

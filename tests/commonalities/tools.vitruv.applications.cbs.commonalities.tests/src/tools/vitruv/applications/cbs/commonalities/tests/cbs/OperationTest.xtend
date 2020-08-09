package tools.vitruv.applications.cbs.commonalities.tests.cbs

import java.util.List
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaOperationTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmOperationTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlOperationTestModels
import tools.vitruv.applications.cbs.commonalities.tests.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.runner.XtextParametersRunnerFactory

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

@RunWith(Parameterized)
@Parameterized.UseParametersRunnerFactory(XtextParametersRunnerFactory)
class OperationTest extends CBSCommonalitiesExecutionTest {

	@Parameterized.Parameters(name='{0} to {1}')
	static def List<Object[]> testParameters() {
		val domainModelsProviders = #[
			new PcmTestModelsProvider [new PcmOperationTestModels(it)],
			new UmlTestModelsProvider [new UmlOperationTestModels(it)],
			new JavaTestModelsProvider [new JavaOperationTestModels(it)]
		]
		return domainModelsProviders.toListOfPairs(true)
	}

	interface DomainModels {

		static val INTERFACE_NAME = 'SomeInterface'
		static val OPERATION_NAME = 'SomeOperation'

		def DomainModel emptyOperationCreation()
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
		sourceModels.emptyOperationCreation.createAndSynchronize()
		targetModels.emptyOperationCreation.check()
	}

	// TODO renaming
}

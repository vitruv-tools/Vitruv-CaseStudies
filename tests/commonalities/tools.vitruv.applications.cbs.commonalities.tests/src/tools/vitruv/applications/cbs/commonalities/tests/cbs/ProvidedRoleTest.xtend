package tools.vitruv.applications.cbs.commonalities.tests.cbs

import java.util.List
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaProvidedRoleTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmProvidedRoleTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlProvidedRoleTestModels
import tools.vitruv.applications.cbs.commonalities.tests.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.runner.XtextParametersRunnerFactory

@RunWith(Parameterized)
@Parameterized.UseParametersRunnerFactory(XtextParametersRunnerFactory)
class ProvidedRoleTest extends CBSCommonalitiesExecutionTest {

	@Parameterized.Parameters(name='{0} to {1}')
	static def List<Object[]> testParameters() {
		val pcmModels = new PcmTestModelsProvider [new PcmProvidedRoleTestModels(it)]
		val umlModels = new UmlTestModelsProvider [new UmlProvidedRoleTestModels(it)]
		val javaModels = new JavaTestModelsProvider [new JavaProvidedRoleTestModels(it)]
		// TODO: The PCM/UML -> Java combinations do not work yet. The issue is that the inserted OperationProvidedRole
		// is initially empty (has no reference to any provided interface yet) and we do not react to changes of the
		// provided interface currently (since this is handled internally of the attribute mapping operator).
//		val domainModelsProviders = #[
//			new PcmTestModelsProvider [new PcmProvidedRoleTestModels(it)],
//			new UmlTestModelsProvider [new UmlProvidedRoleTestModels(it)],
//			new JavaTestModelsProvider [new JavaProvidedRoleTestModels(it)]
//		]
//		return domainModelsProviders.toListOfPairs(true)
		return #[
			#[pcmModels, umlModels],
			#[umlModels, pcmModels],
			#[javaModels, pcmModels],
			#[javaModels, umlModels]
		]
	}

	interface DomainModels {

		static val COMPONENT_NAME = 'SomeComponent'
		static val INTERFACE_1_NAME = 'Interface1'
		static val INTERFACE_2_NAME = 'Interface2'

		def DomainModel componentWithProvidedRoleCreation()

		def DomainModel componentWithMultipleProvidedRolesCreation()
	}

	val DomainModels sourceModels
	val DomainModels targetModels

	new(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		this.sourceModels = sourceModelsProvider.getModels(vitruvApplicationTestAdapter)
		this.targetModels = targetModelsProvider.getModels(vitruvApplicationTestAdapter)
	}

	@Test
	def void componentWithProvidedRoleCreation() {
		debugReactions
		sourceModels.componentWithProvidedRoleCreation.createAndSynchronize()
		targetModels.componentWithProvidedRoleCreation.check()
	}

	@Test
	def void componentWithMultipleProvidedRolesCreation() {
		sourceModels.componentWithMultipleProvidedRolesCreation.createAndSynchronize()
		targetModels.componentWithMultipleProvidedRolesCreation.check()
	}
}

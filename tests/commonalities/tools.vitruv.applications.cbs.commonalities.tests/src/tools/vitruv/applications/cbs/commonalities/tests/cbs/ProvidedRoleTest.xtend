package tools.vitruv.applications.cbs.commonalities.tests.cbs

import java.util.List
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaProvidedRoleTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmProvidedRoleTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlProvidedRoleTestModels
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider

class ProvidedRoleTest extends CBSCommonalitiesExecutionTest {

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
//		return domainModelsProviders.orderedPairs
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

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void componentWithProvidedRoleCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.componentWithProvidedRoleCreation.createAndSynchronize()
		targetModelsProvider.getModels.componentWithProvidedRoleCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void componentWithMultipleProvidedRolesCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.componentWithMultipleProvidedRolesCreation.createAndSynchronize()
		targetModelsProvider.getModels.componentWithMultipleProvidedRolesCreation.check()
	}
}

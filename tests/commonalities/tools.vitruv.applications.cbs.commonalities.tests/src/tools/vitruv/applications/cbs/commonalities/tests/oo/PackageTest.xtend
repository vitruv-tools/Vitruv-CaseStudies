package tools.vitruv.applications.cbs.commonalities.tests.oo

import java.util.List
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaPackageTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlPackageTestModels
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.runner.XtextParametersRunnerFactory
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.*

@RunWith(Parameterized)
@Parameterized.UseParametersRunnerFactory(XtextParametersRunnerFactory)
class PackageTest extends CBSCommonalitiesExecutionTest {

	@Parameterized.Parameters(name='{0} to {1}')
	static def List<Object[]> testParameters() {
		val domainModelsProviders = #[
			new UmlTestModelsProvider [new UmlPackageTestModels(it)],
			new JavaTestModelsProvider [new JavaPackageTestModels(it)]
		]
		return domainModelsProviders.orderedPairs
	}

	interface DomainModels {

		static val ROOT1_PACKAGE_NAME = 'root1'
		static val ROOT2_PACKAGE_NAME = 'root2'
		static val SUB1_PACKAGE_NAME = 'sub1'
		static val SUB2_PACKAGE_NAME = 'sub2'

		def DomainModel singleRootPackageCreation()
		def DomainModel multiRootPackageCreation()
		def DomainModel subPackagesCreation()
	}

	val DomainModels sourceModels
	val DomainModels targetModels

	new(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		this.sourceModels = sourceModelsProvider.getModels(vitruvApplicationTestAdapter)
		this.targetModels = targetModelsProvider.getModels(vitruvApplicationTestAdapter)
	}

	@Test
	def void singleRootPackageCreation() {
		sourceModels.singleRootPackageCreation.createAndSynchronize()
		targetModels.singleRootPackageCreation.check()
	}

	@Test
	def void multiRootPackageCreation() {
		sourceModels.multiRootPackageCreation.createAndSynchronize()
		targetModels.multiRootPackageCreation.check()
	}

	@Test
	def void subPackagesCreation() {
		sourceModels.subPackagesCreation.createAndSynchronize()
		targetModels.subPackagesCreation.check()
		// TODO check that no PCM repository is created for this?
	}

	// TODO package renaming
	// TODO package moving
}

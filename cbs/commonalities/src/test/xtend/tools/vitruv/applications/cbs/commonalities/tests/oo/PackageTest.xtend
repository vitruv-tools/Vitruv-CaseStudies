package tools.vitruv.applications.cbs.commonalities.tests.oo

import java.util.List
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaPackageTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlPackageTestModels
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class PackageTest extends CBSCommonalitiesExecutionTest {

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

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void singleRootPackageCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.singleRootPackageCreation.createAndSynchronize()
		targetModelsProvider.getModels.singleRootPackageCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void multiRootPackageCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.multiRootPackageCreation.createAndSynchronize()
		targetModelsProvider.getModels.multiRootPackageCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void subPackagesCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.subPackagesCreation.createAndSynchronize()
		targetModelsProvider.getModels.subPackagesCreation.check()
		// TODO check that no PCM repository is created for this?
	}

	// TODO package renaming
	// TODO package moving
}

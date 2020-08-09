package tools.vitruv.applications.cbs.commonalities.tests.oo

import java.util.List
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaInterfaceTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlInterfaceTestModels
import tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.runner.XtextParametersRunnerFactory

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

@RunWith(Parameterized)
@Parameterized.UseParametersRunnerFactory(XtextParametersRunnerFactory)
class InterfaceTest extends CBSCommonalitiesExecutionTest {

	@Parameterized.Parameters(name='{0} to {1}')
	static def List<Object[]> testParameters() {
		val domainModelsProviders = #[
			new UmlTestModelsProvider [new UmlInterfaceTestModels(it)],
			new JavaTestModelsProvider [new JavaInterfaceTestModels(it)]
		]
		return domainModelsProviders.toListOfPairs(true)
	}

	/**
	 * All created interfaces are of public visibility currently.
	 */
	interface DomainModels {

		static val PACKAGE_1_NAME = 'root'
		static val PACKAGE_2_NAME = 'root2'
		static val INTERFACE_1_NAME = 'Foo'
		static val INTERFACE_2_NAME = 'Bar'

		// Empty interface

		/**
		 * An interface with only the minimally required attributes.
		 */
		def DomainModel emptyInterfaceCreation()

		// Multiple interfaces

		def DomainModel multipleInterfacesInSamePackageCreation()
		def DomainModel multipleInterfacesInDifferentPackagesCreation()
	}

	val DomainModels sourceModels
	val DomainModels targetModels

	new(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		this.sourceModels = sourceModelsProvider.getModels(vitruvApplicationTestAdapter)
		this.targetModels = targetModelsProvider.getModels(vitruvApplicationTestAdapter)
	}

	// Empty interface

	@Test
	def void emptyInterfaceCreation() {
		sourceModels.emptyInterfaceCreation.createAndSynchronize()
		targetModels.emptyInterfaceCreation.check()
	}

	// Multiple interfaces

	@Test
	def void multipleInterfacesInSamePackageCreation() {
		sourceModels.multipleInterfacesInSamePackageCreation.createAndSynchronize()
		targetModels.multipleInterfacesInSamePackageCreation.check()
	}

	@Test
	def void multipleInterfacesInDifferentPackagesCreation() {
		sourceModels.multipleInterfacesInDifferentPackagesCreation.createAndSynchronize()
		targetModels.multipleInterfacesInDifferentPackagesCreation.check()
	}

	// TODO renaming
	// TODO support for non-public interfaces? (eg. package-private, or private inner interfaces)
}
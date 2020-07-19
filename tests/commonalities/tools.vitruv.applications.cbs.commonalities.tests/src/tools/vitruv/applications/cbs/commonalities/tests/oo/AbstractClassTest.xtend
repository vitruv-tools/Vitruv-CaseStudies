package tools.vitruv.applications.cbs.commonalities.tests.oo

import org.junit.Test
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.DomainModel

abstract class AbstractClassTest extends CBSCommonalitiesExecutionTest {

	/**
	 * If not specified otherwise by the individual test cases, all created
	 * classes use the domain's default class visibility. Since some domains
	 * have different default class visibilities, this has to be taken into
	 * account when testing these pairs of domains.
	 */
	interface DomainModels {

		static val PACKAGE_1_NAME = 'root'
		static val PACKAGE_2_NAME = 'root2'
		static val CLASS_1_NAME = 'Foo'
		static val CLASS_2_NAME = 'Bar'

		// Empty class

		/**
		 * A class with only the minimally required attributes.
		 */
		def DomainModel emptyClassCreation()

		// Modifiers

		def DomainModel privateClassCreation()
		def DomainModel publicClassCreation()
		def DomainModel protectedClassCreation()
		def DomainModel packagePrivateClassCreation()
		def DomainModel finalClassCreation()
		def DomainModel abstractClassCreation()

		/**
		 * A class with the following attributes:
		 * <ul>
		 * <li>public visibility
		 * <li>final
		 * <li>abstract
		 * </ul>
		 * This combination does not make much sense, but that is not important
		 * for this test.
		 */
		def DomainModel classWithMultipleModifiersCreation()

		// Multiple classes

		def DomainModel multipleClassesInSamePackageCreation()
		def DomainModel multipleClassesInDifferentPackagesCreation()
	}

	protected abstract def DomainModels getSourceModels()
	protected abstract def DomainModels getTargetModels()

	// Empty class

	@Test
	def void emptyClassCreation() {
		sourceModels.emptyClassCreation.createAndSynchronize()
		targetModels.emptyClassCreation.check()
	}

	// Modifiers

	@Test
	def void privateClassCreation() {
		sourceModels.privateClassCreation.createAndSynchronize()
		targetModels.privateClassCreation.check()
	}

	@Test
	def void publicClassCreation() {
		sourceModels.publicClassCreation.createAndSynchronize()
		targetModels.publicClassCreation.check()
	}

	@Test
	def void protectedClassCreation() {
		sourceModels.protectedClassCreation.createAndSynchronize()
		targetModels.protectedClassCreation.check()
	}

	@Test
	def void packagePrivateClassCreation() {
		sourceModels.packagePrivateClassCreation.createAndSynchronize()
		targetModels.packagePrivateClassCreation.check()
	}

	@Test
	def void finalClassCreation() {
		sourceModels.finalClassCreation.createAndSynchronize()
		targetModels.finalClassCreation.check()
	}

	@Test
	def void abstractClassCreation() {
		sourceModels.abstractClassCreation.createAndSynchronize()
		targetModels.abstractClassCreation.check()
	}

	@Test
	def void classWithMultipleModifiersCreation() {
		sourceModels.classWithMultipleModifiersCreation.createAndSynchronize()
		targetModels.classWithMultipleModifiersCreation.check()
	}

	// Multiple classes

	@Test
	def void multipleClassesInSamePackageCreation() {
		sourceModels.multipleClassesInSamePackageCreation.createAndSynchronize()
		targetModels.multipleClassesInSamePackageCreation.check()
	}

	@Test
	def void multipleClassesInDifferentPackagesCreation() {
		sourceModels.multipleClassesInDifferentPackagesCreation.createAndSynchronize()
		targetModels.multipleClassesInDifferentPackagesCreation.check()
	}
}

package tools.vitruv.applications.cbs.commonalities.tests.oo

import org.junit.Test
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.DomainModel

abstract class AbstractClassPropertyTest extends CBSCommonalitiesExecutionTest {

	/**
	 * If not specified otherwise by the individual test cases, all created
	 * classes are of public visibility and all created properties are of
	 * primitive <code>int</code> type and use the domain's default property
	 * visibility. Since some domains have different default property
	 * visibilities, this has to be considered in the pairwise tests between
	 * those domains.
	 */
	interface DomainModels {

		static val PACKAGE_NAME = 'root'
		static val CLASS_NAME = 'Foo'
		static val PROPERTY_NAME = 'someProperty'
		static val BOOLEAN_PROPERTY_NAME = 'someBoolean'
		static val INT_PROPERTY_NAME = 'someInt'
		static val DOUBLE_PROPERTY_NAME = 'someDouble'
		static val STRING_PROPERTY_NAME = 'someString'

		// Basic

		/**
		 * A property with only the minimally required attributes.
		 */
		def DomainModel basicPrimitiveClassPropertyCreation()

		// Modifiers

		def DomainModel privateClassPropertyCreation()
		def DomainModel publicClassPropertyCreation()
		def DomainModel protectedClassPropertyCreation()
		def DomainModel packagePrivateClassPropertyCreation()
		def DomainModel finalClassPropertyCreation()
		def DomainModel staticClassPropertyCreation()

		/**
		 * A property with the following attributes:
		 * <ul>
		 * <li>private visibility
		 * <li>static
		 * <li>final
		 * </ul>
		 */
		def DomainModel classPropertyWithMultipleModifiersCreation()

		// Type references

		def DomainModel stringClassPropertyCreation()

		// Multiple properties

		/**
		 * One property for each of the following primitive types:
		 * <ul>
		 * <li>boolean
		 * <li>int
		 * <li>double
		 * </ul>
		 * Note: String-typed properties are tested separately.
		 */
		def DomainModel multiplePrimitiveClassPropertiesCreation()
	}

	protected abstract def DomainModels getSourceModels()
	protected abstract def DomainModels getTargetModels()

	// Basic

	@Test
	def void basicPrimitiveClassPropertyCreation() {
		sourceModels.basicPrimitiveClassPropertyCreation.createAndSynchronize()
		targetModels.basicPrimitiveClassPropertyCreation.check()
	}

	// Modifiers

	@Test
	def void privateClassPropertyCreation() {
		sourceModels.privateClassPropertyCreation.createAndSynchronize()
		targetModels.privateClassPropertyCreation.check()
	}

	@Test
	def void publicClassPropertyCreation() {
		sourceModels.publicClassPropertyCreation.createAndSynchronize()
		targetModels.publicClassPropertyCreation.check()
	}

	@Test
	def void protectedClassPropertyCreation() {
		sourceModels.protectedClassPropertyCreation.createAndSynchronize()
		targetModels.protectedClassPropertyCreation.check()
	}

	@Test
	def void packagePrivateClassPropertyCreation() {
		sourceModels.packagePrivateClassPropertyCreation.createAndSynchronize()
		targetModels.packagePrivateClassPropertyCreation.check()
	}

	@Test
	def void finalClassPropertyCreation() {
		sourceModels.finalClassPropertyCreation.createAndSynchronize()
		targetModels.finalClassPropertyCreation.check()
	}

	@Test
	def void staticClassPropertyCreation() {
		sourceModels.finalClassPropertyCreation.createAndSynchronize()
		targetModels.finalClassPropertyCreation.check()
	}

	@Test
	def void classPropertyWithMultipleModifiersCreation() {
		sourceModels.classPropertyWithMultipleModifiersCreation.createAndSynchronize()
		targetModels.classPropertyWithMultipleModifiersCreation.check()
	}

	// Type references

	@Test
	def void stringClassPropertyCreation() {
		sourceModels.stringClassPropertyCreation.createAndSynchronize()
		targetModels.stringClassPropertyCreation.check()
	}

	// Multiple properties

	@Test
	def void multipleClassesInDifferentPackagesCreation() {
		sourceModels.multiplePrimitiveClassPropertiesCreation.createAndSynchronize()
		targetModels.multiplePrimitiveClassPropertiesCreation.check()
	}

	// TODO other data types: Classes, interfaces

	// TODO collection data types
	// TODO enums
	// TODO primitive wrapper types
}

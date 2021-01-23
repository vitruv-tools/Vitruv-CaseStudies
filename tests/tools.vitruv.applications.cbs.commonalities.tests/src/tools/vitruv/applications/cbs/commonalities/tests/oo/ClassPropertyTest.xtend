package tools.vitruv.applications.cbs.commonalities.tests.oo

import java.util.List
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.modifiers.ModifiersFactory
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaClassPropertyTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlClassPropertyTestModels
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class ClassPropertyTest extends CBSCommonalitiesExecutionTest {

	static def List<Object[]> testParameters() {
		return #[
			#[
				new UmlTestModelsProvider [new UmlClassPropertyTestModels(it)],
				new JavaTestModelsProvider [
					new JavaClassPropertyTestModels(it) {
						// UML creates properties with no visibility by default, which maps to public visibility in Java.
						override protected defaultFieldVisibility() {
							return ModifiersFactory.eINSTANCE.createPublic
						}
					}
				]
			],
			#[
				new JavaTestModelsProvider [new JavaClassPropertyTestModels(it)],
				new UmlTestModelsProvider [
					// Java creates fields with package-private visibility by default.
					new UmlClassPropertyTestModels(it) {
						override protected defaultPropertyVisibility () {
							return VisibilityKind.PACKAGE_LITERAL
						}
					}
				]
			]
		]
	}

	/**
	 * If not specified otherwise by the individual test cases, all created
	 * classes are of public visibility and all created properties are of
	 * private visibility and primitive <code>int</code> type.
	 * <p>
	 * Some test cases may use the domain's default property visibility. Since
	 * some domains have different default visibilities, this has to be
	 * considered in the pairwise tests between those domains.
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
		 * A property with only the minimally required attributes and the
		 * domain's default visibility.
		 */
		def DomainModel basicPrimitiveClassPropertyCreation()

		// Visibility

		def DomainModel privateClassPropertyCreation()
		def DomainModel publicClassPropertyCreation()
		def DomainModel protectedClassPropertyCreation()
		def DomainModel packagePrivateClassPropertyCreation()

		// Modifiers

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

	// Basic

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void basicPrimitiveClassPropertyCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.basicPrimitiveClassPropertyCreation.createAndSynchronize()
		targetModelsProvider.getModels.basicPrimitiveClassPropertyCreation.check()
	}

	// Visibility

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void privateClassPropertyCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.privateClassPropertyCreation.createAndSynchronize()
		targetModelsProvider.getModels.privateClassPropertyCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void publicClassPropertyCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.publicClassPropertyCreation.createAndSynchronize()
		targetModelsProvider.getModels.publicClassPropertyCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void protectedClassPropertyCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.protectedClassPropertyCreation.createAndSynchronize()
		targetModelsProvider.getModels.protectedClassPropertyCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void packagePrivateClassPropertyCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.packagePrivateClassPropertyCreation.createAndSynchronize()
		targetModelsProvider.getModels.packagePrivateClassPropertyCreation.check()
	}

	// Modifiers

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void finalClassPropertyCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.finalClassPropertyCreation.createAndSynchronize()
		targetModelsProvider.getModels.finalClassPropertyCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void staticClassPropertyCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.finalClassPropertyCreation.createAndSynchronize()
		targetModelsProvider.getModels.finalClassPropertyCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void classPropertyWithMultipleModifiersCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.classPropertyWithMultipleModifiersCreation.createAndSynchronize()
		targetModelsProvider.getModels.classPropertyWithMultipleModifiersCreation.check()
	}

	// Type references

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void stringClassPropertyCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.stringClassPropertyCreation.createAndSynchronize()
		targetModelsProvider.getModels.stringClassPropertyCreation.check()
	}

	// Multiple properties

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void multipleClassesInDifferentPackagesCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.multiplePrimitiveClassPropertiesCreation.createAndSynchronize()
		targetModelsProvider.getModels.multiplePrimitiveClassPropertiesCreation.check()
	}

	// TODO other data types: Classes, interfaces

	// TODO collection data types
	// TODO enums
	// TODO primitive wrapper types
}

package tools.vitruv.applications.cbs.commonalities.tests.oo

import java.util.List
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.modifiers.ModifiersFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaClassPropertyTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlClassPropertyTestModels
import tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.runner.XtextParametersRunnerFactory

@RunWith(Parameterized)
@Parameterized.UseParametersRunnerFactory(XtextParametersRunnerFactory)
class ClassPropertyTest extends CBSCommonalitiesExecutionTest {

	@Parameterized.Parameters(name='{0} to {1}')
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

	val DomainModels sourceModels
	val DomainModels targetModels

	new(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		this.sourceModels = sourceModelsProvider.getModels(vitruvApplicationTestAdapter)
		this.targetModels = targetModelsProvider.getModels(vitruvApplicationTestAdapter)
	}

	// Basic

	@Test
	def void basicPrimitiveClassPropertyCreation() {
		sourceModels.basicPrimitiveClassPropertyCreation.createAndSynchronize()
		targetModels.basicPrimitiveClassPropertyCreation.check()
	}

	// Visibility

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

	// Modifiers

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

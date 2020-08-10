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
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaClassTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlClassTestModels
import tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.runner.XtextParametersRunnerFactory

@RunWith(Parameterized)
@Parameterized.UseParametersRunnerFactory(XtextParametersRunnerFactory)
class ClassTest extends CBSCommonalitiesExecutionTest {

	@Parameterized.Parameters(name='{0} to {1}')
	static def List<Object[]> testParameters() {
		return #[
			#[
				new UmlTestModelsProvider [new UmlClassTestModels(it)],
				new JavaTestModelsProvider [
					new JavaClassTestModels(it) {
						// UML creates classes with no visibility by default, which maps to public visibility in Java.
						override protected defaultClassVisibility() {
							return ModifiersFactory.eINSTANCE.createPublic
						}
					}
				]
			],
			#[
				new JavaTestModelsProvider [new JavaClassTestModels(it)],
				new UmlTestModelsProvider [
					// Java creates classes with package-private visibility by default.
					new UmlClassTestModels(it) {
						override protected defaultClassVisibility () {
							return VisibilityKind.PACKAGE_LITERAL
						}
					}
				]
			]
		]
	}

	/**
	 * If not specified otherwise by the individual test cases, all created
	 * classes are of public visibility.
	 * <p>
	 * Some test cases may create classes with the domain's default visibility.
	 * Since some domains have different default class visibilities, this has
	 * to be taken into account when testing these pairs of domains.
	 */
	interface DomainModels {

		static val PACKAGE_1_NAME = 'root'
		static val PACKAGE_2_NAME = 'root2'
		static val CLASS_1_NAME = 'Foo'
		static val CLASS_2_NAME = 'Bar'
		static val INTERFACE_1_NAME = 'IFoo'
		static val INTERFACE_2_NAME = 'IBar'

		// Empty class

		/**
		 * A class with only the minimally required attributes and the domain's
		 * default visibility.
		 */
		def DomainModel emptyClassCreation()

		// Visibility

		def DomainModel privateClassCreation()
		def DomainModel publicClassCreation()
		def DomainModel protectedClassCreation()
		def DomainModel packagePrivateClassCreation()

		// Modifiers

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

		// Super class

		/**
		 * Class 1 extending class 2 from the same package.
		 */
		def DomainModel classWithSuperClassCreation()

		// Implemented interfaces

		/**
		 * Class 1 implementing interface 1 from the same package.
		 */
		def DomainModel classImplementingInterfaceCreation()

		/**
		 * Class 1 implementing interface 1 and 2 from the same package.
		 */
		def DomainModel classImplementingMultipleInterfacesCreation()
	}

	val DomainModels sourceModels
	val DomainModels targetModels

	new(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		this.sourceModels = sourceModelsProvider.getModels(vitruvApplicationTestAdapter)
		this.targetModels = targetModelsProvider.getModels(vitruvApplicationTestAdapter)
	}

	// Empty class

	@Test
	def void emptyClassCreation() {
		sourceModels.emptyClassCreation.createAndSynchronize()
		targetModels.emptyClassCreation.check()
	}

	// Visibility

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

	// Modifiers

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

	// Super class

	@Test
	def void classWithSuperClassCreation() {
		sourceModels.classWithSuperClassCreation.createAndSynchronize()
		targetModels.classWithSuperClassCreation.check()
	}

	// Implemented interfaces

	@Test
	def void classImplementingInterfaceCreation() {
		sourceModels.classImplementingInterfaceCreation.createAndSynchronize()
		targetModels.classImplementingInterfaceCreation.check()
	}

	@Test
	def void classImplementingMultipleInterfacesCreation() {
		sourceModels.classImplementingMultipleInterfacesCreation.createAndSynchronize()
		targetModels.classImplementingMultipleInterfacesCreation.check()
	}
}

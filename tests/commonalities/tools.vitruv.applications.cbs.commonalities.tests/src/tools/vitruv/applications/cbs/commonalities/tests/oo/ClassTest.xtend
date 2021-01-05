package tools.vitruv.applications.cbs.commonalities.tests.oo

import java.util.List
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.modifiers.ModifiersFactory
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaClassTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlClassTestModels
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class ClassTest extends CBSCommonalitiesExecutionTest {

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

	// Empty class

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void emptyClassCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.emptyClassCreation.createAndSynchronize()
		targetModelsProvider.getModels.emptyClassCreation.check()
	}

	// Visibility

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void privateClassCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.privateClassCreation.createAndSynchronize()
		targetModelsProvider.getModels.privateClassCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void publicClassCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.publicClassCreation.createAndSynchronize()
		targetModelsProvider.getModels.publicClassCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void protectedClassCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.protectedClassCreation.createAndSynchronize()
		targetModelsProvider.getModels.protectedClassCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void packagePrivateClassCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.packagePrivateClassCreation.createAndSynchronize()
		targetModelsProvider.getModels.packagePrivateClassCreation.check()
	}

	// Modifiers

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void finalClassCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.finalClassCreation.createAndSynchronize()
		targetModelsProvider.getModels.finalClassCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void abstractClassCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.abstractClassCreation.createAndSynchronize()
		targetModelsProvider.getModels.abstractClassCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void classWithMultipleModifiersCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.classWithMultipleModifiersCreation.createAndSynchronize()
		targetModelsProvider.getModels.classWithMultipleModifiersCreation.check()
	}

	// Multiple classes

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void multipleClassesInSamePackageCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.multipleClassesInSamePackageCreation.createAndSynchronize()
		targetModelsProvider.getModels.multipleClassesInSamePackageCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void multipleClassesInDifferentPackagesCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.multipleClassesInDifferentPackagesCreation.createAndSynchronize()
		targetModelsProvider.getModels.multipleClassesInDifferentPackagesCreation.check()
	}

	// Super class

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void classWithSuperClassCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.classWithSuperClassCreation.createAndSynchronize()
		targetModelsProvider.getModels.classWithSuperClassCreation.check()
	}

	// Implemented interfaces

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void classImplementingInterfaceCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.classImplementingInterfaceCreation.createAndSynchronize()
		targetModelsProvider.getModels.classImplementingInterfaceCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void classImplementingMultipleInterfacesCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.classImplementingMultipleInterfacesCreation.createAndSynchronize()
		targetModelsProvider.getModels.classImplementingMultipleInterfacesCreation.check()
	}
}

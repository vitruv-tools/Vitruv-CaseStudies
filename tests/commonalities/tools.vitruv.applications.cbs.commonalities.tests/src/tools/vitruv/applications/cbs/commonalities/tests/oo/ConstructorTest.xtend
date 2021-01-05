package tools.vitruv.applications.cbs.commonalities.tests.oo

import java.util.List
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaConstructorTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlConstructorTestModels
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class ConstructorTest extends CBSCommonalitiesExecutionTest {

	static def List<Object[]> testParameters() {
		return #[
			#[
				new UmlTestModelsProvider [new UmlConstructorTestModels(it)],
				new JavaTestModelsProvider [new JavaConstructorTestModels(it)]
			],
			#[
				new JavaTestModelsProvider [new JavaConstructorTestModels(it)],
				new UmlTestModelsProvider [new UmlConstructorTestModels(it)]
			]
		]
	}

	/**
	 * If not specified otherwise by the individual test cases, all created
	 * classes and constructors are of public visibility.
	 */
	interface DomainModels {

		static val PACKAGE_NAME = 'root'
		static val CLASS_NAME = 'Foo'
		static val OTHER_CLASS_NAME = 'SomeClass'
		static val INTEGER_PARAMETER_NAME = 'integerInput'
		static val BOOLEAN_PARAMETER_NAME = 'booleanInput'
		static val DOUBLE_PARAMETER_NAME = 'doubleInput'
		static val STRING_PARAMETER_NAME = 'stringInput'
		static val CLASS_PARAMETER_NAME = 'classInput'
		static val OWN_TYPE_PARAMETER_NAME = 'ownTypeInput'

		// Basic

		/**
		 * A constructor with only the minimally required attributes (i.e.
		 * without input parameters).
		 */
		def DomainModel basicConstructorCreation()

		// Visibility

		def DomainModel publicConstructorCreation()

		def DomainModel protectedConstructorCreation()

		def DomainModel packagePrivateConstructorCreation()

		def DomainModel privateConstructorCreation()

		// Input parameters

		def DomainModel constructorWithIntegerInputCreation()

		/**
		 * Constructor with a boolean, integer and double input parameter.
		 */
		def DomainModel constructorWithMultiplePrimitiveInputsCreation()

		def DomainModel constructorWithStringInputCreation()

		def DomainModel constructorWithClassInputCreation()

		/**
		 * Constructor which has an input of the containing class' type.
		 */
		def DomainModel constructorWithSelfInputCreation()

		/**
		 * Constructor with an integer, String and class input parameter.
		 */
		def DomainModel constructorWithMixedInputsCreation()

		// Multiple constructors

		/**
		 * The first constructor has a boolean input parameter, the second has
		 * a String input parameter.
		 */
		def DomainModel multipleConstructorsCreation()
	}

	// Basic

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void basicConstructorCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.basicConstructorCreation.createAndSynchronize()
		targetModelsProvider.getModels.basicConstructorCreation.check()
	}

	// Visibility

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void publicConstructorCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.publicConstructorCreation.createAndSynchronize()
		targetModelsProvider.getModels.publicConstructorCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void protectedConstructorCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.protectedConstructorCreation.createAndSynchronize()
		targetModelsProvider.getModels.protectedConstructorCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void packagePrivateConstructorCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.packagePrivateConstructorCreation.createAndSynchronize()
		targetModelsProvider.getModels.packagePrivateConstructorCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void privateConstructorCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.privateConstructorCreation.createAndSynchronize()
		targetModelsProvider.getModels.privateConstructorCreation.check()
	}

	// Input parameters

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void constructorWithIntegerInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.constructorWithIntegerInputCreation.createAndSynchronize()
		targetModelsProvider.getModels.constructorWithIntegerInputCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void constructorWithMultiplePrimitiveInputsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.constructorWithMultiplePrimitiveInputsCreation.createAndSynchronize()
		targetModelsProvider.getModels.constructorWithMultiplePrimitiveInputsCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void constructorWithStringInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.constructorWithStringInputCreation.createAndSynchronize()
		targetModelsProvider.getModels.constructorWithStringInputCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void constructorWithClassInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.constructorWithClassInputCreation.createAndSynchronize()
		targetModelsProvider.getModels.constructorWithClassInputCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void constructorWithSelfInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.constructorWithSelfInputCreation.createAndSynchronize()
		targetModelsProvider.getModels.constructorWithSelfInputCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void constructorWithMixedInputsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.constructorWithMixedInputsCreation.createAndSynchronize()
		targetModelsProvider.getModels.constructorWithMixedInputsCreation.check()
	}

	// Multiple constructors

	// TODO Does not work yet in the UML->Java direction. Problem: The created Java constructors are first empty. When
	// adding parameters, we retrieve the target constructor via the correspondence model. However, Java's TUIDs for
	// constructors without parameters are the same. Result: All parameters get added to the same single constructor.
	@Disabled
	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void multipleConstructorsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.multipleConstructorsCreation.createAndSynchronize()
		targetModelsProvider.getModels.multipleConstructorsCreation.check()
	}
}

package tools.vitruv.applications.cbs.commonalities.tests.oo

import java.util.List
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaClassMethodTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlClassMethodTestModels
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class ClassMethodTest extends CBSCommonalitiesExecutionTest {

	static def List<Object[]> testParameters() {
		return #[
			#[
				new UmlTestModelsProvider [new UmlClassMethodTestModels(it)],
				new JavaTestModelsProvider [new JavaClassMethodTestModels(it)]
			],
			#[
				new JavaTestModelsProvider [new JavaClassMethodTestModels(it)],
				new UmlTestModelsProvider [new UmlClassMethodTestModels(it)]
			]
		]
	}

	/**
	 * If not specified otherwise by the individual test cases, all created
	 * classes and methods are of public visibility.
	 */
	interface DomainModels {

		static val PACKAGE_NAME = 'root'
		static val CLASS_NAME = 'Foo'
		static val METHOD_NAME = 'someMethod'
		static val METHOD_2_NAME = 'someMethod2'
		static val OTHER_CLASS_NAME = 'SomeClass'
		static val INTEGER_PARAMETER_NAME = 'integerInput'
		static val BOOLEAN_PARAMETER_NAME = 'booleanInput'
		static val DOUBLE_PARAMETER_NAME = 'doubleInput'
		static val STRING_PARAMETER_NAME = 'stringInput'
		static val CLASS_PARAMETER_NAME = 'classInput'
		static val OWN_TYPE_PARAMETER_NAME = 'ownTypeInput'

		// Basic

		/**
		 * A method with only the minimally required attributes (i.e. without
		 * input or return parameters).
		 */
		def DomainModel basicClassMethodCreation()

		// Visibility

		def DomainModel publicClassMethodCreation()

		def DomainModel protectedClassMethodCreation()

		def DomainModel packagePrivateClassMethodCreation()

		def DomainModel privateClassMethodCreation()

		// Modifiers

		def DomainModel finalClassMethodCreation()

		def DomainModel abstractClassMethodCreation()

		def DomainModel staticClassMethodCreation()

		// Return type

		/**
		 * Class method with integer return type and no inputs.
		 */
		def DomainModel classMethodWithIntegerReturnCreation()

		def DomainModel classMethodWithStringReturnCreation()

		def DomainModel classMethodWithClassReturnCreation()

		/**
		 * Class method which has the containing class as return type.
		 */
		def DomainModel classMethodWithSelfReturnCreation()

		// Input parameters

		def DomainModel classMethodWithIntegerInputCreation()

		/**
		 * Class method with a boolean, integer and double input parameter.
		 */
		def DomainModel classMethodWithMultiplePrimitiveInputsCreation()

		def DomainModel classMethodWithStringInputCreation()

		def DomainModel classMethodWithClassInputCreation()

		/**
		 * Class method which has an input of the containing class' type.
		 */
		def DomainModel classMethodWithSelfInputCreation()

		/**
		 * Class method with an integer, String and class input parameter.
		 */
		def DomainModel classMethodWithMixedInputsCreation()

		// Mixed input and return types

		/**
		 * Class method with an integer, String and class input parameter and
		 * an integer return type.
		 */
		def DomainModel classMethodWithMixedInputsAndReturnCreation()

		// Multiple methods

		/**
		 * Both methods have an integer return type. The first method has a
		 * boolean input parameter, the second has a String input parameter.
		 */
		def DomainModel multipleClassMethodsCreation()
	}

	// Basic

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void basicClassMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.basicClassMethodCreation.createAndSynchronize()
		targetModelsProvider.getModels.basicClassMethodCreation.check()
	}

	// Visibility

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void publicClassMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.publicClassMethodCreation.createAndSynchronize()
		targetModelsProvider.getModels.publicClassMethodCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void protectedClassMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.protectedClassMethodCreation.createAndSynchronize()
		targetModelsProvider.getModels.protectedClassMethodCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void packagePrivateClassMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.packagePrivateClassMethodCreation.createAndSynchronize()
		targetModelsProvider.getModels.packagePrivateClassMethodCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void privateClassMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.privateClassMethodCreation.createAndSynchronize()
		targetModelsProvider.getModels.privateClassMethodCreation.check()
	}

	// Modifiers

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void finalClassMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.finalClassMethodCreation.createAndSynchronize()
		targetModelsProvider.getModels.finalClassMethodCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void abstractClassMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.abstractClassMethodCreation.createAndSynchronize()
		targetModelsProvider.getModels.abstractClassMethodCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void staticClassMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.staticClassMethodCreation.createAndSynchronize()
		targetModelsProvider.getModels.staticClassMethodCreation.check()
	}

	// Return type

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void IntegerReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.classMethodWithIntegerReturnCreation.createAndSynchronize()
		targetModelsProvider.getModels.classMethodWithIntegerReturnCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void classMethodWithStringReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.classMethodWithStringReturnCreation.createAndSynchronize()
		targetModelsProvider.getModels.classMethodWithStringReturnCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void classMethodWithClassReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.classMethodWithClassReturnCreation.createAndSynchronize()
		targetModelsProvider.getModels.classMethodWithClassReturnCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void classMethodWithSelfReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.classMethodWithSelfReturnCreation.createAndSynchronize()
		targetModelsProvider.getModels.classMethodWithSelfReturnCreation.check()
	}

	// Input parameters

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void classMethodWithIntegerInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.classMethodWithIntegerInputCreation.createAndSynchronize()
		targetModelsProvider.getModels.classMethodWithIntegerInputCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void classMethodWithMultiplePrimitiveInputsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.classMethodWithMultiplePrimitiveInputsCreation.createAndSynchronize()
		targetModelsProvider.getModels.classMethodWithMultiplePrimitiveInputsCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void classMethodWithStringInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.classMethodWithStringInputCreation.createAndSynchronize()
		targetModelsProvider.getModels.classMethodWithStringInputCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void classMethodWithClassInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.classMethodWithClassInputCreation.createAndSynchronize()
		targetModelsProvider.getModels.classMethodWithClassInputCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void classMethodWithSelfInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.classMethodWithSelfInputCreation.createAndSynchronize()
		targetModelsProvider.getModels.classMethodWithSelfInputCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void classMethodWithMixedInputsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.classMethodWithMixedInputsCreation.createAndSynchronize()
		targetModelsProvider.getModels.classMethodWithMixedInputsCreation.check()
	}

	// Mixed input and return types

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void classMethodWithMixedInputsAndReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.classMethodWithMixedInputsAndReturnCreation.createAndSynchronize()
		targetModelsProvider.getModels.classMethodWithMixedInputsAndReturnCreation.check()
	}

	// Multiple methods

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void multipleClassMethodsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.multipleClassMethodsCreation.createAndSynchronize()
		targetModelsProvider.getModels.multipleClassMethodsCreation.check()
	}

	// TODO rename
	// TODO parameter changes
}

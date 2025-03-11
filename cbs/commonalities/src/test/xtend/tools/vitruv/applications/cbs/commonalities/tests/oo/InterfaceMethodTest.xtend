package tools.vitruv.applications.cbs.commonalities.tests.oo

import java.util.List
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaInterfaceMethodTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlInterfaceMethodTestModels
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class InterfaceMethodTest extends CBSCommonalitiesExecutionTest {

	static def List<Object[]> testParameters() {
		return #[
			#[
				new UmlTestModelsProvider [new UmlInterfaceMethodTestModels(it)],
				new JavaTestModelsProvider [new JavaInterfaceMethodTestModels(it)]
			],
			#[
				new JavaTestModelsProvider [new JavaInterfaceMethodTestModels(it)],
				new UmlTestModelsProvider [new UmlInterfaceMethodTestModels(it)]
			]
		]
	}

	/**
	 * If not specified otherwise by the individual test cases, all created
	 * interfaces and interface methods are of public visibility.
	 * <p>
	 * The class used in some test cases has public visibility as well.
	 */
	interface DomainModels {

		static val PACKAGE_NAME = 'root'
		static val INTERFACE_NAME = 'Foo'
		static val METHOD_NAME = 'someMethod'
		static val METHOD_2_NAME = 'someMethod2'
		static val OTHER_CLASS_NAME = 'SomeOtherClass'
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
		def DomainModel basicInterfaceMethodCreation()

		// Static

		def DomainModel staticInterfaceMethodCreation()

		// Return type

		/**
		 * Interface method with integer return type and no inputs.
		 */
		def DomainModel interfaceMethodWithIntegerReturnCreation()

		def DomainModel interfaceMethodWithStringReturnCreation()

		def DomainModel interfaceMethodWithClassReturnCreation()

		/**
		 * Interface method which has the containing interface as return type.
		 */
		def DomainModel interfaceMethodWithSelfReturnCreation()

		// Input parameters

		def DomainModel interfaceMethodWithIntegerInputCreation()

		/**
		 * Interface method with a boolean, integer and double input parameter.
		 */
		def DomainModel interfaceMethodWithMultiplePrimitiveInputsCreation()

		def DomainModel interfaceMethodWithStringInputCreation()

		def DomainModel interfaceMethodWithClassInputCreation()

		/**
		 * Interface method which has an input of the containing interface's
		 * type.
		 */
		def DomainModel interfaceMethodWithSelfInputCreation()

		/**
		 * Interface method with an integer, String and class input parameter.
		 */
		def DomainModel interfaceMethodWithMixedInputsCreation()

		// Mixed input and return types

		/**
		 * Interface method with an integer, String and class input parameter
		 * and an integer return type.
		 */
		def DomainModel interfaceMethodWithMixedInputsAndReturnCreation()

		// Multiple methods

		/**
		 * Both methods have an integer return type. The first method has a
		 * boolean input parameter, the second has a String input parameter.
		 */
		def DomainModel multipleInterfaceMethodsCreation()
	}

	// Basic

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void basicInterfaceMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.basicInterfaceMethodCreation.createAndSynchronize()
		targetModelsProvider.getModels.basicInterfaceMethodCreation.check()
	}

	// Static

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void staticInterfaceMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.staticInterfaceMethodCreation.createAndSynchronize()
		targetModelsProvider.getModels.staticInterfaceMethodCreation.check()
	}

	// Return type

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void interfaceMethodWithIntegerReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.interfaceMethodWithIntegerReturnCreation.createAndSynchronize()
		targetModelsProvider.getModels.interfaceMethodWithIntegerReturnCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void interfaceMethodWithStringReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.interfaceMethodWithStringReturnCreation.createAndSynchronize()
		targetModelsProvider.getModels.interfaceMethodWithStringReturnCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void interfaceMethodWithClassReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.interfaceMethodWithClassReturnCreation.createAndSynchronize()
		targetModelsProvider.getModels.interfaceMethodWithClassReturnCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void interfaceMethodWithSelfReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.interfaceMethodWithSelfReturnCreation.createAndSynchronize()
		targetModelsProvider.getModels.interfaceMethodWithSelfReturnCreation.check()
	}

	// Input parameters

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void interfaceMethodWithIntegerInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.interfaceMethodWithIntegerInputCreation.createAndSynchronize()
		targetModelsProvider.getModels.interfaceMethodWithIntegerInputCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void interfaceMethodWithMultiplePrimitiveInputsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.interfaceMethodWithMultiplePrimitiveInputsCreation.createAndSynchronize()
		targetModelsProvider.getModels.interfaceMethodWithMultiplePrimitiveInputsCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void interfaceMethodWithStringInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.interfaceMethodWithStringInputCreation.createAndSynchronize()
		targetModelsProvider.getModels.interfaceMethodWithStringInputCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void interfaceMethodWithClassInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.interfaceMethodWithClassInputCreation.createAndSynchronize()
		targetModelsProvider.getModels.interfaceMethodWithClassInputCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void interfaceMethodWithSelfInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.interfaceMethodWithSelfInputCreation.createAndSynchronize()
		targetModelsProvider.getModels.interfaceMethodWithSelfInputCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void interfaceMethodWithMixedInputsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.interfaceMethodWithMixedInputsCreation.createAndSynchronize()
		targetModelsProvider.getModels.interfaceMethodWithMixedInputsCreation.check()
	}

	// Mixed input and return types

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void interfaceMethodWithMixedInputsAndReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.interfaceMethodWithMixedInputsAndReturnCreation.createAndSynchronize()
		targetModelsProvider.getModels.interfaceMethodWithMixedInputsAndReturnCreation.check()
	}

	// Multiple methods

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void multipleInterfaceMethodsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.multipleInterfaceMethodsCreation.createAndSynchronize()
		targetModelsProvider.getModels.multipleInterfaceMethodsCreation.check()
	}

	// TODO rename
	// TODO parameter changes
}

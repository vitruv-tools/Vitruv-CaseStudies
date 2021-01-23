package tools.vitruv.applications.cbs.commonalities.tests.cbs

import java.util.List
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaOperationTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmOperationTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlOperationTestModels
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider

class OperationTest extends CBSCommonalitiesExecutionTest {

	static def List<Object[]> testParameters() {
		val domainModelsProviders = #[
			new PcmTestModelsProvider [new PcmOperationTestModels(it)],
			new UmlTestModelsProvider [new UmlOperationTestModels(it)],
			new JavaTestModelsProvider [new JavaOperationTestModels(it)]
		]
		return domainModelsProviders.orderedPairs
	}

	interface DomainModels {

		static val INTERFACE_NAME = 'SomeInterface'
		static val OPERATION_NAME = 'SomeOperation'
		static val OPERATION_2_NAME = 'SomeOtherOperation'
		static val INTEGER_PARAMETER_NAME = 'integerInput'
		static val BOOLEAN_PARAMETER_NAME = 'booleanInput'
		static val DOUBLE_PARAMETER_NAME = 'doubleInput'
		static val STRING_PARAMETER_NAME = 'stringInput'

		// Empty

		def DomainModel emptyOperationCreation()

		// Return type

		/**
		 * Operation with integer return type and no inputs.
		 */
		def DomainModel operationWithIntegerReturnCreation()

		def DomainModel operationWithStringReturnCreation()

		// Input parameters

		def DomainModel operationWithIntegerInputCreation()

		/**
		 * Operation with a boolean, integer and double input parameter.
		 */
		def DomainModel operationWithMultiplePrimitiveInputsCreation()

		def DomainModel operationWithStringInputCreation()

		// Mixed input and return types

		/**
		 * Operation with an integer and String input parameter and an integer
		 * return type.
		 */
		def DomainModel operationWithMixedInputsAndReturnCreation()

		// Multiple operations

		/**
		 * Both operations have an integer return type. The first operation has
		 * a boolean input parameter, the second has a String input parameter.
		 */
		def DomainModel multipleOperationsCreation()
	}

	// Empty

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void emptyOperationCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.emptyOperationCreation.createAndSynchronize()
		targetModelsProvider.getModels.emptyOperationCreation.check()
	}

	// Return type

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void operationWithIntegerReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.operationWithIntegerReturnCreation.createAndSynchronize()
		targetModelsProvider.getModels.operationWithIntegerReturnCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void operationWithStringReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.operationWithStringReturnCreation.createAndSynchronize()
		targetModelsProvider.getModels.operationWithStringReturnCreation.check()
	}

	// Input parameters

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void operationWithIntegerInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.operationWithIntegerInputCreation.createAndSynchronize()
		targetModelsProvider.getModels.operationWithIntegerInputCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void operationWithMultiplePrimitiveInputsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.operationWithMultiplePrimitiveInputsCreation.createAndSynchronize()
		targetModelsProvider.getModels.operationWithMultiplePrimitiveInputsCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void operationWithStringInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.operationWithStringInputCreation.createAndSynchronize()
		targetModelsProvider.getModels.operationWithStringInputCreation.check()
	}

	// Mixed input and return types

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void operationWithMixedInputsAndReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.operationWithMixedInputsAndReturnCreation.createAndSynchronize()
		targetModelsProvider.getModels.operationWithMixedInputsAndReturnCreation.check()
	}

	// Multiple operations

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void multipleOperationsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.multipleOperationsCreation.createAndSynchronize()
		targetModelsProvider.getModels.multipleOperationsCreation.check()
	}

	// TODO renaming
}

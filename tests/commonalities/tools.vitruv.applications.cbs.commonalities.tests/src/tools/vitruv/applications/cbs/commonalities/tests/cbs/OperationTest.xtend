package tools.vitruv.applications.cbs.commonalities.tests.cbs

import java.util.List
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaOperationTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmOperationTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlOperationTestModels
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.runner.XtextParametersRunnerFactory
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.*

@RunWith(Parameterized)
@Parameterized.UseParametersRunnerFactory(XtextParametersRunnerFactory)
class OperationTest extends CBSCommonalitiesExecutionTest {

	@Parameterized.Parameters(name='{0} to {1}')
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

	val DomainModels sourceModels
	val DomainModels targetModels

	new(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		this.sourceModels = sourceModelsProvider.getModels(vitruvApplicationTestAdapter)
		this.targetModels = targetModelsProvider.getModels(vitruvApplicationTestAdapter)
	}

	// Empty

	@Test
	def void emptyOperationCreation() {
		sourceModels.emptyOperationCreation.createAndSynchronize()
		targetModels.emptyOperationCreation.check()
	}

	// Return type

	@Test
	def void operationWithIntegerReturnCreation() {
		sourceModels.operationWithIntegerReturnCreation.createAndSynchronize()
		targetModels.operationWithIntegerReturnCreation.check()
	}

	@Test
	def void operationWithStringReturnCreation() {
		sourceModels.operationWithStringReturnCreation.createAndSynchronize()
		targetModels.operationWithStringReturnCreation.check()
	}

	// Input parameters

	@Test
	def void operationWithIntegerInputCreation() {
		sourceModels.operationWithIntegerInputCreation.createAndSynchronize()
		targetModels.operationWithIntegerInputCreation.check()
	}

	@Test
	def void operationWithMultiplePrimitiveInputsCreation() {
		sourceModels.operationWithMultiplePrimitiveInputsCreation.createAndSynchronize()
		targetModels.operationWithMultiplePrimitiveInputsCreation.check()
	}

	@Test
	def void operationWithStringInputCreation() {
		sourceModels.operationWithStringInputCreation.createAndSynchronize()
		targetModels.operationWithStringInputCreation.check()
	}

	// Mixed input and return types

	@Test
	def void operationWithMixedInputsAndReturnCreation() {
		sourceModels.operationWithMixedInputsAndReturnCreation.createAndSynchronize()
		targetModels.operationWithMixedInputsAndReturnCreation.check()
	}

	// Multiple operations

	@Test
	def void multipleOperationsCreation() {
		sourceModels.multipleOperationsCreation.createAndSynchronize()
		targetModels.multipleOperationsCreation.check()
	}

	// TODO renaming
}

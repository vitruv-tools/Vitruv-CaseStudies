package tools.vitruv.applications.cbs.commonalities.tests.oo

import java.util.List
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaInterfaceMethodTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlInterfaceMethodTestModels
import tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.runner.XtextParametersRunnerFactory

@RunWith(Parameterized)
@Parameterized.UseParametersRunnerFactory(XtextParametersRunnerFactory)
class InterfaceMethodTest extends CBSCommonalitiesExecutionTest {

	@Parameterized.Parameters(name='{0} to {1}')
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

		// Return parameter

		/**
		 * Interface method with integer return parameter and no inputs.
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

		// Mixed inputs and return types

		/**
		 * Interface method with an integer, String and class input parameter
		 * and an integer return parameter.
		 */
		def DomainModel interfaceMethodWithMixedInputsAndReturnCreation()

		// Multiple methods

		/**
		 * Both methods have an integer return type. The first method has a
		 * boolean input parameter, the second has a String input parameter.
		 */
		def DomainModel multipleInterfaceMethodsCreation()
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
	def void basicInterfaceMethodCreation() {
		sourceModels.basicInterfaceMethodCreation.createAndSynchronize()
		targetModels.basicInterfaceMethodCreation.check()
	}

	// Static

	@Test
	def void staticInterfaceMethodCreation() {
		sourceModels.staticInterfaceMethodCreation.createAndSynchronize()
		targetModels.staticInterfaceMethodCreation.check()
	}

	// Return parameter

	@Test
	def void interfaceMethodWithIntegerReturnCreation() {
		sourceModels.interfaceMethodWithIntegerReturnCreation.createAndSynchronize()
		targetModels.interfaceMethodWithIntegerReturnCreation.check()
	}

	@Test
	def void interfaceMethodWithStringReturnCreation() {
		sourceModels.interfaceMethodWithStringReturnCreation.createAndSynchronize()
		targetModels.interfaceMethodWithStringReturnCreation.check()
	}

	@Test
	def void interfaceMethodWithClassReturnCreation() {
		sourceModels.interfaceMethodWithClassReturnCreation.createAndSynchronize()
		targetModels.interfaceMethodWithClassReturnCreation.check()
	}

	@Test
	def void interfaceMethodWithSelfReturnCreation() {
		sourceModels.interfaceMethodWithSelfReturnCreation.createAndSynchronize()
		targetModels.interfaceMethodWithSelfReturnCreation.check()
	}

	// Input parameters

	@Test
	def void interfaceMethodWithIntegerInputCreation() {
		sourceModels.interfaceMethodWithIntegerInputCreation.createAndSynchronize()
		targetModels.interfaceMethodWithIntegerInputCreation.check()
	}

	@Test
	def void interfaceMethodWithMultiplePrimitiveInputsCreation() {
		sourceModels.interfaceMethodWithMultiplePrimitiveInputsCreation.createAndSynchronize()
		targetModels.interfaceMethodWithMultiplePrimitiveInputsCreation.check()
	}

	@Test
	def void interfaceMethodWithStringInputCreation() {
		sourceModels.interfaceMethodWithStringInputCreation.createAndSynchronize()
		targetModels.interfaceMethodWithStringInputCreation.check()
	}

	@Test
	def void interfaceMethodWithClassInputCreation() {
		sourceModels.interfaceMethodWithClassInputCreation.createAndSynchronize()
		targetModels.interfaceMethodWithClassInputCreation.check()
	}

	@Test
	def void interfaceMethodWithSelfInputCreation() {
		sourceModels.interfaceMethodWithSelfInputCreation.createAndSynchronize()
		targetModels.interfaceMethodWithSelfInputCreation.check()
	}

	@Test
	def void interfaceMethodWithMixedInputsCreation() {
		sourceModels.interfaceMethodWithMixedInputsCreation.createAndSynchronize()
		targetModels.interfaceMethodWithMixedInputsCreation.check()
	}

	// Mixed inputs and return types

	@Test
	def void interfaceMethodWithMixedInputsAndReturnCreation() {
		sourceModels.interfaceMethodWithMixedInputsAndReturnCreation.createAndSynchronize()
		targetModels.interfaceMethodWithMixedInputsAndReturnCreation.check()
	}

	// Multiple methods

	@Test
	def void multipleInterfaceMethodsCreation() {
		sourceModels.multipleInterfaceMethodsCreation.createAndSynchronize()
		targetModels.multipleInterfaceMethodsCreation.check()
	}

	// TODO rename
	// TODO parameter changes
}

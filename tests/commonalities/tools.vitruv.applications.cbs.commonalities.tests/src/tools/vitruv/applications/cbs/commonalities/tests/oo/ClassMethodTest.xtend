package tools.vitruv.applications.cbs.commonalities.tests.oo

import java.util.List
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaClassMethodTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlClassMethodTestModels
import tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.runner.XtextParametersRunnerFactory

@RunWith(Parameterized)
@Parameterized.UseParametersRunnerFactory(XtextParametersRunnerFactory)
class ClassMethodTest extends CBSCommonalitiesExecutionTest {

	@Parameterized.Parameters(name='{0} to {1}')
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

		// Return parameter

		/**
		 * Class method with integer return parameter and no inputs.
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

		// Mixed inputs and return types

		/**
		 * Class method with an integer, String and class input parameter and
		 * an integer return parameter.
		 */
		def DomainModel classMethodWithMixedInputsAndReturnCreation()

		// Multiple methods

		/**
		 * Both methods have an integer return type. The first method has a
		 * boolean input parameter, the second has a String input parameter.
		 */
		def DomainModel multipleClassMethodsCreation()
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
	def void basicClassMethodCreation() {
		sourceModels.basicClassMethodCreation.createAndSynchronize()
		targetModels.basicClassMethodCreation.check()
	}

	// Visibility

	@Test
	def void publicClassMethodCreation() {
		sourceModels.publicClassMethodCreation.createAndSynchronize()
		targetModels.publicClassMethodCreation.check()
	}

	@Test
	def void protectedClassMethodCreation() {
		sourceModels.protectedClassMethodCreation.createAndSynchronize()
		targetModels.protectedClassMethodCreation.check()
	}

	@Test
	def void packagePrivateClassMethodCreation() {
		sourceModels.packagePrivateClassMethodCreation.createAndSynchronize()
		targetModels.packagePrivateClassMethodCreation.check()
	}

	@Test
	def void privateClassMethodCreation() {
		sourceModels.privateClassMethodCreation.createAndSynchronize()
		targetModels.privateClassMethodCreation.check()
	}

	// Return parameter

	@Test
	def void classMethodWithIntegerReturnCreation() {
		sourceModels.classMethodWithIntegerReturnCreation.createAndSynchronize()
		targetModels.classMethodWithIntegerReturnCreation.check()
	}

	@Test
	def void classMethodWithStringReturnCreation() {
		sourceModels.classMethodWithStringReturnCreation.createAndSynchronize()
		targetModels.classMethodWithStringReturnCreation.check()
	}

	@Test
	def void classMethodWithClassReturnCreation() {
		sourceModels.classMethodWithClassReturnCreation.createAndSynchronize()
		targetModels.classMethodWithClassReturnCreation.check()
	}

	@Test
	def void classMethodWithSelfReturnCreation() {
		sourceModels.classMethodWithSelfReturnCreation.createAndSynchronize()
		targetModels.classMethodWithSelfReturnCreation.check()
	}

	// Input parameters

	@Test
	def void classMethodWithIntegerInputCreation() {
		sourceModels.classMethodWithIntegerInputCreation.createAndSynchronize()
		targetModels.classMethodWithIntegerInputCreation.check()
	}

	@Test
	def void classMethodWithMultiplePrimitiveInputsCreation() {
		sourceModels.classMethodWithMultiplePrimitiveInputsCreation.createAndSynchronize()
		targetModels.classMethodWithMultiplePrimitiveInputsCreation.check()
	}

	@Test
	def void classMethodWithStringInputCreation() {
		sourceModels.classMethodWithStringInputCreation.createAndSynchronize()
		targetModels.classMethodWithStringInputCreation.check()
	}

	@Test
	def void classMethodWithClassInputCreation() {
		sourceModels.classMethodWithClassInputCreation.createAndSynchronize()
		targetModels.classMethodWithClassInputCreation.check()
	}

	@Test
	def void classMethodWithSelfInputCreation() {
		sourceModels.classMethodWithSelfInputCreation.createAndSynchronize()
		targetModels.classMethodWithSelfInputCreation.check()
	}

	@Test
	def void classMethodWithMixedInputsCreation() {
		sourceModels.classMethodWithMixedInputsCreation.createAndSynchronize()
		targetModels.classMethodWithMixedInputsCreation.check()
	}

	// Mixed inputs and return types

	@Test
	def void classMethodWithMixedInputsAndReturnCreation() {
		sourceModels.classMethodWithMixedInputsAndReturnCreation.createAndSynchronize()
		targetModels.classMethodWithMixedInputsAndReturnCreation.check()
	}

	// Multiple methods

	@Test
	def void multipleInterfaceMethodsCreation() {
		sourceModels.multipleClassMethodsCreation.createAndSynchronize()
		targetModels.multipleClassMethodsCreation.check()
	}

	// TODO rename
	// TODO parameter changes
}

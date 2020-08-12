package tools.vitruv.applications.cbs.commonalities.tests.oo

import java.util.List
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaConstructorTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlConstructorTestModels
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.runner.XtextParametersRunnerFactory
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider

@RunWith(Parameterized)
@Parameterized.UseParametersRunnerFactory(XtextParametersRunnerFactory)
class ConstructorTest extends CBSCommonalitiesExecutionTest {

	@Parameterized.Parameters(name='{0} to {1}')
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

	val DomainModels sourceModels
	val DomainModels targetModels

	new(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		this.sourceModels = sourceModelsProvider.getModels(vitruvApplicationTestAdapter)
		this.targetModels = targetModelsProvider.getModels(vitruvApplicationTestAdapter)
	}

	// Basic

	@Test
	def void basicConstructorCreation() {
		sourceModels.basicConstructorCreation.createAndSynchronize()
		targetModels.basicConstructorCreation.check()
	}

	// Visibility

	@Test
	def void publicConstructorCreation() {
		sourceModels.publicConstructorCreation.createAndSynchronize()
		targetModels.publicConstructorCreation.check()
	}

	@Test
	def void protectedConstructorCreation() {
		sourceModels.protectedConstructorCreation.createAndSynchronize()
		targetModels.protectedConstructorCreation.check()
	}

	@Test
	def void packagePrivateConstructorCreation() {
		sourceModels.packagePrivateConstructorCreation.createAndSynchronize()
		targetModels.packagePrivateConstructorCreation.check()
	}

	@Test
	def void privateConstructorCreation() {
		sourceModels.privateConstructorCreation.createAndSynchronize()
		targetModels.privateConstructorCreation.check()
	}

	// Input parameters

	@Test
	def void constructorWithIntegerInputCreation() {
		sourceModels.constructorWithIntegerInputCreation.createAndSynchronize()
		targetModels.constructorWithIntegerInputCreation.check()
	}

	@Test
	def void constructorWithMultiplePrimitiveInputsCreation() {
		sourceModels.constructorWithMultiplePrimitiveInputsCreation.createAndSynchronize()
		targetModels.constructorWithMultiplePrimitiveInputsCreation.check()
	}

	@Test
	def void constructorWithStringInputCreation() {
		sourceModels.constructorWithStringInputCreation.createAndSynchronize()
		targetModels.constructorWithStringInputCreation.check()
	}

	@Test
	def void constructorWithClassInputCreation() {
		sourceModels.constructorWithClassInputCreation.createAndSynchronize()
		targetModels.constructorWithClassInputCreation.check()
	}

	@Test
	def void constructorWithSelfInputCreation() {
		sourceModels.constructorWithSelfInputCreation.createAndSynchronize()
		targetModels.constructorWithSelfInputCreation.check()
	}

	@Test
	def void constructorWithMixedInputsCreation() {
		sourceModels.constructorWithMixedInputsCreation.createAndSynchronize()
		targetModels.constructorWithMixedInputsCreation.check()
	}

	// Multiple constructors

	// TODO Does not work yet in the UML->Java direction. Problem: The created Java constructors are first empty. When
	// adding parameters, we retrieve the target constructor via the correspondence model. However, Java's TUIDs for
	// constructors without parameters are the same. Result: All parameters get added to the same single constructor.
	@Ignore
	@Test
	def void multipleConstructorsCreation() {
		sourceModels.multipleConstructorsCreation.createAndSynchronize()
		targetModels.multipleConstructorsCreation.check()
	}
}

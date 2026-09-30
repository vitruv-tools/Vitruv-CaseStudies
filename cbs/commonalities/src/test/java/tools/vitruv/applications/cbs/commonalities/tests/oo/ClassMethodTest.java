package tools.vitruv.applications.cbs.commonalities.tests.oo;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest;
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaClassMethodTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlClassMethodTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider;

public class ClassMethodTest extends CBSCommonalitiesExecutionTest {

	public static List<Object[]> testParameters() {
		return List.of(
				new Object[] {
						new UmlTestModelsProvider<DomainModels>(UmlClassMethodTestModels::new),
						new JavaTestModelsProvider<DomainModels>(JavaClassMethodTestModels::new)
				},
				new Object[] {
						new JavaTestModelsProvider<DomainModels>(JavaClassMethodTestModels::new),
						new UmlTestModelsProvider<DomainModels>(UmlClassMethodTestModels::new)
				});
	}

	/**
	 * If not specified otherwise by the individual test cases, all created
	 * classes and methods are of public visibility.
	 */
	public interface DomainModels {

		String PACKAGE_NAME = "root";
		String CLASS_NAME = "Foo";
		String METHOD_NAME = "someMethod";
		String METHOD_2_NAME = "someMethod2";
		String OTHER_CLASS_NAME = "SomeClass";
		String INTEGER_PARAMETER_NAME = "integerInput";
		String BOOLEAN_PARAMETER_NAME = "booleanInput";
		String DOUBLE_PARAMETER_NAME = "doubleInput";
		String STRING_PARAMETER_NAME = "stringInput";
		String CLASS_PARAMETER_NAME = "classInput";
		String OWN_TYPE_PARAMETER_NAME = "ownTypeInput";

		// Basic

		/**
		 * A method with only the minimally required attributes (i.e. without
		 * input or return parameters).
		 */
		DomainModel basicClassMethodCreation();

		// Visibility

		DomainModel publicClassMethodCreation();

		DomainModel protectedClassMethodCreation();

		DomainModel packagePrivateClassMethodCreation();

		DomainModel privateClassMethodCreation();

		// Modifiers

		DomainModel finalClassMethodCreation();

		DomainModel abstractClassMethodCreation();

		DomainModel staticClassMethodCreation();

		// Return type

		/**
		 * Class method with integer return type and no inputs.
		 */
		DomainModel classMethodWithIntegerReturnCreation();

		DomainModel classMethodWithStringReturnCreation();

		DomainModel classMethodWithClassReturnCreation();

		/**
		 * Class method which has the containing class as return type.
		 */
		DomainModel classMethodWithSelfReturnCreation();

		// Input parameters

		DomainModel classMethodWithIntegerInputCreation();

		/**
		 * Class method with a boolean, integer and double input parameter.
		 */
		DomainModel classMethodWithMultiplePrimitiveInputsCreation();

		DomainModel classMethodWithStringInputCreation();

		DomainModel classMethodWithClassInputCreation();

		/**
		 * Class method which has an input of the containing class' type.
		 */
		DomainModel classMethodWithSelfInputCreation();

		/**
		 * Class method with an integer, String and class input parameter.
		 */
		DomainModel classMethodWithMixedInputsCreation();

		// Mixed input and return types

		/**
		 * Class method with an integer, String and class input parameter and
		 * an integer return type.
		 */
		DomainModel classMethodWithMixedInputsAndReturnCreation();

		// Multiple methods

		/**
		 * Both methods have an integer return type. The first method has a
		 * boolean input parameter, the second has a String input parameter.
		 */
		DomainModel multipleClassMethodsCreation();
	}

	// Basic

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void basicClassMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).basicClassMethodCreation().createAndSynchronize();
		getModels(targetModelsProvider).basicClassMethodCreation().check();
	}

	// Visibility

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void publicClassMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).publicClassMethodCreation().createAndSynchronize();
		getModels(targetModelsProvider).publicClassMethodCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void protectedClassMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).protectedClassMethodCreation().createAndSynchronize();
		getModels(targetModelsProvider).protectedClassMethodCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void packagePrivateClassMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).packagePrivateClassMethodCreation().createAndSynchronize();
		getModels(targetModelsProvider).packagePrivateClassMethodCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void privateClassMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).privateClassMethodCreation().createAndSynchronize();
		getModels(targetModelsProvider).privateClassMethodCreation().check();
	}

	// Modifiers

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void finalClassMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).finalClassMethodCreation().createAndSynchronize();
		getModels(targetModelsProvider).finalClassMethodCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void abstractClassMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).abstractClassMethodCreation().createAndSynchronize();
		getModels(targetModelsProvider).abstractClassMethodCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void staticClassMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).staticClassMethodCreation().createAndSynchronize();
		getModels(targetModelsProvider).staticClassMethodCreation().check();
	}

	// Return type

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void IntegerReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).classMethodWithIntegerReturnCreation().createAndSynchronize();
		getModels(targetModelsProvider).classMethodWithIntegerReturnCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void classMethodWithStringReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).classMethodWithStringReturnCreation().createAndSynchronize();
		getModels(targetModelsProvider).classMethodWithStringReturnCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void classMethodWithClassReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).classMethodWithClassReturnCreation().createAndSynchronize();
		getModels(targetModelsProvider).classMethodWithClassReturnCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void classMethodWithSelfReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).classMethodWithSelfReturnCreation().createAndSynchronize();
		getModels(targetModelsProvider).classMethodWithSelfReturnCreation().check();
	}

	// Input parameters

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void classMethodWithIntegerInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).classMethodWithIntegerInputCreation().createAndSynchronize();
		getModels(targetModelsProvider).classMethodWithIntegerInputCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void classMethodWithMultiplePrimitiveInputsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).classMethodWithMultiplePrimitiveInputsCreation().createAndSynchronize();
		getModels(targetModelsProvider).classMethodWithMultiplePrimitiveInputsCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void classMethodWithStringInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).classMethodWithStringInputCreation().createAndSynchronize();
		getModels(targetModelsProvider).classMethodWithStringInputCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void classMethodWithClassInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).classMethodWithClassInputCreation().createAndSynchronize();
		getModels(targetModelsProvider).classMethodWithClassInputCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void classMethodWithSelfInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).classMethodWithSelfInputCreation().createAndSynchronize();
		getModels(targetModelsProvider).classMethodWithSelfInputCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void classMethodWithMixedInputsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).classMethodWithMixedInputsCreation().createAndSynchronize();
		getModels(targetModelsProvider).classMethodWithMixedInputsCreation().check();
	}

	// Mixed input and return types

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void classMethodWithMixedInputsAndReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).classMethodWithMixedInputsAndReturnCreation().createAndSynchronize();
		getModels(targetModelsProvider).classMethodWithMixedInputsAndReturnCreation().check();
	}

	// Multiple methods

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void multipleClassMethodsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).multipleClassMethodsCreation().createAndSynchronize();
		getModels(targetModelsProvider).multipleClassMethodsCreation().check();
	}

	// TODO rename
	// TODO parameter changes
}

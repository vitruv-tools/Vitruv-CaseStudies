package tools.vitruv.applications.cbs.commonalities.tests.oo;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest;
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaInterfaceMethodTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlInterfaceMethodTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider;

public class InterfaceMethodTest extends CBSCommonalitiesExecutionTest {

	public static List<Object[]> testParameters() {
		return List.of(
				new Object[] {
						new UmlTestModelsProvider<DomainModels>(UmlInterfaceMethodTestModels::new),
						new JavaTestModelsProvider<DomainModels>(JavaInterfaceMethodTestModels::new)
				},
				new Object[] {
						new JavaTestModelsProvider<DomainModels>(JavaInterfaceMethodTestModels::new),
						new UmlTestModelsProvider<DomainModels>(UmlInterfaceMethodTestModels::new)
				});
	}

	/**
	 * If not specified otherwise by the individual test cases, all created
	 * interfaces and interface methods are of public visibility.
	 * <p>
	 * The class used in some test cases has public visibility as well.
	 */
	public interface DomainModels {

		String PACKAGE_NAME = "root";
		String INTERFACE_NAME = "Foo";
		String METHOD_NAME = "someMethod";
		String METHOD_2_NAME = "someMethod2";
		String OTHER_CLASS_NAME = "SomeOtherClass";
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
		DomainModel basicInterfaceMethodCreation();

		// Static

		DomainModel staticInterfaceMethodCreation();

		// Return type

		/**
		 * Interface method with integer return type and no inputs.
		 */
		DomainModel interfaceMethodWithIntegerReturnCreation();

		DomainModel interfaceMethodWithStringReturnCreation();

		DomainModel interfaceMethodWithClassReturnCreation();

		/**
		 * Interface method which has the containing interface as return type.
		 */
		DomainModel interfaceMethodWithSelfReturnCreation();

		// Input parameters

		DomainModel interfaceMethodWithIntegerInputCreation();

		/**
		 * Interface method with a boolean, integer and double input parameter.
		 */
		DomainModel interfaceMethodWithMultiplePrimitiveInputsCreation();

		DomainModel interfaceMethodWithStringInputCreation();

		DomainModel interfaceMethodWithClassInputCreation();

		/**
		 * Interface method which has an input of the containing interface's
		 * type.
		 */
		DomainModel interfaceMethodWithSelfInputCreation();

		/**
		 * Interface method with an integer, String and class input parameter.
		 */
		DomainModel interfaceMethodWithMixedInputsCreation();

		// Mixed input and return types

		/**
		 * Interface method with an integer, String and class input parameter
		 * and an integer return type.
		 */
		DomainModel interfaceMethodWithMixedInputsAndReturnCreation();

		// Multiple methods

		/**
		 * Both methods have an integer return type. The first method has a
		 * boolean input parameter, the second has a String input parameter.
		 */
		DomainModel multipleInterfaceMethodsCreation();
	}

	// Basic

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void basicInterfaceMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).basicInterfaceMethodCreation().createAndSynchronize();
		getModels(targetModelsProvider).basicInterfaceMethodCreation().check();
	}

	// Static

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void staticInterfaceMethodCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).staticInterfaceMethodCreation().createAndSynchronize();
		getModels(targetModelsProvider).staticInterfaceMethodCreation().check();
	}

	// Return type

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void interfaceMethodWithIntegerReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).interfaceMethodWithIntegerReturnCreation().createAndSynchronize();
		getModels(targetModelsProvider).interfaceMethodWithIntegerReturnCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void interfaceMethodWithStringReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).interfaceMethodWithStringReturnCreation().createAndSynchronize();
		getModels(targetModelsProvider).interfaceMethodWithStringReturnCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void interfaceMethodWithClassReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).interfaceMethodWithClassReturnCreation().createAndSynchronize();
		getModels(targetModelsProvider).interfaceMethodWithClassReturnCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void interfaceMethodWithSelfReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).interfaceMethodWithSelfReturnCreation().createAndSynchronize();
		getModels(targetModelsProvider).interfaceMethodWithSelfReturnCreation().check();
	}

	// Input parameters

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void interfaceMethodWithIntegerInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).interfaceMethodWithIntegerInputCreation().createAndSynchronize();
		getModels(targetModelsProvider).interfaceMethodWithIntegerInputCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void interfaceMethodWithMultiplePrimitiveInputsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).interfaceMethodWithMultiplePrimitiveInputsCreation().createAndSynchronize();
		getModels(targetModelsProvider).interfaceMethodWithMultiplePrimitiveInputsCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void interfaceMethodWithStringInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).interfaceMethodWithStringInputCreation().createAndSynchronize();
		getModels(targetModelsProvider).interfaceMethodWithStringInputCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void interfaceMethodWithClassInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).interfaceMethodWithClassInputCreation().createAndSynchronize();
		getModels(targetModelsProvider).interfaceMethodWithClassInputCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void interfaceMethodWithSelfInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).interfaceMethodWithSelfInputCreation().createAndSynchronize();
		getModels(targetModelsProvider).interfaceMethodWithSelfInputCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void interfaceMethodWithMixedInputsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).interfaceMethodWithMixedInputsCreation().createAndSynchronize();
		getModels(targetModelsProvider).interfaceMethodWithMixedInputsCreation().check();
	}

	// Mixed input and return types

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void interfaceMethodWithMixedInputsAndReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).interfaceMethodWithMixedInputsAndReturnCreation().createAndSynchronize();
		getModels(targetModelsProvider).interfaceMethodWithMixedInputsAndReturnCreation().check();
	}

	// Multiple methods

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void multipleInterfaceMethodsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).multipleInterfaceMethodsCreation().createAndSynchronize();
		getModels(targetModelsProvider).multipleInterfaceMethodsCreation().check();
	}

	// TODO rename
	// TODO parameter changes
}

package tools.vitruv.applications.cbs.commonalities.tests.oo;

import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest;
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaConstructorTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlConstructorTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider;

public class ConstructorTest extends CBSCommonalitiesExecutionTest {

	public static List<Object[]> testParameters() {
		return List.of(
				new Object[] {
						new UmlTestModelsProvider<DomainModels>(UmlConstructorTestModels::new),
						new JavaTestModelsProvider<DomainModels>(JavaConstructorTestModels::new)
				},
				new Object[] {
						new JavaTestModelsProvider<DomainModels>(JavaConstructorTestModels::new),
						new UmlTestModelsProvider<DomainModels>(UmlConstructorTestModels::new)
				});
	}

	/**
	 * If not specified otherwise by the individual test cases, all created
	 * classes and constructors are of public visibility.
	 */
	public interface DomainModels {

		String PACKAGE_NAME = "root";
		String CLASS_NAME = "Foo";
		String OTHER_CLASS_NAME = "SomeClass";
		String INTEGER_PARAMETER_NAME = "integerInput";
		String BOOLEAN_PARAMETER_NAME = "booleanInput";
		String DOUBLE_PARAMETER_NAME = "doubleInput";
		String STRING_PARAMETER_NAME = "stringInput";
		String CLASS_PARAMETER_NAME = "classInput";
		String OWN_TYPE_PARAMETER_NAME = "ownTypeInput";

		// Basic

		/**
		 * A constructor with only the minimally required attributes (i.e.
		 * without input parameters).
		 */
		DomainModel basicConstructorCreation();

		// Visibility

		DomainModel publicConstructorCreation();

		DomainModel protectedConstructorCreation();

		DomainModel packagePrivateConstructorCreation();

		DomainModel privateConstructorCreation();

		// Input parameters

		DomainModel constructorWithIntegerInputCreation();

		/**
		 * Constructor with a boolean, integer and double input parameter.
		 */
		DomainModel constructorWithMultiplePrimitiveInputsCreation();

		DomainModel constructorWithStringInputCreation();

		DomainModel constructorWithClassInputCreation();

		/**
		 * Constructor which has an input of the containing class' type.
		 */
		DomainModel constructorWithSelfInputCreation();

		/**
		 * Constructor with an integer, String and class input parameter.
		 */
		DomainModel constructorWithMixedInputsCreation();

		// Multiple constructors

		/**
		 * The first constructor has a boolean input parameter, the second has
		 * a String input parameter.
		 */
		DomainModel multipleConstructorsCreation();
	}

	// Basic

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void basicConstructorCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).basicConstructorCreation().createAndSynchronize();
		getModels(targetModelsProvider).basicConstructorCreation().check();
	}

	// Visibility

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void publicConstructorCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).publicConstructorCreation().createAndSynchronize();
		getModels(targetModelsProvider).publicConstructorCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void protectedConstructorCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).protectedConstructorCreation().createAndSynchronize();
		getModels(targetModelsProvider).protectedConstructorCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void packagePrivateConstructorCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).packagePrivateConstructorCreation().createAndSynchronize();
		getModels(targetModelsProvider).packagePrivateConstructorCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void privateConstructorCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).privateConstructorCreation().createAndSynchronize();
		getModels(targetModelsProvider).privateConstructorCreation().check();
	}

	// Input parameters

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void constructorWithIntegerInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).constructorWithIntegerInputCreation().createAndSynchronize();
		getModels(targetModelsProvider).constructorWithIntegerInputCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void constructorWithMultiplePrimitiveInputsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).constructorWithMultiplePrimitiveInputsCreation().createAndSynchronize();
		getModels(targetModelsProvider).constructorWithMultiplePrimitiveInputsCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void constructorWithStringInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).constructorWithStringInputCreation().createAndSynchronize();
		getModels(targetModelsProvider).constructorWithStringInputCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void constructorWithClassInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).constructorWithClassInputCreation().createAndSynchronize();
		getModels(targetModelsProvider).constructorWithClassInputCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void constructorWithSelfInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).constructorWithSelfInputCreation().createAndSynchronize();
		getModels(targetModelsProvider).constructorWithSelfInputCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void constructorWithMixedInputsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).constructorWithMixedInputsCreation().createAndSynchronize();
		getModels(targetModelsProvider).constructorWithMixedInputsCreation().check();
	}

	// Multiple constructors

	// TODO Does not work yet in the UML->Java direction. Problem: The created Java constructors are first empty. When
	// adding parameters, we retrieve the target constructor via the correspondence model. However, Java's TUIDs for
	// constructors without parameters are the same. Result: All parameters get added to the same single constructor.
	@Disabled
	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void multipleConstructorsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).multipleConstructorsCreation().createAndSynchronize();
		getModels(targetModelsProvider).multipleConstructorsCreation().check();
	}
}

package tools.vitruv.applications.cbs.commonalities.tests.cbs;

import static tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.orderedPairs;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaOperationTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmOperationTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlOperationTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider;

public class OperationTest extends CBSCommonalitiesExecutionTest {

    public static List<Object[]> testParameters() {
        List<DomainModelsProvider<DomainModels>> domainModelsProviders = List.of(
                new PcmTestModelsProvider<DomainModels>(PcmOperationTestModels::new),
                new UmlTestModelsProvider<DomainModels>(UmlOperationTestModels::new),
                new JavaTestModelsProvider<DomainModels>(JavaOperationTestModels::new));
        return orderedPairs(domainModelsProviders);
    }

    public interface DomainModels {

        String INTERFACE_NAME = "SomeInterface";
        String OPERATION_NAME = "SomeOperation";
        String OPERATION_2_NAME = "SomeOtherOperation";
        String INTEGER_PARAMETER_NAME = "integerInput";
        String BOOLEAN_PARAMETER_NAME = "booleanInput";
        String DOUBLE_PARAMETER_NAME = "doubleInput";
        String STRING_PARAMETER_NAME = "stringInput";

        // Empty

        DomainModel emptyOperationCreation();

        // Return type

        /**
         * Operation with integer return type and no inputs.
         */
        DomainModel operationWithIntegerReturnCreation();

        DomainModel operationWithStringReturnCreation();

        // Input parameters

        DomainModel operationWithIntegerInputCreation();

        /**
         * Operation with a boolean, integer and double input parameter.
         */
        DomainModel operationWithMultiplePrimitiveInputsCreation();

        DomainModel operationWithStringInputCreation();

        // Mixed input and return types

        /**
         * Operation with an integer and String input parameter and an integer
         * return type.
         */
        DomainModel operationWithMixedInputsAndReturnCreation();

        // Multiple operations

        /**
         * Both operations have an integer return type. The first operation has
         * a boolean input parameter, the second has a String input parameter.
         */
        DomainModel multipleOperationsCreation();
    }

    // Empty

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void emptyOperationCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).emptyOperationCreation().createAndSynchronize();
        getModels(targetModelsProvider).emptyOperationCreation().check();
    }

    // Return type

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void operationWithIntegerReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).operationWithIntegerReturnCreation().createAndSynchronize();
        getModels(targetModelsProvider).operationWithIntegerReturnCreation().check();
    }

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void operationWithStringReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).operationWithStringReturnCreation().createAndSynchronize();
        getModels(targetModelsProvider).operationWithStringReturnCreation().check();
    }

    // Input parameters

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void operationWithIntegerInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).operationWithIntegerInputCreation().createAndSynchronize();
        getModels(targetModelsProvider).operationWithIntegerInputCreation().check();
    }

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void operationWithMultiplePrimitiveInputsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).operationWithMultiplePrimitiveInputsCreation().createAndSynchronize();
        getModels(targetModelsProvider).operationWithMultiplePrimitiveInputsCreation().check();
    }

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void operationWithStringInputCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).operationWithStringInputCreation().createAndSynchronize();
        getModels(targetModelsProvider).operationWithStringInputCreation().check();
    }

    // Mixed input and return types

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void operationWithMixedInputsAndReturnCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).operationWithMixedInputsAndReturnCreation().createAndSynchronize();
        getModels(targetModelsProvider).operationWithMixedInputsAndReturnCreation().check();
    }

    // Multiple operations

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void multipleOperationsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).multipleOperationsCreation().createAndSynchronize();
        getModels(targetModelsProvider).multipleOperationsCreation().check();
    }

    // TODO renaming
}

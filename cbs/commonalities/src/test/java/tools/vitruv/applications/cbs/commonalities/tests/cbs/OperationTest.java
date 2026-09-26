package tools.vitruv.applications.cbs.commonalities.tests.cbs;

import java.util.ArrayList;
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

import static tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.orderedPairs;

public class OperationTest extends CBSCommonalitiesExecutionTest {

    public static List<Object[]> testParameters() {
        List<DomainModelsProvider<DomainModels>> domainModelsProviders = new ArrayList<>();
        domainModelsProviders.add(new PcmTestModelsProvider<>(context -> new PcmOperationTestModels(context)));
        domainModelsProviders.add(new UmlTestModelsProvider<>(context -> new UmlOperationTestModels(context)));
        domainModelsProviders.add(new JavaTestModelsProvider<>(context -> new JavaOperationTestModels(context)));
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

        DomainModel emptyOperationCreation();

        /**
         * Operation with integer return type and no inputs.
         */
        DomainModel operationWithIntegerReturnCreation();

        DomainModel operationWithStringReturnCreation();

        DomainModel operationWithIntegerInputCreation();

        /**
         * Operation with a boolean, integer and double input parameter.
         */
        DomainModel operationWithMultiplePrimitiveInputsCreation();

        DomainModel operationWithStringInputCreation();

        /**
         * Operation with an integer and String input parameter and an integer
         * return type.
         */
        DomainModel operationWithMixedInputsAndReturnCreation();

        /**
         * Both operations have an integer return type. The first operation has
         * a boolean input parameter, the second has a String input parameter.
         */
        DomainModel multipleOperationsCreation();
    }

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void emptyOperationCreation(
            DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        sourceModelsProvider.getModels(this.getVitruvApplicationTestAdapter()).emptyOperationCreation()
                .createAndSynchronize();
        targetModelsProvider.getModels(this.getVitruvApplicationTestAdapter()).emptyOperationCreation().check();
    }

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void operationWithIntegerReturnCreation(
            DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        sourceModelsProvider.getModels(this.getVitruvApplicationTestAdapter()).operationWithIntegerReturnCreation()
                .createAndSynchronize();
        targetModelsProvider.getModels(this.getVitruvApplicationTestAdapter()).operationWithIntegerReturnCreation()
                .check();
    }

    // ...remaining test methods follow the same pattern...

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void multipleOperationsCreation(
            DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        sourceModelsProvider.getModels(this.getVitruvApplicationTestAdapter()).multipleOperationsCreation()
                .createAndSynchronize();
        targetModelsProvider.getModels(this.getVitruvApplicationTestAdapter()).multipleOperationsCreation().check();
    }

    // TODO renaming
}
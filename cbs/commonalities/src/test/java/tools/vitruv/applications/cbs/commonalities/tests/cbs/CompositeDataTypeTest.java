package tools.vitruv.applications.cbs.commonalities.tests.cbs;

import static tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.orderedPairs;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaCompositeDataTypeTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmCompositeDataTypeTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlCompositeDataTypeTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider;

public class CompositeDataTypeTest extends CBSCommonalitiesExecutionTest {

    public static List<Object[]> testParameters() {
        List<DomainModelsProvider<DomainModels>> domainModelsProviders = List.of(
                new PcmTestModelsProvider<DomainModels>(PcmCompositeDataTypeTestModels::new),
                new UmlTestModelsProvider<DomainModels>(UmlCompositeDataTypeTestModels::new),
                new JavaTestModelsProvider<DomainModels>(JavaCompositeDataTypeTestModels::new));
        return orderedPairs(domainModelsProviders);
    }

    public interface DomainModels {

        String COMPOSITE_DATATYPE_1_NAME = "CompositeDataType1";
        String COMPOSITE_DATATYPE_2_NAME = "CompositeDataType2";
        String BOOLEAN_ELEMENT_NAME = "booleanElement";
        String INTEGER_ELEMENT_NAME = "integerElement";
        String DOUBLE_ELEMENT_NAME = "doubleElement";
        String STRING_ELEMENT_NAME = "stringElement";
        String COMPOSITE_ELEMENT_1_NAME = "compositeElement1";
        String COMPOSITE_ELEMENT_2_NAME = "compositeElement2";

        // Empty CompositeDataType

        DomainModel emptyCompositeDataTypeCreation();

        // Primitive inner elements

        DomainModel compositeDataTypeWithBooleanElementCreation();
        DomainModel compositeDataTypeWithIntegerElementCreation();
        DomainModel compositeDataTypeWithDoubleElementCreation();
        DomainModel compositeDataTypeWithStringElementCreation();

        /**
         * A composite data type with elements for each of the following
         * primitive types:
         * <ul>
         * <li>Boolean
         * <li>Integer
         * <li>Double
         * <li>String
         * </ul>
         */
        DomainModel compositeDataTypeWithWithMultiplePrimitiveElementsCreation();

        // Multiple CompositeDataTypes

        /**
         * Two composite data types, one with a <code>boolean</code> typed
         * element and one with an <code>integer</code> typed element.
         */
        DomainModel multipleCompositeDataTypesWithPrimitiveElementsCreation();

        /**
         * A composite data type with an <code>integer</code> typed element and
         * another composite data type with two elements which each reference
         * the first composite data type as their type.
         */
        DomainModel compositeDataTypeWithCompositeElementsCreation();
    }

    // Empty CompositeDataType

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void emptyCompositeDataTypeCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).emptyCompositeDataTypeCreation().createAndSynchronize();
        getModels(targetModelsProvider).emptyCompositeDataTypeCreation().check();
    }

    // Primitive inner elements

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void compositeDataTypeWithBooleanElementCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).compositeDataTypeWithBooleanElementCreation().createAndSynchronize();
        getModels(targetModelsProvider).compositeDataTypeWithBooleanElementCreation().check();
    }

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void compositeDataTypeWithIntegerElementCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).compositeDataTypeWithIntegerElementCreation().createAndSynchronize();
        getModels(targetModelsProvider).compositeDataTypeWithIntegerElementCreation().check();
    }

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void compositeDataTypeWithDoubleElementCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).compositeDataTypeWithDoubleElementCreation().createAndSynchronize();
        getModels(targetModelsProvider).compositeDataTypeWithDoubleElementCreation().check();
    }

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void compositeDataTypeWithStringElementCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).compositeDataTypeWithStringElementCreation().createAndSynchronize();
        getModels(targetModelsProvider).compositeDataTypeWithStringElementCreation().check();
    }

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void compositeDataTypeWithWithMultiplePrimitiveElementsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).compositeDataTypeWithWithMultiplePrimitiveElementsCreation().createAndSynchronize();
        getModels(targetModelsProvider).compositeDataTypeWithWithMultiplePrimitiveElementsCreation().check();
    }

    // Multiple CompositeDataTypes

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void multipleCompositeDataTypesWithPrimitiveElementsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).multipleCompositeDataTypesWithPrimitiveElementsCreation().createAndSynchronize();
        getModels(targetModelsProvider).multipleCompositeDataTypesWithPrimitiveElementsCreation().check();
    }

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void compositeDataTypeWithCompositeElementsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).compositeDataTypeWithCompositeElementsCreation().createAndSynchronize();
        getModels(targetModelsProvider).compositeDataTypeWithCompositeElementsCreation().check();
    }

    // TODO renaming
}

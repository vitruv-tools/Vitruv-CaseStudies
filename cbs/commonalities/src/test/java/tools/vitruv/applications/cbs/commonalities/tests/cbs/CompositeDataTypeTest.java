package tools.vitruv.applications.cbs.commonalities.tests.cbs;

import java.util.ArrayList;
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

import static tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.orderedPairs;

public class CompositeDataTypeTest extends CBSCommonalitiesExecutionTest {

    public static List<Object[]> testParameters() {
        List<DomainModelsProvider<DomainModels>> domainModelsProviders = new ArrayList<>();
        domainModelsProviders.add(new PcmTestModelsProvider<>(context -> new PcmCompositeDataTypeTestModels(context)));
        domainModelsProviders.add(new UmlTestModelsProvider<>(context -> new UmlCompositeDataTypeTestModels(context)));
        domainModelsProviders
                .add(new JavaTestModelsProvider<>(context -> new JavaCompositeDataTypeTestModels(context)));
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

        DomainModel emptyCompositeDataTypeCreation();

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

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void emptyCompositeDataTypeCreation(
            DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        sourceModelsProvider.getModels(this.getVitruvApplicationTestAdapter()).emptyCompositeDataTypeCreation()
                .createAndSynchronize();
        targetModelsProvider.getModels(this.getVitruvApplicationTestAdapter()).emptyCompositeDataTypeCreation().check();
    }

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void compositeDataTypeWithBooleanElementCreation(
            DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        sourceModelsProvider.getModels(this.getVitruvApplicationTestAdapter())
                .compositeDataTypeWithBooleanElementCreation().createAndSynchronize();
        targetModelsProvider.getModels(this.getVitruvApplicationTestAdapter())
                .compositeDataTypeWithBooleanElementCreation().check();
    }

    // ... remaining test methods follow the same pattern ...

    // TODO renaming
}
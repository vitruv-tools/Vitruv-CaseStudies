package tools.vitruv.applications.cbs.commonalities.tests.cbs;

import static tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.orderedPairs;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaComponentInterfaceTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmComponentInterfaceTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlComponentInterfaceTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider;

public class ComponentInterfaceTest extends CBSCommonalitiesExecutionTest {

        public static List<Object[]> testParameters() {

                List<DomainModelsProvider<DomainModels>> domainModelsProviders = Arrays
                                .<DomainModelsProvider<DomainModels>>asList(
                                                new PcmTestModelsProvider<DomainModels>(
                                                                context -> new PcmComponentInterfaceTestModels(
                                                                                context)),
                                                new UmlTestModelsProvider<DomainModels>(
                                                                context -> new UmlComponentInterfaceTestModels(
                                                                                context)),
                                                new JavaTestModelsProvider<DomainModels>(
                                                                context -> new JavaComponentInterfaceTestModels(
                                                                                context)));
                return orderedPairs(domainModelsProviders);
        }

        public interface DomainModels {
                String INTERFACE_NAME = "SomeInterface";

                DomainModel emptyComponentInterfaceCreation();
        }

        @ParameterizedTest(name = "{0} to {1}")
        @MethodSource("testParameters")
        public void emptyComponentInterfaceCreation(
                        DomainModelsProvider<DomainModels> sourceModelsProvider,
                        DomainModelsProvider<DomainModels> targetModelsProvider) {
                sourceModelsProvider.getModels(this.getVitruvApplicationTestAdapter()).emptyComponentInterfaceCreation()
                                .createAndSynchronize();
                targetModelsProvider.getModels(this.getVitruvApplicationTestAdapter()).emptyComponentInterfaceCreation()
                                .check();
        }

        // TODO interface renaming
}
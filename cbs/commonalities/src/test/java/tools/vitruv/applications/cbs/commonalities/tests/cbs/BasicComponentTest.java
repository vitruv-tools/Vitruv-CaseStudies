package tools.vitruv.applications.cbs.commonalities.tests.cbs;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaBasicComponentTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmBasicComponentTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlBasicComponentTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider;
import static tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.orderedPairs;

public class BasicComponentTest extends CBSCommonalitiesExecutionTest {

    public static List<Object[]> testParameters() {
        List<DomainModelsProvider<DomainModels>> domainModelsProviders = Arrays
                .<DomainModelsProvider<DomainModels>>asList(
                        new PcmTestModelsProvider<DomainModels>(context -> new PcmBasicComponentTestModels(context)),
                        new UmlTestModelsProvider<DomainModels>(context -> new UmlBasicComponentTestModels(context)),
                        new JavaTestModelsProvider<DomainModels>(context -> new JavaBasicComponentTestModels(context)));
        return orderedPairs(domainModelsProviders);
    }

    public interface DomainModels {
        String COMPONENT_NAME = "SomeBasicComponent";

        DomainModel emptyBasicComponentCreation();
    }

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void emptyBasicComponentCreation(
            DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        sourceModelsProvider.getModels(this.getVitruvApplicationTestAdapter()).emptyBasicComponentCreation()
                .createAndSynchronize();
        targetModelsProvider.getModels(this.getVitruvApplicationTestAdapter()).emptyBasicComponentCreation().check();
    }

    // TODO component renaming
}
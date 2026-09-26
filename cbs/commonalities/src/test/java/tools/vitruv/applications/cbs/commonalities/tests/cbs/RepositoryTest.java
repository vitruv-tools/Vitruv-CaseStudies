package tools.vitruv.applications.cbs.commonalities.tests.cbs;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaRepositoryTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmRepositoryTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlRepositoryTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider;

import static tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.orderedPairs;

public class RepositoryTest extends CBSCommonalitiesExecutionTest {

    public static List<Object[]> testParameters() {
        List<DomainModelsProvider<DomainModels>> domainModelsProviders = new ArrayList<>();
        domainModelsProviders.add(new PcmTestModelsProvider<>(context -> new PcmRepositoryTestModels(context)));
        domainModelsProviders.add(new UmlTestModelsProvider<>(context -> new UmlRepositoryTestModels(context)));
        domainModelsProviders.add(new JavaTestModelsProvider<>(context -> new JavaRepositoryTestModels(context)));
        return orderedPairs(domainModelsProviders);
    }

    public interface DomainModels {
        DomainModel emptyRepositoryCreation();
    }

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void emptyRepositoryCreation(
            DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        sourceModelsProvider.getModels(this.getVitruvApplicationTestAdapter()).emptyRepositoryCreation()
                .createAndSynchronize();
        targetModelsProvider.getModels(this.getVitruvApplicationTestAdapter()).emptyRepositoryCreation().check();
    }

    // TODO repository renaming
}
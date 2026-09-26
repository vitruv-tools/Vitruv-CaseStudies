package tools.vitruv.applications.cbs.commonalities.tests.cbs;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmMediaStoreTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.*;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsProvider;

public class MediaStoreTest extends CBSCommonalitiesExecutionTest {

    public static List<Object[]> testParameters() {
        DomainModelsProvider<DomainModels> pcmModels = new PcmTestModelsProvider<>(
                context -> new PcmMediaStoreTestModels(context));

        DomainModelsProvider<DomainModels> umlModels1 = new DomainModelsProvider<>("UML (1_Packages)",
                context -> new UmlMediaStoreTestModels_1_Packages(context));

        DomainModelsProvider<DomainModels> umlModels2 = new DomainModelsProvider<>("UML (2_ClassAndInterfaceStubs)",
                context -> new UmlMediaStoreTestModels_2_ClassAndInterfaceStubs(context));

        DomainModelsProvider<DomainModels> umlModels3 = new DomainModelsProvider<>("UML (3_CompositeDataTypes)",
                context -> new UmlMediaStoreTestModels_3_CompositeDataTypes(context));

        DomainModelsProvider<DomainModels> umlModels4 = new DomainModelsProvider<>("UML (4_OperationSignatures)",
                context -> new UmlMediaStoreTestModels_4_OperationSignatures(context));

        DomainModelsProvider<DomainModels> umlModels5 = new DomainModelsProvider<>("UML (5_ProvidedRoles)",
                context -> new UmlMediaStoreTestModels_5_ProvidedRoles(context));

        List<Object[]> parameters = new ArrayList<>();
        parameters.add(new Object[] { pcmModels, umlModels1 });
        parameters.add(new Object[] { pcmModels, umlModels2 });
        parameters.add(new Object[] { pcmModels, umlModels3 });
        parameters.add(new Object[] { pcmModels, umlModels4 });
        parameters.add(new Object[] { pcmModels, umlModels5 });

        return parameters;
    }

    public interface DomainModels {
        DomainModel mediaStoreCreation();
    }

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void mediaStoreCreation(
            DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        sourceModelsProvider.getModels(this.getVitruvApplicationTestAdapter()).mediaStoreCreation()
                .createAndSynchronize();
        targetModelsProvider.getModels(this.getVitruvApplicationTestAdapter()).mediaStoreCreation().check();
    }
}
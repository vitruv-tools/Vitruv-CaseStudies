package tools.vitruv.applications.cbs.commonalities.tests.cbs;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmMediaStoreTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlMediaStoreTestModels_1_Packages;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlMediaStoreTestModels_2_ClassAndInterfaceStubs;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlMediaStoreTestModels_3_CompositeDataTypes;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlMediaStoreTestModels_4_OperationSignatures;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlMediaStoreTestModels_5_ProvidedRoles;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsProvider;

public class MediaStoreTest extends CBSCommonalitiesExecutionTest {

    public static List<Object[]> testParameters() {
        DomainModelsProvider<DomainModels> pcmModels = new PcmTestModelsProvider<>(PcmMediaStoreTestModels::new);
        DomainModelsProvider<DomainModels> umlModels1 = new DomainModelsProvider<>("UML (1_Packages)",
                UmlMediaStoreTestModels_1_Packages::new);
        DomainModelsProvider<DomainModels> umlModels2 = new DomainModelsProvider<>("UML (2_ClassAndInterfaceStubs)",
                UmlMediaStoreTestModels_2_ClassAndInterfaceStubs::new);
        DomainModelsProvider<DomainModels> umlModels3 = new DomainModelsProvider<>("UML (3_CompositeDataTypes)",
                UmlMediaStoreTestModels_3_CompositeDataTypes::new);
        DomainModelsProvider<DomainModels> umlModels4 = new DomainModelsProvider<>("UML (4_OperationSignatures)",
                UmlMediaStoreTestModels_4_OperationSignatures::new);
        DomainModelsProvider<DomainModels> umlModels5 = new DomainModelsProvider<>("UML (5_ProvidedRoles)",
                UmlMediaStoreTestModels_5_ProvidedRoles::new);
        return List.of(
                new Object[] {pcmModels, umlModels1},
                new Object[] {pcmModels, umlModels2},
                new Object[] {pcmModels, umlModels3},
                new Object[] {pcmModels, umlModels4},
                new Object[] {pcmModels, umlModels5});
    }

    public interface DomainModels {

        DomainModel mediaStoreCreation();
    }

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void mediaStoreCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).mediaStoreCreation().createAndSynchronize();
        getModels(targetModelsProvider).mediaStoreCreation().check();
    }
}

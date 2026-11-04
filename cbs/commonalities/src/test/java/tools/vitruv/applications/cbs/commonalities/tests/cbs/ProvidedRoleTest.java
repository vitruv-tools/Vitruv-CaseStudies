package tools.vitruv.applications.cbs.commonalities.tests.cbs;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaProvidedRoleTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmProvidedRoleTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlProvidedRoleTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider;

public class ProvidedRoleTest extends CBSCommonalitiesExecutionTest {

    public static List<Object[]> testParameters() {
        DomainModelsProvider<DomainModels> pcmModels = new PcmTestModelsProvider<>(PcmProvidedRoleTestModels::new);
        DomainModelsProvider<DomainModels> umlModels = new UmlTestModelsProvider<>(UmlProvidedRoleTestModels::new);
        DomainModelsProvider<DomainModels> javaModels = new JavaTestModelsProvider<>(JavaProvidedRoleTestModels::new);
        // TODO: The PCM/UML -> Java combinations do not work yet. The issue is that the inserted OperationProvidedRole
        // is initially empty (has no reference to any provided interface yet) and we do not react to changes of the
        // provided interface currently (since this is handled internally of the attribute mapping operator).
        // return orderedPairs(List.of(pcmModels, umlModels, javaModels));
        return List.of(
                new Object[] {pcmModels, umlModels},
                new Object[] {umlModels, pcmModels},
                new Object[] {javaModels, pcmModels},
                new Object[] {javaModels, umlModels});
    }

    public interface DomainModels {

        String COMPONENT_NAME = "SomeComponent";
        String INTERFACE_1_NAME = "Interface1";
        String INTERFACE_2_NAME = "Interface2";

        DomainModel componentWithProvidedRoleCreation();

        DomainModel componentWithMultipleProvidedRolesCreation();
    }

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void componentWithProvidedRoleCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).componentWithProvidedRoleCreation().createAndSynchronize();
        getModels(targetModelsProvider).componentWithProvidedRoleCreation().check();
    }

    @ParameterizedTest(name = "{0} to {1}")
    @MethodSource("testParameters")
    public void componentWithMultipleProvidedRolesCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
            DomainModelsProvider<DomainModels> targetModelsProvider) {
        getModels(sourceModelsProvider).componentWithMultipleProvidedRolesCreation().createAndSynchronize();
        getModels(targetModelsProvider).componentWithMultipleProvidedRolesCreation().check();
    }
}

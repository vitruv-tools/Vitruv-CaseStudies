package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm;

import java.util.List;
import org.eclipse.emf.ecore.resource.Resource;
import org.palladiosimulator.pcm.repository.Repository;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import tools.vitruv.applications.cbs.commonalities.tests.cbs.MediaStoreTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsBase;

public class PcmMediaStoreTestModels extends PcmTestModelsBase implements MediaStoreTest.DomainModels {

    // private static final String PCM_MEDIA_STORE_REPOSITORY_PATH =
    // "src/test/resources/model/pcm/MediaStore.repository";
    private static final String PCM_MEDIA_STORE_REPOSITORY_PATH = "src/test/resources/model/pcm/MediaStore_NoSEFF.repository";

    public PcmMediaStoreTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    @Override
    public DomainModel mediaStoreCreation() {
        return newModel(() -> {
            // Load media store repository from test resource
            Resource pcmMediaStoreResource = this.vitruvApplicationTestAdapter
                    .getTestResource(PCM_MEDIA_STORE_REPOSITORY_PATH);
            Repository pcmMediaStoreRepository = (Repository) pcmMediaStoreResource.getContents().get(0);

            // Verify repository was loaded
            assertNotNull(pcmMediaStoreRepository, "Could not find PCM MediaStore repository!");

            return List.of(pcmMediaStoreRepository);
        });
    }
}
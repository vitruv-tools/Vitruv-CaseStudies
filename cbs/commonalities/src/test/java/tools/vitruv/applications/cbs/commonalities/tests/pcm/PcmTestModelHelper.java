package tools.vitruv.applications.cbs.commonalities.tests.pcm;

import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import tools.vitruv.applications.cbs.commonalities.tests.TestConstants.PCM;

public final class PcmTestModelHelper {

	private PcmTestModelHelper() {
	}

	public static Repository newPcmRepository() {
		Repository pcmRepository = RepositoryFactory.eINSTANCE.createRepository();
		pcmRepository.setEntityName(PCM.REPOSITORY_NAME);
		return pcmRepository;
	}
}

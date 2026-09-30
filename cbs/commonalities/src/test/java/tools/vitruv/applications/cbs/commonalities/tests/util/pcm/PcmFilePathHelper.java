package tools.vitruv.applications.cbs.commonalities.tests.util.pcm;

import static tools.vitruv.applications.cbs.commonalities.tests.util.common.FilePathUtil.PATH_SEPARATOR;
import static tools.vitruv.applications.cbs.commonalities.tests.util.common.FilePathUtil.appendFile;

import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.util.temporary.pcm.PcmNamespace;

public final class PcmFilePathHelper {

	private static final String MODEL_PATH = "model" + PATH_SEPARATOR;

	private PcmFilePathHelper() {
	}

	public static String pcmRepositoryFilePath(String modelName) {
		return appendFile(MODEL_PATH, modelName, PcmNamespace.REPOSITORY_FILE_EXTENSION);
	}

	public static String pcmRepositoryFilePath(Repository repository) {
		return pcmRepositoryFilePath(repository.getEntityName());
	}

	public static String pcmSystemFilePath(String modelName) {
		return appendFile(MODEL_PATH, modelName, PcmNamespace.SYSTEM_FILE_EXTENSION);
	}
}

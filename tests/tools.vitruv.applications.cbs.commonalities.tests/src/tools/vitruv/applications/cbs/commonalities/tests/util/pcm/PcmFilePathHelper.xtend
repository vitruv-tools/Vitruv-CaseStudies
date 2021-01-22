package tools.vitruv.applications.cbs.commonalities.tests.util.pcm

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.domains.pcm.PcmNamespace

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.common.FilePathUtil.*

@Utility
class PcmFilePathHelper {

	static val MODEL_PATH = "model" + PATH_SEPARATOR

	private new() {
	}

	static def pcmRepositoryFilePath(String modelName) {
		return MODEL_PATH.appendFile(modelName, PcmNamespace.REPOSITORY_FILE_EXTENSION)
	}

	static def pcmRepositoryFilePath(Repository repository) {
		return pcmRepositoryFilePath(repository.entityName)
	}

	static def pcmSystemFilePath(String modelName) {
		return MODEL_PATH.appendFile(modelName, PcmNamespace.SYSTEM_FILE_EXTENSION)
	}
}

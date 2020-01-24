package tools.vitruv.applications.util.temporary.pcm

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Set
import org.apache.log4j.Logger
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.framework.correspondence.CorrespondenceModel

@Utility
class PcmRepositoryUtil {
    private static final Logger logger = Logger.getLogger(PcmRepositoryUtil.simpleName)

    def static Repository getFirstRepository(CorrespondenceModel correspondenceModel) {
        val Set<Repository> repos = correspondenceModel.getAllEObjectsOfTypeInCorrespondences(Repository)
        if (repos.nullOrEmpty) {
            return null
        }
        if (1 != repos.size) {
            logger.warn("found more than one repository. Returning the first")
        }
        return repos.get(0)
    }
}

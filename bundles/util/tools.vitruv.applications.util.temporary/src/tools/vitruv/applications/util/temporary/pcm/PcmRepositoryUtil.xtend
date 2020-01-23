package tools.vitruv.applications.util.temporary.pcm

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Set
import org.apache.log4j.Logger
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.framework.correspondence.CorrespondenceModel

@Utility
class PcmRepositoryUtil {
    private static final Logger logger = Logger.getLogger(PcmRepositoryUtil.simpleName)

    /**
     * Tries to locate a repository with a specific name out of a collection of repositories.
     * @throws IllegalStateException if there is more than one matching repository.
     */
    def static Repository findRepository(Iterable<Repository> allRepositories, String name) {
        val matchingRepositories = allRepositories.filter[entityName == name]
        if (matchingRepositories.size > 1) throw new IllegalStateException("There are " + matchingRepositories.size + " repositories with name " + name)
        return matchingRepositories.head
    }

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

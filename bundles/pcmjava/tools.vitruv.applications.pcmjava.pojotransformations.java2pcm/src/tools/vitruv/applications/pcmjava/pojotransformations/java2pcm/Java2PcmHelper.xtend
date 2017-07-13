package tools.vitruv.applications.pcmjava.pojotransformations.java2pcm

import tools.vitruv.framework.correspondence.CorrespondenceModel
import org.apache.log4j.Logger
import org.palladiosimulator.pcm.repository.Repository
import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*

public class Java2PcmHelper {
	private static val logger = Logger.getLogger(Java2PcmHelper)

	/** 
	 * Searches and retrieves the first PCM-Repository in the correspondence model that has an
	 * equal name as the given java package name.
	 * @param correspondenceModel the correspondenceModel in which the PCM-Repository should be searched
	 * @param packageName the package name for which a fitting PCM-Repository should be retrieved
	 * @return the corresponding PCM-Repository or null if none could be found 
	 */
	def static Repository findPcmRepository(CorrespondenceModel correspondenceModel, String packageName) {

		val allRepositories = correspondenceModel.getAllEObjectsOfTypeInCorrespondences(Repository)
		val repository = allRepositories.filter[entityName.equals(getRootPackageName(packageName))]

		if (repository.nullOrEmpty) {
			logger.warn("The PCM-Repository with the name " + packageName + " does not exist in the correspondence model")
			return null
		}
		return repository.head
	}
	
	/**
	 * Check if no Repository exists in correspondence model.
	 * @param correspondenceModel the correspondenceModel in which the PCM-Repository should be searched
	 * @return true if no exists, false otherwise.
	 */
	def static boolean noCorrespondenceRepository(CorrespondenceModel correspondenceModel) {
		return correspondenceModel.getAllEObjectsOfTypeInCorrespondences(Repository).isNullOrEmpty
	}
	
	def static String getRootPackageName(String packageName) {
		val index = packageName.indexOf('.')
		if (index < 0) {
			return packageName
		}
		return packageName.substring(0, packageName.indexOf('.'))
	}
	
	def static String getLastPackageName(String packageName) {
		return packageName.substring(packageName.indexOf('.') + 1)
	}
	

}

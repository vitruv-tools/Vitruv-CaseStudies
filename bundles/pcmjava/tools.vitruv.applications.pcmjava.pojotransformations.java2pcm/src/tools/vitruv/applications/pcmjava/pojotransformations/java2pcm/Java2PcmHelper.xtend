package tools.vitruv.applications.pcmjava.pojotransformations.java2pcm

import org.apache.log4j.Logger
import org.emftext.language.java.members.Method
import org.emftext.language.java.types.TypeReference
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.applications.pcmjava.util.java2pcm.TypeReferenceCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.userinteraction.UserInteracting

import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import org.eclipse.emf.common.util.EList
import java.util.ArrayList
import org.emftext.language.java.classifiers.Interface
import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmUtils
import org.eclipse.emf.ecore.EObject
import tools.vitruv.applications.pcmjava.util.PcmJavaUtils
import org.emftext.language.java.members.ClassMethod
import java.util.Set
import org.palladiosimulator.pcm.repository.OperationInterface

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
	
	def static Repository findPcmRepository(CorrespondenceModel correspondenceModel) {
		return Java2PcmUtils.getRepository(correspondenceModel)
	}

	def static boolean hasCorrespondance(EObject eObject, CorrespondenceModel correspondenceModel) {
		return !correspondenceModel.getCorrespondingEObjects(eObject).isNullOrEmpty
	}
	
	def static Set<OperationInterface> getCorrespondingOperationInterface(EObject eObject, CorrespondenceModel correspondenceModel) {
		return correspondenceModel.getCorrespondingEObjectsByType(eObject, OperationInterface)
	}
	
	def static sameSignature(Method interfaceMethod, ClassMethod classMethod) {
		PcmJavaUtils.hasSameSignature(interfaceMethod, classMethod)
	}
	
	/**
	 * Check if no Repository exists in correspondence model.
	 * @param correspondenceModel the correspondenceModel in which the PCM-Repository should be searched
	 * @return true if no exists, false otherwise.
	 */
	def static boolean noCorrespondenceRepository(CorrespondenceModel correspondenceModel) {
		return correspondenceModel.getAllEObjectsOfTypeInCorrespondences(Repository).isNullOrEmpty
	}
	
	def static getPCMDataTypeForTypeReference(TypeReference typeReference, CorrespondenceModel correspondenceModel, UserInteracting userInteracting, Repository repository, Method newMethod) {
		return TypeReferenceCorrespondenceHelper.
					getCorrespondingPCMDataTypeForTypeReference(typeReference,
						correspondenceModel, userInteracting, repository, newMethod.arrayDimension)
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
	
	def static findImplementingInterfacesFromTypeRefs(EList<TypeReference> typeReferences) {
		val implementingInterfaces = new ArrayList<Interface>
		for(typeRef : typeReferences){
			val classifier = Java2PcmUtils.getTargetClassifierFromImplementsReferenceAndNormalizeURI(typeRef)
			if(classifier instanceof Interface){
				implementingInterfaces.add(classifier)
			}
		}
		return implementingInterfaces
	}
	

}

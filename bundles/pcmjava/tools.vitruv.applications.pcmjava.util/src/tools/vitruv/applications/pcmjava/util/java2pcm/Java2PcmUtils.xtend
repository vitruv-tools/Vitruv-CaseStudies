package tools.vitruv.applications.pcmjava.util.java2pcm

import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.PrimitiveType
import org.emftext.language.java.types.TypeReference
import org.palladiosimulator.pcm.repository.Repository

import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.applications.pcmjava.util.PcmJavaUtils

abstract class Java2PcmUtils extends PcmJavaUtils {
	private new() {
	}

	private static Logger logger = Logger.getLogger(Java2PcmUtils.simpleName)

	def public static Repository getRepository(CorrespondenceModel correspondenceModel) {
		val Set<Repository> repos = correspondenceModel.getAllEObjectsOfTypeInCorrespondences(Repository)
		if (repos.nullOrEmpty) {
			return null
		}
		if (1 != repos.size) {
			logger.warn("found more than one repository. Retruning the first")
		}
		return repos.get(0)
	}

	def dispatch static Classifier getTargetClassifierFromTypeReference(TypeReference reference) {
		return null
	}
	
	def dispatch static Classifier getTargetClassifierFromTypeReference(NamespaceClassifierReference reference) {
		if (reference.classifierReferences.nullOrEmpty) {
			return null
		}
		return getTargetClassifierFromTypeReference(reference.classifierReferences.get(0))
	}
	
	def dispatch static Classifier getTargetClassifierFromTypeReference(ClassifierReference reference) {
		return reference.target
	}
	
	def dispatch static Classifier getTargetClassifierFromTypeReference(PrimitiveType reference) {
		return null
	}
	
	def public static Classifier getTargetClassifierFromImplementsReferenceAndNormalizeURI(
		TypeReference reference) {
		var interfaceClassifier = Java2PcmUtils.getTargetClassifierFromTypeReference(reference)
		if (null === interfaceClassifier) {
			return null
		}
		
		if(interfaceClassifier.eIsProxy){
			val resSet = reference.eResource.resourceSet
			interfaceClassifier = EcoreUtil.resolve(interfaceClassifier, resSet) as Classifier			
		}
		normalizeURI(interfaceClassifier)
		return interfaceClassifier
	}

	def public static normalizeURI(EObject eObject) {
		if (null === eObject.eResource || null === eObject.eResource.resourceSet) {
			return false
		}
		val resource = eObject.eResource
		val resourceSet = resource.resourceSet
		val uri = resource.getURI
		val uriConverter = resourceSet.getURIConverter
		val normalizedURI = uriConverter.normalize(uri)
		resource.URI = normalizedURI
		return true
	}

}

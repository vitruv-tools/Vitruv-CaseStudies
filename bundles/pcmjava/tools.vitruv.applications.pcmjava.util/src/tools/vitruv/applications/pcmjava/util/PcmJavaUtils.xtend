package tools.vitruv.applications.pcmjava.util

import com.google.common.collect.Sets
import tools.vitruv.framework.tuid.Tuid
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.datatypes.ClaimableMap
import java.util.Map
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IProject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.JavaRoot
import org.emftext.language.java.containers.Package
import org.emftext.language.java.members.Method
import org.emftext.language.java.types.TypeReference
import org.palladiosimulator.pcm.core.entity.NamedElement
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.system.System

import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import tools.vitruv.framework.correspondence.CorrespondenceModel
import static extension tools.vitruv.framework.util.bridges.CollectionBridge.*
import tools.vitruv.framework.correspondence.Correspondence
import tools.vitruv.framework.util.command.ResourceAccess
import tools.vitruv.domains.pcm.PcmNamespace
import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmUtils
import org.palladiosimulator.pcm.repository.Parameter
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import tools.vitruv.framework.tuid.TuidManager

class PcmJavaUtils {
	private static val Logger logger = Logger.getLogger(PcmJavaUtils.simpleName)

	static Set<Class<?>> pcmJavaRootObjects = Sets.newHashSet(Repository, System, Package, CompilationUnit)

	protected new() {
	}

	/**
	 * Checks whether the affectedAttribute is in the featureCorrespondenceMap and returns all corresponding objects, 
	 * if any. otherwise null is returned.
	 */
	def static checkKeyAndCorrespondingObjects(EObject eObject, EStructuralFeature affectedEFeature,
		Map<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap, CorrespondenceModel correspondenceModel) {
		if (!featureCorrespondenceMap.containsKey(affectedEFeature)) {
			logger.debug("no feature correspondence found for affectedEeature: " + affectedEFeature)
			return null
		}
		var correspondingEObjects = correspondenceModel.getCorrespondingEObjects(eObject)
		if (correspondingEObjects.nullOrEmpty) {
			logger.info("No corresponding objects found for " + eObject)
		}
		return correspondingEObjects
	}

	def private static boolean eObjectInstanceOfRootEObject(EObject object, Set<Class<? extends EObject>> classes) {
		for (c : classes) {
			if (c.isInstance(object)) {
				return true
			}
		}
		return false
	}

	/**
	 * Signatures are considered equal if methods have the same name, the same parameter types and the same return type
	 * We do not consider modifiers (e.g. public or private here)
	 */
	public static def boolean hasSameSignature(Method method1, Method method2) {
		if (method1 == method2) {
			return true
		}
		if (!method1.name.equals(method2.name)) {
			return false
		}
		if (!method1.typeReference.hasSameTargetReference(method2.typeReference)) {
			return false
		}
		if (method1.parameters.size != method2.parameters.size) {
			return false
		}
		var int i = 0
		for (param1 : method1.parameters) {
			if (!hasSameTargetReference(param1.typeReference, method2.parameters.get(i).typeReference)) {
				return false
			}
			i++
		}
		return true
	}

	public static def boolean hasSameTargetReference(TypeReference reference1, TypeReference reference2) {
		if (reference1 == reference2 || reference1.equals(reference2)) {
			return true
		}
		val target1 = Java2PcmUtils.getTargetClassifierFromTypeReference(reference1)
		val target2 = Java2PcmUtils.getTargetClassifierFromTypeReference(reference2)
		return target1 == target2 || target1.equals(target2)
	}

	def dispatch static handleRootChange(Repository repo, CorrespondenceModel correspondenceModel, VURI sourceModelVURI,
		ResourceAccess resourceAccess) {
		handlePCMRootEObject(repo, sourceModelVURI, correspondenceModel, PcmNamespace.REPOSITORY_FILE_EXTENSION,
			resourceAccess)
	}

	def dispatch static handleRootChange(System system, CorrespondenceModel correspondenceModel, VURI sourceModelVURI,
		ResourceAccess resourceAccess) {
		handlePCMRootEObject(system, sourceModelVURI, correspondenceModel, PcmNamespace.SYSTEM_FILE_EXTENSION,
			resourceAccess)
	}

	def dispatch static handleRootChange(JavaRoot newJavaRoot, CorrespondenceModel correspondenceModel,
		VURI sourceModelVURI, ResourceAccess resourceAccess) {
		// TODO: use configured src-folder path instead of hardcoded "src"
		val String srcFolderPath = getFolderPathInProjectOfResource(sourceModelVURI, "src");
		var String javaRootPath = newJavaRoot.getNamespacesAsString().replace(".", "/").replace("$", "/") +
			newJavaRoot.getName().replace("$", ".");
		if (newJavaRoot instanceof Package) {
			javaRootPath = javaRootPath + "/package-info.java";
		}
		val VURI cuVURI = VURI.getInstance(srcFolderPath + javaRootPath);
		resourceAccess.persistAsRoot(newJavaRoot, cuVURI)
		TuidManager.instance.flushRegisteredObjectsUnderModification
	}

	def static VURI getSourceModelVURI(EObject eObject) {
		if (null === eObject || null === eObject.eResource) {
			logger.warn("can not get SourceModelVURI cause eObject or its resource is null: " + eObject)
			return VURI.getInstance("")
		}
		val VURI vuri = VURI.getInstance(eObject.eResource)
		return vuri
	}

	def private static void handlePCMRootEObject(NamedElement namedElement, VURI sourceModelVURI,
		CorrespondenceModel correspondenceModel, String fileExt, ResourceAccess resourceAccess) {
		var String folderName = getFolderPathInProjectOfResource(sourceModelVURI, "model");
		val String fileName = namedElement.getEntityName() + "." + fileExt;
		if (!folderName.endsWith("/")) {
			folderName = folderName + "/";
		}
		val VURI vuri = VURI.getInstance(folderName + fileName);
		resourceAccess.persistAsRoot(namedElement, vuri)
		TuidManager.instance.flushRegisteredObjectsUnderModification
	}

	def dispatch static handleRootChanges(Iterable<EObject> eObjects, CorrespondenceModel correspondenceModel,
		VURI sourceModelVURI, ResourceAccess resourceAccess, VURI vuriToDelete, Tuid oldTuid) {
		eObjects.forEach [ eObject |
			handleSingleRootChange(eObject, correspondenceModel, sourceModelVURI, resourceAccess, vuriToDelete, oldTuid)
		]
	}

	def dispatch static handleRootChanges(EObject[] eObjects, CorrespondenceModel correspondenceModel,
		VURI sourceModelVURI, ResourceAccess resourceAccess, VURI vuriToDelete, Tuid oldTuid) {
		eObjects.forEach [ eObject |
			handleSingleRootChange(eObject, correspondenceModel, sourceModelVURI, resourceAccess, vuriToDelete, oldTuid)
		]
	}

	def dispatch static handleRootChanges(EObject eObject, CorrespondenceModel correspondenceModel,
		VURI sourceModelVURI, ResourceAccess resourceAccess, VURI vuriToDelete, Tuid oldTuid) {
		handleSingleRootChange(eObject, correspondenceModel, sourceModelVURI, resourceAccess, vuriToDelete, oldTuid)
	}

	def static handleSingleRootChange(EObject eObject, CorrespondenceModel correspondenceModel, VURI sourceModelVURI,
		ResourceAccess resourceAccess, VURI vuriToDelete, Tuid oldTuid) {
		TuidManager.instance.registerObjectUnderModification(eObject);
		EcoreUtil.remove(eObject)
		TuidManager.instance.updateTuidsOfRegisteredObjects;
		TuidManager.instance.flushRegisteredObjectsUnderModification;
		PcmJavaUtils.handleRootChange(eObject, correspondenceModel, sourceModelVURI, resourceAccess)
	}

	private static def String getFolderPathInProjectOfResource(VURI sourceModelVURI, String folderName) {
		val IFile fileSourceModel = URIUtil.getIFileForEMFUri(sourceModelVURI.getEMFUri());
		val IProject projectSourceModel = fileSourceModel.getProject();
		var String srcFolderPath = projectSourceModel.getFullPath().toString() + "/" + folderName + "/";
		if (srcFolderPath.startsWith("/")) {
			srcFolderPath = srcFolderPath.substring(1, srcFolderPath.length());
		}
		return srcFolderPath;
	}

	public dispatch static def getNameFromPCMDataType(PrimitiveDataType primitiveDataType) {
		return primitiveDataType.type.getName
	}

	public dispatch static def getNameFromPCMDataType(CollectionDataType collectionDataType) {
		return collectionDataType.entityName
	}

	public dispatch static def getNameFromPCMDataType(CompositeDataType compositeDataType) {
		return compositeDataType.entityName
	}

	def public static deleteNonRootEObjectInList(EObject affectedEObject, EObject oldEObject,
		CorrespondenceModel correspondenceModel, ResourceAccess resourceAccess) {
		removeCorrespondenceAndAllObjects(oldEObject, affectedEObject, correspondenceModel, pcmJavaRootObjects,
			resourceAccess)
		return resourceAccess
	}

	def static ResourceAccess removeCorrespondenceAndAllObjects(EObject object, EObject exRootObject,
		CorrespondenceModel correspondenceModel, ResourceAccess resourceAccess) {
		removeCorrespondenceAndAllObjects(object, exRootObject, correspondenceModel, pcmJavaRootObjects, resourceAccess)
	}

	def static ResourceAccess removeCorrespondenceAndAllObjects(EObject object, EObject exRootObject,
		CorrespondenceModel correspondenceModel, Set<Class<?>> rootObjects, ResourceAccess resourceAccess) {
		var Set<Correspondence> correspondences = null
		if (null !== exRootObject) {
			val rootTuid = correspondenceModel.calculateTuidFromEObject(exRootObject)
			val String prefix = rootTuid.toString
			EcoreUtil.remove(object)
			val tuid = correspondenceModel.calculateTuidFromEObject(object, exRootObject, prefix)
			correspondences = correspondenceModel.
				removeCorrespondencesThatInvolveAtLeastAndDependendForTuids(tuid.toSet)
		} else {
			correspondences = correspondenceModel.removeCorrespondencesThatInvolveAtLeastAndDependend(object.toSet)
		}
		for (correspondence : correspondences) {
			resolveAndRemoveEObject(correspondence.getATuids, correspondenceModel, resourceAccess, rootObjects)
			resolveAndRemoveEObject(correspondence.getBTuids, correspondenceModel, resourceAccess, rootObjects)
		}
		return resourceAccess
	}

	def private static resolveAndRemoveEObject(Iterable<Tuid> tuids, CorrespondenceModel correspondenceModel,
		ResourceAccess resourceAccess, Set<Class<?>> rootObjectClasses) {
		for (tuid : tuids) {
			try {
				val eObject = correspondenceModel.resolveEObjectFromTuid(tuid)
				if (null !== eObject) {
					EcoreUtil.delete(eObject)
				}
				if (eObject.isInstanceOfARootClass(rootObjectClasses)) {
					TuidManager.instance.registerObjectUnderModification(eObject);
					EcoreUtil.remove(eObject);
					TuidManager.instance.updateTuidsOfRegisteredObjects
					TuidManager.instance.flushRegisteredObjectsUnderModification
				}
			} catch (RuntimeException e) {
				// ignore runtime exception during object deletion
			}
		}
	}

	def private static boolean isInstanceOfARootClass(EObject eObject, Set<Class<?>> rootObjectClasses) {
		for (rootClass : rootObjectClasses) {
			if (rootClass.isInstance(eObject)) {
				return true
			}
		}
		return false
	}

	def static updateNameAttribute(Set<EObject> correspondingEObjects, Object newValue,
			EStructuralFeature affectedFeature,
			ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap,
			CorrespondenceModel correspondenceModel, boolean saveFilesOfChangedEObjects,
			Set<Class<? extends EObject>> classesOfRootObjects) {
		val EStructuralFeature eStructuralFeature = featureCorrespondenceMap.claimValueForKey(affectedFeature)

		val boolean rootAffected = correspondingEObjects.exists [ eObject |
			eObjectInstanceOfRootEObject(eObject, classesOfRootObjects)
		]
		if (rootAffected) {
			logger.error("The method updateNameattribut is not able to rename root objects")
			return
		}
		for (EObject correspondingObject : correspondingEObjects) {
			if (null === correspondingObject) {
				logger.error("corresponding object is null")
			} else {
				val Tuid oldTuid = correspondenceModel.calculateTuidFromEObject(correspondingObject)
				if (correspondingObject.eClass.EAllStructuralFeatures.contains(eStructuralFeature)) {
					correspondingObject.eSet(eStructuralFeature, newValue)
				}
				if (correspondingObject instanceof Parameter && eStructuralFeature.name == "entityName") {
					setParameterName(correspondingObject as Parameter, newValue as String);
				}

				oldTuid.updateTuid(correspondingObject)
				if (saveFilesOfChangedEObjects) {
					// nothing to do here?
				}
			}
		}
	}

	public static def void setParameterName(Parameter parameter, String newName) {
		// Set entity name as well if existing
		if (parameter.eClass.EAllAttributes.exists[name == "entityName"]) {
			parameter.eSet(parameter.eClass.EAllAttributes.filter[name == "entityName"].claimOne, newName);
		}
		parameter.parameterName = newName;
	}
}
	
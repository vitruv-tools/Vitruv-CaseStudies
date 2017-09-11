package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.util.transformationexecutor

import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.util.datatypes.ClaimableHashMap
import tools.vitruv.framework.util.datatypes.ClaimableMap
import java.util.Deque
import java.util.LinkedList
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.domains.java.echange.feature.JavaFeatureEChange
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.domains.java.echange.feature.reference.JavaInsertEReference
import tools.vitruv.domains.java.echange.feature.reference.JavaRemoveEReference
import tools.vitruv.domains.java.echange.feature.reference.JavaReplaceSingleValuedEReference
import tools.vitruv.domains.java.echange.feature.attribute.JavaReplaceSingleValuedEAttribute
import tools.vitruv.domains.java.echange.feature.attribute.JavaInsertEAttributeValue
import tools.vitruv.domains.java.echange.feature.attribute.JavaRemoveEAttributeValue
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.command.ResourceAccess

public class TransformationExecutor {

	val private static final Logger logger = Logger.getLogger(TransformationExecutor.simpleName)

	val protected ClaimableMap<Class<?>, EObjectMappingTransformation> mappingTransformations
	var protected CorrespondenceModel correspondenceModel

	new() {
		mappingTransformations = new ClaimableHashMap<Class<?>, EObjectMappingTransformation>()
	}

	def void setCorrespondenceModel(CorrespondenceModel correspondenceModel) {
		this.correspondenceModel = correspondenceModel
		for (mappingTransformation : mappingTransformations.values) {
			mappingTransformation.setCorrespondenceModel(correspondenceModel)
		}
	}

	def setUserInteracting(UserInteracting userInteracting) {
		for (mappingTransformations : mappingTransformations.values) {
			mappingTransformations.setUserInteracting(userInteracting)
		}
	}

	def public void executeTransformationForChange(EChange change, ResourceAccess resourceAccess) {
		executeTransformation(change, resourceAccess)
		updateTuidOfAffectedEObjectInEChange(change)
	}

	def protected updateTuidOfAffectedEObjectInEChange(EChange change) {
		if (change instanceof JavaFeatureEChange<?,?>) {
			val JavaFeatureEChange<?,?> eFeatureChange = change as JavaFeatureEChange<?,?>
			val EObject oldAffectedEObject = eFeatureChange.oldAffectedEObject
			val EObject newAffectedEObject = eFeatureChange.affectedEObject
			if (null !== oldAffectedEObject && null !== newAffectedEObject) {
				TuidManager.instance.updateTuid(oldAffectedEObject, newAffectedEObject)
			}
		}
	}

	def private dispatch void executeTransformation(EChange change, ResourceAccess resourceAccess) {
		logger.error("No executeTransformation method found for change " + change + ". Change not synchronized")
	}

	def private dispatch void executeTransformation(InsertRootEObject<? extends EObject> createRootEObject, ResourceAccess resourceAccess) {
		val EObject[] createdObjects = mappingTransformations.
			claimForMappedClassOrImplementingInterface(createRootEObject.newValue.class).createEObject(
				createRootEObject.newValue, resourceAccess)
		mappingTransformations.claimForMappedClassOrImplementingInterface(createRootEObject.newValue.class).
			createRootEObject(createRootEObject.newValue, createdObjects, resourceAccess)
	}

	def private dispatch void executeTransformation(RemoveRootEObject<? extends EObject> deleteRootEObject, ResourceAccess resourceAccess) {
		val EObject[] removedEObjects = mappingTransformations.
			claimForMappedClassOrImplementingInterface(deleteRootEObject.oldValue.class).removeEObject(
				deleteRootEObject.oldValue, resourceAccess)
		mappingTransformations.claimForMappedClassOrImplementingInterface(deleteRootEObject.oldValue.class).
			deleteRootEObject(deleteRootEObject.oldValue, removedEObjects, resourceAccess)
	}

//	def private dispatch TransformationResult executeTransformation(ReplaceRootEObject<?> replaceRootEObject) {
//		val EObject[] createdObjects = mappingTransformations.
//			claimForMappedClassOrImplementingInterface(replaceRootEObject.newValue.class).createEObject(
//				replaceRootEObject.newValue)
//		val EObject[] removedEObjects = mappingTransformations.
//			claimForMappedClassOrImplementingInterface(replaceRootEObject.oldValue.class).removeEObject(
//				replaceRootEObject.oldValue)
//		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceRootEObject.newValue.class).
//			replaceRoot(replaceRootEObject.oldValue, replaceRootEObject.newValue, removedEObjects, createdObjects)
//	}

	def private dispatch void executeTransformation(InsertEReference<? extends EObject,? extends EObject> insertEReference, ResourceAccess resourceAccess) {
		val EObject oldAffectedEObject = if (insertEReference instanceof JavaInsertEReference<?,?>) {
			insertEReference.oldAffectedEObject as EObject // Cast is only necessary due to Xcore/Xtend problem
		} else {
			insertEReference.affectedEObject;
		}
		if (insertEReference.isContainment) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(insertEReference.newValue.class)

		val EObject[] createdEObjects = mappingTransformations.
			claimForMappedClassOrImplementingInterface(insertEReference.newValue.class).
			createEObject(insertEReference.newValue, resourceAccess)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			insertEReference.affectedEObject.class).createNonRootEObjectInList(
			insertEReference.affectedEObject, oldAffectedEObject,
			insertEReference.affectedFeature, insertEReference.newValue,
			insertEReference.index, createdEObjects, resourceAccess)
		} else {
			mappingTransformations.claimForMappedClassOrImplementingInterface(
			insertEReference.class).
			insertNonContaimentEReference(oldAffectedEObject,
				insertEReference.affectedFeature, insertEReference.newValue,
				insertEReference.index, resourceAccess)
		}
	}

	def private dispatch void executeTransformation(RemoveEReference<? extends EObject,? extends EObject> removeEReference, ResourceAccess resourceAccess) {
		val oldAffectedEObject = if (removeEReference instanceof JavaRemoveEReference<?,?>) {
			removeEReference.oldAffectedEObject as EObject // Cast is only necessary due to Xcore/Xtend problem
		} else {
			removeEReference.affectedEObject;
		}
		if (removeEReference.isContainment) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(removeEReference.oldValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class)

		val EObject[] eObjectsToDelete = mappingTransformations.
			claimForMappedClassOrImplementingInterface(removeEReference.oldValue.class).
			removeEObject(removeEReference.oldValue, resourceAccess)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class).deleteNonRootEObjectInList(
			removeEReference.affectedEObject, oldAffectedEObject,
			removeEReference.affectedFeature, removeEReference.oldValue,
			removeEReference.index, eObjectsToDelete, resourceAccess)
		} else {
			mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class).
			removeNonContainmentEReference(oldAffectedEObject,
				removeEReference.affectedFeature, removeEReference.oldValue,
				removeEReference.index, resourceAccess)
		}
	}

//	def private dispatch TransformationResult executeTransformation(
//		ReplaceNonRootEObjectInList<?> replaceNonRootEObjectInList) {
//		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonRootEObjectInList.newValue.class)
//		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonRootEObjectInList.oldValue.class)
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			replaceNonRootEObjectInList.oldAffectedEObject.class)
//
//		val EObject[] eObjectsToDelete = mappingTransformations.
//			claimForMappedClassOrImplementingInterface(replaceNonRootEObjectInList.oldValue.class).
//			removeEObject(replaceNonRootEObjectInList.oldAffectedEObject)
//		val EObject[] createdEObjects = mappingTransformations.
//			claimForMappedClassOrImplementingInterface(replaceNonRootEObjectInList.newValue.class).
//			createEObject(replaceNonRootEObjectInList.oldAffectedEObject)
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			replaceNonRootEObjectInList.oldAffectedEObject.class).replaceNonRootEObjectInList(
//			replaceNonRootEObjectInList.oldAffectedEObject, replaceNonRootEObjectInList.affectedFeature,
//			replaceNonRootEObjectInList.oldValue, replaceNonRootEObjectInList.newValue,
//			replaceNonRootEObjectInList.index, eObjectsToDelete, createdEObjects)
//	}

	def private dispatch void executeTransformation(ReplaceSingleValuedEReference<? extends EObject,? extends EObject> replaceSingleValuedEReference, ResourceAccess resourceAccess) {
		val oldAffectedEObject = if (replaceSingleValuedEReference instanceof JavaReplaceSingleValuedEReference<?,?>) {
			replaceSingleValuedEReference.oldAffectedEObject as EObject // Cast is only necessary due to Xcore/Xtend problem
		} else {
			replaceSingleValuedEReference.affectedEObject;
		}
		if (replaceSingleValuedEReference.isContainment) {
		if (replaceSingleValuedEReference.oldValue === null && replaceSingleValuedEReference.newValue !== null) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceSingleValuedEReference.newValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class)

		val EObject[] createdEObjects = mappingTransformations.
			claimForMappedClassOrImplementingInterface(replaceSingleValuedEReference.newValue.class).
			createEObject(replaceSingleValuedEReference.newValue, resourceAccess)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			replaceSingleValuedEReference.affectedEObject.class).createNonRootEObjectSingle(
			oldAffectedEObject, replaceSingleValuedEReference.affectedFeature,
			replaceSingleValuedEReference.newValue, createdEObjects, resourceAccess)
		} else if (replaceSingleValuedEReference.oldValue !== null && replaceSingleValuedEReference.newValue === null) {
			mappingTransformations.claimForMappedClassOrImplementingInterface(replaceSingleValuedEReference.oldValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class)

		val EObject[] eObjectsToDelete = mappingTransformations.
			claimForMappedClassOrImplementingInterface(replaceSingleValuedEReference.oldValue.class).
			removeEObject(replaceSingleValuedEReference.oldValue, resourceAccess)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class).deleteNonRootEObjectSingle(
			oldAffectedEObject, replaceSingleValuedEReference.affectedFeature,
			replaceSingleValuedEReference.oldValue, eObjectsToDelete, resourceAccess)
		} else {
			mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class)

		mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class).replaceNonRootEObjectSingle(
			replaceSingleValuedEReference.affectedEObject, oldAffectedEObject,
			replaceSingleValuedEReference.affectedFeature, replaceSingleValuedEReference.oldValue,
			replaceSingleValuedEReference.newValue, resourceAccess)
		}
		} else {
			mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class).
			updateSingleValuedNonContainmentEReference(oldAffectedEObject,
				replaceSingleValuedEReference.affectedFeature,
				replaceSingleValuedEReference.oldValue, replaceSingleValuedEReference.newValue, resourceAccess)
		}
	}

//	def private dispatch TransformationResult executeTransformation(
//		PermuteContainmentEReferenceValues<?> permuteContainmentEReferenceValues) {
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			permuteContainmentEReferenceValues.oldAffectedEObject.class).
//			permuteContainmentEReferenceValues(permuteContainmentEReferenceValues.oldAffectedEObject,
//				permuteContainmentEReferenceValues.affectedFeature,
//				permuteContainmentEReferenceValues.newIndexForElementAt)
//	}
//
//	def private dispatch TransformationResult executeTransformation(
//		ReplaceNonContainmentEReference<?> replaceNonContainmentEReference) {
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			replaceNonContainmentEReference.oldAffectedEObject.class).
//			replaceNonContainmentEReference(replaceNonContainmentEReference.oldAffectedEObject,
//				replaceNonContainmentEReference.affectedFeature, replaceNonContainmentEReference.oldValue,
//				replaceNonContainmentEReference.newValue, replaceNonContainmentEReference.index)
//	}
//
//	def private dispatch TransformationResult executeTransformation(
//		PermuteNonContainmentEReferenceValues<?> permuteNonContainmentEReferenceValues) {
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			permuteNonContainmentEReferenceValues.oldAffectedEObject.class).
//			permuteNonContainmentEReferenceValues(permuteNonContainmentEReferenceValues.oldAffectedEObject,
//				permuteNonContainmentEReferenceValues.affectedFeature,
//				permuteNonContainmentEReferenceValues.newIndexForElementAt)
//	}

	def private dispatch void executeTransformation(
		ReplaceSingleValuedEAttribute<? extends EObject,? extends Object> replaceSingleValuedEAttribute, ResourceAccess resourceAccess) {
		val oldAffectedEObject = if (replaceSingleValuedEAttribute instanceof JavaReplaceSingleValuedEAttribute<?,?>) {
			replaceSingleValuedEAttribute.oldAffectedEObject as EObject // Cast is only necessary due to Xcore/Xtend problem
		} else {
			replaceSingleValuedEAttribute.affectedEObject;
		}
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class).updateSingleValuedEAttribute(
			oldAffectedEObject, replaceSingleValuedEAttribute.affectedFeature,
			replaceSingleValuedEAttribute.oldValue, replaceSingleValuedEAttribute.newValue, resourceAccess)
	}

	def private dispatch void executeTransformation(InsertEAttributeValue<? extends EObject,? extends Object> insertEAttributeValue, ResourceAccess resourceAccess) {
		val oldAffectedEObject = if (insertEAttributeValue instanceof JavaInsertEAttributeValue<?,?>) {
			insertEAttributeValue.oldAffectedEObject as EObject  // Cast is only necessary due to Xcore/Xtend problem
		} else {
			insertEAttributeValue.affectedEObject;
		}
		mappingTransformations.claimForMappedClassOrImplementingInterface(oldAffectedEObject.class).
			insertEAttributeValue(oldAffectedEObject, insertEAttributeValue.affectedFeature,
				insertEAttributeValue.newValue, insertEAttributeValue.index, resourceAccess)
	}

	def private dispatch void executeTransformation(RemoveEAttributeValue<? extends EObject,? extends Object> removeEAttributeValue, ResourceAccess resourceAccess) {
		val oldAffectedEObject = if (removeEAttributeValue instanceof JavaRemoveEAttributeValue<?,?>) {
			removeEAttributeValue.oldAffectedEObject as EObject // Cast is only necessary due to Xcore/Xtend problem
		} else {
			removeEAttributeValue.affectedEObject;
		}
		mappingTransformations.claimForMappedClassOrImplementingInterface(oldAffectedEObject.class).
			removeEAttributeValue(oldAffectedEObject, removeEAttributeValue.affectedFeature,
				removeEAttributeValue.oldValue, removeEAttributeValue.index, resourceAccess)
	}

//	def private dispatch TransformationResult executeTransformation(ReplaceEAttributeValue<?> replaceEAttributeValue) {
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			replaceEAttributeValue.oldAffectedEObject.class).replaceEAttributeValue(
//			replaceEAttributeValue.oldAffectedEObject, replaceEAttributeValue.affectedFeature,
//			replaceEAttributeValue.oldValue, replaceEAttributeValue.newValue, replaceEAttributeValue.index)
//	}

//	def private dispatch TransformationResult executeTransformation(PermuteEAttributeValues<?> permuteEAttributeValues) {
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			permuteEAttributeValues.oldAffectedEObject.class).permuteEAttributeValues(
//			permuteEAttributeValues.oldAffectedEObject, permuteEAttributeValues.affectedFeature,
//			permuteEAttributeValues.newIndexForElementAt)
//	}
//
//	def private dispatch TransformationResult executeTransformation(UnsetEAttribute<?> unsetEAttribute) {
//		mappingTransformations.claimForMappedClassOrImplementingInterface(unsetEAttribute.oldAffectedEObject.class).
//			unsetEAttribute(unsetEAttribute.oldAffectedEObject, unsetEAttribute.affectedFeature,
//				unsetEAttribute.oldValue)
//	}

//	def private dispatch TransformationResult executeTransformation(
//		UnsetNonContainmentEReference<?> unsetNonContainmentEReference) {
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			unsetNonContainmentEReference.oldAffectedEObject.class).
//			unsetNonContainmentEReference(unsetNonContainmentEReference.oldAffectedEObject,
//				unsetNonContainmentEReference.affectedFeature, unsetNonContainmentEReference.oldValue)
//	}

//	def private dispatch TransformationResult executeTransformation(UnsetContainmentEReference<?> unsetContainmentEReference) {
//		var EObject[] eObjectsToDelete = null
//		if (null != unsetContainmentEReference.oldValue) {
//			eObjectsToDelete = mappingTransformations.
//				claimForMappedClassOrImplementingInterface(unsetContainmentEReference.oldValue.class).
//				removeEObject(unsetContainmentEReference.oldValue)
//		}
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			unsetContainmentEReference.oldAffectedEObject.class).unsetContainmentEReference(
//			unsetContainmentEReference.oldAffectedEObject, unsetContainmentEReference.affectedFeature,
//			unsetContainmentEReference.oldValue, eObjectsToDelete)
//	}

//	def private dispatch TransformationResult executeTransformation(
//		InsertNonRootEObjectInContainmentList<?> insertNonRootEObjectInContainmentList) {
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			insertNonRootEObjectInContainmentList.newAffectedEObject.class).insertNonRootEObjectInContainmentList(
//			insertNonRootEObjectInContainmentList.newAffectedEObject,
//			insertNonRootEObjectInContainmentList.oldAffectedEObject,
//			insertNonRootEObjectInContainmentList.affectedFeature,
//			insertNonRootEObjectInContainmentList.newValue
//		)
//	}
//
//	def private dispatch TransformationResult executeTransformation(UnsetEFeature<?> unsetEFeature) {
//		logger.error("executeTransformation for UnsetEFeature<?> is not implemented yet...")
//		return null
//	}

	def public addMapping(EObjectMappingTransformation transformation) {
		if (null !== correspondenceModel) {
			transformation.setCorrespondenceModel(correspondenceModel)
		}
		mappingTransformations.putClaimingNullOrSameMapped(transformation.classOfMappedEObject, transformation)
	}

	def private EObjectMappingTransformation claimForMappedClassOrImplementingInterface(
		ClaimableMap<Class<?>, EObjectMappingTransformation> transformations, Class<?> concreteInstance) {
		val Deque<Class<?>> bfsQueue = new LinkedList()
		bfsQueue.addAll(concreteInstance)
		while (!bfsQueue.empty) {
			val concreteIf = bfsQueue.poll
			if (transformations.containsKey(concreteIf)) {
				return transformations.get(concreteIf)
			} else {
				bfsQueue.addAll(concreteIf.interfaces)
			}
		}
		return transformations.claimValueForKey(concreteInstance)
	}

}

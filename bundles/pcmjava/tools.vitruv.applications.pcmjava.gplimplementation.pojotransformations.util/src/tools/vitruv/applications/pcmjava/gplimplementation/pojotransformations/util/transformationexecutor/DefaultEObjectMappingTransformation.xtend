package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.util.transformationexecutor

import org.apache.log4j.Logger
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.framework.util.command.ResourceAccess

/**
 * The implements create and delete EObject with null and all other transformations with empty results.
 * The goal is to have for all EObjects a matching class in the map in order to avoid runtime exceptions.   
 */
class DefaultEObjectMappingTransformation extends EmptyEObjectMappingTransformation {
	
	private static val Logger logger = Logger.getLogger(DefaultEObjectMappingTransformation.simpleName)
	
	override getClassOfMappedEObject() {
		return EObject
	}
	
	override setCorrespondenceForFeatures() {
	}
	
	override deleteRootEObject(EObject oldRootEObject, EObject[] oldCorrespondingEObjectsToDelete, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override deleteNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject[] eObjectsToDelete, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override insertEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object newValue, int index, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override removeEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue, int index, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override insertNonContaimentEReference(EObject affectedEObject, EReference affectedReference, EObject newValue,
		int index, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override permuteContainmentEReferenceValues(EObject affectedEObject, EReference affectedReference,
		EList<Integer> newIndexForElementAt, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override permuteEAttributeValues(EObject affectedEObject, EAttribute affectedAttribute,
		EList<Integer> newIndexForElementAt, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override permuteNonContainmentEReferenceValues(EObject affectedEObject, EReference affectedReference,
		EList<Integer> newIndexForElementAt, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override removeNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		int index, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override replaceNonRootEObjectSingle(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override replaceEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue, int index, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override replaceNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject newValue, int index, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override replaceNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject newValue, int index, EObject[] oldCorrespondingEObjectsToDelete, EObject[] newCorrespondingEObjects, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override unsetNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override unsetContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject[] oldCorrespondingEObjectsToDelete, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override unsetEAttribute(EObject affectedEObject, EStructuralFeature affectedFeature, Object oldValue, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override replaceRoot(EObject oldRootEObject, EObject newRootEObject, EObject[] oldCorrespondingEObjectsToDelete,
		EObject[] newCorrespondingEObjects, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override createEObject(EObject eObject, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation. EObject: " + eObject)
		return null
	}

	override removeEObject(EObject eObject, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		return null
	}

	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue,
		EObject[] newCorrespondingEObjects, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override insertNonRootEObjectInContainmentList(EObject object, EObject object2, EReference reference,
		EObject newValue, ResourceAccess resourceAccess) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}
	
}
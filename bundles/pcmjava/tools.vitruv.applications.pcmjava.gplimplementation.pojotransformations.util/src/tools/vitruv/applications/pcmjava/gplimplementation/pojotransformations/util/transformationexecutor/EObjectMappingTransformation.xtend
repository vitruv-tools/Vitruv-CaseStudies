package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.util.transformationexecutor

import tools.vitruv.framework.userinteraction.UserInteractionType
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.util.datatypes.ClaimableHashMap
import tools.vitruv.framework.util.datatypes.ClaimableMap
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.command.ResourceAccess

abstract class EObjectMappingTransformation {

	var protected ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap
	var private UserInteracting userInteracting

	new() {
		featureCorrespondenceMap = new ClaimableHashMap<EStructuralFeature, EStructuralFeature>();
	}

	var protected CorrespondenceModel correspondenceModel

	def Class<?> getClassOfMappedEObject()

	def EObject[] createEObject(EObject eObject, ResourceAccess resourceAccess)

	def EObject[] removeEObject(EObject eObject, ResourceAccess resourceAccess)

	def void createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects, ResourceAccess resourceAccess)

	def void deleteRootEObject(EObject oldRootEObject, EObject[] oldCorrespondingEObjectsToDelete, ResourceAccess resourceAccess)

	def void replaceRoot(EObject oldRootEObject, EObject newRootEObject,
		EObject[] oldCorrespondingEObjectsToDelete, EObject[] newCorrespondingEObjects, ResourceAccess resourceAccess)

	def void createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects, ResourceAccess resourceAccess)

	def void deleteNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject[] eObjectsToDelete, ResourceAccess resourceAccess)

	def void createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference,
		EObject newValue, EObject[] newCorrespondingEObjects, ResourceAccess resourceAccess)

	def void replaceNonRootEObjectInList(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue, int index, EObject[] oldCorrespondingEObjectsToDelete,
		EObject[] newCorrespondingEObjects, ResourceAccess resourceAccess)

	def void deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete, ResourceAccess resourceAccess)

	def void replaceNonRootEObjectSingle(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue, ResourceAccess resourceAccess)

	def void permuteContainmentEReferenceValues(EObject affectedEObject,
		EReference affectedReference, EList<Integer> newIndexForElementAt, ResourceAccess resourceAccess)

	def void insertNonContaimentEReference(EObject affectedEObject, EReference affectedReference,
		EObject newValue, int index, ResourceAccess resourceAccess)

	def void updateSingleValuedNonContainmentEReference(EObject affectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue, ResourceAccess resourceAccess)

	def void permuteNonContainmentEReferenceValues(EObject affectedEObject,
		EReference affectedReference, EList<Integer> newIndexForElementAt, ResourceAccess resourceAccess)

	def void replaceNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue, int index, ResourceAccess resourceAccess)

	def void removeNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, int index, ResourceAccess resourceAccess)

	def void updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute,
		Object oldValue, Object newValue, ResourceAccess resourceAccess)

	def void removeEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute,
		Object oldValue, int index, ResourceAccess resourceAccess)

	def void insertEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute,
		Object newValue, int index, ResourceAccess resourceAccess)

	def void unsetEAttribute(EObject affectedEObject, EStructuralFeature affectedFeature,
		Object oldValue, ResourceAccess resourceAccess)

	def void unsetContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject[] oldCorrespondingEObjectsToDelete, ResourceAccess resourceAccess)

	def void unsetNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, ResourceAccess resourceAccess)

	def void replaceEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute,
		Object oldValue, Object newValue, int index, ResourceAccess resourceAccess)

	def void permuteEAttributeValues(EObject affectedEObject, EAttribute affectedAttribute,
		EList<Integer> newIndexForElementAt, ResourceAccess resourceAccess)

	def void insertNonRootEObjectInContainmentList(EObject oldAffectedEObject,
		EObject newAffectedEObject, EReference reference, EObject newValue, ResourceAccess resourceAccess)

	def void setCorrespondenceForFeatures()

	def void setCorrespondenceModel(CorrespondenceModel correspondenceModel) {
		this.correspondenceModel = correspondenceModel
		if (null !== correspondenceModel) {
			setCorrespondenceForFeatures()
		}
	}

	def void setUserInteracting(UserInteracting userInteracting) {
		this.userInteracting = userInteracting
	}

	def protected UserInteracting getUserInteracting() {
		if (null === userInteracting) {
			throw new RuntimeException(
				"getUserInteracting failed: Reason: UserInteracting is not set (==null)." +
					"UserInteracting has to be set before using it.")
		}
		return userInteracting
	}

	def protected int modalTextUserinteracting(String msg, String... selections) {
		return userInteracting.selectFromMessage(UserInteractionType.MODAL, msg, selections)
	}

	def protected boolean modalTextYesNoUserInteracting(String msg) {
		return 0 == modalTextUserinteracting(msg, "Yes", "No")
	}

}

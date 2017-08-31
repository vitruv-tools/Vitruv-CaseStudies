package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.java2pcm.transformations

import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.util.transformationexecutor.EmptyEObjectMappingTransformation
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.modifiers.Modifier
import tools.vitruv.framework.util.command.ResourceAccess

/**
 * Triggered when a CUD operation on a Modifier is detected.
 */
class ModifierMappingTransformation extends EmptyEObjectMappingTransformation {

	//private static val Logger logger = Logger.getLogger(ModifierMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return Modifier
	}

	override setCorrespondenceForFeatures() {
	}

	override createEObject(EObject eObject, ResourceAccess resourceAccess) {
	}

	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects, ResourceAccess resourceAccess) {
	}

	override removeEObject(EObject eObject, ResourceAccess resourceAccess) {
	}

	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject, EReference affectedReference, EObject oldValue,
		int index, EObject[] oldCorrespondingEObjectsToDelete, ResourceAccess resourceAccess) {
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue, ResourceAccess resourceAccess) {
	}

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue,
		EObject[] newCorrespondingEObjects, ResourceAccess resourceAccess) {
	}

}

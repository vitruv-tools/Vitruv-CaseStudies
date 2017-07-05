package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.system

import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.ComposedProvidingRequiringEntityMappingTransformation
import org.eclipse.emf.ecore.EObject
import org.palladiosimulator.pcm.system.System
import org.emftext.language.java.containers.JavaRoot

import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.applications.pcmjava.util.PcmJavaUtils

class SystemMappingTransformation extends ComposedProvidingRequiringEntityMappingTransformation {

	/**
	 * called when a system has been created.
	 * system has no parent package --> return null
	 */
	override getParentPackage(EObject eObject) {
		return null
	}

	override getClassOfMappedEObject() {
		return System
	}

	override createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects) {
		val transformationResult = new ChangePropagationResult
		if (newCorrespondingEObjects.nullOrEmpty) {
			return transformationResult
		}
		newCorrespondingEObjects.filter(JavaRoot).forEach [ newCorrespondingEObject |
			PcmJavaUtils.addRootChangeToTransformationResult(newCorrespondingEObject, correspondenceModel,
				PcmJavaUtils.getSourceModelVURI(newRootEObject), transformationResult)
		]
		for (correspondingEObject : newCorrespondingEObjects) {
			correspondenceModel.createAndAddCorrespondence(newRootEObject, correspondingEObject)
		}
		return transformationResult
	}

}
package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.java2pcm.transformations

import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.util.transformationexecutor.EmptyEObjectMappingTransformation
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.CompilationUnit
import org.palladiosimulator.pcm.system.System

import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import tools.vitruv.applications.pcmjava.util.PcmJavaUtils
import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmUtils
import tools.vitruv.framework.util.command.ResourceAccess

class CompilationUnitMappingTransformation extends EmptyEObjectMappingTransformation {

	val private static Logger logger = Logger.getLogger(CompilationUnitMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return CompilationUnit
	}

	/**
	 * We do not need this method. 
	 * After a creation of a compilation unit the creation method for a class or an interface is executed
	 * @see ClassMappingTransformation We handle the creation of a classes or interfaces in the specific transformations.
	 */
	override createEObject(EObject eObject, ResourceAccess resourceAccess) {
		logger.trace("Compilation unit " + eObject + " created. Currently nothing is done for compilation unit")
		return null
	}

	override createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects, ResourceAccess resourceAccess) {
		logger.trace(
			"Compilation unit " + newRootEObject +
				" created as root EObject. Currently nothing is done for compilation unit")
	}

	/**
	 * The method is called when a class or interface has been created within the compilation unit 
	 * that has corresponding PCM objects (i.e. basic components)
	 */
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects, ResourceAccess resourceAccess) {
		if (newValue instanceof Class || newValue instanceof Interface) {

			// if it is a class that should correspond to a system and the system already has a container 
			//do not mark the system as new objet to create.
			if (!newCorrespondingEObjects.nullOrEmpty) {
				val systems = newCorrespondingEObjects.filter(typeof(System))
				if (!systems.nullOrEmpty) {
					for (system : systems) {
						if (null === system.eResource) {
							PcmJavaUtils.handleRootChange(system, correspondenceModel, PcmJavaUtils.getSourceModelVURI(newAffectedEObject), resourceAccess)
						} else {
							//do nothing, cause save is done later
						}
						correspondenceModel.createAndAddCorrespondence(system, newValue)
					}
				}
			}
			Java2PcmUtils.
				createNewCorrespondingEObjects(newValue, newCorrespondingEObjects,
					correspondenceModel, resourceAccess)
		}
	}

	override setCorrespondenceForFeatures() {
	}
	
	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete, ResourceAccess resourceAccess) {
		PcmJavaUtils.removeCorrespondenceAndAllObjects(oldValue, oldAffectedEObject, correspondenceModel, resourceAccess)
	}

}

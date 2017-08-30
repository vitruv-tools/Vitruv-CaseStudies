package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.java2pcm.transformations

import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.util.transformationexecutor.EmptyEObjectMappingTransformation
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.Package
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory

import static extension tools.vitruv.framework.util.bridges.CollectionBridge.*
import tools.vitruv.applications.pcmjava.util.PcmJavaUtils
import tools.vitruv.applications.pcmjava.util.pcm2java.Pcm2JavaUtils
import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmUtils
import tools.vitruv.framework.util.command.ResourceAccess

/**
 * Maps a JaMoPP interface to a PCM interface 
 * Triggered when a CUD operation on JaMoPP interface is detected.
 */
class InterfaceMappingTransformation extends EmptyEObjectMappingTransformation {

	val private static final Logger logger = Logger.getLogger(InterfaceMappingTransformation.name)

	override getClassOfMappedEObject() {
		return Interface
	}

	/**
	 * Called when a Java-interface was added to the source code
	 * Determines whether the interface is architecture relevant or not
	 * An interface is architectural relevant, if 
	 * i) checking whether it is in the contracts package
	 * ii) asking the developer (not yet implemented)
	 */
	override createEObject(EObject eObject, ResourceAccess resourceAccess) {
		val Interface jaMoPPInterface = eObject as Interface
		try {
			val Package jaMoPPPackage = Pcm2JavaUtils.
				getContainingPackageFromCorrespondenceModel(jaMoPPInterface, correspondenceModel)
			var boolean createInterface = false
			if (null !== jaMoPPPackage && jaMoPPPackage.name.equals("contracts")) {

				//i)
				createInterface = true
			} else {

				//ii)
				val String msg = "The created interface is not in the contracts packages. Should an architectural interface be created for the interface " +
					jaMoPPInterface.name + " ?"
				createInterface = super.modalTextYesNoUserInteracting(msg)
			}
			if (createInterface) {
				logger.debug("Created interface: " + eObject);
				var OperationInterface opInterface = RepositoryFactory.eINSTANCE.createOperationInterface
				opInterface.setEntityName(jaMoPPInterface.name)
				val Repository repo = Java2PcmUtils.getRepository(correspondenceModel)
				opInterface.setRepository__Interface(repo)
				return opInterface.toList
			}

		} catch (Exception e) {
			logger.info(e)
		}
		return null;
	}

	/**
	 * called when an interface method has been created
	 * 
	 */
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject, EReference affectedReference, EObject newValue,
		int index, EObject[] newCorrespondingEObjects, ResourceAccess resourceAccess) {
		Java2PcmUtils.
			createNewCorrespondingEObjects(newValue, newCorrespondingEObjects,
				correspondenceModel, resourceAccess)
	}

	/**
	 * Called when a Java-interface was removed. Also removes the corresponding PCM Interface (if there is one)
	 * Does not ask the developer whether the PCM interface should be removed also.
	 */
	override removeEObject(EObject eObject, ResourceAccess resourceAccess) {
		return null
	}

	/**
	 * we do not really need the method deleteNonRootEObjectInList in InterfaceMappingTransformation because the deletion of the 
	 * object has already be done in removeEObject.
	 * We just return an empty TransformationChangeResult 
	 */
	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject, EReference affectedReference, EObject oldValue,
		int index, EObject[] oldCorrespondingEObjectsToDelete, ResourceAccess resourceAccess) {
			PcmJavaUtils.deleteNonRootEObjectInList(oldAffectedEObject, oldValue, correspondenceModel, resourceAccess)
	}

	override updateSingleValuedEAttribute(EObject eObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue, ResourceAccess resourceAccess) {
		Java2PcmUtils.updateNameAsSingleValuedEAttribute(eObject, affectedAttribute, oldValue, newValue,
			featureCorrespondenceMap, correspondenceModel, resourceAccess)
	}

	override setCorrespondenceForFeatures() {
		var interfaceNameAttribute = ClassifiersFactory.eINSTANCE.createInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase("name")].iterator.next
		var opInterfaceNameAttribute = RepositoryFactory.eINSTANCE.createOperationInterface.eClass.getEAllAttributes.
			filter[attribute|attribute.name.equalsIgnoreCase("entityName")].iterator.next
		featureCorrespondenceMap.put(interfaceNameAttribute, opInterfaceNameAttribute)
	}

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue,
		EObject[] newCorrespondingEObjects, ResourceAccess resourceAccess) {
		logger.warn(
			"method createNonRootEObjectSingle should not be called for " + InterfaceMappingTransformation.simpleName +
				" transformation")
	}

}

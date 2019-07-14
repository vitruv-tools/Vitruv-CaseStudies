package tools.vitruv.applications.pcmumlclass.tests

import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Property
import tools.vitruv.domains.java.util.JavaPersistenceHelper
import org.eclipse.uml2.uml.Classifier

class TransitiveChangeTest extends PcmUmlClassApplicationTest {

	private static val logger = Logger.getLogger(typeof(TransitiveChangeTest).simpleName)

	/**
	 * Retrieves the first corresponding java field for a given uml property
	 */
	def protected getCorrespondingJavaAttribute(Property uAttribute) {
		return getFirstCorrespondingObjectWithClass(uAttribute, org.emftext.language.java.members.Field)
	}

	/**
	 * Retrieves the first corresponding java class for a given uml class
	 */
	def protected getCorrespondingJavaClass(Classifier uClass) {
		return getFirstCorrespondingObjectWithClass(uClass, org.emftext.language.java.classifiers.Class)
	}

	/**
	 * Retrieves the first corresponding java interface for a given uml interface
	 */
	def protected getCorrespondingJavaInterface(Interface uInterface) { // TODO TS do I still need this?
		return getFirstCorrespondingObjectWithClass(uInterface, org.emftext.language.java.classifiers.Interface)
	}

	/**
	 * Retrieves the first corresponding java package for a given uml package
	 */
	def protected getCorrespondingJavaPackage(Package uPackage) {
		return getFirstCorrespondingObjectWithClass(uPackage, org.emftext.language.java.containers.Package)
	}

	/**
	 * Retrieves the first corresponding uml package for a given java package
	 */
	def protected getCorrespondingUMLPackage(org.emftext.language.java.containers.Package javaPackage) {
		return getFirstCorrespondingObjectWithClass(javaPackage, Package)
	}

	/**
	 * Retrieves all corresponding objects of obj, filters the result list by the class c
	 * and returns the first element of the remaining list
	 * 
	 * {@link #getCorrespondingObjectList(EObject)}
	 * @param obj the object for which the first corresponding object should be retrieved
	 * @return the first corresponding object of obj or null if none could be found
	 */
	def private <T extends EObject> getFirstCorrespondingObjectWithClass(EObject obj, Class<T> c) { // TODO TS do I still need this?
		val correspondingObjectList = getCorrespondingObjectListWithClass(obj, c)
		if (correspondingObjectList.nullOrEmpty) {
			logger.warn("There are no corresponding objects for " + obj + " of the type " + c.class + ". Returning null.")
			return null
		} else if (correspondingObjectList.size > 1) {
			logger.warn("There are more than one corresponding object for " + obj + " of the type " + c.class + ". Returning the first.")
		}
		return correspondingObjectList.head
	}

	/**
	 * Retrieves all corresponding objects of obj.
	 * 
	 * {@link tools.vitruv.framework.tests.VitruviusUnmonitoredApplicationTest#getCorrespondenceModel}
	 * @param obj the object for which the corresponding objects should be retrieved
	 * @return the corresponding objects of obj or null if none could be found
	 * @throws IllegalArgumentException if obj is null
	 */
	def private getCorrespondingObjectList(EObject obj) { // TODO TS do I still need this?
		if (obj === null) {
			throw new IllegalArgumentException("Cannot retrieve correspondence for null")
		}
		val corrList = getCorrespondenceModel.getCorrespondingEObjects(#[obj]).flatten;
		if (corrList.nullOrEmpty) {
			logger.warn("No Correspondences found for " + obj)
			return null
		}
		return corrList
	}

	def protected <E> Set<E> getCorrespondingObjectsOfClass(Class<E> clazz) {
		return getCorrespondenceModel.getAllEObjectsOfTypeInCorrespondences(clazz)
	}

	/**
	 * Retrieves all corresponding objects of obj and filters the result list by the class c
	 * 
	 * {@link #getCorrespondingObjectList(EObject)}
	 * @param obj the object for which the corresponding objects should be retrieved
	 * @return the corresponding objects of obj filtered by c or null if none could be found
	 */
	def private <T extends EObject> getCorrespondingObjectListWithClass(EObject obj, Class<T> c) { // TODO TS do I still need this?
		return getCorrespondingObjectList(obj)?.filter(c)
	}

	def protected assertJavaFileExists(String fileName, String[] namespaces) {
		assertModelExists(JavaPersistenceHelper.buildJavaFilePath(fileName + ".java", namespaces)); // TODO TS do I still need this?
	}

}

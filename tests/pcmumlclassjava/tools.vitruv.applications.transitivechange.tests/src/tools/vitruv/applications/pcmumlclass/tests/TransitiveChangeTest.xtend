package tools.vitruv.applications.pcmumlclass.tests

import java.util.ArrayList
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.InterfaceRealization
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Property
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.InterfaceMethod
import tools.vitruv.applications.umljava.util.java.JavaVisibility
import tools.vitruv.domains.java.util.JavaPersistenceHelper

import static org.junit.Assert.*
import static tools.vitruv.applications.umljava.testutil.JavaTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import org.eclipse.uml2.uml.Operation
import org.emftext.language.java.members.Method

class TransitiveChangeTest extends PcmUmlClassApplicationTest {

	private static val logger = Logger.getLogger(typeof(TransitiveChangeTest).simpleName)

	def protected checkJavaInterface(Interface umlInterface) {
		assertJavaFileExists(umlInterface.name, umlInterface.convertNamespaces)
		val javaInterface = getFirstCorrespondingObject(umlInterface, org.emftext.language.java.classifiers.Interface)
		assertEquals(umlInterface.name, javaInterface.name)
	}

	def protected checkJavaClass(Classifier umlClass) {
		val javaClass = getFirstCorrespondingObject(umlClass, org.emftext.language.java.classifiers.Class)
		assertJavaFileExists(umlClass.name, umlClass.convertNamespaces);
		assertEquals(umlClass.name, javaClass.name)
	}

	def protected checkJavaInterfaceRealization(InterfaceRealization umlRealization) {
		val javaInterface = getFirstCorrespondingObject(umlRealization.contract, org.emftext.language.java.classifiers.Interface)
		val javaClass = getFirstCorrespondingObject(umlRealization.implementingClassifier, org.emftext.language.java.classifiers.Class)
		assertTrue(javaClass.allSuperClassifiers.contains(javaInterface))
	}

	def protected checkJavaPackage(Package umlPackage) {
		val javaPackage = getFirstCorrespondingObject(umlPackage, org.emftext.language.java.containers.Package)
		assertEquals(umlPackage.name, javaPackage.name)
		assertPackageEquals(umlPackage, javaPackage)
	}

	def protected checkJavaAttribute(Property umlAttribute) {
		val javaAttribute = getFirstCorrespondingObject(umlAttribute, Field)
		assertEquals(umlAttribute.name, javaAttribute.name)
		assertVisibilityEquals(umlAttribute, javaAttribute)
		assertFinalAttributeEquals(umlAttribute, javaAttribute)
		assertStaticEquals(umlAttribute, javaAttribute)
		if (umlAttribute.upper != LiteralUnlimitedNatural.UNLIMITED && umlAttribute.upper < 2) {
			assertTypeEquals(umlAttribute.type, javaAttribute.typeReference) // Type is only equal for non collection types
		}
	}

	def protected checkJavaConstructor(Operation umlConstructor) {
		val javaConstructor = getFirstCorrespondingObject(umlConstructor, org.emftext.language.java.members.Constructor)
		assertEquals(umlConstructor.name, javaConstructor.name)
		assertJavaModifiableAbstract(javaConstructor, umlConstructor.abstract)
		assertStaticEquals(umlConstructor, javaConstructor)
		assertVisibilityEquals(umlConstructor, javaConstructor)
		assertParameterListEquals(umlConstructor.ownedParameters, javaConstructor.parameters)
	}

	def protected checkJavaMethod(Operation umlOperation) {
		val javaMethod = getFirstCorrespondingObject(umlOperation, Method)
		checkJavaMethod(javaMethod, umlOperation)
	}

	def private dispatch checkJavaMethod(Void javaMethod, Operation umlOperation) {
		fail('''No correlating Java method for «umlOperation»''')
	}

	def private dispatch checkJavaMethod(ClassMethod javaMethod, Operation umlOperation) {
		val javaClass = javaMethod.eContainer as org.emftext.language.java.classifiers.Class
		assertJavaClassMethodTraits(javaMethod, umlOperation.name, JavaVisibility.PUBLIC, null, umlOperation.static, umlOperation.abstract, null,
			javaClass)
		assertClassMethodEquals(umlOperation, javaMethod)
	}

	def private dispatch checkJavaMethod(InterfaceMethod javaMethod, Operation umlOperation) {
		val javaInterface = javaMethod.eContainer as org.emftext.language.java.classifiers.Interface
		assertJavaInterfaceMethodTraits(javaMethod, umlOperation.name, null, null, javaInterface)
		assertInterfaceMethodEquals(umlOperation, javaMethod)
	}

	/**
	 * Retrieves all corresponding objects of obj, filters the result list by the class c
	 * and returns the first element of the remaining list
	 * 
	 * {@link #getCorrespondingObjectList(EObject)}
	 * @param obj the object for which the first corresponding object should be retrieved
	 * @return the first corresponding object of obj or null if none could be found
	 */
	def protected <T extends EObject> getFirstCorrespondingObject(EObject obj, Class<T> c) {
		if (obj === null) {
			throw new IllegalArgumentException("Cannot retrieve correspondence for null")
		}
		val correspondingObjectList = getCorrespondenceModel.getCorrespondingEObjects(#[obj]).flatten.filter(c);
		if (correspondingObjectList.nullOrEmpty) {
			logger.warn("There are no corresponding objects for " + obj + " of the type " + c.class + ". Returning null.")
			return null
		} else if (correspondingObjectList.size > 1) {
			logger.warn("There are more than one corresponding object for " + obj + " of the type " + c.class + ". Returning the first.")
		}
		return correspondingObjectList.head
	}

	/** Retrieves all corresponding objects of the type defined by class c
	 * 
	 * {@link tools.vitruv.framework.tests.VitruviusUnmonitoredApplicationTest#getCorrespondenceModel}
	 * @param obj the object for which the corresponding objects should be retrieved
	 * @return the corresponding objects of obj filtered by c or null if none could be found
	 * @throws IllegalArgumentException if obj is null
	 */
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
	def protected assertJavaFileExists(String fileName, String[] namespaces) {
		assertModelExists(JavaPersistenceHelper.buildJavaFilePath('''«fileName».java''', namespaces))
	}

	/**
	 * Namespaces of NamedElement (EList of namespaces) to Array of namespace names.
	 */
	def private String[] convertNamespaces(NamedElement element) {
		val result = new ArrayList
		element.allNamespaces.forEach[it|result.add(0, it.name)] // reversed list of names
		return result.drop(1).toList.toArray(#[]) // drop root element namespace
	}
}

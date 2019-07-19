package tools.vitruv.applications.pcmumlclass.tests

import java.util.ArrayList
import java.util.Collection
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Generalization
import org.eclipse.uml2.uml.InterfaceRealization
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.Type
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.InterfaceMethod
import org.emftext.language.java.members.Method
import org.emftext.language.java.types.TypeReference
import tools.vitruv.applications.umljava.util.java.JavaVisibility
import tools.vitruv.domains.java.util.JavaPersistenceHelper

import static org.junit.Assert.*
import static tools.vitruv.applications.umljava.testutil.JavaTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import static tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*

class TransitiveChangeTest extends PcmUmlClassApplicationTest {

	private static val logger = Logger.getLogger(typeof(TransitiveChangeTest).simpleName)

	def protected checkJavaType(Classifier umlClassifier) {
		assertJavaFileExists(Classifier.name, umlClassifier.convertNamespaces)
		val javaType = getFirstCorrespondingObject(umlClassifier, Type)
		assertEquals(javaType.name, javaType.name)
	}

	def protected checkJavaRealization(InterfaceRealization umlRealization) {
		val javaInterface = getFirstCorrespondingObject(umlRealization.contract, Interface)
		val javaClassifier = getFirstCorrespondingObject(umlRealization.implementingClassifier, Class)
		assertTrue(javaClassifier.allSuperClassifiers.contains(javaInterface)) // classes AND interfaces
	}

	def protected checkJavaGeneralization(Generalization umlGeneralization) {
		val javaSuperClass = getFirstCorrespondingObject(umlGeneralization.general, Class)
		val javaSubClass = getFirstCorrespondingObject(umlGeneralization.specific, Class)
		assertTrue(javaSubClass.allSuperClassifiers.contains(javaSuperClass))
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
		checkTypes(umlAttribute.type, javaAttribute.typeReference, umlAttribute.upper)
	}

	def protected checkJavaConstructor(Operation umlConstructor) {
		val javaConstructor = getFirstCorrespondingObject(umlConstructor, Constructor)
		assertEquals(umlConstructor.name, javaConstructor.name)
		assertJavaModifiableAbstract(javaConstructor, umlConstructor.abstract)
		assertStaticEquals(umlConstructor, javaConstructor)
		assertVisibilityEquals(umlConstructor, javaConstructor)
		assertParameterListEquals(umlConstructor.ownedParameters, javaConstructor.parameters)
	}

	def protected checkJavaMethod(Operation umlOperation) {
		val javaMethod = getFirstCorrespondingObject(umlOperation, Method)
		checkJavaMethod(javaMethod, umlOperation)
		checkTypes(umlOperation.type, javaMethod.typeReference, umlOperation.upper)
		assertParameterListEquals(umlOperation.ownedParameters, javaMethod.parameters)
	}

	def private dispatch checkJavaMethod(Void javaMethod, Operation umlOperation) {
		fail('''No correlating Java method for «umlOperation»''')
	}

	def private dispatch checkJavaMethod(ClassMethod javaMethod, Operation umlOperation) {
		val javaClass = javaMethod.eContainer as Class
		assertJavaClassMethodTraits(javaMethod, umlOperation.name, JavaVisibility.PUBLIC, null, umlOperation.static, umlOperation.abstract, null,
			javaClass)
		assertFinalMethodEquals(umlOperation, javaMethod)
	}

	def private dispatch checkJavaMethod(InterfaceMethod javaMethod, Operation umlOperation) {
		val javaInterface = javaMethod.eContainer as Interface
		assertJavaInterfaceMethodTraits(javaMethod, umlOperation.name, null, null, javaInterface)
		assertJavaModifiableAbstract(javaMethod, umlOperation.abstract)
	}

	/**
	 * Simply checks whether a UML type is equal to a Java type with the exception that when the UML multiplicity suggests 
	 * a collection type, the Java collection type is retrieved for the comparison. 
	 */
	def protected checkTypes(Type umlType, TypeReference javaTypeReference, int umlTypeUpperBound) {
		if (umlTypeUpperBound > 1 || umlTypeUpperBound == LiteralUnlimitedNatural.UNLIMITED) { // has multiary multiplicity
			val referencedClassifier = getClassifierFromTypeReference(javaTypeReference)
			if (!referencedClassifier.isCollection) {
				fail('''UML type «umlType.name» has a multiary multiplicity and should be represented in Java through a collection type instead of «referencedClassifier»''')
			}
			val collectionType = getInnerTypeReferenceOfCollectionTypeReference(javaTypeReference)
			assertTypeEquals(umlType, collectionType) // use type of collection arguments
		} else {
			assertTypeEquals(umlType, javaTypeReference) // compare directly
		}
	}

	/**
	 * Retrieves all corresponding objects of obj, filters the result list by the class c
	 * and returns the first element of the remaining list
	 * 
	 * {@link #getCorrespondingObjectList(EObject)}
	 * @param obj the object for which the first corresponding object should be retrieved
	 * @return the first corresponding object of obj or null if none could be found
	 */
	def protected <T extends EObject> getFirstCorrespondingObject(EObject obj, java.lang.Class<T> c) {
		if (obj === null) {
			throw new IllegalArgumentException("Cannot retrieve correspondence for null")
		}
		val correspondingObjectList = getCorrespondenceModel.getCorrespondingEObjects(#[obj]).flatten.filter(c)
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
	def protected <E> Set<E> getCorrespondingObjectsOfClass(java.lang.Class<E> clazz) {
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
	 * Checks whether a classifier is a java.util.Collection. This means any super type or itself must be a collection.
	 */
	def private dispatch boolean isCollection(Classifier classifier) {
		return false; // classifier is type parameter => cannot be collection
	}

	def private dispatch boolean isCollection(ConcreteClassifier classifier) {
		typeof(Collection).name == classifier.qualifiedName || classifier.allSuperClassifiers.stream.anyMatch[isCollection]
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

package tools.vitruv.applications.transitivechange.tests.circular.pcmumlclassjava;

import static org.junit.jupiter.api.Assertions.*;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.*;
import static tools.vitruv.applications.testutility.integration.JavaUmlElementEqualityValidation.*;
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.getClassifierFromTypeReference;
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.getInnerTypeReferenceOfCollectionTypeReference;

import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.ContainersPackage;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.types.TypeReference;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.applications.pcmumlclass.PcmUmlClassHelper;
import tools.vitruv.applications.testutility.integration.LegacyPcmUmlClassApplicationTest;
import tools.vitruv.applications.transitivechange.tests.util.TransitiveChangeSetup;
import tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper;
import tools.vitruv.applications.util.temporary.java.JavaSetup;
import tools.vitruv.applications.util.temporary.java.JavaVisibility;
import tools.vitruv.applications.util.temporary.pcm.PcmDataTypeUtil;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.testutils.RegisterMetamodelsInStandalone;

/**
 * Transitive change test class for networks of UML, Java and PCM models.
 * Provides additional checks for comparing the Java model to the correlating UML model.
 */
@ExtendWith(RegisterMetamodelsInStandalone.class)
public abstract class PcmUmlJavaTransitiveChangeTest extends LegacyPcmUmlClassApplicationTest {

	protected static final int ARRAY_LIST_SELECTION = 0;
	protected static boolean linearNetwork; // set true (before class) to avoid the transformation between PCM and Java
	private static final Logger logger = Logger.getLogger(PcmUmlJavaTransitiveChangeTest.class.getSimpleName());

	@BeforeAll
	public static void setupJavaFactories() {
		JavaSetup.prepareFactories();
	}

	@BeforeEach
	public final void setupJavaClasspath() {
		JavaSetup.resetClasspathAndRegisterStandardLibrary();
	}

	@Override
	protected List<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return TransitiveChangeSetup.getChangePropagationSpecifications(linearNetwork);
	}

	protected void checkJavaType(Classifier umlClassifier) {
		assertJavaFileExists(umlClassifier.getName(), convertNamespaces(umlClassifier));
		ConcreteClassifier javaType = getFirstCorrespondingObject(umlClassifier, ConcreteClassifier.class);
		assertEquals(umlClassifier.getName(), javaType.getName());
	}

	protected void checkJavaRealization(InterfaceRealization umlRealization) {
		Interface javaInterface = getFirstCorrespondingObject(umlRealization.getContract(), Interface.class);
		Class javaClassifier = getFirstCorrespondingObject(umlRealization.getImplementingClassifier(), Class.class);
		assertTrue(javaClassifier.getAllSuperClassifiers().contains(javaInterface)); // classes AND interfaces
	}

	protected void checkJavaGeneralization(Generalization umlGeneralization) {
		Class javaSuperClass = getFirstCorrespondingObject(umlGeneralization.getGeneral(), Class.class);
		Class javaSubClass = getFirstCorrespondingObject(umlGeneralization.getSpecific(), Class.class);
		assertTrue(javaSubClass.getAllSuperClassifiers().contains(javaSuperClass));
	}

	protected void checkJavaPackage(Package umlPackage) {
		org.emftext.language.java.containers.Package javaPackage = getFirstCorrespondingObject(umlPackage,
			org.emftext.language.java.containers.Package.class);
		assertEquals(umlPackage.getName(), javaPackage.getName());
		assertElementsEqual(umlPackage, javaPackage);
	}

	protected void checkNumberOfJavaPackages(Package umlRootPackage) {
		Iterable<org.emftext.language.java.containers.Package> allJavaPackages = getCorrespondingEObjects(
			ContainersPackage.Literals.PACKAGE, org.emftext.language.java.containers.Package.class);
		int umlPackagesCount = countPackages(umlRootPackage);
		if (umlRootPackage instanceof Model) umlPackagesCount -= 1; // do not count the model
		assertEquals(Iterables.size(allJavaPackages), umlPackagesCount);
	}

	protected void checkUmlPackage(org.emftext.language.java.containers.Package javaPackage) {
		Package umlPackage = getFirstCorrespondingObject(javaPackage, Package.class);
		assertEquals(umlPackage.getName(), javaPackage.getName());
		assertElementsEqual(umlPackage, javaPackage);
	}

	protected void checkJavaAttribute(Property umlAttribute) {
		Field javaAttribute = getFirstCorrespondingObject(umlAttribute, Field.class);
		assertEquals(umlAttribute.getName(), javaAttribute.getName());
		assertVisibilityEquals(umlAttribute, javaAttribute);
		assertFinalAttributeEquals(umlAttribute, javaAttribute);
		assertStaticEquals(umlAttribute, javaAttribute);
		checkTypes(umlAttribute.getType(), javaAttribute.getTypeReference(), umlAttribute.getUpper());
	}

	protected void checkJavaConstructor(Operation umlConstructor) {
		Constructor javaConstructor = getFirstCorrespondingObject(umlConstructor, Constructor.class);
		assertEquals(umlConstructor.getName(), javaConstructor.getName());
		assertJavaModifiableAbstract(javaConstructor, umlConstructor.isAbstract());
		assertStaticEquals(umlConstructor, javaConstructor);
		assertVisibilityEquals(umlConstructor, javaConstructor);
		assertParameterListEquals(umlConstructor.getOwnedParameters(), javaConstructor.getParameters());
	}

	protected void checkJavaMethod(Operation umlOperation) {
		Method javaMethod = getFirstCorrespondingObject(umlOperation, Method.class);
		checkJavaMethod(javaMethod, umlOperation);
		checkTypes(umlOperation.getType(), javaMethod.getTypeReference(), umlOperation.getUpper());
		assertParameterListEquals(umlOperation.getOwnedParameters(), javaMethod.getParameters());
	}

	private void checkJavaMethod(Method javaMethod, Operation umlOperation) {
		if (javaMethod instanceof ClassMethod classMethod && umlOperation != null) {
			Class javaClass = (Class) classMethod.eContainer();
			assertJavaClassMethodTraits(classMethod, umlOperation.getName(), JavaVisibility.PUBLIC, null,
				umlOperation.isStatic(), umlOperation.isAbstract(), classMethod.getParameters(), javaClass);
			assertFinalMethodEquals(umlOperation, classMethod);
		} else if (javaMethod instanceof InterfaceMethod interfaceMethod && umlOperation != null) {
			Interface javaInterface = (Interface) interfaceMethod.eContainer();
			assertJavaInterfaceMethodTraits(interfaceMethod, umlOperation.getName(), null,
				interfaceMethod.getParameters(), javaInterface);
			assertJavaModifiableAbstract(interfaceMethod, umlOperation.isAbstract());
		} else if (javaMethod == null && umlOperation != null) {
			fail("No correlating Java method for " + umlOperation);
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " + Arrays.asList(javaMethod, umlOperation));
		}
	}

	protected void assertUmlTypeEquality(Type firstType, Type secondType) {
		if (firstType instanceof PrimitiveType) {
			var pcmPrimitiveTypes = PcmDataTypeUtil.getPcmPrimitiveTypes(firstType.eResource().getResourceSet());
			assertTrue(EcoreUtil.equals(
				PcmUmlClassHelper.mapUmlToPcmPrimitiveType((PrimitiveType) firstType, pcmPrimitiveTypes),
				PcmUmlClassHelper.mapUmlToPcmPrimitiveType((PrimitiveType) secondType, pcmPrimitiveTypes)));
		} else {
			assertTrue(EcoreUtil.equals(firstType, secondType));
		}
	}

	/**
	 * Simply checks whether a UML type is equal to a Java type with the exception that when the UML multiplicity suggests
	 * a collection type, the Java collection type is retrieved for the comparison.
	 */
	protected void checkTypes(Type umlType, TypeReference javaTypeReference, int umlTypeUpperBound) {
		if (umlTypeUpperBound > 1 || umlTypeUpperBound == LiteralUnlimitedNatural.UNLIMITED) { // has multiary multiplicity
			var referencedClassifier = getClassifierFromTypeReference(javaTypeReference);
			if (!isCollection(referencedClassifier)) {
				fail("UML type " + umlType.getName()
					+ " has a multiary multiplicity and should be represented in Java through a collection type instead of "
					+ referencedClassifier);
			}
			TypeReference collectionType = getInnerTypeReferenceOfCollectionTypeReference(javaTypeReference);
			assertTypeEquals(umlType, collectionType); // use type of collection arguments
		} else {
			assertTypeEquals(umlType, javaTypeReference); // compare directly
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
	protected <T extends EObject> T getFirstCorrespondingObject(EObject obj, java.lang.Class<T> c) {
		if (obj == null) {
			throw new IllegalArgumentException("Cannot retrieve correspondence for null");
		}
		Iterable<T> correspondingObjectList = getCorrespondingEObjects(obj, c);
		if (correspondingObjectList == null || Iterables.isEmpty(correspondingObjectList)) {
			logger.warn("There are no corresponding objects for " + obj + " of the type " + c.getClass() +
				". Returning null.");
			return null;
		} else if (Iterables.size(correspondingObjectList) > 1) {
			logger.warn("There are more than one corresponding object for " + obj + " of the type " + c.getClass() +
				". Returning the first.");
		}
		return Iterables.getFirst(correspondingObjectList, null);
	}

	/**
	 * Retrieves all corresponding objects of obj and filters the result list by the class c
	 *
	 * {@link #getCorrespondingObjectList(EObject)}
	 * @param obj the object for which the corresponding objects should be retrieved
	 * @return the corresponding objects of obj filtered by c or null if none could be found
	 */
	protected void assertJavaFileExists(String fileName, String[] namespaces) {
		assertModelExists(JavaPersistenceHelper.buildJavaFilePath(fileName + ".java", Arrays.asList(namespaces)));
	}

	private int countPackages(PackageableElement element) {
		if (element instanceof Package umlPackage) {
			int count = 1;
			for (PackageableElement packagedElement : umlPackage.getPackagedElements()) {
				count += countPackages(packagedElement);
			}
			return count;
		} else if (element != null) {
			return 0; // no more packages in this branch
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " + Arrays.asList(element));
		}
	}

	/**
	 * Checks whether a classifier is a java.util.Collection. This means any super type or itself must be a collection.
	 */
	private boolean isCollection(EObject classifier) {
		if (classifier instanceof ConcreteClassifier concreteClassifier) {
			return Collection.class.getName().equals(concreteClassifier.getQualifiedName())
				|| concreteClassifier.getAllSuperClassifiers().stream().anyMatch(this::isCollection);
		} else if (classifier instanceof Classifier) {
			return false; // classifier is type parameter => cannot be collection
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " + Arrays.asList(classifier));
		}
	}

	/**
	 * Fixed version of TestUtil.assertParameterListEquals(), mostly copied. This versions accepts Java collection types for certain UML multiplicities.
	 * @see TransitiveChangeTest.checkTypes(Type, TypeReference, int)
	 */
	private void assertParameterListEquals(List<Parameter> umlParameters,
		List<org.emftext.language.java.parameters.Parameter> javaParameters) {
		List<Parameter> uParamListWithoutReturn = umlParameters.stream()
			.filter(it -> it.getDirection() != ParameterDirectionKind.RETURN_LITERAL)
			.collect(Collectors.toList());
		if (uParamListWithoutReturn == null) {
			assertNull(javaParameters);
		} else {
			assertEquals(uParamListWithoutReturn.size(), javaParameters.size());
			for (Parameter umlParameter : uParamListWithoutReturn) {
				List<org.emftext.language.java.parameters.Parameter> javaParameter = javaParameters.stream()
					.filter(it -> Objects.equals(it.getName(), umlParameter.getName()))
					.collect(Collectors.toList());
				if (javaParameter.isEmpty()) {
					fail("There is no corresponding java parameter with the name '" + umlParameter.getName() + "'");
				} else if (javaParameter.size() > 1) {
					System.out.println("There are more than one parameter with the name '" + umlParameter.getName() + "'");
				} else {
					checkTypes(umlParameter.getType(), javaParameter.get(0).getTypeReference(), umlParameter.getUpper());
				}
			}
		}
	}

	/**
	 * Namespaces of NamedElement (EList of namespaces) to Array of namespace names.
	 */
	private String[] convertNamespaces(NamedElement element) {
		List<String> result = new ArrayList<>();
		element.allNamespaces().forEach(it -> result.add(0, it.getName())); // reversed list of names
		return result.stream().skip(1).toArray(String[]::new); // drop root element namespace
	}

	/**
	 * Returns the given string with its first character in upper case.
	 */
	protected static String toFirstUpper(String string) {
		if (string == null || string.isEmpty() || Character.isUpperCase(string.charAt(0))) {
			return string;
		}
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}

	/**
	 * Returns the given string with its first character in lower case.
	 */
	protected static String toFirstLower(String string) {
		if (string == null || string.isEmpty() || Character.isLowerCase(string.charAt(0))) {
			return string;
		}
		return string.substring(0, 1).toLowerCase() + string.substring(1);
	}
}

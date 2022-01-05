package tools.vitruv.applications.umljava.tests.uml2java

import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.types.TypesFactory
import org.junit.jupiter.api.Test
import tools.vitruv.applications.util.temporary.java.JavaVisibility

import static tools.vitruv.applications.umljava.tests.util.JavaTestUtil.*
import static tools.vitruv.domains.java.util.JavaModificationUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import org.eclipse.uml2.uml.Model
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.*
import org.emftext.language.java.members.ClassMethod
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.ParameterizedTest
import static tools.vitruv.applications.util.temporary.java.JavaModifierUtil.getJavaVisibilityConstantFromUmlVisibilityKind

/**
 * A test class to test class methods and its traits.
 */
class UmlToJavaClassMethodTest extends AbstractUmlToJavaTest {
	static val CLASS_NAME = "ClassName"
	static val CLASS_NAME_2 = "ClassName2"
	static val TYPE_CLASS_NAME = "TypeName"
	static val OPERATION_NAME = "classMethod"
	static val OPERATION_RENAME = "classMethodRenamed"
	static val DATATYPE_NAME = "DataTypeName"
	static val DATATYPE_NAME_2 = "DataTypeName2"

	private def assertClassWithNameInRootPackage(String name) {
		assertClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Class, org.eclipse.uml2.uml.Class,
			name)
	}

	private def assertSingleClassWithNameInRootPackage(String name) {
		assertSingleClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Class,
			org.eclipse.uml2.uml.Class, name)
	}

	private def assertDataTypeWithNameInRootPackage(String name) {
		assertClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Class, org.eclipse.uml2.uml.DataType,
			name)
	}
	
	private def assertSingleDataTypeWithNameInRootPackage(String name) {
		assertSingleClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Class,
			org.eclipse.uml2.uml.DataType, name)
	}

	private def void createClassWithOperation(String className, String operationName) {
		createClassInRootPackage(className)
		changeUmlModel [
			claimClass(className) => [
				createOwnedOperation(operationName, null, null, null)
			]
		]
	}

	private def void createDataTypeWithOperation(String dataTypeName, String operationName) {
		createDataTypeInRootPackage(dataTypeName)
		changeUmlModel [
			claimDataType(dataTypeName) => [
				createOwnedOperation(operationName, null, null, null)
			]
		]
	}

	private def changeMethod(String className, String methodName, (Operation)=>void changeFunction) {
		changeMethod(className, methodName, [model, operation|changeFunction.apply(operation)])
	}

	private def changeMethod(String className, String methodName, (Model, Operation)=>void changeFunction) {
		changeUmlModel [
			val model = it
			claimClass(className) => [
				claimOperation(methodName) => [
					changeFunction.apply(model, it)
				]
			]
		]
	}

	/**
	 * Initializes two uml classes and a primitive type. One uml class contains 
	 * an operation with a parameter.
	 */
//	@BeforeEach
//	def void before() {
//		uClass = createSimpleUmlClass(rootElement, CLASS_NAME)
//		typeClass = createSimpleUmlClass(rootElement, TYPE_NAME)
//		pType = UmlTypeUtil.getSupportedPredefinedUmlPrimitiveTypes(resourceRetriever).findFirst[it.name == "Integer"]
//		uParam = createUmlParameter(PARAMETER_NAME, pType)
//		uOperation = createUmlOperation(OPERATION_NAME, null, VisibilityKind.PUBLIC_LITERAL, false, false, #[uParam])
//		uClass.ownedOperations += uOperation
//		rootElement.packagedElements += uClass
//		rootElement.packagedElements += typeClass
//		propagate
//	}
	/**
	 * Tests if creating a UML operation also causes the creating of an corresponding
	 * Java method.
	 */
	@Test
	def void testCreateClassMethod() {
		createClassWithOperation(CLASS_NAME, OPERATION_NAME)
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createJavaClassesView => [
			val javaClass = claimJavaClass(CLASS_NAME)
			val javaMethod = javaClass.claimClassMethod(OPERATION_NAME)
			assertJavaClassMethodTraits(javaMethod, OPERATION_NAME, JavaVisibility.PUBLIC,
				TypesFactory.eINSTANCE.createVoid, false, false, null, javaClass)
		]
	}

	/**
	 * Tests the change of the UML method return type. Checks if
	 * the corresponding Java method adapted the corresponding type.
	 */
	@Test
	def void testChangeReturnType() {
		createClassWithOperation(CLASS_NAME, OPERATION_NAME)
		createClassInRootPackage(TYPE_CLASS_NAME)
		changeMethod(CLASS_NAME, OPERATION_NAME) [ model, operation |
			operation.type = model.claimClass(TYPE_CLASS_NAME)
		]
		assertClassWithNameInRootPackage(CLASS_NAME)
		createJavaClassesView => [
			val javaMethod = claimJavaClass(CLASS_NAME).claimClassMethod(OPERATION_NAME)
			val javaTypeClass = claimJavaClass(TYPE_CLASS_NAME)
			assertJavaElementHasTypeRef(javaMethod, createNamespaceClassifierReference(javaTypeClass))
		]
	}

	/**
	 * Tests if renaming a method is correctly reflected on the Java side.
	 */
	@Test
	def void testRenameMethod() {
		createClassWithOperation(CLASS_NAME, OPERATION_NAME)
		changeMethod(CLASS_NAME, OPERATION_NAME) [
			name = OPERATION_RENAME
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createJavaClassesView => [
			val javaClass = claimJavaClass(CLASS_NAME)
			assertJavaMemberContainerDontHaveMember(javaClass, OPERATION_NAME)
		]
	}

	/**
	 * Tests if deleting a method is correctly reflected on the Java side.
	 */
	@Test
	def void testDeleteMethod() {
		createClassWithOperation(CLASS_NAME, OPERATION_NAME)
		changeMethod(CLASS_NAME, OPERATION_NAME) [
			destroy()
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createJavaClassesView => [
			val javaClass = claimJavaClass(CLASS_NAME)
			assertJavaMemberContainerDontHaveMember(javaClass, OPERATION_NAME)
		]
	}

	@Test
	def void testMoveMethod() {
		createClassWithOperation(CLASS_NAME, OPERATION_NAME)
		createClassInRootPackage(CLASS_NAME_2)
		changeMethod(CLASS_NAME, OPERATION_NAME) [ model, method |
			model.claimClass(CLASS_NAME_2).ownedOperations += method
		]
		assertClassWithNameInRootPackage(CLASS_NAME)
		assertClassWithNameInRootPackage(CLASS_NAME_2)
		createJavaClassesView => [
			val javaClass = claimJavaClass(CLASS_NAME)
			val javaClass2 = claimJavaClass(CLASS_NAME_2)
			assertJavaMemberContainerDontHaveMember(javaClass, OPERATION_NAME)
			assertThat(CLASS_NAME_2 + " must have operation " + OPERATION_NAME,
				javaClass2.getMembersByName(OPERATION_NAME).toSet, is(not(emptySet)))
		]
	}

	private def void changeAndCheckPropertyOfAttribute(String className, String methodName,
		(Operation)=>void changeUmlMethod, (ClassMethod)=>void validateJavaMethod) {
		changeMethod(className, methodName) [
			changeUmlMethod.apply(it)
		]
		assertSingleClassWithNameInRootPackage(className)
		createJavaClassesView => [
			val javaMethod = claimJavaClass(className).claimClassMethod(methodName)
			validateJavaMethod.apply(javaMethod)
		]
	}

	/**
	 * Tests if setting a method static correctly reflected on the Java side.
	 */
	@Test
	def void testStaticMethod() {
		createClassWithOperation(CLASS_NAME, OPERATION_NAME)
		changeAndCheckPropertyOfAttribute(CLASS_NAME, OPERATION_NAME, [isStatic = true], [
			assertJavaModifiableStatic(it, true)
		])
		changeAndCheckPropertyOfAttribute(CLASS_NAME, OPERATION_NAME, [isStatic = false], [
			assertJavaModifiableStatic(it, false)
		])
	}

	/**
	 * Tests if setting a method final correctly reflected on the Java side.
	 */
	@Test
	def void testFinalMethod() {
		createClassWithOperation(CLASS_NAME, OPERATION_NAME)
		changeAndCheckPropertyOfAttribute(CLASS_NAME, OPERATION_NAME, [isLeaf = true], [
			assertJavaModifiableFinal(it, true)
		])
		changeAndCheckPropertyOfAttribute(CLASS_NAME, OPERATION_NAME, [isLeaf = false], [
			assertJavaModifiableFinal(it, false)
		])
	}

	/**
	 * Tests if setting a method abstract is correctly reflected on the Java side.
	 */
	@Test
	def testAbstractMethod() {
		createClassWithOperation(CLASS_NAME, OPERATION_NAME)
		changeAndCheckPropertyOfAttribute(CLASS_NAME, OPERATION_NAME, [isAbstract = true], [
			assertJavaModifiableAbstract(it, true)
		])
		changeAndCheckPropertyOfAttribute(CLASS_NAME, OPERATION_NAME, [isAbstract = false], [
			assertJavaModifiableAbstract(it, false)
		])
	}

	/**
	 * Tests if visibility changes are propagated to the Java method.
	 */
	@ParameterizedTest
	@EnumSource(value=VisibilityKind, names=#["PUBLIC_LITERAL"], mode=EnumSource.Mode.EXCLUDE)
	def void testMethodVisibility(VisibilityKind visibility) {
		createClassWithOperation(CLASS_NAME, OPERATION_NAME)
		changeAndCheckPropertyOfAttribute(CLASS_NAME, OPERATION_NAME, [it.visibility = visibility], [
			assertJavaModifiableHasVisibility(it, getJavaVisibilityConstantFromUmlVisibilityKind(visibility))
		])
	}

	/**
	 * Tests the creation of a method that act as constructor and checks if a 
	 * constructor is created on the Java side.
	 */
	@Test
	def void testCreateConstructor() {
		createClassWithOperation(CLASS_NAME, CLASS_NAME)
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createJavaClassesView => [
			claimJavaClass(CLASS_NAME).claimConstructor()
		]
	}

	@Test
	def void testMoveConstructor() {
		createClassWithOperation(CLASS_NAME, CLASS_NAME)
		createClassInRootPackage(CLASS_NAME_2)
		changeMethod(CLASS_NAME, CLASS_NAME) [ model, method |
			model.claimClass(CLASS_NAME_2).ownedOperations += method => [
				name = CLASS_NAME_2
			]
		]
		assertClassWithNameInRootPackage(CLASS_NAME)
		assertClassWithNameInRootPackage(CLASS_NAME_2)
		createJavaClassesView => [
			val javaClass = claimJavaClass(CLASS_NAME)
			val javaClass2 = claimJavaClass(CLASS_NAME_2)
			javaClass2.claimConstructor()
			assertJavaMemberContainerDontHaveMember(javaClass, CLASS_NAME)
			assertJavaMemberContainerDontHaveMember(javaClass, CLASS_NAME_2)
			assertJavaMemberContainerDontHaveMember(javaClass2, CLASS_NAME)
			assertThat(javaClass2.getMembersByName(CLASS_NAME_2).toSet, is(not(emptySet)))
		]
	}

	/**
	 * Same as testMoveConstructor but the order of move and rename is switched
	 */
	@Test
	def void testMoveConstructor2() {
		createClassWithOperation(CLASS_NAME, CLASS_NAME)
		createClassInRootPackage(CLASS_NAME_2)
		changeMethod(CLASS_NAME, CLASS_NAME) [ model, method |
			method => [
				name = CLASS_NAME_2
			]
			model.claimClass(CLASS_NAME_2).ownedOperations += method
		]
		assertClassWithNameInRootPackage(CLASS_NAME)
		assertClassWithNameInRootPackage(CLASS_NAME_2)
		createJavaClassesView => [
			val javaClass = claimJavaClass(CLASS_NAME)
			val javaClass2 = claimJavaClass(CLASS_NAME_2)
			javaClass2.claimConstructor()
			assertJavaMemberContainerDontHaveMember(javaClass, CLASS_NAME)
			assertJavaMemberContainerDontHaveMember(javaClass, CLASS_NAME_2)
			assertJavaMemberContainerDontHaveMember(javaClass2, CLASS_NAME)
			assertThat(javaClass2.getMembersByName(CLASS_NAME_2).toSet, is(not(emptySet)))
		]
	}

	/**
	 * Checks if method creating in data types is reflected in the corresponding Java class.
	 */
	@Test
	def void testCreateMethodInDataType() {
		createDataTypeWithOperation(DATATYPE_NAME, OPERATION_NAME)
		assertSingleDataTypeWithNameInRootPackage(DATATYPE_NAME)
		createJavaClassesView => [
			val javaClass = claimJavaClass(DATATYPE_NAME)
			val javaMethod = javaClass.claimClassMethod(OPERATION_NAME)
			assertJavaClassMethodTraits(javaMethod, OPERATION_NAME, JavaVisibility.PUBLIC,
				TypesFactory.eINSTANCE.createVoid, false, false, null, javaClass)
		]
	}

	/**
	 * Tests the deletion of methods in data types and if the deletion is
	 * propagated to the Java model.
	 */
	@Test
	def void testDeleteMethodInDataType() {
		createDataTypeWithOperation(DATATYPE_NAME, OPERATION_NAME)
		changeUmlModel [
			claimDataType(DATATYPE_NAME) => [
				claimOperation(OPERATION_NAME) => [
					destroy()	
				]
			]
		]
		assertSingleDataTypeWithNameInRootPackage(DATATYPE_NAME)
		createJavaClassesView => [
			val javaClass = claimJavaClass(DATATYPE_NAME)
			assertJavaMemberContainerDontHaveMember(javaClass, OPERATION_NAME)
		]
	}

	@Test
	def void testMoveMethodInDataType() {
		createDataTypeWithOperation(DATATYPE_NAME, OPERATION_NAME)
		createDataTypeInRootPackage(DATATYPE_NAME_2)
		changeUmlModel [
			val operation = claimDataType(DATATYPE_NAME).claimOperation(OPERATION_NAME)
			claimDataType(DATATYPE_NAME_2) => [
				ownedOperations += operation
			]
		]
		assertDataTypeWithNameInRootPackage(DATATYPE_NAME)
		assertDataTypeWithNameInRootPackage(DATATYPE_NAME_2)
		createJavaClassesView => [
			val javaClass = claimJavaClass(DATATYPE_NAME)
			val javaClass2 = claimJavaClass(DATATYPE_NAME_2)
			assertJavaMemberContainerDontHaveMember(javaClass, OPERATION_NAME)
			assertThat(javaClass2.getMembersByName(OPERATION_NAME).toSet, is(not(emptySet)))
		]
	}
}

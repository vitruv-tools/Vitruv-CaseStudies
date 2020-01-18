package tools.vitruv.applications.umljava.uml2java.tests

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.types.TypesFactory
import org.junit.Before
import org.junit.Test
import tools.vitruv.applications.umljava.uml2java.Uml2JavaTransformationTest
import tools.vitruv.applications.umljava.util.UmlJavaTypePropagationHelper
import tools.vitruv.applications.util.temporary.java.JavaVisibility

import static org.junit.Assert.*
import static tools.vitruv.applications.umljava.testutil.JavaTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlOperationAndParameterUtil.*

/**
 * A Test class to test class methods and its traits.
 * 
 * @author Fei
 */
class UmlToJavaClassMethodTest extends Uml2JavaTransformationTest {
    private static val CLASS_NAME = "ClassName";
    private static val TYPE_NAME = "TypeName";
    private static val OPERATION_NAME = "classMethod";
    private static val STANDARD_OPERATION_NAME = "standardMethod";
    private static val OPERATION_RENAME = "classMethodRenamed";
    private static val PARAMETER_NAME = "parameterName";
    private static val DATATYPE_NAME = "DataTypeName";
    
    private static var Class uClass
    private static var Class typeClass
    private static var Parameter uParam
    private static var PrimitiveType pType
    private static var Operation uOperation
    
    /**
     * Initializes two uml classes and a primitive type. One uml class contains 
     * an operation with a parameter.
     */
    @Before
    def void before() {
        uClass = createSimpleUmlClass(rootElement, CLASS_NAME);
        typeClass = createSimpleUmlClass(rootElement, TYPE_NAME);
        pType = UmlJavaTypePropagationHelper.getSupportedPredefinedUmlPrimitiveTypes(resourceRetriever).findFirst[it.name=="Integer"]
        uParam = createUmlParameter(PARAMETER_NAME, pType)
        uOperation = createUmlOperation(OPERATION_NAME, null, VisibilityKind.PUBLIC_LITERAL, false, false, #[uParam])
        uClass.ownedOperations += uOperation;
        rootElement.packagedElements += uClass;
        rootElement.packagedElements += typeClass;
        saveAndSynchronizeChanges(rootElement);
    }
    
    /**
     * Tests if creating a uml operation also causes the creating of an corresponding
     * java method.
     */
    @Test
    def void testCreateClassMethod() {
        val operation = uClass.createOwnedOperation(STANDARD_OPERATION_NAME, null, null, null);
        saveAndSynchronizeChanges(uClass);
        
        val jMethod = getCorrespondingClassMethod(operation)
        val jClass = getCorrespondingClass(uClass)
        assertNotNull(jMethod)
        assertJavaClassMethodTraits(jMethod, STANDARD_OPERATION_NAME, JavaVisibility.PUBLIC,
        	TypesFactory.eINSTANCE.createVoid, false, false, null, jClass)
        assertClassMethodEquals(operation, jMethod)
    }
    
    /**
     * Tests the change of the uml method return type. Checks if
     * the corresponding java method adapated the corresponding type.
     */
    @Test
    def void testChangeReturnType() {
        uOperation.type = typeClass;
        saveAndSynchronizeChanges(uOperation);
        
        val jMethod = getCorrespondingClassMethod(uOperation)
        val jTypeClass = getCorrespondingClass(typeClass)
        assertJavaElementHasTypeRef(jMethod, createNamespaceReferenceFromClassifier(jTypeClass))
        assertClassMethodEquals(uOperation, jMethod)
    }
    
    /**
     * Tests if renaming a method is correctly reflected on the java side.
     */
    @Test
    def testRenameMethod() {
        uOperation.name = OPERATION_RENAME;
        saveAndSynchronizeChanges(uOperation);
        
        val jMethod = getCorrespondingClassMethod(uOperation)
        val jClass = getCorrespondingClass(uClass)
        assertEquals(OPERATION_RENAME, jMethod.name)
        assertClassMethodEquals(uOperation, jMethod)
        assertJavaMemberContainerDontHaveMember(jClass, OPERATION_NAME)
    }
    
    /**
     * Tests if deleting a method is correctly reflected on the java side.
     */
    @Test
    def testDeleteMethod() {
        uOperation.destroy;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getCorrespondingClass(uClass)
        assertJavaMemberContainerDontHaveMember(jClass, OPERATION_NAME)
    }
    
    /**
     * Tests if setting a method static correctly reflected on the java side.
     */
    @Test
    def testStaticMethod() {
        uOperation.isStatic = true;
        saveAndSynchronizeChanges(uOperation);
        
        val jMethod = getCorrespondingClassMethod(uOperation)
        assertJavaModifiableStatic(jMethod, true)
        assertClassMethodEquals(uOperation, jMethod)
    }
    
    /**
     * Tests if setting a method final correctly reflected on the java side.
     */
    @Test
    def testFinalMethod() {
        uOperation.isLeaf = true;
        saveAndSynchronizeChanges(uOperation);
        
        val jMethod = getCorrespondingClassMethod(uOperation)
        assertJavaModifiableFinal(jMethod, true)
        assertClassMethodEquals(uOperation, jMethod)
    }
    
    /**
     * Tests if setting a method abstract is correctly reflected on the java side.
     */
    @Test
    def testAbstractMethod() {
        uOperation.isAbstract = true;
        saveAndSynchronizeChanges(uOperation);
        
        val jMethod = getCorrespondingClassMethod(uOperation)
        assertJavaModifiableAbstract(jMethod, true)
        assertClassMethodEquals(uOperation, jMethod)
    }
    
    /**
     * Tests if visibility changes are propagated to the java method.
     */
    @Test
    def testMethodVisibility() {
        uOperation.visibility = VisibilityKind.PRIVATE_LITERAL;
        saveAndSynchronizeChanges(uOperation);
        
        var jMethod = getCorrespondingClassMethod(uOperation)
        assertJavaModifiableHasVisibility(jMethod, JavaVisibility.PRIVATE)
        assertClassMethodEquals(uOperation, jMethod)
        
        uOperation.visibility = VisibilityKind.PROTECTED_LITERAL;
        saveAndSynchronizeChanges(uOperation);
        
        jMethod = getCorrespondingClassMethod(uOperation)
        assertJavaModifiableHasVisibility(jMethod, JavaVisibility.PROTECTED)
        assertClassMethodEquals(uOperation, jMethod)
    }
    
    /**
     * Tests the creation of a method that act as constructor and checks if a 
     * constructor is created on the java side.
     */
    @Test
    def testCreateConstructor() {
        val uConstr = createSimpleUmlOperation(uClass.name)
        uClass.ownedOperations += uConstr
        saveAndSynchronizeChanges(uClass)
        val jConstr = getCorrespondingConstructor(uConstr)
        assertNotNull(jConstr)
    }
    
    /**
     * Checks if method creating in datatypes is reflected in the corresponding java class.
     */
    @Test
    def void testCreateMethodInDataType() {
        val dataType = createUmlDataType(rootElement, DATATYPE_NAME)
        val operation = dataType.createOwnedOperation(STANDARD_OPERATION_NAME, null, null, null);
        saveAndSynchronizeChanges(rootElement);
        
        val jMethod = getCorrespondingClassMethod(operation)
        val jClass = getCorrespondingClass(dataType)
        assertNotNull(jMethod)
        assertJavaClassMethodTraits(jMethod, STANDARD_OPERATION_NAME, JavaVisibility.PUBLIC,
            TypesFactory.eINSTANCE.createVoid, false, false, null, jClass)
        assertClassMethodEquals(operation, jMethod)
    }
    
    /**
     * Tests the deletion of methods in data types and if the deletion is
     * propagated to the java model.
     */
    @Test
    def void testDeleteMethodInDataType() {
        val dataType = createUmlDataType(rootElement, DATATYPE_NAME)
        val operation = dataType.createOwnedOperation(STANDARD_OPERATION_NAME, null, null, null);
        saveAndSynchronizeChanges(rootElement);
        
        var jMethod = getCorrespondingClassMethod(operation)
        assertNotNull(jMethod)
        
        operation.destroy
        saveAndSynchronizeChanges(dataType)
        
        val jClass = getCorrespondingClass(dataType)
        assertJavaMemberContainerDontHaveMember(jClass, STANDARD_OPERATION_NAME)
    }
}

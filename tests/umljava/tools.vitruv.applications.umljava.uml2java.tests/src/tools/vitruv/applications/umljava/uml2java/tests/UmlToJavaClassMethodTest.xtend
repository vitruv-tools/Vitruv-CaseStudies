package tools.vitruv.applications.umljava.uml2java.tests


import static tools.vitruv.applications.umljava.testutil.JavaTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import static tools.vitruv.applications.umljava.util.uml.UmlClassifierAndPackageUtil.*
import static tools.vitruv.applications.umljava.util.uml.UmlOperationAndParameterUtil.*
import static extension tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*
import tools.vitruv.applications.umljava.uml2java.Uml2JavaTransformationTest
import org.junit.Test
import static org.junit.Assert.*;
import org.junit.Before
import org.junit.After
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.umljava.util.java.JavaVisibility
import org.emftext.language.java.types.TypesFactory

class UmlToJavaClassMethodTest extends Uml2JavaTransformationTest {
    private static val CLASS_NAME = "ClassName";
    private static val TYPE_NAME = "TypeName";
    private static val OPERATION_NAME = "classMethod";
    private static val STANDARD_OPERATION_NAME = "standardMethod";
    private static val OPERATION_RENAME = "classMethodRenamed";
    private static val PRIMITIVE_TYPE = "int"
    private static val PARAMETER_NAME = "parameterName";
    
    private static var org.eclipse.uml2.uml.Class uClass
    private static var org.eclipse.uml2.uml.Class typeClass
    private static var org.eclipse.uml2.uml.Parameter uParam
    private static var org.eclipse.uml2.uml.PrimitiveType pType
    private static var Operation uOperation
    
    @Before
    def void before() {
        uClass = createSimpleUmlClass(rootElement, CLASS_NAME);
        typeClass = createSimpleUmlClass(rootElement, TYPE_NAME);
        pType = createUmlPrimitiveTypeAndAddToModel(rootElement, PRIMITIVE_TYPE)
        uParam = createUmlParameter(PARAMETER_NAME, pType)
        uOperation = createUmlOperation(OPERATION_NAME, null, VisibilityKind.PUBLIC_LITERAL, false, false, #[uParam])
        uClass.ownedOperations += uOperation;
        rootElement.packagedElements += uClass;
        rootElement.packagedElements += typeClass;
        rootElement.packagedElements += pType;
        saveAndSynchronizeChanges(rootElement);
    }
    
    //@After
    def void after() {
        if (uOperation != null) {
            uOperation.destroy;
        }
        if (uClass != null) {
            uClass.destroy;
        }
        if (typeClass != null) {
            typeClass.destroy;
        }
        if (uParam != null) {
        	uParam.destroy
        }
        if (pType != null) {
        	pType.destroy
        }
        saveAndSynchronizeChanges(rootElement);
    }
    
    @Test
    def void testCreateSimpleMethod() {
        val operation = uClass.createOwnedOperation(STANDARD_OPERATION_NAME, null, null, null);
        saveAndSynchronizeChanges(uClass);
        
        val jMethod = getCorrespondingClassMethod(operation)
        val jClass = getCorrespondingClass(uClass)
        assertJavaMethodTraits(jMethod, STANDARD_OPERATION_NAME, JavaVisibility.PUBLIC,
        	TypesFactory.eINSTANCE.createVoid, false, false, null, jClass)
        assertMethodEquals(operation, jMethod)
    }
    
    @Test
    def void testChangeReturnType() {
        uOperation.type = typeClass;
        saveAndSynchronizeChanges(uOperation);
        
        val jMethod = getCorrespondingClassMethod(uOperation)
        val jTypeClass = getCorrespondingClass(typeClass)
        assertJavaElementHasTypeRef(jMethod, createNamespaceReferenceFromClassifier(jTypeClass))
        assertMethodEquals(uOperation, jMethod)
    }
    
    @Test
    def testRenameMethod() {
        uOperation.name = OPERATION_RENAME;
        saveAndSynchronizeChanges(uOperation);
        
        val jMethod = getCorrespondingClassMethod(uOperation)
        val jClass = getCorrespondingClass(uClass)
        assertEquals(OPERATION_RENAME, jMethod.name)
        assertMethodEquals(uOperation, jMethod)
        assertJavaMemberContainerDontHaveMember(jClass, OPERATION_NAME)
    }
    
    @Test
    def testDeleteMethod() {
        uOperation.destroy;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getCorrespondingClass(uClass)
        assertJavaMemberContainerDontHaveMember(jClass, OPERATION_NAME)
    }
    
    @Test
    def testStaticMethod() {
        uOperation.isStatic = true;
        saveAndSynchronizeChanges(uOperation);
        
        val jMethod = getCorrespondingClassMethod(uOperation)
        assertJavaModifiableStatic(jMethod, true)
        assertMethodEquals(uOperation, jMethod)
    }
    
    @Test
    def testAbstractMethod() {
        uOperation.isAbstract = true;
        saveAndSynchronizeChanges(uOperation);
        
        val jMethod = getCorrespondingClassMethod(uOperation)
        assertJavaModifiableAbstract(jMethod, true)
        assertMethodEquals(uOperation, jMethod)
    }
    
    @Test
    def testMethodVisibility() {
        uOperation.visibility = VisibilityKind.PRIVATE_LITERAL;
        saveAndSynchronizeChanges(uOperation);
        
        var jMethod = getCorrespondingClassMethod(uOperation)
        assertJavaModifiableHasVisibility(jMethod, JavaVisibility.PRIVATE)
        assertMethodEquals(uOperation, jMethod)
        
        uOperation.visibility = VisibilityKind.PROTECTED_LITERAL;
        saveAndSynchronizeChanges(uOperation);
        
        jMethod = getCorrespondingClassMethod(uOperation)
        assertJavaModifiableHasVisibility(jMethod, JavaVisibility.PROTECTED)
        assertMethodEquals(uOperation, jMethod)
    }
}
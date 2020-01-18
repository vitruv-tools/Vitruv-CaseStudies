package tools.vitruv.applications.umljava.uml2java.tests

import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlOperationAndParameterUtil.*
import static extension tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static tools.vitruv.applications.umljava.testutil.JavaTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import static org.junit.Assert.*;
import tools.vitruv.applications.umljava.uml2java.Uml2JavaTransformationTest
import org.junit.Test
import org.junit.Before
import org.emftext.language.java.types.TypesFactory


/**
 * This class provides basic tests for creating, deleting and changing traits of interface methods.
 * 
 * @author Fei
 */
class UmlToJavaInterfaceMethodTest extends Uml2JavaTransformationTest {
    private static val INTERFACE_NAME = "InterfaceName";
    private static val TYPE_NAME = "TypeName";
    private static val IOPERATION_NAME = "interfaceMethod";
    private static val STANDARD_IOPERATION_NAME = "standardInterfaceMethod";
    private static val IOPERATION_RENAME = "interfaceMethodRenamed";
    
    private static var org.eclipse.uml2.uml.Interface uInterface;
    private static var org.eclipse.uml2.uml.Class typeClass;
    private static var org.eclipse.uml2.uml.Operation uOperation;
    
    @Before
    def void before() {
        uInterface = createSimpleUmlInterface(rootElement, INTERFACE_NAME);
        uOperation = createUmlInterfaceOperation(IOPERATION_NAME, null, null);
        uInterface.ownedOperations += uOperation;
        typeClass = createSimpleUmlClass(rootElement, TYPE_NAME);
        rootElement.packagedElements += uInterface;
        rootElement.packagedElements += typeClass;
        saveAndSynchronizeChanges(rootElement);
    }
    
    @Test
    def void testCreateInterfaceMethod() {
        val interfaceMethod = createUmlInterfaceOperation(STANDARD_IOPERATION_NAME, null, null)
        uInterface.ownedOperations += interfaceMethod
        saveAndSynchronizeChanges(uInterface);
        
        val jMethod = getCorrespondingInterfaceMethod(interfaceMethod)
        val jInterface = getCorrespondingInterface(uInterface)
        assertJavaInterfaceMethodTraits(jMethod, STANDARD_IOPERATION_NAME, 
        	TypesFactory.eINSTANCE.createVoid, null, jInterface)
        assertInterfaceMethodEquals(interfaceMethod, jMethod)
    }
     
    @Test
    def testRenameInterfaceMethod() {
        uOperation.name = IOPERATION_RENAME;
        saveAndSynchronizeChanges(uOperation);
        
        val jMethod = getCorrespondingInterfaceMethod(uOperation)
        val jInterface = getCorrespondingInterface(uInterface)
        assertEquals(IOPERATION_RENAME, jMethod.name)
        assertJavaMemberContainerDontHaveMember(jInterface, IOPERATION_NAME)
        assertInterfaceMethodEquals(uOperation, jMethod)
    }
    
    @Test
    def testDeleteInterfaceMethod() {
        assertNotNull(uOperation)
        uOperation.destroy;
        saveAndSynchronizeChanges(rootElement);
        
        val jInterface = getCorrespondingInterface(uInterface)
        assertJavaMemberContainerDontHaveMember(jInterface, IOPERATION_NAME)
    }
    
    @Test
    def testChangeInterfaceMethodReturnType() {
        uOperation.type = typeClass;
        saveAndSynchronizeChanges(uOperation);
        
        val jMethod = getCorrespondingInterfaceMethod(uOperation)
        val jTypeClass = getCorrespondingClass(typeClass)
        assertJavaElementHasTypeRef(jMethod, createNamespaceReferenceFromClassifier(jTypeClass))
        assertInterfaceMethodEquals(uOperation, jMethod)
    }
        
    
}

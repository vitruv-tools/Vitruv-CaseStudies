package tools.vitruv.applications.umljava.uml2java.tests

import static tools.vitruv.applications.umljava.util.UmlUtil.*
import static tools.vitruv.applications.umljava.util.JavaUtil.*
import static tools.vitruv.applications.umljava.testutil.JavaTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import static org.junit.Assert.*;
import tools.vitruv.applications.umljava.uml2java.AbstractUmlJavaTest
import org.junit.Test
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.Interface
import org.emftext.language.java.members.InterfaceMethod
import org.eclipse.uml2.uml.Operation
import org.junit.Before
import org.junit.After
import tools.vitruv.applications.umljava.util.JavaUtil.JavaVisibility
import org.emftext.language.java.types.TypesFactory

class UmlToJavaInterfaceMethodTest extends AbstractUmlJavaTest {
    private static val INTERFACE_NAME = "InterfaceName";
    private static val TYPE_NAME = "TypeName";
    private static val IOPERATION_NAME = "interfaceMethod";
    private static val STANDARD_IOPERATION_NAME = "standardInterfaceMethod";
    private static val IOPERATION_RENAME = "interfaceMethodRenamed";
    private static val PARAMETER_NAME = "parameterName";
    
    private static var org.eclipse.uml2.uml.Interface uI;
    private static var org.eclipse.uml2.uml.Class typeClass;
    private static var org.eclipse.uml2.uml.Operation op;
    
    @Before
    def void before() {
        uI = createSimpleUmlInterface(rootElement, INTERFACE_NAME);
        op = createSimpleUmlOperation(IOPERATION_NAME);
        uI.ownedOperations += op;
        typeClass = createSimpleUmlClass(rootElement, TYPE_NAME);
        rootElement.packagedElements += uI;
        rootElement.packagedElements += typeClass;
        saveAndSynchronizeChanges(rootElement);
    }
    
    @After
    def void after() {
        if (uI != null) {
            uI.destroy;
        }
        if (op != null) {
            op.destroy;
        }
        if (typeClass != null) {
            typeClass.destroy;
        }
        saveAndSynchronizeChanges(rootElement)
    }
    
    @Test
    def void testCreateInterfaceMethod() {
        val interfaceMethod = uI.createOwnedOperation(STANDARD_IOPERATION_NAME, null, null, null)
        saveAndSynchronizeChanges(uI);
        
        val jMethod = getCorrespondingInterfaceMethod(interfaceMethod)
        val jInterface = getCorrespondingInterface(uI)
        assertJavaMethodTraits(jMethod, STANDARD_IOPERATION_NAME, JavaVisibility.PUBLIC,
        	TypesFactory.eINSTANCE.createVoid, false, false, null, jInterface)
        assertMethodEquals(interfaceMethod, jMethod)
    }
     
    @Test
    def testRenameInterfaceMethod() {
        op.name = IOPERATION_RENAME;
        saveAndSynchronizeChanges(op);
        
        val jMethod = getCorrespondingInterfaceMethod(op)
        val jInterface = getCorrespondingInterface(uI)
        assertEquals(IOPERATION_RENAME, jMethod.name)
        assertJavaMemberContainerDontHaveMember(jInterface, IOPERATION_NAME)
        assertMethodEquals(op, jMethod)
    }
    
    @Test
    def testDeleteInterfaceMethod() {
        op.destroy;
        saveAndSynchronizeChanges(rootElement);
        
        val jInterface = getCorrespondingInterface(uI)
        assertJavaMemberContainerDontHaveMember(jInterface, IOPERATION_NAME)
    }
    
    @Test
    def testChangeInterfaceMethodReturnType() {
        op.type = typeClass;
        saveAndSynchronizeChanges(op);
        
        val jMethod = getCorrespondingInterfaceMethod(op)
        val jTypeClass = getCorrespondingClass(typeClass)
        if (jTypeClass == null) {
            println("customTypeClass is null")
        }
        assertJavaElementHasTypeRef(jMethod, createNamespaceReferenceFromClassifier(jTypeClass))
        assertMethodEquals(op, jMethod)
    }
    
    @Test
    def testCreateInterfaceParameter() {
        val param = createUmlParameter(PARAMETER_NAME, typeClass);
        op.ownedParameters += param;
        saveAndSynchronizeChanges(op);
        
        val jMethod = getCorrespondingInterfaceMethod(op)
        val jParam = getCorrespondingParameter(param)
        val jTypeClass = getCorrespondingClass(typeClass)
        assertJavaMethodHasUniqueParameter(jMethod, PARAMETER_NAME, createNamespaceReferenceFromClassifier(jTypeClass))
        assertMethodEquals(op, jMethod)
        }
        
    
}
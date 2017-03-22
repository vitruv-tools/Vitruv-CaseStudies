package tools.vitruv.applications.umljava.uml2java.tests

import static org.junit.Assert.*;
import tools.vitruv.applications.umljava.uml2java.AbstractUmlJavaTest
import org.junit.Test
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.Interface
import org.emftext.language.java.members.InterfaceMethod
import org.eclipse.uml2.uml.Operation
import org.junit.Before
import org.junit.After

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
        uI = createSyncSimpleUmlInterface(INTERFACE_NAME);
        op = createSimpleUmlOperation(IOPERATION_NAME);
        uI.ownedOperations += op;
        typeClass = createSyncSimpleUmlClass(TYPE_NAME);
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
    }
    
    @Test
    def void testCreateInterfaceMethod() {
        uI.createOwnedOperation(STANDARD_IOPERATION_NAME, null, null, null)
        saveAndSynchronizeChanges(uI);
        assertUniqueJavaMemberExistsInClass(INTERFACE_NAME, STANDARD_IOPERATION_NAME, InterfaceMethod);
    }
     
    @Test
    def testRenameInterfaceMethod() {
        op.name = IOPERATION_RENAME;
        saveAndSynchronizeChanges(op);
        assertUniqueJavaMemberExistsInClass(INTERFACE_NAME, IOPERATION_RENAME, InterfaceMethod);
        assertJavaMemberNotExistsInClass(INTERFACE_NAME, IOPERATION_NAME);
    }
    
    @Test
    def testDeleteInterfaceMethod() {
        op.destroy;
        saveAndSynchronizeChanges(rootElement);
        assertJavaMemberNotExistsInClass(INTERFACE_NAME, IOPERATION_NAME);
    }
    
    @Test
    def testChangeInterfaceMethodReturnType() {
        op.type = typeClass;
        saveAndSynchronizeChanges(op);
        
        val jIMeth = assertUniqueJavaMemberExistsInClass(INTERFACE_NAME, IOPERATION_NAME, InterfaceMethod);
        assertJavaMemberHasType(jIMeth, typeClass);
    }
    
    @Test
    def testCreateInterfaceParameter() {
        val param = createUmlParameter(PARAMETER_NAME, typeClass);
        op.ownedParameters += param;
        saveAndSynchronizeChanges(op);
        val jIMeth = assertUniqueJavaMemberExistsInClass(INTERFACE_NAME, IOPERATION_NAME, InterfaceMethod);
        assertJavaMethodHasUniqueParameter(jIMeth, param);
    }
    
}
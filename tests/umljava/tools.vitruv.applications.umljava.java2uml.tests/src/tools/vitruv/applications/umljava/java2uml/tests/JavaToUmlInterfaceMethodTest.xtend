package tools.vitruv.applications.umljava.java2uml.tests

import tools.vitruv.applications.umljava.java2uml.Java2UmlTransformationTest
import org.junit.Before
import org.junit.After
import static org.junit.Assert.*;
import static extension tools.vitruv.applications.umljava.util.JavaUtil.*
import static tools.vitruv.applications.umljava.testutil.UmlTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import org.junit.Test
import org.eclipse.uml2.uml.VisibilityKind

class JavaToUmlInterfaceMethodTest extends Java2UmlTransformationTest {
    private static val INTERFACE_NAME = "InterfaceName";
    private static val TYPE_NAME = "TypeName";
    private static val IOPERATION_NAME = "interfaceMethod";
    private static val STANDARD_IOPERATION_NAME = "standardInterfaceMethod";
    private static val IOPERATION_RENAME = "interfaceMethodRenamed";
    private static val PARAMETER_NAME = "parameterName";
    
    private static var org.emftext.language.java.classifiers.Interface jInterface;
    private static var org.emftext.language.java.classifiers.Class typeClass;
    private static var org.emftext.language.java.members.InterfaceMethod jMeth;
    
    @Before
    def void before() {
        jInterface = createSimpleJavaInterfaceWithCompilationUnit(INTERFACE_NAME)
        typeClass = createSimpleJavaClassWithCompilationUnit(TYPE_NAME)
        jMeth = createJavaInterfaceMethod(IOPERATION_NAME, null, null)
        jInterface.members += jMeth
        saveAndSynchronizeChanges(jInterface)
    }
    
    //@After
    def void after() {
        if (jInterface != null) {
            
        }
        if (jMeth != null) {
            
        }
        if (typeClass != null) {
            
        }
    }
    
    @Test
    def void testCreateInterfaceMethod() {
        val interfaceMethod = createJavaInterfaceMethod(STANDARD_IOPERATION_NAME, null, null)
        jInterface.members += interfaceMethod
        saveAndSynchronizeChanges(jInterface)
        
        val uOperation = getCorrespondingMethod(interfaceMethod)
        val uInterface = getCorrespondingInterface(jInterface)
        assertUmlOperationTraits(uOperation, STANDARD_IOPERATION_NAME, VisibilityKind.PUBLIC_LITERAL, null, false, false,
            uInterface, null)
        assertMethodEquals(uOperation, interfaceMethod)
    }
     
    @Test
    def void testRenameInterfaceMethod() {
        jMeth.name = IOPERATION_RENAME
        saveAndSynchronizeChanges(jMeth)

        val uOperation = getCorrespondingMethod(jMeth)
        val uInterface = getCorrespondingInterface(jInterface)
        assertEquals(IOPERATION_RENAME, uOperation.name)
        assertUmlInterfaceDontHaveOperation(uInterface, IOPERATION_NAME)
        assertMethodEquals(uOperation, jMeth)
        
    }
    
    @Test
    def testDeleteInterfaceMethod() {
        jInterface.members.clear
        jMeth = null
        saveAndSynchronizeChanges(jInterface)
        
        val uInterface = getCorrespondingInterface(jInterface)
        assertUmlInterfaceDontHaveOperation(uInterface, IOPERATION_NAME)
    }
    
    @Test
    def testChangeInterfaceMethodReturnType() {
        jMeth.typeReference = createNamespaceReferenceFromClassifier(typeClass)
        saveAndSynchronizeChanges(jMeth)
        
        val uOperation = getCorrespondingMethod(jMeth)
        val utypeClass = getCorrespondingClass(typeClass)
        assertUmlOperationHasReturntype(uOperation, utypeClass)
        assertMethodEquals(uOperation, jMeth)
    }
    
    @Test
    def testCreateInterfaceParameter() {
        val jParam = createJavaParameter(PARAMETER_NAME, createNamespaceReferenceFromClassifier(typeClass))
        jMeth.parameters += jParam
        saveAndSynchronizeChanges(jMeth)

        val uOperation = getCorrespondingMethod(jMeth)
        assertUmlOperationHasUniqueParameter(uOperation, PARAMETER_NAME)
        assertMethodEquals(uOperation, jMeth)
    }
}
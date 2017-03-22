package tools.vitruv.applications.umljava.java2uml.tests

import tools.vitruv.applications.umljava.java2uml.AbstractJavaUmlTest
import org.junit.Before
import org.junit.After
import static org.junit.Assert.*;
import static tools.vitruv.applications.umljava.util.JavaUtil.*;
import org.junit.Test

class JavaToUmlInterfaceMethodTest extends AbstractJavaUmlTest {
    private static val INTERFACE_NAME = "InterfaceName";
    private static val TYPE_NAME = "TypeName";
    private static val IOPERATION_NAME = "interfaceMethod";
    private static val STANDARD_IOPERATION_NAME = "standardInterfaceMethod";
    private static val IOPERATION_RENAME = "interfaceMethodRenamed";
    private static val PARAMETER_NAME = "parameterName";
    
    private static var org.emftext.language.java.classifiers.Interface jI;
    private static var org.emftext.language.java.classifiers.Class typeClass;
    private static var org.emftext.language.java.members.InterfaceMethod jMeth;
    
    @Before
    def void before() {
        jI = createSimpleJavaInterfaceWithCompilationUnit(INTERFACE_NAME)
        typeClass = createSimpleJavaClassWithCompilationUnit(TYPE_NAME)
        jMeth = createJavaInterfaceMethod(IOPERATION_NAME, null, null)
        jI.members += jMeth
        saveAndSynchronizeChanges(jI)
    }
    
    //@After
    def void after() {
        if (jI != null) {
            
        }
        if (jMeth != null) {
            
        }
        if (typeClass != null) {
            
        }
    }
    
    @Test
    def void testCreateInterfaceMethod() {
        val interfaceMethod = createJavaInterfaceMethod(STANDARD_IOPERATION_NAME, null, null)
        jI.members += interfaceMethod
        saveAndSynchronizeChanges(jI)
        
        assertNameAndReturnUniqueUmlMethod(interfaceMethod)
    }
     
    @Test
    def void testRenameInterfaceMethod() {
        jMeth.name = IOPERATION_RENAME
        saveAndSynchronizeChanges(jMeth)

        assertNameAndReturnUniqueUmlMethod(jMeth)
    }
    
    @Test
    def testDeleteInterfaceMethod() {
        jI.members.clear
        jMeth = null
        saveAndSynchronizeChanges(jI)
        
        val uInterface = getCorrespondingObject(jI, org.eclipse.uml2.uml.Interface)
        assertTrue(uInterface.ownedOperations.nullOrEmpty)
    }
    
    @Test
    def testChangeInterfaceMethodReturnType() {
        jMeth.typeReference = createNamespaceReferenceFromClassifier(typeClass)
        saveAndSynchronizeChanges(jMeth)
        
        val uMeth = assertNameAndReturnUniqueUmlMethod(jMeth)
        assertEquals(typeClass.name, uMeth.type.name)
    }
    
    @Test
    def testCreateInterfaceParameter() {
        val jParam = createJavaParameter(PARAMETER_NAME, createNamespaceReferenceFromClassifier(typeClass))
        jMeth.parameters += jParam
        saveAndSynchronizeChanges(jMeth)

        val uOp = assertNameAndReturnUniqueUmlMethod(jMeth)
        assertUmlOperationHasUniqueParameter(uOp, jParam)
    }
}
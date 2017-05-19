package tools.vitruv.applications.umljava.java2uml.tests

import static org.junit.Assert.*;
import tools.vitruv.applications.umljava.java2uml.Java2UmlTransformationTest
import org.junit.Test
import org.junit.Before
import static tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*
import static tools.vitruv.applications.umljava.testutil.UmlTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper
import org.eclipse.uml2.uml.VisibilityKind
import org.eclipse.emf.ecore.util.EcoreUtil

class JavaToUmlInterfaceTest extends Java2UmlTransformationTest {
    private static val INTERFACE_NAME = "InterfaceName"
    private static val INTERFACE_RENAME = "InterfaceRename"
    private static val STANDARD_INTERFACE_NAME = "StandardInterfaceName"
    private static val SUPERINTERFACENAME_1 = "SuperInterfaceOne"
    private static val SUPERINTERFACENAME_2 = "SuperInterfaceTwo"
    
    private static var org.emftext.language.java.classifiers.Interface jInterface
    
    @Before
    def void before() {
        jInterface = createSimpleJavaInterfaceWithCompilationUnit(INTERFACE_NAME)
    }
    
    
    @Test
    def void testCreateInterface() {
        val jInterface = createSimpleJavaInterfaceWithCompilationUnit(STANDARD_INTERFACE_NAME)
        
        val uInterface = getCorrespondingInterface(jInterface)
        assertUmlInterfaceTraits(uInterface, STANDARD_INTERFACE_NAME, VisibilityKind.PUBLIC_LITERAL, getUmlRootModel(JavaToUmlHelper.rootModelFile))
        assertInterfaceEquals(uInterface, jInterface)
    }
    
    @Test
    def void testRenameInterface() {
        jInterface.name = INTERFACE_RENAME
        saveAndSynchronizeChanges(jInterface)
        
        val uInterface = getCorrespondingInterface(jInterface)
        assertEquals(INTERFACE_RENAME, uInterface.name)
        assertInterfaceEquals(uInterface, jInterface)
    }
    @Test
    def void testDeleteInterface() {
        val comp = jInterface.containingCompilationUnit
        EcoreUtil.delete(jInterface)
        saveAndSynchronizeChanges(comp)
        assertTrue(getUmlPackagedElementsbyName(JavaToUmlHelper.rootModelFile, org.eclipse.uml2.uml.Interface, INTERFACE_NAME).nullOrEmpty)
    }
    
    @Test
    def testAddSuperInterface() {
        val superInterface = createSimpleJavaInterfaceWithCompilationUnit(SUPERINTERFACENAME_1)
        jInterface.extends += createNamespaceReferenceFromClassifier(superInterface)
        saveAndSynchronizeChanges(jInterface)
        
        val uInterface = getCorrespondingInterface(jInterface)
        val uSuperInterface = getCorrespondingInterface(superInterface)
        assertUmlClassifierHasSuperClassifier(uInterface, uSuperInterface)
        assertInterfaceEquals(uInterface, jInterface)
    }
    
    @Test
    def testRemoveSuperInterface() {
        val superInterface = createSimpleJavaInterfaceWithCompilationUnit(SUPERINTERFACENAME_1)
        val superInterface2 = createSimpleJavaInterfaceWithCompilationUnit(SUPERINTERFACENAME_2)
        jInterface.extends += createNamespaceReferenceFromClassifier(superInterface)
        jInterface.extends += createNamespaceReferenceFromClassifier(superInterface2)
        saveAndSynchronizeChanges(jInterface)
        
        jInterface.extends.remove(0)
        saveAndSynchronizeChanges(jInterface)
        
        val uInterface = getCorrespondingInterface(jInterface)
        val uSuperInterface = getCorrespondingInterface(superInterface)
        val uSuperInterface2 = getCorrespondingInterface(superInterface2)
        assertUmlClassifierHasSuperClassifier(uInterface, uSuperInterface2)
        assertUmlClassifierDontHaveSuperClassifier(uInterface, uSuperInterface)
        assertInterfaceEquals(uInterface, jInterface)
    }

 
}
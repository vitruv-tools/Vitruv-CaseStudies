package tools.vitruv.applications.umljava.java2uml.tests

import static org.junit.Assert.*;
import tools.vitruv.applications.umljava.java2uml.AbstractJavaUmlTest
import org.junit.Test
import org.junit.Before
import org.junit.Ignore
import static tools.vitruv.applications.umljava.util.JavaUtil.*;
import static tools.vitruv.applications.umljava.util.UmlUtil.*;
import org.emftext.language.java.containers.CompilationUnit
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper

class JavaToUmlInterfaceTest extends AbstractJavaUmlTest {
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
        val jI = createSimpleJavaInterfaceWithCompilationUnit(STANDARD_INTERFACE_NAME)
        
        assertAndReturnCorrespondingInterface(jI)
    }
    
    @Test
    def void testRenameInterface() {
        jInterface.name = INTERFACE_RENAME
        saveAndSynchronizeChanges(jInterface)
        
        assertAndReturnCorrespondingInterface(jInterface)
    }
    @Test
    def void testDeleteInterface() {
        val comp = jInterface.eContainer as CompilationUnit
        jInterface = null;
        comp.classifiers.clear
        saveAndSynchronizeChanges(comp)
        val uI = getUmlPackagedElementsbyName(JavaToUmlHelper.rootModelFile, org.eclipse.uml2.uml.Interface, INTERFACE_NAME).head
        assertNull(uI)
    }
    
    @Test
    def testAddSuperInterface() {
        val superInterface = createSimpleJavaInterfaceWithCompilationUnit(SUPERINTERFACENAME_1)
        jInterface.extends += createNamespaceReferenceFromClassifier(superInterface)
        saveAndSynchronizeChanges(jInterface)
        
        val uI = assertAndReturnCorrespondingInterface(jInterface)
        val umlSuperInterfaces = extractSuperInterfaces(uI)
        assertTrue(umlSuperInterfaces.size == 1)
        assertEquals(SUPERINTERFACENAME_1, umlSuperInterfaces.head.name)
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
        
        val uI = assertAndReturnCorrespondingInterface(jInterface)
        val umlSuperInterfaces = extractSuperInterfaces(uI)
        assertTrue(umlSuperInterfaces.size == 1)
        assertEquals(SUPERINTERFACENAME_2, umlSuperInterfaces.head.name)
    }

    
    /**
     * @return Das Uml-Interface dessen namen zum javaInterface passt und korrespondiert.
     */
    private def org.eclipse.uml2.uml.Interface assertAndReturnCorrespondingInterface(org.emftext.language.java.classifiers.Interface javaInterface) {
        val uI = getCorrespondingObject(javaInterface, org.eclipse.uml2.uml.Interface)
        assertEquals(javaInterface.name, uI?.name)
        return uI;
    }
}
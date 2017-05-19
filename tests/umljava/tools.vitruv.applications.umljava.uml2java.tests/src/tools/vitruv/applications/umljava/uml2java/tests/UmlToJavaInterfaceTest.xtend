package tools.vitruv.applications.umljava.uml2java.tests

import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.uml2.uml.Interface
import org.junit.After
import org.junit.Before
import org.junit.Test
import tools.vitruv.applications.umljava.uml2java.Uml2JavaTransformationTest
import static org.junit.Assert.*
import static tools.vitruv.applications.umljava.util.uml.UmlClassifierAndPackageUtil.*
import static extension tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*

class UmlToJavaInterfaceTest extends Uml2JavaTransformationTest {
    private static val INTERFACE_NAME = "InterfaceName"
    private static val INTERFACE_RENAME = "InterfaceRename"
    private static val SUPERINTERFACENAME_1 = "SuperInterfaceOne"
    private static val SUPERINTERFACENAME_2 = "SuperInterfaceTwo"
    private static val STANDARD_INTERFACE_NAME = "StandardInterfaceName"
    private static var Interface uI
    
    
    @Before
    def void before() {
        uI = createSimpleUmlInterface(rootElement, INTERFACE_NAME)
        saveAndSynchronizeChanges(rootElement)
        
    }
    
   @After
    def void after() {
        if (uI !== null) {
            uI.destroy
        }
        saveAndSynchronizeChanges(rootElement)
    }
    
    @Test
    def testCreateInterface() {
        val interface = createSimpleUmlInterface(rootElement, STANDARD_INTERFACE_NAME);
        saveAndSynchronizeChanges(rootElement)
        
        assertJavaFileExists(STANDARD_INTERFACE_NAME, #[]);
        val jInterface = getCorrespondingInterface(interface)
        assertEquals(STANDARD_INTERFACE_NAME, jInterface.name)
    }
    
    @Test
    def testRenameInterface() {
        uI.name = INTERFACE_RENAME;
        saveAndSynchronizeChanges(uI);
        
        assertJavaFileExists(INTERFACE_RENAME, #[]);
        val jInterface = getCorrespondingInterface(uI)
        assertEquals(INTERFACE_RENAME, jInterface.name)
        assertJavaFileNotExists(INTERFACE_NAME, #[]);
    }
    
    @Test
    def testDeleteInterface() {
        uI.destroy;
        saveAndSynchronizeChanges(rootElement);
        
        assertJavaFileNotExists(INTERFACE_NAME, #[]);
    }
    
    @Test
    def testAddSuperInterface() {
        val interface = createInterfaceWithTwoSuperInterfaces(STANDARD_INTERFACE_NAME, SUPERINTERFACENAME_1, SUPERINTERFACENAME_2);
        saveAndSynchronizeChanges(rootElement)
        val jI = getCorrespondingInterface(interface)
        assertEquals(SUPERINTERFACENAME_1, getClassifierFromTypeReference(jI.extends.get(0)).name)
        assertEquals(SUPERINTERFACENAME_2, getClassifierFromTypeReference(jI.extends.get(1)).name)
    }
    
    @Test
    def testRemoveSuperInterface() {
        val uI = createInterfaceWithTwoSuperInterfaces(STANDARD_INTERFACE_NAME, SUPERINTERFACENAME_1, SUPERINTERFACENAME_2);
        saveAndSynchronizeChanges(rootElement)
        uI.generalizations.remove(0);
        saveAndSynchronizeChanges(rootElement);
        val jI = getCorrespondingInterface(uI)
        assertTrue(jI.extends.size.toString, jI.extends.size == 1);
        assertEquals(SUPERINTERFACENAME_2, getClassifierFromTypeReference(jI.extends.get(0)).name)
        assertJavaFileExists(SUPERINTERFACENAME_1, #[]);
    }
    

    
    /**
     * @return Das Interface namens iName, dass die anderen beiden SuperInterfaces superName1 & superName2 beerbt.
     */
    private def Interface createInterfaceWithTwoSuperInterfaces(String iName, String superName1, String superName2) {
        val super1 = createSimpleUmlInterface(rootElement, superName1);
        val super2  = createSimpleUmlInterface(rootElement, superName2);
        val EList<Interface> supers = new BasicEList<Interface>;
        supers += super1;
        supers += super2;
        return createUmlInterfaceAndAddToPackage(rootElement, iName, supers);
    }
    
}
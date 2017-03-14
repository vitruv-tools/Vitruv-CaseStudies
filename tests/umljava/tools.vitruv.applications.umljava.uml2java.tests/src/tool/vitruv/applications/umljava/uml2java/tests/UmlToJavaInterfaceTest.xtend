package tool.vitruv.applications.umljava.uml2java.tests

import tool.vitruv.applications.umljava.uml2java.AbstractUmlJavaTest
import org.junit.Test
import static org.junit.Assert.*;
import org.eclipse.emf.common.util.EList
import org.eclipse.uml2.uml.Interface
import org.eclipse.emf.common.util.BasicEList

class UmlToJavaInterfaceTest extends AbstractUmlJavaTest {
    private static val INTERFACE_NAME = "InterfaceName"
    private static val INTERFACE_RENAME = "InterfaceRename"
    private static val SUPERINTERFACENAME_1 = "SuperInterfaceOne"
    private static val SUPERINTERFACENAME_2 = "SuperInterfaceTwo"
    
    @Test
    def testCreateInterface() {
        createSyncSimpleUmlInterface(INTERFACE_NAME);
        assertJavaFileExists(INTERFACE_NAME);
    }
    
    @Test
    def testRenameInterface() {
        val uI = createSyncSimpleUmlInterface(INTERFACE_NAME);
        uI.name = INTERFACE_RENAME;
        saveAndSynchronizeChanges(uI);
        
        assertJavaFileExists(INTERFACE_RENAME);
        assertJavaFileNotExists(INTERFACE_NAME);
    }
    
    @Test
    def testDeleteInterface() {
        val ui = createSyncSimpleUmlInterface(INTERFACE_NAME);
        ui.destroy;
        saveAndSynchronizeChanges(rootElement);
        
        assertJavaFileNotExists(INTERFACE_NAME);
    }
    
    @Test
    def testAddSuperInterface() {
        createSyncInterfaceWithTwoSuperInterfaces(INTERFACE_NAME, SUPERINTERFACENAME_1, SUPERINTERFACENAME_2);
        
        val jI = getJInterfaceFromName(INTERFACE_NAME);
        assertEquals(SUPERINTERFACENAME_1, getClassifierfromTypeRef(jI.extends.get(0)).name)
        assertEquals(SUPERINTERFACENAME_2, getClassifierfromTypeRef(jI.extends.get(1)).name)
    }
    
    @Test
    def removeSuperInterface() {
        val uI = createSyncInterfaceWithTwoSuperInterfaces(INTERFACE_NAME, SUPERINTERFACENAME_1, SUPERINTERFACENAME_2);
        uI.generalizations.remove(0);
        saveAndSynchronizeChanges(rootElement);
        val jI = getJInterfaceFromName(INTERFACE_NAME);
        assertTrue(jI.extends.size.toString, jI.extends.size == 1); //TODO Ist 0 statt 1. Falsches Interface? Im Model ist s richtig.
        assertEquals(SUPERINTERFACENAME_2, getClassifierfromTypeRef(jI.extends.get(0)).name)
        assertJavaFileExists(SUPERINTERFACENAME_1);
    }

    
    /**
     * @return Das Interface namens iName, dass die anderen beiden SuperInterfaces superName1 & superName2 beerbt.
     */
    private def Interface createSyncInterfaceWithTwoSuperInterfaces(String iName, String superName1, String superName2) {
        val super1 = createSyncSimpleUmlInterface(superName1);
        val super2  = createSyncSimpleUmlInterface(superName2);
        val EList<Interface> supers = new BasicEList<Interface>;
        supers += super1;
        supers += super2;
        return createSyncUmlInterface(iName, supers);
    }
    
}
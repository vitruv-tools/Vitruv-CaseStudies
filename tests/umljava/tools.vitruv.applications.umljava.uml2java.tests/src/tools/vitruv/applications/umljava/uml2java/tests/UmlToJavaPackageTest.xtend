package tools.vitruv.applications.umljava.uml2java.tests

import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.VisibilityKind
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import tools.vitruv.applications.umljava.uml2java.Uml2JavaTransformationTest

import static org.junit.Assert.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*

/**
 * This test class contains basic test cases for package creation, renaming and deletion.
 * 
 * @author Fei
 */
class UmlToJavaPackageTest extends Uml2JavaTransformationTest {
    private static val PACKAGE_LEVEL_1 = "level1"
    private static val PACKAGE_LEVEL_2 = "level2"
    private static val PACKAGE_NAME = "packagename"
    private static val PACKAGE_RENAMED = "packagerenamed"
    private static val CLASS_NAME = "ClassName"
    
    private static var Package uPackageLevel1

    @Before
    def void before() {
        uPackageLevel1 = createUmlPackageAndAddToSuperPackage(PACKAGE_LEVEL_1, rootElement)
        createUmlClassAndAddToPackage(uPackageLevel1, CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false)
        saveAndSynchronizeChanges(rootElement)
        
    }
    
    @Test
    def testCreatePackage() {
        val uPackage = createUmlPackageAndAddToSuperPackage(PACKAGE_NAME, rootElement)
        saveAndSynchronizeChanges(uPackage)
        
        val jPackage = getCorrespondingPackage(uPackage)
        assertEquals(PACKAGE_NAME, jPackage.name)
        assertPackageEquals(uPackage, jPackage)
        
    }
    @Test
    def testCreateNestedPackage() {
        val uPackageLevel2 = createUmlPackageAndAddToSuperPackage(PACKAGE_LEVEL_2, uPackageLevel1)
        saveAndSynchronizeChanges(uPackageLevel2)
        
        val jPackageLevel2 = getCorrespondingPackage(uPackageLevel2)
        assertEquals(PACKAGE_LEVEL_2, jPackageLevel2.name)
        assertPackageEquals(uPackageLevel2, jPackageLevel2)
        
    }
    
    @Test @Ignore
    def testDeletePackage() { //Delete or Refactoring java packages seems to lead to problems
        var jPackage = getCorrespondingPackage(uPackageLevel1)
        assertNotNull(jPackage)
        
        uPackageLevel1.destroy
        saveAndSynchronizeChanges(rootElement)
        
        jPackage = getCorrespondingPackage(uPackageLevel1)
        assertNull(jPackage)
    }
    
    @Test @Ignore
    def testRenamePackage() { //Delete or Refactoring java packages seems to lead to problems
        uPackageLevel1.name = PACKAGE_RENAMED
        saveAndSynchronizeChanges(uPackageLevel1)
        
        val jPackage = getCorrespondingPackage(uPackageLevel1)
        assertEquals(PACKAGE_RENAMED, jPackage.name)
        assertPackageEquals(uPackageLevel1, jPackage)
    }
}

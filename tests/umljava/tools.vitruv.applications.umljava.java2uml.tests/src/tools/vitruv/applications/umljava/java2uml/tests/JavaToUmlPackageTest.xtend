package tools.vitruv.applications.umljava.java2uml.tests

import tools.vitruv.applications.umljava.java2uml.Java2UmlTransformationTest
import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import static extension tools.vitruv.applications.umljava.util.java.JavaContainerAndClassifierUtil.*
import static tools.vitruv.applications.umljava.testutil.UmlTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper
import org.eclipse.uml2.uml.Package

class JavaToUmlPackageTest extends Java2UmlTransformationTest {
    
    private static val PACKAGE_LEVEL_1 = "level1"
    private static val PACKAGE_NAME = "packagename"
    private static val PACKAGE_RENAMED = "packagerenamed"
    private static val CLASS_NAME = "ClassName"
    private static val CLASS_NAME2 = "ClassName2"
    
    private static var org.emftext.language.java.containers.Package jPackageLevel1
    private static var org.emftext.language.java.classifiers.Class jClass
    
    @Before
    def void before() {
        jPackageLevel1 = createJavaPackageAsModel(PACKAGE_LEVEL_1, null)
        jClass = createSimpleJavaClassWithCompilationUnit(CLASS_NAME)
        jPackageLevel1.compilationUnits += getContainingCompilationUnit(jClass)
        saveAndSynchronizeChanges(jPackageLevel1)
        
    }
    
    @Test
    def void testCreatePackage() {
        val jPackage = createJavaPackageAsModel(PACKAGE_NAME, null)
        
        val uPackage = getCorrespondingPackage(jPackage)
        assertNotNull(uPackage)
        assertEquals(PACKAGE_NAME, uPackage.name)
        assertPackageEquals(uPackage, jPackage)
    }
    
    @Test
    def void testRenamePackage() {
        jPackageLevel1.name = PACKAGE_RENAMED
        saveAndSynchronizeChanges(jPackageLevel1)
        
        val uPackage = getCorrespondingPackage(jPackageLevel1)
        assertEquals(PACKAGE_RENAMED, uPackage.name)
        assertPackageEquals(uPackage, jPackageLevel1)
    }
    
    @Test
    def void testDeletePackage() {
        deleteAndSynchronizeModel(buildJavaFilePath(jPackageLevel1))
        assertTrue(getUmlPackagedElementsbyName(JavaToUmlHelper.rootModelFile, Package, PACKAGE_LEVEL_1).nullOrEmpty)
        
    }
    
    @Test
    def void testAddClassToPackage() {
        val javaClass = createSimpleJavaClassWithCompilationUnit(CLASS_NAME2)
        jPackageLevel1.compilationUnits += getContainingCompilationUnit(javaClass)
        saveAndSynchronizeChanges(jPackageLevel1)
        
        val uPackage = getCorrespondingPackage(jPackageLevel1)
        val uClass = getCorrespondingClass(javaClass)
        assertUmlPackageableElementIsInPackage(uClass, uPackage)
    }
    
}
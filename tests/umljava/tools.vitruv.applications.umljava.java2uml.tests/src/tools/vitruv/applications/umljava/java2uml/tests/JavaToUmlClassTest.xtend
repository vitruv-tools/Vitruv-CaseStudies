package tools.vitruv.applications.umljava.java2uml.tests

import org.eclipse.uml2.uml.Class
import org.junit.Before
import org.junit.Test
import tools.vitruv.applications.umljava.java2uml.Java2UmlTransformationTest
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper
import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*
import static org.junit.Assert.*
import static tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*
import static extension tools.vitruv.applications.umljava.util.java.JavaModifierUtil.*
import static tools.vitruv.applications.umljava.testutil.UmlTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import org.eclipse.uml2.uml.VisibilityKind
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * A Test class to test classes and their traits.
 * 
 * @author Fei
 */
class JavaToUmlClassTest extends Java2UmlTransformationTest {
    private static val CLASS_NAME = "ClassName";
    private static val STANDARD_CLASS_NAME = "StandardClassName";
    private static val CLASS_RENAMED = "ClassRenamed"
    private static val SUPER_CLASS_NAME = "SuperClassName"
    private static val INTERFACE_NAME = "InterfaceName";
    private static val INTERFACE_NAME2 = "InterfaceName2";
    
    private static var org.emftext.language.java.classifiers.Class jClass;
    
    @Before
    def void before() {
        jClass = createSimpleJavaClassWithCompilationUnit(CLASS_NAME);
    }
    
    /**
     * Tests if a corresponding java class is created when an uml class is created.
     */
    @Test
    def void testCreateClass() {
        val cls = createSimpleJavaClassWithCompilationUnit(STANDARD_CLASS_NAME);
        
        val uClass = getCorrespondingClass(cls);
        assertUmlClassTraits(uClass, STANDARD_CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false, getUmlRootModel(JavaToUmlHelper.rootModelFile))
        assertClassEquals(uClass, cls)
    }
    
    /**
     * Tests if renaming a java class also renames the corresponding uml class.
     */
    @Test
    def testRenameClass() {
        jClass.name = CLASS_RENAMED;
        saveAndSynchronizeChanges(jClass);
        
        val uClass = getCorrespondingClass(jClass);
        assertEquals(CLASS_RENAMED, uClass.name)
        assertClassEquals(uClass, jClass)
    }
    
    /**
     * Tests if deleting a java class also cause the deleting of the corresponding
     * uml class.
     */
    @Test
    def testDeleteClass() {
        assertNotNull(getCorrespondingClass(jClass))
        val comp = jClass.containingCompilationUnit

        EcoreUtil.delete(jClass)
        saveAndSynchronizeChanges(comp)
        
        val uClass = getUmlPackagedElementsbyName(JavaToUmlHelper.rootModelFile, Class, CLASS_NAME).head
        assertNull(uClass)
    }
    
    /**
     * Tests if deleting a java compilation unit also cause the deleting of the corresponding
     * uml class.
     */
    @Test
    def testDeleteCompilationUnit() {
        val compUnitFilePath = buildJavaFilePath(jClass.containingCompilationUnit)
        assertNotNull(getCorrespondingClass(jClass))
        deleteAndSynchronizeModel(compUnitFilePath)
        
        assertTrue(getUmlPackagedElementsbyName(JavaToUmlHelper.rootModelFile, Class, CLASS_NAME).nullOrEmpty)
    }
    
    /**
     * Checks if visibility changes are propagated to the uml class.
     */
    @Test
    def testChangeClassVisibility() {
        jClass.makeProtected;
        saveAndSynchronizeChanges(jClass);
        
        var uClass = getCorrespondingClass(jClass);
        assertUmlNamedElementHasVisibility(uClass, VisibilityKind.PROTECTED_LITERAL)
        assertClassEquals(uClass, jClass)
        
        jClass.makePrivate;
        saveAndSynchronizeChanges(jClass);
        
        uClass = getCorrespondingClass(jClass);
        assertUmlNamedElementHasVisibility(uClass, VisibilityKind.PRIVATE_LITERAL)
        assertClassEquals(uClass, jClass)
    }
    
    /**
     * Tests the change of the abstract value in uml.
     */
   @Test
   def testChangeAbstractClass() {
       jClass.abstract = true
       saveAndSynchronizeChanges(jClass);
       
       var uClass = getCorrespondingClass(jClass)
       assertTrue(uClass.abstract)
       assertClassEquals(uClass, jClass)
       
       jClass.abstract = false
       saveAndSynchronizeChanges(jClass);
       
       uClass = getCorrespondingClass(jClass)
       assertFalse(uClass.abstract)
       assertClassEquals(uClass, jClass)
   }
   
   /**
     * Checks if the changing the final value in the java class
     * causes the correct change in the uml class.
     */
   @Test
   def testChangeFinalClass() {
       jClass.final = true
       saveAndSynchronizeChanges(jClass);
       
       var uClass = getCorrespondingClass(jClass)
       assertTrue(uClass.finalSpecialization)
       assertClassEquals(uClass, jClass)
       
       jClass.final = false
       saveAndSynchronizeChanges(jClass);
       
       uClass = getCorrespondingClass(jClass)
       assertFalse(uClass.finalSpecialization)
       assertClassEquals(uClass, jClass)
   }
   
   /**
     * Tests if add a super class is correctly reflected on the uml side.
     */
   @Test
   def testSuperClassChanged() {
       val superClass = createSimpleJavaClassWithCompilationUnit(SUPER_CLASS_NAME);
       jClass.extends = createNamespaceReferenceFromClassifier(superClass);
       saveAndSynchronizeChanges(jClass);
       
       val uClass = getCorrespondingClass(jClass)
       val uSuperClass = getCorrespondingClass(superClass)
       assertUmlClassifierHasSuperClassifier(uClass, uSuperClass)
       assertClassEquals(uClass, jClass)
       
       
   }
   /**
    * Tests if removing a super class is reflected on the uml side.
    */
   @Test
   def testRemoveSuperClass() {
       val superClass = createSimpleJavaClassWithCompilationUnit(SUPER_CLASS_NAME)
       jClass.extends = createNamespaceReferenceFromClassifier(superClass);
       saveAndSynchronizeChanges(jClass);
       
       var uClass = getCorrespondingClass(jClass)
       val uSuperClass = getCorrespondingClass(superClass)
       assertUmlClassifierHasSuperClassifier(uClass, uSuperClass)
       
       EcoreUtil.delete(jClass.extends)
       saveAndSynchronizeChanges(jClass)
       uClass = getCorrespondingClass(jClass)
       assertUmlClassifierDontHaveSuperClassifier(uClass, uSuperClass)
   }
   
   /**
    * Check the creation of an interface implementation on the uml side.
    */
   @Test
   def testAddClassImplement() {
       val implInterface = createSimpleJavaInterfaceWithCompilationUnit(INTERFACE_NAME);
       jClass.implements += createNamespaceReferenceFromClassifier(implInterface);
       saveAndSynchronizeChanges(jClass);
       
       val uClass = getCorrespondingClass(jClass)
       val uInterface = getCorrespondingInterface(implInterface)
       assertUmlClassHasImplement(uClass, uInterface)
       assertClassEquals(uClass, jClass)
   }
   
   /**
    * Tests if Removing an implementation relation is correctly reflected on the java side.
    */
   @Test
   def testRemoveClassImplement() {
       val implInterface = createSimpleJavaInterfaceWithCompilationUnit(INTERFACE_NAME)
       val implInterface2 = createSimpleJavaInterfaceWithCompilationUnit(INTERFACE_NAME2)
       jClass.implements += createNamespaceReferenceFromClassifier(implInterface)
       jClass.implements += createNamespaceReferenceFromClassifier(implInterface2)
       saveAndSynchronizeChanges(jClass);
       
       var uClass = getCorrespondingClass(jClass)
       var uInterface = getCorrespondingInterface(implInterface)
       var uInterface2 = getCorrespondingInterface(implInterface2)
       assertUmlClassHasImplement(uClass, uInterface)
       assertUmlClassHasImplement(uClass, uInterface2)
       
       jClass.implements.remove(0)
       saveAndSynchronizeChanges(jClass)
       
       uClass = getCorrespondingClass(jClass)
       assertUmlClassDontHaveImplement(uClass, uInterface)
       assertUmlClassHasImplement(uClass, uInterface2)
   }
    

    
}
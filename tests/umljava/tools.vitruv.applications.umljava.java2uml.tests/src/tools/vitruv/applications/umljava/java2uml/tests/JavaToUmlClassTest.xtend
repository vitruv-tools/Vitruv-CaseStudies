package tools.vitruv.applications.umljava.java2uml.tests

import org.eclipse.uml2.uml.Class
import org.junit.After
import org.junit.Before
import org.junit.Test
import tools.vitruv.applications.umljava.java2uml.Java2UmlTransformationTest
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper
import static extension tools.vitruv.applications.umljava.util.java.JavaContainerAndClassifierUtil.*
import static org.junit.Assert.*
import static tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*
import static extension tools.vitruv.applications.umljava.util.java.JavaModifierUtil.*
import static tools.vitruv.applications.umljava.testutil.UmlTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import org.eclipse.uml2.uml.VisibilityKind

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
    
    //@After
    def void after() {
        if (jClass != null) {
            jClass = null
        }
    }



    
    @Test
    def void testCreateClass() {
        val cls = createSimpleJavaClassWithCompilationUnit(STANDARD_CLASS_NAME);
        
        val uClass = getCorrespondingClass(cls);
        assertUmlClassTraits(uClass, STANDARD_CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false, null)
        assertClassEquals(uClass, cls)
    }
    
    @Test
    def testRenameClass() {
        jClass.name = CLASS_RENAMED;
        saveAndSynchronizeChanges(jClass);
        
        val uClass = getCorrespondingClass(jClass);
        assertEquals(CLASS_RENAMED, uClass.name)
        assertClassEquals(uClass, jClass)
    }
    
    @Test
    def testDeleteClass() {//TODO Überarbeiten
        val comp = jClass.containingCompilationUnit
        jClass = null;
        comp.classifiers.clear
        saveAndSynchronizeChanges(comp)
        
        val uClass = getUmlPackagedElementsbyName(JavaToUmlHelper.rootModelFile, Class, CLASS_NAME).head
        assertNull(uClass)
    }
    
    @Test
    def testDeleteCompilationUnit() { //TODO Überarbeiten
        var comp = jClass.containingCompilationUnit
        comp = null;
        deleteAndSynchronizeModel("src/ClassName.java")
        fail("Not implemented")
    }
    
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
    
   @Test
   def testChangeAbstractClass() {
       jClass.abstract = true
       saveAndSynchronizeChanges(jClass);
       
       val uClass = getCorrespondingClass(jClass)
       assertTrue(uClass.abstract)
       assertClassEquals(uClass, jClass)
   }
   
   @Test
   def testChangeFinalClass() {
       jClass.final = true
       saveAndSynchronizeChanges(jClass);
       
       val uClass = getCorrespondingClass(jClass)
       assertTrue(uClass.finalSpecialization)
       assertClassEquals(uClass, jClass)
   }
   
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
    

    
}
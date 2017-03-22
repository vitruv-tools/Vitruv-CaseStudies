package tools.vitruv.applications.umljava.java2uml.tests

import org.eclipse.uml2.uml.Class
import org.junit.After
import org.junit.Before
import org.junit.Test
import tools.vitruv.applications.umljava.java2uml.AbstractJavaUmlTest

import static org.junit.Assert.*
import tools.vitruv.framework.tests.util.TestUtil
import org.emftext.language.java.modifiers.ModifiersFactory
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import org.junit.Ignore
import tools.vitruv.applications.umljava.util.JavaUtil

class JavaToUmlClassTest extends AbstractJavaUmlTest {
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
    
    @After
    def void after() {
        if (jClass != null) {
            jClass = null
        }
    }

    @Test
    def void testCreateClass() {
        val cls = createSimpleJavaClassWithCompilationUnit(STANDARD_CLASS_NAME);
        
        val uClass = getCorrespondingObject(cls, Class);
        assertEquals(STANDARD_CLASS_NAME, uClass.name)
    }
    
    @Test
    def testRenameClass() {
        jClass.name = CLASS_RENAMED;
        saveAndSynchronizeChanges(jClass);
        
        val uClass = getCorrespondingObject(jClass, Class);
        assertEquals(CLASS_RENAMED, uClass.name)
    }
    
    @Ignore @Test
    def testDeleteClass() {
        deleteAndSynchronizeModel(TestUtil.SOURCE_FOLDER + "/" + jClass.name + ".java")
        val uClass = getCorrespondingObject(jClass, Class);
        assertNull(uClass)
    }
    
    @Test
    def testChangeClassVisibility() {
        jClass.makeProtected;
        saveAndSynchronizeChanges(jClass);
        
        var uClass = getCorrespondingObject(jClass, Class);
        assertEquals(CLASS_NAME, uClass.name)
        assertHasVisibility(uClass, JavaVisibility.PROTECTED)
        
        jClass.makePrivate;
        saveAndSynchronizeChanges(jClass);
        
        uClass = getCorrespondingObject(jClass, Class);
        assertEquals(CLASS_NAME, uClass.name)
        assertHasVisibility(uClass, JavaVisibility.PRIVATE)
    }
    
   @Test
   def testChangeAbstractClass() {
       jClass.addModifier(ModifiersFactory.eINSTANCE.createAbstract)
       saveAndSynchronizeChanges(jClass);
       
       val uClass = getCorrespondingObject(jClass, Class);
       assertEquals(CLASS_NAME, uClass.name)
       assertTrue(uClass.isAbstract);
   }
   
   @Test
   def testChangeFinalClass() {
       jClass.addModifier(ModifiersFactory.eINSTANCE.createFinal)
       saveAndSynchronizeChanges(jClass);
       
       val uClass = getCorrespondingObject(jClass, Class);
       assertEquals(CLASS_NAME, uClass.name)
       assertTrue(uClass.isFinalSpecialization);
   }
   
   @Test
   def testSuperClassChanged() {
       val superClass = createSimpleJavaClassWithCompilationUnit(SUPER_CLASS_NAME);
       jClass.extends = JavaUtil::createNamespaceReferenceFromClassifier(superClass);
       saveAndSynchronizeChanges(jClass);
       
       val uClass = getCorrespondingObject(jClass, Class);
       assertEquals(CLASS_NAME, uClass.name)
       assertEquals(SUPER_CLASS_NAME, uClass.generals.head.name);
   }
   
   @Test
   def testAddClassImplement() {
       val implInterface = createSimpleJavaInterfaceWithCompilationUnit(INTERFACE_NAME);
       jClass.implements += JavaUtil::createNamespaceReferenceFromClassifier(implInterface);
       //EcoreResourceBridge.saveResource(jClass.eResource())
       saveAndSynchronizeChanges(jClass);
       val uClass = getCorrespondingObject(jClass, Class);
       assertEquals(CLASS_NAME, uClass.name)
       assertEquals(INTERFACE_NAME, uClass.interfaceRealizations.head.contract.name);
   }
    

    
}
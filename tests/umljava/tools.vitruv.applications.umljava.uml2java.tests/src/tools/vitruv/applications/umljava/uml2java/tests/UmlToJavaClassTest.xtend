package tools.vitruv.applications.umljava.uml2java.tests

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.modifiers.Abstract
import org.emftext.language.java.modifiers.Final
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import tools.vitruv.applications.umljava.uml2java.Uml2JavaTransformationTest

import static org.junit.Assert.*
import static tools.vitruv.applications.umljava.util.JavaUtil.*
import static tools.vitruv.applications.umljava.testutil.JavaTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import static tools.vitruv.applications.umljava.util.UmlUtil.*
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.applications.umljava.util.JavaUtil.JavaVisibility
import org.emftext.language.java.types.NamespaceClassifierReference
import javax.naming.event.NamespaceChangeListener

class UmlToJavaClassTest extends Uml2JavaTransformationTest {
	private static val CLASS_NAME = "ClassName";
	private static val STANDARD_CLASS_NAME = "StandardClassName"
	private static val CLASS_RENAME = "ClassRenamed"
	private static val SUPER_CLASS_NAME = "SuperClassName"
	private static val INTERFACE_NAME = "InterfaceName";
	private static val INTERFACE_NAME2 = "InterfaceName2";
	
	private var Class uClass;
	
	
	
	@Before
	def void before() {
	    uClass = createUmlClassAndAddToPackage(rootElement, CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false)
        saveAndSynchronizeChanges(rootElement)
	}
	
	//@After
	def void after() {
	    if (uClass !== null) {
	        uClass.destroy;
	    }
        saveAndSynchronizeChanges(rootElement);
	}

	@Test
	def testCreateClass() {
	    val c = createUmlClassAndAddToPackage(rootElement, STANDARD_CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false)
	    saveAndSynchronizeChanges(rootElement) 
	    
	    val jClass = getCorrespondingClass(c)
	    assertEquals(STANDARD_CLASS_NAME, jClass.name)
		assertJavaFileExists(STANDARD_CLASS_NAME, #[]);
	}
	
	@Test
	def testDeletedClass() {
        uClass.destroy;
        saveAndSynchronizeChanges(rootElement);
        
        assertJavaFileNotExists(CLASS_NAME, #[]);
	}
	
	@Test
    def testChangeClassVisibility() {
        uClass.visibility = VisibilityKind.PRIVATE_LITERAL;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getCorrespondingClass(uClass)
        assertJavaModifiableHasVisibility(jClass, JavaVisibility.PRIVATE)
        assertClassEquals(uClass, jClass)

    }
    
    @Test
    def testChangeClassVisibility2() {
        uClass.visibility = VisibilityKind.PACKAGE_LITERAL;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getCorrespondingClass(uClass)
        assertJavaModifiableHasVisibility(jClass, JavaVisibility.PACKAGE)
        assertClassEquals(uClass, jClass)
    }
    
    @Test
    def testChangeAbstractClass() {
        uClass.isAbstract = true;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getCorrespondingClass(uClass)
        assertJavaModifiableAbstract(jClass, true)
        assertClassEquals(uClass, jClass)
    }
    
    @Test
    def testRenameClass() {
        uClass.name =  CLASS_RENAME;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getCorrespondingClass(uClass)
        assertEquals(CLASS_RENAME, jClass.name)
        assertJavaFileExists(CLASS_RENAME, #[]);
        assertJavaFileNotExists(CLASS_NAME, #[]);
        assertClassEquals(uClass, jClass)
    }
    
    
    @Test
    def testChangeFinalClass() {
        uClass.isFinalSpecialization = true;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getCorrespondingClass(uClass)
        assertJavaModifiableFinal(jClass, true)
        assertClassEquals(uClass, jClass)
    }
    
    @Test
    def testSuperClassChanged() {
        val superClass = createSimpleUmlClass(rootElement, SUPER_CLASS_NAME);
        uClass.generals += superClass;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getCorrespondingClass(uClass)
        val jSuperClass = getCorrespondingClass(superClass)
        assertHasSuperClass(jClass, jSuperClass);
        assertClassEquals(uClass, jClass)
    }
    
    @Test
    def testDeleteClassImplement() {
        val uI = createSimpleUmlInterface(rootElement, INTERFACE_NAME);
        val uI2 = createSimpleUmlInterface(rootElement, INTERFACE_NAME2);
        uClass.createInterfaceRealization("InterfacRealization", uI);
        uClass.createInterfaceRealization("InterfacRealization2", uI2);
        saveAndSynchronizeChanges(uClass);
        
        uClass.interfaceRealizations.remove(0);
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getCorrespondingClass(uClass)
        assertTrue(jClass.implements.size.toString, jClass.implements.size == 1);
        assertEquals(INTERFACE_NAME2, getClassifierFromTypeReference(jClass.implements.get(0)).name)
        assertJavaFileExists(INTERFACE_NAME, #[]);
        assertClassEquals(uClass, jClass)
    }
    
    @Test
    def testAddClassImplement() {
    	val class2 = createSimpleUmlClass(rootElement, "Class2")
        val uI = createSimpleUmlInterface(rootElement, INTERFACE_NAME);
        val realization = uClass.createInterfaceRealization("InterfacRealization", uI);
        println(realization.contract)
        saveAndSynchronizeChanges(uClass);
        val jClass = getCorrespondingClass(uClass)
        assertEquals(INTERFACE_NAME, getClassifierFromTypeReference(jClass.implements.head).name);
        realization.implementingClassifier = class2
        saveAndSynchronizeChanges(class2);
        assertClassEquals(uClass, jClass)
        
       
    }
    
    
    def testChangeInterfaceImplementer() {
    	
    }
 
    
	
	
}
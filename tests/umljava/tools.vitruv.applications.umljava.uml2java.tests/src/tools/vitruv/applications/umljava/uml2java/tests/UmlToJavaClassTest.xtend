package tools.vitruv.applications.umljava.uml2java.tests

import org.apache.log4j.Logger;
import tools.vitruv.applications.umljava.uml2java.AbstractUmlJavaTest
import org.junit.Test
import static org.junit.Assert.*;
import org.eclipse.uml2.uml.UMLFactory
import org.junit.Ignore
import org.junit.Before
import org.junit.After
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.modifiers.Abstract
import org.emftext.language.java.modifiers.Final
import static tools.vitruv.applications.umljava.util.JavaUtil.*

class UmlToJavaClassTest extends AbstractUmlJavaTest {
	private static val CLASS_NAME = "ClassName";
	private static val STANDARD_CLASS_NAME = "StandardClassName"
	private static val CLASS_RENAME = "ClassRenamed"
	private static val SUPER_CLASS_NAME = "SuperClassName"
	private static val INTERFACE_NAME = "InterfaceName";
	private static val INTERFACE_NAME2 = "InterfaceName2";
	
	private var org.eclipse.uml2.uml.Class uClass;
	
	
	
	@Before
	def void before() {
	    uClass = createSyncUmlClass(CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false)

	}
	
	//@After
	def void after() {
	    if (uClass != null) {
	        uClass.destroy;
	    }
        saveAndSynchronizeChanges(rootElement);
	}
	

	@Test
	def testCreateClass() {
	    val c = createSyncUmlClass(STANDARD_CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false)
		assertJavaFileExists(STANDARD_CLASS_NAME);
	}
	
	@Test
	def testDeletedClass() {
        uClass.destroy;
        saveAndSynchronizeChanges(rootElement);
        
        assertJavaFileNotExists(CLASS_NAME);
	}
	
	@Test
    def testChangeClassVisibility() {
        uClass.visibility = VisibilityKind.PRIVATE_LITERAL;
        saveAndSynchronizeChanges(uClass);
        
        var jClass = getJClassFromName(CLASS_NAME);
        assertJavaModifiableHasVisibility(jClass, VisibilityKind.PRIVATE_LITERAL);
        
        uClass.visibility = VisibilityKind.PACKAGE_LITERAL;
        saveAndSynchronizeChanges(uClass);
        
        jClass = getJClassFromName(CLASS_NAME);
        assertJavaModifiableHasVisibility(jClass, VisibilityKind.PACKAGE_LITERAL);
    }
    
    @Test
    def testChangeAbstractClass() {
        uClass.isAbstract = true;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(CLASS_NAME);
        assertJavaModifiableHasModifier(jClass, Abstract);
    }
    
    @Test
    def testRenameClass() {
        uClass.name =  CLASS_RENAME;
        saveAndSynchronizeChanges(uClass);
        
        assertJavaFileExists(CLASS_RENAME);
        assertJavaFileNotExists(CLASS_NAME);
    }
    
    
    @Test
    def testChangeFinalClass() {
        uClass.isFinalSpecialization = true;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(CLASS_NAME);
        assertJavaModifiableHasModifier(jClass, Final);
    }
    
    @Test
    def testSuperClassChanged() {
        val superClass = createSyncSimpleUmlClass(SUPER_CLASS_NAME);
        uClass.generals += superClass;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(CLASS_NAME);
        assertHasSuperClass(jClass, superClass);
    }
    
    @Test
    def testDeleteClassImplement() {
        val uI = createSyncSimpleUmlInterface(INTERFACE_NAME);
        val uI2 = createSyncSimpleUmlInterface(INTERFACE_NAME2);
        uClass.createInterfaceRealization("InterfacRealization", uI);
        uClass.createInterfaceRealization("InterfacRealization2", uI2);
        saveAndSynchronizeChanges(uClass);
        
        uClass.interfaceRealizations.remove(0);
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(CLASS_NAME);
        assertTrue(jClass.implements.size.toString, jClass.implements.size == 1); //TODO Ist 0 statt 1. Falsches Interface? Im Model ist s richtig.
        assertEquals(INTERFACE_NAME2, getClassifierfromTypeRef(jClass.implements.get(0)).name)
        assertJavaFileExists(INTERFACE_NAME);
    }
    
    @Test
    def testAddClassImplement() {
        val uI = createSyncSimpleUmlInterface(INTERFACE_NAME);
        uClass.createInterfaceRealization("InterfacRealization", uI);
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(CLASS_NAME)
        assertEquals(INTERFACE_NAME, getClassifierfromTypeRef(jClass.implements.head).name);
       
    }
 
    /**
     * @param childClass Java-Kindklasse
     * @param superClass Uml-Superclass
     */
	def private assertHasSuperClass(org.emftext.language.java.classifiers.Class childClass, org.eclipse.uml2.uml.Class superClass) {
	    assertEquals(superClass.name, getClassifierfromTypeRef(childClass.extends).name);
	}
	
	
}
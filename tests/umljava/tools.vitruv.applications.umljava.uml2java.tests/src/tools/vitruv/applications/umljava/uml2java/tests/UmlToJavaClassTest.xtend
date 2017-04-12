package tools.vitruv.applications.umljava.uml2java.tests

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.modifiers.Abstract
import org.emftext.language.java.modifiers.Final
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import tools.vitruv.applications.umljava.uml2java.AbstractUmlJavaTest

import static org.junit.Assert.*
import static tools.vitruv.applications.umljava.util.JavaUtil.*
import static tools.vitruv.applications.umljava.util.UmlUtil.*
import org.eclipse.uml2.uml.UMLFactory

class UmlToJavaClassTest extends AbstractUmlJavaTest {
	private static val CLASS_NAME = "ClassName";
	private static val STANDARD_CLASS_NAME = "StandardClassName"
	private static val CLASS_RENAME = "ClassRenamed"
	private static val SUPER_CLASS_NAME = "SuperClassName"
	private static val INTERFACE_NAME = "InterfaceName";
	private static val INTERFACE_NAME2 = "InterfaceName2";
	
	private var Class uClass;
	
	
	
	@Before
	def void before() {
	    uClass = createUmlClassAndAddToModel(rootElement, CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false)
        saveAndSynchronizeChanges(rootElement)
	}
	
	//@After
	def void after() {
	    if (uClass != null) {
	        uClass.destroy;
	    }
        saveAndSynchronizeChanges(rootElement);
	}
	
	@Test
	def void t() {
		val c = UMLFactory.eINSTANCE.createClass;
		c.name = "Cname"
		val uPack2 = UMLFactory.eINSTANCE.createPackage
		uPack2.name = "second"
		val uPack = UMLFactory.eINSTANCE.createPackage
		uPack.name = "first"
		uPack2.packagedElements += c
		rootElement.packagedElements += uPack
		uPack.packagedElements += uPack2
		saveAndSynchronizeChanges(rootElement)
		
		uPack.name = "neww"
		saveAndSynchronizeChanges(rootElement)
		
		/* 
		val jCl = getJClassFromName("Cname")
		println(jCl.modifiers)
		println(jCl)*/
	}

	@Test
	def testCreateClass() {
	    val c = createUmlClassAndAddToModel(rootElement, STANDARD_CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false)
	    saveAndSynchronizeChanges(rootElement) 
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
        
        val jClass = getJClassFromName(CLASS_NAME);
        assertJavaModifiableHasVisibility(jClass, VisibilityKind.PRIVATE_LITERAL);
        

    }
    
    @Test
    def testChangeClassVisibility2() {
        uClass.visibility = VisibilityKind.PACKAGE_LITERAL;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(CLASS_NAME);
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
        val superClass = createSimpleUmlClass(rootElement, SUPER_CLASS_NAME);
        uClass.generals += superClass;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(CLASS_NAME);
        assertHasSuperClass(jClass, superClass);
    }
    
    @Ignore @Test
    def testDeleteClassImplement() {
        val uI = createSimpleUmlInterface(rootElement, INTERFACE_NAME);
        val uI2 = createSimpleUmlInterface(rootElement, INTERFACE_NAME2);
        uClass.createInterfaceRealization("InterfacRealization", uI);
        uClass.createInterfaceRealization("InterfacRealization2", uI2);
        saveAndSynchronizeChanges(uClass);
        
        uClass.interfaceRealizations.remove(0);
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(CLASS_NAME);
        assertTrue(jClass.implements.size.toString, jClass.implements.size == 1); //TODO Ist 0 statt 1. Im Model ist es richtig.
        assertEquals(INTERFACE_NAME2, getClassifierfromTypeRef(jClass.implements.get(0)).name)
        assertJavaFileExists(INTERFACE_NAME);
    }
    
    @Test
    def testAddClassImplement() {
    	val class2 = createSimpleUmlClass(rootElement, "Class2")
        val uI = createSimpleUmlInterface(rootElement, INTERFACE_NAME);
        val realization = uClass.createInterfaceRealization("InterfacRealization", uI);
        println(realization.contract)
        saveAndSynchronizeChanges(uClass);
        val jClass = getJClassFromName(CLASS_NAME)
        assertEquals(INTERFACE_NAME, getClassifierfromTypeRef(jClass.implements.head).name);
        realization.implementingClassifier = class2
        saveAndSynchronizeChanges(class2);
        
       
    }
    
    
    def testChangeInterfaceImplementer() {
    	
    }
 
    /**
     * @param childClass Java-Kindklasse
     * @param superClass Uml-Superclass
     */
	def private assertHasSuperClass(org.emftext.language.java.classifiers.Class childClass, Class superClass) {
	    assertEquals(superClass.name, getClassifierfromTypeRef(childClass.extends).name);
	}
	
	
}
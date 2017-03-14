package tool.vitruv.applications.umljava.uml2java.tests

import org.apache.log4j.Logger;
import tool.vitruv.applications.umljava.uml2java.AbstractUmlJavaTest
import org.junit.Test
import static org.junit.Assert.*;
import org.eclipse.uml2.uml.UMLFactory
import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*;
import org.junit.Ignore
import org.junit.Before
import org.junit.After
import org.apache.log4j.PropertyConfigurator
import org.junit.BeforeClass
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.ClassMethod
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.modifiers.Abstract
import org.emftext.language.java.types.TypesFactory
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.members.InterfaceMethod
import org.emftext.language.java.types.NamespaceClassifierReference
import org.eclipse.uml2.uml.Type
import org.emftext.language.java.types.TypeReference
import java.util.List
import java.util.ArrayList
import org.eclipse.uml2.uml.Operation

class UmlToJavaClassTest extends AbstractUmlJavaTest {
	private static val CLASS_NAME = "ClassName";
	private static val CLASS_NAME2 = "SecondClassName"
	private static val CLASS_NAME3 = "ThirdClassName"
	private static val CLASS_NAME4 = "FourthClassName"
	private static val INTERFACE_NAME = "InterfaceName";
    private static val INTERFACE_NAME2 = "InterfaceName2"
    private static val INTERFACE_NAME3 = "InterfaceName3"
	private static val ATTR_NAME = "attributeName"
	private static val ATTR_NAME2 = "attributeName2"
	private static val DATATYPE_NAME = "DataTypeName"
	private static val DATATYPE_NAME2 = "DataTypeName2"
	private static val PRIMITIVETYPE_NAME = "int";
	private static val OP_NAME = "operationName"
	private static val OP_NAME2 = "operationName2"
	private static val PARAM_NAME = "parameterName"
    private static val PARAM_NAME2 = "parameterName2"
	
	private var org.eclipse.uml2.uml.Class uClass;
	private var org.eclipse.uml2.uml.Class uClass4;
	private var org.eclipse.uml2.uml.Property uAtt;
	private var org.eclipse.uml2.uml.DataType uDataType;
	private var org.eclipse.uml2.uml.Operation uOperation;
	private var org.eclipse.uml2.uml.Operation uIOperation;
	private var org.eclipse.uml2.uml.Interface uI;
	
    private var Class jClass;
	private var Interface jI;
	
	
	@Before
	def void bef() {
	    //log.info("Before")
	    /*  
	     *  --------------------------------     ------------------------     --------------------------------
	     *  |          + ClassName         |     |    FourthClassName   |     | <<Interface>> InterfaceName  |
	     *  |------------------------------|     |----------------------|     |------------------------------|
	     *  | + DataTypeName attributname  |     |                      |     |                              |
	     *  |------------------------------|     |----------------------|     |------------------------------|
	     *  | + operationName() : void     |     |                      |     |   operationName() : void     |
	     *  --------------------------------     ------------------------     --------------------------------
	     */
	    uClass = UMLFactory.eINSTANCE.createClass();
	    uClass.name = CLASS_NAME;
	    uClass.visibility = VisibilityKind.PUBLIC_LITERAL;
	    
	    uDataType = UMLFactory.eINSTANCE.createDataType;
        uDataType.name = DATATYPE_NAME;
        rootElement.packagedElements += uDataType
        
	    uAtt = UMLFactory.eINSTANCE.createProperty;
	    uAtt.name = ATTR_NAME;
	    uAtt.type = uDataType;
	    uAtt.visibility = VisibilityKind.PUBLIC_LITERAL;
	    uAtt.isReadOnly = false; //d.h. nicht final
	    uAtt.isStatic = false;
	    
	    uOperation = UMLFactory.eINSTANCE.createOperation;
	    uOperation.name = OP_NAME;
	    uOperation.visibility = VisibilityKind.PUBLIC_LITERAL;
	    uOperation.isStatic = false;
	    uOperation.isAbstract = false;
	    
	    uClass.ownedAttributes += uAtt;
	    uClass.ownedOperations += uOperation;
	    uClass.isAbstract = false;
	    uClass.isFinalSpecialization = false;
	    rootElement.packagedElements += uClass;
	    saveAndSynchronizeChanges(uClass);
	    
	    uIOperation = UMLFactory.eINSTANCE.createOperation;
        uIOperation.name = OP_NAME;
        uIOperation.type = uDataType;
        
        
        
	    uI = UMLFactory.eINSTANCE.createInterface;
	    uI.name = INTERFACE_NAME;
	    uI.ownedOperations += uIOperation;
	    
	    rootElement.packagedElements += uI;
	    saveAndSynchronizeChanges(uI);
	    
	    uClass4 = UMLFactory.eINSTANCE.createClass
	    uClass4.name = CLASS_NAME4;
	    rootElement.packagedElements += uClass4;
	    saveAndSynchronizeChanges(uClass4);
	}
	
	//@After
	def void after() {
	    //log.info("After")
	    if (uDataType != null) {
	        uDataType.destroy;
	    }
	    if (uAtt != null) {
	        uAtt.destroy;
	    }
	    if (uOperation != null) {
	        uOperation.destroy;
	    }
	    if (uIOperation != null) {
            uIOperation.destroy;
        }
	    if (uClass != null) {
	        uClass.destroy;
	    }
	    if (uI != null) {
            uI.destroy;
        }
        if (jI != null) {
            jI = null;
        }
        if (jClass != null) {
            jClass = null;
        }
        saveAndSynchronizeChanges(rootElement);
	}
	

	@Test
	def testCreateClass() {
	    val uClass2 = UMLFactory.eINSTANCE.createClass();
	    uClass2.name = CLASS_NAME2;
	    rootElement.packagedElements += uClass2;
        saveAndSynchronizeChanges(uClass2);

		assertModelExists(buildJavaFilePath(CLASS_NAME2));
        uClass2.destroy;

	}
	
	@Test
	def testDeletedClass() {
        uClass.destroy
        saveAndSynchronizeChanges(rootElement);
        
        assertModelNotExists(buildJavaFilePath(CLASS_NAME));
	}
	
	@Test
    def testChangeClassVisibility() {
        uClass.visibility = VisibilityKind.PRIVATE_LITERAL;
        saveAndSynchronizeChanges(uClass);
        
        jClass = getJClassFromName(Class, CLASS_NAME);
        assertTrue(jClass.private);
        assertFalse(jClass.public);
        assertFalse(jClass.protected);
        
        uClass.visibility = VisibilityKind.PROTECTED_LITERAL;
        saveAndSynchronizeChanges(uClass);
        
        jClass = getJClassFromName(Class, CLASS_NAME);
        assertTrue(jClass.protected);
        assertFalse(jClass.public);
        assertFalse(jClass.private);
        
    }
    @Test
    def testChangeAbstractClass() {
        uClass.isAbstract = true;
        saveAndSynchronizeChanges(uClass);
        
        jClass = getJClassFromName(Class, CLASS_NAME);
        assertNotNull(jClass);
        assertTrue(jClass.hasModifier(org.emftext.language.java.modifiers.Abstract))
        
        uClass.isAbstract = false;
        saveAndSynchronizeChanges(uClass);
        
        jClass = getJClassFromName(Class, CLASS_NAME);
        assertNotNull(jClass);
        assertTrue(!jClass.hasModifier(org.emftext.language.java.modifiers.Abstract))
    }
    
    @Test
    def testRenameClass() {
        uClass.name =  CLASS_NAME2;
        saveAndSynchronizeChanges(uClass);
        
        jClass = getJClassFromName(Class, CLASS_NAME2);
        assertNotNull(jClass);
        assertModelNotExists(CLASS_NAME);
    }
    
    
    @Test
    def testChangeFinalClass() {
        uClass.isFinalSpecialization = true;
        saveAndSynchronizeChanges(uClass);
        
        jClass = getJClassFromName(Class, CLASS_NAME)
        assertNotNull(jClass)
        assertTrue(jClass.hasModifier(org.emftext.language.java.modifiers.Final))
        
        uClass.isFinalSpecialization = false;
        saveAndSynchronizeChanges(uClass);
        
        jClass = getJClassFromName(Class, CLASS_NAME)
        assertNotNull(jClass)
        assertTrue(!jClass.hasModifier(org.emftext.language.java.modifiers.Final))
    }
    
    @Test
    def testSuperClassChanged() {
        uClass.generals += uClass4;
        saveAndSynchronizeChanges(uClass);
        
        jClass = getJClassFromName(Class, CLASS_NAME)
        assertEquals(CLASS_NAME4, getClassifierfromTypeRef(jClass.extends).name);
    }
    
    @Test
    def testf() {
        val uI = UMLFactory.eINSTANCE.createInterface;
        uI.name = "HustenInterface"
        val oP = UMLFactory.eINSTANCE.createOperation;
        oP.name = "iOperation";
        uI.ownedOperations += oP;
        
        val uC = UMLFactory.eINSTANCE.createClass;
        uC.name = "Saftladen";
        rootElement.packagedElements += uI
        rootElement.packagedElements += uC
        saveAndSynchronizeChanges(uI)
        saveAndSynchronizeChanges(uC)
        
        uC.createInterfaceRealization("InR", uI)
        

        
        saveAndSynchronizeChanges(uC)
        
        uC.interfaceRealizations.clear;
        saveAndSynchronizeChanges(uC)
    }
    
    @Test
    def testAddClassImplement() {
        val uI2 = UMLFactory.eINSTANCE.createInterface;
        uI2.name = INTERFACE_NAME2;
        rootElement.packagedElements += uI2;
        saveAndSynchronizeChanges(uI2);
        uClass.createInterfaceRealization("InterR", uI2)
        uClass.createInterfaceRealization("InterfacRealization", uI);
        
        
        
        saveAndSynchronizeChanges(uClass);
        
        jClass = getJClassFromName(Class, CLASS_NAME)
        assertEquals(INTERFACE_NAME2, getClassifierfromTypeRef(jClass.implements.head).name);
        
        uClass.interfaceRealizations.remove(1);
        saveAndSynchronizeChanges(uClass);
        
        jClass = getJClassFromName(Class, CLASS_NAME)
        assertTrue(jClass.implements.size.toString, jClass.implements.size == 1);
       
    }
 
	
	    
	def private <T extends org.emftext.language.java.classifiers.ConcreteClassifier> T getJClassFromName(java.lang.Class<T> c, String name) {
	    val iter = getModelResource(buildJavaFilePath(name), true).allContents
        val i2 = iter.filter(c)
        return i2.next() as T
	}
	
}
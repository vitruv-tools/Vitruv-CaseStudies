package tool.vitruv.applications.umljava.uml2java

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

class UmlToJavaClassTest extends AbstractUmlJavaTest {
    private static val log = Logger.getLogger(UmlToJavaClassTest);
	private static val CLASS_NAME = "ClassName";
	private static val CLASS_NAME2 = "SecondClassName"
	private static val CLASS_NAME3 = "ThirdClassName"
	private static val INTERFACE_NAME = "InterfaceName";
    private static val INTERFACE_NAME2 = "InterfaceName2"
	private static val ATTR_NAME = "attributeName"
	private static val ATTR_NAME2 = "attributeName2"
	private static val DATATYPE_NAME = "DataTypeName"
	private static val DATATYPE_NAME2 = "DataTypeName2"
	private static val OP_NAME = "operationName"
	private static val OP_NAME2 = "operationName2"
	
	private var org.eclipse.uml2.uml.Class uClass;
	private var org.eclipse.uml2.uml.Property uAtt;
	private var org.eclipse.uml2.uml.DataType uDataType;
	private var org.eclipse.uml2.uml.Operation uOperation;
	private var org.eclipse.uml2.uml.Operation uIOperation;
	private var org.eclipse.uml2.uml.Interface uI;
	

	private var Interface jI;
	
	@BeforeClass
	def static beforeClass() {
        PropertyConfigurator.configure("log4j.properties")
	    //log.info("Start")
	}
	
	@Before
	def void bef() {
	    //log.info("Before")
	    /*  
	     *  --------------------------------
	     *  |          + ClassName         |
	     *  |------------------------------|
	     *  | + DataTypeName attributname  |
	     *  |------------------------------|
	     *  | + operationName() : void     |
	     *  --------------------------------
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
	    
	    //jClass = getJClassFromName(Class, CLASS_NAME)
	    
	    uIOperation = UMLFactory.eINSTANCE.createOperation;
        uIOperation.name = OP_NAME;
        
	    uI = UMLFactory.eINSTANCE.createInterface;
	    uI.name = INTERFACE_NAME;
	    uI.ownedOperations += uIOperation;
	    
	    rootElement.packagedElements += uI;
	    saveAndSynchronizeChanges(uI);
	    
	    
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
        
        val jClass = getJClassFromName(Class, CLASS_NAME);
        assertTrue(jClass.private);
    }
    @Test
    def testChangeAbstractClass() {
        uClass.isAbstract = true;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(Class, CLASS_NAME);
        assertNotNull(jClass);
        assertTrue(jClass.hasModifier(org.emftext.language.java.modifiers.Abstract))
    }
    
    @Test
    def testRenameClass() {
        uClass.name =  CLASS_NAME2;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(Class, CLASS_NAME2)
        assertNotNull(jClass)
    }
    
    
    @Test
    def testChangeFinalClass() {
        
        uClass.isFinalSpecialization = true;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(Class, CLASS_NAME)
        assertNotNull(jClass)
        assertTrue(jClass.hasModifier(org.emftext.language.java.modifiers.Final))
    }
    
    
    
    @Test
    def testCreateInterface() {
        val uI2 = UMLFactory.eINSTANCE.createInterface;
        uI2.name = INTERFACE_NAME2;
        rootElement.packagedElements += uI2;
        saveAndSynchronizeChanges(uI2)
        
        var jI = getJClassFromName(org.emftext.language.java.classifiers.Interface, INTERFACE_NAME2);
        assertNotNull(jI);
        uI2.destroy;
    }
    
    @Test
    def testRenameInterface() {
        uI.name = INTERFACE_NAME2;
        saveAndSynchronizeChanges(uI)
        
        jI = getJClassFromName(org.emftext.language.java.classifiers.Interface, INTERFACE_NAME2);
        assertNotNull(jI);
        assertEquals(INTERFACE_NAME2, jI.name)
        assertModelNotExists(buildJavaFilePath(INTERFACE_NAME))
    }
    
    @Test
    def testDeleteInterface() {
        uI.destroy;
        saveAndSynchronizeChanges(rootElement);
        
        assertModelNotExists(buildJavaFilePath(INTERFACE_NAME))
    }
    
	@Test
	def testCreatePrimitiveAttribute() {
        val datatype = UMLFactory.eINSTANCE.createDataType;
        datatype.name = DATATYPE_NAME2;
        rootElement.packagedElements += datatype
        uClass.createOwnedAttribute(ATTR_NAME2, datatype);
        
        saveAndSynchronizeChanges(uClass);       
        
        val jClass = getJClassFromName(Class, CLASS_NAME)     
        val jAttribute = jClass.getMembersByName(ATTR_NAME2).head as Field
        assertNotNull(jAttribute);
	}
	
	@Test
	def testCreateAttribute() {
	    val customClass = UMLFactory.eINSTANCE.createClass;
	    customClass.name = CLASS_NAME3;
	    rootElement.packagedElements += customClass;
	    saveAndSynchronizeChanges(customClass);
	    
	    uClass.createOwnedAttribute(ATTR_NAME2, customClass);
	    saveAndSynchronizeChanges(uClass);
	       
	    val jClass = getJClassFromName(Class, CLASS_NAME);
        val jAttribute = jClass.getMembersByName(ATTR_NAME2).head as Field
        assertNotNull(jAttribute);
        assertEquals(CLASS_NAME3, jAttribute.typeReference.target.class.name)
	    
	}
	
	@Test
	def testRenameAttribute() {
	    uAtt.name = ATTR_NAME2;
	    saveAndSynchronizeChanges(uClass);
	    
	    val jClass = getJClassFromName(Class, CLASS_NAME);
	    val jAttr = jClass.getMembersByName(ATTR_NAME2).head as Field
	    assertNotNull(jAttr);
	    assertTrue(jClass.getMembersByName(ATTR_NAME).empty);
	}
	
	@Test
	def testDeleteAttribute() {
	    uAtt.destroy;
	    saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(Class, CLASS_NAME);
        assertTrue(jClass.getMembersByName(ATTR_NAME).empty);
	}

	@Test
	def testStaticAttribute() {
	    uAtt.isStatic = true;
        saveAndSynchronizeChanges(uClass);
         
        val jClass = getJClassFromName(Class, CLASS_NAME);
        val jAttribute = jClass.getMembersByName(ATTR_NAME).head as Field
        assertNotNull(jAttribute);
        assertTrue(jAttribute.hasModifier(org.emftext.language.java.modifiers.Static))
	}
	
    @Test
    def testFinalAttribute() {
        uAtt.isReadOnly = true;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(Class, CLASS_NAME);
        val jAttribute = jClass.getMembersByName(ATTR_NAME).head as Field
        assertNotNull(jAttribute);
        assertTrue(jAttribute.hasModifier(org.emftext.language.java.modifiers.Final))
    }
    
    @Test
    def testAttributeVisibility() {
        uAtt.visibility = VisibilityKind.PRIVATE_LITERAL;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(Class, CLASS_NAME); 
        val jAttribute = jClass.getMembersByName(ATTR_NAME).head as Field
        assertNotNull(jAttribute);
        assertTrue(jAttribute.hasModifier(org.emftext.language.java.modifiers.Private))
    }
    
    @Test
    def testCreateSimpleMethod() {
        uClass.createOwnedOperation(OP_NAME, null, null, null);
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(Class, CLASS_NAME);
        val jMeth = jClass.getMembersByName(OP_NAME).head as ClassMethod
        assertNotNull(jMeth)
        assertTrue(jMeth.typeReference instanceof org.emftext.language.java.types.Void)
    }
    
    @Test
    def testRenameMethod() {
        uOperation.name = OP_NAME2;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(Class, CLASS_NAME);
        val jMeth = jClass.getMembersByName(OP_NAME2).head as ClassMethod
        assertNotNull(jMeth)
        assertTrue(jMeth.typeReference instanceof org.emftext.language.java.types.Void)
        assertTrue(jClass.getMembersByName(OP_NAME).empty)
    }
    
    @Test
    def testDeleteMethod() {
        uOperation.destroy;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(Class, CLASS_NAME);
        assertTrue(jClass.getMembersByName(OP_NAME).empty)
    }
    
    @Test
    def testStaticMethod() {
        uOperation.isStatic = true;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(Class, CLASS_NAME);
        val jMeth = jClass.getMembersByName(OP_NAME).head as ClassMethod
        assertNotNull(jMeth)
        assertTrue(jMeth.hasModifier(org.emftext.language.java.modifiers.Static))
    }
    
    @Test
    def testAbstractMethod() {
        uOperation.isAbstract = true;
        saveAndSynchronizeChanges(uClass);
        val jClass = getJClassFromName(Class, CLASS_NAME);
        val jMeth = jClass.getMembersByName(OP_NAME).head as ClassMethod
        assertNotNull(jMeth)
        assertTrue(jMeth.hasModifier(org.emftext.language.java.modifiers.Abstract))
    }
    
    @Test
    def testMethodVisibility() {
        uOperation.visibility = VisibilityKind.PRIVATE_LITERAL;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getJClassFromName(Class, CLASS_NAME);
        val jMeth = jClass.getMembersByName(OP_NAME).head as ClassMethod
        assertNotNull(jMeth);
        assertTrue(jMeth.hasModifier(org.emftext.language.java.modifiers.Private))
    }
    
    @Test
    def testCreateInterfaceMethod() {
        val uOp = UMLFactory.eINSTANCE.createOperation;
        uOp.name = OP_NAME2;
        uI.ownedOperations += uOp;
        saveAndSynchronizeChanges(uI);
        
        val jI = getJClassFromName(Interface, INTERFACE_NAME);
        val jIM = jI.getMembersByName(OP_NAME2).head as InterfaceMethod;
        assertNotNull(jIM);
        uOp.destroy;
    }
    
    @Test
    def testRenameInterfaceMethod() {
        uIOperation.name = OP_NAME2;
        saveAndSynchronizeChanges(uI);
        
        val jI = getJClassFromName(Interface, INTERFACE_NAME);
        val jIM = jI.getMembersByName(OP_NAME2).head as InterfaceMethod
        assertNotNull(jIM)
        assertTrue(jI.getMembersByName(OP_NAME).empty)
    }
    
    @Test
    def testDeleteInterfaceMethod() {
        uIOperation.destroy;
        saveAndSynchronizeChanges(uI);
        
        val jI = getJClassFromName(Interface, INTERFACE_NAME);
        assertNull(jI.getMembersByName(OP_NAME).head)
    }
    
	
	    
	def private <T> T getJClassFromName(java.lang.Class<T> c, String name) {
	    val iter = getModelResource(buildJavaFilePath(name)).allContents
        val i2 = iter.filter(c)
        return i2.next() as T
	}
}
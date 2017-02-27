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

class UmlToJavaClassTest extends AbstractUmlJavaTest {
    private static val log = Logger.getLogger(UmlToJavaClassTest);
	private static val CLASS_NAME = "ClassName";
	private static val CLASS_NAME2 = "SecondClassName"
	private static val CLASS_NAME3 = "ThirdClassName"
	private static val INTERFACE_NAME = "InterfaceName";
    private static val INTERFACE_NAME2 = "InterfaceName2"
	private static val ATTR_NAME = "AttributeName"
	private static val DATATYPE_NAME = "DataTypeName"
	private static val OP_NAME = "OperationName"
	
	private var org.eclipse.uml2.uml.Class uClass;
	private var org.eclipse.uml2.uml.Class uClass2;
	
	@BeforeClass
	def static beforeClass() {
        PropertyConfigurator.configure("log4j.properties")
	    //log.info("Start")
	}
	
	@Before
	def before() {
	    //log.info("Before")
	    uClass = UMLFactory.eINSTANCE.createClass();
	    uClass.name = CLASS_NAME;
	    rootElement.packagedElements += uClass;
	    saveAndSynchronizeChanges(uClass);
	}
	
	@After
	def after() {
	    log.info("After")
	    if (uClass != null) {
	        uClass.destroy;
	    }
	}

	@Test
	def testCreateClass() {
	    uClass2 = UMLFactory.eINSTANCE.createClass();
	    uClass2.name = CLASS_NAME3;
	    rootElement.packagedElements += uClass2;
        saveAndSynchronizeChanges(uClass2);

		assertModelExists(buildJavaFilePath(CLASS_NAME3));
        val jClass = getJClassfromName(CLASS_NAME3);
        assertEquals(CLASS_NAME3, jClass.name)
		
	}
	
	@Test
	def testDeletedClass() {
        uClass.destroy
        saveAndSynchronizeChanges(rootElement);
        
        assertModelNotExists(buildJavaFilePath(CLASS_NAME));
	}
	
	@Test
	def testCreateAttribute() {   
        val datatype = UMLFactory.eINSTANCE.createDataType;
        datatype.name = DATATYPE_NAME;
        rootElement.packagedElements += datatype
        uClass.createOwnedAttribute(ATTR_NAME, datatype);
        
        saveAndSynchronizeChanges(uClass);       
        
        val jClass = getJClassfromName(CLASS_NAME)     
        val jAttribute = jClass.getMembersByName(ATTR_NAME).head as Field
        assertNotNull(jAttribute);
	}
	
	@Test
	def testCreateSimpleMethod() {
	    uClass.createOwnedOperation(OP_NAME, null, null, null);
	    saveAndSynchronizeChanges(uClass);
	    
        val jClass = getJClassfromName(CLASS_NAME)
        val jMeth = jClass.getMembersByName(OP_NAME).head as ClassMethod
        assertNotNull(jMeth)
        assertTrue(jMeth.typeReference instanceof org.emftext.language.java.types.Void)
	}
	
	@Test
	def testRenameClass() {
	    uClass.name =  CLASS_NAME2;
	    saveAndSynchronizeChanges(uClass);
	    val jClass = getJClassfromName(CLASS_NAME2)
	    assertNotNull(jClass)
	    
	}
	
	@Test
	def testCreateInterface() {
	    val uI = UMLFactory.eINSTANCE.createInterface;
	    uI.name = INTERFACE_NAME;
	    rootElement.packagedElements += uI;
	    
	    saveAndSynchronizeChanges(uI)
	    
	    val iter = getModelResource(buildJavaFilePath(INTERFACE_NAME)).allContents
        val i2 = iter.filter(org.emftext.language.java.classifiers.Interface)
        val jI = i2.next() as org.emftext.language.java.classifiers.Interface
        assertEquals(INTERFACE_NAME, jI.name)
	    
	    
	}
	
	@Test
	def testChangeClassVisibility() {
	    uClass.visibility = VisibilityKind.PRIVATE_LITERAL;
	    saveAndSynchronizeChanges(uClass);
	    
	    val jClass = getJClassfromName(CLASS_NAME);
	    assertTrue(jClass.private);
	}
	@Test
	def testChangeAbstractClass() {
	    uClass.isAbstract = true;
	    saveAndSynchronizeChanges(uClass);
	    val jClass = getJClassfromName(CLASS_NAME);
	    assertTrue(!jClass.modifiers.empty)
        val b = jClass.modifiers.head
        
        assertTrue(b instanceof Abstract);
	}
	
	    
	def private org.emftext.language.java.classifiers.Class getJClassfromName(String name) {
	    val iter = getModelResource(buildJavaFilePath(name)).allContents
        val i2 = iter.filter(org.emftext.language.java.classifiers.Class)
        return i2.next() as org.emftext.language.java.classifiers.Class
	}
}
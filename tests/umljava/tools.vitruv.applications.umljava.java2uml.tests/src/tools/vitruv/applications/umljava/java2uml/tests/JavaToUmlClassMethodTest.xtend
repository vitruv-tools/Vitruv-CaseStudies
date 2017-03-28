package tools.vitruv.applications.umljava.java2uml.tests

import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.modifiers.ModifiersFactory
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import tools.vitruv.applications.umljava.java2uml.AbstractJavaUmlTest
import tools.vitruv.framework.util.bridges.EcoreBridge

import static org.junit.Assert.*
import static tools.vitruv.applications.umljava.util.JavaUtil.*
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.applications.umljava.util.JavaUtil.JavaVisibility

class JavaToUmlClassMethodTest extends AbstractJavaUmlTest {
    private static val CLASS_NAME = "ClassName";
    private static val TYPE_NAME = "TypeName";
    private static val OPERATION_NAME = "classMethod";
    private static val STANDARD_OPERATION_NAME = "standardMethod";
    private static val OPERATION_RENAME = "classMethodRenamed";
    private static val PARAMETER_NAME = "parameterName";
    private static val PARAMETER_RENAME = "parameterRenamed";
    
    private static var Class jClass;
    private static var Class typeClass;
    private static var ClassMethod jMeth;
    
    @Before
    def void before() {
        jClass = createSimpleJavaClassWithCompilationUnit(CLASS_NAME);
        typeClass = createSimpleJavaClassWithCompilationUnit(TYPE_NAME);
        jMeth = createSimpleJavaOperation(OPERATION_NAME)
        jClass.members += jMeth;
        saveAndSynchronizeChanges(jClass);
    }
    
    //@After
    def void after() {
        if (jMeth != null) {
            
        }
        if (jClass != null) {
            
        }
        if (typeClass != null) {

        }
        //saveAndSynchronizeChanges(rootElement);
    }
    
    @Test
    def void testCreateSimpleMethod() {
        val meth = createSimpleJavaOperation(STANDARD_OPERATION_NAME)
        jClass.members += meth
        saveAndSynchronizeChanges(jClass)
        
        assertNameAndReturnUniqueUmlMethod(meth)
    }
    
    @Test
    def void testChangeReturnType() {
        jMeth.typeReference = createNamespaceReferenceFromClassifier(typeClass)
        saveAndSynchronizeChanges(jMeth)
        
        val uOp = assertNameAndReturnUniqueUmlMethod(jMeth)
        assertEquals(typeClass.name, uOp.type.name)
    }
    
    @Test
    def void testRenameMethod() {
        jMeth.name = OPERATION_RENAME
        saveAndSynchronizeChanges(jMeth)
        
        assertNameAndReturnUniqueUmlMethod(jMeth)
    }
    
    @Test
    def void testDeleteMethod() {
        jClass.members.clear
        jMeth = null
        saveAndSynchronizeChanges(jClass)
        
        val uClass = getCorrespondingObject(jClass, org.eclipse.uml2.uml.Class)
        assertTrue(uClass.ownedOperations.nullOrEmpty)
    }
    
    @Test
    def testStaticMethod() {
        jMeth.addModifier(ModifiersFactory.eINSTANCE.createStatic)
        saveAndSynchronizeChanges(jMeth)
        
        val uOp = assertNameAndReturnUniqueUmlMethod(jMeth)
        assertTrue(uOp.static)
        
    }
    
    @Test
    def testAbstractMethod() {
        jMeth.addModifier(ModifiersFactory.eINSTANCE.createAbstract)
        saveAndSynchronizeChanges(jMeth)
        
        val uOp = assertNameAndReturnUniqueUmlMethod(jMeth)
        assertTrue(uOp.abstract)
    }
    
    @Test
    def testMethodVisibility() {
        jMeth.makeProtected
        saveAndSynchronizeChanges(jMeth)
        
        var uOp = assertNameAndReturnUniqueUmlMethod(jMeth)
        assertHasVisibility(uOp, JavaVisibility.PROTECTED)
        
        jMeth.makePrivate
        saveAndSynchronizeChanges(jMeth)
        
        uOp = assertNameAndReturnUniqueUmlMethod(jMeth)
        assertHasVisibility(uOp, JavaVisibility.PRIVATE)
    }

    
    @Test
    def testCreateParameter() {
        val jParam = createJavaParameter(PARAMETER_NAME, createNamespaceReferenceFromClassifier(typeClass))
        jMeth.parameters += jParam
        saveAndSynchronizeChanges(jMeth)

        val uOp = assertNameAndReturnUniqueUmlMethod(jMeth)
        assertUmlOperationHasUniqueParameter(uOp, jParam)
    }
    

}
package tools.vitruv.applications.umljava.java2uml.tests

import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.modifiers.ModifiersFactory
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import tools.vitruv.applications.umljava.java2uml.Java2UmlTransformationTest
import tools.vitruv.framework.util.bridges.EcoreBridge

import static org.junit.Assert.*
import static extension tools.vitruv.applications.umljava.util.java.JavaMemberAndParameterUtil.*
import static tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*
import static extension tools.vitruv.applications.umljava.util.java.JavaModifierUtil.*
import static tools.vitruv.applications.umljava.testutil.UmlTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.applications.umljava.util.java.JavaVisibility
import org.eclipse.uml2.types.TypesFactory
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.members.MembersFactory
import org.eclipse.uml2.uml.VisibilityKind
import org.eclipse.emf.ecore.util.EcoreUtil

class JavaToUmlClassMethodTest extends Java2UmlTransformationTest {
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
        val uOperation = getCorrespondingMethod(meth)
        val uClass = getCorrespondingClass(jClass)
        assertUmlOperationTraits(uOperation, STANDARD_OPERATION_NAME, VisibilityKind.PUBLIC_LITERAL, null, 
            false, false, uClass, null)
        assertMethodEquals(uOperation, meth)
    }
    
    @Test
    def void testChangeReturnType() {
        jMeth.typeReference = createNamespaceReferenceFromClassifier(typeClass)
        saveAndSynchronizeChanges(jMeth)
        
        val uOperation = getCorrespondingMethod(jMeth)
        val uTypeClass = getCorrespondingClass(typeClass)
        assertUmlOperationHasReturntype(uOperation, uTypeClass)
        assertMethodEquals(uOperation, jMeth)
    }
    
    @Test
    def void testRenameMethod() {
        jMeth.name = OPERATION_RENAME
        saveAndSynchronizeChanges(jMeth)
        
        val uOperation = getCorrespondingMethod(jMeth)
        val uClass = getCorrespondingClass(jClass)
        assertEquals(OPERATION_RENAME, uOperation.name)
        assertUmlClassHasUniqueOperation(uClass, OPERATION_RENAME)
        assertMethodEquals(uOperation, jMeth)
    }
    
    @Test
    def void testDeleteMethod() {
        assertNotNull(getCorrespondingMethod(jMeth))
        
        EcoreUtil.delete(jMeth)
        saveAndSynchronizeChanges(jClass)
        
        val uClass = getCorrespondingClass(jClass)
        assertUmlClassDontHaveOperation(uClass, OPERATION_RENAME)
    }
    
    @Test
    def testStaticMethod() {
        jMeth.static = true
        saveAndSynchronizeChanges(jMeth)
        
        val uOperation = getCorrespondingMethod(jMeth)
        assertUmlFeatureHasStaticValue(uOperation, true)
        assertMethodEquals(uOperation, jMeth)
        
    }
    
    @Test
    def testAbstractMethod() {
        jMeth.abstract = true
        saveAndSynchronizeChanges(jMeth)
        
        val uOperation = getCorrespondingMethod(jMeth)
        assertUmlOperationHasAbstractValue(uOperation, true)
        assertMethodEquals(uOperation, jMeth)
    }
    
    @Test
    def testMethodVisibility() {
        jMeth.makeProtected
        saveAndSynchronizeChanges(jMeth)
        
        var uOperation = getCorrespondingMethod(jMeth)
        assertUmlNamedElementHasVisibility(uOperation, VisibilityKind.PROTECTED_LITERAL)
        assertMethodEquals(uOperation, jMeth)
        
        jMeth.makePrivate
        saveAndSynchronizeChanges(jMeth)
        
        uOperation = getCorrespondingMethod(jMeth)
        assertUmlNamedElementHasVisibility(uOperation, VisibilityKind.PRIVATE_LITERAL)
        assertMethodEquals(uOperation, jMeth)
    }

    
    @Test
    def testCreateParameter() {
        val jParam = createJavaParameter(PARAMETER_NAME, createNamespaceReferenceFromClassifier(typeClass))
        jMeth.parameters += jParam
        saveAndSynchronizeChanges(jMeth)

        val uOperation = getCorrespondingMethod(jMeth)
        assertUmlOperationHasUniqueParameter(uOperation, PARAMETER_NAME)
        assertMethodEquals(uOperation, jMeth)
    }
    

}
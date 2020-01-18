package tools.vitruv.applications.umljava.java2uml.tests

import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.members.ClassMethod
import org.junit.Before
import org.junit.Test
import tools.vitruv.applications.umljava.java2uml.Java2UmlTransformationTest

import static org.junit.Assert.*
import static extension tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static extension tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*
import static tools.vitruv.applications.umljava.testutil.UmlTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import tools.vitruv.applications.util.temporary.java.JavaVisibility
import org.eclipse.uml2.uml.VisibilityKind
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.types.TypesFactory
import org.emftext.language.java.parameters.OrdinaryParameter

/**
 * A test class to test the class method reactions.
 * @author Fei
 */
class JavaToUmlClassMethodTest extends Java2UmlTransformationTest {
    private static val CLASS_NAME = "ClassName";
    private static val TYPE_NAME = "TypeName";
    private static val TYPE_NAME2 = "TypeName2";
    private static val OPERATION_NAME = "classMethod";
    private static val OPERATION_NAME2 = "classMethod2";
    private static val STANDARD_OPERATION_NAME = "standardMethod";
    private static val OPERATION_RENAME = "classMethodRenamed";
    private static val PARAMETER_NAME = "parameterName";
    private static val PARAMETER_NAME2 = "parameterName2";
    private static val PARAMETER_RENAME = "parameterRenamed";
    
    private static var Class jClass;
    private static var Class typeClass;
    private static var Class typeClass2;
    private static var ClassMethod jMeth;
    private static var ClassMethod jParamMeth;
    private static var OrdinaryParameter jParam;
    
    /**
     * Initializes and synchronizes three classes. One class has two methods.
     * One of the methods owns a parameter.
     */
    @Before
    def void before() {
        jClass = createSimpleJavaClassWithCompilationUnit(CLASS_NAME);
        typeClass = createSimpleJavaClassWithCompilationUnit(TYPE_NAME);
        typeClass2 = createSimpleJavaClassWithCompilationUnit(TYPE_NAME2);
        jMeth = createSimpleJavaOperation(OPERATION_NAME)
        jParam = createJavaParameter(PARAMETER_NAME, createNamespaceReferenceFromClassifier(typeClass2))
        jParamMeth = createJavaClassMethod(OPERATION_NAME2, TypesFactory.eINSTANCE.createBoolean, JavaVisibility.PUBLIC, false, false, #[jParam])
        jClass.members += jMeth;
        jClass.members += jParamMeth
        saveAndSynchronizeChanges(jClass);
    }
    
    /**
     * Tests if a corresponding uml method is created when a java method is created.
     */
    @Test
    def void testCreateMethod() {
        val meth = createSimpleJavaOperation(STANDARD_OPERATION_NAME)
        jClass.members += meth
        saveAndSynchronizeChanges(jClass)
        val uOperation = getCorrespondingMethod(meth)
        val uClass = getCorrespondingClass(jClass)
        assertUmlOperationTraits(uOperation, STANDARD_OPERATION_NAME, VisibilityKind.PUBLIC_LITERAL, null, 
            false, false, uClass, null)
        assertClassMethodEquals(uOperation, meth)
    }
    
    /**
     * Tests if a change of the return type is correctly reflected on the uml method.
     */
    @Test
    def void testChangeReturnType() {
        jMeth.typeReference = createNamespaceReferenceFromClassifier(typeClass)
        saveAndSynchronizeChanges(jMeth)
        
        val uOperation = getCorrespondingMethod(jMeth)
        val uTypeClass = getCorrespondingClass(typeClass)
        assertUmlOperationHasReturntype(uOperation, uTypeClass)
        assertClassMethodEquals(uOperation, jMeth)
    }
    
    /**
     * Tests if renaming the java method also renames the corresponding uml method.
     */
    @Test
    def void testRenameMethod() {
        jMeth.name = OPERATION_RENAME
        saveAndSynchronizeChanges(jMeth)
        
        val uOperation = getCorrespondingMethod(jMeth)
        val uClass = getCorrespondingClass(jClass)
        assertEquals(OPERATION_RENAME, uOperation.name)
        assertUmlClassHasUniqueOperation(uClass, OPERATION_RENAME)
        assertClassMethodEquals(uOperation, jMeth)
    }
    
    /**
     * Tests if a deletion is reflected on the uml method.
     */
    @Test
    def void testDeleteMethod() {
        assertNotNull(getCorrespondingMethod(jMeth))
        
        EcoreUtil.delete(jMeth)
        saveAndSynchronizeChanges(jClass)
        
        val uClass = getCorrespondingClass(jClass)
        assertUmlClassDontHaveOperation(uClass, OPERATION_RENAME)
    }
    
    /**
     * Checks if changing the static modifier also changes the static property of
     * the corresponding uml method.
     */
    @Test
    def testStaticMethod() {
        jMeth.static = true
        saveAndSynchronizeChanges(jMeth)
        
        var uOperation = getCorrespondingMethod(jMeth)
        assertUmlFeatureHasStaticValue(uOperation, true)
        assertClassMethodEquals(uOperation, jMeth)
        
        jMeth.static = false
        saveAndSynchronizeChanges(jMeth)
        
        uOperation = getCorrespondingMethod(jMeth)
        assertUmlFeatureHasStaticValue(uOperation, false)
        assertClassMethodEquals(uOperation, jMeth)
        
    }
    
    /**
     * Tests if changing the abstract modifier also changes the abstract property of
     * the corresponding uml method.
     */
    @Test
    def testAbstractMethod() {
        jMeth.abstract = true
        saveAndSynchronizeChanges(jMeth)
        
        var uOperation = getCorrespondingMethod(jMeth)
        assertUmlOperationHasAbstractValue(uOperation, true)
        assertClassMethodEquals(uOperation, jMeth)
        
        jMeth.abstract = false
        saveAndSynchronizeChanges(jMeth)
        
        uOperation = getCorrespondingMethod(jMeth)
        assertUmlOperationHasAbstractValue(uOperation, false)
        assertClassMethodEquals(uOperation, jMeth)
    }
    
    /**
     * Asserts that changing the final modifier also changes the final property of
     * the corresponding uml method.
     */
    @Test
    def testFinalMethod() {
        jMeth.final = true
        saveAndSynchronizeChanges(jMeth)
        
        var uOperation = getCorrespondingMethod(jMeth)
        assertUmlOperationHasFinalValue(uOperation, true)
        assertClassMethodEquals(uOperation, jMeth)
        
        jMeth.final = false
        saveAndSynchronizeChanges(jMeth)
        
        uOperation = getCorrespondingMethod(jMeth)
        assertUmlOperationHasFinalValue(uOperation, false)
        assertClassMethodEquals(uOperation, jMeth)
    }
    
    /**
     * Checks if changing the visibility modifier also changes the visibility of
     * the corresponding uml method.
     */
    @Test
    def testMethodVisibility() {
        jMeth.makeProtected
        saveAndSynchronizeChanges(jMeth)
        
        var uOperation = getCorrespondingMethod(jMeth)
        assertUmlNamedElementHasVisibility(uOperation, VisibilityKind.PROTECTED_LITERAL)
        assertClassMethodEquals(uOperation, jMeth)
        
        jMeth.makePrivate
        saveAndSynchronizeChanges(jMeth)
        
        uOperation = getCorrespondingMethod(jMeth)
        assertUmlNamedElementHasVisibility(uOperation, VisibilityKind.PRIVATE_LITERAL)
        assertClassMethodEquals(uOperation, jMeth)
    }

    /**
     * Checks if a uml parameter is created after a java parameter is created.
     */
    @Test
    def testCreateParameter() {
        val param = createJavaParameter(PARAMETER_NAME2, createNamespaceReferenceFromClassifier(typeClass))
        jMeth.parameters += param
        saveAndSynchronizeChanges(jMeth)

        val uOperation = getCorrespondingMethod(jMeth)
        val uParam = getCorrespondingParameter(param)
        assertUmlOperationHasUniqueParameter(uOperation, PARAMETER_NAME2)
        assertParameterEquals(uParam, param)
    }
    
    /**
     * Tests the rename reaction in the context of parameters.
     */
    @Test
    def testRenameParameter() {
        jParam.name = PARAMETER_RENAME
        saveAndSynchronizeChanges(jMeth)

        val uOperation = getCorrespondingMethod(jParamMeth)
        val uParam = getCorrespondingParameter(jParam)
        assertUmlOperationHasUniqueParameter(uOperation, PARAMETER_RENAME)
        assertParameterEquals(uParam, jParam)
    }
    
    /**
     * Tests if the corresponding uml parameter is deleted when the java parameter is deleted.
     */
    @Test
    def testDeleteParameter() {
        assertNotNull(jParam)
        EcoreUtil.delete(jParam)
        saveAndSynchronizeChanges(jMeth)
        
        val uOperation = getCorrespondingMethod(jMeth)
        assertUmlOperationDontHaveParameter(uOperation, PARAMETER_NAME)
    }
    
    /**
     * Tests if a change of the java parameter type is correctly propagated to the uml parameter.
     */
    @Test
    def testChangeParameterType() {
        jParam.typeReference = createNamespaceReferenceFromClassifier(typeClass)
        saveAndSynchronizeChanges(jParam)

        val uParam = getCorrespondingParameter(jParam)
        val uTypeClass = getCorrespondingClass(typeClass)
        assertUmlParameterTraits(uParam, PARAMETER_NAME, uTypeClass)
        assertParameterEquals(uParam, jParam)
    }
    
    /**
     * Tests if a constructor creates a fitting uml operation.
     */
    @Test
    def testCreateConstructor() {
        val jConstr = createJavaConstructorAndAddToClass(jClass, JavaVisibility.PUBLIC)
        saveAndSynchronizeChanges(jConstr)
        
        val uConstr = getCorrespondingMethod(jConstr)
        assertNotNull(uConstr)
        assertEquals(jConstr.name, uConstr.name)
        assertUmlNamedElementHasVisibility(uConstr, VisibilityKind.PUBLIC_LITERAL)
       
    }
    

}
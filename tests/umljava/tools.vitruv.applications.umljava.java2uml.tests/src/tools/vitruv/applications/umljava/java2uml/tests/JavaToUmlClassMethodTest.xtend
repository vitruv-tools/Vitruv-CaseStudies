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
import static tools.vitruv.applications.umljava.util.java.JavaStandardType.*
import static extension tools.vitruv.applications.umljava.util.java.JavaModifierUtil.*
import static tools.vitruv.applications.umljava.testutil.UmlTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.applications.umljava.util.java.JavaVisibility
import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*
import org.emftext.language.java.members.MembersFactory
import org.eclipse.uml2.uml.VisibilityKind
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.types.TypesFactory
import org.emftext.language.java.parameters.OrdinaryParameter

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
    
    //@After
    def void after() {
        if (jClass !== null) {
            deleteAndSynchronizeModel(buildJavaFilePath(jClass.eContainer as CompilationUnit))
        }
        if (typeClass !== null) {
            deleteAndSynchronizeModel(buildJavaFilePath(typeClass.eContainer as CompilationUnit))
        }
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
        
        var uOperation = getCorrespondingMethod(jMeth)
        assertUmlFeatureHasStaticValue(uOperation, true)
        assertMethodEquals(uOperation, jMeth)
        
        jMeth.static = false
        saveAndSynchronizeChanges(jMeth)
        
        uOperation = getCorrespondingMethod(jMeth)
        assertUmlFeatureHasStaticValue(uOperation, false)
        assertMethodEquals(uOperation, jMeth)
        
    }
    
    @Test
    def testAbstractMethod() {
        jMeth.abstract = true
        saveAndSynchronizeChanges(jMeth)
        
        var uOperation = getCorrespondingMethod(jMeth)
        assertUmlOperationHasAbstractValue(uOperation, true)
        assertMethodEquals(uOperation, jMeth)
        
        jMeth.abstract = false
        saveAndSynchronizeChanges(jMeth)
        
        uOperation = getCorrespondingMethod(jMeth)
        assertUmlOperationHasAbstractValue(uOperation, false)
        assertMethodEquals(uOperation, jMeth)
    }
    
    @Test
    def testFinalMethod() {
        jMeth.final = true
        saveAndSynchronizeChanges(jMeth)
        
        var uOperation = getCorrespondingMethod(jMeth)
        assertUmlOperationHasFinalValue(uOperation, true)
        assertMethodEquals(uOperation, jMeth)
        
        jMeth.final = false
        saveAndSynchronizeChanges(jMeth)
        
        uOperation = getCorrespondingMethod(jMeth)
        assertUmlOperationHasFinalValue(uOperation, false)
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
        val param = createJavaParameter(PARAMETER_NAME2, createNamespaceReferenceFromClassifier(typeClass))
        jMeth.parameters += param
        saveAndSynchronizeChanges(jMeth)

        val uOperation = getCorrespondingMethod(jMeth)
        val uParam = getCorrespondingParameter(param)
        assertUmlOperationHasUniqueParameter(uOperation, PARAMETER_NAME2)
        assertParameterEquals(uParam, param)
    }
    
    @Test
    def testRenameParameter() {
        jParam.name = PARAMETER_RENAME
        saveAndSynchronizeChanges(jMeth)

        val uOperation = getCorrespondingMethod(jParamMeth)
        val uParam = getCorrespondingParameter(jParam)
        assertUmlOperationHasUniqueParameter(uOperation, PARAMETER_RENAME)
        assertParameterEquals(uParam, jParam)
    }
    
    @Test
    def testDeleteParameter() {
        assertNotNull(jParam)
        EcoreUtil.delete(jParam)
        saveAndSynchronizeChanges(jMeth)
        
        val uOperation = getCorrespondingMethod(jMeth)
        assertUmlOperationDontHaveParameter(uOperation, PARAMETER_NAME)
    }
    
    @Test
    def testChangeParameterType() {
        jParam.typeReference = createNamespaceReferenceFromClassifier(typeClass)
        saveAndSynchronizeChanges(jParam)

        val uParam = getCorrespondingParameter(jParam)
        val uTypeClass = getCorrespondingClass(typeClass)
        assertUmlParameterTraits(uParam, PARAMETER_NAME, uTypeClass)
        assertParameterEquals(uParam, jParam)
    }
    
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
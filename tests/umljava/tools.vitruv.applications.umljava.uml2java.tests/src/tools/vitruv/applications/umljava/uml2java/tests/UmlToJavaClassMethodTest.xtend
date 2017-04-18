package tools.vitruv.applications.umljava.uml2java.tests

import static tools.vitruv.applications.umljava.util.UmlUtil.*
import static tools.vitruv.applications.umljava.testutil.JavaTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import static tools.vitruv.applications.umljava.util.JavaUtil.*
import tools.vitruv.applications.umljava.uml2java.Uml2JavaTransformationTest
import org.junit.Test
import static org.junit.Assert.*;
import org.junit.Before
import org.junit.After
import org.emftext.language.java.members.ClassMethod
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.modifiers.Static
import org.emftext.language.java.modifiers.Abstract
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.applications.umljava.util.JavaUtil.JavaVisibility
import org.emftext.language.java.types.TypesFactory

class UmlToJavaClassMethodTest extends Uml2JavaTransformationTest {
    private static val CLASS_NAME = "ClassName";
    private static val TYPE_NAME = "TypeName";
    private static val OPERATION_NAME = "classMethod";
    private static val STANDARD_OPERATION_NAME = "standardMethod";
    private static val OPERATION_RENAME = "classMethodRenamed";
    private static val PRIMITIVE_TYPE = "int"
    private static val PARAMETER_NAME = "parameterName";
    private static val STANDARD_PARAMETER_NAME = "standardParameterName"
    private static val PARAMETER_RENAME = "parameterRenamed";
    
    private static var org.eclipse.uml2.uml.Class uClass
    private static var org.eclipse.uml2.uml.Class typeClass
    private static var org.eclipse.uml2.uml.Parameter uParam
    private static var org.eclipse.uml2.uml.PrimitiveType pType
    private static var Operation uOp
    
    @Before
    def void before() {
        uClass = createSimpleUmlClass(rootElement, CLASS_NAME);
        typeClass = createSimpleUmlClass(rootElement, TYPE_NAME);
        pType = createUmlPrimitiveTypeAndAddToModel(rootElement, PRIMITIVE_TYPE)
        uParam = createUmlParameter(PARAMETER_NAME, pType)
        uOp = createUmlOperation(OPERATION_NAME, null, VisibilityKind.PUBLIC_LITERAL, false, false, #[uParam])
        uClass.ownedOperations += uOp;
        rootElement.packagedElements += uClass;
        rootElement.packagedElements += typeClass;
        rootElement.packagedElements += pType;
        saveAndSynchronizeChanges(rootElement);
    }
    
    //@After
    def void after() {
        if (uOp != null) {
            uOp.destroy;
        }
        if (uClass != null) {
            uClass.destroy;
        }
        if (typeClass != null) {
            typeClass.destroy;
        }
        if (uParam != null) {
        	uParam.destroy
        }
        if (pType != null) {
        	pType.destroy
        }
        saveAndSynchronizeChanges(rootElement);
    }
    
    @Test
    def void testCreateSimpleMethod() {
        val operation = uClass.createOwnedOperation(STANDARD_OPERATION_NAME, null, null, null);
        saveAndSynchronizeChanges(uClass);
        
        val jMethod = getCorrespondingClassMethod(operation)
        val jClass = getCorrespondingClass(uClass)
        assertJavaMethodTraits(jMethod, STANDARD_OPERATION_NAME, JavaVisibility.PUBLIC,
        	TypesFactory.eINSTANCE.createVoid, false, false, null, jClass)
        assertMethodEquals(operation, jMethod)
    }
    
    @Test
    def void testChangeReturnType() {
        uOp.type = typeClass;
        saveAndSynchronizeChanges(uOp);
        
        val jMethod = getCorrespondingClassMethod(uOp)
        val jTypeClass = getCorrespondingClass(typeClass)
        assertJavaElementHasTypeRef(jMethod, createNamespaceReferenceFromClassifier(jTypeClass))
        assertMethodEquals(uOp, jMethod)
    }
    
    @Test
    def testRenameMethod() {
        uOp.name = OPERATION_RENAME;
        saveAndSynchronizeChanges(uOp);
        
        val jMethod = getCorrespondingClassMethod(uOp)
        val jClass = getCorrespondingClass(uClass)
        assertEquals(OPERATION_RENAME, jMethod.name)
        assertMethodEquals(uOp, jMethod)
        assertJavaMemberContainerDontHaveMember(jClass, OPERATION_NAME)
    }
    
    @Test
    def testDeleteMethod() {
        uOp.destroy;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getCorrespondingClass(uClass)
        assertJavaMemberContainerDontHaveMember(jClass, OPERATION_NAME)
    }
    
    @Test
    def testStaticMethod() {
        uOp.isStatic = true;
        saveAndSynchronizeChanges(uOp);
        
        val jMethod = getCorrespondingClassMethod(uOp)
        assertJavaModifiableStatic(jMethod, true)
        assertMethodEquals(uOp, jMethod)
    }
    
    @Test
    def testAbstractMethod() {
        uOp.isAbstract = true;
        saveAndSynchronizeChanges(uOp);
        
        val jMethod = getCorrespondingClassMethod(uOp)
        assertJavaModifiableAbstract(jMethod, true)
        assertMethodEquals(uOp, jMethod)
    }
    
    @Test
    def testMethodVisibility() {
        uOp.visibility = VisibilityKind.PRIVATE_LITERAL;
        saveAndSynchronizeChanges(uOp);
        
        var jMethod = getCorrespondingClassMethod(uOp)
        assertJavaModifiableHasVisibility(jMethod, JavaVisibility.PRIVATE)
        assertMethodEquals(uOp, jMethod)
        
        uOp.visibility = VisibilityKind.PROTECTED_LITERAL;
        saveAndSynchronizeChanges(uOp);
        
        jMethod = getCorrespondingClassMethod(uOp)
        assertJavaModifiableHasVisibility(jMethod, JavaVisibility.PROTECTED)
        assertMethodEquals(uOp, jMethod)
    }

    
    @Test
    def testCreateParameter() {
        val uParam = createUmlParameter(STANDARD_PARAMETER_NAME, typeClass)
        uOp.ownedParameters += uParam;
        saveAndSynchronizeChanges(uOp);

        val jParam = getCorrespondingParameter(uParam)
        val jTypeClass = getCorrespondingClass(typeClass)
        assertJavaParameterTraits(jParam, STANDARD_PARAMETER_NAME, createNamespaceReferenceFromClassifier(jTypeClass))
        assertParameterEquals(uParam, jParam)
    }
    
    @Test
    def testRenameParameter() {
    	uParam.name = PARAMETER_RENAME
    	saveAndSynchronizeChanges(uParam)
    	
    	val jParam = getCorrespondingParameter(uParam)
    	val jMethod = getCorrespondingClassMethod(uOp)
    	assertEquals(PARAMETER_RENAME, jParam.name)
    	assertJavaMethodHasUniqueParameter(jMethod, PARAMETER_RENAME, TypesFactory.eINSTANCE.createInt)
    	assertJavaMethodDontHaveParameter(jMethod, PARAMETER_NAME)
    }
    
    @Test
    def testDeleteParameter() {
    	uParam.destroy
    	saveAndSynchronizeChanges(rootElement)
    	
    	val jMethod = getCorrespondingClassMethod(uOp)
    	assertJavaMethodDontHaveParameter(jMethod, PARAMETER_NAME)
    }
    
}
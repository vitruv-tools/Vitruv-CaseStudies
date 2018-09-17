package tools.vitruv.applications.umljava.uml2java.tests

import static tools.vitruv.applications.umljava.util.uml.UmlClassifierAndPackageUtil.*
import static tools.vitruv.applications.umljava.util.uml.UmlOperationAndParameterUtil.*
import static extension tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*
import static tools.vitruv.applications.umljava.testutil.JavaTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import tools.vitruv.applications.umljava.uml2java.Uml2JavaTransformationTest
import org.junit.Before
import org.junit.After
import org.junit.Test
import static org.junit.Assert.*;
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.types.TypesFactory
import org.eclipse.uml2.uml.ParameterDirectionKind
import tools.vitruv.applications.umljava.util.UmlJavaTypePropagationHelper

/**
 * This class tests the change of parameter traits.
 * 
 * @author Fei
 */
class UmlToJavaParameterTest extends Uml2JavaTransformationTest {
    private static val CLASS_NAME = "ClassName";
    private static val TYPE_NAME = "TypeName";
    private static val OPERATION_NAME = "classMethod";
    private static val PARAMETER_NAME = "parameterName";
    private static val STANDARD_PARAMETER_NAME = "standardParameterName"
    private static val PARAMETER_RENAME = "parameterRenamed";
    
    private static var org.eclipse.uml2.uml.Class uClass
    private static var org.eclipse.uml2.uml.Class typeClass
    private static var org.eclipse.uml2.uml.Parameter uParam
    private static var org.eclipse.uml2.uml.PrimitiveType pType
    private static var Operation uOperation
    
    
    @Before
    def void before() {
        uClass = createSimpleUmlClass(rootElement, CLASS_NAME);
        typeClass = createSimpleUmlClass(rootElement, TYPE_NAME);
        pType = UmlJavaTypePropagationHelper.getSupoortedPredefinedUmlPrimitiveTypes(resourceSet).findFirst[it.name=="Integer"]
        uParam = createUmlParameter(PARAMETER_NAME, pType)
        uOperation = createUmlOperation(OPERATION_NAME, null, VisibilityKind.PUBLIC_LITERAL, false, false, #[uParam])
        uClass.ownedOperations += uOperation;
        rootElement.packagedElements += uClass;
        rootElement.packagedElements += typeClass;
        saveAndSynchronizeChanges(rootElement);
    }
    
    @After
    def void after() {
//        if (uOperation !== null) {
//            uOperation.destroy;
//        }
//        if (uClass !== null) {
//            uClass.destroy;
//        }
//        if (typeClass !== null) {
//            typeClass.destroy;
//        }
//        if (uParam !== null) {
//            uParam.destroy
//        }
//        if (pType !== null) {
//            pType.destroy
//        }
//        saveAndSynchronizeChanges(rootElement);
    }
    
    @Test
    def testCreateParameter() {
        val uParam = createUmlParameter(STANDARD_PARAMETER_NAME, typeClass)
        uOperation.ownedParameters += uParam;
        println(uParam.direction)
        saveAndSynchronizeChanges(uOperation);

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
        val jMethod = getCorrespondingClassMethod(uOperation)
        assertEquals(PARAMETER_RENAME, jParam.name)
        assertJavaMethodHasUniqueParameter(jMethod, PARAMETER_RENAME, TypesFactory.eINSTANCE.createInt)
        assertJavaMethodDontHaveParameter(jMethod, PARAMETER_NAME)
    }
    
    @Test
    def testDeleteParameter() {
        uParam.destroy
        saveAndSynchronizeChanges(rootElement)
        
        val jMethod = getCorrespondingClassMethod(uOperation)
        assertJavaMethodDontHaveParameter(jMethod, PARAMETER_NAME)
    }
    
    @Test
    def testChangeParameterType() {
        uParam.type = typeClass
        saveAndSynchronizeChanges(uParam)
        
        val jParam = getCorrespondingParameter(uParam)
        val jTypeClass = getCorrespondingClass(typeClass)
        assertJavaParameterTraits(jParam, PARAMETER_NAME, createNamespaceReferenceFromClassifier(jTypeClass))
        assertParameterEquals(uParam, jParam)
    }
    
    @Test
    def testChangeParameterDirectionToReturn() {
        uParam.direction = ParameterDirectionKind.RETURN_LITERAL
        saveAndSynchronizeChanges(uParam)
        assertNull(getCorrespondingParameter(uParam))
        var jMethod = getCorrespondingClassMethod(uOperation)
        assertJavaElementHasTypeRef(jMethod, TypesFactory.eINSTANCE.createInt)
        
    }
}

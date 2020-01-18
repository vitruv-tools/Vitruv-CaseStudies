package tools.vitruv.applications.umljava.uml2java.tests

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.types.TypesFactory
import org.junit.Before
import org.junit.Test
import tools.vitruv.applications.umljava.uml2java.Uml2JavaTransformationTest
import tools.vitruv.applications.umljava.util.UmlJavaTypePropagationHelper

import static org.junit.Assert.*
import static tools.vitruv.applications.umljava.testutil.JavaTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlOperationAndParameterUtil.*

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
    
    private static var Class uClass
    private static var Class typeClass
    private static var Parameter uParam
    private static var PrimitiveType pType
    private static var Operation uOperation
    
    
    @Before
    def void before() {
        uClass = createSimpleUmlClass(rootElement, CLASS_NAME);
        typeClass = createSimpleUmlClass(rootElement, TYPE_NAME);
        pType = UmlJavaTypePropagationHelper.getSupportedPredefinedUmlPrimitiveTypes(resourceRetriever).findFirst[it.name=="Integer"]
        uParam = createUmlParameter(PARAMETER_NAME, pType)
        uOperation = createUmlOperation(OPERATION_NAME, null, VisibilityKind.PUBLIC_LITERAL, false, false, #[uParam])
        uClass.ownedOperations += uOperation;
        rootElement.packagedElements += uClass;
        rootElement.packagedElements += typeClass;
        saveAndSynchronizeChanges(rootElement);
    }
    
    @Test
    def testCreateParameter() {
        val uParam = createUmlParameter(STANDARD_PARAMETER_NAME, typeClass)
        uOperation.ownedParameters += uParam;
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

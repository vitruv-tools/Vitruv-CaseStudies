package tools.vitruv.applications.umljava.uml2java.tests

import static tools.vitruv.applications.umljava.util.UmlUtil.*
import tools.vitruv.applications.umljava.uml2java.AbstractUmlJavaTest
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

class UmlToJavaClassMethodTest extends AbstractUmlJavaTest {
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
        uClass.createOwnedOperation(STANDARD_OPERATION_NAME, null, null, null);
        saveAndSynchronizeChanges(uClass);
        assertUniqueJavaMemberExistsInClass(CLASS_NAME, STANDARD_OPERATION_NAME, ClassMethod);
    }
    
    @Test
    def void testChangeReturnType() {
        uOp.type = typeClass;
        saveAndSynchronizeChanges(uOp);
        
        val jMeth = assertUniqueJavaMemberExistsInClass(CLASS_NAME, OPERATION_NAME, ClassMethod);
        assertJavaMemberHasType(jMeth, typeClass);
    }
    
    @Test
    def testRenameMethod() {
        uOp.name = OPERATION_RENAME;
        saveAndSynchronizeChanges(uOp);
        
        assertUniqueJavaMemberExistsInClass(CLASS_NAME, OPERATION_RENAME, ClassMethod);
        assertJavaMemberNotExistsInClass(CLASS_NAME, OPERATION_NAME);
    }
    
    @Test
    def testDeleteMethod() {
        uOp.destroy;
        saveAndSynchronizeChanges(uClass);
        
        assertJavaMemberNotExistsInClass(CLASS_NAME, OPERATION_NAME);
    }
    
    @Test
    def testStaticMethod() {
        uOp.isStatic = true;
        saveAndSynchronizeChanges(uOp);
        
        val jMeth = assertUniqueJavaMemberExistsInClass(CLASS_NAME, OPERATION_NAME, ClassMethod)
        assertJavaModifiableHasModifier(jMeth, Static)
    }
    
    @Test
    def testAbstractMethod() {
        uOp.isAbstract = true;
        saveAndSynchronizeChanges(uOp);
        
        val jMeth = assertUniqueJavaMemberExistsInClass(CLASS_NAME, OPERATION_NAME, ClassMethod)
        assertJavaModifiableHasModifier(jMeth, Abstract)
    }
    
    @Test
    def testMethodVisibility() {
        uOp.visibility = VisibilityKind.PRIVATE_LITERAL;
        saveAndSynchronizeChanges(uOp);
        
        var jMeth = assertUniqueJavaMemberExistsInClass(CLASS_NAME, OPERATION_NAME, ClassMethod)
        assertJavaModifiableHasVisibility(jMeth, VisibilityKind.PRIVATE_LITERAL)
        
        uOp.visibility = VisibilityKind.PROTECTED_LITERAL;
        saveAndSynchronizeChanges(uOp);
        
        jMeth = assertUniqueJavaMemberExistsInClass(CLASS_NAME, OPERATION_NAME, ClassMethod)
        assertJavaModifiableHasVisibility(jMeth, VisibilityKind.PROTECTED_LITERAL)
    }

    
    @Test
    def testCreateParameter() {
        val uParam = createUmlParameter(STANDARD_PARAMETER_NAME, typeClass)
        uOp.ownedParameters += uParam;
        saveAndSynchronizeChanges(uOp);

        val jMeth = assertUniqueJavaMemberExistsInClass(CLASS_NAME, OPERATION_NAME, ClassMethod)
        assertJavaMethodHasUniqueParameter(jMeth, uParam);
        
    }
    
    @Test
    def testRenameParameter() {
    	uParam.name = PARAMETER_RENAME
    	saveAndSynchronizeChanges(uParam)
    	
    	val jMeth = assertUniqueJavaMemberExistsInClass(CLASS_NAME, OPERATION_NAME, ClassMethod)
        assertJavaMethodHasUniqueParameter(jMeth, uParam);
        assertJavaMethodDontHaveParameter(jMeth, PARAMETER_NAME)
    }
    
    @Test
    def testDeleteParameter() {
    	uParam.destroy
    	saveAndSynchronizeChanges(rootElement)
    	
    	val jMeth = assertUniqueJavaMemberExistsInClass(CLASS_NAME, OPERATION_NAME, ClassMethod)
    	assertJavaMethodDontHaveParameter(jMeth, PARAMETER_NAME)
    }
    
}
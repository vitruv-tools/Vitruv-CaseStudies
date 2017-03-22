package tools.vitruv.applications.umljava.uml2java.tests

import tools.vitruv.applications.umljava.uml2java.AbstractUmlJavaTest
import org.junit.Test
import static org.junit.Assert.*;
import org.junit.Before
import org.junit.After
import org.emftext.language.java.members.InterfaceMethod
import org.emftext.language.java.members.Method
import org.emftext.language.java.members.ClassMethod
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.Static
import org.emftext.language.java.modifiers.Abstract
import org.emftext.language.java.modifiers.Private
import org.emftext.language.java.modifiers.Protected

class UmlToJavaClassMethodTest extends AbstractUmlJavaTest {
    private static val CLASS_NAME = "ClassName";
    private static val TYPE_NAME = "TypeName";
    private static val OPERATION_NAME = "classMethod";
    private static val STANDARD_OPERATION_NAME = "standardMethod";
    private static val OPERATION_RENAME = "classMethodRenamed";
    private static val PARAMETER_NAME = "parameterName";
    private static val PARAMETER_RENAME = "parameterRenamed";
    
    private static var org.eclipse.uml2.uml.Class uClass;
    private static var org.eclipse.uml2.uml.Class typeClass;
    private static var Operation uOp;
    
    @Before
    def void before() {
        uClass = createSyncSimpleUmlClass(CLASS_NAME);
        typeClass = createSyncSimpleUmlClass(TYPE_NAME);
        uOp = createUmlOperation(OPERATION_NAME, null, VisibilityKind.PUBLIC_LITERAL, false, false, null)
        uClass.ownedOperations += uOp;
        rootElement.packagedElements += uClass;
        rootElement.packagedElements += typeClass;
        saveAndSynchronizeChanges(rootElement);
    }
    
    @After
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
        val uParam = createUmlParameter(PARAMETER_NAME, typeClass)
        uOp.ownedParameters += uParam;
        saveAndSynchronizeChanges(uOp);

        val jMeth = assertUniqueJavaMemberExistsInClass(CLASS_NAME, OPERATION_NAME, ClassMethod)
        assertJavaMethodHasUniqueParameter(jMeth, uParam);
        
    }
}
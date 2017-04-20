package tools.vitruv.applications.umljava.java2uml.tests

import tools.vitruv.applications.umljava.java2uml.Java2UmlTransformationTest
import org.junit.Before
import org.emftext.language.java.members.Field
import org.emftext.language.java.types.TypesFactory
import org.junit.Test
import static org.junit.Assert.*
import static extension tools.vitruv.applications.umljava.util.JavaUtil.*
import static tools.vitruv.applications.umljava.util.UmlUtil.*
import static tools.vitruv.applications.umljava.testutil.UmlTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import org.emftext.language.java.modifiers.ModifiersFactory
import tools.vitruv.applications.umljava.util.JavaUtil.JavaVisibility
import org.eclipse.uml2.uml.VisibilityKind
import org.eclipse.uml2.uml.UMLFactory

class JavaToUmlAttributeTest extends Java2UmlTransformationTest {
    private static val ATTRIBUTE_NAME = "attributName"
    private static val ATTRIBUTE_RENAME = "attributeRenamed"
    private static val STANDARD_ATTRIBUTE_NAME = "standardAttributName"
    private static val PRIMITIVE_TYPE = "int"
    private static val CLASS_NAME = "ClassName"
    private static val TYPE_CLASS = "TypeClass"
    
    private static var Field jAttr
    private static var org.emftext.language.java.classifiers.Class jClass
    private static var org.emftext.language.java.classifiers.Class typeClass
    
    @Before
    def void before() {
        jClass = createSimpleJavaClassWithCompilationUnit(CLASS_NAME)
        typeClass = createSimpleJavaClassWithCompilationUnit(TYPE_CLASS)
        jAttr = createJavaAttribute(ATTRIBUTE_NAME, TypesFactory.eINSTANCE.createInt, JavaVisibility.PRIVATE, false, false)
        jClass.members += jAttr
        saveAndSynchronizeChanges(jClass);
    }
    
    //@After
    def void after() {
        if (jClass != null) {

        }
        if (typeClass != null) {

        }
        if (jAttr != null) {

        }
        //saveAndSynchronizeChanges(rootElement);
    }
    @Test
    def testCreatePrimitiveAttribute() {
        val attr = createJavaAttribute(STANDARD_ATTRIBUTE_NAME, TypesFactory.eINSTANCE.createInt, JavaVisibility.PRIVATE, false, false)
        jClass.members += attr
        val getter = createJavaGetterForAttribute(attr, JavaVisibility.PRIVATE)
        jClass.members += getter
        saveAndSynchronizeChanges(jClass)
        
        val uAttr = getCorrespondingAttribute(attr)
        val uClass = getCorrespondingClass(jClass)
        assertUmlPropertyTraits(uAttr, STANDARD_ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL, createUmlPrimitiveType(PRIMITIVE_TYPE),
            false, false, uClass, null, null)
        assertAttributeEquals(uAttr, attr)
    }
    
    @Test
    def testCreateAttribute() {
        val attr = createJavaAttribute(STANDARD_ATTRIBUTE_NAME, createNamespaceReferenceFromClassifier(typeClass), JavaVisibility.PRIVATE, false, false)
        jClass.members += attr
        saveAndSynchronizeChanges(jClass)
        
        val uAttr = getCorrespondingAttribute(attr)
        val uClass = getCorrespondingClass(jClass)
        val uTypeClass = getCorrespondingClass(typeClass)
        assertUmlPropertyTraits(uAttr, STANDARD_ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL, uTypeClass,
            false, false, uClass, null, null)
        assertAttributeEquals(uAttr, attr)
    }
    
    @Test
    def void testRenameAttribute() {
        jAttr.name = ATTRIBUTE_RENAME
        saveAndSynchronizeChanges(jAttr)
        
        val uAttr = getCorrespondingAttribute(jAttr)
        val uClass = getCorrespondingClass(jClass)
        assertEquals(ATTRIBUTE_RENAME, uAttr.name)
        assertUmlClassDontHaveOperation(uClass, ATTRIBUTE_NAME)
        assertAttributeEquals(uAttr, jAttr)
    }
    
    @Test
    def testDeleteAttribute() {
        jClass.members.clear
        jAttr = null;
        saveAndSynchronizeChanges(jClass);

        val uClass = getCorrespondingClass(jClass)
        assertUmlClassDontHaveOperation(uClass, ATTRIBUTE_NAME)
    }

    @Test
    def testStaticAttribute() {
        jAttr.static = true
        saveAndSynchronizeChanges(jAttr);
         
        val uAttr = getCorrespondingAttribute(jAttr)
        assertTrue(uAttr.static)
        assertAttributeEquals(uAttr, jAttr)
    }
    
    @Test
    def testFinalAttribute() {
        jAttr.final = true
        saveAndSynchronizeChanges(jAttr);
         
        val uAttr = getCorrespondingAttribute(jAttr)
        assertTrue(uAttr.readOnly)
        assertAttributeEquals(uAttr, jAttr)
    }
    
    @Test
    def testAttributeVisibility() {
        jAttr.makePublic
        saveAndSynchronizeChanges(jAttr);
         
        var uAttr = getCorrespondingAttribute(jAttr)
        assertUmlNamedElementHasVisibility(uAttr, VisibilityKind.PUBLIC_LITERAL)
        assertAttributeEquals(uAttr, jAttr)
        
        jAttr.makeProtected
        saveAndSynchronizeChanges(jAttr);
         
        uAttr = getCorrespondingAttribute(jAttr)
        assertUmlNamedElementHasVisibility(uAttr, VisibilityKind.PROTECTED_LITERAL)
        assertAttributeEquals(uAttr, jAttr)
    }
}
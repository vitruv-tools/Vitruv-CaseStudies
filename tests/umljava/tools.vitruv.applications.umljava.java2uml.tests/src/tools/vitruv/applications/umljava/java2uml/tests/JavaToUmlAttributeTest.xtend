package tools.vitruv.applications.umljava.java2uml.tests

import tools.vitruv.applications.umljava.java2uml.Java2UmlTransformationTest
import org.junit.Before
import org.emftext.language.java.members.Field
import org.emftext.language.java.types.TypesFactory
import org.junit.Test
import static org.junit.Assert.*
import static tools.vitruv.applications.umljava.util.JavaUtil.*
import org.emftext.language.java.modifiers.ModifiersFactory
import tools.vitruv.applications.umljava.util.JavaUtil.JavaVisibility

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
        saveAndSynchronizeChanges(jClass)
        
        val uAttr = assertNameAndReturnUniqueUmlAttribute(attr)
        assertTrue(uAttr.type instanceof org.eclipse.uml2.uml.PrimitiveType)
        assertEquals(INT, uAttr.type.name)
    }
    
    @Test
    def testCreateAttribute() {
        val attr = createJavaAttribute(STANDARD_ATTRIBUTE_NAME, createNamespaceReferenceFromClassifier(typeClass), JavaVisibility.PRIVATE, false, false)
        jClass.members += attr
        saveAndSynchronizeChanges(jClass)
        
        val uAttr = assertNameAndReturnUniqueUmlAttribute(attr)
        assertTrue(uAttr.type instanceof org.eclipse.uml2.uml.Class)
        assertEquals(typeClass.name, uAttr.type.name)
    }
    
    @Test
    def void testRenameAttribute() {
        jAttr.name = ATTRIBUTE_RENAME
        saveAndSynchronizeChanges(jAttr)
        
        assertNameAndReturnUniqueUmlAttribute(jAttr)
    }
    
    @Test
    def testDeleteAttribute() {
        jClass.members.clear
        jAttr = null;
        
        saveAndSynchronizeChanges(jClass);
        val uClass = getCorrespondingObjectWithClass(jClass, org.eclipse.uml2.uml.Class)
        assertTrue(uClass.ownedAttributes.nullOrEmpty)
    }

    @Test
    def testStaticAttribute() {
        jAttr.addModifier(ModifiersFactory.eINSTANCE.createStatic)
        saveAndSynchronizeChanges(jAttr);
         
        val uAttr = assertNameAndReturnUniqueUmlAttribute(jAttr)
        assertTrue(uAttr.isStatic);
    }
    
    @Test
    def testFinalAttribute() {
        jAttr.addModifier(ModifiersFactory.eINSTANCE.createFinal)
        saveAndSynchronizeChanges(jAttr);
         
        val uAttr = assertNameAndReturnUniqueUmlAttribute(jAttr)
        assertTrue(uAttr.isReadOnly);
    }
    
    @Test
    def testAttributeVisibility() {
        jAttr.makePublic
        saveAndSynchronizeChanges(jAttr);
         
        var uAttr = assertNameAndReturnUniqueUmlAttribute(jAttr)
        assertHasVisibility(uAttr, JavaVisibility.PUBLIC)
        
        jAttr.makeProtected
        saveAndSynchronizeChanges(jAttr);
         
        uAttr = assertNameAndReturnUniqueUmlAttribute(jAttr)
        assertHasVisibility(uAttr, JavaVisibility.PROTECTED)
    }
}
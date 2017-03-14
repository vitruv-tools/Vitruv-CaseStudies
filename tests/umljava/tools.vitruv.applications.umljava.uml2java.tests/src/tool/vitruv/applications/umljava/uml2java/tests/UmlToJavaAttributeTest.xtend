package tool.vitruv.applications.umljava.uml2java.tests

import tool.vitruv.applications.umljava.uml2java.AbstractUmlJavaTest
import org.junit.Test
import static org.junit.Assert.*;
import org.junit.Before
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.VisibilityKind
import org.junit.After
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.Member
import org.eclipse.uml2.uml.DataType
import org.emftext.language.java.modifiers.Static
import org.emftext.language.java.modifiers.Final
import org.emftext.language.java.modifiers.Private
import org.emftext.language.java.modifiers.Protected

class UmlToJavaAttributeTest extends AbstractUmlJavaTest {
    private static val ATTRIBUTE_NAME = "attributName";
    private static val ATTRIBUTE_RENAME = "attributeRenamed";
    private static val STANDARD_ATTRIBUTE_NAME = "standardAttributName";
    private static val PRIMITIVE_TYPE = "int";
    private static val CLASS_NAME = "ClassName";
    private static val TYPE_CLASS = "TypeClass";
    
    private static var Property uAttr;
    private static var org.eclipse.uml2.uml.Class uClass;
    private static var org.eclipse.uml2.uml.Class typeClass;
    
    @Before
    def void before() {
        uClass = createSyncSimpleUmlClass(CLASS_NAME);
        typeClass = createSyncSimpleUmlClass(TYPE_CLASS);
        uAttr = createUmlAttribute(ATTRIBUTE_NAME, null, VisibilityKind.PUBLIC_LITERAL, false, false);
        uClass.ownedAttributes += uAttr;
        rootElement.packagedElements += uClass;
        rootElement.packagedElements += typeClass;
        saveAndSynchronizeChanges(rootElement);
    }
    
    @After
    def void after() {
        if (uClass != null) {
            uClass.destroy;
        }
        if (typeClass != null) {
            typeClass.destroy;
        }
        if (uAttr != null) {
            uAttr.destroy;
        }
    }
    @Test
    def testCreatePrimitiveAttribute() {
        val pType = createUmlPrimitiveType(PRIMITIVE_TYPE);
        val attr = createUmlAttribute(STANDARD_ATTRIBUTE_NAME, pType, VisibilityKind.PUBLIC_LITERAL, false, false);
        uClass.ownedAttributes += attr;
        saveAndSynchronizeChanges(uClass);       
        
        val jAttr = assertUniqueJavaMemberExistsInClass(CLASS_NAME, STANDARD_ATTRIBUTE_NAME, Field);
        assertJavaMemberHasType(jAttr, pType);
    }
    
    @Test
    def testCreateAttribute() {
        uClass.createOwnedAttribute(STANDARD_ATTRIBUTE_NAME, typeClass);
        saveAndSynchronizeChanges(uClass);
           
        val jAttr = assertUniqueJavaMemberExistsInClass(CLASS_NAME, STANDARD_ATTRIBUTE_NAME, Field);
        assertJavaMemberHasType(jAttr, typeClass);
        
    }
    
    @Test
    def testRenameAttribute() {
        uAttr.name = ATTRIBUTE_RENAME;
        saveAndSynchronizeChanges(uClass);
        
        assertUniqueJavaMemberExistsInClass(CLASS_NAME, ATTRIBUTE_RENAME, Field);
        assertJavaMemberNotExistsInClass(CLASS_NAME, ATTRIBUTE_NAME);
    }
    
    @Test
    def testDeleteAttribute() {
        uAttr.destroy;
        saveAndSynchronizeChanges(uClass);
        
        assertJavaMemberNotExistsInClass(CLASS_NAME, ATTRIBUTE_NAME);
    }

    @Test
    def testStaticAttribute() {
        uAttr.isStatic = true;
        saveAndSynchronizeChanges(uClass);
         
        val jAttr = assertUniqueJavaMemberExistsInClass(CLASS_NAME, ATTRIBUTE_NAME, Field);
        assertJavaModifiableHasModifier(jAttr, Static);
    }
    
    @Test
    def testFinalAttribute() {
        uAttr.isReadOnly = true;
        saveAndSynchronizeChanges(uClass);
        
        val jAttr = assertUniqueJavaMemberExistsInClass(CLASS_NAME, ATTRIBUTE_NAME, Field);
        assertJavaModifiableHasModifier(jAttr, Final);
    }
    
    @Test
    def testAttributeVisibilityPrivate() {
        uAttr.visibility = VisibilityKind.PRIVATE_LITERAL;
        saveAndSynchronizeChanges(uClass);
        
        val jAttr = assertUniqueJavaMemberExistsInClass(CLASS_NAME, ATTRIBUTE_NAME, Field);
        assertJavaModifiableHasModifier(jAttr, Private);
    }
    
    @Test
    def testAttributeVisibilityPrrotected() {
        uAttr.visibility = VisibilityKind.PROTECTED_LITERAL;
        saveAndSynchronizeChanges(uClass);
        
        val jAttr = assertUniqueJavaMemberExistsInClass(CLASS_NAME, ATTRIBUTE_NAME, Field);
        assertJavaModifiableHasModifier(jAttr, Protected);
    }
    

}
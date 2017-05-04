package tools.vitruv.applications.umljava.uml2java.tests

import org.junit.Test
import tools.vitruv.applications.umljava.uml2java.Uml2JavaTransformationTest
import static org.junit.Assert.*
import static tools.vitruv.applications.umljava.util.uml.UmlClassifierAndPackageUtil.*
import static tools.vitruv.applications.umljava.util.uml.UmlPropertyAndAssociationUtil.*
import static extension tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*
import static extension tools.vitruv.applications.umljava.util.java.JavaContainerAndClassifierUtil.*
import static extension tools.vitruv.applications.umljava.util.java.JavaMemberAndParameterUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import static tools.vitruv.applications.umljava.testutil.JavaTestUtil.*
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.junit.Before

class UmlToJavaAssociationTest extends Uml2JavaTransformationTest {
    
    private static val CLASSNAME_1 = "ClassName1"
    private static val CLASSNAME_2 = "ClassName2"
    
    private static var org.eclipse.uml2.uml.Class uClass1
    private static var org.eclipse.uml2.uml.Class uClass2
    
    @Before
    def void before() {
        uClass1 = createSimpleUmlClass(rootElement, CLASSNAME_1)
        uClass2 = createSimpleUmlClass(rootElement, CLASSNAME_2)
        saveAndSynchronizeChanges(rootElement)
    }
    
    @Test
    def void testCreateAssociation1() {
        this.userInteractor.addNextSelections(0)
        createDirectedAssociation(uClass1, uClass2, 0, 1)
        saveAndSynchronizeChanges(rootElement)
        
        val uAttribute = uClass1.ownedAttributes.head
        val jClass1 = getCorrespondingClass(uClass1)
        val jAttribute = getCorrespondingAttribute(uAttribute)
        val jClass2 = getCorrespondingClass(uClass2)
        assertJavaElementHasTypeRef(jAttribute, createNamespaceReferenceFromClassifier(jClass2))
        assertClassEquals(uClass1, jClass1)
        assertAttributeEquals(uAttribute, jAttribute)
    }
	
	@Test
	def void testCreateAssociation2() {
		this.userInteractor.addNextSelections(0)
		createDirectedAssociation(uClass1, uClass2, 1, 1)
		saveAndSynchronizeChanges(rootElement)
		
		val uAttribute = uClass1.ownedAttributes.head
		val jClass1 = getCorrespondingClass(uClass1)
		val jAttribute = getCorrespondingAttribute(uAttribute)
		val jClass2 = getCorrespondingClass(uClass2)
		val jConstructor = jClass1.firstJavaConstructor
		assertJavaElementHasTypeRef(jAttribute, createNamespaceReferenceFromClassifier(jClass2))
		assertClassEquals(uClass1, jClass1)
		assertNotNull(jConstructor)
		assertTrue(constructorContainsAttributeSelfReferenceStatement(jConstructor, jAttribute))
		assertTrue(javaGetterForAttributeExists(jAttribute))
        assertTrue(javaSetterForAttributeExists(jAttribute))
	}
	
	@Test
	def void testCreateAssociation3() {
	    this.userInteractor.addNextSelections(0) //0 is ArrayList
        createDirectedAssociation(uClass1, uClass2, 0, LiteralUnlimitedNatural.UNLIMITED)
        saveAndSynchronizeChanges(rootElement)
        
        val uAttribute = uClass1.ownedAttributes.head
        
        val jAttribute = getCorrespondingAttribute(uAttribute)
        val jClass2 = getCorrespondingClass(uClass2)
        val arrayListReference = getClassifierFromTypeReference(jAttribute.typeReference)
        assertEquals("ArrayList", arrayListReference.name)
        val innerTypeRef = getInnerTypeReferenceOfCollectionTypeReference(jAttribute.typeReference)
	    assertTypeEquals(createNamespaceReferenceFromClassifier(jClass2), innerTypeRef)
	}

}
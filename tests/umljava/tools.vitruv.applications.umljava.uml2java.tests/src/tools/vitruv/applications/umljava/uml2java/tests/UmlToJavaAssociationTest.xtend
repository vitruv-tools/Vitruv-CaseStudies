package tools.vitruv.applications.umljava.uml2java.tests

import org.junit.Test
import tools.vitruv.applications.umljava.uml2java.AbstractUmlJavaTest
import tools.vitruv.framework.util.bridges.EcoreResourceBridge

import static tools.vitruv.applications.umljava.util.UmlUtil.*
import org.eclipse.uml2.uml.LiteralUnlimitedNatural

class UmlToJavaAssociationTest extends AbstractUmlJavaTest {
	
	@Test
	def void testCreateAssociation() {
		val c1 = createSimpleUmlClass(rootElement, "Klasse1") 
		val c2 = createSimpleUmlClass(rootElement, "Klasse2")
		val attr = c2.createOwnedAttribute("atttrrr", c1)
		attr.lower = 1
		attr.upper = LiteralUnlimitedNatural.UNLIMITED
		
		println(attr.lower)
		println(attr.lowerBound)
		println(attr.lowerValue.integerValue)
		println(attr.lowerValue.class)
		println(attr.lowerValue.eClass)
		println(attr.upper)
		println(attr.upperBound)
		//println(attr.upperValue.integerValue)
		println(attr.upperValue.unlimitedValue)
		println(attr.upperValue.class)
		println(attr.upperValue.eClass)
		//EcoreResourceBridge.saveResource(rootElement.eResource());
		saveAndSynchronizeChanges(rootElement)
	}
}
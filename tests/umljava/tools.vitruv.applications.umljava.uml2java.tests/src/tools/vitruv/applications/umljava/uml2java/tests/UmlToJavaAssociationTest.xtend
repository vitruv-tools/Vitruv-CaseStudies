package tools.vitruv.applications.umljava.uml2java.tests

import org.junit.Test
import tools.vitruv.applications.umljava.uml2java.AbstractUmlJavaTest
import tools.vitruv.framework.util.bridges.EcoreResourceBridge

import static tools.vitruv.applications.umljava.util.UmlUtil.*
import org.eclipse.uml2.uml.LiteralUnlimitedNatural

class UmlToJavaAssociationTest extends AbstractUmlJavaTest {
	
	@Test
	def void testCreateAssociation() {
		this.userInteractor.addNextSelections(0,2)
		val c1 = createSimpleUmlClass(rootElement, "Klasse1") 
		val c2 = createSimpleUmlClass(rootElement, "Klasse2")
		//val attr = c2.createOwnedAttribute("atttrrr", c1)
		
		createDirectedAssociation(c1, c2, 0, LiteralUnlimitedNatural.UNLIMITED)
		saveAndSynchronizeChanges(rootElement)
		createDirectedAssociation(c2, c1, 0, 1)
		saveAndSynchronizeChanges(rootElement)
		
	}
}
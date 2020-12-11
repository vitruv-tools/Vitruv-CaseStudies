package tools.vitruv.applications.umlclassumlcomponents.tests.comp2class

import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.VisibilityKind

import static tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertTrue

class VisibilityTest extends AbstractComp2ClassTest{
			    	
   	/*******
	*Tests:*
	********/
	
	private def void assertVisibility(NamedElement element) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[element]).flatten
		for (EObject corrElement : correspondingElements) {
			assertTrue((corrElement as NamedElement).visibility == element.visibility)
		} 
		
	}
	
	@Test
	def void testVisibilityChange() {
		val umlComp = createComponent(COMP_NAME)
		umlComp.visibility = VisibilityKind.PRIVATE_LITERAL
		saveAndSynchronizeChanges(umlComp)		
		assertVisibility(umlComp)
		
		umlComp.visibility = VisibilityKind.PUBLIC_LITERAL
		saveAndSynchronizeChanges(umlComp)		
		assertVisibility(umlComp)
	}			
	
}
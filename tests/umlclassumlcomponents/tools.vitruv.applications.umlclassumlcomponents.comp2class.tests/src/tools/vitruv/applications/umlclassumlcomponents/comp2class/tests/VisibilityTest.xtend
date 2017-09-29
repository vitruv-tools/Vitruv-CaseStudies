package tools.vitruv.applications.umlclassumlcomponents.comp2class.tests

import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.VisibilityKind
import org.junit.Test

import static org.junit.Assert.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedTestUtil.*

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
	public def void testVisibilityChange() {
		val umlComp = createComponent(COMP_NAME)
		umlComp.visibility = VisibilityKind.PRIVATE_LITERAL
		saveAndSynchronizeChanges(umlComp)		
		assertVisibility(umlComp)
		
		umlComp.visibility = VisibilityKind.PUBLIC_LITERAL
		saveAndSynchronizeChanges(umlComp)		
		assertVisibility(umlComp)
	}			
	
}
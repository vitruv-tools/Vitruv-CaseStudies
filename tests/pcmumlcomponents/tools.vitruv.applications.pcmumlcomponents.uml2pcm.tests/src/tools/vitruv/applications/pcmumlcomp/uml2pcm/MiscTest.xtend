package tools.vitruv.applications.pcmumlcomp.uml2pcm

import org.junit.Test
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.ocl.ecore.UnlimitedNaturalLiteralExp

class MiscTest extends AbstractUmlPcmTest {
	
	@Test
	public def void fooTest() {
		val types = importPrimitiveTypes()
		val parameterType = types.ownedTypes.get(2)
		val parameterType2 = UMLFactory.eINSTANCE.createPrimitiveType()
		parameterType2.name = "Timestamp"
		userInteractor.addNextSelections(1)
		rootElement.packagedElements += parameterType2
		val t = UMLFactory.eINSTANCE.createDataType()
		t.name = "TestComplexType"
		t.createOwnedAttribute("foo", parameterType)
		t.createOwnedAttribute("bar", parameterType, 0, UnlimitedNaturalLiteralExp.UNBOUNDED_MULTIPLICITY)
		t.createOwnedAttribute("baz", parameterType2, 0, UnlimitedNaturalLiteralExp.UNBOUNDED_MULTIPLICITY)
		rootElement.packagedElements += t
		saveAndSynchronizeChanges(rootElement)
		//t.ownedAttributes.head.upper = UnlimitedNaturalLiteralExp.UNBOUNDED_MULTIPLICITY
		//t.ownedAttributes.head.lower = 0
		//t.ownedAttributes.head.type = types.ownedTypes.get(0)
		//saveAndSynchronizeChanges(t)
	}
	
	@Test
	public def void barTest() {
		val types = importPrimitiveTypes()
		val i = UMLFactory.eINSTANCE.createInterface()
		i.name = "TestyMcInterface"
		//i.createOwnedAttribute("foo", types.ownedTypes.get(0))
		//i.createOwnedAttribute("bar", types.ownedTypes.get(1), 0, UnlimitedNaturalLiteralExp.UNBOUNDED_MULTIPLICITY)
		i.createOwnedOperation("foo", null, null, types.ownedTypes.get(0))
		i.createOwnedOperation("bar", null, null, types.ownedTypes.get(1))
		rootElement.packagedElements += i
		saveAndSynchronizeChanges(rootElement)
	}
	
}

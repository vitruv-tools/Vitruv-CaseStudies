package tool.vitruv.applications.pcmumlcomp.uml2pcm

import org.eclipse.emf.common.util.BasicEList
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.UMLFactory
import org.junit.Test
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationSignature

import static org.junit.Assert.*

class InterfacesTest extends AbstractUmlPcmTest {
	
	protected def Interface createUmlInterface(String name) {
		val umlInterface = UMLFactory.eINSTANCE.createInterface();
		umlInterface.name = name
		rootElement.packagedElements += umlInterface
		saveAndSynchronizeChanges(rootElement);
		return umlInterface
	}
	
	@Test
	public def void createInterfaceTest() {
		val interfaceName = "i1"
		val umlInterface = createUmlInterface(interfaceName)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlInterface]).flatten;
		assertEquals(1, correspondingElements.length);
		val pcmInterface = (correspondingElements.get(0) as OperationInterface)
		assertTrue(pcmInterface instanceof OperationInterface)
		assertEquals(interfaceName, pcmInterface.entityName)
	}
	
	@Test
	public def void createIntefaceSignature() {
		val umlInterface = createUmlInterface("i2")
		val p1Type = UMLFactory.eINSTANCE.createPrimitiveType()
		p1Type.name = "t1";
		rootElement.ownedTypes += p1Type
		rootElement.packagedElements += p1Type
		val parameters = new BasicEList<String>()
		parameters += "p1"
		val parameterTypes = new BasicEList<Type>()
		parameterTypes += p1Type
		val umlOperation = umlInterface.createOwnedOperation("o1", parameters, parameterTypes)
		saveAndSynchronizeChanges(rootElement)
		saveAndSynchronizeChanges(umlInterface)
		saveAndSynchronizeChanges(umlOperation)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlOperation]).flatten;
		assertEquals(1, correspondingElements.length);
		val pcmOperation = (correspondingElements.get(0) as OperationSignature)
		assertTrue(pcmOperation instanceof OperationSignature)
	}
	
}
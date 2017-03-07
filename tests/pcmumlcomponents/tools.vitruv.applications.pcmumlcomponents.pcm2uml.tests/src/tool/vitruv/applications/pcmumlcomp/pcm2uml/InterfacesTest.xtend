package tool.vitruv.applications.pcmumlcomp.pcm2uml

import org.junit.Test
import static org.junit.Assert.*
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.repository.OperationInterface
import org.eclipse.uml2.uml.Interface

class InterfacesTest extends AbstractPcmUmlTest {
	
	protected def OperationInterface createOperationInterface(String name) {
		val pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface()
		pcmInterface.entityName = name
		rootElement.interfaces__Repository += pcmInterface
		saveAndSynchronizeChanges(rootElement)
		return pcmInterface
	}
	
	@Test
	public def void testInterfaceCreation() {
		val interfaceName = "i1"
		val pcmInterface = createOperationInterface(interfaceName)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmInterface]).flatten
		assertEquals(1, correspondingElements.length)
		assertTrue(correspondingElements.get(0) instanceof Interface)
		val umlInterface = (correspondingElements.get(0) as Interface)
		assertEquals(interfaceName, umlInterface.name)
	}
	
}
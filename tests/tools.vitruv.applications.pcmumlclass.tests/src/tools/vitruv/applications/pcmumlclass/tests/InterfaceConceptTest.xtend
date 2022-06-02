package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.uml2.uml.Interface
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import static extension tools.vitruv.applications.pcmumlclass.tests.PcmQueryUtil.*
import static extension tools.vitruv.applications.pcmumlclass.tests.UmlQueryUtil.*

/**
 *  This test class tests the reactions and routines are supposed to synchronize a pcm::OperationInterface
 * with its corresponding uml::Interface (in the contracts uml::Package corresponding to a pcm::Repository).
 * <br><br>
 * Related files: PcmInterface.reactions, UmlInterface.reactions, UmlInterfaceGeneralization.reactions
 */
class InterfaceConceptTest extends NewPcmUmlClassApplicationTest {

	static val TEST_INTERFACE_NAME = "TestInterface"
	static val PACKAGE_NAME = "rootpackage"
	static val PACKAGE_NAME_FIRST_UPPER = "Rootpackage"
	static val PACKAGE_RENAMED = "rootpackagerenamed"

	def checkInterfaceConcept(
		OperationInterface pcmInterface,
		Interface umlInterface
	) {
		assertNotNull(pcmInterface)
		assertNotNull(umlInterface)
		assertTrue(pcmInterface.entityName == umlInterface.name)
		// should be contained in corresponding repository and contracts package respectively

		// parent interfaces should correspond
	}

	def void init_PCM() {
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		createPcmRepository(PACKAGE_NAME_FIRST_UPPER)
	}
	
	def void init_UML() {
		userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY)
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		createUmlRootPackage(PACKAGE_NAME)
	}
	
	@Test
	def void testCreateInterfaceConcept_UML() {
		/* 
		init_PCM()
		
		changeUmlView [
			println("uml root contents " + defaultUmlModel.eContents)
		]
		*/
	}

	@Test
	def void testCreateInterfaceConcept_PCM() {
		
	}
	
}

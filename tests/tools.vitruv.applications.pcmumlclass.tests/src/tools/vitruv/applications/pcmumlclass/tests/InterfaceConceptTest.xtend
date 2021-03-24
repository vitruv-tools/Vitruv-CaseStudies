package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Interface
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.TagLiterals
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import java.nio.file.Path

/**
 *  This test class tests the reactions and routines are supposed to synchronize a pcm::OperationInterface
 * with its corresponding uml::Interface (in the contracts uml::Package corresponding to a pcm::Repository).
 * <br><br>
 * Related files: PcmInterface.reactions, UmlInterface.reactions, UmlInterfaceGeneralization.reactions
 */
class InterfaceConceptTest extends PcmUmlClassApplicationTest {

	static val TEST_INTERFACE_NAME = "TestInterface"

	def checkInterfaceConcept(
		OperationInterface pcmInterface,
		Interface umlInterface
	) {
		assertNotNull(pcmInterface)
		assertNotNull(umlInterface)
		assertTrue(corresponds(pcmInterface, umlInterface, TagLiterals.INTERFACE_TO_INTERFACE))
		assertTrue(pcmInterface.entityName == umlInterface.name)
		// should be contained in corresponding repository and contracts package respectively
		assertTrue(
			corresponds(pcmInterface.repository__Interface, umlInterface.package,
				TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE))
		// parent interfaces should correspond
		val umlParentCorrespondences = pcmInterface.parentInterfaces__Interface.map [ pcmParent |
			getCorrespondingEObjects(pcmParent, Interface).head
		].toList
		assertFalse(umlParentCorrespondences.contains(null))
		assertFalse(
			umlParentCorrespondences.map [ umlParent |
				umlInterface.generalizations.exists[gen|EcoreUtil.equals(gen.general, umlParent)]
			].exists[it == false]
		)
	}

	def protected checkInterfaceConcept(OperationInterface pcmInterface) {
		val umlInterface = helper.getModifiableCorr(pcmInterface, Interface, TagLiterals.INTERFACE_TO_INTERFACE)
		checkInterfaceConcept(pcmInterface, umlInterface)
	}

	def protected checkInterfaceConcept(Interface umlInterface) {
		val pcmInterface = helper.getModifiableCorr(umlInterface, OperationInterface,
			TagLiterals.INTERFACE_TO_INTERFACE)
		checkInterfaceConcept(pcmInterface, umlInterface)
	}

	def private Repository createRepositoryConcept() {
		val pcmRepository = helper.createRepository

		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		resourceAt(Path.of(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)).startRecordingChanges => [
			contents += pcmRepository
		]
		propagate

		assertModelExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		return pcmRepository.clearResourcesAndReloadRoot
	}

	@Test
	def void testCreateInterfaceConcept_UML() {
		var pcmRepository = createRepositoryConcept()
		var umlContractsPkg = helper.getUmlContractsPackage(pcmRepository)
		startRecordingChanges(umlContractsPkg)

		var mUmlInterface = umlContractsPkg.createOwnedInterface(TEST_INTERFACE_NAME)
		propagate

		umlContractsPkg.clearResourcesAndReloadRoot
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot
		umlContractsPkg = helper.getUmlContractsPackage(pcmRepository)

		mUmlInterface = umlContractsPkg.packagedElements.head as Interface
		assertNotNull(mUmlInterface)
		checkInterfaceConcept(mUmlInterface)
	}

	@Test
	def void testCreateInterfaceConcept_PCM() {
		var pcmRepository = createRepositoryConcept()
		var umlContractsPkg = helper.getUmlContractsPackage(pcmRepository)
		startRecordingChanges(umlContractsPkg)

		var mPcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface
		mPcmInterface.entityName = TEST_INTERFACE_NAME
		pcmRepository.interfaces__Repository += mPcmInterface
		propagate

		umlContractsPkg.clearResourcesAndReloadRoot
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot
		umlContractsPkg = helper.getUmlContractsPackage(pcmRepository)

		mPcmInterface = pcmRepository.interfaces__Repository.head as OperationInterface
		assertNotNull(mPcmInterface)
		checkInterfaceConcept(mPcmInterface)
	}
}

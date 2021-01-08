package tools.vitruv.applications.pcmumlclass.mapping.tests.cases

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Interface
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals
import static tools.vitruv.applications.pcmumlclass.mapping.DefaultLiterals.*
import tools.vitruv.applications.pcmumlclass.mapping.tests.PcmUmlClassTest
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import java.nio.file.Path

class InterfaceTest extends PcmUmlClassTest {
	static val TEST_INTERFACE_NAME = "TestInterface"

	def static checkInterfaceConcept(
		CorrespondenceModel cm,
		OperationInterface pcmInterface,
		Interface umlInterface
	) {
		assertNotNull(pcmInterface)
		assertNotNull(umlInterface)
		assertTrue(corresponds(cm, pcmInterface, umlInterface, TagLiterals.INTERFACE_TO_INTERFACE))
		assertTrue(pcmInterface.entityName == umlInterface.name)
		// should be contained in corresponding repository and contracts package respectively
		assertTrue(
			corresponds(cm, pcmInterface.repository__Interface, umlInterface.package,
				TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE))
		// parent interfaces should correspond
		val umlParentCorrespondences = pcmInterface.parentInterfaces__Interface.map [ pcmParent |
			CorrespondenceModelUtil.getCorrespondingEObjectsByType(cm, pcmParent, Interface).head
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
		checkInterfaceConcept(correspondenceModel, pcmInterface, umlInterface)
	}

	def protected checkInterfaceConcept(Interface umlInterface) {
		val pcmInterface = helper.getModifiableCorr(umlInterface, OperationInterface,
			TagLiterals.INTERFACE_TO_INTERFACE)
		checkInterfaceConcept(correspondenceModel, pcmInterface, umlInterface)
	}

	def private Repository createRepositoryConcept() {
		val pcmRepository = helper.createRepository

		userInteraction.addNextTextInput(UML_MODEL_FILE)
		resourceAt(Path.of(PCM_MODEL_FILE)).startRecordingChanges => [
			contents += pcmRepository
		]
		propagate

		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

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

		var mPcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface
		mPcmInterface.entityName = TEST_INTERFACE_NAME
		pcmRepository.interfaces__Repository += mPcmInterface
		propagate

		pcmRepository = pcmRepository.clearResourcesAndReloadRoot
		mPcmInterface = pcmRepository.interfaces__Repository.head as OperationInterface
		assertNotNull(mPcmInterface)
		checkInterfaceConcept(mPcmInterface)
	}
}

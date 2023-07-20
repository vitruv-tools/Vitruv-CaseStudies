package tools.vitruv.applications.pcmumlclass.tests

import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMCompositeComponentBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMOperationInterfaceBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMRepositoryBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLClassBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLInterfaceBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLPackageBuilder
import tools.vitruv.applications.testutility.uml.UmlQueryUtil

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::OperationProvidedRole 
 * in an pcm::InterfaceProvidingRequiringEntity (IPRE) with an uml::InterfaceRealization in the uml::Class (implementation) corresponding to the IPRE.
 * <br><br>
 * Related files: PcmProvidedRole.reactions, UmlProvidedRoleGeneralization.reactions
 */
class ProvidedRoleTest extends PcmUmlClassApplicationTest {

	static val PROVIDED_ROLE_NAME = "testProvidedRole"

	def private void createRepositoryComponentInterface() {
		initPCMRepository()

		changePcmView[
			PcmUmlClassApplicationTestHelper.createComponent(defaultPcmRepository)
			PcmUmlClassApplicationTestHelper.createOperationInterface(defaultPcmRepository)
		]
	}

	@Test
	def void testProvidedRoleConcept_PCM() {
		createRepositoryComponentInterface()

		changePcmView[
			var pcmProvided = RepositoryFactory.eINSTANCE.createOperationProvidedRole
			pcmProvided.entityName = PROVIDED_ROLE_NAME
			pcmProvided.providedInterface__OperationProvidedRole = PcmUmlClassApplicationTestHelper.
				getPcmOperationInterface(defaultPcmRepository)
			PcmUmlClassApplicationTestHelper.getPcmComponent(defaultPcmRepository).
				providedRoles_InterfaceProvidingEntity += pcmProvided
		]

		validateUmlView[
			val expectedInterface = new FluentUMLInterfaceBuilder(PcmUmlClassApplicationTestHelper.INTERFACE_NAME).build
			val expectedContractsPackage = new FluentUMLPackageBuilder(CONTRACTS_PACKAGE).
				addPackagedElement(expectedInterface).build

			val expectedComponentClass = new FluentUMLClassBuilder(PcmUmlClassApplicationTestHelper.COMPONENT_NAME_UC +
				PcmUmlClassApplicationTestHelper.IMPL_SUFFIX, true).addDefaultConstructor.
				addInterfaceRealization(PROVIDED_ROLE_NAME, expectedInterface).build
			val expectedComponentsPackage = new FluentUMLPackageBuilder(
				PcmUmlClassApplicationTestHelper.COMPONENT_NAME_LC).addPackagedElement(expectedComponentClass).build

			assertEqualityAndContainmentOfUmlPackage(defaultUmlModel, String.join(".", PACKAGE_NAME, CONTRACTS_PACKAGE),
				expectedContractsPackage)
			assertEqualityAndContainmentOfUmlPackage(defaultUmlModel,
				String.join(".", PACKAGE_NAME, PcmUmlClassApplicationTestHelper.COMPONENT_NAME_LC),
				expectedComponentsPackage)
		]
	}

	@Test
	def void testProvidedRoleConcept_UML() {
		createRepositoryComponentInterface()

		changeUmlView[
			val testComponent = PcmUmlClassApplicationTestHelper.claimClass(defaultUmlModel,
				PcmUmlClassApplicationTestHelper.COMPONENT_NAME_UC + PcmUmlClassApplicationTestHelper.IMPL_SUFFIX)
			val testInterface = UmlQueryUtil.claimInterface(umlContractsPackage,
				PcmUmlClassApplicationTestHelper.INTERFACE_NAME)
			testComponent.createInterfaceRealization(PROVIDED_ROLE_NAME, testInterface)
		]

		validatePcmView[
			val pcmInterface = new FluentPCMOperationInterfaceBuilder(PcmUmlClassApplicationTestHelper.INTERFACE_NAME).
				build
			val pcmComponent = new FluentPCMCompositeComponentBuilder(
				PcmUmlClassApplicationTestHelper.COMPONENT_NAME_UC).addProvidedRole(PROVIDED_ROLE_NAME, pcmInterface).
				build
			val expectedRepository = new FluentPCMRepositoryBuilder(PACKAGE_NAME_FIRST_UPPER).addComponent(
				pcmComponent).addInterface(pcmInterface).build

			assertEqualityOfPcmRepository(defaultPcmRepository, expectedRepository)
		]
	}

}

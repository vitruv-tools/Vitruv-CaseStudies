package tools.vitruv.applications.pcmumlclass.tests

import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMCompositeComponentBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMOperationInterfaceBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMRepositoryBuilder
import tools.vitruv.applications.testutility.uml.UmlQueryUtil
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLInterfaceBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLPackageBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLClassBuilder

import edu.kit.ipd.sdq.commons.util.java.Pair;

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::OperationRequiredRole 
 * of a pcm::InterfaceProvidingRequiringEntity (IPRE) with its corresponding uml::Parameter (constructor parameter 
 * of the IPRE implementation class) and uml::Property (field in the IPRE implementation class used to store the 
 * Component passed to the constructor).
 * <br><br>
 * Related files: PcmRequiredRole.reactions, UmlRequiredRoleParameter.reactions, UmlRequiredRoleProperty.reactions
 */
class RequiredRoleConceptTest extends PcmUmlClassApplicationTest {
	val REQUIRED_ROLE_NAME = "testRequiredRole"

	def private void createRepository_Component_Interface() {
		initPCMRepository

		changePcmView[
			PcmUmlClassApplicationTestHelper.createComponent(defaultPcmRepository)
			PcmUmlClassApplicationTestHelper.createOperationInterface(defaultPcmRepository)
		]
	}

	@Test
	def void testRequiredRoleConcept_PCM() {
		createRepository_Component_Interface()

		changePcmView[
			var pcmRequiredRole = RepositoryFactory.eINSTANCE.createOperationRequiredRole
			pcmRequiredRole.requiredInterface__OperationRequiredRole = PcmUmlClassApplicationTestHelper.
				getPcmOperationInterface(defaultPcmRepository)
			PcmUmlClassApplicationTestHelper.getPcmComponent(defaultPcmRepository).
				requiredRoles_InterfaceRequiringEntity += pcmRequiredRole
		]

		validateUmlView[
			val expectedInterface = new FluentUMLInterfaceBuilder(PcmUmlClassApplicationTestHelper.INTERFACE_NAME).build
			val expectedContractsPackage = new FluentUMLPackageBuilder(CONTRACTS_PACKAGE).
				addPackagedElement(expectedInterface).build

			val expectedComponentClass = new FluentUMLClassBuilder(PcmUmlClassApplicationTestHelper.COMPONENT_NAME_UC +
				PcmUmlClassApplicationTestHelper.IMPL_SUFFIX, true).addParameterizedConstructor(
				#[new Pair("aName", expectedInterface)]).addAttribute("aName", expectedInterface).build
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
	def void testRequiredRoleConcept_UML_RequiredConstructorParameter() {
		createRepository_Component_Interface()

		changeUmlView[
			val umlInterface = UmlQueryUtil.claimInterface(umlContractsPackage,
				PcmUmlClassApplicationTestHelper.INTERFACE_NAME)
			val umlConstructor = PcmUmlClassApplicationTestHelper.claimClass(defaultUmlModel,
				PcmUmlClassApplicationTestHelper.COMPONENT_NAME_UC + PcmUmlClassApplicationTestHelper.IMPL_SUFFIX).
				ownedOperations.findFirst [
					it.name == PcmUmlClassApplicationTestHelper.COMPONENT_NAME_UC +
						PcmUmlClassApplicationTestHelper.IMPL_SUFFIX
				]

			umlConstructor.createOwnedParameter(REQUIRED_ROLE_NAME, umlInterface)
		]

		validatePcmView[
			val pcmInterface = new FluentPCMOperationInterfaceBuilder(PcmUmlClassApplicationTestHelper.INTERFACE_NAME).
				build
			val pcmComponent = new FluentPCMCompositeComponentBuilder(
				PcmUmlClassApplicationTestHelper.COMPONENT_NAME_UC).addRequiredRole(REQUIRED_ROLE_NAME, pcmInterface).
				build
			val expectedPcmRepository = new FluentPCMRepositoryBuilder(PACKAGE_NAME_FIRST_UPPER).addComponent(
				pcmComponent).addInterface(pcmInterface).build

			assertEqualityOfPcmRepository(defaultPcmRepository, expectedPcmRepository)
		]
	}

	@Test
	def void testRequiredRoleConcept_UML_RequiredInstanceField() {
		createRepository_Component_Interface()

		changeUmlView[
			val umlInterface = UmlQueryUtil.claimInterface(umlContractsPackage,
				PcmUmlClassApplicationTestHelper.INTERFACE_NAME)
			val umlComponentImpl = PcmUmlClassApplicationTestHelper.claimClass(defaultUmlModel,
				PcmUmlClassApplicationTestHelper.COMPONENT_NAME_UC + PcmUmlClassApplicationTestHelper.IMPL_SUFFIX)

			umlComponentImpl.createOwnedAttribute(REQUIRED_ROLE_NAME, umlInterface)
		]

		validatePcmView[
			val pcmInterface = new FluentPCMOperationInterfaceBuilder(PcmUmlClassApplicationTestHelper.INTERFACE_NAME).
				build
			val pcmComponent = new FluentPCMCompositeComponentBuilder(
				PcmUmlClassApplicationTestHelper.COMPONENT_NAME_UC).addRequiredRole(REQUIRED_ROLE_NAME, pcmInterface).
				build
			val expectedPcmRepository = new FluentPCMRepositoryBuilder(PACKAGE_NAME_FIRST_UPPER).addComponent(
				pcmComponent).addInterface(pcmInterface).build

			assertEqualityOfPcmRepository(defaultPcmRepository, expectedPcmRepository)
		]
	}
}

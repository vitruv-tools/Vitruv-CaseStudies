package tools.vitruv.applications.transitivechange.tests.linear.pcmumlclassjava

import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.Property
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.applications.pcmumlclass.tests.PcmUmlClassApplicationTestHelper

import static org.junit.jupiter.api.Assertions.*
import java.nio.file.Path

/**
 * This class is based on the correlating PCM/UML test class. It is extended to include Java in the network.
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::OperationRequiredRole 
 * of a pcm::InterfaceProvidingRequiringEntity (IPRE) with its corresponding uml::Parameter (constructor parameter 
 * of the IPRE implementation class) and uml::Property (field in the IPRE implementation class used to store the 
 * Component passed to the constructor).
 * <br><br>
 * Related files: PcmRequiredRole.reactions, UmlRequiredRoleParameter.reactions, UmlRequiredRoleProperty.reactions
 */
class RequiredRoleConceptTest extends PcmUmlJavaLinearTransitiveChangeTest {

	val REQUIRED_ROLE_NAME = "testRequiredRole"

	def void checkRequiredRoleConcept(
		OperationRequiredRole pcmRequired,
		Property umlRequiredInstance,
		Parameter umlRequiredParameter
	) {
		assertNotNull(pcmRequired)
		assertNotNull(umlRequiredInstance)
		assertNotNull(umlRequiredParameter)
		assertTrue(corresponds(pcmRequired, umlRequiredInstance, TagLiterals.REQUIRED_ROLE__PROPERTY))
		assertTrue(corresponds(pcmRequired, umlRequiredParameter, TagLiterals.REQUIRED_ROLE__PARAMETER))
		// the respective type references have to correspond
		assertTrue(corresponds(pcmRequired.requiredInterface__OperationRequiredRole, umlRequiredInstance.type))
		assertTrue(corresponds(pcmRequired.requiredInterface__OperationRequiredRole, umlRequiredParameter.type))
		// the owning component and component implementation have to correspond
		assertTrue(
			corresponds(pcmRequired.requiringEntity_RequiredRole, umlRequiredInstance.class_,
				TagLiterals.IPRE__IMPLEMENTATION))
		assertTrue(
			corresponds(pcmRequired.requiringEntity_RequiredRole, umlRequiredParameter.operation?.class_,
				TagLiterals.IPRE__IMPLEMENTATION))
		assertTrue(pcmRequired.entityName == umlRequiredInstance.name)
		assertTrue(pcmRequired.entityName == umlRequiredParameter.name)
	}

	def protected checkRequiredRoleConcept(OperationRequiredRole pcmRequired) {
		val umlRequiredInstance = helper.getModifiableCorr(pcmRequired, Property, TagLiterals.REQUIRED_ROLE__PROPERTY)
		val umlRequiredParameter = helper.getModifiableCorr(pcmRequired, Parameter,
			TagLiterals.REQUIRED_ROLE__PARAMETER)
		checkRequiredRoleConcept(pcmRequired, umlRequiredInstance, umlRequiredParameter)
		val pcmRepository = pcmRequired.requiredInterface__OperationRequiredRole.repository__Interface
		checkRequiredRoleJavaConcept(umlRequiredParameter.operation, umlRequiredInstance, pcmRepository)
	}

	def protected checkRequiredRoleConcept(Property umlRequiredInstance) {
		val pcmRequired = helper.getModifiableCorr(umlRequiredInstance, OperationRequiredRole,
			TagLiterals.REQUIRED_ROLE__PROPERTY)
		assertNotNull(pcmRequired)
		checkRequiredRoleConcept(pcmRequired)
	}

	def protected checkRequiredRoleConcept(Parameter umlRequiredParameter) {
		val pcmRequired = helper.getModifiableCorr(umlRequiredParameter, OperationRequiredRole,
			TagLiterals.REQUIRED_ROLE__PARAMETER)
		assertNotNull(pcmRequired)
		checkRequiredRoleConcept(pcmRequired)
	}

	def protected checkRequiredRoleJavaConcept(Operation umlConstructor, Property umlRequiredInstance,
		Repository pcmRepository) {
		// Created during the test cases:
		checkJavaType(umlConstructor.eContainer as Classifier)
		checkJavaConstructor(umlConstructor)
		checkJavaAttribute(umlRequiredInstance)
		// Created before test cases, should be still there:
		val umlPackage = helper.getUmlRepositoryPackage(pcmRepository)
		checkJavaPackage(umlPackage)
		umlPackage.nestedPackages.forEach[checkJavaPackage]
		helper.getUmlComponentImpl(pcmRepository).checkJavaType
		helper.getUmlInterface(pcmRepository).checkJavaType
	}

	def private Repository createRepository_Component_Interface() {
		val pcmRepository = helper.createRepository
		helper.createComponent(pcmRepository)
		helper.createOperationInterface(pcmRepository)

		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		resourceAt(Path.of(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)).startRecordingChanges => [
			contents += pcmRepository
		]
		propagate

		return pcmRepository.clearResourcesAndReloadRoot
	}

	@Test
	def void testRequiredRoleConcept_PCM() {
		var pcmRepository = createRepository_Component_Interface

		var pcmRequired = RepositoryFactory.eINSTANCE.createOperationRequiredRole
		pcmRequired.requiredInterface__OperationRequiredRole = helper.getPcmOperationInterface(pcmRepository)
		helper.getPcmComponent(pcmRepository).requiredRoles_InterfaceRequiringEntity += pcmRequired

		propagate
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot

		val pcmComponent = helper.getPcmComponent(pcmRepository)
		assertEquals(1, pcmComponent.requiredRoles_InterfaceRequiringEntity.size,
			"There should be exactly one RequiredRole since only one was created by the test case.")
		pcmRequired = pcmComponent.requiredRoles_InterfaceRequiringEntity.head as OperationRequiredRole
		assertNotNull(pcmRequired)
		checkRequiredRoleConcept(pcmRequired)
	}

	@Test
	def void testRequiredRoleConcept_UML_RequiredConstructorParameter() {
		var pcmRepository = createRepository_Component_Interface
		var umlConstructor = helper.getUmlComponentConstructor(pcmRepository)
		startRecordingChanges(umlConstructor)

		var umlConstructorParameter = umlConstructor.createOwnedParameter(REQUIRED_ROLE_NAME,
			helper.getUmlInterface(pcmRepository))

		propagate
		umlConstructorParameter.clearResourcesAndReloadRoot
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot

		umlConstructor = helper.getUmlComponentConstructor(pcmRepository)
		assertEquals(1, umlConstructor.ownedParameters.size,
			"There should be exactly one Parameter for one RequiredRole created by the test case.")
		umlConstructorParameter = umlConstructor.ownedParameters.findFirst[it.name == REQUIRED_ROLE_NAME]
		assertNotNull(umlConstructorParameter)
		checkRequiredRoleConcept(umlConstructorParameter)
	}

	@Test
	def void testRequiredRoleConcept_UML_RequiredInstanceField() {
		var pcmRepository = createRepository_Component_Interface
		var umlComponentImpl = helper.getUmlComponentImpl(pcmRepository)
		var umlInterface = helper.getUmlInterface(pcmRepository)
		startRecordingChanges(umlComponentImpl)

		var umlRequiredInstanceField = umlComponentImpl.createOwnedAttribute(REQUIRED_ROLE_NAME, umlInterface)

		propagate
		umlRequiredInstanceField.clearResourcesAndReloadRoot
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot

		umlComponentImpl = helper.getUmlComponentImpl(pcmRepository)
		assertEquals(1, umlComponentImpl.ownedAttributes.size,
			"There should be exactly one Property for one RequiredRole created by the test case.")
		umlRequiredInstanceField = helper.getUmlComponentImpl(pcmRepository).ownedAttributes.findFirst [
			it.name == REQUIRED_ROLE_NAME
		]
		assertNotNull(umlRequiredInstanceField)
		checkRequiredRoleConcept(umlRequiredInstanceField)
	}
}

package tools.vitruv.applications.transitivechange.tests.linear.pcmumlclassjava;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.Objects;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.applications.testutility.integration.LegacyPcmUmlClassApplicationTestHelper;

/**
 * This class is based on the correlating PCM/UML test class. It is extended to include Java in the network.
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::OperationRequiredRole
 * of a pcm::InterfaceProvidingRequiringEntity (IPRE) with its corresponding uml::Parameter (constructor parameter
 * of the IPRE implementation class) and uml::Property (field in the IPRE implementation class used to store the
 * Component passed to the constructor).
 * <br><br>
 * Related files: PcmRequiredRole.reactions, UmlRequiredRoleParameter.reactions, UmlRequiredRoleProperty.reactions
 */
public class RequiredRoleConceptTest extends PcmUmlJavaLinearTransitiveChangeTest {

	private final String REQUIRED_ROLE_NAME = "testRequiredRole";

	public void checkRequiredRoleConcept(
		OperationRequiredRole pcmRequired,
		Property umlRequiredInstance,
		Parameter umlRequiredParameter
	) {
		assertNotNull(pcmRequired);
		assertNotNull(umlRequiredInstance);
		assertNotNull(umlRequiredParameter);
		assertTrue(corresponds(pcmRequired, umlRequiredInstance, TagLiterals.REQUIRED_ROLE__PROPERTY));
		assertTrue(corresponds(pcmRequired, umlRequiredParameter, TagLiterals.REQUIRED_ROLE__PARAMETER));
		// the respective type references have to correspond
		assertTrue(corresponds(pcmRequired.getRequiredInterface__OperationRequiredRole(), umlRequiredInstance.getType()));
		assertTrue(corresponds(pcmRequired.getRequiredInterface__OperationRequiredRole(), umlRequiredParameter.getType()));
		// the owning component and component implementation have to correspond
		assertTrue(
			corresponds(pcmRequired.getRequiringEntity_RequiredRole(), umlRequiredInstance.getClass_(),
				TagLiterals.IPRE__IMPLEMENTATION));
		assertTrue(
			corresponds(pcmRequired.getRequiringEntity_RequiredRole(),
				umlRequiredParameter.getOperation() == null ? null : umlRequiredParameter.getOperation().getClass_(),
				TagLiterals.IPRE__IMPLEMENTATION));
		assertTrue(Objects.equals(pcmRequired.getEntityName(), umlRequiredInstance.getName()));
		assertTrue(Objects.equals(pcmRequired.getEntityName(), umlRequiredParameter.getName()));
	}

	protected void checkRequiredRoleConcept(OperationRequiredRole pcmRequired) {
		Property umlRequiredInstance = helper.getModifiableCorr(pcmRequired, Property.class, TagLiterals.REQUIRED_ROLE__PROPERTY);
		Parameter umlRequiredParameter = helper.getModifiableCorr(pcmRequired, Parameter.class,
			TagLiterals.REQUIRED_ROLE__PARAMETER);
		checkRequiredRoleConcept(pcmRequired, umlRequiredInstance, umlRequiredParameter);
		var pcmRepository = pcmRequired.getRequiredInterface__OperationRequiredRole().getRepository__Interface();
		checkRequiredRoleJavaConcept(umlRequiredParameter.getOperation(), umlRequiredInstance, pcmRepository);
	}

	protected void checkRequiredRoleConcept(Property umlRequiredInstance) {
		OperationRequiredRole pcmRequired = helper.getModifiableCorr(umlRequiredInstance, OperationRequiredRole.class,
			TagLiterals.REQUIRED_ROLE__PROPERTY);
		assertNotNull(pcmRequired);
		checkRequiredRoleConcept(pcmRequired);
	}

	protected void checkRequiredRoleConcept(Parameter umlRequiredParameter) {
		OperationRequiredRole pcmRequired = helper.getModifiableCorr(umlRequiredParameter, OperationRequiredRole.class,
			TagLiterals.REQUIRED_ROLE__PARAMETER);
		assertNotNull(pcmRequired);
		checkRequiredRoleConcept(pcmRequired);
	}

	protected void checkRequiredRoleJavaConcept(Operation umlConstructor, Property umlRequiredInstance,
		Repository pcmRepository) {
		// Created during the test cases:
		checkJavaType((Classifier) umlConstructor.eContainer());
		checkJavaConstructor(umlConstructor);
		checkJavaAttribute(umlRequiredInstance);
		// Created before test cases, should be still there:
		var umlPackage = helper.getUmlRepositoryPackage(pcmRepository);
		checkJavaPackage(umlPackage);
		umlPackage.getNestedPackages().forEach(this::checkJavaPackage);
		checkJavaType(helper.getUmlComponentImpl(pcmRepository));
		checkJavaType(helper.getUmlInterface(pcmRepository));
	}

	private Repository createRepository_Component_Interface() {
		Repository pcmRepository = helper.createRepository();
		helper.createComponent(pcmRepository);
		helper.createOperationInterface(pcmRepository);

		getUserInteraction().addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);
		startRecordingChanges(resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE))).getContents()
			.add(pcmRepository);
		propagate();

		return clearResourcesAndReloadRoot(pcmRepository);
	}

	@Test
	public void testRequiredRoleConcept_PCM() {
		var pcmRepository = createRepository_Component_Interface();

		var pcmRequired = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
		pcmRequired.setRequiredInterface__OperationRequiredRole(helper.getPcmOperationInterface(pcmRepository));
		helper.getPcmComponent(pcmRepository).getRequiredRoles_InterfaceRequiringEntity().add(pcmRequired);

		propagate();
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);

		var pcmComponent = helper.getPcmComponent(pcmRepository);
		assertEquals(1, pcmComponent.getRequiredRoles_InterfaceRequiringEntity().size(),
			"There should be exactly one RequiredRole since only one was created by the test case.");
		pcmRequired = (OperationRequiredRole) Iterables.getFirst(pcmComponent.getRequiredRoles_InterfaceRequiringEntity(), null);
		assertNotNull(pcmRequired);
		checkRequiredRoleConcept(pcmRequired);
	}

	@Test
	public void testRequiredRoleConcept_UML_RequiredConstructorParameter() {
		var pcmRepository = createRepository_Component_Interface();
		var umlConstructor = helper.getUmlComponentConstructor(pcmRepository);
		startRecordingChanges(umlConstructor);

		var umlConstructorParameter = umlConstructor.createOwnedParameter(REQUIRED_ROLE_NAME,
			helper.getUmlInterface(pcmRepository));

		propagate();
		clearResourcesAndReloadRoot(umlConstructorParameter);
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);

		umlConstructor = helper.getUmlComponentConstructor(pcmRepository);
		assertEquals(1, umlConstructor.getOwnedParameters().size(),
			"There should be exactly one Parameter for one RequiredRole created by the test case.");
		umlConstructorParameter = umlConstructor.getOwnedParameters().stream()
			.filter(it -> Objects.equals(it.getName(), REQUIRED_ROLE_NAME))
			.findFirst().orElse(null);
		assertNotNull(umlConstructorParameter);
		checkRequiredRoleConcept(umlConstructorParameter);
	}

	@Test
	public void testRequiredRoleConcept_UML_RequiredInstanceField() {
		var pcmRepository = createRepository_Component_Interface();
		var umlComponentImpl = helper.getUmlComponentImpl(pcmRepository);
		var umlInterface = helper.getUmlInterface(pcmRepository);
		startRecordingChanges(umlComponentImpl);

		var umlRequiredInstanceField = umlComponentImpl.createOwnedAttribute(REQUIRED_ROLE_NAME, umlInterface);

		propagate();
		clearResourcesAndReloadRoot(umlRequiredInstanceField);
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);

		umlComponentImpl = helper.getUmlComponentImpl(pcmRepository);
		assertEquals(1, umlComponentImpl.getOwnedAttributes().size(),
			"There should be exactly one Property for one RequiredRole created by the test case.");
		umlRequiredInstanceField = helper.getUmlComponentImpl(pcmRepository).getOwnedAttributes().stream()
			.filter(it -> Objects.equals(it.getName(), REQUIRED_ROLE_NAME))
			.findFirst().orElse(null);
		assertNotNull(umlRequiredInstanceField);
		checkRequiredRoleConcept(umlRequiredInstanceField);
	}
}

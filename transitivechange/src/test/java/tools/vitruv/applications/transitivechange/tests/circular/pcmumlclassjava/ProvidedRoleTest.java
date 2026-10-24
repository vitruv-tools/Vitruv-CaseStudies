package tools.vitruv.applications.transitivechange.tests.circular.pcmumlclassjava;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.Objects;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.applications.testutility.integration.LegacyPcmUmlClassApplicationTestHelper;

/**
 * This class is based on the correlating PCM/UML test class. It is extended to include Java in the network.
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::OperationProvidedRole
 * in an pcm::InterfaceProvidingRequiringEntity (IPRE) with an uml::InterfaceRealization in the uml::Class (implementation) corresponding to the IPRE.
 * <br><br>
 * Related files: PcmProvidedRole.reactions, UmlProvidedRoleGeneralization.reactions
 */
public class ProvidedRoleTest extends PcmUmlJavaTransitiveChangeTest {

	private static final String PROVIDED_ROLE_NAME = "testProvidedRole";

	public void checkProvidedRoleConcept(
		OperationProvidedRole pcmProvided,
		InterfaceRealization umlRealization
	) {
		assertNotNull(pcmProvided);
		assertNotNull(umlRealization);
		assertTrue(corresponds(pcmProvided, umlRealization, TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION));
		assertTrue(Objects.equals(pcmProvided.getEntityName(), umlRealization.getName()));
		// the respective type references have to correspond
		assertTrue(
			corresponds(pcmProvided.getProvidedInterface__OperationProvidedRole(), umlRealization.getContract(),
				TagLiterals.INTERFACE_TO_INTERFACE));
		// the owning component and component implementation have to correspond
		assertTrue(
			corresponds(pcmProvided.getProvidingEntity_ProvidedRole(), umlRealization.getImplementingClassifier(),
				TagLiterals.IPRE__IMPLEMENTATION));
	}

	protected void checkProvidedRoleConcept(OperationProvidedRole pcmProvided) {
		InterfaceRealization umlRealization = helper.getModifiableCorr(pcmProvided, InterfaceRealization.class,
			TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION);
		checkProvidedRoleConcept(pcmProvided, umlRealization);
		checkJavaProvidedRoleConcept(umlRealization, pcmProvided);
	}

	protected void checkProvidedRoleConcept(InterfaceRealization umlRealization) {
		OperationProvidedRole pcmProvided = helper.getModifiableCorr(umlRealization, OperationProvidedRole.class,
			TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION);
		checkProvidedRoleConcept(pcmProvided, umlRealization);
		checkJavaProvidedRoleConcept(umlRealization, pcmProvided);
	}

	protected void checkJavaProvidedRoleConcept(InterfaceRealization umlRealization, OperationProvidedRole pcmProvided) {
		checkJavaType(umlRealization.getContract());
		checkJavaType(umlRealization.getImplementingClassifier());
		checkJavaRealization(umlRealization);
		umlRealization.getImplementingClassifier().getOperations().forEach(this::checkJavaConstructor);
		// Created before test cases, should be still there:
		var umlPackage = helper.getUmlRepositoryPackage(
			pcmProvided.getProvidedInterface__OperationProvidedRole().getRepository__Interface());
		checkJavaPackage(umlPackage);
		umlPackage.getNestedPackages().forEach(this::checkJavaPackage);
	}

	private Repository createRepository_Component_Interface() {
		Repository pcmRepository = helper.createRepository();
		helper.createComponent(pcmRepository);
		helper.createOperationInterface(pcmRepository);

		getUserInteraction().addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);
		startRecordingChanges(resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE))).getContents()
			.add(pcmRepository);
		propagate();
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE);
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);

		return clearResourcesAndReloadRoot(pcmRepository);
	}

	@Test
	public void testProvidedRoleConcept_PCM() {
		var pcmRepository = createRepository_Component_Interface();

		var pcmProvided = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
		pcmProvided.setEntityName(PROVIDED_ROLE_NAME);
		pcmProvided.setProvidedInterface__OperationProvidedRole(helper.getPcmOperationInterface(pcmRepository));
		helper.getPcmComponent(pcmRepository).getProvidedRoles_InterfaceProvidingEntity().add(pcmProvided);

		propagate();
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);

		pcmProvided = (OperationProvidedRole) Iterables.getFirst(
			helper.getPcmComponent(pcmRepository).getProvidedRoles_InterfaceProvidingEntity(), null);
		checkProvidedRoleConcept(pcmProvided);
	}

	@Test
	public void testProvidedRoleConcept_UML() {
		var pcmRepository = createRepository_Component_Interface();
		startRecordingChanges(helper.getUmlComponentImpl(pcmRepository));

		var umlRealization = helper.getUmlComponentImpl(pcmRepository).createInterfaceRealization(PROVIDED_ROLE_NAME,
			helper.getUmlInterface(pcmRepository));

		propagate();
		clearResourcesAndReloadRoot(umlRealization);
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);

		final var umlInterface = helper.getUmlInterface(pcmRepository); // necessary that it is final for Lambda
		umlRealization = helper.getUmlComponentImpl(pcmRepository).getInterfaceRealizations().stream()
			.filter(it -> Objects.equals(it.getContract(), umlInterface))
			.findFirst().orElse(null);
		checkProvidedRoleConcept(umlRealization);
	}

}

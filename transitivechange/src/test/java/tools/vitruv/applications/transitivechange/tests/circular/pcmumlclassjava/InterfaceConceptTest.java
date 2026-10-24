package tools.vitruv.applications.transitivechange.tests.circular.pcmumlclassjava;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.applications.testutility.integration.LegacyPcmUmlClassApplicationTestHelper;

/**
 * This class is based on the correlating PCM/UML test class. It is extended to include Java in the network.
 * This test class tests the reactions and routines are supposed to synchronize a pcm::OperationInterface
 * with its corresponding uml::Interface (in the contracts uml::Package corresponding to a pcm::Repository).
 * <br><br>
 * Related files: PcmInterface.reactions, UmlInterface.reactions, UmlInterfaceGeneralization.reactions
 */
public class InterfaceConceptTest extends PcmUmlJavaTransitiveChangeTest {

	private static final String TEST_INTERFACE_NAME = "TestInterface";

	public void checkInterfaceConcept(
		OperationInterface pcmInterface,
		Interface umlInterface
	) {
		assertNotNull(pcmInterface);
		assertNotNull(umlInterface);
		assertTrue(corresponds(pcmInterface, umlInterface, TagLiterals.INTERFACE_TO_INTERFACE));
		assertTrue(Objects.equals(pcmInterface.getEntityName(), umlInterface.getName()));
		// should be contained in corresponding repository and contracts package respectively
		assertTrue(
			corresponds(pcmInterface.getRepository__Interface(), umlInterface.getPackage(),
				TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE));
		// parent interfaces should correspond
		List<Interface> umlParentCorrespondences = pcmInterface.getParentInterfaces__Interface().stream()
			.map(pcmParent -> Iterables.getFirst(getCorrespondingEObjects(pcmParent, Interface.class), null))
			.collect(Collectors.toList());
		assertFalse(umlParentCorrespondences.contains(null));
		assertFalse(
			umlParentCorrespondences.stream()
				.map(umlParent -> umlInterface.getGeneralizations().stream()
					.anyMatch(gen -> EcoreUtil.equals(gen.getGeneral(), umlParent)))
				.anyMatch(it ->
					it == false
				)
		);
	}

	protected void checkInterfaceConcept(OperationInterface pcmInterface) {
		Interface umlInterface = helper.getCorr(pcmInterface, Interface.class, TagLiterals.INTERFACE_TO_INTERFACE);
		checkInterfaceConcept(pcmInterface, umlInterface);
		checkJavaInterfaceConcept(umlInterface, pcmInterface);
	}

	protected void checkInterfaceConcept(Interface umlInterface) {
		OperationInterface pcmInterface = helper.getCorr(umlInterface, OperationInterface.class,
			TagLiterals.INTERFACE_TO_INTERFACE);
		checkInterfaceConcept(pcmInterface, umlInterface);
		checkJavaInterfaceConcept(umlInterface, pcmInterface);
	}

	protected void checkJavaInterfaceConcept(Interface umlInterface, OperationInterface pcmInterface) {
		checkJavaType(umlInterface);
		// Created before test cases, should be still there:
		var umlPackage = helper.getUmlRepositoryPackage(pcmInterface.getRepository__Interface());
		checkJavaPackage(umlPackage);
		umlPackage.getNestedPackages().forEach(this::checkJavaPackage);
	}

	private Repository createRepositoryConcept() {
		Repository pcmRepository = helper.createRepository();

		getUserInteraction().addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);
		startRecordingChanges(resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE))).getContents()
			.add(pcmRepository);
		propagate();

		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE);
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);

		return clearResourcesAndReloadRoot(pcmRepository);
	}

	@Test
	public void testCreateInterfaceConcept_UML() {
		var pcmRepository = createRepositoryConcept();
		var umlContractsPkg = helper.getUmlContractsPackage(pcmRepository);
		startRecordingChanges(umlContractsPkg);

		var mUmlInterface = umlContractsPkg.createOwnedInterface(TEST_INTERFACE_NAME);
		mUmlInterface.setVisibility(VisibilityKind.PUBLIC_LITERAL);
		propagate();

		clearResourcesAndReloadRoot(umlContractsPkg);
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);
		umlContractsPkg = helper.getUmlContractsPackage(pcmRepository);

		mUmlInterface = (Interface) Iterables.getFirst(umlContractsPkg.getPackagedElements(), null);
		assertNotNull(mUmlInterface);
		checkInterfaceConcept(mUmlInterface);
	}

	@Test
	public void testCreateInterfaceConcept_PCM() {
		var pcmRepository = createRepositoryConcept();
		var umlContractsPkg = helper.getUmlContractsPackage(pcmRepository);
		startRecordingChanges(umlContractsPkg);

		var mPcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
		mPcmInterface.setEntityName(TEST_INTERFACE_NAME);
		pcmRepository.getInterfaces__Repository().add(mPcmInterface);
		propagate();

		clearResourcesAndReloadRoot(umlContractsPkg);
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);
		umlContractsPkg = helper.getUmlContractsPackage(pcmRepository);

		mPcmInterface = (OperationInterface) Iterables.getFirst(pcmRepository.getInterfaces__Repository(), null);
		assertNotNull(mPcmInterface);
		checkInterfaceConcept(mPcmInterface);
	}
}

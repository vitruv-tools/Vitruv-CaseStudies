package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.ClassMethod;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.pcm2java.Pcm2JavaTestUtils;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SEFFMappingTransformationTest extends Pcm2JavaTransformationTest {

	@Test
	public void testCreateSEFF() throws Throwable {
		final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
		final BasicComponent bc1 = this.addBasicComponentAndSync(repo);
		final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo,
				Pcm2JavaTestUtils.INTERFACE_NAME);
		final OperationSignature opSignature = this.createAndSyncOperationSignature(repo, opInterface);
		this.createAndSyncOperationProvidedRole(opInterface, bc1);

		final ResourceDemandingSEFF rdSEFF = this.createAndSyncSeff(bc1, opSignature);

		this.assertSEFFCorrespondenceToMethod(rdSEFF, opSignature.getEntityName());
	}

	@Test
	public void testRenameSEFF() throws Throwable {
		final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
		final BasicComponent bc1 = this.addBasicComponentAndSync(repo);
		final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo,
				Pcm2JavaTestUtils.INTERFACE_NAME);
		final OperationSignature opSignature = this.createAndSyncOperationSignature(repo, opInterface);
		this.createAndSyncOperationProvidedRole(opInterface, bc1);
		final ResourceDemandingSEFF rdSEFF = this.createAndSyncSeff(bc1, opSignature);

		opSignature.setEntityName(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME + Pcm2JavaTestUtils.RENAME);
		propagate();

		this.assertSEFFCorrespondenceToMethod(rdSEFF, opSignature.getEntityName());
	}

	private void assertSEFFCorrespondenceToMethod(final ResourceDemandingSEFF rdSEFF, final String expectedName)
			throws Throwable {
		final Set<EObject> correspondingEObjects = CorrespondenceModelUtil
				.getCorrespondingEObjects(this.getCorrespondenceModel(), rdSEFF);
		assertEquals(1, correspondingEObjects.size(),
				"Expected exactly one corresponding EObject for rdSEFF " + rdSEFF);
		final EObject correspondingEObject = correspondingEObjects.iterator().next();
		assertTrue(correspondingEObject instanceof ClassMethod,
				"The corresponding EObject for a SEFF has to be a ClassMethod");
		final ClassMethod correspondingClassMethod = (ClassMethod) correspondingEObject;
		assertEquals(expectedName, correspondingClassMethod.getName(),
				"The corresponding class method has the wrong name");
	}
}

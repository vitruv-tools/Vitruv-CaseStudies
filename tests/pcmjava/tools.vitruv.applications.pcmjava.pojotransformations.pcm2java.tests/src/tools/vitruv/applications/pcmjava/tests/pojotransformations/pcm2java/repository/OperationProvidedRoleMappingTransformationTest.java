package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import static org.junit.Assert.assertEquals;

import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.containers.CompilationUnit;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;
import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*;

public class OperationProvidedRoleMappingTransformationTest extends Pcm2JavaTransformationTest {

	@Test
	public void testAddOperationProvidedRole() throws Throwable {
		final OperationProvidedRole operationProvidedRole = this
				.createAndSyncRepoOpIntfOpSigBasicCompAndOperationProvRole();

		this.assertOperationProvidedRole(operationProvidedRole);
	}

	@Test
	public void testChangeInterfaceOfOperationProvidedRole() throws Throwable {
		final OperationProvidedRole operationProvidedRole = this
				.createAndSyncRepoOpIntfOpSigBasicCompAndOperationProvRole();
		final Repository repo = operationProvidedRole.getProvidedInterface__OperationProvidedRole()
				.getRepository__Interface();
		final OperationInterface newInterface = this.addInterfaceToReposiotryAndSync(repo,
				Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME);

		operationProvidedRole.setProvidedInterface__OperationProvidedRole(newInterface);
		super.saveAndSynchronizeChanges(repo);

		this.assertOperationProvidedRole(operationProvidedRole);
	}

	@Test
	public void testChangeComponentOfOperationProvidedRole() throws Throwable {
		final OperationProvidedRole operationProvidedRole = this
				.createAndSyncRepoOpIntfOpSigBasicCompAndOperationProvRole();
		final Repository repo = operationProvidedRole.getProvidedInterface__OperationProvidedRole()
				.getRepository__Interface();
		final BasicComponent newBasicComponent = this.addBasicComponentAndSync(repo, "NewProvidingComponent");

		operationProvidedRole.setProvidingEntity_ProvidedRole(newBasicComponent);
		super.saveAndSynchronizeChanges(repo);

		this.assertOperationProvidedRole(operationProvidedRole);
	}

	@Test
	public void testTwoProvidedRolesInOneComponent() throws Throwable {
		final OperationProvidedRole operationProvidedRole = this
				.createAndSyncRepoOpIntfOpSigBasicCompAndOperationProvRole();
		final Repository repo = operationProvidedRole.getProvidedInterface__OperationProvidedRole()
				.getRepository__Interface();
		final BasicComponent basicComponent = (BasicComponent) operationProvidedRole.getProvidingEntity_ProvidedRole();
		final OperationInterface newInterface = this.addInterfaceToReposiotryAndSync(repo,
				Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME);

		final OperationProvidedRole newOperationProvidedRole = RepositoryFactory.eINSTANCE
				.createOperationProvidedRole();
		newOperationProvidedRole.setEntityName("NewOperationProvidedRole");
		newOperationProvidedRole.setProvidedInterface__OperationProvidedRole(newInterface);
		newOperationProvidedRole.setProvidingEntity_ProvidedRole(basicComponent);
		basicComponent.getProvidedRoles_InterfaceProvidingEntity().add(newOperationProvidedRole);
		super.saveAndSynchronizeChanges(repo);

		this.assertOperationProvidedRole(operationProvidedRole);
		this.assertOperationProvidedRole(newOperationProvidedRole);
	}

	@Test
	public void testTwoProvidedRolesInOneComponentAndRemoveOne() throws Throwable {
		final OperationProvidedRole operationProvidedRole = this
				.createAndSyncRepoOpIntfOpSigBasicCompAndOperationProvRole();
		final Repository repo = operationProvidedRole.getProvidedInterface__OperationProvidedRole()
				.getRepository__Interface();
		final BasicComponent basicComponent = (BasicComponent) operationProvidedRole.getProvidingEntity_ProvidedRole();
		final OperationInterface newInterface = this.addInterfaceToReposiotryAndSync(repo,
				Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME);
		final OperationProvidedRole newOperationProvidedRole = RepositoryFactory.eINSTANCE
				.createOperationProvidedRole();
		newOperationProvidedRole.setEntityName("NewOperationProvidedRole");
		newOperationProvidedRole.setProvidedInterface__OperationProvidedRole(newInterface);
		newOperationProvidedRole.setProvidingEntity_ProvidedRole(basicComponent);
		basicComponent.getProvidedRoles_InterfaceProvidingEntity().add(newOperationProvidedRole);
		super.saveAndSynchronizeChanges(repo);

		basicComponent.getProvidedRoles_InterfaceProvidingEntity().remove(newOperationProvidedRole);
		super.saveAndSynchronizeChanges(repo);

		this.assertOperationProvidedRole(operationProvidedRole);
		final CompilationUnit jaMoPPCu = claimOne(CorrespondenceModelUtil
				.getCorrespondingEObjectsByType(this.getCorrespondenceModel(), basicComponent, CompilationUnit.class));
		assertEquals("Unexpected size of imports", 1, jaMoPPCu.getImports().size());
		final Class jaMoPPClass = (Class) jaMoPPCu.getClassifiers().get(0);
		assertEquals("Unexpected size of implemented interfaces", 1, jaMoPPClass.getImplements().size());
	}

	@Test
	public void testOperationProvidedRoleToSystem() throws Throwable {
		final Repository repo = super.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
		final OperationInterface opInterface = super.addInterfaceToReposiotryAndSync(repo,
				Pcm2JavaTestUtils.INTERFACE_NAME);
		final org.palladiosimulator.pcm.system.System system = super.createAndSyncSystem(Pcm2JavaTestUtils.SYSTEM_NAME);

		final OperationProvidedRole operationProvidedRole = super.createAndSyncOperationProvidedRole(opInterface,
				system);

		this.assertOperationProvidedRole(operationProvidedRole);
	}

}

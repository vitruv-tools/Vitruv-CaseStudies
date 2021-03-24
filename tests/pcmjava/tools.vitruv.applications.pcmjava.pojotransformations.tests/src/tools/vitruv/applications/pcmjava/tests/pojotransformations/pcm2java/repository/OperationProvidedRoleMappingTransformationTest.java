package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.containers.CompilationUnit;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.pcm2java.Pcm2JavaTestUtils;
import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
		propagate();

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
		propagate();

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
		propagate();

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
		propagate();

		basicComponent.getProvidedRoles_InterfaceProvidingEntity().remove(newOperationProvidedRole);
		propagate();

		this.assertOperationProvidedRole(operationProvidedRole);
		final CompilationUnit jaMoPPCu = claimOne(getCorrespondingEObjects(basicComponent, CompilationUnit.class));
		assertEquals(1, jaMoPPCu.getImports().size(), "Unexpected size of imports");
		final Class jaMoPPClass = (Class) jaMoPPCu.getClassifiers().get(0);
		assertEquals(1, jaMoPPClass.getImplements().size(), "Unexpected size of implemented interfaces");
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

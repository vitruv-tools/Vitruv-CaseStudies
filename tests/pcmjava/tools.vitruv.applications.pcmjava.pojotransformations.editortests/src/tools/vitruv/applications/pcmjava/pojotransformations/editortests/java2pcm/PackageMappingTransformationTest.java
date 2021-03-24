package tools.vitruv.applications.pcmjava.pojotransformations.editortests.java2pcm;

import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

import tools.vitruv.applications.pcmjava.tests.util.pcm2java.Pcm2JavaTestUtils;
import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PackageMappingTransformationTest extends Java2PcmPackageMappingTransformationTest {

	/**
	 * first package is created --> should be mapped to a repository
	 *
	 * @throws Throwable
	 */
	@Test
	public void testAddFirstPackage() throws Throwable {
		final Repository repo = super.addRepoContractsAndDatatypesPackage();
		assertEquals(Pcm2JavaTestUtils.REPOSITORY_NAME_EXPECTED, repo.getEntityName(),
				"Name of the repository is not the same as the name of the package");
		this.assertResourceAndFileForEObjects(repo);
		this.assertFilesOnlyForEObjects(repo);
	}

	/**
	 * second packages is added --> should be mapped to a basic component
	 *
	 * @throws Throwable
	 */
	@Test
	public void testAddSecondPackage() throws Throwable {
		final Repository repo = super.addRepoContractsAndDatatypesPackage();

		final BasicComponent bc = super.addSecondPackageCorrespondsToBasicComponent();

		this.assertRepositoryAndPCMName(repo, bc, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME);
		this.assertFilesOnlyForEObjects(bc);
	}

	@Test
	public void testCreateCompositeComponent() throws Throwable {
		final Repository repo = super.addRepoContractsAndDatatypesPackage();

		final CompositeComponent cc = super.addSecondPackageCorrespondsToCompositeComponent();

		this.assertRepositoryAndPCMName(repo, cc, Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME);
		this.assertFilesOnlyForEObjects(cc);
	}

	@Test
	public void testCreateSystem() throws Throwable {
		final Repository repository = super.addRepoContractsAndDatatypesPackage();

		final System system = super.addSecondPackageCorrespondsToSystem();

		this.assertPCMNamedElement(system, Pcm2JavaTestUtils.SYSTEM_NAME);
		this.assertFilesOnlyForEObjects(repository, system);
	}

	@Test
	public void testCreateNoCorrespondingObject() throws Throwable {
		final Repository repo = super.addRepoContractsAndDatatypesPackage();

		super.addSecondPackageCorrespondsWithoutCorrespondences();
		assertNotNull(repo);
		// TODO:what to check?
	}

	@Test
	public void testRenamePackage() throws Throwable {
		final Repository repo = super.addRepoContractsAndDatatypesPackage();
		super.addSecondPackageCorrespondsToBasicComponent();

		final String packageName = Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.RENAME;

		final Package renamedPackage = super.renamePackage(this.secondPackage, packageName);

		final BasicComponent bc = claimOne(getCorrespondingEObjects(renamedPackage, BasicComponent.class));
		// repository of basic component has to be the repository
		assertEquals(repo.getId(), bc.getRepository__RepositoryComponent().getId(),
				"Repository of basic compoennt is not the repository: " + repo);
		// name should be changed since there is no implementing class (yet) for the
		// component
		assertTrue(packageName.toLowerCase().contains(bc.getEntityName().toLowerCase()),
				"The name of the basic component, which is '" + bc.getEntityName()
						+ "', is not contained in the name of the package: " + packageName + " ");
		this.assertResourceAndFileForEObjects(bc);
	}
}

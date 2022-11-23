package tools.vitruv.applications.pcmjava.javaeditor.java2pcm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil.claimPackage;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimComponent;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimSingleRepository;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimSingleSystem;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.BASIC_COMPONENT_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.RENAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.REPOSITORY_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.REPOSITORY_NAME_EXPECTED;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.SYSTEM_NAME;

import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmUserSelection;

class PackageMappingTransformationTest extends Java2PcmTransformationTest {
	/**
	 * first package is created --> should be mapped to a repository
	 */
	@Test
	void testAddFirstPackage() throws Exception {
		createRepositoryPackage();

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			assertEquals(REPOSITORY_NAME_EXPECTED, repository.getEntityName(), "repository has wrong name");
		});
	}

	/**
	 * second package is added --> should be mapped to a basic component
	 */
	@Test
	void testAddSecondPackageAsBasicComponent() throws Exception {
		createRepositoryPackage();

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		changeJavaView(view -> {
			createPackageWithPackageInfo(view, REPOSITORY_NAME, BASIC_COMPONENT_NAME);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			claimComponent(repository, BASIC_COMPONENT_NAME, BasicComponent.class);
		});
	}

	/**
	 * second package is added --> should be mapped to a composite component
	 */
	@Test
	void testAddSecondPackageAsCompositeComponent() throws Exception {
		createRepositoryPackage();

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_COMPONENT.getSelection());
		changeJavaView(view -> {
			createPackageWithPackageInfo(view, REPOSITORY_NAME, COMPOSITE_COMPONENT_NAME);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			claimComponent(repository, COMPOSITE_COMPONENT_NAME, CompositeComponent.class);
		});
	}

	/**
	 * second package is added --> should be mapped to a system
	 */
	@Test
	void testAddSecondPackageAsSystem() throws Exception {
		createRepositoryPackage();

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_SYSTEM.getSelection());
		changeJavaView(view -> {
			createPackageWithPackageInfo(view, REPOSITORY_NAME, SYSTEM_NAME);
		});

		validatePcmView(view -> {
			System system = claimSingleSystem(view);
			assertEquals(SYSTEM_NAME, system.getEntityName(), "system has wrong name");
		});
	}

	/**
	 * second package is added --> should be mapped to nothing
	 */
	@Test
	void testAddSecondPackageAsNone() throws Exception {
		createRepositoryPackage();

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_NOTHING_DECIDE_LATER.getSelection());
		changeJavaView(view -> {
			createPackageWithPackageInfo(view, REPOSITORY_NAME, BASIC_COMPONENT_NAME);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			assertTrue(repository.getComponents__Repository().isEmpty(),
					"there must not exist any contained Component");
			assertTrue(view.getRootObjects(System.class).isEmpty(), "there must not exist any System");
		});
	}

	@Test
	void testRenameRepository() throws Exception {
		createRepositoryPackage();

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		changeJavaView(view -> {
			createPackageWithPackageInfo(view, REPOSITORY_NAME, BASIC_COMPONENT_NAME);
		});

		String changedPackageName = BASIC_COMPONENT_NAME + RENAME;

		changeJavaEditorView(view -> {
			Package innerPackage = claimPackage(view, BASIC_COMPONENT_NAME);
			view.getManipulationUtil().renamePackage(innerPackage, changedPackageName);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			assertEquals(1, repository.getComponents__Repository().size(), "wrong number of components in repository");
			claimComponent(repository, changedPackageName, BasicComponent.class);
		});
	}
}

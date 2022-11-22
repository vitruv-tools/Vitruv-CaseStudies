package tools.vitruv.applications.pcmjava.javaeditor.java2pcm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil.claimPackage;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimInterface;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimSingleRepository;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.BASIC_COMPONENT_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.INTERFACE_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.RENAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.REPOSITORY_NAME;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.text.edits.TextEdit;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmUserSelection;
import tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil;
import tools.vitruv.applications.pcmjava.javaeditor.util.JavaTextEditFactory;

class InterfaceMappingTransformationTest extends Java2PcmTransformationTest {
	/**
	 * interface in contracts package --> should automatically be mapped to
	 * operation interface
	 */
	@Test
	void testAddInterfaceInContractsPackage() throws Exception {
		createRepositoryPackage();

		changeJavaEditorView(view -> {
			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			view.getManipulationUtil().createInterface(INTERFACE_NAME, contractsPackage, null);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			claimInterface(repository, INTERFACE_NAME, OperationInterface.class);
		});
	}

	/**
	 * interface in non-repository package --> "user" should be asked and decide to
	 * add it
	 */
	@Test
	void testAddInterfaceInNonRepositoryPackageAsArchitecturalInterface() throws Exception {
		createRepositoryPackage();

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		changeJavaView(view -> {
			createPackageWithPackageInfo(view, REPOSITORY_NAME, BASIC_COMPONENT_NAME);
		});

		getUserInteraction()
				.addNextSingleSelection(Java2PcmUserSelection.SELECT_CREATE_INTERFACE_NOT_IN_CONTRACTS.getSelection());
		changeJavaEditorView(view -> {
			Package componentPackage = claimPackage(view, BASIC_COMPONENT_NAME);
			view.getManipulationUtil().createInterface(INTERFACE_NAME, componentPackage, null);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			claimInterface(repository, INTERFACE_NAME, OperationInterface.class);
		});
	}

	/**
	 * interface in non-repository package --> "user" should be asked and decide to
	 * not add it
	 */
	@Test
	void testAddInterfaceInNonRepositoryPackageAsTechnicalInterface() throws Exception {
		createRepositoryPackage();

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		changeJavaView(view -> {
			createPackageWithPackageInfo(view, REPOSITORY_NAME, BASIC_COMPONENT_NAME);
		});

		getUserInteraction().addNextSingleSelection(
				Java2PcmUserSelection.SELECT_DONT_CREATE_INTERFACE_NOT_IN_CONTRACTS.getSelection());
		changeJavaEditorView(view -> {
			Package componentPackage = claimPackage(view, BASIC_COMPONENT_NAME);
			view.getManipulationUtil().createInterface(INTERFACE_NAME, componentPackage, null);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			assertEquals(0, repository.getInterfaces__Repository().size(), "there must not exist an interface");
		});
	}

	@Test
	void testRenameInterface() throws Exception {
		createRepositoryPackage();

		changeJavaEditorView(view -> {
			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			view.getManipulationUtil().createInterface(INTERFACE_NAME, contractsPackage, null);
		});

		changeJavaEditorView(view -> {
			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			ICompilationUnit anInterface = view.getManipulationUtil().claimCompilationUnit(INTERFACE_NAME,
					contractsPackage);
			TextEdit renameEdit = JavaTextEditFactory.renameCompilationUnit(anInterface, INTERFACE_NAME,
					INTERFACE_NAME + RENAME);
			view.getManipulationUtil().editCompilationUnit(anInterface, renameEdit);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			claimInterface(repository, INTERFACE_NAME + RENAME, OperationInterface.class);
			assertEquals(1, repository.getInterfaces__Repository().size(), "too many interfaces in repository");
		});
	}
}

package tools.vitruv.applications.pcmjava.javaeditor.java2pcm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil.claimPackage;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimComponent;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimDataType;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimSingleRepository;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimSingleSystem;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.BASIC_COMPONENT_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.IMPLEMENTING_CLASS_SUFFIX;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.RENAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.REPOSITORY_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.SYSTEM_NAME;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.text.edits.ReplaceEdit;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmUserSelection;
import tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil;

class ClassMappingTransformationTest extends Java2PcmTransformationTest {
	/**
	 * Class that in mapped package and same name as component + impl--> should be
	 * the new implementing class for the component
	 */
	@ParameterizedTest()
	@EnumSource(value = Java2PcmUserSelection.class, names = { "SELECT_BASIC_COMPONENT",
			"SELECT_NOTHING_DECIDE_LATER" })
	void testAddClassInSecondPackageAsBasicComponent(Java2PcmUserSelection packageSelection) throws Exception {
		createRepositoryPackage();

		getUserInteraction().addNextSingleSelection(packageSelection.getSelection());
		changeJavaView(view -> {
			createPackageWithPackageInfo(view, REPOSITORY_NAME, BASIC_COMPONENT_NAME);
		});

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		changeJavaEditorView(view -> {
			Package componentPackage = claimPackage(view, BASIC_COMPONENT_NAME);
			view.getManipulationUtil().createClass(BASIC_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX, componentPackage,
					null);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			claimComponent(repository, BASIC_COMPONENT_NAME, BasicComponent.class);
		});
	}

	@ParameterizedTest()
	@EnumSource(value = Java2PcmUserSelection.class, names = { "SELECT_COMPOSITE_COMPONENT",
			"SELECT_NOTHING_DECIDE_LATER" })
	void testAddClassInSecondPackageAsCompositeComponent(Java2PcmUserSelection packageSelection) throws Exception {
		createRepositoryPackage();

		getUserInteraction().addNextSingleSelection(packageSelection.getSelection());
		changeJavaView(view -> {
			createPackageWithPackageInfo(view, REPOSITORY_NAME, COMPOSITE_COMPONENT_NAME);
		});

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_COMPONENT.getSelection());
		changeJavaEditorView(view -> {
			Package componentPackage = claimPackage(view, COMPOSITE_COMPONENT_NAME);
			view.getManipulationUtil().createClass(COMPOSITE_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX,
					componentPackage, null);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			claimComponent(repository, COMPOSITE_COMPONENT_NAME, CompositeComponent.class);
		});
	}

	@ParameterizedTest()
	@EnumSource(value = Java2PcmUserSelection.class, names = { "SELECT_SYSTEM", "SELECT_NOTHING_DECIDE_LATER" })
	void testAddClassInSecondPackageAsSystem(Java2PcmUserSelection packageSelection) throws Exception {
		createRepositoryPackage();

		getUserInteraction().addNextSingleSelection(packageSelection.getSelection());
		changeJavaView(view -> {
			createPackageWithPackageInfo(view, REPOSITORY_NAME, SYSTEM_NAME);
		});

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_SYSTEM.getSelection());
		changeJavaEditorView(view -> {
			Package systemPackage = claimPackage(view, SYSTEM_NAME);
			view.getManipulationUtil().createClass(SYSTEM_NAME + IMPLEMENTING_CLASS_SUFFIX, systemPackage, null);
		});

		validatePcmView(view -> {
			System system = claimSingleSystem(view);
			assertEquals(SYSTEM_NAME, system.getEntityName(), "system has wrong name");
		});
	}

	/**
	 * Test ii) class in non corresponding package --> should not be mapped to a
	 * Basic Component
	 */
	@Test
	void testAddClassInSecondPackageAsNone() throws Exception {
		createRepositoryPackage();

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_NOTHING_DECIDE_LATER.getSelection());
		changeJavaView(view -> {
			createPackageWithPackageInfo(view, REPOSITORY_NAME, BASIC_COMPONENT_NAME);
		});

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_NOTHING_DECIDE_LATER.getSelection());
		changeJavaEditorView(view -> {
			Package componentPackage = claimPackage(view, BASIC_COMPONENT_NAME);
			view.getManipulationUtil().createClass(BASIC_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX, componentPackage,
					null);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			assertTrue(repository.getComponents__Repository().isEmpty(),
					"there must not exist any contained Component");
			assertTrue(view.getRootObjects(System.class).isEmpty(), "there must not exist any System");
		});
	}

	@Test
	void testAddClassInDataTypesPackageAsCompositeDataType() throws Exception {
		createRepositoryPackage();

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.getSelection());
		changeJavaEditorView(view -> {
			Package dataTypesPackage = claimPackage(view, JavaQueryUtil.DATATYPES_PACKAGE);
			view.getManipulationUtil().createClass(BASIC_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX, dataTypesPackage,
					null);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			claimDataType(repository, BASIC_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX, CompositeDataType.class);
		});
	}

	@Test
	void testAddClassInDataTypesPackageAsNone() throws Exception {
		createRepositoryPackage();

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_NOTHING_DECIDE_LATER.getSelection());
		changeJavaEditorView(view -> {
			Package dataTypesPackage = claimPackage(view, JavaQueryUtil.DATATYPES_PACKAGE);
			view.getManipulationUtil().createClass(BASIC_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX, dataTypesPackage,
					null);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			assertEquals(0, repository.getDataTypes__Repository().size(), "repository must not have any data type");
		});
	}

	@ParameterizedTest()
	@EnumSource(value = Java2PcmUserSelection.class, names = { "SELECT_BASIC_COMPONENT",
			"SELECT_NOTHING_DECIDE_LATER" })
	void testRenameClassCorrespondingToBasicComponent(Java2PcmUserSelection packageSelection) throws Exception {
		testAddClassInSecondPackageAsBasicComponent(packageSelection);

		String changedName = BASIC_COMPONENT_NAME + RENAME;

		changeJavaEditorView(view -> {
			String componentClassName = BASIC_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX;
			Package componentPackage = claimPackage(view, BASIC_COMPONENT_NAME);
			ICompilationUnit componentClass = view.getManipulationUtil().claimCompilationUnit(componentClassName,
					componentPackage);

			int offset = componentClass.getBuffer().getContents().indexOf(componentClassName);
			ReplaceEdit renameEdit = new ReplaceEdit(offset, componentClassName.length(),
					changedName + IMPLEMENTING_CLASS_SUFFIX);
			view.getManipulationUtil().editCompilationUnit(componentClass, renameEdit);
			// TODO: resource URI is not adjusted to new name
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			claimComponent(repository, changedName, BasicComponent.class);
			assertEquals(1, repository.getComponents__Repository().size(), "too many components in repository");
		});
	}
}

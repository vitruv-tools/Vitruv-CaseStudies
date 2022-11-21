package tools.vitruv.applications.pcmjava.javaeditor.java2pcm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil.claimPackage;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimComponent;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimSingleRepository;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimSingleSystem;

import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmUserSelection;
import tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils;

public class ClassMappingTransformationTest extends Java2PcmTransformationTest {
	/**
	 * Class that in mapped package and same name as component + impl--> should be
	 * the new implementing class for the component
	 */
	@Test
	void testAddClassInSecondPackageAsBasicComponent() throws Exception {
		createRepositoryPackage();
		
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		changeJavaView(view -> {
			createPackageWithPackageInfo(view, Pcm2JavaTestUtils.REPOSITORY_NAME, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME);
		});
		
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		changeJavaEditorView(view -> {
			Package innerPackage = claimPackage(view, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME);
			view.getManipulationUtil().createClass(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPLEMENTING_CLASS_SUFFIX, innerPackage, null);
		});
		
		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			claimComponent(repository, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME, BasicComponent.class);
		});
	}
	
	@Test
	void testAddClassInSecondPackageAsCompositeComponent() throws Exception {
		createRepositoryPackage();
		
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_COMPONENT.getSelection());
		changeJavaView(view -> {
			createPackageWithPackageInfo(view, Pcm2JavaTestUtils.REPOSITORY_NAME, Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME);
		});
		
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_COMPONENT.getSelection());
		changeJavaEditorView(view -> {
			Package innerPackage = claimPackage(view, Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME);
			view.getManipulationUtil().createClass(Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME + Pcm2JavaTestUtils.IMPLEMENTING_CLASS_SUFFIX, innerPackage, null);
		});
		
		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			claimComponent(repository, Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME, CompositeComponent.class);
		});
	}
	
	@Test
	void testAddClassInSecondPackageAsSystem() throws Exception {
		createRepositoryPackage();
		
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_SYSTEM.getSelection());
		changeJavaView(view -> {
			createPackageWithPackageInfo(view, Pcm2JavaTestUtils.REPOSITORY_NAME, Pcm2JavaTestUtils.SYSTEM_NAME);
		});
		
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_SYSTEM.getSelection());
		changeJavaEditorView(view -> {
			Package innerPackage = claimPackage(view, Pcm2JavaTestUtils.SYSTEM_NAME);
			view.getManipulationUtil().createClass(Pcm2JavaTestUtils.SYSTEM_NAME + Pcm2JavaTestUtils.IMPLEMENTING_CLASS_SUFFIX, innerPackage, null);
		});
		
		validatePcmView(view -> {
			System system = claimSingleSystem(view);
			assertEquals(Pcm2JavaTestUtils.SYSTEM_NAME, system.getEntityName(), "system has wrong name");
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
			createPackageWithPackageInfo(view, Pcm2JavaTestUtils.REPOSITORY_NAME, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME);
		});
		
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_NOTHING_DECIDE_LATER.getSelection());
		changeJavaEditorView(view -> {
			Package innerPackage = claimPackage(view, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME);
			view.getManipulationUtil().createClass(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPLEMENTING_CLASS_SUFFIX, innerPackage, null);
		});
		
		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			assertTrue(repository.getComponents__Repository().isEmpty(), "there must not exist any contained Component");
			assertTrue(view.getRootObjects(System.class).isEmpty(), "there must not exist any System");
		});
	}
	
	//TODO: add more
}

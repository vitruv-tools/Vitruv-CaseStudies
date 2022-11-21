package tools.vitruv.applications.pcmjava.javaeditor.java2pcm;

import static tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil.claimPackage;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimComponent;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimSingleRepository;

import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmUserSelection;
import tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils;

class ClassMethodMappingTransformationTest extends Java2PcmTransformationTest {
	@Test
	void testCreateClassMethod() throws Exception {
		//TODO: adapt
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
			claimComponent(repository, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + "TODO", BasicComponent.class);
		});
	}
}

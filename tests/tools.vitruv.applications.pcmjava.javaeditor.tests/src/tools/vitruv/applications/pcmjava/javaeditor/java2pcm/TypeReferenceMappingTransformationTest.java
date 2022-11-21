package tools.vitruv.applications.pcmjava.javaeditor.java2pcm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil.claimPackage;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimComponent;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimInterface;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimNamedElement;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimSingleRepository;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.text.edits.InsertEdit;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmUserSelection;
import tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil;
import tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils;

class TypeReferenceMappingTransformationTest extends Java2PcmTransformationTest {
	@Test
	void testAddImplementsToClassCorrespondingToBasicComponent() throws Exception {
		createRepositoryPackage();

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		changeJavaView(view -> {
			createPackageWithPackageInfo(view, Pcm2JavaTestUtils.REPOSITORY_NAME,
					Pcm2JavaTestUtils.BASIC_COMPONENT_NAME);
		});

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		changeJavaEditorView(view -> {
			Package componentPackage = claimPackage(view, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME);
			view.getManipulationUtil().createClass(
					Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPLEMENTING_CLASS_SUFFIX,
					componentPackage, null);

			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			view.getManipulationUtil().createInterface(Pcm2JavaTestUtils.INTERFACE_NAME, contractsPackage, null);
		});

		changeJavaEditorView(view -> {
			Package componentPackage = claimPackage(view, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME);
			ICompilationUnit componentClass = view.getManipulationUtil().claimCompilationUnit(
					Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPLEMENTING_CLASS_SUFFIX,
					componentPackage);

			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			ICompilationUnit anInterface = view.getManipulationUtil()
					.claimCompilationUnit(Pcm2JavaTestUtils.INTERFACE_NAME, contractsPackage);
			view.getManipulationUtil().addImportToCompilationUnit(componentClass, anInterface,
					Pcm2JavaTestUtils.INTERFACE_NAME);

			IType classType = componentClass
					.getType(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPLEMENTING_CLASS_SUFFIX);
			String newSource = " implements " + Pcm2JavaTestUtils.INTERFACE_NAME;
			int offset = classType.getSourceRange().getOffset();
			int firstBracket = classType.getSource().indexOf("{");
			offset = offset + firstBracket - 1;
			final InsertEdit insertEdit = new InsertEdit(offset, newSource);
			view.getManipulationUtil().editCompilationUnit(componentClass, insertEdit);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			BasicComponent component = claimComponent(repository, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME,
					BasicComponent.class);
			OperationInterface anInterface = claimInterface(repository, Pcm2JavaTestUtils.INTERFACE_NAME,
					OperationInterface.class);
			OperationProvidedRole providedRole = claimNamedElement(
					component.getProvidedRoles_InterfaceProvidingEntity(),
					Pcm2JavaTestUtils.providesInterfaceName(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME,
							Pcm2JavaTestUtils.INTERFACE_NAME),
					OperationProvidedRole.class);
			assertEquals(anInterface, providedRole.getProvidedInterface__OperationProvidedRole(),
					"incorrect provided interface");
		});
	}
}

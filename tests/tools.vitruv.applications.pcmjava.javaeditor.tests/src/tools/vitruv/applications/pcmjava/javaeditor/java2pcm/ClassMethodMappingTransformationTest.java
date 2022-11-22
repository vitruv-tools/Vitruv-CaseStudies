package tools.vitruv.applications.pcmjava.javaeditor.java2pcm;

import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil.claimPackage;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimComponent;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimInterface;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimNamedElement;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimSingleRepository;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.BASIC_COMPONENT_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.IMPLEMENTING_CLASS_SUFFIX;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.INTERFACE_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.REPOSITORY_NAME;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.text.edits.InsertEdit;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmUserSelection;
import tools.vitruv.applications.pcmjava.javaeditor.java2pcm.legacy.CompilationUnitManipulatorHelper;
import tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil;
import tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils;

class ClassMethodMappingTransformationTest extends Java2PcmTransformationTest {
	@Test
	void testCreateClassMethod() throws Exception {
		createRepositoryPackage();

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		changeJavaView(view -> {
			createPackageWithPackageInfo(view, REPOSITORY_NAME, BASIC_COMPONENT_NAME);
		});

		// setup interface with one method
		// setup class corresponding to basic component, implementing interface
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		changeJavaEditorView(view -> {
			Package componentPackage = claimPackage(view, BASIC_COMPONENT_NAME);
			view.getManipulationUtil().createClass(BASIC_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX, componentPackage,
					null);
			ICompilationUnit componentClass = view.getManipulationUtil()
					.claimCompilationUnit(BASIC_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX, componentPackage);

			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			view.getManipulationUtil().createInterface(INTERFACE_NAME, contractsPackage, null);
			ICompilationUnit anInterface = view.getManipulationUtil().claimCompilationUnit(INTERFACE_NAME,
					contractsPackage);

			{
				String methodName = OPERATION_SIGNATURE_1_NAME;
				String methodSignature = "\nvoid " + methodName + "();\n";
				IType firstType = anInterface.getAllTypes()[0];
				int offset = CompilationUnitManipulatorHelper.getOffsetForClassifierManipulation(firstType);
				InsertEdit insertEdit = new InsertEdit(offset, methodSignature);
				view.getManipulationUtil().editCompilationUnit(anInterface, insertEdit);
			}

			{
				view.getManipulationUtil().addImportToCompilationUnit(componentClass, anInterface, INTERFACE_NAME);

				IType classType = componentClass.getType(BASIC_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX);
				String newSource = " implements " + INTERFACE_NAME;
				int offset = classType.getSourceRange().getOffset();
				int firstBracket = classType.getSource().indexOf("{");
				offset = offset + firstBracket - 1;
				final InsertEdit insertEdit = new InsertEdit(offset, newSource);
				view.getManipulationUtil().editCompilationUnit(componentClass, insertEdit);
			}
		});

		// add implementation of interface method to class
		changeJavaEditorView(view -> {
			Package componentPackage = claimPackage(view, BASIC_COMPONENT_NAME);
			ICompilationUnit componentClass = view.getManipulationUtil()
					.claimCompilationUnit(BASIC_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX, componentPackage);

			final String methodBlock = "\n\tpublic void " + OPERATION_SIGNATURE_1_NAME + " () {\n\t}\n";
			IType firstType = componentClass.getAllTypes()[0];
			int offset = CompilationUnitManipulatorHelper.getOffsetForClassifierManipulation(firstType);
			InsertEdit insertEdit = new InsertEdit(offset, methodBlock);
			view.getManipulationUtil().editCompilationUnit(componentClass, insertEdit);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			BasicComponent component = claimComponent(repository, BASIC_COMPONENT_NAME, BasicComponent.class);
			OperationInterface anInterface = claimInterface(repository, INTERFACE_NAME, OperationInterface.class);
			Signature signature = claimOne(anInterface.getSignatures__OperationInterface());
			claimNamedElement(component.getProvidedRoles_InterfaceProvidingEntity(),
					Pcm2JavaTestUtils.providesInterfaceName(BASIC_COMPONENT_NAME, INTERFACE_NAME),
					OperationProvidedRole.class);
			ServiceEffectSpecification seff = claimOne(component.getServiceEffectSpecifications__BasicComponent());
			assertEquals(signature, seff.getDescribedService__SEFF(), "SEFF is describing wrong service");
		});
	}
}

package tools.vitruv.applications.pcmjava.javaeditor.java2pcm;

import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil.claimPackage;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimInterface;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimNamedElement;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimSingleRepository;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.INTERFACE_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.PARAMETER_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.RENAME;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.text.edits.TextEdit;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil;
import tools.vitruv.applications.pcmjava.javaeditor.util.JavaTextEditFactory;

class ParameterMappingTransformationTest extends Java2PcmTransformationTest {
	private static final String PARAMETER_TYPE = "String";

	@Test
	void testAddParameter() throws Exception {
		createRepositoryPackage();

		changeJavaEditorView(view -> {
			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			view.getManipulationUtil().createInterface(INTERFACE_NAME, contractsPackage, null);
			ICompilationUnit anInterface = view.getManipulationUtil().claimCompilationUnit(INTERFACE_NAME,
					contractsPackage);
			IType interfaceType = anInterface.getType(INTERFACE_NAME);

			TextEdit addMethodEdit = JavaTextEditFactory.addMethodSignature(interfaceType, "void",
					OPERATION_SIGNATURE_1_NAME);
			view.getManipulationUtil().editCompilationUnit(anInterface, addMethodEdit);
		});

		changeJavaEditorView(view -> {
			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			ICompilationUnit anInterface = view.getManipulationUtil().claimCompilationUnit(INTERFACE_NAME,
					contractsPackage);
			IType interfaceType = anInterface.getType(INTERFACE_NAME);

			TextEdit addParameterEdit = JavaTextEditFactory.addMethodParameter(interfaceType,
					OPERATION_SIGNATURE_1_NAME, PARAMETER_NAME, PARAMETER_TYPE);
			view.getManipulationUtil().editCompilationUnit(anInterface, addParameterEdit);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			OperationInterface anInterface = claimInterface(repository, INTERFACE_NAME, OperationInterface.class);
			OperationSignature operationSignature = claimNamedElement(anInterface.getSignatures__OperationInterface(),
					OPERATION_SIGNATURE_1_NAME);
			Parameter parameter = claimOne(operationSignature.getParameters__OperationSignature());
			assertEquals(PARAMETER_NAME, parameter.getParameterName(), "invalid parameter name");
			assertEquals(PARAMETER_TYPE, getTypeNameOfPcmDataType(parameter.getDataType__Parameter()),
					"invalid parameter type");
		});
	}

	@Test
	void testRenameParameter() throws Exception {
		testAddParameter();

		String changedParameterName = PARAMETER_NAME + RENAME;

		changeJavaEditorView(view -> {
			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			ICompilationUnit anInterface = view.getManipulationUtil().claimCompilationUnit(INTERFACE_NAME,
					contractsPackage);
			IType interfaceType = anInterface.getType(INTERFACE_NAME);

			TextEdit renameParameterEdit = JavaTextEditFactory.renameMethodParameter(interfaceType,
					OPERATION_SIGNATURE_1_NAME, PARAMETER_NAME, changedParameterName);
			view.getManipulationUtil().editCompilationUnit(anInterface, renameParameterEdit);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			OperationInterface anInterface = claimInterface(repository, INTERFACE_NAME, OperationInterface.class);
			OperationSignature operationSignature = claimNamedElement(anInterface.getSignatures__OperationInterface(),
					OPERATION_SIGNATURE_1_NAME);
			Parameter parameter = claimOne(operationSignature.getParameters__OperationSignature());
			assertEquals(changedParameterName, parameter.getParameterName(), "invalid parameter name");
			assertEquals(PARAMETER_TYPE, getTypeNameOfPcmDataType(parameter.getDataType__Parameter()),
					"invalid parameter type");
		});
	}

	@Test
	void testChangeParameterType() throws Exception {
		testAddParameter();

		String changedParameterType = "int";

		changeJavaEditorView(view -> {
			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			ICompilationUnit anInterface = view.getManipulationUtil().claimCompilationUnit(INTERFACE_NAME,
					contractsPackage);
			IType interfaceType = anInterface.getType(INTERFACE_NAME);

			TextEdit changeParameterTypeEdit = JavaTextEditFactory.changeParameterType(interfaceType,
					OPERATION_SIGNATURE_1_NAME, PARAMETER_NAME, changedParameterType);
			view.getManipulationUtil().editCompilationUnit(anInterface, changeParameterTypeEdit);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			OperationInterface anInterface = claimInterface(repository, INTERFACE_NAME, OperationInterface.class);
			OperationSignature operationSignature = claimNamedElement(anInterface.getSignatures__OperationInterface(),
					OPERATION_SIGNATURE_1_NAME);
			Parameter parameter = claimOne(operationSignature.getParameters__OperationSignature());
			assertEquals(PARAMETER_NAME, parameter.getParameterName(), "invalid parameter name");
			assertEquals(changedParameterType, getTypeNameOfPcmDataType(parameter.getDataType__Parameter()),
					"invalid parameter type");
		});
	}
}

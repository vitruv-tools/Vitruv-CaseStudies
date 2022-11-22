package tools.vitruv.applications.pcmjava.javaeditor.java2pcm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil.claimPackage;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimInterface;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimNamedElement;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimSingleRepository;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.INTERFACE_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.RENAME;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.javaeditor.java2pcm.legacy.CompilationUnitManipulatorHelper;
import tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil;

class MethodMappingTransformationTest extends Java2PcmTransformationTest {
	@Test
	void testAddMethod() throws Exception {
		createRepositoryPackage();

		changeJavaEditorView(view -> {
			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			view.getManipulationUtil().createInterface(INTERFACE_NAME, contractsPackage, null);
		});

		String returnType = "void";

		changeJavaEditorView(view -> {
			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			ICompilationUnit anInterface = view.getManipulationUtil().claimCompilationUnit(INTERFACE_NAME,
					contractsPackage);

			String methodName = OPERATION_SIGNATURE_1_NAME;
			String methodSignature = "\n" + returnType + " " + methodName + "();\n";

			IType firstType = anInterface.getAllTypes()[0];
			int offset = CompilationUnitManipulatorHelper.getOffsetForClassifierManipulation(firstType);
			InsertEdit insertEdit = new InsertEdit(offset, methodSignature);
			view.getManipulationUtil().editCompilationUnit(anInterface, insertEdit);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			OperationInterface operationInterface = claimInterface(repository, INTERFACE_NAME,
					OperationInterface.class);
			OperationSignature operationSignature = claimNamedElement(
					operationInterface.getSignatures__OperationInterface(), OPERATION_SIGNATURE_1_NAME);
			assertEquals(0, operationSignature.getParameters__OperationSignature().size());
			assertEquals(returnType, getTypeNameOfPcmDataType(operationSignature.getReturnType__OperationSignature()));
		});
	}

	@Test
	void testRenameMethod() throws Exception {
		testAddMethod();

		changeJavaEditorView(view -> {
			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			ICompilationUnit anInterface = view.getManipulationUtil().claimCompilationUnit(INTERFACE_NAME,
					contractsPackage);

			IMethod iMethod = anInterface.getType(INTERFACE_NAME).getMethod(OPERATION_SIGNATURE_1_NAME, null);
			int offset = iMethod.getNameRange().getOffset();
			int length = iMethod.getNameRange().getLength();
			String newMethodName = OPERATION_SIGNATURE_1_NAME + RENAME;
			ReplaceEdit renameEdit = new ReplaceEdit(offset, length, newMethodName);
			view.getManipulationUtil().editCompilationUnit(anInterface, renameEdit);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			OperationInterface operationInterface = claimInterface(repository, INTERFACE_NAME,
					OperationInterface.class);
			claimNamedElement(operationInterface.getSignatures__OperationInterface(),
					OPERATION_SIGNATURE_1_NAME + RENAME);
			assertEquals(1, operationInterface.getSignatures__OperationInterface().size(),
					"too many operation signatures");
		});
	}

	@Test
	void testAddReturnType() throws Exception {
		testAddMethod();

		String changedReturnType = "String";

		changeJavaEditorView(view -> {
			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			ICompilationUnit anInterface = view.getManipulationUtil().claimCompilationUnit(INTERFACE_NAME,
					contractsPackage);

			IMethod iMethod = anInterface.getType(INTERFACE_NAME).getMethod(OPERATION_SIGNATURE_1_NAME, null);
			int returnTypeOffset = iMethod.getSourceRange().getOffset();
			String retTypeName = iMethod.getSource().split(" ")[0];
			int returnTypeLength = retTypeName.length();
			DeleteEdit deleteEdit = new DeleteEdit(returnTypeOffset, returnTypeLength);
			InsertEdit insertEdit = new InsertEdit(returnTypeOffset, changedReturnType);
			view.getManipulationUtil().editCompilationUnit(anInterface, deleteEdit, insertEdit);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			OperationInterface operationInterface = claimInterface(repository, INTERFACE_NAME,
					OperationInterface.class);
			OperationSignature operationSignature = claimNamedElement(
					operationInterface.getSignatures__OperationInterface(), OPERATION_SIGNATURE_1_NAME);
			assertEquals(0, operationSignature.getParameters__OperationSignature().size());
			assertEquals(changedReturnType,
					getTypeNameOfPcmDataType(operationSignature.getReturnType__OperationSignature()));
		});
	}
}

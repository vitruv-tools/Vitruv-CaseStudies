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
import org.eclipse.jdt.core.IType;
import org.eclipse.text.edits.TextEdit;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil;
import tools.vitruv.applications.pcmjava.javaeditor.util.JavaTextEditFactory;

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
			IType interfaceType = anInterface.getType(INTERFACE_NAME);

			TextEdit addMethodEdit = JavaTextEditFactory.addMethodSignature(interfaceType, returnType,
					OPERATION_SIGNATURE_1_NAME);
			view.getManipulationUtil().editCompilationUnit(anInterface, addMethodEdit);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			OperationInterface operationInterface = claimInterface(repository, INTERFACE_NAME,
					OperationInterface.class);
			OperationSignature operationSignature = claimNamedElement(
					operationInterface.getSignatures__OperationInterface(), OPERATION_SIGNATURE_1_NAME);
			assertEquals(0, operationSignature.getParameters__OperationSignature().size(),
					"signature must not have any parameters");
			assertEquals(returnType, getTypeNameOfPcmDataType(operationSignature.getReturnType__OperationSignature()),
					"signature has wrong return type");
		});
	}

	@Test
	void testRenameMethod() throws Exception {
		testAddMethod();

		changeJavaEditorView(view -> {
			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			ICompilationUnit anInterface = view.getManipulationUtil().claimCompilationUnit(INTERFACE_NAME,
					contractsPackage);
			IType interfaceType = anInterface.getType(INTERFACE_NAME);

			TextEdit renameMethodEdit = JavaTextEditFactory.renameMethod(interfaceType, OPERATION_SIGNATURE_1_NAME,
					OPERATION_SIGNATURE_1_NAME + RENAME);
			view.getManipulationUtil().editCompilationUnit(anInterface, renameMethodEdit);
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
			IType interfaceType = anInterface.getType(INTERFACE_NAME);
			
			TextEdit changeReturnTypeEdit = JavaTextEditFactory.changeReturnTypeOfMethod(interfaceType, OPERATION_SIGNATURE_1_NAME, changedReturnType);
			view.getManipulationUtil().editCompilationUnit(anInterface, changeReturnTypeEdit);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			OperationInterface operationInterface = claimInterface(repository, INTERFACE_NAME,
					OperationInterface.class);
			OperationSignature operationSignature = claimNamedElement(
					operationInterface.getSignatures__OperationInterface(), OPERATION_SIGNATURE_1_NAME);
			assertEquals(0, operationSignature.getParameters__OperationSignature().size(), "signature must not have any parameters");
			assertEquals(changedReturnType,
					getTypeNameOfPcmDataType(operationSignature.getReturnType__OperationSignature()), "signature has wrong return type");
		});
	}
}

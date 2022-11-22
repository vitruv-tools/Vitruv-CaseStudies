package tools.vitruv.applications.pcmjava.javaeditor.java2pcm;

import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil.claimPackage;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimInterface;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimNamedElement;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimSingleRepository;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.javaeditor.java2pcm.legacy.CompilationUnitManipulatorHelper;
import tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil;
import tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils;

class ParameterMappingTransformationTest extends Java2PcmTransformationTest {
	private static final String PARAMETER_TYPE = "String";
	
	@Test
	void testAddParameter() throws Exception {
		createRepositoryPackage();
		
		changeJavaEditorView(view -> {
			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			view.getManipulationUtil().createInterface(Pcm2JavaTestUtils.INTERFACE_NAME, contractsPackage, null);
			ICompilationUnit anInterface = view.getManipulationUtil().claimCompilationUnit(Pcm2JavaTestUtils.INTERFACE_NAME, contractsPackage);
			
			String methodName = Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME;
			String returnType = "void";
			String methodSignature = "\n" + returnType + " " + methodName + "();\n";
			
			IType firstType = anInterface.getAllTypes()[0];
			int offset = CompilationUnitManipulatorHelper.getOffsetForClassifierManipulation(firstType);
			InsertEdit insertEdit = new InsertEdit(offset, methodSignature);
			view.getManipulationUtil().editCompilationUnit(anInterface, insertEdit);
		});
		
		changeJavaEditorView(view -> {
			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			ICompilationUnit anInterface = view.getManipulationUtil().claimCompilationUnit(Pcm2JavaTestUtils.INTERFACE_NAME, contractsPackage);
			IMethod iMethod = anInterface.getType(Pcm2JavaTestUtils.INTERFACE_NAME).getMethod(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, null);
			String parameterString = PARAMETER_TYPE + " " + Pcm2JavaTestUtils.PARAMETER_NAME;
			int offset = iMethod.getSourceRange().getOffset() + iMethod.getSourceRange().getLength() - 2;
			InsertEdit insertEdit = new InsertEdit(offset, parameterString);
			view.getManipulationUtil().editCompilationUnit(anInterface, insertEdit);
		});
		
		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			OperationInterface anInterface = claimInterface(repository, Pcm2JavaTestUtils.INTERFACE_NAME,
					OperationInterface.class);
			OperationSignature operationSignature = claimNamedElement(anInterface.getSignatures__OperationInterface(), Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME);
			Parameter parameter = claimOne(operationSignature.getParameters__OperationSignature());
			assertEquals(Pcm2JavaTestUtils.PARAMETER_NAME, parameter.getParameterName(), "invalid parameter name");
			assertEquals(PARAMETER_TYPE, getTypeNameOfPcmDataType(parameter.getDataType__Parameter()), "invalid parameter type");
		});
	}
	
	@Test
	void testRenameParameter() throws Exception {
		testAddParameter();
		
		String changedParameterName = Pcm2JavaTestUtils.PARAMETER_NAME + Pcm2JavaTestUtils.RENAME;
		
		changeJavaEditorView(view -> {
			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			ICompilationUnit anInterface = view.getManipulationUtil().claimCompilationUnit(Pcm2JavaTestUtils.INTERFACE_NAME, contractsPackage);
			IMethod iMethod = findIMethodByName(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, anInterface.getType(Pcm2JavaTestUtils.INTERFACE_NAME));
			ILocalVariable parameter = this.findParameterInIMethod(iMethod, Pcm2JavaTestUtils.PARAMETER_NAME);
			String typeName = parameter.getSource().split(" ")[0];
			String paramName = parameter.getSource().split(" ")[1];
			int offset = parameter.getSourceRange().getOffset() + typeName.length() + 1;
			int length = paramName.length();
			ReplaceEdit replaceEdit = new ReplaceEdit(offset, length, changedParameterName);
			view.getManipulationUtil().editCompilationUnit(anInterface, replaceEdit);
		});
		
		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			OperationInterface anInterface = claimInterface(repository, Pcm2JavaTestUtils.INTERFACE_NAME,
					OperationInterface.class);
			OperationSignature operationSignature = claimNamedElement(anInterface.getSignatures__OperationInterface(), Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME);
			Parameter parameter = claimOne(operationSignature.getParameters__OperationSignature());
			assertEquals(changedParameterName, parameter.getParameterName(), "invalid parameter name");
			assertEquals(PARAMETER_TYPE, getTypeNameOfPcmDataType(parameter.getDataType__Parameter()), "invalid parameter type");
		});
	}
	
	@Test
	void testChangeParameterType() throws Exception {
		testAddParameter();
		
		String changedParameterType = "int";
		
		changeJavaEditorView(view -> {
			Package contractsPackage = claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			ICompilationUnit anInterface = view.getManipulationUtil().claimCompilationUnit(Pcm2JavaTestUtils.INTERFACE_NAME, contractsPackage);
			IMethod iMethod = findIMethodByName(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, anInterface.getType(Pcm2JavaTestUtils.INTERFACE_NAME));
			ILocalVariable parameter = this.findParameterInIMethod(iMethod, Pcm2JavaTestUtils.PARAMETER_NAME);
			int offset = parameter.getSourceRange().getOffset();
			int length = parameter.getSource().split(" ")[0].length();
			ReplaceEdit replaceEdit = new ReplaceEdit(offset, length, changedParameterType);
			view.getManipulationUtil().editCompilationUnit(anInterface, replaceEdit);
		});
		
		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			OperationInterface anInterface = claimInterface(repository, Pcm2JavaTestUtils.INTERFACE_NAME,
					OperationInterface.class);
			OperationSignature operationSignature = claimNamedElement(anInterface.getSignatures__OperationInterface(), Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME);
			Parameter parameter = claimOne(operationSignature.getParameters__OperationSignature());
			assertEquals(Pcm2JavaTestUtils.PARAMETER_NAME, parameter.getParameterName(), "invalid parameter name");
			assertEquals(changedParameterType, getTypeNameOfPcmDataType(parameter.getDataType__Parameter()), "invalid parameter type");
		});
	}
	
	protected IMethod findIMethodByName(final String methodName, final IType type)
			throws JavaModelException {
		for (final IMethod method : type.getMethods()) {
			if (method.getElementName().equals(methodName)) {
				return method;
			}
		}
		throw new RuntimeException("Method " + methodName + " not found in type " + type);
	}
	
	private ILocalVariable findParameterInIMethod(final IMethod iMethod, final String parameterName)
			throws JavaModelException {
		for (final ILocalVariable localVariable : iMethod.getParameters()) {
			if (localVariable.getElementName().equals(parameterName)) {
				return localVariable;
			}
		}
		throw new RuntimeException("Old parameter with name " + parameterName + " not found");
	}
}

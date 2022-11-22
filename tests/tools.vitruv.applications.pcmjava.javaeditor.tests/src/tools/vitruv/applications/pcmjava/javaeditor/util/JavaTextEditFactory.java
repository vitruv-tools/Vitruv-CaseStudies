package tools.vitruv.applications.pcmjava.javaeditor.util;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

/**
 * Factory method to encapsulate all kinds of editing operations directly on the
 * Java source code.
 */
public class JavaTextEditFactory {
	public enum Visibility {
		PUBLIC("public "), PRIVATE("private "), PROTECTED("");

		private String codeRepresentation;

		public String getCodeRepresentation() {
			return this.codeRepresentation;
		}

		private Visibility(String codeRepresentation) {
			this.codeRepresentation = codeRepresentation;
		}
	}

	private JavaTextEditFactory() {
	}

	// === COMPILATION UNIT ===

	public static TextEdit renameCompilationUnit(ICompilationUnit compilationUnit, String previousName, String newName)
			throws JavaModelException {
		int offset = compilationUnit.getBuffer().getContents().indexOf(previousName);
		return new ReplaceEdit(offset, previousName.length(), newName);
		// TODO: resource URI is not adjusted to new name
	}

	public static TextEdit addImplementsRelation(IType implementingType, String interfaceName)
			throws JavaModelException {
		String newSource = " implements " + interfaceName;
		int offset = implementingType.getSourceRange().getOffset();
		int firstBracket = implementingType.getSource().indexOf("{");
		offset = offset + firstBracket - 1;
		return new InsertEdit(offset, newSource);
	}

	// === METHOD ===

	public static TextEdit addMethodWithEmptyBody(IType type, Visibility visibility, String returnType,
			String methodName) throws JavaModelException {
		String methodBlock = "\n\t" + visibility.getCodeRepresentation() + returnType + " " + methodName + "() {\n\t}\n";
		int offset = getOffsetForClassifierManipulation(type);
		return new InsertEdit(offset, methodBlock);
	}

	public static TextEdit addMethodSignature(IType type, String returnType, String methodName)
			throws JavaModelException {
		String methodSignature = "\n" + returnType + " " + methodName + "();\n";
		int offset = getOffsetForClassifierManipulation(type);
		return new InsertEdit(offset, methodSignature);
	}

	public static TextEdit renameMethod(IType type, String oldMethodName, String newMethodName)
			throws JavaModelException {
		IMethod iMethod = getMethodByName(type, oldMethodName);
		int offset = iMethod.getNameRange().getOffset();
		int length = iMethod.getNameRange().getLength();
		return new ReplaceEdit(offset, length, newMethodName);
	}

	public static TextEdit changeReturnTypeOfMethod(IType type, String methodName, String newReturnType)
			throws JavaModelException {
		IMethod iMethod = getMethodByName(type, methodName);
		int offset = iMethod.getSourceRange().getOffset();
		String oldReturnType = iMethod.getSource().split(" ")[0];
		int returnTypeLength = oldReturnType.length();
		return new ReplaceEdit(offset, returnTypeLength, newReturnType);
	}

	// === METHOD PARAMETER ===

	public static TextEdit addMethodParameter(IType type, String methodName, String parameterName, String parameterType)
			throws JavaModelException {
		IMethod iMethod = getMethodByName(type, methodName);
		String parameterString = parameterType + " " + parameterName;
		int offset = iMethod.getSourceRange().getOffset() + iMethod.getSourceRange().getLength() - 2;
		return new InsertEdit(offset, parameterString);
	}

	public static TextEdit renameMethodParameter(IType type, String methodName, String oldParameterName,
			String newParameterName) throws JavaModelException {
		IMethod iMethod = getMethodByName(type, methodName);
		ILocalVariable parameter = getParameterByName(iMethod, oldParameterName);
		String typeName = parameter.getSource().split(" ")[0];
		String paramName = parameter.getSource().split(" ")[1];
		int offset = parameter.getSourceRange().getOffset() + typeName.length() + 1;
		int length = paramName.length();
		return new ReplaceEdit(offset, length, newParameterName);
	}

	public static TextEdit changeParameterType(IType type, String methodName, String parameterName, String newType)
			throws JavaModelException {
		IMethod iMethod = getMethodByName(type, methodName);
		ILocalVariable parameter = getParameterByName(iMethod, parameterName);
		int offset = parameter.getSourceRange().getOffset();
		int length = parameter.getSource().split(" ")[0].length();
		return new ReplaceEdit(offset, length, newType);
	}

	// === FIELDS ===

	public static TextEdit addField(IType type, Visibility visibility, String fieldName, String fieldType)
			throws JavaModelException {
		int offset = getOffsetForClassifierManipulation(type);
		String fieldStr = visibility.getCodeRepresentation() + fieldType + " " + fieldName + ";";
		return new InsertEdit(offset, fieldStr);
	}

	public static TextEdit renameField(IType type, String previousName, String newName) throws JavaModelException {
		IField fieldToRename = type.getField(previousName);
		String fieldToRenameString = fieldToRename.getSource();
		String fieldToRenameType = fieldToRenameString.split(" ")[1];
		String fieldToRenameName = fieldToRenameString.split(" ")[2];
		int offset = fieldToRename.getSourceRange().getOffset() + fieldToRenameString.indexOf(fieldToRenameType)
				+ fieldToRenameType.length() + 1;
		int lengthToDelete = fieldToRenameName.length();
		return new ReplaceEdit(offset, lengthToDelete, newName + ";");
	}

	public static TextEdit changeTypeOfField(IType type, String fieldName, String newType) throws JavaModelException {
		IField fieldToRename = type.getField(fieldName);
		String fieldSrc = fieldToRename.getSource();
		String fieldType = fieldSrc.split(" ")[1];
		int offset = fieldToRename.getSourceRange().getOffset() + fieldSrc.indexOf(fieldType);
		int lengthToDelete = fieldType.length();
		return new ReplaceEdit(offset, lengthToDelete, newType);
	}

	// === Helpers ===

	private static int getOffsetForClassifierManipulation(IType firstType) throws JavaModelException {
		int posOfFirstBracket = firstType.getCompilationUnit().getSource().indexOf("{");
		return posOfFirstBracket + 1;
	}

	private static IMethod getMethodByName(IType type, String methodName) throws JavaModelException {
		for (IMethod method : type.getMethods()) {
			if (method.getElementName().equals(methodName)) {
				return method;
			}
		}
		throw new IllegalStateException("Method " + methodName + " not found in type " + type);
	}

	private static ILocalVariable getParameterByName(IMethod iMethod, String parameterName) throws JavaModelException {
		for (ILocalVariable localVariable : iMethod.getParameters()) {
			if (localVariable.getElementName().equals(parameterName)) {
				return localVariable;
			}
		}
		throw new IllegalStateException("Old parameter with name " + parameterName + " not found");
	}
}

package tools.vitruv.applications.pcmjava.javaeditor.java2pcm;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * Helper class that allows the manipulation of compilation units that causes a
 * notification of the Java monitor.
 *
 * @author langhamm
 *
 */
public final class CompilationUnitManipulatorHelper {

	private CompilationUnitManipulatorHelper() {
	}

	public static String ensureJavaFileExtension(String entityName) {
		if (!entityName.endsWith(".java")) {
			entityName = entityName + ".java";
		}
		return entityName;
	}

	public static ICompilationUnit findICompilationUnitWithClassName(String entityName,
			final IProject currentTestProject) throws JavaModelException {
		entityName = CompilationUnitManipulatorHelper.ensureJavaFileExtension(entityName);
		final IJavaProject javaProject = JavaCore.create(currentTestProject);
		for (final IPackageFragmentRoot packageFragmentRoot : javaProject.getPackageFragmentRoots()) {
			final IJavaElement[] children = packageFragmentRoot.getChildren();
			for (final IJavaElement iJavaElement : children) {
				if (iJavaElement instanceof IPackageFragment) {
					final IPackageFragment fragment = (IPackageFragment) iJavaElement;
					final IJavaElement[] javaElements = fragment.getChildren();
					for (int k = 0; k < javaElements.length; k++) {
						final IJavaElement javaElement = javaElements[k];
						if (javaElement.getElementType() == IJavaElement.COMPILATION_UNIT) {
							final ICompilationUnit compilationUnit = (ICompilationUnit) javaElement;
							if (compilationUnit.getElementName().equals(entityName)) {
								return compilationUnit;
							}
						}
					}
				}
			}
		}
		throw new RuntimeException("Could not find a compilation unit with name " + entityName);
	}

	public static int getOffsetForClassifierManipulation(final IType firstType) throws JavaModelException {
		final int posOfFirstBracket = firstType.getCompilationUnit().getSource().indexOf("{");
		return posOfFirstBracket + 1;
	}

	public static int getOffsetForAddingAnntationToClass(final IType firstType) throws JavaModelException {
		final int posOfFirstBracket = firstType.getCompilationUnit().getSource().indexOf("public");
		return posOfFirstBracket - 1;
	}

	public static int getOffsetForAddingAnntationToField(final IType type, final String fieldName)
			throws JavaModelException {
		for (final IField iField : type.getFields()) {
			if (iField.getElementName().equals(fieldName)) {
				return iField.getSourceRange().getOffset();
			}
		}
		throw new RuntimeException("Could not find field " + fieldName + " in class " + type.getElementName());
	}
}

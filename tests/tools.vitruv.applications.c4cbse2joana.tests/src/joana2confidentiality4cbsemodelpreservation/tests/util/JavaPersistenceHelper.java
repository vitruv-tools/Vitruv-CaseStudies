package joana2confidentiality4cbsemodelpreservation.tests.util;

import java.util.Arrays;
import tools.mdsd.jamopp.model.java.containers.CompilationUnit;
import tools.mdsd.jamopp.model.java.containers.Package;
import static com.google.common.base.Preconditions.checkState;

class JavaPersistenceHelper {

	static final String UNNAMED_PACKAGE = "unnamedPackage";

	public static final String PATH_SEPARATOR = "/";
	public static final String FILE_EXTENSION_SEPARATOR = ".";
	public static final String JAVA_FILE_EXTENSION = "java";
	static final String FILE_EXTENSION_WITH_SEPARATOR = FILE_EXTENSION_SEPARATOR + JAVA_FILE_EXTENSION;

	static String getJavaProjectSrc() {
		return "src" + PATH_SEPARATOR;
	}

	static String getPackageInfoClassName() {
		return "package-info";
	}

	// Ensures that the source path is either empty or ends with '/'.
	private static String normalizeSourcePath(String sourcePath) {
		if (sourcePath == null || sourcePath.isEmpty()) return "";
		if (sourcePath.endsWith(PATH_SEPARATOR)) {
			return sourcePath;
		} else {
			return sourcePath + PATH_SEPARATOR;
		}
	}

	static String stripJavaSourcePath(String javaFilePath, String sourcePath) {
		if (javaFilePath == null || javaFilePath.isEmpty()) return javaFilePath; // null -> null, '' -> ''

		// Get and return the path relative to the (normalized) source path:
		String normalizedSourcePath = normalizeSourcePath(sourcePath);
		if (!normalizedSourcePath.isEmpty() && javaFilePath.startsWith(normalizedSourcePath)) {
			return javaFilePath.substring(normalizedSourcePath.length());
		} else {
			// Ensure that the returned path is relative:
			if (javaFilePath.startsWith(PATH_SEPARATOR)) {
				return javaFilePath.substring(PATH_SEPARATOR.length());
			} else {
				return javaFilePath;
			}
		}
	}
	
	static String getSimpleName(CompilationUnit compilationUnit) {
		var simpleName = compilationUnit.getName();
		checkState(simpleName.endsWith(FILE_EXTENSION_WITH_SEPARATOR), "compilation unit has to end with '%s' but doesn't: %s", FILE_EXTENSION_WITH_SEPARATOR, compilationUnit.name);
		simpleName = simpleName.substring(0, simpleName.length() - 5);
		simpleName = simpleName.substring(simpleName.lastIndexOf('.') + 1);
		return simpleName;
	}
	
	static String buildJavaPath(String sourcePath, Iterable<String> namespaces) {
		return buildJavaFilePath(sourcePath, namespaces, "", "");
	}

	// fileExtension can be empty and is then omitted.
	static String buildJavaFilePath(String sourcePath, Iterable<String> namespaces, String fileName,
		String fileExtension) {
		return '''«sourcePath.normalizeSourcePath»«
			FOR namespace : namespaces SEPARATOR PATH_SEPARATOR AFTER PATH_SEPARATOR»«namespace»«ENDFOR»«
			fileName»«
			IF !fileExtension.nullOrEmpty»«FILE_EXTENSION_SEPARATOR»«fileExtension»«ENDIF»''';
	}

	// Uses 'src/' as source path.
	// The fileName is simply appended to the end of the file path. It may or may not contain additional path segments
	// and may or may not include the file extension.
	static String buildJavaFilePath(String fileName, Iterable<String> namespaces) {
		return buildJavaFilePath(getJavaProjectSrc(), namespaces, fileName, null);
	}

	// Uses 'src/' as source path.
	static String buildJavaFilePath(Package javaPackage) {
		return buildJavaPackageFilePath(getJavaProjectSrc(), javaPackage.getNamespaces(), javaPackage.getName());
	}

	static String buildJavaPackageFilePath(String sourcePath, Iterable<String> namespaces,
		String packageName) {
		String fullNamespaces = getFullPackageNamespaces(namespaces, packageName);
		return buildJavaFilePath(sourcePath, fullNamespaces, getPackageInfoClassName(), JAVA_FILE_EXTENSION);
	}

	static String buildJavaPackagePath(String sourcePath, Iterable<String> namespaces,
		String packageName) {
		String fullNamespaces = getFullPackageNamespaces(namespaces, packageName);
		return buildJavaPath(sourcePath, fullNamespaces);
	}

	private static String getFullPackageNamespaces(Iterable<String> namespaces, String packageName) {
		return namespaces + Arrays.asList(packageName ?: UNNAMED_PACKAGE);
	}
}

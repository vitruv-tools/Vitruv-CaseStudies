package tools.vitruv.applications.cbs.commonalities.tests.util.java;

import static tools.vitruv.applications.cbs.commonalities.tests.util.common.FilePathUtil.EMPTY_PATH_SEGMENTS;
import static tools.vitruv.applications.cbs.commonalities.tests.util.common.FilePathUtil.FILE_EXTENSION_SEPARATOR;
import static tools.vitruv.applications.cbs.commonalities.tests.util.common.FilePathUtil.appendFile;
import static tools.vitruv.applications.cbs.commonalities.tests.util.common.FilePathUtil.appendPath;
import static tools.vitruv.applications.cbs.commonalities.tests.util.common.FilePathUtil.appendPathSegments;
import static tools.vitruv.applications.cbs.commonalities.tests.util.common.FilePathUtil.toPath;

import java.util.List;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper;

// TODO Move this into JavaPersistenceHelper? However, we also need to move
// FilePathUtil to some common place first.
public final class JavaFilePathHelper {

	private static final String JAVA_SOURCE_PATH = JavaPersistenceHelper.getJavaProjectSrc();
	private static final String JAVA_PACKAGE_SEPARATOR = ".";
	private static final String JAVA_FILE_EXTENSION = JavaPersistenceHelper.JAVA_FILE_EXTENSION;
	private static final String JAVA_FULL_FILE_EXTENSION = FILE_EXTENSION_SEPARATOR + JAVA_FILE_EXTENSION;
	private static final String JAVA_PACKAGE_INFO_CLASS_NAME = JavaPersistenceHelper.getPackageInfoClassName();

	private JavaFilePathHelper() {
	}

	// The resulting path is relative to the Java source folder.
	public static String javaFilePath(List<String> namespaces, String fileName) {
		return appendFile(appendPath(JAVA_SOURCE_PATH, toPath(namespaces)), fileName, JAVA_FILE_EXTENSION);
	}

	public static String javaFilePath(String fileName) {
		return javaFilePath(EMPTY_PATH_SEGMENTS, fileName);
	}

	public static String javaPackageFilePath(List<String> namespaces, String packageName) {
		return javaFilePath(appendPathSegments(namespaces, packageName), JAVA_PACKAGE_INFO_CLASS_NAME);
	}

	public static String javaPackageFilePath(Package javaPackage) {
		return javaPackageFilePath(javaPackage.getNamespaces(), javaPackage.getName());
	}

	// TODO duplication with JavaCompilationUnitNameOperator
	// CompilationUnit name schema: '<dot-separated-namespaces>.<fileName>.java'
	public static String getCompilationUnitName(Iterable<String> namespaces, String fileName) {
		StringBuilder compilationUnitNameBuilder = new StringBuilder();
		if (namespaces != null && namespaces.iterator().hasNext()) {
			compilationUnitNameBuilder.append(String.join(JAVA_PACKAGE_SEPARATOR, namespaces));
		}
		if (fileName != null && !fileName.isEmpty()) {
			if (compilationUnitNameBuilder.length() > 0) {
				compilationUnitNameBuilder.append(JAVA_PACKAGE_SEPARATOR);
			}
			compilationUnitNameBuilder.append(fileName);
			compilationUnitNameBuilder.append(FILE_EXTENSION_SEPARATOR);
			compilationUnitNameBuilder.append(JAVA_FILE_EXTENSION);
		}
		return compilationUnitNameBuilder.toString();
	}

	// TODO duplication with JavaCompilationUnitNameOperator
	// CompilationUnit name schema: '<dot-separated-namespaces>.<fileName>.java'
	private static String getFileNameFromCompilationUnitName(String compilationUnitName) {
		if (compilationUnitName == null) {
			return null;
		}
		int fileExtensionStartIndex; // inclusive
		if (compilationUnitName.endsWith(JAVA_FULL_FILE_EXTENSION)) {
			fileExtensionStartIndex = compilationUnitName.length() - JAVA_FULL_FILE_EXTENSION.length();
		} else {
			// The file extension is missing:
			fileExtensionStartIndex = compilationUnitName.length();
		}
		// fileNameStartIndex == 0 if no package separator ('.') is found in front of the file extension:
		int fileNameStartIndex = compilationUnitName.lastIndexOf(JAVA_PACKAGE_SEPARATOR, fileExtensionStartIndex - 1)
				+ 1;
		return compilationUnitName.substring(fileNameStartIndex, fileExtensionStartIndex);
	}

	public static String javaCompilationUnitFilePath(List<String> namespaces, String compilationUnitName) {
		return javaFilePath(namespaces, getFileNameFromCompilationUnitName(compilationUnitName));
	}

	public static String javaCompilationUnitFilePath(CompilationUnit compilationUnit) {
		return javaCompilationUnitFilePath(compilationUnit.getNamespaces(), compilationUnit.getName());
	}
}

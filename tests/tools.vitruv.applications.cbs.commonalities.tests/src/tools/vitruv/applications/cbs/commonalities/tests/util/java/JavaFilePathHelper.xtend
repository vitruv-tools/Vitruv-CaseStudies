package tools.vitruv.applications.cbs.commonalities.tests.util.java

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.List
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.Package
import tools.vitruv.domains.java.util.JavaPersistenceHelper

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.common.FilePathUtil.*

// TODO Move this into JavaPersistenceHelper? However, we also need to move
// FilePathUtil to some common place first.
@Utility
class JavaFilePathHelper {

	static val JAVA_SOURCE_PATH = JavaPersistenceHelper.javaProjectSrc
	static val JAVA_PACKAGE_SEPARATOR = "."
	static val JAVA_FILE_EXTENSION = JavaPersistenceHelper.JAVA_FILE_EXTENSION
	static val JAVA_FULL_FILE_EXTENSION = FILE_EXTENSION_SEPARATOR + JAVA_FILE_EXTENSION
	static val JAVA_PACKAGE_INFO_CLASS_NAME = JavaPersistenceHelper.packageInfoClassName

	private new() {
	}

	// The resulting path is relative to the Java source folder.
	static def javaFilePath(List<String> namespaces, String fileName) {
		return JAVA_SOURCE_PATH.appendPath(namespaces.toPath).appendFile(fileName, JAVA_FILE_EXTENSION)
	}

	static def javaFilePath(String fileName) {
		return EMPTY_PATH_SEGMENTS.javaFilePath(fileName)
	}

	static def javaPackageFilePath(List<String> namespaces, String packageName) {
		return namespaces.appendPathSegments(packageName).javaFilePath(JAVA_PACKAGE_INFO_CLASS_NAME)
	}

	static def javaPackageFilePath(Package javaPackage) {
		return javaPackageFilePath(javaPackage.namespaces, javaPackage.name)
	}

	// TODO duplication with JavaCompilationUnitNameOperator
	// CompilationUnit name schema: '<dot-separated-namespaces>.<fileName>.java'
	static def String getCompilationUnitName(Iterable<String> namespaces, String fileName) {
		val compilationUnitNameBuilder = new StringBuilder
		if (!namespaces.nullOrEmpty) {
			compilationUnitNameBuilder.append(namespaces.join(JAVA_PACKAGE_SEPARATOR))
		}
		if (!fileName.nullOrEmpty) {
			if (compilationUnitNameBuilder.length > 0) {
				compilationUnitNameBuilder.append(JAVA_PACKAGE_SEPARATOR)
			}
			compilationUnitNameBuilder.append(fileName)
			compilationUnitNameBuilder.append(FILE_EXTENSION_SEPARATOR)
			compilationUnitNameBuilder.append(JAVA_FILE_EXTENSION)
		}
		val compilationUnitName = compilationUnitNameBuilder.toString
		return compilationUnitName
	}

	// TODO duplication with JavaCompilationUnitNameOperator
	// CompilationUnit name schema: '<dot-separated-namespaces>.<fileName>.java'
	private static def getFileNameFromCompilationUnitName(String compilationUnitName) {
		if (compilationUnitName === null) return null
		var int fileExtensionStartIndex // inclusive
		if (compilationUnitName.endsWith(JAVA_FULL_FILE_EXTENSION)) {
			fileExtensionStartIndex = compilationUnitName.length - JAVA_FULL_FILE_EXTENSION.length
		} else {
			// The file extension is missing:
			fileExtensionStartIndex = compilationUnitName.length
		}
		// fileNameStartIndex == 0 if no package separator ('.') is found in front of the file extension:
		val fileNameStartIndex = compilationUnitName.lastIndexOf(JAVA_PACKAGE_SEPARATOR, fileExtensionStartIndex - 1) + 1
		val fileName = compilationUnitName.substring(fileNameStartIndex, fileExtensionStartIndex)
		return fileName
	}

	static def javaCompilationUnitFilePath(List<String> namespaces, String compilationUnitName) {
		return javaFilePath(namespaces, compilationUnitName.fileNameFromCompilationUnitName)
	}

	static def javaCompilationUnitFilePath(CompilationUnit compilationUnit) {
		return javaCompilationUnitFilePath(compilationUnit.namespaces, compilationUnit.name)
	}
}

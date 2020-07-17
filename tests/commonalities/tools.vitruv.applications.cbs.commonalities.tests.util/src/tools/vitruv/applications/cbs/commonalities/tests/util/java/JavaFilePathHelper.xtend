package tools.vitruv.applications.cbs.commonalities.tests.util.java

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.List
import org.emftext.language.java.containers.Package
import tools.vitruv.domains.java.util.JavaPersistenceHelper

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.FilePathUtil.*

// TODO Move this into JavaPersistenceHelper? However, we also need to move
// FilePathUtil to some common place first.
@Utility
class JavaFilePathHelper {

	static val JAVA_SOURCE_PATH = JavaPersistenceHelper.javaProjectSrc
	static val JAVA_FILE_EXTENSION = JavaPersistenceHelper.JAVA_FILE_EXTENSION
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
}

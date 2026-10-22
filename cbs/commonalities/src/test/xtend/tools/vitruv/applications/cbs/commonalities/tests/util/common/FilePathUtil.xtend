package tools.vitruv.applications.cbs.commonalities.tests.util.common

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collections
import java.util.List

@Utility
class FilePathUtil {

	public static val PATH_SEPARATOR = "/"
	public static val FILE_EXTENSION_SEPARATOR = "."
	public static val EMPTY_PATH = ""
	public static val EMPTY_PATH_SEGMENTS = Collections.<String>emptyList

	private new() {
	}

	static def String toPath(String... segments) {
		if (segments === null) return EMPTY_PATH
		return segments.join(PATH_SEPARATOR)
	}

	static def List<String> appendPathSegments(List<String> segments, String... childSegments) {
		return (segments + childSegments).toList
	}

	static def String appendPath(String path, String childPath) {
		return path + pathSeparatorIfMissing(path) + childPath
	}

	private static def String pathSeparatorIfMissing(String path) {
		return (!path.nullOrEmpty && !path.endsWith(PATH_SEPARATOR)) ? PATH_SEPARATOR : ""
	}

	static def String appendFile(String path, String fileName, String fileExtension) {
		return path.appendPath(fileName + FILE_EXTENSION_SEPARATOR + fileExtension)
	}

	static def String stripFileExtension(String filePath) {
		// 0 if no path separator ('/') is found:
		val fileNameStartIndex = filePath.lastIndexOf(PATH_SEPARATOR) + 1
		val fileExtensionStartIndex = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR)
		if (fileExtensionStartIndex < fileNameStartIndex) {
			// No file extension found: No '.' found, or the found '.' is in front of the file name.
			return filePath
		} else {
			return filePath.substring(0, fileExtensionStartIndex)
		}
	}
}

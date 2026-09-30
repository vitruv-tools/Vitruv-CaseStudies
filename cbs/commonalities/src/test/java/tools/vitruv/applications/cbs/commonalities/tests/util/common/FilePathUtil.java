package tools.vitruv.applications.cbs.commonalities.tests.util.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class FilePathUtil {

	public static final String PATH_SEPARATOR = "/";
	public static final String FILE_EXTENSION_SEPARATOR = ".";
	public static final String EMPTY_PATH = "";
	public static final List<String> EMPTY_PATH_SEGMENTS = Collections.<String>emptyList();

	private FilePathUtil() {
	}

	public static String toPath(String... segments) {
		if (segments == null) {
			return EMPTY_PATH;
		}
		return String.join(PATH_SEPARATOR, segments);
	}

	public static String toPath(List<String> segments) {
		if (segments == null) {
			return EMPTY_PATH;
		}
		return String.join(PATH_SEPARATOR, segments);
	}

	public static List<String> appendPathSegments(List<String> segments, String... childSegments) {
		List<String> result = new ArrayList<>(segments);
		result.addAll(List.of(childSegments));
		return result;
	}

	public static String appendPath(String path, String childPath) {
		return path + pathSeparatorIfMissing(path) + childPath;
	}

	private static String pathSeparatorIfMissing(String path) {
		return (path != null && !path.isEmpty() && !path.endsWith(PATH_SEPARATOR)) ? PATH_SEPARATOR : "";
	}

	public static String appendFile(String path, String fileName, String fileExtension) {
		return appendPath(path, fileName + FILE_EXTENSION_SEPARATOR + fileExtension);
	}

	public static String stripFileExtension(String filePath) {
		// 0 if no path separator ('/') is found:
		int fileNameStartIndex = filePath.lastIndexOf(PATH_SEPARATOR) + 1;
		int fileExtensionStartIndex = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		if (fileExtensionStartIndex < fileNameStartIndex) {
			// No file extension found: No '.' found, or the found '.' is in front of the file name.
			return filePath;
		} else {
			return filePath.substring(0, fileExtensionStartIndex);
		}
	}
}

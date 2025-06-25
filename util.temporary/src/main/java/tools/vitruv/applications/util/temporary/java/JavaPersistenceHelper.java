package tools.vitruv.applications.util.temporary.java;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import java.util.Collections;
import java.util.List;

import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.JavaRoot;
import org.emftext.language.java.containers.Package;

/**
 * A utility class providing helper methods for building and manipulating file paths
 * within a standard Java project structure. This class is designed to handle conversions
 * between EMF Java model elements and their corresponding file system paths.
 */
public final class JavaPersistenceHelper {

    /**
     * The name used for packages that are not explicitly named.
     */
    private static final String UNNAMED_PACKAGE_NAME = "unnamedPackage";

    /**
     * The standard path separator for resource paths (always a forward slash).
     */
    public static final String PATH_SEPARATOR = "/";

    /**
     * The separator character for file extensions.
     */
    public static final String FILE_EXTENSION_SEPARATOR = ".";

    /**
     * The file extension for Java source files.
     */
    public static final String JAVA_FILE_EXTENSION = "java";

    /**
     * Utility classes should not be instantiated.
     */
    private JavaPersistenceHelper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Returns the default source directory for a Java project.
     *
     * @return The string "src/".
     */
    public static String getJavaProjectSrc() {
        return "src/";
    }

    /**
     * Returns the base name of the package-info file.
     * The "package-info.java" file is a standard Java convention for package-level
     * documentation and annotations.
     *
     * @return The string "package-info".
     */
    public static String getPackageInfoFileName() {
        return "package-info";
    }

    /**
     * Ensures that a given source path ends with a path separator ('/').
     * If the path is null or empty, it returns an empty string.
     *
     * @param sourcePath The source path to normalize.
     * @return The normalized source path with a trailing slash.
     */
    private static String normalizeSourcePath(String sourcePath) {
        if (StringExtensions.isNullOrEmpty(sourcePath)) {
            return "";
        }
        return sourcePath.endsWith(PATH_SEPARATOR) ? sourcePath : sourcePath + PATH_SEPARATOR;
    }

    /**
     * Removes the leading source path segment from a full Java file path.
     * If the source path is not present, it attempts to remove any leading slash.
     *
     * @param javaFilePath The full path to a Java file (e.g., "src/com/example/MyClass.java").
     * @param sourcePath The source folder path to remove (e.g., "src").
     * @return The file path relative to the source folder (e.g., "com/example/MyClass.java").
     */
    public static String stripJavaSourcePath(String javaFilePath, String sourcePath) {
        if (StringExtensions.isNullOrEmpty(javaFilePath)) {
            return javaFilePath;
        }

        String normalizedSourcePath = normalizeSourcePath(sourcePath);

        // If the file path starts with the normalized source path, strip it.
        if (!normalizedSourcePath.isEmpty() && javaFilePath.startsWith(normalizedSourcePath)) {
            return javaFilePath.substring(normalizedSourcePath.length());
        }

        // As a fallback, if the path is absolute, make it relative.
        if (javaFilePath.startsWith(PATH_SEPARATOR)) {
            return javaFilePath.substring(PATH_SEPARATOR.length());
        }

        return javaFilePath;
    }

    /**
     * Extracts the simple name of a class from a CompilationUnit model element.
     * For example, for a CompilationUnit named "com.example.MyClass.java", this returns "MyClass".
     *
     * @param compilationUnit The EMF model element for a Java compilation unit.
     * @return The simple class name without package or extension.
     * @throws IllegalStateException if the compilation unit's name does not end with ".java".
     */
    public static String getSimpleName(CompilationUnit compilationUnit) {
        String fullName = compilationUnit.getName();
        Preconditions.checkState(fullName.endsWith(FILE_EXTENSION_SEPARATOR + JAVA_FILE_EXTENSION),
                "compilation unit has to end with '%s' but doesn't: %s",
                FILE_EXTENSION_SEPARATOR + JAVA_FILE_EXTENSION, fullName);

        // 1. Strip the ".java" file extension.
        String nameWithoutExtension = fullName.substring(0, fullName.length() - (JAVA_FILE_EXTENSION.length() + 1));

        // 2. Find the last dot of the package name to isolate the simple class name.
        int lastDotIndex = nameWithoutExtension.lastIndexOf(FILE_EXTENSION_SEPARATOR);

        // 3. Return the substring after the last dot, or the full string if no dot exists.
        return nameWithoutExtension.substring(lastDotIndex + 1);
    }

    /**
     * Builds a path to a Java package directory.
     *
     * @param sourcePath The source folder (e.g., "src").
     * @param namespaces The package components (e.g., ["com", "example"]).
     * @return A full path to the package directory (e.g., "src/com/example/").
     */
    public static String buildJavaPath(String sourcePath, Iterable<String> namespaces) {
        // A path to a directory is equivalent to a file path with an empty file name.
        return buildJavaFilePath(sourcePath, namespaces, "", "");
    }

    /**
     * Builds a full file path for a Java resource.
     *
     * @param sourcePath    The source folder (e.g., "src").
     * @param namespaces    An iterable of package name components (e.g., ["com", "example"]).
     * @param fileName      The name of the file without extension (e.g., "MyClass").
     * @param fileExtension The file extension without a dot (e.g., "java").
     * @return A complete, platform-independent file path (e.g., "src/com/example/MyClass.java").
     */
    public static String buildJavaFilePath(String sourcePath, Iterable<String> namespaces, String fileName, String fileExtension) {
        StringBuilder pathBuilder = new StringBuilder();

        // Append the normalized source folder path (e.g., "src/").
        pathBuilder.append(normalizeSourcePath(sourcePath));

        // Join the namespace components with the path separator.
        if (namespaces != null && namespaces.iterator().hasNext()) {
            pathBuilder.append(String.join(PATH_SEPARATOR, namespaces));
            pathBuilder.append(PATH_SEPARATOR);
        }

        // Append the file name.
        pathBuilder.append(fileName);

        // Append the extension if it is provided.
        if (!StringExtensions.isNullOrEmpty(fileExtension)) {
            pathBuilder.append(FILE_EXTENSION_SEPARATOR);
            pathBuilder.append(fileExtension);
        }

        return pathBuilder.toString();
    }

    /**
     * Builds a file path using the default source folder ("src/").
     *
     * @param fileName   The name of the file.
     * @param namespaces The package components.
     * @return A full path to the file (e.g., "src/com/example/MyClass").
     */
    public static String buildJavaFilePath(String fileName, Iterable<String> namespaces) {
        return buildJavaFilePath(getJavaProjectSrc(), namespaces, fileName, null);
    }

    /**
     * Internal helper to build a file path from a {@link CompilationUnit}.
     */
    private static String buildPathFromCompilationUnit(CompilationUnit compilationUnit) {
        return buildJavaFilePath(
                getJavaProjectSrc(),
                compilationUnit.getNamespaces(),
                getSimpleName(compilationUnit),
                JAVA_FILE_EXTENSION
        );
    }

    /**
     * Internal helper to build a file path from a {@link Package}.
     * This resolves to the path of the "package-info.java" file.
     */
    private static String buildPathFromPackage(Package javaPackage) {
        return buildJavaPackageFilePath(getJavaProjectSrc(), javaPackage.getNamespaces(), javaPackage.getName());
    }

    /**
     * Builds the full file path for a "package-info.java" file.
     *
     * @param sourcePath  The source folder.
     * @param namespaces  The parent package components.
     * @param packageName The final package name.
     * @return A path like "src/com/example/my-package/package-info.java".
     */
    public static String buildJavaPackageFilePath(String sourcePath, Iterable<String> namespaces, String packageName) {
        Iterable<String> fullNamespaces = getFullPackageNamespaces(namespaces, packageName);
        return buildJavaFilePath(sourcePath, fullNamespaces, getPackageInfoFileName(), JAVA_FILE_EXTENSION);
    }

    /**
     * Builds the path to a package directory.
     *
     * @param sourcePath  The source folder.
     * @param namespaces  The parent package components.
     * @param packageName The final package name.
     * @return A path like "src/com/example/my-package/".
     */
    public static String buildJavaPackagePath(String sourcePath, Iterable<String> namespaces, String packageName) {
        Iterable<String> fullNamespaces = getFullPackageNamespaces(namespaces, packageName);
        return buildJavaPath(sourcePath, fullNamespaces);
    }

    /**
     * Combines parent namespaces with a final package name into a single iterable of path components.
     * If the package name is null, it defaults to {@link #UNNAMED_PACKAGE_NAME}.
     */
    private static Iterable<String> getFullPackageNamespaces(Iterable<String> namespaces, String packageName) {
        // Use a default name if the package name is null.
        String finalPackageName = (packageName != null) ? packageName : UNNAMED_PACKAGE_NAME;
        return Iterables.concat(namespaces, List.of(finalPackageName));
    }

    /**
     * Dispatches to the correct path-building method based on the runtime type of the {@link JavaRoot} element.
     * This method is annotated with {@code @XbaseGenerated}, indicating it may have been
     * originally generated from a Xtend dispatch method.
     *
     * @param javaRoot The EMF Java model element (can be a {@link CompilationUnit} or a {@link Package}).
     * @return The corresponding file system path.
     * @throws IllegalArgumentException if the type of {@code javaRoot} is not handled.
     */
    public static String buildJavaFilePath(JavaRoot javaRoot) {
        if (javaRoot instanceof CompilationUnit) {
            return buildPathFromCompilationUnit((CompilationUnit) javaRoot);
        } else if (javaRoot instanceof Package) {
            return buildPathFromPackage((Package) javaRoot);
        } else {
            throw new IllegalArgumentException("Unhandled parameter types: " +
                    Collections.singletonList(javaRoot));
        }
    }
}
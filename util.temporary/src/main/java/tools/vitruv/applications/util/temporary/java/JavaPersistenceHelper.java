package tools.vitruv.applications.util.temporary.java;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import java.util.Collections;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.JavaRoot;
import org.emftext.language.java.containers.Package;

/**
 * A utility class providing static methods to handle file paths and names
 * for Java source code artifacts. This includes constructing paths from package
 * structures and extracting simple names from compilation units.
 */
public final class JavaPersistenceHelper {

    private static final String UNNAMED_PACKAGE = "unnamedPackage";

    /**
     * The standard path separator used in Java project structures ('/').
     */
    public static final String PATH_SEPARATOR = "/";

    /**
     * The separator for file extensions ('.').
     */
    public static final String FILE_EXTENSION_SEPARATOR = ".";

    /**
     * The standard file extension for Java source files ("java").
     */
    public static final String JAVA_FILE_EXTENSION = "java";

    private static final String FILE_EXTENSION_WITH_SEPARATOR = ".java";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private JavaPersistenceHelper() {
        // Utility class
    }

    /**
     * Returns the default source directory path for a Java project.
     *
     * @return The default source path, which is "src/".
     */
    public static String getJavaProjectSrc() {
        return "src/";
    }

    /**
     * Returns the conventional class name for a Java package-info file.
     *
     * @return The string "package-info".
     */
    public static String getPackageInfoClassName() {
        return "package-info";
    }

    /**
     * Ensures a source path string ends with a path separator ('/').
     * If the path is null or empty, an empty string is returned.
     *
     * @param sourcePath The source path to normalize.
     * @return The normalized source path, ending with a '/'.
     */
    private static String normalizeSourcePath(String sourcePath) {
        if (StringExtensions.isNullOrEmpty(sourcePath)) {
            return "";
        }
        return sourcePath.endsWith(PATH_SEPARATOR) ? sourcePath : sourcePath + PATH_SEPARATOR;
    }

    /**
     * Strips the given source path from a full Java file path.
     * <p>
     * For example, given the file path "src/com/example/MyClass.java" and the source path "src",
     * this method returns "com/example/MyClass.java".
     * If the source path does not match the beginning of the file path, it attempts to strip a leading path separator.
     *
     * @param javaFilePath The full path to a Java file. Can be null or empty.
     * @param sourcePath   The source folder path to remove (e.g., "src" or "src/main/java").
     * @return The file path relative to the source path, or the original path if it cannot be stripped.
     */
    public static String stripJavaSourcePath(String javaFilePath, String sourcePath) {
        if (StringExtensions.isNullOrEmpty(javaFilePath)) {
            return javaFilePath;
        }

        String normalizedSourcePath = normalizeSourcePath(sourcePath);
        if (!normalizedSourcePath.isEmpty() && javaFilePath.startsWith(normalizedSourcePath)) {
            return javaFilePath.substring(normalizedSourcePath.length());
        }

        if (javaFilePath.startsWith(PATH_SEPARATOR)) {
            return javaFilePath.substring(PATH_SEPARATOR.length());
        }

        return javaFilePath;
    }

    /**
     * Extracts the simple class name (without package and extension) from a {@link CompilationUnit}.
     * <p>
     * For example, given a compilation unit with the name "org.example.MyClass.java", this method returns "MyClass".
     *
     * @param compilationUnit The compilation unit to process. Must not be null.
     * @return The simple name of the Java class.
     * @throws IllegalStateException if the compilation unit's name does not end with ".java".
     */
    public static String getSimpleName(CompilationUnit compilationUnit) {
        String fullName = compilationUnit.getName();
        Preconditions.checkState(fullName.endsWith(FILE_EXTENSION_WITH_SEPARATOR),
                "Compilation unit name must end with '%s' but was: %s", FILE_EXTENSION_WITH_SEPARATOR, fullName);

        // Remove the ".java" extension
        String nameWithoutExtension = fullName.substring(0, fullName.length() - FILE_EXTENSION_WITH_SEPARATOR.length());

        // Find the last dot to remove the package path
        int lastDotIndex = nameWithoutExtension.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        if (lastDotIndex != -1) {
            return nameWithoutExtension.substring(lastDotIndex + 1);
        }

        return nameWithoutExtension;
    }

    /**
     * Constructs a directory path from a source path and a set of namespaces.
     *
     * @param sourcePath The source folder path (e.g., "src").
     * @param namespaces An iterable of package name parts (e.g., ["com", "example"]).
     * @return The constructed directory path, like "src/com/example/".
     */
    public static String buildJavaPath(String sourcePath, Iterable<String> namespaces) {
        return buildJavaFilePath(sourcePath, namespaces, "", "");
    }

    /**
     * Constructs a full file path for a Java resource.
     *
     * @param sourcePath    The source folder path (e.g., "src"). It will be normalized to end with a '/'.
     * @param namespaces    An iterable of package name parts (e.g., ["com", "example"]).
     * @param fileName      The name of the file without extension (e.g., "MyClass").
     * @param fileExtension The file extension without a dot (e.g., "java"). Can be null or empty.
     * @return The fully constructed path, like "src/com/example/MyClass.java".
     */
    public static String buildJavaFilePath(String sourcePath, Iterable<String> namespaces, String fileName, String fileExtension) {
        // Using StringConcatenation to preserve the exact logic of the original Xtend-generated code.
        StringConcatenation builder = new StringConcatenation();
        builder.append(normalizeSourcePath(sourcePath));
        boolean hasNamespaceElements = false;
        for (String namespace : namespaces) {
            if (hasNamespaceElements) {
                builder.appendImmediate(PATH_SEPARATOR, "");
            } else {
                hasNamespaceElements = true;
            }
            builder.append(namespace);
        }

        if (hasNamespaceElements) {
            builder.append(PATH_SEPARATOR);
        }

        builder.append(fileName);
        if (!StringExtensions.isNullOrEmpty(fileExtension)) {
            builder.append(FILE_EXTENSION_SEPARATOR);
            builder.append(fileExtension);
        }
        return builder.toString();
    }

    /**
     * Constructs a full file path using the default source directory ("src/").
     *
     * @param fileName   The name of the file without an extension.
     * @param namespaces An iterable of package name parts.
     * @return The fully constructed path with no file extension.
     */
    public static String buildJavaFilePath(String fileName, Iterable<String> namespaces) {
        return buildJavaFilePath(getJavaProjectSrc(), namespaces, fileName, null);
    }

    /**
     * Constructs the full file path for a package-info.java file.
     *
     * @param sourcePath  The source folder path.
     * @param namespaces  The parent namespaces of the package.
     * @param packageName The final name of the package.
     * @return The full path to the package-info.java file.
     */
    public static String buildJavaPackageFilePath(String sourcePath, Iterable<String> namespaces, String packageName) {
        Iterable<String> fullNamespaces = getFullPackageNamespaces(namespaces, packageName);
        return buildJavaFilePath(sourcePath, fullNamespaces, getPackageInfoClassName(), JAVA_FILE_EXTENSION);
    }

    /**
     * Constructs the directory path for a Java package.
     *
     * @param sourcePath  The source folder path.
     * @param namespaces  The parent namespaces of the package.
     * @param packageName The final name of the package.
     * @return The full path to the package directory.
     */
    public static String buildJavaPackagePath(String sourcePath, Iterable<String> namespaces, String packageName) {
        Iterable<String> fullNamespaces = getFullPackageNamespaces(namespaces, packageName);
        return buildJavaPath(sourcePath, fullNamespaces);
    }

    /**
     * Appends a final package name to an existing list of parent namespaces.
     * If the given package name is null, a default name for unnamed packages is used.
     *
     * @param namespaces  The parent namespaces (e.g., ["com", "example"]).
     * @param packageName The final package name to append (e.g., "util").
     * @return An iterable containing the full sequence of namespaces (e.g., ["com", "example", "util"]).
     */
    private static Iterable<String> getFullPackageNamespaces(Iterable<String> namespaces, String packageName) {
        String effectivePackageName = (packageName != null) ? packageName : UNNAMED_PACKAGE;
        return Iterables.concat(namespaces, Collections.singletonList(effectivePackageName));
    }

    /**
     * Builds the full project-relative file path for a {@link JavaRoot} element, which can be a
     * {@link CompilationUnit} or a {@link Package}.
     * <p>
     * This method dispatches to the appropriate helper based on the runtime type of the {@code javaRoot} parameter.
     *
     * @param javaRoot The Java model element ({@code CompilationUnit} or {@code Package}).
     * @return The full path, e.g., "src/com/example/MyClass.java" or "src/com/example/package-info.java".
     * @throws IllegalArgumentException if the {@code javaRoot} is not a supported type.
     */
    @XbaseGenerated
    public static String buildJavaFilePath(JavaRoot javaRoot) {
        if (javaRoot instanceof CompilationUnit) {
            return buildJavaFilePathForCompilationUnit((CompilationUnit) javaRoot);
        } else if (javaRoot instanceof Package) {
            return buildJavaFilePathForPackage((Package) javaRoot);
        } else {
            throw new IllegalArgumentException("Unhandled parameter type: " + javaRoot.getClass().getName());
        }
    }

    /**
     * Builds the file path for a {@link CompilationUnit} using the default source directory ("src/").
     *
     * @param compilationUnit The compilation unit.
     * @return The full file path.
     */
    private static String buildJavaFilePathForCompilationUnit(CompilationUnit compilationUnit) {
        return buildJavaFilePath(getJavaProjectSrc(), compilationUnit.getNamespaces(), getSimpleName(compilationUnit), JAVA_FILE_EXTENSION);
    }

    /**
     * Builds the file path for a {@link Package} (i.e., its package-info.java file) using the default source directory.
     *
     * @param javaPackage The Java package.
     * @return The full file path for the package-info.java file.
     */
    private static String buildJavaFilePathForPackage(Package javaPackage) {
        return buildJavaPackageFilePath(getJavaProjectSrc(), javaPackage.getNamespaces(), javaPackage.getName());
    }
}
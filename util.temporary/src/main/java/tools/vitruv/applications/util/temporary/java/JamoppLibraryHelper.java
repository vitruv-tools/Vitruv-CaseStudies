package tools.vitruv.applications.util.temporary.java;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.emftext.language.java.JavaClasspath;

/**
 * This helper class allows loading the Java standard library in JaMoPP,
 * especially with Java 9 and above where the boot classpath was removed.
 */
public class JamoppLibraryHelper {

    private static final String STANDARD_LIBRARY_FOLDER_IN_HOME = "/jmods/";
    private static final String BASE_LIBRARY_NAME = "java.base";

    /**
     * Registers the Java standard library.
     * For Java 8 and below, this uses the standard deployed library.
     * For Java 9+, it registers the "java.base" module.
     */
    public static void registerStdLib() {
        final String javaVersion = System.getProperty("java.version");
        if (javaVersion.startsWith("1.")) {
            JavaClasspath.get().registerStdLib();
        } else {
            Set<JavaClasspath.Initializer> initializers = JavaClasspath.getInitializers();
            initializers.add(new JavaClasspath.Initializer() {
                @Override
                public void initialize(Resource resource) {
                    // No-op initializer
                }

                @Override
                public boolean requiresLocalClasspath() {
                    return false;
                }

                @Override
                public boolean requiresStdLib() {
                    return false;
                }
            });
            registerStdLibraryModule(BASE_LIBRARY_NAME);
        }
    }

    /**
     * Registers a Java standard library module (Java 9+ only).
     *
     * @param name Name of the module to register.
     * @throws IllegalStateException if Java version is < 9
     */
    public static void registerStdLibraryModule(final String name) {
        final String javaVersion = System.getProperty("java.version");
        if (javaVersion.startsWith("1.")) {
            throw new IllegalStateException("Modules can only be registered in Java version 9 and above");
        }

        String javaHome = System.getProperty("java.home");
        Path modulePath = Paths.get(javaHome, STANDARD_LIBRARY_FOLDER_IN_HOME, name + ".jmod");
        URI moduleUri = URI.createFileURI(modulePath.toString());
        JavaClasspath.get().registerClassifierJar(moduleUri, "classes/");
    }

    /**
     * Registers a local library (e.g., a JAR file).
     *
     * @param pathToLibrary Path to the archive containing Java classes/packages.
     */
    public static void registerLocalLibrary(final Path pathToLibrary) {
        JavaClasspath.get().registerClassifierJar(URI.createFileURI(pathToLibrary.toString()), "");
    }
}

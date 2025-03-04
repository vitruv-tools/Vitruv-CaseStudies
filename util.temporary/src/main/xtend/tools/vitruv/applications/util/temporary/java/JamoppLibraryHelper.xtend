package tools.vitruv.applications.util.temporary.java

import org.emftext.language.java.JavaClasspath
import org.emftext.language.java.JavaClasspath.Initializer
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.common.util.URI
import static com.google.common.base.Preconditions.checkState
import java.nio.file.Path

/**
 * This helper class allows to load the Java standard library in JaMoPP also with
 * Java versions 9 and above.
 * In Java 9 the boot classpath was removed and the standard library is packaged
 * differently, which is corrected by this patch.
 */
class JamoppLibraryHelper {
	static String STANDARD_LIBRARY_FOLDER_IN_HOME = "/jmods/"
	static String BASE_LIBRARY_NAME = "java.base"

	/**
	 * Registers the Java standard library. In case of Java 8 and below, this is the deployed standard library.
	 * In case of Java 9 and above, this is the "java.base" module. Further modules can be registered with
	 * the {@link #registerStdLibraryModule} method.
	 */
	static def void registerStdLib() {
		val String javaVersion = System.getProperty("java.version")

		// Until Java 1.8 we can use the mechanism of JaMoPP
		if (javaVersion.startsWith("1.")) {
			JavaClasspath.get().registerStdLib();
		// From Java 9 on, we have to search for the Java base module instead of the rt.jar file.
		// To do so, we disable automatic initialization of the standard library using the classpath 
		// (where library cannot be found in Java 9 and above) and manually load the base Java module
		} else {
			JavaClasspath.initializers.add(new Initializer() {
				override initialize(Resource resource) {}

				override requiresLocalClasspath() { false }

				override requiresStdLib() { false }
			})
			registerStdLibraryModule(BASE_LIBRARY_NAME)
		}
	}

	/**
	 * Registers the Java standard library module with the given name.
	 * This may only be called when using Java in version 9 or above.
	 * Otherwise, it will throw an {@link IllegalStateException}.
	 * @param name - the name of the module to register
	 */
	static def void registerStdLibraryModule(String name) {
		val String javaVersion = System.getProperty("java.version");
		checkState(!javaVersion.startsWith("1."), "Modules can only be registered in Java version 9 and above")

		val modulePath = System.getProperty("java.home") + STANDARD_LIBRARY_FOLDER_IN_HOME + name + ".jmod"
		val moduleUri = URI.createFileURI(modulePath)
		// From java 9, the module files do not directly contain the classes in the package structure
		// but are placed in the "classes" folder, so that prefix has to be removed.
		JavaClasspath.get().registerClassifierJar(moduleUri, "classes/");
	}

	/**
	 * Registers the library placed at the given path to. The library must
	 * be an archive (like a ".jar" file) directly containing the package structure
	 * to be imported.
	 * @param pathToLibrary - the path to the library to register
	 */
	static def void registerLocalLibrary(Path pathToLibrary) {
		JavaClasspath.get().registerClassifierJar(URI.createFileURI(pathToLibrary.toString), "");
	}

}

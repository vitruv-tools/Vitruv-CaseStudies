package tools.vitruv.applications.util.temporary.java;

import java.util.function.Supplier;

import org.eclipse.emf.ecore.resource.Resource;
import org.emftext.language.java.JavaClasspath;
import org.emftext.language.java.resource.JavaSourceOrClassFileResourceFactoryImpl;

public final class JavaSetup {
	private JavaSetup() {
	}
	
	/**
	 * Prepares for using Java without an OSGI environment registering file extensions for the given resource factory.
	 * @param resourceFactory the resource factory to use for loading Java and class files
	 */
	public static void prepareFactories(Supplier<Resource.Factory> resourceFactoryProvider) {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("java", resourceFactoryProvider.get());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("class", resourceFactoryProvider.get());
	}
	
	/**
	 * Prepares for using Java without an OSGI environment registering file extensions for the default resource factories.
	 */
	public static void prepareFactories() {
		prepareFactories(() -> new JavaSourceOrClassFileResourceFactoryImpl());
	}

	/**
	 * Resets the JaMoPP classpath to remove any classifier referenced before and registers the standard library (jmods/java.base)
	 * for use with JaMoPP.
	 * @see JavaClasspath#reset()
	 * @see JamoppLibraryHelper#registerStdLib()
	 */
	public static void resetClasspathAndRegisterStandardLibrary() {
		JavaClasspath.reset();
		JamoppLibraryHelper.registerStdLib();
	}
	
}

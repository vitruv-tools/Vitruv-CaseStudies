package tools.vitruv.applications.umljava.tests.java2uml.constructionsimulationtest;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.emftext.language.java.resource.JavaSourceOrClassFileResourceFactoryImpl;

public class JavaSOCFileResourceWithArraysDefaultFactoryImpl
		extends JavaSourceOrClassFileResourceFactoryImpl {

	@Override
	public Resource createResource(URI uri) {
		return new JavaSourceOrClassFileResourceWithArraysDefault(uri);
	}
}

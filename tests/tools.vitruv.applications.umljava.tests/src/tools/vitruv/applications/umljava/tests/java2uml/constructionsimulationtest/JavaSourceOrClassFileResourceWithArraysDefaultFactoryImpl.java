package tools.vitruv.applications.umljava.tests.java2uml.constructionsimulationtest;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import tools.mdsd.jamopp.resource.JavaResource2Factory;

public class JavaSourceOrClassFileResourceWithArraysDefaultFactoryImpl
		extends JavaResource2Factory {

	@Override
	public Resource createResource(URI uri) {
		return new JavaSourceOrClassFileResourceWithArraysDefault(uri);
	}
}

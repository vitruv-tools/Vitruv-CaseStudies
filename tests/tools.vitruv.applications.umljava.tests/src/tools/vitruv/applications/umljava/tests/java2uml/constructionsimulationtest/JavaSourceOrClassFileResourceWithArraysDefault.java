package tools.vitruv.applications.umljava.tests.java2uml.constructionsimulationtest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import tools.mdsd.jamopp.model.java.containers.CompilationUnit;
import tools.mdsd.jamopp.model.java.members.Field;
import tools.mdsd.jamopp.model.java.members.MembersFactory;
import tools.mdsd.jamopp.resource.JavaResource2;
import tools.mdsd.jamopp.model.java.types.ClassifierReference;
import tools.mdsd.jamopp.model.java.types.TypesFactory;

public class JavaSourceOrClassFileResourceWithArraysDefault extends JavaResource2 {

	public JavaSourceOrClassFileResourceWithArraysDefault(URI uri) {
		super(uri);
	}

	@Override
	public void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		super.doLoad(inputStream, options);
		addArrayLengthField();
	}

	private void addArrayLengthField() {
		if (!contents.isEmpty() && contents.get(0) instanceof CompilationUnit) {
			Field standardArrayLengthField = MembersFactory.eINSTANCE.createField();
			standardArrayLengthField.setName("length");
			ClassifierReference typeReference = TypesFactory.eINSTANCE.createClassifierReference();
			typeReference.setTarget(((CompilationUnit) contents.get(0)).getLibClass("Integer"));
			standardArrayLengthField.setTypeReference(typeReference);
			contents.add(standardArrayLengthField);
		}
	}

}

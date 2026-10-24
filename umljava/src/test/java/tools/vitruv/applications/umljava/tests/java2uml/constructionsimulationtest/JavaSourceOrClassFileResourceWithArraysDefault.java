package tools.vitruv.applications.umljava.tests.java2uml.constructionsimulationtest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.MembersFactory;
import org.emftext.language.java.resource.JavaSourceOrClassFileResource;
import org.emftext.language.java.types.ClassifierReference;
import org.emftext.language.java.types.TypesFactory;

public class JavaSourceOrClassFileResourceWithArraysDefault extends JavaSourceOrClassFileResource {

	public JavaSourceOrClassFileResourceWithArraysDefault(URI uri) {
		super(uri);
	}

	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
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

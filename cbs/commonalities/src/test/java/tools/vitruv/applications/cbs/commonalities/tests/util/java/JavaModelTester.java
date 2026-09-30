package tools.vitruv.applications.cbs.commonalities.tests.util.java;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelTester;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class JavaModelTester extends DomainModelTester {

	private final JavaTestHelper javaTestHelper = new JavaTestHelper(vitruvApplicationTestAdapter);

	public JavaModelTester(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	@Override
	public void createAndSynchronizeModel(EObject rootObject) {
		if (rootObject instanceof Package javaPackage) {
			javaTestHelper.createAndSynchronizeJavaPackage(javaPackage);
		} else if (rootObject instanceof CompilationUnit compilationUnit) {
			javaTestHelper.createAndSynchronizeJavaCompilationUnit(compilationUnit);
		} else {
			throw new IllegalStateException("Unhandled Java root object: " + rootObject);
		}
	}

	@Override
	public void assertModelExists(EObject rootObject) {
		if (rootObject instanceof Package javaPackage) {
			javaTestHelper.assertJavaPackageExists(javaPackage);
		} else if (rootObject instanceof CompilationUnit compilationUnit) {
			javaTestHelper.assertJavaCompilationUnitExists(compilationUnit);
		} else {
			throw new IllegalStateException("Unhandled Java root object: " + rootObject);
		}
	}
}

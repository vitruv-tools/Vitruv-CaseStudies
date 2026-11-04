package tools.vitruv.applications.cbs.commonalities.tests.util.java;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;

import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelTester;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class JavaModelTester extends DomainModelTester {

    private final JavaTestHelper javaTestHelper;

    public JavaModelTester(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
        this.javaTestHelper = new JavaTestHelper(vitruvApplicationTestAdapter);
    }

    @Override
    public void createAndSynchronizeModel(EObject rootObject) {
        switch (rootObject) {
            case Package javaPackage -> javaTestHelper.createAndSynchronizeJavaPackage(javaPackage);
            case CompilationUnit compilationUnit ->
                javaTestHelper.createAndSynchronizeJavaCompilationUnit(compilationUnit);
            case null, default -> throw new IllegalStateException("Unhandled Java root object: " + rootObject);
        }
    }

    @Override
    public void assertModelExists(EObject rootObject) {
        switch (rootObject) {
            case Package javaPackage -> javaTestHelper.assertJavaPackageExists(javaPackage);
            case CompilationUnit compilationUnit -> javaTestHelper.assertJavaCompilationUnitExists(compilationUnit);
            case null, default -> throw new IllegalStateException("Unhandled Java root object: " + rootObject);
        }
    }
}

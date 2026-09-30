package tools.vitruv.applications.cbs.commonalities.tests.util.java;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static tools.vitruv.applications.cbs.commonalities.tests.util.ModelMatchers.contains;
import static tools.vitruv.applications.cbs.commonalities.tests.util.ModelMatchers.ignoringUnsetFeatures;
import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaFilePathHelper.javaCompilationUnitFilePath;
import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaFilePathHelper.javaPackageFilePath;

import org.eclipse.emf.ecore.resource.Resource;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class JavaTestHelper {

	private final VitruvApplicationTestAdapter vitruvApplicationTestAdapter;

	public JavaTestHelper(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		checkNotNull(vitruvApplicationTestAdapter, "vitruvApplicationTestAdapter is null");
		this.vitruvApplicationTestAdapter = vitruvApplicationTestAdapter;
	}

	public Package createAndSynchronizeJavaPackage(Package javaPackage) {
		vitruvApplicationTestAdapter.createAndSynchronizeModel(javaPackageFilePath(javaPackage), javaPackage);
		return javaPackage;
	}

	public CompilationUnit createAndSynchronizeJavaCompilationUnit(CompilationUnit compilationUnit) {
		vitruvApplicationTestAdapter.createAndSynchronizeModel(javaCompilationUnitFilePath(compilationUnit),
				compilationUnit);
		return compilationUnit;
	}

	public Resource getJavaPackageResource(Package javaPackage) {
		return vitruvApplicationTestAdapter.getResourceAt(javaPackageFilePath(javaPackage));
	}

	public Resource getJavaCompilationUnitResource(CompilationUnit compilationUnit) {
		return vitruvApplicationTestAdapter.getResourceAt(javaCompilationUnitFilePath(compilationUnit));
	}

	public void assertJavaPackageExists(Package javaPackage) {
		assertThat(getJavaPackageResource(javaPackage), contains(javaPackage, ignoringUnsetFeatures()));
	}

	public void assertJavaCompilationUnitExists(CompilationUnit compilationUnit) {
		assertThat(getJavaCompilationUnitResource(compilationUnit), contains(compilationUnit, ignoringUnsetFeatures()));
	}
}

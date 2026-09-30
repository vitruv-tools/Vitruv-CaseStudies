package tools.vitruv.applications.cbs.commonalities.tests.oo.java;

import java.util.List;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import tools.vitruv.applications.cbs.commonalities.tests.oo.PackageTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase;

public class JavaPackageTestModels extends JavaTestModelsBase implements PackageTest.DomainModels {

	private static Package newJavaPackage(String name, String... namespaces) {
		Package javaPackage = ContainersFactory.eINSTANCE.createPackage();
		javaPackage.getNamespaces().addAll(List.of(namespaces));
		javaPackage.setName(name);
		return javaPackage;
	}

	private static Package newRoot1JavaPackage() {
		return newJavaPackage(ROOT1_PACKAGE_NAME);
	}

	private static Package newRoot2JavaPackage() {
		return newJavaPackage(ROOT2_PACKAGE_NAME);
	}

	private static Package newRoot1Sub1JavaPackage() {
		return newJavaPackage(SUB1_PACKAGE_NAME, ROOT1_PACKAGE_NAME);
	}

	private static Package newRoot1Sub2JavaPackage() {
		return newJavaPackage(SUB2_PACKAGE_NAME, ROOT1_PACKAGE_NAME);
	}

	private static Package newRoot2Sub1JavaPackage() {
		return newJavaPackage(SUB1_PACKAGE_NAME, ROOT2_PACKAGE_NAME);
	}

	private static Package newRoot2Sub2JavaPackage() {
		return newJavaPackage(SUB2_PACKAGE_NAME, ROOT2_PACKAGE_NAME);
	}

	public JavaPackageTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	@Override
	public DomainModel singleRootPackageCreation() {
		return newModel(() -> List.of(
				newRoot1JavaPackage()));
	}

	@Override
	public DomainModel multiRootPackageCreation() {
		return newModel(() -> List.of(
				newRoot1JavaPackage(),
				newRoot2JavaPackage()));
	}

	@Override
	public DomainModel subPackagesCreation() {
		return newModel(() -> List.of(
				newRoot1JavaPackage(),
				newRoot1Sub1JavaPackage(),
				newRoot1Sub2JavaPackage(),
				newRoot2JavaPackage(),
				newRoot2Sub1JavaPackage(),
				newRoot2Sub2JavaPackage()));
	}
}

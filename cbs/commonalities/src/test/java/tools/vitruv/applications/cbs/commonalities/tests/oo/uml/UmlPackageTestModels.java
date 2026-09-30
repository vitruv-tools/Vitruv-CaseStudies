package tools.vitruv.applications.cbs.commonalities.tests.oo.uml;

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.newUmlModel;

import java.util.List;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import tools.vitruv.applications.cbs.commonalities.tests.oo.PackageTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase;

public class UmlPackageTestModels extends UmlTestModelsBase implements PackageTest.DomainModels {

	private static Package newUmlPackage(String name) {
		Package umlPackage = UMLFactory.eINSTANCE.createPackage();
		umlPackage.setName(name);
		return umlPackage;
	}

	private static Package newRoot1UmlPackage() {
		return newUmlPackage(ROOT1_PACKAGE_NAME);
	}

	private static Package newRoot2UmlPackage() {
		return newUmlPackage(ROOT2_PACKAGE_NAME);
	}

	private static Package newSub1UmlPackage() {
		return newUmlPackage(SUB1_PACKAGE_NAME);
	}

	private static Package newSub2UmlPackage() {
		return newUmlPackage(SUB2_PACKAGE_NAME);
	}

	public UmlPackageTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	@Override
	public DomainModel singleRootPackageCreation() {
		return newModel(() -> {
			Model umlModel = newUmlModel();
			umlModel.getPackagedElements().add(newRoot1UmlPackage());
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel multiRootPackageCreation() {
		return newModel(() -> {
			Model umlModel = newUmlModel();
			umlModel.getPackagedElements().add(newRoot1UmlPackage());
			umlModel.getPackagedElements().add(newRoot2UmlPackage());
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel subPackagesCreation() {
		return newModel(() -> {
			Model umlModel = newUmlModel();
			Package root1Package = newRoot1UmlPackage();
			root1Package.getPackagedElements().add(newSub1UmlPackage());
			root1Package.getPackagedElements().add(newSub2UmlPackage());
			umlModel.getPackagedElements().add(root1Package);
			Package root2Package = newRoot2UmlPackage();
			root2Package.getPackagedElements().add(newSub1UmlPackage());
			root2Package.getPackagedElements().add(newSub2UmlPackage());
			umlModel.getPackagedElements().add(root2Package);
			return List.of(umlModel);
		});
	}
}

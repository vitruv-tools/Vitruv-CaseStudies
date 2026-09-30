package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml;

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.newUmlModel;

import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import tools.vitruv.applications.cbs.commonalities.tests.TestConstants.UML;

public class UmlRepositoryModel {

	private final Model model = newUmlModel();
	private final Package repositoryPackage = newUmlPackage(model, UML.REPOSITORY_PACKAGE_NAME);
	private final Package datatypesPackage = newUmlPackage(repositoryPackage, UML.DATATYPES_PACKAGE_NAME);
	private final Package contractsPackage = newUmlPackage(repositoryPackage, UML.CONTRACTS_PACKAGE_NAME);

	private static Package newUmlPackage(Package parentPackage, String name) {
		Package umlPackage = UMLFactory.eINSTANCE.createPackage();
		parentPackage.getPackagedElements().add(umlPackage);
		umlPackage.setName(name);
		return umlPackage;
	}

	public Model getModel() {
		return model;
	}

	public Package getRepositoryPackage() {
		return repositoryPackage;
	}

	public Package getDatatypesPackage() {
		return datatypesPackage;
	}

	public Package getContractsPackage() {
		return contractsPackage;
	}
}

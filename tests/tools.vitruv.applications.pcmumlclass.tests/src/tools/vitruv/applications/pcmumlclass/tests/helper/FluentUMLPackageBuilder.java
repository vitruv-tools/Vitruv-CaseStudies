package tools.vitruv.applications.pcmumlclass.tests.helper;

import org.eclipse.uml2.uml.UMLFactory;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;

public class FluentUMLPackageBuilder {

	private final String name;
	private final List<PackageableElement> packagedElements = new ArrayList<>();

	public FluentUMLPackageBuilder(String name) {
		this.name = name;
	}

	public FluentUMLPackageBuilder addPackagedElement(PackageableElement element) {
		this.packagedElements.add(element);
		return this;
	}

	public Package build() {
		Package result = UMLFactory.eINSTANCE.createPackage();

		result.setName(name);
		result.getPackagedElements().addAll(packagedElements);

		return result;
	}
}
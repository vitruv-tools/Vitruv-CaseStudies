package tools.vitruv.applications.cbs.commonalities.tests.oo.uml;

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.newUmlModel;
import static tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlModelHelper.withElements;

import java.util.List;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.VisibilityKind;
import tools.vitruv.applications.cbs.commonalities.tests.oo.InterfaceTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase;

public class UmlInterfaceTestModels extends UmlTestModelsBase implements InterfaceTest.DomainModels {

	private static Package newUmlPackage1() {
		Package umlPackage = UMLFactory.eINSTANCE.createPackage();
		umlPackage.setName(PACKAGE_1_NAME);
		return umlPackage;
	}

	private static Package newUmlPackage2() {
		Package umlPackage = UMLFactory.eINSTANCE.createPackage();
		umlPackage.setName(PACKAGE_2_NAME);
		return umlPackage;
	}

	private static Interface newUmlInterface1() {
		Interface umlInterface = UMLFactory.eINSTANCE.createInterface();
		umlInterface.setName(INTERFACE_1_NAME);
		umlInterface.setVisibility(VisibilityKind.PUBLIC_LITERAL);
		return umlInterface;
	}

	private static Interface newUmlInterface2() {
		Interface umlInterface = newUmlInterface1();
		umlInterface.setName(INTERFACE_2_NAME);
		return umlInterface;
	}

	private static Interface newUmlInterface3() {
		Interface umlInterface = newUmlInterface1();
		umlInterface.setName(INTERFACE_3_NAME);
		return umlInterface;
	}

	private static Generalization newUmlGeneralization(Interface general) {
		Generalization generalization = UMLFactory.eINSTANCE.createGeneralization();
		generalization.setGeneral(general);
		return generalization;
	}

	public UmlInterfaceTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	// Empty interface

	@Override
	public DomainModel emptyInterfaceCreation() {
		return newModel(() -> {
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage1(), newUmlInterface1()));
			return List.of(umlModel);
		});
	}

	// Multiple interfaces

	@Override
	public DomainModel multipleInterfacesInSamePackageCreation() {
		return newModel(() -> {
			Model umlModel = withElements(newUmlModel(),
					withElements(newUmlPackage1(), newUmlInterface1(), newUmlInterface2()));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel multipleInterfacesInDifferentPackagesCreation() {
		return newModel(() -> {
			Model umlModel = withElements(newUmlModel(),
					withElements(newUmlPackage1(), newUmlInterface1()),
					withElements(newUmlPackage2(), newUmlInterface2()));
			return List.of(umlModel);
		});
	}

	// Super interfaces

	@Override
	public DomainModel interfaceWithSuperInterfaceCreation() {
		return newModel(() -> {
			Package umlPackage = newUmlPackage1();
			Interface umlInterface2 = newUmlInterface2();
			umlPackage.getPackagedElements().add(umlInterface2);
			Interface umlInterface1 = newUmlInterface1();
			umlInterface1.getGeneralizations().add(newUmlGeneralization(umlInterface2));
			umlPackage.getPackagedElements().add(umlInterface1);
			Model umlModel = withElements(newUmlModel(), umlPackage);
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel interfaceWithMultipleSuperInterfacesCreation() {
		return newModel(() -> {
			Package umlPackage = newUmlPackage1();
			Interface umlInterface2 = newUmlInterface2();
			Interface umlInterface3 = newUmlInterface3();
			umlPackage.getPackagedElements().add(umlInterface2);
			umlPackage.getPackagedElements().add(umlInterface3);
			Interface umlInterface1 = newUmlInterface1();
			umlInterface1.getGeneralizations().add(newUmlGeneralization(umlInterface2));
			umlInterface1.getGeneralizations().add(newUmlGeneralization(umlInterface3));
			umlPackage.getPackagedElements().add(umlInterface1);
			Model umlModel = withElements(newUmlModel(), umlPackage);
			return List.of(umlModel);
		});
	}
}

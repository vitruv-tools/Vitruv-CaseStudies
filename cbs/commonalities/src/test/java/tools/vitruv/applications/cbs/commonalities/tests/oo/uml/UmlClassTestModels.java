package tools.vitruv.applications.cbs.commonalities.tests.oo.uml;

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.newUmlModel;
import static tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlModelHelper.withElements;

import java.util.List;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.VisibilityKind;
import tools.vitruv.applications.cbs.commonalities.tests.oo.ClassTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase;

public class UmlClassTestModels extends UmlTestModelsBase implements ClassTest.DomainModels {

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

	private static Class newBasicUmlClass1() {
		Class umlClass = UMLFactory.eINSTANCE.createClass();
		umlClass.setName(CLASS_1_NAME);
		return umlClass;
	}

	private static Class newUmlClass1() {
		Class umlClass = newBasicUmlClass1();
		umlClass.setVisibility(VisibilityKind.PUBLIC_LITERAL);
		return umlClass;
	}

	private static Class newUmlClass2() {
		Class umlClass = newUmlClass1();
		umlClass.setName(CLASS_2_NAME);
		return umlClass;
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

	private static Generalization newUmlGeneralization(Classifier general) {
		Generalization generalization = UMLFactory.eINSTANCE.createGeneralization();
		generalization.setGeneral(general);
		return generalization;
	}

	private static InterfaceRealization newUmlInterfaceRealization(Interface contract) {
		InterfaceRealization interfaceRealization = UMLFactory.eINSTANCE.createInterfaceRealization();
		interfaceRealization.setContract(contract);
		return interfaceRealization;
	}

	private static Model newUmlModelWithClassInPackage1(Class umlClass) {
		return withElements(newUmlModel(), withElements(newUmlPackage1(), umlClass));
	}

	public UmlClassTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	/**
	 * Returning <code>null</code> results in no visibility being set.
	 */
	protected VisibilityKind defaultClassVisibility() {
		return null;
	}

	private Class withDefaultVisibility(Class umlClass) {
		VisibilityKind defaultVisibility = defaultClassVisibility();
		if (defaultVisibility != null) {
			umlClass.setVisibility(defaultVisibility);
		}
		return umlClass;
	}

	// Empty class

	@Override
	public DomainModel emptyClassCreation() {
		return newModel(() -> {
			Model umlModel = newUmlModelWithClassInPackage1(withDefaultVisibility(newBasicUmlClass1()));
			return List.of(umlModel);
		});
	}

	// Visibility

	@Override
	public DomainModel privateClassCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass1();
			umlClass.setVisibility(VisibilityKind.PRIVATE_LITERAL);
			Model umlModel = newUmlModelWithClassInPackage1(umlClass);
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel publicClassCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass1();
			umlClass.setVisibility(VisibilityKind.PUBLIC_LITERAL);
			Model umlModel = newUmlModelWithClassInPackage1(umlClass);
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel protectedClassCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass1();
			umlClass.setVisibility(VisibilityKind.PROTECTED_LITERAL);
			Model umlModel = newUmlModelWithClassInPackage1(umlClass);
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel packagePrivateClassCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass1();
			umlClass.setVisibility(VisibilityKind.PACKAGE_LITERAL);
			Model umlModel = newUmlModelWithClassInPackage1(umlClass);
			return List.of(umlModel);
		});
	}

	// Modifiers

	@Override
	public DomainModel finalClassCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass1();
			umlClass.setIsFinalSpecialization(true);
			Model umlModel = newUmlModelWithClassInPackage1(umlClass);
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel abstractClassCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass1();
			umlClass.setIsAbstract(true);
			Model umlModel = newUmlModelWithClassInPackage1(umlClass);
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel classWithMultipleModifiersCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass1();
			umlClass.setIsAbstract(true);
			umlClass.setIsFinalSpecialization(true);
			Model umlModel = newUmlModelWithClassInPackage1(umlClass);
			return List.of(umlModel);
		});
	}

	// Multiple classes

	@Override
	public DomainModel multipleClassesInSamePackageCreation() {
		return newModel(() -> {
			Model umlModel = withElements(newUmlModel(),
					withElements(newUmlPackage1(), newUmlClass1(), newUmlClass2()));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel multipleClassesInDifferentPackagesCreation() {
		return newModel(() -> {
			Model umlModel = withElements(newUmlModel(),
					withElements(newUmlPackage1(), newUmlClass1()),
					withElements(newUmlPackage2(), newUmlClass2()));
			return List.of(umlModel);
		});
	}

	// Super class

	@Override
	public DomainModel classWithSuperClassCreation() {
		return newModel(() -> {
			Package umlPackage = newUmlPackage1();
			Class umlClass2 = newUmlClass2();
			umlPackage.getPackagedElements().add(umlClass2);
			Class umlClass1 = newUmlClass1();
			umlClass1.getGeneralizations().add(newUmlGeneralization(umlClass2));
			umlPackage.getPackagedElements().add(umlClass1);
			Model umlModel = withElements(newUmlModel(), umlPackage);
			return List.of(umlModel);
		});
	}

	// Implemented interfaces

	@Override
	public DomainModel classImplementingInterfaceCreation() {
		return newModel(() -> {
			Package umlPackage = newUmlPackage1();
			Interface umlInterface1 = newUmlInterface1();
			umlPackage.getPackagedElements().add(umlInterface1);
			Class umlClass1 = newUmlClass1();
			umlClass1.getInterfaceRealizations().add(newUmlInterfaceRealization(umlInterface1));
			umlPackage.getPackagedElements().add(umlClass1);
			Model umlModel = withElements(newUmlModel(), umlPackage);
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel classImplementingMultipleInterfacesCreation() {
		return newModel(() -> {
			Package umlPackage = newUmlPackage1();
			Interface umlInterface1 = newUmlInterface1();
			Interface umlInterface2 = newUmlInterface2();
			umlPackage.getPackagedElements().add(umlInterface1);
			umlPackage.getPackagedElements().add(umlInterface2);
			Class umlClass1 = newUmlClass1();
			umlClass1.getInterfaceRealizations().add(newUmlInterfaceRealization(umlInterface1));
			umlClass1.getInterfaceRealizations().add(newUmlInterfaceRealization(umlInterface2));
			umlPackage.getPackagedElements().add(umlClass1);
			Model umlModel = withElements(newUmlModel(), umlPackage);
			return List.of(umlModel);
		});
	}
}

package tools.vitruv.applications.cbs.commonalities.tests.oo.uml;

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.newUmlModel;
import static tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlModelHelper.withElements;

import java.util.List;

import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
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

    /**
     * Interface 1 generalizing the given interfaces.
     */
    private static Interface newUmlInterface1Extending(Interface... superInterfaces) {
        Interface umlInterface1 = newUmlInterface1();
        for (Interface superInterface : superInterfaces) {
            Generalization generalization = UMLFactory.eINSTANCE.createGeneralization();
            generalization.setGeneral(superInterface);
            umlInterface1.getGeneralizations().add(generalization);
        }
        return umlInterface1;
    }

    public UmlInterfaceTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    // Empty interface

    @Override
    public DomainModel emptyInterfaceCreation() {
        return newModel(() -> List.of(withElements(newUmlModel(), withElements(newUmlPackage1(), newUmlInterface1()))));
    }

    // Multiple interfaces

    @Override
    public DomainModel multipleInterfacesInSamePackageCreation() {
        return newModel(() -> List.of(withElements(newUmlModel(),
                withElements(newUmlPackage1(), newUmlInterface1(), newUmlInterface2()))));
    }

    @Override
    public DomainModel multipleInterfacesInDifferentPackagesCreation() {
        return newModel(() -> List.of(withElements(newUmlModel(),
                withElements(newUmlPackage1(), newUmlInterface1()),
                withElements(newUmlPackage2(), newUmlInterface2()))));
    }

    // Super interfaces

    @Override
    public DomainModel interfaceWithSuperInterfaceCreation() {
        return newModel(() -> {
            Interface umlInterface2 = newUmlInterface2();
            return List.of(withElements(newUmlModel(),
                    withElements(newUmlPackage1(), umlInterface2, newUmlInterface1Extending(umlInterface2))));
        });
    }

    @Override
    public DomainModel interfaceWithMultipleSuperInterfacesCreation() {
        return newModel(() -> {
            Interface umlInterface2 = newUmlInterface2();
            Interface umlInterface3 = newUmlInterface3();
            return List.of(withElements(newUmlModel(), withElements(newUmlPackage1(), umlInterface2, umlInterface3,
                    newUmlInterface1Extending(umlInterface2, umlInterface3))));
        });
    }
}

package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml;

import static tools.vitruv.applications.cbs.commonalities.tests.util.common.NamingUtil.toFirstLower;

import java.util.List;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.VisibilityKind;

import tools.vitruv.applications.cbs.commonalities.tests.cbs.ProvidedRoleTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase;

public class UmlProvidedRoleTestModels extends UmlTestModelsBase implements ProvidedRoleTest.DomainModels {

    private static Package newUmlComponentPackage() {
        Package componentPackage = UMLFactory.eINSTANCE.createPackage();
        componentPackage.setName(toFirstLower(COMPONENT_NAME));
        return componentPackage;
    }

    private static Class newUmlComponentClass() {
        Class componentClass = UMLFactory.eINSTANCE.createClass();
        componentClass.setName(COMPONENT_NAME + "Impl");
        componentClass.setVisibility(VisibilityKind.PUBLIC_LITERAL);
        return componentClass;
    }

    private static InterfaceRealization newUmlInterfaceRealization(Interface contract) {
        InterfaceRealization interfaceRealization = UMLFactory.eINSTANCE.createInterfaceRealization();
        interfaceRealization.setContract(contract);
        return interfaceRealization;
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

    /**
     * Adds a component package with a component class that realizes the given
     * interfaces to the repository package.
     */
    private static void addComponentProvidingInterfaces(UmlRepositoryModel umlRepositoryModel,
            Interface... providedInterfaces) {
        Package componentPackage = newUmlComponentPackage();
        Class componentClass = newUmlComponentClass();
        for (Interface providedInterface : providedInterfaces) {
            componentClass.getInterfaceRealizations().add(newUmlInterfaceRealization(providedInterface));
        }
        componentPackage.getPackagedElements().add(componentClass);
        umlRepositoryModel.getRepositoryPackage().getPackagedElements().add(componentPackage);
    }

    public UmlProvidedRoleTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    @Override
    public DomainModel componentWithProvidedRoleCreation() {
        return newModel(() -> {
            UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();
            Interface interface1 = newUmlInterface1();
            umlRepositoryModel.getContractsPackage().getPackagedElements().add(interface1);
            addComponentProvidingInterfaces(umlRepositoryModel, interface1);
            return List.of(umlRepositoryModel.getModel());
        });
    }

    @Override
    public DomainModel componentWithMultipleProvidedRolesCreation() {
        return newModel(() -> {
            UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();
            Interface interface1 = newUmlInterface1();
            Interface interface2 = newUmlInterface2();
            umlRepositoryModel.getContractsPackage().getPackagedElements().add(interface1);
            umlRepositoryModel.getContractsPackage().getPackagedElements().add(interface2);
            addComponentProvidingInterfaces(umlRepositoryModel, interface1, interface2);
            return List.of(umlRepositoryModel.getModel());
        });
    }
}

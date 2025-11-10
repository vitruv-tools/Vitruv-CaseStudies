package tools.vitruv.applications.cbs.commonalities.tests.cbs.java;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.modifiers.ModifiersFactory;

import tools.vitruv.applications.cbs.commonalities.tests.cbs.ProvidedRoleTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase;
import tools.vitruv.applications.util.temporary.java.JavaModificationUtil;

public class JavaProvidedRoleTestModels extends JavaTestModelsBase implements ProvidedRoleTest.DomainModels {

    private static Package newJavaComponentPackage() {
        Package componentPackage = ContainersFactory.eINSTANCE.createPackage();
        componentPackage.setName(COMPONENT_NAME.toLowerCase());
        return componentPackage;
    }

    private static Class newJavaComponentClass() {
        Class componentClass = ClassifiersFactory.eINSTANCE.createClass();
        componentClass.setName(COMPONENT_NAME + "Impl");
        componentClass.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        return componentClass;
    }

    private static Interface newJavaInterface1() {
        Interface interface1 = ClassifiersFactory.eINSTANCE.createInterface();
        interface1.setName(INTERFACE_1_NAME);
        interface1.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        return interface1;
    }

    private static Interface newJavaInterface2() {
        Interface interface2 = newJavaInterface1();
        interface2.setName(INTERFACE_2_NAME);
        return interface2;
    }

    public JavaProvidedRoleTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    @Override
    public DomainModel componentWithProvidedRoleCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package contractsPackage = javaRepositoryModel.getContractsPackage();
            Package repositoryPackage = javaRepositoryModel.getRepositoryPackage();

            // Create interface
            Interface interface1 = newJavaInterface1();
            CompilationUnit compilationUnitInterface1 = JavaModelHelper.newCompilationUnit(
                    contractsPackage,
                    interface1);

            // Create component
            Package componentPackage = JavaModelHelper.insertJavaPackage(
                    repositoryPackage,
                    newJavaComponentPackage());
            Class componentClass = newJavaComponentClass();
            componentClass.getImplements().add(
                    JavaModificationUtil.createNamespaceClassifierReference(interface1));
            CompilationUnit compilationUnitClass = JavaModelHelper.newCompilationUnit(
                    componentPackage,
                    componentClass);

            // Combine all root objects
            List<EObject> rootObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            rootObjects.add(compilationUnitInterface1);
            rootObjects.add(componentPackage);
            rootObjects.add(compilationUnitClass);
            return rootObjects;
        });
    }

    @Override
    public DomainModel componentWithMultipleProvidedRolesCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package contractsPackage = javaRepositoryModel.getContractsPackage();
            Package repositoryPackage = javaRepositoryModel.getRepositoryPackage();

            // Create interfaces
            Interface interface1 = newJavaInterface1();
            CompilationUnit compilationUnitInterface1 = JavaModelHelper.newCompilationUnit(
                    contractsPackage,
                    interface1);

            Interface interface2 = newJavaInterface2();
            CompilationUnit compilationUnitInterface2 = JavaModelHelper.newCompilationUnit(
                    contractsPackage,
                    interface2);

            // Create component implementing both interfaces
            Package componentPackage = JavaModelHelper.insertJavaPackage(
                    repositoryPackage,
                    newJavaComponentPackage());
            Class componentClass = newJavaComponentClass();
            componentClass.getImplements().add(
                    JavaModificationUtil.createNamespaceClassifierReference(interface1));
            componentClass.getImplements().add(
                    JavaModificationUtil.createNamespaceClassifierReference(interface2));
            CompilationUnit compilationUnitClass = JavaModelHelper.newCompilationUnit(
                    componentPackage,
                    componentClass);

            // Combine all root objects
            List<EObject> rootObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            rootObjects.add(compilationUnitInterface1);
            rootObjects.add(compilationUnitInterface2);
            rootObjects.add(componentPackage);
            rootObjects.add(compilationUnitClass);
            return rootObjects;
        });
    }
}
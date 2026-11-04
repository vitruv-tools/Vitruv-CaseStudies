package tools.vitruv.applications.cbs.commonalities.tests.cbs.java;

import static tools.vitruv.applications.cbs.commonalities.tests.util.common.NamingUtil.toFirstLower;
import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.insertJavaPackage;
import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.newCompilationUnit;

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
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase;
import tools.vitruv.applications.util.temporary.java.JavaModificationUtil;

public class JavaProvidedRoleTestModels extends JavaTestModelsBase implements ProvidedRoleTest.DomainModels {

    private static Package newJavaComponentPackage() {
        Package componentPackage = ContainersFactory.eINSTANCE.createPackage();
        componentPackage.setName(toFirstLower(COMPONENT_NAME));
        return componentPackage;
    }

    private static Class newJavaComponentClass(Interface... providedInterfaces) {
        Class componentClass = ClassifiersFactory.eINSTANCE.createClass();
        componentClass.setName(COMPONENT_NAME + "Impl");
        componentClass.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        for (Interface providedInterface : providedInterfaces) {
            componentClass.getImplements()
                    .add(JavaModificationUtil.createNamespaceClassifierReference(providedInterface));
        }
        return componentClass;
    }

    private static Interface newJavaInterface1() {
        Interface javaInterface = ClassifiersFactory.eINSTANCE.createInterface();
        javaInterface.setName(INTERFACE_1_NAME);
        javaInterface.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        return javaInterface;
    }

    private static Interface newJavaInterface2() {
        Interface javaInterface = newJavaInterface1();
        javaInterface.setName(INTERFACE_2_NAME);
        return javaInterface;
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
            Interface interface1 = newJavaInterface1();
            CompilationUnit compilationUnitInterface1 = newCompilationUnit(contractsPackage, interface1);
            Package componentPackage = insertJavaPackage(repositoryPackage, newJavaComponentPackage());
            Class componentClass = newJavaComponentClass(interface1);
            CompilationUnit compilationUnitClass = newCompilationUnit(componentPackage, componentClass);
            return javaRepositoryModel.getRootObjectsWith(compilationUnitInterface1, componentPackage,
                    compilationUnitClass);
        });
    }

    @Override
    public DomainModel componentWithMultipleProvidedRolesCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package contractsPackage = javaRepositoryModel.getContractsPackage();
            Package repositoryPackage = javaRepositoryModel.getRepositoryPackage();
            Interface interface1 = newJavaInterface1();
            CompilationUnit compilationUnitInterface1 = newCompilationUnit(contractsPackage, interface1);
            Interface interface2 = newJavaInterface2();
            CompilationUnit compilationUnitInterface2 = newCompilationUnit(contractsPackage, interface2);
            Package componentPackage = insertJavaPackage(repositoryPackage, newJavaComponentPackage());
            Class componentClass = newJavaComponentClass(interface1, interface2);
            CompilationUnit compilationUnitClass = newCompilationUnit(componentPackage, componentClass);
            return javaRepositoryModel.getRootObjectsWith(compilationUnitInterface1, compilationUnitInterface2,
                    componentPackage, compilationUnitClass);
        });
    }
}

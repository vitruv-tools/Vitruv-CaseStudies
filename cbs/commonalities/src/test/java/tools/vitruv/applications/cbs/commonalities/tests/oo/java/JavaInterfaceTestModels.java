package tools.vitruv.applications.cbs.commonalities.tests.oo.java;

import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.newCompilationUnit;

import java.util.List;

import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.modifiers.ModifiersFactory;

import tools.vitruv.applications.cbs.commonalities.tests.oo.InterfaceTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase;
import tools.vitruv.applications.util.temporary.java.JavaModificationUtil;

public class JavaInterfaceTestModels extends JavaTestModelsBase implements InterfaceTest.DomainModels {

    private static Package newJavaPackage1() {
        Package javaPackage = ContainersFactory.eINSTANCE.createPackage();
        javaPackage.setName(PACKAGE_1_NAME);
        return javaPackage;
    }

    private static Package newJavaPackage2() {
        Package javaPackage = ContainersFactory.eINSTANCE.createPackage();
        javaPackage.setName(PACKAGE_2_NAME);
        return javaPackage;
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

    private static Interface newJavaInterface3() {
        Interface javaInterface = newJavaInterface1();
        javaInterface.setName(INTERFACE_3_NAME);
        return javaInterface;
    }

    /**
     * Interface 1 extending the given interfaces.
     */
    private static Interface newJavaInterface1Extending(Interface... superInterfaces) {
        Interface javaInterface1 = newJavaInterface1();
        for (Interface superInterface : superInterfaces) {
            javaInterface1.getExtends().add(JavaModificationUtil.createNamespaceClassifierReference(superInterface));
        }
        return javaInterface1;
    }

    public JavaInterfaceTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    // Empty interface

    @Override
    public DomainModel emptyInterfaceCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage1();
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, newJavaInterface1());
            return List.of(javaPackage, javaCompilationUnit);
        });
    }

    // Multiple interfaces

    @Override
    public DomainModel multipleInterfacesInSamePackageCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage1();
            CompilationUnit javaCompilationUnit1 = newCompilationUnit(javaPackage, newJavaInterface1());
            CompilationUnit javaCompilationUnit2 = newCompilationUnit(javaPackage, newJavaInterface2());
            return List.of(javaPackage, javaCompilationUnit1, javaCompilationUnit2);
        });
    }

    @Override
    public DomainModel multipleInterfacesInDifferentPackagesCreation() {
        return newModel(() -> {
            Package javaPackage1 = newJavaPackage1();
            Package javaPackage2 = newJavaPackage2();
            CompilationUnit javaCompilationUnit1 = newCompilationUnit(javaPackage1, newJavaInterface1());
            CompilationUnit javaCompilationUnit2 = newCompilationUnit(javaPackage2, newJavaInterface2());
            return List.of(javaPackage1, javaPackage2, javaCompilationUnit1, javaCompilationUnit2);
        });
    }

    // Super interfaces

    @Override
    public DomainModel interfaceWithSuperInterfaceCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage1();
            Interface javaInterface2 = newJavaInterface2();
            CompilationUnit javaCompilationUnit1 = newCompilationUnit(javaPackage, javaInterface2);
            CompilationUnit javaCompilationUnit2 = newCompilationUnit(javaPackage,
                    newJavaInterface1Extending(javaInterface2));
            return List.of(javaPackage, javaCompilationUnit1, javaCompilationUnit2);
        });
    }

    @Override
    public DomainModel interfaceWithMultipleSuperInterfacesCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage1();
            Interface javaInterface2 = newJavaInterface2();
            Interface javaInterface3 = newJavaInterface3();
            CompilationUnit javaCompilationUnit1 = newCompilationUnit(javaPackage, javaInterface2);
            CompilationUnit javaCompilationUnit2 = newCompilationUnit(javaPackage, javaInterface3);
            CompilationUnit javaCompilationUnit3 = newCompilationUnit(javaPackage,
                    newJavaInterface1Extending(javaInterface2, javaInterface3));
            return List.of(javaPackage, javaCompilationUnit1, javaCompilationUnit2, javaCompilationUnit3);
        });
    }
}

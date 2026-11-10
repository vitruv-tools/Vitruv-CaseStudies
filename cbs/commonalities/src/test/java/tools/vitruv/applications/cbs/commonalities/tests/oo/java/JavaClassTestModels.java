package tools.vitruv.applications.cbs.commonalities.tests.oo.java;

import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.newCompilationUnit;

import java.util.List;

import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.modifiers.ModifiersFactory;

import tools.vitruv.applications.cbs.commonalities.tests.oo.ClassTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase;
import tools.vitruv.applications.util.temporary.java.JavaModificationUtil;

public class JavaClassTestModels extends JavaTestModelsBase implements ClassTest.DomainModels {

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

    private static Class newBasicJavaClass1() {
        Class javaClass = ClassifiersFactory.eINSTANCE.createClass();
        javaClass.setName(CLASS_1_NAME);
        return javaClass;
    }

    private static Class newJavaClass1() {
        Class javaClass = newBasicJavaClass1();
        javaClass.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic()); // Default visibility
        return javaClass;
    }

    private static Class newJavaClass2() {
        Class javaClass = newJavaClass1();
        javaClass.setName(CLASS_2_NAME);
        return javaClass;
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

    /**
     * Creates a basic class 1 with the given modifiers.
     */
    private static Class newBasicJavaClass1(Modifier... modifiers) {
        Class javaClass = newBasicJavaClass1();
        List.of(modifiers).forEach(javaClass.getAnnotationsAndModifiers()::add);
        return javaClass;
    }

    /**
     * Creates class 1 (public) with the given additional modifiers.
     */
    private static Class newJavaClass1(Modifier... modifiers) {
        Class javaClass = newJavaClass1();
        List.of(modifiers).forEach(javaClass.getAnnotationsAndModifiers()::add);
        return javaClass;
    }

    public JavaClassTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    /**
     * Returning <code>null</code> results in no visibility modifier being
     * added and therefore package-private visibility.
     */
    protected Modifier defaultClassVisibility() {
        return null; // package-private
    }

    private Class withDefaultVisibility(Class javaClass) {
        Modifier defaultVisibility = defaultClassVisibility();
        if (defaultVisibility != null) {
            javaClass.getAnnotationsAndModifiers().add(defaultVisibility);
        }
        return javaClass;
    }

    // Empty class

    @Override
    public DomainModel emptyClassCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage1();
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage,
                    withDefaultVisibility(newBasicJavaClass1()));
            return List.of(javaPackage, javaCompilationUnit);
        });
    }

    // Visibility

    @Override
    public DomainModel privateClassCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage1();
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage,
                    newBasicJavaClass1(ModifiersFactory.eINSTANCE.createPrivate()));
            return List.of(javaPackage, javaCompilationUnit);
        });
    }

    @Override
    public DomainModel publicClassCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage1();
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage,
                    newBasicJavaClass1(ModifiersFactory.eINSTANCE.createPublic()));
            return List.of(javaPackage, javaCompilationUnit);
        });
    }

    @Override
    public DomainModel protectedClassCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage1();
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage,
                    newBasicJavaClass1(ModifiersFactory.eINSTANCE.createProtected()));
            return List.of(javaPackage, javaCompilationUnit);
        });
    }

    @Override
    public DomainModel packagePrivateClassCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage1();
            // The created class has no modifiers and is therefore package-private.
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, newBasicJavaClass1());
            return List.of(javaPackage, javaCompilationUnit);
        });
    }

    // Modifiers

    @Override
    public DomainModel finalClassCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage1();
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage,
                    newJavaClass1(ModifiersFactory.eINSTANCE.createFinal()));
            return List.of(javaPackage, javaCompilationUnit);
        });
    }

    @Override
    public DomainModel abstractClassCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage1();
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage,
                    newJavaClass1(ModifiersFactory.eINSTANCE.createAbstract()));
            return List.of(javaPackage, javaCompilationUnit);
        });
    }

    @Override
    public DomainModel classWithMultipleModifiersCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage1();
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage,
                    newBasicJavaClass1(
                            ModifiersFactory.eINSTANCE.createPublic(),
                            ModifiersFactory.eINSTANCE.createFinal(),
                            ModifiersFactory.eINSTANCE.createAbstract()));
            return List.of(javaPackage, javaCompilationUnit);
        });
    }

    // Multiple classes

    @Override
    public DomainModel multipleClassesInSamePackageCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage1();
            Class javaClass1 = newJavaClass1();
            Class javaClass2 = newJavaClass2();
            CompilationUnit javaCompilationUnit1 = newCompilationUnit(javaPackage, javaClass1);
            CompilationUnit javaCompilationUnit2 = newCompilationUnit(javaPackage, javaClass2);
            return List.of(javaPackage, javaCompilationUnit1, javaCompilationUnit2);
        });
    }

    @Override
    public DomainModel multipleClassesInDifferentPackagesCreation() {
        return newModel(() -> {
            Package javaPackage1 = newJavaPackage1();
            Package javaPackage2 = newJavaPackage2();
            Class javaClass1 = newJavaClass1();
            Class javaClass2 = newJavaClass2();
            CompilationUnit javaCompilationUnit1 = newCompilationUnit(javaPackage1, javaClass1);
            CompilationUnit javaCompilationUnit2 = newCompilationUnit(javaPackage2, javaClass2);
            return List.of(javaPackage1, javaPackage2, javaCompilationUnit1, javaCompilationUnit2);
        });
    }

    // Super class

    @Override
    public DomainModel classWithSuperClassCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage1();
            Class javaClass2 = newJavaClass2();
            Class javaClass1 = newJavaClass1();
            javaClass1.setExtends(JavaModificationUtil.createNamespaceClassifierReference(javaClass2));
            CompilationUnit javaCompilationUnit1 = newCompilationUnit(javaPackage, javaClass2);
            CompilationUnit javaCompilationUnit2 = newCompilationUnit(javaPackage, javaClass1);
            return List.of(javaPackage, javaCompilationUnit1, javaCompilationUnit2);
        });
    }

    // Implemented interfaces

    @Override
    public DomainModel classImplementingInterfaceCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage1();
            Interface javaInterface1 = newJavaInterface1();
            Class javaClass1 = newJavaClass1();
            javaClass1.getImplements().add(JavaModificationUtil.createNamespaceClassifierReference(javaInterface1));
            CompilationUnit javaCompilationUnit1 = newCompilationUnit(javaPackage, javaInterface1);
            CompilationUnit javaCompilationUnit2 = newCompilationUnit(javaPackage, javaClass1);
            return List.of(javaPackage, javaCompilationUnit1, javaCompilationUnit2);
        });
    }

    @Override
    public DomainModel classImplementingMultipleInterfacesCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage1();
            Interface javaInterface1 = newJavaInterface1();
            Interface javaInterface2 = newJavaInterface2();
            Class javaClass1 = newJavaClass1();
            javaClass1.getImplements().add(JavaModificationUtil.createNamespaceClassifierReference(javaInterface1));
            javaClass1.getImplements().add(JavaModificationUtil.createNamespaceClassifierReference(javaInterface2));
            CompilationUnit javaCompilationUnit1 = newCompilationUnit(javaPackage, javaInterface1);
            CompilationUnit javaCompilationUnit2 = newCompilationUnit(javaPackage, javaInterface2);
            CompilationUnit javaCompilationUnit3 = newCompilationUnit(javaPackage, javaClass1);
            return List.of(javaPackage, javaCompilationUnit1, javaCompilationUnit2, javaCompilationUnit3);
        });
    }
}

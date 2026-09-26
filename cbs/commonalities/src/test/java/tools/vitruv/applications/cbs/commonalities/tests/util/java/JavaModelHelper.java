package tools.vitruv.applications.cbs.commonalities.tests.util.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;

import edu.kit.ipd.sdq.activextendannotations.Utility;
import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaFilePathHelper.getCompilationUnitName;

@Utility
public final class JavaModelHelper {

    private JavaModelHelper() {
        // Utility class - prevent instantiation
    }

    public static List<String> getFullPackageNamespaces(Package javaPackage) {
        List<String> namespaces = new ArrayList<>(javaPackage.getNamespaces());
        namespaces.addAll(Arrays.asList(javaPackage.getName()));
        return namespaces; // TODO duplication with JavaPersistenceHelper
    }

    public static Package newJavaPackage(Package parentPackage, String packageName) {
        Package newPackage = ContainersFactory.eINSTANCE.createPackage();
        insertJavaPackage(parentPackage, newPackage);
        newPackage.setName(packageName);
        return newPackage;
    }

    public static Package insertJavaPackage(Package parentPackage, Package subPackage) {
        subPackage.getNamespaces().clear();
        if (parentPackage != null) {
            subPackage.getNamespaces().addAll(getFullPackageNamespaces(parentPackage));
        }
        return subPackage;
    }

    public static CompilationUnit newCompilationUnit(Package parentPackage, String fileName) {
        CompilationUnit unit = ContainersFactory.eINSTANCE.createCompilationUnit();
        insertCompilationUnit(parentPackage, unit);
        unit.setName(getCompilationUnitName(unit.getNamespaces(), fileName));
        return unit;
    }

    public static CompilationUnit insertCompilationUnit(Package parentPackage, CompilationUnit compilationUnit) {
        compilationUnit.getNamespaces().clear();
        if (parentPackage != null) {
            compilationUnit.getNamespaces().addAll(getFullPackageNamespaces(parentPackage));
        }
        return compilationUnit;
    }

    public static <C extends ConcreteClassifier> CompilationUnit newCompilationUnit(Package parentPackage,
            C classifier) {
        CompilationUnit unit = newCompilationUnit(parentPackage, classifier.getName());
        unit.getClassifiers().add(classifier);
        return unit;
    }
}
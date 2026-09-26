package tools.vitruv.applications.cbs.commonalities.tests.cbs.java;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.modifiers.ModifiersFactory;

import tools.vitruv.applications.cbs.commonalities.tests.cbs.BasicComponentTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase;
import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.insertJavaPackage;
import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.newCompilationUnit;

public class JavaBasicComponentTestModels extends JavaTestModelsBase implements BasicComponentTest.DomainModels {

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

    public JavaBasicComponentTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    @Override
    public DomainModel emptyBasicComponentCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package repositoryPackage = javaRepositoryModel.getRepositoryPackage();

            Package componentPackage = insertJavaPackage(repositoryPackage, newJavaComponentPackage());

            Class componentClass = newJavaComponentClass();
            CompilationUnit compilationUnit = newCompilationUnit(componentPackage, componentClass);

            List<EObject> rootObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            rootObjects.add(componentPackage);
            rootObjects.add(compilationUnit);

            return rootObjects;
        });
    }
}
package tools.vitruv.applications.cbs.commonalities.tests.cbs.java;

import java.util.ArrayList;
import java.util.List;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.modifiers.ModifiersFactory;

import org.eclipse.emf.ecore.EObject;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.ComponentInterfaceTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase;
import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.newCompilationUnit;

public class JavaComponentInterfaceTestModels extends JavaTestModelsBase
        implements ComponentInterfaceTest.DomainModels {

    public JavaComponentInterfaceTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    @Override
    public DomainModel emptyComponentInterfaceCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package contractsPackage = javaRepositoryModel.getContractsPackage();

            Interface javaInterface = ClassifiersFactory.eINSTANCE.createInterface();
            javaInterface.setName(INTERFACE_NAME);
            javaInterface.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
            CompilationUnit compilationUnit = newCompilationUnit(contractsPackage, javaInterface);

            List<EObject> rootObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            rootObjects.add(compilationUnit);

            return rootObjects;
        });
    }
}
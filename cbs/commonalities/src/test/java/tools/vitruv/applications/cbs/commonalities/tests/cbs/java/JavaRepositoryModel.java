package tools.vitruv.applications.cbs.commonalities.tests.cbs.java;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.containers.Package;

import tools.vitruv.applications.cbs.commonalities.tests.TestConstants.Java;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper;

public class JavaRepositoryModel {
    private final Package repositoryPackage;
    private final Package datatypesPackage;
    private final Package contractsPackage;

    public JavaRepositoryModel() {
        this.repositoryPackage = JavaModelHelper.newJavaPackage(null, Java.REPOSITORY_PACKAGE_NAME);
        this.datatypesPackage = JavaModelHelper.newJavaPackage(repositoryPackage, Java.DATATYPES_PACKAGE_NAME);
        this.contractsPackage = JavaModelHelper.newJavaPackage(repositoryPackage, Java.CONTRACTS_PACKAGE_NAME);
    }

    public Package getRepositoryPackage() {
        return repositoryPackage;
    }

    public Package getDatatypesPackage() {
        return datatypesPackage;
    }

    public Package getContractsPackage() {
        return contractsPackage;
    }

    public List<? extends EObject> getRootObjects() {
        return List.of(repositoryPackage, datatypesPackage, contractsPackage);
    }

    /**
     * Returns the root objects of the repository followed by the given
     * additional root objects.
     */
    public List<EObject> getRootObjectsWith(EObject... additionalRootObjects) {
        List<EObject> rootObjects = new ArrayList<>(getRootObjects());
        rootObjects.addAll(List.of(additionalRootObjects));
        return rootObjects;
    }
}
package tools.vitruv.applications.pcmjava.tests.util;

import java.io.IOException;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.system.SystemFactory;

public class Pcm2JavaTestUtils {

    public static final String REPOSITORY_NAME = "testRepository";
    public static final String BASIC_COMPONENT_NAME = "TestBasicComponent";
    public static final String IMPLEMENTING_CLASS_NAME = BASIC_COMPONENT_NAME + "Impl";
    public static final String INTERFACE_NAME = "TestInterface";
    public static final String RENAME = "Rename";
    public static final String OPERATION_SIGNATURE_1_NAME = "TestOperationSignature1";
    public static final String PARAMETER_NAME = "testParameterName";
    public static final String COMPOSITE_DATA_TYPE_NAME = "CompositeDataType";
    public static final String INNER_DEC_NAME = "testInnerDec";
    public static final String SYSTEM_NAME = "TestSystem";
    public static final String ASSEMBLY_CONTEXT_NAME = "assemblyContext";
    public static final String COMPOSITE_COMPONENT_NAME = "TestCompositeComponent";
    public static final String COLLECTION_DATA_TYPE_NAME = "TestCollectionDatatype";

    // private ctor for util class
    private Pcm2JavaTestUtils() {
    }

    public static Repository createRepository( final String repositoryName) throws IOException {
        final Repository repo = RepositoryFactory.eINSTANCE.createRepository();
        repo.setEntityName(repositoryName);
        return repo;
    }

    public static BasicComponent createBasicComponent(final Repository repo, final String name) {
        final BasicComponent basicComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
        basicComponent.setRepository__RepositoryComponent(repo);
        basicComponent.setEntityName(name);
        return basicComponent;
    }

    public static CompositeComponent createCompositeComponent(final Repository repo, final String name) {
        final CompositeComponent compositeComponent = RepositoryFactory.eINSTANCE.createCompositeComponent();
        compositeComponent.setRepository__RepositoryComponent(repo);
        compositeComponent.setEntityName(name);
        return compositeComponent;
    }

    public static System createSystem(final String systemName)
            throws Throwable {
        final System system = SystemFactory.eINSTANCE.createSystem();
        system.setEntityName(systemName);
        return system;
    }
}

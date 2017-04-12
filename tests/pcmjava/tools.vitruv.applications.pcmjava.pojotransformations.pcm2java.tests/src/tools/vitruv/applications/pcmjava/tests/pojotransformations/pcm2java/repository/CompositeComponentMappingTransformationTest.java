package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import java.io.IOException;

import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.junit.Assert;
import org.junit.Test;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;

public class CompositeComponentMappingTransformationTest extends Pcm2JavaTransformationTest {

    @Test
    public void testCreateCompositeComponent() throws Throwable {
        final CompositeComponent compositeComponent = this.createAndSyncRepoAndCompositeComponent();

        this.assertCompositeComponentCorrespondences(compositeComponent, Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME);
    }

    @Test
    public void testAddProvidedRoleToCompositeComponent() throws Throwable {
        final CompositeComponent compositeComponent = this.createAndSyncRepoAndCompositeComponent();
        final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(
                compositeComponent.getRepository__RepositoryComponent(), Pcm2JavaTestUtils.INTERFACE_NAME);

        final OperationProvidedRole providedRole = super.createAndSyncOperationProvidedRole(opInterface,
                compositeComponent);

        this.assertCompositeComponentCorrespondences(compositeComponent, Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME);
        this.assertOperationProvidedRole(providedRole);
    }

    @Test
    public void testAddRequiredRoleToCompositeComponent() throws Throwable {
        final CompositeComponent compositeComponent = this.createAndSyncRepoAndCompositeComponent();
        final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(
                compositeComponent.getRepository__RepositoryComponent(), Pcm2JavaTestUtils.INTERFACE_NAME);

        final OperationRequiredRole operationRequiredRole = super.createAndSyncOperationRequiredRole(opInterface,
                compositeComponent);

        this.assertCompositeComponentCorrespondences(compositeComponent, Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME);
        this.assertOperationRequiredRole(operationRequiredRole);
    }

    @Test
    public void testAddAssemblyContextToCompositeComponent() throws Throwable {
        final CompositeComponent compositeComponent = this.createAndSyncRepoAndCompositeComponent();
        final BasicComponent basicComponent = super.addBasicComponentAndSync(compositeComponent
                .getRepository__RepositoryComponent());

        final AssemblyContext assemblyContext = this.createAndSyncAssemblyContext(compositeComponent, basicComponent);

        this.assertCompositeComponentCorrespondences(compositeComponent, Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME);
        super.assertAssemblyContext(assemblyContext);
    }

    @Test
    public void testAddRequiredDelegationRoleToCompositeComponent() throws Throwable {
        final CompositeComponent compositeComponent = this.createAndSyncRepoAndCompositeComponent();
        final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(
                compositeComponent.getRepository__RepositoryComponent(), Pcm2JavaTestUtils.INTERFACE_NAME);
        Assert.assertNotNull(opInterface);
    }

    private CompositeComponent createAndSyncRepoAndCompositeComponent() throws IOException {
        final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
        final CompositeComponent compositeComponent = this.addCompositeComponentAndSync(repo);
        return compositeComponent;
    }

    private CompositeComponent addCompositeComponentAndSync(final Repository repo) throws IOException {
        final CompositeComponent cc = RepositoryFactory.eINSTANCE.createCompositeComponent();
        cc.setEntityName(Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME);
        cc.setRepository__RepositoryComponent(repo);
        super.saveAndSynchronizeChanges(repo);
        return cc;
    }

    /**
     * a composite component should correspond to a class and a package
     *
     * @param compositeComponent
     * @throws Throwable
     */
    @SuppressWarnings("unchecked")
    private void assertCompositeComponentCorrespondences(final CompositeComponent compositeComponent,
            final String expectedName) throws Throwable {
        super.assertCorrespondnecesAndCompareNames(compositeComponent, 3, new java.lang.Class[] { Package.class,
                org.emftext.language.java.classifiers.Class.class, CompilationUnit.class }, new String[] {
                expectedName, expectedName + "Impl", expectedName + "Impl" });
    }

}

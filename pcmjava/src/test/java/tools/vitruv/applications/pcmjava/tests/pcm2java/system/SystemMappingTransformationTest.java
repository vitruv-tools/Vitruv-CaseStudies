package tools.vitruv.applications.pcmjava.tests.pcm2java.system;

import java.nio.file.Path;
import java.util.List;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils;
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaViewFactory;
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaClassBuilder;
import tools.vitruv.applications.util.temporary.java.JavaSetup;
import tools.vitruv.framework.vsum.VirtualModel;

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimSinglePcmSystem;

public class SystemMappingTransformationTest extends Pcm2JavaTransformationTest {

    @Override
    protected boolean enableTransitiveCyclicChangePropagation() {
        return false;
    }

    @BeforeAll
    public static void setupJavaFactories() {
        JavaSetup.prepareFactories();
    }

    protected Path getProjectModelPath(final String modelName, final String modelFileExtension) {
        return Path.of(Pcm2JavaTransformationTest.PCM_MODEL_FOLDER_NAME)
                .resolve(((modelName + ".") + modelFileExtension));
    }

    @BeforeEach
    @Override
    public void setupViewFactory() {
        VirtualModel virtualModel = this.getVirtualModel();
        Pcm2JavaViewFactory pcm2JavaViewFactory = new Pcm2JavaViewFactory(virtualModel);
        this.viewFactory = pcm2JavaViewFactory;
    }

    @Test
    public void testCreateSystem() throws Exception {
        createSystem(Pcm2JavaTestUtils.SYSTEM_NAME);
        this.viewFactory.validateJavaView(view -> {
            var systemCompilationUnit = new FluentJavaClassBuilder(
                    Pcm2JavaTestUtils.SYSTEM_NAME + Pcm2JavaTestUtils.IMPL_SUFFIX,
                    Pcm2JavaTestUtils.SYSTEM_NAMESPACE).build();

            assertExistenceOfCompilationUnitsDeeplyEqualTo(view, List.of(systemCompilationUnit));
        });
    }

    @Test
    public void testRenameSystem() throws Exception {
        createSystem(Pcm2JavaTestUtils.SYSTEM_NAME);

        this.viewFactory.changePcmView(view -> {
            var system = claimSinglePcmSystem(view);
            system.setEntityName(Pcm2JavaTestUtils.SYSTEM_NAME + Pcm2JavaTestUtils.RENAME_SUFFIX);
        });

        this.viewFactory.validateJavaView(view -> {
            FluentJavaClassBuilder systemCompilationUnit = new FluentJavaClassBuilder(
                    Pcm2JavaTestUtils.SYSTEM_NAME + Pcm2JavaTestUtils.RENAME_SUFFIX + Pcm2JavaTestUtils.IMPL_SUFFIX,
                    Pcm2JavaTestUtils.SYSTEM_NAMESPACE + Pcm2JavaTestUtils.RENAME_SUFFIX);

            assertExistenceOfCompilationUnitsDeeplyEqualTo(view, List.of(systemCompilationUnit.build()));
        });
    }

    @Test
    public void testRemoveSystem() throws Exception {
        createSystem(Pcm2JavaTestUtils.SYSTEM_NAME);

        this.viewFactory.changePcmView(view -> {
            var system = claimSinglePcmSystem(view);
            EcoreUtil.delete(system);
        });

        this.viewFactory.validateJavaView(view -> {
            assertExistenceOfCompilationUnitsDeeplyEqualTo(view, List.of());
            assertExistenceOfPackagesDeeplyEqualTo(view, List.of());
        });
    }

    public static class BidirectionalTest extends SystemMappingTransformationTest {
        @Override
        protected boolean enableTransitiveCyclicChangePropagation() {
            return true;
        }
    }
}
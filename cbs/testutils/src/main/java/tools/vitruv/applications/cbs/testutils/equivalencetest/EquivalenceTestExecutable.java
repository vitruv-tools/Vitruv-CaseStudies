package tools.vitruv.applications.cbs.testutils.equivalencetest;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.xbase.lib.Pair;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.function.Executable;

import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor;
import tools.vitruv.applications.cbs.testutils.ModelComparisonSettings;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.change.testutils.TestUserInteraction;
import tools.vitruv.change.testutils.printing.DefaultPrintIdProvider;
import tools.vitruv.change.testutils.printing.ModelPrinting;
import tools.vitruv.change.testutils.printing.PrintIdProvider;
import tools.vitruv.change.testutils.printing.UriReplacingPrinter;
import tools.vitruv.change.testutils.views.BasicTestView;
import tools.vitruv.change.testutils.views.ChangePublishingTestView;
import tools.vitruv.change.testutils.views.TestView;
import tools.vitruv.change.testutils.views.UriMode;

import static com.google.common.base.Preconditions.checkNotNull;

import static java.nio.file.FileVisitResult.*;

import static org.hamcrest.MatcherAssert.assertThat;

import tools.vitruv.change.testutils.matchers.ModelDeepEqualityOption;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.containsModelOf;

public class EquivalenceTestExecutable implements Executable, AutoCloseable {
    private static final TestProjectManager testProjectManager = new TestProjectManager();
    private final MetamodelStep testStep;
    private final Map<MetamodelDescriptor, List<MetamodelStep>> dependencySteps;
    private final Map<MetamodelDescriptor, MetamodelStep> referenceSteps;
    private final Collection<ChangePropagationSpecification> changePropagationSpecifications;
    private final UriMode uriMode;
    private final ModelComparisonSettings comparisonSettings;
    private final EquivalenceTestExtensionContext extensionContext;

    public EquivalenceTestExecutable(
            MetamodelStep testStep,
            Map<MetamodelDescriptor, List<MetamodelStep>> dependencySteps,
            Map<MetamodelDescriptor, MetamodelStep> referenceSteps,
            Collection<ChangePropagationSpecification> changePropagationSpecifications,
            UriMode uriMode,
            ModelComparisonSettings comparisonSettings,
            EquivalenceTestExtensionContext extensionContext) {
        this.testStep = testStep;
        this.dependencySteps = dependencySteps;
        this.referenceSteps = referenceSteps;
        this.changePropagationSpecifications = changePropagationSpecifications;
        this.uriMode = uriMode;
        this.comparisonSettings = comparisonSettings;
        this.extensionContext = extensionContext;
    }

    @Override
    public void execute() throws Throwable {
        try (
                DirectoryTestView testView = setupTestView();
                DirectoryTestView referenceView = setupReferenceView();
                AutoCloseable printerChange = installViewDirectoryUriReplacement(testView, referenceView)) {
            checkNotNull(printerChange); // Suppress warning for variable not being used
            executeDependencies(testView, referenceView);

            testStep.executeIn(testView);
            for (MetamodelStep step : referenceSteps.values()) {
                step.executeIn(referenceView);
            }
            verifyTestViewResults();
        } catch (Throwable t) {
            extensionContext.getExecutionException().ifPresentOrElse(
                    original -> {
                        original.addSuppressed(t);
                        throw new RuntimeException(original);
                    },
                    () -> {
                        throw new RuntimeException(t);
                    });
            throw t;
        } finally {
            close();
        }
    }

    private DirectoryTestView setupTestView() {
        Path viewDirectory = testProjectManager.getProject("", extensionContext);
        Path vsumDirectory = testProjectManager.getProject("vsum", extensionContext);

        Collection<ChangePropagationSpecification> changePropagationSpecifications = this.changePropagationSpecifications;
        TestUserInteraction userInteraction = new TestUserInteraction();
        var vsum = new VirtualModelBuilder()
                .withStorageFolder(vsumDirectory)
                .withUserInteractorForResultProvider(new TestUserInteraction.ResultProvider(userInteraction))
                .withChangePropagationSpecifications(changePropagationSpecifications)
                .buildAndInitialize();

        return new DirectoryTestView(
                new ChangePublishingTestView(viewDirectory, userInteraction, uriMode, vsum,
                        (resource, resolver) -> vsum.getUuidResolver()),
                viewDirectory);
    }

    private DirectoryTestView setupReferenceView() {
        return newBasicView(testProjectManager.getProject("reference", extensionContext));
    }

    private DirectoryTestView setupReadOnlyTestView() {
        return newBasicView(testProjectManager.getProject("", extensionContext));
    }

    private void executeDependencies(TestView testView, TestView referenceView) {
        for (MetamodelStep dependencyTestStep : dependencySteps.getOrDefault(testStep.getTargetMetamodel(),
                Collections.emptyList())) {
            dependencyTestStep.executeIn(testView);
        }
        for (MetamodelDescriptor referenceMetamodel : getReferenceMetamodels()) {
            for (MetamodelStep dependencyReferenceStep : this.dependencySteps.getOrDefault(referenceMetamodel,
                    Collections.emptyList())) {
                dependencyReferenceStep.executeIn(referenceView);
            }
        }
        try {
            verifyTestViewResults();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    private void verifyTestViewResults() throws Throwable {
        try (
                DirectoryTestView testView = setupReadOnlyTestView();
                DirectoryTestView referenceView = setupReferenceView()) {
            Set<Path> referenceFiles = dataFiles(referenceView.directory,
                    file -> getReferenceMetamodels().stream().anyMatch(m -> belongsTo(file, m)));

            assertThat(testView, containsExactlyResources(referenceView, referenceFiles));

            for (Path model : referenceFiles) {
                Resource referenceResource = referenceView.resourceAt(model);
                MetamodelDescriptor referenceMetamodel = getReferenceMetamodels().stream()
                        .filter(m -> belongsTo(referenceResource, m))
                        .findFirst()
                        .orElseThrow(
                                () -> new IllegalStateException("Cannot find metamodel of " + referenceResource + "!"));
                List<? extends ModelDeepEqualityOption> filterList = comparisonSettings
                        .getEqualityOptionsForMetamodel(referenceMetamodel);
                ModelDeepEqualityOption[] filters = filterList.toArray(new ModelDeepEqualityOption[0]);

                assertThat(testView.resourceAt(model), containsModelOf(referenceResource, filters));
            }
        }
    }

    private AutoCloseable installViewDirectoryUriReplacement(TestView testView, TestView referenceView) {
        Map<URI, URI> replacements = Map.of(
                referenceView.getUri(Path.of(".")).appendSegment(""),
                URI.createFileURI("[reference view]/"),
                testView.getUri(Path.of(".")).appendSegment(""),
                URI.createFileURI("[test view]/"));
        return ModelPrinting.prepend(new UriReplacingPrinter((List<Pair<URI, URI>>) replacements));
    }

    @Override
    public void close() {
        try {
            testProjectManager.afterEach(extensionContext);
        } catch (Exception e) {
            throw new RuntimeException("Exception during afterEach", e);
        }
        extensionContext.close();
    }

    private static Set<Path> dataFiles(Path path, Predicate<Path> predicate) {
        Set<Path> result = new LinkedHashSet<>();
        try {
            java.nio.file.Files.walkFileTree(path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    String dirName = dir.getFileName().toString();
                    if (dirName.startsWith("[") && dirName.endsWith("]") && !dir.equals(path)) {
                        return SKIP_SUBTREE;
                    } else {
                        return CONTINUE;
                    }
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path relativized = path.relativize(file);
                    if (predicate.test(relativized)) {
                        result.add(relativized);
                    }
                    return CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private static boolean belongsTo(Path path, MetamodelDescriptor metamodel) {
        return metamodel.fileExtensions.stream().anyMatch(ext -> path.toString().endsWith("." + ext));
    }

    private static boolean belongsTo(Resource resource, MetamodelDescriptor metamodel) {
        return metamodel.fileExtensions.contains(resource.getURI().fileExtension());
    }

    private Matcher<? super DirectoryTestView> containsExactlyResources(TestView referenceView,
            Set<Path> referenceFiles) {
        return new ModelFilesMatcher(referenceFiles, referenceView, getReferenceMetamodels());
    }

    private Set<MetamodelDescriptor> getReferenceMetamodels() {
        return referenceSteps.keySet();
    }

    private DirectoryTestView newBasicView(Path viewDirectory) {
        return new DirectoryTestView(new BasicTestView(viewDirectory, uriMode), viewDirectory);
    }

    public static class DirectoryTestView implements TestView, AutoCloseable {
        private final TestView delegate;
        private final Path directory;

        public DirectoryTestView(TestView delegate, Path directory) {
            this.delegate = delegate;
            this.directory = directory;
        }

        public Path getDirectory() {
            return directory;
        }

        @Override
        public void close() throws Exception {
            if (delegate instanceof AutoCloseable) {
                ((AutoCloseable) delegate).close();
            }
        }

        @Override
        public <T extends EObject> T from(Class<T> arg0, URI arg1) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'from'");
        }

        @Override
        public URI getUri(Path arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getUri'");
        }

        @Override
        public TestUserInteraction getUserInteraction() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getUserInteraction'");
        }

        @Override
        public void moveTo(Resource arg0, Path arg1) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'moveTo'");
        }

        @Override
        public void moveTo(Resource arg0, URI arg1) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'moveTo'");
        }

        @Override
        public <T extends Notifier> List<PropagatedChange> propagate(T arg0, Consumer<T> arg1) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'propagate'");
        }

        @Override
        public <T extends Notifier> T record(T arg0, Consumer<T> arg1) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'record'");
        }

        @Override
        public Resource resourceAt(URI arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'resourceAt'");
        }

        // Delegate all TestView methods to 'delegate'
        // ... (implement as needed)
    }

    private static class ModelFilesMatcher extends TypeSafeMatcher<DirectoryTestView> {
        private final Set<Path> referenceFiles;
        private final TestView referenceView;
        private final Set<MetamodelDescriptor> referenceMetamodels;
        private Set<Path> testFiles;
        private final PrintIdProvider idProvider = new DefaultPrintIdProvider();

        public ModelFilesMatcher(Set<Path> referenceFiles, TestView referenceView,
                Set<MetamodelDescriptor> referenceMetamodels) {
            this.referenceFiles = referenceFiles;
            this.referenceView = referenceView;
            this.referenceMetamodels = referenceMetamodels;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("exactly these resource paths to exist in the test view: ");
            // Implement appendPrintResult and printSet as needed
            // description.appendPrintResult(...);
        }

        @Override
        protected boolean matchesSafely(DirectoryTestView testView) {
            testFiles = dataFiles(testView.getDirectory(),
                    file -> referenceMetamodels.stream().anyMatch(m -> belongsTo(file, m)));
            return testFiles.equals(referenceFiles);
        }

        @Override
        protected void describeMismatchSafely(DirectoryTestView testView, Description mismatchDescription) {
            Set<Path> missingResources = new LinkedHashSet<>(referenceFiles);
            missingResources.removeAll(testFiles);
            Set<Path> unexpectedResources = new LinkedHashSet<>(testFiles);
            unexpectedResources.removeAll(referenceFiles);

            // Map missing/unexpected resources to model values if needed
            // and append to mismatchDescription using appendModelValueSet
            if (!missingResources.isEmpty()) {
                mismatchDescription.appendText("the following resources are missing in the test view: ");
                // mismatchDescription.appendModelValueSet(...);
            }
            if (!unexpectedResources.isEmpty()) {
                if (!missingResources.isEmpty()) {
                    mismatchDescription.appendText(System.lineSeparator()).appendText("    and ");
                }
                mismatchDescription.appendText("the test view contains the following unexpected resources: ");
                // mismatchDescription.appendModelValueSet(...);
            }
        }
    }
}
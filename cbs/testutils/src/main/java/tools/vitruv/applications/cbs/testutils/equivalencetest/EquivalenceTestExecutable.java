package tools.vitruv.applications.cbs.testutils.equivalencetest;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;
import static org.hamcrest.MatcherAssert.assertThat;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.containsModelOf;
import static tools.vitruv.change.testutils.printing.ModelPrinting.appendModelValueSet;
import static tools.vitruv.change.testutils.printing.ModelPrinting.appendPrintResult;
import static tools.vitruv.change.testutils.printing.PrintMode.MULTI_LINE_LIST;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.function.Executable;

import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor;
import tools.vitruv.applications.cbs.testutils.ModelComparisonSettings;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.change.testutils.TestUserInteraction;
import tools.vitruv.change.testutils.matchers.ModelDeepEqualityOption;
import tools.vitruv.change.testutils.printing.DefaultPrintIdProvider;
import tools.vitruv.change.testutils.printing.ModelPrinting;
import tools.vitruv.change.testutils.printing.PrintIdProvider;
import tools.vitruv.change.testutils.printing.UriReplacingPrinter;
import tools.vitruv.change.testutils.views.BasicTestView;
import tools.vitruv.change.testutils.views.ChangePublishingTestView;
import tools.vitruv.change.testutils.views.TestView;
import tools.vitruv.change.testutils.views.UriMode;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.internal.ModelInstance;

/**
 * Executes one equivalence test: runs the dependencies and the test step in a view backed by a virtual model, runs
 * the reference steps in a plain view, and checks that both views contain the same models.
 */
class EquivalenceTestExecutable implements Executable, AutoCloseable {
    private static final TestProjectManager testProjectManager = new TestProjectManager();
    private final MetamodelStep testStep;
    private final Map<MetamodelDescriptor, List<MetamodelStep>> dependencySteps;
    private final Map<MetamodelDescriptor, MetamodelStep> referenceSteps;
    private final Collection<ChangePropagationSpecification> changePropagationSpecifications;
    private final UriMode uriMode;
    private final ModelComparisonSettings comparisonSettings;
    private final EquivalenceTestExtensionContext extensionContext;

    EquivalenceTestExecutable(MetamodelStep testStep, Map<MetamodelDescriptor, List<MetamodelStep>> dependencySteps,
            Map<MetamodelDescriptor, MetamodelStep> referenceSteps,
            Collection<ChangePropagationSpecification> changePropagationSpecifications, UriMode uriMode,
            ModelComparisonSettings comparisonSettings, EquivalenceTestExtensionContext extensionContext) {
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
        try (DirectoryTestView testView = setupTestView();
                DirectoryTestView referenceView = setupReferenceView();
                AutoCloseable printerChange = installViewDirectoryUriReplacement(testView, referenceView)) {
            checkNotNull(printerChange); // Suppress warning for variable not being used
            executeDependencies(testView, referenceView);

            testStep.executeIn(testView);
            referenceSteps.values().forEach(step -> step.executeIn(referenceView));
            verifyTestViewResults();
        } catch (Throwable throwable) {
            extensionContext.setExecutionException(Optional.of(throwable));
            throw throwable;
        } finally {
            close();
        }
    }

    private DirectoryTestView setupTestView() throws IOException {
        Path viewDirectory = testProjectManager.getProject("", extensionContext);
        Path vsumDirectory = testProjectManager.getProject("vsum", extensionContext);

        TestUserInteraction userInteraction = new TestUserInteraction();
        InternalVirtualModel vsum = new VirtualModelBuilder()
                .withStorageFolder(vsumDirectory)
                .withUserInteractorForResultProvider(new TestUserInteraction.ResultProvider(userInteraction))
                .withChangePropagationSpecifications(changePropagationSpecifications)
                .buildAndInitialize();
        return new DirectoryTestView(
                new ChangePublishingTestView(viewDirectory, userInteraction, uriMode, vsum, vsum.getUuidResolver(),
                        uri -> {
                            ModelInstance modelInstance = vsum.getModelInstance(uri);
                            return modelInstance != null ? modelInstance.getResource() : null;
                        }),
                viewDirectory);
    }

    private DirectoryTestView setupReferenceView() {
        return newBasicView(testProjectManager.getProject("reference", extensionContext));
    }

    private DirectoryTestView setupReadOnlyTestView() {
        return newBasicView(testProjectManager.getProject("", extensionContext));
    }

    private void executeDependencies(TestView testView, TestView referenceView) throws Throwable {
        for (MetamodelStep dependencyTestStep : dependencySteps.getOrDefault(testStep.getTargetMetamodel(),
                List.of())) {
            dependencyTestStep.executeIn(testView);
        }
        for (MetamodelDescriptor referenceMetamodel : getReferenceMetamodels()) {
            for (MetamodelStep dependencyReferenceStep : dependencySteps.getOrDefault(referenceMetamodel,
                    List.of())) {
                dependencyReferenceStep.executeIn(referenceView);
            }
        }
        verifyTestViewResults();
    }

    private void verifyTestViewResults() throws Throwable {
        // use new views to reload the actual resources from disk
        try (DirectoryTestView testView = setupReadOnlyTestView();
                DirectoryTestView referenceView = setupReferenceView()) {
            Set<Path> referenceFiles = dataFiles(referenceView.getDirectory(), this::belongsToReferenceMetamodel);

            assertThat(testView, containsExactlyResources(referenceView, referenceFiles));

            for (Path model : referenceFiles) {
                Resource referenceResource = referenceView.resourceAt(model);
                MetamodelDescriptor referenceMetamodel = checkNotNull(
                        getReferenceMetamodels().stream()
                                .filter(metamodel -> belongsTo(referenceResource, metamodel))
                                .findFirst()
                                .orElse(null),
                        "Cannot find metamodel of %s!", referenceResource);
                ModelDeepEqualityOption[] filters = comparisonSettings
                        .getEqualityOptionsForMetamodel(referenceMetamodel)
                        .toArray(ModelDeepEqualityOption[]::new);

                assertThat(testView.resourceAt(model), containsModelOf(referenceResource, filters));
            }
        }
    }

    private AutoCloseable installViewDirectoryUriReplacement(TestView testView, TestView referenceView) {
        return ModelPrinting.prepend(new UriReplacingPrinter(List.of(
                // mind the order, the test view is a prefix of the reference view!
                Map.entry(referenceView.getUri(Path.of(".")).appendSegment(""),
                        URI.createFileURI("[reference view]/")),
                Map.entry(testView.getUri(Path.of(".")).appendSegment(""), URI.createFileURI("[test view]/")))));
    }

    @Override
    public void close() throws Exception {
        testProjectManager.afterEach(extensionContext);
        extensionContext.close();
    }

    private static Set<Path> dataFiles(Path path, Predicate<Path> predicate) {
        Set<Path> result = new LinkedHashSet<>();
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    String dirName = dir.getFileName().toString();
                    if (dirName.startsWith("[") && dirName.endsWith("]") && !dir.equals(path)) {
                        return SKIP_SUBTREE;
                    }
                    return CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    Path relativized = path.relativize(file);
                    if (predicate.test(relativized)) {
                        result.add(relativized);
                    }
                    return CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return result;
    }

    private boolean belongsToReferenceMetamodel(Path file) {
        return getReferenceMetamodels().stream().anyMatch(metamodel -> belongsTo(file, metamodel));
    }

    private static boolean belongsTo(Path path, MetamodelDescriptor metamodel) {
        return metamodel.getFileExtensions().stream().anyMatch(extension -> path.toString().endsWith("." + extension));
    }

    private static boolean belongsTo(Resource resource, MetamodelDescriptor metamodel) {
        return metamodel.getFileExtensions().contains(resource.getURI().fileExtension());
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

    /**
     * A {@link TestView} that knows the directory it stores its models in.
     */
    private static class DirectoryTestView implements TestView {
        private final TestView delegate;
        private final Path directory;

        DirectoryTestView(TestView delegate, Path directory) {
            this.delegate = delegate;
            this.directory = directory;
        }

        Path getDirectory() {
            return directory;
        }

        @Override
        public Resource resourceAt(URI modelUri) {
            return delegate.resourceAt(modelUri);
        }

        @Override
        public Resource resourceAt(Path modelPathInProject) {
            return delegate.resourceAt(modelPathInProject);
        }

        @Override
        public <T extends EObject> T from(Class<T> clazz, URI modelUri) {
            return delegate.from(clazz, modelUri);
        }

        @Override
        public <T extends EObject> T from(Class<T> clazz, Resource resource) {
            return delegate.from(clazz, resource);
        }

        @Override
        public <T extends EObject> T from(Class<T> clazz, Path modelPathInProject) {
            return delegate.from(clazz, modelPathInProject);
        }

        @Override
        public URI getUri(Path viewRelativePath) {
            return delegate.getUri(viewRelativePath);
        }

        @Override
        public void moveTo(Resource resource, Path newViewRelativePath) {
            delegate.moveTo(resource, newViewRelativePath);
        }

        @Override
        public void moveTo(Resource resource, URI newUri) {
            delegate.moveTo(resource, newUri);
        }

        @Override
        public <T extends Notifier> T record(T notifier, Consumer<T> consumer) {
            return delegate.record(notifier, consumer);
        }

        @Override
        public <T extends Notifier> List<PropagatedChange> propagate(T notifier, Consumer<T> consumer) {
            return delegate.propagate(notifier, consumer);
        }

        @Override
        public TestUserInteraction getUserInteraction() {
            return delegate.getUserInteraction();
        }

        @Override
        public void close() throws Exception {
            delegate.close();
        }
    }

    private static class ModelFilesMatcher extends TypeSafeMatcher<DirectoryTestView> {
        private final Set<Path> referenceFiles;
        private final TestView referenceView;
        private final Set<MetamodelDescriptor> referenceMetamodels;
        private Set<Path> testFiles;
        private final PrintIdProvider idProvider = new DefaultPrintIdProvider();

        ModelFilesMatcher(Set<Path> referenceFiles, TestView referenceView,
                Set<MetamodelDescriptor> referenceMetamodels) {
            this.referenceFiles = referenceFiles;
            this.referenceView = referenceView;
            this.referenceMetamodels = referenceMetamodels;
        }

        @Override
        public void describeTo(Description description) {
            appendPrintResult(description.appendText("exactly these resource paths to exist in the test view: "),
                    target -> target.printSet(referenceFiles, MULTI_LINE_LIST,
                            (subTarget, path) -> subTarget.print(path.toString())));
        }

        @Override
        protected boolean matchesSafely(DirectoryTestView testView) {
            testFiles = dataFiles(testView.getDirectory(),
                    file -> referenceMetamodels.stream().anyMatch(metamodel -> belongsTo(file, metamodel)));
            return testFiles.equals(referenceFiles);
        }

        @Override
        protected void describeMismatchSafely(DirectoryTestView testView, Description mismatchDescription) {
            Set<Resource> missingResources = resourcesInReferenceView(difference(referenceFiles, testFiles));
            Set<Resource> unexpectedResources = resourcesInReferenceView(difference(testFiles, referenceFiles));

            if (!missingResources.isEmpty()) {
                appendModelValueSet(
                        mismatchDescription.appendText("the following resources are missing in the test view: "),
                        missingResources, MULTI_LINE_LIST, idProvider);
            }
            if (!unexpectedResources.isEmpty()) {
                if (!missingResources.isEmpty()) {
                    mismatchDescription.appendText(System.lineSeparator()).appendText("    and ");
                }
                appendModelValueSet(
                        mismatchDescription.appendText("the test view contains the following unexpected resources: "),
                        unexpectedResources, MULTI_LINE_LIST, idProvider);
            }
        }

        private static Set<Path> difference(Set<Path> minuend, Set<Path> subtrahend) {
            Set<Path> result = new LinkedHashSet<>(minuend);
            result.removeAll(subtrahend);
            return result;
        }

        private Set<Resource> resourcesInReferenceView(Set<Path> paths) {
            Set<Resource> resources = new LinkedHashSet<>();
            paths.forEach(path -> resources.add(referenceView.resourceAt(path)));
            return resources;
        }
    }
}

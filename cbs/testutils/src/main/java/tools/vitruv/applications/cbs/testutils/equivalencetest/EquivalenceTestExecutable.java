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
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.function.Executable;

import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor;
import tools.vitruv.applications.cbs.testutils.ModelComparisonSettings;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.testutils.integration.DefaultVirtualModelBasedTestView;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.change.testutils.TestUserInteraction;
import tools.vitruv.change.testutils.printing.PrintMode;
import tools.vitruv.change.testutils.printing.DefaultPrintIdProvider;
import tools.vitruv.change.testutils.printing.ModelPrinting;
import tools.vitruv.change.testutils.printing.PrintIdProvider;
import tools.vitruv.change.testutils.printing.UriReplacingPrinter;
import tools.vitruv.change.testutils.views.BasicTestView;
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

        return new DirectoryTestView(
                new DefaultVirtualModelBasedTestView(viewDirectory, vsumDirectory, changePropagationSpecifications,
                        uriMode),
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
        return ModelPrinting.prepend(new UriReplacingPrinter(List.of(
                Map.entry(referenceView.getUri(Path.of(".")).appendSegment(""),
                        URI.createFileURI("[reference view]/")),
                Map.entry(testView.getUri(Path.of(".")).appendSegment(""),
                        URI.createFileURI("[test view]/")))));
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
        public Resource resourceAt(URI uri) {
            return delegate.resourceAt(uri);
        }

        @Override
        public Resource resourceAt(Path viewRelativePath) {
            return delegate.resourceAt(viewRelativePath);
        }

        @Override
        public <T extends EObject> T from(Class<T> clazz, URI uri) {
            return delegate.from(clazz, uri);
        }

        @Override
        public <T extends EObject> T from(Class<T> clazz, Resource resource) {
            return delegate.from(clazz, resource);
        }

        @Override
        public <T extends EObject> T from(Class<T> clazz, Path viewRelativePath) {
            return delegate.from(clazz, viewRelativePath);
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
            ModelPrinting.appendPrintResult(
                    description.appendText("exactly these resource paths to exist in the test view: "),
                    target -> target.printSet(referenceFiles, PrintMode.MULTI_LINE_LIST,
                            (subTarget, path) -> subTarget.print(path.toString())));
        }

        @Override
        protected boolean matchesSafely(DirectoryTestView testView) {
            testFiles = dataFiles(testView.getDirectory(),
                    file -> referenceMetamodels.stream().anyMatch(m -> belongsTo(file, m)));
            return testFiles.equals(referenceFiles);
        }

        @Override
        protected void describeMismatchSafely(DirectoryTestView testView, Description mismatchDescription) {
            Set<Resource> missingResources = new LinkedHashSet<>();
            for (Path path : referenceFiles) {
                if (!testFiles.contains(path)) {
                    missingResources.add(referenceView.resourceAt(path));
                }
            }
            Set<Resource> unexpectedResources = new LinkedHashSet<>();
            for (Path path : testFiles) {
                if (!referenceFiles.contains(path)) {
                    unexpectedResources.add(referenceView.resourceAt(path));
                }
            }

            if (!missingResources.isEmpty()) {
                ModelPrinting.appendModelValueSet(
                        mismatchDescription.appendText("the following resources are missing in the test view: "),
                        missingResources, PrintMode.MULTI_LINE_LIST, idProvider);
            }
            if (!unexpectedResources.isEmpty()) {
                if (!missingResources.isEmpty()) {
                    mismatchDescription.appendText(System.lineSeparator()).appendText("    and ");
                }
                ModelPrinting.appendModelValueSet(
                        mismatchDescription.appendText("the test view contains the following unexpected resources: "),
                        unexpectedResources, PrintMode.MULTI_LINE_LIST, idProvider);
            }
        }
    }
}
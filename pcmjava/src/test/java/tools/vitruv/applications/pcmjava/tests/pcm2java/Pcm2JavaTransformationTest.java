package tools.vitruv.applications.pcmjava.tests.pcm2java;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.Member;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.system.SystemFactory;

import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmChangePropagationSpecification;
import tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaChangePropagationSpecification;
import tools.vitruv.applications.util.temporary.java.JavaSetup;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.testutils.printing.DefaultModelPrinter;
import tools.vitruv.change.testutils.printing.DefaultPrintIdProvider;
import tools.vitruv.change.testutils.printing.DefaultPrintTarget;
import tools.vitruv.framework.testutils.integration.ViewBasedVitruvApplicationTest;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.vsum.VirtualModel;

public class Pcm2JavaTransformationTest extends ViewBasedVitruvApplicationTest {

    protected Pcm2JavaViewFactory viewFactory;

    // === setup ===
    public static final String PCM_REPOSITORY_FILE_EXTENSION = "repository";
    public static final String PCM_SYSTEM_FILE_EXTENSION = "system";
    public static final String PCM_MODEL_FOLDER_NAME = "pcm";

    @Override
    protected boolean enableTransitiveCyclicChangePropagation() {
        return false;
    }

    @BeforeAll
    public static void setupJavaFactories() {
        JavaSetup.prepareFactories();
    }

    @BeforeEach
    public void setupViewFactory() {
        VirtualModel virtualModel = this.getVirtualModel();
        Pcm2JavaViewFactory pcm2JavaViewFactory = new Pcm2JavaViewFactory(virtualModel);
        this.viewFactory = pcm2JavaViewFactory;
    }

    @BeforeEach
    public void setupJavaClasspath() {
        JavaSetup.resetClasspathAndRegisterStandardLibrary();
    }

    protected Path getProjectModelPath(String modelName, String modelFileExtension) {
        return Path.of(PCM_MODEL_FOLDER_NAME).resolve(modelName + "." + modelFileExtension);
    }

    @Override
    protected Iterable<? extends ChangePropagationSpecification> getChangePropagationSpecifications() {
        Pcm2JavaChangePropagationSpecification pcm2JavaChangePropagationSpecification = new Pcm2JavaChangePropagationSpecification();
        Java2PcmChangePropagationSpecification java2PcmChangePropagationSpecification = new Java2PcmChangePropagationSpecification();

        return Collections.unmodifiableList(
                Arrays.asList(pcm2JavaChangePropagationSpecification, java2PcmChangePropagationSpecification));
    }

    // === creators ===
    private void createRepository(Procedure1<? super Repository> repositoryInitalization) throws Exception {
        try {
            final Procedure1<View> function = (View it) -> {
                Repository repository = RepositoryFactory.eINSTANCE.createRepository();
                repositoryInitalization.apply(repository);
                it.registerRoot(repository, this.getUri(this.getProjectModelPath(repository.getEntityName(),
                        Pcm2JavaTransformationTest.PCM_REPOSITORY_FILE_EXTENSION)));
            };
            this.viewFactory.changePcmView(function);
        } catch (Exception e) {
            throw e;
        }
    }

    protected void createRepository(String repositoryName) throws Exception {
        Procedure1<Repository> function = (Repository it) -> {
            it.setEntityName(repositoryName);
        };
        this.createRepository(function);
    }

    private void createSystem(Procedure1<? super org.palladiosimulator.pcm.system.System> systemInitialization)
            throws Exception {
        try {
            final Procedure1<View> function = (View it) -> {
                final org.palladiosimulator.pcm.system.System system = SystemFactory.eINSTANCE.createSystem();
                systemInitialization.apply(system);
                it.registerRoot(system, this.getUri(this.getProjectModelPath(system.getEntityName(),
                        Pcm2JavaTransformationTest.PCM_SYSTEM_FILE_EXTENSION)));
            };
            this.viewFactory.changePcmView(function);
        } catch (Exception e) {
            throw Exceptions.sneakyThrow(e);
        }
    }

    protected void createSystem(String systemName) throws Exception {
        createSystem(system -> system.setEntityName(systemName));
    }

    // === assertions ===
    protected void assertExistenceOfCompilationUnitsDeeplyEqualTo(View view,
            Collection<CompilationUnit> expectedCompilationUnits) {
        EcoreUtil.EqualityHelper equalityHelper = new EcoreUtil.EqualityHelper();
        Collection<CompilationUnit> allActualCompilationUnits = JavaQueryUtil.getJavaCompilationUnits(view);

        assertEquals(expectedCompilationUnits.size(), allActualCompilationUnits.size());
        assertFalse(hasDuplicate(
                allActualCompilationUnits.stream().map(CompilationUnit::getName).collect(Collectors.toList())));
        assertFalse(hasDuplicate(
                expectedCompilationUnits.stream().map(CompilationUnit::getName).collect(Collectors.toList())));

        this.ignoreComparisonOfJavaUtilClassifieres(equalityHelper, allActualCompilationUnits,
                expectedCompilationUnits);
        for (CompilationUnit expected : expectedCompilationUnits) {
            CompilationUnit actualUnit = JavaQueryUtil.claimJavaCompilationUnit(view, expected.getName());
            adaptClassMemberOrderOfExpectedToActualOrder(expected, actualUnit);
            assertTrue(equalityHelper.equals(actualUnit, expected), modelComparisonErrorMessage(expected, actualUnit));
        }
    }

    protected void assertExistenceOfPackagesDeeplyEqualTo(View view,
            Collection<org.emftext.language.java.containers.Package> expectedPackages) {
        final EcoreUtil.EqualityHelper equalityHelper = new EcoreUtil.EqualityHelper();
        final Collection<org.emftext.language.java.containers.Package> allActualPackages = JavaQueryUtil
                .getJavaPackages(view);
        Assertions.assertEquals(expectedPackages.size(), allActualPackages.size());
        final Function1<org.emftext.language.java.containers.Package, String> _function = (
                org.emftext.language.java.containers.Package it) -> {
            return it.getName();
        };
        Assertions.assertFalse(this.<String>hasDuplicate(IterableExtensions.<String>toList(
                IterableExtensions.<org.emftext.language.java.containers.Package, String>map(allActualPackages,
                        _function))));
        final Function1<org.emftext.language.java.containers.Package, String> _function_1 = (
                org.emftext.language.java.containers.Package it) -> {
            return it.getName();
        };
        Assertions.assertFalse(this.<String>hasDuplicate(IterableExtensions.<String>toList(
                IterableExtensions.<org.emftext.language.java.containers.Package, String>map(expectedPackages,
                        _function_1))));
        final Consumer<org.emftext.language.java.containers.Package> _function_2 = (
                org.emftext.language.java.containers.Package it) -> {
            org.emftext.language.java.containers.Package actualPackage = JavaQueryUtil.claimJavaPackage(view,
                    it.getName());
            Assertions.assertTrue(equalityHelper.equals(actualPackage, it),
                    this.modelComparisonErrorMessage(it, actualPackage));
        };
        expectedPackages.forEach(_function_2);
    }

    private String modelComparisonErrorMessage(EObject expected, EObject actual) {
        DefaultPrintTarget printTarget = new DefaultPrintTarget();
        DefaultPrintIdProvider printIdProvider = new DefaultPrintIdProvider();
        DefaultModelPrinter modelPrinter = new DefaultModelPrinter();

        printTarget.print("Expected actual model to equal expected model.");
        printTarget.newLine();
        printTarget.print("Actual:");
        printTarget.newLine();
        modelPrinter.printObject(printTarget, printIdProvider, actual);

        printTarget.newLine();
        printTarget.print("Expected:");
        printTarget.newLine();
        modelPrinter.printObject(printTarget, printIdProvider, expected);

        return printTarget.toString();
    }

    /*
     * Within CollectionDataTypeMappingTransformationTest some classes of java.util
     * are used.
     * We don't want our tests to compare their internals as their are not part of
     * the test scope,
     * their comparison is potentially time consuming and could lead to unwanted
     * errors.
     *
     * The method puts all corresponding pairs of compilation units within namespace
     * java.util
     * into the EqualityHelper. Calling put on the EqualityHelper has the effect
     * that the
     * EqualityHelper treats them as equal and doesn't check their equality further.
     */
    private void ignoreComparisonOfJavaUtilClassifieres(EcoreUtil.EqualityHelper equalityHelper,
            Collection<CompilationUnit> actualCompilationUnits, Collection<CompilationUnit> expectedCompilationUnits) {
        List<CompilationUnit> expectedJavaUtilCompilationUnits = expectedCompilationUnits.stream()
                .filter(cu -> cu.getNamespaces().equals(Arrays.asList("java", "util"))).collect(Collectors.toList());
        List<CompilationUnit> actualJavaUtilCompilationUnits = actualCompilationUnits.stream()
                .filter(cu -> cu.getNamespaces().equals(Arrays.asList("java", "util"))).collect(Collectors.toList());

        for (CompilationUnit expectedCompilationUnit : expectedJavaUtilCompilationUnits) {
            CompilationUnit actualCompilationUnit = actualJavaUtilCompilationUnits.stream()
                    .filter(acu -> acu.getName().equals(expectedCompilationUnit.getName())).findFirst().get();

            ConcreteClassifier expectedClassifier = expectedCompilationUnit.getClassifiers().iterator().next();
            ConcreteClassifier actualClassifier = actualCompilationUnit.getClassifiers().iterator().next();
            equalityHelper.put(expectedClassifier, actualClassifier);
            equalityHelper.put(actualClassifier, expectedClassifier);
        }
    }

    private void adaptClassMemberOrderOfExpectedToActualOrder(CompilationUnit expected, CompilationUnit actual) {
        final Procedure2<ConcreteClassifier, Integer> function = (ConcreteClassifier expectedClassifier,
                Integer classifierIdx) -> {
            final ConcreteClassifier actualClassifier = actual.getClassifiers().get((classifierIdx).intValue());
            final Function<Object, Integer> function_1 = (Object item) -> {
                int xblockexpression = 0;
                {
                    final IntPredicate function_2 = (int idx) -> {
                        final Member actualMemberAtIdx = actualClassifier.getMembers().get(idx);
                        boolean namesEqualOrNotPresent = true;
                        if ((item instanceof NamedElement)) {
                            namesEqualOrNotPresent = actualMemberAtIdx.getName()
                                    .equals(((NamedElement) item).getName());
                        }
                        return (Objects.equals(actualMemberAtIdx.getClass(), item.getClass())
                                && namesEqualOrNotPresent);
                    };
                    OptionalInt maybeIndexInActualOfItem = IntStream.range(0, actualClassifier.getMembers().size())
                            .filter(function_2).findFirst();
                    int xifexpression = 0;
                    boolean _isPresent = maybeIndexInActualOfItem.isPresent();
                    if (_isPresent) {
                        xifexpression = maybeIndexInActualOfItem.getAsInt();
                    } else {
                        xifexpression = actualClassifier.getMembers().size();
                    }
                    xblockexpression = xifexpression;
                }
                return xblockexpression;
            };
            ECollections.<Member>sort(expectedClassifier.getMembers(),
                    Comparator.<Object, Integer>comparing(function_1));
        };
        IterableExtensions.<ConcreteClassifier>forEach(expected.getClassifiers(), function);
    }

    // === Misc ===
    private <T> boolean hasDuplicate(Collection<T> all) {
        Set<T> set = new HashSet<>(all);
        return set.size() != all.size();
    }
}
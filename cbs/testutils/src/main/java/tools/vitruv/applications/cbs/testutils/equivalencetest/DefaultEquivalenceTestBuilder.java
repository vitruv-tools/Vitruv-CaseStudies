package tools.vitruv.applications.cbs.testutils.equivalencetest;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.extension.ExtensionContext;

import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor;
import tools.vitruv.applications.cbs.testutils.ModelComparisonSettings;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.testutils.views.TestView;
import tools.vitruv.change.testutils.views.UriMode;

class DefaultEquivalenceTestBuilder extends DefaultBuilderCommon {
    private final ExtensionContext parentContext;
    private final Collection<ChangePropagationSpecification> changePropagationSpecifications;
    private final UriMode uriMode;
    private final ModelComparisonSettings modelComparisonSettings;

    DefaultEquivalenceTestBuilder(ExtensionContext parentContext,
            Collection<ChangePropagationSpecification> changePropagationSpecifications, UriMode uriMode,
            ModelComparisonSettings modelComparisonSettings) {
        super(Set.of());
        this.parentContext = parentContext;
        this.changePropagationSpecifications = changePropagationSpecifications;
        this.uriMode = uriMode;
        this.modelComparisonSettings = modelComparisonSettings;
    }

    @Override
    public void dependsOn(Consumer<EquivalenceTestBuilder> otherTest) {
        DependencyBuilder dependencyBuilder = new DependencyBuilder(steps.keySet());
        otherTest.accept(dependencyBuilder);
        dependencySteps.putAll(dependencyBuilder.getAllDependencySteps());
    }

    @Override
    public void stepFor(MetamodelDescriptor metamodel, Consumer<TestView> action) {
        checks.forStep(metamodel);
        addStep(steps, new MainStep(metamodel, action));
    }

    @Override
    public VariantOptions inputVariantFor(MetamodelDescriptor metamodel, String name, Consumer<TestView> action) {
        checks.forVariant(metamodel, name);
        return addStep(steps, new VariantStep(metamodel, action, name));
    }

    @Override
    public Iterable<? extends DynamicNode> testsThatStepsAreEquivalent() {
        checks.forFinalizing();
        // Created lazily, like the Xtend original: the tests are only instantiated when JUnit iterates over them.
        Iterable<DynamicTest> tests = () -> steps.entrySet().stream()
                .flatMap(entry -> testsFrom(entry.getKey(), entry.getValue()))
                .iterator();
        return tests;
    }

    private Stream<DynamicTest> testsFrom(MetamodelDescriptor testMetamodel, List<MetamodelStep> testSteps) {
        return IntStream.range(0, testSteps.size()).mapToObj(testIndex -> {
            MetamodelStep testStep = modifyIfNecessary(testSteps.get(testIndex));
            Map<MetamodelDescriptor, MetamodelStep> referenceSteps = testStep.determineReferenceSteps(steps);
            String testName = "FROM " + testMetamodel.getName() + " TO {"
                    + referenceSteps.keySet().stream().map(MetamodelDescriptor::getName)
                            .collect(Collectors.joining(", "))
                    + "}";
            if (testStep.getName() != null) {
                testName += " - " + testStep.getName();
            }
            EquivalenceTestExtensionContext testContext = new EquivalenceTestExtensionContext(testName, testIndex,
                    parentContext, testStep.getTargetMetamodel());
            return dynamicTest(testName, new EquivalenceTestExecutable(testStep, dependencySteps, referenceSteps,
                    changePropagationSpecifications, uriMode, modelComparisonSettings, testContext));
        });
    }

    private static class DependencyBuilder extends DefaultBuilderCommon {
        private static final VariantOptions MOCK_VARIANT_OPTIONS = new VariantOptions() {
            @Override
            public VariantOptions alsoCompareToMainStepOfSameMetamodel() {
                return this;
            }
        };

        DependencyBuilder(Set<MetamodelDescriptor> targetMetamodels) {
            super(targetMetamodels);
        }

        @Override
        public void dependsOn(Consumer<EquivalenceTestBuilder> otherTest) {
            checks.forDependency();
            DependencyBuilder dependencyBuilder = new DependencyBuilder(steps.keySet());
            otherTest.accept(dependencyBuilder);
            dependencySteps.putAll(dependencyBuilder.getAllDependencySteps());
        }

        @Override
        public void stepFor(MetamodelDescriptor metamodel, Consumer<TestView> action) {
            checks.forStep(metamodel);
            addStep(steps, new MainStep(metamodel, action));
        }

        @Override
        public VariantOptions inputVariantFor(MetamodelDescriptor metamodel, String name,
                Consumer<TestView> action) {
            checks.forVariant(metamodel, name);
            // variants are irrelevant for dependencies, so discard them
            return MOCK_VARIANT_OPTIONS;
        }

        @Override
        public Iterable<? extends DynamicNode> testsThatStepsAreEquivalent() {
            checks.forFinalizing();
            return List.of();
        }

        Map<MetamodelDescriptor, List<MetamodelStep>> getAllDependencySteps() {
            checks.forFinalizing();
            steps.forEach((metamodel, stepList) -> stepList.stream()
                    .map(this::modifyIfNecessary)
                    .forEach(step -> addStep(dependencySteps, step)));
            return dependencySteps;
        }
    }
}

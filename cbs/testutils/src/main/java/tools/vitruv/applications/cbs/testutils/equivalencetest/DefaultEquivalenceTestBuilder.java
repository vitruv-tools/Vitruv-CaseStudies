package tools.vitruv.applications.cbs.testutils.equivalencetest;

import java.util.*;
import java.util.function.Consumer;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.extension.ExtensionContext;
import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor;
import tools.vitruv.applications.cbs.testutils.ModelComparisonSettings;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.testutils.TestUserInteraction;
import tools.vitruv.change.testutils.views.TestView;
import tools.vitruv.change.testutils.views.UriMode;

public class DefaultEquivalenceTestBuilder extends DefaultBuilderCommon implements EquivalenceTestBuilder {
    private final ExtensionContext parentContext;
    private final Collection<ChangePropagationSpecification> changePropagationSpecifications;
    private final UriMode uriMode;
    private final ModelComparisonSettings modelComparisonSettings;

    DefaultEquivalenceTestBuilder(
            ExtensionContext parentContext,
            Collection<ChangePropagationSpecification> changePropagationSpecifications,
            UriMode uriMode,
            ModelComparisonSettings modelComparisonSettings) {
        super(Collections.emptySet());
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
        addStep(new MainStep(metamodel, action));
    }

    @Override
    public VariantOptions inputVariantFor(MetamodelDescriptor metamodel, String name, Consumer<TestView> action) {
        checks.forVariant(metamodel, name);
        return addStep(new VariantStep(metamodel, action, name));
    }

    @Override
    public Collection<DynamicTest> testsThatStepsAreEquivalent() {
        checks.forFinalizing();

        List<DynamicTest> result = new ArrayList<>();
        for (Map.Entry<MetamodelDescriptor, List<MetamodelStep>> entry : steps.entrySet()) {
            MetamodelDescriptor testMetamodel = entry.getKey();
            List<MetamodelStep> testSteps = entry.getValue();

            for (int testIndex = 0; testIndex < testSteps.size(); testIndex++) {
                MetamodelStep testStep = modifyIfNecessary(testSteps.get(testIndex));
                Map<MetamodelDescriptor, MetamodelStep> referenceSteps = testStep.determineReferenceSteps(steps);

                StringBuilder testName = new StringBuilder("FROM ")
                        .append(testMetamodel.getName())
                        .append(" TO {");
                Iterator<MetamodelDescriptor> it = referenceSteps.keySet().iterator();
                while (it.hasNext()) {
                    testName.append(it.next().getName());
                    if (it.hasNext())
                        testName.append(", ");
                }
                testName.append("}");

                if (testStep.getName() != null) {
                    testName.append(" - ").append(testStep.getName());
                }

                EquivalenceTestExtensionContext testContext = new EquivalenceTestExtensionContext(
                        testName.toString(), testIndex, parentContext, testStep.getTargetMetamodel());

                result.add(DynamicTest.dynamicTest(
                        testName.toString(),
                        new EquivalenceTestExecutable(testStep, dependencySteps, referenceSteps,
                                changePropagationSpecifications, uriMode, modelComparisonSettings, testContext)));
            }
        }
        return result;
    }

    private <T extends MetamodelStep> T addStep(T metamodelStep) {
        steps.computeIfAbsent(metamodelStep.getTargetMetamodel(), k -> new LinkedList<>())
                .add(metamodelStep);
        return metamodelStep;
    }

    private static class DependencyBuilder extends DefaultBuilderCommon implements EquivalenceTestBuilder {
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
            this.addStepInBuilder(new MainStep(metamodel, action));

        }

        private <T extends MetamodelStep> T addStepInBuilder(T metamodelStep) {
            steps.computeIfAbsent(metamodelStep.getTargetMetamodel(), k -> new LinkedList<>())
                    .add(metamodelStep);
            return metamodelStep;
        }

        @Override
        public VariantOptions inputVariantFor(MetamodelDescriptor metamodel, String name, Consumer<TestView> action) {
            checks.forVariant(metamodel, name);
            // variants are irrelevant for dependencies, so discard them
            return MOCK_VARIANT_OPTIONS;
        }

        @Override
        public Collection<DynamicTest> testsThatStepsAreEquivalent() {
            checks.forFinalizing();
            return Collections.emptyList();
        }

        Map<MetamodelDescriptor, List<MetamodelStep>> getAllDependencySteps() {
            checks.forFinalizing();
            steps.forEach((metamodel, stepsList) -> stepsList.stream()
                    .map(this::modifyIfNecessary)
                    .forEach(step -> dependencySteps.computeIfAbsent(step.getTargetMetamodel(),
                            k -> new LinkedList<>()).add(step)));
            return dependencySteps;
        }
    }
}
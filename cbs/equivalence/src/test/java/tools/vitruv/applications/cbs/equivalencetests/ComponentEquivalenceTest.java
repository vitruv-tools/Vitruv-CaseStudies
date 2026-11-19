package tools.vitruv.applications.cbs.equivalencetests;

import static tools.vitruv.applications.cbs.equivalencetests.TestModels.newImplementationCompilationUnit;
import static tools.vitruv.applications.cbs.equivalencetests.TestModels.newJavaPackage;
import static tools.vitruv.applications.cbs.testutils.JavaCreators.java;
import static tools.vitruv.applications.cbs.testutils.PcmCreators.createRepositoryElement;
import static tools.vitruv.applications.cbs.testutils.PcmCreators.pcm;
import static tools.vitruv.applications.cbs.testutils.PcmCreators.repository;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

import tools.vitruv.applications.cbs.testutils.equivalencetest.ParameterizedEquivalenceTestBuilder;
import tools.vitruv.applications.cbs.testutils.equivalencetest.ParametersBuilder.Parameter2;
import tools.vitruv.applications.cbs.testutils.junit.InheritableDisplayName;
import tools.vitruv.change.testutils.views.TestView;

@ReactionsEquivalenceTest
@InheritableDisplayName("components")
class ComponentEquivalenceTest {
    protected RepositoryEquivalenceTest getRepository() {
        return new RepositoryEquivalenceTest();
    }

    @TestFactory
    Iterable<? extends DynamicNode> creation(ParameterizedEquivalenceTestBuilder parameterBuilder) {
        // the second parameter is the user's choice of the component type when creating it from Java
        List<Parameter2<Class<? extends RepositoryComponent>, Integer>> componentTypes = List.of(
                parameterBuilder.parameters(BasicComponent.class, 0),
                parameterBuilder.parameters(CompositeComponent.class, 1));

        return parameterBuilder.<Class<? extends RepositoryComponent>, Integer>parameterizedBy(componentTypes,
                (componentType, componentTypeChoice) -> componentType.getSimpleName(),
                (builder, componentType, componentTypeChoice) -> {

                    builder.dependsOn(dependency -> getRepository().creation(dependency));

                    builder.stepFor(pcm.getMetamodel(), view -> view.propagate(
                            view.from(Repository.class, repository("model/Test")),
                            repositoryModel -> repositoryModel.getComponents__Repository()
                                    .add(newComponent(componentType, "TestComponent"))));

                    builder.inputVariantFor(pcm.getMetamodel(), "lowercase name", view -> view.propagate(
                            view.from(Repository.class, repository("model/Test")),
                            repositoryModel -> repositoryModel.getComponents__Repository()
                                    .add(newComponent(componentType, "testComponent"))));

                    builder.stepFor(java.getMetamodel(), view -> {
                        view.getUserInteraction()
                                .addNextSingleSelection(componentTypeChoice /* create appropriate Component */);

                        view.propagate(view.resourceAt(java("src/test/testComponent/package-info")),
                                resource -> resource.getContents().add(newJavaPackage("testComponent", "test")));

                        Resource implementation = view.resourceAt(java("src/test/testComponent/TestComponentImpl"));
                        /*
                         * In the test view, the implementation class may already be present due to transitive change
                         * propagation, so do not create it again. For the reference view created without change
                         * propagation, it has to be created.
                         */
                        if (implementation.getContents().isEmpty()) {
                            view.propagate(implementation, resource -> resource.getContents()
                                    .add(newImplementationCompilationUnit("TestComponentImpl", "test",
                                            "testComponent")));
                        }
                    });

                    builder.inputVariantFor(java.getMetamodel(), "creating only a package", view -> {
                        view.getUserInteraction()
                                .addNextSingleSelection(componentTypeChoice /* create appropriate Component */);
                        view.propagate(view.resourceAt(java("src/test/testComponent/package-info")),
                                resource -> resource.getContents().add(newJavaPackage("testComponent", "test")));
                    }).alsoCompareToMainStepOfSameMetamodel();
                });
    }

    @TestFactory
    Iterable<? extends DynamicNode> renaming(ParameterizedEquivalenceTestBuilder parameterBuilder) {
        return parameterBuilder.dependsOn(this::creation, builder -> {

            builder.stepFor(pcm.getMetamodel(), view -> view.propagate(
                    view.from(Repository.class, repository("model/Test")).getComponents__Repository().get(0),
                    component -> component.setEntityName("RenamedComponent")));

            builder.inputVariantFor(pcm.getMetamodel(), "lowercase name", view -> view.propagate(
                    view.from(Repository.class, repository("model/Test")).getComponents__Repository().get(0),
                    component -> component.setEntityName("renamedComponent")));

            builder.stepFor(java.getMetamodel(), view -> {
                view.getUserInteraction().addNextSingleSelection(3 /* do nothing */);
                view.propagate(view.resourceAt(java("src/test/renamedComponent/package-info")),
                        resource -> resource.getContents().add(newJavaPackage("renamedComponent", "test")));

                view.propagate(view.resourceAt(java("src/test/testComponent/TestComponentImpl")),
                        resource -> moveAndRenameImplementation(view, resource));

                /*
                 * In the test view, the package may already be removed due to transitive change propagation, so
                 * trying to remove it again would result in an empty change (which is invalid to propagate). For the
                 * reference view created without change propagation, the package has to be removed manually.
                 */
                Resource originalPackage = view.resourceAt(java("src/test/testComponent/package-info"));
                if (!originalPackage.getContents().isEmpty()) {
                    view.propagate(originalPackage, resource -> resource.getContents().clear());
                }
            });
        });
    }

    private static RepositoryComponent newComponent(Class<? extends RepositoryComponent> componentType,
            String name) {
        RepositoryComponent component = createRepositoryElement(componentType);
        component.setEntityName(name);
        return component;
    }

    private static void moveAndRenameImplementation(TestView view, Resource implementation) {
        /*
         * Inserting the compilation unit into the containing package is necessary to be consistent with the
         * underlying VirtualModel, in which the reference is set. The JaMoPP metamodel does not ensure that this
         * reference is consistently set. It depends on the order in which packages and compilation units are created
         * and/or loaded.
         */
        Package originalPackage = view.from(Package.class,
                view.resourceAt(java("src/test/testComponent/package-info")));
        originalPackage.getCompilationUnits().add(view.from(CompilationUnit.class, implementation));
        view.moveTo(implementation, java("src/test/renamedComponent/RenamedComponentImpl"));
        CompilationUnit compilationUnit = view.from(CompilationUnit.class, implementation);
        compilationUnit.getNamespaces().set(1, "renamedComponent");
        compilationUnit.setName("RenamedComponentImpl");
        var implementationClass = compilationUnit.getClassifiers().get(0);
        implementationClass.setName("RenamedComponentImpl");
        implementationClass.getMembers().get(0).setName("RenamedComponentImpl");
    }
}

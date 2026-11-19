package tools.vitruv.applications.cbs.equivalencetests;

import static tools.vitruv.applications.cbs.equivalencetests.TestModels.newJavaPackage;
import static tools.vitruv.applications.cbs.equivalencetests.TestModels.newRepository;
import static tools.vitruv.applications.cbs.equivalencetests.TestModels.newUmlModel;
import static tools.vitruv.applications.cbs.equivalencetests.TestModels.newUmlPackage;
import static tools.vitruv.applications.cbs.testutils.JavaCreators.java;
import static tools.vitruv.applications.cbs.testutils.PcmCreators.pcm;
import static tools.vitruv.applications.cbs.testutils.PcmCreators.repository;
import static tools.vitruv.applications.cbs.testutils.UmlCreators.uml;

import java.nio.file.Path;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Model;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTestBuilder;
import tools.vitruv.applications.cbs.testutils.junit.InheritableDisplayName;
import tools.vitruv.change.testutils.views.TestView;

@ReactionsEquivalenceTest
@InheritableDisplayName("repositories")
class RepositoryEquivalenceTest {
    @TestFactory
    Iterable<? extends DynamicNode> creation(EquivalenceTestBuilder builder) {
        builder.userInteractions(interaction -> {
            // TODO unify the interactions
            interaction.onTextInput(it -> it.getMessage().contains("path for the UML root model"))
                    .respondWith("model/");
            interaction.onTextInput(it -> it.getMessage().contains("name for the UML root model"))
                    .respondWith("model");
            interaction.onTextInput(it -> it.getMessage().contains("where to save the corresponding Uml-Model"))
                    .respondWith("model/model");
            interaction.onTextInput(it -> it.getMessage().contains("where to save the corresponding PCM model"))
                    .respondWith("model/Test");
            interaction.onMultipleChoiceSingleSelection(
                    it -> it.getMessage().contains("a pcm::Repository or a pcm::System"))
                    .respondWithChoiceMatching(choice -> choice.contains("Repository"));
        });

        builder.stepFor(pcm.getMetamodel(), view -> view.propagate(view.resourceAt(repository("model/Test")),
                resource -> resource.getContents().add(newRepository("Test"))));

        builder.inputVariantFor(pcm.getMetamodel(), "lowercase repository name",
                view -> view.propagate(view.resourceAt(repository("model/test")),
                        resource -> resource.getContents().add(newRepository("test"))));

        builder.stepFor(java.getMetamodel(), view -> {
            view.propagate(view.resourceAt(java("src/test/package-info")),
                    resource -> resource.getContents().add(newJavaPackage("test")));
            createJavaPackageIfAbsent(view, java("src/test/contracts/package-info"), "contracts", "test");
            createJavaPackageIfAbsent(view, java("src/test/datatypes/package-info"), "datatypes", "test");
        });

        builder.inputVariantFor(java.getMetamodel(), "uppercase package name", view -> {
            view.propagate(view.resourceAt(java("src/Test/package-info")),
                    resource -> resource.getContents().add(newJavaPackage("Test")));
            createJavaPackageIfAbsent(view, java("src/Test/contracts/package-info"), "contracts", "Test");
            createJavaPackageIfAbsent(view, java("src/Test/datatypes/package-info"), "datatypes", "Test");
        });

        builder.inputVariantFor(java.getMetamodel(), "creating only Java root package",
                view -> view.propagate(view.resourceAt(java("src/test/package-info")),
                        resource -> resource.getContents().add(newJavaPackage("test"))))
                .alsoCompareToMainStepOfSameMetamodel();

        builder.inputVariantFor(java.getMetamodel(), "creating only Java root package - uppercase package name",
                view -> view.propagate(view.resourceAt(java("src/Test/package-info")),
                        resource -> resource.getContents().add(newJavaPackage("Test"))));

        builder.stepFor(uml.getMetamodel(), view -> view.propagate(view.resourceAt(uml("model/model")),
                resource -> resource.getContents().add(newUmlModel("model",
                        newUmlPackage("test", newUmlPackage("contracts"), newUmlPackage("datatypes"))))));

        builder.inputVariantFor(uml.getMetamodel(), "uppercase package name",
                view -> view.propagate(view.resourceAt(uml("model/model")),
                        resource -> resource.getContents().add(newUmlModel("model",
                                newUmlPackage("Test", newUmlPackage("contracts"), newUmlPackage("datatypes"))))));

        builder.inputVariantFor(uml.getMetamodel(), "creating only UML root package",
                view -> view.propagate(view.resourceAt(uml("model/model")),
                        resource -> resource.getContents().add(newUmlModel("model", newUmlPackage("test")))))
                .alsoCompareToMainStepOfSameMetamodel();

        builder.inputVariantFor(uml.getMetamodel(), "creating only UML root package - uppercase package name",
                view -> view.propagate(view.resourceAt(uml("model/model")),
                        resource -> resource.getContents().add(newUmlModel("model", newUmlPackage("Test")))));

        return builder.testsThatStepsAreEquivalent();
    }

    @TestFactory
    @DisplayName("renaming")
    Iterable<? extends DynamicNode> renaming(EquivalenceTestBuilder builder) {
        builder.dependsOn(this::creation);

        builder.stepFor(pcm.getMetamodel(), view -> view.propagate(
                view.from(Repository.class, repository("model/Test")),
                repositoryModel -> repositoryModel.setEntityName("Renamed")));

        builder.inputVariantFor(pcm.getMetamodel(), "lowercase repository name", view -> view.propagate(
                view.from(Repository.class, repository("model/Test")),
                repositoryModel -> repositoryModel.setEntityName("renamed")));

        builder.inputVariantFor(pcm.getMetamodel(), "also rename file",
                view -> view.propagate(view.resourceAt(repository("model/Test")), resource -> {
                    view.moveTo(resource, repository("model/Renamed"));
                    view.from(Repository.class, resource).setEntityName("Renamed");
                }));

        builder.stepFor(java.getMetamodel(), view -> {
            moveAndRenameJavaRootPackage(view, "src/test/package-info", "src/renamed/package-info", "renamed");
            moveJavaSubPackage(view, "src/test/contracts/package-info", "src/renamed/contracts/package-info",
                    "renamed");
            moveJavaSubPackage(view, "src/test/datatypes/package-info", "src/renamed/datatypes/package-info",
                    "renamed");
        });

        builder.inputVariantFor(java.getMetamodel(), "uppercase package name", view -> {
            moveAndRenameJavaRootPackage(view, "src/test/package-info", "src/Renamed/package-info", "Renamed");
            moveJavaSubPackage(view, "src/test/contracts/package-info", "src/Renamed/contracts/package-info",
                    "Renamed");
            moveJavaSubPackage(view, "src/test/datatypes/package-info", "src/Renamed/datatypes/package-info",
                    "Renamed");
        });

        builder.inputVariantFor(java.getMetamodel(), "renaming only the root package",
                view -> moveAndRenameJavaRootPackage(view, "src/test/package-info", "src/renamed/package-info",
                        "renamed"));

        builder.inputVariantFor(java.getMetamodel(), "renaming only the root package - uppercase package name",
                view -> moveAndRenameJavaRootPackage(view, "src/test/package-info", "src/Renamed/package-info",
                        "Renamed"));

        builder.stepFor(uml.getMetamodel(), view -> view.propagate(view.from(Model.class, uml("model/model")),
                model -> model.getPackagedElements().get(0).setName("renamed")));

        builder.inputVariantFor(uml.getMetamodel(), "uppercase package name",
                view -> view.propagate(view.from(Model.class, uml("model/model")),
                        model -> model.getPackagedElements().get(0).setName("Renamed")));

        return builder.testsThatStepsAreEquivalent();
    }

    @TestFactory
    Iterable<? extends DynamicNode> deletion(EquivalenceTestBuilder builder) {
        builder.userInteractions(interaction -> interaction
                .onConfirmation(it -> it.getMessage().contains("delete the UML model")).respondWith(true));

        builder.dependsOn(this::creation);

        builder.stepFor(pcm.getMetamodel(), view -> view.propagate(view.resourceAt(repository("model/Test")),
                resource -> resource.getContents().clear()));

        builder.inputVariantFor(pcm.getMetamodel(), "deleting complete PCM resource",
                view -> view.propagate(view.resourceAt(repository("model/Test")),
                        TestModels::deleteResource));

        /*
         * TODO: JW - Tests from Java are currently failing as reloading the packages loads layout information data
         * which is absent when using the resources from memory. As such, propagateChanges tries to apply delete
         * operations for the layout information which cannot be found in the underlying resources.
         *
         * builder.stepFor(java.getMetamodel(), view -> {
         *     view.propagate(view.resourceAt(java("src/test/package-info")), TestModels::deleteResource);
         *     deleteResource(view.resourceAt(java("src/test/contracts/package-info")));
         *     deleteResource(view.resourceAt(java("src/test/datatypes/package-info")));
         * });
         *
         * builder.inputVariantFor(java.getMetamodel(), "deleting only the root package",
         *         view -> view.propagate(view.resourceAt(java("src/test/package-info")),
         *                 TestModels::deleteResource))
         *         .alsoCompareToMainStepOfSameMetamodel();
         */

        builder.stepFor(uml.getMetamodel(), view -> view.propagate(view.from(Model.class, uml("model/model")),
                model -> model.getPackagedElements().remove(0)));

        builder.inputVariantFor(uml.getMetamodel(), "deleting complete UML model",
                view -> view.propagate(view.resourceAt(uml("model/model")),
                        resource -> resource.getContents().clear()));

        builder.inputVariantFor(uml.getMetamodel(), "deleting complete UML resource",
                view -> view.propagate(view.resourceAt(uml("model/model")),
                        TestModels::deleteResource));

        return builder.testsThatStepsAreEquivalent();
    }

    /**
     * Creates the Java package only if its resource is still empty. In the test view, it may already be present due
     * to transitive change propagation. For the reference view created without change propagation, it has to be
     * created.
     */
    private static void createJavaPackageIfAbsent(TestView view, Path packageInfo, String name,
            String parentNamespace) {
        Resource resource = view.resourceAt(packageInfo);
        if (resource.getContents().isEmpty()) {
            view.propagate(resource, it -> it.getContents().add(newJavaPackage(name, parentNamespace)));
        }
    }

    private static void moveAndRenameJavaRootPackage(TestView view, String oldPackageInfo, String newPackageInfo,
            String newName) {
        view.propagate(view.resourceAt(java(oldPackageInfo)), resource -> {
            view.moveTo(resource, java(newPackageInfo));
            view.from(Package.class, resource).setName(newName);
        });
    }

    private static void moveJavaSubPackage(TestView view, String oldPackageInfo, String newPackageInfo,
            String newRootNamespace) {
        view.propagate(view.resourceAt(java(oldPackageInfo)), resource -> {
            view.moveTo(resource, java(newPackageInfo));
            view.from(Package.class, resource).getNamespaces().set(0, newRootNamespace);
        });
    }
}

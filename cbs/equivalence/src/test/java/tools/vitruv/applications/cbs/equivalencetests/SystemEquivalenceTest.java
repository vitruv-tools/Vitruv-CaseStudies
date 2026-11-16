package tools.vitruv.applications.cbs.equivalencetests;

import static tools.vitruv.applications.cbs.equivalencetests.TestModels.newImplementationCompilationUnit;
import static tools.vitruv.applications.cbs.equivalencetests.TestModels.newJavaPackage;
import static tools.vitruv.applications.cbs.equivalencetests.TestModels.newPcmSystem;
import static tools.vitruv.applications.cbs.testutils.JavaCreators.java;
import static tools.vitruv.applications.cbs.testutils.PcmCreators.pcm;
import static tools.vitruv.applications.cbs.testutils.PcmCreators.system;

import org.eclipse.emf.ecore.resource.Resource;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;

import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTestBuilder;
import tools.vitruv.applications.cbs.testutils.junit.InheritableDisplayName;

@ReactionsEquivalenceTest
@InheritableDisplayName("systems")
class SystemEquivalenceTest {
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
                    .respondWithChoiceMatching(choice -> choice.contains("System"));
        });

        builder.stepFor(pcm.getMetamodel(), view -> view.propagate(view.resourceAt(system("model/Test")),
                resource -> resource.getContents().add(newPcmSystem("Test"))));

        builder.inputVariantFor(pcm.getMetamodel(), "lowercase name",
                view -> view.propagate(view.resourceAt(system("model/test")),
                        resource -> resource.getContents().add(newPcmSystem("test"))));

        builder.stepFor(java.getMetamodel(), view -> {
            view.propagate(view.resourceAt(java("src/test/package-info")),
                    resource -> resource.getContents().add(newJavaPackage("test")));

            Resource implementation = view.resourceAt(java("src/test/TestImpl"));
            /*
             * In the test view, the implementation class may already be present due to transitive change
             * propagation, so do not create it again. For the reference view created without change propagation, it
             * has to be created.
             */
            if (implementation.getContents().isEmpty()) {
                view.propagate(implementation,
                        resource -> resource.getContents().add(newImplementationCompilationUnit("TestImpl", "test")));
            }
        });

        builder.inputVariantFor(java.getMetamodel(), "creating only a package",
                view -> view.propagate(view.resourceAt(java("src/test/package-info")),
                        resource -> resource.getContents().add(newJavaPackage("test"))))
                .alsoCompareToMainStepOfSameMetamodel();

        return builder.testsThatStepsAreEquivalent();
    }

    @TestFactory
    Iterable<? extends DynamicNode> renaming(EquivalenceTestBuilder builder) {
        builder.dependsOn(this::creation);

        builder.stepFor(pcm.getMetamodel(), view -> view.propagate(
                view.from(org.palladiosimulator.pcm.system.System.class, system("model/Test")),
                pcmSystem -> pcmSystem.setEntityName("Renamed")));

        builder.inputVariantFor(pcm.getMetamodel(), "lowercase name", view -> view.propagate(
                view.from(org.palladiosimulator.pcm.system.System.class, system("model/Test")),
                pcmSystem -> pcmSystem.setEntityName("renamed")));

        builder.stepFor(java.getMetamodel(), view -> {
            view.getUserInteraction().addNextSingleSelection(2 /* do nothing */);
            view.propagate(view.resourceAt(java("src/renamed/package-info")),
                    resource -> resource.getContents().add(newJavaPackage("renamed")));

            view.propagate(view.resourceAt(java("src/test/TestImpl")), resource -> {
                /*
                 * Inserting the compilation unit into the containing package is necessary to be consistent with the
                 * underlying VirtualModel, in which the reference is set. The JaMoPP metamodel does not ensure that
                 * this reference is consistently set. It depends on the order in which packages and compilation units
                 * are created and/or loaded.
                 */
                Package originalPackage = view.from(Package.class, view.resourceAt(java("src/test/package-info")));
                originalPackage.getCompilationUnits().add(view.from(CompilationUnit.class, resource));
                view.moveTo(resource, java("src/renamed/RenamedImpl"));
                CompilationUnit compilationUnit = view.from(CompilationUnit.class, resource);
                compilationUnit.getNamespaces().set(0, "renamed");
                var implementationClass = compilationUnit.getClassifiers().get(0);
                implementationClass.setName("RenamedImpl");
                implementationClass.getMembers().get(0).setName("RenamedImpl");
            });

            /*
             * In the test view, the package may already be removed due to transitive change propagation, so trying to
             * remove it again would result in an empty change (which is invalid to propagate). For the reference view
             * created without change propagation, the package has to be removed manually.
             */
            Resource originalPackage = view.resourceAt(java("src/test/package-info"));
            if (!originalPackage.getContents().isEmpty()) {
                view.propagate(originalPackage, resource -> resource.getContents().clear());
            }
        });

        return builder.testsThatStepsAreEquivalent();
    }
}

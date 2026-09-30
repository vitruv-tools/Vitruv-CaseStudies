package tools.vitruv.applications.cbs.equivalencetests;

import static tools.vitruv.applications.cbs.testutils.JavaCreators.java;
import static tools.vitruv.applications.cbs.testutils.PcmCreators.pcm;
import static tools.vitruv.applications.cbs.testutils.PcmCreators.system;

import java.util.List;
import org.eclipse.emf.ecore.resource.Resource;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.palladiosimulator.pcm.system.System;
import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTestBuilder;
import tools.vitruv.applications.cbs.testutils.junit.InheritableDisplayName;

@ReactionsEquivalenceTest
@InheritableDisplayName("systems")
public class SystemEquivalenceTest {
	@TestFactory
	public Iterable<? extends DynamicNode> creation(EquivalenceTestBuilder builder) {
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

		builder.stepFor(pcm.getMetamodel(), view -> {
			view.propagate(view.resourceAt(system("model/Test")), resource -> {
				resource.getContents().add(newPcmSystem("Test"));
			});
		});

		builder.inputVariantFor(pcm.getMetamodel(), "lowercase name", view -> {
			view.propagate(view.resourceAt(system("model/test")), resource -> {
				resource.getContents().add(newPcmSystem("test"));
			});
		});

		builder.stepFor(java.getMetamodel(), view -> {
			view.propagate(view.resourceAt(java("src/test/package-info")), resource -> {
				resource.getContents().add(newJavaPackage("test"));
			});

			Resource implementationResource = view.resourceAt(java("src/test/TestImpl"));
			/*
			 * In the test view, the implementation class may already be present due to transitive change
			 * propagation, so do not create it again. For the reference view created without change propagation,
			 * it has to be created.
			 */
			if (implementationResource.getContents().isEmpty()) {
				view.propagate(implementationResource, resource -> {
					resource.getContents().add(newJavaCompilationUnit("TestImpl", "test"));
				});
			}
		});

		builder.inputVariantFor(java.getMetamodel(), "creating only a package", view -> {
			view.propagate(view.resourceAt(java("src/test/package-info")), resource -> {
				resource.getContents().add(newJavaPackage("test"));
			});
		}).alsoCompareToMainStepOfSameMetamodel();

		return builder.testsThatStepsAreEquivalent();
	}

	@TestFactory
	public Iterable<? extends DynamicNode> renaming(EquivalenceTestBuilder builder) {
		builder.dependsOn(this::creation);

		builder.stepFor(pcm.getMetamodel(), view -> {
			view.propagate(view.from(System.class, system("model/Test")),
					pcmSystem -> pcmSystem.setEntityName("Renamed"));
		});

		builder.inputVariantFor(pcm.getMetamodel(), "lowercase name", view -> {
			view.propagate(view.from(System.class, system("model/Test")),
					pcmSystem -> pcmSystem.setEntityName("renamed"));
		});

		builder.stepFor(java.getMetamodel(), view -> {
			// do nothing
			view.getUserInteraction().addNextSingleSelection(2);
			view.propagate(view.resourceAt(java("src/renamed/package-info")), resource -> {
				resource.getContents().add(newJavaPackage("renamed"));
			});

			view.propagate(view.resourceAt(java("src/test/TestImpl")), resource -> {
				/*
				 * Inserting the compilation unit into the containing package is necessary to be consistent with the
				 * underlying VirtualModel, in which the reference is set. The JaMoPP metamodel does not ensure that
				 * this reference is consistently set. It depends on the order in which packages and compilation
				 * units are created and/or loaded.
				 */
				Package originalPackage = view.from(Package.class, view.resourceAt(java("src/test/package-info")));
				originalPackage.getCompilationUnits().add(view.from(CompilationUnit.class, resource));
				view.moveTo(resource, java("src/renamed/RenamedImpl"));
				CompilationUnit compilationUnit = view.from(CompilationUnit.class, resource);
				compilationUnit.getNamespaces().set(0, "renamed");
				var classifier = compilationUnit.getClassifiers().get(0);
				classifier.setName("RenamedImpl");
				classifier.getMembers().get(0).setName("RenamedImpl");
			});

			/*
			 * In the test view, the package may already be removed due to transitive change propagation, so trying
			 * to remove it again would result in an empty change (which is invalid to propagate). For the reference
			 * view created without change propagation, the package has to be removed manually.
			 */
			Resource originalPackage = view.resourceAt(java("src/test/package-info"));
			if (!originalPackage.getContents().isEmpty()) {
				view.propagate(originalPackage, resource -> resource.getContents().clear());
			}
		});

		return builder.testsThatStepsAreEquivalent();
	}

	private static System newPcmSystem(String entityName) {
		System pcmSystem = pcm.system.create(System.class);
		pcmSystem.setEntityName(entityName);
		return pcmSystem;
	}

	private static Package newJavaPackage(String name) {
		Package javaPackage = java.containers.create(Package.class);
		javaPackage.setName(name);
		return javaPackage;
	}

	private static CompilationUnit newJavaCompilationUnit(String className, String... namespaces) {
		CompilationUnit compilationUnit = java.containers.create(CompilationUnit.class);
		compilationUnit.getNamespaces().addAll(List.of(namespaces));
		Class javaClass = java.classifiers.create(Class.class);
		javaClass.setName(className);
		javaClass.makePublic();
		javaClass.addModifier(ModifiersFactory.eINSTANCE.createFinal());
		Constructor constructor = java.members.create(Constructor.class);
		constructor.setName(className);
		constructor.makePublic();
		javaClass.getMembers().add(constructor);
		compilationUnit.getClassifiers().add(javaClass);
		return compilationUnit;
	}
}

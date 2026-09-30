package tools.vitruv.applications.cbs.equivalencetests;

import static tools.vitruv.applications.cbs.testutils.JavaCreators.java;
import static tools.vitruv.applications.cbs.testutils.PcmCreators.pcm;
import static tools.vitruv.applications.cbs.testutils.PcmCreators.repository;
import static tools.vitruv.applications.cbs.testutils.UmlCreators.uml;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Model;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTestBuilder;
import tools.vitruv.applications.cbs.testutils.junit.InheritableDisplayName;

@ReactionsEquivalenceTest
@InheritableDisplayName("repositories")
public class RepositoryEquivalenceTest {
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
					.respondWithChoiceMatching(choice -> choice.contains("Repository"));
		});

		builder.stepFor(pcm.getMetamodel(), view -> {
			view.propagate(view.resourceAt(repository("model/Test")), resource -> {
				resource.getContents().add(newPcmRepository("Test"));
			});
		});

		builder.inputVariantFor(pcm.getMetamodel(), "lowercase repository name", view -> {
			view.propagate(view.resourceAt(repository("model/test")), resource -> {
				resource.getContents().add(newPcmRepository("test"));
			});
		});

		builder.stepFor(java.getMetamodel(), view -> {
			view.propagate(view.resourceAt(java("src/test/package-info")), resource -> {
				resource.getContents().add(newJavaPackage("test"));
			});

			Resource contractsResource = view.resourceAt(java("src/test/contracts/package-info"));
			if (contractsResource.getContents().isEmpty()) {
				view.propagate(contractsResource, resource -> {
					resource.getContents().add(newJavaPackage("contracts", "test"));
				});
			}

			Resource datatypesResource = view.resourceAt(java("src/test/datatypes/package-info"));
			if (datatypesResource.getContents().isEmpty()) {
				view.propagate(datatypesResource, resource -> {
					resource.getContents().add(newJavaPackage("datatypes", "test"));
				});
			}
		});

		builder.inputVariantFor(java.getMetamodel(), "uppercase package name", view -> {
			view.propagate(view.resourceAt(java("src/Test/package-info")), resource -> {
				resource.getContents().add(newJavaPackage("Test"));
			});

			Resource contractsResource = view.resourceAt(java("src/Test/contracts/package-info"));
			if (contractsResource.getContents().isEmpty()) {
				view.propagate(contractsResource, resource -> {
					resource.getContents().add(newJavaPackage("contracts", "Test"));
				});
			}

			Resource datatypesResource = view.resourceAt(java("src/Test/datatypes/package-info"));
			if (datatypesResource.getContents().isEmpty()) {
				view.propagate(datatypesResource, resource -> {
					resource.getContents().add(newJavaPackage("datatypes", "Test"));
				});
			}
		});

		builder.inputVariantFor(java.getMetamodel(), "creating only Java root package", view -> {
			view.propagate(view.resourceAt(java("src/test/package-info")), resource -> {
				resource.getContents().add(newJavaPackage("test"));
			});
		}).alsoCompareToMainStepOfSameMetamodel();

		builder.inputVariantFor(java.getMetamodel(), "creating only Java root package - uppercase package name",
				view -> {
					view.propagate(view.resourceAt(java("src/Test/package-info")), resource -> {
						resource.getContents().add(newJavaPackage("Test"));
					});
				});

		builder.stepFor(uml.getMetamodel(), view -> {
			view.propagate(view.resourceAt(uml("model/model")), resource -> {
				resource.getContents().add(newUmlModel("model",
						newUmlPackage("test", newUmlPackage("contracts"), newUmlPackage("datatypes"))));
			});
		});

		builder.inputVariantFor(uml.getMetamodel(), "uppercase package name", view -> {
			view.propagate(view.resourceAt(uml("model/model")), resource -> {
				resource.getContents().add(newUmlModel("model",
						newUmlPackage("Test", newUmlPackage("contracts"), newUmlPackage("datatypes"))));
			});
		});

		builder.inputVariantFor(uml.getMetamodel(), "creating only UML root package", view -> {
			view.propagate(view.resourceAt(uml("model/model")), resource -> {
				resource.getContents().add(newUmlModel("model", newUmlPackage("test")));
			});
		}).alsoCompareToMainStepOfSameMetamodel();

		builder.inputVariantFor(uml.getMetamodel(), "creating only UML root package - uppercase package name",
				view -> {
					view.propagate(view.resourceAt(uml("model/model")), resource -> {
						resource.getContents().add(newUmlModel("model", newUmlPackage("Test")));
					});
				});

		return builder.testsThatStepsAreEquivalent();
	}

	@TestFactory
	@DisplayName("renaming")
	public Iterable<? extends DynamicNode> renaming(EquivalenceTestBuilder builder) {
		builder.dependsOn(this::creation);

		builder.stepFor(pcm.getMetamodel(), view -> {
			view.propagate(view.from(Repository.class, repository("model/Test")), pcmRepository -> {
				pcmRepository.setEntityName("Renamed");
			});
		});

		builder.inputVariantFor(pcm.getMetamodel(), "lowercase repository name", view -> {
			view.propagate(view.from(Repository.class, repository("model/Test")), pcmRepository -> {
				pcmRepository.setEntityName("renamed");
			});
		});

		builder.inputVariantFor(pcm.getMetamodel(), "also rename file", view -> {
			view.propagate(view.resourceAt(repository("model/Test")), resource -> {
				view.moveTo(resource, repository("model/Renamed"));
				view.from(Repository.class, resource).setEntityName("Renamed");
			});
		});

		builder.stepFor(java.getMetamodel(), view -> {
			view.propagate(view.resourceAt(java("src/test/package-info")), resource -> {
				view.moveTo(resource, java("src/renamed/package-info"));
				view.from(Package.class, resource).setName("renamed");
			});

			view.propagate(view.resourceAt(java("src/test/contracts/package-info")), resource -> {
				view.moveTo(resource, java("src/renamed/contracts/package-info"));
				view.from(Package.class, resource).getNamespaces().set(0, "renamed");
			});

			view.propagate(view.resourceAt(java("src/test/datatypes/package-info")), resource -> {
				view.moveTo(resource, java("src/renamed/datatypes/package-info"));
				view.from(Package.class, resource).getNamespaces().set(0, "renamed");
			});
		});

		builder.inputVariantFor(java.getMetamodel(), "uppercase package name", view -> {
			view.propagate(view.resourceAt(java("src/test/package-info")), resource -> {
				view.moveTo(resource, java("src/Renamed/package-info"));
				view.from(Package.class, resource).setName("Renamed");
			});

			view.propagate(view.resourceAt(java("src/test/contracts/package-info")), resource -> {
				view.moveTo(resource, java("src/Renamed/contracts/package-info"));
				view.from(Package.class, resource).getNamespaces().set(0, "Renamed");
			});

			view.propagate(view.resourceAt(java("src/test/datatypes/package-info")), resource -> {
				view.moveTo(resource, java("src/Renamed/datatypes/package-info"));
				view.from(Package.class, resource).getNamespaces().set(0, "Renamed");
			});
		});

		builder.inputVariantFor(java.getMetamodel(), "renaming only the root package", view -> {
			view.propagate(view.resourceAt(java("src/test/package-info")), resource -> {
				view.moveTo(resource, java("src/renamed/package-info"));
				view.from(Package.class, resource).setName("renamed");
			});
		});

		builder.inputVariantFor(java.getMetamodel(), "renaming only the root package - uppercase package name",
				view -> {
					view.propagate(view.resourceAt(java("src/test/package-info")), resource -> {
						view.moveTo(resource, java("src/Renamed/package-info"));
						view.from(Package.class, resource).setName("Renamed");
					});
				});

		builder.stepFor(uml.getMetamodel(), view -> {
			view.propagate(view.from(Model.class, uml("model/model")), model -> {
				model.getPackagedElements().get(0).setName("renamed");
			});
		});

		builder.inputVariantFor(uml.getMetamodel(), "uppercase package name", view -> {
			view.propagate(view.from(Model.class, uml("model/model")), model -> {
				model.getPackagedElements().get(0).setName("Renamed");
			});
		});

		return builder.testsThatStepsAreEquivalent();
	}

	@TestFactory
	public Iterable<? extends DynamicNode> deletion(EquivalenceTestBuilder builder) {
		builder.userInteractions(interaction -> {
			interaction.onConfirmation(it -> it.getMessage().contains("delete the UML model")).respondWith(true);
		});

		builder.dependsOn(this::creation);

		builder.stepFor(pcm.getMetamodel(), view -> {
			view.propagate(view.resourceAt(repository("model/Test")), resource -> resource.getContents().clear());
		});

		builder.inputVariantFor(pcm.getMetamodel(), "deleting complete PCM resource", view -> {
			view.propagate(view.resourceAt(repository("model/Test")), RepositoryEquivalenceTest::deleteResource);
		});

		/*
		 * TODO: JW - Tests from Java are currently failing as reloading the packages loads layout information data
		 * which is absent when using the resources from memory. As such, propagateChanges tries to apply delete
		 * operations for the layout information which cannot be found in the underlying resources.
		 *
		 * builder.stepFor(java.getMetamodel(), view -> {
		 *     view.propagate(view.resourceAt(java("src/test/package-info")), RepositoryEquivalenceTest::deleteResource);
		 *     deleteResource(view.resourceAt(java("src/test/contracts/package-info")));
		 *     deleteResource(view.resourceAt(java("src/test/datatypes/package-info")));
		 * });
		 *
		 * builder.inputVariantFor(java.getMetamodel(), "deleting only the root package", view -> {
		 *     view.propagate(view.resourceAt(java("src/test/package-info")), RepositoryEquivalenceTest::deleteResource);
		 * }).alsoCompareToMainStepOfSameMetamodel();
		 */

		builder.stepFor(uml.getMetamodel(), view -> {
			view.propagate(view.from(Model.class, uml("model/model")), model -> {
				model.getPackagedElements().remove(0);
			});
		});

		builder.inputVariantFor(uml.getMetamodel(), "deleting complete UML model", view -> {
			view.propagate(view.resourceAt(uml("model/model")), resource -> resource.getContents().clear());
		});

		builder.inputVariantFor(uml.getMetamodel(), "deleting complete UML resource", view -> {
			view.propagate(view.resourceAt(uml("model/model")), RepositoryEquivalenceTest::deleteResource);
		});

		return builder.testsThatStepsAreEquivalent();
	}

	private static Repository newPcmRepository(String entityName) {
		Repository pcmRepository = pcm.repository.create(Repository.class);
		pcmRepository.setEntityName(entityName);
		return pcmRepository;
	}

	private static Package newJavaPackage(String name, String... namespaces) {
		Package javaPackage = java.containers.create(Package.class);
		javaPackage.setName(name);
		javaPackage.getNamespaces().addAll(List.of(namespaces));
		return javaPackage;
	}

	private static Model newUmlModel(String name, org.eclipse.uml2.uml.Package... packagedElements) {
		Model model = uml.create(Model.class);
		model.setName(name);
		model.getPackagedElements().addAll(List.of(packagedElements));
		return model;
	}

	private static org.eclipse.uml2.uml.Package newUmlPackage(String name,
			org.eclipse.uml2.uml.Package... packagedElements) {
		org.eclipse.uml2.uml.Package umlPackage = uml.create(org.eclipse.uml2.uml.Package.class);
		umlPackage.setName(name);
		umlPackage.getPackagedElements().addAll(List.of(packagedElements));
		return umlPackage;
	}

	private static void deleteResource(Resource resource) {
		try {
			resource.delete(Map.of());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}

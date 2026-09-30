package tools.vitruv.applications.cbs.equivalencetests;

import static tools.vitruv.applications.cbs.testutils.JavaCreators.java;
import static tools.vitruv.applications.cbs.testutils.PcmCreators.pcm;
import static tools.vitruv.applications.cbs.testutils.PcmCreators.repository;

import java.util.List;
import org.eclipse.emf.ecore.resource.Resource;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.ImplementationComponentType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.cbs.testutils.equivalencetest.ParameterizedEquivalenceTestBuilder;
import tools.vitruv.applications.cbs.testutils.junit.InheritableDisplayName;

@ReactionsEquivalenceTest
@InheritableDisplayName("components")
public class ComponentEquivalenceTest {
	protected RepositoryEquivalenceTest getRepository() {
		return new RepositoryEquivalenceTest();
	}

	@TestFactory
	public Iterable<? extends DynamicNode> creation(ParameterizedEquivalenceTestBuilder parameterBuilder) {
		return parameterBuilder.<java.lang.Class<? extends ImplementationComponentType>, Integer>parameterizedBy(
				List.of(
						parameterBuilder.parameters(BasicComponent.class, 0),
						parameterBuilder.parameters(CompositeComponent.class, 1)),
				(componentType, componentTypeChoice) -> componentType.getSimpleName(),
				(builder, componentType, componentTypeChoice) -> {

					builder.dependsOn(dependency -> getRepository().creation(dependency));

					builder.stepFor(pcm.getMetamodel(), view -> {
						view.propagate(view.from(Repository.class, repository("model/Test")), pcmRepository -> {
							ImplementationComponentType component = pcm.repository.create(componentType);
							component.setEntityName("TestComponent");
							pcmRepository.getComponents__Repository().add(component);
						});
					});

					builder.inputVariantFor(pcm.getMetamodel(), "lowercase name", view -> {
						view.propagate(view.from(Repository.class, repository("model/Test")), pcmRepository -> {
							ImplementationComponentType component = pcm.repository.create(componentType);
							component.setEntityName("testComponent");
							pcmRepository.getComponents__Repository().add(component);
						});
					});

					builder.stepFor(java.getMetamodel(), view -> {
						// create appropriate Component
						view.getUserInteraction().addNextSingleSelection(componentTypeChoice);

						view.propagate(view.resourceAt(java("src/test/testComponent/package-info")), resource -> {
							resource.getContents().add(newJavaPackage("testComponent", "test"));
						});

						Resource implementationResource = view
								.resourceAt(java("src/test/testComponent/TestComponentImpl"));
						/*
						 * In the test view, the implementation class may already be present due to transitive change
						 * propagation, so do not create it again. For the reference view created without change
						 * propagation, it has to be created.
						 */
						if (implementationResource.getContents().isEmpty()) {
							view.propagate(implementationResource, resource -> {
								resource.getContents().add(newJavaCompilationUnit("TestComponentImpl", "test",
										"testComponent"));
							});
						}
					});

					builder.inputVariantFor(java.getMetamodel(), "creating only a package", view -> {
						// create appropriate Component
						view.getUserInteraction().addNextSingleSelection(componentTypeChoice);
						view.propagate(view.resourceAt(java("src/test/testComponent/package-info")), resource -> {
							resource.getContents().add(newJavaPackage("testComponent", "test"));
						});
					}).alsoCompareToMainStepOfSameMetamodel();
				});
	}

	@TestFactory
	public Iterable<? extends DynamicNode> renaming(ParameterizedEquivalenceTestBuilder parameterBuilder) {
		return parameterBuilder.dependsOn(this::creation, builder -> {

			builder.stepFor(pcm.getMetamodel(), view -> {
				view.propagate(view.from(Repository.class, repository("model/Test")).getComponents__Repository().get(0),
						component -> component.setEntityName("RenamedComponent"));
			});

			builder.inputVariantFor(pcm.getMetamodel(), "lowercase name", view -> {
				view.propagate(view.from(Repository.class, repository("model/Test")).getComponents__Repository().get(0),
						component -> component.setEntityName("renamedComponent"));
			});

			builder.stepFor(java.getMetamodel(), view -> {
				// do nothing
				view.getUserInteraction().addNextSingleSelection(3);
				view.propagate(view.resourceAt(java("src/test/renamedComponent/package-info")), resource -> {
					resource.getContents().add(newJavaPackage("renamedComponent", "test"));
				});

				view.propagate(view.resourceAt(java("src/test/testComponent/TestComponentImpl")), resource -> {
					/*
					 * Inserting the compilation unit into the containing package is necessary to be consistent with
					 * the underlying VirtualModel, in which the reference is set. The JaMoPP metamodel does not
					 * ensure that this reference is consistently set. It depends on the order in which packages and
					 * compilation units are created and/or loaded.
					 */
					Package originalPackage = view.from(Package.class,
							view.resourceAt(java("src/test/testComponent/package-info")));
					originalPackage.getCompilationUnits().add(view.from(CompilationUnit.class, resource));
					view.moveTo(resource, java("src/test/renamedComponent/RenamedComponentImpl"));
					CompilationUnit compilationUnit = view.from(CompilationUnit.class, resource);
					compilationUnit.getNamespaces().set(1, "renamedComponent");
					compilationUnit.setName("RenamedComponentImpl");
					var classifier = compilationUnit.getClassifiers().get(0);
					classifier.setName("RenamedComponentImpl");
					classifier.getMembers().get(0).setName("RenamedComponentImpl");
				});

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

	private static Package newJavaPackage(String name, String... namespaces) {
		Package javaPackage = java.containers.create(Package.class);
		javaPackage.getNamespaces().addAll(List.of(namespaces));
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

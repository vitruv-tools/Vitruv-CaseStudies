package tools.vitruv.applications.cbs.equivalencetests;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.MembersFactory;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.system.SystemFactory;

/**
 * Creates the model elements used by the equivalence tests.
 */
final class TestModels {
    private TestModels() {
    }

    static Repository newRepository(String name) {
        Repository repository = RepositoryFactory.eINSTANCE.createRepository();
        repository.setEntityName(name);
        return repository;
    }

    static org.palladiosimulator.pcm.system.System newPcmSystem(String name) {
        org.palladiosimulator.pcm.system.System pcmSystem = SystemFactory.eINSTANCE.createSystem();
        pcmSystem.setEntityName(name);
        return pcmSystem;
    }

    static Package newJavaPackage(String name, String... namespaces) {
        Package javaPackage = ContainersFactory.eINSTANCE.createPackage();
        javaPackage.getNamespaces().addAll(List.of(namespaces));
        javaPackage.setName(name);
        return javaPackage;
    }

    /**
     * Creates a compilation unit containing a public final class with a public default constructor.
     */
    static CompilationUnit newImplementationCompilationUnit(String className, String... namespaces) {
        CompilationUnit compilationUnit = ContainersFactory.eINSTANCE.createCompilationUnit();
        compilationUnit.getNamespaces().addAll(List.of(namespaces));
        var implementationClass = ClassifiersFactory.eINSTANCE.createClass();
        implementationClass.setName(className);
        implementationClass.makePublic();
        implementationClass.addModifier(ModifiersFactory.eINSTANCE.createFinal());
        Constructor constructor = MembersFactory.eINSTANCE.createConstructor();
        constructor.setName(className);
        constructor.makePublic();
        implementationClass.getMembers().add(constructor);
        compilationUnit.getClassifiers().add(implementationClass);
        return compilationUnit;
    }

    static Model newUmlModel(String name, org.eclipse.uml2.uml.Package... packages) {
        Model model = UMLFactory.eINSTANCE.createModel();
        model.setName(name);
        model.getPackagedElements().addAll(List.of(packages));
        return model;
    }

    static org.eclipse.uml2.uml.Package newUmlPackage(String name, org.eclipse.uml2.uml.Package... subPackages) {
        org.eclipse.uml2.uml.Package umlPackage = UMLFactory.eINSTANCE.createPackage();
        umlPackage.setName(name);
        umlPackage.getPackagedElements().addAll(List.of(subPackages));
        return umlPackage;
    }

    static void deleteResource(Resource resource) {
        try {
            resource.delete(Map.of());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

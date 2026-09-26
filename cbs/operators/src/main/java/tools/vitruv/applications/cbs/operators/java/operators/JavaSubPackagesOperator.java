package tools.vitruv.applications.cbs.operators.java.operators;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emftext.language.java.JavaUniquePathConstructor;
import org.emftext.language.java.containers.ContainersPackage;
import org.emftext.language.java.containers.Package;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil;
import tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper;
import tools.vitruv.dsls.commonalities.runtime.helper.IntermediateModelHelper;
import static tools.vitruv.dsls.commonalities.runtime.helper.XtendAssertHelper.assertTrue;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.reference.AbstractReferenceMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.reference.ReferenceMappingOperator;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Converted from Xtend: JavaSubPackagesOperator
 */
@ReferenceMappingOperator(name = "javaSubPackages", isMultiValued = true, isAttributeReference = true)
public class JavaSubPackagesOperator extends AbstractReferenceMappingOperator {

    private static final Logger logger = Logger.getLogger(JavaSubPackagesOperator.class);

    public JavaSubPackagesOperator(ReactionExecutionState executionState) {
        super(executionState);
    }

    private Package validateContainer(EObject container) {
        checkNotNull(container, "container is null");
        checkArgument(container instanceof Package, "container is not of type Package");
        return (Package) container;
    }

    private Package validateObject(EObject object) {
        checkNotNull(object, "object is null");
        checkArgument(object instanceof Package, "object is not of type Package");
        return (Package) object;
    }

    @Override
    public Collection<EObject> getContainedObjects(EObject container) {
        Package pkg = validateContainer(container);
        return findSubPackagesInProject(pkg).stream().map(p -> (EObject) p).collect(Collectors.toList());
    }

    @Override
    public EObject getContainer(EObject object) {
        Package subPackage = validateObject(object);
        String subPackageNamespacesString = getNamespacesString(subPackage);
        logger.trace(String.format("Searching container for Java package: %s", subPackage));
        // TODO avoid brute force search
        // TODO only finds packages with an correspondence
        List<EObject> knownPackages = this.executionState.getCorrespondenceModel()
                .getCorrespondingEObjects(ContainersPackage.Literals.PACKAGE)
                .stream().filter(ContainersPackage.Literals.PACKAGE.getEPackage()::equals) // defensive; keep original
                                                                                           // behaviour
                .collect(Collectors.toList());
        return knownPackages.stream()
                .filter(Package.class::isInstance)
                .map(Package.class::cast)
                .peek(p -> logger.trace(String.format("  Found candidate package: %s", p)))
                .filter(p -> getPackageString(p).equals(subPackageNamespacesString))
                .findFirst()
                .orElse(null);
    }

    private String getNamespacesString(Package javaPackage) {
        return String.join(JavaUniquePathConstructor.PACKAGE_SEPARATOR, javaPackage.getNamespaces());
    }

    private String getPackageString(Package javaPackage) {
        String namespacesString = getNamespacesString(javaPackage);
        if (namespacesString == null || namespacesString.isEmpty()) {
            return javaPackage.getName();
        } else {
            return namespacesString + JavaUniquePathConstructor.PACKAGE_SEPARATOR + javaPackage.getName();
        }
    }

    @Override
    public boolean isContained(EObject container, EObject object) {
        Package pkg = validateContainer(container);
        Package subPackage = validateObject(object);
        String packageString = getPackageString(pkg);
        String subPackageNamespacesString = subPackage.getNamespacesAsString();
        return packageString.equals(subPackageNamespacesString);
    }

    @Override
    public void insert(EObject container, EObject object) {
        validateContainer(container);
        validateObject(object);
        Package pkg = (Package) container;
        Package subPackage = (Package) object;
        // TODO Only update namespaces if not already matching?
        logger.trace(
                String.format("Inserting Java package '%s' into package '%s'.", getPackageString(subPackage),
                        getPackageString(pkg)));

        JavaContainerAndClassifierUtil.updateNamespaces(subPackage, pkg);

        EObject resourceBridge = IntermediateModelHelper
                .getCorrespondingResourceBridge(executionState.getCorrespondenceModel(), subPackage);

        assertTrue(resourceBridge != null);
        // TODO specify source folder as operator argument
        String newPath = JavaPersistenceHelper.buildJavaPackagePath("src/", subPackage.getNamespaces(),
                subPackage.getName());
        // resourceBridge.path = newPath; -- resourceBridge is a generic EObject, the
        // original code set a 'path' feature.
        IntermediateModelHelper
                .getCorrespondingResourceBridge(executionState.getCorrespondenceModel(), subPackage)
                .eSet(IntermediateModelHelper
                        .getCorrespondingResourceBridge(executionState.getCorrespondenceModel(), subPackage).eClass()
                        .getEStructuralFeature("path"), newPath);

        logger.trace(String.format("  Updated sub-package resource path to: %s", newPath));
    }

    // parentPackage needs to already be persisted
    // only finds sub packages in the current project
    private List<Package> findSubPackagesInProject(Package parentPackage) {
        checkNotNull(parentPackage, "Parent package is null");
        Resource parentResource = parentPackage.eResource();
        checkNotNull(parentResource, "The parent package is not contained inside a resource!");
        URI parentURI = parentResource.getURI();
        ResourceSet resourceSet = parentResource.getResourceSet();

        Path parentFile = URIUtil.getIPathForEMFUri(parentURI).toFile().toPath();
        Path parentDirectory = parentFile.getParent();
        if (parentDirectory == null)
            return Collections.emptyList();
        if (!Files.isDirectory(parentDirectory))
            return Collections.emptyList(); // in case the directory does not exist (yet)

        try (Stream<Path> stream = Files.list(parentDirectory)) {
            List<Path> subDirectories = stream.filter(Files::isDirectory).collect(Collectors.toList());
            // TODO This does not find sub-packages which have not yet been persisted.
            // Since model resources might get persisted only after change propagation, this
            // might not find packages which
            // have been created during the current change propagation. However, this might
            // not be an issue if the creation
            // of these packages triggers another transitive change propagation which should
            // then be able to find them.
            // To detect these packages during the current change propagation already, we
            // may have to also search the
            // ResourceSet for not yet persisted packages. Or use the correspondence model
            // to find all packages.
            return subDirectories.stream()
                    .map(dir -> getJavaPackage(resourceSet, dir))
                    .filter(p -> p != null)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.warn("Failed to list directory " + parentDirectory, e);
            return Collections.emptyList();
        }
    }

    private Package getJavaPackage(ResourceSet resourceSet, Path directory) {
        Path packageFile = directory
                .resolve(JavaPersistenceHelper.getPackageInfoClassName()
                        + JavaUniquePathConstructor.JAVA_FILE_EXTENSION);
        if (!Files.isRegularFile(packageFile)) {
            return null;
        }
        URI packageURI = URI.createFileURI(packageFile.toAbsolutePath().toString());
        Resource packageResource = resourceSet.getResource(packageURI, true);
        if (packageResource == null)
            return null;
        EObject headContent = packageResource.getContents().isEmpty() ? null : packageResource.getContents().get(0);
        if (headContent instanceof Package aPackage) {
            return aPackage;
        }
        return null;
    }
}
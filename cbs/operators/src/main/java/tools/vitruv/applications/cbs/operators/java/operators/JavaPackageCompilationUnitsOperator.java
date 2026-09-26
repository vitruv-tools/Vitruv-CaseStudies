package tools.vitruv.applications.cbs.operators.java.operators;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.JavaUniquePathConstructor;
import org.emftext.language.java.commons.NamespaceAwareElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersPackage;
import org.emftext.language.java.containers.Package;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil;
import tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper;
import tools.vitruv.dsls.commonalities.runtime.helper.IntermediateModelHelper;
import static tools.vitruv.dsls.commonalities.runtime.helper.XtendAssertHelper.assertTrue;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.reference.AbstractReferenceMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.reference.ReferenceMappingOperator;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

// TODO Some duplication with JavaSubPackagesOperator
@ReferenceMappingOperator(name = "javaPackageCompilationUnits", isMultiValued = true, isAttributeReference = true)
public class JavaPackageCompilationUnitsOperator extends AbstractReferenceMappingOperator {

    private static final Logger logger = Logger.getLogger(JavaPackageCompilationUnitsOperator.class);

    // Operands: ref CompilationUnit.namespaces, Package.namespaces, Package.name
    public JavaPackageCompilationUnitsOperator(ReactionExecutionState executionState) {
        super(executionState);
    }

    private Package validateContainer(EObject container) {
        checkNotNull(container, "container is null");
        checkArgument(container instanceof Package, "container is not of type Package");
        return (Package) container;
    }

    private CompilationUnit validateObject(EObject object) {
        checkNotNull(object, "object is null");
        checkArgument(object instanceof CompilationUnit, "object is not of type CompilationUnit");
        return (CompilationUnit) object;
    }

    @Override
    public Collection<EObject> getContainedObjects(EObject container) {
        Package pkg = validateContainer(container);
        logger.trace(String.format("Found compilation units in package '%s': %s",
                getPackageString(pkg), pkg.getCompilationUnits()));
        return pkg.getCompilationUnits().stream()
                .map(e -> (EObject) e)
                .collect(Collectors.toList());
    }

    @Override
    public EObject getContainer(EObject object) {
        CompilationUnit compilationUnit = validateObject(object);
        String compilationUnitNamespacesString = getNamespacesString(compilationUnit);
        logger.trace(String.format("Searching container for Java CompilationUnit: %s", compilationUnit));

        // TODO avoid brute force search
        // TODO only finds CompilationUnits with a correspondence
        Collection<Package> knownPackages = executionState.getCorrespondenceModel()
                .getCorrespondingEObjects(ContainersPackage.Literals.PACKAGE)
                .stream()
                .filter(Package.class::isInstance)
                .map(Package.class::cast)
                .collect(Collectors.toList());

        return knownPackages.stream()
                .peek(pkg -> logger.trace(String.format("Found candidate package: %s", pkg)))
                .filter(pkg -> getPackageString(pkg).equals(compilationUnitNamespacesString))
                .findFirst()
                .orElse(null);
    }

    private String getNamespacesString(NamespaceAwareElement element) {
        return String.join(JavaUniquePathConstructor.PACKAGE_SEPARATOR, element.getNamespaces());
    }

    private String getPackageString(Package javaPackage) {
        String namespacesString = getNamespacesString(javaPackage);
        if (namespacesString.isEmpty()) {
            return javaPackage.getName();
        } else {
            return namespacesString + JavaUniquePathConstructor.PACKAGE_SEPARATOR + javaPackage.getName();
        }
    }

    @Override
    public boolean isContained(EObject container, EObject object) {
        Package pkg = validateContainer(container);
        CompilationUnit compilationUnit = validateObject(object);
        String packageString = getPackageString(pkg);
        String compilationUnitNamespacesString = getNamespacesString(compilationUnit);
        return packageString.equals(compilationUnitNamespacesString);
    }

    @Override
    public void insert(EObject container, EObject object) {
        validateContainer(container);
        validateObject(object);
        Package pkg = (Package) container;
        CompilationUnit compilationUnit = (CompilationUnit) object;

        logger.trace(String.format("Inserting Java CompilationUnit %s into package '%s'",
                compilationUnit, getPackageString(pkg)));

        JavaContainerAndClassifierUtil.updateNamespaces(compilationUnit, pkg);

        var resourceBridge = IntermediateModelHelper
                .getCorrespondingResourceBridge(executionState.getCorrespondenceModel(), compilationUnit);
        assertTrue(resourceBridge != null);

        // TODO specify source folder as operator argument
        resourceBridge.setPath(JavaPersistenceHelper.buildJavaPath("src/", compilationUnit.getNamespaces()));
        logger.trace(String.format("Updated CompilationUnit resource path to: %s", resourceBridge.getPath()));

        compilationUnit.setName(getCompilationUnitName(compilationUnit.getNamespaces(), resourceBridge.getName()));
    }

    // CompilationUnit name schema:
    // '<dot-separated-namespaces>.<fileName/classifierName>.java'
    private static String getCompilationUnitName(Iterable<String> namespaces, String fileName) {
        StringBuilder name = new StringBuilder(String.join(".", namespaces));
        if (fileName != null && !fileName.isEmpty()) {
            if (name.length() > 0) {
                name.append('.');
            }
            name.append(fileName)
                    .append(JavaUniquePathConstructor.JAVA_FILE_EXTENSION);
        }
        return name.toString();
    }
}
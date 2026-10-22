package tools.vitruv.applications.cbs.operators.java.operators;

import java.util.List;

import org.apache.log4j.Logger;
import org.emftext.language.java.containers.ContainersPackage;
import org.emftext.language.java.containers.Package;

import static com.google.common.base.Preconditions.checkNotNull;

import static tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper.PATH_SEPARATOR;
import static tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper.buildJavaPackagePath;
import static tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper.stripJavaSourcePath;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

@AttributeMappingOperator(name = "javaPackageResourcePathFromName", commonalityAttributeType = @AttributeType(multiValued = false, type = String.class), participationAttributeType = @AttributeType(multiValued = false, type = String.class))
public class JavaPackageResourcePathFromNameOperator extends AbstractAttributeMappingOperator<String, String> {

    private static final Logger logger = Logger.getLogger(JavaPackageResourcePathFromNameOperator.class);

    private final String sourcePath;
    private final Package javaPackage;

    /**
     * @param executionState the reactions execution state
     * @param sourcePath     the java source path (eg. 'src/'), can be empty
     * @param javaPackage    the java package
     */
    public JavaPackageResourcePathFromNameOperator(ReactionExecutionState executionState, String sourcePath,
            Package javaPackage) {
        super(executionState);
        checkNotNull(sourcePath, "sourcePath is null");
        checkNotNull(javaPackage, "javaPackage is null");
        this.sourcePath = sourcePath;
        this.javaPackage = javaPackage;
    }

    // resource path -> package name
    @Override
    public String applyTowardsCommonality(String resourcePath) {
        String normalizedPath = normalizeResourcePathStatic(resourcePath); // can be null
        String pathRelativeToSource = (normalizedPath == null) ? null
                : stripJavaSourcePath(normalizedPath, sourcePath);
        String packageName = getPackageName(pathRelativeToSource);
        logger.debug(String.format("Mapping resource path '%s' and source path '%s' to package name '%s'.",
                normalizedPath, sourcePath, packageName));
        return packageName;
    }

    // package name -> resource path
    @Override
    public String applyTowardsParticipation(String packageName) {
        registerUniqueJavaPackageCorrespondence();
        List<String> namespaces = javaPackage.getNamespaces();
        String resourcePath = buildJavaPackagePath(sourcePath, namespaces, packageName);
        logger.debug(
                String.format("Mapping package name '%s', namespaces %s and source path '%s' to resource path '%s'.",
                        packageName, namespaces, sourcePath, resourcePath));
        return resourcePath;
    }

    private void registerUniqueJavaPackageCorrespondence() {
        if (!this.executionState.getCorrespondenceModel().getCorrespondingEObjects(ContainersPackage.Literals.PACKAGE)
                .contains(javaPackage)) {
            this.executionState.getCorrespondenceModel().addCorrespondenceBetween(javaPackage,
                    ContainersPackage.Literals.PACKAGE, null);
        }
    }

    // The path is expected to be relative to the Java source path.
    // Examples:
    // 'a/b/c/name.ext' -> 'c'
    // 'a/b/c/name' -> 'c'
    // 'a/b/c/' -> 'c'
    // name.ext -> null
    // name -> null
    // '' -> null
    // null -> null
    private static String getPackageName(String javaFilePath) {
        if (javaFilePath == null || javaFilePath.isEmpty())
            return null;
        // Includes a trailing empty segment if the file name is empty:
        String[] segments = javaFilePath.split(PATH_SEPARATOR, -1);
        // length < 2: No path separator found -> Path contains only the file name
        if (segments.length < 2)
            return null;
        // Package name is the segment in front of the file name:
        String packageName = segments[segments.length - 2];
        // Normalize empty package name to null:
        return (packageName == null || packageName.isEmpty()) ? null : packageName;
    }

    /*
     * Call JavaPersistenceHelper static methods (these are the Xtend extension
     * methods in the original). This avoids accidental name clashes with local
     * helpers.
     */
    private static String normalizeResourcePathStatic(String resourcePath) {
        if (resourcePath == null || resourcePath.isEmpty())
            return null;
        // Append the path separator if it is missing:
        if (resourcePath.endsWith(PATH_SEPARATOR)) {
            return resourcePath;
        } else {
            return resourcePath + PATH_SEPARATOR;
        }
    }
}
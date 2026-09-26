package tools.vitruv.applications.cbs.operators.java.operators;

import static java.lang.String.join;

import org.apache.log4j.Logger;
import org.emftext.language.java.JavaUniquePathConstructor;
import org.emftext.language.java.containers.CompilationUnit;

import static com.google.common.base.Preconditions.checkNotNull;

import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * CompilationUnit name schema:
 * '<dot-separated-namespaces>.<fileName/classifierName>.java'
 */
@AttributeMappingOperator(name = "javaCompilationUnitName", commonalityAttributeType = @AttributeType(multiValued = false, type = String.class), participationAttributeType = @AttributeType(multiValued = false, type = String.class))
public class JavaCompilationUnitNameOperator extends AbstractAttributeMappingOperator<String, String> {

    private static final Logger logger = Logger.getLogger(JavaCompilationUnitNameOperator.class);
    private final CompilationUnit javaCompilationUnit;

    /**
     * Creates a new JavaCompilationUnitNameOperator.
     * 
     * @param executionState      the reactions execution state
     * @param javaCompilationUnit the java compilation unit
     * @throws IllegalArgumentException if javaCompilationUnit is null
     */
    public JavaCompilationUnitNameOperator(ReactionExecutionState executionState,
            CompilationUnit javaCompilationUnit) {
        super(executionState);
        checkNotNull(javaCompilationUnit, "javaCompilationUnit is null");
        this.javaCompilationUnit = javaCompilationUnit;
    }

    @Override
    public String applyTowardsCommonality(String compilationUnitName) {
        String fileName;
        if (compilationUnitName.endsWith(JavaUniquePathConstructor.JAVA_FILE_EXTENSION)) {
            int fileExtensionStartIndex = compilationUnitName.length()
                    - JavaUniquePathConstructor.JAVA_FILE_EXTENSION.length();
            // fileNameStartIndex == 0 if no dot is found in front of the file extension:
            int fileNameStartIndex = compilationUnitName.lastIndexOf('.', fileExtensionStartIndex - 1) + 1;
            fileName = compilationUnitName.substring(fileNameStartIndex, fileExtensionStartIndex);
        } else {
            // CompilationUnit name is of unexpected format.
            // fileNameStartIndex == 0 if no dot is found:
            int fileNameStartIndex = compilationUnitName.lastIndexOf('.', compilationUnitName.length()) + 1;
            fileName = compilationUnitName.substring(fileNameStartIndex);
        }

        logger.debug(String.format("Mapping CompilationUnit name '%s' to file name '%s'",
                compilationUnitName, fileName));

        return fileName;
    }

    @Override
    public String applyTowardsParticipation(String fileName) {
        StringBuilder compilationUnitNameBuilder = new StringBuilder();
        compilationUnitNameBuilder.append(join(".", javaCompilationUnit.getNamespaces()));

        if (!fileName.isEmpty()) {
            if (compilationUnitNameBuilder.length() > 0) {
                compilationUnitNameBuilder.append('.');
            }
            compilationUnitNameBuilder.append(fileName)
                    .append(JavaUniquePathConstructor.JAVA_FILE_EXTENSION);
        }

        String compilationUnitName = compilationUnitNameBuilder.toString();

        logger.debug(String.format("Mapping file name '%s' to CompilationUnit name '%s'",
                fileName, compilationUnitName));

        return compilationUnitName;
    }
}
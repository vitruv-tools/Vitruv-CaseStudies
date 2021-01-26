package tools.vitruv.applications.cbs.commonalities.java.operators

import org.apache.log4j.Logger
import org.emftext.language.java.JavaUniquePathConstructor
import org.emftext.language.java.containers.CompilationUnit
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static com.google.common.base.Preconditions.*

// CompilationUnit name schema: '<dot-separated-namespaces>.<fileName/classifierName>.java'
@AttributeMappingOperator(
	name='javaCompilationUnitName',
	commonalityAttributeType = @AttributeType(multiValued=false, type=String),
	participationAttributeType = @AttributeType(multiValued=false, type=String)
)
class JavaCompilationUnitNameOperator extends AbstractAttributeMappingOperator<String, String> {

	static val Logger logger = Logger.getLogger(JavaCompilationUnitNameOperator)

	val CompilationUnit javaCompilationUnit

	/**
	 * @param executionState the reactions execution state
	 * @param javaCompilationUnit the java compilation unit
	 */
	new(ReactionExecutionState executionState, CompilationUnit javaCompilationUnit) {
		super(executionState)
		checkNotNull(javaCompilationUnit, "javaCompilationUnit is null")
		this.javaCompilationUnit = javaCompilationUnit
	}

	// CompilationUnit name -> fileName
	override applyTowardsCommonality(String compilationUnitName) {
		var String fileName
		if (compilationUnitName.endsWith(JavaUniquePathConstructor.JAVA_FILE_EXTENSION)) {
			val fileExtensionStartIndex = compilationUnitName.length - JavaUniquePathConstructor.JAVA_FILE_EXTENSION.length
			// fileNameStartIndex == 0 if no dot is found in front of the file extension:
			val fileNameStartIndex = compilationUnitName.lastIndexOf('.', fileExtensionStartIndex - 1) + 1
			fileName = compilationUnitName.substring(fileNameStartIndex, fileExtensionStartIndex)
		} else {
			// CompilationUnit name is of unexpected format.
			// fileNameStartIndex == 0 if no dot is found:
			val fileNameStartIndex = compilationUnitName.lastIndexOf('.', compilationUnitName.length) + 1
			fileName = compilationUnitName.substring(fileNameStartIndex)
		}
		logger.debug('''Mapping CompilationUnit name '«compilationUnitName»' to file name '«fileName»'.''')
		return fileName
	}

	// fileName -> CompilationUnit name
	override applyTowardsParticipation(String fileName) {
		val namespaces = javaCompilationUnit.namespaces
		val compilationUnitNameBuilder = new StringBuilder
		compilationUnitNameBuilder.append(namespaces.join('.'))
		if (!fileName.empty) {
			if (compilationUnitNameBuilder.length > 0) {
				compilationUnitNameBuilder.append('.')
			}
			compilationUnitNameBuilder.append(fileName)
			compilationUnitNameBuilder.append(JavaUniquePathConstructor.JAVA_FILE_EXTENSION)
		}
		val compilationUnitName = compilationUnitNameBuilder.toString
		logger.debug('''Mapping file name '«fileName»' to CompilationUnit name '«compilationUnitName»'.''')
		return compilationUnitName
	}
}

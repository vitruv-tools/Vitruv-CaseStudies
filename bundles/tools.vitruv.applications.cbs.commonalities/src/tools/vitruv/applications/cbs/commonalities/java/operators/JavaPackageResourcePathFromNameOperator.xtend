package tools.vitruv.applications.cbs.commonalities.java.operators

import org.apache.log4j.Logger
import org.emftext.language.java.containers.Package
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.domains.java.util.JavaPersistenceHelper.*
import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.getCorrespondingEObjects
import org.emftext.language.java.containers.ContainersPackage
import java.util.List

@AttributeMappingOperator(
	name='javaPackageResourcePathFromName',
	commonalityAttributeType = @AttributeType(multiValued=false, type=String),
	participationAttributeType = @AttributeType(multiValued=false, type=String)
)
class JavaPackageResourcePathFromNameOperator extends AbstractAttributeMappingOperator<String, String> {

	static val Logger logger = Logger.getLogger(JavaPackageResourcePathFromNameOperator)

	val String sourcePath
	val Package javaPackage

	/**
	 * @param executionState the reactions execution state
	 * @param sourcePath the java source path (eg. 'src/'), can be empty
	 * @param javaPackage the java package
	 */
	new(ReactionExecutionState executionState, String sourcePath, Package javaPackage) {
		super(executionState)
		checkNotNull(sourcePath, "sourcePath is null")
		checkNotNull(javaPackage, "javaPackage is null")
		this.sourcePath = sourcePath
		this.javaPackage = javaPackage
	}

	// resource path -> package name
	override applyTowardsCommonality(String resourcePath) {
		val normalizedPath = resourcePath.normalizeResourcePath // can be null
		val pathRelativeToSource = normalizedPath?.stripJavaSourcePath(sourcePath)
		val packageName = getPackageName(pathRelativeToSource)
		logger.debug('''Mapping resource path '«normalizedPath»' and source path '«sourcePath»' to package name '«
			packageName»'.''')
		return packageName
	}

	private def normalizeResourcePath(String resourcePath) {
		if (resourcePath.nullOrEmpty) return null
		// Append the path separator if it is missing:
		if (resourcePath.endsWith(PATH_SEPARATOR)) {
			return resourcePath
		} else {
			return resourcePath + PATH_SEPARATOR
		}
	}
	
	private def registerUniqueJavaPackageCorrespondence() {
		if (!correspondenceModel.getCorrespondingEObjects(ContainersPackage.Literals.PACKAGE).contains(javaPackage)) {
			correspondenceModel.createAndAddCorrespondence(List.of(javaPackage), List.of(ContainersPackage.Literals.PACKAGE))
		}
	}

	// package name -> resource path
	override applyTowardsParticipation(String packageName) {
		registerUniqueJavaPackageCorrespondence()
		val namespaces = javaPackage.namespaces
		val resourcePath = buildJavaPackagePath(sourcePath, namespaces, packageName)
		logger.debug('''Mapping package name '«packageName»', namespaces «namespaces» and source path '«sourcePath
			»' to resource path '«resourcePath»'.''')
		return resourcePath
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
	private static def String getPackageName(String javaFilePath) {
		if (javaFilePath.nullOrEmpty) return null
		// Includes a trailing empty segment if the file name is empty:
		val segments = javaFilePath.split(PATH_SEPARATOR, -1)
		// length < 2: No path separator found -> Path contains only the file name
		if (segments.length < 2) return null
		// Package name is the segment in front of the file name:
		val packageName = segments.get(segments.length - 2)
		// Normalize empty package name to null:
		return (packageName.nullOrEmpty) ? null : packageName
	}
}

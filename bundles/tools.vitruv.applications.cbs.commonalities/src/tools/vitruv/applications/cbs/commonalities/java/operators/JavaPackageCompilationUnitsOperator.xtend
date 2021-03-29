package tools.vitruv.applications.cbs.commonalities.java.operators

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.JavaUniquePathConstructor
import org.emftext.language.java.commons.NamespaceAwareElement
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.Package
import tools.vitruv.domains.java.util.JavaPersistenceHelper
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference.AbstractReferenceMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference.ReferenceMappingOperator
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static com.google.common.base.Preconditions.*
import static tools.vitruv.framework.util.XtendAssertHelper.*

import static extension tools.vitruv.extensions.dslruntime.commonalities.helper.IntermediateModelHelper.*
import static extension tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.*
import org.emftext.language.java.containers.ContainersPackage
import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.getCorrespondingEObjects

// TODO Some duplication with JavaSubPackagesOperator
@ReferenceMappingOperator(
	name = 'javaPackageCompilationUnits',
	isMultiValued = true,
	isAttributeReference = true
)
class JavaPackageCompilationUnitsOperator extends AbstractReferenceMappingOperator {

	static val Logger logger = Logger.getLogger(JavaPackageCompilationUnitsOperator)

	// Operands: ref CompilationUnit.namespaces, Package.namespaces, Package.name
	new(ReactionExecutionState executionState) {
		super(executionState)
	}

	private def Package validateContainer(EObject container) {
		checkNotNull(container, "container is null")
		checkArgument(container instanceof Package, "container is not of type Package")
		return container as Package
	}

	private def CompilationUnit validateObject(EObject object) {
		checkNotNull(object, "object is null")
		checkArgument(object instanceof CompilationUnit, "object is not of type CompilationUnit")
		return object as CompilationUnit
	}

	override getContainedObjects(EObject container) {
		val Package package = validateContainer(container)
		logger.trace('''  Found compilation units in package '«package.packageString»': «package.compilationUnits»''')
		return package.compilationUnits
	}

	override getContainer(EObject object) {
		val CompilationUnit compilationUnit = validateObject(object)
		val compilationUnitNamespacesString = compilationUnit.namespacesString
		logger.trace('''Searching container for Java CompilationUnit: «compilationUnit»''')
		// TODO avoid brute force search
		// TODO only finds CompilationUnits with a correspondence
		val knownPackages = correspondenceModel.getCorrespondingEObjects(ContainersPackage.Literals.PACKAGE, Package)
		return knownPackages.findFirst [
			logger.trace('''  Found candidate package: «it»''')
			it.packageString == compilationUnitNamespacesString
		]
	}

	private def getNamespacesString(NamespaceAwareElement javaNamespaceAwareElement) {
		return javaNamespaceAwareElement.namespaces.join(JavaUniquePathConstructor.PACKAGE_SEPARATOR)
	}

	private def getPackageString(Package javaPackage) {
		val namespacesString = javaPackage.namespacesString
		if (namespacesString.empty) {
			return javaPackage.name
		} else {
			return namespacesString + JavaUniquePathConstructor.PACKAGE_SEPARATOR + javaPackage.name
		}
	}

	override isContained(EObject container, EObject object) {
		val Package package = validateContainer(container)
		val CompilationUnit compilationUnit = validateObject(object)
		val packageString = package.packageString
		val compilationUnitNamespacesString = compilationUnit.namespacesString
		return (packageString == compilationUnitNamespacesString)
	}

	override insert(EObject container, EObject object) {
		validateContainer(container)
		validateObject(object)
		val Package package = (container as Package)
		val CompilationUnit compilationUnit = (object as CompilationUnit)
		logger.trace('''Inserting Java CompilationUnit «compilationUnit» into package '«package.packageString»'.''')
		compilationUnit.updateNamespaces(package)

		val resourceBridge = correspondenceModel.getCorrespondingResourceBridge(compilationUnit)
		assertTrue(resourceBridge !== null)
		// TODO specify source folder as operator argument
		resourceBridge.path = JavaPersistenceHelper.buildJavaPath("src/", compilationUnit.namespaces)
		logger.trace('''  Updated CompilationUnit resource path to: «resourceBridge.path»''')

		compilationUnit.name = getCompilationUnitName(compilationUnit.namespaces, resourceBridge.name)
	}

	// CompilationUnit name schema: '<dot-separated-namespaces>.<fileName/classifierName>.java'
	private static def String getCompilationUnitName(Iterable<String> namespaces, String fileName) {
		var name = namespaces.join('.')
		if (!fileName.nullOrEmpty) {
			if (!name.empty) name += '.'
			name += fileName + JavaUniquePathConstructor.JAVA_FILE_EXTENSION
		}
		return name
	}
}

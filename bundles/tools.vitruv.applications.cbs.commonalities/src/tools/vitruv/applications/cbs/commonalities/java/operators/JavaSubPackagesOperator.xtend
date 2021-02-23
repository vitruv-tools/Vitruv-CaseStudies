package tools.vitruv.applications.cbs.commonalities.java.operators

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil
import java.nio.file.Path
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.emftext.language.java.JavaUniquePathConstructor
import org.emftext.language.java.containers.Package
import tools.vitruv.domains.java.util.JavaPersistenceHelper
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference.AbstractReferenceMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference.ReferenceMappingOperator
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState
import tools.vitruv.framework.util.bridges.EMFBridge

import static com.google.common.base.Preconditions.*
import static java.util.stream.Collectors.*
import static tools.vitruv.framework.util.XtendAssertHelper.*

import static extension java.nio.file.Files.*
import static extension tools.vitruv.extensions.dslruntime.commonalities.helper.IntermediateModelHelper.*

import static extension tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.*

@ReferenceMappingOperator(
	name = 'javaSubPackages',
	isMultiValued = true,
	isAttributeReference = true
)
class JavaSubPackagesOperator extends AbstractReferenceMappingOperator {

	static val Logger logger = Logger.getLogger(JavaSubPackagesOperator)

	// Operands: ref Package.namespaces, Package.namespaces, Package.name
	new(ReactionExecutionState executionState) {
		super(executionState)
	}

	private def Package validateContainer(EObject container) {
		checkNotNull(container, "container is null")
		checkArgument(container instanceof Package, "container is not of type Package")
		return container as Package
	}

	private def Package validateObject(EObject object) {
		checkNotNull(object, "object is null")
		checkArgument(object instanceof Package, "object is not of type Package")
		return object as Package
	}

	override getContainedObjects(EObject container) {
		val Package package = validateContainer(container)
		return package.findSubPackagesInProject
	}

	override getContainer(EObject object) {
		val Package subPackage = validateObject(object)
		val subPackageNamespacesString = subPackage.namespacesString
		logger.trace('''Searching container for Java package: «subPackage»''')
		// TODO avoid brute force search
		// TODO only finds packages with an correspondence
		val knownPackages = correspondenceModel.getAllEObjectsOfTypeInCorrespondences(Package)
		return knownPackages.findFirst [
			logger.trace('''  Found candidate package: «it»''')
			it.packageString == subPackageNamespacesString
		]
	}

	private def getNamespacesString(Package javaPackage) {
		return javaPackage.namespaces.join(JavaUniquePathConstructor.PACKAGE_SEPARATOR)
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
		val Package subPackage = validateObject(object)
		val packageString = package.packageString
		val subPackageNamespacesString = subPackage.namespacesString
		return (packageString == subPackageNamespacesString)
	}

	override insert(EObject container, EObject object) {
		validateContainer(container)
		validateObject(object)
		val Package package = (container as Package)
		val Package subPackage = (object as Package)
		// TODO Only update namespaces if not already matching?
		logger.trace('''Inserting Java package '«subPackage.packageString»' into package '«package.packageString»'.''')
		subPackage.updateNamespaces(package)

		val resourceBridge = correspondenceModel.getCorrespondingResourceBridge(subPackage)
		assertTrue(resourceBridge !== null)
		// TODO specify source folder as operator argument
		resourceBridge.path = JavaPersistenceHelper.buildJavaPackagePath("src/", subPackage.namespaces, subPackage.name)
		logger.trace('''  Updated sub-package resource path to: «resourceBridge.path»''')
	}

	// parentPackage needs to already be persisted
	// only finds sub packages in the current project
	private def findSubPackagesInProject(Package parentPackage) {
		checkNotNull(parentPackage, "Parent package is null")
		val parentResource = parentPackage.eResource
		checkNotNull(parentResource, "The parent package is not contained inside a resource!")
		val parentURI = parentResource.URI
		val resourceSet = parentResource.resourceSet

		val parentFile = URIUtil.getIPathForEMFUri(parentURI).toFile.toPath
		val parentDirectory = parentFile.parent
		if (!parentDirectory.isDirectory) return #[] // in case the directory does not exist (yet)

		val subDirectories = parentDirectory.list.filter[isDirectory].collect(toList)
		// TODO This does not find sub-packages which have not yet been persisted.
		// Since model resources might get persisted only after change propagation, this might not find packages which
		// have been created during the current change propagation. However, this might not be an issue if the creation
		// of these packages triggers another transitive change propagation which should then be able to find them.
		// To detect these packages during the current change propagation already, we may have to also search the
		// ResourceSet for not yet persisted packages. Or use the correspondence model to find all packages.
		return subDirectories.map [ subDirectory |
			return resourceSet.getJavaPackage(subDirectory)
		].filterNull
	}

	private def Package getJavaPackage(ResourceSet resourceSet, Path directory) {
		val packageFile = directory.resolve(JavaPersistenceHelper.packageInfoClassName + JavaUniquePathConstructor.JAVA_FILE_EXTENSION)
		if (!packageFile.isRegularFile) {
			return null
		}
		val packageURI = EMFBridge.getEmfFileUriForFile(packageFile.toFile)
		val packageResource = resourceSet.getResource(packageURI, true)
		if (packageResource === null) return null
		val headContent = packageResource.contents.head
		if (headContent instanceof Package) {
			return headContent
		}
		return null
	}
}

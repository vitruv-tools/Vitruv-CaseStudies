package tools.vitruv.applications.umljava.tests.java2uml.constructionsimulationtest

import org.junit.jupiter.api.Test
import java.io.File
import static tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.*
import java.util.List
import java.nio.file.Path
import static org.eclipse.emf.common.util.URI.createFileURI
import tools.vitruv.applications.umljava.tests.java2uml.AbstractJavaToUmlTest
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import java.util.Map
import org.emftext.language.java.resource.java.IJavaOptions
import org.emftext.language.java.containers.CompilationUnit
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import org.emftext.language.java.members.Member
import org.emftext.language.java.classifiers.ConcreteClassifier
import static tools.vitruv.domains.java.util.JavaPersistenceHelper.buildJavaFilePath
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.types.TypeReference
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.mapFixed
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import static com.google.common.base.Preconditions.checkArgument
import org.eclipse.emf.ecore.EObject
import static org.junit.jupiter.api.Assertions.assertFalse
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import org.emftext.language.java.statements.Statement
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.flatMapFixed
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.resource.Resource
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.instanceOf
import org.eclipse.emf.ecore.resource.ResourceSet
import org.emftext.language.java.statements.StatementListContainer
import org.junit.jupiter.api.BeforeAll
import tools.vitruv.domains.java.JavaDomainProvider
import org.emftext.language.java.members.Field
import org.emftext.language.java.expressions.Expression
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.isPathmap
import static tools.vitruv.domains.java.JamoppLibraryHelper.*

/**
 * Test class for the reconstruction of existing java models
 */
class JavaConstructionSimulationTest extends AbstractJavaToUmlTest {
	static val LOGGER = Logger.getLogger(JavaConstructionSimulationTest)

	@BeforeAll
	static def void registerJavaFactoriesSupportingArrays() {
		new JavaDomainProvider().domain
		/* We use special Java resource factories that create the "length" field for 
		 * arrays in the resources of an array's type. This is necessary, because these 
		 * fields are only created when loading a Java file but not when changing it to 
		 * have an array (like it is the case when propagating changes into a VirtualModel).
		 */
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("java",
			new JavaSourceOrClassFileResourceWithArraysDefaultFactoryImpl())
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("class",
			new JavaSourceOrClassFileResourceWithArraysDefaultFactoryImpl())
	}

	/**
	 * Tests a small synthetic project that accesses the "length" attribute of arrays.
	 * This leads to the array's type's resource being modified by JaMoPP.
	 */
	@Test
	def void testSyntheticProjectWithArrayAccess() {
		transformJavaProjectAndValidateUmlModel("resources/synthetic/java/")
	}

	/** 
	 * Tests the files from "myproject" by suresh519
	 * https://repository.genmymodel.com/suresh519/MyProject (12.5.2017)
	 */
	@Test
	def void testMyProject() {
		transformJavaProjectAndValidateUmlModel("resources/suresh519/java/")
	}

	/**
	 * Tests the files from the logger project by orhan obut
	 * https://github.com/orhanobut/logger (12.5.2017)
	 */
	@Test
	def void testOrhanobutLoggerProject() {
		registerStdLibraryModule("java.xml")
		registerLocalLibrary(Path.of(new File("lib/json-20211205.jar").absolutePath))
		transformJavaProjectAndValidateUmlModel("resources/orhanobut/java/")
	}

	private def Iterable<File> collectJavaFilesInFolder(File folder) {
		return collectJavaFilesInPackagesRecursive(folder, #[], true)
	}

	private def Iterable<File> collectJavaFilesInPackagesRecursive(File folder, Iterable<String> superNamespaces,
		boolean isRoot) {
		checkArgument(folder.isDirectory, "file for folder must be a directory")
		if (!isRoot && !folder.listFiles.exists[isPackageInfoFile]) {
			createPackageInfo(folder.toString, (superNamespaces + #[folder.name]).join("."))
		}
		return (folder.listFiles.filter[name.endsWith(".java")] + folder.listFiles.filter[isDirectory].flatMapFixed [
			collectJavaFilesInPackagesRecursive(it, superNamespaces + #[folder.name], false)
		]).filterNull
	}

	private def void transformJavaProjectAndValidateUmlModel(String projectRelativeJavaDirectoryPath) {
		transformJavaFilesAndValidateUmlModel(new File(projectRelativeJavaDirectoryPath).collectJavaFilesInFolder())
	}

	private def void transformJavaFilesAndValidateUmlModel(Iterable<File> javaFiles) {
		val javaRoots = javaFiles.toList.loadRootObjects
		val packages = javaRoots.filter(org.emftext.language.java.containers.Package)
		val compilationUnits = javaRoots.filter(CompilationUnit)

		val compilationUnitExtractors = packages.mapFixed[new CompilationUnitExtractor(it)]
		val classifiers = compilationUnits.mapFixed[classifiers.claimOne]
		val memberExtractors = classifiers.mapFixed[new MemberExtractor(it)]
		val methodImplementationExtractors = classifiers.flatMap[members].filter(StatementListContainer).mapFixed [
			new MethodImplementationExtractor(it)
		]
		val fieldInitializerExtractors = classifiers.flatMap[members].filter(Field).mapFixed [
			new FieldInitializerExtractor(it)
		]
		val inheritanceExtractors = classifiers.mapFixed[new InheritanceExtractor(it)]
		compilationUnitExtractors.forEach[removeAndStoreCompilationUnits()]
		fieldInitializerExtractors.forEach[removeAndStoreInitializer()]
		methodImplementationExtractors.forEach[removeAndStoreImplementation()]
		memberExtractors.forEach[removeAndStoreMembers()]
		inheritanceExtractors.forEach[removeAndStoreExtends()]

		changeJavaView [
			LOGGER.debug("Adding packages and compilation units")
			packages.forEach [ package |
				LOGGER.trace("Adding package " + package)
				createAndRegisterRoot(package, Path.of(buildJavaFilePath(package)).uri)
			]
			compilationUnits.forEach [ compilationUnit |
				LOGGER.trace("Adding compilation unit " + compilationUnit)
				createAndRegisterRoot(compilationUnit, Path.of(buildJavaFilePath(compilationUnit)).uri)
			]
			LOGGER.debug("Adding compilation units to packages")
			compilationUnitExtractors.forEach[reapplyStoredCompilationUnits()]
			LOGGER.debug("Adding inheritance relations")
			inheritanceExtractors.forEach[reapplyStoredExtends()]
			LOGGER.debug("Adding members")
			memberExtractors.forEach[reapplyStoredMembers()]
			LOGGER.debug("Adding field initializer")
			fieldInitializerExtractors.forEach[reapplyStoredInitializer()]
			LOGGER.debug("Adding method implementations")
			methodImplementationExtractors.forEach[reapplyStoredImplementation()]
			LOGGER.debug("Simulating construction")
		]

		for (classifier : classifiers) {
			val namespaces = classifier.containingCompilationUnit.namespaces
			switch (classifier) {
				org.emftext.language.java.classifiers.Class:
					assertClassWithNameInPackage(namespaces, classifier.name)
				org.emftext.language.java.classifiers.Interface:
					assertInterfaceWithNameInPackage(namespaces, classifier.name)
				org.emftext.language.java.classifiers.Enumeration:
					assertEnumWithNameInPackage(namespaces, classifier.name)
			}
		}
	}

	private def Iterable<EObject> loadRootObjects(Iterable<File> javaFiles) {
		val originalResourceSet = new ResourceSetImpl().withGlobalFactories
		originalResourceSet.loadOptions += Map.of(IJavaOptions.DISABLE_LAYOUT_INFORMATION_RECORDING, true)
		LOGGER.debug("Loading models")
		val javaRoots = javaFiles.flatMapFixed [ file |
			LOGGER.trace("Loading model " + file.path)
			val contents = originalResourceSet.getResource(createFileURI(file.absolutePath), true).contents
			if (!file.isPackageInfoFile) {
				assertThat(contents.head, instanceOf(CompilationUnit))
			} else if (file.isPackageInfoFile) {
				assertThat(contents.head, instanceOf(org.emftext.language.java.containers.Package))
			}
			contents
		]
		LOGGER.debug("Resolving proxies")
		validateNoProxies(originalResourceSet)
		return javaRoots
	}

	private def void validateNoProxies(ResourceSet resourceSet) {
		LOGGER.debug("Validating non-proxy references")
		val resourceSetContents = resourceSet.resources.filter[!URI.isPathmap].flatMapFixed[allContents.toList]
		resourceSetContents.forEach [ eObject |
			eObject.eClass.EAllReferences.forEach [
				val referencedObjects = if (it.many) {
						eObject.eGet(it) as Iterable<EObject>
					} else if (eObject.eGet(it) !== null) {
						#[eObject.eGet(it) as EObject]
					} else {
						#[]
					}
				for (referencedObject : referencedObjects) {
					if (referencedObject.eIsProxy) {
						assertFalse(referencedObject.eIsProxy, eObject + " must be resolved")
					}
				}
			]
		]
	}

	private def static boolean isPackageInfoFile(File file) {
		file.name == "package-info.java"
	}

	@FinalFieldsConstructor
	private static class CompilationUnitExtractor {
		val org.emftext.language.java.containers.Package javaPackage
		val List<CompilationUnit> compilationUnits = newArrayList

		def void removeAndStoreCompilationUnits() {
			LOGGER.trace("Removing compilation units from " + javaPackage)
			compilationUnits += javaPackage.compilationUnits
			javaPackage.compilationUnits.clear()
		}

		def void reapplyStoredCompilationUnits() {
			LOGGER.trace("Adding compilation units to " + javaPackage)
			javaPackage.compilationUnits += compilationUnits
			compilationUnits.clear()
		}
	}

	@FinalFieldsConstructor
	private static class MemberExtractor {
		val ConcreteClassifier classifier
		val List<Member> members = newArrayList

		def void removeAndStoreMembers() {
			LOGGER.trace("Removing members from " + classifier)
			members += classifier.members
			classifier.members.clear()
		}

		def void reapplyStoredMembers() {
			LOGGER.trace("Adding members to " + classifier)
			classifier.members += members
			members.clear()
		}
	}

	@FinalFieldsConstructor
	private static class MethodImplementationExtractor {
		val StatementListContainer methodWithImplementation
		val List<Statement> statements = newArrayList

		def void removeAndStoreImplementation() {
			LOGGER.trace("Removing statements from " + methodWithImplementation)
			statements += methodWithImplementation.statements
			methodWithImplementation.statements.clear()
		}

		def void reapplyStoredImplementation() {
			LOGGER.trace("Adding statements to " + methodWithImplementation)
			methodWithImplementation.statements += statements
			statements.clear()
		}
	}

	@FinalFieldsConstructor
	private static class FieldInitializerExtractor {
		val Field field
		var Expression initializer

		def void removeAndStoreInitializer() {
			LOGGER.trace("Removing initializer from " + field)
			initializer = field.initialValue
			field.initialValue = null
		}

		def void reapplyStoredInitializer() {
			LOGGER.trace("Adding initializer to " + field)
			field.initialValue = initializer
			initializer = null
		}
	}

	@FinalFieldsConstructor
	private static class InheritanceExtractor {
		val ConcreteClassifier classifier
		val List<TypeReference> extensions = newArrayList
		val List<TypeReference> implementations = newArrayList

		def void removeAndStoreExtends() {
			LOGGER.trace("Removing extends from " + classifier)
			switch (classifier) {
				Interface: {
					extensions += classifier.extends
					classifier.extends.clear()
				}
				org.emftext.language.java.classifiers.Class: {
					extensions += classifier.extends
					classifier.extends = null
					implementations += classifier.implements
					classifier.implements.clear()
				}
			}
		}

		def void reapplyStoredExtends() {
			LOGGER.trace("Adding extends to " + classifier)
			switch (classifier) {
				Interface: {
					classifier.extends += extensions
					extensions.clear()
				}
				org.emftext.language.java.classifiers.Class: {
					classifier.extends = extensions.claimOne
					extensions.clear()
					classifier.implements += implementations
					implementations.clear()
				}
			}
		}
	}

}

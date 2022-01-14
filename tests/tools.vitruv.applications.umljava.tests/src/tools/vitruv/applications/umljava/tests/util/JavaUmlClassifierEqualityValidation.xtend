package tools.vitruv.applications.umljava.tests.util

import tools.vitruv.framework.vsum.views.View
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.*
import static tools.vitruv.testutils.matchers.ModelMatchers.isResource
import static tools.vitruv.testutils.matchers.ModelMatchers.isNoResource
import static org.hamcrest.MatcherAssert.assertThat
import org.eclipse.uml2.uml.Classifier
import org.eclipse.emf.common.util.URI
import java.nio.file.Path
import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*
import static extension tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.isInExistingLibrary
import static tools.vitruv.applications.umljava.tests.util.JavaUmlElementEqualityValidation.assertElementsEqual

/**
 * This class provides validations for the equal existence of classifiers of different types to exist
 * in both UML and Java models.
 */
@FinalFieldsConstructor
class JavaUmlClassifierEqualityValidation {
	val String umlModelName
	val ()=>View viewProvider
	val (Path)=>URI uriForProjectRelativePathProvider

	def assertClassWithNameInRootPackage(String className) {
		assertClassWithNameInPackage(#[], className)
	}

	def assertClassWithNameInPackage(String packageName, String className) {
		assertClassWithNameInPackage(#[packageName], className)
	}

	def assertClassWithNameInPackage(Iterable<String> packageNamespaces, String className) {
		assertClassifierWithName(org.emftext.language.java.classifiers.Class, org.eclipse.uml2.uml.Class,
			packageNamespaces, className)
	}

	def assertSingleClassWithNameInRootPackage(String className) {
		assertSingleClassWithNameInPackage(#[], className)
	}

	def assertSingleClassWithNameInPackage(String packageName, String className) {
		assertSingleClassWithNameInPackage(#[packageName], className)
	}

	def assertSingleClassWithNameInPackage(Iterable<String> packageNamespaces, String className) {
		assertSingleClassifierWithName(org.emftext.language.java.classifiers.Class, org.eclipse.uml2.uml.Class,
			packageNamespaces, className)
	}

	def assertDataTypeWithNameInRootPackage(String dataTypeName) {
		assertDataTypeWithNameInPackage(#[], dataTypeName)
	}

	def assertDataTypeWithNameInPackage(String packageName, String dataTypeName) {
		assertDataTypeWithNameInPackage(#[packageName], dataTypeName)
	}

	def assertDataTypeWithNameInPackage(Iterable<String> packageNamespaces, String dataTypeName) {
		assertClassifierWithName(org.emftext.language.java.classifiers.Class, org.eclipse.uml2.uml.DataType,
			packageNamespaces, dataTypeName)
	}

	def assertSingleDataTypeWithNameInRootPackage(String dataTypeName) {
		assertSingleDataTypeWithNameInPackage(#[], dataTypeName)
	}

	def assertSingleDataTypeWithNameInPackage(String packageName, String dataTypeName) {
		assertSingleDataTypeWithNameInPackage(#[packageName], dataTypeName)
	}

	def assertSingleDataTypeWithNameInPackage(Iterable<String> packageNamespaces, String dataTypeName) {
		assertSingleClassifierWithName(org.emftext.language.java.classifiers.Class, org.eclipse.uml2.uml.DataType,
			packageNamespaces, dataTypeName)
	}

	def assertEnumWithNameInRootPackage(String enumName) {
		assertEnumWithNameInPackage(#[], enumName)
	}

	def assertEnumWithNameInPackage(String packageName, String enumName) {
		assertEnumWithNameInPackage(#[packageName], enumName)
	}

	def assertEnumWithNameInPackage(Iterable<String> packageNamespaces, String enumName) {
		assertClassifierWithName(org.emftext.language.java.classifiers.Enumeration, org.eclipse.uml2.uml.Enumeration,
			packageNamespaces, enumName)
	}

	def assertSingleEnumWithNameInRootPackage(String enumName) {
		assertSingleEnumWithNameInPackage(#[], enumName)
	}

	def assertSingleEnumWithNameInPackage(String packageName, String enumName) {
		assertSingleEnumWithNameInPackage(#[packageName], enumName)
	}

	def assertSingleEnumWithNameInPackage(Iterable<String> packageNamespaces, String enumName) {
		assertSingleClassifierWithName(org.emftext.language.java.classifiers.Enumeration,
			org.eclipse.uml2.uml.Enumeration, packageNamespaces, enumName)
	}

	def assertInterfaceWithNameInRootPackage(String interfaceName) {
		assertInterfaceWithNameInPackage(#[], interfaceName)
	}

	def assertInterfaceWithNameInPackage(String packageName, String interfaceName) {
		assertInterfaceWithNameInPackage(#[packageName], interfaceName)
	}

	def assertInterfaceWithNameInPackage(Iterable<String> packageNamespaces, String interfaceName) {
		assertClassifierWithName(org.emftext.language.java.classifiers.Interface, org.eclipse.uml2.uml.Interface,
			packageNamespaces, interfaceName)
	}

	def assertSingleInterfaceWithNameInRootPackage(String interfaceName) {
		assertSingleInterfaceWithNameInPackage(#[], interfaceName)
	}

	def assertSingleInterfaceWithNameInPackage(String packageName, String interfaceName) {
		assertSingleInterfaceWithNameInPackage(#[packageName], interfaceName)
	}

	def assertSingleInterfaceWithNameInPackage(Iterable<String> packageNamespaces, String interfaceName) {
		assertSingleClassifierWithName(org.emftext.language.java.classifiers.Interface, org.eclipse.uml2.uml.Interface,
			packageNamespaces, interfaceName)
	}

	/**
	 * Validates that the root package does not contain a classifier with the given name both in 
	 * the UML and in the Java model.
	 */
	def void assertNoClassifierWithNameInRootPackage(String classifierName) {
		viewProvider.apply => [
			assertThat("no classifier in UML model with name " + classifierName + " is expected to exist",
				claimUmlModel(umlModelName).packagedElements.filter(Classifier).filter [
					name == classifierName
				].toSet, is(emptySet))
			assertThat("no Java classifier with name " + classifierName + " is expected to exist",
				javaClassifiers.filter [
					name == classifierName && containingCompilationUnit.namespacesAsString.nullOrEmpty
				].toSet, is(emptySet))
			assertThat("no Java compilation unit with name " + classifierName + " is expected to exist",
				javaCompilationUnits.filter[name == classifierName + ".java"].toSet, is(emptySet))
		]
		assertJavaFileNotExists(classifierName, #[])
	}

	/**
	 * Validates that the given package does not contain a classifier with the given name both in 
	 * the UML and in the Java model.
	 */
	def void assertNoClassifierWithNameInPackage(String packageName, String classifierName) {
		viewProvider.apply => [
			assertThat("no classifier in UML model with name " + classifierName + " is expected to exist in package " +
				packageName,
				claimUmlModel(umlModelName).claimPackage(packageName).packagedElements.filter(Classifier).filter [
					name == classifierName
				].toSet, is(emptySet))
			assertThat("no Java classifier with name " + classifierName + " is expected to exist in package " +
				packageName, javaClassifiers.filter [
				name == classifierName && containingCompilationUnit.namespacesAsString == packageName
			].toSet, is(emptySet))
			assertThat("no Java compilation unit with name " + classifierName + " is expected to exist in package " +
				packageName, javaCompilationUnits.filter[name == packageName + "." + classifierName + ".java"].toSet,
				is(emptySet))
		]
		assertJavaFileNotExists(classifierName, #[packageName])
	}

	/**
	 * Validates that the root package does not contain any classifier both in 
	 * the UML and in the Java model.
	 */
	def void assertNoClassifierExistsInRootPackage() {
		viewProvider.apply => [
			assertThat("no element in UML model is expected to exist",
				claimUmlModel(umlModelName).packagedElements.filter(Classifier).toSet, is(emptySet))
			assertThat("no Java classifier is expected to exist", javaClassifiers.filter [
				containingCompilationUnit.namespacesAsString.nullOrEmpty
			].toSet, is(emptySet))
			assertThat("no Java compilation unit is expected to exist", javaCompilationUnits.filter [
				namespacesAsString.nullOrEmpty
			].toSet, is(emptySet))
		]
	}

	/**
	 * Validates that a classifier with the given name exists in the package with the given namespaces
	 * with equal properties both in the UML and in the Java model.
	 */
	private def void assertClassifierWithName(
		Class<? extends org.emftext.language.java.classifiers.Classifier> javaClassifierType,
		Class<? extends org.eclipse.uml2.uml.Classifier> umlClassifierType, Iterable<String> namespaces,
		String classifierName) {
		viewProvider.apply => [
			val javaClassifier = claimJavaClassifier(javaClassifierType, classifierName)
			var org.eclipse.uml2.uml.Package umlPackage = claimUmlModel(umlModelName)
			for (namespace : namespaces) {
				umlPackage = umlPackage.claimPackage(namespace)
			}
			val umlClassifier = umlPackage.claimPackageableElement(umlClassifierType, classifierName)
			assertElementsEqual(umlClassifier, javaClassifier)
		]
		assertJavaFileExists(classifierName, namespaces)
	}

	/**
	 * Validates that a classifier with the given name exists in the package with the given namespaces
	 * with equal properties both in the UML and in the Java model.
	 * This will fail if the package contains any further elements.
	 */
	private def void assertSingleClassifierWithName(
		Class<? extends org.emftext.language.java.classifiers.Classifier> javaClassifierType,
		Class<? extends org.eclipse.uml2.uml.Classifier> umlClassifierType, Iterable<String> namespaces,
		String classifierName) {
		viewProvider.apply => [
			assertClassifierWithName(javaClassifierType, umlClassifierType, namespaces, classifierName)
			val javaClassifier = claimJavaClassifier(javaClassifierType, classifierName)
			val javaCompilationUnit = claimJavaCompilationUnit(namespaces.join("", ".", ".", [it]) + classifierName)
			var org.eclipse.uml2.uml.Package umlPackage = claimUmlModel(umlModelName)
			for (namespace : namespaces) {
				umlPackage = umlPackage.claimPackage(namespace)
			}
			val umlClassifier = umlPackage.claimPackageableElement(umlClassifierType, classifierName)
			assertThat("only one element in UML model is expected to exist",
				umlPackage.packagedElements.filter(Classifier).toSet, is(#{umlClassifier}))
			assertThat("only one Java compilation unit is expected to exist", javaCompilationUnits.filter [
				!isInExistingLibrary
			].toSet, is(#{javaCompilationUnit}))
			assertThat("only one Java classifier is expected to exist",
				getJavaClassifiersOfType(javaClassifierType).filter[!isInExistingLibrary].toSet, is(#{javaClassifier}))
			assertElementsEqual(umlClassifier, javaClassifier)
		]
	}

	private def assertJavaFileExists(String fileName, String[] namespaces) {
		assertThat(uriForProjectRelativePathProvider.apply(Path.of(buildJavaFilePath(fileName + ".java", namespaces))),
			isResource)
	}

	private def assertJavaFileNotExists(String fileName, String[] namespaces) {
		assertThat(uriForProjectRelativePathProvider.apply(Path.of(buildJavaFilePath(fileName + ".java", namespaces))),
			isNoResource)
	}

}

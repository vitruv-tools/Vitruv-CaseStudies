package tools.vitruv.applications.umljava.tests.util

import tools.vitruv.framework.vsum.views.View
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import static tools.vitruv.applications.umljava.tests.util.TestUtil.assertElementsEqual
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.*
import static tools.vitruv.testutils.matchers.ModelMatchers.isResource
import static tools.vitruv.testutils.matchers.ModelMatchers.isNoResource
import static org.hamcrest.MatcherAssert.assertThat
import org.eclipse.uml2.uml.Classifier
import org.eclipse.emf.common.util.URI
import java.nio.file.Path
import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*

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
		assertClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Class, org.eclipse.uml2.uml.Class,
			className)
	}

	def assertSingleClassWithNameInRootPackage(String className) {
		assertSingleClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Class,
			org.eclipse.uml2.uml.Class, className)
	}

	def assertClassWithNameInPackage(String packageName, String className) {
		assertClassifierWithNameInPackage(org.emftext.language.java.classifiers.Class, org.eclipse.uml2.uml.Class,
			packageName, className)
	}

	def assertSingleClassWithNameInPackage(String packageName, String className) {
		assertSingleClassifierWithNameInPackage(org.emftext.language.java.classifiers.Class, org.eclipse.uml2.uml.Class,
			packageName, className)
	}

	def assertDataTypeWithNameInRootPackage(String dataTypeName) {
		assertClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Class,
			org.eclipse.uml2.uml.DataType, dataTypeName)
	}

	def assertSingleDataTypeWithNameInRootPackage(String dataTypeName) {
		assertSingleClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Class,
			org.eclipse.uml2.uml.DataType, dataTypeName)
	}

	def assertDataTypeWithNameInPackage(String packageName, String dataTypeName) {
		assertClassifierWithNameInPackage(org.emftext.language.java.classifiers.Class, org.eclipse.uml2.uml.DataType,
			packageName, dataTypeName)
	}

	def assertSingleDataTypeWithNameInPackage(String packageName, String dataTypeName) {
		assertSingleClassifierWithNameInPackage(org.emftext.language.java.classifiers.Class,
			org.eclipse.uml2.uml.DataType, packageName, dataTypeName)
	}

	def assertEnumWithNameInRootPackage(String enumName) {
		assertClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Enumeration,
			org.eclipse.uml2.uml.Enumeration, enumName)
	}

	def assertSingleEnumWithNameInRootPackage(String enumName) {
		assertSingleClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Enumeration,
			org.eclipse.uml2.uml.Enumeration, enumName)
	}

	def assertEnumWithNameInPackage(String packageName, String enumName) {
		assertClassifierWithNameInPackage(org.emftext.language.java.classifiers.Enumeration,
			org.eclipse.uml2.uml.Enumeration, packageName, enumName)
	}

	def assertSingleEnumWithNameInPackage(String packageName, String enumName) {
		assertSingleClassifierWithNameInPackage(org.emftext.language.java.classifiers.Enumeration,
			org.eclipse.uml2.uml.Enumeration, packageName, enumName)
	}

	def assertInterfaceWithNameInRootPackage(String interfaceName) {
		assertClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Interface,
			org.eclipse.uml2.uml.Interface, interfaceName)
	}

	def assertSingleInterfaceWithNameInRootPackage(String interfaceName) {
		assertSingleClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Interface,
			org.eclipse.uml2.uml.Interface, interfaceName)
	}

	def assertInterfaceWithNameInPackage(String packageName, String interfaceName) {
		assertClassifierWithNameInPackage(org.emftext.language.java.classifiers.Interface,
			org.eclipse.uml2.uml.Interface, packageName, interfaceName)
	}

	def assertSingleInterfaceWithNameInPackage(String packageName, String interfaceName) {
		assertSingleClassifierWithNameInPackage(org.emftext.language.java.classifiers.Interface,
			org.eclipse.uml2.uml.Interface, packageName, interfaceName)
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
	 * Validates that a classifier with the given name exists in the root package
	 * with equal properties both in the UML and in the Java model.
	 */
	private def void assertClassifierWithNameInRootPackage(
		Class<? extends org.emftext.language.java.classifiers.Classifier> javaClassifierType,
		Class<? extends org.eclipse.uml2.uml.Classifier> umlClassifierType, String classifierName) {
		viewProvider.apply => [
			val javaClassifier = claimJavaClassifier(javaClassifierType, classifierName)
			val umlClassifier = claimUmlModel(umlModelName).claimPackageableElement(umlClassifierType, classifierName)
			assertElementsEqual(umlClassifier, javaClassifier)
		]
		assertJavaFileExists(classifierName, #[])
	}

	/**
	 * Validates that only a classifier with the given name exists in the root package
	 * with equal properties both in the UML and in the Java model.
	 * This will fail if the root package contains any further elements.
	 */
	private def void assertSingleClassifierWithNameInRootPackage(
		Class<? extends org.emftext.language.java.classifiers.Classifier> javaClassifierType,
		Class<? extends org.eclipse.uml2.uml.Classifier> umlClassifierType, String classifierName) {
		viewProvider.apply => [
			assertClassifierWithNameInRootPackage(javaClassifierType, umlClassifierType, classifierName)
			val javaClassifier = claimJavaClassifier(javaClassifierType, classifierName)
			val javaCompilationUnit = claimJavaCompilationUnit(classifierName)
			val umlClassifier = claimUmlModel(umlModelName).claimPackageableElement(umlClassifierType, classifierName)
			assertThat("only one element in UML model is expected to exist",
				claimUmlModel(umlModelName).packagedElements.filter(Classifier).toSet, is(#{umlClassifier}))
			assertThat("only one Java compilation unit is expected to exist", javaCompilationUnits.filter [
				!namespacesAsString.startsWith("java") && !namespacesAsString.startsWith("jdk") &&
					!namespacesAsString.startsWith("sun") // Do not consider standard library compilation units
			].toSet, is(#{javaCompilationUnit}))
			assertThat("only one Java classifier is expected to exist",
				getJavaClassifiersOfType(javaClassifierType).filter [
					!containingCompilationUnit.namespacesAsString.startsWith("java") &&
						!containingCompilationUnit.namespacesAsString.startsWith("jdk") &&
						!containingCompilationUnit.namespacesAsString.startsWith("sun") // Do not consider standard library compilation units
				].toSet, is(#{javaClassifier}))
		]
	}

	/**
	 * Validates that a classifier with the given name exists in the package with the given name
	 * with equal properties both in the UML and in the Java model.
	 */
	private def void assertClassifierWithNameInPackage(
		Class<? extends org.emftext.language.java.classifiers.Classifier> javaClassifierType,
		Class<? extends org.eclipse.uml2.uml.Classifier> umlClassifierType, String packageName, String classifierName) {
		viewProvider.apply => [
			val javaClassifier = claimJavaClassifier(javaClassifierType, classifierName)
			val umlPackage = claimUmlModel(umlModelName).claimPackage(packageName)
			val umlClassifier = umlPackage.claimPackageableElement(umlClassifierType, classifierName)
			assertElementsEqual(umlClassifier, javaClassifier)
		]
		assertJavaFileExists(classifierName, #[packageName])
	}

	/**
	 * Validates that a classifier with the given name exists in the package with the given name
	 * with equal properties both in the UML and in the Java model.
	 * This will fail if the package contains any further elements.
	 */
	private def void assertSingleClassifierWithNameInPackage(
		Class<? extends org.emftext.language.java.classifiers.Classifier> javaClassifierType,
		Class<? extends org.eclipse.uml2.uml.Classifier> umlClassifierType, String packageName, String classifierName) {
		viewProvider.apply => [
			assertClassifierWithNameInPackage(javaClassifierType, umlClassifierType, packageName, classifierName)
			val javaClassifier = claimJavaClassifier(javaClassifierType, classifierName)
			val javaCompilationUnit = claimJavaCompilationUnit(packageName + "." + classifierName)
			val umlPackage = claimUmlModel(umlModelName).claimPackage(packageName)
			val umlClassifier = umlPackage.claimPackageableElement(umlClassifierType, classifierName)
			assertThat("only one element in UML model is expected to exist",
				umlPackage.packagedElements.filter(Classifier).toSet, is(#{umlClassifier}))
			assertThat("only one Java compilation unit is expected to exist", javaCompilationUnits.filter [
				!namespacesAsString.startsWith("java") && !namespacesAsString.startsWith("jdk") &&
					!namespacesAsString.startsWith("sun") // Do not consider standard library compilation units
			].toSet, is(#{javaCompilationUnit}))
			assertThat("only one Java classifier is expected to exist",
				getJavaClassifiersOfType(javaClassifierType).filter [
					!containingCompilationUnit.namespacesAsString.startsWith("java") &&
						!containingCompilationUnit.namespacesAsString.startsWith("jdk") &&
						!containingCompilationUnit.namespacesAsString.startsWith("sun") // Do not consider standard library compilation units
				].toSet, is(#{javaClassifier}))
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

package tools.vitruv.applications.umljava.tests.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import tools.vitruv.framework.vsum.views.View
import static tools.vitruv.applications.umljava.tests.util.TestUtil.assertElementsEqual
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.*
import org.eclipse.uml2.uml.Classifier

@Utility
class JavaUmlTestUtil {
	/**
	 * Validates that in the given view a classifier with the given name exists in the root package
	 * with equal properties both in the UML and in the Java model.
	 */
	static def void assertClassifierWithNameInRootPackage(View view,
		Class<? extends org.emftext.language.java.classifiers.Classifier> javaClassifierType,
		Class<? extends org.eclipse.uml2.uml.Classifier> umlClassifierType, String umlModelName,
		String classifierName) {
		view => [
			val javaClassifier = claimJavaClassifier(javaClassifierType, classifierName)
			val umlClassifier = claimUmlModel(umlModelName).claimPackageableElement(umlClassifierType, classifierName)
			assertElementsEqual(umlClassifier, javaClassifier)
		]
	}

	/**
	 * Validates that in the given view only a classifier with the given name exists in the root package
	 * with equal properties both in the UML and in the Java model.
	 * This will fail if the root package contains any further elements.
	 */
	static def void assertSingleClassifierWithNameInRootPackage(View view,
		Class<? extends org.emftext.language.java.classifiers.Classifier> javaClassifierType,
		Class<? extends org.eclipse.uml2.uml.Classifier> umlClassifierType, String umlModelName,
		String classifierName) {
		view => [
			assertClassifierWithNameInRootPackage(javaClassifierType, umlClassifierType, umlModelName, classifierName)
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
	 * Validates that in the given view a classifier with the given name exists in the package with the given name
	 * with equal properties both in the UML and in the Java model.
	 */
	static def void assertClassifierWithNameInPackage(View view,
		Class<? extends org.emftext.language.java.classifiers.Classifier> javaClassifierType,
		Class<? extends org.eclipse.uml2.uml.Classifier> umlClassifierType, String umlModelName, String packageName,
		String classifierName) {
		view => [
			val javaClassifier = claimJavaClassifier(javaClassifierType, classifierName)
			val umlPackage = claimUmlModel(umlModelName).claimPackage(packageName)
			val umlClassifier = umlPackage.claimPackageableElement(umlClassifierType, classifierName)
			assertElementsEqual(umlClassifier, javaClassifier)
		]
	}

	/**
	 * Validates that in the given view a classifier with the given name exists in the package with the given name
	 * with equal properties both in the UML and in the Java model.
	 * This will fail if the package contains any further elements.
	 */
	static def void assertSingleClassifierWithNameInPackage(View view,
		Class<? extends org.emftext.language.java.classifiers.Classifier> javaClassifierType,
		Class<? extends org.eclipse.uml2.uml.Classifier> umlClassifierType, String umlModelName, String packageName,
		String classifierName) {
		view => [
			assertClassifierWithNameInPackage(javaClassifierType, umlClassifierType, umlModelName, packageName,
				classifierName)
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

	/**
	 * Validates that in the given view the root package does not contain a classifier with the given name both in 
	 * the UML and in the Java model.
	 */
	static def void assertNoClassifierWithNameInRootPackage(View view, String umlModelName, String classifierName) {
		view => [
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
	}

	/**
	 * Validates that in the given view the given package does not contain a classifier with the given name both in 
	 * the UML and in the Java model.
	 */
	static def void assertNoClassifierWithNameInPackage(View view, String umlModelName, String packageName,
		String classifierName) {
		view => [
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
	}

	/**
	 * Validates that in the given view the root package does not contain any classifier both in 
	 * the UML and in the Java model.
	 */
	static def void assertNoClassifierInRootPackage(View view, String umlModelName) {
		view => [
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
}

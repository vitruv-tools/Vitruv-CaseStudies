package tools.vitruv.applications.umljava.tests.java2uml

import tools.vitruv.applications.umljava.tests.UmlJavaTransformationTest
import org.emftext.language.java.classifiers.ClassifiersFactory
import java.util.List

import org.junit.jupiter.api.BeforeEach

abstract class AbstractJavaToUmlTest extends UmlJavaTransformationTest {

	@BeforeEach
	def protected setup() {
		userInteraction.addNextTextInput(UML_MODEL_NAME)
		userInteraction.addNextTextInput(MODEL_FOLDER_NAME)
	}

	protected def void createJavaPackageInRootPackage(String name) {
		createJavaPackagesView => [
			createJavaPackage[
				it.name = name
			]
		]
	}

	protected def void createJavaEnumInRootPackage(String name) {
		createJavaEnumInPackage(#[], name)
	}

	protected def void createJavaEnumInPackage(List<String> namespace, String name) {
		createJavaClassesView => [
			createJavaCompilationUnit[
				it.name = namespace.join("", ".", ".", [it]) + name + ".java"
				it.namespaces += namespace
				classifiers += ClassifiersFactory.eINSTANCE.createEnumeration => [
					it.name = name
					makePublic
				]
			]
		]
	}

	protected def void createJavaClassInRootPackage(String name) {
		createJavaClassInPackage(#[], name)
	}

	protected def void createJavaClassInPackage(List<String> namespace, String name) {
		createJavaClassesView => [
			createJavaCompilationUnit[
				it.name = namespace.join("", ".", ".", [it]) + name + ".java"
				it.namespaces += namespace
				classifiers += ClassifiersFactory.eINSTANCE.createClass => [
					it.name = name
					makePublic
				]
			]
		]
	}

	protected def void createJavaInterfaceInRootPackage(String name) {
		createJavaInterfaceInPackage(#[], name)
	}

	protected def void createJavaInterfaceInPackage(List<String> namespace, String name) {
		createJavaClassesView => [
			createJavaCompilationUnit[
				it.name = namespace.join("", ".", ".", [it]) + name + ".java"
				it.namespaces += namespace
				classifiers += ClassifiersFactory.eINSTANCE.createInterface => [
					it.name = name
					makePublic
				]
			]
		]
	}

}

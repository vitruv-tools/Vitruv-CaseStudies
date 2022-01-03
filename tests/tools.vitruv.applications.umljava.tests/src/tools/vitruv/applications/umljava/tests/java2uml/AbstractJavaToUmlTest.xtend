package tools.vitruv.applications.umljava.tests.java2uml

import tools.vitruv.applications.umljava.tests.UmlJavaTransformationTest
import org.emftext.language.java.classifiers.ClassifiersFactory
import tools.vitruv.applications.util.temporary.java.JavaVisibility
import static extension tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.List

abstract class AbstractJavaToUmlTest extends UmlJavaTransformationTest {
	@Accessors(PROTECTED_GETTER)
	static val DEFAULT_UML_MODEL_NAME = "model"

	protected def createJavaPackageInRootPackage(String name) {
		createJavaPackagesView => [
			createJavaPackage[
				it.name = name
			]
		]
	}

	protected def createJavaClassInRootPackage(String name) {
		createJavaClassInPackage(#[], name)
	}

	protected def createJavaClassInPackage(List<String> namespace, String name) {
		createJavaClassesView => [
			createJavaCompilationUnit[
				it.name = name + ".java"
				it.namespaces += namespace
				classifiers += ClassifiersFactory.eINSTANCE.createClass => [
					it.name = name
					javaVisibilityModifier = JavaVisibility.PUBLIC
				]
			]
		]
	}

	protected def createJavaInterfaceInRootPackage(String name) {
		createJavaInterfaceInPackage(#[], name)
	}
	
	protected def createJavaInterfaceInPackage(List<String> namespace, String name) {
		createJavaClassesView => [
			createJavaCompilationUnit[
				it.name = name + ".java"
				it.namespaces += namespace
				classifiers += ClassifiersFactory.eINSTANCE.createInterface => [
					it.name = name
					javaVisibilityModifier = JavaVisibility.PUBLIC
				]
			]
		]
	}

}

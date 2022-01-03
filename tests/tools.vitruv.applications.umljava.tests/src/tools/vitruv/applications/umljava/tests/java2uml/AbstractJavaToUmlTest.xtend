package tools.vitruv.applications.umljava.tests.java2uml

import tools.vitruv.applications.umljava.tests.UmlJavaTransformationTest
import org.emftext.language.java.classifiers.ClassifiersFactory
import tools.vitruv.applications.util.temporary.java.JavaVisibility
import static extension tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*
import java.util.List
import tools.vitruv.framework.vsum.views.View
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*

abstract class AbstractJavaToUmlTest extends UmlJavaTransformationTest {
	static val DEFAULT_UML_MODEL_NAME = "model"

	protected def getUniqueDefaultUmlModel(View view) {
		view.getUniqueUmlModelWithName(DEFAULT_UML_MODEL_NAME)
	}

	protected def void createJavaPackageInRootPackage(String name) {
		createJavaPackagesView => [
			createJavaPackage[
				it.name = name
			]
		]
	}

	protected def void createJavaClassInRootPackage(String name) {
		createJavaClassInPackage(#[], name)
	}

	protected def void createJavaClassInPackage(List<String> namespace, String name) {
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

	protected def void createJavaInterfaceInRootPackage(String name) {
		createJavaInterfaceInPackage(#[], name)
	}
	
	protected def void createJavaInterfaceInPackage(List<String> namespace, String name) {
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

package tools.vitruv.applications.umljava.tests.java2uml

import tools.vitruv.applications.umljava.tests.UmlJavaTransformationTest
import org.emftext.language.java.classifiers.ClassifiersFactory
import tools.vitruv.applications.util.temporary.java.JavaVisibility
import static extension tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*

abstract class AbstractJavaToUmlTest extends UmlJavaTransformationTest {
	protected def createJavaClassInRootPackage(String name) {
		createJavaClassesView => [
			createJavaCompilationUnit[
				it.name = name + ".java"
				classifiers += ClassifiersFactory.eINSTANCE.createClass => [
					it.name = name
					javaVisibilityModifier = JavaVisibility.PUBLIC
				]
			]
		]
	}
	
	protected def createJavaInterfaceInRootPackage(String name) {
		createJavaClassesView => [
			createJavaCompilationUnit[
				it.name = name + ".java"
				classifiers += ClassifiersFactory.eINSTANCE.createInterface => [
					it.name = name
					javaVisibilityModifier = JavaVisibility.PUBLIC
				]
			]
		]
	}
	
}
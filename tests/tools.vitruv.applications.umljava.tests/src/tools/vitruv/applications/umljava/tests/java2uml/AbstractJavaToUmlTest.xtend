package tools.vitruv.applications.umljava.tests.java2uml

import tools.vitruv.applications.umljava.tests.UmlJavaTransformationTest
import org.emftext.language.java.classifiers.ClassifiersFactory
import java.util.List

import org.junit.jupiter.api.BeforeEach
import static extension tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.*
import org.emftext.language.java.classifiers.ConcreteClassifier

abstract class AbstractJavaToUmlTest extends UmlJavaTransformationTest {

	@BeforeEach
	def protected void setupUserInteractions() {
		userInteraction.onTextInput[message.contains("enter a name for the UML root")].respondWith(UML_MODEL_NAME)
		userInteraction.onTextInput[message.contains("enter a path for the UML root")].respondWith(MODEL_FOLDER_NAME)
	}

	private def void createClassifierInCompilationUnit(ConcreteClassifier uninitializedClassifier,
		List<String> namespace, String classifierName) {
		createJavaCompilationUnit [
			it.namespaces += namespace
			updateCompilationUnitName(classifierName)
			classifiers += uninitializedClassifier => [
				name = classifierName
			]
		]
	}

	protected def void createJavaPackageInRootPackage(String name) {
		createJavaPackage [
			it.name = name
		]
	}

	protected def void createJavaEnumInRootPackage(String name) {
		createJavaEnumInPackage(#[], name)
	}

	protected def void createJavaEnumInPackage(List<String> namespace, String name) {
		createClassifierInCompilationUnit(ClassifiersFactory.eINSTANCE.createEnumeration, namespace, name)
	}

	protected def void createJavaClassInRootPackage(String name) {
		createJavaClassInPackage(#[], name)
	}

	protected def void createJavaClassInPackage(List<String> namespace, String name) {
		createClassifierInCompilationUnit(ClassifiersFactory.eINSTANCE.createClass, namespace, name)
	}

	protected def void createJavaInterfaceInRootPackage(String name) {
		createJavaInterfaceInPackage(#[], name)
	}

	protected def void createJavaInterfaceInPackage(List<String> namespace, String name) {
		createClassifierInCompilationUnit(ClassifiersFactory.eINSTANCE.createInterface, namespace, name)
	}

}

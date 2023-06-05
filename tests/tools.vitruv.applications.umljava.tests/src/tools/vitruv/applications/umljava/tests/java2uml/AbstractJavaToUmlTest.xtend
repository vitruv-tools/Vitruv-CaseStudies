package tools.vitruv.applications.umljava.tests.java2uml

import java.nio.file.Path
import java.util.List
import tools.mdsd.jamopp.model.java.classifiers.ClassifiersFactory
import tools.mdsd.jamopp.model.java.classifiers.ConcreteClassifier
import tools.mdsd.jamopp.model.java.containers.CompilationUnit
import tools.mdsd.jamopp.model.java.containers.ContainersFactory
import tools.mdsd.jamopp.model.java.containers.JavaRoot
import tools.mdsd.jamopp.model.java.containers.Package
import org.junit.jupiter.api.BeforeEach
import tools.vitruv.applications.umljava.tests.UmlJavaTransformationTest
import tools.vitruv.framework.views.View

import static tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper.*

import static extension tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.*

abstract class AbstractJavaToUmlTest extends UmlJavaTransformationTest {

	@BeforeEach
	def protected void setupUserInteractions() {
		userInteraction.onTextInput[message.contains("enter a name for the UML root")].respondWith(UML_MODEL_NAME)
		userInteraction.onTextInput[message.contains("enter a path for the UML root")].respondWith(MODEL_FOLDER_NAME)
	}

	protected def void createJavaCompilationUnit((CompilationUnit)=>void compilationUnitInitialization) {
		changeJavaView [
			val compilationUnit = ContainersFactory.eINSTANCE.createCompilationUnit
			compilationUnitInitialization.apply(compilationUnit)
			createAndRegisterRoot(compilationUnit, Path.of(buildJavaFilePath(compilationUnit)).uri)
		]
	}

	protected def void createJavaPackage((Package)=>void packageInitialization) {
		changeJavaView [
			val package = ContainersFactory.eINSTANCE.createPackage
			packageInitialization.apply(package)
			createAndRegisterRoot(package, Path.of(buildJavaFilePath(package)).uri)
		]
	}

	protected def void moveJavaRootElement(View view, JavaRoot rootElement) {
		view.moveRoot(rootElement, Path.of(buildJavaFilePath(rootElement)).uri)
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

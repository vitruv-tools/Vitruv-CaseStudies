package tools.vitruv.applications.cbs.commonalities.tests.oo.java

import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.ContainersFactory
import tools.vitruv.applications.cbs.commonalities.tests.java.JavaTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.oo.InterfaceTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.*

class JavaInterfaceTestModels extends JavaTestModelsBase implements InterfaceTest.DomainModels {

	private static def newJavaPackage1() {
		return ContainersFactory.eINSTANCE.createPackage => [
			name = PACKAGE_1_NAME
		]
	}

	private static def newJavaPackage2() {
		return ContainersFactory.eINSTANCE.createPackage => [
			name = PACKAGE_2_NAME
		]
	}

	private static def newJavaInterface1() {
		return ClassifiersFactory.eINSTANCE.createInterface => [
			name = INTERFACE_1_NAME
		]
	}

	private static def newJavaInterface2() {
		return ClassifiersFactory.eINSTANCE.createInterface => [
			name = INTERFACE_2_NAME
		]
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	// Empty interface

	override emptyInterfaceCreation() {
		return newModel [
			val javaPackage = newJavaPackage1
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaInterface1)
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	// Multiple interfaces

	override multipleInterfacesInSamePackageCreation() {
		return newModel [
			val javaPackage = newJavaPackage1
			val javaCompilationUnit1 = javaPackage.newCompilationUnit(newJavaInterface1)
			val javaCompilationUnit2 = javaPackage.newCompilationUnit(newJavaInterface2)
			return #[
				javaPackage,
				javaCompilationUnit1,
				javaCompilationUnit2
			]
		]
	}

	override multipleInterfacesInDifferentPackagesCreation() {
		return newModel [
			val javaPackage1 = newJavaPackage1
			val javaPackage2 = newJavaPackage2
			val javaCompilationUnit1 = javaPackage1.newCompilationUnit(newJavaInterface1)
			val javaCompilationUnit2 = javaPackage2.newCompilationUnit(newJavaInterface2)
			return #[
				javaPackage1,
				javaPackage2,
				javaCompilationUnit1,
				javaCompilationUnit2
			]
		]
	}
}

package tools.vitruv.applications.cbs.commonalities.tests.oo.java

import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.modifiers.ModifiersFactory
import tools.vitruv.applications.cbs.commonalities.tests.oo.InterfaceTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase
import tools.vitruv.domains.java.util.JavaModificationUtil

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
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
		]
	}

	private static def newJavaInterface2() {
		return newJavaInterface1 => [
			name = INTERFACE_2_NAME
		]
	}

	private static def newJavaInterface3() {
		return newJavaInterface1 => [
			name = INTERFACE_3_NAME
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

	// Super interfaces

	override interfaceWithSuperInterfaceCreation() {
		return newModel [
			val javaPackage = newJavaPackage1
			val javaInterface2 = newJavaInterface2
			val javaCompilationUnit1 = javaPackage.newCompilationUnit(javaInterface2)
			val javaCompilationUnit2 = javaPackage.newCompilationUnit(newJavaInterface1 => [
				extends += JavaModificationUtil.createNamespaceClassifierReference(javaInterface2)
			])
			return #[
				javaPackage,
				javaCompilationUnit1,
				javaCompilationUnit2
			]
		]
	}

	override interfaceWithMultipleSuperInterfacesCreation() {
		return newModel [
			val javaPackage = newJavaPackage1
			val javaInterface2 = newJavaInterface2
			val javaInterface3 = newJavaInterface3
			val javaCompilationUnit1 = javaPackage.newCompilationUnit(javaInterface2)
			val javaCompilationUnit2 = javaPackage.newCompilationUnit(javaInterface3)
			val javaCompilationUnit3 = javaPackage.newCompilationUnit(newJavaInterface1 => [
				extends += JavaModificationUtil.createNamespaceClassifierReference(javaInterface2)
				extends += JavaModificationUtil.createNamespaceClassifierReference(javaInterface3)
			])
			return #[
				javaPackage,
				javaCompilationUnit1,
				javaCompilationUnit2,
				javaCompilationUnit3
			]
		]
	}
}

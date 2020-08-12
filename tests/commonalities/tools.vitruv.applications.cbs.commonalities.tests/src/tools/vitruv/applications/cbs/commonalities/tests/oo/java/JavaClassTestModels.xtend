package tools.vitruv.applications.cbs.commonalities.tests.oo.java

import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.ModifiersFactory
import tools.vitruv.applications.cbs.commonalities.tests.oo.ClassTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase
import tools.vitruv.domains.java.util.JavaModificationUtil

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.*

class JavaClassTestModels extends JavaTestModelsBase implements ClassTest.DomainModels {

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

	private static def newBasicJavaClass1() {
		return ClassifiersFactory.eINSTANCE.createClass => [
			name = CLASS_1_NAME
		]
	}

	private static def newJavaClass1() {
		return newBasicJavaClass1 => [
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic // Default visibility
		]
	}

	private static def newJavaClass2() {
		return newJavaClass1 => [
			name = CLASS_2_NAME
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

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	/**
	 * Returning <code>null</code> results in no visibility modifier being
	 * added and therefore package-private visibility.
	 */
	protected def Modifier defaultClassVisibility() {
		return null // package-private
	}

	private def withDefaultVisibility(Class javaClass) {
		return javaClass => [
			val defaultVisibility = defaultClassVisibility
			if (defaultVisibility !== null) {
				annotationsAndModifiers += defaultVisibility
			}
		]
	}

	// Empty class

	override emptyClassCreation() {
		return newModel [
			val javaPackage = newJavaPackage1
			val javaCompilationUnit = javaPackage.newCompilationUnit(newBasicJavaClass1 => [
				withDefaultVisibility
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	// Visibility

	override privateClassCreation() {
		return newModel [
			val javaPackage = newJavaPackage1
			val javaCompilationUnit = javaPackage.newCompilationUnit(newBasicJavaClass1 => [
				annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPrivate
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override publicClassCreation() {
		return newModel [
			val javaPackage = newJavaPackage1
			val javaCompilationUnit = javaPackage.newCompilationUnit(newBasicJavaClass1 => [
				annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override protectedClassCreation() {
		return newModel [
			val javaPackage = newJavaPackage1
			val javaCompilationUnit = javaPackage.newCompilationUnit(newBasicJavaClass1 => [
				annotationsAndModifiers += ModifiersFactory.eINSTANCE.createProtected
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override packagePrivateClassCreation() {
		return newModel [
			val javaPackage = newJavaPackage1
			// The created class has no modifiers and is therefore package-private.
			val javaCompilationUnit = javaPackage.newCompilationUnit(newBasicJavaClass1)
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	// Modifiers

	override finalClassCreation() {
		return newModel [
			val javaPackage = newJavaPackage1
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass1 => [
				annotationsAndModifiers += ModifiersFactory.eINSTANCE.createFinal
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override abstractClassCreation() {
		return newModel [
			val javaPackage = newJavaPackage1
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass1 => [
				annotationsAndModifiers += ModifiersFactory.eINSTANCE.createAbstract
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override classWithMultipleModifiersCreation() {
		return newModel [
			val javaPackage = newJavaPackage1
			val javaCompilationUnit = javaPackage.newCompilationUnit(newBasicJavaClass1 => [
				annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
				annotationsAndModifiers += ModifiersFactory.eINSTANCE.createFinal
				annotationsAndModifiers += ModifiersFactory.eINSTANCE.createAbstract
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	// Multiple classes

	override multipleClassesInSamePackageCreation() {
		return newModel [
			val javaPackage = newJavaPackage1
			val javaClass1 = newJavaClass1
			val javaClass2 = newJavaClass2
			val javaCompilationUnit1 = javaPackage.newCompilationUnit(javaClass1)
			val javaCompilationUnit2 = javaPackage.newCompilationUnit(javaClass2)
			return #[
				javaPackage,
				javaCompilationUnit1,
				javaCompilationUnit2
			]
		]
	}

	override multipleClassesInDifferentPackagesCreation() {
		return newModel [
			val javaPackage1 = newJavaPackage1
			val javaPackage2 = newJavaPackage2
			val javaClass1 = newJavaClass1
			val javaClass2 = newJavaClass2
			val javaCompilationUnit1 = javaPackage1.newCompilationUnit(javaClass1)
			val javaCompilationUnit2 = javaPackage2.newCompilationUnit(javaClass2)
			return #[
				javaPackage1,
				javaPackage2,
				javaCompilationUnit1,
				javaCompilationUnit2
			]
		]
	}

	// Super class

	override classWithSuperClassCreation() {
		return newModel [
			val javaPackage = newJavaPackage1
			val javaClass2 = newJavaClass2
			val javaClass1 = newJavaClass1 => [
				extends = JavaModificationUtil.createNamespaceClassifierReference(javaClass2)
			]
			val javaCompilationUnit1 = javaPackage.newCompilationUnit(javaClass2)
			val javaCompilationUnit2 = javaPackage.newCompilationUnit(javaClass1)
			return #[
				javaPackage,
				javaCompilationUnit1,
				javaCompilationUnit2
			]
		]
	}

	// Implemented interfaces

	override classImplementingInterfaceCreation() {
		return newModel [
			val javaPackage = newJavaPackage1
			val javaInterface1 = newJavaInterface1
			val javaClass1 = newJavaClass1 => [
				implements += JavaModificationUtil.createNamespaceClassifierReference(javaInterface1)
			]
			val javaCompilationUnit1 = javaPackage.newCompilationUnit(javaInterface1)
			val javaCompilationUnit2 = javaPackage.newCompilationUnit(javaClass1)
			return #[
				javaPackage,
				javaCompilationUnit1,
				javaCompilationUnit2
			]
		]
	}

	override classImplementingMultipleInterfacesCreation() {
		return newModel [
			val javaPackage = newJavaPackage1
			val javaInterface1 = newJavaInterface1
			val javaInterface2 = newJavaInterface2
			val javaClass1 = newJavaClass1 => [
				implements += JavaModificationUtil.createNamespaceClassifierReference(javaInterface1)
				implements += JavaModificationUtil.createNamespaceClassifierReference(javaInterface2)
			]
			val javaCompilationUnit1 = javaPackage.newCompilationUnit(javaInterface1)
			val javaCompilationUnit2 = javaPackage.newCompilationUnit(javaInterface2)
			val javaCompilationUnit3 = javaPackage.newCompilationUnit(javaClass1)
			return #[
				javaPackage,
				javaCompilationUnit1,
				javaCompilationUnit2,
				javaCompilationUnit3
			]
		]
	}
}

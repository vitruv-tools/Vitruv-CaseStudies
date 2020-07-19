package tools.vitruv.applications.cbs.commonalities.tests.oo.java

import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.types.TypesFactory
import tools.vitruv.applications.cbs.commonalities.tests.java.JavaTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.oo.AbstractClassPropertyTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.domains.java.util.JavaModificationUtil

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.*

class JavaClassPropertyTestModels extends JavaTestModelsBase implements AbstractClassPropertyTest.DomainModels {

	private static def newJavaPackage() {
		return ContainersFactory.eINSTANCE.createPackage => [
			name = PACKAGE_NAME
		]
	}

	private static def newJavaClass() {
		return ClassifiersFactory.eINSTANCE.createClass => [
			name = CLASS_NAME
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
		]
	}

	private static def newJavaField() {
		return MembersFactory.eINSTANCE.createField => [
			name = PROPERTY_NAME
		]
	}

	private static def newBasicPrimitiveJavaField() {
		return newJavaField => [
			typeReference = TypesFactory.eINSTANCE.createInt
		]
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	/**
	 * Returning <code>null</code> results in no visibility modifier being
	 * added and therefore package-private visibility.
	 */
	protected def Modifier defaultPropertyVisibility() {
		return null // package-private
	}

	private def withDefaultVisibility(Field javaField) {
		return javaField => [
			val defaultVisibility = defaultPropertyVisibility
			if (defaultVisibility !== null) {
				annotationsAndModifiers += defaultVisibility
			}
		]
	}

	// Basic

	override basicPrimitiveClassPropertyCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newBasicPrimitiveJavaField => [
					withDefaultVisibility
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	// Modifiers

	override privateClassPropertyCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newBasicPrimitiveJavaField => [
					annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPrivate
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override publicClassPropertyCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newBasicPrimitiveJavaField => [
					annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override protectedClassPropertyCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newBasicPrimitiveJavaField => [
					annotationsAndModifiers += ModifiersFactory.eINSTANCE.createProtected
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override packagePrivateClassPropertyCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				// The created field has no modifiers and is therefore package-private.
				members += newBasicPrimitiveJavaField
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override finalClassPropertyCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newBasicPrimitiveJavaField => [
					withDefaultVisibility
					annotationsAndModifiers += ModifiersFactory.eINSTANCE.createFinal
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override staticClassPropertyCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newBasicPrimitiveJavaField => [
					withDefaultVisibility
					annotationsAndModifiers += ModifiersFactory.eINSTANCE.createStatic
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override classPropertyWithMultipleModifiersCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newBasicPrimitiveJavaField => [
					annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPrivate
					annotationsAndModifiers += ModifiersFactory.eINSTANCE.createStatic
					annotationsAndModifiers += ModifiersFactory.eINSTANCE.createFinal
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	// Type references

	override stringClassPropertyCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaField => [
					withDefaultVisibility
					name = STRING_PROPERTY_NAME
					typeReference = JavaModificationUtil.createNamespaceClassifierReferenceForName(String.name, false)
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	// Multiple properties

	override multiplePrimitiveClassPropertiesCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaField => [
					withDefaultVisibility
					name = BOOLEAN_PROPERTY_NAME
					typeReference = TypesFactory.eINSTANCE.createBoolean
				]
				members += newJavaField => [
					withDefaultVisibility
					name = INT_PROPERTY_NAME
					typeReference = TypesFactory.eINSTANCE.createInt
				]
				members += newJavaField => [
					withDefaultVisibility
					name = DOUBLE_PROPERTY_NAME
					typeReference = TypesFactory.eINSTANCE.createDouble
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}
}

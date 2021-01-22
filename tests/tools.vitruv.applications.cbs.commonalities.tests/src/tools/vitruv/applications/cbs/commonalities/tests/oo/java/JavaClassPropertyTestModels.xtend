package tools.vitruv.applications.cbs.commonalities.tests.oo.java

import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.types.TypesFactory
import tools.vitruv.applications.cbs.commonalities.tests.oo.ClassPropertyTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase
import tools.vitruv.domains.java.util.JavaModificationUtil

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.*

class JavaClassPropertyTestModels extends JavaTestModelsBase implements ClassPropertyTest.DomainModels {

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

	private static def newBasicJavaField() {
		return MembersFactory.eINSTANCE.createField => [
			name = PROPERTY_NAME
			typeReference = TypesFactory.eINSTANCE.createInt // Default type
		]
	}

	private static def newJavaField() {
		return newBasicJavaField => [
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPrivate // Default visibility
		]
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	/**
	 * Returning <code>null</code> results in no visibility modifier being
	 * added and therefore package-private visibility.
	 */
	protected def Modifier defaultFieldVisibility() {
		return null // package-private
	}

	private def withDefaultVisibility(Field javaField) {
		return javaField => [
			val defaultVisibility = defaultFieldVisibility
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
				members += newBasicJavaField => [
					withDefaultVisibility
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	// Visibility

	override privateClassPropertyCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newBasicJavaField => [
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
				members += newBasicJavaField => [
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
				members += newBasicJavaField => [
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
				members += newBasicJavaField
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	// Modifiers

	override finalClassPropertyCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaField => [
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
				members += newJavaField => [
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
				members += newJavaField => [
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
					name = STRING_PROPERTY_NAME
					typeReference = JavaModificationUtil.createNamespaceClassifierReferenceForName(String.name)
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
					name = BOOLEAN_PROPERTY_NAME
					typeReference = TypesFactory.eINSTANCE.createBoolean
				]
				members += newJavaField => [
					name = INT_PROPERTY_NAME
					typeReference = TypesFactory.eINSTANCE.createInt
				]
				members += newJavaField => [
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

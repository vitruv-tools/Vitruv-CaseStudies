package tools.vitruv.applications.cbs.commonalities.tests.cbs.java

import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.types.TypesFactory
import tools.vitruv.applications.cbs.commonalities.tests.cbs.CompositeDataTypeTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase
import tools.vitruv.domains.java.util.JavaModificationUtil

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.*

class JavaCompositeDataTypeTestModels extends JavaTestModelsBase implements CompositeDataTypeTest.DomainModels {

	private static def newJavaCompositeDataTypeClass() {
		return ClassifiersFactory.eINSTANCE.createClass => [
			name = COMPOSITE_DATATYPE_1_NAME
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
		]
	}

	private static def newJavaElementField() {
		return MembersFactory.eINSTANCE.createField => [
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPrivate
		]
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	// Empty CompositeDataType

	override emptyCompositeDataTypeCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val datatypesPackage = javaRepositoryModel.datatypesPackage

			val datatypeClass = newJavaCompositeDataTypeClass
			val compilationUnit = datatypesPackage.newCompilationUnit(datatypeClass)

			return (javaRepositoryModel.rootObjects + #[
				compilationUnit
			]).toList
		]
	}

	// Primitive inner elements

	override compositeDataTypeWithBooleanElementCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val datatypesPackage = javaRepositoryModel.datatypesPackage

			val datatypeClass = newJavaCompositeDataTypeClass => [
				members += newJavaElementField => [
					name = BOOLEAN_ELEMENT_NAME
					typeReference = TypesFactory.eINSTANCE.createBoolean
				]
			]
			val compilationUnit = datatypesPackage.newCompilationUnit(datatypeClass)

			return (javaRepositoryModel.rootObjects + #[
				compilationUnit
			]).toList
		]
	}

	override compositeDataTypeWithIntegerElementCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val datatypesPackage = javaRepositoryModel.datatypesPackage

			val datatypeClass = newJavaCompositeDataTypeClass => [
				members += newJavaElementField => [
					name = INTEGER_ELEMENT_NAME
					typeReference = TypesFactory.eINSTANCE.createInt
				]
			]
			val compilationUnit = datatypesPackage.newCompilationUnit(datatypeClass)

			return (javaRepositoryModel.rootObjects + #[
				compilationUnit
			]).toList
		]
	}

	override compositeDataTypeWithDoubleElementCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val datatypesPackage = javaRepositoryModel.datatypesPackage

			val datatypeClass = newJavaCompositeDataTypeClass => [
				members += newJavaElementField => [
					name = DOUBLE_ELEMENT_NAME
					typeReference = TypesFactory.eINSTANCE.createDouble
				]
			]
			val compilationUnit = datatypesPackage.newCompilationUnit(datatypeClass)

			return (javaRepositoryModel.rootObjects + #[
				compilationUnit
			]).toList
		]
	}

	override compositeDataTypeWithStringElementCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val datatypesPackage = javaRepositoryModel.datatypesPackage

			val datatypeClass = newJavaCompositeDataTypeClass => [
				members += newJavaElementField => [
					name = STRING_ELEMENT_NAME
					typeReference = JavaModificationUtil.createNamespaceClassifierReferenceForName(String.name)
				]
			]
			val compilationUnit = datatypesPackage.newCompilationUnit(datatypeClass)

			return (javaRepositoryModel.rootObjects + #[
				compilationUnit
			]).toList
		]
	}

	override compositeDataTypeWithWithMultiplePrimitiveElementsCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val datatypesPackage = javaRepositoryModel.datatypesPackage

			val datatypeClass = newJavaCompositeDataTypeClass => [
				members += newJavaElementField => [
					name = BOOLEAN_ELEMENT_NAME
					typeReference = TypesFactory.eINSTANCE.createBoolean
				]
				members += newJavaElementField => [
					name = INTEGER_ELEMENT_NAME
					typeReference = TypesFactory.eINSTANCE.createInt
				]
				members += newJavaElementField => [
					name = DOUBLE_ELEMENT_NAME
					typeReference = TypesFactory.eINSTANCE.createDouble
				]
				members += newJavaElementField => [
					name = STRING_ELEMENT_NAME
					typeReference = JavaModificationUtil.createNamespaceClassifierReferenceForName(String.name)
				]
			]
			val compilationUnit = datatypesPackage.newCompilationUnit(datatypeClass)

			return (javaRepositoryModel.rootObjects + #[
				compilationUnit
			]).toList
		]
	}

	// Multiple CompositeDataTypes

	override multipleCompositeDataTypesWithPrimitiveElementsCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val datatypesPackage = javaRepositoryModel.datatypesPackage

			val datatype1Class = newJavaCompositeDataTypeClass => [
				members += newJavaElementField => [
					name = BOOLEAN_ELEMENT_NAME
					typeReference = TypesFactory.eINSTANCE.createBoolean
				]
			]
			val compilationUnit1 = datatypesPackage.newCompilationUnit(datatype1Class)

			val datatype2Class = newJavaCompositeDataTypeClass => [
				name = COMPOSITE_DATATYPE_2_NAME
				members += newJavaElementField => [
					name = INTEGER_ELEMENT_NAME
					typeReference = TypesFactory.eINSTANCE.createInt
				]
			]
			val compilationUnit2 = datatypesPackage.newCompilationUnit(datatype2Class)

			return (javaRepositoryModel.rootObjects + #[
				compilationUnit1,
				compilationUnit2
			]).toList
		]
	}

	override compositeDataTypeWithCompositeElementsCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val datatypesPackage = javaRepositoryModel.datatypesPackage

			val datatype1Class = newJavaCompositeDataTypeClass => [
				members += newJavaElementField => [
					name = INTEGER_ELEMENT_NAME
					typeReference = TypesFactory.eINSTANCE.createInt
				]
			]
			val compilationUnit1 = datatypesPackage.newCompilationUnit(datatype1Class)

			val datatype2Class = newJavaCompositeDataTypeClass => [
				name = COMPOSITE_DATATYPE_2_NAME
				members += newJavaElementField => [
					name = COMPOSITE_ELEMENT_1_NAME
					typeReference = JavaModificationUtil.createNamespaceClassifierReference(datatype1Class)
				]
				members += newJavaElementField => [
					name = COMPOSITE_ELEMENT_2_NAME
					typeReference = JavaModificationUtil.createNamespaceClassifierReference(datatype1Class)
				]
			]
			val compilationUnit2 = datatypesPackage.newCompilationUnit(datatype2Class)

			return (javaRepositoryModel.rootObjects + #[
				compilationUnit1,
				compilationUnit2
			]).toList
		]
	}
}

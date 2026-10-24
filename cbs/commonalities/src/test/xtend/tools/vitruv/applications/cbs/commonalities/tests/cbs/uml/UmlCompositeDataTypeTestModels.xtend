package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml

import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.cbs.commonalities.tests.cbs.CompositeDataTypeTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase
import tools.vitruv.applications.cbs.commonalities.uml.UmlPrimitiveType

class UmlCompositeDataTypeTestModels extends UmlTestModelsBase implements CompositeDataTypeTest.DomainModels {

	private static def newUmlCompositeDataTypeClass() {
		return UMLFactory.eINSTANCE.createClass => [
			name = COMPOSITE_DATATYPE_1_NAME
			visibility = VisibilityKind.PUBLIC_LITERAL
		]
	}

	private static def newUmlProperty() {
		return UMLFactory.eINSTANCE.createProperty => [
			visibility = VisibilityKind.PRIVATE_LITERAL
		]
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	// Empty CompositeDataType

	override emptyCompositeDataTypeCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.datatypesPackage => [
				packagedElements += newUmlCompositeDataTypeClass
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}

	// Primitive inner elements

	override compositeDataTypeWithBooleanElementCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.datatypesPackage => [
				packagedElements += newUmlCompositeDataTypeClass => [
					ownedAttributes += newUmlProperty => [
						name = BOOLEAN_ELEMENT_NAME
						type = UmlPrimitiveType.BOOLEAN.umlType
					]
				]
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}

	override compositeDataTypeWithIntegerElementCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.datatypesPackage => [
				packagedElements += newUmlCompositeDataTypeClass => [
					ownedAttributes += newUmlProperty => [
						name = INTEGER_ELEMENT_NAME
						type = UmlPrimitiveType.INTEGER.umlType
					]
				]
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}

	override compositeDataTypeWithDoubleElementCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.datatypesPackage => [
				packagedElements += newUmlCompositeDataTypeClass => [
					ownedAttributes += newUmlProperty => [
						name = DOUBLE_ELEMENT_NAME
						type = UmlPrimitiveType.REAL.umlType
					]
				]
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}

	override compositeDataTypeWithStringElementCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.datatypesPackage => [
				packagedElements += newUmlCompositeDataTypeClass => [
					ownedAttributes += newUmlProperty => [
						name = STRING_ELEMENT_NAME
						type = UmlPrimitiveType.STRING.umlType
					]
				]
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}

	override compositeDataTypeWithWithMultiplePrimitiveElementsCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.datatypesPackage => [
				packagedElements += newUmlCompositeDataTypeClass => [
					ownedAttributes += newUmlProperty => [
						name = BOOLEAN_ELEMENT_NAME
						type = UmlPrimitiveType.BOOLEAN.umlType
					]
					ownedAttributes += newUmlProperty => [
						name = INTEGER_ELEMENT_NAME
						type = UmlPrimitiveType.INTEGER.umlType
					]
					ownedAttributes += newUmlProperty => [
						name = DOUBLE_ELEMENT_NAME
						type = UmlPrimitiveType.REAL.umlType
					]
					ownedAttributes += newUmlProperty => [
						name = STRING_ELEMENT_NAME
						type = UmlPrimitiveType.STRING.umlType
					]
				]
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}

	// Multiple CompositeDataTypes

	override multipleCompositeDataTypesWithPrimitiveElementsCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.datatypesPackage => [
				packagedElements += newUmlCompositeDataTypeClass => [
					ownedAttributes += newUmlProperty => [
						name = BOOLEAN_ELEMENT_NAME
						type = UmlPrimitiveType.BOOLEAN.umlType
					]
				]
				packagedElements += newUmlCompositeDataTypeClass => [
					name = COMPOSITE_DATATYPE_2_NAME
					ownedAttributes += newUmlProperty => [
						name = INTEGER_ELEMENT_NAME
						type = UmlPrimitiveType.INTEGER.umlType
					]
				]
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}

	override compositeDataTypeWithCompositeElementsCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.datatypesPackage => [
				val datatype1Class = newUmlCompositeDataTypeClass => [
					ownedAttributes += newUmlProperty => [
						name = INTEGER_ELEMENT_NAME
						type = UmlPrimitiveType.INTEGER.umlType
					]
				]
				packagedElements += datatype1Class

				val datatype2Class = newUmlCompositeDataTypeClass => [
					name = COMPOSITE_DATATYPE_2_NAME
					ownedAttributes += newUmlProperty => [
						name = COMPOSITE_ELEMENT_1_NAME
						type = datatype1Class
					]
					ownedAttributes += newUmlProperty => [
						name = COMPOSITE_ELEMENT_2_NAME
						type = datatype1Class
					]
				]
				packagedElements += datatype2Class
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}
}

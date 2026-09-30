package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml;

import java.util.List;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.VisibilityKind;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.CompositeDataTypeTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase;
import tools.vitruv.applications.cbs.operators.uml.UmlPrimitiveType;

public class UmlCompositeDataTypeTestModels extends UmlTestModelsBase implements CompositeDataTypeTest.DomainModels {

	private static Class newUmlCompositeDataTypeClass() {
		Class compositeDataTypeClass = UMLFactory.eINSTANCE.createClass();
		compositeDataTypeClass.setName(COMPOSITE_DATATYPE_1_NAME);
		compositeDataTypeClass.setVisibility(VisibilityKind.PUBLIC_LITERAL);
		return compositeDataTypeClass;
	}

	private static Property newUmlProperty() {
		Property property = UMLFactory.eINSTANCE.createProperty();
		property.setVisibility(VisibilityKind.PRIVATE_LITERAL);
		return property;
	}

	private static Property newUmlProperty(String name, Type type) {
		Property property = newUmlProperty();
		property.setName(name);
		property.setType(type);
		return property;
	}

	public UmlCompositeDataTypeTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	// Empty CompositeDataType

	@Override
	public DomainModel emptyCompositeDataTypeCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();

			umlRepositoryModel.getDatatypesPackage().getPackagedElements().add(newUmlCompositeDataTypeClass());

			return List.of(umlRepositoryModel.getModel());
		});
	}

	// Primitive inner elements

	@Override
	public DomainModel compositeDataTypeWithBooleanElementCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();

			Class datatypeClass = newUmlCompositeDataTypeClass();
			datatypeClass.getOwnedAttributes()
					.add(newUmlProperty(BOOLEAN_ELEMENT_NAME, UmlPrimitiveType.BOOLEAN.getUmlType()));
			umlRepositoryModel.getDatatypesPackage().getPackagedElements().add(datatypeClass);

			return List.of(umlRepositoryModel.getModel());
		});
	}

	@Override
	public DomainModel compositeDataTypeWithIntegerElementCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();

			Class datatypeClass = newUmlCompositeDataTypeClass();
			datatypeClass.getOwnedAttributes()
					.add(newUmlProperty(INTEGER_ELEMENT_NAME, UmlPrimitiveType.INTEGER.getUmlType()));
			umlRepositoryModel.getDatatypesPackage().getPackagedElements().add(datatypeClass);

			return List.of(umlRepositoryModel.getModel());
		});
	}

	@Override
	public DomainModel compositeDataTypeWithDoubleElementCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();

			Class datatypeClass = newUmlCompositeDataTypeClass();
			datatypeClass.getOwnedAttributes()
					.add(newUmlProperty(DOUBLE_ELEMENT_NAME, UmlPrimitiveType.REAL.getUmlType()));
			umlRepositoryModel.getDatatypesPackage().getPackagedElements().add(datatypeClass);

			return List.of(umlRepositoryModel.getModel());
		});
	}

	@Override
	public DomainModel compositeDataTypeWithStringElementCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();

			Class datatypeClass = newUmlCompositeDataTypeClass();
			datatypeClass.getOwnedAttributes()
					.add(newUmlProperty(STRING_ELEMENT_NAME, UmlPrimitiveType.STRING.getUmlType()));
			umlRepositoryModel.getDatatypesPackage().getPackagedElements().add(datatypeClass);

			return List.of(umlRepositoryModel.getModel());
		});
	}

	@Override
	public DomainModel compositeDataTypeWithWithMultiplePrimitiveElementsCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();

			Class datatypeClass = newUmlCompositeDataTypeClass();
			datatypeClass.getOwnedAttributes()
					.add(newUmlProperty(BOOLEAN_ELEMENT_NAME, UmlPrimitiveType.BOOLEAN.getUmlType()));
			datatypeClass.getOwnedAttributes()
					.add(newUmlProperty(INTEGER_ELEMENT_NAME, UmlPrimitiveType.INTEGER.getUmlType()));
			datatypeClass.getOwnedAttributes()
					.add(newUmlProperty(DOUBLE_ELEMENT_NAME, UmlPrimitiveType.REAL.getUmlType()));
			datatypeClass.getOwnedAttributes()
					.add(newUmlProperty(STRING_ELEMENT_NAME, UmlPrimitiveType.STRING.getUmlType()));
			umlRepositoryModel.getDatatypesPackage().getPackagedElements().add(datatypeClass);

			return List.of(umlRepositoryModel.getModel());
		});
	}

	// Multiple CompositeDataTypes

	@Override
	public DomainModel multipleCompositeDataTypesWithPrimitiveElementsCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();

			Class datatype1Class = newUmlCompositeDataTypeClass();
			datatype1Class.getOwnedAttributes()
					.add(newUmlProperty(BOOLEAN_ELEMENT_NAME, UmlPrimitiveType.BOOLEAN.getUmlType()));
			umlRepositoryModel.getDatatypesPackage().getPackagedElements().add(datatype1Class);

			Class datatype2Class = newUmlCompositeDataTypeClass();
			datatype2Class.setName(COMPOSITE_DATATYPE_2_NAME);
			datatype2Class.getOwnedAttributes()
					.add(newUmlProperty(INTEGER_ELEMENT_NAME, UmlPrimitiveType.INTEGER.getUmlType()));
			umlRepositoryModel.getDatatypesPackage().getPackagedElements().add(datatype2Class);

			return List.of(umlRepositoryModel.getModel());
		});
	}

	@Override
	public DomainModel compositeDataTypeWithCompositeElementsCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();

			Class datatype1Class = newUmlCompositeDataTypeClass();
			datatype1Class.getOwnedAttributes()
					.add(newUmlProperty(INTEGER_ELEMENT_NAME, UmlPrimitiveType.INTEGER.getUmlType()));
			umlRepositoryModel.getDatatypesPackage().getPackagedElements().add(datatype1Class);

			Class datatype2Class = newUmlCompositeDataTypeClass();
			datatype2Class.setName(COMPOSITE_DATATYPE_2_NAME);
			datatype2Class.getOwnedAttributes().add(newUmlProperty(COMPOSITE_ELEMENT_1_NAME, datatype1Class));
			datatype2Class.getOwnedAttributes().add(newUmlProperty(COMPOSITE_ELEMENT_2_NAME, datatype1Class));
			umlRepositoryModel.getDatatypesPackage().getPackagedElements().add(datatype2Class);

			return List.of(umlRepositoryModel.getModel());
		});
	}
}

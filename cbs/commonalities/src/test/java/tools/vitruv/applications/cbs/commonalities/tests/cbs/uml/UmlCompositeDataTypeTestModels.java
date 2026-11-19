package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
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

    private static Class newUmlCompositeDataTypeClass(String name, Property... properties) {
        Class compositeDataTypeClass = newUmlCompositeDataTypeClass();
        compositeDataTypeClass.setName(name);
        compositeDataTypeClass.getOwnedAttributes().addAll(List.of(properties));
        return compositeDataTypeClass;
    }

    private static Property newUmlProperty(String name, Type type) {
        Property property = UMLFactory.eINSTANCE.createProperty();
        property.setVisibility(VisibilityKind.PRIVATE_LITERAL);
        property.setName(name);
        property.setType(type);
        return property;
    }

    /**
     * The UML repository model whose datatypes package contains the given
     * classes.
     */
    private static List<EObject> repositoryWithDatatypes(Class... compositeDataTypeClasses) {
        UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();
        umlRepositoryModel.getDatatypesPackage().getPackagedElements().addAll(List.of(compositeDataTypeClasses));
        return List.of(umlRepositoryModel.getModel());
    }

    public UmlCompositeDataTypeTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    // Empty CompositeDataType

    @Override
    public DomainModel emptyCompositeDataTypeCreation() {
        return newModel(() -> repositoryWithDatatypes(newUmlCompositeDataTypeClass()));
    }

    // Primitive inner elements

    @Override
    public DomainModel compositeDataTypeWithBooleanElementCreation() {
        return newModel(() -> repositoryWithDatatypes(newUmlCompositeDataTypeClass(COMPOSITE_DATATYPE_1_NAME,
                newUmlProperty(BOOLEAN_ELEMENT_NAME, UmlPrimitiveType.BOOLEAN.getUmlType()))));
    }

    @Override
    public DomainModel compositeDataTypeWithIntegerElementCreation() {
        return newModel(() -> repositoryWithDatatypes(newUmlCompositeDataTypeClass(COMPOSITE_DATATYPE_1_NAME,
                newUmlProperty(INTEGER_ELEMENT_NAME, UmlPrimitiveType.INTEGER.getUmlType()))));
    }

    @Override
    public DomainModel compositeDataTypeWithDoubleElementCreation() {
        return newModel(() -> repositoryWithDatatypes(newUmlCompositeDataTypeClass(COMPOSITE_DATATYPE_1_NAME,
                newUmlProperty(DOUBLE_ELEMENT_NAME, UmlPrimitiveType.REAL.getUmlType()))));
    }

    @Override
    public DomainModel compositeDataTypeWithStringElementCreation() {
        return newModel(() -> repositoryWithDatatypes(newUmlCompositeDataTypeClass(COMPOSITE_DATATYPE_1_NAME,
                newUmlProperty(STRING_ELEMENT_NAME, UmlPrimitiveType.STRING.getUmlType()))));
    }

    @Override
    public DomainModel compositeDataTypeWithWithMultiplePrimitiveElementsCreation() {
        return newModel(() -> repositoryWithDatatypes(newUmlCompositeDataTypeClass(COMPOSITE_DATATYPE_1_NAME,
                newUmlProperty(BOOLEAN_ELEMENT_NAME, UmlPrimitiveType.BOOLEAN.getUmlType()),
                newUmlProperty(INTEGER_ELEMENT_NAME, UmlPrimitiveType.INTEGER.getUmlType()),
                newUmlProperty(DOUBLE_ELEMENT_NAME, UmlPrimitiveType.REAL.getUmlType()),
                newUmlProperty(STRING_ELEMENT_NAME, UmlPrimitiveType.STRING.getUmlType()))));
    }

    // Multiple CompositeDataTypes

    @Override
    public DomainModel multipleCompositeDataTypesWithPrimitiveElementsCreation() {
        return newModel(() -> repositoryWithDatatypes(
                newUmlCompositeDataTypeClass(COMPOSITE_DATATYPE_1_NAME,
                        newUmlProperty(BOOLEAN_ELEMENT_NAME, UmlPrimitiveType.BOOLEAN.getUmlType())),
                newUmlCompositeDataTypeClass(COMPOSITE_DATATYPE_2_NAME,
                        newUmlProperty(INTEGER_ELEMENT_NAME, UmlPrimitiveType.INTEGER.getUmlType()))));
    }

    @Override
    public DomainModel compositeDataTypeWithCompositeElementsCreation() {
        return newModel(() -> {
            Class datatype1Class = newUmlCompositeDataTypeClass(COMPOSITE_DATATYPE_1_NAME,
                    newUmlProperty(INTEGER_ELEMENT_NAME, UmlPrimitiveType.INTEGER.getUmlType()));
            Class datatype2Class = newUmlCompositeDataTypeClass(COMPOSITE_DATATYPE_2_NAME,
                    newUmlProperty(COMPOSITE_ELEMENT_1_NAME, datatype1Class),
                    newUmlProperty(COMPOSITE_ELEMENT_2_NAME, datatype1Class));
            return repositoryWithDatatypes(datatype1Class, datatype2Class);
        });
    }
}

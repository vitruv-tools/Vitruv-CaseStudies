package tools.vitruv.applications.cbs.commonalities.tests.oo.uml;

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.newUmlModel;
import static tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlModelHelper.withElements;

import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.VisibilityKind;

import tools.vitruv.applications.cbs.commonalities.tests.oo.ClassPropertyTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase;
import tools.vitruv.applications.cbs.operators.uml.UmlPrimitiveType;

public class UmlClassPropertyTestModels extends UmlTestModelsBase implements ClassPropertyTest.DomainModels {

    private static Package newUmlPackage() {
        Package umlPackage = UMLFactory.eINSTANCE.createPackage();
        umlPackage.setName(PACKAGE_NAME);
        return umlPackage;
    }

    private static Class newUmlClass() {
        Class umlClass = UMLFactory.eINSTANCE.createClass();
        umlClass.setName(CLASS_NAME);
        umlClass.setVisibility(VisibilityKind.PUBLIC_LITERAL);
        return umlClass;
    }

    private static Property newBasicUmlProperty() {
        Property umlProperty = UMLFactory.eINSTANCE.createProperty();
        umlProperty.setName(PROPERTY_NAME);
        umlProperty.setType(UmlPrimitiveType.INTEGER.getUmlType()); // Default type
        return umlProperty;
    }

    private static Property newUmlProperty() {
        Property umlProperty = newBasicUmlProperty();
        umlProperty.setVisibility(VisibilityKind.PRIVATE_LITERAL); // Default visibility
        return umlProperty;
    }

    private static Property configured(Property umlProperty, Consumer<Property> configuration) {
        configuration.accept(umlProperty);
        return umlProperty;
    }

    private static Property newUmlProperty(String name, Type type) {
        return configured(newUmlProperty(), umlProperty -> {
            umlProperty.setName(name);
            umlProperty.setType(type);
        });
    }

    /**
     * The UML model with a class containing the given properties.
     */
    private static List<EObject> classWithProperties(Property... umlProperties) {
        Class umlClass = newUmlClass();
        umlClass.getOwnedAttributes().addAll(List.of(umlProperties));
        return List.of(withElements(newUmlModel(), withElements(newUmlPackage(), umlClass)));
    }

    public UmlClassPropertyTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    /**
     * Returning <code>null</code> results in no visibility being set.
     */
    protected VisibilityKind defaultPropertyVisibility() {
        return null;
    }

    private Property withDefaultVisibility(Property umlProperty) {
        VisibilityKind defaultVisibility = defaultPropertyVisibility();
        if (defaultVisibility != null) {
            umlProperty.setVisibility(defaultVisibility);
        }
        return umlProperty;
    }

    // Basic

    @Override
    public DomainModel basicPrimitiveClassPropertyCreation() {
        return newModel(() -> classWithProperties(withDefaultVisibility(newBasicUmlProperty())));
    }

    // Visibility

    @Override
    public DomainModel privateClassPropertyCreation() {
        return newModel(() -> classWithProperties(
                configured(newBasicUmlProperty(), p -> p.setVisibility(VisibilityKind.PRIVATE_LITERAL))));
    }

    @Override
    public DomainModel publicClassPropertyCreation() {
        return newModel(() -> classWithProperties(
                configured(newBasicUmlProperty(), p -> p.setVisibility(VisibilityKind.PUBLIC_LITERAL))));
    }

    @Override
    public DomainModel protectedClassPropertyCreation() {
        return newModel(() -> classWithProperties(
                configured(newBasicUmlProperty(), p -> p.setVisibility(VisibilityKind.PROTECTED_LITERAL))));
    }

    @Override
    public DomainModel packagePrivateClassPropertyCreation() {
        return newModel(() -> classWithProperties(
                configured(newUmlProperty(), p -> p.setVisibility(VisibilityKind.PACKAGE_LITERAL))));
    }

    // Modifiers

    @Override
    public DomainModel finalClassPropertyCreation() {
        return newModel(() -> classWithProperties(configured(newUmlProperty(), p -> p.setIsReadOnly(true))));
    }

    @Override
    public DomainModel staticClassPropertyCreation() {
        return newModel(() -> classWithProperties(configured(newUmlProperty(), p -> p.setIsStatic(true))));
    }

    @Override
    public DomainModel classPropertyWithMultipleModifiersCreation() {
        return newModel(() -> classWithProperties(configured(newUmlProperty(), p -> {
            p.setIsStatic(true);
            p.setIsReadOnly(true);
        })));
    }

    // Type references

    @Override
    public DomainModel stringClassPropertyCreation() {
        return newModel(() -> classWithProperties(
                newUmlProperty(STRING_PROPERTY_NAME, UmlPrimitiveType.STRING.getUmlType())));
    }

    // Multiple properties

    @Override
    public DomainModel multiplePrimitiveClassPropertiesCreation() {
        return newModel(() -> classWithProperties(
                newUmlProperty(BOOLEAN_PROPERTY_NAME, UmlPrimitiveType.BOOLEAN.getUmlType()),
                newUmlProperty(INT_PROPERTY_NAME, UmlPrimitiveType.INTEGER.getUmlType()),
                newUmlProperty(DOUBLE_PROPERTY_NAME, UmlPrimitiveType.REAL.getUmlType())));
    }
}

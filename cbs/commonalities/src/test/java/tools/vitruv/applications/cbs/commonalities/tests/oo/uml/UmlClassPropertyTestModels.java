package tools.vitruv.applications.cbs.commonalities.tests.oo.uml;

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.newUmlModel;
import static tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlModelHelper.withElements;

import java.util.List;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
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

	private static Model newUmlModelWithClassProperties(Property... umlProperties) {
		Class umlClass = newUmlClass();
		umlClass.getOwnedAttributes().addAll(List.of(umlProperties));
		return withElements(newUmlModel(), withElements(newUmlPackage(), umlClass));
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
		return newModel(() -> List.of(newUmlModelWithClassProperties(withDefaultVisibility(newBasicUmlProperty()))));
	}

	// Visibility

	@Override
	public DomainModel privateClassPropertyCreation() {
		return newModel(() -> {
			Property umlProperty = newBasicUmlProperty();
			umlProperty.setVisibility(VisibilityKind.PRIVATE_LITERAL);
			return List.of(newUmlModelWithClassProperties(umlProperty));
		});
	}

	@Override
	public DomainModel publicClassPropertyCreation() {
		return newModel(() -> {
			Property umlProperty = newBasicUmlProperty();
			umlProperty.setVisibility(VisibilityKind.PUBLIC_LITERAL);
			return List.of(newUmlModelWithClassProperties(umlProperty));
		});
	}

	@Override
	public DomainModel protectedClassPropertyCreation() {
		return newModel(() -> {
			Property umlProperty = newBasicUmlProperty();
			umlProperty.setVisibility(VisibilityKind.PROTECTED_LITERAL);
			return List.of(newUmlModelWithClassProperties(umlProperty));
		});
	}

	@Override
	public DomainModel packagePrivateClassPropertyCreation() {
		return newModel(() -> {
			Property umlProperty = newUmlProperty();
			umlProperty.setVisibility(VisibilityKind.PACKAGE_LITERAL);
			return List.of(newUmlModelWithClassProperties(umlProperty));
		});
	}

	// Modifiers

	@Override
	public DomainModel finalClassPropertyCreation() {
		return newModel(() -> {
			Property umlProperty = newUmlProperty();
			umlProperty.setIsReadOnly(true);
			return List.of(newUmlModelWithClassProperties(umlProperty));
		});
	}

	@Override
	public DomainModel staticClassPropertyCreation() {
		return newModel(() -> {
			Property umlProperty = newUmlProperty();
			umlProperty.setIsStatic(true);
			return List.of(newUmlModelWithClassProperties(umlProperty));
		});
	}

	@Override
	public DomainModel classPropertyWithMultipleModifiersCreation() {
		return newModel(() -> {
			Property umlProperty = newUmlProperty();
			umlProperty.setIsStatic(true);
			umlProperty.setIsReadOnly(true);
			return List.of(newUmlModelWithClassProperties(umlProperty));
		});
	}

	// Type references

	@Override
	public DomainModel stringClassPropertyCreation() {
		return newModel(() -> {
			Property umlProperty = newUmlProperty();
			umlProperty.setName(STRING_PROPERTY_NAME);
			umlProperty.setType(UmlPrimitiveType.STRING.getUmlType());
			return List.of(newUmlModelWithClassProperties(umlProperty));
		});
	}

	// Multiple properties

	@Override
	public DomainModel multiplePrimitiveClassPropertiesCreation() {
		return newModel(() -> {
			Property booleanProperty = newUmlProperty();
			booleanProperty.setName(BOOLEAN_PROPERTY_NAME);
			booleanProperty.setType(UmlPrimitiveType.BOOLEAN.getUmlType());
			Property intProperty = newUmlProperty();
			intProperty.setName(INT_PROPERTY_NAME);
			intProperty.setType(UmlPrimitiveType.INTEGER.getUmlType());
			Property doubleProperty = newUmlProperty();
			doubleProperty.setName(DOUBLE_PROPERTY_NAME);
			doubleProperty.setType(UmlPrimitiveType.REAL.getUmlType());
			return List.of(newUmlModelWithClassProperties(booleanProperty, intProperty, doubleProperty));
		});
	}
}

package tools.vitruv.applications.cbs.commonalities.tests.oo.uml;

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.newUmlModel;
import static tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlModelHelper.withElements;

import java.util.List;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.VisibilityKind;
import tools.vitruv.applications.cbs.commonalities.tests.oo.ConstructorTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase;
import tools.vitruv.applications.cbs.operators.uml.UmlPrimitiveType;

public class UmlConstructorTestModels extends UmlTestModelsBase implements ConstructorTest.DomainModels {

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

	private static Operation newUmlConstructor() {
		Operation umlConstructor = UMLFactory.eINSTANCE.createOperation();
		// Constructor: Operation with the same name as the containing class and no return parameter.
		umlConstructor.setName(CLASS_NAME);
		umlConstructor.setVisibility(VisibilityKind.PUBLIC_LITERAL);
		return umlConstructor;
	}

	private static Operation newUmlConstructor(Parameter... umlParameters) {
		Operation umlConstructor = newUmlConstructor();
		umlConstructor.getOwnedParameters().addAll(List.of(umlParameters));
		return umlConstructor;
	}

	private static Operation newUmlConstructor(VisibilityKind visibility) {
		Operation umlConstructor = newUmlConstructor();
		umlConstructor.setVisibility(visibility);
		return umlConstructor;
	}

	private static Class newOtherUmlClass() {
		Class umlClass = UMLFactory.eINSTANCE.createClass();
		umlClass.setName(OTHER_CLASS_NAME);
		umlClass.setVisibility(VisibilityKind.PUBLIC_LITERAL);
		return umlClass;
	}

	private static Parameter newUmlInputParameter() {
		Parameter umlParameter = UMLFactory.eINSTANCE.createParameter();
		umlParameter.setDirection(ParameterDirectionKind.IN_LITERAL);
		return umlParameter;
	}

	private static Parameter newUmlInputParameter(String name, Type type) {
		Parameter umlParameter = newUmlInputParameter();
		umlParameter.setName(name);
		umlParameter.setType(type);
		return umlParameter;
	}

	private static Class withOperations(Class umlClass, Operation... umlOperations) {
		umlClass.getOwnedOperations().addAll(List.of(umlOperations));
		return umlClass;
	}

	private static Model newUmlModelWithPackageElements(PackageableElement... umlPackageableElements) {
		return withElements(newUmlModel(), withElements(newUmlPackage(), umlPackageableElements));
	}

	public UmlConstructorTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	// Basic

	@Override
	public DomainModel basicConstructorCreation() {
		return newModel(() -> List.of(newUmlModelWithPackageElements(
				withOperations(newUmlClass(), newUmlConstructor()))));
	}

	// Visibility

	@Override
	public DomainModel publicConstructorCreation() {
		return newModel(() -> List.of(newUmlModelWithPackageElements(
				withOperations(newUmlClass(), newUmlConstructor(VisibilityKind.PUBLIC_LITERAL)))));
	}

	@Override
	public DomainModel protectedConstructorCreation() {
		return newModel(() -> List.of(newUmlModelWithPackageElements(
				withOperations(newUmlClass(), newUmlConstructor(VisibilityKind.PROTECTED_LITERAL)))));
	}

	@Override
	public DomainModel packagePrivateConstructorCreation() {
		return newModel(() -> List.of(newUmlModelWithPackageElements(
				withOperations(newUmlClass(), newUmlConstructor(VisibilityKind.PACKAGE_LITERAL)))));
	}

	@Override
	public DomainModel privateConstructorCreation() {
		return newModel(() -> List.of(newUmlModelWithPackageElements(
				withOperations(newUmlClass(), newUmlConstructor(VisibilityKind.PRIVATE_LITERAL)))));
	}

	// Input parameters

	@Override
	public DomainModel constructorWithIntegerInputCreation() {
		return newModel(() -> List.of(newUmlModelWithPackageElements(
				withOperations(newUmlClass(), newUmlConstructor(
						newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()))))));
	}

	@Override
	public DomainModel constructorWithMultiplePrimitiveInputsCreation() {
		return newModel(() -> List.of(newUmlModelWithPackageElements(
				withOperations(newUmlClass(), newUmlConstructor(
						newUmlInputParameter(BOOLEAN_PARAMETER_NAME, UmlPrimitiveType.BOOLEAN.getUmlType()),
						newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()),
						newUmlInputParameter(DOUBLE_PARAMETER_NAME, UmlPrimitiveType.REAL.getUmlType()))))));
	}

	@Override
	public DomainModel constructorWithStringInputCreation() {
		return newModel(() -> List.of(newUmlModelWithPackageElements(
				withOperations(newUmlClass(), newUmlConstructor(
						newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType()))))));
	}

	@Override
	public DomainModel constructorWithClassInputCreation() {
		return newModel(() -> {
			Class otherUmlClass = newOtherUmlClass();
			Model umlModel = newUmlModelWithPackageElements(otherUmlClass,
					withOperations(newUmlClass(), newUmlConstructor(
							newUmlInputParameter(CLASS_PARAMETER_NAME, otherUmlClass))));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel constructorWithSelfInputCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass();
			Model umlModel = newUmlModelWithPackageElements(
					withOperations(umlClass, newUmlConstructor(
							newUmlInputParameter(OWN_TYPE_PARAMETER_NAME, umlClass))));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel constructorWithMixedInputsCreation() {
		return newModel(() -> {
			Class otherUmlClass = newOtherUmlClass();
			Model umlModel = newUmlModelWithPackageElements(otherUmlClass,
					withOperations(newUmlClass(), newUmlConstructor(
							newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()),
							newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType()),
							newUmlInputParameter(CLASS_PARAMETER_NAME, otherUmlClass))));
			return List.of(umlModel);
		});
	}

	// Multiple methods

	@Override
	public DomainModel multipleConstructorsCreation() {
		return newModel(() -> List.of(newUmlModelWithPackageElements(
				withOperations(newUmlClass(),
						newUmlConstructor(
								newUmlInputParameter(BOOLEAN_PARAMETER_NAME, UmlPrimitiveType.BOOLEAN.getUmlType())),
						newUmlConstructor(
								newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType()))))));
	}
}

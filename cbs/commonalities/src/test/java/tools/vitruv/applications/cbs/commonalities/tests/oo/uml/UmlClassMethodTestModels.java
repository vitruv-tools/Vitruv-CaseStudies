package tools.vitruv.applications.cbs.commonalities.tests.oo.uml;

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.newUmlModel;
import static tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlModelHelper.withElements;

import java.util.List;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.VisibilityKind;
import tools.vitruv.applications.cbs.commonalities.tests.oo.ClassMethodTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase;
import tools.vitruv.applications.cbs.commonalities.uml.UmlPrimitiveType;

public class UmlClassMethodTestModels extends UmlTestModelsBase implements ClassMethodTest.DomainModels {

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

	private static Operation newUmlOperation() {
		Operation umlOperation = UMLFactory.eINSTANCE.createOperation();
		umlOperation.setName(METHOD_NAME);
		umlOperation.setVisibility(VisibilityKind.PUBLIC_LITERAL);
		return umlOperation;
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

	private static Parameter newUmlReturnParameter() {
		Parameter umlParameter = UMLFactory.eINSTANCE.createParameter();
		umlParameter.setDirection(ParameterDirectionKind.RETURN_LITERAL);
		return umlParameter;
	}

	public UmlClassMethodTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	// Basic

	@Override
	public DomainModel basicClassMethodCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass();
			umlClass.getOwnedOperations().add(newUmlOperation());
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlClass));
			return List.of(umlModel);
		});
	}

	// Visibility

	@Override
	public DomainModel publicClassMethodCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			umlOperation.setVisibility(VisibilityKind.PUBLIC_LITERAL);
			umlClass.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlClass));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel protectedClassMethodCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			umlOperation.setVisibility(VisibilityKind.PROTECTED_LITERAL);
			umlClass.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlClass));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel packagePrivateClassMethodCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			umlOperation.setVisibility(VisibilityKind.PACKAGE_LITERAL);
			umlClass.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlClass));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel privateClassMethodCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			umlOperation.setVisibility(VisibilityKind.PRIVATE_LITERAL);
			umlClass.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlClass));
			return List.of(umlModel);
		});
	}

	// Modifiers

	@Override
	public DomainModel finalClassMethodCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			umlOperation.setIsLeaf(true);
			umlClass.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlClass));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel abstractClassMethodCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			umlOperation.setIsAbstract(true);
			umlClass.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlClass));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel staticClassMethodCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			umlOperation.setIsStatic(true);
			umlClass.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlClass));
			return List.of(umlModel);
		});
	}

	// Return type

	@Override
	public DomainModel classMethodWithIntegerReturnCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			Parameter returnParameter = newUmlReturnParameter();
			returnParameter.setType(UmlPrimitiveType.INTEGER.getUmlType());
			umlOperation.getOwnedParameters().add(returnParameter);
			umlClass.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlClass));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel classMethodWithStringReturnCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			Parameter returnParameter = newUmlReturnParameter();
			returnParameter.setType(UmlPrimitiveType.STRING.getUmlType());
			umlOperation.getOwnedParameters().add(returnParameter);
			umlClass.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlClass));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel classMethodWithClassReturnCreation() {
		return newModel(() -> {
			Class otherUmlClass = newOtherUmlClass();
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			Parameter returnParameter = newUmlReturnParameter();
			returnParameter.setType(otherUmlClass);
			umlOperation.getOwnedParameters().add(returnParameter);
			umlClass.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), otherUmlClass, umlClass));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel classMethodWithSelfReturnCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			Parameter returnParameter = newUmlReturnParameter();
			returnParameter.setType(umlClass);
			umlOperation.getOwnedParameters().add(returnParameter);
			umlClass.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlClass));
			return List.of(umlModel);
		});
	}

	// Input parameters

	@Override
	public DomainModel classMethodWithIntegerInputCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			Parameter integerParameter = newUmlInputParameter();
			integerParameter.setName(INTEGER_PARAMETER_NAME);
			integerParameter.setType(UmlPrimitiveType.INTEGER.getUmlType());
			umlOperation.getOwnedParameters().add(integerParameter);
			umlClass.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlClass));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel classMethodWithMultiplePrimitiveInputsCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			Parameter booleanParameter = newUmlInputParameter();
			booleanParameter.setName(BOOLEAN_PARAMETER_NAME);
			booleanParameter.setType(UmlPrimitiveType.BOOLEAN.getUmlType());
			umlOperation.getOwnedParameters().add(booleanParameter);
			Parameter integerParameter = newUmlInputParameter();
			integerParameter.setName(INTEGER_PARAMETER_NAME);
			integerParameter.setType(UmlPrimitiveType.INTEGER.getUmlType());
			umlOperation.getOwnedParameters().add(integerParameter);
			Parameter doubleParameter = newUmlInputParameter();
			doubleParameter.setName(DOUBLE_PARAMETER_NAME);
			doubleParameter.setType(UmlPrimitiveType.REAL.getUmlType());
			umlOperation.getOwnedParameters().add(doubleParameter);
			umlClass.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlClass));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel classMethodWithStringInputCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			Parameter stringParameter = newUmlInputParameter();
			stringParameter.setName(STRING_PARAMETER_NAME);
			stringParameter.setType(UmlPrimitiveType.STRING.getUmlType());
			umlOperation.getOwnedParameters().add(stringParameter);
			umlClass.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlClass));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel classMethodWithClassInputCreation() {
		return newModel(() -> {
			Class otherUmlClass = newOtherUmlClass();
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			Parameter classParameter = newUmlInputParameter();
			classParameter.setName(CLASS_PARAMETER_NAME);
			classParameter.setType(otherUmlClass);
			umlOperation.getOwnedParameters().add(classParameter);
			umlClass.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), otherUmlClass, umlClass));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel classMethodWithSelfInputCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			Parameter ownTypeParameter = newUmlInputParameter();
			ownTypeParameter.setName(OWN_TYPE_PARAMETER_NAME);
			ownTypeParameter.setType(umlClass);
			umlOperation.getOwnedParameters().add(ownTypeParameter);
			umlClass.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlClass));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel classMethodWithMixedInputsCreation() {
		return newModel(() -> {
			Class otherUmlClass = newOtherUmlClass();
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			Parameter integerParameter = newUmlInputParameter();
			integerParameter.setName(INTEGER_PARAMETER_NAME);
			integerParameter.setType(UmlPrimitiveType.INTEGER.getUmlType());
			umlOperation.getOwnedParameters().add(integerParameter);
			Parameter stringParameter = newUmlInputParameter();
			stringParameter.setName(STRING_PARAMETER_NAME);
			stringParameter.setType(UmlPrimitiveType.STRING.getUmlType());
			umlOperation.getOwnedParameters().add(stringParameter);
			Parameter classParameter = newUmlInputParameter();
			classParameter.setName(CLASS_PARAMETER_NAME);
			classParameter.setType(otherUmlClass);
			umlOperation.getOwnedParameters().add(classParameter);
			umlClass.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), otherUmlClass, umlClass));
			return List.of(umlModel);
		});
	}

	// Mixed input and return types

	@Override
	public DomainModel classMethodWithMixedInputsAndReturnCreation() {
		return newModel(() -> {
			Class otherUmlClass = newOtherUmlClass();
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			Parameter returnParameter = newUmlReturnParameter();
			returnParameter.setType(UmlPrimitiveType.INTEGER.getUmlType());
			umlOperation.getOwnedParameters().add(returnParameter);
			Parameter integerParameter = newUmlInputParameter();
			integerParameter.setName(INTEGER_PARAMETER_NAME);
			integerParameter.setType(UmlPrimitiveType.INTEGER.getUmlType());
			umlOperation.getOwnedParameters().add(integerParameter);
			Parameter stringParameter = newUmlInputParameter();
			stringParameter.setName(STRING_PARAMETER_NAME);
			stringParameter.setType(UmlPrimitiveType.STRING.getUmlType());
			umlOperation.getOwnedParameters().add(stringParameter);
			Parameter classParameter = newUmlInputParameter();
			classParameter.setName(CLASS_PARAMETER_NAME);
			classParameter.setType(otherUmlClass);
			umlOperation.getOwnedParameters().add(classParameter);
			umlClass.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), otherUmlClass, umlClass));
			return List.of(umlModel);
		});
	}

	// Multiple methods

	@Override
	public DomainModel multipleClassMethodsCreation() {
		return newModel(() -> {
			Class umlClass = newUmlClass();
			Operation umlOperation = newUmlOperation();
			Parameter returnParameter = newUmlReturnParameter();
			returnParameter.setType(UmlPrimitiveType.INTEGER.getUmlType());
			umlOperation.getOwnedParameters().add(returnParameter);
			Parameter booleanParameter = newUmlInputParameter();
			booleanParameter.setName(BOOLEAN_PARAMETER_NAME);
			booleanParameter.setType(UmlPrimitiveType.BOOLEAN.getUmlType());
			umlOperation.getOwnedParameters().add(booleanParameter);
			umlClass.getOwnedOperations().add(umlOperation);
			Operation umlOperation2 = newUmlOperation();
			umlOperation2.setName(METHOD_2_NAME);
			Parameter returnParameter2 = newUmlReturnParameter();
			returnParameter2.setType(UmlPrimitiveType.INTEGER.getUmlType());
			umlOperation2.getOwnedParameters().add(returnParameter2);
			Parameter stringParameter = newUmlInputParameter();
			stringParameter.setName(STRING_PARAMETER_NAME);
			stringParameter.setType(UmlPrimitiveType.STRING.getUmlType());
			umlOperation2.getOwnedParameters().add(stringParameter);
			umlClass.getOwnedOperations().add(umlOperation2);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlClass));
			return List.of(umlModel);
		});
	}
}

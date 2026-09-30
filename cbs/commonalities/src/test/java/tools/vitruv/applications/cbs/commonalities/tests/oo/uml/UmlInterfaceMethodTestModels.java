package tools.vitruv.applications.cbs.commonalities.tests.oo.uml;

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.newUmlModel;
import static tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlModelHelper.withElements;

import java.util.List;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.VisibilityKind;
import tools.vitruv.applications.cbs.commonalities.tests.oo.InterfaceMethodTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase;
import tools.vitruv.applications.cbs.operators.uml.UmlPrimitiveType;

public class UmlInterfaceMethodTestModels extends UmlTestModelsBase implements InterfaceMethodTest.DomainModels {

	private static Package newUmlPackage() {
		Package umlPackage = UMLFactory.eINSTANCE.createPackage();
		umlPackage.setName(PACKAGE_NAME);
		return umlPackage;
	}

	private static Interface newUmlInterface() {
		Interface umlInterface = UMLFactory.eINSTANCE.createInterface();
		umlInterface.setName(INTERFACE_NAME);
		umlInterface.setVisibility(VisibilityKind.PUBLIC_LITERAL);
		return umlInterface;
	}

	private static Operation newUmlOperation() {
		Operation umlOperation = UMLFactory.eINSTANCE.createOperation();
		umlOperation.setName(METHOD_NAME);
		umlOperation.setVisibility(VisibilityKind.PUBLIC_LITERAL);
		umlOperation.setIsAbstract(true);
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

	public UmlInterfaceMethodTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	// Basic

	@Override
	public DomainModel basicInterfaceMethodCreation() {
		return newModel(() -> {
			Interface umlInterface = newUmlInterface();
			umlInterface.getOwnedOperations().add(newUmlOperation());
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlInterface));
			return List.of(umlModel);
		});
	}

	// Static

	@Override
	public DomainModel staticInterfaceMethodCreation() {
		return newModel(() -> {
			Interface umlInterface = newUmlInterface();
			Operation umlOperation = newUmlOperation();
			umlOperation.setIsAbstract(false);
			umlOperation.setIsStatic(true);
			umlInterface.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlInterface));
			return List.of(umlModel);
		});
	}

	// Return type

	@Override
	public DomainModel interfaceMethodWithIntegerReturnCreation() {
		return newModel(() -> {
			Interface umlInterface = newUmlInterface();
			Operation umlOperation = newUmlOperation();
			Parameter returnParameter = newUmlReturnParameter();
			returnParameter.setType(UmlPrimitiveType.INTEGER.getUmlType());
			umlOperation.getOwnedParameters().add(returnParameter);
			umlInterface.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlInterface));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel interfaceMethodWithStringReturnCreation() {
		return newModel(() -> {
			Interface umlInterface = newUmlInterface();
			Operation umlOperation = newUmlOperation();
			Parameter returnParameter = newUmlReturnParameter();
			returnParameter.setType(UmlPrimitiveType.STRING.getUmlType());
			umlOperation.getOwnedParameters().add(returnParameter);
			umlInterface.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlInterface));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel interfaceMethodWithClassReturnCreation() {
		return newModel(() -> {
			Class otherUmlClass = newOtherUmlClass();
			Interface umlInterface = newUmlInterface();
			Operation umlOperation = newUmlOperation();
			Parameter returnParameter = newUmlReturnParameter();
			returnParameter.setType(otherUmlClass);
			umlOperation.getOwnedParameters().add(returnParameter);
			umlInterface.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), otherUmlClass, umlInterface));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel interfaceMethodWithSelfReturnCreation() {
		return newModel(() -> {
			Interface umlInterface = newUmlInterface();
			Operation umlOperation = newUmlOperation();
			Parameter returnParameter = newUmlReturnParameter();
			returnParameter.setType(umlInterface);
			umlOperation.getOwnedParameters().add(returnParameter);
			umlInterface.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlInterface));
			return List.of(umlModel);
		});
	}

	// Input parameters

	@Override
	public DomainModel interfaceMethodWithIntegerInputCreation() {
		return newModel(() -> {
			Interface umlInterface = newUmlInterface();
			Operation umlOperation = newUmlOperation();
			Parameter integerParameter = newUmlInputParameter();
			integerParameter.setName(INTEGER_PARAMETER_NAME);
			integerParameter.setType(UmlPrimitiveType.INTEGER.getUmlType());
			umlOperation.getOwnedParameters().add(integerParameter);
			umlInterface.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlInterface));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel interfaceMethodWithMultiplePrimitiveInputsCreation() {
		return newModel(() -> {
			Interface umlInterface = newUmlInterface();
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
			umlInterface.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlInterface));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel interfaceMethodWithStringInputCreation() {
		return newModel(() -> {
			Interface umlInterface = newUmlInterface();
			Operation umlOperation = newUmlOperation();
			Parameter stringParameter = newUmlInputParameter();
			stringParameter.setName(STRING_PARAMETER_NAME);
			stringParameter.setType(UmlPrimitiveType.STRING.getUmlType());
			umlOperation.getOwnedParameters().add(stringParameter);
			umlInterface.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlInterface));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel interfaceMethodWithClassInputCreation() {
		return newModel(() -> {
			Class otherUmlClass = newOtherUmlClass();
			Interface umlInterface = newUmlInterface();
			Operation umlOperation = newUmlOperation();
			Parameter classParameter = newUmlInputParameter();
			classParameter.setName(CLASS_PARAMETER_NAME);
			classParameter.setType(otherUmlClass);
			umlOperation.getOwnedParameters().add(classParameter);
			umlInterface.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), otherUmlClass, umlInterface));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel interfaceMethodWithSelfInputCreation() {
		return newModel(() -> {
			Interface umlInterface = newUmlInterface();
			Operation umlOperation = newUmlOperation();
			Parameter ownTypeParameter = newUmlInputParameter();
			ownTypeParameter.setName(OWN_TYPE_PARAMETER_NAME);
			ownTypeParameter.setType(umlInterface);
			umlOperation.getOwnedParameters().add(ownTypeParameter);
			umlInterface.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlInterface));
			return List.of(umlModel);
		});
	}

	@Override
	public DomainModel interfaceMethodWithMixedInputsCreation() {
		return newModel(() -> {
			Class otherUmlClass = newOtherUmlClass();
			Interface umlInterface = newUmlInterface();
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
			umlInterface.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), otherUmlClass, umlInterface));
			return List.of(umlModel);
		});
	}

	// Mixed input and return types

	@Override
	public DomainModel interfaceMethodWithMixedInputsAndReturnCreation() {
		return newModel(() -> {
			Class otherUmlClass = newOtherUmlClass();
			Interface umlInterface = newUmlInterface();
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
			umlInterface.getOwnedOperations().add(umlOperation);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), otherUmlClass, umlInterface));
			return List.of(umlModel);
		});
	}

	// Multiple methods

	@Override
	public DomainModel multipleInterfaceMethodsCreation() {
		return newModel(() -> {
			Interface umlInterface = newUmlInterface();
			Operation umlOperation = newUmlOperation();
			Parameter returnParameter = newUmlReturnParameter();
			returnParameter.setType(UmlPrimitiveType.INTEGER.getUmlType());
			umlOperation.getOwnedParameters().add(returnParameter);
			Parameter booleanParameter = newUmlInputParameter();
			booleanParameter.setName(BOOLEAN_PARAMETER_NAME);
			booleanParameter.setType(UmlPrimitiveType.BOOLEAN.getUmlType());
			umlOperation.getOwnedParameters().add(booleanParameter);
			umlInterface.getOwnedOperations().add(umlOperation);
			Operation umlOperation2 = newUmlOperation();
			umlOperation2.setName(METHOD_2_NAME);
			Parameter returnParameter2 = newUmlReturnParameter();
			returnParameter2.setType(UmlPrimitiveType.INTEGER.getUmlType());
			umlOperation2.getOwnedParameters().add(returnParameter2);
			Parameter stringParameter = newUmlInputParameter();
			stringParameter.setName(STRING_PARAMETER_NAME);
			stringParameter.setType(UmlPrimitiveType.STRING.getUmlType());
			umlOperation2.getOwnedParameters().add(stringParameter);
			umlInterface.getOwnedOperations().add(umlOperation2);
			Model umlModel = withElements(newUmlModel(), withElements(newUmlPackage(), umlInterface));
			return List.of(umlModel);
		});
	}
}

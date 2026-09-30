package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml;

import java.util.List;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.VisibilityKind;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.OperationTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase;
import tools.vitruv.applications.cbs.operators.uml.UmlPrimitiveType;

public class UmlOperationTestModels extends UmlTestModelsBase implements OperationTest.DomainModels {

	private static Interface newUmlInterface() {
		Interface umlInterface = UMLFactory.eINSTANCE.createInterface();
		umlInterface.setName(INTERFACE_NAME);
		umlInterface.setVisibility(VisibilityKind.PUBLIC_LITERAL);
		return umlInterface;
	}

	private static Operation newUmlOperation() {
		Operation operation = UMLFactory.eINSTANCE.createOperation();
		operation.setName(OPERATION_NAME);
		operation.setVisibility(VisibilityKind.PUBLIC_LITERAL);
		operation.setIsAbstract(true);
		return operation;
	}

	private static Parameter newUmlInputParameter(String name, Type type) {
		Parameter parameter = UMLFactory.eINSTANCE.createParameter();
		parameter.setDirection(ParameterDirectionKind.IN_LITERAL);
		parameter.setName(name);
		parameter.setType(type);
		return parameter;
	}

	private static Parameter newUmlReturnParameter(Type type) {
		Parameter parameter = UMLFactory.eINSTANCE.createParameter();
		parameter.setDirection(ParameterDirectionKind.RETURN_LITERAL);
		parameter.setType(type);
		return parameter;
	}

	public UmlOperationTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	// Empty

	@Override
	public DomainModel emptyOperationCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();

			Interface umlInterface = newUmlInterface();
			Operation operation = newUmlOperation();
			umlInterface.getOwnedOperations().add(operation);
			umlRepositoryModel.getContractsPackage().getPackagedElements().add(umlInterface);

			return List.of(umlRepositoryModel.getModel());
		});
	}

	// Return type

	@Override
	public DomainModel operationWithIntegerReturnCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();

			Interface umlInterface = newUmlInterface();
			Operation operation = newUmlOperation();
			operation.getOwnedParameters().add(newUmlReturnParameter(UmlPrimitiveType.INTEGER.getUmlType()));
			umlInterface.getOwnedOperations().add(operation);
			umlRepositoryModel.getContractsPackage().getPackagedElements().add(umlInterface);

			return List.of(umlRepositoryModel.getModel());
		});
	}

	@Override
	public DomainModel operationWithStringReturnCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();

			Interface umlInterface = newUmlInterface();
			Operation operation = newUmlOperation();
			operation.getOwnedParameters().add(newUmlReturnParameter(UmlPrimitiveType.STRING.getUmlType()));
			umlInterface.getOwnedOperations().add(operation);
			umlRepositoryModel.getContractsPackage().getPackagedElements().add(umlInterface);

			return List.of(umlRepositoryModel.getModel());
		});
	}

	// Input parameters

	@Override
	public DomainModel operationWithIntegerInputCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();

			Interface umlInterface = newUmlInterface();
			Operation operation = newUmlOperation();
			operation.getOwnedParameters()
					.add(newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()));
			umlInterface.getOwnedOperations().add(operation);
			umlRepositoryModel.getContractsPackage().getPackagedElements().add(umlInterface);

			return List.of(umlRepositoryModel.getModel());
		});
	}

	@Override
	public DomainModel operationWithMultiplePrimitiveInputsCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();

			Interface umlInterface = newUmlInterface();
			Operation operation = newUmlOperation();
			operation.getOwnedParameters()
					.add(newUmlInputParameter(BOOLEAN_PARAMETER_NAME, UmlPrimitiveType.BOOLEAN.getUmlType()));
			operation.getOwnedParameters()
					.add(newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()));
			operation.getOwnedParameters()
					.add(newUmlInputParameter(DOUBLE_PARAMETER_NAME, UmlPrimitiveType.REAL.getUmlType()));
			umlInterface.getOwnedOperations().add(operation);
			umlRepositoryModel.getContractsPackage().getPackagedElements().add(umlInterface);

			return List.of(umlRepositoryModel.getModel());
		});
	}

	@Override
	public DomainModel operationWithStringInputCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();

			Interface umlInterface = newUmlInterface();
			Operation operation = newUmlOperation();
			operation.getOwnedParameters()
					.add(newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType()));
			umlInterface.getOwnedOperations().add(operation);
			umlRepositoryModel.getContractsPackage().getPackagedElements().add(umlInterface);

			return List.of(umlRepositoryModel.getModel());
		});
	}

	// Mixed input and return types

	@Override
	public DomainModel operationWithMixedInputsAndReturnCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();

			Interface umlInterface = newUmlInterface();
			Operation operation = newUmlOperation();
			operation.getOwnedParameters().add(newUmlReturnParameter(UmlPrimitiveType.INTEGER.getUmlType()));
			operation.getOwnedParameters()
					.add(newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()));
			operation.getOwnedParameters()
					.add(newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType()));
			umlInterface.getOwnedOperations().add(operation);
			umlRepositoryModel.getContractsPackage().getPackagedElements().add(umlInterface);

			return List.of(umlRepositoryModel.getModel());
		});
	}

	// Multiple operations

	@Override
	public DomainModel multipleOperationsCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();

			Interface umlInterface = newUmlInterface();
			Operation operation1 = newUmlOperation();
			operation1.getOwnedParameters().add(newUmlReturnParameter(UmlPrimitiveType.INTEGER.getUmlType()));
			operation1.getOwnedParameters()
					.add(newUmlInputParameter(BOOLEAN_PARAMETER_NAME, UmlPrimitiveType.BOOLEAN.getUmlType()));
			umlInterface.getOwnedOperations().add(operation1);
			Operation operation2 = newUmlOperation();
			operation2.setName(OPERATION_2_NAME);
			operation2.getOwnedParameters().add(newUmlReturnParameter(UmlPrimitiveType.INTEGER.getUmlType()));
			operation2.getOwnedParameters()
					.add(newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType()));
			umlInterface.getOwnedOperations().add(operation2);
			umlRepositoryModel.getContractsPackage().getPackagedElements().add(umlInterface);

			return List.of(umlRepositoryModel.getModel());
		});
	}
}

package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
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

    private static Operation newUmlOperation(Parameter... parameters) {
        Operation operation = UMLFactory.eINSTANCE.createOperation();
        operation.setName(OPERATION_NAME);
        operation.setVisibility(VisibilityKind.PUBLIC_LITERAL);
        operation.setIsAbstract(true);
        operation.getOwnedParameters().addAll(List.of(parameters));
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

    /**
     * The UML repository model whose contracts package contains an interface
     * with the given operations.
     */
    private static List<EObject> interfaceWithOperations(Operation... operations) {
        UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();
        Interface umlInterface = newUmlInterface();
        umlInterface.getOwnedOperations().addAll(List.of(operations));
        umlRepositoryModel.getContractsPackage().getPackagedElements().add(umlInterface);
        return List.of(umlRepositoryModel.getModel());
    }

    public UmlOperationTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    // Empty

    @Override
    public DomainModel emptyOperationCreation() {
        return newModel(() -> interfaceWithOperations(newUmlOperation()));
    }

    // Return type

    @Override
    public DomainModel operationWithIntegerReturnCreation() {
        return newModel(() -> interfaceWithOperations(
                newUmlOperation(newUmlReturnParameter(UmlPrimitiveType.INTEGER.getUmlType()))));
    }

    @Override
    public DomainModel operationWithStringReturnCreation() {
        return newModel(() -> interfaceWithOperations(
                newUmlOperation(newUmlReturnParameter(UmlPrimitiveType.STRING.getUmlType()))));
    }

    // Input parameters

    @Override
    public DomainModel operationWithIntegerInputCreation() {
        return newModel(() -> interfaceWithOperations(newUmlOperation(
                newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()))));
    }

    @Override
    public DomainModel operationWithMultiplePrimitiveInputsCreation() {
        return newModel(() -> interfaceWithOperations(newUmlOperation(
                newUmlInputParameter(BOOLEAN_PARAMETER_NAME, UmlPrimitiveType.BOOLEAN.getUmlType()),
                newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()),
                newUmlInputParameter(DOUBLE_PARAMETER_NAME, UmlPrimitiveType.REAL.getUmlType()))));
    }

    @Override
    public DomainModel operationWithStringInputCreation() {
        return newModel(() -> interfaceWithOperations(newUmlOperation(
                newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType()))));
    }

    // Mixed input and return types

    @Override
    public DomainModel operationWithMixedInputsAndReturnCreation() {
        return newModel(() -> interfaceWithOperations(newUmlOperation(
                newUmlReturnParameter(UmlPrimitiveType.INTEGER.getUmlType()),
                newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()),
                newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType()))));
    }

    // Multiple operations

    @Override
    public DomainModel multipleOperationsCreation() {
        return newModel(() -> {
            Operation operation1 = newUmlOperation(
                    newUmlReturnParameter(UmlPrimitiveType.INTEGER.getUmlType()),
                    newUmlInputParameter(BOOLEAN_PARAMETER_NAME, UmlPrimitiveType.BOOLEAN.getUmlType()));
            Operation operation2 = newUmlOperation(
                    newUmlReturnParameter(UmlPrimitiveType.INTEGER.getUmlType()),
                    newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType()));
            operation2.setName(OPERATION_2_NAME);
            return interfaceWithOperations(operation1, operation2);
        });
    }
}

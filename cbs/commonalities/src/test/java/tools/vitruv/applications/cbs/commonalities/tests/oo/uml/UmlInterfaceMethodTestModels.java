package tools.vitruv.applications.cbs.commonalities.tests.oo.uml;

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.newUmlModel;
import static tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlModelHelper.withElements;

import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;
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
        Operation operation = UMLFactory.eINSTANCE.createOperation();
        operation.setName(METHOD_NAME);
        operation.setVisibility(VisibilityKind.PUBLIC_LITERAL);
        operation.setIsAbstract(true);
        return operation;
    }

    private static Operation newUmlOperation(Parameter... parameters) {
        Operation operation = newUmlOperation();
        operation.getOwnedParameters().addAll(List.of(parameters));
        return operation;
    }

    private static Operation configured(Operation operation, Consumer<Operation> configuration) {
        configuration.accept(operation);
        return operation;
    }

    private static Class newOtherUmlClass() {
        Class umlClass = UMLFactory.eINSTANCE.createClass();
        umlClass.setName(OTHER_CLASS_NAME);
        umlClass.setVisibility(VisibilityKind.PUBLIC_LITERAL);
        return umlClass;
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

    private static Interface withOperations(Interface umlInterface, Operation... operations) {
        umlInterface.getOwnedOperations().addAll(List.of(operations));
        return umlInterface;
    }

    /**
     * The UML model whose package contains the given elements.
     */
    private static List<EObject> modelWithElements(PackageableElement... elements) {
        return List.of(withElements(newUmlModel(), withElements(newUmlPackage(), elements)));
    }

    /**
     * The UML model with the default interface containing the given
     * operations.
     */
    private static List<EObject> interfaceWithOperations(Operation... operations) {
        return modelWithElements(withOperations(newUmlInterface(), operations));
    }

    public UmlInterfaceMethodTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    // Basic

    @Override
    public DomainModel basicInterfaceMethodCreation() {
        return newModel(() -> interfaceWithOperations(newUmlOperation()));
    }

    // Static

    @Override
    public DomainModel staticInterfaceMethodCreation() {
        return newModel(() -> interfaceWithOperations(configured(newUmlOperation(), op -> {
            op.setIsAbstract(false);
            op.setIsStatic(true);
        })));
    }

    // Return type

    @Override
    public DomainModel interfaceMethodWithIntegerReturnCreation() {
        return newModel(() -> interfaceWithOperations(
                newUmlOperation(newUmlReturnParameter(UmlPrimitiveType.INTEGER.getUmlType()))));
    }

    @Override
    public DomainModel interfaceMethodWithStringReturnCreation() {
        return newModel(() -> interfaceWithOperations(
                newUmlOperation(newUmlReturnParameter(UmlPrimitiveType.STRING.getUmlType()))));
    }

    @Override
    public DomainModel interfaceMethodWithClassReturnCreation() {
        return newModel(() -> {
            Class otherUmlClass = newOtherUmlClass();
            return modelWithElements(otherUmlClass,
                    withOperations(newUmlInterface(), newUmlOperation(newUmlReturnParameter(otherUmlClass))));
        });
    }

    @Override
    public DomainModel interfaceMethodWithSelfReturnCreation() {
        return newModel(() -> {
            Interface umlInterface = newUmlInterface();
            return modelWithElements(
                    withOperations(umlInterface, newUmlOperation(newUmlReturnParameter(umlInterface))));
        });
    }

    // Input parameters

    @Override
    public DomainModel interfaceMethodWithIntegerInputCreation() {
        return newModel(() -> interfaceWithOperations(newUmlOperation(
                newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()))));
    }

    @Override
    public DomainModel interfaceMethodWithMultiplePrimitiveInputsCreation() {
        return newModel(() -> interfaceWithOperations(newUmlOperation(
                newUmlInputParameter(BOOLEAN_PARAMETER_NAME, UmlPrimitiveType.BOOLEAN.getUmlType()),
                newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()),
                newUmlInputParameter(DOUBLE_PARAMETER_NAME, UmlPrimitiveType.REAL.getUmlType()))));
    }

    @Override
    public DomainModel interfaceMethodWithStringInputCreation() {
        return newModel(() -> interfaceWithOperations(newUmlOperation(
                newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType()))));
    }

    @Override
    public DomainModel interfaceMethodWithClassInputCreation() {
        return newModel(() -> {
            Class otherUmlClass = newOtherUmlClass();
            return modelWithElements(otherUmlClass, withOperations(newUmlInterface(),
                    newUmlOperation(newUmlInputParameter(CLASS_PARAMETER_NAME, otherUmlClass))));
        });
    }

    @Override
    public DomainModel interfaceMethodWithSelfInputCreation() {
        return newModel(() -> {
            Interface umlInterface = newUmlInterface();
            return modelWithElements(withOperations(umlInterface,
                    newUmlOperation(newUmlInputParameter(OWN_TYPE_PARAMETER_NAME, umlInterface))));
        });
    }

    @Override
    public DomainModel interfaceMethodWithMixedInputsCreation() {
        return newModel(() -> {
            Class otherUmlClass = newOtherUmlClass();
            return modelWithElements(otherUmlClass, withOperations(newUmlInterface(), newUmlOperation(
                    newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()),
                    newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType()),
                    newUmlInputParameter(CLASS_PARAMETER_NAME, otherUmlClass))));
        });
    }

    // Mixed input and return types

    @Override
    public DomainModel interfaceMethodWithMixedInputsAndReturnCreation() {
        return newModel(() -> {
            Class otherUmlClass = newOtherUmlClass();
            return modelWithElements(otherUmlClass, withOperations(newUmlInterface(), newUmlOperation(
                    newUmlReturnParameter(UmlPrimitiveType.INTEGER.getUmlType()),
                    newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()),
                    newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType()),
                    newUmlInputParameter(CLASS_PARAMETER_NAME, otherUmlClass))));
        });
    }

    // Multiple methods

    @Override
    public DomainModel multipleInterfaceMethodsCreation() {
        return newModel(() -> interfaceWithOperations(
                newUmlOperation(
                        newUmlReturnParameter(UmlPrimitiveType.INTEGER.getUmlType()),
                        newUmlInputParameter(BOOLEAN_PARAMETER_NAME, UmlPrimitiveType.BOOLEAN.getUmlType())),
                configured(newUmlOperation(
                        newUmlReturnParameter(UmlPrimitiveType.INTEGER.getUmlType()),
                        newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType())),
                        op -> op.setName(METHOD_2_NAME))));
    }
}

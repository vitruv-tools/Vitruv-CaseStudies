package tools.vitruv.applications.cbs.commonalities.tests.oo.uml;

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.newUmlModel;
import static tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlModelHelper.withElements;

import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.VisibilityKind;

import tools.vitruv.applications.cbs.commonalities.tests.oo.ClassMethodTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase;
import tools.vitruv.applications.cbs.operators.uml.UmlPrimitiveType;

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
        Operation operation = UMLFactory.eINSTANCE.createOperation();
        operation.setName(METHOD_NAME);
        operation.setVisibility(VisibilityKind.PUBLIC_LITERAL);
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

    private static Class withOperations(Class umlClass, Operation... operations) {
        umlClass.getOwnedOperations().addAll(List.of(operations));
        return umlClass;
    }

    /**
     * The UML model whose package contains the given classes.
     */
    private static List<EObject> modelWithClasses(Class... umlClasses) {
        return List.of(withElements(newUmlModel(), withElements(newUmlPackage(), umlClasses)));
    }

    /**
     * The UML model with the default class containing the given operations.
     */
    private static List<EObject> classWithOperations(Operation... operations) {
        return modelWithClasses(withOperations(newUmlClass(), operations));
    }

    public UmlClassMethodTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    // Basic

    @Override
    public DomainModel basicClassMethodCreation() {
        return newModel(() -> classWithOperations(newUmlOperation()));
    }

    // Visibility

    @Override
    public DomainModel publicClassMethodCreation() {
        return newModel(() -> classWithOperations(
                configured(newUmlOperation(), op -> op.setVisibility(VisibilityKind.PUBLIC_LITERAL))));
    }

    @Override
    public DomainModel protectedClassMethodCreation() {
        return newModel(() -> classWithOperations(
                configured(newUmlOperation(), op -> op.setVisibility(VisibilityKind.PROTECTED_LITERAL))));
    }

    @Override
    public DomainModel packagePrivateClassMethodCreation() {
        return newModel(() -> classWithOperations(
                configured(newUmlOperation(), op -> op.setVisibility(VisibilityKind.PACKAGE_LITERAL))));
    }

    @Override
    public DomainModel privateClassMethodCreation() {
        return newModel(() -> classWithOperations(
                configured(newUmlOperation(), op -> op.setVisibility(VisibilityKind.PRIVATE_LITERAL))));
    }

    // Modifiers

    @Override
    public DomainModel finalClassMethodCreation() {
        return newModel(() -> classWithOperations(configured(newUmlOperation(), op -> op.setIsLeaf(true))));
    }

    @Override
    public DomainModel abstractClassMethodCreation() {
        return newModel(() -> classWithOperations(configured(newUmlOperation(), op -> op.setIsAbstract(true))));
    }

    @Override
    public DomainModel staticClassMethodCreation() {
        return newModel(() -> classWithOperations(configured(newUmlOperation(), op -> op.setIsStatic(true))));
    }

    // Return type

    @Override
    public DomainModel classMethodWithIntegerReturnCreation() {
        return newModel(() -> classWithOperations(
                newUmlOperation(newUmlReturnParameter(UmlPrimitiveType.INTEGER.getUmlType()))));
    }

    @Override
    public DomainModel classMethodWithStringReturnCreation() {
        return newModel(() -> classWithOperations(
                newUmlOperation(newUmlReturnParameter(UmlPrimitiveType.STRING.getUmlType()))));
    }

    @Override
    public DomainModel classMethodWithClassReturnCreation() {
        return newModel(() -> {
            Class otherUmlClass = newOtherUmlClass();
            return modelWithClasses(otherUmlClass,
                    withOperations(newUmlClass(), newUmlOperation(newUmlReturnParameter(otherUmlClass))));
        });
    }

    @Override
    public DomainModel classMethodWithSelfReturnCreation() {
        return newModel(() -> {
            Class umlClass = newUmlClass();
            return modelWithClasses(withOperations(umlClass, newUmlOperation(newUmlReturnParameter(umlClass))));
        });
    }

    // Input parameters

    @Override
    public DomainModel classMethodWithIntegerInputCreation() {
        return newModel(() -> classWithOperations(newUmlOperation(
                newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()))));
    }

    @Override
    public DomainModel classMethodWithMultiplePrimitiveInputsCreation() {
        return newModel(() -> classWithOperations(newUmlOperation(
                newUmlInputParameter(BOOLEAN_PARAMETER_NAME, UmlPrimitiveType.BOOLEAN.getUmlType()),
                newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()),
                newUmlInputParameter(DOUBLE_PARAMETER_NAME, UmlPrimitiveType.REAL.getUmlType()))));
    }

    @Override
    public DomainModel classMethodWithStringInputCreation() {
        return newModel(() -> classWithOperations(newUmlOperation(
                newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType()))));
    }

    @Override
    public DomainModel classMethodWithClassInputCreation() {
        return newModel(() -> {
            Class otherUmlClass = newOtherUmlClass();
            return modelWithClasses(otherUmlClass, withOperations(newUmlClass(),
                    newUmlOperation(newUmlInputParameter(CLASS_PARAMETER_NAME, otherUmlClass))));
        });
    }

    @Override
    public DomainModel classMethodWithSelfInputCreation() {
        return newModel(() -> {
            Class umlClass = newUmlClass();
            return modelWithClasses(withOperations(umlClass,
                    newUmlOperation(newUmlInputParameter(OWN_TYPE_PARAMETER_NAME, umlClass))));
        });
    }

    @Override
    public DomainModel classMethodWithMixedInputsCreation() {
        return newModel(() -> {
            Class otherUmlClass = newOtherUmlClass();
            return modelWithClasses(otherUmlClass, withOperations(newUmlClass(), newUmlOperation(
                    newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()),
                    newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType()),
                    newUmlInputParameter(CLASS_PARAMETER_NAME, otherUmlClass))));
        });
    }

    // Mixed input and return types

    @Override
    public DomainModel classMethodWithMixedInputsAndReturnCreation() {
        return newModel(() -> {
            Class otherUmlClass = newOtherUmlClass();
            return modelWithClasses(otherUmlClass, withOperations(newUmlClass(), newUmlOperation(
                    newUmlReturnParameter(UmlPrimitiveType.INTEGER.getUmlType()),
                    newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()),
                    newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType()),
                    newUmlInputParameter(CLASS_PARAMETER_NAME, otherUmlClass))));
        });
    }

    // Multiple methods

    @Override
    public DomainModel multipleClassMethodsCreation() {
        return newModel(() -> classWithOperations(
                newUmlOperation(
                        newUmlReturnParameter(UmlPrimitiveType.INTEGER.getUmlType()),
                        newUmlInputParameter(BOOLEAN_PARAMETER_NAME, UmlPrimitiveType.BOOLEAN.getUmlType())),
                configured(newUmlOperation(
                        newUmlReturnParameter(UmlPrimitiveType.INTEGER.getUmlType()),
                        newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType())),
                        op -> op.setName(METHOD_2_NAME))));
    }
}

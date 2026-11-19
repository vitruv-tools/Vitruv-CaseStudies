package tools.vitruv.applications.cbs.commonalities.tests.oo.uml;

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.newUmlModel;
import static tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlModelHelper.withElements;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
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
        Operation constructor = UMLFactory.eINSTANCE.createOperation();
        // Constructor: Operation with the same name as the containing class and no return parameter.
        constructor.setName(CLASS_NAME);
        constructor.setVisibility(VisibilityKind.PUBLIC_LITERAL);
        return constructor;
    }

    private static Operation newUmlConstructor(VisibilityKind visibility) {
        Operation constructor = newUmlConstructor();
        constructor.setVisibility(visibility);
        return constructor;
    }

    private static Operation newUmlConstructor(Parameter... parameters) {
        Operation constructor = newUmlConstructor();
        constructor.getOwnedParameters().addAll(List.of(parameters));
        return constructor;
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

    public UmlConstructorTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    // Basic

    @Override
    public DomainModel basicConstructorCreation() {
        return newModel(() -> modelWithClasses(withOperations(newUmlClass(), newUmlConstructor())));
    }

    // Visibility

    @Override
    public DomainModel publicConstructorCreation() {
        return newModel(() -> modelWithClasses(
                withOperations(newUmlClass(), newUmlConstructor(VisibilityKind.PUBLIC_LITERAL))));
    }

    @Override
    public DomainModel protectedConstructorCreation() {
        return newModel(() -> modelWithClasses(
                withOperations(newUmlClass(), newUmlConstructor(VisibilityKind.PROTECTED_LITERAL))));
    }

    @Override
    public DomainModel packagePrivateConstructorCreation() {
        return newModel(() -> modelWithClasses(
                withOperations(newUmlClass(), newUmlConstructor(VisibilityKind.PACKAGE_LITERAL))));
    }

    @Override
    public DomainModel privateConstructorCreation() {
        return newModel(() -> modelWithClasses(
                withOperations(newUmlClass(), newUmlConstructor(VisibilityKind.PRIVATE_LITERAL))));
    }

    // Input parameters

    @Override
    public DomainModel constructorWithIntegerInputCreation() {
        return newModel(() -> modelWithClasses(withOperations(newUmlClass(), newUmlConstructor(
                newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType())))));
    }

    @Override
    public DomainModel constructorWithMultiplePrimitiveInputsCreation() {
        return newModel(() -> modelWithClasses(withOperations(newUmlClass(), newUmlConstructor(
                newUmlInputParameter(BOOLEAN_PARAMETER_NAME, UmlPrimitiveType.BOOLEAN.getUmlType()),
                newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()),
                newUmlInputParameter(DOUBLE_PARAMETER_NAME, UmlPrimitiveType.REAL.getUmlType())))));
    }

    @Override
    public DomainModel constructorWithStringInputCreation() {
        return newModel(() -> modelWithClasses(withOperations(newUmlClass(), newUmlConstructor(
                newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType())))));
    }

    @Override
    public DomainModel constructorWithClassInputCreation() {
        return newModel(() -> {
            Class otherUmlClass = newOtherUmlClass();
            return modelWithClasses(otherUmlClass, withOperations(newUmlClass(), newUmlConstructor(
                    newUmlInputParameter(CLASS_PARAMETER_NAME, otherUmlClass))));
        });
    }

    @Override
    public DomainModel constructorWithSelfInputCreation() {
        return newModel(() -> {
            Class umlClass = newUmlClass();
            return modelWithClasses(withOperations(umlClass, newUmlConstructor(
                    newUmlInputParameter(OWN_TYPE_PARAMETER_NAME, umlClass))));
        });
    }

    @Override
    public DomainModel constructorWithMixedInputsCreation() {
        return newModel(() -> {
            Class otherUmlClass = newOtherUmlClass();
            return modelWithClasses(otherUmlClass, withOperations(newUmlClass(), newUmlConstructor(
                    newUmlInputParameter(INTEGER_PARAMETER_NAME, UmlPrimitiveType.INTEGER.getUmlType()),
                    newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType()),
                    newUmlInputParameter(CLASS_PARAMETER_NAME, otherUmlClass))));
        });
    }

    // Multiple methods

    @Override
    public DomainModel multipleConstructorsCreation() {
        return newModel(() -> modelWithClasses(withOperations(newUmlClass(),
                newUmlConstructor(newUmlInputParameter(BOOLEAN_PARAMETER_NAME, UmlPrimitiveType.BOOLEAN.getUmlType())),
                newUmlConstructor(newUmlInputParameter(STRING_PARAMETER_NAME, UmlPrimitiveType.STRING.getUmlType())))));
    }
}

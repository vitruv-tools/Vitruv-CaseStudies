package tools.vitruv.applications.cbs.commonalities.tests.oo.java;

import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.newCompilationUnit;

import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.MembersFactory;
import org.emftext.language.java.modifiers.Abstract;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.ParametersFactory;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypesFactory;

import tools.vitruv.applications.cbs.commonalities.tests.oo.InterfaceMethodTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase;
import tools.vitruv.applications.util.temporary.java.JavaModificationUtil;

public class JavaInterfaceMethodTestModels extends JavaTestModelsBase implements InterfaceMethodTest.DomainModels {

    private static Package newJavaPackage() {
        Package javaPackage = ContainersFactory.eINSTANCE.createPackage();
        javaPackage.setName(PACKAGE_NAME);
        return javaPackage;
    }

    private static Interface newJavaInterface() {
        Interface javaInterface = ClassifiersFactory.eINSTANCE.createInterface();
        javaInterface.setName(INTERFACE_NAME);
        javaInterface.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        return javaInterface;
    }

    private static InterfaceMethod newJavaInterfaceMethod() {
        InterfaceMethod method = MembersFactory.eINSTANCE.createInterfaceMethod();
        method.setName(METHOD_NAME);
        method.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        method.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createAbstract());
        method.setTypeReference(TypesFactory.eINSTANCE.createVoid());
        return method;
    }

    private static Class newOtherJavaClass() {
        Class javaClass = ClassifiersFactory.eINSTANCE.createClass();
        javaClass.setName(OTHER_CLASS_NAME);
        javaClass.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        return javaClass;
    }

    private static OrdinaryParameter newJavaOrdinaryParameter(String name, TypeReference typeReference) {
        OrdinaryParameter parameter = ParametersFactory.eINSTANCE.createOrdinaryParameter();
        parameter.setName(name);
        parameter.setTypeReference(typeReference);
        return parameter;
    }

    private static InterfaceMethod withReturnType(InterfaceMethod method, TypeReference returnType) {
        method.setTypeReference(returnType);
        return method;
    }

    private static InterfaceMethod withParameters(InterfaceMethod method, OrdinaryParameter... parameters) {
        method.getParameters().addAll(List.of(parameters));
        return method;
    }

    private static Interface withMethods(Interface javaInterface, InterfaceMethod... methods) {
        javaInterface.getMembers().addAll(List.of(methods));
        return javaInterface;
    }

    /**
     * The package and the compilation unit of the default interface with the
     * given methods.
     */
    private static List<EObject> interfaceWithMethods(InterfaceMethod... methods) {
        Package javaPackage = newJavaPackage();
        CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage,
                withMethods(newJavaInterface(), methods));
        return List.of(javaPackage, javaCompilationUnit);
    }

    /**
     * The package, the compilation unit of the other class, and the compilation
     * unit of the default interface with a method that is created by the given
     * function from a reference to the other class.
     */
    private static List<EObject> interfaceWithMethodReferencingOtherClass(
            Function<TypeReference, InterfaceMethod> methodCreator) {
        Package javaPackage = newJavaPackage();
        Class otherJavaClass = newOtherJavaClass();
        CompilationUnit javaClassCompilationUnit = newCompilationUnit(javaPackage, otherJavaClass);
        InterfaceMethod method = methodCreator
                .apply(JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass));
        CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, withMethods(newJavaInterface(), method));
        return List.of(javaPackage, javaClassCompilationUnit, javaCompilationUnit);
    }

    public JavaInterfaceMethodTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    // Basic

    @Override
    public DomainModel basicInterfaceMethodCreation() {
        return newModel(() -> interfaceWithMethods(newJavaInterfaceMethod()));
    }

    // Static

    @Override
    public DomainModel staticInterfaceMethodCreation() {
        return newModel(() -> {
            InterfaceMethod method = newJavaInterfaceMethod();
            method.getAnnotationsAndModifiers().removeIf(Abstract.class::isInstance);
            method.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createStatic());
            return interfaceWithMethods(method);
        });
    }

    // Return type

    @Override
    public DomainModel interfaceMethodWithIntegerReturnCreation() {
        return newModel(() -> interfaceWithMethods(
                withReturnType(newJavaInterfaceMethod(), TypesFactory.eINSTANCE.createInt())));
    }

    @Override
    public DomainModel interfaceMethodWithStringReturnCreation() {
        return newModel(() -> interfaceWithMethods(
                withReturnType(newJavaInterfaceMethod(), referenceJamoppType(String.class))));
    }

    @Override
    public DomainModel interfaceMethodWithClassReturnCreation() {
        return newModel(() -> interfaceWithMethodReferencingOtherClass(
                otherClassReference -> withReturnType(newJavaInterfaceMethod(), otherClassReference)));
    }

    @Override
    public DomainModel interfaceMethodWithSelfReturnCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage();
            Interface javaInterface = newJavaInterface();
            withMethods(javaInterface, withReturnType(newJavaInterfaceMethod(),
                    JavaModificationUtil.createNamespaceClassifierReference(javaInterface)));
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaInterface);
            return List.of(javaPackage, javaCompilationUnit);
        });
    }

    // Input parameters

    @Override
    public DomainModel interfaceMethodWithIntegerInputCreation() {
        return newModel(() -> interfaceWithMethods(withParameters(newJavaInterfaceMethod(),
                newJavaOrdinaryParameter(INTEGER_PARAMETER_NAME, TypesFactory.eINSTANCE.createInt()))));
    }

    @Override
    public DomainModel interfaceMethodWithMultiplePrimitiveInputsCreation() {
        return newModel(() -> interfaceWithMethods(withParameters(newJavaInterfaceMethod(),
                newJavaOrdinaryParameter(BOOLEAN_PARAMETER_NAME, TypesFactory.eINSTANCE.createBoolean()),
                newJavaOrdinaryParameter(INTEGER_PARAMETER_NAME, TypesFactory.eINSTANCE.createInt()),
                newJavaOrdinaryParameter(DOUBLE_PARAMETER_NAME, TypesFactory.eINSTANCE.createDouble()))));
    }

    @Override
    public DomainModel interfaceMethodWithStringInputCreation() {
        return newModel(() -> interfaceWithMethods(withParameters(newJavaInterfaceMethod(),
                newJavaOrdinaryParameter(STRING_PARAMETER_NAME, referenceJamoppType(String.class)))));
    }

    @Override
    public DomainModel interfaceMethodWithClassInputCreation() {
        return newModel(() -> interfaceWithMethodReferencingOtherClass(
                otherClassReference -> withParameters(newJavaInterfaceMethod(),
                        newJavaOrdinaryParameter(CLASS_PARAMETER_NAME, otherClassReference))));
    }

    @Override
    public DomainModel interfaceMethodWithSelfInputCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage();
            Interface javaInterface = newJavaInterface();
            withMethods(javaInterface, withParameters(newJavaInterfaceMethod(),
                    newJavaOrdinaryParameter(OWN_TYPE_PARAMETER_NAME,
                            JavaModificationUtil.createNamespaceClassifierReference(javaInterface))));
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaInterface);
            return List.of(javaPackage, javaCompilationUnit);
        });
    }

    @Override
    public DomainModel interfaceMethodWithMixedInputsCreation() {
        return newModel(() -> interfaceWithMethodReferencingOtherClass(
                otherClassReference -> withParameters(newJavaInterfaceMethod(),
                        newJavaOrdinaryParameter(INTEGER_PARAMETER_NAME, TypesFactory.eINSTANCE.createInt()),
                        newJavaOrdinaryParameter(STRING_PARAMETER_NAME, referenceJamoppType(String.class)),
                        newJavaOrdinaryParameter(CLASS_PARAMETER_NAME, otherClassReference))));
    }

    // Mixed input and return types

    @Override
    public DomainModel interfaceMethodWithMixedInputsAndReturnCreation() {
        return newModel(() -> interfaceWithMethodReferencingOtherClass(
                otherClassReference -> withParameters(
                        withReturnType(newJavaInterfaceMethod(), TypesFactory.eINSTANCE.createInt()),
                        newJavaOrdinaryParameter(INTEGER_PARAMETER_NAME, TypesFactory.eINSTANCE.createInt()),
                        newJavaOrdinaryParameter(STRING_PARAMETER_NAME, referenceJamoppType(String.class)),
                        newJavaOrdinaryParameter(CLASS_PARAMETER_NAME, otherClassReference))));
    }

    // Multiple methods

    @Override
    public DomainModel multipleInterfaceMethodsCreation() {
        return newModel(() -> {
            InterfaceMethod method1 = withParameters(
                    withReturnType(newJavaInterfaceMethod(), TypesFactory.eINSTANCE.createInt()),
                    newJavaOrdinaryParameter(BOOLEAN_PARAMETER_NAME, TypesFactory.eINSTANCE.createBoolean()));
            InterfaceMethod method2 = newJavaInterfaceMethod();
            method2.setName(METHOD_2_NAME);
            withParameters(withReturnType(method2, TypesFactory.eINSTANCE.createInt()),
                    newJavaOrdinaryParameter(STRING_PARAMETER_NAME, referenceJamoppType(String.class)));
            return interfaceWithMethods(method1, method2);
        });
    }
}

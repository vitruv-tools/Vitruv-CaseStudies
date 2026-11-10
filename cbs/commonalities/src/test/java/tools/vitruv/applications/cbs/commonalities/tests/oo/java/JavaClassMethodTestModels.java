package tools.vitruv.applications.cbs.commonalities.tests.oo.java;

import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.newCompilationUnit;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.MembersFactory;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.ParametersFactory;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypesFactory;

import tools.vitruv.applications.cbs.commonalities.tests.oo.ClassMethodTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase;
import tools.vitruv.applications.util.temporary.java.JavaModificationUtil;

public class JavaClassMethodTestModels extends JavaTestModelsBase implements ClassMethodTest.DomainModels {

    private static Package newJavaPackage() {
        Package javaPackage = ContainersFactory.eINSTANCE.createPackage();
        javaPackage.setName(PACKAGE_NAME);
        return javaPackage;
    }

    private static Class newJavaClass() {
        Class javaClass = ClassifiersFactory.eINSTANCE.createClass();
        javaClass.setName(CLASS_NAME);
        javaClass.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        return javaClass;
    }

    // Creates a method without public modifier:
    private static ClassMethod newDefaultJavaClassMethod() {
        ClassMethod method = MembersFactory.eINSTANCE.createClassMethod();
        method.setName(METHOD_NAME);
        method.setTypeReference(TypesFactory.eINSTANCE.createVoid());
        return method;
    }

    private static ClassMethod newJavaClassMethod() {
        ClassMethod method = newDefaultJavaClassMethod();
        method.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
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

    private static ClassMethod withModifier(ClassMethod method, Modifier modifier) {
        method.getAnnotationsAndModifiers().add(modifier);
        return method;
    }

    private static ClassMethod withReturnType(ClassMethod method, TypeReference returnType) {
        method.setTypeReference(returnType);
        return method;
    }

    private static ClassMethod withParameters(ClassMethod method, OrdinaryParameter... parameters) {
        method.getParameters().addAll(List.of(parameters));
        return method;
    }

    private static Class withMethods(Class javaClass, ClassMethod... methods) {
        javaClass.getMembers().addAll(List.of(methods));
        return javaClass;
    }

    /**
     * The package and the compilation unit of the default class with the given
     * methods.
     */
    private static List<EObject> classWithMethods(ClassMethod... methods) {
        Package javaPackage = newJavaPackage();
        CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, withMethods(newJavaClass(), methods));
        return List.of(javaPackage, javaCompilationUnit);
    }

    public JavaClassMethodTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    // Basic

    @Override
    public DomainModel basicClassMethodCreation() {
        return newModel(() -> classWithMethods(newJavaClassMethod()));
    }

    // Visibility

    @Override
    public DomainModel publicClassMethodCreation() {
        return newModel(() -> classWithMethods(
                withModifier(newDefaultJavaClassMethod(), ModifiersFactory.eINSTANCE.createPublic())));
    }

    @Override
    public DomainModel protectedClassMethodCreation() {
        return newModel(() -> classWithMethods(
                withModifier(newDefaultJavaClassMethod(), ModifiersFactory.eINSTANCE.createProtected())));
    }

    @Override
    public DomainModel packagePrivateClassMethodCreation() {
        return newModel(() -> classWithMethods(newDefaultJavaClassMethod()));
    }

    @Override
    public DomainModel privateClassMethodCreation() {
        return newModel(() -> classWithMethods(
                withModifier(newDefaultJavaClassMethod(), ModifiersFactory.eINSTANCE.createPrivate())));
    }

    // Modifiers

    @Override
    public DomainModel finalClassMethodCreation() {
        return newModel(() -> classWithMethods(
                withModifier(newJavaClassMethod(), ModifiersFactory.eINSTANCE.createFinal())));
    }

    @Override
    public DomainModel abstractClassMethodCreation() {
        return newModel(() -> classWithMethods(
                withModifier(newJavaClassMethod(), ModifiersFactory.eINSTANCE.createAbstract())));
    }

    @Override
    public DomainModel staticClassMethodCreation() {
        return newModel(() -> classWithMethods(
                withModifier(newJavaClassMethod(), ModifiersFactory.eINSTANCE.createStatic())));
    }

    // Return type

    @Override
    public DomainModel classMethodWithIntegerReturnCreation() {
        return newModel(() -> classWithMethods(
                withReturnType(newJavaClassMethod(), TypesFactory.eINSTANCE.createInt())));
    }

    @Override
    public DomainModel classMethodWithStringReturnCreation() {
        return newModel(() -> classWithMethods(
                withReturnType(newJavaClassMethod(), referenceJamoppType(String.class))));
    }

    @Override
    public DomainModel classMethodWithClassReturnCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage();
            Class otherJavaClass = newOtherJavaClass();
            CompilationUnit javaClassCompilationUnit = newCompilationUnit(javaPackage, otherJavaClass);
            ClassMethod method = withReturnType(newJavaClassMethod(),
                    JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass));
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, withMethods(newJavaClass(), method));
            return List.of(javaPackage, javaClassCompilationUnit, javaCompilationUnit);
        });
    }

    @Override
    public DomainModel classMethodWithSelfReturnCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage();
            Class javaClass = newJavaClass();
            withMethods(javaClass, withReturnType(newJavaClassMethod(),
                    JavaModificationUtil.createNamespaceClassifierReference(javaClass)));
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
            return List.of(javaPackage, javaCompilationUnit);
        });
    }

    // Input parameters

    @Override
    public DomainModel classMethodWithIntegerInputCreation() {
        return newModel(() -> classWithMethods(withParameters(newJavaClassMethod(),
                newJavaOrdinaryParameter(INTEGER_PARAMETER_NAME, TypesFactory.eINSTANCE.createInt()))));
    }

    @Override
    public DomainModel classMethodWithMultiplePrimitiveInputsCreation() {
        return newModel(() -> classWithMethods(withParameters(newJavaClassMethod(),
                newJavaOrdinaryParameter(BOOLEAN_PARAMETER_NAME, TypesFactory.eINSTANCE.createBoolean()),
                newJavaOrdinaryParameter(INTEGER_PARAMETER_NAME, TypesFactory.eINSTANCE.createInt()),
                newJavaOrdinaryParameter(DOUBLE_PARAMETER_NAME, TypesFactory.eINSTANCE.createDouble()))));
    }

    @Override
    public DomainModel classMethodWithStringInputCreation() {
        return newModel(() -> classWithMethods(withParameters(newJavaClassMethod(),
                newJavaOrdinaryParameter(STRING_PARAMETER_NAME, referenceJamoppType(String.class)))));
    }

    @Override
    public DomainModel classMethodWithClassInputCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage();
            Class otherJavaClass = newOtherJavaClass();
            CompilationUnit javaClassCompilationUnit = newCompilationUnit(javaPackage, otherJavaClass);
            ClassMethod method = withParameters(newJavaClassMethod(),
                    newJavaOrdinaryParameter(CLASS_PARAMETER_NAME,
                            JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass)));
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, withMethods(newJavaClass(), method));
            return List.of(javaPackage, javaClassCompilationUnit, javaCompilationUnit);
        });
    }

    @Override
    public DomainModel classMethodWithSelfInputCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage();
            Class javaClass = newJavaClass();
            withMethods(javaClass, withParameters(newJavaClassMethod(),
                    newJavaOrdinaryParameter(OWN_TYPE_PARAMETER_NAME,
                            JavaModificationUtil.createNamespaceClassifierReference(javaClass))));
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
            return List.of(javaPackage, javaCompilationUnit);
        });
    }

    @Override
    public DomainModel classMethodWithMixedInputsCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage();
            Class otherJavaClass = newOtherJavaClass();
            CompilationUnit javaClassCompilationUnit = newCompilationUnit(javaPackage, otherJavaClass);
            ClassMethod method = withParameters(newJavaClassMethod(),
                    newJavaOrdinaryParameter(INTEGER_PARAMETER_NAME, TypesFactory.eINSTANCE.createInt()),
                    newJavaOrdinaryParameter(STRING_PARAMETER_NAME, referenceJamoppType(String.class)),
                    newJavaOrdinaryParameter(CLASS_PARAMETER_NAME,
                            JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass)));
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, withMethods(newJavaClass(), method));
            return List.of(javaPackage, javaClassCompilationUnit, javaCompilationUnit);
        });
    }

    // Mixed input and return types

    @Override
    public DomainModel classMethodWithMixedInputsAndReturnCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage();
            Class otherJavaClass = newOtherJavaClass();
            CompilationUnit javaClassCompilationUnit = newCompilationUnit(javaPackage, otherJavaClass);
            ClassMethod method = withParameters(
                    withReturnType(newJavaClassMethod(), TypesFactory.eINSTANCE.createInt()),
                    newJavaOrdinaryParameter(INTEGER_PARAMETER_NAME, TypesFactory.eINSTANCE.createInt()),
                    newJavaOrdinaryParameter(STRING_PARAMETER_NAME, referenceJamoppType(String.class)),
                    newJavaOrdinaryParameter(CLASS_PARAMETER_NAME,
                            JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass)));
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, withMethods(newJavaClass(), method));
            return List.of(javaPackage, javaClassCompilationUnit, javaCompilationUnit);
        });
    }

    // Multiple methods

    @Override
    public DomainModel multipleClassMethodsCreation() {
        return newModel(() -> {
            ClassMethod method1 = withParameters(
                    withReturnType(newJavaClassMethod(), TypesFactory.eINSTANCE.createInt()),
                    newJavaOrdinaryParameter(BOOLEAN_PARAMETER_NAME, TypesFactory.eINSTANCE.createBoolean()));
            ClassMethod method2 = newJavaClassMethod();
            method2.setName(METHOD_2_NAME);
            withParameters(withReturnType(method2, TypesFactory.eINSTANCE.createInt()),
                    newJavaOrdinaryParameter(STRING_PARAMETER_NAME, referenceJamoppType(String.class)));
            return classWithMethods(method1, method2);
        });
    }
}

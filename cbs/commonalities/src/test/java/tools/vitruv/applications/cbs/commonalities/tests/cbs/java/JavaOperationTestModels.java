package tools.vitruv.applications.cbs.commonalities.tests.cbs.java;

import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.newCompilationUnit;

import java.util.List;
import java.util.function.Supplier;

import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.MembersFactory;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.ParametersFactory;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypesFactory;

import tools.vitruv.applications.cbs.commonalities.tests.cbs.OperationTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase;

public class JavaOperationTestModels extends JavaTestModelsBase implements OperationTest.DomainModels {

    private static Interface newJavaInterface(InterfaceMethod... methods) {
        Interface javaInterface = ClassifiersFactory.eINSTANCE.createInterface();
        javaInterface.setName(INTERFACE_NAME);
        javaInterface.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        javaInterface.getMembers().addAll(List.of(methods));
        return javaInterface;
    }

    private static InterfaceMethod newJavaInterfaceMethod() {
        InterfaceMethod method = MembersFactory.eINSTANCE.createInterfaceMethod();
        method.setName(OPERATION_NAME);
        method.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        method.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createAbstract());
        method.setTypeReference(TypesFactory.eINSTANCE.createVoid());
        return method;
    }

    private static InterfaceMethod newJavaInterfaceMethod(TypeReference returnType, OrdinaryParameter... parameters) {
        InterfaceMethod method = newJavaInterfaceMethod();
        method.setTypeReference(returnType);
        method.getParameters().addAll(List.of(parameters));
        return method;
    }

    private static InterfaceMethod newJavaInterfaceMethodWithParameters(OrdinaryParameter... parameters) {
        InterfaceMethod method = newJavaInterfaceMethod();
        method.getParameters().addAll(List.of(parameters));
        return method;
    }

    private static OrdinaryParameter newJavaOrdinaryParameter(String name, TypeReference typeReference) {
        OrdinaryParameter parameter = ParametersFactory.eINSTANCE.createOrdinaryParameter();
        parameter.setName(name);
        parameter.setTypeReference(typeReference);
        return parameter;
    }

    public JavaOperationTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    /**
     * The repository with a compilation unit in the contracts package for the
     * interface with the given methods.
     */
    private DomainModel interfaceWithMethods(Supplier<List<InterfaceMethod>> methodsCreator) {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            CompilationUnit compilationUnit = newCompilationUnit(javaRepositoryModel.getContractsPackage(),
                    newJavaInterface(methodsCreator.get().toArray(new InterfaceMethod[0])));
            return javaRepositoryModel.getRootObjectsWith(compilationUnit);
        });
    }

    // Empty

    @Override
    public DomainModel emptyOperationCreation() {
        return interfaceWithMethods(() -> List.of(newJavaInterfaceMethod()));
    }

    // Return type

    @Override
    public DomainModel operationWithIntegerReturnCreation() {
        return interfaceWithMethods(() -> List.of(newJavaInterfaceMethod(TypesFactory.eINSTANCE.createInt())));
    }

    @Override
    public DomainModel operationWithStringReturnCreation() {
        return interfaceWithMethods(() -> List.of(newJavaInterfaceMethod(referenceJamoppType(String.class))));
    }

    // Input parameters

    @Override
    public DomainModel operationWithIntegerInputCreation() {
        return interfaceWithMethods(() -> List.of(newJavaInterfaceMethodWithParameters(
                newJavaOrdinaryParameter(INTEGER_PARAMETER_NAME, TypesFactory.eINSTANCE.createInt()))));
    }

    @Override
    public DomainModel operationWithMultiplePrimitiveInputsCreation() {
        return interfaceWithMethods(() -> List.of(newJavaInterfaceMethodWithParameters(
                newJavaOrdinaryParameter(BOOLEAN_PARAMETER_NAME, TypesFactory.eINSTANCE.createBoolean()),
                newJavaOrdinaryParameter(INTEGER_PARAMETER_NAME, TypesFactory.eINSTANCE.createInt()),
                newJavaOrdinaryParameter(DOUBLE_PARAMETER_NAME, TypesFactory.eINSTANCE.createDouble()))));
    }

    @Override
    public DomainModel operationWithStringInputCreation() {
        return interfaceWithMethods(() -> List.of(newJavaInterfaceMethodWithParameters(
                newJavaOrdinaryParameter(STRING_PARAMETER_NAME, referenceJamoppType(String.class)))));
    }

    // Mixed input and return types

    @Override
    public DomainModel operationWithMixedInputsAndReturnCreation() {
        return interfaceWithMethods(() -> List.of(newJavaInterfaceMethod(TypesFactory.eINSTANCE.createInt(),
                newJavaOrdinaryParameter(INTEGER_PARAMETER_NAME, TypesFactory.eINSTANCE.createInt()),
                newJavaOrdinaryParameter(STRING_PARAMETER_NAME, referenceJamoppType(String.class)))));
    }

    // Multiple operations

    @Override
    public DomainModel multipleOperationsCreation() {
        return interfaceWithMethods(() -> {
            InterfaceMethod method1 = newJavaInterfaceMethod(TypesFactory.eINSTANCE.createInt(),
                    newJavaOrdinaryParameter(BOOLEAN_PARAMETER_NAME, TypesFactory.eINSTANCE.createBoolean()));
            InterfaceMethod method2 = newJavaInterfaceMethod(TypesFactory.eINSTANCE.createInt(),
                    newJavaOrdinaryParameter(STRING_PARAMETER_NAME, referenceJamoppType(String.class)));
            method2.setName(OPERATION_2_NAME);
            return List.of(method1, method2);
        });
    }
}

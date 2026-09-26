package tools.vitruv.applications.cbs.commonalities.tests.cbs.java;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
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
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase;

public class JavaOperationTestModels extends JavaTestModelsBase implements OperationTest.DomainModels {

    private static Interface newJavaInterface() {
        Interface javaInterface = ClassifiersFactory.eINSTANCE.createInterface();
        javaInterface.setName(INTERFACE_NAME);
        javaInterface.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        return javaInterface;
    }

    private static InterfaceMethod newJavaInterfaceMethod(String name) {
        InterfaceMethod method = MembersFactory.eINSTANCE.createInterfaceMethod();
        method.setName(name);
        method.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        method.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createAbstract());
        method.setTypeReference(TypesFactory.eINSTANCE.createVoid());
        return method;
    }

    private static OrdinaryParameter newParameter(String name, TypeReference type) {
        OrdinaryParameter parameter = ParametersFactory.eINSTANCE.createOrdinaryParameter();
        parameter.setName(name);
        parameter.setTypeReference(type);
        return parameter;
    }

    public JavaOperationTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    @Override
    public DomainModel emptyOperationCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package contractsPackage = javaRepositoryModel.getContractsPackage();

            Interface javaInterface = newJavaInterface();
            InterfaceMethod method = newJavaInterfaceMethod(OPERATION_NAME);
            javaInterface.getMembers().add(method);

            CompilationUnit compilationUnit = JavaModelHelper.newCompilationUnit(
                    contractsPackage,
                    javaInterface);

            List<EObject> rootObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            rootObjects.add(compilationUnit);
            return rootObjects;
        });
    }

    @Override
    public DomainModel operationWithIntegerReturnCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package contractsPackage = javaRepositoryModel.getContractsPackage();

            Interface javaInterface = newJavaInterface();
            InterfaceMethod method = newJavaInterfaceMethod(OPERATION_NAME);
            method.setTypeReference(TypesFactory.eINSTANCE.createInt());
            javaInterface.getMembers().add(method);

            CompilationUnit compilationUnit = JavaModelHelper.newCompilationUnit(
                    contractsPackage,
                    javaInterface);

            List<EObject> rootObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            rootObjects.add(compilationUnit);
            return rootObjects;
        });
    }

    @Override
    public DomainModel operationWithStringReturnCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package contractsPackage = javaRepositoryModel.getContractsPackage();

            Interface javaInterface = newJavaInterface();
            InterfaceMethod method = newJavaInterfaceMethod(OPERATION_NAME);
            method.setTypeReference(referenceJamoppType(String.class));
            javaInterface.getMembers().add(method);

            CompilationUnit compilationUnit = JavaModelHelper.newCompilationUnit(
                    contractsPackage,
                    javaInterface);

            List<EObject> rootObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            rootObjects.add(compilationUnit);
            return rootObjects;
        });
    }

    @Override
    public DomainModel operationWithIntegerInputCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package contractsPackage = javaRepositoryModel.getContractsPackage();

            Interface javaInterface = newJavaInterface();
            InterfaceMethod method = newJavaInterfaceMethod(OPERATION_NAME);
            method.getParameters().add(newParameter(
                    INTEGER_PARAMETER_NAME,
                    TypesFactory.eINSTANCE.createInt()));
            javaInterface.getMembers().add(method);

            CompilationUnit compilationUnit = JavaModelHelper.newCompilationUnit(
                    contractsPackage,
                    javaInterface);

            List<EObject> rootObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            rootObjects.add(compilationUnit);
            return rootObjects;
        });
    }

    @Override
    public DomainModel operationWithMultiplePrimitiveInputsCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package contractsPackage = javaRepositoryModel.getContractsPackage();

            Interface javaInterface = newJavaInterface();
            InterfaceMethod method = newJavaInterfaceMethod(OPERATION_NAME);

            // Add boolean parameter
            method.getParameters().add(newParameter(
                    BOOLEAN_PARAMETER_NAME,
                    TypesFactory.eINSTANCE.createBoolean()));

            // Add integer parameter
            method.getParameters().add(newParameter(
                    INTEGER_PARAMETER_NAME,
                    TypesFactory.eINSTANCE.createInt()));

            // Add double parameter
            method.getParameters().add(newParameter(
                    DOUBLE_PARAMETER_NAME,
                    TypesFactory.eINSTANCE.createDouble()));

            javaInterface.getMembers().add(method);

            CompilationUnit compilationUnit = JavaModelHelper.newCompilationUnit(
                    contractsPackage,
                    javaInterface);

            List<EObject> rootObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            rootObjects.add(compilationUnit);
            return rootObjects;
        });
    }

    @Override
    public DomainModel operationWithStringInputCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package contractsPackage = javaRepositoryModel.getContractsPackage();

            Interface javaInterface = newJavaInterface();
            InterfaceMethod method = newJavaInterfaceMethod(OPERATION_NAME);
            method.getParameters().add(newParameter(
                    STRING_PARAMETER_NAME,
                    referenceJamoppType(String.class)));
            javaInterface.getMembers().add(method);

            CompilationUnit compilationUnit = JavaModelHelper.newCompilationUnit(
                    contractsPackage,
                    javaInterface);

            List<EObject> rootObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            rootObjects.add(compilationUnit);
            return rootObjects;
        });
    }

    @Override
    public DomainModel operationWithMixedInputsAndReturnCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package contractsPackage = javaRepositoryModel.getContractsPackage();

            Interface javaInterface = newJavaInterface();
            InterfaceMethod method = newJavaInterfaceMethod(OPERATION_NAME);
            method.setTypeReference(TypesFactory.eINSTANCE.createInt());

            // Add integer parameter
            method.getParameters().add(newParameter(
                    INTEGER_PARAMETER_NAME,
                    TypesFactory.eINSTANCE.createInt()));

            // Add string parameter
            method.getParameters().add(newParameter(
                    STRING_PARAMETER_NAME,
                    referenceJamoppType(String.class)));

            javaInterface.getMembers().add(method);

            CompilationUnit compilationUnit = JavaModelHelper.newCompilationUnit(
                    contractsPackage,
                    javaInterface);

            List<EObject> rootObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            rootObjects.add(compilationUnit);
            return rootObjects;
        });
    }

    @Override
    public DomainModel multipleOperationsCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package contractsPackage = javaRepositoryModel.getContractsPackage();
            Interface javaInterface = newJavaInterface();

            // First operation
            InterfaceMethod method1 = newJavaInterfaceMethod(OPERATION_NAME);
            method1.setTypeReference(TypesFactory.eINSTANCE.createInt());
            method1.getParameters().add(newParameter(
                    BOOLEAN_PARAMETER_NAME,
                    TypesFactory.eINSTANCE.createBoolean()));
            javaInterface.getMembers().add(method1);

            // Second operation
            InterfaceMethod method2 = newJavaInterfaceMethod(OPERATION_2_NAME);
            method2.setTypeReference(TypesFactory.eINSTANCE.createInt());
            method2.getParameters().add(newParameter(
                    STRING_PARAMETER_NAME,
                    referenceJamoppType(String.class)));
            javaInterface.getMembers().add(method2);

            CompilationUnit compilationUnit = JavaModelHelper.newCompilationUnit(
                    contractsPackage,
                    javaInterface);

            List<EObject> rootObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            rootObjects.add(compilationUnit);
            return rootObjects;
        });
    }
}
package tools.vitruv.applications.cbs.commonalities.tests.oo.java;

import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.newCompilationUnit;

import java.util.List;
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
		InterfaceMethod javaMethod = MembersFactory.eINSTANCE.createInterfaceMethod();
		javaMethod.setName(METHOD_NAME);
		javaMethod.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
		javaMethod.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createAbstract());
		javaMethod.setTypeReference(TypesFactory.eINSTANCE.createVoid());
		return javaMethod;
	}

	private static Class newOtherJavaClass() {
		Class javaClass = ClassifiersFactory.eINSTANCE.createClass();
		javaClass.setName(OTHER_CLASS_NAME);
		javaClass.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
		return javaClass;
	}

	private static OrdinaryParameter newJavaOrdinaryParameter() {
		return ParametersFactory.eINSTANCE.createOrdinaryParameter();
	}

	public JavaInterfaceMethodTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	// Basic

	@Override
	public DomainModel basicInterfaceMethodCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Interface javaInterface = newJavaInterface();
			javaInterface.getMembers().add(newJavaInterfaceMethod());
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaInterface);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	// Static

	@Override
	public DomainModel staticInterfaceMethodCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Interface javaInterface = newJavaInterface();
			InterfaceMethod javaMethod = newJavaInterfaceMethod();
			javaMethod.getAnnotationsAndModifiers().removeIf(Abstract.class::isInstance);
			javaMethod.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createStatic());
			javaInterface.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaInterface);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	// Return type

	@Override
	public DomainModel interfaceMethodWithIntegerReturnCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Interface javaInterface = newJavaInterface();
			InterfaceMethod javaMethod = newJavaInterfaceMethod();
			javaMethod.setTypeReference(TypesFactory.eINSTANCE.createInt());
			javaInterface.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaInterface);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel interfaceMethodWithStringReturnCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Interface javaInterface = newJavaInterface();
			InterfaceMethod javaMethod = newJavaInterfaceMethod();
			javaMethod.setTypeReference(referenceJamoppType(String.class));
			javaInterface.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaInterface);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel interfaceMethodWithClassReturnCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class otherJavaClass = newOtherJavaClass();
			CompilationUnit javaClassCompilationUnit = newCompilationUnit(javaPackage, otherJavaClass);
			Interface javaInterface = newJavaInterface();
			InterfaceMethod javaMethod = newJavaInterfaceMethod();
			javaMethod.setTypeReference(JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass));
			javaInterface.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaInterface);
			return List.of(
					javaPackage,
					javaClassCompilationUnit,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel interfaceMethodWithSelfReturnCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Interface javaInterface = newJavaInterface();
			InterfaceMethod javaMethod = newJavaInterfaceMethod();
			javaMethod.setTypeReference(JavaModificationUtil.createNamespaceClassifierReference(javaInterface));
			javaInterface.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaInterface);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	// Input parameters

	@Override
	public DomainModel interfaceMethodWithIntegerInputCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Interface javaInterface = newJavaInterface();
			InterfaceMethod javaMethod = newJavaInterfaceMethod();
			OrdinaryParameter integerParameter = newJavaOrdinaryParameter();
			integerParameter.setName(INTEGER_PARAMETER_NAME);
			integerParameter.setTypeReference(TypesFactory.eINSTANCE.createInt());
			javaMethod.getParameters().add(integerParameter);
			javaInterface.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaInterface);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel interfaceMethodWithMultiplePrimitiveInputsCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Interface javaInterface = newJavaInterface();
			InterfaceMethod javaMethod = newJavaInterfaceMethod();
			OrdinaryParameter booleanParameter = newJavaOrdinaryParameter();
			booleanParameter.setName(BOOLEAN_PARAMETER_NAME);
			booleanParameter.setTypeReference(TypesFactory.eINSTANCE.createBoolean());
			javaMethod.getParameters().add(booleanParameter);
			OrdinaryParameter integerParameter = newJavaOrdinaryParameter();
			integerParameter.setName(INTEGER_PARAMETER_NAME);
			integerParameter.setTypeReference(TypesFactory.eINSTANCE.createInt());
			javaMethod.getParameters().add(integerParameter);
			OrdinaryParameter doubleParameter = newJavaOrdinaryParameter();
			doubleParameter.setName(DOUBLE_PARAMETER_NAME);
			doubleParameter.setTypeReference(TypesFactory.eINSTANCE.createDouble());
			javaMethod.getParameters().add(doubleParameter);
			javaInterface.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaInterface);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel interfaceMethodWithStringInputCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Interface javaInterface = newJavaInterface();
			InterfaceMethod javaMethod = newJavaInterfaceMethod();
			OrdinaryParameter stringParameter = newJavaOrdinaryParameter();
			stringParameter.setName(STRING_PARAMETER_NAME);
			stringParameter.setTypeReference(referenceJamoppType(String.class));
			javaMethod.getParameters().add(stringParameter);
			javaInterface.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaInterface);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel interfaceMethodWithClassInputCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class otherJavaClass = newOtherJavaClass();
			CompilationUnit javaClassCompilationUnit = newCompilationUnit(javaPackage, otherJavaClass);
			Interface javaInterface = newJavaInterface();
			InterfaceMethod javaMethod = newJavaInterfaceMethod();
			OrdinaryParameter classParameter = newJavaOrdinaryParameter();
			classParameter.setName(CLASS_PARAMETER_NAME);
			classParameter.setTypeReference(JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass));
			javaMethod.getParameters().add(classParameter);
			javaInterface.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaInterface);
			return List.of(
					javaPackage,
					javaClassCompilationUnit,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel interfaceMethodWithSelfInputCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Interface javaInterface = newJavaInterface();
			InterfaceMethod javaMethod = newJavaInterfaceMethod();
			OrdinaryParameter ownTypeParameter = newJavaOrdinaryParameter();
			ownTypeParameter.setName(OWN_TYPE_PARAMETER_NAME);
			ownTypeParameter.setTypeReference(JavaModificationUtil.createNamespaceClassifierReference(javaInterface));
			javaMethod.getParameters().add(ownTypeParameter);
			javaInterface.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaInterface);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel interfaceMethodWithMixedInputsCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class otherJavaClass = newOtherJavaClass();
			CompilationUnit javaClassCompilationUnit = newCompilationUnit(javaPackage, otherJavaClass);
			Interface javaInterface = newJavaInterface();
			InterfaceMethod javaMethod = newJavaInterfaceMethod();
			OrdinaryParameter integerParameter = newJavaOrdinaryParameter();
			integerParameter.setName(INTEGER_PARAMETER_NAME);
			integerParameter.setTypeReference(TypesFactory.eINSTANCE.createInt());
			javaMethod.getParameters().add(integerParameter);
			OrdinaryParameter stringParameter = newJavaOrdinaryParameter();
			stringParameter.setName(STRING_PARAMETER_NAME);
			stringParameter.setTypeReference(referenceJamoppType(String.class));
			javaMethod.getParameters().add(stringParameter);
			OrdinaryParameter classParameter = newJavaOrdinaryParameter();
			classParameter.setName(CLASS_PARAMETER_NAME);
			classParameter.setTypeReference(JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass));
			javaMethod.getParameters().add(classParameter);
			javaInterface.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaInterface);
			return List.of(
					javaPackage,
					javaClassCompilationUnit,
					javaCompilationUnit);
		});
	}

	// Mixed input and return types

	@Override
	public DomainModel interfaceMethodWithMixedInputsAndReturnCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class otherJavaClass = newOtherJavaClass();
			CompilationUnit javaClassCompilationUnit = newCompilationUnit(javaPackage, otherJavaClass);
			Interface javaInterface = newJavaInterface();
			InterfaceMethod javaMethod = newJavaInterfaceMethod();
			javaMethod.setTypeReference(TypesFactory.eINSTANCE.createInt());
			OrdinaryParameter integerParameter = newJavaOrdinaryParameter();
			integerParameter.setName(INTEGER_PARAMETER_NAME);
			integerParameter.setTypeReference(TypesFactory.eINSTANCE.createInt());
			javaMethod.getParameters().add(integerParameter);
			OrdinaryParameter stringParameter = newJavaOrdinaryParameter();
			stringParameter.setName(STRING_PARAMETER_NAME);
			stringParameter.setTypeReference(referenceJamoppType(String.class));
			javaMethod.getParameters().add(stringParameter);
			OrdinaryParameter classParameter = newJavaOrdinaryParameter();
			classParameter.setName(CLASS_PARAMETER_NAME);
			classParameter.setTypeReference(JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass));
			javaMethod.getParameters().add(classParameter);
			javaInterface.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaInterface);
			return List.of(
					javaPackage,
					javaClassCompilationUnit,
					javaCompilationUnit);
		});
	}

	// Multiple methods

	@Override
	public DomainModel multipleInterfaceMethodsCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Interface javaInterface = newJavaInterface();
			InterfaceMethod javaMethod = newJavaInterfaceMethod();
			javaMethod.setTypeReference(TypesFactory.eINSTANCE.createInt());
			OrdinaryParameter booleanParameter = newJavaOrdinaryParameter();
			booleanParameter.setName(BOOLEAN_PARAMETER_NAME);
			booleanParameter.setTypeReference(TypesFactory.eINSTANCE.createBoolean());
			javaMethod.getParameters().add(booleanParameter);
			javaInterface.getMembers().add(javaMethod);
			InterfaceMethod javaMethod2 = newJavaInterfaceMethod();
			javaMethod2.setName(METHOD_2_NAME);
			javaMethod2.setTypeReference(TypesFactory.eINSTANCE.createInt());
			OrdinaryParameter stringParameter = newJavaOrdinaryParameter();
			stringParameter.setName(STRING_PARAMETER_NAME);
			stringParameter.setTypeReference(referenceJamoppType(String.class));
			javaMethod2.getParameters().add(stringParameter);
			javaInterface.getMembers().add(javaMethod2);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaInterface);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}
}

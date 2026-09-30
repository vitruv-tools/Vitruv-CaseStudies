package tools.vitruv.applications.cbs.commonalities.tests.oo.java;

import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.newCompilationUnit;

import java.util.List;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.MembersFactory;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.ParametersFactory;
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
		ClassMethod javaMethod = MembersFactory.eINSTANCE.createClassMethod();
		javaMethod.setName(METHOD_NAME);
		javaMethod.setTypeReference(TypesFactory.eINSTANCE.createVoid());
		return javaMethod;
	}

	private static ClassMethod newJavaClassMethod() {
		ClassMethod javaMethod = newDefaultJavaClassMethod();
		javaMethod.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
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

	public JavaClassMethodTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	// Basic

	@Override
	public DomainModel basicClassMethodCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class javaClass = newJavaClass();
			javaClass.getMembers().add(newJavaClassMethod());
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	// Visibility

	@Override
	public DomainModel publicClassMethodCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class javaClass = newJavaClass();
			ClassMethod javaMethod = newDefaultJavaClassMethod();
			javaMethod.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
			javaClass.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel protectedClassMethodCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class javaClass = newJavaClass();
			ClassMethod javaMethod = newDefaultJavaClassMethod();
			javaMethod.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createProtected());
			javaClass.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel packagePrivateClassMethodCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class javaClass = newJavaClass();
			javaClass.getMembers().add(newDefaultJavaClassMethod());
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel privateClassMethodCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class javaClass = newJavaClass();
			ClassMethod javaMethod = newDefaultJavaClassMethod();
			javaMethod.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPrivate());
			javaClass.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	// Modifiers

	@Override
	public DomainModel finalClassMethodCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class javaClass = newJavaClass();
			ClassMethod javaMethod = newJavaClassMethod();
			javaMethod.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createFinal());
			javaClass.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel abstractClassMethodCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class javaClass = newJavaClass();
			ClassMethod javaMethod = newJavaClassMethod();
			javaMethod.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createAbstract());
			javaClass.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel staticClassMethodCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class javaClass = newJavaClass();
			ClassMethod javaMethod = newJavaClassMethod();
			javaMethod.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createStatic());
			javaClass.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	// Return type

	@Override
	public DomainModel classMethodWithIntegerReturnCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class javaClass = newJavaClass();
			ClassMethod javaMethod = newJavaClassMethod();
			javaMethod.setTypeReference(TypesFactory.eINSTANCE.createInt());
			javaClass.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel classMethodWithStringReturnCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class javaClass = newJavaClass();
			ClassMethod javaMethod = newJavaClassMethod();
			javaMethod.setTypeReference(referenceJamoppType(String.class));
			javaClass.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel classMethodWithClassReturnCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class otherJavaClass = newOtherJavaClass();
			CompilationUnit javaClassCompilationUnit = newCompilationUnit(javaPackage, otherJavaClass);
			Class javaClass = newJavaClass();
			ClassMethod javaMethod = newJavaClassMethod();
			javaMethod.setTypeReference(JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass));
			javaClass.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaClassCompilationUnit,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel classMethodWithSelfReturnCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class javaClass = newJavaClass();
			ClassMethod javaMethod = newJavaClassMethod();
			javaMethod.setTypeReference(JavaModificationUtil.createNamespaceClassifierReference(javaClass));
			javaClass.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	// Input parameters

	@Override
	public DomainModel classMethodWithIntegerInputCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class javaClass = newJavaClass();
			ClassMethod javaMethod = newJavaClassMethod();
			OrdinaryParameter integerParameter = newJavaOrdinaryParameter();
			integerParameter.setName(INTEGER_PARAMETER_NAME);
			integerParameter.setTypeReference(TypesFactory.eINSTANCE.createInt());
			javaMethod.getParameters().add(integerParameter);
			javaClass.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel classMethodWithMultiplePrimitiveInputsCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class javaClass = newJavaClass();
			ClassMethod javaMethod = newJavaClassMethod();
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
			javaClass.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel classMethodWithStringInputCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class javaClass = newJavaClass();
			ClassMethod javaMethod = newJavaClassMethod();
			OrdinaryParameter stringParameter = newJavaOrdinaryParameter();
			stringParameter.setName(STRING_PARAMETER_NAME);
			stringParameter.setTypeReference(referenceJamoppType(String.class));
			javaMethod.getParameters().add(stringParameter);
			javaClass.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel classMethodWithClassInputCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class otherJavaClass = newOtherJavaClass();
			CompilationUnit javaClassCompilationUnit = newCompilationUnit(javaPackage, otherJavaClass);
			Class javaClass = newJavaClass();
			ClassMethod javaMethod = newJavaClassMethod();
			OrdinaryParameter classParameter = newJavaOrdinaryParameter();
			classParameter.setName(CLASS_PARAMETER_NAME);
			classParameter.setTypeReference(JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass));
			javaMethod.getParameters().add(classParameter);
			javaClass.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaClassCompilationUnit,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel classMethodWithSelfInputCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class javaClass = newJavaClass();
			ClassMethod javaMethod = newJavaClassMethod();
			OrdinaryParameter ownTypeParameter = newJavaOrdinaryParameter();
			ownTypeParameter.setName(OWN_TYPE_PARAMETER_NAME);
			ownTypeParameter.setTypeReference(JavaModificationUtil.createNamespaceClassifierReference(javaClass));
			javaMethod.getParameters().add(ownTypeParameter);
			javaClass.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel classMethodWithMixedInputsCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class otherJavaClass = newOtherJavaClass();
			CompilationUnit javaClassCompilationUnit = newCompilationUnit(javaPackage, otherJavaClass);
			Class javaClass = newJavaClass();
			ClassMethod javaMethod = newJavaClassMethod();
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
			javaClass.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaClassCompilationUnit,
					javaCompilationUnit);
		});
	}

	// Mixed input and return types

	@Override
	public DomainModel classMethodWithMixedInputsAndReturnCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class otherJavaClass = newOtherJavaClass();
			CompilationUnit javaClassCompilationUnit = newCompilationUnit(javaPackage, otherJavaClass);
			Class javaClass = newJavaClass();
			ClassMethod javaMethod = newJavaClassMethod();
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
			javaClass.getMembers().add(javaMethod);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaClassCompilationUnit,
					javaCompilationUnit);
		});
	}

	// Multiple methods

	@Override
	public DomainModel multipleClassMethodsCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class javaClass = newJavaClass();
			ClassMethod javaMethod = newJavaClassMethod();
			javaMethod.setTypeReference(TypesFactory.eINSTANCE.createInt());
			OrdinaryParameter booleanParameter = newJavaOrdinaryParameter();
			booleanParameter.setName(BOOLEAN_PARAMETER_NAME);
			booleanParameter.setTypeReference(TypesFactory.eINSTANCE.createBoolean());
			javaMethod.getParameters().add(booleanParameter);
			javaClass.getMembers().add(javaMethod);
			ClassMethod javaMethod2 = newJavaClassMethod();
			javaMethod2.setName(METHOD_2_NAME);
			javaMethod2.setTypeReference(TypesFactory.eINSTANCE.createInt());
			OrdinaryParameter stringParameter = newJavaOrdinaryParameter();
			stringParameter.setName(STRING_PARAMETER_NAME);
			stringParameter.setTypeReference(referenceJamoppType(String.class));
			javaMethod2.getParameters().add(stringParameter);
			javaClass.getMembers().add(javaMethod2);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}
}

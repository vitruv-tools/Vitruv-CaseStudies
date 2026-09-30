package tools.vitruv.applications.cbs.commonalities.tests.oo.java;

import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.newCompilationUnit;

import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.MembersFactory;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.ParametersFactory;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypesFactory;
import tools.vitruv.applications.cbs.commonalities.tests.oo.ConstructorTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase;
import tools.vitruv.applications.util.temporary.java.JavaModificationUtil;

public class JavaConstructorTestModels extends JavaTestModelsBase implements ConstructorTest.DomainModels {

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

	// Creates a constructor without any visibility modifier:
	private static Constructor newDefaultJavaConstructor() {
		Constructor javaConstructor = MembersFactory.eINSTANCE.createConstructor();
		javaConstructor.setName(CLASS_NAME);
		return javaConstructor;
	}

	private static Constructor newJavaConstructor() {
		Constructor javaConstructor = newDefaultJavaConstructor();
		javaConstructor.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
		return javaConstructor;
	}

	private static Constructor newJavaConstructor(OrdinaryParameter... javaParameters) {
		Constructor javaConstructor = newJavaConstructor();
		javaConstructor.getParameters().addAll(List.of(javaParameters));
		return javaConstructor;
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

	private static OrdinaryParameter newJavaOrdinaryParameter(String name, TypeReference typeReference) {
		OrdinaryParameter javaParameter = newJavaOrdinaryParameter();
		javaParameter.setName(name);
		javaParameter.setTypeReference(typeReference);
		return javaParameter;
	}

	private static Class withConstructors(Class javaClass, Constructor... javaConstructors) {
		javaClass.getMembers().addAll(List.of(javaConstructors));
		return javaClass;
	}

	private static List<EObject> newJavaClassInPackage(Class javaClass) {
		Package javaPackage = newJavaPackage();
		CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
		return List.of(
				javaPackage,
				javaCompilationUnit);
	}

	public JavaConstructorTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	// Basic

	@Override
	public DomainModel basicConstructorCreation() {
		return newModel(() -> newJavaClassInPackage(withConstructors(newJavaClass(), newJavaConstructor())));
	}

	// Visibility

	@Override
	public DomainModel publicConstructorCreation() {
		return newModel(() -> {
			Constructor javaConstructor = newDefaultJavaConstructor();
			javaConstructor.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
			return newJavaClassInPackage(withConstructors(newJavaClass(), javaConstructor));
		});
	}

	@Override
	public DomainModel protectedConstructorCreation() {
		return newModel(() -> {
			Constructor javaConstructor = newDefaultJavaConstructor();
			javaConstructor.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createProtected());
			return newJavaClassInPackage(withConstructors(newJavaClass(), javaConstructor));
		});
	}

	@Override
	public DomainModel packagePrivateConstructorCreation() {
		return newModel(() -> newJavaClassInPackage(withConstructors(newJavaClass(), newDefaultJavaConstructor())));
	}

	@Override
	public DomainModel privateConstructorCreation() {
		return newModel(() -> {
			Constructor javaConstructor = newDefaultJavaConstructor();
			javaConstructor.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPrivate());
			return newJavaClassInPackage(withConstructors(newJavaClass(), javaConstructor));
		});
	}

	// Input parameters

	@Override
	public DomainModel constructorWithIntegerInputCreation() {
		return newModel(() -> newJavaClassInPackage(withConstructors(newJavaClass(), newJavaConstructor(
				newJavaOrdinaryParameter(INTEGER_PARAMETER_NAME, TypesFactory.eINSTANCE.createInt())))));
	}

	@Override
	public DomainModel constructorWithMultiplePrimitiveInputsCreation() {
		return newModel(() -> newJavaClassInPackage(withConstructors(newJavaClass(), newJavaConstructor(
				newJavaOrdinaryParameter(BOOLEAN_PARAMETER_NAME, TypesFactory.eINSTANCE.createBoolean()),
				newJavaOrdinaryParameter(INTEGER_PARAMETER_NAME, TypesFactory.eINSTANCE.createInt()),
				newJavaOrdinaryParameter(DOUBLE_PARAMETER_NAME, TypesFactory.eINSTANCE.createDouble())))));
	}

	@Override
	public DomainModel constructorWithStringInputCreation() {
		return newModel(() -> newJavaClassInPackage(withConstructors(newJavaClass(), newJavaConstructor(
				newJavaOrdinaryParameter(STRING_PARAMETER_NAME, referenceJamoppType(String.class))))));
	}

	@Override
	public DomainModel constructorWithClassInputCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class otherJavaClass = newOtherJavaClass();
			CompilationUnit javaClassCompilationUnit = newCompilationUnit(javaPackage, otherJavaClass);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage,
					withConstructors(newJavaClass(), newJavaConstructor(
							newJavaOrdinaryParameter(CLASS_PARAMETER_NAME,
									JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass)))));
			return List.of(
					javaPackage,
					javaClassCompilationUnit,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel constructorWithSelfInputCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class javaClass = newJavaClass();
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage,
					withConstructors(javaClass, newJavaConstructor(
							newJavaOrdinaryParameter(OWN_TYPE_PARAMETER_NAME,
									JavaModificationUtil.createNamespaceClassifierReference(javaClass)))));
			return List.of(
					javaPackage,
					javaCompilationUnit);
		});
	}

	@Override
	public DomainModel constructorWithMixedInputsCreation() {
		return newModel(() -> {
			Package javaPackage = newJavaPackage();
			Class otherJavaClass = newOtherJavaClass();
			CompilationUnit javaClassCompilationUnit = newCompilationUnit(javaPackage, otherJavaClass);
			CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage,
					withConstructors(newJavaClass(), newJavaConstructor(
							newJavaOrdinaryParameter(INTEGER_PARAMETER_NAME, TypesFactory.eINSTANCE.createInt()),
							newJavaOrdinaryParameter(STRING_PARAMETER_NAME, referenceJamoppType(String.class)),
							newJavaOrdinaryParameter(CLASS_PARAMETER_NAME,
									JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass)))));
			return List.of(
					javaPackage,
					javaClassCompilationUnit,
					javaCompilationUnit);
		});
	}

	// Multiple constructors

	@Override
	public DomainModel multipleConstructorsCreation() {
		return newModel(() -> newJavaClassInPackage(withConstructors(newJavaClass(),
				newJavaConstructor(
						newJavaOrdinaryParameter(BOOLEAN_PARAMETER_NAME, TypesFactory.eINSTANCE.createBoolean())),
				newJavaConstructor(
						newJavaOrdinaryParameter(STRING_PARAMETER_NAME, referenceJamoppType(String.class))))));
	}
}

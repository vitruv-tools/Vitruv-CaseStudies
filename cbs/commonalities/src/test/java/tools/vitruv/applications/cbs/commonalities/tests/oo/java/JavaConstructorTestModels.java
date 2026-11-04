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
import org.emftext.language.java.modifiers.Modifier;
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
        Constructor constructor = MembersFactory.eINSTANCE.createConstructor();
        constructor.setName(CLASS_NAME);
        return constructor;
    }

    private static Constructor newJavaConstructor() {
        Constructor constructor = newDefaultJavaConstructor();
        constructor.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        return constructor;
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

    private static Constructor withModifier(Constructor constructor, Modifier modifier) {
        constructor.getAnnotationsAndModifiers().add(modifier);
        return constructor;
    }

    private static Constructor withParameters(Constructor constructor, OrdinaryParameter... parameters) {
        constructor.getParameters().addAll(List.of(parameters));
        return constructor;
    }

    private static Class withConstructors(Class javaClass, Constructor... constructors) {
        javaClass.getMembers().addAll(List.of(constructors));
        return javaClass;
    }

    /**
     * The package and the compilation unit of the default class with the given
     * constructors.
     */
    private static List<EObject> classWithConstructors(Constructor... constructors) {
        Package javaPackage = newJavaPackage();
        CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage,
                withConstructors(newJavaClass(), constructors));
        return List.of(javaPackage, javaCompilationUnit);
    }

    public JavaConstructorTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    // Basic

    @Override
    public DomainModel basicConstructorCreation() {
        return newModel(() -> classWithConstructors(newJavaConstructor()));
    }

    // Visibility

    @Override
    public DomainModel publicConstructorCreation() {
        return newModel(() -> classWithConstructors(
                withModifier(newDefaultJavaConstructor(), ModifiersFactory.eINSTANCE.createPublic())));
    }

    @Override
    public DomainModel protectedConstructorCreation() {
        return newModel(() -> classWithConstructors(
                withModifier(newDefaultJavaConstructor(), ModifiersFactory.eINSTANCE.createProtected())));
    }

    @Override
    public DomainModel packagePrivateConstructorCreation() {
        return newModel(() -> classWithConstructors(newDefaultJavaConstructor()));
    }

    @Override
    public DomainModel privateConstructorCreation() {
        return newModel(() -> classWithConstructors(
                withModifier(newDefaultJavaConstructor(), ModifiersFactory.eINSTANCE.createPrivate())));
    }

    // Input parameters

    @Override
    public DomainModel constructorWithIntegerInputCreation() {
        return newModel(() -> classWithConstructors(withParameters(newJavaConstructor(),
                newJavaOrdinaryParameter(INTEGER_PARAMETER_NAME, TypesFactory.eINSTANCE.createInt()))));
    }

    @Override
    public DomainModel constructorWithMultiplePrimitiveInputsCreation() {
        return newModel(() -> classWithConstructors(withParameters(newJavaConstructor(),
                newJavaOrdinaryParameter(BOOLEAN_PARAMETER_NAME, TypesFactory.eINSTANCE.createBoolean()),
                newJavaOrdinaryParameter(INTEGER_PARAMETER_NAME, TypesFactory.eINSTANCE.createInt()),
                newJavaOrdinaryParameter(DOUBLE_PARAMETER_NAME, TypesFactory.eINSTANCE.createDouble()))));
    }

    @Override
    public DomainModel constructorWithStringInputCreation() {
        return newModel(() -> classWithConstructors(withParameters(newJavaConstructor(),
                newJavaOrdinaryParameter(STRING_PARAMETER_NAME, referenceJamoppType(String.class)))));
    }

    @Override
    public DomainModel constructorWithClassInputCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage();
            Class otherJavaClass = newOtherJavaClass();
            CompilationUnit javaClassCompilationUnit = newCompilationUnit(javaPackage, otherJavaClass);
            Constructor constructor = withParameters(newJavaConstructor(),
                    newJavaOrdinaryParameter(CLASS_PARAMETER_NAME,
                            JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass)));
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage,
                    withConstructors(newJavaClass(), constructor));
            return List.of(javaPackage, javaClassCompilationUnit, javaCompilationUnit);
        });
    }

    @Override
    public DomainModel constructorWithSelfInputCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage();
            Class javaClass = newJavaClass();
            withConstructors(javaClass, withParameters(newJavaConstructor(),
                    newJavaOrdinaryParameter(OWN_TYPE_PARAMETER_NAME,
                            JavaModificationUtil.createNamespaceClassifierReference(javaClass))));
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
            return List.of(javaPackage, javaCompilationUnit);
        });
    }

    @Override
    public DomainModel constructorWithMixedInputsCreation() {
        return newModel(() -> {
            Package javaPackage = newJavaPackage();
            Class otherJavaClass = newOtherJavaClass();
            CompilationUnit javaClassCompilationUnit = newCompilationUnit(javaPackage, otherJavaClass);
            Constructor constructor = withParameters(newJavaConstructor(),
                    newJavaOrdinaryParameter(INTEGER_PARAMETER_NAME, TypesFactory.eINSTANCE.createInt()),
                    newJavaOrdinaryParameter(STRING_PARAMETER_NAME, referenceJamoppType(String.class)),
                    newJavaOrdinaryParameter(CLASS_PARAMETER_NAME,
                            JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass)));
            CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage,
                    withConstructors(newJavaClass(), constructor));
            return List.of(javaPackage, javaClassCompilationUnit, javaCompilationUnit);
        });
    }

    // Multiple constructors

    @Override
    public DomainModel multipleConstructorsCreation() {
        return newModel(() -> classWithConstructors(
                withParameters(newJavaConstructor(),
                        newJavaOrdinaryParameter(BOOLEAN_PARAMETER_NAME, TypesFactory.eINSTANCE.createBoolean())),
                withParameters(newJavaConstructor(),
                        newJavaOrdinaryParameter(STRING_PARAMETER_NAME, referenceJamoppType(String.class)))));
    }
}

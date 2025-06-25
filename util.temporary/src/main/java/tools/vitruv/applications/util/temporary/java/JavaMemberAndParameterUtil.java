package tools.vitruv.applications.util.temporary.java;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.activextendannotations.Utility;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.expressions.AssignmentExpression;
import org.emftext.language.java.expressions.Expression;
import org.emftext.language.java.expressions.ExpressionsFactory;
import org.emftext.language.java.literals.LiteralsFactory;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.EnumConstant;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.MembersFactory;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.modifiers.Public;
import org.emftext.language.java.operators.OperatorsFactory;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.parameters.ParametersFactory;
import org.emftext.language.java.parameters.Parametrizable;
import org.emftext.language.java.references.IdentifierReference;
import org.emftext.language.java.references.ReferencesFactory;
import org.emftext.language.java.references.SelfReference;
import org.emftext.language.java.statements.ExpressionStatement;
import org.emftext.language.java.statements.Return;
import org.emftext.language.java.statements.StatementsFactory;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypesFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Utility class for creating and manipulating JaMoPP objects for Java members (fields, methods, constructors) and parameters.
 */
@Utility
public final class JavaMemberAndParameterUtil {
    private static final Logger logger = Logger.getLogger(JavaMemberAndParameterUtil.class.getSimpleName());

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private JavaMemberAndParameterUtil() {
        // Utility class
    }

    /**
     * Creates a simple public Java method with the given name, no return type (void), and no parameters.
     *
     * @param name The name of the method.
     * @return A new {@link ClassMethod} instance.
     */
    public static ClassMethod createSimpleJavaOperation(final String name) {
        return createJavaClassMethod(name, null, JavaVisibility.PUBLIC, false, false, null);
    }

    /**
     * Creates a Java class method with the specified properties.
     *
     * @param name       The name of the method.
     * @param returnType The return type of the method. Can be null for void.
     * @param visibility The visibility of the method.
     * @param isAbstract True if the method is abstract, false otherwise.
     * @param isStatic   True if the method is static, false otherwise.
     * @param params     A list of parameters for the method. Can be null or empty.
     * @return A new {@link ClassMethod} instance.
     */
    public static ClassMethod createJavaClassMethod(final String name, final TypeReference returnType, final JavaVisibility visibility, final boolean isAbstract, final boolean isStatic, final List<Parameter> params) {
        ClassMethod jMethod = MembersFactory.eINSTANCE.createClassMethod();
        JavaModifierUtil.setName(jMethod, name);
        JavaTypeUtil.setTypeReference(jMethod, returnType);
        JavaModifierUtil.setJavaVisibilityModifier(jMethod, visibility);
        JavaModifierUtil.setAbstract(jMethod, isAbstract);
        JavaModifierUtil.setStatic(jMethod, isStatic);
        addParametersIfNotNull(jMethod, params);
        return jMethod;
    }

    /**
     * Creates a Java interface method with the specified properties. Interface methods are implicitly public.
     *
     * @param name       The name of the method.
     * @param returnType The return type of the method. Can be null for void.
     * @param params     A list of parameters for the method. Can be null or empty.
     * @return A new {@link InterfaceMethod} instance.
     */
    public static InterfaceMethod createJavaInterfaceMethod(final String name, final TypeReference returnType, final List<Parameter> params) {
        InterfaceMethod jMethod = MembersFactory.eINSTANCE.createInterfaceMethod();
        JavaModifierUtil.setName(jMethod, name);
        JavaTypeUtil.setTypeReference(jMethod, returnType);
        jMethod.makePublic();
        addParametersIfNotNull(jMethod, params);
        return jMethod;
    }

    /**
     * Creates a Java field (attribute) with the specified properties.
     *
     * @param name       The name of the field.
     * @param type       The type of the field.
     * @param visibility The visibility of the field.
     * @param isFinal    True if the field is final, false otherwise.
     * @param isStatic   True if the field is static, false otherwise.
     * @return A new {@link Field} instance.
     */
    public static Field createJavaAttribute(final String name, final TypeReference type, final JavaVisibility visibility, final boolean isFinal, final boolean isStatic) {
        Field jAttribute = MembersFactory.eINSTANCE.createField();
        JavaModifierUtil.setName(jAttribute, name);
        JavaModifierUtil.setJavaVisibilityModifier(jAttribute, visibility);
        JavaModifierUtil.setFinal(jAttribute, isFinal);
        JavaModifierUtil.setStatic(jAttribute, isStatic);
        JavaTypeUtil.setTypeReference(jAttribute, type);
        return jAttribute;
    }

    /**
     * Creates a Java method parameter.
     *
     * @param name The name of the parameter.
     * @param type The type of the parameter.
     * @return A new {@link OrdinaryParameter} instance.
     */
    public static OrdinaryParameter createJavaParameter(final String name, final TypeReference type) {
        OrdinaryParameter param = ParametersFactory.eINSTANCE.createOrdinaryParameter();
        param.setName(name);
        param.setTypeReference(type);
        return param;
    }

    /**
     * Creates a Java enum constant with the specified name.
     *
     * @param name The name of the enum constant.
     * @return A new {@link EnumConstant} instance.
     */
    public static EnumConstant createJavaEnumConstant(final String name) {
        EnumConstant constant = MembersFactory.eINSTANCE.createEnumConstant();
        constant.setName(name);
        return constant;
    }

    /**
     * Creates a list of {@link EnumConstant}s from a list of names.
     *
     * @param enumConstantNames A list of names for the enum constants.
     * @return A list of new {@link EnumConstant} instances.
     */
    public static List<EnumConstant> createJavaEnumConstantsFromList(final List<String> enumConstantNames) {
        if (enumConstantNames == null) {
            return Collections.emptyList();
        }
        return enumConstantNames.stream()
                .map(JavaMemberAndParameterUtil::createJavaEnumConstant)
                .collect(Collectors.toList());
    }

    /**
     * Creates a public getter method for the given field.
     *
     * @param jAttribute The field for which to create a getter.
     * @return A new {@link ClassMethod} representing the getter.
     */
    public static ClassMethod createGetterForAttribute(final Field jAttribute) {
        return createJavaGetterForAttribute(jAttribute, JavaVisibility.PUBLIC);
    }

    /**
     * Creates a public setter method for the given field.
     *
     * @param jAttribute The field for which to create a setter.
     * @return A new {@link ClassMethod} representing the setter.
     */
    public static ClassMethod createSetterForAttribute(final Field jAttribute) {
        return createJavaSetterForAttribute(jAttribute, JavaVisibility.PUBLIC);
    }

    /**
     * Creates a constructor, adds it to the given class, and returns it.
     *
     * @param jClass     The class to add the constructor to.
     * @param visibility The visibility of the constructor.
     * @param params     A list of parameters for the constructor. Can be null or empty.
     * @return The newly created and added {@link Constructor}.
     */
    public static Constructor addJavaConstructorToClass(final Class jClass, final JavaVisibility visibility, final List<Parameter> params) {
        Constructor constructor = MembersFactory.eINSTANCE.createConstructor();
        JavaModifierUtil.setName(constructor, jClass.getName());
        addParametersIfNotNull(constructor, params);
        JavaModifierUtil.setJavaVisibilityModifier(constructor, visibility);
        jClass.getMembers().add(constructor);
        return constructor;
    }

    /**
     * Creates a constructor with no parameters, adds it to the given class, and returns it.
     *
     * @param jClass     The class to add the constructor to.
     * @param visibility The visibility of the constructor.
     * @return The newly created and added {@link Constructor}.
     */
    public static Constructor createJavaConstructorAndAddToClass(final Class jClass, final JavaVisibility visibility) {
        Constructor constructor = MembersFactory.eINSTANCE.createConstructor();
        constructor.setName(jClass.getName());
        JavaModifierUtil.setJavaVisibilityModifier(constructor, visibility);
        jClass.getMembers().add(constructor);
        return constructor;
    }

    /**
     * Gets the first existing constructor in a class, or creates a new one if none exists.
     * The new constructor will be public by default.
     *
     * @param javaClass The class to search for or add a constructor to.
     * @return An existing or newly created {@link Constructor}.
     */
    public static Constructor getOrCreateConstructorToClass(final Class javaClass) {
        return javaClass.getMembers().stream()
                .filter(Constructor.class::isInstance)
                .map(Constructor.class::cast)
                .findFirst()
                .orElseGet(() -> {
                    Constructor newConstructor = MembersFactory.eINSTANCE.createConstructor();
                    return JavaModificationUtil.addConstructorToClass(newConstructor, javaClass);
                });
    }

    /**
     * Creates a getter method for the given field with the specified visibility.
     * The method body will contain "return this.fieldName;".
     *
     * @param jAttribute The field for which to create a getter.
     * @param visibility The desired visibility of the getter.
     * @return A new {@link ClassMethod} representing the getter.
     */
    public static ClassMethod createJavaGetterForAttribute(final Field jAttribute, final JavaVisibility visibility) {
        String getterName = buildGetterName(jAttribute.getName());
        TypeReference returnType = EcoreUtil.copy(jAttribute.getTypeReference());
        ClassMethod getterMethod = createJavaClassMethod(getterName, returnType, visibility, false, false, null);

        Return returnStatement = JavaStatementUtil.createReturnStatement(JavaStatementUtil.createSelfReferenceToAttribute(jAttribute));
        getterMethod.getStatements().add(returnStatement);
        return getterMethod;
    }

    /**
     * Creates a setter method for the given field with the specified visibility.
     * The method body will contain "this.fieldName = fieldName;".
     *
     * @param jAttribute The field for which to create a setter.
     * @param visibility The desired visibility of the setter.
     * @return A new {@link ClassMethod} representing the setter.
     */
    public static ClassMethod createJavaSetterForAttribute(final Field jAttribute, final JavaVisibility visibility) {
        String paramName = toFirstLower(jAttribute.getName());
        OrdinaryParameter param = createJavaParameter(paramName, EcoreUtil.copy(jAttribute.getTypeReference()));

        String setterName = buildSetterName(jAttribute.getName());
        ClassMethod setterMethod = createJavaClassMethod(setterName, null, visibility, false, false, Collections.singletonList(param));

        IdentifierReference paramReference = JavaStatementUtil.createIdentifierReference(param);
        AssignmentExpression attributeAssignment = JavaStatementUtil.createAssignmentExpression(
                JavaStatementUtil.createSelfReferenceToAttribute(jAttribute),
                OperatorsFactory.eINSTANCE.createAssignment(),
                EcoreUtil.copy(paramReference)
        );
        setterMethod.getStatements().add(JavaStatementUtil.wrapExpressionInExpressionStatement(attributeAssignment));
        return setterMethod;
    }

    /**
     * Configures an existing {@link ClassMethod} object to be a setter for the given field.
     * This modifies the passed method object.
     *
     * @param field  The field the setter is for.
     * @param method The {@link ClassMethod} object to be configured as a setter.
     * @return The modified {@code method} object.
     */
    public static ClassMethod createSetter(final Field field, final ClassMethod method) {
        method.setName(buildSetterName(field.getName()));
        method.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        method.setTypeReference(TypesFactory.eINSTANCE.createVoid());

        OrdinaryParameter parameter = ParametersFactory.eINSTANCE.createOrdinaryParameter();
        parameter.setName(field.getName());
        parameter.setTypeReference(EcoreUtil.copy(field.getTypeReference()));
        method.getParameters().add(parameter);

        AssignmentExpression assignmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression();

        SelfReference selfReference = ReferencesFactory.eINSTANCE.createSelfReference();
        selfReference.setSelf(LiteralsFactory.eINSTANCE.createThis());
        IdentifierReference fieldReference = ReferencesFactory.eINSTANCE.createIdentifierReference();
        fieldReference.setTarget(field);
        selfReference.setNext(fieldReference);
        assignmentExpression.setChild(selfReference);

        assignmentExpression.setAssignmentOperator(OperatorsFactory.eINSTANCE.createAssignment());

        IdentifierReference identifierReference = ReferencesFactory.eINSTANCE.createIdentifierReference();
        identifierReference.setTarget(parameter);
        assignmentExpression.setValue(identifierReference);

        ExpressionStatement expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement();
        expressionStatement.setExpression(assignmentExpression);
        method.getStatements().add(expressionStatement);
        return method;
    }

    /**
     * Configures an existing {@link ClassMethod} object to be a getter for the given field.
     * This modifies the passed method object.
     *
     * @param field  The field the getter is for.
     * @param method The {@link ClassMethod} object to be configured as a getter.
     * @return The modified {@code method} object.
     */
    public static ClassMethod createGetter(final Field field, final ClassMethod method) {
        method.setName(buildGetterName(field.getName()));
        method.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        method.setTypeReference(EcoreUtil.copy(field.getTypeReference()));

        IdentifierReference identifierRef = ReferencesFactory.eINSTANCE.createIdentifierReference();
        identifierRef.setTarget(field);

        Return returnStatement = StatementsFactory.eINSTANCE.createReturn();
        returnStatement.setReturnValue(identifierRef);
        method.getStatements().add(returnStatement);
        return method;
    }

    /**
     * Adds a list of parameters to a parametrizable element (e.g., a method or constructor) if the list is not null or empty.
     *
     * @param parametrizable The element to add parameters to.
     * @param params         The list of parameters to add.
     * @return True if parameters were added, false otherwise.
     */
    public static boolean addParametersIfNotNull(final Parametrizable parametrizable, final List<Parameter> params) {
        if (params != null && !params.isEmpty()) {
            return parametrizable.getParameters().addAll(params);
        }
        return false;
    }

    /**
     * Checks if a conventional getter method exists for the given field in its containing class.
     *
     * @param jAttribute The field to check for a getter.
     * @return True if a getter exists, false otherwise.
     */
    public static boolean javaGetterForAttributeExists(final Field jAttribute) {
        return !getJavaGettersOfAttribute(jAttribute).isEmpty();
    }

    /**
     * Checks if a conventional setter method exists for the given field in its containing class.
     *
     * @param jAttribute The field to check for a setter.
     * @return True if a setter exists, false otherwise.
     */
    public static boolean javaSetterForAttributeExists(final Field jAttribute) {
        return !getJavaSettersOfAttribute(jAttribute).isEmpty();
    }

    /**
     * Finds all conventional getter methods for a given field in its containing class.
     *
     * @param jAttribute The field for which to find getters.
     * @return A list of matching getter methods.
     */
    public static List<ClassMethod> getJavaGettersOfAttribute(final Field jAttribute) {
        if (jAttribute == null) {
            logger.warn("Cannot retrieve Getters for Java-Attribute null. Returning empty List.");
            return Collections.emptyList();
        }
        return getJavaGettersOfAttribute(jAttribute.getContainingConcreteClassifier(), jAttribute.getName());
    }

    /**
     * Finds all conventional setter methods for a given field in its containing class.
     *
     * @param jAttribute The field for which to find setters.
     * @return A list of matching setter methods.
     */
    public static List<ClassMethod> getJavaSettersOfAttribute(final Field jAttribute) {
        if (jAttribute == null) {
            logger.warn("Cannot retrieve Setters for Java-Attribute null. Returning empty List.");
            return Collections.emptyList();
        }
        return getJavaSettersOfAttribute(jAttribute.getContainingConcreteClassifier(), jAttribute.getName());
    }

    /**
     * Finds all class methods with a specific name within a given classifier.
     *
     * @param jClass The classifier to search within.
     * @param name   The name of the methods to find.
     * @return A list of matching methods.
     */
    public static List<ClassMethod> getJavaClassMethodsWithName(final ConcreteClassifier jClass, final String name) {
        if (jClass == null) {
            return Collections.emptyList();
        }
        return jClass.getMembers().stream()
                .filter(ClassMethod.class::isInstance)
                .map(ClassMethod.class::cast)
                .filter(method -> Objects.equals(method.getName(), name))
                .collect(Collectors.toList());
    }

    /**
     * Finds all conventional setter methods for a given attribute name within a classifier.
     *
     * @param jClass         The classifier to search within.
     * @param jAttributeName The name of the attribute.
     * @return A list of matching setter methods.
     */
    public static List<ClassMethod> getJavaSettersOfAttribute(final ConcreteClassifier jClass, final String jAttributeName) {
        return getJavaClassMethodsWithName(jClass, buildSetterName(jAttributeName));
    }

    /**
     * Finds all conventional getter methods for a given attribute name within a classifier.
     *
     * @param jClass         The classifier to search within.
     * @param jAttributeName The name of the attribute.
     * @return A list of matching getter methods.
     */
    public static List<ClassMethod> getJavaGettersOfAttribute(final ConcreteClassifier jClass, final String jAttributeName) {
        return getJavaClassMethodsWithName(jClass, buildGetterName(jAttributeName));
    }

    /**
     * Renames a setter method to match a new attribute name.
     * This updates the method name, its parameter name, and the field reference within the method body.
     *
     * @param setter     The setter method to rename.
     * @param jAttribute The corresponding field, which should already have its new name.
     * @param oldName    The old name of the attribute, used to find the reference in the method body.
     */
    public static void renameSetter(final ClassMethod setter, final Field jAttribute, final String oldName) {
        setter.setName(buildSetterName(jAttribute.getName()));

        if (!setter.getParameters().isEmpty()) {
            setter.getParameters().get(0).setName(toFirstLower(jAttribute.getName()));
        }

        for (ExpressionStatement expStatement : Iterables.filter(setter.getStatements(), ExpressionStatement.class)) {
            IdentifierReference selfReference = JavaStatementUtil.getAttributeSelfReferenceInExpressionStatement(expStatement, oldName);
            if (selfReference != null) {
                selfReference.setTarget(jAttribute);
            }
        }
    }

    /**
     * Updates the type of a setter's parameter to match a change in the corresponding attribute's type.
     * It also updates the assignment value in the method body.
     *
     * @param setter     The setter method to update.
     * @param jAttribute The field with the updated type.
     */
    public static void updateAttributeTypeInSetter(ClassMethod setter, Field jAttribute) {
        Parameter param = IterableExtensions.head(setter.getParameters());
        if (param != null) {
            param.setTypeReference(EcoreUtil.copy(jAttribute.getTypeReference()));
            ExpressionStatement expStatement = IterableExtensions.head(Iterables.filter(setter.getStatements(), ExpressionStatement.class));
            boolean _expressionHasAttributeSelfReference = JavaStatementUtil.expressionHasAttributeSelfReference(expStatement, jAttribute);
            if (_expressionHasAttributeSelfReference) {
                Expression _expression = expStatement.getExpression();
                ((AssignmentExpression)_expression).setValue(JavaStatementUtil.createIdentifierReference(param));
            }
        }

    }

    /**
     * Renames a getter method to match a new attribute name.
     * This updates the method name and the field reference in the return statement.
     *
     * @param getter     The getter method to rename.
     * @param jAttribute The corresponding field, which should already have its new name.
     */
    public static void renameGetterOfAttribute(ClassMethod getter, Field jAttribute) {
        getter.setName(buildGetterName(jAttribute.getName()));
        Return returnStatement = IterableExtensions.head(Iterables.filter(getter.getStatements(), Return.class));
        if (returnStatement != null) {
            returnStatement.setReturnValue(JavaStatementUtil.createSelfReferenceToAttribute(jAttribute));
        }

    }

    /**
     * Updates the return type of a getter method to match a change in the corresponding attribute's type.
     *
     * @param getter     The getter method to update.
     * @param jAttribute The field with the updated type.
     */
    public static void updateAttributeTypeInGetter(final ClassMethod getter, final Field jAttribute) {
        getter.setTypeReference(EcoreUtil.copy(jAttribute.getTypeReference()));
    }

    /**
     * Builds a conventional setter name (e.g., "attributeName" -> "setAttributeName").
     *
     * @param attributeName The name of the attribute.
     * @return The conventional setter name.
     */
    public static String buildSetterName(final String attributeName) {
        return "set" + toFirstUpper(attributeName);
    }

    /**
     * Builds a conventional getter name (e.g., "attributeName" -> "getAttributeName").
     *
     * @param attributeName The name of the attribute.
     * @return The conventional getter name.
     */
    public static String buildGetterName(final String attributeName) {
        return "get" + toFirstUpper(attributeName);
    }

    /**
     * Checks if two methods have the same signature (name, return type, and parameter types).
     *
     * @param method1 The first method to compare.
     * @param method2 The second method to compare.
     * @return True if the methods have the same signature, false otherwise.
     */
    public static boolean hasSameSignature(final Method method1, final Method method2) {
        if (Objects.equals(method1, method2)) {
            return true;
        }
        if (method1 == null || method2 == null) {
            return false;
        }
        if (!Objects.equals(method1.getName(), method2.getName())) {
            return false;
        }
        if (JavaTypeUtil.hasSameTargetReference(method1.getTypeReference(), method2.getTypeReference())) {
            return false;
        }
        EList<Parameter> params1 = method1.getParameters();
        EList<Parameter> params2 = method2.getParameters();
        if (params1.size() != params2.size()) {
            return false;
        }
        for (int i = 0; i < params1.size(); i++) {
            if (JavaTypeUtil.hasSameTargetReference(params1.get(i).getTypeReference(), params2.get(i).getTypeReference())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Initializes a class method based on the signature of another method (e.g., an implemented interface method).
     *
     * @param classMethod       The class method to initialize.
     * @param implementedMethod The method providing the signature.
     * @param ensurePublic      If true, ensures the initialized method has a public modifier.
     * @return True if parameters were successfully added.
     */
    public static boolean initializeClassMethod(final ClassMethod classMethod, final Method implementedMethod, final boolean ensurePublic) {
        return initializeClassMethod(
                classMethod,
                implementedMethod.getName(),
                implementedMethod.getTypeReference(),
                implementedMethod.getModifiers().toArray(new Modifier[0]),
                implementedMethod.getParameters().toArray(new Parameter[0]),
                ensurePublic
        );
    }

    /**
     * Initializes a class method with the given properties.
     *
     * @param classMethod   The class method to initialize.
     * @param name          The name for the method.
     * @param typeReference The return type for the method.
     * @param modifiers     An array of modifiers to add.
     * @param parameters    An array of parameters to add.
     * @param ensurePublic  If true, ensures the initialized method has a public modifier.
     * @return True if parameters were successfully added.
     */
    public static boolean initializeClassMethod(final ClassMethod classMethod, final String name, final TypeReference typeReference, final Modifier[] modifiers, final Parameter[] parameters, final boolean ensurePublic) {
        classMethod.setName(name);
        if (typeReference != null) {
            classMethod.setTypeReference(EcoreUtil.copy(typeReference));
        }
        if (modifiers != null) {
            classMethod.getAnnotationsAndModifiers().addAll(EcoreUtil.copyAll(Arrays.asList(modifiers)));
        }
        if (ensurePublic) {
            boolean alreadyPublic = classMethod.getAnnotationsAndModifiers().stream()
                    .anyMatch(Public.class::isInstance);
            if (!alreadyPublic) {
                classMethod.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
            }
        }
        if (parameters != null) {
            return classMethod.getParameters().addAll(EcoreUtil.copyAll(Arrays.asList(parameters)));
        }
        return false;
    }

    /**
     * Finds a method in a class that matches the name and number of type parameters of a given method.
     * Note: This is not a full signature match.
     *
     * @param concreteClassifier The classifier to search within.
     * @param method             The method to find a match for.
     * @return The first matching method found, or null if no match is found.
     */
    public static ClassMethod findMethodInClass(final ConcreteClassifier concreteClassifier, final ClassMethod method) {
        return concreteClassifier.getMethods().stream()
                .filter(ClassMethod.class::isInstance)
                .map(ClassMethod.class::cast)
                .filter(currentMethod -> Objects.equals(currentMethod.getName(), method.getName()))
                .filter(currentMethod -> currentMethod.getTypeParameters().size() == method.getTypeParameters().size())
                .findFirst()
                .orElse(null);
    }

    /**
     * Sorts a list of class members in a conventional order: Fields, then Constructors, then Methods.
     * The sorting is done in-place.
     *
     * @param members The list of members to sort.
     */
    public static void sortMembers(final EList<? extends EObject> members) {
        ECollections.sort(members, Comparator.comparingInt(JavaMemberAndParameterUtil::getSortRank));
    }

    /**
     * Assigns a rank to a member for sorting purposes.
     * The order is: Fields (1), Constructors (2), Methods (3), Others (4).
     *
     * @param member The member to rank.
     * @return an integer representing the sort rank.
     */
    private static int getSortRank(EObject member) {
        if (member instanceof Field) {
            return 1;
        }
        if (member instanceof Constructor) {
            return 2;
        }
        if (member instanceof Method) {
            return 3;
        }
        return 4; // Should not happen for standard members
    }

    // Helper to replace StringExtensions.toFirstUpper
    private static String toFirstUpper(final String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    // Helper to replace StringExtensions.toFirstLower
    private static String toFirstLower(final String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}
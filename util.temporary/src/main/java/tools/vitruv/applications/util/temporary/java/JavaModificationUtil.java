package tools.vitruv.applications.util.temporary.java;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.activextendannotations.Utility;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import org.emftext.language.java.JavaClasspath;
import org.emftext.language.java.annotations.AnnotationInstance;
import org.emftext.language.java.annotations.AnnotationsFactory;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.expressions.AssignmentExpression;
import org.emftext.language.java.expressions.ExpressionsFactory;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.imports.Import;
import org.emftext.language.java.imports.ImportsFactory;
import org.emftext.language.java.instantiations.NewConstructorCall;
import org.emftext.language.java.literals.LiteralsFactory;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.MembersFactory;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.operators.OperatorsFactory;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.parameters.ParametersFactory;
import org.emftext.language.java.references.IdentifierReference;
import org.emftext.language.java.references.Reference;
import org.emftext.language.java.references.ReferenceableElement;
import org.emftext.language.java.references.ReferencesFactory;
import org.emftext.language.java.references.SelfReference;
import org.emftext.language.java.statements.ExpressionStatement;
import org.emftext.language.java.statements.Statement;
import org.emftext.language.java.statements.StatementsFactory;
import org.emftext.language.java.types.Char;
import org.emftext.language.java.types.ClassifierReference;
import org.emftext.language.java.types.Int;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.PrimitiveType;
import org.emftext.language.java.types.Type;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypesFactory;
import org.emftext.language.java.types.Void;

/**
 * Utility class for creating and modifying JaMoPP (Java Model-to-Model Printer) Ecore models programmatically.
 * This class provides helper methods for common tasks such as creating parameters, fields, imports, and assignments.
 */
@Utility
public final class JavaModificationUtil {
    private static final Logger logger = Logger.getLogger(JavaModificationUtil.class);

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private JavaModificationUtil() {
    }

    /**
     * Creates an ordinary method parameter.
     *
     * @param typeReference The type of the parameter.
     * @param name The name of the parameter.
     * @return The newly created {@link Parameter}.
     */
    public static Parameter createOrdinaryParameter(final TypeReference typeReference, final String name) {
        OrdinaryParameter parameter = ParametersFactory.eINSTANCE.createOrdinaryParameter();
        parameter.setName(name);
        parameter.setTypeReference(typeReference);
        return parameter;
    }

    /**
     * Creates a statement that assigns a parameter's value to a field (e.g., "this.field = parameter;").
     *
     * @param field The field to assign to.
     * @param parameter The parameter whose value will be assigned.
     * @return The newly created assignment {@link Statement}.
     */
    public static Statement createAssignmentFromParameterToField(final Field field, final Parameter parameter) {
        ExpressionStatement expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement();
        AssignmentExpression assigmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression();

        SelfReference selfReference = ReferencesFactory.eINSTANCE.createSelfReference();
        selfReference.setSelf(LiteralsFactory.eINSTANCE.createThis());
        assigmentExpression.setChild(selfReference);

        IdentifierReference fieldReference = ReferencesFactory.eINSTANCE.createIdentifierReference();
        fieldReference.setTarget(field);
        selfReference.setNext(fieldReference);

        assigmentExpression.setAssignmentOperator(OperatorsFactory.eINSTANCE.createAssignment());

        IdentifierReference identifierReference = ReferencesFactory.eINSTANCE.createIdentifierReference();
        identifierReference.setTarget(parameter);
        assigmentExpression.setValue(identifierReference);

        expressionStatement.setExpression(assigmentExpression);
        return expressionStatement;
    }

    /**
     * Creates a {@link NamespaceClassifierReference} for a given {@link ConcreteClassifier}.
     *
     * @param concreteClassifier The classifier to reference.
     * @return The newly created {@link NamespaceClassifierReference}.
     */
    public static NamespaceClassifierReference createNamespaceClassifierReference(final ConcreteClassifier concreteClassifier) {
        NamespaceClassifierReference namespaceClassifierReference = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
        createNamespaceClassifierReference(namespaceClassifierReference, concreteClassifier);
        return namespaceClassifierReference;
    }

    /**
     * Adds an import for a classifier to the compilation unit of another classifier.
     *
     * @param classifier The classifier whose compilation unit will receive the import.
     * @param classifierToImport The classifier to be imported.
     * @return The newly created {@link Import}.
     */
    public static Import addImportToCompilationUnitOfClassifier(final Classifier classifier, final ConcreteClassifier classifierToImport) {
        ClassifierImport classifierImport = ImportsFactory.eINSTANCE.createClassifierImport();
        CompilationUnit containingCompilationUnit = classifierToImport.getContainingCompilationUnit();
        if (containingCompilationUnit != null) {
            EList<String> namespaces = containingCompilationUnit.getNamespaces();
            if (namespaces != null) {
                classifierImport.getNamespaces().addAll(namespaces);
            }
            classifier.getContainingCompilationUnit().getImports().add(classifierImport);
        }
        classifierImport.setClassifier(classifierToImport);
        return classifierImport;
    }

    /**
     * Populates a {@link NamespaceClassifierReference} to point to a given {@link ConcreteClassifier}.
     * This method handles both resolved classifiers within a compilation unit and proxy classifiers from the classpath.
     *
     * @param namespaceClassifierReference The reference to populate.
     * @param concreteClassifier The target classifier.
     * @return {@code true} if namespaces were successfully added to the reference, {@code false} otherwise.
     */
    public static boolean createNamespaceClassifierReference(final NamespaceClassifierReference namespaceClassifierReference, final ConcreteClassifier concreteClassifier) {
        ClassifierReference classifierRef = TypesFactory.eINSTANCE.createClassifierReference();
        classifierRef.setTarget(concreteClassifier);
        namespaceClassifierReference.getClassifierReferences().add(classifierRef);

        CompilationUnit containingCompilationUnit = concreteClassifier.getContainingCompilationUnit();
        if (containingCompilationUnit != null) {
            return Iterables.addAll(namespaceClassifierReference.getNamespaces(), containingCompilationUnit.getNamespaces());
        } else if (concreteClassifier.eIsProxy()) {
            URI uri = EcoreUtil.getURI(concreteClassifier);
            String prefix = "/javaclass/";
            String suffix = concreteClassifier.getName() + ".java";
            String path = uri.path();

            if (path != null && path.startsWith(prefix) && path.endsWith(suffix)) {
                int beginIndex = prefix.length();
                int endIndex = path.length() - suffix.length() -1; // remove trailing dot
                if (beginIndex < endIndex) {
                    String namespaceString = path.substring(beginIndex, endIndex);
                    String[] namespaces = namespaceString.split("\\.");
                    return Iterables.addAll(namespaceClassifierReference.getNamespaces(), (Iterable<String>) Conversions.doWrapArray(namespaces));
                }
            }
        }
        return false;
    }

    /**
     * Configures a {@link Field} to be a private field with a given type and name.
     * If the name is null or empty, a default name is generated based on the type.
     *
     * @param field The field EObject to configure.
     * @param reference The type reference for the field.
     * @param name The desired name for the field.
     */
    public static void createPrivateField(final Field field, final TypeReference reference, final String name) {
        field.setTypeReference(EcoreUtil.copy(reference));
        field.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPrivate());
        String fieldName = name;
        if (StringExtensions.isNullOrEmpty(name)) {
            String typeName = JavaQueryUtil.getNameFromJaMoPPType(reference);
            fieldName = "field_" + typeName;
        }
        field.setName(fieldName);
    }

    /**
     * Default dispatch method for finding a wrapper type for a primitive type. Logs a warning.
     *
     * @param type The primitive type.
     * @return {@code null}, as no specific wrapper is found.
     */
    protected static TypeReference getWrapperTypeReferenceForPrimitiveType(final PrimitiveType type) {
        logger.warn("no dispatch method found for type: " + type);
        return null;
    }

    /**
     * Returns a type reference to {@code java.lang.Boolean} for the primitive {@code boolean} type.
     *
     * @param type The boolean primitive type instance (not used, for dispatch only).
     * @return A {@link TypeReference} for the Boolean wrapper class.
     */
    protected static TypeReference getWrapperTypeReferenceForPrimitiveType(final org.emftext.language.java.types.Boolean type) {
        return createNamespaceClassifierReferenceForName("java.lang", "Boolean");
    }

    /**
     * Returns a type reference to {@code java.lang.Byte} for the primitive {@code byte} type.
     *
     * @param type The byte primitive type instance (not used, for dispatch only).
     * @return A {@link TypeReference} for the Byte wrapper class.
     */
    protected static TypeReference getWrapperTypeReferenceForPrimitiveType(final org.emftext.language.java.types.Byte type) {
        return createNamespaceClassifierReferenceForName("java.lang", "Byte");
    }

    /**
     * Returns a type reference to {@code java.lang.Character} for the primitive {@code char} type.
     *
     * @param type The char primitive type instance.
     * @return A {@link TypeReference} for the Character wrapper class.
     */
    protected static TypeReference getWrapperTypeReferenceForPrimitiveType(final Char type) {
        return createNamespaceClassifierReferenceForName("java.lang", "Character");
    }

    /**
     * Returns a type reference to {@code java.lang.Double} for the primitive {@code double} type.
     *
     * @param type The double primitive type instance (not used, for dispatch only).
     * @return A {@link TypeReference} for the Double wrapper class.
     */
    protected static TypeReference getWrapperTypeReferenceForPrimitiveType(final org.emftext.language.java.types.Double type) {
        return createNamespaceClassifierReferenceForName("java.lang", "Double");
    }

    /**
     * Returns a type reference to {@code java.lang.Float} for the primitive {@code float} type.
     *
     * @param type The float primitive type instance (not used, for dispatch only).
     * @return A {@link TypeReference} for the Float wrapper class.
     */
    protected static TypeReference getWrapperTypeReferenceForPrimitiveType(final org.emftext.language.java.types.Float type) {
        return createNamespaceClassifierReferenceForName("java.lang", "Float");
    }

    /**
     * Returns a type reference to {@code java.lang.Integer} for the primitive {@code int} type.
     *
     * @param type The int primitive type instance.
     * @return A {@link TypeReference} for the Integer wrapper class.
     */
    protected static TypeReference getWrapperTypeReferenceForPrimitiveType(final Int type) {
        return createNamespaceClassifierReferenceForName("java.lang", "Integer");
    }

    /**
     * Returns a type reference to {@code java.lang.Long} for the primitive {@code long} type.
     *
     * @param type The long primitive type instance (not used, for dispatch only).
     * @return A {@link TypeReference} for the Long wrapper class.
     */
    protected static TypeReference getWrapperTypeReferenceForPrimitiveType(final org.emftext.language.java.types.Long type) {
        return createNamespaceClassifierReferenceForName("java.lang", "Long");
    }

    /**
     * Returns a type reference to {@code java.lang.Short} for the primitive {@code short} type.
     *
     * @param type The short primitive type instance (not used, for dispatch only).
     * @return A {@link TypeReference} for the Short wrapper class.
     */
    protected static TypeReference getWrapperTypeReferenceForPrimitiveType(final org.emftext.language.java.types.Short type) {
        return createNamespaceClassifierReferenceForName("java.lang", "Short");
    }

    /**
     * Returns a type reference to {@code java.lang.Void}.
     *
     * @param type The void primitive type instance.
     * @return A {@link TypeReference} for the Void wrapper class.
     */
    protected static TypeReference getWrapperTypeReferenceForPrimitiveType(final Void type) {
        return createNamespaceClassifierReferenceForName("java.lang", "Void");
    }

    /**
     * Creates a Java class import statement from a fully qualified class name.
     *
     * @param name The fully qualified name of the class to import.
     * @return The created {@link ClassifierImport}.
     */
    public static ClassifierImport createJavaClassImport(final String name) {
        ConcreteClassifier classifier = getClassifier(name);
        ClassifierImport classifierImport = ImportsFactory.eINSTANCE.createClassifierImport();
        classifierImport.setClassifier(classifier);
        return classifierImport;
    }

    /**
     * Creates a {@link NamespaceClassifierReference} for a class given its namespace and simple name.
     *
     * @param namespace The package/namespace of the class (e.g., "java.lang").
     * @param name The simple name of the class (e.g., "String").
     * @return The created {@link NamespaceClassifierReference}.
     */
    public static NamespaceClassifierReference createNamespaceClassifierReferenceForName(final String namespace, final String name) {
        ConcreteClassifier classifier = getClassifier(namespace + "." + name);
        return createNamespaceClassifierReference(classifier);
    }

    /**
     * Creates a {@link NamespaceClassifierReference} for a class given its fully qualified name.
     *
     * @param qualifiedName The fully qualified name of the class (e.g., "java.util.List").
     * @return The created {@link NamespaceClassifierReference}.
     */
    public static NamespaceClassifierReference createNamespaceClassifierReferenceForName(final String qualifiedName) {
        return createNamespaceClassifierReference(getClassifier(qualifiedName));
    }

    /**
     * Retrieves a {@link ConcreteClassifier} from the Java classpath by its fully qualified name.
     *
     * @param qualifiedName The fully qualified name of the classifier.
     * @return The found {@link ConcreteClassifier}, or a proxy if not fully resolved.
     */
    public static ConcreteClassifier getClassifier(final String qualifiedName) {
        EObject classifier = JavaClasspath.get().getClassifier(qualifiedName);
        return (ConcreteClassifier) classifier;
    }

    /**
     * Adds an annotation to a modifiable element (like a class, field, or method).
     *
     * @param annotableAndModifiable The element to annotate.
     * @param annotationName The simple name of the annotation class.
     * @return {@code true} if the annotation was successfully added.
     */
    public static boolean addAnnotationToAnnotableAndModifiable(final AnnotableAndModifiable annotableAndModifiable, final String annotationName) {
        AnnotationInstance newAnnotation = AnnotationsFactory.eINSTANCE.createAnnotationInstance();
        Class jaMoPPClass = ClassifiersFactory.eINSTANCE.createClass();
        jaMoPPClass.setName(annotationName);
        newAnnotation.setAnnotation(jaMoPPClass);
        return annotableAndModifiable.getAnnotationsAndModifiers().add(newAnnotation);
    }

    /**
     * Adds an import to a class's compilation unit if it doesn't already exist.
     *
     * @param jaMoPPClass The class whose compilation unit will receive the import.
     * @param namespaceArray The namespace parts of the import (e.g., ["com", "google", "inject"]).
     * @param entityToImport The simple name of the class to import (e.g., "Inject").
     */
    public static void addImportToClassFromString(final ConcreteClassifier jaMoPPClass, final List<String> namespaceArray, final String entityToImport) {
        // Check if the import already exists
        for (Import currentImport : jaMoPPClass.getContainingCompilationUnit().getImports()) {
            if (currentImport instanceof ClassifierImport) {
                String importedClassName = ((ClassifierImport) currentImport).getClassifier().getName();
                if (Objects.equals(importedClassName, entityToImport)) {
                    return; // Import already present
                }
            }
        }

        ClassifierImport newImport = ImportsFactory.eINSTANCE.createClassifierImport();
        ConcreteClassifier classifierToImport = ClassifiersFactory.eINSTANCE.createClass();
        classifierToImport.setName(entityToImport);
        newImport.setClassifier(classifierToImport);
        newImport.getNamespaces().addAll(namespaceArray);
        jaMoPPClass.getContainingCompilationUnit().getImports().add(newImport);
    }

    /**
     * Adds a new, empty, public constructor to a Java class.
     *
     * @param javaClass The class to which the constructor will be added.
     * @return The newly created {@link Constructor}.
     */
    public static Constructor addConstructorToClass(final Class javaClass) {
        Constructor constructor = MembersFactory.eINSTANCE.createConstructor();
        return addConstructorToClass(constructor, javaClass);
    }

    /**
     * Adds a given constructor to a Java class, setting its name and making it public.
     *
     * @param constructor The constructor to add and configure.
     * @param javaClass The class to which the constructor will be added.
     * @return The configured and added {@link Constructor}.
     */
    public static Constructor addConstructorToClass(final Constructor constructor, final Class javaClass) {
        constructor.setName(javaClass.getName());
        constructor.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        javaClass.getMembers().add(constructor);
        return constructor;
    }

    /**
     * Populates a {@link ClassifierImport} and adds it to the compilation unit of a classifier.
     *
     * @param classifierImport The import object to configure and add.
     * @param classifier The classifier whose compilation unit will receive the import.
     * @param classifierToImport The classifier to be imported.
     */
    public static void addImportToCompilationUnitOfClassifier(final ClassifierImport classifierImport, final Classifier classifier, final ConcreteClassifier classifierToImport) {
        CompilationUnit containingCompilationUnit = classifierToImport.getContainingCompilationUnit();
        if (containingCompilationUnit != null) {
            EList<String> namespaces = containingCompilationUnit.getNamespaces();
            if (namespaces != null) {
                classifierImport.getNamespaces().addAll(namespaces);
            }
            classifier.getContainingCompilationUnit().getImports().add(classifierImport);
        }
        classifierImport.setClassifier(classifierToImport);
    }

    /**
     * Creates a "new" expression for a field and adds it as an initialization statement in a constructor.
     * Example: this.myField = new MyField(...);
     *
     * @param newConstructorCall The {@link NewConstructorCall} EObject to be configured.
     * @param constructor The constructor where the initialization statement will be added.
     * @param field The field to be initialized.
     * @return {@code true} if the statement was added successfully, {@code null} if the field's container is not a Class.
     */
    public static Boolean createNewForFieldInConstructor(final NewConstructorCall newConstructorCall, final Constructor constructor, final Field field) {
        ConcreteClassifier classifier = field.getContainingConcreteClassifier();
        if (classifier instanceof Class jaMoPPClass) {
            Field[] fields = (Field[]) Conversions.unwrapArray(jaMoPPClass.getFields(), Field.class);
            Parameter[] parameters = (Parameter[]) Conversions.unwrapArray(constructor.getParameters(), Parameter.class);
            return addNewStatementToConstructor(newConstructorCall, constructor, field, fields, parameters);
        }
        return null;
    }

    /**
     * Adds a statement to a constructor to initialize a field with a new object instance.
     * It attempts to resolve arguments for the new object's constructor from available fields and parameters.
     *
     * @param newConstructorCall The "new" expression to configure.
     * @param constructor The constructor to add the statement to.
     * @param field The field to initialize.
     * @param fieldsToUseAsArgument An array of fields available as potential constructor arguments.
     * @param parametersToUseAsArgument An array of parameters available as potential constructor arguments.
     * @return {@code true} if the statement was successfully added.
     */
    public static boolean addNewStatementToConstructor(final NewConstructorCall newConstructorCall, final Constructor constructor, final Field field, final Field[] fieldsToUseAsArgument, final Parameter[] parametersToUseAsArgument) {
        ExpressionStatement expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement();
        AssignmentExpression assigmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression();

        SelfReference selfReference = ReferencesFactory.eINSTANCE.createSelfReference();
        selfReference.setSelf(LiteralsFactory.eINSTANCE.createThis());
        assigmentExpression.setChild(selfReference);

        IdentifierReference fieldReference = ReferencesFactory.eINSTANCE.createIdentifierReference();
        fieldReference.setTarget(field);
        selfReference.setNext(EcoreUtil.copy(fieldReference));

        assigmentExpression.setAssignmentOperator(OperatorsFactory.eINSTANCE.createAssignment());

        newConstructorCall.setTypeReference(EcoreUtil.copy(field.getTypeReference()));
        updateArgumentsOfConstructorCall(field, fieldsToUseAsArgument, parametersToUseAsArgument, newConstructorCall);
        assigmentExpression.setValue(newConstructorCall);

        expressionStatement.setExpression(assigmentExpression);
        return constructor.getStatements().add(expressionStatement);
    }

    /**
     * Scans the constructor parameters of a field's type and attempts to find matching arguments
     * from a list of available fields and parameters. It then populates the arguments of the given {@link NewConstructorCall}.
     *
     * @param field The field being initialized.
     * @param fieldsToUseAsArgument Fields available as arguments.
     * @param parametersToUseAsArgument Parameters available as arguments.
     * @param newConstructorCall The constructor call whose arguments will be populated.
     */
    private static void updateArgumentsOfConstructorCall(final Field field, final Field[] fieldsToUseAsArgument, final Parameter[] parametersToUseAsArgument, final NewConstructorCall newConstructorCall) {
        List<TypeReference> typeListForConstructor = new ArrayList<>();
        if (field.getTypeReference() != null &&
                field.getTypeReference().getPureClassifierReference() != null &&
                field.getTypeReference().getPureClassifierReference().getTarget() != null) {

            Classifier classifier = field.getTypeReference().getPureClassifierReference().getTarget();
            if (classifier instanceof Class) {
                Class jaMoPPClass = (Class) classifier;
                Iterable<Constructor> constructorsForClass = Iterables.filter(jaMoPPClass.getMembers(), Constructor.class);
                if (!IterableExtensions.isNullOrEmpty(constructorsForClass)) {
                    Constructor constructorForClass = ((Constructor[]) Conversions.unwrapArray(constructorsForClass, Constructor.class))[0];
                    for (Parameter parameter : constructorForClass.getParameters()) {
                        typeListForConstructor.add(parameter.getTypeReference());
                    }
                }
            }
        }

        for (TypeReference typeRef : typeListForConstructor) {
            ReferenceableElement refElement = findMatchingTypeInParametersOrFields(typeRef, fieldsToUseAsArgument, parametersToUseAsArgument);
            if (refElement != null) {
                IdentifierReference identifierReference = ReferencesFactory.eINSTANCE.createIdentifierReference();
                identifierReference.setTarget(refElement);
                newConstructorCall.getArguments().add(identifierReference);
            } else {
                newConstructorCall.getArguments().add(LiteralsFactory.eINSTANCE.createNullLiteral());
            }
        }
    }

    /**
     * Finds a parameter or field that matches a given type reference.
     *
     * @param typeReferenceToFind The type to match.
     * @param fieldsToUseAsArgument An array of fields to search in.
     * @param parametersToUseAsArgument An array of parameters to search in.
     * @return The matching {@link ReferenceableElement} (field or parameter), or {@code null} if none is found.
     */
    private static ReferenceableElement findMatchingTypeInParametersOrFields(final TypeReference typeReferenceToFind, final Field[] fieldsToUseAsArgument, final Parameter[] parametersToUseAsArgument) {
        for (Parameter parameter : parametersToUseAsArgument) {
            Type parameterType = parameter.getTypeReference().getTarget();
            Type typeToFind = typeReferenceToFind.getTarget();
            if (Objects.equals(parameterType, typeToFind)) {
                return parameter;
            }
        }

        for (Field field : fieldsToUseAsArgument) {
            Type fieldType = field.getTypeReference().getTarget();
            Type typeToFind = typeReferenceToFind.getTarget();
            if (Objects.equals(fieldType, typeToFind)) {
                return field;
            }
        }
        return null;
    }

    /**
     * Dispatches to the appropriate protected method to get the wrapper type for a given primitive type object.
     * This method provides type-safe dispatching for different subclasses of {@link PrimitiveType}.
     *
     * @param type The primitive type object (e.g., an instance of {@link Int}, {@link Char}).
     * @return A {@link TypeReference} to the corresponding wrapper class (e.g., {@code java.lang.Integer}).
     * @throws IllegalArgumentException if the type is not a recognized primitive type.
     */
    @XbaseGenerated
    public static TypeReference getWrapperTypeReferenceForPrimitiveType(final Object type) {
        if (type instanceof Char) {
            return getWrapperTypeReferenceForPrimitiveType((Char) type);
        } else if (type instanceof Int) {
            return getWrapperTypeReferenceForPrimitiveType((Int) type);
        } else if (type instanceof Void) {
            return getWrapperTypeReferenceForPrimitiveType((Void) type);
        } else if (type instanceof org.emftext.language.java.types.Byte) {
            return getWrapperTypeReferenceForPrimitiveType((org.emftext.language.java.types.Byte) type);
        } else if (type instanceof org.emftext.language.java.types.Double) {
            return getWrapperTypeReferenceForPrimitiveType((org.emftext.language.java.types.Double) type);
        } else if (type instanceof org.emftext.language.java.types.Float) {
            return getWrapperTypeReferenceForPrimitiveType((org.emftext.language.java.types.Float) type);
        } else if (type instanceof org.emftext.language.java.types.Long) {
            return getWrapperTypeReferenceForPrimitiveType((org.emftext.language.java.types.Long) type);
        } else if (type instanceof org.emftext.language.java.types.Short) {
            return getWrapperTypeReferenceForPrimitiveType((org.emftext.language.java.types.Short) type);
        } else if (type instanceof org.emftext.language.java.types.Boolean) {
            return getWrapperTypeReferenceForPrimitiveType((org.emftext.language.java.types.Boolean) type);
        } else if (type instanceof PrimitiveType) {
            return getWrapperTypeReferenceForPrimitiveType((PrimitiveType) type);
        } else {
            throw new IllegalArgumentException("Unhandled parameter types: " + Arrays.asList(type).toString());
        }
    }
}
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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
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
 * A utility class for programmatically modifying Java code models (ASTs) using the JaMoPP framework.
 * It provides high-level helper methods for common code generation tasks like adding fields,
 * constructors, imports, and complex statements.
 */
@Utility
public final class JavaModificationUtil {

    private static final Logger logger = Logger.getLogger(JavaModificationUtil.class);

    private JavaModificationUtil() {
        // Private constructor to prevent instantiation of this utility class.
    }

    /**
     * Creates a new method parameter.
     *
     * @param typeReference The type of the parameter (e.g., a reference to String.class).
     * @param name          The name of the parameter (e.g., "myString").
     * @return The newly created {@link Parameter} object.
     */
    public static Parameter createOrdinaryParameter(TypeReference typeReference, String name) {
        OrdinaryParameter parameter = ParametersFactory.eINSTANCE.createOrdinaryParameter();
        parameter.setName(name);
        parameter.setTypeReference(typeReference);
        return parameter;
    }

    /**
     * Creates a statement that assigns a parameter's value to a field, e.g., {@code this.field = parameter;}.
     *
     * @param field     The class field to assign to.
     * @param parameter The parameter to assign from.
     * @return The newly created assignment {@link Statement}.
     */
    public static Statement createAssignmentFromParameterToField(Field field, Parameter parameter) {
        // The statement is an ExpressionStatement that holds the assignment.
        ExpressionStatement expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement();

        // Create the core assignment expression: LHS = RHS
        AssignmentExpression assignmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression();

        // Build the Left Hand Side (LHS): this.field
        SelfReference selfReference = ReferencesFactory.eINSTANCE.createSelfReference();
        selfReference.setSelf(LiteralsFactory.eINSTANCE.createThis());
        IdentifierReference fieldReference = ReferencesFactory.eINSTANCE.createIdentifierReference();
        fieldReference.setTarget(field);
        selfReference.setNext(fieldReference); // Chain them: this -> .field
        assignmentExpression.setChild(selfReference);

        // Set the assignment operator: =
        assignmentExpression.setAssignmentOperator(OperatorsFactory.eINSTANCE.createAssignment());

        // Build the Right Hand Side (RHS): a reference to the parameter
        IdentifierReference identifierReference = ReferencesFactory.eINSTANCE.createIdentifierReference();
        identifierReference.setTarget(parameter);
        assignmentExpression.setValue(identifierReference);

        expressionStatement.setExpression(assignmentExpression);
        return expressionStatement;
    }

    /**
     * Creates a {@link NamespaceClassifierReference} for a given {@link ConcreteClassifier}.
     * This is used to create a typed reference to a class, including its package name.
     *
     * @param concreteClassifier The classifier to reference (e.g., the JaMoPP model for ArrayList).
     * @return The newly created {@link NamespaceClassifierReference}.
     */
    public static NamespaceClassifierReference createNamespaceClassifierReference(ConcreteClassifier concreteClassifier) {
        NamespaceClassifierReference namespaceClassifierReference = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
        createNamespaceClassifierReference(namespaceClassifierReference, concreteClassifier);
        return namespaceClassifierReference;
    }

    /**
     * Adds an import statement for a given classifier to the compilation unit of another classifier.
     *
     * @param requiredInterfaceImport
     * @param classifier              The classifier whose compilation unit will receive the new import.
     * @param classifierToImport      The classifier that needs to be imported.
     * @return The newly created {@link Import} object.
     */
    public static Import addImportToCompilationUnitOfClassifier(ClassifierImport requiredInterfaceImport, Classifier classifier, ConcreteClassifier classifierToImport) {
        ClassifierImport classifierImport = ImportsFactory.eINSTANCE.createClassifierImport();

        CompilationUnit compilationUnitToImportFrom = classifierToImport.getContainingCompilationUnit();
        if (compilationUnitToImportFrom != null) {
            // Copy the package (namespaces) from the source compilation unit.
            EList<String> namespaces = compilationUnitToImportFrom.getNamespaces();
            if (namespaces != null) {
                classifierImport.getNamespaces().addAll(namespaces);
            }
            classifier.getContainingCompilationUnit().getImports().add(classifierImport);
        }

        classifierImport.setClassifier(classifierToImport);
        return classifierImport;
    }

    /**
     * Populates a {@link NamespaceClassifierReference} with the details from a {@link ConcreteClassifier}.
     * This includes handling classifiers that are proxies (e.g., from external JARs).
     *
     * @param namespaceClassifierReference The reference object to populate.
     * @param concreteClassifier           The classifier to get the details from.
     * @return True if namespaces were successfully added, false otherwise.
     */
    public static boolean createNamespaceClassifierReference(NamespaceClassifierReference namespaceClassifierReference, ConcreteClassifier concreteClassifier) {
        // Create the reference to the class itself.
        ClassifierReference classifierRef = TypesFactory.eINSTANCE.createClassifierReference();
        classifierRef.setTarget(concreteClassifier);
        namespaceClassifierReference.getClassifierReferences().add(classifierRef);

        // Handle adding the package (namespaces).
        CompilationUnit containingUnit = concreteClassifier.getContainingCompilationUnit();
        if (containingUnit != null) {
            // Case 1: The classifier is fully loaded in the model.
            return Iterables.addAll(namespaceClassifierReference.getNamespaces(), containingUnit.getNamespaces());
        } else if (concreteClassifier.eIsProxy()) {
            // Case 2: The classifier is a proxy, likely from a JAR file resolved by JaMoPP's classpath.
            // We need to parse the package name from its URI.
            URI uri = EcoreUtil.getURI(concreteClassifier);
            String path = uri.path();

            // JaMoPP URIs for compiled classes often look like: /javaclass/java.lang.String.java
            String prefix = "/javaclass/";
            String suffix = concreteClassifier.getName() + ".java"; // Construct the expected suffix.

            if (path != null && path.startsWith(prefix) && path.endsWith(suffix)) {
                // Extract the middle part containing the package name.
                int packageEndIndex = path.length() - suffix.length() - 1; // -1 for the dot before class name
                if (packageEndIndex > prefix.length()) {
                    String packagePath = path.substring(prefix.length(), packageEndIndex);
                    String[] namespaces = packagePath.split("\\.");
                    return Iterables.addAll(namespaceClassifierReference.getNamespaces(), Arrays.asList(namespaces));
                }
            }
        }
        return false;
    }

    /**
     * Populates a {@link Field} object to be a private field.
     * If the name is null or empty, a default name is generated based on the type.
     *
     * @param field     The Field object to configure.
     * @param reference The type of the field.
     * @param name      The desired name for the field.
     */
    public static void createPrivateField(Field field, TypeReference reference, String name) {
        field.setTypeReference(EcoreUtil.copy(reference));
        field.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPrivate());

        String fieldName = name;
        if (StringExtensions.isNullOrEmpty(name)) {
            // Generate a default name if none is provided.
            String typeName = JavaQueryUtil.getNameFromJaMoPPType(reference);
            fieldName = "field_" + typeName;
        }
        field.setName(fieldName);
    }

    /**
     * Gets the corresponding wrapper class type reference for a given primitive type.
     * For example, for {@code int}, it returns a reference to {@code java.lang.Integer}.
     *
     * @param type The primitive type.
     * @return A {@link TypeReference} to the wrapper class, or null if no mapping exists.
     */
    public static TypeReference getWrapperTypeReferenceForPrimitive(PrimitiveType type) {
        if (type instanceof Int) return getWrapperTypeReferenceFor((Int) type);
        if (type instanceof Char) return getWrapperTypeReferenceFor((Char) type);
        if (type instanceof org.emftext.language.java.types.Boolean) return getWrapperTypeReferenceFor((org.emftext.language.java.types.Boolean) type);
        if (type instanceof org.emftext.language.java.types.Byte) return getWrapperTypeReferenceFor((org.emftext.language.java.types.Byte) type);
        if (type instanceof org.emftext.language.java.types.Double) return getWrapperTypeReferenceFor((org.emftext.language.java.types.Double) type);
        if (type instanceof org.emftext.language.java.types.Float) return getWrapperTypeReferenceFor((org.emftext.language.java.types.Float) type);
        if (type instanceof org.emftext.language.java.types.Long) return getWrapperTypeReferenceFor((org.emftext.language.java.types.Long) type);
        if (type instanceof org.emftext.language.java.types.Short) return getWrapperTypeReferenceFor((org.emftext.language.java.types.Short) type);
        if (type instanceof Void) return getWrapperTypeReferenceFor((Void) type);

        logger.warn("No dispatch method found for primitive type: " + type);
        return null;
    }

    // Private helper methods for primitive-to-wrapper mapping.
    private static TypeReference getWrapperTypeReferenceFor(org.emftext.language.java.types.Boolean type) { return createNamespaceClassifierReferenceForName("java.lang.Boolean"); }
    private static TypeReference getWrapperTypeReferenceFor(org.emftext.language.java.types.Byte type) { return createNamespaceClassifierReferenceForName("java.lang.Byte"); }
    private static TypeReference getWrapperTypeReferenceFor(Char type) { return createNamespaceClassifierReferenceForName("java.lang.Character"); }
    private static TypeReference getWrapperTypeReferenceFor(org.emftext.language.java.types.Double type) { return createNamespaceClassifierReferenceForName("java.lang.Double"); }
    private static TypeReference getWrapperTypeReferenceFor(org.emftext.language.java.types.Float type) { return createNamespaceClassifierReferenceForName("java.lang.Float"); }
    private static TypeReference getWrapperTypeReferenceFor(Int type) { return createNamespaceClassifierReferenceForName("java.lang.Integer"); }
    private static TypeReference getWrapperTypeReferenceFor(org.emftext.language.java.types.Long type) { return createNamespaceClassifierReferenceForName("java.lang.Long"); }
    private static TypeReference getWrapperTypeReferenceFor(org.emftext.language.java.types.Short type) { return createNamespaceClassifierReferenceForName("java.lang.Short"); }
    private static TypeReference getWrapperTypeReferenceFor(Void type) { return createNamespaceClassifierReferenceForName("java.lang.Void"); }


    /**
     * Creates an import statement for a class given its fully qualified name.
     *
     * @param qualifiedName The fully qualified name of the class to import (e.g., "java.util.List").
     * @return The newly created {@link ClassifierImport}.
     */
    public static ClassifierImport createJavaClassImport(String qualifiedName) {
        ConcreteClassifier classifier = getClassifier(qualifiedName);
        ClassifierImport classifierImport = ImportsFactory.eINSTANCE.createClassifierImport();
        classifierImport.setClassifier(classifier);
        return classifierImport;
    }

    /**
     * Creates a {@link NamespaceClassifierReference} for a class given its namespace and simple name.
     *
     * @param namespace The package name (e.g., "java.lang").
     * @param name      The simple class name (e.g., "String").
     * @return The newly created {@link NamespaceClassifierReference}.
     */
    public static NamespaceClassifierReference createNamespaceClassifierReferenceForName(String namespace, String name) {
        ConcreteClassifier classifier = getClassifier(namespace + "." + name);
        return createNamespaceClassifierReference(classifier);
    }

    /**
     * Creates a {@link NamespaceClassifierReference} for a class given its fully qualified name.
     *
     * @param qualifiedName The fully qualified name (e.g., "java.util.ArrayList").
     * @return The newly created {@link NamespaceClassifierReference}.
     */
    public static NamespaceClassifierReference createNamespaceClassifierReferenceForName(String qualifiedName) {
        return createNamespaceClassifierReference(getClassifier(qualifiedName));
    }

    /**
     * Retrieves a {@link ConcreteClassifier} from the Java classpath using its fully qualified name.
     *
     * @param qualifiedName The fully qualified name of the classifier.
     * @return The resolved {@link ConcreteClassifier}.
     */
    public static ConcreteClassifier getClassifier(String qualifiedName) {
        return (ConcreteClassifier) JavaClasspath.get().getClassifier(qualifiedName);
    }

    /**
     * Adds an annotation to a modifiable element (like a class, field, or method).
     *
     * @param annotableAndModifiable The element to annotate.
     * @param annotationName         The simple name of the annotation (e.g., "Override").
     * @return True if the annotation was successfully added.
     */
    public static boolean addAnnotationToAnnotableAndModifiable(AnnotableAndModifiable annotableAndModifiable, String annotationName) {
        AnnotationInstance newAnnotation = AnnotationsFactory.eINSTANCE.createAnnotationInstance();
        // We create a dummy class to represent the annotation type.
        // JaMoPP's post-processors will resolve this to the actual annotation.
        Class annotationClass = ClassifiersFactory.eINSTANCE.createClass();
        annotationClass.setName(annotationName);
        newAnnotation.setAnnotation(annotationClass);
        return annotableAndModifiable.getAnnotationsAndModifiers().add(newAnnotation);
    }

    /**
     * Adds an import to a class's compilation unit, checking to avoid duplicates.
     *
     * @param targetClass     The class whose compilation unit will get the import.
     * @param namespaceArray  The package of the entity to import, as a list of strings.
     * @param entityToImport  The simple name of the class/interface to import.
     */
    public static void addImportToClassFromString(ConcreteClassifier targetClass, List<String> namespaceArray, String entityToImport) {
        // Check if the import already exists to avoid duplicates.
        for (Import existingImport : targetClass.getContainingCompilationUnit().getImports()) {
            if (existingImport instanceof ClassifierImport) {
                if (Objects.equals(((ClassifierImport) existingImport).getClassifier().getName(), entityToImport)) {
                    return; // Import already exists.
                }
            }
        }

        // Create and add the new import.
        ClassifierImport newImport = ImportsFactory.eINSTANCE.createClassifierImport();
        ConcreteClassifier importedClassifier = ClassifiersFactory.eINSTANCE.createClass();
        importedClassifier.setName(entityToImport);
        newImport.setClassifier(importedClassifier);
        newImport.getNamespaces().addAll(namespaceArray);
        targetClass.getContainingCompilationUnit().getImports().add(newImport);
    }

    /**
     * Adds a new, empty, public constructor to a Java class.
     *
     * @param javaClass The class to which the constructor will be added.
     * @return The newly created {@link Constructor}.
     */
    public static Constructor addConstructorToClass(Class javaClass) {
        Constructor constructor = MembersFactory.eINSTANCE.createConstructor();
        return addConstructorToClass(constructor, javaClass);
    }

    /**
     * Adds a given constructor to a Java class, setting its name and public visibility.
     *
     * @param constructor The constructor object to add.
     * @param javaClass   The class to which the constructor will be added.
     * @return The constructor that was added.
     */
    public static Constructor addConstructorToClass(Constructor constructor, Class javaClass) {
        constructor.setName(javaClass.getName());
        constructor.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        javaClass.getMembers().add(constructor);
        return constructor;
    }

    /**
     * Creates an assignment statement in a constructor to initialize a field with a new object instance.
     * Example: {@code this.myField = new MyFieldType(...);}
     * The method intelligently tries to find arguments for the constructor call from other fields or parameters.
     *
     * @param newConstructorCall The {@link NewConstructorCall} object to configure (for the RHS).
     * @param constructor        The constructor where the statement will be added.
     * @param field              The field to be initialized.
     * @return True if the statement was successfully added, null if the target classifier is not a Class.
     */
    public static Boolean createNewForFieldInConstructor(NewConstructorCall newConstructorCall, Constructor constructor, Field field) {
        ConcreteClassifier classifier = field.getContainingConcreteClassifier();
        if (!(classifier instanceof Class targetClass)) {
            return null; // Can only do this within a class.
        }

        boolean wasAdded = addNewStatementToConstructor(newConstructorCall, constructor, field,
                (Field[]) Conversions.unwrapArray(targetClass.getFields(), Field.class),
                (Parameter[]) Conversions.unwrapArray(constructor.getParameters(), Parameter.class));
        return wasAdded;
    }

    /**
     * Helper method to build and add the field initialization statement to a constructor.
     *
     * @param newConstructorCall        The {@link NewConstructorCall} object to configure.
     * @param constructor               The constructor to modify.
     * @param field                     The field to initialize.
     * @param fieldsToUseAsArgument     An array of other fields available as potential constructor arguments.
     * @param parametersToUseAsArgument An array of parameters available as potential constructor arguments.
     * @return True if the statement was successfully added.
     */
    public static boolean addNewStatementToConstructor(NewConstructorCall newConstructorCall, Constructor constructor, Field field, Field[] fieldsToUseAsArgument, Parameter[] parametersToUseAsArgument) {
        // Create the statement that will hold the expression.
        ExpressionStatement expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement();

        // Create the assignment expression: this.field = new ...
        AssignmentExpression assignmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression();

        // Build the LHS: this.field
        SelfReference selfReference = ReferencesFactory.eINSTANCE.createSelfReference();
        selfReference.setSelf(LiteralsFactory.eINSTANCE.createThis());
        IdentifierReference fieldReference = ReferencesFactory.eINSTANCE.createIdentifierReference();
        fieldReference.setTarget(field);
        selfReference.setNext(EcoreUtil.copy(fieldReference));
        assignmentExpression.setChild(selfReference);
        assignmentExpression.setAssignmentOperator(OperatorsFactory.eINSTANCE.createAssignment());

        // Build the RHS: new Type(...)
        newConstructorCall.setTypeReference(EcoreUtil.copy(field.getTypeReference()));
        updateArgumentsOfConstructorCall(field, fieldsToUseAsArgument, parametersToUseAsArgument, newConstructorCall);
        assignmentExpression.setValue(newConstructorCall);

        expressionStatement.setExpression(assignmentExpression);
        return constructor.getStatements().add(expressionStatement);
    }

    /**
     * Intelligently populates the arguments of a {@link NewConstructorCall}.
     * It inspects the constructor of the field's type and tries to find matching arguments
     * among the available fields and parameters.
     *
     * @param field                     The field being initialized, used to determine the type to construct.
     * @param fieldsToUseAsArgument     Available fields to use as arguments.
     * @param parametersToUseAsArgument Available parameters to use as arguments.
     * @param newConstructorCall        The constructor call object to populate with arguments.
     */
    private static void updateArgumentsOfConstructorCall(Field field, Field[] fieldsToUseAsArgument, Parameter[] parametersToUseAsArgument, NewConstructorCall newConstructorCall) {
        // Step 1: Find the parameter types of the constructor we need to call.
        List<TypeReference> requiredParameterTypes = new ArrayList<>();
        Type fieldType = field.getTypeReference().getTarget();
        if (fieldType instanceof Class fieldClass) {
            // Find the first available constructor for the field's class.
            Iterable<Constructor> constructorsForClass = Iterables.filter(fieldClass.getMembers(), Constructor.class);
            if (!IterableExtensions.isNullOrEmpty(constructorsForClass)) {
                Constructor constructorForClass = constructorsForClass.iterator().next();
                for (Parameter parameter : constructorForClass.getParameters()) {
                    requiredParameterTypes.add(parameter.getTypeReference());
                }
            }
        }

        // Step 2: For each required parameter, try to find a matching source.
        for (TypeReference requiredType : requiredParameterTypes) {
            ReferenceableElement matchingSource = findMatchingTypeInParametersOrFields(requiredType, fieldsToUseAsArgument, parametersToUseAsArgument);
            if (matchingSource != null) {
                // A match was found, create a reference to it.
                IdentifierReference argumentReference = ReferencesFactory.eINSTANCE.createIdentifierReference();
                argumentReference.setTarget(matchingSource);
                newConstructorCall.getArguments().add(argumentReference);
            } else {
                // No match found, pass null as a fallback.
                newConstructorCall.getArguments().add(LiteralsFactory.eINSTANCE.createNullLiteral());
            }
        }
    }

    /**
     * Searches for a parameter or field that matches a given type.
     *
     * @param typeReferenceToFind       The type of the argument we are looking for.
     * @param fieldsToSearch            An array of fields to search within.
     * @param parametersToSearch        An array of parameters to search within.
     * @return The matching {@link Parameter} or {@link Field}, or null if no match is found.
     */
    private static ReferenceableElement findMatchingTypeInParametersOrFields(TypeReference typeReferenceToFind, Field[] fieldsToSearch, Parameter[] parametersToSearch) {
        // Priority 1: Search in constructor parameters.
        for (Parameter parameter : parametersToSearch) {
            if (Objects.equals(parameter.getTypeReference().getTarget(), typeReferenceToFind.getTarget())) {
                return parameter;
            }
        }

        // Priority 2: Search in other class fields.
        for (Field field : fieldsToSearch) {
            if (Objects.equals(field.getTypeReference().getTarget(), typeReferenceToFind.getTarget())) {
                return field;
            }
        }

        return null; // No match found.
    }
}
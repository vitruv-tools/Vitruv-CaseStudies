package tools.vitruv.applications.util.temporary.java;

import edu.kit.ipd.sdq.activextendannotations.Utility;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.generics.GenericsFactory;
import org.emftext.language.java.generics.QualifiedTypeArgument;
import org.emftext.language.java.generics.TypeArgument;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.imports.ImportsFactory;
import org.emftext.language.java.types.ClassifierReference;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.PrimitiveType;
import org.emftext.language.java.types.Type;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypedElement;
import org.emftext.language.java.types.TypesFactory;
import tools.vitruv.applications.util.temporary.other.UriUtil;

/**
 * A utility class for handling and manipulating JaMoPP (Java Model-based Parser) type objects.
 * This class provides static methods for resolving, creating, comparing, and modifying
 * various kinds of {@link TypeReference} and {@link Classifier} objects from the JaMoPP metamodel.
 */
@Utility
public final class JavaTypeUtil {
    // Logger for this class.
    private static final Logger logger = Logger.getLogger(JavaTypeUtil.class.getSimpleName());

    // A list of standard Java collection types supported by utility methods in this class.
    private static final List<Class<?>> supportedCollectionTypes = List.of(ArrayList.class, LinkedList.class, HashSet.class);

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private JavaTypeUtil() {}

    /**
     * Resolves a TypeReference to its target {@link Type}.
     * A {@link TypeReference} is a pointer, and this method gets the actual type it points to.
     *
     * @param typeReference The reference to resolve.
     * @return The resolved {@link Type}, or null if resolution fails or the input is invalid.
     */
    public static Type getTypeFromReference(TypeReference typeReference) {
        // Primitive types are direct types, not references, so we can cast and return.
        if (typeReference instanceof PrimitiveType) {
            return (PrimitiveType) typeReference;
        }
        // A ClassifierReference directly holds a target classifier.
        if (typeReference instanceof ClassifierReference) {
            return ((ClassifierReference) typeReference).getTarget();
        }
        // A NamespaceClassifierReference is a qualified name (e.g., a.b.C). We resolve the first part.
        if (typeReference instanceof NamespaceClassifierReference namespaceRef) {
            if (namespaceRef.getClassifierReferences() != null && !namespaceRef.getClassifierReferences().isEmpty()) {
                // Recursively resolve the first reference in the namespace chain.
                return getTypeFromReference(namespaceRef.getClassifierReferences().get(0));
            }
            return null;
        }

        // Log warnings for unexpected or null inputs.
        if (typeReference == null) {
            logger.warn("Cannot get Type of a null-TypeReference. Returning null.");
        } else {
            logger.warn(typeReference + " is not a supported TypeReference subclass. Returning null.");
        }
        return null;
    }

    /**
     * A convenience method that resolves a {@link TypeReference} to a {@link Classifier}.
     *
     * @param typeRef The type reference to resolve.
     * @return The resolved {@link Classifier}, or null if the reference does not point to a classifier.
     */
    public static Classifier getClassifierFromTypeReference(TypeReference typeRef) {
        Type type = getTypeFromReference(typeRef);
        if (type instanceof Classifier) {
            return (Classifier) type;
        }
        logger.warn("The TypeReference " + typeRef + " does not resolve to a Classifier. Returning null.");
        return null;
    }

    /**
     * Resolves a {@link TypeReference} to a {@link Classifier} and normalizes it.
     * "Normalization" involves resolving EMF proxies and standardizing the resource URI,
     * which is crucial for reliable comparisons and lookups across different model resources.
     *
     * @param typeRef The type reference to resolve and normalize.
     * @return The resolved and normalized {@link Classifier}, or null on failure.
     */
    public static Classifier getNormalizedClassifierFromTypeReference(TypeReference typeRef) {
        Type type = getTypeFromReference(typeRef);
        if (type instanceof Classifier classifier) {
            // If the classifier is a proxy, it's a placeholder. We need to resolve it to the actual object.
            if (classifier.eIsProxy()) {
                Resource resource = classifier.eResource();
                ResourceSet resourceSet = (resource != null) ? resource.getResourceSet() : null;
                classifier = (Classifier) EcoreUtil.resolve(classifier, resourceSet);
            }
            // Normalize the URI to ensure consistency.
            UriUtil.normalizeURI(classifier);
            return classifier;
        }
        logger.warn("The TypeReference " + typeRef + " does not resolve to a Classifier. Returning null.");
        return null;
    }

    /**
     * A convenience method that resolves a {@link TypeReference} to an {@link Interface}.
     *
     * @param typeRef The type reference to resolve.
     * @return The resolved {@link Interface}, or null if the reference does not point to an interface.
     */
    public static Interface getInterfaceFromTypeReference(TypeReference typeRef) {
        Type type = getTypeFromReference(typeRef);
        if (type instanceof Interface) {
            return (Interface) type;
        }
        logger.warn("The TypeReference " + typeRef + " does not resolve to an Interface. Returning null.");
        return null;
    }

    /**
     * Creates a list of {@link NamespaceClassifierReference} from a list of {@link ConcreteClassifier}.
     * This is useful for building model elements that require a list of type references, such as an `implements` clause.
     *
     * @param list A list of concrete classifiers.
     * @return An {@link EList} containing a {@link NamespaceClassifierReference} for each input classifier.
     */
    public static EList<NamespaceClassifierReference> createNamespaceReferenceFromList(List<? extends ConcreteClassifier> list) {
        EList<NamespaceClassifierReference> typeReferences = new BasicEList<>();
        for (ConcreteClassifier classifier : list) {
            typeReferences.add(JavaModificationUtil.createNamespaceClassifierReference(classifier));
        }
        return typeReferences;
    }

    /**
     * Sets the type reference for a given {@link TypedElement}.
     * If the provided type reference is null, it defaults to `void`.
     *
     * @param typedElement The element whose type should be set.
     * @param typeRef The {@link TypeReference} to set.
     */
    public static void setTypeReference(TypedElement typedElement, TypeReference typeRef) {
        if (typeRef != null) {
            typedElement.setTypeReference(typeRef);
        } else {
            // Default to void if the type reference is null.
            typedElement.setTypeReference(TypesFactory.eINSTANCE.createVoid());
        }
    }

    /**
     * Compares two {@link TypeReference}s for semantic equality.
     * Instead of checking for object identity, it checks if they resolve to the same underlying type.
     *
     * @param typeRef1 The first type reference.
     * @param typeRef2 The second type reference.
     * @return True if they are considered semantically equal, false otherwise.
     */
    public static boolean typeReferenceEquals(TypeReference typeRef1, TypeReference typeRef2) {
        if (typeRef1 == null && typeRef2 == null) {
            logger.warn("Both TypeReferences to compare are null. Returning true.");
            return true;
        }
        if (typeRef1 == null || typeRef2 == null) {
            return false;
        }

        // For primitive types (e.g., int, boolean), compare their specific classes.
        if (typeRef1 instanceof PrimitiveType && typeRef2 instanceof PrimitiveType) {
            return typeRef1.getClass().equals(typeRef2.getClass());
        }

        // For classifier types, resolve them and compare their names.
        if (typeRef1 instanceof NamespaceClassifierReference && typeRef2 instanceof NamespaceClassifierReference) {
            Classifier c1 = getClassifierFromTypeReference(typeRef1);
            Classifier c2 = getClassifierFromTypeReference(typeRef2);
            return c1 != null && c2 != null && Objects.equals(c1.getName(), c2.getName());
        }

        logger.warn("No dispatch logic found for comparing " + typeRef1 + " and " + typeRef2 + ". Returning false.");
        return false;
    }

    /**
     * Extracts the inner type reference from a generic collection type reference.
     * For example, given a reference to {@code List<String>}, this method returns a reference to {@code String}.
     *
     * @param typeRef The generic collection type reference.
     * @return The inner {@link TypeReference}, or null if not applicable.
     */
    public static TypeReference getInnerTypeReferenceOfCollectionTypeReference(TypeReference typeRef) {
        if (typeRef instanceof NamespaceClassifierReference nsClassifierRef) {
            if (!nsClassifierRef.getClassifierReferences().isEmpty()) {
                ClassifierReference classifierRef = nsClassifierRef.getClassifierReferences().get(0);
                if (!classifierRef.getTypeArguments().isEmpty()) {
                    TypeArgument typeArgument = classifierRef.getTypeArguments().get(0);
                    // Ensure the type argument is a qualified type (not a wildcard).
                    if (typeArgument instanceof QualifiedTypeArgument) {
                        return ((QualifiedTypeArgument) typeArgument).getTypeReference();
                    }
                }
            }
        }
        logger.warn("Cannot get inner TypeReference of a non-NamespaceClassifierReference or a non-generic reference. Returning null.");
        return null;
    }

    /**
     * Checks if two {@link TypeReference}s point to the same target classifier object.
     *
     * @param reference1 The first type reference.
     * @param reference2 The second type reference.
     * @return True if the references are identical or resolve to the same classifier object.
     */
    public static boolean hasSameTargetReference(TypeReference reference1, TypeReference reference2) {
        if (Objects.equals(reference1, reference2)) {
            return false;
        }
        Classifier target1 = getClassifierFromTypeReference(reference1);
        Classifier target2 = getClassifierFromTypeReference(reference2);
        return !Objects.equals(target1, target2);
    }

    /**
     * Finds all interfaces implemented by a class from a list of its super-type references.
     *
     * @param typeReferences The list of type references (e.g., from an `implements` or `extends` clause).
     * @return An {@link ArrayList} of all resolved {@link Interface}s.
     */
    public static ArrayList<Interface> findImplementingInterfacesFromTypeRefs(EList<TypeReference> typeReferences) {
        ArrayList<Interface> implementingInterfaces = new ArrayList<>();
        for (TypeReference typeRef : typeReferences) {
            // We use the normalized classifier to ensure correct resolution.
            Classifier classifier = getNormalizedClassifierFromTypeReference(typeRef);
            if (classifier instanceof Interface) {
                implementingInterfaces.add((Interface) classifier);
            }
        }
        return implementingInterfaces;
    }

    /**
     * Gets the fully qualified name of a classifier (e.g., "java.lang.String").
     *
     * @param classifier The classifier model object.
     * @return The fully qualified name as a String.
     */
    public static String getQualifiedName(Classifier classifier) {
        if (classifier instanceof ConcreteClassifier concreteClassifier) {
            // The compilation unit contains the package declaration (namespaces).
            CompilationUnit cu = concreteClassifier.getContainingCompilationUnit();
            if (cu != null && cu.getNamespaces() != null && !cu.getNamespaces().isEmpty()) {
                String packageName = String.join(".", cu.getNamespaces());
                return packageName + "." + concreteClassifier.getName();
            }
            return concreteClassifier.getName(); // No package, return simple name.
        }
        return classifier.getName(); // For classifiers without compilation unit info.
    }

    /**
     * Checks if a {@link TypeReference} points to a known collection type (e.g., ArrayList, HashSet).
     *
     * @param jRef The Java type reference to check.
     * @return True if it is a supported collection type, false otherwise.
     */
    public static boolean isCollectionTypeReference(TypeReference jRef) {
        Classifier classifier = getNormalizedClassifierFromTypeReference(jRef);
        if (classifier != null) {
            String qualifiedName = getQualifiedName(classifier);
            // Check if the qualified name matches any of the supported collection types.
            return supportedCollectionTypes.stream()
                    .anyMatch(supportedType -> Objects.equals(supportedType.getName(), qualifiedName));
        }
        return false;
    }

    /**
     * Adds the necessary Java import statement(s) to a {@link CompilationUnit} for a given {@link TypeReference}.
     * This method is recursive, so if the type is generic (e.g., `List<MyClass>`), it will add imports
     * for both `List` and `MyClass`.
     *
     * @param compUnit The compilation unit to add the import to.
     * @param jTypeRef The type reference for which an import is needed.
     */
    public static void addJavaImport(CompilationUnit compUnit, TypeReference jTypeRef) {
        // No import needed for primitive types.
        if (jTypeRef instanceof PrimitiveType) {
            return;
        }

        // Add an import for the main type itself.
        if (jTypeRef.getTarget() instanceof ConcreteClassifier) {
            addJavaImport(compUnit, (ConcreteClassifier) jTypeRef.getTarget());
        }

        // Recursively add imports for any generic type arguments.
        if (jTypeRef instanceof NamespaceClassifierReference nsRef) {
            if (!nsRef.getClassifierReferences().isEmpty()) {
                EList<TypeArgument> typeArguments = nsRef.getClassifierReferences().get(0).getTypeArguments();
                typeArguments.stream()
                        .filter(QualifiedTypeArgument.class::isInstance)
                        .map(QualifiedTypeArgument.class::cast)
                        .map(QualifiedTypeArgument::getTypeReference)
                        .filter(Objects::nonNull)
                        .forEach(innerTypeRef -> addJavaImport(compUnit, innerTypeRef));
            }
        }
    }

    /**
     * Adds a {@link ClassifierImport} for a given {@link ConcreteClassifier} to a {@link CompilationUnit}.
     * It avoids adding duplicate imports.
     *
     * @param compUnit The compilation unit to add the import to.
     * @param jType    The concrete classifier to import.
     */
    public static void addJavaImport(CompilationUnit compUnit, ConcreteClassifier jType) {
        if (compUnit == null || jType == null || jType instanceof PrimitiveType) {
            return;
        }

        // Check if the import already exists to avoid duplicates.
        boolean alreadyImported = compUnit.getImports().stream()
                .filter(ClassifierImport.class::isInstance)
                .map(ClassifierImport.class::cast)
                .anyMatch(it -> Objects.equals(it.getClassifier().getName(), jType.getName()));

        if (alreadyImported) {
            return;
        }

        // Create the import statement model element.
        ClassifierImport classifierImport = ImportsFactory.eINSTANCE.createClassifierImport();
        classifierImport.setClassifier(jType);

        // Copy the package (namespaces) from the source type's compilation unit.
        CompilationUnit sourceCU = jType.getContainingCompilationUnit();
        if (sourceCU != null && sourceCU.getNamespaces() != null) {
            classifierImport.getNamespaces().addAll(sourceCU.getNamespaces());
        }

        compUnit.getImports().add(classifierImport);
    }

    /**
     * Gets the corresponding wrapper type for a given primitive type. For example, for 'int' it
     * returns a reference to 'java.lang.Integer'. This is necessary for using primitive types
     * in generic collections.
     *
     * @param primitiveType The primitive type reference. Must be an instance of {@link PrimitiveType}.
     * @return A {@link TypeReference} to the wrapper type, or the original reference if no
     *         corresponding wrapper is found.
     */
    private static TypeReference getWrapperTypeReferenceForPrimitiveType(TypeReference primitiveType) {
        // This method handles the "boxing" of primitives.
        if (primitiveType instanceof org.emftext.language.java.types.Int) {
            return JavaModificationUtil.createNamespaceClassifierReferenceForName("java.lang.Integer");
        }
        if (primitiveType instanceof org.emftext.language.java.types.Boolean) {
            return JavaModificationUtil.createNamespaceClassifierReferenceForName("java.lang.Boolean");
        }
        if (primitiveType instanceof org.emftext.language.java.types.Char) {
            return JavaModificationUtil.createNamespaceClassifierReferenceForName("java.lang.Character");
        }
        if (primitiveType instanceof org.emftext.language.java.types.Double) {
            return JavaModificationUtil.createNamespaceClassifierReferenceForName("java.lang.Double");
        }
        if (primitiveType instanceof org.emftext.language.java.types.Float) {
            return JavaModificationUtil.createNamespaceClassifierReferenceForName("java.lang.Float");
        }
        if (primitiveType instanceof org.emftext.language.java.types.Long) {
            return JavaModificationUtil.createNamespaceClassifierReferenceForName("java.lang.Long");
        }
        if (primitiveType instanceof org.emftext.language.java.types.Short) {
            return JavaModificationUtil.createNamespaceClassifierReferenceForName("java.lang.Short");
        }
        if (primitiveType instanceof org.emftext.language.java.types.Byte) {
            return JavaModificationUtil.createNamespaceClassifierReferenceForName("java.lang.Byte");
        }
        logger.warn("Could not find wrapper type for primitive type: " + primitiveType
                + ". Returning original reference.");
        return primitiveType;
    }

    /**
     * Creates a complete generic collection type reference by combining a collection type and an inner type.
     * For example, it can combine `List` and `Integer` to create `List<Integer>`.
     * This method handles primitive type wrapping automatically.
     *
     * @param collectionTypeReference A reference to the collection type (e.g., `java.util.List`).
     * @param innerTypeReference A reference to the inner type (e.g., `int` or `String`).
     * @return The combined generic type reference.
     */
    public static NamespaceClassifierReference createCollectionTypeReference(NamespaceClassifierReference collectionTypeReference, TypeReference innerTypeReference) {
        TypeReference effectiveInnerReference = innerTypeReference;
        // If the inner type is primitive (e.g., int), wrap it in its object equivalent (e.g., Integer).
        if (innerTypeReference instanceof PrimitiveType) {
            effectiveInnerReference = getWrapperTypeReferenceForPrimitiveType(innerTypeReference);
        }

        // Create the generic type argument (e.g., <Integer>).
        QualifiedTypeArgument qualifiedTypeArgument = GenericsFactory.eINSTANCE.createQualifiedTypeArgument();
        qualifiedTypeArgument.setTypeReference(effectiveInnerReference);

        // Add the argument to the collection reference.
        collectionTypeReference.getClassifierReferences().get(0).getTypeArguments().add(qualifiedTypeArgument);
        return collectionTypeReference;
    }

    /**
     * Overloaded method to create a generic collection type reference from a {@link ConcreteClassifier}
     * representing the collection type.
     *
     * @param collectionTypeClassifier The classifier for the collection type.
     * @param innerTypeReference A reference to the inner type.
     * @return The combined generic type reference.
     */
    public static NamespaceClassifierReference createCollectionTypeReference(ConcreteClassifier collectionTypeClassifier, TypeReference innerTypeReference) {
        NamespaceClassifierReference collectionTypeReference = JavaModificationUtil.createNamespaceClassifierReference(collectionTypeClassifier);
        return createCollectionTypeReference(collectionTypeReference, innerTypeReference);
    }

    /**
     * Overloaded method to create a generic collection type reference from a standard Java {@link Class} object.
     *
     * @param collectionType The `Class` object for the collection type (e.g., `ArrayList.class`).
     * @param innerTypeReference A reference to the inner type.
     * @return The combined generic type reference.
     */
    public static NamespaceClassifierReference createCollectionTypeReference(Class<?> collectionType, TypeReference innerTypeReference) {
        String qualifiedName = collectionType.getName();
        String simpleName = collectionType.getSimpleName();
        // Extract package name safely.
        int lastDot = qualifiedName.lastIndexOf('.');
        String namespace = (lastDot > 0) ? qualifiedName.substring(0, lastDot) : "";

        // Create a model reference from the Java Class information.
        NamespaceClassifierReference collectionTypeReference = JavaModificationUtil.createNamespaceClassifierReferenceForName(namespace, simpleName);
        return createCollectionTypeReference(collectionTypeReference, innerTypeReference);
    }
}
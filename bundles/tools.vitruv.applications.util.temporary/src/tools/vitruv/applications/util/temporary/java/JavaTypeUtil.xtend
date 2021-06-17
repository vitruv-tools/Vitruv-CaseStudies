package tools.vitruv.applications.util.temporary.java

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.ArrayList
import java.util.HashSet
import java.util.LinkedList
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.generics.GenericsFactory
import org.emftext.language.java.generics.QualifiedTypeArgument
import org.emftext.language.java.imports.ClassifierImport
import org.emftext.language.java.imports.ImportsFactory
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.PrimitiveType
import org.emftext.language.java.types.Type
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypedElement
import org.emftext.language.java.types.TypesFactory

import static tools.vitruv.applications.util.temporary.other.UriUtil.normalizeURI
import static tools.vitruv.domains.java.util.JavaModificationUtil.*

/**
 * Class for type and typereference util function
 * @author Fei
 */
@Utility
class JavaTypeUtil {

    static val logger = Logger.getLogger(JavaTypeUtil.simpleName)

    static val List<Class<?>> supportedCollectionTypes = #[ArrayList, LinkedList, HashSet]

    /**
     * Retrieves the type referenced by a type reference. This can either be a classifier or a primitive type.
     * @return the type wrapped in the type reference or null if it is a Void type or unknown type reference.
     */
    def private static dispatch Type getTypeFromReference(TypeReference typeReference) {
        logger.warn(typeReference + " is neither a NamespaceClassifierReference nor a PrimitiveType. Returning null.")
        return null
    }

    def private static dispatch Type getTypeFromReference(Void nullTypeReference) {
        logger.warn("Cannot get Type of a null-TypeReference. Returning null.")
        return null
    }

    def private static dispatch Type getTypeFromReference(PrimitiveType primitiveType) {
        return primitiveType
    }

    def private static dispatch Type getTypeFromReference(ClassifierReference classifierReference) {
        return classifierReference.target
    }

    def private static dispatch Type getTypeFromReference(NamespaceClassifierReference namespace) {
        if (namespace === null || namespace.classifierReferences.nullOrEmpty) {
            return null
        }
        return getTypeFromReference(namespace.classifierReferences.head)
    }

    /**
     * Retrieves the classifier wrapped in a type reference.
     * @return the classifier or null if the type reference does not contain any classifier.
     */
    def static Classifier getClassifierFromTypeReference(TypeReference typeRef) {
        val type = getTypeFromReference(typeRef)
        if (type instanceof Classifier) {
            return type
        }
        logger.warn("The TypeReference " + typeRef + " does not contain a Classifier. Returning null.")
        return null
    }

    /**
     * Retrieves the classifier wrapped in a type reference. Proxys are resolved and the classifier URI is normalized.
     * @return the classifier or null if the type reference does not contain any classifier.
     */
    def static Classifier getNormalizedClassifierFromTypeReference(TypeReference typeRef) {
        var type = getTypeFromReference(typeRef)
        if (type instanceof PrimitiveType) {
        	type = type.wrapPrimitiveType
        }
        if (type instanceof Classifier) {
            if (type.eIsProxy) { // resolve proxy
                val resourceSet = type.eResource?.resourceSet // resource can be null, but EcoreUtil.resolve() can handle null as resource set
                type = EcoreUtil.resolve(type, resourceSet) as Classifier
            }
            normalizeURI(type)
            return type as Classifier
        }
        logger.warn("The TypeReference " + typeRef + " does not contain a Classifier. Returning null.")
        return null
    }

    /**
     * Retrieves the interface wrapped in a type reference.
     * @return the interface or null if the type reference does not contain any interfaces.
     */
    def static Interface getInterfaceFromTypeReference(TypeReference typeRef) {
        val type = getTypeFromReference(typeRef)
        if (type instanceof Interface) {
            return type
        }
        logger.warn("The TypeReference " + typeRef + " does not contain a Interface. Returning null.")
        return null
    }

    /**
     * Converts a list with ConcreteClassifiers to a list with corresponding NamespaceClassifierRefrences.
     */
    def static EList<NamespaceClassifierReference> createNamespaceReferenceFromList(List<? extends ConcreteClassifier> list) {
        val typeReferences = new BasicEList<NamespaceClassifierReference>
        for (ConcreteClassifier i : list) {
            typeReferences += createNamespaceClassifierReference(i)
        }
        return typeReferences
    }

    def static TypeReference createCollectiontypeReference(String collectionQualifiedName, ConcreteClassifier innerTypeClass) {
        val innerTypeReference = createNamespaceClassifierReference(innerTypeClass)
        val qualifiedTypeArgument = GenericsFactory.eINSTANCE.createQualifiedTypeArgument()
        qualifiedTypeArgument.typeReference = innerTypeReference
        val collectionClassNamespaceReference = createNamespaceClassifierReferenceForName(collectionQualifiedName)
        collectionClassNamespaceReference.classifierReferences.get(0).typeArguments += qualifiedTypeArgument
        return collectionClassNamespaceReference
    }

    def static setTypeReference(TypedElement typedElement, TypeReference typeRef) {
        if (typeRef !== null) {
            typedElement.typeReference = typeRef
        } else {
            typedElement.typeReference = TypesFactory.eINSTANCE.createVoid
        }
    }

    /**
     * Compares two type references. Both type references must be primitive types or
     * namespaceclassifierreferences or null.
     * If they are primitive types, it will check if they are same primitive type
     * If they are NamespaceClassifierReferences, it will check the name of their wrapped classifiers for equality
     */
    def static dispatch typeReferenceEquals(TypeReference typeRef1, TypeReference typeRef2) {
        logger.warn("No dispatch Method found for the typeReferences " + typeRef1 + " and " + typeRef2 + ". Returning false.")
        return false
    }

    def static dispatch typeReferenceEquals(NamespaceClassifierReference typeRef1, NamespaceClassifierReference typeRef2) {
        return getClassifierFromTypeReference(typeRef1).name.equals(getClassifierFromTypeReference(typeRef2).name)
    }

    def static dispatch typeReferenceEquals(PrimitiveType primType1, PrimitiveType primtype2) {
        return primType1.class.equals(primtype2.class)
    }

    def static dispatch typeReferenceEquals(Void type1, Void typ22) {
        logger.warn("Both TypeReferences to compare are null. Returning true.")
        return true
    }

    def static getInnerTypeReferenceOfCollectionTypeReference(TypeReference typeRef) {
        if (typeRef instanceof NamespaceClassifierReference) {
            return (typeRef.classifierReferences.head.typeArguments.head as QualifiedTypeArgument).typeReference
        }
        logger.warn("Cannot get inner TypeReference of a non-NamespaceClassifierReference. Returning null.")
        return null
    }

    def static boolean hasSameTargetReference(TypeReference reference1, TypeReference reference2) {
        if (reference1 == reference2 || reference1.equals(reference2)) {
            return true
        }
        val target1 = getClassifierFromTypeReference(reference1)
        val target2 = getClassifierFromTypeReference(reference2)
        return target1 == target2 || target1.equals(target2)
    }

    def static findImplementingInterfacesFromTypeRefs(EList<TypeReference> typeReferences) {
        val implementingInterfaces = new ArrayList<Interface>
        for (typeRef : typeReferences) {
            val classifier = getNormalizedClassifierFromTypeReference(typeRef)
            if (classifier instanceof Interface) {
                implementingInterfaces.add(classifier)
            }
        }
        return implementingInterfaces
    }

    def static String getQualifiedName(Classifier classifier) {
        if (classifier instanceof ConcreteClassifier) {
            val namespace = if (classifier.containingCompilationUnit !== null) classifier.containingCompilationUnit.namespaces.join(".") else ""
            val qualifiedName = if (namespace != "") namespace + "." + classifier.name else classifier.name
            return qualifiedName
        } else {
            // can't retrieve namespaces
            return classifier.name
        }
    }

    def static boolean isCollectionTypeReference(TypeReference jRef) {
        val classifier = getNormalizedClassifierFromTypeReference(jRef)
        if (classifier !== null) {
            val qualifiedName = getQualifiedName(classifier)
            if (supportedCollectionTypes.exists[it.name == qualifiedName]) {
                return true
            }
        }
        return false
    }

    def static dispatch TypeReference getInnerTypeRefOfCollectionReference(TypeReference collRef) {
        return null
    }

    def static dispatch TypeReference getInnerTypeRefOfCollectionReference(ClassifierReference collRef) {
        val typeArgument = collRef.typeArguments.head
        if (typeArgument !== null && typeArgument instanceof QualifiedTypeArgument) {
            val typeRef = (typeArgument as QualifiedTypeArgument).typeReference
            return typeRef
        }
        return null
    }

    def static dispatch TypeReference getInnerTypeRefOfCollectionReference(NamespaceClassifierReference collRef) {
        return if (!collRef.classifierReferences.nullOrEmpty)
            getInnerTypeRefOfCollectionReference(collRef.classifierReferences.head)
        else
            null
    }

    def static void addJavaImport(CompilationUnit compUnit, TypeReference jTypeRef) {
        if (jTypeRef instanceof PrimitiveType) {
            return // nothing to do
        } else if (jTypeRef.target !== null && jTypeRef.target instanceof ConcreteClassifier) {
            val jType = jTypeRef.target as ConcreteClassifier
            addJavaImport(compUnit, jType)
            // add imports for typeArguments if any exist
            if (jTypeRef instanceof NamespaceClassifierReference) {
                val typeArguments = jTypeRef.classifierReferences.get(0).typeArguments
                for (typeArgument : typeArguments.filter(QualifiedTypeArgument).filter[it.typeReference !== null]) {
                    typeArgument.typeReference
                    addJavaImport(compUnit, typeArgument.typeReference)
                }
            }
        }
    }

    def static ClassifierImport addJavaImport(CompilationUnit compUnit, ConcreteClassifier jType) {
        if (compUnit === null) {
        	return null
        }
        if (jType === null || jType instanceof PrimitiveType) {
            return null // nothing to do
        }

        /*
         * If a referenced type lives in the same package as the concerned CompilationUnit, then an import
         * is generally unnecessary. However, if the Compilation unit is then moved to another package,
         * the transformation has to ensure that imports for types in the same package are added afterwards.
         * 
         * TODO The samePackage check is disabled, because the required transformation implementation to support
         * this feature is still missing. Until then, imports are added for every referenced Type, so that the
         * CompilationUnit remains compilable after beeing moved.
         */
        // val targetNamespace = if (jType.containingCompilationUnit !== null) jType.containingCompilationUnit.namespaces.join(".") else ""
        // val samePackage = targetNamespace.equals(compUnit.namespaces.join("."))
        val samePackage = false
        val alreadyImported = compUnit.imports.filterNull.filter(ClassifierImport).exists [
            it.classifier.name == jType.name
        // TODO unclear how to handle name conflicts
        /* It is possible that two Types have the same name but different namespaces. In this case
         * at least one of the types has to be used fully qualified throughout the compilation unit.
         * How should such a problem be communicated and how can the JaMoPP printer then be triggered
         * to write TypeReferences fully qualified.
         */
        ]
        if (!samePackage && !alreadyImported) {
            val classifierImport = ImportsFactory.eINSTANCE.createClassifierImport
            if (null !== jType.containingCompilationUnit) {
                if (null !== jType.containingCompilationUnit.namespaces) {
                    classifierImport.namespaces.addAll(jType.containingCompilationUnit.namespaces)
                }
            }
            classifierImport.classifier = jType
            compUnit.imports.add(classifierImport)
            return classifierImport
        } else {
            return null // nothing to do
        }
    }

    def static NamespaceClassifierReference createCollectionTypeReference(
        NamespaceClassifierReference collectionTypeReference,
        TypeReference innerTypeReference
    ) {
        // wrap typeReference if necessary
        var wrappedInnerReference = innerTypeReference
        if (wrappedInnerReference instanceof PrimitiveType) {
            wrappedInnerReference = getWrapperTypeReferenceForPrimitiveType(wrappedInnerReference)
        }

        // set the inner type reference on the NamespaceClassifierReference of the Collection type
        val qualifiedTypeArgument = GenericsFactory.eINSTANCE.createQualifiedTypeArgument()
        qualifiedTypeArgument.typeReference = wrappedInnerReference
        collectionTypeReference.classifierReferences.get(0).typeArguments += qualifiedTypeArgument

        return collectionTypeReference
    }

    def static NamespaceClassifierReference createCollectionTypeReference(
        ConcreteClassifier collectionTypeClassifier,
        TypeReference innerTypeReference
    ) {
        val collectionTypeReference = createNamespaceClassifierReference(collectionTypeClassifier)
        return createCollectionTypeReference(collectionTypeReference, innerTypeReference)
    }

    def static NamespaceClassifierReference createCollectionTypeReference(
        Class<?> collectionType,
        TypeReference innerTypeReference
    ) {
        val collectionNamespace = collectionType.name.replace("." + collectionType.simpleName, "")
        val collectionSimpleName = collectionType.simpleName
        val collectionTypeReference = createNamespaceClassifierReferenceForName(collectionNamespace, collectionSimpleName)

        return createCollectionTypeReference(collectionTypeReference, innerTypeReference)
    }

}

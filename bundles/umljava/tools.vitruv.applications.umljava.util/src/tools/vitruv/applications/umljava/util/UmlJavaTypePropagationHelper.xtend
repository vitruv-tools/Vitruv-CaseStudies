package tools.vitruv.applications.umljava.util

import java.util.ArrayList
import java.util.HashSet
import java.util.LinkedList
import java.util.List
import java.util.Optional
import java.util.function.Function
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.UMLPackage
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.generics.GenericsFactory
import org.emftext.language.java.generics.QualifiedTypeArgument
import org.emftext.language.java.imports.ClassifierImport
import org.emftext.language.java.imports.ImportsFactory
import org.emftext.language.java.types.Boolean
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.Double
import org.emftext.language.java.types.Int
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypesFactory
import tools.vitruv.domains.java.util.JavaModificationUtil
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import tools.vitruv.framework.userinteraction.UserInteractor

import static tools.vitruv.applications.umljava.util.CommonUtil.*

/**
 * Helper class for the Uml <-> Java - reactions. Contains functions for handling java::TypeReferences
 * and user interaction concerned with Type and CollectionType propagation.
 * 
 * @author Torsten Syma
 */
class UmlJavaTypePropagationHelper {
    private static val logger = Logger.getLogger(UmlJavaTypePropagationHelper.simpleName)

    public static val UML_PRIMITIVE_BOOLEAN_TAG = "Boolean"
    public static val UML_PRIMITIVE_REAL_TAG = "Real"
    public static val UML_PRIMITIVE_INTEGER_TAG = "Integer"
    public static val UML_PRIMITIVE_STRING_TAG = "String"

    public static def List<PrimitiveType> getSupportedPredefinedUmlPrimitiveTypes(ResourceSet rs) {
        var List<PrimitiveType> umlPrimitiveTypes = #[]
        val uri = URI.createURI("pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml")
        val resource = rs.getResource(uri, true)
        umlPrimitiveTypes = resource.allContents.filter(PrimitiveType).toList
        return umlPrimitiveTypes
    }

    public static def List<PrimitiveType> getSupportedPredefinedUmlPrimitiveTypes(Function<URI, Resource> resourceRetriever) {
        var List<PrimitiveType> umlPrimitiveTypes = #[]
        val uri = URI.createURI("pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml")
        val resource = resourceRetriever.apply(uri)
        if (resource !== null) {
            umlPrimitiveTypes = resource.allContents.filter(PrimitiveType).toList
        }
        return umlPrimitiveTypes
    }

    public static def void registerPredefinedUmlPrimitiveTypes(CorrespondenceModel cm, ResourceSet rs) {
        var List<PrimitiveType> umlPrimitiveTypes = getSupportedPredefinedUmlPrimitiveTypes(rs)
        for (primitive : umlPrimitiveTypes) {
            val alreadyRegistered = ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm, UMLPackage.Literals.PRIMITIVE_TYPE, primitive.name,
                PrimitiveType).head
            if (alreadyRegistered === null)
                ReactionsCorrespondenceHelper.addCorrespondence(cm, UMLPackage.Literals.PRIMITIVE_TYPE, primitive, primitive.name)
        }
    }

    private static val List<Class<?>> supportedCollectionTypes = #[ArrayList, LinkedList, HashSet]

    def static boolean isCollectionTypeReference(TypeReference jRef) {
        val classifier = getClassifier(jRef)
        if (classifier !== null) {
            val qualifiedName = getQualifiedName(classifier)
            if (supportedCollectionTypes.exists[it.name == qualifiedName]) {
                return true
            }
        }
        return false
    }

    /**
     * Prompts a message to the user that allows him to choose a collection datatype.
     * 
     * @param userInteractor the userInteractor to prompt the message
     * @return the selected Collection implementation Class 
     */
    def static Class<?> userSelectCollectionType(UserInteractor userInteractor) {
        val String selectTypeMsg = "Select a Collection type for the association end"
        val int selectedType = userInteractor.singleSelectionDialogBuilder.message(selectTypeMsg).choices(supportedCollectionTypes.map[it.name]).windowModality(
            WindowModality.MODAL).startInteraction()
        return supportedCollectionTypes.get(selectedType)
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

    def static dispatch Classifier getClassifier(Void jRef) {
        return null
    }

    def static dispatch Classifier getClassifier(TypeReference jRef) {
        return null
    }

    def static dispatch Classifier getClassifier(org.emftext.language.java.types.PrimitiveType jRef) {
        return null
    }

    def static dispatch Classifier getClassifier(ClassifierReference jRef) {
        var classifier = jRef.target
        if (classifier === null) return null

        // resolve
        if (classifier.eIsProxy) {
            val resSet = classifier.eResource.resourceSet
            classifier = EcoreUtil.resolve(classifier, resSet) as Classifier
        }
        // normalize
        normalizeURI(classifier)
        return classifier
    }

    def static dispatch Classifier getClassifier(NamespaceClassifierReference jRef) {
        return if (!jRef.classifierReferences.nullOrEmpty)
            getClassifier(jRef.classifierReferences.head)
        else
            null
    }

    def static dispatch Type getUmlTypeFromReference(Void jRef, CorrespondenceModel cm) {
        return null
    }

    def static dispatch Type getUmlTypeFromReference(TypeReference jRef, CorrespondenceModel cm) {
        return null
    }

    def static dispatch Type getUmlTypeFromReference(org.emftext.language.java.types.PrimitiveType jRef, CorrespondenceModel cm) {
        return mapJavaPrimitiveToUmlPrimitive(jRef, cm)
    }

    def static dispatch Type getUmlTypeFromReference(ClassifierReference jRef, CorrespondenceModel cm) {
        // it could be a wrapped primitive type or java.lang.String
        val umlPrimitive = mapJavaPrimitiveToUmlPrimitive(jRef, cm)
        if (umlPrimitive !== null) {
            return umlPrimitive
        }

        val classifier = getClassifier(jRef)
        if (classifier !== null)
            return ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm, classifier, null, Type).head
        else {
            return null
        }
    }

    def static dispatch Type getUmlTypeFromReference(NamespaceClassifierReference jRef, CorrespondenceModel cm) {
        return if (!jRef.classifierReferences.nullOrEmpty)
            getUmlTypeFromReference(jRef.classifierReferences.head, cm)
        else
            null
    }

    // Copied from Java2PCMUtils
    def private static normalizeURI(EObject eObject) {
        if (null === eObject.eResource || null === eObject.eResource.resourceSet) {
            return false
        }
        val resource = eObject.eResource
        val resourceSet = resource.resourceSet
        val uri = resource.getURI
        val uriConverter = resourceSet.getURIConverter
        val normalizedURI = uriConverter.normalize(uri)
        resource.URI = normalizedURI
        return true
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

    def static org.emftext.language.java.types.PrimitiveType unwrapWrappedPrimitiveType(TypeReference jRef) {
        val classifier = getClassifier(jRef)
        if (classifier === null) return null
        val qualifiedName = getQualifiedName(classifier)

        return switch (qualifiedName) {
            case "java.lang.Boolean": TypesFactory.eINSTANCE.createBoolean
            case "java.lang.Byte": TypesFactory.eINSTANCE.createByte
            case "java.lang.Character": TypesFactory.eINSTANCE.createChar
            case "java.lang.Double": TypesFactory.eINSTANCE.createDouble
            case "java.lang.Float": TypesFactory.eINSTANCE.createFloat
            case "java.lang.Integer": TypesFactory.eINSTANCE.createInt
            case "java.lang.Long": TypesFactory.eINSTANCE.createLong
            case "java.lang.Short": TypesFactory.eINSTANCE.createShort
            case "java.lang.Void": TypesFactory.eINSTANCE.createVoid
            default: null
        }
    }

    /**
     * Retrieves the predefined uml::PrimitiveType corresponding to the java::TypeReference.
     * <br><br>
     * This method is defined with TypeReference as input instead of the more specific PrimitiveType, 
     * because java.lang.String, which is a Classifier held by a TypeReference, and wrapped primitive types
     * are mapped to uml::PrimitiveTypes as well and are supposed to be retrieved with this method.
     * <br><br>
     * Currently supported java types because only those have a good correspondence in "pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml": 
     * 	Boolean, Integer, Double, String 
     * 
     * @param jRef 
     * 		the java type (ClassifierReference or PrimitiveType) for which to retrieve the registered uml::PrimitiveType
     * @param cm
     * 		the correspondenceModel where the uml::PrimitiveTypes are registered
     * @return
     * 		the mapped uml::PrimitiveType or null if no matching mapping exists
     */
    def static dispatch PrimitiveType mapJavaPrimitiveToUmlPrimitive(TypeReference jRef, CorrespondenceModel cm) {
        val classifier = getClassifier(jRef)
        if (classifier !== null) {
            // check if it is a wrapped primitive type
            val unwrappedPrimitive = unwrapWrappedPrimitiveType(jRef)
            if (unwrappedPrimitive !== null)
                return mapJavaPrimitiveToUmlPrimitive(unwrappedPrimitive, cm)
            // check if it is of type String, which has to be mapped to an uml::PrimitiveType 
            if (getQualifiedName(classifier) == "java.lang.String") {
                val umlString = ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm, UMLPackage.Literals.PRIMITIVE_TYPE, UML_PRIMITIVE_STRING_TAG,
                    PrimitiveType).head
                return umlString
            } else {
                return null
            }
        }
    }

    def static dispatch PrimitiveType mapJavaPrimitiveToUmlPrimitive(org.emftext.language.java.types.PrimitiveType jRef, CorrespondenceModel cm) {
        return switch jRef {
            case jRef instanceof Boolean:
                ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm, UMLPackage.Literals.PRIMITIVE_TYPE, UML_PRIMITIVE_BOOLEAN_TAG, PrimitiveType).
                    head
            case jRef instanceof Double:
                ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm, UMLPackage.Literals.PRIMITIVE_TYPE, UML_PRIMITIVE_REAL_TAG, PrimitiveType).head
            case jRef instanceof Int:
                ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm, UMLPackage.Literals.PRIMITIVE_TYPE, UML_PRIMITIVE_INTEGER_TAG, PrimitiveType).
                    head
            default: {
                logger.warn("Tried to map a java primitive type, that is not supported by the uml <-> java transformations: " + jRef)
                null
            }
        }
    }

    def static TypeReference mapUmlPrimitiveToJavaPrimitive(PrimitiveType uType) {
        switch (uType.name) {
            case "Boolean":
                return TypesFactory.eINSTANCE.createBoolean
            case "Real":
                return TypesFactory.eINSTANCE.createDouble
            case "Integer":
                return TypesFactory.eINSTANCE.createInt
            case "String":
                return JavaModificationUtil.createNamespaceClassifierReferenceForName("java.lang", "String")
            default: {
//            	throw new IllegalArgumentException("Unknown standard primitive type name: " +  uType.name)
                logger.warn("(uml -> java) Unsupported uml::PrimitiveType with name: " + uType.name +
                    "\n Please use the types defined in \"pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml\".")
                return TypesFactory.eINSTANCE.createVoid
            }
        }
    }

    // ///////////////////////////////////////////
    // ///////////////////////////////////////////
    def static void addJavaImport(CompilationUnit compUnit, TypeReference jTypeRef) {
        if (jTypeRef instanceof org.emftext.language.java.types.PrimitiveType) {
            return // nothing to do
        } else if (jTypeRef.target !== null && jTypeRef.target instanceof ConcreteClassifier) {
            val jType = jTypeRef.target as ConcreteClassifier
            addJavaImport(compUnit, jType)
            // add imports for typeArguments if any exist
            if (jTypeRef instanceof NamespaceClassifierReference) {
                val typeArguments = (jTypeRef as NamespaceClassifierReference).classifierReferences.get(0).typeArguments
                for (typeArgument : typeArguments.filter(QualifiedTypeArgument).filter[it.typeReference !== null]) {
                    typeArgument.typeReference
                    addJavaImport(compUnit, typeArgument.typeReference)
                }
            }
        } else {
            // shouldn't occur
        }
    }

    def static ClassifierImport addJavaImport(CompilationUnit compUnit, ConcreteClassifier jType) {
        if (compUnit === null) {
            throw new IllegalStateException("Tried to add a java import without passing a CompilationUnit.")
        }
        if (jType === null || jType instanceof org.emftext.language.java.types.PrimitiveType) {
            return null // nothing to do
        }

        /*
         * If a referenced type lives in the same package as the concerned CompilationUnit, then an import
         * is generally unnecessary. However, if the Compilation unit is then moved to another package,
         * the transformation has to ensure that imports for types in the same package are added afterwards.
         * 
         * The samePackage check is disabled, because the required transformation implementation to support 
         * this feature is still missing. Until then, imports are added for every referenced Type, so that the 
         * CompilationUnit remains compilable after beeing moved.
         */
//		val targetNamespace = if (jType.containingCompilationUnit !== null) jType.containingCompilationUnit.namespaces.join(".") else ""
//		val samePackage = targetNamespace.equals(compUnit.namespaces.join(".")) 
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

    /**
     * Creates a java::TypeReference with the correct type for the passed uml::Type. 
     * Depending on the Class of uType, this can be a java::NamespaceClassifierReference or a java PrimitiveType. 
     * 
     * @param uType
     * 		the input uml Type
     * @param jType 
     * 		the ConcreteClassifier (Class or Interface) that was retrieved from the correspondence model
     * @param defaultReference 
     * 		the output TypeReference this method should default to, if uType is null or cannot be mapped
     */
    def static TypeReference createTypeReference(
        Type uType,
        Optional<? extends ConcreteClassifier> jType,
        TypeReference defaultReference,
        UserInteractor userInteractor
    ) {
        return createTypeReference(uType, jType.orElse(null), defaultReference, userInteractor)
    }

    /**
     * Creates a java::TypeReference with the correct type for the passed uml::Type. 
     * Depending on the Class of uType, this can be a java::NamespaceClassifierReference or a java PrimitiveType. 
     * 
     * @param uType
     * 		the input uml Type
     * @param jType 
     * 		the ConcreteClassifier (Class or Interface) that was retrieved from the correspondence model (may be null)
     * @param defaultReference 
     * 		the output TypeReference this method should default to, if uType is null or cannot be mapped
     */
    def static TypeReference createTypeReference(
        Type uType,
        ConcreteClassifier jType,
        TypeReference defaultReference,
        UserInteractor userInteractor
    ) {
        var TypeReference typeReference = null
        if (jType !== null) {
            typeReference = JavaModificationUtil.createNamespaceClassifierReference(jType)
        } else if (uType !== null && uType instanceof PrimitiveType) {
            typeReference = mapUmlPrimitiveToJavaPrimitive(uType as PrimitiveType)
        } else if (uType === null) {
            typeReference = defaultReference
        }

        if (typeReference === null) {
            showMessage(
                userInteractor,
                "Something went wrong and no java::TypeReference could be created for:\n\t" + uType + "\nUsing " + defaultReference.target + " instead."
            )
            typeReference = defaultReference
        }
        return typeReference
    }

    def static NamespaceClassifierReference createCollectionTypeReference(
        NamespaceClassifierReference collectionTypeReference,
        TypeReference innerTypeReference
    ) {
        // wrap typeReference if necessary
        var wrappedInnerReference = innerTypeReference
        if (wrappedInnerReference instanceof org.emftext.language.java.types.PrimitiveType) {
            wrappedInnerReference = JavaModificationUtil.getWrapperTypeReferenceForPrimitiveType(wrappedInnerReference)
        }

        // set the inner type reference on the NamespaceClassifierReference of the Collection type
        val qualifiedTypeArgument = GenericsFactory.eINSTANCE.createQualifiedTypeArgument();
        qualifiedTypeArgument.typeReference = wrappedInnerReference;
        collectionTypeReference.classifierReferences.get(0).typeArguments += qualifiedTypeArgument;

        return collectionTypeReference
    }

    def static NamespaceClassifierReference createCollectionTypeReference(
        ConcreteClassifier collectionTypeClassifier,
        TypeReference innerTypeReference
    ) {
        val collectionTypeReference = JavaModificationUtil.createNamespaceClassifierReference(collectionTypeClassifier)
        return createCollectionTypeReference(collectionTypeReference, innerTypeReference)
    }

    def static NamespaceClassifierReference createCollectionTypeReference(
        Class<?> collectionType,
        TypeReference innerTypeReference
    ) {
        val collectionNamespace = collectionType.name.replace("." + collectionType.simpleName, "")
        val collectionSimpleName = collectionType.simpleName
        val collectionTypeReference = JavaModificationUtil.createNamespaceClassifierReferenceForName(collectionNamespace, collectionSimpleName)

        return createCollectionTypeReference(collectionTypeReference, innerTypeReference)
    }

}

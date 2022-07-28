package tools.vitruv.applications.umljava.util

import java.util.ArrayList
import java.util.HashSet
import java.util.LinkedList
import java.util.List
import java.util.Optional
import org.apache.log4j.Logger
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.UMLPackage
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypesFactory
import tools.vitruv.applications.util.temporary.java.JavaModificationUtil
import tools.vitruv.change.interaction.UserInteractionOptions.WindowModality
import tools.vitruv.change.interaction.UserInteractor

import static tools.vitruv.applications.umljava.util.CommonUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.resource.ResourceSet
import static tools.vitruv.applications.util.temporary.uml.UmlTypeUtil.getUmlPrimitiveTypes
import tools.vitruv.applications.util.temporary.other.CorrespondenceRetriever

/**
 * Helper class for the UML <-> Java - reactions. Contains functions for handling java::TypeReferences
 * and user interaction concerned with Type and CollectionType propagation.
 */
@Utility
class UmlJavaTypePropagationHelper {
	static val logger = Logger.getLogger(UmlJavaTypePropagationHelper.simpleName)

	static val List<Class<?>> supportedCollectionTypes = #[ArrayList, LinkedList, HashSet]

	/**
	 * Prompts a message to the user that allows him to choose a collection datatype.
	 * 
	 * @param userInteractor the userInteractor to prompt the message
	 * @return the selected Collection implementation Class
	 */
	def static Class<?> userSelectCollectionType(UserInteractor userInteractor) {
		val String selectTypeMsg = "Select a Collection type for the association end"
		val int selectedType = userInteractor.singleSelectionDialogBuilder.message(selectTypeMsg).
			choices(supportedCollectionTypes.map[it.name]).windowModality(WindowModality.MODAL).startInteraction()
		return supportedCollectionTypes.get(selectedType)
	}

	def static dispatch Type getUmlTypeFromReference(Void jRef, CorrespondenceRetriever correspondenceRetriever) {
		return null
	}

	def static dispatch Type getUmlTypeFromReference(TypeReference jRef,
		CorrespondenceRetriever correspondenceRetriever) {
		return null
	}

	def static dispatch Type getUmlTypeFromReference(
		org.emftext.language.java.types.PrimitiveType jRef,
		CorrespondenceRetriever correspondenceRetriever
	) {
		return PrimitiveTypesPropagation.mapJavaPrimitiveTypeToUmlPrimitiveType(jRef, correspondenceRetriever)
	}

	def static dispatch Type getUmlTypeFromReference(ClassifierReference jRef,
		CorrespondenceRetriever correspondenceRetriever) {
		// it could be a wrapped primitive type or java.lang.String
		val umlPrimitive = PrimitiveTypesPropagation.
			mapJavaPrimitiveTypeToUmlPrimitiveType(jRef, correspondenceRetriever)
		if (umlPrimitive !== null) {
			return umlPrimitive
		}

		val classifier = getNormalizedClassifierFromTypeReference(jRef)
		if (classifier !== null)
			return correspondenceRetriever.retrieveCorrespondingElement(classifier, Type, null) as Type
		else {
			return null
		}
	}

	def static dispatch Type getUmlTypeFromReference(NamespaceClassifierReference jRef,
		CorrespondenceRetriever correspondenceRetriever) {
		return if (!jRef.classifierReferences.nullOrEmpty)
			getUmlTypeFromReference(jRef.classifierReferences.head, correspondenceRetriever)
		else
			null
	}

	/**
	 * Creates a java::TypeReference with the correct type for the passed uml::Type.
	 * Depending on the Class of uType, this can be a java::NamespaceClassifierReference or a java PrimitiveType.
	 * 
	 * @param uType
	 *      the input uml Type
	 * @param jType
	 *      the ConcreteClassifier (Class or Interface) that was retrieved from the correspondence model
	 * @param defaultReference
	 *      the output TypeReference this method should default to, if uType is null or cannot be mapped
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
	 *      the input uml Type
	 * @param jType
	 *      the ConcreteClassifier (Class or Interface) that was retrieved from the correspondence model (may be null)
	 * @param defaultReference
	 *      the output TypeReference this method should default to, if uType is null or cannot be mapped
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
			typeReference = getJavaTypeReferenceForUmlPrimitiveType(uType as PrimitiveType)
		} else if (uType === null) {
			typeReference = defaultReference
		}

		if (typeReference === null) {
			showMessage(
				userInteractor,
				"Something went wrong and no java::TypeReference could be created for:\n\t" + uType + "\nUsing " +
					defaultReference.target + " instead."
			)
			typeReference = defaultReference
		}
		return typeReference
	}

	def static getJavaTypeReferenceForUmlPrimitiveType(PrimitiveType umlPrimitiveType) {
		PrimitiveTypesPropagation.mapUmlPrimitiveTypeToJavaPrimitiveType(umlPrimitiveType)
	}

	def static isSupportedUmlPrimitiveType(PrimitiveType umlPrimitiveType) {
		PrimitiveTypesPropagation.isSupportedUmlPrimitiveType(umlPrimitiveType)
	}

	def static getNotRegisteredPrimitiveTypesWithUnifiedNames(CorrespondenceRetriever correspondenceRetriever,
		ResourceSet resourceSet) {
		PrimitiveTypesPropagation.getNotRegisteredPrimitiveTypesWithUnifiedNames(correspondenceRetriever, resourceSet)
	}

	private static class PrimitiveTypesPropagation {
		private static enum UnifiedPrimitiveType {
			BOOLEAN,
			BYTE,
			CHAR,
			FLOAT,
			DOUBLE,
			INT,
			LONG,
			SHORT,
			STRING,
			VOID
		}

		val static umlPrimitiveTypeNamesToUnifiedNames = #{
			"Boolean" -> UnifiedPrimitiveType.BOOLEAN,
			"Bool" -> UnifiedPrimitiveType.BOOLEAN,
			"Byte" -> UnifiedPrimitiveType.BYTE,
			"Char" -> UnifiedPrimitiveType.CHAR,
			"Float" -> UnifiedPrimitiveType.FLOAT,
			"Real" -> UnifiedPrimitiveType.DOUBLE,
			"Double" -> UnifiedPrimitiveType.DOUBLE,
			"Long" -> UnifiedPrimitiveType.LONG,
			"Short" -> UnifiedPrimitiveType.SHORT,
			"Integer" -> UnifiedPrimitiveType.INT,
			"Int" -> UnifiedPrimitiveType.INT,
			"String" -> UnifiedPrimitiveType.STRING
		}

		/**
		 * Returns the {@link UmlJavaTypePropagationHelper$UnifiedPrimitiveType} for the given UML primitive type.
		 * Returns <code>null</code> if there is no unified type for the given type.
		 */
		private def static UnifiedPrimitiveType getUnifiedNameForUmlPrimitiveTypeName(PrimitiveType umlPrimitiveType) {
			umlPrimitiveTypeNamesToUnifiedNames.get(umlPrimitiveType.name.toFirstUpper)
		}

		/**
		 * Returns the {@link UmlJavaTypePropagationHelper$UnifiedPrimitiveType} for the given Java primitive type.
		 * Returns <code>null</code> if there is no unified type for the given type.
		 */
		private def static UnifiedPrimitiveType getUnifiedNameForJavaPrimitiveTypeName(
			org.emftext.language.java.types.PrimitiveType javaPrimitiveType) {
			switch (javaPrimitiveType) {
				org.emftext.language.java.types.Boolean: UnifiedPrimitiveType.BOOLEAN
				org.emftext.language.java.types.Char: UnifiedPrimitiveType.CHAR
				org.emftext.language.java.types.Float: UnifiedPrimitiveType.FLOAT
				org.emftext.language.java.types.Double: UnifiedPrimitiveType.DOUBLE
				org.emftext.language.java.types.Int: UnifiedPrimitiveType.INT
				org.emftext.language.java.types.Long: UnifiedPrimitiveType.LONG
				org.emftext.language.java.types.Short: UnifiedPrimitiveType.SHORT
			}
		}

		private def static org.emftext.language.java.types.PrimitiveType unwrapWrappedPrimitiveType(
			TypeReference javaTypeReference) {
			val classifier = getNormalizedClassifierFromTypeReference(javaTypeReference)
			if(classifier === null) return null
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

		def static isSupportedUmlPrimitiveType(PrimitiveType umlPrimitiveType) {
			umlPrimitiveType.unifiedNameForUmlPrimitiveTypeName !== null
		}

		def static Iterable<Pair<PrimitiveType, String>> getNotRegisteredPrimitiveTypesWithUnifiedNames(
			CorrespondenceRetriever correspondenceRetriever, ResourceSet resourceSet) {
			val notRegisteredPrimitiveTypes = getUmlPrimitiveTypes(resourceSet).filter [
				hasCorrespondingElement(it, correspondenceRetriever)
			]
			return notRegisteredPrimitiveTypes.map[it -> unifiedNameForUmlPrimitiveTypeName.toString]
		}

		private def static hasCorrespondingElement(PrimitiveType primitiveType,
			CorrespondenceRetriever correspondenceRetriever) {
			val unifiedType = primitiveType.unifiedNameForUmlPrimitiveTypeName
			if (unifiedType !== null) {
				val alreadyRegisteredElement = correspondenceRetriever.retrieveCorrespondingElement(
					UMLPackage.Literals.PRIMITIVE_TYPE,
					PrimitiveType,
					unifiedType.toString
				)
				return alreadyRegisteredElement === null
			}
			return false
		}

		/**
		 * Retrieves the predefined uml::PrimitiveType corresponding to the java::TypeReference.
		 * <br><br>
		 * This method is defined with TypeReference as input instead of the more specific PrimitiveType,
		 * because java.lang.String, which is a Classifier held by a TypeReference, and wrapped primitive types
		 * are mapped to uml::PrimitiveTypes as well and are supposed to be retrieved with this method.
		 * <br><br>
		 * Currently supported java types because only those have a good correspondence in "pathmap://UML_LIBRARIES/JavaPrimitiveTypes.library.uml":
		 * 	Boolean, Integer, Double, String
		 * 
		 * @param jRef
		 * 		the java type (ClassifierReference or PrimitiveType) for which to retrieve the registered uml::PrimitiveType
		 * @param cm
		 * 		the correspondenceModel where the uml::PrimitiveTypes are registered
		 * @return
		 * 		the mapped uml::PrimitiveType or null if no matching mapping exists
		 */
		def static dispatch PrimitiveType mapJavaPrimitiveTypeToUmlPrimitiveType(TypeReference javaTypeReference,
			CorrespondenceRetriever correspondenceRetriever) {
			val classifier = getNormalizedClassifierFromTypeReference(javaTypeReference)
			if (classifier !== null) {
				// check if it is a wrapped primitive type
				val unwrappedPrimitive = unwrapWrappedPrimitiveType(javaTypeReference)
				if (unwrappedPrimitive !== null)
					return mapJavaPrimitiveTypeToUmlPrimitiveType(unwrappedPrimitive, correspondenceRetriever)
				// check if it is of type String, which has to be mapped to an uml::PrimitiveType
				if (getQualifiedName(classifier) == "java.lang.String" ||
					getQualifiedName(classifier) == "java.lang.CharSequence") {
					correspondenceRetriever.retrieveCorrespondingElement(UMLPackage.Literals.PRIMITIVE_TYPE,
						PrimitiveType, UnifiedPrimitiveType.STRING.toString) as PrimitiveType
				} else {
					return null
				}
			}
		}

		def static TypeReference mapUmlPrimitiveTypeToJavaPrimitiveType(PrimitiveType uType) {
			val unifiedType = uType.unifiedNameForUmlPrimitiveTypeName
			if (unifiedType === null) {
				logger.warn(
					"(uml -> java) Unsupported uml::PrimitiveType with name: " + uType.name +
						"\n Please use the types defined in \"pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml\" " +
						"or \"pathmap://UML_LIBRARIES/JavaPrimitiveTypes.library.uml\".")
				return TypesFactory.eINSTANCE.createVoid
			}
			switch (unifiedType) {
				case BOOLEAN:
					return TypesFactory.eINSTANCE.createBoolean
				case BYTE:
					return TypesFactory.eINSTANCE.createByte
				case CHAR:
					return TypesFactory.eINSTANCE.createChar
				case FLOAT:
					return TypesFactory.eINSTANCE.createFloat
				case DOUBLE:
					return TypesFactory.eINSTANCE.createDouble
				case INT:
					return TypesFactory.eINSTANCE.createInt
				case LONG:
					return TypesFactory.eINSTANCE.createLong
				case SHORT:
					return TypesFactory.eINSTANCE.createShort
				case STRING:
					return JavaModificationUtil.createNamespaceClassifierReferenceForName("java.lang", "String")
				case VOID:
					return TypesFactory.eINSTANCE.createVoid
			}
		}

		def static dispatch PrimitiveType mapJavaPrimitiveTypeToUmlPrimitiveType(
			org.emftext.language.java.types.PrimitiveType javaTypeReference,
			CorrespondenceRetriever correspondenceRetriever) {
			val unifiedPrimitiveType = javaTypeReference.unifiedNameForJavaPrimitiveTypeName
			if (unifiedPrimitiveType !== null) {
				correspondenceRetriever.retrieveCorrespondingElement(UMLPackage.Literals.PRIMITIVE_TYPE, PrimitiveType,
					unifiedPrimitiveType.toString) as PrimitiveType
			}
		}

	}
}

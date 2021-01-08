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
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import edu.kit.ipd.sdq.activextendannotations.Utility

/**
 * Helper class for the Uml <-> Java - reactions. Contains functions for handling java::TypeReferences
 * and user interaction concerned with Type and CollectionType propagation.
 * 
 * @author Torsten Syma
 */
@Utility
class UmlJavaTypePropagationHelper {
	static val logger = Logger.getLogger(UmlJavaTypePropagationHelper.simpleName)

	public static val UML_PRIMITIVE_BOOLEAN_TAG = "Boolean"
	public static val UML_PRIMITIVE_REAL_TAG = "Real"
	public static val UML_PRIMITIVE_INTEGER_TAG = "Integer"
	public static val UML_PRIMITIVE_STRING_TAG = "String"

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

	def static dispatch Type getUmlTypeFromReference(Void jRef, CorrespondenceModel cm) {
		return null
	}

	def static dispatch Type getUmlTypeFromReference(TypeReference jRef, CorrespondenceModel cm) {
		return null
	}

	def static dispatch Type getUmlTypeFromReference(org.emftext.language.java.types.PrimitiveType jRef,
		CorrespondenceModel cm) {
		return mapJavaPrimitiveToUmlPrimitive(jRef, cm)
	}

	def static dispatch Type getUmlTypeFromReference(ClassifierReference jRef, CorrespondenceModel cm) {
		// it could be a wrapped primitive type or java.lang.String
		val umlPrimitive = mapJavaPrimitiveToUmlPrimitive(jRef, cm)
		if (umlPrimitive !== null) {
			return umlPrimitive
		}

		val classifier = getNormalizedClassifierFromTypeReference(jRef)
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

	def static org.emftext.language.java.types.PrimitiveType unwrapWrappedPrimitiveType(TypeReference jRef) {
		val classifier = getNormalizedClassifierFromTypeReference(jRef)
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
		val classifier = getNormalizedClassifierFromTypeReference(jRef)
		if (classifier !== null) {
			// check if it is a wrapped primitive type
			val unwrappedPrimitive = unwrapWrappedPrimitiveType(jRef)
			if (unwrappedPrimitive !== null)
				return mapJavaPrimitiveToUmlPrimitive(unwrappedPrimitive, cm)
			// check if it is of type String, which has to be mapped to an uml::PrimitiveType
			if (getQualifiedName(classifier) == "java.lang.String") {
				val umlString = ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm,
					UMLPackage.Literals.PRIMITIVE_TYPE, UML_PRIMITIVE_STRING_TAG, PrimitiveType).head
				return umlString
			} else {
				return null
			}
		}
	}

	def static dispatch PrimitiveType mapJavaPrimitiveToUmlPrimitive(org.emftext.language.java.types.PrimitiveType jRef,
		CorrespondenceModel cm) {
		return switch jRef {
			case jRef instanceof Boolean:
				ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm, UMLPackage.Literals.PRIMITIVE_TYPE,
					UML_PRIMITIVE_BOOLEAN_TAG, PrimitiveType).head
			case jRef instanceof Double:
				ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm, UMLPackage.Literals.PRIMITIVE_TYPE,
					UML_PRIMITIVE_REAL_TAG, PrimitiveType).head
			case jRef instanceof Int:
				ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm, UMLPackage.Literals.PRIMITIVE_TYPE,
					UML_PRIMITIVE_INTEGER_TAG, PrimitiveType).head
			default: {
				logger.warn(
					"Tried to map a java primitive type, that is not supported by the uml <-> java transformations: " +
						jRef)
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
			typeReference = mapUmlPrimitiveToJavaPrimitive(uType as PrimitiveType)
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

}

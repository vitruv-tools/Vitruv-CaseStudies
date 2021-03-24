package tools.vitruv.applications.pcmjava.util.java2pcm

import java.util.Set
import org.apache.log4j.Logger
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.types.Boolean
import org.emftext.language.java.types.Byte
import org.emftext.language.java.types.Char
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.Double
import org.emftext.language.java.types.Float
import org.emftext.language.java.types.Int
import org.emftext.language.java.types.Long
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.PrimitiveType
import org.emftext.language.java.types.Short
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.Void
import org.palladiosimulator.pcm.core.entity.NamedElement
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.domains.pcm.util.PrimitiveTypesRepositoryLoader
import tools.vitruv.applications.util.temporary.pcm.PcmDataTypeUtil
import edu.kit.ipd.sdq.activextendannotations.Utility
import static com.google.common.base.Preconditions.checkState
import java.util.List

/**
 * Helper to map type References to PCM data types
 * 
 */
@Utility
class TypeReferenceCorrespondenceHelper {

	static final Logger logger = Logger.getLogger(TypeReferenceCorrespondenceHelper.simpleName)

	private def static DataType claimPCMDataTypeForJaMoPPPrimitiveType(PrimitiveType primitiveJavaType) {
		switch primitiveJavaType {
			Void: null
			Boolean: PrimitiveTypesRepositoryLoader.getPrimitiveDataTypes.get("BOOL")
			Byte: PrimitiveTypesRepositoryLoader.getPrimitiveDataTypes.get("BYTE")
			Char: PrimitiveTypesRepositoryLoader.getPrimitiveDataTypes.get("CHAR")
			Double: PrimitiveTypesRepositoryLoader.getPrimitiveDataTypes.get("DOUBLE")
			Int: PrimitiveTypesRepositoryLoader.getPrimitiveDataTypes.get("INT")
			Long: PrimitiveTypesRepositoryLoader.getPrimitiveDataTypes.get("INT")
			Float: PrimitiveTypesRepositoryLoader.getPrimitiveDataTypes.get("DOUBLE")
			Short: PrimitiveTypesRepositoryLoader.getPrimitiveDataTypes.get("INT")
			default: throw new IllegalStateException("No PCM type for Java type '" + primitiveJavaType.eClass.name + "' defined")
		}
	}
	
	def static DataType getCorrespondingPCMDataTypeForTypeReference(TypeReference typeReference,
		CorrespondenceModel correspondenceModel, UserInteractor userInteractor, Repository repo, long arrayDimension) {
		var DataType pcmDataType = getDataTypeFromTypeReference(typeReference, correspondenceModel, userInteractor,
			repo)

		if (arrayDimension > 0 && null !== pcmDataType && null !== repo) {
			// find CollectionDatatype list for innerValue or create new one
			val typeName = "List_" + PcmDataTypeUtil.getNameFromPCMDataType(pcmDataType)
			var collectionDataType = repo.dataTypes__Repository.filter(CollectionDataType).findFirst [
				it.entityName.equals(typeName)
			]
			if (null === collectionDataType) {
				collectionDataType = RepositoryFactory.eINSTANCE.createCollectionDataType
				collectionDataType.innerType_CollectionDataType = pcmDataType
				collectionDataType.entityName = typeName
				repo.dataTypes__Repository.add(collectionDataType)
				if (!(pcmDataType instanceof PrimitiveDataType)) {
					// create a correspondence from the collection to the non collection DataType.
					// reason: as long as the inner type exists the collection resectievly an array can be used easily
					correspondenceModel.createAndAddCorrespondence(List.of(collectionDataType), List.of(pcmDataType))
				}
			}
			pcmDataType = collectionDataType
		}
		return pcmDataType
	}

	/**
	 * Returns a valid PCM type for the given type reference. Throws an exception if none was found
	 */
	def static DataType getDataTypeFromTypeReference(TypeReference typeReference,
		CorrespondenceModel correspondenceModel, UserInteractor userInteractor, Repository repo) {
		if (typeReference instanceof PrimitiveType) {
			return claimPCMDataTypeForJaMoPPPrimitiveType(typeReference)
		}
		val type = if (typeReference instanceof ClassifierReference) {
			getPCMDataTypeForClassifierReference(typeReference, correspondenceModel, userInteractor, repo)
		} else if (typeReference instanceof NamespaceClassifierReference) {
			getPCMDataTypeForNamespaceClassifierReference(typeReference, correspondenceModel, userInteractor,
				repo)
		}
		checkState(type !== null, "Could not find a PCM data type for type reference %s", typeReference)
		return type
	}

	def private static DataType getPCMDataTypeForNamespaceClassifierReference(NamespaceClassifierReference reference,
		CorrespondenceModel correspondenceModel, UserInteractor userInteractor, Repository repo) {
		if (!reference.classifierReferences.nullOrEmpty) {

			// just create the data type from the first classifier that is non null
			for (classifierRef : reference.classifierReferences) {
				if (null !== classifierRef) {
					return getPCMDataTypeForClassifierReference(classifierRef, correspondenceModel,
						userInteractor, repo)
				}
			}
		}
		logger.error("Could not find a PCM data type for namespace classifier reference " + reference +
			" Possible reason: no classifierReference found inside namespace classifier reference.")
		return null
	}

	def private static DataType getPCMDataTypeForClassifierReference(ClassifierReference classifierReference,
		CorrespondenceModel correspondenceModel, UserInteractor userInteractor, Repository repo) {
		val Classifier classifier = classifierReference.target
		if (null !== classifier) {

			// if classifier is string return primitive type string
			if (classifier.name.equalsIgnoreCase("String")) {
				return PrimitiveTypesRepositoryLoader.getPrimitiveDataTypeString()
			}
			var Set<DataType> dataTypes = null
			try {
				dataTypes = correspondenceModel.getCorrespondingEObjects(classifier, DataType)
			} catch (Throwable t) {
				logger.info("No correspondence found for classifier")
				return null
			}
			if (!dataTypes.nullOrEmpty) {
				if (1 == dataTypes.size) {

					// found only datatype
					return dataTypes.get(0)
				} else {
					logger.warn("more than one datatype correspondences found: returning the first")
					return dataTypes.get(0)
				}
			}

			// no data type found -->create one from the class
			val DataType newDataType = createDataTypeForClassifier(classifier, correspondenceModel, userInteractor,
				repo)
			return newDataType
		}
		return null
	}

	def private static DataType createDataTypeForClassifier(Classifier classifier,
		CorrespondenceModel correspondenceModel, UserInteractor userInteractor, Repository repo) {
		if (null === classifier) {
			logger.warn("Classifier is null! Can not create a data type for the classifier")
			return null
		}
		val correspondingPCMEObjects = correspondenceModel.getCorrespondingEObjects(classifier, NamedElement)
		var String correspondingWarning = ""
		if (!correspondingPCMEObjects.nullOrEmpty) {
			correspondingWarning = System.getProperty("line.seperator") + "Warning: the classifier " + classifier.name +
				" already correspond to the PCM Elements: "
			for (namedElement : correspondingPCMEObjects) {
				correspondingWarning = correspondingWarning + System.getProperty("line.seperator") +
					namedElement.class.simpleName + ": " + namedElement.entityName
			}
		}

		// create a composite data type
		val CompositeDataType cdt = RepositoryFactory.eINSTANCE.createCompositeDataType
		cdt.entityName = classifier.name
		cdt.repository__DataType = repo
		correspondenceModel.createAndAddCorrespondence(List.of(cdt), List.of(classifier))

		/*val String message = "Automatically created the corresponding composite data type " + cdt.entityName +
		 * 	" for classifier " + classifier.name + correspondingWarning
		 userInteraction.showMessage(UserInteractionType.MODELESS, message)*/
		return cdt
	}

}

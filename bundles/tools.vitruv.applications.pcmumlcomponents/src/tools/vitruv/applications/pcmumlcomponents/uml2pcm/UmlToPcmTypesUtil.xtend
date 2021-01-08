package tools.vitruv.applications.pcmumlcomponents.uml2pcm

import java.util.ArrayList
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.DataType
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.domains.pcm.util.RepositoryModelLoader
import tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsCorrespondence
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.extensions.dslsruntime.reactions.ReactionsCorrespondenceModelViewFactory
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class UmlToPcmTypesUtil {

	public static val CollectionTypeAttributeName = "innerType"
	public static val COLLECTION_TYPE_TAG = "Collection"
	public static val COLLECTION_TYPE_SUFFIX = "Collection"

	protected static val primitiveTypeMapping = newHashMap(
		"Integer" -> PrimitiveTypeEnum.INT,
		"Real" -> PrimitiveTypeEnum.DOUBLE,
		"Boolean" -> PrimitiveTypeEnum.BOOL,
		"String" -> PrimitiveTypeEnum.STRING,
		// see http://www.omg.org/spec/UML/20131001/PrimitiveTypes.xmi
		"UnlimitedNatural" -> PrimitiveTypeEnum.STRING
	)

	private static def getReactionsView(CorrespondenceModel correspondenceModel) {
		return correspondenceModel.getEditableView(ReactionsCorrespondenceModelViewFactory.instance);
	}

	static def PrimitiveTypeEnum getDataTypeEnumValue(String typeName) {
		return if (primitiveTypeMapping.containsKey(typeName))
			primitiveTypeMapping.get(typeName)
		else
			null
	}

	static def org.palladiosimulator.pcm.repository.DataType retrieveCorrespondingPcmType(
		DataType umlType,
		Repository pcmRepository,
		Boolean isCollection,
		UserInteractor userInteractor,
		CorrespondenceModel correspondenceModel
	) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlType]).flatten
		var correspondences = correspondingElements
		val tagName = if(isCollection) COLLECTION_TYPE_TAG else ""
		correspondences = correspondenceModel.reactionsView.getCorrespondingEObjects(#[umlType], tagName).flatten
		if (!correspondences.empty) {
			return correspondences.head as org.palladiosimulator.pcm.repository.DataType
		}
		var org.palladiosimulator.pcm.repository.DataType correspondingType = null
		if (!correspondingElements.empty) {
			correspondingType = correspondingElements.head as org.palladiosimulator.pcm.repository.DataType
		} else {
			// There is no corresponding type - a new one is to be associated
			if (primitiveTypeMapping.containsKey(umlType.name)) {
				correspondingType = PrimitiveTypesHelper.get(primitiveTypeMapping.get(umlType.name))
			} else {
				val promptMsg = "For this data type no mapping to a PCM primitive type is available. Select an applicable type from the provided options"
				var options = new ArrayList<String>(PrimitiveTypeEnum.values.map[pt|pt.getName])
				options.add("Create a composite Type")
				val userSelection = userInteractor.singleSelectionDialogBuilder.message(promptMsg).choices(options).
					windowModality(WindowModality.MODAL).startInteraction()
				val selectedType = PrimitiveTypeEnum.get(userSelection)
				if (userSelection >= PrimitiveTypeEnum.VALUES.length) {
					// Create a composite Type instead
					val compositeType = RepositoryFactory.eINSTANCE.createCompositeDataType()
					compositeType.entityName = umlType.name
					correspondingType = compositeType
					pcmRepository.dataTypes__Repository += compositeType
				} else {
					correspondingType = PrimitiveTypesHelper.get(selectedType)
				}
			}
			createTaggedCorrespondence(correspondenceModel, umlType, correspondingType, "")
		}
		// There is a corresponding inner type but it's not a collection type
		if (isCollection && correspondingType !== null) {
			val pcmCollectionType = createCollectionDataType(umlType.name, correspondingType, pcmRepository)
			createTaggedCorrespondence(correspondenceModel, umlType, pcmCollectionType, COLLECTION_TYPE_TAG)
			return pcmCollectionType
		} else if (correspondingType === null) {
			throw new IllegalStateException("No corresponding type was found for: " + umlType);
		}
		return correspondingType
	}

	protected static def CollectionDataType createCollectionDataType(String name,
		org.palladiosimulator.pcm.repository.DataType innerType, Repository repository) {
		val pcmCollectionType = RepositoryFactory.eINSTANCE.createCollectionDataType()
		pcmCollectionType.entityName = name + COLLECTION_TYPE_SUFFIX
		pcmCollectionType.innerType_CollectionDataType = innerType
		repository.dataTypes__Repository += pcmCollectionType
		return pcmCollectionType
	}

	protected static def ReactionsCorrespondence createTaggedCorrespondence(CorrespondenceModel correspondenceModel,
		EObject elementA, EObject elementB, String tag) {
		val correspondence = correspondenceModel.reactionsView.createAndAddCorrespondence(#[elementA], #[elementB])
		correspondence.tag = tag
		return correspondence
	}

	protected static class PrimitiveTypesHelper {

		protected static val PRIMITIVETYPES_URI = "pathmap://PCM_MODELS/PrimitiveTypes.repository"

		protected static var Repository primitiveTypesRepository = null
		protected static var Map<PrimitiveTypeEnum, PrimitiveDataType> primitives = new HashMap<PrimitiveTypeEnum, PrimitiveDataType>()

		static def get(PrimitiveTypeEnum pcmType) {
			getPrimitiveTypes().get(pcmType)
		}

		protected static def loadPrimitiveTypesRepository() {
			if (primitiveTypesRepository === null) {
				val resource = RepositoryModelLoader.loadPcmResource(URI.createURI(PRIMITIVETYPES_URI))
				primitiveTypesRepository = resource.contents.head as Repository
			}
		}

		protected static def Map<PrimitiveTypeEnum, PrimitiveDataType> getPrimitiveTypes() {
			if (primitiveTypesRepository === null) {
				loadPrimitiveTypesRepository()
				for (dataType : primitiveTypesRepository.dataTypes__Repository) {
					if (dataType instanceof PrimitiveDataType) {
						primitives.put(dataType.type, dataType)
					}
				}
			}
			return primitives
		}
	}
}

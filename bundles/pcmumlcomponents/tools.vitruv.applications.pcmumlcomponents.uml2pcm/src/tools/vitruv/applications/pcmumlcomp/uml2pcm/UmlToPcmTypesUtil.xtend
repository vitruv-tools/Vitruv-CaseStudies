package tools.vitruv.applications.pcmumlcomp.uml2pcm

import java.util.ArrayList
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.uml2.uml.DataType
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsCorrespondence
import tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsFactory
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.userinteraction.UserInteractionType

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

	static def org.palladiosimulator.pcm.repository.DataType retrieveCorrespondingPcmType(
		DataType umlType,
		Repository pcmRepository,
		Boolean isCollection,
		UserInteracting userInteracting,
		CorrespondenceModel correspondenceModel
	) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlType]).flatten
		var correspondences = correspondingElements
		if (isCollection) {
			val tagName = COLLECTION_TYPE_TAG
			correspondences = correspondences.filter[c|(c as ReactionsCorrespondence).tag == tagName].toSet()
		}
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
				var options = new ArrayList<String>(PrimitiveTypeEnum.values.map[pt | pt.getName])
				options.add("Create a composite Type")
				val userSelection = userInteracting.selectFromMessage(UserInteractionType.MODAL, promptMsg, options)
				val selectedType = PrimitiveTypeEnum.get(userSelection)
				if (userSelection >= PrimitiveTypeEnum.VALUES.length) {
					// Create a composite Type instead
					val compositeType = RepositoryFactory.eINSTANCE.createCompositeDataType()
					compositeType.entityName = umlType.name
					correspondingType = compositeType
				} else {
					correspondingType = PrimitiveTypesHelper.get(selectedType)
				}
			}
			correspondenceModel.createAndAddCorrespondence(#[umlType], #[correspondingType])
		}
		// There is a corresponding inner type but it's not a collection type
		if (isCollection && correspondingType !== null) {
			val pcmCollectionType = createCollectionDataType(umlType.name, correspondingType, pcmRepository)
			val correspondence = createTaggedCorrespondence(umlType, pcmCollectionType, COLLECTION_TYPE_TAG)
			correspondenceModel.addCorrespondence(correspondence)
			return pcmCollectionType
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

	protected static def ReactionsCorrespondence createTaggedCorrespondence(EObject elementA, EObject elementB,
		String tag) {
		val correspondence = ReactionsFactory.eINSTANCE.createReactionsCorrespondence()
		correspondence.^as += elementA
		correspondence.bs += elementB
		correspondence.tag = tag
		return correspondence
	}
	
	protected static class PrimitiveTypesHelper {
		
		//protected static val PRIMITIVETYPES_URI = "platform:/plugin/org.palladiosimulator.pcm.resources/defaultModels/PrimitiveTypes.repository"
		protected static val PRIMITIVETYPES_URI = "pathmap://PCM_MODELS/PrimitiveTypes.repository"
		
		protected static var Repository primitiveTypesRepository = null
		protected static var Map<PrimitiveTypeEnum, PrimitiveDataType> primitives = new HashMap<PrimitiveTypeEnum, PrimitiveDataType>()
		
		static def get(PrimitiveTypeEnum pcmType) {
			getPrimitiveTypes().get(pcmType)
		}
		
		protected static def loadPrimitiveTypesRepository() {
			if (primitiveTypesRepository === null) {				
				val URI uri = URI.createURI(PRIMITIVETYPES_URI)
			
				val ResourceSet resSet = new ResourceSetImpl()
				val Resource resource = resSet.getResource(uri, true)
				
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

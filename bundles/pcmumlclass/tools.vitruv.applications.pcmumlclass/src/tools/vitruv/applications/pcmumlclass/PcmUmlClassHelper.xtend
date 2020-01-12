package tools.vitruv.applications.pcmumlclass

import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.PrimitiveType
import org.palladiosimulator.pcm.repository.ParameterModifier
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import org.eclipse.uml2.uml.Type
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.RepositoryFactory
import java.util.function.Function
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.userinteraction.UserInteractionOptions.NotificationType
import org.eclipse.uml2.uml.Model
import java.util.Set
import org.apache.log4j.Logger

class PcmUmlClassHelper {
	private static val PCM_PRIMITIVE_TYPES_URI = URI.createURI("pathmap://PCM_MODELS/PrimitiveTypes.repository");
	private static val UML_PRIMITIVE_TYPES_URI = URI.createURI("pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml");
     private static val logger = Logger.getLogger(PcmUmlClassHelper.simpleName)

	private new() {
	}
	
	/**
	 * TODO FIXME TS This method is DIRECTLY copied from  tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper due to the lack of a shared helper/util class
     * Searches and retrieves the UML package in the UML model that has an equal name as the given package name.
     * If there is more than one package with the given name, an {@link IllegalStateException} is thrown.
     * 
     * @param umlModel the UML model Model in which the UML packages should be searched
     * @param packageName the package name for which a fitting UML package should be retrieved
     * @return the UML package or null if none could be found
     */
    def static Package findUmlPackage(Model umlModel, String packageName) {
        val Set<Package> allPackages = umlModel.eAllContents.filter(Package).toSet
        val packages = allPackages.filter[name == packageName]
        if (packages.nullOrEmpty) {
            logger.warn("The UML-Package with the name " + packageName + " does not exist in the correspondence model")
            return null
        }
        if (packages.size > 1) {
            throw new IllegalStateException("There is more than one package with name " + packageName + " in the UML model.")
        }
        return packages.head
    }

	def public static getPcmPrimitiveTypes(EObject alreadyPersistedObject) {
		return getPcmPrimitiveTypes(alreadyPersistedObject.eResource.resourceSet)
	}

	def public static getUmlPrimitiveTypes(EObject alreadyPersistedObject) {
		return getUmlPrimitiveTypes(alreadyPersistedObject.eResource.resourceSet)
	}

	def public static getPcmPrimitiveTypes(Function<URI, Resource> resourceRetriever) {
		val resource = resourceRetriever.apply(PCM_PRIMITIVE_TYPES_URI)
		val pcmPrimitiveTypes = resource.allContents.filter(PrimitiveDataType).toList
		return pcmPrimitiveTypes
	}

	def public static getPcmPrimitiveTypes(ResourceSet resourceSet) {
		var List<PrimitiveDataType> pcmPrimitiveTypes = #[]
		val resource = resourceSet.getResource(PCM_PRIMITIVE_TYPES_URI, true)
		pcmPrimitiveTypes = resource.allContents.filter(PrimitiveDataType).toList
		return pcmPrimitiveTypes
	}

	def public static getUmlPrimitiveTypes(Function<URI, Resource> resourceRetriever) {
		val resource = resourceRetriever.apply(UML_PRIMITIVE_TYPES_URI)
		val umlPrimitiveTypes = resource.allContents.filter(PrimitiveType).toList
		return umlPrimitiveTypes
	}

	def public static getUmlPrimitiveTypes(ResourceSet resourceSet) {
		var List<PrimitiveType> umlPrimitiveTypes = #[]
		val resource = resourceSet.getResource(UML_PRIMITIVE_TYPES_URI, true)
		umlPrimitiveTypes = resource.allContents.filter(PrimitiveType).toList
		return umlPrimitiveTypes
	}

	public static def isSupportedPcmPrimitiveType(PrimitiveDataType pcmPrimitiveType) {
		return switch (pcmPrimitiveType.type) {
			case PrimitiveTypeEnum.BOOL,
			case PrimitiveTypeEnum.INT,
			case PrimitiveTypeEnum.DOUBLE,
			case PrimitiveTypeEnum.STRING: true
			default: false
		}
	}

	public static def isSupportedUmlPrimitiveType(PrimitiveType umlPrimitiveType) {
		return switch (umlPrimitiveType.name) {
			case "Boolean",
			case "Integer",
			case "Real",
			case "String": true
			default: false
		}
	}

	def public static PrimitiveType mapPrimitiveTypes(PrimitiveDataType pcmPredefinedPrimitiveType,
		Iterable<PrimitiveType> umlPredifinedPrimitiveTypes) {
		return switch (pcmPredefinedPrimitiveType.type) {
			case PrimitiveTypeEnum.BOOL: umlPredifinedPrimitiveTypes.findFirst[it.name == "Boolean"]
			case PrimitiveTypeEnum.INT: umlPredifinedPrimitiveTypes.findFirst[it.name == "Integer"]
			case PrimitiveTypeEnum.DOUBLE: umlPredifinedPrimitiveTypes.findFirst[it.name == "Real"]
			case PrimitiveTypeEnum.STRING: umlPredifinedPrimitiveTypes.findFirst[it.name == "String"]
			default: null
		// pcm::Char, pcm::Byte, uml::UnlimitedNatural are not mapped and the user is notified if one of these types is set
		}
	}

	def public static getMatchingParameterDirection(ParameterModifier pcmModifier) {
		return switch (pcmModifier) {
			case IN: ParameterDirectionKind.IN_LITERAL
			case OUT: ParameterDirectionKind.OUT_LITERAL
			case INOUT: ParameterDirectionKind.INOUT_LITERAL
			case NONE: ParameterDirectionKind.IN_LITERAL
			default: ParameterDirectionKind.IN_LITERAL
		}
	}

	/**
	 * Test if the passed package is contained directly or indirectly in a package, that corresponds to a pcm::Repository.
	 * 
	 * @param pkg 
	 * 		the package for which the parent hierarchy will be searched
	 * @param correspondenceModel
	 * 		the CorrespondenceModel that is actively being used
	 * @return 
	 * 		true, if any nesting package of pkg correspond to a pcm::Repository according to the correspondenceModel
	 */
	def public static boolean isContainedInRepositoryHierarchy(Package pkg, CorrespondenceModel correspondenceModel) {
		var parentPkg = pkg.nestingPackage
		var repositoryFound = false
		while (parentPkg !== null && !repositoryFound) {
			repositoryFound = repositoryFound || isRepositoryPackage(parentPkg, correspondenceModel)
			parentPkg = parentPkg.nestingPackage
		}
		return repositoryFound
	}

	def public static boolean isRepositoryPackage(Package pkg, CorrespondenceModel corrModel) {
		return !ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(corrModel, pkg,
			TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE, Repository).nullOrEmpty
	}

	public static def getCorrespondingPcmDataType(CorrespondenceModel corrModel, Type umlType, int lower, int upper,
		Repository pcmRepository, UserInteractor userInteractor) {
		if(umlType === null) return null

		val pcmPrimitiveType = ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(corrModel, umlType,
			TagLiterals.DATATYPE__TYPE, PrimitiveDataType)
		val pcmCompositeType = ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(corrModel, umlType,
			TagLiterals.COMPOSITE_DATATYPE__CLASS, CompositeDataType)
		val pcmSimpleType = #[pcmPrimitiveType.head, pcmCompositeType.head].findFirst[it !== null]

		if (umlType !== null && pcmSimpleType === null) {
			// warn user that a non-synchronized DataType has been set where it should not
			userInteractor.notificationDialogBuilder.message("The uml::Type " + umlType +
				" could not be resolved to a corresponding pcm::DataType.").notificationType(NotificationType.WARNING).
				startInteraction
			return null
		}

		var pcmDataType = pcmSimpleType
		if (pcmSimpleType !== null && lower == 0 && upper == LiteralUnlimitedNatural.UNLIMITED) {
			// userInteraction to disambiguate from multiple Collection Types
			val pcmCollectionTypeCandidates = pcmRepository.dataTypes__Repository.filter(CollectionDataType).filter [
				it.innerType_CollectionDataType === pcmSimpleType
			].toList
			var CollectionDataType pcmCollectionDataType = null
			if (pcmCollectionTypeCandidates.size <= 1) {
				pcmCollectionDataType = pcmCollectionTypeCandidates.head
			} else {
				val userSelection = userInteractor.singleSelectionDialogBuilder.message(
					"Please select the appropriate pcm::CollectionDataType:").choices(pcmCollectionTypeCandidates.map [
					it.entityName
				]).startInteraction
				pcmCollectionDataType = pcmCollectionTypeCandidates.get(userSelection)
			}
			if (pcmCollectionDataType === null) {
				// none found -> create default
				pcmCollectionDataType = RepositoryFactory.eINSTANCE.createCollectionDataType
				pcmCollectionDataType.entityName = umlType.name + "_CollectionDataType"
				pcmCollectionDataType.innerType_CollectionDataType = pcmSimpleType
				pcmRepository.dataTypes__Repository += pcmCollectionDataType
			}
			pcmDataType = pcmCollectionDataType
		}

		return pcmDataType
	}

}

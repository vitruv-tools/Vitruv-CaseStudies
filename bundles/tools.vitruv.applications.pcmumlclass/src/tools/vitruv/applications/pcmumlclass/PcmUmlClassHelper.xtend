package tools.vitruv.applications.pcmumlclass

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Type
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.ParameterModifier
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.userinteraction.UserInteractionOptions.NotificationType
import tools.vitruv.framework.userinteraction.UserInteractor
import org.eclipse.uml2.uml.Model
import org.palladiosimulator.pcm.system.System
import org.palladiosimulator.pcm.core.entity.Entity

@Utility
class PcmUmlClassHelper {

	def static Iterable<PrimitiveType> mapPrimitiveTypes(PrimitiveDataType pcmPredefinedPrimitiveType,
		Iterable<PrimitiveType> umlPredefinedPrimitiveTypes) {
		return switch (pcmPredefinedPrimitiveType.type) {
			case PrimitiveTypeEnum.BOOL: umlPredefinedPrimitiveTypes.filter[it.name.toLowerCase == "bool" || it.name.toLowerCase == "boolean"]
			case PrimitiveTypeEnum.BYTE: umlPredefinedPrimitiveTypes.filter[it.name.toLowerCase == "byte"]
			case PrimitiveTypeEnum.CHAR: umlPredefinedPrimitiveTypes.filter[it.name.toLowerCase == "char"]
			case PrimitiveTypeEnum.INT: umlPredefinedPrimitiveTypes.filter[it.name.toLowerCase == "int" || it.name.toLowerCase == "integer"]
			case PrimitiveTypeEnum.DOUBLE: umlPredefinedPrimitiveTypes.filter[it.name.toLowerCase == "double" || it.name.toLowerCase == "real"]
			case PrimitiveTypeEnum.STRING: umlPredefinedPrimitiveTypes.filter[it.name.toLowerCase == "string"]
			default: null
		// uml::UnlimitedNatural are not mapped and the user is notified if one of these types is set
		}
	}

	def static getMatchingParameterDirection(ParameterModifier pcmModifier) {
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
	def static boolean isContainedInRepositoryHierarchy(Package pkg, CorrespondenceModel correspondenceModel) {
		var parentPkg = pkg.nestingPackage
		var repositoryFound = false
		while (parentPkg !== null && !repositoryFound) {
			repositoryFound = repositoryFound || isRepositoryPackage(parentPkg, correspondenceModel)
			parentPkg = parentPkg.nestingPackage
		}
		return repositoryFound
	}

	def private static boolean isRepositoryPackage(Package pkg, CorrespondenceModel corrModel) {
		return !ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(corrModel, pkg,
			TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE, Repository).nullOrEmpty
	}

	def static getCorrespondingPcmDataType(CorrespondenceModel corrModel, Type umlType, int lower, int upper,
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

	static def String getCorrespondingPackageName(Entity entity) {
		return entity.entityName.toFirstLower
	}

	static def boolean isPackageFor(Package pkg, Entity entity) {
		// We ignore the casing of packages
		return switch (entity) {
			Repository, System:
				pkg.eContainer instanceof Model
			default:
				true 
		} && pkg.name.toLowerCase == entity.entityName.toLowerCase
	}

}

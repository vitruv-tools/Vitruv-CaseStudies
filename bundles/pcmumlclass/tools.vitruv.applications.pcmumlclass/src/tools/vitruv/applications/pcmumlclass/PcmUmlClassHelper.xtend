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

class PcmUmlClassHelper {
	private new(){}
	
	def public static getPcmPrimitiveTypes(EObject alreadyPersistedObject){
		return getPcmPrimitiveTypes(alreadyPersistedObject.eResource.resourceSet)
	}
	
	def public static getUmlPrimitiveTypes(EObject alreadyPersistedObject){
		return getUmlPrimitiveTypes(alreadyPersistedObject.eResource.resourceSet)
	}
	
	def public static getPcmPrimitiveTypes(ResourceSet resourceSet){
		var List<PrimitiveDataType> pcmPrimitiveTypes = #[]
		val uri = URI.createURI("pathmap://PCM_MODELS/PrimitiveTypes.repository")
		if(true){ //URIUtil.existsResourceAtUri(uri)){	//check does not yet support 'pathmap://' URIs
			val resource = resourceSet.getResource(uri,true)
			pcmPrimitiveTypes = resource.allContents.filter(PrimitiveDataType).toList				
		}
		return pcmPrimitiveTypes
	}
	
	def public static getUmlPrimitiveTypes(ResourceSet resourceSet){
		var List<PrimitiveType> umlPrimitiveTypes = #[]
		val uri = URI.createURI("pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml")
		if(true){ //URIUtil.existsResourceAtUri(uri)){	//check does not yet support 'pathmap://' URIs
			val resource = resourceSet.getResource(uri,true)
			umlPrimitiveTypes = resource.allContents.filter(PrimitiveType).toList		
		}
		return umlPrimitiveTypes
	}
	
	def public static PrimitiveType mapPrimitiveTypes(PrimitiveDataType pcmPredefinedPrimitiveType, Iterable<PrimitiveType> umlPredifinedPrimitiveTypes){
		return switch (pcmPredefinedPrimitiveType.type){
			case PrimitiveTypeEnum.BOOL: umlPredifinedPrimitiveTypes.findFirst[it.name == "Boolean"]
			case PrimitiveTypeEnum.INT: umlPredifinedPrimitiveTypes.findFirst[it.name == "Integer"]
			case PrimitiveTypeEnum.DOUBLE: umlPredifinedPrimitiveTypes.findFirst[it.name == "Real"]
			case PrimitiveTypeEnum.STRING: umlPredifinedPrimitiveTypes.findFirst[it.name == "String"]
			case PrimitiveTypeEnum.CHAR,
			case PrimitiveTypeEnum.BYTE,
			default : null
			// TODO decide how to map Char, Byte, UnlimitedNatural
		}
	}
	
	def public static getMatchingParameterDirection(ParameterModifier pcmModifier){
		return switch(pcmModifier){
				case IN: ParameterDirectionKind.IN_LITERAL
				case OUT: ParameterDirectionKind.OUT_LITERAL
				case INOUT: ParameterDirectionKind.INOUT_LITERAL
				case NONE: ParameterDirectionKind.INOUT_LITERAL
				default: ParameterDirectionKind.INOUT_LITERAL}
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
	def public static boolean isContainedInRepositoryHierarchy(Package pkg, CorrespondenceModel correspondenceModel){
		var parentPkg = pkg.nestingPackage
		var repositoryFound = false
		while (parentPkg !== null && !repositoryFound){
			repositoryFound = repositoryFound || isRepositoryPackage(parentPkg, correspondenceModel)
			parentPkg = parentPkg.nestingPackage
		}
		return repositoryFound
	}
	
	def public static boolean isRepositoryPackage(Package pkg, CorrespondenceModel corrModel){
		return !ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(corrModel, pkg, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE, Repository).nullOrEmpty
	}
	
	
	
	
}
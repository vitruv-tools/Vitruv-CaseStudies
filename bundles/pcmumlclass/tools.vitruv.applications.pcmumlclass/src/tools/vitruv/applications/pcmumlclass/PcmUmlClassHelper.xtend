package tools.vitruv.applications.pcmumlclass

import java.util.Optional
import org.eclipse.emf.ecore.EObject
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.Repository
import org.eclipse.emf.common.util.URI
import org.palladiosimulator.pcm.repository.ParameterModifier
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import java.util.HashMap
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper

class PcmUmlClassHelper {
	private new(){}
	
	def public static getPcmPrimitiveTypes(EObject alreadyPersistedObject){
		var Iterable<PrimitiveDataType> pcmPrimitiveTypes = #[]
		var Repository primitiveTypesRepository = null 
		val uri = URI.createURI("pathmap://PCM_MODELS/PrimitiveTypes.repository")
		if(true){ //URIUtil.existsResourceAtUri(uri)){	//check does not yet support 'pathmap://' URIs
			val resource = alreadyPersistedObject.eResource.resourceSet.getResource(uri,true)
			primitiveTypesRepository = resource.contents.filter(Repository).head				
		}
		if (primitiveTypesRepository !== null){
			pcmPrimitiveTypes = primitiveTypesRepository.dataTypes__Repository.filter(PrimitiveDataType)
		}
		return pcmPrimitiveTypes
	}
	
	def public static getMatchingParameterDirection(ParameterModifier pcmModifier){
		return switch(pcmModifier){
				case IN: ParameterDirectionKind.IN_LITERAL
				case OUT: ParameterDirectionKind.OUT_LITERAL
				case INOUT: ParameterDirectionKind.INOUT_LITERAL
				case NONE: ParameterDirectionKind.INOUT_LITERAL
				default: ParameterDirectionKind.INOUT_LITERAL}
	}
	
	def public static boolean isContainedInRepositoryHierarchy(org.eclipse.uml2.uml.Package pkg, CorrespondenceModel corrModel){
		var parentPkg = pkg.nestingPackage
		var repositoryFound = false
		while (parentPkg !== null && !repositoryFound){
			repositoryFound = repositoryFound || isRepositoryPackage(parentPkg, corrModel)
			parentPkg = parentPkg.nestingPackage
		}
		return repositoryFound
	}
	
	def public static boolean isRepositoryPackage(org.eclipse.uml2.uml.Package pkg, CorrespondenceModel corrModel){
		return !ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(corrModel, pkg, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE, Repository).nullOrEmpty
	}
	
}
package tools.vitruv.aplications.pcmumlcomp.uml2pcm

import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.repository.CompositeDataType

class UmlToPcmUtil {
	
	static val CollectionTypeAttributeName = "innerType" 
	
	def static PrimitiveTypeEnum getPcmPrimitiveType(String typeName) {
		if (typeName == "Integer")
			return PrimitiveTypeEnum.INT
		if (typeName == "Real")
			return PrimitiveTypeEnum.DOUBLE
		val pcmType = PrimitiveTypeEnum.getByName(typeName.toUpperCase())
		if (pcmType == null)
			return PrimitiveTypeEnum.STRING
		return pcmType
	}
	
	// TODO: probably just don't create CollectionDataTypes
	def static DataType adaptCollectionDataType(org.eclipse.uml2.uml.DataType umlType, DataType pcmType) {
		if (pcmType instanceof CollectionDataType) {
			if (umlType.ownedAttributes.length > 1 ||
				(umlType.ownedAttributes.length == 1 && umlType.ownedAttributes.get(0).name != UmlToPcmUtil.CollectionTypeAttributeName)) {
					val compositeType = RepositoryFactory.eINSTANCE.createCompositeDataType()
					compositeType.entityName = pcmType.entityName
					
					val pcmRepository = pcmType.repository__DataType
					pcmRepository.dataTypes__Repository.remove(pcmType)
				}
		} else if (pcmType instanceof CompositeDataType) {
			if (umlType.ownedAttributes.length == 1 && umlType.ownedAttributes.get(0).name == UmlToPcmUtil.CollectionTypeAttributeName) {
				val collectionType = RepositoryFactory.eINSTANCE.createCollectionDataType()
				collectionType.entityName = pcmType.entityName
				val innerType = pcmType.innerDeclaration_CompositeDataType.get(0).datatype_InnerDeclaration
				collectionType.innerType_CollectionDataType = innerType
				
				val pcmRepository = pcmType.repository__DataType
				pcmRepository.dataTypes__Repository.remove(pcmType)
				pcmRepository.dataTypes__Repository.add(collectionType)
				return collectionType
			}
		}
		return pcmType
	}
}
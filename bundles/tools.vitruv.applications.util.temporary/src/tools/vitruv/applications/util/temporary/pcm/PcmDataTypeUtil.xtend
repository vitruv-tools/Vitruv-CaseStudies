package tools.vitruv.applications.util.temporary.pcm

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.List
import java.util.function.Function
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum

@Utility
class PcmDataTypeUtil {

    static val PCM_PRIMITIVE_TYPES_URI = URI.createURI("pathmap://PCM_MODELS/PrimitiveTypes.repository")

    def static getPcmPrimitiveTypes(EObject alreadyPersistedObject) {
        return getPcmPrimitiveTypes(alreadyPersistedObject.eResource.resourceSet)
    }

    def static getPcmPrimitiveTypes(Function<URI, Resource> resourceRetriever) {
        val resource = resourceRetriever.apply(PCM_PRIMITIVE_TYPES_URI)
        val pcmPrimitiveTypes = resource.allContents.filter(PrimitiveDataType).toList
        return pcmPrimitiveTypes
    }

    def static getPcmPrimitiveTypes(ResourceSet resourceSet) {
        var List<PrimitiveDataType> pcmPrimitiveTypes = #[]
        val resource = resourceSet.getResource(PCM_PRIMITIVE_TYPES_URI, true)
        pcmPrimitiveTypes = resource.allContents.filter(PrimitiveDataType).toList
        return pcmPrimitiveTypes
    }

    def static isSupportedPcmPrimitiveType(PrimitiveDataType pcmPrimitiveType) {
        return switch (pcmPrimitiveType.type) {
            case PrimitiveTypeEnum.BOOL,
            case PrimitiveTypeEnum.INT,
            case PrimitiveTypeEnum.DOUBLE,
            case PrimitiveTypeEnum.STRING: true
            default: false
        }
    }

    def static dispatch getNameFromPCMDataType(PrimitiveDataType primitiveDataType) {
        return primitiveDataType.type.getName
    }

    def static dispatch getNameFromPCMDataType(CollectionDataType collectionDataType) {
        return collectionDataType.entityName
    }

    def static dispatch getNameFromPCMDataType(CompositeDataType compositeDataType) {
        return compositeDataType.entityName
    }
}

package tools.vitruv.applications.util.temporary.pcm

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType

@Utility
class DataTypeUtil {
    public dispatch static def getNameFromPCMDataType(PrimitiveDataType primitiveDataType) {
        return primitiveDataType.type.getName
    }

    public dispatch static def getNameFromPCMDataType(CollectionDataType collectionDataType) {
        return collectionDataType.entityName
    }

    public dispatch static def getNameFromPCMDataType(CompositeDataType compositeDataType) {
        return compositeDataType.entityName
    }
}

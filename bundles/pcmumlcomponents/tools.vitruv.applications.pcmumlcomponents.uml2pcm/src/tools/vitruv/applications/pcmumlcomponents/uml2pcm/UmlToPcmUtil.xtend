package tools.vitruv.applications.pcmumlcomponents.uml2pcm

import org.eclipse.uml2.uml.ParameterDirectionKind
import org.palladiosimulator.pcm.repository.ParameterModifier
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum

class UmlToPcmUtil {
	
	public static val CollectionTypeAttributeName = "innerType" 
	public static val COLLECTION_TYPE_TAG = "Collection" 
	public static val COLLECTION_TYPE_SUFFIX = "Collection" 
	
	def static PrimitiveTypeEnum getPcmPrimitiveType(String typeName) {
		if (typeName == "Integer")
			return PrimitiveTypeEnum.INT
		if (typeName == "Real")
			return PrimitiveTypeEnum.DOUBLE
		if (typeName == "Boolean")
			return PrimitiveTypeEnum.BOOL
		val pcmType = PrimitiveTypeEnum.getByName(typeName.toUpperCase())
		if (pcmType === null)
			return PrimitiveTypeEnum.STRING
		return pcmType
	}
		
	def static ParameterModifier getPcmParameterModifier(ParameterDirectionKind parameterDirection) {
		switch (parameterDirection) {
			case IN_LITERAL: return ParameterModifier.IN
			case OUT_LITERAL: return ParameterModifier.OUT
			case INOUT_LITERAL: return ParameterModifier.INOUT
			case RETURN_LITERAL: throw new IllegalArgumentException("There is no corresponding parameter direction in the PCM.")
		}
	}
}
package tools.vitruv.aplications.pcmumlcomp.pcm2uml

import org.eclipse.uml2.uml.DataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.palladiosimulator.pcm.repository.ParameterModifier

class PcmToUmlUtil {
	
	public static val CollectionTypeAttributeName = "innerType"
	
	def static String getUmlPrimitiveType(PrimitiveTypeEnum dataType) {
		switch dataType {
			case BOOL: "Boolean"
			case BYTE: "Byte"
			case CHAR: "Char"
			case DOUBLE: "Real"
			case INT: "Integer"
			case LONG: "Long"
			case STRING: "String"
		}
	}
	
	def static ParameterDirectionKind getUmlParameterDirection(ParameterModifier parameterModifier) {
		switch (parameterModifier) {
			case IN: return ParameterDirectionKind.IN_LITERAL
			case INOUT: return ParameterDirectionKind.INOUT_LITERAL
			case OUT: return ParameterDirectionKind.OUT_LITERAL
			default: return ParameterDirectionKind.IN_LITERAL
		}
	}
	
	def static void changeCollectionDataTypeReturnType(DataType container, DataType innerType) {
		if (container.getOwnedAttributes.length === 0) {
			container.createOwnedAttribute(CollectionTypeAttributeName, innerType)
		} else {
			if (container.getOwnedAttributes.get(0).name != CollectionTypeAttributeName)
				throw new IllegalArgumentException("Mapped type is not created from a pcm::CollectionDataType")
			container.getOwnedAttributes.get(0).type = innerType
		}
	}
}
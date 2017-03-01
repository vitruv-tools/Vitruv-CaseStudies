package tools.vitruv.aplications.pcmumlcomp.pcm2uml

import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum

class PcmToUmlUtil {
	def static PrimitiveTypeEnum getPcmPrimitiveType(String typeName) {
		PrimitiveTypeEnum.getByName(typeName);
		/*switch typeName {
			case "Bool": PrimitiveTypeEnum.BOOL
			case "Byte": PrimitiveTypeEnum.BYTE
			case "Char": PrimitiveTypeEnum.CHAR
			case "Double": PrimitiveTypeEnum.DOUBLE
			case "Int": PrimitiveTypeEnum.INT
			case "Long": PrimitiveTypeEnum.LONG
			case "String": PrimitiveTypeEnum.STRING
		}*/
	}
	
	def static String getUmlPrimitiveType(PrimitiveTypeEnum dataType) {
		switch dataType {
			case BOOL: "bool"
			case BYTE: "byte"
			case CHAR: "char"
			case DOUBLE: "double"
			case INT: "int"
			case LONG: "long"
			case STRING: "string"
		}
	}
}
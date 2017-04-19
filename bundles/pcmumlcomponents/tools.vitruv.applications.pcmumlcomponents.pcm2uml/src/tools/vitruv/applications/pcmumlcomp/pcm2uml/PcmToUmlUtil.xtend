package tools.vitruv.applications.pcmumlcomp.pcm2uml

import org.eclipse.uml2.uml.DataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.palladiosimulator.pcm.repository.ParameterModifier
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import java.util.HashMap
import java.util.Map
import org.palladiosimulator.pcm.repository.Repository
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl

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
			case NONE: return ParameterDirectionKind.IN_LITERAL
			default: throw new IllegalArgumentException("Invalid parameter modifier")
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
package tools.vitruv.applications.pcmjava.util

import org.emftext.language.java.members.Method
import org.emftext.language.java.types.TypeReference
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType

import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmUtils
import org.palladiosimulator.pcm.repository.Parameter
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

class PcmJavaUtils {
	protected new() {
	}

	/**
	 * Signatures are considered equal if methods have the same name, the same parameter types and the same return type
	 * We do not consider modifiers (e.g. public or private here)
	 */
	public static def boolean hasSameSignature(Method method1, Method method2) {
		if (method1 == method2) {
			return true
		}
		if (!method1.name.equals(method2.name)) {
			return false
		}
		if (!method1.typeReference.hasSameTargetReference(method2.typeReference)) {
			return false
		}
		if (method1.parameters.size != method2.parameters.size) {
			return false
		}
		var int i = 0
		for (param1 : method1.parameters) {
			if (!hasSameTargetReference(param1.typeReference, method2.parameters.get(i).typeReference)) {
				return false
			}
			i++
		}
		return true
	}

	public static def boolean hasSameTargetReference(TypeReference reference1, TypeReference reference2) {
		if (reference1 == reference2 || reference1.equals(reference2)) {
			return true
		}
		val target1 = Java2PcmUtils.getTargetClassifierFromTypeReference(reference1)
		val target2 = Java2PcmUtils.getTargetClassifierFromTypeReference(reference2)
		return target1 == target2 || target1.equals(target2)
	}

	public dispatch static def getNameFromPCMDataType(PrimitiveDataType primitiveDataType) {
		return primitiveDataType.type.getName
	}

	public dispatch static def getNameFromPCMDataType(CollectionDataType collectionDataType) {
		return collectionDataType.entityName
	}

	public dispatch static def getNameFromPCMDataType(CompositeDataType compositeDataType) {
		return compositeDataType.entityName
	}

	public static def void setParameterName(Parameter parameter, String newName) {
		// Set entity name as well if existing
		if (parameter.eClass.EAllAttributes.exists[name == "entityName"]) {
			parameter.eSet(parameter.eClass.EAllAttributes.filter[name == "entityName"].claimOne, newName);
		}
		parameter.parameterName = newName;
	}
}
	
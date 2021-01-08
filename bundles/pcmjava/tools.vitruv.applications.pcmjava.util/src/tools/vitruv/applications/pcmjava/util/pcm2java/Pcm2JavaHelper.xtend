package tools.vitruv.applications.pcmjava.util.pcm2java

import java.util.Optional
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypesFactory
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import edu.kit.ipd.sdq.activextendannotations.Utility

import static tools.vitruv.domains.java.util.JavaModificationUtil.*

@Utility
class Pcm2JavaHelper {

	def static TypeReference createTypeReference(DataType originalDataType,
		Optional<Class> correspondingJavaClassIfExisting) {
		if (null === originalDataType) {
			return TypesFactory.eINSTANCE.createVoid
		}
		var TypeReference innerDataTypeReference = null
		if (originalDataType instanceof PrimitiveDataType) {
			val type = EcoreUtil.copy(
				DataTypeCorrespondenceHelper.claimJaMoPPTypeForPrimitiveDataType(originalDataType))
			if (type instanceof TypeReference) {
				innerDataTypeReference = type
			} else if (type instanceof ConcreteClassifier) {
				innerDataTypeReference = createNamespaceClassifierReference(type)
			} else {
				// This cannot be since the claimForPrimitiveType function does only return TypeReference or ConcreteClassifier
			}
		} else if (correspondingJavaClassIfExisting.present) {
			innerDataTypeReference = createNamespaceClassifierReference(correspondingJavaClassIfExisting.get)
		} else {
			throw new IllegalArgumentException(
				"Either the dataType must be primitive or a correspondingJavaClass must be specified")
		}
		return innerDataTypeReference
	}
}

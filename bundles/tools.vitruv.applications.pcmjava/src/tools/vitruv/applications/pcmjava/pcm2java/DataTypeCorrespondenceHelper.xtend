package tools.vitruv.applications.pcmjava.pcm2java

import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.apache.log4j.Logger
import tools.mdsd.jamopp.model.java.classifiers.ClassifiersFactory
import tools.mdsd.jamopp.model.java.classifiers.ConcreteClassifier
import tools.mdsd.jamopp.model.java.types.Type
import tools.mdsd.jamopp.model.java.types.TypeReference
import tools.mdsd.jamopp.model.java.types.TypesFactory

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.*
import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView

/**
 * Mapping transformation for primitive data types
 * Mapping is as follows:
 * PCM	     JaMoPP		
 * int 		 int
 * string    java.lang.String
 * bool	     boolean
 * byte		 byte
 * char		 char
 * long      long		
 */
@Utility
class DataTypeCorrespondenceHelper {
	static final Logger logger = Logger.getLogger(DataTypeCorrespondenceHelper.simpleName)

	static def Type claimJaMoPPTypeForPrimitiveDataType(PrimitiveDataType primitivePcmType) {
		switch primitivePcmType.type {
			case BOOL: TypesFactory.eINSTANCE.createBoolean
			case BYTE: TypesFactory.eINSTANCE.createByte
			case CHAR: TypesFactory.eINSTANCE.createChar
			case DOUBLE: TypesFactory.eINSTANCE.createDouble
			case INT: TypesFactory.eINSTANCE.createInt
			case LONG: TypesFactory.eINSTANCE.createLong
			case STRING: { 
				val stringClassifier = ClassifiersFactory.eINSTANCE.createClass
				stringClassifier.setName("String")
				return stringClassifier
			}
		}
	}

	static def TypeReference claimUniqueCorrespondingJaMoPPDataTypeReference(DataType dataType,
		EditableCorrespondenceModelView<?> ci) {
		if (null === dataType) {
			return TypesFactory.eINSTANCE.createVoid
		}
		val Type type = claimUniqueCorrespondingType(dataType, ci)
		if (type instanceof TypeReference) {
			return type
		} else if (type instanceof ConcreteClassifier) {
			return createNamespaceClassifierReference(type)
		}
		logger.warn("found type " + type +
			"is neither a TypeReference nor a ConcreteClassifier - could not create and return TypeReference")
		return TypesFactory.eINSTANCE.createClassifierReference
	}

	private static def dispatch Type claimUniqueCorrespondingType(CollectionDataType cdt, EditableCorrespondenceModelView<?> ci) {
		return ci.getCorrespondingEObjects(cdt).filter(ConcreteClassifier).claimOne
	}

	private static def dispatch Type claimUniqueCorrespondingType(PrimitiveDataType pdt, EditableCorrespondenceModelView<?> ci) {
		return claimJaMoPPTypeForPrimitiveDataType(pdt)
	}

	private static def dispatch Type claimUniqueCorrespondingType(CompositeDataType cdt, EditableCorrespondenceModelView<?> ci) {
		return ci.getCorrespondingEObjects(cdt).filter(ConcreteClassifier).claimOne
	}
}

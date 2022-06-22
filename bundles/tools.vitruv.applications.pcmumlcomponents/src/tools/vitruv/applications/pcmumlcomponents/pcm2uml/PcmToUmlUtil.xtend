package tools.vitruv.applications.pcmumlcomponents.pcm2uml

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.MultiplicityElement
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.resource.UMLResource
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.ParameterModifier
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsCorrespondence
import tools.vitruv.change.correspondence.CorrespondenceModel
import tools.vitruv.extensions.dslsruntime.reactions.ReactionsCorrespondenceModelViewFactory
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class PcmToUmlUtil {

	public static val COLLECTION_TYPE_TAG = "Collection"

	def static getUmlPrimitiveType(PrimitiveTypeEnum dataType) {
		return PrimitiveTypeCorrespondenceHelper.getUmlPrimitiveType(dataType)
	}

	def static String getUmlPrimitiveTypeName(PrimitiveTypeEnum dataType) {
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

	def static DataType retrieveUmlType(CorrespondenceModel correspondenceModel,
		org.palladiosimulator.pcm.repository.DataType pcmType, Model umlModel) {
		if (pcmType === null)
			return null
		val correspondingObjects = correspondenceModel.getCorrespondingEObjects(#[pcmType]).flatten
		if (correspondingObjects.length > 0) {
			val correspondingObject = correspondingObjects.get(0)
			if (correspondingObject instanceof DataType) {
				return correspondingObject
			}
		}
		var DataType correspondingType = null
		if (pcmType instanceof PrimitiveDataType) {
			correspondingType = getUmlPrimitiveType(pcmType.type)
			if (correspondingType === null) {
				correspondingType = createCorrespondingPrimitiveType(correspondenceModel, pcmType, umlModel)
			}
		}
		if (pcmType instanceof CollectionDataType) {
			correspondingType = retrieveUmlType(correspondenceModel, pcmType.innerType_CollectionDataType, umlModel)
		}
		if (correspondingType !== null)
			correspondenceModel.createAndAddCorrespondence(#[pcmType], #[correspondingType])
		return correspondingType
	}

	protected static def DataType createCorrespondingPrimitiveType(CorrespondenceModel correspondenceModel,
		PrimitiveDataType pcmType, Model umlModel) {
		val newType = UMLFactory.eINSTANCE.createPrimitiveType()
		newType.name = getUmlPrimitiveTypeName(pcmType.type)
		umlModel.packagedElements += newType
		createTaggedCorrespondence(correspondenceModel, pcmType, newType, "")
		return newType
	}

	private static def getReactionsView(CorrespondenceModel correspondenceModel) {
		return correspondenceModel.getEditableView(ReactionsCorrespondenceModelViewFactory.getInstance())
	}

	protected static def ReactionsCorrespondence createTaggedCorrespondence(CorrespondenceModel correspondenceModel,
		EObject elementA, EObject elementB, String tag) {
		val correspondence = correspondenceModel.reactionsView.createAndAddCorrespondence(#[elementA], #[elementB])
		correspondence.tag = tag
		return correspondence
	}

	def static void updateMultiplicity(MultiplicityElement umlElement, Boolean setToCollection) {
		if (setToCollection) {
			umlElement.lower = 0
			umlElement.upper = LiteralUnlimitedNatural.UNLIMITED
		} else {
			umlElement.lowerValue = null
			umlElement.upperValue = null
		}
	}

	def static void updateOperationReturnTypeMultiplicity(Operation umlOperation, Boolean setToCollection) {
		val returnParameter = umlOperation.ownedParameters.filter [ p |
			p.direction === ParameterDirectionKind.RETURN_LITERAL
		].head
		updateMultiplicity(returnParameter, setToCollection)
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

	protected static class PrimitiveTypeCorrespondenceHelper {

		static val defaultTypes = #["String", "Real", "Boolean", "Integer", "UnlimitedNatural"]

		static var ResourceSet defaultTypesResource = null

		static def PrimitiveType getUmlPrimitiveType(PrimitiveTypeEnum type) {
			if (isUmlDefaultType(type)) {
				return loadDefaultTypesResource().getEObject(
					URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI).appendFragment(
						getUmlPrimitiveTypeName(type)
					),
					true
				) as PrimitiveType
			}
			return null
		}

		protected static def Boolean isUmlDefaultType(String typeName) {
			return PrimitiveTypeCorrespondenceHelper.defaultTypes.contains(typeName)
		}

		protected static def Boolean isUmlDefaultType(PrimitiveTypeEnum dataType) {
			return isUmlDefaultType(getUmlPrimitiveTypeName(dataType))
		}

		protected static def loadDefaultTypesResource() {
			if (defaultTypesResource === null) {
				val resourceSet = new ResourceSetImpl()
				resourceSet.createResource(URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI))
				defaultTypesResource = resourceSet
			}
			return defaultTypesResource
		}
	}
}

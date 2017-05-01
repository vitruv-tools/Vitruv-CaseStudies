package tools.vitruv.applications.pcmumlcomp.pcm2uml

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.MultiplicityElement
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.resource.UMLResource
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.ParameterModifier
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import tools.vitruv.framework.correspondence.CorrespondenceModel
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.UMLFactory

class PcmToUmlUtil {

	public static val CollectionTypeAttributeName = "innerType"

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
	
	def static DataType retrieveUmlType(CorrespondenceModel correspondenceModel, org.palladiosimulator.pcm.repository.DataType pcmType) {
		println("> retrieveUmlType")
		println(pcmType)
		if (pcmType === null)
			return null
		val correspondingObjects = correspondenceModel.getCorrespondingEObjects(#[pcmType]).flatten
		if (correspondingObjects.length > 0) {
			val correspondingObject = correspondingObjects.get(0)
			if (correspondingObject instanceof DataType) {
				return correspondingObject as DataType
			}
		}
		var DataType correspondingType = null
		if (pcmType instanceof PrimitiveDataType) {
			println("is primitive type: " + pcmType.type)
			correspondingType = getUmlPrimitiveType(pcmType.type)
			if (correspondingType === null) {
				correspondingType = createCorrespondingPrimitiveType(correspondenceModel, pcmType)
			}
		}
		if (pcmType instanceof CollectionDataType) {
			println("is collection type: " + pcmType.entityName)
			correspondingType = retrieveUmlType(correspondenceModel, pcmType.innerType_CollectionDataType)
		}
		println(" > corresponding type")
		println(correspondingType)
		//correspondenceModel.createAndAddCorrespondence(#[pcmType], #[correspondingType])
		return correspondingType
	}
	
	protected static def DataType createCorrespondingPrimitiveType(CorrespondenceModel correspondenceModel, PrimitiveDataType pcmType) {
		val repository = pcmType.repository__DataType
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[repository]).flatten
		// TODO no corresponding model available
		if (!correspondingElements.empty) {
			val model = correspondingElements.get(0) as Model
			val newType = UMLFactory.eINSTANCE.createPrimitiveType()
			newType.name = getUmlPrimitiveTypeName(pcmType.type)
			model.packagedElements += newType
			println(">> createCorrespondingPrimitiveType")
			println(pcmType.type)
			println(newType)
			return newType
		}
		return null
	}

	def static void changeCollectionInnerType(CollectionDataType collectionType, DataType correspondingInnerType) {
		val references = EcoreUtil.UsageCrossReferencer.find(collectionType, collectionType.repository__DataType)
		println(references)
	}

	def static void unsetTypeOccurences(CollectionDataType collectionType) {
		val references = EcoreUtil.UsageCrossReferencer.find(collectionType, collectionType.repository__DataType)
		println(references)
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
		val returnParameter = umlOperation.ownedParameters.filter [p |
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

	def static void changeCollectionDataTypeReturnType(DataType container, DataType innerType) {
		if (container.getOwnedAttributes.length === 0) {
			container.createOwnedAttribute(CollectionTypeAttributeName, innerType)
		} else {
			if (container.getOwnedAttributes.get(0).name != CollectionTypeAttributeName)
				throw new IllegalArgumentException("Mapped type is not created from a pcm::CollectionDataType")
			container.getOwnedAttributes.get(0).type = innerType
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
			} else {
				println("is not a default type")
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

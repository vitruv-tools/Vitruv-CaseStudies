package tools.vitruv.applications.pcmumlcomp.uml2pcm

import java.util.ArrayList
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.PrimitiveType
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.ParameterModifier
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.userinteraction.UserInteractionType

class UmlToPcmUtil {
	
	public static val CollectionTypeAttributeName = "innerType" 
	
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
	
	/**
	 * TODO: this should be stored within the framework's correspondences
	 */
	protected static var primitiveTypeMapping = newHashMap(
		"Integer" -> PrimitiveTypeEnum.INT,
		"Real" -> PrimitiveTypeEnum.DOUBLE,
		"Boolean" -> PrimitiveTypeEnum.BOOL,
		"String" -> PrimitiveTypeEnum.STRING,
		// see http://www.omg.org/spec/UML/20131001/PrimitiveTypes.xmi
		"UnlimitedNatural" -> PrimitiveTypeEnum.STRING
	)
	
	protected static val defaultPrimitiveTypes = newArrayList(
		"Integer",
		"Real",
		"Boolean",
		"String",
		"UnlimitedNatural"
	)
	
	/**
	 * Returns whether a primitive type with the given name is defined in the default profile
	 */
	def static boolean isDefaultPrimitiveType(String name) {
		return defaultPrimitiveTypes.contains(name)
	}
	
	/**
	 * If a type gets renamed, the corresponding PCM type should remain associated
	 */
	def static void renamePrimitiveType(String oldName, String newName) {
		if (isDefaultPrimitiveType(oldName) || isDefaultPrimitiveType(newName)) {
			throw new IllegalArgumentException("A type with this name is already imported")
		}
		if (primitiveTypeMapping.containsKey(oldName)) {
			val oldValue = primitiveTypeMapping.get(oldName)
			primitiveTypeMapping.remove(oldName)
			primitiveTypeMapping.put(newName, oldValue)
		}
	}
	
	/**
	 * After removing a primitive type a newly created type with the same name might be
	 * associated with a different type
	 */
	def static void deletePrimitiveType(String oldName) {
		if (!isDefaultPrimitiveType(oldName) && primitiveTypeMapping.containsKey(oldName)) {
			primitiveTypeMapping.remove(oldName)
		}
	}
	
	/**
	 * Returns the primitive PCM type associated with a primitive UML type with name {typeName}
	 * TODO: should handle associated non-primitive types as well
	 */
	def static PrimitiveDataType getPcmPrimitiveType(String typeName, UserInteracting userInteracting) {
		var getPcmType = [PrimitiveTypeEnum t | PrimitiveTypeCorrespondenceHelper.primitiveDataTypes.get(t.getName())]
		if (primitiveTypeMapping.containsKey(typeName)) {
			val mapping = primitiveTypeMapping.get(typeName)
			if (mapping !== null) {
				return getPcmType.apply(mapping)
			}
		}
		else {
			val promptMsg = "For this data type there is no mapping to a PCM primitive type available. Select an applicable type from the provided options"
			var options = new ArrayList<String>(PrimitiveTypeEnum.values.map[pt | pt.getName])
			options.add("Create a composite Type")
			val userSelection = userInteracting.selectFromMessage(UserInteractionType.MODAL, promptMsg, options)
			val selectedType = PrimitiveTypeEnum.get(userSelection)
			primitiveTypeMapping.put(typeName, selectedType)
			if (selectedType !== null) {
				return getPcmType.apply(selectedType)
			}
		}
		return null;
	}
	
	/**
	 * TODO: should be stored in the framework as well
	 */
	protected static var collectionTypeMapping = new HashMap<String, CollectionDataType>()
	
	/**
	 * Returns a collection-type wrapping a given pcmType. If necessary it is created on demand.
	 * Created collection-types are named {umlType.name}Collection
	 */
	def static CollectionDataType getPcmCollectionType(org.eclipse.uml2.uml.DataType umlType, DataType pcmType, Repository pcmRepository) {
		if (collectionTypeMapping.containsKey(umlType.name)) {
			return collectionTypeMapping.get(umlType.name)
		}
		var DataType correspondingType = null
		if (umlType instanceof PrimitiveType) {
			correspondingType = getPcmPrimitiveType(umlType.name, null)
		} else {
			correspondingType = pcmType
		}
		if (correspondingType !== null) {
			val collectionType = RepositoryFactory.eINSTANCE.createCollectionDataType()
			collectionType.entityName = umlType.name + "Collection"
			collectionType.innerType_CollectionDataType = correspondingType
			pcmRepository.dataTypes__Repository += collectionType
			collectionTypeMapping.put(umlType.name, collectionType)
			return collectionType
		}
		return null
	}
	
	def static ParameterModifier getPcmParameterModifier(ParameterDirectionKind parameterDirection) {
		switch (parameterDirection) {
			case IN_LITERAL: return ParameterModifier.IN
			case OUT_LITERAL: return ParameterModifier.OUT
			case INOUT_LITERAL: return ParameterModifier.INOUT
			case RETURN_LITERAL: throw new IllegalArgumentException("There is no corresponding parameter direction in the PCM.")
		}
	}
	
	
	/**
	 * TODO join implementation with {@link bundles/pcmjava/tools.vitruv.applications.pcmjava.util/src/tools/vitruv/applications/pcmjava/util/java2pcm/TypeReferenceCorrespondenceHelper.xtend}
	 * @see TypeReferenceCorrespondenceHelper
	 */
	private static class PrimitiveTypeCorrespondenceHelper {
		// TODO elements returned by this don't have a correct href
		private static val PRIMITIVETYPES_URI = "platform:/plugin/org.palladiosimulator.pcm.resources/defaultModels/PrimitiveTypes.repository"
		
		private static var Repository primitiveTypesRepository
		private static var Map<String, PrimitiveDataType> primitives = new HashMap<String, PrimitiveDataType>()
		
		public static def Map<String, PrimitiveDataType> getPrimitiveDataTypes() {
			if (primitiveTypesRepository === null) {
				primitiveTypesRepository = getPrimitiveTypesRepository()
				for (DataType d : primitiveTypesRepository.dataTypes__Repository) {
					if (d instanceof PrimitiveDataType) {
						val PrimitiveDataType pdt = d as PrimitiveDataType
						primitives.put(pdt.type.getName, pdt)
					}
				}
			}
			return primitives
		}
		
		private static def Repository getPrimitiveTypesRepository() {
			if (primitiveTypesRepository !== null) {
				return primitiveTypesRepository
			}
			
			val Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE
			val Map<String, Object> m = reg.extensionToFactoryMap
			m.put("repository", new XMIResourceFactoryImpl())
			
			val URI uri = URI.createURI(PRIMITIVETYPES_URI)
			
			val ResourceSet resSet = new ResourceSetImpl()
			val Resource resource = resSet.getResource(uri, true)
			
			primitiveTypesRepository = resource.contents.head as Repository
			return primitiveTypesRepository
		}		
	}
}
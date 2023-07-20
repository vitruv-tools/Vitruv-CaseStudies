package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Model
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeComponent
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum

final class PcmUmlClassApplicationTestHelper {
	public static val REPOSITORY_NAME = "TestRepository"

	public static val IMPL_SUFFIX = "Impl"

	public static val COMPONENT_NAME_UC = "TestComponent"
	public static val COMPONENT_NAME_LC = "testComponent"
	public static val COMPONENT_NAME_2_UC = "TestComponent_2"
	public static val COMPONENT_NAME_2_LC = "testComponent_2"

	public static val COMPOSITE_DATATYPE_NAME = "TestCompositeType"
	public static val COMPOSITE_DATATYPE_NAME_2 = "TestCompositeType_2"
	public static val COLLECTION_DATATYPE_NAME = "TestCollectionType"

	public static val INTERFACE_NAME = "TestInterface"
	public static val SIGNATURE_NAME = "testSignature"

	public static val PCM_MODEL_FILE = "model/Repository.repository"
	public static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
		DefaultLiterals.UML_EXTENSION

	// --- PCM model creators ---
	def static createComponent(Repository pcmRepository) {
		return createComponent(pcmRepository, COMPONENT_NAME_UC)
	}

	def static createComponent_2(Repository pcmRepository) {
		return createComponent(pcmRepository, COMPONENT_NAME_2_UC)
	}

	def static createCompositeDataType(Repository pcmRepository) {
		return createCompositeDataType(pcmRepository, COMPOSITE_DATATYPE_NAME)
	}

	def static createCompositeDataType_2(Repository pcmRepository) {
		return createCompositeDataType(pcmRepository, COMPOSITE_DATATYPE_NAME_2)
	}

	def static createCollectionDataType(Repository pcmRepository, DataType innerType) {
		val pcmCollectionType = RepositoryFactory.eINSTANCE.createCollectionDataType
		pcmCollectionType.entityName = COLLECTION_DATATYPE_NAME
		pcmCollectionType.innerType_CollectionDataType = innerType
		pcmRepository.dataTypes__Repository += pcmCollectionType
		return pcmCollectionType
	}

	def static createOperationInterface(Repository pcmRepository) {
		val pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface
		pcmInterface.entityName = INTERFACE_NAME
		pcmRepository.interfaces__Repository += pcmInterface
		return pcmInterface
	}

	def static createOperationSignature(OperationInterface pcmInterface) {
		val pcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature
		pcmSignature.entityName = SIGNATURE_NAME
		pcmInterface.signatures__OperationInterface += pcmSignature
		return pcmSignature
	}
	
	def static PrimitiveDataType createPrimitiveDataType(PrimitiveTypeEnum primitiveType) {
		var dataType = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
		dataType.type = primitiveType
		return dataType;
	}

	// --- PCM model queries ---
	def static getPcmComponent(Repository pcmRepository) {
		return getPcmComponent(pcmRepository, COMPONENT_NAME_UC)
	}

	def static getPcmComponent_2(Repository pcmRepository) {
		return getPcmComponent(pcmRepository, COMPONENT_NAME_2_UC)
	}

	def static getPcmCompositeDataType(Repository pcmRepository) {
		return getPcmCompositeDataType(pcmRepository, COMPOSITE_DATATYPE_NAME)
	}

	def static getPcmCompositeDataType_2(Repository pcmRepository) {
		return getPcmCompositeDataType(pcmRepository, COMPOSITE_DATATYPE_NAME_2)
	}

	def static getPcmCollectionDataType(Repository pcmRepository) {
		return pcmRepository.dataTypes__Repository.filter(CollectionDataType).findFirst [
			it.entityName == COLLECTION_DATATYPE_NAME
		]
	}

	def static getPcmOperationInterface(Repository pcmRepository) {
		return pcmRepository.interfaces__Repository.filter(OperationInterface).findFirst [
			it.entityName == INTERFACE_NAME
		]
	}

	def static getPcmOperationSignature(OperationInterface pcmInterface) {
		return pcmInterface.signatures__OperationInterface.filter(OperationSignature).findFirst [
			it.entityName == SIGNATURE_NAME
		]
	}

	// --- generic PCM creators ---
	private static def createComponent(Repository pcmRepository, String componentName) {
		val pcmComponent = RepositoryFactory.eINSTANCE.createCompositeComponent
		pcmComponent.entityName = componentName
		pcmRepository.components__Repository += pcmComponent
		return pcmComponent
	}

	private def static createCompositeDataType(Repository pcmRepository, String name) {
		val pcmCompositeDataType = RepositoryFactory.eINSTANCE.createCompositeDataType
		pcmCompositeDataType.entityName = name
		pcmRepository.dataTypes__Repository += pcmCompositeDataType
		return pcmCompositeDataType
	}

	// --- generic PCM queries ---
	private static def getPcmComponent(Repository pcmRepository, String componentName) {
		return pcmRepository.components__Repository.filter(CompositeComponent).findFirst[it.entityName == componentName]
	}

	private def static getPcmCompositeDataType(Repository pcmRepository, String componentName) {
		return pcmRepository.dataTypes__Repository.filter(CompositeDataType).findFirst[it.entityName == componentName]
	}

	// --- UML queries ---
	def static claimClass(Model model, String className) {
		return model.eAllContents.filter[Class.isInstance(it)].map[it as Class].filter[it.name == className].toList.
			claimOne
	}
}

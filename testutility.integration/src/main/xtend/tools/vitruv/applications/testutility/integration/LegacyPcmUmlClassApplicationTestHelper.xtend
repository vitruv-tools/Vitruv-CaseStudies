package tools.vitruv.applications.testutility.integration

import java.util.function.Function
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.PrimitiveType
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeComponent
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.applications.util.temporary.uml.UmlTypeUtil

import tools.vitruv.applications.util.temporary.pcm.PcmDataTypeUtil
import tools.vitruv.framework.testutils.deprecated.LegacyCorrespondenceRetriever
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne

final class LegacyPcmUmlClassApplicationTestHelper {
	new(LegacyCorrespondenceRetriever correspondenceRetriever, Function<URI, Resource> resourceRetriever) {
		this.correspondenceRetriever = correspondenceRetriever
		this.resourceRetriever = resourceRetriever

		val pcmPrimitiveTypes = PcmDataTypeUtil.getPcmPrimitiveTypes(resourceRetriever)
		PCM_BOOL = pcmPrimitiveTypes.findFirst[it.type === PrimitiveTypeEnum.BOOL]
		PCM_INT = pcmPrimitiveTypes.findFirst[it.type === PrimitiveTypeEnum.INT]
		PCM_DOUBLE = pcmPrimitiveTypes.findFirst[it.type === PrimitiveTypeEnum.DOUBLE]
		PCM_STRING = pcmPrimitiveTypes.findFirst[it.type === PrimitiveTypeEnum.STRING]
		PCM_CHAR = pcmPrimitiveTypes.findFirst[it.type === PrimitiveTypeEnum.CHAR]
		PCM_BYTE = pcmPrimitiveTypes.findFirst[it.type === PrimitiveTypeEnum.BYTE]

		val umlPrimitiveTypes = UmlTypeUtil.getUmlPrimitiveTypes(resourceRetriever)
		UML_BOOL = umlPrimitiveTypes.findFirst[it.name.toLowerCase == "bool"]
		UML_INT = umlPrimitiveTypes.findFirst[it.name.toLowerCase == "int"]
		UML_DOUBLE = umlPrimitiveTypes.findFirst[it.name.toLowerCase == "double"]
		UML_STRING = umlPrimitiveTypes.findFirst[it.name.toLowerCase == "string"]
	}

	val LegacyCorrespondenceRetriever correspondenceRetriever
	val Function<URI, Resource> resourceRetriever

	/**
	 * Fetches the given {@link EObject} from the {@link ResourceSet} of the running test.
	 * <br>
	 * Elements retrieved via correspondence model are read-only (except in the Transactions performed by the framework),
	 * and live in a different resourceSet. If corresponding elements need to be changed or compared, they should be retrieved via this method, 
	 * or the getModifiableCorr(...) methods.
	 * 
	 * @param original
	 * 		the {@link EObject} instance living in some ResourceSet
	 * @return the object instance in the ResourceSet of this test
	 */
	def <T extends EObject> getModifiableInstance(T original) {
		val originalURI = EcoreUtil.getURI(original)
		return resourceRetriever.apply(originalURI.trimFragment).getEObject(originalURI.fragment) as T
	}

	def <T extends EObject> T getCorr(EObject source, Class<T> typeFilter, String tag) {
		return correspondenceRetriever.getCorrespondingEObjects(source, typeFilter, tag).claimOne()
	}

	def <T extends EObject> T getModifiableCorr(EObject source, Class<T> typeFilter, String tag) {
		val correspondence = getCorr(source, typeFilter, tag)
		if(correspondence === null) return null
		return getModifiableInstance(getCorr(source, typeFilter, tag))
	}

	// here start the factory and retrieval methods
	public static val PCM_MODEL_FILE = "model/Repository.repository"
	public static val PCM_MODEL_SYSTEM_FILE = "model/System.system"
	public static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
		DefaultLiterals.UML_EXTENSION

	public static val REPOSITORY_NAME = "TestRepository"

	public static val COMPONENT_NAME = "TestComponent"
	public static val COMPONENT_NAME_2 = "TestComponent_2"

	public static val COMPOSITE_DATATYPE_NAME = "TestCompositeType"
	public static val COMPOSITE_DATATYPE_NAME_2 = "TestCompositeType_2"
	public static val COLLECTION_DATATYPE_NAME = "TestCollectionType"

	public static val INTERFACE_NAME = "TestInterface"
	public static val SIGNATURE_NAME = "testSignature"

	public val PrimitiveDataType PCM_BOOL
	public val PrimitiveDataType PCM_INT
	public val PrimitiveDataType PCM_DOUBLE
	public val PrimitiveDataType PCM_STRING
	public val PrimitiveDataType PCM_CHAR
	public val PrimitiveDataType PCM_BYTE

	public val PrimitiveType UML_BOOL
	public val PrimitiveType UML_INT
	public val PrimitiveType UML_DOUBLE
	public val PrimitiveType UML_STRING

	// Repository
	def createRepository() {
		val pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		pcmRepository.entityName = REPOSITORY_NAME
		return pcmRepository
	}

	def getUmlRepositoryPackage(Repository pcmRepository) {
		return getModifiableCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
	}

	def getUmlContractsPackage(Repository pcmRepository) {
		return getModifiableCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE)
	}

	def getUmlDataTypesPackage(Repository pcmRepository) {
		return getModifiableCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE)
	}

	// CompositeComponent
	private def createComponent(Repository pcmRepository, String componentName) {
		val pcmComponent = RepositoryFactory.eINSTANCE.createCompositeComponent
		pcmComponent.entityName = componentName
		pcmRepository.components__Repository += pcmComponent
		return pcmComponent
	}

	def createComponent(Repository pcmRepository) {
		return createComponent(pcmRepository, COMPONENT_NAME)
	}

	def createComponent_2(Repository pcmRepository) {
		return createComponent(pcmRepository, COMPONENT_NAME_2)
	}

	private def getPcmComponent(Repository pcmRepository, String componentName) {
		return pcmRepository.components__Repository.filter(CompositeComponent).findFirst[it.entityName == componentName]
	}

	def getPcmComponent(Repository pcmRepository) {
		return getPcmComponent(pcmRepository, COMPONENT_NAME)
	}

	def getPcmComponent_2(Repository pcmRepository) {
		return getPcmComponent(pcmRepository, COMPONENT_NAME_2)
	}

	def getUmlComponentImpl(Repository pcmRepository) {
		return getModifiableCorr(getPcmComponent(pcmRepository), org.eclipse.uml2.uml.Class,
			TagLiterals.IPRE__IMPLEMENTATION)
	}

	def getUmlComponentImpl_2(Repository pcmRepository) {
		return getModifiableCorr(getPcmComponent_2(pcmRepository), org.eclipse.uml2.uml.Class,
			TagLiterals.IPRE__IMPLEMENTATION)
	}

	def getUmlComponentConstructor(Repository pcmRepository) {
		return getModifiableCorr(getPcmComponent(pcmRepository), Operation, TagLiterals.IPRE__CONSTRUCTOR)
	}

	def getUmlComponentConstructor_2(Repository pcmRepository) {
		return getModifiableCorr(getPcmComponent_2(pcmRepository), Operation, TagLiterals.IPRE__CONSTRUCTOR)
	}

	// CompositeDataType
	private def createCompositeDataType(Repository pcmRepository, String name) {
		val pcmCompositeDataType = RepositoryFactory.eINSTANCE.createCompositeDataType
		pcmCompositeDataType.entityName = name
		pcmRepository.dataTypes__Repository += pcmCompositeDataType
		return pcmCompositeDataType
	}

	def createCompositeDataType(Repository pcmRepository) {
		return createCompositeDataType(pcmRepository, LegacyPcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME)
	}

	def createCompositeDataType_2(Repository pcmRepository) {
		return createCompositeDataType(pcmRepository, LegacyPcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2)
	}

	private def getPcmCompositeDataType(Repository pcmRepository, String componentName) {
		return pcmRepository.dataTypes__Repository.filter(CompositeDataType).findFirst[it.entityName == componentName]
	}

	def getPcmCompositeDataType(Repository pcmRepository) {
		return getPcmCompositeDataType(pcmRepository, LegacyPcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME)
	}

	def getPcmCompositeDataType_2(Repository pcmRepository) {
		return getPcmCompositeDataType(pcmRepository, LegacyPcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2)
	}

	def getUmlCompositeDataTypeClass(Repository pcmRepository) {
		return getModifiableCorr(getPcmCompositeDataType(pcmRepository), org.eclipse.uml2.uml.Class,
			TagLiterals.COMPOSITE_DATATYPE__CLASS)
	}

	def getUmlCompositeDataTypeClass_2(Repository pcmRepository) {
		return getModifiableCorr(getPcmCompositeDataType_2(pcmRepository), org.eclipse.uml2.uml.Class,
			TagLiterals.COMPOSITE_DATATYPE__CLASS)
	}

	// CollectionDataType
	def createCollectionDataType(Repository pcmRepository, DataType innerType) {
		val pcmCollectionType = RepositoryFactory.eINSTANCE.createCollectionDataType
		pcmCollectionType.entityName = LegacyPcmUmlClassApplicationTestHelper.COLLECTION_DATATYPE_NAME
		pcmCollectionType.innerType_CollectionDataType = innerType
		pcmRepository.dataTypes__Repository += pcmCollectionType
		return pcmCollectionType
	}

	def getPcmCollectionDataType(Repository pcmRepository) {
		return pcmRepository.dataTypes__Repository.filter(CollectionDataType).findFirst [
			it.entityName == LegacyPcmUmlClassApplicationTestHelper.COLLECTION_DATATYPE_NAME
		]
	}

	// OperationInterface
	def createOperationInterface(Repository pcmRepository) {
		val pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface
		pcmInterface.entityName = INTERFACE_NAME
		pcmRepository.interfaces__Repository += pcmInterface
		return pcmInterface
	}

	def getPcmOperationInterface(Repository pcmRepository) {
		return pcmRepository.interfaces__Repository.filter(OperationInterface).findFirst [
			it.entityName == INTERFACE_NAME
		]
	}

	def getUmlInterface(Repository pcmRepository) {
		return getModifiableCorr(getPcmOperationInterface(pcmRepository), Interface, TagLiterals.INTERFACE_TO_INTERFACE)
	}

	// OperationSignature
	def createOperationSignature(OperationInterface pcmInterface) {
		val pcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature
		pcmSignature.entityName = SIGNATURE_NAME
		pcmInterface.signatures__OperationInterface += pcmSignature
		return pcmSignature
	}

	def getPcmOperationSignature(OperationInterface pcmInterface) {
		return pcmInterface.signatures__OperationInterface.filter(OperationSignature).findFirst [
			it.entityName == SIGNATURE_NAME
		]
	}

	def getUmlOperation(OperationInterface pcmInterface) {
		return getModifiableCorr(getPcmOperationSignature(pcmInterface), Operation, TagLiterals.SIGNATURE__OPERATION)
	}

	def getUmlReturnParameter(OperationInterface pcmInterface) {
		return getModifiableCorr(getPcmOperationSignature(pcmInterface), Parameter,
			TagLiterals.SIGNATURE__RETURN_PARAMETER)
	}

}
